# DataSource vs DriverManager

## Overview

Both `DriverManager` and `DataSource` are used to obtain JDBC `Connection` objects.

According to the Java JDBC documentation, `DataSource` is the preferred way to obtain connections, while `DriverManager` is the older basic service for managing JDBC drivers.

## DriverManager

`DriverManager` is a class from `java.sql` that manages registered JDBC drivers and returns a `Connection` based on a JDBC URL, username, and password.

### Advantages
- Very simple to start with
- Good for small projects, demos, and learning JDBC
- No extra configuration object is required
- Easy to use in basic console applications

### Disadvantages
- Harder to configure cleanly in larger applications
- Credentials and URL are often passed around directly
- Does not naturally support connection pooling
- Less flexible for enterprise-style configuration

### Example
```java
Connection connection = DriverManager.getConnection(url, username, password);