package com.bobocode.datasource;

import com.bobocode.connection.ConnectionProxy;
import lombok.SneakyThrows;
import org.postgresql.ds.PGSimpleDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Shelupets Denys on 30.11.2021.
 */
public class PoolConnectionDataSource extends PGSimpleDataSource {
    private static final int MAX_TIMEOUT = 5000;
    private final Queue<Connection> connectionsQueue = new ConcurrentLinkedQueue<>();
    private final int INITIAL_CAPACITY_POOL_CONNECTIONS = 10;

    public PoolConnectionDataSource(String url, String user, String password) {
        super();
        setUrl(url);
        setUser(user);
        setPassword(password);
        initializeConnections();
    }


    private void initializeConnections() {
        try {
            for (int i = 0; i < INITIAL_CAPACITY_POOL_CONNECTIONS; i++) {
                connectionsQueue.add(new ConnectionProxy(super.getConnection(), connectionsQueue));
            }
        } catch (Exception e) {
            throw new RuntimeException("Initialize connection failed", e);
        }
    }

    @SneakyThrows
    @Override
    public Connection getConnection() {
        if (connectionsQueue.size() == 0) {
            throw new RuntimeException("Maximum pool size reached, no available connections!");
        }

        Connection connection = connectionsQueue.poll();

        try {
            if (!connection.isValid(MAX_TIMEOUT)) {
                connection = new ConnectionProxy(super.getConnection(), connectionsQueue);
            }
        } catch (SQLException e) {
            throw new RuntimeException("getConnection failed", e);
        }

        return connection;
    }

}
