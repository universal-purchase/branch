package com.chocolate.blogsch.search.service;

import com.chocolate.blogsch.search.domain.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogSearchRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByKeyword(String keyword);

    List<SearchHistory> findTop10ByOrderBySearchCountDesc();
}