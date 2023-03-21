package com.mxd.redis.RedisLottery;

import redis.clients.jedis.Jedis;
import java.util.*;

/**
 * 在代码中，使用 Redis 客户端 Jedis 连接 Redis，然后将奖品存入有序集合中。根据每个奖品的中奖概率，将其分值存入有序集合中，
 * 并为了方便计算取它们的相反数（奖品概率越高，则分数越小）。
 *
 * 抽奖时，生成一个 0 到 1 的随机数作为分数，然后使用 zrevrangeByScore() 方法，按照分数从高到低的顺序获取第一个符合范围
 * 内的带有分数的成员，即为中奖奖品，最后删除有序集合即可。
 *
 * 需要注意的是，在计算分数时，要做对数转换，将概率值转换为分值。这是因为 Redis 的有序集合中，成员必须唯一，而浮点数可能存
 * 在精度问题，不能作为成员存储，因此可以通过对数转换的方法，将概率近似为唯一的分值。
 */
public class RedisLottery {
    // 中奖概率，四种奖品
    private static final Map<String, Double> PRIZES =
            new HashMap<String, Double>() {{
                put("一等奖", 0.01);
                put("二等奖", 0.02);
                put("三等奖", 0.03);
                put("参与奖", 0.94);
            }};

    public static void main(String[] args) {
        // 连接Redis
        Jedis jedis = new Jedis("localhost", 6379);
        // 初始化奖品信息
        String key = "prizes";
        Map<String, Double> prizeScores = new HashMap<String, Double>();
        for (Map.Entry<String, Double> entry : PRIZES.entrySet()) {
            String prize = entry.getKey();
            Double probability = entry.getValue();
            prizeScores.put(prize, -Math.log(probability));
        }
        // 将奖品存入有序集合
        jedis.zadd(key, prizeScores);
        // 抽奖
        double score = -Math.log(Math.random());
        Set<String> result = jedis.zrevrangeByScore(key, score, 0, 0, 1);
        String prize = result.iterator().next();
        System.out.println("恭喜您获得了" + prize);
        // 删除有序集合
        jedis.del(key);
        // 关闭连接
        jedis.close();
    }
}
