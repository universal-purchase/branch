**[블로그 검색]**

프로젝트 목적:
이 프로젝트의 주요 목적은 블로그 검색 서비스를 제공하여 사용자가 원하는 키워드로 블로그 게시물을 검색할 수 있도록 합니다.
블로그 검색 서비스는 정확도 및 최신순으로 정렬된 결과를 제공하며, 사용자가 인기 있는 검색어를 확인할 수 있는 기능을 제공합니다.

- 블로그 검색:
블로그 검색 기능은 사용자가 원하는 주제나 키워드로 블로그 게시물을 검색할 수 있으며, 검색 결과는 정확도순 또는 최신순으로 필터링됩니다. 
또한, 카카오 블로그 검색 API에 장애가 발생한 경우, 네이버 블로그 검색 API를 통해 데이터를 제공합니다.
- 인기 검색어 목록: 
사용자가 가장 많이 검색한 검색어를 확인할 수 있어, 트렌드에 맞게 인기 있는 주제에 대한 정보를 얻을 수 있습니다. 
검색어 별로 검색 횟수가 표기되어, 어떤 주제가 인기 있는지 파악할 수 있습니다.

프로젝트는 트래픽이 많고, 저장되어 있는 데이터가 많음을 염두에 두고 구현했습니다. 

1. Cache 이용:
프로젝트에서는 검색 결과와 검색 히스토리 정보를 캐싱하여 빠른 응답 시간과 효율적인 자원 활용을 지원합니다. 
검색 결과를 캐시에 저장함으로써, 이후 동일한 검색 요청에 대해 빠른 응답을 제공합니다. 

2. 동시성 이슈 고려: 
키워드 별로 검색된 횟수의 정확도와 같이 동시성 이슈가 발생할 수 있는 부분에 대해 트랜잭션 처리를 통해 안전하게 관리합니다. 
검색 히스토리 저장 및 조회 시에 트랜잭션을 적용하여 데이터 무결성을 유지하고 동시성 문제를 방지합니다.

##### **[기술 스택 (Tech Stack)]**
- Java
- Spring Boot
- H2 Database
- Feign Client
- Lombok
- Spring Data JPA
- Ehcache
- Swagger

**[API 명세]**
- swagger ui : http://localhost:8080/service/swagger-ui/index.html

1. 블로그 검색
Endpoint: /v1/search/blog
Method: GET
Description: 입력된 검색어로 블로그를 검색합니다.
Request Parameters:
query (검색 키워드, 필수)
sort (결과 문서 정렬 방식, 선택, 기본값: "accuracy")
page (결과 페이지 번호, 선택, 기본값: 1)
size (한 페이지에 보여질 문서 수, 선택, 기본값: 10)
Response: 검색 결과를 JSON 형식으로 반환합니다.

2. 인기 검색어 목록
Endpoint: /v1/search/blog/populars
Method: GET
Description: 사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 제공합니다.
Response: 최대 10개의 인기 검색어와 해당 검색어의 검색 횟수를 JSON 형식으로 반환합니다.

**[jar 파일]**
- blog-search-0.0.1-SNAPSHOT.jar
- 다운로드 링크 : https://drive.google.com/file/d/1kpusMZ9Sk11arh4yl3Uy4Ja8xirAPX80/view?usp=sharing