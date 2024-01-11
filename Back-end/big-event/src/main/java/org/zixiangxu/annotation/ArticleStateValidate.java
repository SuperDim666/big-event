package org.zixiangxu.annotation;/*
 * @author Zixiang Xu
 * @date 2024/1/3 19:57
 */


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.zixiangxu.validation.ArticleStateValidation;


// Meta annotations: @Documented, @Target, @Retention, @Constraint
@Documented
@Target({ElementType.FIELD}) // only applied to the field Article.state
@Retention(RetentionPolicy.RUNTIME) // annotations are preserved at runtime
@Constraint(validatedBy = ArticleStateValidation.class) // fields validated by specified class.isValid()
public @interface ArticleStateValidate {

    // Provide an error message when the validation fails
    String message() default "Article state can only be \"Published\" or \"Draft\"";

    // Specify groups of validation
    Class<?>[] groups() default {};

    // Get additional info about this annotation
    Class<? extends Payload>[] payload() default {};
}
