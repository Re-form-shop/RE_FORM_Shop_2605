package com.re_form_shop_2605;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@EnableScheduling
@SpringBootApplication
public class ReFormShop2605Application {

    public static void main(String[] args) {
        SpringApplication.run(ReFormShop2605Application.class, args);
    }

}
