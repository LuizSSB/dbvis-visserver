package br.unesp.amoraes.dbvis.dao;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.beans.DisplayEntity;
import br.unesp.amoraes.dbvis.beans.ParametersEntity;
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
 * DAO for Parameters entity
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-08-15
 */
public class ParametersDAO {
     public static ParametersEntity get(){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "SELECT * FROM parameters WHERE id = 1";
            PreparedStatement pstm = conn.prepareStatement(sql);
            ResultSet rs = pstm.executeQuery();
            ParametersEntity entity = new ParametersEntity();
            while(rs.next()){
                entity.setApiPassword(rs.getString("api_password"));                
            }
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(ParametersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     public static ParametersEntity save(ParametersEntity entity){
        try {
            Connection conn = InternalDatabase.getConnection();
            String sql = "SELECT * FROM parameters";
            PreparedStatement pstm1 = conn.prepareStatement(sql);
            ResultSet rs1 = pstm1.executeQuery();
            if(rs1.next()){
                sql = "UPDATE parameters set api_password = ? WHERE id = 1";
            }else{
                sql = "INSERT INTO parameters (api_password) values (?)";
            }
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, entity.getApiPassword());
            pstm.executeUpdate();
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(ParametersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
     }
     
     
}
