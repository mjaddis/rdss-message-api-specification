package uk.ac.jisc.rdss.tests.metadata.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class MetadataCreateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/metadata/create/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/metadata/create/request.json");
    }
}
