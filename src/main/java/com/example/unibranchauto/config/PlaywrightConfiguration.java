package com.example.unibranchauto.config;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightConfiguration {
    public static void configurePlaywright() {
        Playwright playwright = Playwright.create();
        // Chrome 설정
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(true); // Headless 모드 설정

        Browser browser = playwright.chromium().launch(launchOptions);
        Page page = browser.newPage();

        // 필요한 작업 수행

        // 종료
        page.close();
        browser.close();
        playwright.close();
    }
}
