/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import com.mycompany.fct_project.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AdminAlex
 */
public class ConfigDialogGestionTutorFCT extends javax.swing.JDialog {

    private int profesorId;
    private String nombreGrupo;
    private String role;

    public ConfigDialogGestionTutorFCT(java.awt.Frame parent, boolean modal, String role) {
        super(parent, modal);
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null); // Centrar el diálogo
        this.role = role;
        setFieldsEditable(false);
        agregarListeners();
        obtenerNombreGrupo(); // Establecer el nombre del grupo al inicio
        jTextFieldGrupo.setText(nombreGrupo);
        jTextFieldGrupo.setEditable(false);
        
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
        obtenerNombreGrupo(); // Cargar el nombre del grupo después de establecer el profesorId
        cargarDatosTabla(); // Cargar los datos de la tabla después de establecer el profesorId
        jTextFieldGrupo.setText(nombreGrupo); // Establecer el nombre del grupo en el campo de texto
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

    private void setFieldsEditable(boolean editable) {
        jTextFieldIdEmpresa.setEditable(editable);
        jTextFieldCursoEscolar.setEditable(editable);
        jTextFieldPeriodo.setEditable(editable);
        jTextFieldNumAlumnos.setEditable(editable);
        jTextFieldGrupo.setEditable(false); // El campo grupo no es editable
    }
    
    private void setFieldsEditableModify(boolean editable) {
        jTextFieldIdEmpresa.setEditable(false);
        jTextFieldCursoEscolar.setEditable(false);
        jTextFieldPeriodo.setEditable(editable);
        jTextFieldNumAlumnos.setEditable(editable);
        jTextFieldGrupo.setEditable(false); // El campo grupo no es editable
    }


    private void agregarListeners() {
        jTableRealizanFCT.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTableRealizanFCT.getSelectedRow() != -1) {
                mostrarDatosSeleccionados();
            }
        });

        jButtonNuevo.addActionListener(evt -> nuevoRegistro());
        jButtonAgregar.addActionListener(evt -> agregarRegistro());
        jButtonModificar.addActionListener(evt -> {
            setFieldsEditable(true);
            jButtonGuardar.setEnabled(true);
        });
        jButtonGuardar.addActionListener(evt -> {
            guardarCambios();
            setFieldsEditable(false);
            jButtonGuardar.setEnabled(false);
        });
        jButtonEliminar.addActionListener(evt -> eliminarRegistro());
    }

    private void mostrarDatosSeleccionados() {
        int selectedRow = jTableRealizanFCT.getSelectedRow();
        jTextFieldIdEmpresa.setText(jTableRealizanFCT.getValueAt(selectedRow, 0).toString());
        jTextFieldGrupo.setText(jTableRealizanFCT.getValueAt(selectedRow, 1).toString());
        jTextFieldCursoEscolar.setText(jTableRealizanFCT.getValueAt(selectedRow, 2).toString());
        jTextFieldPeriodo.setText(jTableRealizanFCT.getValueAt(selectedRow, 3).toString());
        jTextFieldNumAlumnos.setText(jTableRealizanFCT.getValueAt(selectedRow, 4).toString());
        setFieldsEditable(false);
    }

    private void nuevoRegistro() {
        jTextFieldIdEmpresa.setText("");
        jTextFieldCursoEscolar.setText("");
        jTextFieldPeriodo.setText("");
        jTextFieldNumAlumnos.setText("");
        jTextFieldGrupo.setText(nombreGrupo); // Establecer el nombre del grupo al agregar un nuevo registro
        setFieldsEditable(true);
        jTableRealizanFCT.clearSelection(); // Deseleccionar cualquier fila seleccionada
    }

    private void agregarRegistro() {
        String idEmpresa = jTextFieldIdEmpresa.getText();
        String cursoEscolar = jTextFieldCursoEscolar.getText();
        String periodo = jTextFieldPeriodo.getText();
        int numAlumnos = Integer.parseInt(jTextFieldNumAlumnos.getText());

        String query = "INSERT INTO REALIZAN_FCT (idempresa, nombregrupo, cursoescolar, periodo, num_alu_asignados) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, idEmpresa);
            stmt.setString(2, nombreGrupo);
            stmt.setString(3, cursoEscolar);
            stmt.setString(4, periodo);
            stmt.setInt(5, numAlumnos);

            stmt.executeUpdate();
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Registro agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarCambios() {
        int selectedRow = jTableRealizanFCT.getSelectedRow();
        if (selectedRow != -1) {
            actualizarRegistro(selectedRow);
        }
    }

    private void actualizarRegistro(int row) {
        String idEmpresa = jTextFieldIdEmpresa.getText();
        String cursoEscolar = jTextFieldCursoEscolar.getText();
        String periodo = jTextFieldPeriodo.getText();
        int numAlumnos = Integer.parseInt(jTextFieldNumAlumnos.getText());

        String query = "UPDATE REALIZAN_FCT SET periodo = ?, num_alu_asignados = ? WHERE idempresa = ? AND nombregrupo = ? AND cursoescolar = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, periodo);
            stmt.setInt(2, numAlumnos);
            stmt.setString(3, idEmpresa);
            stmt.setString(4, nombreGrupo);
            stmt.setString(5, cursoEscolar);

            stmt.executeUpdate();
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Registro actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro() {
        int selectedRow = jTableRealizanFCT.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idEmpresa = jTextFieldIdEmpresa.getText();
        String cursoEscolar = jTextFieldCursoEscolar.getText();
        String periodo = jTextFieldPeriodo.getText();

        String query = "DELETE FROM REALIZAN_FCT WHERE idempresa = ? AND nombregrupo = ? AND cursoescolar = ? AND periodo = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, idEmpresa);
            stmt.setString(2, nombreGrupo);
            stmt.setString(3, cursoEscolar);
            stmt.setString(4, periodo);

            stmt.executeUpdate();
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
            return false; // No permitir edición en la tabla directamente
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

        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldCursoEscolar = new javax.swing.JTextField();
        jTextFieldIdEmpresa = new javax.swing.JTextField();
        jTextFieldPeriodo = new javax.swing.JTextField();
        jTextFieldNumAlumnos = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldGrupo = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonNuevo = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableRealizanFCT = new javax.swing.JTable();
        jButtonGuardar = new javax.swing.JButton();
        jButtonBack1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(217, 239, 255));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("ID EMPRESA");

        jLabel5.setText("CURSO ESCOLAR");

        jLabel6.setText("PERIODO");

        jTextFieldCursoEscolar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCursoEscolarActionPerformed(evt);
            }
        });

        jTextFieldIdEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdEmpresaActionPerformed(evt);
            }
        });

        jTextFieldPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPeriodoActionPerformed(evt);
            }
        });

        jTextFieldNumAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumAlumnosActionPerformed(evt);
            }
        });

        jLabel7.setText("GRUPO");

        jLabel8.setText("NUMERO ALUMNOS");

        jTextFieldGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGrupoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldIdEmpresa)
                    .addComponent(jTextFieldNumAlumnos)
                    .addComponent(jTextFieldCursoEscolar)
                    .addComponent(jTextFieldPeriodo)
                    .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCursoEscolar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNumAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonAgregar.setText("ADD");
        jButtonAgregar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonModificar.setText("MODIFY");
        jButtonModificar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setText("DELETE");
        jButtonEliminar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonNuevo.setText("NEW");
        jButtonNuevo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
        jTableRealizanFCT.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTableRealizanFCT);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 522, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonGuardar.setText("Save");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jButtonBack1.setText("Back");
        jButtonBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBack1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBack1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack1)
                    .addComponent(jButtonGuardar))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldCursoEscolarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCursoEscolarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCursoEscolarActionPerformed

    private void jTextFieldIdEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdEmpresaActionPerformed

    private void jTextFieldPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPeriodoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPeriodoActionPerformed

    private void jButtonAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAgregarActionPerformed

    private void jButtonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarActionPerformed
        // TODO add your handling code here:
        setFieldsEditableModify(true);
    }//GEN-LAST:event_jButtonModificarActionPerformed

    private void jButtonEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarActionPerformed

    private void jButtonNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNuevoActionPerformed

    private void jButtonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarActionPerformed
        // TODO add your handling code here:
        guardarCambios();
    }//GEN-LAST:event_jButtonGuardarActionPerformed

    private void jButtonBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionTutorFCT.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonBack1ActionPerformed

    private void jTextFieldNumAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumAlumnosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumAlumnosActionPerformed

    private void jTextFieldGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGrupoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGrupoActionPerformed

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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */

        /* Crear y mostrar el diálogo */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String role = "TUTOR_FCT";
                
                // Crear una instancia de ConfigDialogGestionTutorFCT con los argumentos requeridos
                ConfigDialogGestionTutorFCT dialog = new ConfigDialogGestionTutorFCT(new javax.swing.JFrame(), true, role);
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
    private javax.swing.JButton jButtonAgregar;
    private javax.swing.JButton jButtonBack1;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableRealizanFCT;
    private javax.swing.JTextField jTextFieldCursoEscolar;
    private javax.swing.JTextField jTextFieldGrupo;
    private javax.swing.JTextField jTextFieldIdEmpresa;
    private javax.swing.JTextField jTextFieldNumAlumnos;
    private javax.swing.JTextField jTextFieldPeriodo;
    // End of variables declaration//GEN-END:variables
}
