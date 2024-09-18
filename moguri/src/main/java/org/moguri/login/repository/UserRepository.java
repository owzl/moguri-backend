package org.moguri.member.repository;

import org.moguri.member.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserRepository {
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(@Param("username") String username);

    @Update("UPDATE users SET password = #{password} WHERE username = #{username}")
    void update(@Param("username") String username, @Param("password") String password);
}
