package de.malte.coinapi.utils;

import java.sql.*;

public class MySQL {
    private Connection connection;

    public void createTables() {
        update("CREATE TABLE IF NOT EXIST coins(uuid VARCHAR(32), amount DOUBLE(40,2))");
    }

    public void setupDataSource(String address, int port, String database, String username, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + address + ":" + port + "/" + database, username, password);
            if (!connection.isValid(1))
                throw new RuntimeException("Can't establish Connection to Database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String qry, Object... objs) {
        try {
            PreparedStatement p = connection.prepareStatement(qry);
            setArgs(objs, p);
            p.execute();
            p.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry, Object... objs) {
        try {
            PreparedStatement p = connection.prepareStatement(qry);
            setArgs(objs, p);
            return p.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setArgs(Object[] objs, PreparedStatement p) throws SQLException {
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (obj instanceof String) {
                p.setString(i + 1, (String) obj);
            } else if (obj instanceof Integer) {
                p.setInt(i + 1, (Integer) obj);
            } else if (obj instanceof Date) {
                p.setDate(i + 1, (Date) obj);
            } else if (obj instanceof Timestamp) {
                p.setTimestamp(i + 1, (Timestamp) obj);
            } else if (obj instanceof Boolean) {
                p.setBoolean(i + 1, (Boolean) obj);
            } else if (obj instanceof Float) {
                p.setFloat(i + 1, (Float) obj);
            } else if (obj instanceof Double) {
                p.setDouble(i + 1, (Double) obj);
            } else if (obj instanceof Long) {
                p.setLong(i + 1, (Long) obj);
            }
        }
    }
}
