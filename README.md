# LogiTrack Pro – Multi-threaded Ingestion Server

## Overview

LogiTrack Pro is a backend system for tracking high-value electronics in warehouses.
It simulates real-time sensor data such as GPS location, humidity, and tamper alerts, and processes it using a multi-threaded ingestion pipeline.

The system uses:

* Java (JDBC)
* PostgreSQL (Docker)
* LinkedBlockingQueue for buffering
* Producer–Consumer pattern for concurrency


## Prerequisites

Ensure the following are installed:

* Java 17 or above
* Maven
* Docker and Docker Compose
* psql (optional, for database inspection)


## Project Structure

```
Logitrack/
├── docker-compose.yml
├── .env
├── db/
│   ├── init/        # schema.sql, seed.sql
│   └── queries/     # SQL reference files
├── src/
│   ├── main/java/com/logitrack/
│   └── test/java/com/logitrack/
└── pom.xml
```


## How to Run the Project

### Step 1: Start Database

Run the following command from the project root:

```bash
docker compose up -d
```

### Step 2: Verify Database is Running

```bash
docker ps
```

Optional: connect to the database

```bash
psql -h localhost -p 5430 -U myuser -d logitrack
```

Password:

```
mypassword
```



### Step 3: Build the Project

```bash
mvn clean compile
```



### Step 4: Run Tests and Generate Coverage

```bash
mvn clean verify
```

Coverage report will be available at:

```
target/site/jacoco/index.html
```



### Step 5: Run the Application

```bash
mvn exec:java -Dexec.mainClass="com.logitrack.App"
```



## Application Behavior

* Producer threads generate sensor readings continuously
* Data is pushed into a LinkedBlockingQueue
* Consumer threads process the data:

    * Insert into sensor_reading table
    * Generate alerts for:

        * High humidity
        * Tamper detection



## Verifying Data

Open another terminal and run:

```bash
psql -h localhost -p 5430 -U myuser -d logitrack
```

Then execute:

```sql
SELECT * FROM sensor_reading ORDER BY recorded_at DESC;
```

New records should appear continuously.



## Stopping the Application

Stop the running application:

```bash
CTRL + C
```

Stop the database:

```bash
docker compose down
```

Reset database:

```bash
docker compose down -v
docker compose up -d
```



## Key Concepts

* JDBC abstraction using JdbcTemplate
* PreparedStatement usage
* Connection management (DriverManager and DataSource)
* Multi-threading using Producer–Consumer pattern
* BlockingQueue for buffering
* DAO and Service layer design
* Exception handling
* Code coverage using JaCoCo



## Notes

* The system simulates sensor data; no external hardware is required
* Seed data is optional since the application generates data dynamically
* Ensure the configured port is not already in use before starting Docker



## Author

Nishitha Deverakonda
Java Fullstack Developer Intern
