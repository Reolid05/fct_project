/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import com.mycompany.fct_project.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

/**
 *
 * @author AdminLluna
 */
public class ConfigDialogEmpresa extends javax.swing.JDialog {

    /**
     * Creates new form ConfigDialogStart
     */
    public ConfigDialogEmpresa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        pack();
        
        poblarComboBoxEmpresa();
        poblarComboBoxTecnologia();
    }
    
    private void poblarComboBoxEmpresa() {
        String query = "SELECT DISTINCT nombre FROM EMPRESA";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxEmpresaC1.addItem(rs.getString("nombre"));
                jComboBoxEmpresaC8.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void poblarComboBoxTecnologia() {
        String query = "SELECT DISTINCT descripcion FROM TECNOLOGIA";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxEmpresaC7.addItem(rs.getString("descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
   * FUNCION C1 ----------------------------
   **/
     
    private List<String> llamarFuncionC1(String empresaNombre) {
        String query = "SELECT * FROM c1(?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, empresaNombre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idEmpresa = rs.getString("idempresa");
                    String nombreEmpresa = rs.getString("nombre_empresa");
                    String nombreSector = rs.getString("nombre_sector");
                    String tutorNombre = rs.getString("tutor_responsable_nombre");
                    String tutorApellidos = rs.getString("tutor_responsable_apellidos");
                    String contactoNombre = rs.getString("contacto_nombre");
                    String contactoApellidos = rs.getString("contacto_apellidos");
                    String contactoTelefono = rs.getString("contacto_telefono");
                    String contactoCargo = rs.getString("contacto_cargo");

                    StringBuilder resultado = new StringBuilder();
                    resultado.append("Empresa Id: ").append(idEmpresa).append("\n")
                            .append("Nombre Empresa: ").append(nombreEmpresa).append("\n")
                            .append("Nombre Sector: ").append(nombreSector).append("\n").append("\n")
                            .append("Tutor: ").append(tutorNombre).append(" ").append(tutorApellidos).append("\n").append("\n")
                            .append("Contacto: ").append(contactoNombre).append(" ").append(contactoApellidos).append("\n")
                            .append("Teléfono: ").append(contactoTelefono).append("\n")
                            .append("Cargo: ").append(contactoCargo);

                    resultados.add(resultado.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }


    private void actualizarTextPaneC1(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC1.setText(sb.toString());
    }
    
    /**
   * FUNCION C7 ----------------------------
   **/
     
    private List<String> llamarFuncionC7(String tecnologiaDescripcion) {
        String query = "SELECT * FROM c7(?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, tecnologiaDescripcion);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String idEmpresa = rs.getString("idempresa");
                    String nombreEmpresa = rs.getString("empresa_nombre");
                    String nombreSector = rs.getString("sector_descripcion");

                    StringBuilder resultado = new StringBuilder();
                    resultado.append("Nombre Empresa: ").append(nombreEmpresa).append("\n")
                            .append("ID Empresa: ").append(idEmpresa).append("\n")
                            .append("Sector: ").append(nombreSector).append("\n");

                    resultados.add(resultado.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }


    private void actualizarTextPaneC7(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC7.setText(sb.toString());
    }

    /**
   * FUNCION C8 ----------------------------
   **/
     
    private List<String> llamarFuncionC8(String empresaNombre) {
        String query = "SELECT * FROM c8(?)"; 
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, empresaNombre);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String profesorNombre = rs.getString("profesor_nombre"); 
                    String profesorApellido = rs.getString("profesor_apellido");
                    Date fechaContacto = rs.getDate("fechacontacto");
                    String observaciones = rs.getString("observaciones");

                    StringBuilder resultado = new StringBuilder();
                    resultado.append("Profesor: ").append(profesorNombre).append(" ").append(profesorApellido).append("\n")
                            .append("Fecha de contacto: ").append(fechaContacto).append("\n")
                            .append("Observaciones: ").append(observaciones).append("\n").append("\n");

                    resultados.add(resultado.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private void actualizarTextPaneC8(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC8.setText(sb.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxEmpresaC1 = new javax.swing.JComboBox<>();
        jButtonC1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPaneC1 = new javax.swing.JTextPane();
        jButtonC2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxEmpresaC7 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPaneC7 = new javax.swing.JTextPane();
        jButtonC7 = new javax.swing.JButton();
        jButtonBack1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jComboBoxEmpresaC8 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneC8 = new javax.swing.JTextPane();
        jButtonBack2 = new javax.swing.JButton();
        jButtonC8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(190, 228, 255));

        jTabbedPane1.setBackground(new java.awt.Color(190, 228, 255));

        jPanel1.setBackground(new java.awt.Color(217, 239, 255));

        jLabel3.setText("EMPRESA");

        jComboBoxEmpresaC1.setSelectedItem(2);
        jComboBoxEmpresaC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmpresaC1ActionPerformed(evt);
            }
        });

        jButtonC1.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC1.setText("Consult");
        jButtonC1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC1ActionPerformed(evt);
            }
        });

        jTextPaneC1.setEditable(false);
        jScrollPane1.setViewportView(jTextPaneC1);

        jButtonC2.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC2.setText("Back");
        jButtonC2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxEmpresaC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButtonC2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonC1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(24, 24, 24))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxEmpresaC1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonC1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jButtonC2))
        );

        jTabbedPane1.addTab("Ver Empresas", jPanel1);

        jPanel2.setBackground(new java.awt.Color(217, 239, 255));

        jLabel5.setText("TECNOLOGIA:");

        jComboBoxEmpresaC7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmpresaC7ActionPerformed(evt);
            }
        });

        jTextPaneC7.setEditable(false);
        jScrollPane2.setViewportView(jTextPaneC7);

        jButtonC7.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC7.setText("Consult");
        jButtonC7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC7ActionPerformed(evt);
            }
        });

        jButtonBack1.setBackground(new java.awt.Color(239, 248, 255));
        jButtonBack1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonBack1.setText("Back");
        jButtonBack1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBack1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonBack1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxEmpresaC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jButtonC7, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBoxEmpresaC7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonC7))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBack1)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Tecnologias", jPanel2);

        jPanel3.setBackground(new java.awt.Color(217, 239, 255));

        jLabel7.setText("EMPRESA:");

        jComboBoxEmpresaC8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmpresaC8ActionPerformed(evt);
            }
        });

        jTextPaneC8.setEditable(false);
        jScrollPane3.setViewportView(jTextPaneC8);

        jButtonBack2.setBackground(new java.awt.Color(239, 248, 255));
        jButtonBack2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonBack2.setText("Back");
        jButtonBack2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonBack2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBack2ActionPerformed(evt);
            }
        });

        jButtonC8.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC8.setText("Consult");
        jButtonC8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButtonC8, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxEmpresaC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonBack2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxEmpresaC8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonC8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonBack2)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Historial", jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC1ActionPerformed
        // TODO add your handling code here:
        String empresa = jComboBoxEmpresaC1.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC1(empresa);
        actualizarTextPaneC1(resultados);
    }//GEN-LAST:event_jButtonC1ActionPerformed

    private void jComboBoxEmpresaC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmpresaC7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEmpresaC7ActionPerformed

    private void jButtonC7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC7ActionPerformed
        // TODO add your handling code here
        String tecnologia = jComboBoxEmpresaC7.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC7(tecnologia);
        actualizarTextPaneC7(resultados);
    }//GEN-LAST:event_jButtonC7ActionPerformed

    private void jComboBoxEmpresaC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmpresaC8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEmpresaC8ActionPerformed

    private void jButtonBack2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogConsult menu = new ConfigDialogConsult(null, true);
        menu.setVisible(true);
    }//GEN-LAST:event_jButtonBack2ActionPerformed

    private void jComboBoxEmpresaC1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmpresaC1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEmpresaC1ActionPerformed

    private void jButtonC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogConsult menu = new ConfigDialogConsult(null, true);
        menu.setVisible(true);
    }//GEN-LAST:event_jButtonC2ActionPerformed

    private void jButtonBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogConsult menu = new ConfigDialogConsult(null, true);
        menu.setVisible(true);
    }//GEN-LAST:event_jButtonBack1ActionPerformed

    private void jButtonC8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC8ActionPerformed
        // TODO add your handling code here:
        String historial = jComboBoxEmpresaC8.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC8(historial);
        actualizarTextPaneC8(resultados);
    }//GEN-LAST:event_jButtonC8ActionPerformed
    


    
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
            java.util.logging.Logger.getLogger(ConfigDialogEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogEmpresa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfigDialogEmpresa dialog = new ConfigDialogEmpresa(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonBack1;
    private javax.swing.JButton jButtonBack2;
    private javax.swing.JButton jButtonC1;
    private javax.swing.JButton jButtonC2;
    private javax.swing.JButton jButtonC7;
    private javax.swing.JButton jButtonC8;
    private javax.swing.JComboBox<String> jComboBoxEmpresaC1;
    private javax.swing.JComboBox<String> jComboBoxEmpresaC7;
    private javax.swing.JComboBox<String> jComboBoxEmpresaC8;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPaneC1;
    private javax.swing.JTextPane jTextPaneC7;
    private javax.swing.JTextPane jTextPaneC8;
    // End of variables declaration//GEN-END:variables

    
}

