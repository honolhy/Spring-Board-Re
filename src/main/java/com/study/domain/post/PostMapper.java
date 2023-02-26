package com.study.domain.post;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// 데이터베이스와의 통신 역할을 할 mapper 인터페이스
@Mapper
public interface PostMapper {

    /**
     * 게시글 저장
     * @param params - 게시글 정보
     * 파라미터로 전달받는 params는 요청클래스의 객체이며, params에는 저장할 게시글 정보가 담기게 된다
     */
    void save(PostRequest params);

    /**
     * 게시글 상세정보 조회
     * @param id - PK
     * @return 게시글 상세정보
     * 쿼리 실행시 메서드의 리턴타입인 응답클래스 객체의 각 멤버 변수에 결과값이 매핑(바인딩)된다
     */
    PostResponse findById(Long id);
    
    /**
     * 게시글 수정
     * @param params - 게시글 정보
     * 요청클래스의 객체인 params를 파라미터로 전달받으며, params에는 수정할 게시글 정보가 담기게 된다
     */
    void update(PostRequest params);

    /**
     * 게시글 삭제
     * @param id - PK
     * id(PK)를 파라미터로 전달받아 쿼리가 실행되면 삭제여부컬럼(noticeYn)의 값을 0(false)에서 1(true)로 업데이트한다
     */
    void deleteById(Long id);

    /**
     * 게시글 리스트 조회
     * @return 게시글 리스트
     * 여러개의 게시글(PostResponse)을 리스트에 담아 리턴해준다
     */
    List<PostResponse> findAll();

    /**
     * 게시글 수 카운팅
     * @return 게시글 수
     * 전체 게시글 수를 조회
     */
    int count();
}