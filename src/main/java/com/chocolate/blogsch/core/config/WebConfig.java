package com.chocolate.blogsch.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {




	/**
	 * <pre>
	 * CORS, Cross Origin Resource Sharing
	 *
	 * - addMapping: CORS를 적용할 URL 패턴 정의
	 * - allowedOrigins: CORS를 적용할 도메인 정의
	 * - allowedOriginPatterns: CORS를 적용할 도메인 정의(allowCredentials true일 경우 allowedOrigins("*")는 사용 불가)
	 * - allowedMethods: CORS를 적용할 HTTP method를 정의
	 * - allowedHeaders: CORS를 적용할 HTTP header를 정의
	 * - maxAge: CORS pre-flight 리퀘스트의 유효시간을 정의
	 * - allowCredentials: CORS를 적용할 때 credential(cookies, authentication)을 사용할지 여부를 정의
	 * - exposedHeaders: 클라이언트에게 노출할 응답 header를 정의
	 * </pre>
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // CORS를 적용할 URL 패턴 정의
//			.allowedOrigins("*")
			.allowedOriginPatterns("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.maxAge(1000 * 60) // 1 hour
			.allowCredentials(true);
//			.exposedHeaders("Content-Range", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");

	}

}