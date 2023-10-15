package com.chocolate.blogsch.search.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlogSearchResponse {

        private Meta meta;
        private List<Document> documents;

}
