/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AdminAlex
 */
public class ConfigDialogGestionTutorFCT extends javax.swing.JDialog {
    
    public int profesorId;
    private String nombreGrupo;

    /**
     * Creates new form ConfigDialogGestionTutorFCT
     */
    

    public ConfigDialogGestionTutorFCT(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        obtenerNombreGrupo();
                

        // Configurar tabla
        jTableRealizanFCT.setModel(new RealizanFCTTableModel());
        
        // Configurar botones
        jButtonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                agregarRegistro();
            }
        });

        jButtonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int row = jTableRealizanFCT.getSelectedRow();
                if (row >= 0) {
                    guardarRegistro(row);
                }
            }
        });

        jButtonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int row = jTableRealizanFCT.getSelectedRow();
                if (row >= 0) {
                    actualizarRegistro(row);
                }
            }
        });

        jButtonBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int row = jTableRealizanFCT.getSelectedRow();
                if (row >= 0) {
                    borrarRegistro(row);
                }
            }
        });

        pack();
        setLocationRelativeTo(null); // Centrar el diálogo
    }
    
     public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
        obtenerNombreGrupo(); // Cargar el nombre del grupo después de establecer el profesorId
        cargarDatosTabla(); // Cargar los datos de la tabla después de establecer el profesorId
    }
     
    private void obtenerNombreGrupo() {
        String query = "SELECT nombreGrupo FROM grupo WHERE idprofe = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
             
            stmt.setInt(1, profesorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                this.nombreGrupo = rs.getString("nombreGrupo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     private void cargarDatosTabla() {
        RealizanFCTTableModel model = new RealizanFCTTableModel();

        String query = "SELECT idempresa, nombregrupo, cursoescolar, periodo, num_alu_asignados FROM REALIZAN_FCT WHERE nombregrupo = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
             
            stmt.setString(1, nombreGrupo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String idEmpresa = rs.getString("idempresa");
                String nombreGrupo = rs.getString("nombregrupo");
                String cursoEscolar = rs.getString("cursoescolar");
                String periodo = rs.getString("periodo");
                int numAlumnos = rs.getInt("num_alu_asignados");

                model.addRow(new Object[]{idEmpresa, nombreGrupo, cursoEscolar, periodo, numAlumnos});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        jTableRealizanFCT.setModel(model);
    }
    
    
    private void agregarRegistro() {
     RealizanFCTTableModel model = (RealizanFCTTableModel) jTableRealizanFCT.getModel();
     Object[] newRowData = new Object[5];
     newRowData[0] = ""; // ID Empresa
     newRowData[1] = nombreGrupo; // Nombre del grupo (no editable)
     newRowData[2] = ""; // Curso Escolar
     newRowData[3] = ""; // Periodo
     newRowData[4] = 0; // Número de Alumnos

     model.addRow(newRowData); // Añadir una fila vacía para edición
    }

    // Acción del botón "Guardar" para añadir registro
    private void guardarRegistro(int row) {
        RealizanFCTTableModel model = (RealizanFCTTableModel) jTableRealizanFCT.getModel();
        String idEmpresa = model.getValueAt(row, 0).toString();
        String nombreGrupo = model.getValueAt(row, 1).toString();
        String cursoEscolar = model.getValueAt(row, 2).toString();
        String periodo = model.getValueAt(row, 3).toString();
        int numAlumnos = Integer.parseInt(model.getValueAt(row, 4).toString());

        String query = "INSERT INTO REALIZAN_FCT (idempresa, nombregrupo, cursoescolar, periodo, num_alu_asignados) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, idEmpresa);
            stmt.setString(2, nombreGrupo);
            stmt.setString(3, cursoEscolar);
            stmt.setString(4, periodo);
            stmt.setInt(5, numAlumnos);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Acción del botón "Actualizar" para guardar cambios
    private void actualizarRegistro(int row) {
        RealizanFCTTableModel model = (RealizanFCTTableModel) jTableRealizanFCT.getModel();
        String idEmpresa = model.getValueAt(row, 0).toString();
        String nombreGrupo = model.getValueAt(row, 1).toString();
        String cursoEscolar = model.getValueAt(row, 2).toString();
        String periodo = model.getValueAt(row, 3).toString();
        int numAlumnos = Integer.parseInt(model.getValueAt(row, 4).toString());

        String query = "UPDATE REALIZAN_FCT SET periodo = ?, num_alu_asignados = ? WHERE idempresa = ? AND nombregrupo = ? AND cursoescolar = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(2, numAlumnos);
            stmt.setString(3, idEmpresa);
            stmt.setString(4, nombreGrupo);
            stmt.setString(5, cursoEscolar);
            stmt.setString(1, periodo);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Acción del botón "Borrar" para eliminar registro
    private void borrarRegistro(int row) {
        RealizanFCTTableModel model = (RealizanFCTTableModel) jTableRealizanFCT.getModel();
        String idEmpresa = model.getValueAt(row, 0).toString();
        String nombreGrupo = model.getValueAt(row, 1).toString();
        String cursoEscolar = model.getValueAt(row, 2).toString();
        String periodo = model.getValueAt(row, 3).toString();

        String query = "DELETE FROM REALIZAN_FCT WHERE idempresa = ? AND nombregrupo = ? AND cursoescolar = ? AND periodo = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, idEmpresa);
            stmt.setString(2, nombreGrupo);
            stmt.setString(3, cursoEscolar);
            stmt.setString(4, periodo);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.removeRow(row); // Eliminar la fila de la tabla
    }



    
    class RealizanFCTTableModel extends DefaultTableModel {
        private final String[] columnNames = {"ID Empresa", "Nombre Grupo", "Curso Escolar", "Periodo", "Número de Alumnos"};
        private final Class<?>[] columnTypes = {String.class, String.class, String.class, String.class, Integer.class};

        public RealizanFCTTableModel() {
            super();
            setColumnIdentifiers(columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
        // Permitir la edición en todas las columnas si es una nueva fila
        if (row == getRowCount() - 1) {
            return true;
        } else {
            // Permitir la edición solo en las columnas "Periodo" y "Número de Alumnos" en filas existentes
            return column == 3 || column == 4;
        }
}

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
        }
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTableRealizanFCT = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        jButtonAgregar = new javax.swing.JButton();
        jButtonGuardar = new javax.swing.JButton();
        jButtonActualizar = new javax.swing.JButton();
        jButtonBorrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTableRealizanFCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(jTableRealizanFCT);

        jToolBar1.setRollover(true);

        jButtonAgregar.setText("Agregar");
        jButtonAgregar.setFocusable(false);
        jButtonAgregar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonAgregar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonAgregar);

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.setFocusable(false);
        jButtonGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });
        jToolBar1.add(jButtonGuardar);

        jButtonActualizar.setText("Actualizar");
        jButtonActualizar.setFocusable(false);
        jButtonActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonActualizar);

        jButtonBorrar.setText("Borrar");
        jButtonBorrar.setFocusable(false);
        jButtonBorrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonBorrar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButtonBorrar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */

        /* Crear y mostrar el diálogo */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crear una instancia de ConfigDialogGestionTutorFCT con los argumentos requeridos
                ConfigDialogGestionTutorFCT dialog = new ConfigDialogGestionTutorFCT(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonActualizar;
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonBorrar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableRealizanFCT;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
}
