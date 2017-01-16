# Message API Schema Validator

This Java project allows JSON structures representing a message to be validated against the provided JSON schemas.

## Building

The project can be built using Maven:

```
mvn clean package -U
```

The build process will produce a ZIP file `target/message-api-schema-validator-zip.zip`.

## Usage

The contents of the ZIP produced by the build process should be extracted to a directory of choice. The [`start.bat`](src/main/resources/scripts/start.bat) file should then be modified and the following variables set:

-  `JRE_HOME`
 - The path of the JRE installation.
- `VALIDATOR_HOME`
 - The path of where the ZIP file was extracted to.
- `RDSS_MESSAGE_API_DOCS`
 - The path to a copy of this repository, specifically containing the [`schemas`](../schemas/) and [`operations`](../operations/) folders.

Once the variables have been set, the [`start.bat`](src/main/resources/scripts/start.bat) file can be executed.
