package com.study.common.dto;

import com.study.paging.Pagination;
import lombok.Getter;
import lombok.Setter;

// 페이징 클래스
/*파라미터의 개수가 많을때 @RequestParam으로 하나하나 전달받는건 좋지않다
파라미터의 관리와 수집이 편해지도록 공통으로 사용할 수 있는 하나의 클래스로 관리*/
@Getter
@Setter
public class SearchDto {

    private int page;                  // 현재 페이지 번호
    private int recordSize;            // 페이지당 출력할 데이터 개수
    private int pageSize;              // 화면 하단에 출력할 페이지 사이즈
    private String keyword;            // 검색 키워드
    private String searchType;         // 검색
    private Pagination pagination;     // 페이지네이션 정보

    public SearchDto() {
        this.page = 1;
        this.recordSize = 10;
        this.pageSize = 10;
    }
}