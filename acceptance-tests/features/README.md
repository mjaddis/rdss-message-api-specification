# Acceptance Tests for Write,Read Kinesis Stream

Acceptance tests for writing and Reading from Kinesis are written using Python [behave](http://pythonhosted.org/behave/) and the [Gherkin](https://github.com/cucumber/cucumber/wiki/Gherkin).
 
The Gherkin feature file is implemented by "write-read-tests.feature" in the "acceptance-tests/features/write_read_stream".

The Given, When and Then statements in the .feature file is implemented by "step" functions in the "acceptance-tests/features/steps/steps.py".

## Requirements

- Python 3
- AWS-CLI
- behave
- Gherkin

## Configuration

In order to read and write to the streams, a valid AWS user account must exist that has permissions to interact with the Kinesis streams. For the purposes of the RDSS project, multifactor authentication is mandatory for all AWS users and as such, extra steps must be undertaken to ensure that the scripts can successfuly authenticate against the AWS API.

1. [Install AWS-CLI](https://aws.amazon.com/cli/) so that the `aws` tool is available.
2. Execute `gem install aws-mfa` (this is an open source utility [available on GitHub](https://github.com/lonelyplanet/aws-mfa))
3. Execute `aws configure`, and provide your AWS access key and secret key.
4. [Install Ruby](https://www.ruby-lang.org/en/downloads/) so that the `gem` tool is available.
5. Execute `aws-mfa` and, when promoted, provide your 6 digit multifactor authentication token.

At this point, `aws-mfa` will output four values to the terminal:

```
export AWS_SECRET_ACCESS_KEY='<SECRET_ACCESS_KEY>'
export AWS_ACCESS_KEY_ID='<ACCESS_KEY_ID>'
export AWS_SESSION_TOKEN='<SESSION_TOKEN>'
export AWS_SECURITY_TOKEN='<SECURITY_TOKEN>'
```

For users using a *nix system (e.g. macOS or Linux), these four lines can be copied and pasted straight into the terminal. For users using a Windows system, the output must be modified before being pasted into the command prompt:

- Replace `export` with `set`
- Remove the apostraphes `'` from the around values.

`aws-mfa` will have also created a `~/.aws/mfa_credentials` file in your home directory. This file is then read by the scripts so that authentication against the AWS API can take place.

## Usage

### Writing to Stream

JSON data can be written to the Kinesis stream using the provided `write_stream.py` script. Messages to be written must be in JSON format and saved to files with the extension `.json` (one message per file).

`py write_stream.py <stream_name> <json_dir>`

- `stream_name` **(required)**: the name of the Kinesis stream to which the message(s) are to be written.
- `json_dir` **(required)**: the directory containing the JSON files (with `.json` extension) to be written to the stream.
- `json_dir` ** Replace directory path in the 'steps.py'on line 12 to your local path.

All messages are written using a partition key `test-data`.

### Reading from Stream

The `read_stream.py` script will poll the stream every 5 seconds, printing the entire JSON body of any messages to the terminal.

`py read_stream.py <stream_name> --sequence_number <sequence_number>`

- `stream_name` **(required)**: the name of the Kinesis stream from which messages are to be read.
- `sequence_number` **(optional)**: the sequence number of a message after which polling will begin from.

The process of reading from a Kinesis stream is more involved that writing. There are several steps that must be carried out before records can be retrieved:

1. A call to `DescribeStream` is executed, which returns detailed information about the Kinesis stream, including the ID's of the shards associated with the stream.
2. The shard ID is then passed to the `GetShardIterator` call, which returns a shard iterator.
3. The shard iterator is then passed to the `GetRecords` call, which returns the records from the stream.
4. The response returned from step (3) contains a `NextShardIterator` value, that is then passed to step (3) again, and the loop continues.

The `--sequence_number` parameter however can be provided such that in step (2) above, instead of fetching the shard iterator value for the last untrimmed record in the shard (which would cause **all** records in the shard to be returned), the shard iterator is returned for the record immediately after the record defined by the `--sequence_number` parameter. This allows the script to skip to a certain point in the shard, bypassing records that have already been processed.

A value for the `--sequence_number` parameter can be obtained by executing the script without providing the parameter, and obtaining the `SequenceNumber` value for the relevant record.

### Running behave tests with tags

` $ behave --tags=test ../features/write_read_stream/write-read-tests.feature`
