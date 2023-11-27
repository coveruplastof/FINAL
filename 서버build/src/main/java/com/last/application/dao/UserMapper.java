package com.last.application.dao;

import com.last.domain.entitiy.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    @Insert("INSERT INTO user" +
            " (nickname, profile_image_url, email, accessToken) " +
            "VALUES (#{nickname}, #{profile_image_url}, #{email}, #{accessToken})")

    void insertUser(User user);

    @Delete("DELETE FROM user WHERE accessToken = #{accessToken}")
    void deleteUserByToken(@Param("accessToken") String accessToken);
}
