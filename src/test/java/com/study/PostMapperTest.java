package com.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.domain.post.PostMapper;
import com.study.domain.post.PostRequest;
import com.study.domain.post.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void save() {

        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        postMapper.save(params);

        List<PostResponse> posts = postMapper.findAll();
        System.out.println("전체 게시글 개수는 : " + posts.size() + "개 입니다.");
    }

    @Test
    void findById() {

        PostResponse post = postMapper.findById(5116L);
        try {
            // 스프링부트에 기본으로 내장되어 있는 Jackson 라이브러리를 이용해서 조회한 1번 게시글의 응답 객체를 JSON 문자열로 변환
            // 객체는 디버깅을 해보지 않는 이상 확인이 까다롭기에 JSON 문자열로 변경해서 콘솔에 출력했다
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {

        // 1. 게시글 수정
        /*게시글 생성과 마찬가지로 테이블에 데이터를 저장하는 개념이지만
        없었던 데이터를 생성하는 것인지, 기존 데이터를 수정하는 것인지의 차이가 있다
        이 차이는 PK(id)를 통해 구분한다
        테이블에 새로 생성되는 글은 auto_increment에 의해 자동 생성되지만,
        게시글을 수정하기 위해서는 수정할 게시글의 PK인 id를 파라미터로 전달해 주어야 한다*/
        PostRequest params = new PostRequest();
        params.setId(1L);
        params.setTitle("1번 게시글 제목 수정합니다.");
        params.setContent("1번 게시글 내용 수정합니다.");
        params.setWriter("도뎡이");
        params.setNoticeYn(true);
        postMapper.update(params);

        // 2. 게시글 상세정보 조회
        PostResponse post = postMapper.findById(1L);
        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        System.out.println("삭제 이전의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개입니다.");
        postMapper.deleteById(1L);
        System.out.println("삭제 이후의 전체 게시글 개수는 : " + postMapper.findAll().size() + "개입니다.");
    }
}