package uk.ac.jisc.rdss.tests.metadata.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataUpdateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/update/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/metadata/update/request.json");
    }
}
