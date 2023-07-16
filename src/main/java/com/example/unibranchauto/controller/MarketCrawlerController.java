package com.example.unibranchauto.controller;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
public class MarketCrawlerController {
    private final Playwright playwright = Playwright.create();
    private final Browser browser;

    public MarketCrawlerController() {
//        browser = Playwright.create().chromium().launch();

        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
    }

//    @GetMapping(value = "/v1/market-crawler/products")
//    public ResponseEntity<String> getMarketProducts(@RequestParam(name = "url") String url) {
//        try {
//
//            Page page = browser.newPage();
//            page.navigate(url);
//
//            // CategoryProducts 아이디를 가진 div 요소의 하위에 있는 ul 요소 선택
//            ElementHandle ulElement = page.querySelector("#CategoryProducts > ul");
//
//            // li 요소들 가져오기
//            List<ElementHandle> liElements = ulElement.querySelectorAll("li");
//
//
//            // 각 상품 요소를 클릭하고 타오바오 팝업 창의 스크린샷을 가져온 후, 바이트 배열로 변환
//            byte[] combinedScreenshot = new byte[0];
//            for (ElementHandle liElement : liElements) {
//                // 상품 이미지를 클릭하여 타오바오 이미지 검색 버튼이 나타날 때까지 대기
//                liElement.hover();
//                page.waitForSelector(".ap-sbi-btn-search-wrapper");
//
//                // 타오바오 이미지 검색 버튼 클릭
//                ElementHandle taobaoButton = page.querySelector(".ap-sbi-btn-search-wrapper");
//                taobaoButton.click();
//
//                // 타오바오 팝업 창의 스크린샷을 가져온 후, 바이트 배열로 변환하여 기존 스크린샷에 추가
//                byte[] screenshot = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
//                combinedScreenshot = combineByteArrays(combinedScreenshot, screenshot);
//
//                // 타오바오 팝업 창 닫기
//                closeTaobaoPopup(page);
//            }
//            return new ResponseEntity<>("Success", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // 두 개의 바이트 배열을 합치는 메서드
//    private byte[] combineByteArrays(byte[] array1, byte[] array2) {
//        byte[] combinedArray = new byte[array1.length + array2.length];
//        System.arraycopy(array1, 0, combinedArray, 0, array1.length);
//        System.arraycopy(array2, 0, combinedArray, array1.length, array2.length);
//        return combinedArray;
//    }
//    // 타오바오 팝업 창을 닫는 메서드
//    private void closeTaobaoPopup(Page page) {
//        List<Frame> frames = page.frames();
//        for (Frame frame : frames) {
//            String frameUrl = frame.url();
//            if (frameUrl.contains("taobao")) {
//                page.close();
//                browser.close();
//                playwright.close();
//                break;
//            }
//        }
//    }

    @GetMapping(value = "/v1/market-crawler/products")
    public ResponseEntity<String> getMarketProducts(@RequestParam(name = "url") String url) {
        try {
            Page page = browser.newPage();
            page.navigate(url);

            // CategoryProducts 아이디를 가진 div 요소의 하위에 있는 ul 요소 선택
            ElementHandle ulElement = page.querySelector("#CategoryProducts > ul");

            // li 요소들 가져오기
            List<ElementHandle> liElements = ulElement.querySelectorAll("li");

            for (ElementHandle liElement : liElements) {
                // 각 상품 요소의 이미지 URL 가져오기
                ElementHandle imgElement = liElement.querySelector("img");
                String imageURL = imgElement.getAttribute("src");


                // 이미지를 Base64 인코딩하여 문자열로 변환
                String base64Image = getImageAsBase64(imageURL);

                // TODO: base64Image를 사용하여 타오바오 홈페이지에서 이미지 검색 API 호출 및 결과 처리
                performImageSearch(base64Image);

                // 예시: 이미지를 파일로 저장
                saveImageAsFile(base64Image);
            }

            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 이미지 URL을 Base64로 인코딩하여 문자열로 반환
    private String getImageAsBase64(String imageURL) throws Exception {
//        byte[] imageBytes = Files.readAllBytes(Path.of(imageURL));
        // 이미지 다운로드 및 바이트 배열로 읽어오기
        byte[] imageBytes = downloadImage(imageURL);

        return Base64.getEncoder().encodeToString(imageBytes);
    }

    // Base64로 인코딩된 이미지를 파일로 저장
    private void saveImageAsFile(String imageURL) throws Exception {
        byte[] imageBytes = downloadImage(imageURL);
        String fileName = getFileNameFromURL(imageURL);
        Path filePath = Path.of(fileName);
        Files.write(filePath, imageBytes);
    }

    private String getFileNameFromURL(String imageURL) {
        String[] parts = imageURL.split("/");
        return parts[parts.length - 1];
    }


    // 이미지를 다운로드하고 바이트 배열로 읽어오는 메서드
    private byte[] downloadImage(String imageURL) throws IOException {
        URL url = new URL(imageURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();
        }
    }

    // 타오바오 이미지 검색 기능 수행
    private void performImageSearch(String base64Image) {
        try {
            BrowserContext context = browser.newContext();
            Page searchPage = context.newPage();
            searchPage.navigate("https://www.taobao.com");

            // 타오바오 이미지 검색 페이지로 이동
            searchPage.navigate("https://s.taobao.com/image");

            // 이미지 업로드 버튼 선택
            ElementHandle uploadButton = searchPage.querySelector(".component-search-icon");
            uploadButton.setInputFiles(Paths.get("image.jpg"));


            // 로딩 상태가 "로딩 완료" 상태가 될 때까지 대기
            searchPage.waitForLoadState(LoadState.LOAD);

            // TODO: 검색 결과 처리 로직 추가

            // 예시: 결과를 콘솔에 출력
            System.out.println("Search result: ...");

            // 페이지 및 브라우저 컨텍스트 종료
            searchPage.close();
            context.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
