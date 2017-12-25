package br.unesp.amoraes.dbvis.internals;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * Store data and metadata of a data set obtained from a RDBMS
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-06
 */
public class SelectedData {
    
    private int columnCount;
    private LinkedList<Object[]> list;
    private String[] columnClasses;
    private String[] columnNames;
    private DefaultTableModel tableModel;
    private Method[] columnGetMethod;
    private static TreeMap<String, Method> typeToGetMethod;
    
    private static void configureGetMethods(){
        try {
            typeToGetMethod = new TreeMap<String, Method>();
            //number types
            typeToGetMethod.put(Integer.class.getName(), ResultSet.class.getMethod("getInt", Integer.TYPE));           
            typeToGetMethod.put(Long.class.getName(), ResultSet.class.getMethod("getLong", Integer.TYPE));
            typeToGetMethod.put(Short.class.getName(), ResultSet.class.getMethod("getShort", Integer.TYPE));
            typeToGetMethod.put(Byte.class.getName(), ResultSet.class.getMethod("getByte", Integer.TYPE));
            typeToGetMethod.put(Double.class.getName(), ResultSet.class.getMethod("getDouble", Integer.TYPE));
            typeToGetMethod.put(Float.class.getName(), ResultSet.class.getMethod("getFloat", Integer.TYPE));
            typeToGetMethod.put(BigDecimal.class.getName(), ResultSet.class.getMethod("getBigDecimal", Integer.TYPE));
            //string
            typeToGetMethod.put(String.class.getName(), ResultSet.class.getMethod("getString", Integer.TYPE));
            //other
            typeToGetMethod.put(Boolean.class.getName(), ResultSet.class.getMethod("getBoolean", Integer.TYPE));
            
            
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SelectedData(ResultSet rs){
        try {
            if(typeToGetMethod == null)
                SelectedData.configureGetMethods();
            ResultSetMetaData rsMetaData = rs.getMetaData();
            this.columnCount = rsMetaData.getColumnCount();
            columnClasses = new String[columnCount];
            columnNames = new String[columnCount];
            columnGetMethod = new Method[columnCount];
            
            for(int i = 1; i <= columnCount; i++){
                columnClasses[i-1] = rsMetaData.getColumnClassName(i);
                columnNames[i-1] = rsMetaData.getColumnLabel(i);
                columnGetMethod[i-1] = typeToGetMethod.get(columnClasses[i-1]);
                if(columnGetMethod[i-1] == null){
                    FunctionsHelper.logOnConsole(FunctionsHelper.getString("General.error.dataTypeNotSupported")+": "+
                            rsMetaData.getColumnTypeName(i)+" ("+rsMetaData.getColumnClassName(i)+")", Color.RED);
                }
            }
            this.list = new LinkedList<Object[]>();
            while(rs.next()){
                Object[] line = new Object[columnCount];
                for(int i = 0; i < columnCount; i++){
                    line[i] = getObject(i,rs);
                }
                list.add(line);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Object getObject(int i, ResultSet rs) {
        try {
            Method m = columnGetMethod[i];
            //verify if this columns is not null
            if(rs.getObject(i+1) == null){
                return null;
            }
            if(m != null){
                 return m.invoke(rs, i+1); //resultset columns starts with 1, not 0              
            }
        } catch (SQLException ex){
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SelectedData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public DefaultTableModel getTableModel(){
        tableModel = new DefaultTableModel();
        tableModel.addColumn("#");
        for(int i = 0; i < columnCount; i++){
            tableModel.addColumn(columnNames[i]);
        }
        int lineNumber = 0;
        int maxLines = 1000;
        int count = 0;
        for(Object[] line : list){
            count++;
            if(count > maxLines){
                FunctionsHelper.logOnConsole(FunctionsHelper.getString("DataSelectIFrame.message.showingPartialData", new String[]{ maxLines+"" }));
                break;
            }
            Object[] modelLine = new Object[columnCount+1];
            modelLine[0] = lineNumber;
            lineNumber++;
            for(int i = 0; i < columnCount; i++){
                ValueTextItem item = new ValueTextItem((line[i]!=null)?line[i]:null, (line[i]!=null)?line[i].toString():null); 
                modelLine[i+1] = item;
            }
            tableModel.addRow(modelLine);
        }
        return tableModel;
    }
    
    public int getRowCount(){
        return list.size();
    }
    
    public int getColumnCount(){
        return this.columnCount;
    }
    
    public String[] getColumnNames(){
        return this.columnNames;
    }
    
    public String[] getColumnClasses(){
        return this.columnClasses;
    }
    
    public Object getValue(int row, int column){
        return list.get(row)[column];
    }
    
    
}
