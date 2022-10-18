package dev.nayo.claimcode;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

public class UseDatabase {
    private ClaimCode instance;
    private final String FILE_NAME = "ClaimCode.db";
    private Connection db;
    public UseDatabase() throws SQLException {
        instance = ClaimCode.getInstance();
        File databaseFile = new File(instance.getDataFolder(), FILE_NAME);
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                instance.getLogger().log(Level.SEVERE, "Failed to create database file");
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            db = DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `claim-history` (`uuid` varchar(64) not null,`key` varchar(64) not null, `redeemed` INT(11) NOT NULL DEFAULT '0', PRIMARY KEY (`uuid`));";
        Statement stmt = db.createStatement();
        stmt.executeUpdate(CREATE_TABLE);
        stmt.close();
    }


}
