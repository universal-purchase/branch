package com.chocolate.blogsch.search.service;

import com.chocolate.blogsch.search.domain.BlogSearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping(value = "/v1/search/blog", produces = "application/json")
    public ResponseEntity<Object> searchBlog(
            @RequestParam("query") String query,
            @PageableDefault(size = 10, page=0)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "accuracy", direction = Sort.Direction.DESC),
                    @SortDefault(sort = "recency", direction = Sort.Direction.DESC)
            }) Pageable pageable
    ) {

        Object response = blogSearchService.searchBlog(query, pageable);
        return ResponseEntity.ok(response);
    }

}