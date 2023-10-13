package com.chocolate.blogsch.api;

import com.chocolate.blogsch.search.domain.BlogSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao-search", url = "${api-services.kakao-search-blog-service.url}")
public interface KakaoClient {

    @GetMapping("/v2/search/blog?query={query}&sort={sort}&page={page}&size={size}")
    BlogSearchResponse searchBlog(
            @RequestParam String query,
            @RequestParam Sort sort,
            @RequestParam Integer page,
            @RequestParam Integer size,
            @RequestHeader("Authorization") String authorization
    );

}
