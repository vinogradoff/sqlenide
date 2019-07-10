package de.vinogradoff.sql;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CountResults {

    @BeforeAll
    static void init() throws SQLException{
        SqlDriver.initSQLConnection();
        RunScript.execute(SqlDriver.con,new BufferedReader(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("base.sql"))));
    }

    @Test
    void shouldReturnZeroIfNothingFound() throws SQLException {
        String sql="select count(*) from employee where last= ?";
        assertThat(SqlDriver.countResults(sql,"qwerty")).isZero();
    }

    @Test
    void shouldReturnOneIfOneFound() throws SQLException {
        String sql="select count(*) from employee where last= ?";
        assertThat(SqlDriver.countResults(sql,"Single")).isOne();
    }

    @Test
    void shouldReturnCountIfMoreThanOneFound() throws SQLException {
        String sql="select count(*) from employee";
        assertThat(SqlDriver.countResults(sql)).isGreaterThan(1);
    }

    @Test
    void shouldThrowExceptionWhenNoResults(){
        String sql="select * from employee where last= ?";
        assertThatExceptionOfType(SQLException.class).isThrownBy(
                ()->  SqlDriver.countResults(sql,"qwerty")
        );
    }

    @Test
    void shouldReturnIfParameterInteger() throws SQLException {
        String sql="select count(*) from employee where age= ?";
        assertThat(SqlDriver.countResults(sql,40)).isOne();
    }

    @Test
    void shouldReturnIfParameterBigDecimal() throws SQLException {
        String sql="select count(*) from employee where age>= ?";
        assertThat(SqlDriver.countResults(sql, BigDecimal.valueOf(44.9))).isOne();
    }

    @Test
    void shouldThrowExceptionWhenUnsupportedParameter(){
        String sql="select * from employee where last= ?";
        assertThatExceptionOfType(SQLException.class).isThrownBy(
                ()->  SqlDriver.countResults(sql,45.5)
        );
    }

}