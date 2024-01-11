package org.zixiangxu.pojo;/*
 * @author Zixiang Xu
 * @date 2024/1/1 20:32
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Harmonized response results from HTTP
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    // Operation Status Code 0-Success 1-Failure
    private Integer code;

    // Message / Alerts
    private String message;

    // Response Data (with any type T)
    private T data;

    // Return response results with op=SUCCESS
    // (WITH response data)
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "Operation Successful", data);
    }

    // Return response results with op=SUCCESS
    // (WITHOUT response data)
    public static Result<?> success() {
        return new Result<>(0, "Operation Successful", null);
    }

    // Return response results with op=ERROR
    // (WITH message)
    public static Result<?> error(String message) {
        return new Result<>(1, message, null);
    }
}
