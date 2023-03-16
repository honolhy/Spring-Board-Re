package com.study.domain.post;

import com.study.common.dto.MessageDto;
import com.study.common.dto.SearchDto;
import com.study.paging.PagingResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성 페이지
    @GetMapping("/post/write.do")
    public String openPostWrite(
            @RequestParam(value = "id", required = false) final Long id, Model model) {

        if (id != null) {
            PostResponse post = postService.findPostById(id);
            model.addAttribute("post", post);
        }

        return "post/write";
    }

    // 신규 게시글 생성
    @PostMapping("/post/save.do")
    public String savePost(final PostRequest params, Model model) {

        postService.savePost(params);
        MessageDto message = new MessageDto("게시글이 생성 되었습니다.",
                "/post/list.do", RequestMethod.GET, null);

        return showMessageAndRedirect(message, model);
    }

    // 게시글 리스트 페이지
    @GetMapping("/post/list.do")
    public String openPostList(@ModelAttribute("params") final SearchDto params, Model model) {

        PagingResponse<PostResponse> response = postService.findAllPost(params);
        model.addAttribute("response", response);

        return "post/list";
    }

    // 게시글 상세 페이지
    @GetMapping("post/view.do")
    public String openPostView(@RequestParam final Long id, Model model) {

        PostResponse post = postService.findPostById(id);
        val nlString = System.getProperty("line.separator").toString();

        model.addAttribute("nlString", nlString);
        model.addAttribute("post", post);

        return "post/view";
    }

    // 기존 게시글 수정
    @PostMapping("/post/update.do")
    public String updatePost(final PostRequest params, Model model) {

        postService.updatePost(params);
        MessageDto message = new MessageDto("게시글이 수정 되었습니다.",
                "/post/list.do", RequestMethod.GET, null);

        return showMessageAndRedirect(message, model);
    }

    @PostMapping("/post/delete.do")
    public String deletePost(@RequestParam final Long id, Model model) {

        postService.deletePost(id);
        MessageDto message = new MessageDto("게시글이 삭제 되었습니다.",
                "/post/list.do", RequestMethod.GET, null);

        return showMessageAndRedirect(message, model);
    }

    private String showMessageAndRedirect(final MessageDto params, Model model) {

        model.addAttribute("params", params);

        return "common/messageRedirect";
    }

    // 쿼리 스트링 파라미터를 Map에 담아 반환한다.
    private Map<String, Object> queryParamsToMap(final SearchDto queryParams) {

        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());

        return data;
    }
}