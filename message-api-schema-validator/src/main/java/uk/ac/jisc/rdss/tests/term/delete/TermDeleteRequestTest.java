package uk.ac.jisc.rdss.tests.term.delete;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermDeleteRequestTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/delete/request_schema.json";
    }

    @Test
    public void validateRequest() throws Exception {
        validateJson("messages/term/delete/request.json");
    }
}
