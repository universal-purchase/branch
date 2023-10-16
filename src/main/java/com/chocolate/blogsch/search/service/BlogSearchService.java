package com.chocolate.blogsch.search.service;

import com.chocolate.blogsch.api.KakaoClient;
import com.chocolate.blogsch.api.NaverClient;
import com.chocolate.blogsch.core.config.KakaoProperties;
import com.chocolate.blogsch.core.config.NaverProperties;
import com.chocolate.blogsch.search.domain.BlogSearchResponse;
import com.chocolate.blogsch.search.domain.SearchHistory;
import com.chocolate.blogsch.search.domain.RssContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;

    private final KakaoClient kakaoClient;

    private final NaverClient naverClient;

    private final KakaoProperties kakaoProperties;

    private final NaverProperties naverProperties;

    private final ObjectMapper objectMapper;

    @Cacheable("searchCache")
    public Object searchBlog(String query, String sort, Integer page, Integer size) throws Exception {

        log.info("검색 히스토리 저장: query={}", query);

        Object searchResponse = null;

        try {
            // 카카오 블로그 검색 API를 호출하여 결과를 가져옵니다.
            String response = kakaoClient.searchBlog(query, sort, page, size, "KakaoAK " + kakaoProperties.getApikey());
            searchResponse = objectMapper.readValue(response, BlogSearchResponse.class);
        } catch (IOException e) {
            // 카카오 블로그 검색 API에서 예외가 발생한 경우, 네이버 블로그 검색 API를 사용합니다.
            String response = naverClient.searchBlog(query, sort, size, (page - 1) * page + 1, naverProperties.getClientId(), naverProperties.getClientSecret());
            searchResponse = objectMapper.readValue(response, RssContainer.class);

            log.error("다음 블로그 검색 예외 발생: {}", e.getMessage(), e);
        }
        saveSearchHistoryRepository(query);

        return searchResponse;
    }

    @Transactional
    public void saveSearchHistoryRepository(String keyword) {

        log.info("검색 히스토리 저장: keyword={}", keyword);

        blogSearchRepository.findByKeyword(keyword).stream()
                .findFirst()
                .ifPresentOrElse(
                        this::increaseSearchCount,
                        () -> createSearchHistoryRepository(keyword)
                );
    }

    private void increaseSearchCount(SearchHistory searchHistory) {
        searchHistory.increaseSearchCount();
        blogSearchRepository.save(searchHistory);
    }

    private void createSearchHistoryRepository(String keyword) {
        SearchHistory searchHistory = SearchHistory.builder().keyword(keyword).searchCount(0L).build();
        blogSearchRepository.save(searchHistory);
    }

    // 검색 히스토리 중 상위 10개를 조회합니다.
    public List<SearchHistory> getSearchHistorys() {
        return blogSearchRepository.findTop10ByOrderBySearchCountDesc();
    }
}
