package com.example.simpleboard.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pagination {

    private Integer page; // 현재 페이지

    private Integer size; // 페이지당 몇개가 들어있는지

    private Integer currentElements; // 현재 가지고 있는 element가 몇개인지, 현재 페이지에 몇개인지

    private Integer totalPage; // 총 페이지

    private Long totalElements; // 전체 element
}
