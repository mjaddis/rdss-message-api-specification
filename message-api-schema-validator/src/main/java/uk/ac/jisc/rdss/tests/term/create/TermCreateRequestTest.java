package uk.ac.jisc.rdss.tests.term.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermCreateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/create/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/term/create/request.json");
    }
}
