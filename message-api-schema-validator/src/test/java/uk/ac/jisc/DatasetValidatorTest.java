package uk.ac.jisc;

import org.junit.Test;

public class DatasetValidatorTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "operations/metadata/create/schema.json";
    }

    @Test
    public void validateExampleOne() throws Exception {
        validateJson("operations/metadata/create/example.json");
    }
}
