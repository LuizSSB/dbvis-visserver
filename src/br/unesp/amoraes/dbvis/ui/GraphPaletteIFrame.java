package br.unesp.amoraes.dbvis.ui;

import br.unesp.amoraes.dbvis.KGlobal;
import br.unesp.amoraes.dbvis.beans.DatabaseConnectionEntity;
import br.unesp.amoraes.dbvis.ui.palette.graph.GraphHelper;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.internals.SelectedData;
import br.unesp.amoraes.dbvis.internals.ValueTextItem;
import br.unesp.amoraes.dbvis.rest.APIServer;
import br.unesp.amoraes.dbvis.viewer.graph.GraphViewer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * UI: palette to configure a graph visualization
 * @author Alessandro Moraes (sanfatec at gmail.com)
 * @since 2013-01-08
 */
public class GraphPaletteIFrame extends javax.swing.JInternalFrame implements IPalette{
    private SelectedData selectedData;
    private String scaleOrder = "ASC";
    private Integer sizeDimension;
    private Integer colorDimension;
    private Boolean colorDimensionQualitative;
    private Integer colorDimensionScale;
    private GraphViewer.Algorithm layoutAlgorithm;
    private String sqlRelationships;
    private Integer idColumn;
    private DatabaseConnectionEntity databaseConnection;
    private boolean alwaysShowLabel;
    private Integer labelDimension;
    
    
    /**
     * Creates new form GraphPaletteIFrame
     */
    public GraphPaletteIFrame() {
        initComponents();
        
        //implement the gilberlt auber style icons on hierarchy
        ImageIcon leafIcon = new ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/gilbert_auber_id.png"));
        ImageIcon nodeIcon = new ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/gilbert_auber_node.png"));
        DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
        renderer.setLeafIcon(leafIcon);
        renderer.setClosedIcon(nodeIcon);
        renderer.setOpenIcon(nodeIcon);
        
        //implement the items of color scales
        jComboBoxScale.addItem(new ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/scale_red2green.jpg")));
        jComboBoxScale.addItem(new ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/scale_blue2yellow.jpg")));
        jComboBoxScale.addItem(new ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/scale_black2white.jpg")));
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupColor = new javax.swing.ButtonGroup();
        jLabelAlgorithm = new javax.swing.JLabel();
        jComboBoxAlgorithm = new javax.swing.JComboBox();
        jLabelSize = new javax.swing.JLabel();
        jComboBoxSize = new javax.swing.JComboBox();
        jLabelColor = new javax.swing.JLabel();
        jComboBoxColor = new javax.swing.JComboBox();
        jRadioButtonColorQuality = new javax.swing.JRadioButton();
        jRadioButtonColorQuantity = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabelColorScale = new javax.swing.JLabel();
        jComboBoxScale = new javax.swing.JComboBox();
        jLabelScaleMinor = new javax.swing.JLabel();
        jLabelScaleMajor = new javax.swing.JLabel();
        jButtonInvertScale = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButtonGenerate = new javax.swing.JButton();
        jCheckBoxShowLabel = new javax.swing.JCheckBox();
        jComboBoxLabel = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButtonExportView = new javax.swing.JButton();

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/unesp/amoraes/dbvis/ui/Bundle"); // NOI18N
        setTitle(bundle.getString("GraphPaletteIFrame.title")); // NOI18N

        jLabelAlgorithm.setText(bundle.getString("GraphPaletteIFrame.jLabelAlgorithm.text")); // NOI18N

        jComboBoxAlgorithm.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Default" }));
        jComboBoxAlgorithm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAlgorithmActionPerformed(evt);
            }
        });

        jLabelSize.setText(bundle.getString("GraphPaletteIFrame.jLabelSize.text")); // NOI18N

        jComboBoxSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSizeActionPerformed(evt);
            }
        });

        jLabelColor.setText(bundle.getString("GraphPaletteIFrame.jLabelColor.text")); // NOI18N

        jComboBoxColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxColorActionPerformed(evt);
            }
        });

        buttonGroupColor.add(jRadioButtonColorQuality);
        jRadioButtonColorQuality.setText(bundle.getString("GraphPaletteIFrame.jRadioButtonColorQuality.text")); // NOI18N
        jRadioButtonColorQuality.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonColorQualityActionPerformed(evt);
            }
        });

        buttonGroupColor.add(jRadioButtonColorQuantity);
        jRadioButtonColorQuantity.setText(bundle.getString("GraphPaletteIFrame.jRadioButtonColorQuantity.text")); // NOI18N
        jRadioButtonColorQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonColorQuantityActionPerformed(evt);
            }
        });

        jLabelColorScale.setText(bundle.getString("GraphPaletteIFrame.jLabelColorScale.text")); // NOI18N

        jComboBoxScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxScaleActionPerformed(evt);
            }
        });

        jLabelScaleMinor.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabelScaleMinor.setText(bundle.getString("GraphPaletteIFrame.jLabelScaleMinor.text")); // NOI18N

        jLabelScaleMajor.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N
        jLabelScaleMajor.setText(bundle.getString("GraphPaletteIFrame.jLabelScaleMajor.text")); // NOI18N

        jButtonInvertScale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/unesp/amoraes/dbvis/ui/images/invert_ico.jpg"))); // NOI18N
        jButtonInvertScale.setText(bundle.getString("GraphPaletteIFrame.jButtonInvertScale.text")); // NOI18N
        jButtonInvertScale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInvertScaleActionPerformed(evt);
            }
        });

        jButtonGenerate.setText(bundle.getString("GraphPaletteIFrame.jButtonGenerate.text")); // NOI18N
        jButtonGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenerateActionPerformed(evt);
            }
        });

        jCheckBoxShowLabel.setText(bundle.getString("GraphPaletteIFrame.jCheckBoxShowLabel.text")); // NOI18N
        jCheckBoxShowLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowLabelActionPerformed(evt);
            }
        });

        jComboBoxLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLabelActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("GraphPaletteIFrame.jLabel1.text")); // NOI18N

        jButtonExportView.setText(bundle.getString("GraphPaletteIFrame.jButtonExportView.text")); // NOI18N
        jButtonExportView.setEnabled(false);
        jButtonExportView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportViewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxLabel, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxAlgorithm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxSize, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxColor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBoxScale, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelAlgorithm)
                            .addComponent(jLabelSize)
                            .addComponent(jLabelColor)
                            .addComponent(jRadioButtonColorQuality)
                            .addComponent(jRadioButtonColorQuantity)
                            .addComponent(jLabelColorScale)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(jLabelScaleMinor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonInvertScale, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(jLabelScaleMajor))
                            .addComponent(jCheckBoxShowLabel)
                            .addComponent(jLabel1)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButtonExportView, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonGenerate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelAlgorithm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabelSize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelColor)
                .addGap(8, 8, 8)
                .addComponent(jComboBoxColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonColorQuality)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButtonColorQuantity)
                .addGap(8, 8, 8)
                .addComponent(jLabelColorScale)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelScaleMinor)
                            .addComponent(jLabelScaleMajor))
                        .addGap(22, 22, 22)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonInvertScale, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBoxShowLabel)
                .addGap(65, 65, 65)
                .addComponent(jButtonGenerate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExportView)
                .addContainerGap(62, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxColorActionPerformed
        if(jComboBoxColor.getSelectedIndex() > 0){
            buttonGroupColor.clearSelection();
            jRadioButtonColorQuality.setEnabled(true);
            jComboBoxScale.setEnabled(false);
            String columnClass = selectedData.getColumnClasses()[((ValueTextItem<Integer>)jComboBoxColor.getSelectedItem()).getValue().intValue()];
            List<String> allowedForQuantity = Arrays.asList(KGlobal.NUMBER_CLASSES);
            if(allowedForQuantity.contains(columnClass)){               
                jRadioButtonColorQuantity.setEnabled(true);
            }else{
                jComboBoxScale.setEnabled(false);
                jRadioButtonColorQuantity.setEnabled(false);
                jRadioButtonColorQuality.setSelected(true);  
                this.colorDimensionQualitative = true;
            }
            this.colorDimension = ((ValueTextItem<Integer>)jComboBoxColor.getSelectedItem()).getValue().intValue();
        }else{
            this.colorDimension = null;
            this.colorDimensionQualitative = null;
            this.colorDimensionScale = null;
            jComboBoxScale.setEnabled(false);
            jRadioButtonColorQuality.setEnabled(false);
            jRadioButtonColorQuantity.setEnabled(false);
            jButtonInvertScale.setEnabled(false);
        }
    }//GEN-LAST:event_jComboBoxColorActionPerformed

    private void jRadioButtonColorQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonColorQuantityActionPerformed
        jComboBoxScale.setEnabled(true);
        jButtonInvertScale.setEnabled(true);
        colorDimensionQualitative = false;
        colorDimensionScale = jComboBoxScale.getSelectedIndex();
    }//GEN-LAST:event_jRadioButtonColorQuantityActionPerformed

    private void jRadioButtonColorQualityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonColorQualityActionPerformed
        jComboBoxScale.setEnabled(false);
        jButtonInvertScale.setEnabled(false);
        colorDimensionQualitative = true;
    }//GEN-LAST:event_jRadioButtonColorQualityActionPerformed

    private void jButtonInvertScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInvertScaleActionPerformed
        if(this.scaleOrder.equals("ASC"))
            scaleOrder = "DESC";
        else
            scaleOrder = "ASC";
        String aux = jLabelScaleMinor.getText();
        jLabelScaleMinor.setText(jLabelScaleMajor.getText());
        jLabelScaleMajor.setText(aux);
    }//GEN-LAST:event_jButtonInvertScaleActionPerformed

    private void jButtonGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenerateActionPerformed
        GraphViewer.create(selectedData, idColumn, sqlRelationships,  databaseConnection, sizeDimension,colorDimension, colorDimensionQualitative, colorDimensionScale, scaleOrder, layoutAlgorithm, alwaysShowLabel, labelDimension);
        this.jButtonExportView.setEnabled(true);
    }//GEN-LAST:event_jButtonGenerateActionPerformed

    private void jComboBoxScaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxScaleActionPerformed
        this.colorDimensionScale = jComboBoxScale.getSelectedIndex();
    }//GEN-LAST:event_jComboBoxScaleActionPerformed

    private void jComboBoxSizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxSizeActionPerformed
         if(jComboBoxSize.getSelectedIndex() > 0){
           sizeDimension = ((ValueTextItem<Integer>)jComboBoxSize.getSelectedItem()).getValue();
        }else{
            sizeDimension = null;
        }
    }//GEN-LAST:event_jComboBoxSizeActionPerformed

    private void jComboBoxAlgorithmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAlgorithmActionPerformed
        this.layoutAlgorithm = ((ValueTextItem<GraphViewer.Algorithm>)jComboBoxAlgorithm.getSelectedItem()).getValue();
    }//GEN-LAST:event_jComboBoxAlgorithmActionPerformed

    private void jCheckBoxShowLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowLabelActionPerformed
        if(jCheckBoxShowLabel.isSelected()){
            this.alwaysShowLabel = true;
        }else{
            this.alwaysShowLabel = false;
        }
    }//GEN-LAST:event_jCheckBoxShowLabelActionPerformed

    private void jComboBoxLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLabelActionPerformed
        if(jComboBoxLabel.getSelectedIndex() > 0){
           labelDimension = ((ValueTextItem<Integer>)jComboBoxLabel.getSelectedItem()).getValue();
        }else{
           labelDimension = null;
        }
    }//GEN-LAST:event_jComboBoxLabelActionPerformed

    private void jButtonExportViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportViewActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(FunctionsHelper.getString("GraphPaletteIFrame.selectFileToExport"));
        int userSelection = fileChooser.showSaveDialog(this);
        if(userSelection == JFileChooser.APPROVE_OPTION){
                String xml = GraphViewer.getInstance().exportXml();
                try{
                File file = fileChooser.getSelectedFile();
                if (!file.exists()) {
                        file.createNewFile();
                }

                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(xml);
                bw.close();
                JOptionPane.showMessageDialog(this, FunctionsHelper.getString("GraphPaletteIFrame.xmlExportSuccess"));
            }catch(Exception e){
                Logger.getLogger(GraphPaletteIFrame.class.getName()).log(Level.SEVERE, "Cannot export graph XML", e);
            }
        }
        
    }//GEN-LAST:event_jButtonExportViewActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupColor;
    private javax.swing.JButton jButtonExportView;
    private javax.swing.JButton jButtonGenerate;
    private javax.swing.JButton jButtonInvertScale;
    private javax.swing.JCheckBox jCheckBoxShowLabel;
    private javax.swing.JComboBox jComboBoxAlgorithm;
    private javax.swing.JComboBox jComboBoxColor;
    private javax.swing.JComboBox jComboBoxLabel;
    private javax.swing.JComboBox jComboBoxScale;
    private javax.swing.JComboBox jComboBoxSize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAlgorithm;
    private javax.swing.JLabel jLabelColor;
    private javax.swing.JLabel jLabelColorScale;
    private javax.swing.JLabel jLabelScaleMajor;
    private javax.swing.JLabel jLabelScaleMinor;
    private javax.swing.JLabel jLabelSize;
    private javax.swing.JRadioButton jRadioButtonColorQuality;
    private javax.swing.JRadioButton jRadioButtonColorQuantity;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    // End of variables declaration//GEN-END:variables

    @Override
    public void disableAllComponents() {
        this.selectedData = null;
        jComboBoxAlgorithm.setEnabled(false);
        jComboBoxColor.setEnabled(false);
        jComboBoxSize.setEnabled(false);
        jComboBoxScale.setEnabled(false);
        jRadioButtonColorQuality.setEnabled(false);
        jRadioButtonColorQuantity.setEnabled(false);
        jButtonInvertScale.setEnabled(false);
        jButtonGenerate.setEnabled(false);
        jCheckBoxShowLabel.setSelected(false);
        jCheckBoxShowLabel.setEnabled(false);
        jComboBoxLabel.setEnabled(false);
    }

    @Override
    public void enableAllComponents(SelectedData selectedData, String sqlRelationships, Integer idColumn, DatabaseConnectionEntity databaseConnection) {
        this.selectedData = selectedData;
        this.idColumn = idColumn;
        this.sqlRelationships = sqlRelationships;
        this.databaseConnection = databaseConnection;
        this.alwaysShowLabel = jCheckBoxShowLabel.isSelected();
        
        
        jComboBoxAlgorithm.setModel(GraphHelper.getLayoutAlgorithmComboBox());
        jComboBoxSize.setModel(GraphHelper.getSizeComboBox(selectedData));
        jComboBoxColor.setModel(GraphHelper.getColorComboBox(selectedData));
        jComboBoxLabel.setModel(GraphHelper.getLabelComboBox(selectedData));
        jButtonGenerate.setEnabled(true);
        jComboBoxAlgorithm.setEnabled(true);
        jComboBoxColor.setEnabled(true);
        jComboBoxSize.setEnabled(true);
        jCheckBoxShowLabel.setEnabled(true);
        jComboBoxLabel.setEnabled(true);
        
        
        this.layoutAlgorithm = ((ValueTextItem<GraphViewer.Algorithm>)jComboBoxAlgorithm.getSelectedItem()).getValue();
        this.labelDimension = ((ValueTextItem<Integer>)jComboBoxLabel.getSelectedItem()).getValue();
    }
}