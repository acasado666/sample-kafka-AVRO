# KafkaProducerConsumerAVRO

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

## 📁  Project Structure

- `src/main/java/com/kodebytes/acasado/`: Main application code
  - `KafkaProducerConsumerAvroApplication.java`: Main Spring Boot application class
  - `avro/User.java`: Generated Avro class for the User schema
  - `domain/kafka/`: Kafka producer and consumer services
  - `infraestructure/`: REST controllers and exception handling
- `src/main/resources/avro/user.avsc`: Avro schema definition for User
- `src/main/resources/application.yaml`: Application configuration

## ✈️ Running the Application

1. Ensure you have Java 15 and Gradle installed.
2. Start a Kafka broker and Schema Registry (e.g., using Confluent Platform).
3. Configure the Kafka and Schema Registry URLs in `application.yaml`.
4. Run the application: `./gradlew bootRun`

## 📋 Dependencies

- Spring Boot
- Spring Kafka
- Confluent Kafka Avro Serializer
- Apache Avro
- Lombok

---

## 📄 License

This project is for educational purposes.