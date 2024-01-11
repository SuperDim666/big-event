package org.zixiangxu.validation;/*
 * @author Zixiang Xu
 * @date 2024/1/3 20:13
 */


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.zixiangxu.annotation.ArticleStateValidate;

// ConstraintValidator<Generic 01, Generic 02>:
//      Generic 01: Annotation that to provide validation rules for
//      Generic 02: Data type of validation
public class ArticleStateValidation implements ConstraintValidator<ArticleStateValidate, String> {

    /**
     * Validation Rule Provider
     * @param state data that will be validated (Article.state)
     * @param context context in which the constraint is evaluated
     * @return validation result: true/false
     */
    @Override
    public boolean isValid(String state, ConstraintValidatorContext context) {
        // Provide validation rule
        if (state == null) return false;
        return state.equals("Published") || state.equals("Draft");
    }
}