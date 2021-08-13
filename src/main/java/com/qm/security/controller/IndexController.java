package com.qm.security.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/index")
    public String index() {
        return "hello security";
    }

    @PostMapping("/user/login")
    public String login(String username,String password) {
        return username + password;
    }

    @GetMapping("/admin")
    public String test() {
        return "admin";
    }

    @GetMapping("/update")
    /**
     * 注解 角色校验，拥有该角色用户才可访问（只能写角色）
     * 需要开启注解
     * @EnableGlobalMethodSecurity(securedEnabled = true)
     */
    //@Secured({"ROLE_boss"})
    /**
     * 注解 权限校验 可以写权限，也可以写角色。 进入方法之前校验
     * hasAuthority("admins")
     * hasAnyAuthority("admins","manager")
     * hasRole("sale")
     * hasAnyRole("sale", "boss")
     * 需要开启注解
     * @EnableGlobalMethodSecurity(prePostEnabled = true)
     */
    //@PreAuthorize("hasAnyAuthority('admins')")
    /**
     * 与上一个相似，区别在 方法之后进行校验
     */
    @PostAuthorize("hasAnyAuthority('admins')")
    // 对传入，返回的数据过滤，不常用
    //@PreFilter()
    //@PostFilter()
    public String update() {
        return "hello update";
    }

}
