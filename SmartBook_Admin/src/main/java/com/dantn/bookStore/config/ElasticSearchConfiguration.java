package com.dantn.bookStore.config;

import java.io.File;
import java.io.IOException;

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
	public RestClient getRestClient() throws IOException {
		// this is product
//		File file=new File("C:\\Users\\NTS\\Desktop\\A2525B64D8BFD084D946539261844AC9A3F7DBDC.crt");
//		SSLContext context = TransportUtils.sslContextFromHttpCaCrt(file);
		BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
		credsProv.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "EHqoSWIeWWnuZBZYZNG8V1jo"));
		RestClient restClient = RestClient.builder(new HttpHost("smart-book.es.us-central1.gcp.cloud.es.io",9243,"https"))
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
