package org.example.dianping.controller;


import cn.hutool.core.bean.BeanUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.dianping.dto.LoginFormDTO;
import org.example.dianping.dto.Result;
import org.example.dianping.dto.UserDTO;
import org.example.dianping.entity.User;
import org.example.dianping.entity.UserInfo;
import org.example.dianping.service.IUserInfoService;
import org.example.dianping.service.IUserService;
import org.example.dianping.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;
    @Resource
    private IUserInfoService userInfoService;
    @GetMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        return userService.sendCode(phone,session);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session){
        return userService.login(loginForm,session);
    }

    @PostMapping("/logout")
    public Result logout(){
        return Result.fail("功能未完成");
    }

    @GetMapping("/me")
    public Result me(){
        return Result.ok(UserHolder.getUser());
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId){
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        return Result.ok(info);
    }
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id")Long userId){
        User user = userService.getById(userId);
        if (user==null){
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        return Result.ok(userDTO);
    }
    @PostMapping("/sign")
    public Result sign(){
        return userService.sign();
    }
    @GetMapping("/sign/count")
    public Result signCount(){
        return userService.signCount();
    }
}
