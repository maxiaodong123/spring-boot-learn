package com.mxd.mp.service.impl;

import com.mxd.mp.model.User;
import com.mxd.mp.mapper.UserMapper;
import com.mxd.mp.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mxd
 * @since 2019-06-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
