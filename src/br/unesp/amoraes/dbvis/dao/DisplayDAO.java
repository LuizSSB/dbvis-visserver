package br.unesp.amoraes.dbvis.dao;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.beans.DisplayEntity;
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
 * DAO for Display entity
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-05-04
 */
public class DisplayDAO {
     public static List<DisplayEntity> listAll(){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "SELECT * FROM display ORDER BY name ASC";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            List<DisplayEntity> result = new ArrayList<DisplayEntity>();
            while(rs.next()){
                DisplayEntity entity = new DisplayEntity();
                entity.setId(rs.getInt("id"));
                entity.setName(rs.getString("name"));
                entity.setWidth(rs.getInt("width"));
                entity.setHeight(rs.getInt("height"));
                entity.setActive(rs.getBoolean("active"));
                result.add(entity);
            }
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static DisplayEntity save(DisplayEntity entity){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "";
            if(entity.getId() != null){
                sql = "UPDATE display set name = ?, width = ?, height = ? WHERE id = ?";
            }else{
                sql = "INSERT INTO display (name,width,height) values (?,?,?)";
            }
            PreparedStatement pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, entity.getName());
            pstm.setInt(2, entity.getWidth());
            pstm.setInt(3, entity.getHeight());
            if(entity.getId() != null){
                pstm.setInt(4, entity.getId());
            }
            pstm.executeUpdate();
            if(entity.getId() == null){
                ResultSet keys = pstm.getGeneratedKeys();
                keys.next();
                entity.setId(keys.getInt(1));
            }
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static void delete(int id){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "DELETE FROM display WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
          
     }
     
     /**
      * Make a Display as Active and set others to not
      * @param id 
      */
     public static void activate(int id){
         try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "UPDATE display SET active = true WHERE id = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            sql = "UPDATE display SET active = false WHERE id != ?";
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     /**
      * Return current active display configuration
      * @return 
      */
     public static DisplayEntity getActive(){
         try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "SELECT * FROM display WHERE active = true";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            rs.next();
            DisplayEntity entity = new DisplayEntity();
            entity.setId(rs.getInt("id"));
            entity.setName(rs.getString("name"));
            entity.setWidth(rs.getInt("width"));
            entity.setHeight(rs.getInt("height"));
            entity.setActive(rs.getBoolean("active"));
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(DisplayDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
}
