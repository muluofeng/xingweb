package com.xxx.xing.configuration.image;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xing
 * @Created by 2017-03-06 上午11:22.
 */
@ConfigurationProperties(prefix = "image",locations = "application.yml")
public class ImageConfig {
    private String uploadDir;

    private  String domainName;

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
