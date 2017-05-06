package com.xxx.xing;

import com.xxx.xing.configuration.SolrConfig;
import com.xxx.xing.configuration.image.ImageConfig;
import com.xxx.xing.repository.imp.BaseRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by xing on 2017/2/14.
 */
@SpringBootApplication
@EntityScan("com.xxx.xing.entity")
@EnableConfigurationProperties({ImageConfig.class, SolrConfig.class})
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
@EnableCaching
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
