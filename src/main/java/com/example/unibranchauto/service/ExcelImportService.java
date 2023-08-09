package com.example.unibranchauto.service;

import com.example.unibranchauto.domain.ProductDto;
import com.example.unibranchauto.domain.SearchResponseDto;
import com.example.unibranchauto.domain.ShippingCost;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RequiredArgsConstructor
@Service
public class ExcelImportService {

    private final NaverCrawlerService naverCrawlerService;

    private String clientId = "LhOisVBX40SHXyvOt_WE";

    private String clientSecret = "4J8AvMYzF_";

    public Resource findListByExcel(MultipartFile file) throws Exception {

        // 엑셀 파일을 읽기 위해 Workbook 생성
        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        // 첫 번째 시트를 가져옴
        Sheet sheet = workbook.getSheetAt(0);

        // 컬럼 제목을 확인하여 해당하는 열 번호를 저장할 변수들 초기화
        int categoryColumnIndex = -1;
        int productNameColumnIndex = -1;
        int additionalShippingCostColumnIndex = -1;
        int parsingLinkColumnIndex = -1;
        int byteCountColumnIndex = -1;
        int cautionColumnIndex = -1;

        // 컬럼 제목을 읽어서 컬럼 번호를 찾음
        Row headerRow = sheet.getRow(0);
        Iterator<Cell> cellIterator = headerRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            String columnHeader = cell.getStringCellValue();
            switch (columnHeader) {
                case "카테고리":
                    categoryColumnIndex = cell.getColumnIndex();
                    break;
                case "상품명":
                    productNameColumnIndex = cell.getColumnIndex();
                    break;
                case "추가 배송비":
                    additionalShippingCostColumnIndex = cell.getColumnIndex();
                    break;
                case "파싱링크":
                    parsingLinkColumnIndex = cell.getColumnIndex();
                    break;
                case "글자수 (Byte)":
                    byteCountColumnIndex = cell.getColumnIndex();
                    break;
                case "주의사항":
                    cautionColumnIndex = cell.getColumnIndex();
                    break;
            }
        }

        TreeMap<String, Map<String, Object>> productMap = new TreeMap<>();

        // 데이터 행들을 순회하며 값을 변경
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                // 컬럼 인덱스를 이용하여 값 읽기
                String category = getStringCellValue(row.getCell(categoryColumnIndex));
                String productName = getStringCellValue(row.getCell(productNameColumnIndex));
                int additionalShippingCost = getNumericCellValue(row.getCell(additionalShippingCostColumnIndex));
                String parsingLink = getStringCellValue(row.getCell(parsingLinkColumnIndex));
                int byteCount = getNumericCellValue(row.getCell(byteCountColumnIndex));
                String caution = getStringCellValue(row.getCell(cautionColumnIndex));

                if (productName != null && !productMap.containsKey(productName)) {
                    productName = productName.trim();
                    String encodeQuery = URLEncoder.encode(productName, "UTF-8");
                    SearchResponseDto response = naverCrawlerService.search(clientId, clientSecret, encodeQuery, 100, 1, "sim", "cbshop", "");
                    if (response != null) {
                        List<ProductDto> products = response.getItems();

                        Map<String, Object> map = naverCrawlerService.findKeywords(productName, products);

                        productMap.put(productName, map);
                    }
                }

            }
        }

        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if (row != null) {
                // 컬럼 인덱스를 이용하여 값 읽기
                String category = getStringCellValue(row.getCell(categoryColumnIndex));
                String productName = getStringCellValue(row.getCell(productNameColumnIndex));
                int additionalShippingCost = getNumericCellValue(row.getCell(additionalShippingCostColumnIndex));
                String parsingLink = getStringCellValue(row.getCell(parsingLinkColumnIndex));
                int byteCount = getNumericCellValue(row.getCell(byteCountColumnIndex));
                String caution = getStringCellValue(row.getCell(cautionColumnIndex));

                if (productName != null) {

                    if (productMap.containsKey(productName)) {
                        Map<String, Object> map = productMap.get(productName);
                        TreeSet<String> categories = new TreeSet<>();
                        categories = (TreeSet<String>) map.get("categories");

                        ArrayList<String> tags = (ArrayList<String>) map.get("items");

                        if (!tags.isEmpty()) {
                            // 다음 값을 사용하기 위해 ArrayList에서 첫 번째 값으로 변경
                            Cell productNameCell = row.getCell(productNameColumnIndex);
                            if (productNameCell == null) {
                                productNameCell = row.createCell(productNameColumnIndex);
                            }
                            productNameCell.setCellValue(tags.get(0));

                            Cell categoryCell = row.getCell(categoryColumnIndex);
                            if (categoryCell == null) {
                                categoryCell = row.createCell(categoryColumnIndex);
                            }
                            categoryCell.setCellValue(categories.first());

                            // 사용된 값을 ArrayList에서 제거
                            tags.remove(0);
                            map.put("items", tags);
                            productMap.put(productName, map);
                        }

                        if (additionalShippingCost > 0) {
                            Cell additionalShippingCostCell = row.getCell(additionalShippingCostColumnIndex);
                            if (additionalShippingCostCell == null) {
                                additionalShippingCostCell = row.createCell(additionalShippingCostColumnIndex);

                            }
                            additionalShippingCostCell.setCellValue(ShippingCost.valueOfWeight(Double.parseDouble(String.valueOf(additionalShippingCost))).getCost());
                        }
                    }
                }

            }
        }
        // 변경된 값을 다시 셀에 설정

        // 변경된 엑셀 파일을 새로운 Resource 객체로 생성하여 반환
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        return new InputStreamResource(inputStream);
    }

    private String getStringCellValue(Cell cell) {
        return (cell == null) ? "" : cell.getStringCellValue();
    }

    private int getNumericCellValue(Cell cell) {
        return (cell == null) ? 0 : (int) cell.getNumericCellValue();
    }

    /**
     * 엑셀 시트 내 각 제목에 해당하는 행 인덱스를 맵으로 반환
     * @param sheet
     * @return
     */
    private Map<String, Integer> getTitleMap(Sheet sheet) {
        Map<String, Integer> titleMap = new LinkedHashMap<>();
        String[] titleCol = {"작업유형", "비고", "변경 전 채널", "변경 후 채널", "Org Id", "Ts Id", "Svc Id", "Logical CH.", "채널명"};

        for (Row row : sheet) {
            if (row == null) {
                continue; // Null인 행 건너뛰기
            }

            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();

                    if (Arrays.asList(titleCol).contains(cellValue)) {
                        titleMap.put(titleCol[Arrays.asList(titleCol).indexOf(cellValue)], row.getRowNum());
                    }
                }
            }

            if (titleMap.size() == titleCol.length) {
                break;
            }
        }

        return titleMap;
    }

    /**
     * 엑셀 시트 내 제목 컬럼의 인덱스를 반환
     * @param sheet
     * @param titleMap
     * @return
     */
    private int getTitleColumnIndex(Sheet sheet, Map<String, Integer> titleMap) {
        String[] titleCol = titleMap.keySet().toArray(new String[0]);
        int titleColIdx = -1;

        for (Row row : sheet) {
            if (row == null) {
                continue; // Null인 행 건너뛰기
            }

            for (Cell cell : row) {
                if (cell.getCellType() == CellType.STRING) {
                    String cellValue = cell.getStringCellValue();

                    if (Arrays.asList(titleCol).contains(cellValue)) {
                        titleColIdx = cell.getColumnIndex();
                        break; // 제목 컬럼을 찾았으므로 반복문 종료
                    }
                }
            }

            if (titleColIdx != -1) {
                break; // 제목 컬럼을 찾았으므로 반복문 종료
            }
        }

        return titleColIdx;
    }

    /**
     * 셀의 값을 문자열로 반환
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf((int) Math.floor(cell.getNumericCellValue()));
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case BLANK:
                return "";
            case ERROR:
                return String.valueOf(cell.getErrorCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

}
