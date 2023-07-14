package com.branch.upbranchauto2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {

    private String title;
    private String link;
    private String image;
    private String lprice;
    private String hprice;
    private String mallName;
    private String productId;
    private String productType;
    private String brand;
    private String maker;
    private String category1;
    private String category2;
    private String category3;
    private String category4;

    @Builder
    public ProductDto(String title, String link, String image, String lprice, String hprice, String mallName,
            String productId, String productType, String brand, String maker, String category1, String category2,
            String category3, String category4) {
        this.title = title;
        this.link = link;
        this.image = image;
        this.lprice = lprice;
        this.hprice = hprice;
        this.mallName = mallName;
        this.productId = productId;
        this.productType = productType;
        this.brand = brand;
        this.maker = maker;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.category4 = category4;
    }
}
