package elasticsearch.indexer.batch.dto;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import elasticsearch.common.serializer.UpperSnakeCaseStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@JsonNaming(UpperSnakeCaseStrategy.class)
public class BookDto {

    private String bookName;
    private String publisher;
    private String author;
    private String barcode;
    private String price;
    private String rlseDate;
}
