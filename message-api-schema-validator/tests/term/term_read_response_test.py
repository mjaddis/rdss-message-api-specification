from tests.abstract_schema_validator_test import AbstractSchemaValidatorTest
from unittest import TestCase


class TermReadResponseTest(AbstractSchemaValidatorTest, TestCase):
    def get_json_schema_file_name(self):
        return "messages/term/read/response_schema.json"

    def test_validate_request(self):
        self.validate_json("messages/term/read/response.json")
