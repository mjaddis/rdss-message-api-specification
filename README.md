# RDSS Message API

- [Introduction](#introduction)
- [Message Structure](#message-structure)
- [Message Header](#message-header)
- [Message Body](#message-body)
- [Error Queues](#error-queues)
- [Audit Log](#audit-log)
- [Topology](#topology)
- [Transactional Behaviour](#transactional-behaviour)
- [Message Gateway & Channel Adapter](#message-gateway-channel-adapter)
- [Logging](#logging)
- [Application Error Codes](#application-error-codes)
- [Non-Functional Requirements](#non-functional-requirements)

## Introduction

This repository documents the RDSS Message API and describes the format and structure of messages sent within the RDSS project, and architectural designs and patterns for the underlying messaging system.

The API, format, structures and patterns are derived from material from [Enterprise Integration Patterns](http://www.enterpriseintegrationpatterns.com/).

### Audience

The RDSS Message API is intended for the following audience:

- Engineering
- Operations
- Quality Assurance

### Versioning

Current version:&nbsp;&nbsp;&nbsp;&nbsp;`0.0.1-SNAPSHOT`

Versioning for the RDSS Message API follows [Semantic Versioning 2.0.0](http://semver.org/spec/v2.0.0.html).

### Comformance

The keywords **MAY**, **MUST**, **MUST NOT**, **NOT RECOMMENDED**, **RECOMMENDED**, **SHOULD** and **SHOULD NOT** are to be interpreted as described in [RFC2219](https://tools.ietf.org/html/rfc2119).

## Message Structure

The following diagram describes the Message structure (the diagram can be edited using [StarUML](http://staruml.io/). The source is provided in the [`model/model.mdj`](model/model.mdj) file):

![Message Structure](model/model.png)

A Message is broken into two parts:
- The [Message Header](#message-header)
- The [Message Body](#message-body)

The standard encoding for a Message is [JSON](http://www.json.org/), and the examples provided in this documentation are given in this format.

The maximum size of a serialised JSON Message **MUST NOT** exceed 1000KB.

## Message Header

The Message Header contains important metadata describing the Message itself, including the type of Message, routing information, timings, sequencing, and so forth.

### `messageId`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

System wide unique identifier describing the Message. It is expected that this will be a 128-bit [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier).

### `correlationId`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`0..1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

Provided on a return Message, containing the [`messageId`](#messageid) of the originally received Message.

### `messageClass`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MessageClass`

One of `Command`, `Event` or `Document`.

### `messageType`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MessageType`

Describes the type of Message, in the format of `<Type><Operation>` and is described in the [Message Body](#message-body) section.

### `returnAddress`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`0..1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

Describes the address to which the receiver should dispatch their return Message.

### `messageTimings`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MessageTimings`

Contains any temporal information related to the Message.

#### `publishedTimestamp`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Timestamp`

Describes the point in time in which the Message was dispatched.

#### `expirationTimestamp`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`0..1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Timestamp`

Describes the point in time when the Message expires.

### `messageSequence`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MessageSequence`

Describes whether or not this Message is part of a larger sequence of Messages and, if so, where in that sequence it belongs.

#### `sequence`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

System wide unique identifier that distinguishes this sequence of Messages from others. It is expected that this will be a 128-bit [UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier).

#### `position`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Integer`

Position in the sequence of Messages where this Message belongs.

#### `total`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Integer`

Total number of Messages in this sequence.

### `messageHistory`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`0..*`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`MessageHistory`

Describes the route that this Message has taken, up to and including this point in time.

#### `machineId`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

The ID of the machine that generated the history entry.

#### `machineAddress`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`String`

The hostname, IP address, or other identifying network address of the machine that generated the history entry.

#### `timestamp`

- Multiplicity:&nbsp;&nbsp;&nbsp;&nbsp;`1`
- Type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`Timestamp`

The timestamp at which the history entry was generated.

## Message Body

### JSON Schema

The following JSON schemas are provided as part of this project, which fully describe the associated [Data Model](https://github.com/JiscRDSS/rdss-canonical-data-model):

- [`schemas/research_object.json`](schemas/research_object.json)
- [`schemas/material_asset.json`](schemas/material_asset.json)
- [`schemas/intellectual_asset.json`](schemas/intellectual_asset.json)
- [`schemas/enumeration.json`](schemas/enumeration.json) - *Note that enumeration values are provided for reference only. Enumerations* ***MUST*** *be referenced using their respective ID values.*

The schemas can be used to assist in development and validation of JSON objects that represent payloads, which are described in this API. Additionally, they are also used within the [`message-api-schema-validator/`](message-api-schema-validator/) tool, which validates the example payload JSON objects described in the [`messages/`](messages/) folder.

Currently, all JSON schemas IDs (including `$ref` declarations within the schemas) are namespaced under `https://www.jisc.ac.uk/rdss/schema/`. However, consumers of the schemas should not expect the schemas to be available at the URLs represented by these IDs.

### Messages

The following example Messages are provided in the [`messages/`](messages/) folder:

|                | **Read**                                                                                                    | **Create**                                                                                                  | **Update**                                                                                                  | **Patch**                                                                                                 | **Delete**                                                                                                  |
|----------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|
| **Vocabulary** | Message Type:   `VocabularyRead`<br>Documentation: [`messages/vocabulary/read/`](messages/vocabulary/read/) | Not Supported                                                                                               | Not Supported                                                                                               | Message Type:   `VocabularyRead`Documentation: [`messages/vocabulary/patch/`](messages/vocabulary/patch/) | Not Supported                                                                                               |
| **Metadata**   | Message Type:   `MetadataRead`<br>Documentation: [`messages/metadata/read/`](messages/metadata/read/)       | Message Type:   `MetadataCreate`<br>Documentation: [`messages/metadata/create/`](messages/metadata/create/) | Message Type:   `MetadataUpdate`<br>Documentation: [`messages/metadata/update/`](messages/metadata/update/) | Not Supported                                                                                             | Message Type:   `MetadataDelete`<br>Documentation: [`messages/metadata/delete/`](messages/metadata/delete/) |

In all instances where a response is required, the [`correlationId`](#correlationid) **MUST** be provided in the header of the Message and **MUST** match the [`messageId`](#messageid) provided in the original request.

## Error Queues

### Error Message Queue

If a receiver is unable to process a Message due to, for example, and infrastructure issue (such as network connectivity), or a Message that exceeds the maximum size defined in [Non-Functional Requirement](#non-functional-requirement), it **SHOULD** move the erroneous Message to the Error Message Queue.

A Message that is to be routed to the Error Message Queue must be decorated with the appropriate error code, as per the [Error Codes](#error-codes) section, before it is dispatched.

### Invalid Message Queue

If a receiver receives a Message it cannot process, for example if the Message is malformed or does not contain all the mandatory fields, it **SHOULD** move the invalid Message to an Invalid Message Queue.

A Message that is to be routed to the Invalid Message Queue must be decorated with the appropriate error code, as per the [Error Codes](#error-codes) section, before it is dispatched.

## Error Codes

### General Error Codes

The following tables describes the error codes that **MUST** be utilised when a Message is moved to either the [Error Message Queue](#error-message-queue) and the [Invalid Message Queue](#invalid-message-queue), and in all [Logging](#logging) entries that describe an error:

| Error Code  | Description                                                                                                  |
|-------------|--------------------------------------------------------------------------------------------------------------|
| `GENERR001` | The [`Message Body`](#message-body) is not in the expected format, for example mandatory fields are missing. |
| `GENERR002` | The provided [`messageType`](#messagetype) is not supported.                                                 |
| `GENERR003` | The expiration date of the Message had passed at the point at which delivery was attempted.                  |
| `GENERR004` | Invalid, missing or corrupt headers were detected on the Message.                                            |
| `GENERR005` | Maximum number of connection retries exceeded when attempting to send the Message.                           |
| `GENERR006` | An error occurred interacting with the repository.                                                           |
| `GENERR007` | Malformed JSON was detected in the Message Body.                                                             |
| `GENERR008` | An attempt to roll back a transaction failed.                                                                |
| `GENERR009` | An unexpected or unknown error occurred.                                                                     |

### Application Error Codes

The following sections describe the error codes that **MUST** be utilised when an application raises an error that relate specifically to the type of Messages it receives.

#### Metadata Error Codes

| Error Code     | Description                                                            |
|----------------|------------------------------------------------------------------------|
| `APPERRMET001` | Received a Metadata `UPDATE` with a `datasetUuid` that does not exist. |
| `APPERRMET002` | Received a Metadata `DELETE` with a `datasetUuid` that does not exist. |

#### Vocabulary Error Codes

| Error Code     | Description                                                               |
|----------------|---------------------------------------------------------------------------|
| `APPERRVOC001` | Received a Vocabulary `UPDATE` with a `vocabularyId` that does not exist. |
| `APPERRVOC002` | Received a Vocabulary `DELETE` with a `vocabularyId` that does not exist. |

#### Term Error Codes

| Error Code     | Description                                                                               |
|----------------|-------------------------------------------------------------------------------------------|
| `APPERRTER001` | Received a Term `UPDATE` with a `vocabularyId` that does not exist.                       |
| `APPERRTER002` | Received a Term `UPDATE` with a `termId` that does not exist in the given `vocabularyId`. |
| `APPERRTER003` | Received a Term `DELETE` with a `vocabularyId` that does not exist.                       |
| `APPERRTER004` | Received a Term `DELETE` with a `termId` that does not exist in the given `voabularyId`.  |

## Audit Log

The Audit Log is a destination for Messages that every Message sent through the system will arrive at.

It is delivered in the form of an [AWS Kinesis Firehose](https://aws.amazon.com/kinesis/firehose/), which in turn loads the data into an [Amazon S3](https://aws.amazon.com/s3/). The data is then made available for consumption and processing by other systems (e.g reporting).

In order for a Message to be consumed by the Audit Log, Messages **MUST** be in serialised JSON format and **MUST NOT** exceed 1000KB.

## Topology

The following diagram describes the topology of the Messaging system (the diagram can be edited using [Microsoft Visio](https://products.office.com/en-gb/visio/flowchart-software). The source is provided in the [`topology/topology.vsdx`](topology/topology.vsdx) file).

The following stencils are used in the creation of the diagram:

- [Hohpe EID Stencils](http://www.enterpriseintegrationpatterns.com/downloads.html)
- [Amazon AWS Stencils](https://aws.amazon.com/architecture/icons/)

| Key                                                              | Description             |
|------------------------------------------------------------------|-------------------------|
| ![Directional Channel](topology/directional-channel.png)         | Directional Channel     |
| ![Invalid Message Channel](topology/invalid-message-channel.png) | Invalid Message Channel |
| ![Error Message Channel](topology/error-message-channel.png)     | Error Message Channel   |
| ![Channel Adapter](topology/channel-adapter.png)                 | Channel Adapter         |
| ![Message Broker](topology/message-broker.png)                   | Message Broker          |
| ![Content Based Router](topology/content-based-router.png)       | Content Based Router    |

![Topology](topology/topology.png)

- [Message Routers](http://www.enterpriseintegrationpatterns.com/patterns/messaging/MessageRouter.html) and [Channel Adapters](http://www.enterpriseintegrationpatterns.com/patterns/messaging/ChannelAdapter.html) are implemented as [AWS Lambda](https://aws.amazon.com/lambda/) services.
- [Message Channels](http://www.enterpriseintegrationpatterns.com/patterns/messaging/MessageChannel.html) are implemented using [AWS Kinesis Streams](https://aws.amazon.com/kinesis/streams/).
- Firehoses are implemented using [AWS Kinesis Firehose](https://aws.amazon.com/kinesis/firehose/).
- Logs are implemented using [Amazon S3](https://aws.amazon.com/s3/).

## Transactional Behaviour

All clients **MUST** implement transactional behaviour, such that any message consumed by a receiver will not be regarded as consumed until the client commits receipt of that message back to the sender / queue.

For example, a client can consume a message without actually removing it from the channel. Should the client crash or fail to process the message, the message still exists on the queue after the client recovers. Once the receiver processes the message and is certain that it wants to consume the message, the client will commit the transaction and the message will be removed the queue.

## Message Gateway & Channel Adapter

The messaging system offers applications who wish to send and receive messages two mechanisms of interaction: a [Message Gateway](http://www.enterpriseintegrationpatterns.com/patterns/messaging/MessagingGateway.html) and a [Channel Adapter](http://www.enterpriseintegrationpatterns.com/patterns/messaging/ChannelAdapter.html).

### Message Gateway

The Message Gateway offers the preferred interface to the messaging system. It exists within the application itself and encapsulates the code specific to the messaging system whilst exposing APIs for interaction.

The following diagram describes the class structure of a Message Gateway (the diagram can be edited using [StarUML](http://staruml.io/). The source is provided in the [`message-gateway/message-gateway-simple.mdj`](message-gateway/message-gateway-simple.mdj) file).

![Message Gateway](message-gateway/message-gateway-simple.png)

The Message Gateway is designed to abstract the complexities and specifics of the underlying queueing system from the application, including the handling the requests and responses, and hiding the scenario where a single call to the Message Gateway interface may result in multiple calls to the queueing system.

The Message Gateway **MUST** be synchronous, such that all calls to Message Gateway interface will block until the underlying request(s) complete in full.

All Message Gateway implementations **MUST** be configurable to support, at a minimum:

- Switching between queue types, e.g. AWS Kinesis Stream, RabbitMQ and Mock
- Specify the addresses of the channels supported by the Message Gateway
- The retry interval for pulling from the queue when waiting for a return Message

#### Message Gateway Sequence Diagrams

The sequence diagrams below describe the flow of executing through the Message Gateway for both a [Metadata Create](messages/metadata/create/) and a [Metadata Read](messages/metadata/read/) operation (the diagrams can be edited using [Visual Paradigm](https://www.visual-paradigm.com/). The source is provided in the [`message-gateway/sequence-diagrams.vpp`](message-gateway/sequence-diagrams.vpp) file).

##### Metadata Create

![Message Gateway Metadata Create](message-gateway/metadata-create.png)

The creation process is "fire and forget", insomuch that it does not expect a return Message in response to the Message that is puts on the queue.

##### Metadata Read

![Message Gateway Metdata Read](message-gateway/metadata-read.png)

In contrast to the Metadata Create operation, the Metadata Read operation requires a response Message.

To model this, the `Message Chanel` lifeline enters the following loop:

1. Execute `GetRecords` for the current `ShardIterator`
2. Seach the return `Record`'s for a Message with a `correlationId` that matches the request Message
3. If found, exit the loop
4. Otherwise, update the `ShardIterator` with the `NextShardIterator` value returned in step `1`
5. Sleep for a predefined period of time
6. Return to step `1`

### Channel Adapter

The alternative interface to the messaging system is the Channel Adapter, which does not require code implementation as part of the application. Instead, the Channel Adapter exists as a separate component and acts as a middle man between the channel and the application, leveraging synchronous APIs that the application exposes.

![Channel Adapter](channel-adapter/channel-adapter.png)

## Logging

All applications that interact with the messaging system, whether as a sender or receiver, **MUST** generate useful log messages for consumption by engineers and system operations staff.

### Usage

Log messages generated by applications must be written to the local syslog service provided by the operating system. Most Unix based operating systems provide a simple utility known as `logger` to interact with the syslog service.

The following example describes how to generate the log message examples provided in the [Log Message Format](#log-message-format) section:

```
logger -p local0.info -i "[INFO] Message sent"
logger -p local0.info -i "[INFO] Message received"
```

For informational purposes, the expected format of a raw syslog log message is described in the [Log Message Format](#log-message-format) section.

### Log Message Format

Log messages delivered in syslog format consist of three parts:

- [Log Message Header](#log-message-header)
- [Log Message](#log-message)

Examples:

```
<134>1 2017-03-01T13:14:15.000Z machine.jisc.ac.uk msgsender-1.2.0 848221 "[INFO] Message sent."

<134>1 2017-03-01T15:16:17.000Z machine.jisc.ac.uk msgreceiver-1.3.1 810038 "[INFO] Message received."
```

In this example, the [Log Message Header](#log-message-header) begins with a priority of `134`, followed by a syslog protocol version of `1`. The hostname of the originating machine is `machine.jisc.ac.uk` and the applications are `msgsender` and `msgreceiver` with versions `1.2.0` and `1.3.1` respectively. The process IDs are `848221` and `810038`. The [Log Message](#log-message) then follows.

#### Log Message Header

The header of a syslog log message takes the following format:

```
<PRI>VERSION TIMESTAMP HOSTNAME APP-NAME PROCID
```

##### `PRI`

Describes the priority of the log message. It is derived from the numerical codes of the facility and the severity of the log message as `(FACILITY * 8) + SEVERITY`. These values are described in more detail in section [6.2.1](https://tools.ietf.org/html/rfc5424#section-6.2.1) of RFC5424.

Log messages **MUST** use a facility value of between `16 - local0` and `23 - local7` inclusive (other facilities are reserved for system processes and services), and are free to use any severity value deemed appropriate for the purposes of the log message.

##### `VERSION`

Describes the version of the syslog protocol specification utilised by the originator. The current version is `1`.

##### `TIMESTAMP`

Describes the originator system time at which the log message was generated. It is given in the format of [RFC3339](https://tools.ietf.org/html/rfc3339) with the following further restrictions imposed:

- The `T` and `Z` characters in this syntax **MUST** be uppercase.
- Usage of the `T` character is **REQUIRED**.
- Leap seconds **MUST NOT** be used.

##### `HOSTNAME`

Identifies the fully qualified hostname of the machine that originated the log message.

##### `APP-NAME`

Identifies the application that originated the message. This value **MUST** be unique to the application and **MUST** contain the version of the application.

##### `PROCID`

Contains the process identifier of the application on the operating system of the machine that originated the log message.

#### Log Message

The message itself that contains free-form text that provides information about the event that is being logged.

All Messages sent and received by the application **MUST** be logged and **MUST** contain, at a minimum, the severity of the log message wrapped in square brackets (e.g. `[INFO]`) along with meaningful information relevant to the severity against which the log message is being generated.

## Non-Functional Requirements

### Messages

| Requirement  | Value  | Description                                                                                |
|--------------|--------|--------------------------------------------------------------------------------------------|
| Message Size | 1000KB | AWS Kinesis Firehose and AWS Kinesis Streams imposes a maximum size of 1000KB per message. |

### Message Channels

| Requirement  | Value                             | Description                                                                                                                                                                          |
|--------------|-----------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Time To Live | 168 hours                         | AWS Kinesis Stream has a maximum retention period of 168 hours, thereby giving our Message Channels a TTL of 168 hours.                                                              |
| Throughput   | Per shard: 1MB/s in and 4MB/s out | Each AWS Kinesis Stream can by default support up to 50 shards in US East, ES West and EU Ireland, and 25 shards in other regions. These values can be increased with justification. |

### Audit Logs & Invalid Message Channels

| Requirement   | Value                                                  | Description                                                                                                                                                       |
|---------------|--------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Account Limit | 20 per region                                          | A single AWS account can have a maximum of 20 AWS Kinesis Firehose delivery streams per region.                                                                   |
| Time To Live  | 24 hours with a retry of 0 to 7200 seconds             | Should the delivery destination be unavailable, AWS Kinesis Firehose will retain records for a maximum of 24 hours and can retry delivery from 0 to 7200 seconds. |
| Throughput    | 200 transactions/second, 5000 records/second and 5MB/s | These values can be increased with justification.                                                                                                                 |
| Buffer        | 1MB to 128MB and 60 to 900 seconds                     | Buffer sizes can range from 1MB to 128MB with intervals of 60 to 900 seconds.                                                                                     |
| Compression   | GZIP, ZIP and SNAPPY                                   | Data provided to the AWS Kinesis Firehose can be compressed using GZIP, ZIP and SNAPPY. However, the uncompressed size cannot exceeed 1000KB.                     |

### Log Messages

| Requirement    | Value      | Description                                                                                                                      |
|----------------|------------|----------------------------------------------------------------------------------------------------------------------------------|
| Minimum Length | 480 bytes  | The minimum length of a log message is 480 bytes as per RFC5424.                                                                 |
| Maximum Length | 2048 bytes | The recommended maximum length of a log message is 2048 bytes as per RFC5424, however implementations are free to increase this. |
