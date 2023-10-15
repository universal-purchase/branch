package com.chocolate.blogsch.search.service;

import com.chocolate.blogsch.search.domain.BlogSearchResponse;
import com.chocolate.blogsch.search.domain.SearchHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping(value = "/v1/search/blog", produces = "application/json")
    @ApiOperation(value = "블로그 검색", notes = "입력된 검색어로 블로그를 검색합니다.")
    public ResponseEntity<Object> searchBlog(
            @ApiParam(value = "검색 키워드", required = true)
            @RequestParam(value = "query") String query,
            @ApiParam(value = "결과 문서 정렬 방식 (정확도순 또는 최신순)", defaultValue = "accuracy")
            @RequestParam(value = "sort", required = false, defaultValue = "accuracy") String sort,
            @ApiParam(value = "결과 페이지 번호 (1~50 사이의 값)", defaultValue = "1")
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @ApiParam(value = "한 페이지에 보여질 문서 수 (1~50 사이의 값)", defaultValue = "10")
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) throws Exception {
        log.info("검색 요청 - 검색어: {}, 정렬 방식: {}, 페이지: {}, 페이지 크기: {}", query, sort, page, size);

        Object response = blogSearchService.searchBlog(query, sort, page, size);

        log.info("검색 결과: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/v1/search/blog/populars", produces = "application/json")
    @ApiOperation(value = "인기 검색어 목록", notes = "사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.")
    public ResponseEntity<Object> getSearchHistoryRepositorys() {

        List<SearchHistory> searchHistories = blogSearchService.getSearchHistorys();

        log.info("인기 검색어 목록 조회: {}", searchHistories);

        return ResponseEntity.ok(searchHistories);
    }

}