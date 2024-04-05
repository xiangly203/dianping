package org.example.dianping.service;


import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpSession;
import org.example.dianping.dto.LoginFormDTO;
import org.example.dianping.dto.Result;
import org.example.dianping.entity.User;



public interface IUserService extends IService<User> {

    /**
     * 发送验证码
     *
     * @param phone   手机号码
     * @param session 会话
     * @return {@link Result}
     */
    Result sendCode(String phone, HttpSession session);

    /**
     * 登录
     *
     * @param loginForm 登录表单
     * @param session   会话
     * @return {@link Result}
     */
    Result login(LoginFormDTO loginForm, HttpSession session);


    /**
     * 签到
     *
     * @return {@link Result}
     */
    Result sign();

    /**
     * 统计连续签到
     *
     * @return {@link Result}
     */
    Result signCount();
}