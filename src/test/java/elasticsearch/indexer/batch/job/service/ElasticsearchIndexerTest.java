package elasticsearch.indexer.batch.job.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elasticsearch.indexer.batch.dto.BookDto;
import elasticsearch.persistence.elasticsearch.indices.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles(profiles = "pgsql")
class ElasticsearchIndexerTest {
    @Autowired
    ElasticsearchIndexer indexer;

    @Test
    void testIndex() throws JsonProcessingException {
        // given
        BookDto book = BookDto.builder()
                .bookName("세이노의 가르침")
                .author("세이노(SayNo)")
                .barcode("9791168473690")
                .rlseDate("20230302")
                .publisher("데이원")
                .price("7200")
                .build();

        // when
        List<BookDto> books = new ArrayList<>();
        books.add(book);
        List<IndexedObjectInformation> result = indexer.esBookBulkIndex(books);

        // then
        assertTrue(result.size() > 0);

    }
}