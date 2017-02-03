package uk.ac.jisc.rdss.tests.metadata.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataUpdateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/update/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("messages/metadata/update/example.json");
    }
}
