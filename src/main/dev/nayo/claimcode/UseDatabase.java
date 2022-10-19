package dev.nayo.claimcode;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
    public void deleteRedeemed(String arg, Player player) {
        final String uuid = player.getUniqueId().toString();
        CodeInstance code = instance.getCodeManager().getCodeInstanceFromName(arg);
        boolean all = arg.equalsIgnoreCase("all");
        if (code == null && !all) {
            System.out.println('a');
            return;
        }
        String preprepared = "DELETE FROM `claim-history` WHERE `uuid`=? ";
        if (!all) {
            preprepared += " AND `key`=\"" + code.getName() + "\";";
        }
        final String prep = preprepared;
        Bukkit.getScheduler().runTaskAsynchronously(instance,()->{
            try{
                PreparedStatement stmnt = db.prepareStatement(prep);
                stmnt.setString(1,uuid);
                stmnt.execute();
            }catch(SQLException e){
                e.printStackTrace();
            }
        });
    }

    public void setRedeemed(String arg, Player player) {
        final String uuid = player.getUniqueId().toString();
        Bukkit.getScheduler().runTaskAsynchronously(instance,()->{
            try{
                PreparedStatement stmnt = db.prepareStatement("INSERT into `claim-history` (uuid, key, redeemed) VALUES (?,?,?)");
                stmnt.setString(1,uuid);
                stmnt.setString(2, arg);
                stmnt.setInt(3, 1);
                stmnt.execute();

            }catch(SQLException e){
                e.printStackTrace();
            }
        });
    }
    public boolean canRedeem(List<String> args, Player player, CompletableFuture<Boolean> cb){
        final String uuid = player.getUniqueId().toString();
        String prePrepared = "SELECT * FROM `claim-history` WHERE `uuid`=? OR ";
        for (int i = 0; i < args.size(); i++) {
            prePrepared += "`key`=\"" + args.get(i) + "\" ";
            if ((i + 1) != args.size()) {
                prePrepared += "OR ";
            }
        }
        final String prepared = prePrepared;

        Bukkit.getScheduler().runTaskAsynchronously(instance,()->{
            try{
                System.out.println(prepared);
                PreparedStatement stmnt = db.prepareStatement(prepared);
                stmnt.setString(1,uuid);

                ResultSet rs = stmnt.executeQuery();


                int total = 0;
                int index = 0;
                while (rs.next()) {
                    total += rs.getInt(3);
                    index += 1;
                    System.out.println(rs.getInt(3));
                }
                boolean canRedeem = (!(index == total)) || (index == 0 && total == 0) ;
                System.out.println((!(index == total)) || (index == 0 && total == 0));
                cb.complete(canRedeem);
            }catch(SQLException e){
                e.printStackTrace();
            }
        });
        return false;
    }
}
