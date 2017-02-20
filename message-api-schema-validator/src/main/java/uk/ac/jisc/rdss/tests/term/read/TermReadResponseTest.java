package uk.ac.jisc.rdss.tests.term.read;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermReadResponseTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/read/response_schema.json";
    }

    @Test
    public void validateResponse() throws Exception {
        validateJson("messages/term/read/response.json");
    }
}
