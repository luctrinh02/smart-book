package com.dantn.bookStore.repositories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dantn.bookStore.elastic.EBook;
import com.dantn.bookStore.entities.Book;
import com.dantn.bookStore.ultilities.AppConstraint;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;

@Repository
public class IEBookRepository {
	@Autowired
	private ElasticsearchClient elasticsearchClient;

	private final String indexName = "books";

	public EBook save(EBook book) throws IOException {
		IndexResponse response = elasticsearchClient
				.index(i -> i.index(indexName).id(book.getId() + "").document(book));
		if (response.result().name().equals("Created")) {
			return book;
		} else if (response.result().name().equals("Updated")) {
			return book;
		}
		throw new IOException();
	}

	public EBook getById(String bookId) throws IOException {
		EBook book = null;
		GetResponse<EBook> response = elasticsearchClient.get(g -> g.index(indexName).id(bookId), EBook.class);
		if (response.found()) {
			book = response.source();
		}
		return book;
	}

	public EBook deleteById(String bookId) throws IOException {
		DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(bookId));
		DeleteResponse response = elasticsearchClient.delete(request);
		if (Objects.nonNull(response.result()) && !response.result().name().equals("NotFound")) {
			return this.getById(bookId);
		}
		throw new IOException("Not found");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EBook> getAll() throws IOException {
		SearchRequest request = SearchRequest.of(s -> s.index(indexName));
		SearchResponse response = elasticsearchClient.search(request, EBook.class);
		List<Hit> hits = response.hits().hits();
		List<EBook> eBooks = new ArrayList<>();
		for (Hit obj : hits) {
			eBooks.add((EBook) obj.source());
		}
		return eBooks;
	}

	public List<EBook> getByKey(String key,Integer fromRecord) throws IOException{
		SearchResponse<EBook> response=elasticsearchClient.search(
				s->s.index(indexName).query(q->q.bool(b->b
						.should(createQuery("name", key))
						.should(createQuery("author", key))
						.should(createQuery("publisher", key))
						.should(createQuery("type", key))
						.should(createQuery("charactor", key))
						.should(createQuery("content", key))
						)).from(fromRecord).size(AppConstraint.PAGE_NUM)
				, EBook.class);
		List<Hit<EBook>> hits = response.hits().hits();
		List<EBook> eBooks=new ArrayList<>();
		for (Hit<EBook> obj : hits) {
			eBooks.add((EBook) obj.source());
		}
		return eBooks;
	}

	public Query createQuery(String field, String key) {
		Query query = MatchQuery.of(m -> m.field(field).query(key).fuzziness("1").prefixLength(1))._toQuery();
		return query;
	}
	
	@SuppressWarnings("null")
	public void saveAll(List<EBook> books) throws ElasticsearchException, IOException,RuntimeException{
		BulkRequest.Builder br = new BulkRequest.Builder();
		for (int i = 0; i < books.size(); i++) {
			EBook b=books.get(i);
			br.operations(op -> op.index(idx -> idx.index(indexName).id(b.getId()).document(b)));
		}
		BulkResponse result = elasticsearchClient.bulk(br.build());
		if (result.errors()) {
		    for (BulkResponseItem item: result.items()) {
		        if (item.error() != null) {
		        	throw new RuntimeException(item.error().reason());
		        }
		    }
		}
	}
}
