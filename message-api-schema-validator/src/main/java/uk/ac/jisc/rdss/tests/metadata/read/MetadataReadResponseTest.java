package uk.ac.jisc.rdss.tests.metadata.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataReadResponseTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/read/response_schema.json";
    }

    @Test
    public void validateResponse() throws Exception {
        validateJson("messages/metadata/read/response.json");
    }
}
