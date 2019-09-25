# sqlenide

A simple wrapper over JDBC. To use as is.

to build:

```
mvn install
```

Usage (example with AssertJ):

```
SqlDriver.initSQLConnection();

String sql="select count(*) from employee where last= ?";
assertThat(SqlDriver.countResults(sql,"Single")).isOne();

String sql="DELETE FROM ....= ?"
assertThat(SqlDriver.deleteLines(sql,"Luke")).isGreaterThan(1);
```
