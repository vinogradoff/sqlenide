package de.vinogradoff.sql;

import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.*;

class InitConnection {

    @Test
    void connectionShouldBeEstablished() throws SQLException {
        SqlDriver.initSQLConnection();
        assertThat(SqlDriver.con.isClosed()).isFalse();
    }

    @Test
    void badSettingShouldThrowException(){
        assertThatExceptionOfType(SQLException.class).isThrownBy(
                () -> SqlDriver.initSQLConnection("abcdef","sa","")
        );
    }
}
