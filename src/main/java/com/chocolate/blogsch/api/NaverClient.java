package com.chocolate.blogsch.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "naver-search", url = "${api-services.naver-search-blog-service.url}")
public interface NaverClient {

    @GetMapping("/v1/search/blog.json?query={query}&sort={sort}&display={display}&start={start}")
    String searchBlog(@PathVariable String query, @PathVariable String sort, @PathVariable String display, @PathVariable String start);

}
