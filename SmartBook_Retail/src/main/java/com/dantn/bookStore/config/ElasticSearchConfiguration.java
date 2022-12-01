package com.dantn.bookStore.config;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dantn.bookStore.ultilities.TransportUtils;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfiguration {
	@Bean
	public RestClient getRestClient() {
		//this is product
		String fingerprint="A2525B64D8";
		SSLContext context=TransportUtils.sslContextFromCaFingerprint(fingerprint);
		BasicCredentialsProvider credsProv = new BasicCredentialsProvider(); 
		credsProv.setCredentials(
		    AuthScope.ANY, new UsernamePasswordCredentials("elastic", "EHqoSWIeWWnuZBZYZNG8V1jo")
		);
		RestClient restClient = RestClient.builder(
                new HttpHost("https://smart-book.es.us-central1.gcp.cloud.es.io"))
				.setHttpClientConfigCallback(hc->
				hc.setSSLContext(context)
				.setDefaultCredentialsProvider(credsProv))
				.build();
		//this is local
//		RestClient restClient = RestClient.builder(
//                new HttpHost("localhost", 9200)).build();
        return restClient;
	}
	@Bean
	public ElasticsearchTransport getElasticsearchTransport() {
		return new RestClientTransport(
                getRestClient(), new JacksonJsonpMapper());
	}
	@Bean
    public ElasticsearchClient getElasticsearchClient(){
        ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
        return client;
    }
}
