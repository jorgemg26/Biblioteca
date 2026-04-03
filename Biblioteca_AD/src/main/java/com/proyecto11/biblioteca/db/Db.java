package com.proyecto11.biblioteca.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {
    private static final Properties PROPS = new Properties();
    static {
        try (InputStream inStream = Db.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inStream == null) {
                throw new RuntimeException("No se encuentra db.properties en src/main/resources");
            }
            PROPS.load(inStream);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando db.properties", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                PROPS.getProperty("db.url"),
                PROPS.getProperty("db.user"),
                PROPS.getProperty("db.password")
        );
    }
}
