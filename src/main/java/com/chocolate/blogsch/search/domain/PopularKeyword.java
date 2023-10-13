package com.chocolate.blogsch.search.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PopularKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    private Long searchCount;

    @Builder
    public PopularKeyword(String keyword, Long searchCount) {
        this.keyword = keyword;
        this.searchCount = searchCount;
    }

    public void increaseSearchCount() {
        this.searchCount++;
    }
}
