package br.unesp.amoraes.dbvis;

import br.unesp.amoraes.dbvis.beans.enums.ProjectType;
import br.unesp.amoraes.dbvis.internals.InternalDatabase;
import br.unesp.amoraes.dbvis.internals.exception.InternalDatabaseInitializationException;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.ui.MainWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 * Main class to start the application
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-03
 */
public class App 
{
    private static ProjectType currentProjectType = null;
    
    /**
     * Start the application
     * @param args 
     */
    public static void main( String[] args )
    {
        try {
            if(args.length > 0 && args[0].equals("install")){
                InternalDatabase.create();
            }
            InternalDatabase.initialize();
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            APIServer.getInstance().start();
        } catch (InternalDatabaseInitializationException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        } catch (Exception ex){
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        new MainWindow().setVisible(true);
    }

    public static ProjectType getCurrentProjectType() {
        return currentProjectType;
    }

    public static void setCurrentProjectType(ProjectType t) {
        currentProjectType = t;
    }
    
    
}
