package org.zixiangxu.pojo;/*
 * @author Zixiang Xu
 * @date 2024/1/1 19:06
 */

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    // Primary Key ID
    @NotNull(groups = Update.class)
    private Integer id;

    // Category Name
    @NotEmpty // in Default group if not specifying group
    private String categoryName;

    // Category Alias
    @NotEmpty // in Default group if not specifying group
    private String categoryAlias;

    // Creator User ID
    private Integer createUser;

    // Created Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    // Updated Time
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    // 1. If a validation does not specify a group, it belongs to the "Default" group by default.
    // 2. Grouping can be inherited:
    //         A extends B == A owns all the validations in B.
    // --------------------------------------------------
    // Define validation groups
    // Enable validations for CategoryController.update()
    public interface Update extends Default {}
}
