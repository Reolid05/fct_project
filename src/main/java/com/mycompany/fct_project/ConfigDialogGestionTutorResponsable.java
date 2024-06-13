/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AdminAlex
 */
public class ConfigDialogGestionTutorResponsable extends javax.swing.JDialog {

    private int profesorId;
    private String nombreGrupo;
    private String role;
    
    /**
     * Creates new form ConfigDialogGestionTutorResponsable
     */
    public ConfigDialogGestionTutorResponsable(java.awt.Frame parent, boolean modal, String role) {
        super(parent, modal);
        initComponents();
        setResizable(false);
        setLocationRelativeTo(null);
        this.role = role;
        setFieldsEditable(false);
        setFieldsEditableModify(false);
        agregarListeners();
        agregarListeners2();
        obtenerNombreGrupo();
        jTextFieldGrupo2.setText(nombreGrupo);
        jTextFieldGrupo2.setEditable(false);
        cargarDatosTablaPrevisionFct();
        cargarDatosTablaIncidencias();
        mostrarDatosSeleccionados();
        mostrarDatosSeleccionados2();
        agregarListeners2();
        agregarListenersIncidencias();
    }
    
    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        return (Date) formatter.parse(date);
    }

    public int getProfesorId() {
        return profesorId;
    }
    
    public void setProfesorId(int profesorId) {
        this.profesorId = profesorId;
        obtenerNombreGrupo(); // Cargar el nombre del grupo después de establecer el profesorId
        cargarDatosTabla(); // Cargar los datos de la tabla después de establecer el profesorId
        jTextFieldGrupo2.setText(nombreGrupo); // Establecer el nombre del grupo en el campo de texto
    }
    
    private void agregarListeners() {
        // Listener para jTablePrevisonFct
        jTablePrevisonFct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarDatosSeleccionados();
                }
            }
        });

        // Listener para jButtonModifyProfe
        jButtonModifyProfe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Botón Modify lógica
            }
        });

        // Listener para jButtonSaveProfe
        jButtonSaveProfe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Botón Save lógica
            }
        });

        // Listener para jButtonBack1
        jButtonPlazasBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Botón Back lógica
                ConfigDialogLogin loginDialog = new ConfigDialogLogin(new javax.swing.JFrame(), true);
                String role = loginDialog.getRole();
                setVisible(false);
                ConfigDialogMenu menu = new ConfigDialogMenu(new javax.swing.JFrame(), true, role);
                menu.setVisible(true);
            }
        });
    }

    private void mostrarDatosSeleccionados() {
        int selectedRow = jTablePrevisonFct.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < jTablePrevisonFct.getRowCount()) {
            jTextFieldNombreEmpresa.setText(jTablePrevisonFct.getValueAt(selectedRow, 0).toString());
            jTextFieldNombreCiclo.setText(jTablePrevisonFct.getValueAt(selectedRow, 1).toString());
            jTextFieldCursoCiclo.setText(jTablePrevisonFct.getValueAt(selectedRow, 2).toString());
            jTextFieldNumPlazas.setText(jTablePrevisonFct.getValueAt(selectedRow, 3).toString());
            setFieldsUnEditable2(true);
        }
    }

    private void setFieldsUnEditable2(boolean editable){
        jTextFieldNombreEmpresa.setEditable(!editable);
        jTextFieldNombreCiclo.setEditable(!editable);
        jTextFieldCursoCiclo.setEditable(!editable);
        jTextFieldNumPlazas.setEditable(!editable);
    }
    
    private void setFieldsEditable2(boolean editable) {
        jTextFieldNombreEmpresa.setEditable(!editable);
        jTextFieldNombreCiclo.setEditable(!editable);
        jTextFieldCursoCiclo.setEditable(!editable);
        jTextFieldNumPlazas.setEditable(editable);
    }

    private void cargarDatosTablaPrevisionFct() {
        DefaultTableModel model = (DefaultTableModel) jTablePrevisonFct.getModel();
        model.setRowCount(0);
        String query = "SELECT p.idempresa, e.nombre AS nombre_empresa, p.idciclo, c.ciclo AS nombre_ciclo, c.curso AS curso_ciclo, " +
                   "p.cursoescolar, p.periodo, p.solicitaAlu, p.acogeAlu " +
                   "FROM prevision_FCT p " +
                   "JOIN EMPRESA e ON p.idempresa = e.idempresa " +
                   "JOIN CICLO c ON p.idciclo = c.idciclo";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                try {
                    String nombreEmpresa = rs.getString("nombre_empresa");
                    String nombreCiclo = rs.getString("nombre_ciclo");
                    String cursoCiclo = rs.getString("curso_ciclo"); 
                    int plazas = rs.getInt("solicitaalu");
                    model.addRow(new Object[]{nombreEmpresa, nombreCiclo, cursoCiclo, plazas});
                } catch (SQLException e) {
                    System.err.println("Error processing row: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void modificarPlazas() {
        DefaultTableModel model = (DefaultTableModel) jTablePrevisonFct.getModel();
        int selectedRow = jTablePrevisonFct.getSelectedRow();

        if (selectedRow != -1) {
            String nombreEmpresa = jTextFieldNombreEmpresa.getText();
            String nombreCiclo = jTextFieldNombreCiclo.getText();
            String cursoCiclo = jTextFieldCursoCiclo.getText();
            int nuevasPlazas = Integer.parseInt(jTextFieldNumPlazas.getText());

            String query = "UPDATE prevision_FCT " +
                           "SET solicitaAlu = ? " +
                           "WHERE idempresa = ? AND idciclo = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {

                // Obtener el ID de la empresa y el ciclo desde la tabla
                String selectIdQuery = "SELECT p.idempresa, p.idciclo " +
                                       "FROM prevision_FCT p " +
                                       "JOIN EMPRESA e ON p.idempresa = e.idempresa " +
                                       "JOIN CICLO c ON p.idciclo = c.idciclo " +
                                       "WHERE e.nombre = ? AND c.ciclo = ? AND c.curso = ?";

                try (PreparedStatement selectIdStmt = conn.prepareStatement(selectIdQuery)) {
                    selectIdStmt.setString(1, nombreEmpresa);
                    selectIdStmt.setString(2, nombreCiclo);
                    selectIdStmt.setString(3, cursoCiclo);

                    ResultSet rs = selectIdStmt.executeQuery();

                    if (rs.next()) {
                        String idEmpresa = rs.getString("idempresa");
                        int idCiclo = rs.getInt("idciclo");

                        // Establecer los parámetros para la actualización
                        pstmt.setInt(1, nuevasPlazas);
                        pstmt.setString(2, idEmpresa);
                        pstmt.setInt(3, idCiclo);

                        // Ejecutar la actualización
                        int rowsAffected = pstmt.executeUpdate();

                        if (rowsAffected > 0) {
                            // Éxito en la actualización
                            JOptionPane.showMessageDialog(this, "Plazas modificadas exitosamente.");

                            // Actualizar la tabla con el nuevo valor
                            model.setValueAt(nuevasPlazas, selectedRow, 3);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error al modificar las plazas.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "No se encontró la empresa y ciclo especificados en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error de conexión con la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Formato de número incorrecto para las plazas.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una fila para modificar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    //Gestion de FCT

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
    
    private void agregarListeners2() {
        jTableRealizanFCT.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTableRealizanFCT.getSelectedRow() != -1) {
                mostrarDatosSeleccionados2();
            }
        });

        jButtonNuevo.addActionListener(evt -> nuevoRegistro());
        jButtonAgregar.addActionListener(evt -> agregarRegistro());
        jButtonModificar.addActionListener(evt -> {
            setFieldsEditableModify(true);
            jButtonGuardar.setEnabled(true);
        });
        jButtonGuardar.addActionListener(evt -> {
            guardarCambios();
            setFieldsEditableModify(false);
            jButtonGuardar.setEnabled(false);
        });
        jButtonEliminar.addActionListener(evt -> eliminarRegistro());
    }

    private void mostrarDatosSeleccionados2() {
        int selectedRow = jTableRealizanFCT.getSelectedRow();
        if (selectedRow >= 0) {
            jTextFieldIdEmpresa2.setText(jTableRealizanFCT.getValueAt(selectedRow, 0).toString());
            jTextFieldGrupo2.setText(jTableRealizanFCT.getValueAt(selectedRow, 1).toString());
            jTextFieldCursoEscolar2.setText(jTableRealizanFCT.getValueAt(selectedRow, 2).toString());
            jTextFieldPeriodo2.setText(jTableRealizanFCT.getValueAt(selectedRow, 3).toString());
            jTextFieldNumAlumnos2.setText(jTableRealizanFCT.getValueAt(selectedRow, 4).toString());
            setFieldsEditableModify(false);
        }
    }

    private void setFieldsEditableModify(boolean editable) {
        jTextFieldPeriodo2.setEditable(editable);
        jTextFieldNumAlumnos2.setEditable(editable);
        jTextFieldIdEmpresa2.setEditable(false);
        jTextFieldCursoEscolar2.setEditable(false);
        jTextFieldGrupo2.setEditable(false);
    }

    private void setFieldsEditable(boolean editable) {
        jTextFieldIdEmpresa2.setEditable(editable);
        jTextFieldCursoEscolar2.setEditable(editable);
        jTextFieldPeriodo2.setEditable(editable);
        jTextFieldNumAlumnos2.setEditable(editable);
        jTextFieldGrupo2.setEditable(false); // El campo grupo no es editable
    }

    private void nuevoRegistro() {
        jTextFieldIdEmpresa2.setText("");
        jTextFieldCursoEscolar2.setText("");
        jTextFieldPeriodo2.setText("");
        jTextFieldNumAlumnos2.setText("");
        jTextFieldGrupo2.setText(nombreGrupo); // Establecer el nombre del grupo al agregar un nuevo registro
        setFieldsEditable(true);
        jTableRealizanFCT.clearSelection(); // Deseleccionar cualquier fila seleccionada
    }

    private void agregarRegistro() {
        String idEmpresa = jTextFieldIdEmpresa2.getText();
        String cursoEscolar = jTextFieldCursoEscolar2.getText();
        String periodo = jTextFieldPeriodo2.getText();
        int numAlumnos = Integer.parseInt(jTextFieldNumAlumnos2.getText());

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
        String idEmpresa = jTextFieldIdEmpresa2.getText();
        String cursoEscolar = jTextFieldCursoEscolar2.getText();
        String periodo = jTextFieldPeriodo2.getText();
        int numAlumnos = Integer.parseInt(jTextFieldNumAlumnos2.getText());

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

        String idEmpresa = jTextFieldIdEmpresa2.getText();
        String cursoEscolar = jTextFieldCursoEscolar2.getText();
        String periodo = jTextFieldPeriodo2.getText();

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
    
    //Gestion de incidencias
    
    private void cargarDatosTablaIncidencias() {
        DefaultTableModel model = (DefaultTableModel) jTableInciedencias.getModel();
        model.setRowCount(0);
        String query = "SELECT * FROM incidencia ORDER BY numincidencia";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int numincidencia = rs.getInt("numincidencia");
                Date fechaincidencia = rs.getDate("fechaincidencia");
                String observaciones = rs.getString("observaciones");
                String idempresa = rs.getString("idempresa");
                String cursoescolar = rs.getString("cursoescolar");
                model.addRow(new Object[]{numincidencia, fechaincidencia, observaciones, idempresa, cursoescolar});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarDatosSeleccionadosIncidencias() {
        int selectedRow = jTableInciedencias.getSelectedRow();
        if (selectedRow >= 0) {
            jTextFieldNumeroIncidencia.setText(jTableInciedencias.getValueAt(selectedRow, 0).toString());
            jTextFieldFechaIncidencia.setText(jTableInciedencias.getValueAt(selectedRow, 1).toString());
            jTextFieldObservacionesIncidencia.setText(jTableInciedencias.getValueAt(selectedRow, 2).toString());
            jTextFieldIdEmpresaIncidencia.setText(jTableInciedencias.getValueAt(selectedRow, 3).toString());
            jTextFieldCursoEscolarIncidencia.setText(jTableInciedencias.getValueAt(selectedRow, 4).toString());
            setFieldsEditableIncidencias(false);
        }
    }
    
   
    private void nuevoRegistroIncidencia() {
         jTextFieldNumeroIncidencia.setText("");
         jTextFieldFechaIncidencia.setText("");
         jTextFieldObservacionesIncidencia.setText("");
         jTextFieldIdEmpresaIncidencia.setText("");
         jTextFieldCursoEscolarIncidencia.setText("");
         setFieldsEditableIncidencias(true);
     }
   
    private void setFieldsEditableIncidencias(boolean editable) {
        jTextFieldNumeroIncidencia.setEditable(editable);
        jTextFieldFechaIncidencia.setEditable(editable);
        jTextFieldObservacionesIncidencia.setEditable(editable);
        jTextFieldIdEmpresaIncidencia.setEditable(editable);
        jTextFieldCursoEscolarIncidencia.setEditable(editable);
    }
    
    private void agregarListenersIncidencias() {
        jTableInciedencias.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarDatosSeleccionadosIncidencias();
                }
            }
        });

        jButtonModificarIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFieldsEditableIncidencias(true);
            }
        });

        jButtonSaveIncidencias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarIncidencia();
                setFieldsEditableIncidencias(false);
            }
        });

        jButtonNuevaIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nuevoRegistroIncidencia();
            }
        });

        jButtonAgregarIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarIncidencia();
            }
        });

        jButtonEliminarIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarIncidencia();
            }
        });
    }
    
    private void agregarIncidencia() {
        int numIncidencia = Integer.parseInt(jTextFieldNumeroIncidencia.getText());
        Date fechaIncidencia = Date.valueOf(jTextFieldFechaIncidencia.getText());
        String observaciones = jTextFieldObservacionesIncidencia.getText();
        String idEmpresa = jTextFieldIdEmpresaIncidencia.getText();
        String cursoEscolar = jTextFieldCursoEscolarIncidencia.getText();

        String query = "INSERT INTO incidencia (numincidencia, fechaincidencia, observaciones, idempresa, cursoescolar) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, numIncidencia);
            stmt.setDate(2, fechaIncidencia);
            stmt.setString(3, observaciones);
            stmt.setString(4, idEmpresa);
            stmt.setString(5, cursoEscolar);

            stmt.executeUpdate();
            cargarDatosTablaIncidencias();
            JOptionPane.showMessageDialog(this, "Incidencia agregada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar la incidencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void guardarIncidencia() {
        int numIncidencia = Integer.parseInt(jTextFieldNumeroIncidencia.getText());
        Date fechaIncidencia = Date.valueOf(jTextFieldFechaIncidencia.getText());
        String observaciones = jTextFieldObservacionesIncidencia.getText();
        String idEmpresa = jTextFieldIdEmpresaIncidencia.getText();
        String cursoEscolar = jTextFieldCursoEscolarIncidencia.getText();

        String query = "UPDATE incidencia SET fechaincidencia = ?, observaciones = ?, idempresa = ?, cursoescolar = ? WHERE numincidencia = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setDate(1, fechaIncidencia);
            stmt.setString(2, observaciones);
            stmt.setString(3, idEmpresa);
            stmt.setString(4, cursoEscolar);
            stmt.setInt(5, numIncidencia);

            stmt.executeUpdate();
            cargarDatosTablaIncidencias();
            JOptionPane.showMessageDialog(this, "Incidencia actualizada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la incidencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarIncidencia() {
        int selectedRow = jTableInciedencias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una incidencia para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numIncidencia = Integer.parseInt(jTextFieldNumeroIncidencia.getText());

        String query = "DELETE FROM incidencia WHERE numincidencia = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, numIncidencia);
            stmt.executeUpdate();
            cargarDatosTablaIncidencias();
            JOptionPane.showMessageDialog(this, "Incidencia eliminada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la incidencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldCursoEscolar2 = new javax.swing.JTextField();
        jTextFieldIdEmpresa2 = new javax.swing.JTextField();
        jTextFieldPeriodo2 = new javax.swing.JTextField();
        jTextFieldNumAlumnos2 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldGrupo2 = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jButtonAgregar = new javax.swing.JButton();
        jButtonModificar = new javax.swing.JButton();
        jButtonEliminar = new javax.swing.JButton();
        jButtonNuevo = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTableRealizanFCT = new javax.swing.JTable();
        jButtonGuardar = new javax.swing.JButton();
        jButtonFCTBack = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNombreCiclo = new javax.swing.JTextField();
        jTextFieldNombreEmpresa = new javax.swing.JTextField();
        jTextFieldNumPlazas = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldCursoCiclo = new javax.swing.JTextField();
        jButtonModifyProfe = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTablePrevisonFct = new javax.swing.JTable();
        jButtonSaveProfe = new javax.swing.JButton();
        jButtonPlazasBack = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableInciedencias = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButtonNuevaIncidencia = new javax.swing.JButton();
        jButtonAgregarIncidencia = new javax.swing.JButton();
        jButtonEliminarIncidencia = new javax.swing.JButton();
        jButtonModificarIncidencia = new javax.swing.JButton();
        jButtonSaveIncidencias = new javax.swing.JButton();
        jButtonIncidenciasBack = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldFechaIncidencia = new javax.swing.JTextField();
        jTextFieldObservacionesIncidencia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldIdEmpresaIncidencia = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldCursoEscolarIncidencia = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jTextFieldNumeroIncidencia = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(190, 228, 255));

        jPanel11.setBackground(new java.awt.Color(217, 239, 255));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setText("ID EMPRESA");

        jLabel19.setText("CURSO ESCOLAR");

        jLabel20.setText("PERIODO");

        jTextFieldCursoEscolar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCursoEscolar2ActionPerformed(evt);
            }
        });

        jTextFieldIdEmpresa2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdEmpresa2ActionPerformed(evt);
            }
        });

        jTextFieldPeriodo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPeriodo2ActionPerformed(evt);
            }
        });

        jTextFieldNumAlumnos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumAlumnos2ActionPerformed(evt);
            }
        });

        jLabel21.setText("GRUPO");

        jLabel22.setText("NUMERO ALUMNOS");

        jTextFieldGrupo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGrupo2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldIdEmpresa2)
                    .addComponent(jTextFieldNumAlumnos2)
                    .addComponent(jTextFieldCursoEscolar2)
                    .addComponent(jTextFieldPeriodo2)
                    .addComponent(jTextFieldGrupo2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIdEmpresa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldGrupo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCursoEscolar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPeriodo2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNumAlumnos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonAgregar.setText("AÑADIR");
        jButtonAgregar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarActionPerformed(evt);
            }
        });

        jButtonModificar.setText("MODIFICAR");
        jButtonModificar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarActionPerformed(evt);
            }
        });

        jButtonEliminar.setText("ELIMINAR");
        jButtonEliminar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarActionPerformed(evt);
            }
        });

        jButtonNuevo.setText("CREAR");
        jButtonNuevo.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90))
        );

        jButtonGuardar.setText("Guardar");
        jButtonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarActionPerformed(evt);
            }
        });

        jButtonFCTBack.setText("Retroceder");
        jButtonFCTBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFCTBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jButtonGuardar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonFCTBack)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGuardar)
                    .addComponent(jButtonFCTBack))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gestión de FCT", jPanel11);

        jPanel4.setBackground(new java.awt.Color(217, 239, 255));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("EMPRESA");

        jLabel5.setText("CICLO");

        jLabel6.setText("CURSO");

        jTextFieldNombreCiclo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreCicloActionPerformed(evt);
            }
        });

        jTextFieldNombreEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreEmpresaActionPerformed(evt);
            }
        });

        jTextFieldNumPlazas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumPlazasActionPerformed(evt);
            }
        });

        jLabel7.setText("PLAZAS");

        jTextFieldCursoCiclo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCursoCicloActionPerformed(evt);
            }
        });

        jButtonModifyProfe.setText("MODIFICAR");
        jButtonModifyProfe.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModifyProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyProfeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(4, 4, 4))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNombreCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldCursoCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonModifyProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldNumPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jTextFieldNombreCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextFieldCursoCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButtonModifyProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextFieldNumPlazas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTablePrevisonFct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Empresa", "Ciclo", "Curso", "Plazas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePrevisonFct.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTablePrevisonFct);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSaveProfe.setText("Guardar");
        jButtonSaveProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveProfeActionPerformed(evt);
            }
        });

        jButtonPlazasBack.setText("Retroceder");
        jButtonPlazasBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPlazasBackActionPerformed(evt);
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
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButtonSaveProfe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonPlazasBack)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSaveProfe)
                    .addComponent(jButtonPlazasBack))
                .addGap(26, 26, 26))
        );

        jTabbedPane1.addTab("Gestión Plazas FCT", jPanel4);

        jPanel14.setBackground(new java.awt.Color(217, 239, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel14.setPreferredSize(new java.awt.Dimension(505, 546));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTableInciedencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Número", "Fecha", "Observaciones", "Id Empresa", "Curso Escolar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableInciedencias.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jTableInciedenciasMouseWheelMoved(evt);
            }
        });
        jScrollPane4.setViewportView(jTableInciedencias);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonNuevaIncidencia.setText("CREAR");
        jButtonNuevaIncidencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNuevaIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNuevaIncidenciaActionPerformed(evt);
            }
        });

        jButtonAgregarIncidencia.setText("AÑADIR");
        jButtonAgregarIncidencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAgregarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarIncidenciaActionPerformed(evt);
            }
        });

        jButtonEliminarIncidencia.setText("ELIMINAR");
        jButtonEliminarIncidencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonEliminarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEliminarIncidenciaActionPerformed(evt);
            }
        });

        jButtonModificarIncidencia.setText("MODIFICAR");
        jButtonModificarIncidencia.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModificarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModificarIncidenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jButtonNuevaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAgregarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonEliminarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonModificarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNuevaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModificarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonEliminarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jButtonSaveIncidencias.setText("Guardar");

        jButtonIncidenciasBack.setText("Retroceder");
        jButtonIncidenciasBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncidenciasBackActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setText("NÚMERO");

        jLabel9.setText("FECHA");

        jTextFieldFechaIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFechaIncidenciaActionPerformed(evt);
            }
        });

        jTextFieldObservacionesIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldObservacionesIncidenciaActionPerformed(evt);
            }
        });

        jLabel10.setText("OBSERVACIONES");

        jTextFieldIdEmpresaIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdEmpresaIncidenciaActionPerformed(evt);
            }
        });

        jLabel11.setText("ID EMPRESA");

        jTextFieldCursoEscolarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCursoEscolarIncidenciaActionPerformed(evt);
            }
        });

        jLabel12.setText("CURSO ESCOLAR");

        jTextFieldNumeroIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNumeroIncidenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldNumeroIncidencia)
                            .addComponent(jTextFieldCursoEscolarIncidencia, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldIdEmpresaIncidencia, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jTextFieldFechaIncidencia)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldObservacionesIncidencia)))
                .addGap(16, 16, 16))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNumeroIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldFechaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextFieldCursoEscolarIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextFieldIdEmpresaIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldObservacionesIncidencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addComponent(jButtonSaveIncidencias)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonIncidenciasBack))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncidenciasBack)
                    .addComponent(jButtonSaveIncidencias))
                .addGap(17, 17, 17))
        );

        jTabbedPane1.addTab("Gestión de Incidencias", jPanel14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonPlazasBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPlazasBackActionPerformed
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setProfesorId(this.profesorId);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionTutorResponsable.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonPlazasBackActionPerformed

    private void jButtonSaveProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveProfeActionPerformed
        // TODO add your handling code here:
        modificarPlazas();
    }//GEN-LAST:event_jButtonSaveProfeActionPerformed

    private void jButtonModifyProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyProfeActionPerformed
        // TODO add your handling code here:
        setFieldsEditable2(true);
    }//GEN-LAST:event_jButtonModifyProfeActionPerformed

    private void jTextFieldCursoCicloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCursoCicloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCursoCicloActionPerformed

    private void jTextFieldNumPlazasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumPlazasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumPlazasActionPerformed

    private void jTextFieldNombreEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreEmpresaActionPerformed

    private void jTextFieldNombreCicloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreCicloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreCicloActionPerformed

    private void jTableInciedenciasMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jTableInciedenciasMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableInciedenciasMouseWheelMoved

    private void jTextFieldCursoEscolar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCursoEscolar2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCursoEscolar2ActionPerformed

    private void jTextFieldIdEmpresa2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdEmpresa2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdEmpresa2ActionPerformed

    private void jTextFieldPeriodo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPeriodo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPeriodo2ActionPerformed

    private void jTextFieldNumAlumnos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumAlumnos2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumAlumnos2ActionPerformed

    private void jTextFieldGrupo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGrupo2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGrupo2ActionPerformed

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

    private void jButtonFCTBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFCTBackActionPerformed
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setProfesorId(this.profesorId);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionTutorResponsable.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonFCTBackActionPerformed

    private void jButtonIncidenciasBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncidenciasBackActionPerformed
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setProfesorId(this.profesorId);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionTutorResponsable.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonIncidenciasBackActionPerformed

    private void jButtonNuevaIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNuevaIncidenciaActionPerformed
        // TODO add your handling code here:
       nuevoRegistroIncidencia(); 
    }//GEN-LAST:event_jButtonNuevaIncidenciaActionPerformed

    private void jButtonAgregarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarIncidenciaActionPerformed
        jButtonAgregarIncidencia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarIncidencia();
            }
        });
    }//GEN-LAST:event_jButtonAgregarIncidenciaActionPerformed

    private void jButtonEliminarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEliminarIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonEliminarIncidenciaActionPerformed

    private void jButtonModificarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModificarIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonModificarIncidenciaActionPerformed

    private void jTextFieldFechaIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFechaIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFechaIncidenciaActionPerformed

    private void jTextFieldObservacionesIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldObservacionesIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldObservacionesIncidenciaActionPerformed

    private void jTextFieldIdEmpresaIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdEmpresaIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdEmpresaIncidenciaActionPerformed

    private void jTextFieldCursoEscolarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCursoEscolarIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCursoEscolarIncidenciaActionPerformed

    private void jTextFieldNumeroIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNumeroIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNumeroIncidenciaActionPerformed

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
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionTutorResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String role = "TUTOR_RESPONSABLE";
                
                ConfigDialogGestionTutorResponsable dialog = new ConfigDialogGestionTutorResponsable(new javax.swing.JFrame(), true, role);
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
    private javax.swing.JButton jButtonAgregarIncidencia;
    private javax.swing.JButton jButtonEliminar;
    private javax.swing.JButton jButtonEliminarIncidencia;
    private javax.swing.JButton jButtonFCTBack;
    private javax.swing.JButton jButtonGuardar;
    private javax.swing.JButton jButtonIncidenciasBack;
    private javax.swing.JButton jButtonModificar;
    private javax.swing.JButton jButtonModificarIncidencia;
    private javax.swing.JButton jButtonModifyProfe;
    private javax.swing.JButton jButtonNuevaIncidencia;
    private javax.swing.JButton jButtonNuevo;
    private javax.swing.JButton jButtonPlazasBack;
    private javax.swing.JButton jButtonSaveIncidencias;
    private javax.swing.JButton jButtonSaveProfe;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTableInciedencias;
    private javax.swing.JTable jTablePrevisonFct;
    private javax.swing.JTable jTableRealizanFCT;
    private javax.swing.JTextField jTextFieldCursoCiclo;
    private javax.swing.JTextField jTextFieldCursoEscolar2;
    private javax.swing.JTextField jTextFieldCursoEscolarIncidencia;
    private javax.swing.JTextField jTextFieldFechaIncidencia;
    private javax.swing.JTextField jTextFieldGrupo2;
    private javax.swing.JTextField jTextFieldIdEmpresa2;
    private javax.swing.JTextField jTextFieldIdEmpresaIncidencia;
    private javax.swing.JTextField jTextFieldNombreCiclo;
    private javax.swing.JTextField jTextFieldNombreEmpresa;
    private javax.swing.JTextField jTextFieldNumAlumnos2;
    private javax.swing.JTextField jTextFieldNumPlazas;
    private javax.swing.JTextField jTextFieldNumeroIncidencia;
    private javax.swing.JTextField jTextFieldObservacionesIncidencia;
    private javax.swing.JTextField jTextFieldPeriodo2;
    // End of variables declaration//GEN-END:variables
}
