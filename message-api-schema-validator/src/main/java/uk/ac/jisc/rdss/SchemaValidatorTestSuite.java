package uk.ac.jisc.rdss;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import uk.ac.jisc.rdss.tests.metadata.create.MetadataCreateTest;

@RunWith(Suite.class)
@SuiteClasses({MetadataCreateTest.class})
public class SchemaValidatorTestSuite {

    public static void main(String[] args) {
        JUnitCore.main(SchemaValidatorTestSuite.class.getName());
    }
}
