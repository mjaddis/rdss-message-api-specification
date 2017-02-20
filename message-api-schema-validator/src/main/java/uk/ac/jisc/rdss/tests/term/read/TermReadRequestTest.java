package uk.ac.jisc.rdss.tests.term.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermReadRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/read/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/term/read/request.json");
    }
}
