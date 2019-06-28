package com.cnasurety.extagencyint.batches.ivans.merge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.cnasurety.extagencyint.batches.ivans.*" })
public class IvansMergeBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(IvansMergeBatchApplication.class, args);
        System.exit(0);
    }

}
