package com.chocolate.blogsch.search.service;

import com.chocolate.blogsch.api.KakaoClient;
import com.chocolate.blogsch.core.config.KakaoProperties;
import com.chocolate.blogsch.core.domain.PageResponse;
import com.chocolate.blogsch.search.domain.BlogSearchResponse;
import com.chocolate.blogsch.search.domain.Document;
import com.chocolate.blogsch.search.domain.PopularKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;

    private final KakaoClient kakaoClient;

    private final KakaoProperties kakaoProperties;

    public Object searchBlog(String query, Pageable pageable) {

        log.info("searchBlog: {}, {}, {}, {}", query, pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize());

        String apiKey = kakaoProperties.getApikey();
        BlogSearchResponse response = kakaoClient.searchBlog(query, pageable.getSort(), pageable.getPageNumber(), pageable.getPageSize(), "KakaoAK " + apiKey);

        savePopularKeyword(query);

        return response;

    }

    @Transactional
    public void savePopularKeyword(String keyword) {

        log.info("savePopularKeyword: {}", keyword);

        blogSearchRepository.findByKeyword(keyword).stream()
                .findFirst()
                .ifPresentOrElse(
                        this::increaseSearchCount,
                        () -> createPopularKeyword(keyword)
                );
    }

    private void increaseSearchCount(PopularKeyword popularKeyword) {
        popularKeyword.increaseSearchCount();
        blogSearchRepository.save(popularKeyword);
    }

    private void createPopularKeyword(String keyword) {
        PopularKeyword popularKeyword = PopularKeyword.builder().keyword(keyword).build();
        blogSearchRepository.save(popularKeyword);
    }
}
