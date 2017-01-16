package uk.ac.jisc.rdss.tests.metadata.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataCreateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "operations/metadata/create/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("operations/metadata/create/example.json");
    }
}
