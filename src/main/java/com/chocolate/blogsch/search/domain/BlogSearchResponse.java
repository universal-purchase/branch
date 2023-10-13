package com.chocolate.blogsch.search.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogSearchResponse {

        private Meta meta;
        private List<Document> documents;

}
