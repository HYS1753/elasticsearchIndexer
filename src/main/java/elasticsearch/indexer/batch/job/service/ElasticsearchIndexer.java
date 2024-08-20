package elasticsearch.indexer.batch.job.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import elasticsearch.persistence.elasticsearch.indices.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ElasticsearchIndexer {

    private final ElasticsearchOperations esOperations;

    public List<IndexedObjectInformation> esBookBulkIndex(List<?> sourceList) {
        List<IndexQuery> indexQueryList = this.makeIndexQuery(sourceList);

        return esOperations.bulkIndex(indexQueryList, BookEntity.class);
    }

    private List<IndexQuery> makeIndexQuery(List<?> sourceList) {
        List<IndexQuery> indexQueryList = new ArrayList<>();

        if (sourceList == null || sourceList.isEmpty()) {
            return indexQueryList;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Object firstElement = sourceList.get(0);

        if (firstElement instanceof String) {
            for (Object object : sourceList) {
                indexQueryList.add(
                        new IndexQueryBuilder()
                                .withSource((String)object)
                                .build()
                );
            }
            return indexQueryList;
        } else {
            try {
                for (Object object : sourceList) {
                    indexQueryList.add(
                            new IndexQueryBuilder()
                                    .withSource(objectMapper.writeValueAsString(object))
                                    .build()
                    );
                }
            } catch (Exception e) {
                log.error("convert sourceList(for Elasticsearch indexing) to string failed", e);
            }
            return indexQueryList;
        }
    }

}
