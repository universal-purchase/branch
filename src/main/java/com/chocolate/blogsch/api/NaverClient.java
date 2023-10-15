package com.chocolate.blogsch.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver-search", url = "${api-services.naver-search-blog-service.url}")
public interface NaverClient {

    @GetMapping("/v1/search/blog.json")
    String searchBlog(
            @RequestParam("query") String query,
            @RequestParam("sort") String sort,
            @RequestParam("display") Integer display,
            @RequestParam("start") Integer start,
            @RequestHeader("X-Naver-Client-Id") String clientId,
            @RequestHeader("X-Naver-Client-Secret") String clientSecret
    );

}
