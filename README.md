# qqq-app-starter

A small QQQ 4.0 application for learning: one MySQL table, application navigation, and the Material Dashboard 0.40.0. Requires **Java 21**, **Maven 3.8+**, and MySQL. Dependencies come from Maven Central.

## Database and configuration

Create a dedicated development database and user, then run this SQL in that database:

```sql
CREATE TABLE sample (
   id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(255)
);
INSERT INTO sample (name) VALUES ('First example');
```

Create a `.env` file in the project directory, using your database connection settings:

```properties
RDBMS_VENDOR=mysql
RDBMS_HOSTNAME=localhost
RDBMS_PORT=3306
RDBMS_DATABASE_NAME=qqq_starter
RDBMS_USERNAME=qqq_starter
RDBMS_PASSWORD=your_local_database_password
```

QQQ reads this file automatically when launched from that directory. Process environment variables also work. The `.env` file is ignored by Git.

## Build and run

```bash
mvn clean verify
java -jar target/qqq-app-starter-0.1-SNAPSHOT.jar
```

Open <http://localhost:8000/>, select **Sample App**, and open **Sample Table**. View the seeded row, create a row, and edit its name. The starter uses fully anonymous authentication for local learning; configure authentication before using real application data.

## Customize

Edit [StarterAppMetaDataProvider](src/main/java/com/kingsrook/qqq/starterapp/StarterAppMetaDataProvider.java) to define tables, backends, branding, and navigation. Register new tables in `defineQInstance()`, point them at `rdbmsBackend`, and use `RDBMSTableBackendDetails` for the actual SQL table name. Keep the metadata fields consistent with your database schema.

[StarterAppJavalinServer](src/main/java/com/kingsrook/qqq/starterapp/StarterAppJavalinServer.java) starts the HTTP server and scheduler. The bundled `StarterAppCli` is an empty placeholder, not a runnable CLI.

For migration changes, see the [QQQ 4.0 migration guide](https://github.com/QRun-IO/qqq/blob/main/docs/migration/4.0.adoc). The separate [new-qqq-application-template](https://github.com/QRun-IO/new-qqq-application-template) is a production scaffolding project outside this release train.

## Troubleshooting

- **Database connection refused:** verify MySQL is listening and check the hostname/port in `.env`.
- **Missing table:** run the SQL above in the database named by `RDBMS_DATABASE_NAME`.
- **Missing environment variables:** launch from the directory containing `.env`, or export those variables explicitly.
- **Wrong Java version:** both `java -version` and `mvn -version` must report Java 21 or later.

## License

See [LICENSE](LICENSE) and [NOTICE](NOTICE).
