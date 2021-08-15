package com.qm.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 创建数据库表，用于存储用户token
        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
          // 修改用户名密码第二种方式
//        String password = getPasswordEncoder().encode("sml");
//        auth.inMemoryAuthentication()
//                .withUser("sml").password(password).roles("admin");

        // 修改用户名密码第三种方式
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 退出配置
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();
        // 配置没有访问权限跳转的页面 403
        http.exceptionHandling().accessDeniedPage("/unAuth.html");
        // 自定义自己编写的登录页面
        http.formLogin()
                // 登录页面
                .loginPage("/login.html")
                // 登录访问路径，提交到哪个controller中
                .loginProcessingUrl("/user/login")
                // 登录认证成功后跳转的路径
                .defaultSuccessUrl("/index").permitAll()
                // and 设置认证相关
                .and().authorizeRequests()
                // 设置不需要认证的路径，可以直接访问
                .antMatchers("/index", "/user/login").permitAll()
                // 当前登录用户，只有具有admins权限才可访问
                //.antMatchers("/admin").hasAuthority("admins")
                // 当前登录用户，只要具有其中的一个权限就可以访问
                //.antMatchers("/admin").hasAnyAuthority("admins","manager")
                // 当前登录用户，角色为sale才可访问  源码后拼接了 ROLE_sale
                .antMatchers("/admin").hasRole("sale")
                // 同上，多个角色之一即可
                //.antMatchers("/admin").hasAnyRole("sale", "boss")
                .anyRequest().authenticated()
                .and().rememberMe()
                // 设置token存储仓库，就是数据源和表的一些配置
                .tokenRepository(persistentTokenRepository())
                // 令牌有效时间，单位秒s
                .tokenValiditySeconds(60)
                // 查询数据库的userDatailsService
                .userDetailsService(userDetailsService)
                // 关闭csrf防护
                .and().csrf().disable();
    }
}
