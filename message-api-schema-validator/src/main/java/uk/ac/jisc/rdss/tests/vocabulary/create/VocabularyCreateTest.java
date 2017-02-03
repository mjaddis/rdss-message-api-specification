package uk.ac.jisc.rdss.tests.vocabulary.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class VocabularyCreateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/vocabulary/create/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("messages/vocabulary/create/example.json");
    }
}
