package org.example.dianping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dianping.entity.UserInfo;
import org.example.dianping.mapper.UserInfoMapper;
import org.example.dianping.service.IUserInfoService;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
