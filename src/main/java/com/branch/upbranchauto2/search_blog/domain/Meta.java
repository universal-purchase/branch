package com.branch.upbranchauto2.search_blog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meta {
    private int total_count;
    private int pageable_count;
    private boolean is_end;

    @Builder
    public Meta(int total_count, int pageable_count, boolean is_end) {
        this.total_count = total_count;
        this.pageable_count = pageable_count;
        this.is_end = is_end;
    }
}