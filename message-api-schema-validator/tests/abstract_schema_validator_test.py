from json import load
from jsonschema import validate, RefResolver
from abc import ABCMeta, abstractmethod
from sys import argv
from unittest import TestCase


class AbstractSchemaValidatorTest(TestCase):
    __metaclass__ = ABCMeta

    schema_enumeration_id = "https://www.jisc.ac.uk/rdss/schema/enumeration.json/#";
    schema_intellectual_asset_id = "https://www.jisc.ac.uk/rdss/schema/intellectual_asset.json/#";
    schema_material_asset_id = "https://www.jisc.ac.uk/rdss/schema/material_asset.json/#";
    schema_research_object_id = "https://www.jisc.ac.uk/rdss/schema/research_object.json/#";

    schema_enumeration_path = "schemas/enumeration.json";
    schema_intellectual_asset_path = "schemas/intellectual_asset.json";
    schema_material_asset_path = "schemas/material_asset.json";
    schema_research_object_path = "schemas/research_object.json";

    base_path = argv[2]

    @abstractmethod
    def get_json_schema_file_name(self): pass

    def validate_json(self, json_file_name):
        json_schema = self.get_json(self.get_json_schema_file_name())
        json_data = self.get_json(json_file_name)
        validate(json_data, json_schema, resolver=RefResolver("", {}, store={
            self.schema_enumeration_id: self.get_json(self.schema_enumeration_path),
            self.schema_intellectual_asset_id: self.get_json(self.schema_intellectual_asset_path),
            self.schema_material_asset_id: self.get_json(self.schema_material_asset_path),
            self.schema_research_object_id: self.get_json(self.schema_research_object_path)
        }))

    def get_json(self, file_name):
        with open(self.prepare_file_name(file_name)) as json_data:
            return load(json_data)

    def prepare_file_name(self, file_name):
        return self.base_path + "/" + file_name
