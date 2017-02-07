package uk.ac.jisc.rdss.tests.metadata.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataReadRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/read/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/metadata/read/request.json");
    }
}
