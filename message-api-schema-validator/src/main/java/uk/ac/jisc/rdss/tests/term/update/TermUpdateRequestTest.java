package uk.ac.jisc.rdss.tests.term.update;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermUpdateRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/update/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/term/update/request.json");
    }
}
