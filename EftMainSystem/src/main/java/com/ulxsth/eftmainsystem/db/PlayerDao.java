package com.ulxsth.eftmainsystem.db;

import com.ulxsth.eftmainsystem.EftMainSystem;
import com.ulxsth.eftmainsystem.constants.DbConstants;
import com.ulxsth.eftmainsystem.db.PlayerDto;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class PlayerDao {
    private static final EftMainSystem plugin = EftMainSystem.getInstance();

    public static final String PATH = DbConstants.DB_PATH;
    public static final String TABLE_NAME = "players";

    PlayerDao() throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        Statement statement = null;
        try {
            statement = connection.createStatement();

            // dbの作成
            String sql = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    "(unique_id CHAR(36) PRIMARY KEY," +
                    "name VARCHAR(16)," +
                    "first_logged_in DATE," +
                    "recently_logged_in DATE);";
            statement.executeUpdate(sql);

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (statement != null) statement.close();
            connection.close();
        }
    }

    public void insert(PlayerDto playerDto) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            UUID uniqueId = playerDto.uniqueId();
            String name = playerDto.name();
            Date firstLoggedIn = playerDto.firstLoggedIn();
            Date recentlyLoggedIn = playerDto.recentlyLoggedIn();

            String sql = "INSERT INTO "
                    + TABLE_NAME
                    + "(unique_id, name, first_logged_in, recently_logged_in) "
                    + "VALUES(?, ?, ?, ?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());
            ps.setString(2, name);
            ps.setDate(3, new java.sql.Date(firstLoggedIn.getTime()));
            ps.setDate(4, new java.sql.Date(recentlyLoggedIn.getTime()));
            ps.executeUpdate();
            plugin.getLogger().info("Initialized data: $userName(uuid: $userUUID)");

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (ps != null) ps.close();
            connection.close();
        }
    }

    public PlayerDto selectByUniqueId(UUID uniqueId) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;
        PlayerDto dto = null;

        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE unique_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());

            ResultSet result = ps.executeQuery();
            result.next();
            UUID uuid = UUID.fromString(result.getString(1));
            String name = result.getString(2);
            Date firstLoggedIn = result.getDate(3);
            Date recentlyLoggedIn = result.getDate(4);
            if (result.wasNull()) {
                return null;
            }
            dto = new PlayerDto(uuid, name, firstLoggedIn, recentlyLoggedIn);

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (ps != null) ps.close();
            connection.close();
        }

        return dto;
    }

    public void update(PlayerDto playerDto) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            UUID uniqueId = playerDto.uniqueId();
            String name = playerDto.name();
            Date firstLoggedIn = playerDto.firstLoggedIn();
            Date recentlyLoggedIn = playerDto.recentlyLoggedIn();

            String sql = "UPDATE " + TABLE_NAME
                    + "SET name = ?,"
                    + "first_logged_in = ?,"
                    + "recently_logged_in = ?"
                    + "WHERE uuid = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setDate(2, new java.sql.Date(firstLoggedIn.getTime()));
            ps.setDate(3, new java.sql.Date(recentlyLoggedIn.getTime()));
            ps.setString(4, uniqueId.toString());
            ps.executeUpdate();

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (ps != null) ps.close();
            connection.close();
        }
    }

    public void upsert(PlayerDto playerDto) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            UUID uniqueId = playerDto.uniqueId();
            String name = playerDto.name();
            Date firstLoggedIn = playerDto.firstLoggedIn();
            Date recentlyLoggedIn = playerDto.recentlyLoggedIn();

            String sql = "REPLACE INTO " + TABLE_NAME + " VALUES(?, ?, ?, ?);";
            ps = connection.prepareStatement(sql);
            ps.setString(4, uniqueId.toString());
            ps.setString(1, name);
            ps.setDate(2, new java.sql.Date(firstLoggedIn.getTime()));
            ps.setDate(3, new java.sql.Date(recentlyLoggedIn.getTime()));
            ps.executeUpdate();

            connection.close();
        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (ps != null) ps.close();
            connection.close();
        }
    }

    public void delete(UUID uniqueId) throws SQLException {
        Connection connection = DriverManager.getConnection(PATH);
        PreparedStatement ps = null;

        try {
            String sql = "DELETE FROM " + TABLE_NAME + "WHERE unique_id = ?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());
            ps.executeUpdate();
            connection.close();

        } catch (SQLException err) {
            err.printStackTrace();

        } finally {
            if (ps != null) ps.close();
            connection.close();
        }
    }
}
