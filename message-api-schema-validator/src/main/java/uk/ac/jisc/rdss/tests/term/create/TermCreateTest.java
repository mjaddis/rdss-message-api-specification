package uk.ac.jisc.rdss.tests.term.create;

import org.junit.Test;

import uk.ac.jisc.rdss.tests.AbstractSchemaValidatorTest;

public class TermCreateTest extends AbstractSchemaValidatorTest {

    @Override
    protected String getSchemaFileName() {
        return "messages/term/create/schema.json";
    }

    @Test
    public void validateExample() throws Exception {
        validateJson("messages/term/create/example.json");
    }
}
