package com.ulxsth.dbsystem.dao;

import com.ulxsth.dbsystem.DBSystem;
import com.ulxsth.dbsystem.dto.PlayerDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class PlayerDao {
    private static final DBSystem plugin = DBSystem.getInstance();

    public static final String PATH = "jdbc:sqlite:" + plugin.getDataFolder() + "\\data.db";
    public static final String TABLE_NAME = "players";
    private Connection connection;

    PlayerDao() {
        try {
            this.connection = DriverManager.getConnection(PATH);
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

    public void insert(PlayerDto playerDto) throws SQLException {}
    public PlayerDto selectByUniqueId(UUID uniqueId) throws SQLException {}
    public void update(PlayerDto playerDto) throws SQLException {}
    public void upsert(PlayerDto playerDto) throws SQLException {}
    public void delete(UUID uniqueId) throws SQLException {}
}
