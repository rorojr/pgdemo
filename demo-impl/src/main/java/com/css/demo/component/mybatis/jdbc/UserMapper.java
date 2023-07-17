package com.css.demo.component.mybatis.jdbc;

import com.css.demo.component.mybatis.model.User;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Insert({
            "<script>",
                "INSERT INTO public.user (id, name, phone)",
                "VALUES (#{id}::uuid, #{name}, #{phone})",
            "</script>"
    })
    void insert(User user);

    @Update({
            "<script>",
                "UPDATE public.user",
                    "<set>",
                    "   <if test=\"name != null and name!=''\">",
                    "   name=#{name}, ",
                    "   </if>",
                    "   <if test=\"phone != null and phone!=''\">",
                    "   phone=#{phone}, ",
                    "   </if>",
                    "</set>",
                "WHERE id = #{id}::uuid",
            "</script>"
    })
    int update(User user);

    @Select({
            "SELECT * FROM public.user WHERE id = #{id}::uuid AND status = 1",
    })
    User getUserById(@Param("id") String id);

    @Select({
            "<script>",
            "SELECT * FROM public.user WHERE 1=1",
            "   <if test=\"name != null and name!=''\">",
            "   AND name=#{name} ",
            "   </if>",
            "   <if test=\"phone != null and phone!=''\">",
            "   AND phone=#{phone} ",
            "   </if>",
            "</script>"
    })
    List<User> queryUsers(@Param("name") String name, @Param("phone") String phone);

    @Delete({
            "DELETE FROM public.user WHERE id = #{id}::uuid",
    })
    int deleteById(@Param("id") String id);
}