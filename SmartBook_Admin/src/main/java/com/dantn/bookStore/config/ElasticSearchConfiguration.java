package com.dantn.bookStore.config;

import java.io.IOException;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfiguration {
	@Bean
	public RestClient getRestClient() throws IOException {
		// this is product
		BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
		credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "OgLxgNEqVg4yIkr5vlLhBmga"));
		RestClient restClient = RestClient.builder(new HttpHost("smart-book-2f9395.es.us-central1.gcp.cloud.es.io",9243,"https"))
				.setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credsProv))
				.build();
		// this is local
//				RestClient restClient = RestClient.builder(
//		                new HttpHost("localhost", 9200)).build();
		return restClient;
	}

	@Bean
	public ElasticsearchTransport getElasticsearchTransport() throws IOException {
		return new RestClientTransport(getRestClient(), new JacksonJsonpMapper());
	}

	@Bean
	public ElasticsearchClient getElasticsearchClient() throws IOException {
		ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
		return client;
	}
}
