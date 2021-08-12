package com.qm.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qm.security.model.Users;
import org.springframework.stereotype.Repository;

// 该注解可以不加，防止报错
@Repository
public interface UsersMapper extends BaseMapper<Users> {
}
