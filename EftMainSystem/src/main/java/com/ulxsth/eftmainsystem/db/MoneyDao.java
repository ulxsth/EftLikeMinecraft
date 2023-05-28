package com.ulxsth.eftmainsystem.db;

import com.sun.jdi.request.MonitorWaitedRequest;
import com.ulxsth.eftmainsystem.EftMainSystem;

import java.sql.*;
import java.util.UUID;

public class MoneyDao {
    public static final String PATH = "jdbc:sqlite:eft.db";
    public static final String TABLE_NAME = "moneys";

    private static final EftMainSystem plugin = EftMainSystem.getInstance();

    public MoneyDao() throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        Statement statement = null;

        try {
            statement = connection.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    "(unique_id CHAR(36) PRIMARY KEY," +
                    "amount INT);";
            statement.executeUpdate(sql);

        } catch(SQLException err) {
            err.printStackTrace();

        } finally {
            if(statement != null) statement.close();
            connection.close();
        }
    }

    public void insert(MoneyDto moneyDto) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            UUID uniqueId = moneyDto.uniqueId();
            int amount = moneyDto.amount();

            String sql = "INSERT INTO "
                    + TABLE_NAME
                    + "(unique_id, amount) "
                    + "VALUES(?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());
            ps.setInt(2, amount);
            ps.executeUpdate();

        } catch(SQLException err) {
            err.printStackTrace();

        } finally {
            if(ps != null) ps.close();
            connection.close();
        }
    }


    public MoneyDto selectByUniqueId(UUID uniqueId) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;
        ResultSet result = null;
        MoneyDto dto = null;

        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE unique_id = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());

            result = ps.executeQuery();
            if(result.next()) {
                UUID uuid = UUID.fromString(result.getString(1));
                int amount = result.getInt(2);
                dto = new MoneyDto(uuid, amount);
            }

        } catch(SQLException err) {
            err.printStackTrace();

        } finally {
            if(result != null) result.close();
            if(ps != null) ps.close();
            connection.close();
        }

        return dto;
    }

    public void update(MoneyDto dto) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            String sql = "UPDATE " + TABLE_NAME
                    + " SET amount = ? WHERE unique_id = ?;";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, dto.amount());
            ps.setString(2, dto.uniqueId().toString());
            ps.executeUpdate();

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if(ps != null) ps.close();
            connection.close();
        }
    }

    public void delete(UUID uniqueId) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE unique_id = ?;";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());
            ps.executeUpdate();

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if(ps != null) ps.close();
            connection.close();
        }
    }

    public boolean isExist(UUID uniqueId) {
        MoneyDto result = null;

        try {
            result = selectByUniqueId(uniqueId);
        } catch (SQLException err) {
            err.printStackTrace();
        }

        return result != null;
    }
}
