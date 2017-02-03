package uk.ac.jisc.rdss.tests.vocabulary.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyUpdateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/update/schema.json";
    }
    
    @Test
    public void validateExample() throws Exception {
        validateJson("messages/vocabulary/update/example.json");
    }
}
