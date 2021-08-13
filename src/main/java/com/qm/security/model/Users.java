package com.qm.security.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Users {

    private Integer id;

    @TableField("username")
    private String userName;

    @TableField("password")
    private String passWord;

}
