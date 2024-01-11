package org.zixiangxu.pojo;/*
 * @author Zixiang Xu
 * @date 2024/1/1 19:05
 */


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import org.zixiangxu.annotation.ArticleStateValidate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    // Primary Key ID
    @NotNull(groups = Article.Update.class)
    private Integer id;

    // Article Title
    @NotEmpty @Pattern(regexp = ".{3,100}")
    private String title;

    // Article Content
    @NotEmpty
    private String content;

    // Cover Image
    @NotEmpty @URL
    private String coverImg;

    // Release State: Submitted/Draft
    @ArticleStateValidate
    private String state;

    // Article Category ID
    @NotNull
    private Integer categoryId;

    // Creator User ID
    private Integer createUser;

    // Created Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // Updated Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // Define validation groups
    public interface Update extends Default {}
}
