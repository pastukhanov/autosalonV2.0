package com.ppe.app.autosalon.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Database {
    private Connection conn;
    private Statement stmt;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Ошибка загрузки драйвера SQLite JDBC.", e);
        }
    }

    public Database() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:D:\\javaCode\\AutoSalon\\db\\auto_salon.db");
            stmt = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    public void insert(String table, Map<String, Object> columnValues) throws SQLException {
        String columns = String.join(", ", columnValues.keySet());
        String placeholders = String.join(", ", Collections.nCopies(columnValues.size(), "?"));
        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES (" + placeholders + ")";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columnValues.values()) {
                pstmt.setObject(index++, value);
            }
            pstmt.executeUpdate();
        }
    }

    public void update(String table, Map<String, Object> columnValues, Long id) throws SQLException {
        String sql = "UPDATE " + table + " SET ";
        List<String> sets = new ArrayList<>();

        for (String column : columnValues.keySet()) {
            sets.add(column + " = ?");
        }

        sql += String.join(", ", sets);
        sql += " WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            int index = 1;
            for (Object value : columnValues.values()) {
                pstmt.setObject(index++, value);
            }
            pstmt.setLong(index, id);
            pstmt.executeUpdate();
        }
    }

    public ResultSet fetchAll(String table, String[] columns) throws SQLException {
        String columnsJoined = String.join(", ", columns);
        String sql = "SELECT " + columnsJoined + " FROM " + table;
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println(rs.next());
            return rs;
        }
    }

    public List<Map<String, Object>> fetchAllInList(String table) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        String sql = "SELECT * FROM " + table;
        try (
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            records = convertRsToMap(rs);
        } finally {
            return records;
        }
    }

    private List<Map<String, Object>> convertRsToMap(ResultSet rs) throws SQLException {
        List<Map<String, Object>> records = new ArrayList<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                row.put(metaData.getColumnName(i), rs.getObject(i));
            }
            records.add(row);
        }

        return records;
    }

    public void delete(String table, Long rowId) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, rowId);
            pstmt.executeUpdate();
        }
    }

    public Statement getStmt() {
        return stmt;
    }

    public void initDb() throws SQLException {
        try (ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='car'")) {
            if (rs.next()) {
                return;
            }
        }
        String sql = null;
        try {
            sql = new String(Files.readAllBytes(Paths.get("D:\\javaCode\\AutoSalon\\sql\\createdb.sql")));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read SQL file", e);
        }
        String[] sqlStatements = sql.split(";");
        for (String statement : sqlStatements) {
            statement = statement.trim();
            if (!statement.isEmpty()) {
                stmt.executeUpdate(statement);
            }
        }
    }

    public void close() throws SQLException {
        if (stmt != null && !stmt.isClosed()) {
            stmt.close();
        }
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    public static void main(String[] args) {
        try {
            Database database = new Database();
            database.initDb();
            System.out.println(database.fetchAllInList("sale"));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize the database", e);
        }
    }
}
