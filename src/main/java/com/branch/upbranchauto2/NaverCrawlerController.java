package com.branch.upbranchauto2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class NaverCrawlerController {

    private final NaverCrawlerService naverCrawlerService;

    private String clientId = "LhOisVBX40SHXyvOt_WE";

    private String clientSecret = "4J8AvMYzF_";

    private final ObjectMapper objectMapper;


    /**
     * 목록 조회
     * @return
     */
    @GetMapping(value = "/v1/crawler/products", produces = "application/json")
    public ResponseEntity findPage(@RequestParam(value = "query", required = true) String query,
                                   @RequestParam(value = "display", required = false) Integer display,
                                   @RequestParam(value = "start", required = false) Integer start,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "filter", required = false) String filter,
                                   @RequestParam(value = "exclude", required = false) String exclude
                                   ) throws UnsupportedEncodingException {

        String encodeQuery = URLEncoder.encode(query, "UTF-8");
        SearchResponseDto response = naverCrawlerService.search(clientId, clientSecret, encodeQuery, display, start, sort, filter, exclude);
        List<ProductDto> products = response.getItems();
        for (ProductDto product : products) {
            String title = product.getTitle();
            product.setTitle(naverCrawlerService.removeHtmlTags(title));
        }

        return ResponseEntity.ok(response);
    }


    /**
     * 상품명 리스 조회
     * @return
     */
    @GetMapping(value = "/v1/crawler/products/keywords", produces = "application/json")
    public ResponseEntity findProductNames(@RequestParam(value = "query", required = true) String query,
                                   @RequestParam(value = "display", required = false) Integer display,
                                   @RequestParam(value = "start", required = false) Integer start,
                                   @RequestParam(value = "sort", required = false) String sort,
                                   @RequestParam(value = "filter", required = false) String filter,
                                   @RequestParam(value = "exclude", required = false) String exclude
                                   ) throws UnsupportedEncodingException {

        String encodeQuery = URLEncoder.encode(query, "UTF-8");
        SearchResponseDto response = naverCrawlerService.search(clientId, clientSecret, encodeQuery, display, start, sort, filter, exclude);
        List<ProductDto> products = response.getItems();

        List<String> keywords = new ArrayList<>();
        TreeSet<String> keywordSet = new TreeSet<>();
        TreeMap<String, Integer> keywordMap = new TreeMap<>();
        for (ProductDto product : products) {
            String title = product.getTitle();
            title = naverCrawlerService.removeHtmlTags(title);
            String keywordArr[] = title.split(" ");
            keywords.addAll(Arrays.stream(keywordArr).toList());
        }

        for (String keyword : keywords) {
            keywordSet.add(keyword);
        }

        for (String keyword : keywordSet) {
            keywordMap.put(keyword, Collections.frequency(keywords, keyword));
        }

        List<String> combinedKeywordsList = naverCrawlerService.combineKeywords(query, keywordMap);

        return ResponseEntity.ok(combinedKeywordsList);
    }
}
