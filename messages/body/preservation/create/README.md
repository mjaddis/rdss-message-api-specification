# Preservation Create

Message Type: `PreservationCreate`.

## Request

- Example:&nbsp;&nbsp;&nbsp;[`request.json`](request.json)
- Schema:&nbsp;&nbsp;&nbsp;&nbsp;[`request_schema.json`](request_schema.json)

## Response

N/A.


For a quick validation test use [ajv](https://github.com/epoberezkin/ajv) and run:
```
ajv validate -s messages/body/preservation/create/request_schema.json \
-r schemas/research_object.json \
-r schemas/types.json \
-r schemas/enumeration.json \
-r schemas/material_asset.json \
-r schemas/intellectual_asset.json \
-r schemas/resourceTypes/information_package.json \
-d messages/body/preservation/create/request.json
```
