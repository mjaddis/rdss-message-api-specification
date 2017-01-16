package uk.ac.jisc.rdss.tests.metadata.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataUpdateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "operations/metadata/update/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("operations/metadata/update/example.json");
    }
}
