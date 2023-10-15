package com.chocolate.blogsch.core.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("블로그 검색")
                .description("블로그 검색:\n" +
                        "\n" +
                        "블로그 검색 기능은 다음과 같은 주요 기능을 가지고 있습니다:\n" +
                        "\n" +
                        "키워드 검색: 사용자는 검색할 키워드를 입력하여 원하는 주제 또는 정보를 검색할 수 있습니다.\n" +
                        "\n" +
                        "정확도 또는 최신순 정렬: 검색 결과를 정확도순 또는 최신순으로 정렬할 수 있으며, 이를 통해 사용자는 가장 관련성 높은 결과 또는 가장 최근에 작성된 결과를 볼 수 있습니다.\n" +
                        "\n" +
                        "페이징 및 분할 된 결과: 검색 결과가 여러 페이지로 분할되며, 사용자는 페이지를 이동하고 각 페이지에 표시되는 결과 수를 조정할 수 있습니다.\n" +
                        "\n" +
                        "다양한 검색 소스: 현재는 카카오 API를 사용하여 블로그 검색을 지원하며, 카카오 API 문제 발생 시 네이버 API 를 호출합니다.\n" +
                        "\n" +
                        "인기 검색어 목록:\n" +
                        "\n" +
                        "인기 검색어 목록 기능은 다음과 같은 주요 기능을 가지고 있습니다:\n" +
                        "\n" +
                        "인기 검색어 제공: 최대 10개의 검색어를 사용자가 가장 많이 검색한 순서대로 표시합니다.\n" +
                        "\n" +
                        "검색어 별 검색 횟수 표기: 각 검색어와 해당 검색어가 검색된 횟수가 표기되어 사용자에게 어떤 키워드가 가장 인기가 있는지 확인할 수 있습니다.")
                .version("1.0")
                .build();
    }

}
