package com.example.unibranchauto.service;

import com.example.unibranchauto.domain.SearchResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class NaverCrawlerService {
    final String baseUrl = "https://openapi.naver.com/v1/search/shop.json?query=";

    private final ObjectMapper objectMapper;


    public SearchResponseDto search(String clientId, String clientSecret, String query, Integer display, Integer start, String sort, String filter, String exclude) {

        HttpURLConnection con = null;
        String result = null;
        SearchResponseDto responseDto = null;

        try {
            URL url = new URL(baseUrl + query + "&display=" + display + "&start=" + start + "&sort=" + sort + "&filter=" + filter + "&exclude=" + exclude);
            con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {

                responseDto = objectMapper.readValue(readBody(con.getInputStream()), SearchResponseDto.class);
                return responseDto;
            } else {
                result = readBody(con.getErrorStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseDto;
    }

    public String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (Exception e) {
            throw new RuntimeException("API 응답 실패", e);
        }
    }


    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("<.*?>");

    public static String removeHtmlTags(String input) {
        if (input == null) {
            return null;
        }
        return HTML_TAG_PATTERN.matcher(input).replaceAll("");
    }

    public static String combineKeywords(String mainKeyword, TreeMap<String, Integer> keywordMap) {
        // 빈도수를 기준으로 내림차순 정렬
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(keywordMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        StringBuilder combinedKeywords = new StringBuilder();

        combinedKeywords.append(mainKeyword).append(" ");

        for (Map.Entry<String, Integer> entry : sortedEntries) {
            String keyword = entry.getKey();
            if (combinedKeywords.length() + keyword.length() + 1 <= 35) {
                if (!combinedKeywords.toString().contains(keyword)) {
                    combinedKeywords.append(keyword).append(" ");
                    keywordMap.remove(entry.getKey());
                }
            } else {
                break;
            }
        }

        // 마지막 공백 제거
        if (combinedKeywords.length() > 0) {
            combinedKeywords.setLength(combinedKeywords.length() - 1);
        }

        return combinedKeywords.toString();
    }
    public static String removeMainKeywords(String mainKeyword, String keyword) {
        String[] mainKeywords = mainKeyword.split(" ");
        for (String mainKey : mainKeywords) {
            if (keyword.contains(mainKey)) {
                String escapedMainKey = Pattern.quote(mainKey); // 특수문자 이스케이프 처리
                keyword = keyword.replaceAll(escapedMainKey, ""); // mainKeyword 제거
                keyword = keyword.trim(); // 양쪽 공백 제거
            }
        }
        return keyword;
    }

}