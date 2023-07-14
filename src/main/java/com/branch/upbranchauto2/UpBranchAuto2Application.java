package com.branch.upbranchauto2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;


@SpringBootApplication
@ConfigurationPropertiesScan
public class UpBranchAuto2Application {

    public static void main(String[] args) {

        SpringApplication.run(UpBranchAuto2Application.class, args);
    }

}
