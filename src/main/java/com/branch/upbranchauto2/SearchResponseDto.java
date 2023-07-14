package com.branch.upbranchauto2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponseDto {
    private List<ProductDto> items;

}