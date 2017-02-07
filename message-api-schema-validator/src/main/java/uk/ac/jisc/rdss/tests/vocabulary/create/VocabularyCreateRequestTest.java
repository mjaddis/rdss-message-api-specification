package uk.ac.jisc.rdss.tests.vocabulary.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyCreateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/create/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/vocabulary/create/request.json");
    }
}
