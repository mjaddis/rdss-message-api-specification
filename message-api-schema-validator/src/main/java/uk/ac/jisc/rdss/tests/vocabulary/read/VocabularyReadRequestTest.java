package uk.ac.jisc.rdss.tests.vocabulary.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyReadRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/read/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/vocabulary/read/request.json");
    }
}
