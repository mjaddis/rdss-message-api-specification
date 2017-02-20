package uk.ac.jisc.rdss.tests.vocabulary.delete;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyDeleteRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/delete/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/vocabulary/delete/request.json");
    }
}
