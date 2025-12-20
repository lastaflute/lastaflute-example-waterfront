# LastaFlute Example Waterfront

[![Java CI with Maven](https://github.com/lastaflute/lastaflute-example-waterfront/actions/workflows/maven.yml/badge.svg)](https://github.com/lastaflute/lastaflute-example-waterfront/actions/workflows/maven.yml)

Example web application demonstrating **LastaFlute**, **ESFlute** (with Elasticsearch/OpenSearch), and **JSP** integration.

## Features

- Full-text search with Elasticsearch/OpenSearch via ESFlute
- Traditional RDBMS access via DBFlute
- User authentication and session management
- JSP-based view templates

## Supported Search Engines

- Elasticsearch 7.x, 8.x
- OpenSearch 1.x, 2.x, 3.x

## Requirements

- Java 21
- Maven 3.6+
- Docker (for running tests)
- Elasticsearch or OpenSearch (for development)

## Quick Start

### 1. Clone the repository

```bash
git clone https://github.com/lastaflute/lastaflute-example-waterfront.git
cd lastaflute-example-waterfront
```

### 2. Initialize the database

```bash
cd dbflute_maihamadb
mvn dbflute:replace-schema -Ddbflute.forceExecution=true
cd ..
```

### 3. Start Elasticsearch/OpenSearch

Start Elasticsearch 8.x or OpenSearch on port 9200:

```bash
# Using Docker (Elasticsearch 8.x)
docker run -d --name es -p 9200:9200 -e "discovery.type=single-node" -e "xpack.security.enabled=false" docker.elastic.co/elasticsearch/elasticsearch:8.0.0

# Or OpenSearch
docker run -d --name opensearch -p 9200:9200 -e "discovery.type=single-node" -e "plugins.security.disabled=true" opensearchproject/opensearch:2.0.0
```

### 4. Create the search index

```bash
curl -XPUT localhost:9200/maihama -H "Content-Type: application/json" -d @dbflute_maihamadb/playes/create-maihama.json
```

### 5. Build and run

```bash
mvn package -DskipTests
java -jar target/waterfront.war
```

### 6. Access the application

Open http://localhost:8099/waterfront

**Login credentials:**
- User: `Pixy`
- Password: `sea`

## Development

### Build

```bash
mvn package
```

### Run tests

Requires Docker running:

```bash
mvn test
```

### Code generation

Regenerate DBFlute/ESFlute code:

```bash
mvn dbflute:freegen
```

## Project Structure

```
src/main/java/org/docksidestage/
├── app/web/          # Actions, Forms (Web layer)
├── dbflute/          # DBFlute generated code (RDB)
├── esflute/maihama/  # ESFlute generated code (Elasticsearch)
└── mylasta/          # LastaFlute customization
```

See [CLAUDE.md](CLAUDE.md) for detailed development documentation.

## Related Projects

- [LastaFlute](https://github.com/lastaflute/lastaflute) - Web framework
- [DBFlute](https://github.com/dbflute/dbflute-core) - O/R mapper
- [Fesen HttpClient](https://github.com/codelibs/fesen-httpclient) - Elasticsearch/OpenSearch client

## License

Apache License 2.0

## Author

The DBFlute Project - http://dbflute.org/
