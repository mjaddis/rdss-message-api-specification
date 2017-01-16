package uk.ac.jisc.rdss.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
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

    protected final Logger LOGGER = Logger.getLogger(getClass());

    private static final String SCHEMA_ENUMERATION_ID = "https://www.jisc.ac.uk/rdss/schema/enumeration.json/#";
    private static final String SCHEMA_INTELLECTUAL_ASSET_ID = "https://www.jisc.ac.uk/rdss/schema/intellectual_asset.json/#";
    private static final String SCHEMA_MATERIAL_ASSET_ID = "https://www.jisc.ac.uk/rdss/schema/material_asset.json/#";
    private static final String SCHEMA_RESEARCH_OBJECT_ID = "https://www.jisc.ac.uk/rdss/schema/research_object.json/#";

    private static final String SCHEMA_ENUMERATION_PATH = "schemas/enumeration.json";
    private static final String SCHEMA_INTELLECTUAL_ASSET_PATH = "schemas/intellectual_asset.json";
    private static final String SCHEMA_MATERIAL_ASSET_PATH = "schemas/material_asset.json";
    private static final String SCHEMA_RESEARCH_OBJECT_PATH = "schemas/research_object.json";

    private static final String BASE_PATH = System.getProperty("project.base.path");

    protected abstract String getSchemaFileName();

    private JsonSchemaFactory jsonSchemaFactory;

    @Before
    public void before() throws IOException {
        LoadingConfigurationBuilder loadingConfigurationBuilder = LoadingConfiguration.newBuilder();

        preloadSchema(loadingConfigurationBuilder, SCHEMA_ENUMERATION_ID, SCHEMA_ENUMERATION_PATH);
        preloadSchema(loadingConfigurationBuilder, SCHEMA_INTELLECTUAL_ASSET_ID, SCHEMA_INTELLECTUAL_ASSET_PATH);
        preloadSchema(loadingConfigurationBuilder, SCHEMA_MATERIAL_ASSET_ID, SCHEMA_MATERIAL_ASSET_PATH);
        preloadSchema(loadingConfigurationBuilder, SCHEMA_RESEARCH_OBJECT_ID, SCHEMA_RESEARCH_OBJECT_PATH);

        LOGGER.info(String.format("Creating JsonSchemaFactory with LoadingConfigurationBuilder [%s]", loadingConfigurationBuilder));
        this.jsonSchemaFactory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(loadingConfigurationBuilder.freeze()).freeze();
    }

    private void preloadSchema(LoadingConfigurationBuilder loadingConfigurationBuilder, String schemaId, String schemaPath) throws IOException {
        LOGGER.info(String.format("Preloading schema [%s] with ID [%s]", schemaPath, schemaId));
        loadingConfigurationBuilder.preloadSchema(schemaId, getJson(schemaPath));
    }

    protected void validateJson(String jsonFileName) throws IOException, ProcessingException {

        JsonNode schema = getJson(getSchemaFileName());
        LOGGER.info(String.format("Got JSON Schema [%s] as [%s]", getSchemaFileName(), schema));
        assertNotNull(schema);

        JsonNode json = getJson(jsonFileName);
        LOGGER.info(String.format("Got JSON [%s] as [%s]", jsonFileName, json));
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
        return String.format("%s/%s", BASE_PATH, fileName);
    }
}
