package com.branch.upbranchauto2;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.zip.InflaterInputStream;
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

    public static List<String> combineKeywords(String mainKeyword, Map<String, Integer> keywordMap) {
        List<String> combinedKeywordsList = new ArrayList<>();
        List<String> keywords = new ArrayList<>(keywordMap.keySet());

        // 메인 키워드를 맨 앞에 위치하도록 조정
        keywords.remove(mainKeyword);
        List<String> shuffledKeywords = new ArrayList<>();
        shuffledKeywords.add(mainKeyword);
        shuffledKeywords.addAll(shuffleKeywordsByPriority(keywords, keywordMap));

        generateCombinations(keywordMap, shuffledKeywords, new ArrayList<>(), combinedKeywordsList);

        return combinedKeywordsList;
    }

    private static List<String> shuffleKeywordsByPriority(List<String> keywords, Map<String, Integer> keywordMap) {
        // 키워드들을 우선순위에 따라 랜덤하게 섞음
        List<String> shuffledKeywords = new ArrayList<>(keywords);
        shuffledKeywords.sort(Comparator.comparingInt(keywordMap::get));
        Collections.shuffle(shuffledKeywords.subList(1, shuffledKeywords.size()));

        return shuffledKeywords;
    }

    private static void generateCombinations(Map<String, Integer> keywordMap, List<String> keywords,
                                             List<String> currentCombination, List<String> combinedKeywordsList) {
        if (currentCombination.size() == 5) {
            if (!hasDuplicateSubKeywords(currentCombination)) {
                combinedKeywordsList.add(String.join(" ", currentCombination));
            }
            return;
        }

        Set<String> usedKeywords = new HashSet<>(currentCombination); // 이미 사용된 키워드 저장

        for (String keyword : keywords) {
            if (!usedKeywords.contains(keyword)) {
                currentCombination.add(keyword);
                usedKeywords.add(keyword);

                generateCombinations(keywordMap, keywords, currentCombination, combinedKeywordsList);

                currentCombination.remove(keyword);
                usedKeywords.remove(keyword);

                // 조합된 키워드의 개수가 5개가 되면 추가하고 종료
                if (combinedKeywordsList.size() == 5) {
                    return;
                }
            }
        }
    }

    private static boolean hasDuplicateSubKeywords(List<String> combination) {
        Set<String> subKeywords = new HashSet<>();
        for (String keyword : combination.subList(1, combination.size())) {
            if (subKeywords.contains(keyword)) {
                return true;
            }
            subKeywords.add(keyword);
        }
        return false;
    }

    public static List<String> removeDuplicateSubKeywords(List<String> resultList) {
        List<String> filteredList = new ArrayList<>();
        Set<String> subKeywords = new HashSet<>();

        for (String result : resultList) {
            String[] keywords = result.split("\\s+");
            boolean duplicateFound = false;

            for (int i = 1; i < keywords.length; i++) {
                if (subKeywords.contains(keywords[i])) {
                    duplicateFound = true;
                    break;
                }
            }

            if (!duplicateFound) {
                filteredList.add(result);
                subKeywords.addAll(Arrays.asList(keywords).subList(1, keywords.length));
            }

            if (filteredList.size() == 3) {
                break;
            }
        }

        return filteredList;
    }

}
