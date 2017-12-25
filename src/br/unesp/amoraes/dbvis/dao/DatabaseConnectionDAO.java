package br.unesp.amoraes.dbvis.dao;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.internals.InternalDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO for Database Connection entity
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-05
 */
public class DatabaseConnectionDAO {
     public static List<DatabaseConnectionEntity> listAll(){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "SELECT * FROM databaseconnection ORDER BY name ASC";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            List<DatabaseConnectionEntity> result = new ArrayList<DatabaseConnectionEntity>();
            while(rs.next()){
                DatabaseConnectionEntity entity = new DatabaseConnectionEntity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setDriver(rs.getString("driver"));
                entity.setUrl(rs.getString("url"));
                entity.setUsername(rs.getString("username"));
                entity.setPassword(rs.getString("password"));
                result.add(entity);
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static DatabaseConnectionEntity save(DatabaseConnectionEntity entity){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "";
            if(entity.getId() != null){
                sql = "UPDATE databaseconnection set name = ?, driver = ?, url = ?, username = ?, password = ? WHERE id = ?";
            }else{
                sql = "INSERT INTO databaseconnection (name,driver,url,username,password) values (?,?,?,?,?)";
            }
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, entity.getName());
            pstm.setString(2, entity.getDriver());
            pstm.setString(3, entity.getUrl());
            pstm.setString(4, entity.getUsername());
            pstm.setString(5, entity.getPassword());
            if(entity.getId() != null){
                pstm.setInt(6, entity.getId());
            }
            pstm.executeUpdate();
            if(entity.getId() == null){
                ResultSet keys = pstm.getGeneratedKeys();
                keys.next();
                entity.setId(keys.getInt(1));
            }
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static void delete(int id){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "DELETE FROM databaseconnection WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnectionDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          
     }
}
