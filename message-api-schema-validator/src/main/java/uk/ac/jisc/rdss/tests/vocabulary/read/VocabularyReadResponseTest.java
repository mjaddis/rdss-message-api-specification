package uk.ac.jisc.rdss.tests.vocabulary.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyReadResponseTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/read/response_schema.json";
    }

    @Test
    public void validateResponse() throws Exception {
        validateJson("messages/vocabulary/read/response.json");
    }
}
