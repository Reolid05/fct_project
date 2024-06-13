/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import javax.swing.JOptionPane;

/**
 *
 * @author AdminLluna
 */
public class ConfigDialogMenu extends javax.swing.JDialog {

    private int profesorId;
    private String role;

    public ConfigDialogMenu(java.awt.Frame parent, boolean modal, String role) {
        super(parent, modal);
        initComponents();
        this.role = role;
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButtonManage = new javax.swing.JButton();
        jButtonConsult = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(null);

        jPanel1.setBackground(new java.awt.Color(190, 228, 255));
        jPanel1.setEnabled(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(600, 400));

        jButtonManage.setBackground(new java.awt.Color(239, 248, 255));
        jButtonManage.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonManage.setText("MANAGE");
        jButtonManage.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonManage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonManageActionPerformed(evt);
            }
        });

        jButtonConsult.setBackground(new java.awt.Color(239, 248, 255));
        jButtonConsult.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonConsult.setText("CONSULT");
        jButtonConsult.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonConsult.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButtonConsult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gestion.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/consulta.png"))); // NOI18N

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLoginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jLabel3.setText("New login:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(76, 76, 76))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonLogin))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonConsult, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonManage, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonManage, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConsult, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonLogin)
                    .addComponent(jLabel3))
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 288, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConsultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultActionPerformed
        this.setVisible(false);
        ConfigDialogConsult consult = new ConfigDialogConsult(null, true);
        consult.setVisible(true);
        consult.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogMenu.this.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButtonConsultActionPerformed

    private void jButtonManageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonManageActionPerformed
        this.setVisible(false);
        switch (role) {
            case "ADMINISTRADOR":
                ConfigDialogGestionAdministrador adminDialog = new ConfigDialogGestionAdministrador(null, true, role);
                adminDialog.setVisible(true);
                adminDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        ConfigDialogMenu.this.setVisible(true);
                    }
                });
                break;
            case "TUTOR_FCT":
                ConfigDialogGestionTutorFCT tutorFCTDialog = new ConfigDialogGestionTutorFCT(null, true);
                tutorFCTDialog.setProfesorId(profesorId);
                tutorFCTDialog.setVisible(true);
                tutorFCTDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        ConfigDialogMenu.this.setVisible(true);
                    }
                });
                break;
            case "TUTOR_RESPONSABLE":
                // ConfigDialogGestionTutorResponsable tutorRespDialog = new ConfigDialogGestionTutorResponsable(null, true);
                // tutorRespDialog.setVisible(true);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Role not recognized.");
                break;
        }
        this.setVisible(true);
    }//GEN-LAST:event_jButtonManageActionPerformed

    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLoginActionPerformed
        this.setVisible(false);
        ConfigDialogLogin login = new ConfigDialogLogin(null, true);
        login.setVisible(true);
        login.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogMenu.this.setVisible(false);
            }
        });
    }//GEN-LAST:event_jButtonLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigDialogLogin loginDialog = new ConfigDialogLogin(new javax.swing.JFrame(), false);
                loginDialog.setVisible(false);
                String role = loginDialog.getRole();
                if (role != null) {
                    ConfigDialogMenu menuDialog = new ConfigDialogMenu(new javax.swing.JFrame(), true, role);
                    menuDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    menuDialog.setVisible(true);
                } else {
                    System.exit(0);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConsult;
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JButton jButtonManage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
