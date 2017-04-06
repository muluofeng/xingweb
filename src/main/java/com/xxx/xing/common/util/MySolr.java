package com.xxx.xing.common.util;

import com.xxx.xing.configuration.SolrConfig;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xing
 * @Created by 2017-03-31 上午11:32.
 */
@Component
public class MySolr {
    @Autowired
    private SolrConfig solrConfig;

    public HttpSolrClient getSolrClient() {
        return new HttpSolrClient(solrConfig.getHost() + "/" + solrConfig.getSolrName());
    }


    public void search() throws Exception {
        System.out.println(solrConfig.getHost() + solrConfig.getSolrName());
        HttpSolrClient client=getSolrClient();
         SolrQuery query=new SolrQuery();
         query.set("q","product_name:鞋子");
         query.setStart(0);
         query.setRows(10);

         query.set("df","product_keywords");
         QueryResponse response=client.query(query);
         SolrDocumentList results=response.getResults();
         for(SolrDocument document:results){
             System.out.println("商品id" + document.get("id"));
             System.out.println("商品名称" + document.get("product_name"));
             System.out.println("商品描述" + document.get("product_description"));
             System.out.println("商品价格" + document.get("product_price"));
         }

    }

}
