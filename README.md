# Kafka Producer Consumer AVRO

This is a sample Spring Boot application demonstrating the use of Apache Kafka with AVRO serialization for producing and consuming messages.

## Overview: What is Apache Avro?

Apache Avro is an open-source data serialization and data exchange framework developed by the Apache Software Foundation. It provides a compact, fast, and efficient way to serialize data in a binary format, making it ideal for big data processing and messaging systems like Apache Kafka.

Avro uses JSON for defining data schemas, which describe the structure of the data being serialized. These schemas are stored alongside the data, enabling schema evolution and ensuring compatibility between producers and consumers.

## Advantages of Apache Avro

1. **Schema Evolution**: Avro supports backward and forward compatibility, allowing schemas to evolve over time without breaking existing data or applications. This is crucial in distributed systems where different versions of producers and consumers may coexist.

2. **Compact Binary Format**: Avro serializes data into a compact binary format, which is more efficient in terms of storage and network bandwidth compared to text-based formats like JSON or XML.

3. **Language Agnostic**: Avro supports multiple programming languages, including Java, Python, C++, and more, making it suitable for polyglot environments.

4. **Performance**: Due to its binary nature and schema-based approach, Avro provides fast serialization and deserialization, which is beneficial for high-throughput systems like Kafka.

5. **Self-Describing Data**: Avro data includes the schema, making it self-describing and easier to process without prior knowledge of the data structure.

6. **Integration with Kafka**: Avro integrates seamlessly with Kafka through serializers and deserializers, enabling efficient message handling in event-driven architectures.

# Sample Kafka AVRO - Ice Cream Orders

A Spring Boot microservices project demonstrating end-to-end Apache Kafka integration with Apache AVRO for schema management and Confluent Schema Registry. The project showcases a producer-consumer architecture for processing ice cream orders.

## 📋 Project Overview

This is a multi-module Maven project that implements:
- **Ice Cream Orders Service**: REST API that creates orders and publishes them to Kafka
- **Ice Cream Orders Consumer**: Consumes order messages from Kafka
- **AVRO Schemas Module**: Centralized schema definitions for all domain objects

### Architecture Pattern

```
                 ┌─────────────────────┐
                 │   Client Request    │
                 └──────────┬──────────┘
                            │
                            ▼
            ┌─────────────────────────────────┐
            │  Ice Cream Orders Service       │
            │  (REST API on Port 8082)        │
            │  • Controller                   │
            │  • Service Layer                │
            │  • Producer (Kafka)             │
            └─────────────────────────────────┘
                           │
                           │ (AVRO Serialized Messages)
                           ▼
                    ┌──────────────┐
                    │    Kafka     │
                    │   Broker     │
                    │   (Port 9092)│
                    └──────────────┘
                          │
                          │ (AVRO Deserialized Messages)
                          ▼
            ┌─────────────────────────────────┐
            │  Ice Cream Orders Consumer      │
            │  • Consumer (Kafka)             │
            │  • Message Processing           │
            └─────────────────────────────────┘
                           │
                           ▼
            ┌──────────────────────────────────┐
            │  Schema Registry (Port 8081)     │
            │  • AVRO Schema Management        │
            │  • Schema Versioning             │
            └──────────────────────────────────┘
```

## 🏗️ Project Structure

```
sample-kafka-AVRO/
├── docker-compose.yaml               # Docker services (Zookeeper, Kafka, Schema Registry)
├── pom.xml                           # Parent POM with Maven configuration
├── icecream-orders-service/          # Producer Service Module
│   ├── src/main/java/
│   │   └── com/kodebytes/acasado/
│   │       ├── IceCreamOrdersServiceApplication.java
│   │       ├── controller/           # REST API endpoints
│   │       ├── service/              # Business logic
│   │       ├── producer/             # Kafka producer
│   │       ├── dto/                  # Data Transfer Objects
│   │       └── exceptionHandler/     # Global error handling
│   ├── src/main/resources/
│   │    └── application.yml           # Service configuration
│   └── pom.xml
├── icecream-orders-consumer/         # Consumer Service Module
│   ├── src/main/java/
│   │   └── com/kodebytes/acasado/
│   │       ├── IceCreamOrdersConsumerApplication.java
│   │       └── consumer/             # Kafka consumer
│   ├── src/main/resources/
│   │    └── application.yml           # Consumer configuration
│   └── pom.xml
└── schemas/                          # AVRO Schema Module
    ├── src/main/avro/
    │     ├── IceCreamOrder.avsc        # Main order schema
    │     ├── OrderLineItem.avsc
    │     ├── Shop.avsc
    │     ├── Address.avsc
    │     ├── OrderId.avsc
    │     └── IceCreamUpdateEvent.avsc
    └── pom.xml
```

## 🚀 Getting Started

### Prerequisites

- **Java 21** or higher
- **Maven 3.8.0** or higher
- **Docker & Docker Compose** (for Kafka infrastructure)

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd sample-kafka-AVRO
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **NEW Start infrastructure (KRaft, Schema Registry, AVRO, Kafdrop)**

   ```bash
   docker-compose-Kraft up -d
   ```
   This will start:
   - **Kafka Broker** (Port 9092)
   - **Confluent Schema Registry** (Port 8081)
   - **Kafdrp** (Port 9000)
4. **Start infrastructure (Kafka, Zookeeper, Schema Registry AVRO, Kafdrop)**
   ```bash
   docker-compose up -d
   ```
   This will start:
    - **Zookeeper** (Port 2181)
    - **Kafka Broker** (Port 9092)
    - **Confluent Schema Registry** (Port 8081)
    - **Kafdrp** (Port 9000)

4. **Run the Ice Cream Orders Service**
   ```bash
   cd icecream-orders-service
   mvn spring-boot:run
   ```
   Service runs on `http://localhost:8082`

5. **In another terminal, run the Ice Cream Orders Consumer**
   ```bash
   cd icecream-orders-consumer
   mvn spring-boot:run
   ```

## 📊 API Endpoints

### Create Ice Cream Order
Create a new ice cream order which will be published to Kafka.

**Request:**
```http
POST /v1/ice_cream_orders HTTP/1.1
Host: localhost:8082
Content-Type: application/json

{
  "id": 164,
  "name": "Antonio Casado",
  "nickName": "Toni",
  "shop": {
    "shopId": 12,
    "shopName": "Giovanni's Ice Cream",
    "address": {
      "addressLine1": "1234 Address Line 1",
      "city": "Zurich",
      "country": "Switzerland",
      "zip": "8044"
    }
  },
  "orderLineItems": [
    {
      "recipient": "CONE",
      "flavor": "VANILLA",
      "size": "MEDIUM",
      "quantity": 1,
      "cost": 3.99
    }
  ],
  "pickUp":"IN_STORE",
  "status":"NEW"
}
```

**Response:**
```json
{
   "id": "e39c896e-700d-4698-a58b-d3fed1a410e0",
   "name": "Antonio Casado",
   "nickName": "Toni",
   "shop": {
      "shopId": 12,
      "shopName": "Giovanni's Ice Cream",
      "address": {
         "addressLine1": "1234 Address Line 1",
         "city": "Zurich",
         "country": "Switzerland",
         "zip": "8044"
      }
   },
   "orderLineItems": [
      {
         "recipient": "CONE",
         "flavor": "VANILLA",
         "size": "MEDIUM",
         "quantity": 1,
         "cost": 3.99
      }
   ],
   "pickUp": "IN_STORE",
   "status": "NEW"
}
```

**Status Code:** `201 Created`

## 🔧 Technology Stack

### Core Framework
- **Spring Boot 4.1.0** - Application framework
- **Spring Kafka** - Kafka integration

### Serialization & Schema Management
- **Apache AVRO 1.12.1** - Serialization format
- **Confluent Kafka AVRO Serializer 7.6.0** - Schema Registry integration
- **Maven AVRO Plugin 1.12.1** - Code generation from AVRO schemas

### Infrastructure
- **Apache Kafka 7.5.0** (Confluent)
- **Apache Zookeeper 7.5.0** (Confluent)
- **Confluent Schema Registry 7.5.0**

### Utilities
- **Lombok** - Reduce boilerplate code
- **Jakarta Validation** - Bean validation

### Build
- **Maven** - Build and dependency management
- **Java 21** - Target runtime

## 🗂️ Key Modules

### Schemas Module
Centralized AVRO schema definitions that are compiled to Java classes using the Maven AVRO plugin.

**Key Schemas:**
- `IceCreamOrder.avsc` - Main order entity
- `OrderLineItem.avsc` - Order line items
- `Shop.avsc` - Shop information
- `Address.avsc` - Address details
- `OrderId.avsc` - Order identifier
- `IceCreamUpdateEvent.avsc` - Order update events

**Generated Classes Location:**
```
schemas/src/main/java/com/kodebytes/acasado/domain/generated/
```

These JAR is consumed by both service and consumer modules.

### Ice Cream Orders Service
**Responsibilities:**
- Expose REST API for creating orders
- Convert DTOs to AVRO-generated domain objects
- Publish orders to Kafka topic `ice-cream-orders`
- Handle validation and error responses

**Key Components:**
- `IceCreamOrderController` - REST endpoints
- `IceCreamOrderService` - Business logic
- `IceCreamOrderProducer` - Kafka producer
- DTOs - Request/response models

### Ice Cream Orders Consumer
**Responsibilities:**
- Listen to `ice-cream-orders` Kafka topic
- Deserialize AVRO messages
- Process order messages (logging, business logic, etc.)

**Key Components:**
- `IceCreamOrdersConsumer` - Kafka consumer with @KafkaListener

## ⚙️ Configuration

### Service Configuration (`icecream-orders-service/application.yml`)
```yaml
server:
   port: 8082
   servlet:
      session:
         timeout: 30m

spring:
   application:
      name: icecream-orders-service
   profiles:
      active: local

---
spring:
   config:
      activate:
         on-profile: local
   kafka:
      topic: ice-cream-orders
      template:
         default-topic: ice-cream-orders
      producer:
         bootstrap-servers: localhost:9092
         key-serializer: org.apache.kafka.common.serialization.StringSerializer
         value-serializer: io.confluent.kafka.serializers.KafkaAvroSerializer
      properties:
         schema.registry.url : http://localhost:8081
         value:
            subject:
               name:
                  strategy: io.confluent.kafka.serializers.subject.RecordNameStrategy
```

### Consumer Configuration (`icecream-orders-consumer/application.yml`)
```yaml
server:
   port: 8083

spring:
   application:
      name: icecream-orders-consumer
   profiles:
      active: local
---
spring:
   config:
      activate:
         on-profile: local
   kafka:
      topic: ice-cream-orders
      template:
         default-topic: ice-cream-orders
      consumer:
         bootstrap-servers: localhost:9092
         key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
         value-deserializer: io.confluent.kafka.serializers.KafkaAvroDeserializer
         group-id: icecream-orders-listener-group
         auto-offset-reset: earliest
      properties:
         schema.registry.url : http://localhost:8081
         specific.avro.reader: true
         value:
            subject:
               name:
                  strategy: io.confluent.kafka.serializers.subject.RecordNameStrategy
```

## 📝 AVRO Schema Example

**IceCreamOrder.avsc:**
```json
{
  "name": "IceCreamOrder",
  "namespace": "com.kodebytes.acasado.domain.generated",
  "type": "record",
  "fields": [
    { "name": "id", "type": "OrderId" },
    { "name": "name", "type": "string" },
    { "name": "nickName", "type": "string", "default": "" },
    { "name": "shop", "type": "Shop" },
    { "name": "orderLineItems", "type": { "type": "array", "items": "OrderLineItem" } },
    { "name": "ordered_time", "type": { "type": "long", "logicalType": "timestamp-millis" } },
    {
      "name": "pick_up",
      "type": {
        "type": "enum",
        "name": "PickUp",
        "symbols": ["IN_STORE", "CURBSIDE"]
      }
    },
    { "name": "status", "type": "string", "default": "NEW" }
  ]
}
```

## 🔄 Message Flow

1. **Order Creation**
    - Client submits POST request to `/v1/ice_cream_orders` with order details
    - Service validates request using Jakarta Validation
    - Service converts DTO to AVRO-generated `IceCreamOrder` object

2. **Publishing to Kafka**
    - `IceCreamOrderProducer` sends the AVRO object to topic `ice-cream-orders`
    - KafkaAvroSerializer serializes the object using the AVRO schema
    - Schema Registry validates the schema and stores if new

3. **Consuming from Kafka**
    - `IceCreamOrdersConsumer` listens to `ice-cream-orders` topic
    - KafkaAvroDeserializer deserializes bytes using Schema Registry
    - Consumer processes the message (logging, business logic, etc.)

## 🧪 Testing

Run tests for all modules:
```bash
mvn test
```

Run tests for specific module:
```bash
cd icecream-orders-service
mvn test
```

## 📚 Key Concepts

### Apache AVRO
A data serialization format used for:
- **Compact binary format** - Efficient network transmission
- **Schema evolution** - Handle schema changes safely
- **Language-neutral** - Generate code in multiple languages

### Schema Registry
Confluent component that:
- **Manages AVRO schemas** - Central repository for schemas
- **Schema versioning** - Track schema evolution
- **Client integration** - Serializers/deserializers use registry for validation

### Subject Name Strategy
This project uses `RecordNameStrategy` which:
- Names subjects after the AVRO record name (e.g., `com.kodebytes.acasado.domain.generated.IceCreamOrder`)
- Allows multiple record types in one topic
- Each record type gets its own schema version

### Kafka Consumer Groups
- Consumer group: `icecream-orders-listener-group`
- Allows multiple consumers to process messages in parallel
- Tracks offsets for scalability

## 🐳 Docker Services

### Start Services
```bash
docker-compose up -d
```

### Stop Services
```bash
docker-compose down
```

### View Logs
```bash
docker-compose logs -f
```

### Access Schema Registry UI
```
http://localhost:8081
```

## 🔍 Monitoring & Debugging

### View Kafka Topics
```bash
docker exec broker_avro kafka-topics --list --bootstrap-server localhost:9092
```

### Consume Messages (Avro format)
```bash
docker exec broker_avro kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic ice-cream-orders \
  --from-beginning \
  --property print.key=true
```

### Check Schema Registry
```bash
curl http://localhost:8081/subjects
curl http://localhost:8081/subjects/com.kodebytes.acasado.domain.generated.IceCreamOrder-value/versions
```

## 📋 Dependencies & Versions

| Component | Version |
|-----------|---------|
| Spring Boot | 4.0.6 |
| Spring Kafka | Latest (Boot) |
| Apache AVRO | 1.12.1 |
| Confluent AVRO Serializer | 8.2.0 |
| Java | 21 |
| Kafka (Confluent) | 7.5.0 |
| Zookeeper (Confluent) | 7.5.0 |
| Schema Registry | 7.5.0 |

## 🤝 Contributing

1. Follow existing code structure and naming conventions
2. Add unit tests for new features
3. Update AVRO schemas in `schemas/src/main/avro/`
4. Rebuild schemas: `mvn clean install -pl schemas`
5. Update this README with new features/changes

## 📄 License

This project is provided as-is for educational and reference purposes.

## 🔗 Resources

- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [Apache AVRO Documentation](https://avro.apache.org/)
- [Confluent Schema Registry](https://docs.confluent.io/platform/current/schema-registry/)
- [Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
- [Confluent Platform](https://www.confluent.io/)

## 📞 Support

For issues, questions, or suggestions:
1. Check existing documentation
2. Review the code comments
3. Create an issue with detailed description

---

**Last Updated:** May 2026  
**Project Version:** 1.0  
**Group ID:** `com.kodebytes.acasado`

