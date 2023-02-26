package com.study.domain.post;

import lombok.Getter;
import lombok.Setter;

// 게시글 요청 클래스
@Getter
@Setter
public class PostRequest {

    private Long id;              // PK, 게시글 수정시에 WHERE 조건으로 id(PK) 사용
    private String title;         // 제목
    private String content;       // 내용
    private String writer;        // 작성자
    private Boolean noticeYn;     // 공지글 여부
}
