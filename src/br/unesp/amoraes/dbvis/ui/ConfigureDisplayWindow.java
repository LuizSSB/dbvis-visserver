package br.unesp.amoraes.dbvis.ui;

import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.beans.DisplayEntity;
import br.unesp.amoraes.dbvis.dao.DatabaseConnectionDAO;
import br.unesp.amoraes.dbvis.dao.DisplayDAO;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.internals.ValueTextItem;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 * UI: Configure database connections with external RDBMSs
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-05
 */
public class ConfigureDisplayWindow extends javax.swing.JDialog {

    private DefaultListModel<ValueTextItem<DisplayEntity>> listSavedDisplays;
    private DisplayEntity display;
    
    /**
     * Creates new form ConfigureDatabaseWindow
     */
    public ConfigureDisplayWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        loadSavedDisplaysList();
        initComponents();
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPaneSavedDisplays = new javax.swing.JScrollPane();
        jListSavedConnections = new javax.swing.JList();
        jLabelSavedDisplays = new javax.swing.JLabel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelWidth = new javax.swing.JLabel();
        jTextFieldWidth = new javax.swing.JTextField();
        jLabelHeight = new javax.swing.JLabel();
        jTextFieldHeight = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonNew = new javax.swing.JButton();
        jLabelActive = new javax.swing.JLabel();
        jCheckBoxActive = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/unesp/amoraes/dbvis/ui/Bundle"); // NOI18N
        setTitle(bundle.getString("ConfigureDisplayWindow.title")); // NOI18N

        jListSavedConnections.setModel(listSavedDisplays);
        jListSavedConnections.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jListSavedConnections.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListSavedConnectionsValueChanged(evt);
            }
        });
        jScrollPaneSavedDisplays.setViewportView(jListSavedConnections);

        jLabelSavedDisplays.setText(bundle.getString("ConfigureDisplayWindow.jLabelSavedDisplays.text")); // NOI18N

        jLabelName.setText(bundle.getString("ConfigureDisplayWindow.jLabelName.text")); // NOI18N

        jLabelWidth.setText(bundle.getString("ConfigureDisplayWindow.jLabelWidth.text")); // NOI18N

        jLabelHeight.setText(bundle.getString("ConfigureDisplayWindow.jLabelHeight.text")); // NOI18N

        jButtonSave.setText(bundle.getString("ConfigureDisplayWindow.jButtonSave.text")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonDelete.setText(bundle.getString("ConfigureDisplayWindow.jButtonDelete.text")); // NOI18N
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonNew.setText(bundle.getString("ConfigureDisplayWindow.jButtonNew.text")); // NOI18N
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        jLabelActive.setText(bundle.getString("ConfigureDisplayWindow.jLabelActive.text")); // NOI18N

        jCheckBoxActive.setText(bundle.getString("ConfigureDisplayWindow.jCheckBoxActive.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPaneSavedDisplays, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelName)
                            .addComponent(jLabelWidth)
                            .addComponent(jLabelHeight)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButtonSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNew))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldHeight, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(jTextFieldWidth, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLabelActive)
                            .addComponent(jCheckBoxActive)))
                    .addComponent(jLabelSavedDisplays))
                .addContainerGap(229, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelSavedDisplays)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneSavedDisplays, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelWidth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelHeight)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxActive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonSave)
                            .addComponent(jButtonDelete)
                            .addComponent(jButtonNew))))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        DisplayEntity entity = new DisplayEntity();
        if(display != null && display.getId() != null){
            entity.setId(display.getId());
        }
        entity.setName(jTextFieldName.getText());
        entity.setWidth(Integer.parseInt(jTextFieldWidth.getText()));
        entity.setHeight(Integer.parseInt(jTextFieldHeight.getText()));
        DisplayDAO.save(entity);
        if(jCheckBoxActive.isSelected()){
            entity.setActive(true);
            DisplayDAO.activate(entity.getId());
        }
        loadSavedDisplaysList();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        if(display != null && display.getId() != null){
            if(display.getActive() == false){
                DisplayDAO.delete(display.getId());
                jListSavedConnections.clearSelection();
                loadSavedDisplaysList();
            }else{
                JOptionPane.showMessageDialog(this, FunctionsHelper.getString("ConfigureDisplayWindow.message.cannotDeleteActiveDisplay", null), FunctionsHelper.getString("General.error", null), JOptionPane.ERROR_MESSAGE);
            }
        }
        clearFields(); 
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
         clearFields(); 
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jListSavedConnectionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListSavedConnectionsValueChanged
        int index = jListSavedConnections.getSelectedIndex();
        if(index != -1){
            DisplayEntity entity = listSavedDisplays.get(index).getValue();
            display = entity;
            jTextFieldName.setText(entity.getName());
            jTextFieldWidth.setText(entity.getWidth().toString());
            jTextFieldHeight.setText(entity.getHeight().toString());
            if(entity.getActive())
                jCheckBoxActive.setSelected(true);
            else
                jCheckBoxActive.setSelected(false);
        }
    }//GEN-LAST:event_jListSavedConnectionsValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxActive;
    private javax.swing.JLabel jLabelActive;
    private javax.swing.JLabel jLabelHeight;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelSavedDisplays;
    private javax.swing.JLabel jLabelWidth;
    private javax.swing.JList jListSavedConnections;
    private javax.swing.JScrollPane jScrollPaneSavedDisplays;
    private javax.swing.JTextField jTextFieldHeight;
    private javax.swing.JTextField jTextFieldName;
    private javax.swing.JTextField jTextFieldWidth;
    // End of variables declaration//GEN-END:variables

    private void loadSavedDisplaysList() {
         if(listSavedDisplays == null){
            listSavedDisplays = new DefaultListModel<ValueTextItem<DisplayEntity>>();
        }
        listSavedDisplays.removeAllElements();
        List<DisplayEntity> listConnections = DisplayDAO.listAll();
        for(DisplayEntity e : listConnections){
            listSavedDisplays.addElement(new ValueTextItem(e, e.getName()));
        }
        
    }

    private void clearFields() {
        display = null;
        jTextFieldName.setText("");
        jTextFieldWidth.setText("");
        jTextFieldHeight.setText("");
        jCheckBoxActive.setSelected(false);
    }
}
