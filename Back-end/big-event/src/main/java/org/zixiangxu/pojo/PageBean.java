package org.zixiangxu.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Pagination returns the result object
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageBean <T>{

    //total number of articles in current page
    private Long total;

    // Current page data set (Articles)
    private List<T> items;
}
