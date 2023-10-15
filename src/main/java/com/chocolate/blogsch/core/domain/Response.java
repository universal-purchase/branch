package com.chocolate.blogsch.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {

    String code;
    String message;

    private T content;

    @Builder
    public Response(String code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }
}
