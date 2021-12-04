package com.bobocode;

import com.bobocode.datasource.PoolConnectionDataSource;
import com.bobocode.model.Person;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Shelupets Denys on 30.11.2021.
 */
public class MainApp {
    public static void main(String[] args) {
        var poolConnectionDataSource = new PoolConnectionDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        var dataSource = getDataSource("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        System.out.println("Time execute of create 500 connections by custom connection pool = " + dataSourceTimeExecution(poolConnectionDataSource));
        System.out.println("Time execute of create 500 connections by DataSource implementation = " + dataSourceTimeExecution(dataSource));
    }

    private static Long dataSourceTimeExecution(DataSource dataSource) {
        var start = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            Connection connection = null;
            try {
                connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM persons");
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Person.builder()
                            .id(resultSet.getLong("id"))
                            .firstName(resultSet.getString("first_name"))
                            .lastName(resultSet.getString("last_name"))
                            .email(resultSet.getString("email"))
                            .address(resultSet.getString("address"))
                            .build();
                }

            } catch (SQLException ignore) {

            } finally {
                try {
                    assert connection != null;
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        var finish = System.currentTimeMillis();
        return finish - start;


    }

    private static DataSource getDataSource(String url, String user, String password) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setURL(url);
        pgSimpleDataSource.setPassword(password);
        pgSimpleDataSource.setUser(user);
        return pgSimpleDataSource;
    }
}
