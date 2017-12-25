package br.unesp.amoraes.dbvis;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Store global constants
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-08
 */
public class KGlobal {
    public static final String APP_NAME = "DBVis";
    public static final String[] NUMBER_CLASSES = new String[]{BigDecimal.class.getName()
            , BigInteger.class.getName()
            , Byte.class.getName()
            , Double.class.getName()
            , Float.class.getName()
            , Integer.class.getName()
            , Long.class.getName()
            , Short.class.getName()
            , Boolean.class.getName()};
    
    public static final Color[] COLORS_FOR_CATEGORIES = new Color[]{
        Color.RED,
        Color.YELLOW,
        Color.GREEN,
        Color.PINK,
        Color.ORANGE,
        Color.CYAN,
        Color.MAGENTA,
        Color.GRAY,
        Color.BLUE,
        new Color(100, 149, 237),
        new Color(165, 42, 42),
        new Color(127, 255, 212),
        new Color(107, 142, 35),
        new Color(148, 0, 211)
        
    };
    public static int DEFAULT_LABEL_FONT_SIZE = 8;
}
