package br.unesp.amoraes.dbvis.internals;

import br.unesp.amoraes.dbvis.internals.exception.InternalDatabaseInitializationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * Provides connection and initialization of the internal database
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-04
 */
public class InternalDatabase {
    private static String dbUrl = "jdbc:derby:internal_db;user=dbvis;password=infovis";
    private static String dbUrlCreate = "jdbc:derby:internal_db;create=true;user=dbvis;password=infovis";
    public static Connection getConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            return DriverManager.getConnection(dbUrl);
        } catch (Exception ex) {
            Logger.getLogger(InternalDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }   
    
    public static void create(){
         try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            DriverManager.getConnection(dbUrlCreate);
        } catch (Exception ex) {
            Logger.getLogger(InternalDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void initialize() throws InternalDatabaseInitializationException{
        try{
            //create the internal tables
            InputStream is = InternalDatabase.class.getResourceAsStream("/br/unesp/amoraes/dbvis/beans/create_tables.sql");
            String sql = IOUtils.toString(is, "UTF-8");
            Connection conn = getConnection();
            StringTokenizer tokenizer = new StringTokenizer(sql,";");
            while(tokenizer.hasMoreElements()){
                PreparedStatement pstm = conn.prepareStatement(tokenizer.nextToken().replaceAll(";", ""));
                try{
                    pstm.executeUpdate();
                }catch(SQLException e){
                    Logger.getLogger(InternalDatabase.class.getName()).log(Level.INFO,e.getMessage(),new Object[]{});
                }
            }
            
        }catch(Exception e){
            throw new InternalDatabaseInitializationException(e);
        }
    }
}
