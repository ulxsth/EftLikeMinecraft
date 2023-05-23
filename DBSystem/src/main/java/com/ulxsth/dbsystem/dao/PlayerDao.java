package com.ulxsth.dbsystem.dao;

import com.ulxsth.dbsystem.DBSystem;
import com.ulxsth.dbsystem.dto.PlayerDto;

import java.sql.*;
import java.util.Date;
import java.util.UUID;

public class PlayerDao {
    private static final DBSystem plugin = DBSystem.getInstance();

    public static final String PATH = "jdbc:sqlite:" + plugin.getDataFolder() + "\\data.db";
    public static final String TABLE_NAME = "players";

    PlayerDao() {
        try {
            Connection connection = DriverManager.getConnection(PATH);
            Statement statement = connection.createStatement();

            // dbの作成
            String sql = "CREATE TABLE IF NOT EXISTS " +
                    TABLE_NAME +
                    "(unique_id CHAR(36) PRIMARY KEY," +
                    "name VARCHAR(16)," +
                    "first_logged_in DATE," +
                    "recently_logged_in DATE);";
            statement.executeUpdate(sql);
            connection.close();
        } catch(SQLException err) {
            err.printStackTrace();
        }
    }

    public void insert(PlayerDto playerDto) throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(PATH);
            UUID uniqueId = playerDto.uniqueId();
            String name = playerDto.name();
            Date firstLoggedIn = playerDto.firstLoggedIn();
            Date recentlyLoggedIn = playerDto.recentlyLoggedIn();

            String sql = "INSERT INTO "
                    + TABLE_NAME
                    + "(unique_id, name, first_logged_in, recently_logged_in) "
                    + "VALUES(?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());
            ps.setString(2, name);
            ps.setDate(3, new java.sql.Date(firstLoggedIn.getTime()));
            ps.setDate(4, new java.sql.Date(recentlyLoggedIn.getTime()));
            ps.executeUpdate();

            plugin.getLogger().info("Initialized data: $userName(uuid: $userUUID)")
            connection.close();
        } catch(SQLException err) {
            err.printStackTrace();
        }
    }

    public PlayerDto selectByUniqueId(UUID uniqueId) throws SQLException {
        PlayerDto dto = null;

        try {
            Connection connection = DriverManager.getConnection(PATH);
            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE unique_id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uniqueId.toString());

            ResultSet result = ps.executeQuery();
            result.next();
            UUID uuid = UUID.fromString(result.getString(1));
            String name = result.getString(2);
            Date firstLoggedIn = result.getDate(3);
            Date recentlyLoggedIn = result.getDate(4);

            dto = new PlayerDto(uuid, name, firstLoggedIn, recentlyLoggedIn);
        } catch(SQLException err) {
            err.printStackTrace();
        }
        
        return dto;
    }

    public void update(PlayerDto playerDto) throws SQLException {}

    public void upsert(PlayerDto playerDto) throws SQLException {}

    public void delete(UUID uniqueId) throws SQLException {}
}
