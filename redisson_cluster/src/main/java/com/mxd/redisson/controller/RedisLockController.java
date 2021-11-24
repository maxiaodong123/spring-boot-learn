package com.mxd.redisson.controller;

import com.mxd.redisson.util.RedisUtil;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("")
public class RedisLockController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static String product1Count = "product1Count";//商品1的数量key
    private static String lockKey = "testLockKey";//分布式锁的key
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedissonClient redisson;

    /**
     * 初始化设置商品数量
     *
     * @return
     */
    @RequestMapping("/setProductCount")
    public String setValue() {
        redisTemplate.opsForValue().set(product1Count, "100");
        return "success";
    }

    /**
     * 模拟秒杀抢购，并发多个请求过来，查看是否出现超卖
     *
     * @return
     */
    @RequestMapping("/spike")
    public String spike() {
        String flag = "success";
        RLock lock = redisson.getLock(lockKey);
        try {
            //lock.lockAsync(5 , TimeUnit.SECONDS);
            //lock.lock(5, TimeUnit.SECONDS); //设置60秒自动释放锁  （默认是30秒自动过期）
            Future<Boolean> res = lock.tryLockAsync(100, 5, TimeUnit.SECONDS);
            boolean result = res.get();
            System.out.println("result:" + result);
            if (result) {
                int stock = Integer.parseInt(RedisUtil.get(product1Count).toString());
                if (stock > 0) {
                    redisTemplate.opsForValue().set(product1Count, (stock - 1) + "");
                } else {
                    flag = "fail";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //释放锁
        }
        return flag;
    }


    int a = 100;
    Set<Integer> setA = new HashSet<>();


    /**
     * 采用Redisson的可重入锁方式加锁
     */
    @GetMapping("lock")
    public String lock() throws InterruptedException {
        RLock lock = redisson.getLock(lockKey);
        boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
        if (res) {
            try {
                a = --a;
                setA.add(a);
            } finally {
                lock.unlock();
            }
        }
        log.info("可重入加锁->结果为：{},set的size:{}", a, setA.size());
        return String.valueOf(a);
    }

    /**
     * 采用Redisson的可重入锁方式加锁，异步执行
     */
    @GetMapping("lockAsync")
    public String lockAsync() throws InterruptedException, ExecutionException {
        RLock lock = redisson.getLock(lockKey);
        Future<Boolean> res = lock.tryLockAsync(100, 10, TimeUnit.SECONDS);
        if (res.get()) {
            try {
                a = --a;
                setA.add(a);
            } finally {
                lock.unlock();
            }
        }
        log.info("可重入异步加锁->结果为：{},set的size:{}", a, setA.size());
        return String.valueOf(a);
    }


    /**
     * 采用Redisson的可重入公平锁方式加锁，异步执行
     */
    @GetMapping("fairAsync")
    public String fairAsync() throws InterruptedException, ExecutionException {
        RLock fairLock = redisson.getFairLock(lockKey);
        RFuture<Boolean> booleanRFuture = fairLock.tryLockAsync(100, 10, TimeUnit.SECONDS);
        if (booleanRFuture.get()) {
            try {
                a = --a;
                setA.add(a);
            } finally {
                fairLock.unlock();
            }
        }
        log.info("公平锁异步加锁->结果：{},set的size:{}", a, setA.size());
        return String.valueOf(a);
    }


    /**
     * 采用Redisson的信号量分布式加锁，异步执行
     */
    @GetMapping("semaphore")
    public String semaphore() throws InterruptedException, ExecutionException {
        RSemaphore semaphore = redisson.getSemaphore(lockKey);
        semaphore.trySetPermitsAsync(10);
        RFuture<Boolean> booleanRFuture = semaphore.tryAcquireAsync(10, TimeUnit.SECONDS);
        if (booleanRFuture.get()) {
            try {
                a = --a;
                setA.add(a);
            } finally {
                semaphore.releaseAsync();
            }
        }
        log.info("信号量异步加锁->结果为：{},set的size:{}", a, setA.size());
        return String.valueOf(a);
    }


}

