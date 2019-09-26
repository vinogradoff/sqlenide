# sqlenide

A simple wrapper over JDBC. To use as is.

to build:

```
mvn install
```

Usage (example with AssertJ):

Setup the following system properties for your database.

```
 sql-driver.url=jdbc:h2:mem:test
 sql-driver.user=sa
 sql-driver.password=
 ```

Functions implemented in SqlDriver:

- countResults()
- getSingleValue()
- getListOfValues()
- deleteLines()


```
SqlDriver.initSQLConnection();

String sql="select count(*) from employee where last= ?";
assertThat(SqlDriver.countResults(sql,"Single")).isOne();

String sql="delete from employee where first= ?"
assertThat(SqlDriver.deleteLines(sql,"Luke")).isGreaterThan(1);
```
