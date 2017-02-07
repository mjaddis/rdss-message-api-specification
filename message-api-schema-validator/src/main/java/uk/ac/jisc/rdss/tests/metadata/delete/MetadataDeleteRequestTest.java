package uk.ac.jisc.rdss.tests.metadata.delete;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataDeleteRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/delete/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/metadata/delete/request.json");
    }
}
