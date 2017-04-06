package com.xxx.xing.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xing
 * @Created by 2017-03-31 上午11:24.
 */
@ConfigurationProperties(prefix = "solr",locations = "application.yml")
public class SolrConfig {
    private String host;
    private String solrName;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSolrName() {
        return solrName;
    }

    public void setSolrName(String solrName) {
        this.solrName = solrName;
    }
}
