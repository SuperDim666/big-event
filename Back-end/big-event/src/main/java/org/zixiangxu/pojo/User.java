package org.zixiangxu.pojo;/*
 * @author Zixiang Xu
 * @date 2024/1/1 19:07
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    // Primary Key ID
    @NotNull
    private Integer id;

    // Username
    private String username;

    // User Password
    @JsonIgnore // Ignore password when SpringMVC converts current object to JSON string
    private String password;

    // User Nickname
    @NotEmpty @Pattern(regexp=".{3,30}")
    private String nickname;

    // User Email
    @NotEmpty @Email
    private String email;

    // User Picture Address
    @URL
    private String userPic;

    // Created Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // Updated Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
