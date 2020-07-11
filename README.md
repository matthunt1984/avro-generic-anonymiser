# avro-generic-anonymiser

Currently a POC to show that we can anonymise any given Avro payload, given an appropriate custom metadata tag

In this case we have a schema [person.avsc](src/main/resources/person.avsc) that defines first name and last name fields with `ispii=true`
We also have a JSON representation of a person in [person1.json](src/main/resources/person1.json) that needs redacting

Currently this runs as a java console application and simply reads the schema and msg, anonymises and outputs 'REDACTED' where PII fields are encountered.