package com.chocolate.blogsch.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-search", url = "${api-services.kakao-search-blog-service.url}")
public interface KakaoClient {

    @GetMapping("/v2/search/blog")
    String searchBlog(
            @RequestParam("query") String query,
            @RequestParam("sort") String sort,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestHeader("Authorization") String authorization
    ) throws Exception;

}
