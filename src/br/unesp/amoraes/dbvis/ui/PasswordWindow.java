/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unesp.amoraes.dbvis.ui;

import br.unesp.amoraes.dbvis.beans.ParametersEntity;
import br.unesp.amoraes.dbvis.dao.ParametersDAO;
import br.unesp.amoraes.dbvis.internals.FunctionsHelper;
import br.unesp.amoraes.dbvis.rest.APIServer;
import javax.swing.JOptionPane;

/**
 *
 * @author alessandro
 */
public class PasswordWindow extends javax.swing.JDialog {

    /**
     * Creates new form PasswordWindow
     */
    public PasswordWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jLabelTitle = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabelPasswordConfirm = new javax.swing.JLabel();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButtonSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("br/unesp/amoraes/dbvis/ui/Bundle"); // NOI18N
        setTitle(bundle.getString("PasswordWindow.title")); // NOI18N

        jLabelTitle.setText(bundle.getString("PasswordWindow.jLabelTitle.text")); // NOI18N

        jLabelPassword.setText(bundle.getString("PasswordWindow.jLabelPassword.text")); // NOI18N

        jPasswordField1.setText(bundle.getString("PasswordWindow.jPasswordField1.text")); // NOI18N

        jLabelPasswordConfirm.setText(bundle.getString("PasswordWindow.jLabelPasswordConfirm.text")); // NOI18N

        jPasswordField2.setText(bundle.getString("PasswordWindow.jPasswordField2.text")); // NOI18N

        jButtonSave.setText(bundle.getString("PasswordWindow.jButtonSave.text")); // NOI18N
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabelTitle)
                        .addComponent(jLabelPassword)
                        .addComponent(jPasswordField1)
                        .addComponent(jLabelPasswordConfirm)
                        .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE))
                    .addComponent(jButtonSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle)
                .addGap(18, 18, 18)
                .addComponent(jLabelPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPasswordConfirm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        String pass1 = new String(jPasswordField1.getPassword());
        String pass2 = new String(jPasswordField2.getPassword());
        if(!pass1.equals(pass2)){
            JOptionPane.showMessageDialog(this, FunctionsHelper.getString("PasswordWindow.error.notEqual"), FunctionsHelper.getString("General.error"), JOptionPane.ERROR_MESSAGE);
            return;
        }else{
            ParametersEntity entity = ParametersDAO.get();
            entity.setApiPassword(pass1);
            ParametersDAO.save(entity);
            APIServer.getInstance().setPassword(pass1);
            JOptionPane.showMessageDialog(this, FunctionsHelper.getString("PasswordWindow.error.passwordChanged"), FunctionsHelper.getString("General.success"), JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelPasswordConfirm;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JPasswordField jPasswordField2;
    // End of variables declaration//GEN-END:variables
}