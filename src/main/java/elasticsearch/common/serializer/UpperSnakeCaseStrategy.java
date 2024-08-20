package elasticsearch.common.serializer;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class UpperSnakeCaseStrategy extends PropertyNamingStrategies.SnakeCaseStrategy {

    @Override
    public String translate(String input) {
        return super.translate(input).toUpperCase();
    }
}
