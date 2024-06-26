/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AdminLluna
 */
public class ConfigDialogIncidence extends javax.swing.JDialog {

    /**
     * Creates new form ConfigDialogIncidence
     */
    private String role;
    private int profesorId;
    
    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
    }

    public int getProfesorId() {
        return profesorId;
    }
    
    public ConfigDialogIncidence(java.awt.Frame parent, boolean modal, String role) {
        super(parent, modal);
        initComponents();
        this.role = role;
        setLocationRelativeTo(null);
        setResizable(false);
        poblarComboBoxCursoEscolarC5();
    }
    
    private List<String> llamarFuncionC5(String cursoEscolar) {
    String query = "SELECT * FROM c5(?) order by c5.num_incidencia";
    List<String> resultados = new ArrayList<>();

    try (Connection con = DatabaseConnection.getConnection();
         PreparedStatement stmt = con.prepareStatement(query)) {

        stmt.setString(1, cursoEscolar);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            int numIncidencia = rs.getInt("num_incidencia");
            Date fechaIncidencia = rs.getDate("fecha_incidencia");
            String observaciones = rs.getString("observaciones");
            String empresaNombre = rs.getString("empresa_nombre");

            resultados.add(numIncidencia
                         + ", " + fechaIncidencia
                         + "," + observaciones
                         + ", " + empresaNombre);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return resultados;
}
/*
private void actualizarTextPaneC5(List<String> resultados) {
    StringBuilder sb = new StringBuilder();
    for (String resultado : resultados) {
        sb.append(resultado).append("\n");
    }
    jTextPaneC5.setText(sb.toString());
}
*/
    private void poblarComboBoxCursoEscolarC5() {
        String query = "SELECT DISTINCT cursoescolar FROM INCIDENCIA";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxCursoEscolarC5.addItem(rs.getString("cursoescolar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
private void actualizarTablaC5(List<String> incidencias) {
    DefaultTableModel model = new DefaultTableModel(
        new String[]{"Número de incidencia", "Fecha de incidencia", "Observaciones", "Empresa"}, 0);

    for (String incidencia : incidencias) {
        String[] partes = incidencia.split(",");
        model.addRow(partes);
    }

    jTableC5.setModel(model);
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
        jLabel1 = new javax.swing.JLabel();
        jComboBoxCursoEscolarC5 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jButtonC5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableC5 = new javax.swing.JTable();
        jButtonBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(217, 239, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("INCIDENCIAS");

        jComboBoxCursoEscolarC5.setSelectedItem(2);
        jComboBoxCursoEscolarC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoEscolarC5ActionPerformed(evt);
            }
        });

        jLabel4.setText("CURSO ESCOLAR:");

        jButtonC5.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC5.setText("Consultar");
        jButtonC5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC5ActionPerformed(evt);
            }
        });

        jTableC5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTableC5.setEnabled(false);
        jTableC5.setGridColor(new java.awt.Color(217, 239, 255));
        jTableC5.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTableC5);

        jButtonBack.setBackground(new java.awt.Color(239, 248, 255));
        jButtonBack.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonBack.setText("Retroceder");
        jButtonBack.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(jButtonC5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxCursoEscolarC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButtonBack)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBoxCursoEscolarC5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addComponent(jButtonC5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBack)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxCursoEscolarC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoEscolarC5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCursoEscolarC5ActionPerformed

    private void jButtonC5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC5ActionPerformed
        // TODO add your handling code here:
        // Obtiene el curso escolar seleccionado del JComboBox
    String cursoEscolar = jComboBoxCursoEscolarC5.getSelectedItem().toString();
    
    // Llama a la función para obtener las incidencias
    List<String> incidencias = llamarFuncionC5(cursoEscolar);
    
    // Actualiza la tabla con las incidencias obtenidas
    actualizarTablaC5(incidencias);
    }//GEN-LAST:event_jButtonC5ActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        this.setVisible(false);
        ConfigDialogConsult menu = new ConfigDialogConsult(null, true, role);
        menu.setProfesorId(this.profesorId);
        menu.setVisible(true);
    }//GEN-LAST:event_jButtonBackActionPerformed

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
            java.util.logging.Logger.getLogger(ConfigDialogIncidence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogIncidence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogIncidence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogIncidence.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        ConfigDialogLogin loginDialog = new ConfigDialogLogin(new javax.swing.JFrame(), false);
        loginDialog.setVisible(false);
        java.awt.EventQueue.invokeLater(new Runnable() {
            String role = loginDialog.getRole();
            public void run() {
                ConfigDialogIncidence dialog = new ConfigDialogIncidence(new javax.swing.JFrame(), true, role);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonC5;
    private javax.swing.JComboBox<String> jComboBoxCursoEscolarC5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableC5;
    // End of variables declaration//GEN-END:variables
}
