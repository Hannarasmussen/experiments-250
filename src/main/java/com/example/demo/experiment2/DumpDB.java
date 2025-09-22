package com.example.demo.experiment2;

import java.sql.*;

public class DumpDB {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:file:./polls/polls", "sa", "")) {
            dumpTable(conn, "users");
            dumpTable(conn, "polls");
            dumpTable(conn, "vote_options");
            dumpTable(conn, "votes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void dumpTable(Connection conn, String tableName) throws SQLException {
        System.out.println("Table: " + tableName);
        try (Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName)) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            // Print header
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(meta.getColumnName(i) + "\t");
            }
            System.out.println();

            // Print rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getString(i) + "\t");
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}
