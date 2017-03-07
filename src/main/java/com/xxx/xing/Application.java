package com.xxx.xing;

import com.xxx.xing.configuration.image.ImageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Created by xing on 2017/2/14.
 */
@SpringBootApplication
@EnableConfigurationProperties(ImageConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
