package com.chocolate.blogsch.core.domain;

import com.chocolate.blogsch.search.domain.Meta;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageResponse<T> {
    private Meta pagination;

    private List<T> content;

    @Builder
    public PageResponse(Meta pagination, List<T> content) {
        this.pagination = pagination;
        this.content = content;
    }

    public static <T> PageResponse<T> empty() {
        return (PageResponse<T>) PageResponse.builder()
                .pagination(Meta.builder().build())
                .content(List.of())
                .build();
    }

    public static <T> PageResponse<T> empty(Integer size) {
        return (PageResponse<T>) PageResponse.builder()
                .pagination(Meta.builder().pageable_count(size).build())
                .content(List.of())
                .build();
    }
}
