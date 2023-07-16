package com.example.unibranchauto.controller;

import com.example.unibranchauto.domain.ProductDto;
import com.example.unibranchauto.domain.SearchResponseDto;
import com.example.unibranchauto.service.NaverCrawlerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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
     *
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
        query = query.trim();
        String encodeQuery = URLEncoder.encode(query, "UTF-8");
        SearchResponseDto response = naverCrawlerService.search(clientId, clientSecret, encodeQuery, display, start, sort, filter, exclude);
        List<ProductDto> products = response.getItems();
        for (ProductDto product : products) {
            if (product.getBrand().equals("") && product.getMaker().equals("")) {
                String title = product.getTitle();
                product.setTitle(naverCrawlerService.removeHtmlTags(title));
            }

        }

        return ResponseEntity.ok(response);
    }


    /**
     * 상품명 리스 조회
     *
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

        query = query.trim();
        String encodeQuery = URLEncoder.encode(query, "UTF-8");
        SearchResponseDto response = naverCrawlerService.search(clientId, clientSecret, encodeQuery, display, start, sort, filter, exclude);
        List<ProductDto> products = response.getItems();

        List<String> keywords = new ArrayList<>();
        TreeSet<String> categories = new TreeSet<>();
        TreeSet<String> keywordSet = new TreeSet<>();
        TreeMap<String, Integer> keywordMap = new TreeMap<>();
        for (ProductDto product : products) {
            if (product.getBrand().equals("") && product.getMaker().equals("")) {
                String title = product.getTitle();
                title = naverCrawlerService.removeHtmlTags(title);
                String keywordArr[] = title.split(" ");
                keywords.addAll(Arrays.stream(keywordArr).toList());
                categories.add(product.getCategory1() + "/" + product.getCategory2() + "/" + product.getCategory3() + "/" + product.getCategory4());
            }
        }

        for (String keyword : keywords) {
            keyword = naverCrawlerService.removeMainKeywords(query, keyword);
            if (!keywordSet.contains(keyword) && !keyword.equals(" ")) {
                keywordSet.add(keyword);
            }
        }

        for (String keyword : keywordSet) {
            keywordMap.put(keyword, Collections.frequency(keywords, keyword));
        }

        Set<String> usedKeywords = new HashSet<>();

        List<String> combinedKeywordsList = new ArrayList<>();
        String combinedKeywords = "";
        for (int i = 0; i < 20; i++) {
            combinedKeywords = naverCrawlerService.combineKeywords(query, keywordMap);
            combinedKeywordsList.add(combinedKeywords);
        }

        Set<String> uniqueTags = new HashSet<>();
        for (int i = 0; i < 3; i++) {
            String[] tags = combinedKeywordsList.get(i).split(" ");

            for (String tag : tags) {
                uniqueTags.add(tag);
            }
        }

        String[] uniqueTagArray = uniqueTags.toArray(new String[0]);
        String joinedString = String.join(",", uniqueTagArray);

        Map<String, Object> map = new HashMap<>();

        map.put("categories", categories);
        map.put("items", combinedKeywordsList);
        map.put("tags", joinedString);

        return ResponseEntity.ok(map);
    }


    /**
     * 상품명 리스 조회
     *
     * @return
     */
    @GetMapping(value = "/v1/crawler/products/tags", produces = "application/json")
    public ResponseEntity createTags(@RequestParam(value = "tags", required = true) String tags
    ) throws UnsupportedEncodingException {
        String[] tagList = tags.split(" ");

        Set<String> uniqueTags = new HashSet<>();
        for (String tag : tagList) {
            uniqueTags.add(tag);
        }

        String[] uniqueTagArray = uniqueTags.toArray(new String[0]);
        String joinedString = String.join(",", uniqueTagArray);
        return ResponseEntity.ok(joinedString);
    }
}