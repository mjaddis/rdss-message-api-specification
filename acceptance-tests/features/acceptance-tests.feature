Feature: RDSS Message API 

Scenario: verify message structure with required fields
  Given User post create metadata message  
  When the message is send to the RDSS message channel
  Then I validate message contains 2 parts message header and message body
  
Scenario: verify maximum size of message must not exceed 1000KB
  Given User post create metadata message  
  When the message is send to the RDSS message channel 
  Then I validate that message size does not exceed 1000kb
  
  
Scenario outline: verify Mesage Headers with datatypes
  Given User post create metadata message  
  When the message is send to the RDSS message channel 
  Then I validate <Message Header> with <Multiplicity> and <datatype>
  
   Examples:
    | Message Header     | Multiplicity | datatype       | 
    | messageId          | 1            | String         | 
    | correlationId      | 0..1         | String         | 
    | messageClass       | 1            | MessageClass   |
    | messageType        | 1            | MessageType    |
    | returnAddress      | 0..1         | String         |
    | messageTimings     | 1            | MessageTimings |
    | publishedTimestamp | 1  	        | Timestamp      |
    | expirationTimestamp| 0..1  	| Timestamp      |
    | messageSequence    | 1  	        | MessageSequence|
    | sequence           | 1     	| String         |
    | position           | 1       	| Integer        |
    | total              | 1  	        | Integer        |
    | messageHistory     | 0..*     	| MessageHistory |
    | machineId          | 1            | String         |
    | machineAddress     | 1            | String         |
    | timestamp          | 1            | Timestamp      |
	
Scenario outline: verify message body research_object schema
  Given User post create metadata message  
  When the message is send to the RDSS message channel 
  Then I validate <required fields> are available
  
  Examples:
    | required fields   |                                         
    | objectUuid        |
    | objectTitle       |
    | objectContributor |
    | objectDescription |
    | objectRights      |
    | objectDate        |
    | objectResourceType|
  
Scenario: verify Vocabulary Read message
  Given User sends a message type vocabularyRead
  When the JSON payload for Vocabulary is generated
  Then I validate request and response json
  
 Scenario: verify Error code for message body missing required fields
  Given User post create metadata message with missing required fields
  When the message is send to the RDSS message channel  
  Then I validate that error code generated as "GENERR01"
  
