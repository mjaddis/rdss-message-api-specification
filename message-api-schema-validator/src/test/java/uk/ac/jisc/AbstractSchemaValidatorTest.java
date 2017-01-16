package uk.ac.jisc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.junit.Before;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfiguration;
import com.github.fge.jsonschema.core.load.configuration.LoadingConfigurationBuilder;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.fge.jsonschema.main.JsonValidator;

public abstract class AbstractSchemaValidatorTest {

    protected abstract String getSchemaFileName();

    private JsonSchemaFactory jsonSchemaFactory;

    @Before
    public void before() throws IOException {
        LoadingConfigurationBuilder loadingConfigurationBuilder = LoadingConfiguration.newBuilder();
        loadingConfigurationBuilder.preloadSchema("https://www.jisc.ac.uk/rdss/schema/enumeration.json/#", getJson("schemas/enumeration.json"));
        loadingConfigurationBuilder.preloadSchema("https://www.jisc.ac.uk/rdss/schema/intellectual_asset.json/#", getJson("schemas/intellectual_asset.json"));
        loadingConfigurationBuilder.preloadSchema("https://www.jisc.ac.uk/rdss/schema/material_asset.json/#", getJson("schemas/material_asset.json"));
        loadingConfigurationBuilder.preloadSchema("https://www.jisc.ac.uk/rdss/schema/research_object.json/#", getJson("schemas/research_object.json"));

        this.jsonSchemaFactory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(loadingConfigurationBuilder.freeze()).freeze();
    }

    protected void validateJson(String jsonFileName) throws IOException, ProcessingException {

        JsonNode schema = getJson(getSchemaFileName());
        assertNotNull(schema);

        JsonNode json = getJson(jsonFileName);
        assertNotNull(json);

        JsonValidator jsonValidator = jsonSchemaFactory.getValidator();
        ProcessingReport processingReport = jsonValidator.validate(schema, json);
        assertNotNull(processingReport);
        if (!processingReport.isSuccess()) {

            ArrayNode jsonArray = JsonNodeFactory.instance.arrayNode();
            assertNotNull(jsonArray);

            Iterator<ProcessingMessage> iterator = processingReport.iterator();
            while (iterator.hasNext()) {
                ProcessingMessage processingMessage = iterator.next();
                jsonArray.add(processingMessage.asJson());
            }

            fail(jsonArray.toString());
        }
    }

    private JsonNode getJson(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(prepareFileName(fileName));
        String jsonStr = IOUtils.toString(inputStream, "UTF-8");
        return JsonLoader.fromString(jsonStr);
    }

    private String prepareFileName(String fileName) {
        String basePath = System.getProperty("project.base.path");
        return String.format("%s/%s", basePath, fileName);
    }
}
