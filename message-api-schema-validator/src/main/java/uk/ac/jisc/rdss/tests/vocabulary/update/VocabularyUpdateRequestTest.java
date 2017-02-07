package uk.ac.jisc.rdss.tests.vocabulary.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyUpdateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/update/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/vocabulary/update/request.json");
    }
}
