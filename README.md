# qqq-app-starter

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Example QQQ application for learning and experimentation.

**For:** Developers learning QQQ who want a working example to explore  
**Status:** Stable

## Why This Exists

Learning a framework is easier with a running example. This starter app connects to a MySQL database and exposes a table through the QQQ dashboard. Use it to understand how QQQ metadata, entities, and the dashboard work together.

For new production projects, use [new-qqq-application-template](https://github.com/QRun-IO/new-qqq-application-template) instead.

## What's Included

- **MetaDataProvider** - Example metadata configuration
- **Javalin Server** - HTTP server setup
- **Dashboard** - Material Dashboard at `/`
- **Sample Entity** - Configurable table definition

## Quick Start

### Prerequisites

- Java 17+
- Maven 3.8+
- MySQL database
- GitHub token for Maven registry access

### Maven Settings

Add to `~/.m2/settings.xml`:

```xml
<settings>
  <servers>
    <server>
      <id>github-qqq-maven-registry</id>
      <username>YOUR-GITHUB-USERNAME</username>
      <password>YOUR-GITHUB-TOKEN</password>
    </server>
  </servers>
</settings>
```

Generate a token at: GitHub > Settings > Developer Settings > Personal access tokens > Tokens (classic) with `read:packages` scope.

### Configuration

Create `.env` file:

```properties
RDBMS_VENDOR=mysql
RDBMS_HOSTNAME=localhost
RDBMS_PORT=3306
RDBMS_DATABASE_NAME=your_database
RDBMS_USERNAME=your_user
RDBMS_PASSWORD=your_password
```

### Run

```bash
# Build
mvn package

# Run
java -jar target/qqq-app-starter-0.1-SNAPSHOT.jar
```

Open `http://localhost:8000/`

## Usage

### Configure Your Table

Edit `StarterAppMetaDataProvider.java`:

```java
private QTableMetaData defineSampleTable() {
    return new QTableMetaData()
        .withName("your_table")
        .withBackendName("rdbms")
        .withPrimaryKeyField("id")
        .withField(new QFieldMetaData("id", QFieldType.INTEGER))
        .withField(new QFieldMetaData("name", QFieldType.STRING));
}
```

### Add More Tables

1. Create additional table definitions in the MetaDataProvider
2. Register them in the `defineInstance()` method
3. Restart the server

### Running in Docker

```dockerfile
FROM openjdk:17-alpine
EXPOSE 8000
ADD target/qqq-app-starter-0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
```

```bash
docker build -t qqq-app-starter .
docker run -p 8000:8000 --env-file .env qqq-app-starter
```

## Project Structure

| File | Purpose |
|------|---------|
| `StarterAppMetaDataProvider.java` | Defines tables, backends, apps |
| `StarterAppJavalinServer.java` | Starts HTTP server |
| `.env` | Database connection settings |

## Common Issues

**401 from Maven**: GitHub token missing or invalid. Check `~/.m2/settings.xml`.

**NullPointerException in defineSampleApp**: Table name mismatch between definition and reference.

**Connection refused**: Check database hostname, port, and credentials in `.env`.

## Project Status

Stable example project. For production apps, use [new-qqq-application-template](https://github.com/QRun-IO/new-qqq-application-template).

## Contributing

1. Fork the repository
2. Create a feature branch
3. Submit a pull request

## License

Apache-2.0 - See [LICENSE](LICENSE) for details.
