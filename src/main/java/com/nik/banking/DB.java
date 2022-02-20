package com.nik.banking;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private static final String url = "jdbc:sqlite:" + Main.fileName;

    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }




    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card ("
                + "id INTEGER PRIMARY KEY,"
                + "number TEXT NOT NULL,"
                + "pin TEXT NOT NULL,"
                + "balance INTEGER DEFAULT 0"
                + ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void insertData(String cardNumber, String pinNumber) {
        String sql = "INSERT INTO card(number, pin) VALUES(?,?)";

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pinNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static ArrayList<String> getCardAndPin() {
        ArrayList<String> result = new ArrayList<>();
        String sql = "SELECT number, pin FROM card";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                result.add(rs.getString(1));
                result.add(rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }


    public static int getBalance(String cardNumber) {
        //find card in DB and get balance that reports to it
        String sql = "SELECT balance FROM card WHERE number = ?;";
        int balance = 0;

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, cardNumber);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                balance = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return balance;
    }


    public static void updateBalance(int balance, String cardNum) {
        String sql = "UPDATE card SET  balance = balance + ?" +
                " WHERE number = ?;";

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, balance);
            preparedStatement.setString(2, cardNum);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void deleteAcc(String cardNum) {
        String sql = "DELETE FROM card WHERE number = ?;";

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNum);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void takeIncome(String cardNum, int amount) {
        String sql = "UPDATE card SET balance = balance - ? WHERE number = ?;";

        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, cardNum);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
