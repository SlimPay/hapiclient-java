# HAPI Client

An HTTP Client implementing the [HAL specification](https://tools.ietf.org/html/draft-kelly-json-hal-07).

# Requirements

Java 5 or higher

# Installation

Maven dependency:
<dependency>
	<groupId>com.slimpay</groupId>
	<artifactId>hapiclient</artifactId>
	<version>1.0.0</version>
</dependency>

For Java 5 to 7 included, you also need to add:
<dependency>
	<groupId>org.glassfish</groupId>
	<artifactId>javax.json</artifactId>
	<version>1.0.4</version>
	<optional>true</optional>
</dependency>

# Use

Examples and full working snippets are available in the [HAPI Crawler](http://www.slimpay.net/rest-hapi-crawler/).