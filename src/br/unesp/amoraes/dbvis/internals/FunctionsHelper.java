package br.unesp.amoraes.dbvis.internals;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.ui.ConsoleIFrame;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * Some useful functions used on the whole application
 *
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-07
 */
public class FunctionsHelper {

    private static final ResourceBundle bundle = ResourceBundle.getBundle("br/unesp/amoraes/dbvis/ui/Messages");

    public static String getString(String key, String... args) {
        String text = bundle.getString(key);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                text = text.replaceAll("\\{" + i + "\\}", args[i]);
            }
        }
        return text;
    }

    public static void logOnConsole(String text) {
        logOnConsole(text, Color.BLACK);
    }

    public static void logOnConsole(String text, Color color) {
        try {
            JTextPane jTextPane = ConsoleIFrame.getInstance().getJTextPaneConsole();
            StyledDocument sdoc = jTextPane.getStyledDocument();

            Style style = jTextPane.getStyle("style_" + color.getRGB());
            if (style == null) {
                style = jTextPane.addStyle("style_" + color.getRGB(), null);
            }
            StyleConstants.setForeground(style, color);
            sdoc.insertString(jTextPane.getText().length(), "\n" + text, style);
        } catch (BadLocationException ex) {
            Logger.getLogger(FunctionsHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static int linearColorInterpolate(int A, int B, int l, int L) {
        // extract r, g, b information
        // A and B is a ARGB-packed int so we use bit operation to extract
        int Ar = (A >> 16) & 0xff;
        int Ag = (A >> 8) & 0xff;
        int Ab = A & 0xff;
        int Br = (B >> 16) & 0xff;
        int Bg = (B >> 8) & 0xff;
        int Bb = B & 0xff;
        // now calculate Y. convert float to avoid early rounding
        // There are better ways but this is for clarity's sake
        int Yr = (int) (Ar + l * (Br - Ar) / (float) L);
        int Yg = (int) (Ag + l * (Bg - Ag) / (float) L);
        int Yb = (int) (Ab + l * (Bb - Ab) / (float) L);
        // pack ARGB with hardcoded alpha
        return 0xff000000 | // alpha
                ((Yr << 16) & 0xff0000)
                | ((Yg << 8) & 0xff00)
                | (Yb & 0xff);
    }

    /**
     * Verify if a column have nulls
     * @param selectedData
     * @param column
     * @return 
     */
    public static boolean columnHaveNulls(SelectedData selectedData, int column) {
        boolean haveNulls = false;
        for (int j = 0; j < selectedData.getRowCount(); j++) {
            if (selectedData.getValue(j, column) == null) {
                haveNulls = true;
                break;
            }
        }
        return haveNulls;
    }
    
    public static List<SelectedRelationship> getRelationshionship(SelectedData data, int row, int idColumn, String sql, Connection connection){
        List<SelectedRelationship> list = new ArrayList<SelectedRelationship>();
        String sqlFinal = sql.replaceAll("\\$id", data.getValue(row, idColumn).toString());
        try{
            ResultSet rs = connection.prepareStatement(sqlFinal).executeQuery();
            while(rs.next()){
                String source = rs.getString("source");
                String target = rs.getString("target");
                Boolean directed = rs.getBoolean("directed");
                SelectedRelationship rel = new SelectedRelationship(source, target, directed);
                list.add(rel);
            }
            rs.close();
        }catch(SQLException ex){
            FunctionsHelper.logOnConsole(FunctionsHelper.getString("DataSelectIFrame.message.SQLError")+" ("+ex.getMessage()+")", Color.RED);
        }
        return list;
    }

    public static Connection getJDBCConnection(DatabaseConnectionEntity databaseConnection) throws SQLException {
        return DriverManager.getConnection(databaseConnection.getUrl(), databaseConnection.getUsername(), (!databaseConnection.getPassword().equals("")?databaseConnection.getPassword():null));
    }
    
    
    public static BufferedImage resizeImage(Image originalImage, int newWidth, int newHeight) {
        int type = BufferedImage.TYPE_INT_ARGB;

        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g = resizedImage.createGraphics();

        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
        g.dispose();

        return resizedImage;
    }
}
