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

class DeleteLines {

    @BeforeAll
    static void init() throws SQLException{
        SqlDriver.initSQLConnection();
        RunScript.execute(SqlDriver.con,new BufferedReader(new InputStreamReader(
                Thread.currentThread().getContextClassLoader().getResourceAsStream("delete_example.sql"))));
    }

    @Test
    void shouldReturnZeroIfNothingDeleted() throws SQLException {
        String sql="delete from employee_delete where last= ?";
        assertThat(SqlDriver.deleteLines(sql,"qwerty")).isZero();
    }

    @Test
    void shouldReturnOneIfOneDeleted() throws SQLException {
        String sql="delete from employee_delete where last= ?";
        assertThat(SqlDriver.deleteLines(sql,"Single")).isOne();
    }

    @Test
    void shouldReturnCountIfMoreThanOneDeleted() throws SQLException {
        String sql="delete from employee_delete where first = ?";
        assertThat(SqlDriver.deleteLines(sql,"Luke")).isGreaterThan(1);
    }

    @Test
    void shouldReturnIfParameterInteger() throws SQLException {
        String sql="delete from employee_delete where age= ?";
        assertThat(SqlDriver.deleteLines(sql,99)).isZero();
    }

    @Test
    void shouldReturnIfParameterBigDecimal() throws SQLException {
        String sql="delete from employee_delete where age>= ?";
        assertThat(SqlDriver.deleteLines(sql, BigDecimal.valueOf(99.1))).isZero();
    }

    @Test
    void shouldThrowExceptionWhenUnsupportedParameter(){
        String sql="delete from employee where age= ?";
        assertThatExceptionOfType(SQLException.class).isThrownBy(
                ()->  SqlDriver.deleteLines(sql,45.5)
        );
    }

}