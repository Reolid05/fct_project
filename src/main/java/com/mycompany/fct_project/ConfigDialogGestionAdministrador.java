/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.mycompany.fct_project;

import static com.mycompany.fct_project.DatabaseConnection.getConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AdminAlex
 */
public class ConfigDialogGestionAdministrador extends javax.swing.JDialog {

    private HashMap<String, Integer> sectorMap;
    private String role;

    public ConfigDialogGestionAdministrador(java.awt.Frame parent, boolean modal, String role) {
        super(parent, modal);
        initComponents();
        this.role = role;
        setResizable(false);
        setLocationRelativeTo(null);
        cargarDatosTabla();
        cargarDatosTablaProfesor();
        cargarDatosTablaContactos();
        cargarDatosTablaIncidencias();
        agregarListeners();
        setFieldsEditable(false);
        setFieldsEditableProfesor(false);
        setFieldsEditableContactos(false);
        cargarSectores();
    }
    
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
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarIncidencia() {
        int selectedRow = jTableInciedencias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una incidencia para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int numincidencia = Integer.parseInt(jTableInciedencias.getValueAt(selectedRow, 0).toString());
        String query = "DELETE FROM incidencia WHERE numincidencia = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, numincidencia);
            pst.executeUpdate();
            cargarDatosTablaIncidencias();
            JOptionPane.showMessageDialog(this, "Incidencia eliminada exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la incidencia: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void cargarDatosTablaContactos() {
        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        model.setRowCount(0);
        String query = "SELECT * FROM personal order by idpersonal";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int idPersonal = rs.getInt("idpersonal");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                String telefono = rs.getString("telefono");
                String cargo = rs.getString("cargo");
                boolean esContacto = rs.getBoolean("esContacto");
                String idEmpresa = rs.getString("idempresa");
                model.addRow(new Object[]{idPersonal, nombre, apellidos, telefono, cargo, esContacto, idEmpresa});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void guardarCambiosContactos() {
        int idPersonal = Integer.parseInt(jTextFieldIdPersonalID.getText());
        boolean esContacto = Boolean.parseBoolean(jComboBoxPersonalEsContacto.getSelectedItem().toString());

        String query = "UPDATE personal SET esContacto = ? WHERE idpersonal = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setBoolean(1, esContacto);
            pst.setInt(2, idPersonal);
            pst.executeUpdate();
            cargarDatosTablaContactos();
            setFieldsEditableContactos(false);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar los cambios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void mostrarDatosSeleccionadosContactos() {
        int selectedRow = jTable3.getSelectedRow();
        jTextFieldIdPersonalID.setText(jTable3.getValueAt(selectedRow, 0).toString());
        jTextFieldPersonalNombre.setText(jTable3.getValueAt(selectedRow, 1).toString());
        jTextFieldPersonalApellidos.setText(jTable3.getValueAt(selectedRow, 2).toString());
        jTextFieldPersonalTelefono.setText(jTable3.getValueAt(selectedRow, 3).toString());
        jTextFieldPersonalCargo.setText(jTable3.getValueAt(selectedRow, 4).toString());
        jComboBoxPersonalEsContacto.setSelectedItem(jTable3.getValueAt(selectedRow, 5).toString());
        jTextFieldPersonalIdEmpresa.setText(jTable3.getValueAt(selectedRow, 6).toString());
        
        jTextFieldIdPersonalID.setEditable(false);
        jTextFieldPersonalNombre.setEditable(false);
        jTextFieldPersonalApellidos.setEditable(false);
        jTextFieldPersonalTelefono.setEditable(false);
        jTextFieldPersonalCargo.setEditable(false);
        jTextFieldPersonalIdEmpresa.setEditable(false);
        jComboBoxPersonalEsContacto.setEnabled(false); 
    }
    
    private void setFieldsEditableContactos(boolean editable) {
        jComboBoxPersonalEsContacto.setEnabled(editable);
    }

    private void cargarSectores() {
        sectorMap = new HashMap<>();
        sectorMap.put("Sistemas Microinformáticos y Redes", 1);
        sectorMap.put("Desarrollo de Aplicaciones Web", 2);
        sectorMap.put("Desarrollo de Aplicaciones Multiplataforma", 3);
        sectorMap.put("Administración de Sistemas Informáticos en Red", 4);
        sectorMap.put("Ciberseguridad", 5);
        sectorMap.put("Redes y Comunicaciones", 6);
        sectorMap.put("Desarrollo de Software", 7);
        sectorMap.put("Bases de Datos", 8);
        sectorMap.put("Soporte Técnico", 9);
        sectorMap.put("Ingeniería de Software", 10);

        for (String descripcion : sectorMap.keySet()) {
            jComboBoxSector.addItem(descripcion);
        }
    }

    private void setFieldsEditable(boolean editable) {
        jTextFieldCIF.setEditable(editable);
        jTextFieldNombre.setEditable(editable);
        jComboBoxSector.setEnabled(editable);
    }

    private void agregarListeners() {
        jTable1.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable1.getSelectedRow() != -1) {
                mostrarDatosSeleccionados();
            }
        });

        jButtonNew.addActionListener(evt -> nuevoRegistro());
        jButtonAdd.addActionListener(evt -> agregarRegistro());
        jButtonModify.addActionListener(evt -> {
            setFieldsEditable(true);
            jButtonSave.setEnabled(true);
        });
        jButtonSave.addActionListener(evt -> {
            guardarCambios();
            setFieldsEditable(false);
            jButtonSave.setEnabled(false);
        });
        jButtonDelete.addActionListener(evt -> eliminarRegistro());

        // Listeners para gestión de profesores
        jTable2.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable2.getSelectedRow() != -1) {
                mostrarDatosSeleccionadosProfesor();
            }
        });

        jButtonNewProfe.addActionListener(evt -> nuevoRegistroProfesor());
        jButtonAddProfe.addActionListener(evt -> agregarRegistroProfesor());
        jButtonModifyProfe.addActionListener(evt -> {
            setFieldsEditableProfesor(true);
            jButtonSaveProfe.setEnabled(true);
        });
        jButtonSaveProfe.addActionListener(evt -> {
            guardarCambiosProfesor();
            setFieldsEditableProfesor(false);
            jButtonSaveProfe.setEnabled(false);
        });
        jButtonDeleteProfe.addActionListener(evt -> eliminarRegistroProfesor());
        
        jTable3.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && jTable3.getSelectedRow() != -1) {
                mostrarDatosSeleccionadosContactos();
            }
        });

        jButtonSaveContactos.addActionListener(evt -> {
            guardarCambiosContactos();
            jButtonSaveContactos.setEnabled(true);
            setFieldsEditable(false);
        });

        jButtonModifyContacto.addActionListener(evt -> jButtonModifyContactoActionPerformed(evt));
        
        jButtonDeleteIncidencia.addActionListener(evt -> eliminarIncidencia());
    }

    private void mostrarDatosSeleccionados() {
        int selectedRow = jTable1.getSelectedRow();
        jTextFieldCIF.setText(jTable1.getValueAt(selectedRow, 0).toString());
        jTextFieldNombre.setText(jTable1.getValueAt(selectedRow, 1).toString());
        jComboBoxSector.setSelectedItem(jTable1.getValueAt(selectedRow, 2).toString());
        setFieldsEditable(false);
    }

    private void nuevoRegistro() {
        jTextFieldCIF.setText("");
        jTextFieldNombre.setText("");
        jComboBoxSector.setSelectedIndex(0);
        setFieldsEditable(true);
    }

    private void agregarRegistro() {
        String idempresa = jTextFieldCIF.getText();
        String nombre = jTextFieldNombre.getText();
        String sectorDescripcion = jComboBoxSector.getSelectedItem().toString();
        Integer sectorId = sectorMap.get(sectorDescripcion);

        if (sectorId == null) {
            JOptionPane.showMessageDialog(this, "El sector seleccionado no es válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "INSERT INTO EMPRESA (idempresa, nombre, sector) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, idempresa);
            pst.setString(2, nombre);
            pst.setInt(3, sectorId);
            pst.executeUpdate();
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Registro agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el registro: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getSectorFromComboBox() {
        return jComboBoxSector.getSelectedItem().toString();
    }

    private String getIdEmpresaSeleccionada() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            return jTable1.getValueAt(selectedRow, 0).toString();
        } else {
            return null;
        }
    }

    private void guardarCambios() {
        modificarRegistro();
    }

    public void modificarRegistro() {
        String idempresa = getIdEmpresaSeleccionada();
        String nombre = jTextFieldNombre.getText();
        String sectorDescripcion = jComboBoxSector.getSelectedItem().toString();
        Integer sectorId = sectorMap.get(sectorDescripcion);

        if (sectorId == null) {
            JOptionPane.showMessageDialog(this, "El sector seleccionado no es válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "UPDATE EMPRESA SET nombre = ?, sector = ? WHERE idempresa = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setInt(2, sectorId);
            pstmt.setString(3, idempresa);
            pstmt.executeUpdate();
            cargarDatosTabla();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el registro: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarRegistro() {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String idempresa = jTextFieldCIF.getText();
        String query = "DELETE FROM EMPRESA WHERE idempresa = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, idempresa);
            pst.executeUpdate();
            cargarDatosTabla();
            JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " +
                    e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosTabla() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        String query = "SELECT e.idempresa, e.nombre, s.descripcion FROM EMPRESA e " +
                       "JOIN SECTOR s ON e.sector = s.idsector";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                try {
                    String idempresa = rs.getString("idempresa");
                    String nombre = rs.getString("nombre");
                    String sector = rs.getString("descripcion");
                    model.addRow(new Object[]{idempresa, nombre, sector});
                } catch (SQLException e) {
                    System.err.println("Error processing row: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Métodos para la gestión de profesores
    
    private void cargarDatosTablaProfesor() {
        String query = "SELECT idprofe, nombre, apellidos FROM PROFESOR";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                int idprofe = rs.getInt("idprofe");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                model.addRow(new Object[]{idprofe, nombre, apellidos});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void mostrarDatosSeleccionadosProfesor() {
        int selectedRow = jTable2.getSelectedRow();
        jTextFieldIdProfe.setText(jTable2.getValueAt(selectedRow, 0).toString());
        jTextFieldNombreProfe.setText(jTable2.getValueAt(selectedRow, 1).toString());
        jTextFieldApellidosProfe.setText(jTable2.getValueAt(selectedRow, 2).toString());
        setFieldsEditable(false);
    }

    private void nuevoRegistroProfesor() {
        jTextFieldIdProfe.setText("");
        jTextFieldNombreProfe.setText("");
        jTextFieldApellidosProfe.setText("");
        setFieldsEditableProfesor(true);
    }

    private void agregarRegistroProfesor() {
        int idprofe = Integer.parseInt(jTextFieldIdProfe.getText());
        String nombre = jTextFieldNombreProfe.getText();
        String apellidos = jTextFieldApellidosProfe.getText();

        String query = "INSERT INTO PROFESOR (idprofe, nombre, apellidos) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, idprofe);
            pst.setString(2, nombre);
            pst.setString(3, apellidos);
            pst.executeUpdate();
            cargarDatosTablaProfesor();
            JOptionPane.showMessageDialog(this, "Profesor agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al agregar el profesor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void guardarCambiosProfesor() {
        modificarRegistroProfesor();
    }

    private void modificarRegistroProfesor() {
        int idprofe = Integer.parseInt(jTextFieldIdProfe.getText());
        String nombre = jTextFieldNombreProfe.getText();
        String apellidos = jTextFieldApellidosProfe.getText();

        String query = "UPDATE PROFESOR SET nombre = ?, apellidos = ? WHERE idprofe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, apellidos);
            pstmt.setInt(3, idprofe);
            pstmt.executeUpdate();
            cargarDatosTablaProfesor();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al modificar el registro de profesor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void eliminarRegistroProfesor() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un profesor para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idprofe = Integer.parseInt(jTable2.getValueAt(selectedRow, 0).toString());
        String query = "DELETE FROM PROFESOR WHERE idprofe = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, idprofe);
            pst.executeUpdate();
            cargarDatosTablaProfesor();
            JOptionPane.showMessageDialog(this, "Profesor eliminado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar el profesor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void setFieldsEditableProfesor(boolean editable) {
        jTextFieldIdProfe.setEditable(editable);
        jTextFieldNombreProfe.setEditable(editable);
        jTextFieldApellidosProfe.setEnabled(editable);
    }
    
    private String getIdProfesorSeleccionado() {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow != -1) {
            return jTable2.getValueAt(selectedRow, 0).toString();
        }
        return null;
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
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxSector = new javax.swing.JComboBox<>();
        jTextFieldNombre = new javax.swing.JTextField();
        jTextFieldCIF = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jButtonNew = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonSave = new javax.swing.JButton();
        jButtonBack = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextFieldNombreProfe = new javax.swing.JTextField();
        jTextFieldIdProfe = new javax.swing.JTextField();
        jTextFieldApellidosProfe = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jButtonAddProfe = new javax.swing.JButton();
        jButtonModifyProfe = new javax.swing.JButton();
        jButtonDeleteProfe = new javax.swing.JButton();
        jButtonNewProfe = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButtonSaveProfe = new javax.swing.JButton();
        jButtonBack1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldIdPersonalID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldPersonalNombre = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldPersonalApellidos = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldPersonalTelefono = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldPersonalCargo = new javax.swing.JTextField();
        jTextFieldPersonalIdEmpresa = new javax.swing.JTextField();
        jComboBoxPersonalEsContacto = new javax.swing.JComboBox<>();
        jButtonModifyContacto = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButtonSaveContactos = new javax.swing.JButton();
        jButtonBackContactos = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTableInciedencias = new javax.swing.JTable();
        jButtonBackInidencia = new javax.swing.JButton();
        jButtonDeleteIncidencia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTabbedPane1.setBackground(new java.awt.Color(190, 228, 255));

        jPanel1.setBackground(new java.awt.Color(217, 239, 255));

        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setText("CIF");

        jLabel2.setText("NOMBRE");

        jLabel3.setText("SECTOR");

        jTextFieldNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreActionPerformed(evt);
            }
        });

        jTextFieldCIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldCIFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                        .addComponent(jTextFieldCIF))
                    .addComponent(jComboBoxSector, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldCIF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxSector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonAdd.setText("ADD");
        jButtonAdd.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonModify.setText("MODIFY");
        jButtonModify.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyActionPerformed(evt);
            }
        });

        jButtonDelete.setText("DELETE");
        jButtonDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });

        jButtonNew.setText("NEW");
        jButtonNew.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonModify, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModify, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNew, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                " Id Empresa", "Nombre", "Sector"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSave.setText("Save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonBack.setText("Back");
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
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBack))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack)
                    .addComponent(jButtonSave))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Gestión Empresa", jPanel1);

        jPanel4.setBackground(new java.awt.Color(217, 239, 255));

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("ID PROFESOR");

        jLabel5.setText("NOMBRE");

        jLabel6.setText("APELLIDOS");

        jTextFieldNombreProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNombreProfeActionPerformed(evt);
            }
        });

        jTextFieldIdProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdProfeActionPerformed(evt);
            }
        });

        jTextFieldApellidosProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldApellidosProfeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldNombreProfe)
                    .addComponent(jTextFieldIdProfe)
                    .addComponent(jTextFieldApellidosProfe, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextFieldIdProfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextFieldNombreProfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextFieldApellidosProfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButtonAddProfe.setText("ADD");
        jButtonAddProfe.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonAddProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddProfeActionPerformed(evt);
            }
        });

        jButtonModifyProfe.setText("MODIFY");
        jButtonModifyProfe.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModifyProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyProfeActionPerformed(evt);
            }
        });

        jButtonDeleteProfe.setText("DELETE");
        jButtonDeleteProfe.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonDeleteProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteProfeActionPerformed(evt);
            }
        });

        jButtonNewProfe.setText("NEW");
        jButtonNewProfe.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonNewProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewProfeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addComponent(jButtonNewProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonAddProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonDeleteProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButtonModifyProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModifyProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonDeleteProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNewProfe, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id Profesores", "Nombre", "Apellidos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSaveProfe.setText("Save");
        jButtonSaveProfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveProfeActionPerformed(evt);
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
                        .addComponent(jButtonSaveProfe)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBack1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBack1)
                    .addComponent(jButtonSaveProfe))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Gestión Profesores", jPanel4);

        jPanel9.setBackground(new java.awt.Color(217, 239, 255));

        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel7.setText("ID");

        jTextFieldIdPersonalID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdPersonalIDActionPerformed(evt);
            }
        });

        jLabel8.setText("NOMBRE");

        jTextFieldPersonalNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPersonalNombreActionPerformed(evt);
            }
        });

        jLabel9.setText("APELLIDOS");

        jTextFieldPersonalApellidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPersonalApellidosActionPerformed(evt);
            }
        });

        jLabel10.setText("TELEFONO");

        jTextFieldPersonalTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPersonalTelefonoActionPerformed(evt);
            }
        });

        jLabel11.setText("CARGO");

        jLabel12.setText("ES CONTACTO?");

        jLabel13.setText("ID EMPRESA");

        jTextFieldPersonalCargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPersonalCargoActionPerformed(evt);
            }
        });

        jTextFieldPersonalIdEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPersonalIdEmpresaActionPerformed(evt);
            }
        });

        jComboBoxPersonalEsContacto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "True", "False" }));
        jComboBoxPersonalEsContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxPersonalEsContactoActionPerformed(evt);
            }
        });

        jButtonModifyContacto.setText("MODIFY");
        jButtonModifyContacto.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonModifyContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyContactoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIdPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPersonalNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPersonalApellidos))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldPersonalTelefono)))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel13))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextFieldPersonalIdEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addComponent(jTextFieldPersonalCargo, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jComboBoxPersonalEsContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModifyContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jTextFieldIdPersonalID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextFieldPersonalNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jTextFieldPersonalApellidos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jTextFieldPersonalCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jComboBoxPersonalEsContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jTextFieldPersonalIdEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextFieldPersonalTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonModifyContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Id Personal", "Nombre", "Apellidos", "Teléfono", "Cargo", "Es Contacto?", "Id Empresa"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonSaveContactos.setText("Save");
        jButtonSaveContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveContactosActionPerformed(evt);
            }
        });

        jButtonBackContactos.setText("Back");
        jButtonBackContactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackContactosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jButtonSaveContactos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                        .addComponent(jButtonBackContactos)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBackContactos)
                    .addComponent(jButtonSaveContactos))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Gestión contactos", jPanel9);

        jPanel11.setBackground(new java.awt.Color(217, 239, 255));

        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
        jTableInciedencias.getTableHeader().setReorderingAllowed(false);
        jTableInciedencias.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jTableInciedenciasMouseWheelMoved(evt);
            }
        });
        jScrollPane4.setViewportView(jTableInciedencias);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButtonBackInidencia.setText("Back");
        jButtonBackInidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBackInidenciaActionPerformed(evt);
            }
        });

        jButtonDeleteIncidencia.setText("Delete");
        jButtonDeleteIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteIncidenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jButtonDeleteIncidencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBackInidencia)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonBackInidencia)
                    .addComponent(jButtonDeleteIncidencia))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Gestión incidencias", jPanel11);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreActionPerformed

    private void jTextFieldCIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldCIFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldCIFActionPerformed

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        // TODO add your handling code here:
        setFieldsEditable(true);
    }//GEN-LAST:event_jButtonModifyActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jButtonNewActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        guardarCambios();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackActionPerformed
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionAdministrador.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonBackActionPerformed

    private void jTextFieldNombreProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNombreProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNombreProfeActionPerformed

    private void jTextFieldIdProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdProfeActionPerformed

    private void jButtonAddProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddProfeActionPerformed

    private void jButtonModifyProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyProfeActionPerformed
        // TODO add your handling code here:
        setFieldsEditableProfesor(true);
    }//GEN-LAST:event_jButtonModifyProfeActionPerformed

    private void jButtonDeleteProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDeleteProfeActionPerformed

    private void jButtonNewProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewProfeActionPerformed

    private void jButtonSaveProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveProfeActionPerformed
        // TODO add your handling code here:
        guardarCambiosProfesor();
        JOptionPane.showMessageDialog(this, "Registro de profesor modificado exitosamente.");
    }//GEN-LAST:event_jButtonSaveProfeActionPerformed

    private void jButtonBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBack1ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionAdministrador.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonBack1ActionPerformed

    private void jTextFieldApellidosProfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldApellidosProfeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldApellidosProfeActionPerformed

    private void jTextFieldIdPersonalIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdPersonalIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdPersonalIDActionPerformed

    private void jButtonSaveContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveContactosActionPerformed
        // TODO add your handling code here:
        guardarCambiosContactos();
        JOptionPane.showMessageDialog(this, "Registro de profesor modificado exitosamente.");
    }//GEN-LAST:event_jButtonSaveContactosActionPerformed

    private void jButtonBackContactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackContactosActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionAdministrador.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonBackContactosActionPerformed

    private void jTextFieldPersonalNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPersonalNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPersonalNombreActionPerformed

    private void jTextFieldPersonalApellidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPersonalApellidosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPersonalApellidosActionPerformed

    private void jTextFieldPersonalTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPersonalTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPersonalTelefonoActionPerformed

    private void jTextFieldPersonalIdEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPersonalIdEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPersonalIdEmpresaActionPerformed

    private void jTextFieldPersonalCargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPersonalCargoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPersonalCargoActionPerformed

    private void jComboBoxPersonalEsContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxPersonalEsContactoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxPersonalEsContactoActionPerformed

    private void jButtonModifyContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyContactoActionPerformed
        // TODO add your handling code here:
        setFieldsEditableContactos(true);
    }//GEN-LAST:event_jButtonModifyContactoActionPerformed

    private void jButtonBackInidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBackInidenciaActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        ConfigDialogMenu menu = new ConfigDialogMenu(null, true, this.role);
        menu.setVisible(true);
        menu.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                ConfigDialogGestionAdministrador.this.setVisible(true);
            }
        });
        this.dispose();
    }//GEN-LAST:event_jButtonBackInidenciaActionPerformed

    private void jButtonDeleteIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteIncidenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonDeleteIncidenciaActionPerformed

    private void jTableInciedenciasMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jTableInciedenciasMouseWheelMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jTableInciedenciasMouseWheelMoved

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
            java.util.logging.Logger.getLogger(ConfigDialogGestionAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogGestionAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String role = "ADMINISTRADOR";
                ConfigDialogMenu configMenu = new ConfigDialogMenu(new javax.swing.JFrame(), true, role);
                configMenu.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                configMenu.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonAddProfe;
    private javax.swing.JButton jButtonBack;
    private javax.swing.JButton jButtonBack1;
    private javax.swing.JButton jButtonBackContactos;
    private javax.swing.JButton jButtonBackInidencia;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonDeleteIncidencia;
    private javax.swing.JButton jButtonDeleteProfe;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonModifyContacto;
    private javax.swing.JButton jButtonModifyProfe;
    private javax.swing.JButton jButtonNew;
    private javax.swing.JButton jButtonNewProfe;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSaveContactos;
    private javax.swing.JButton jButtonSaveProfe;
    private javax.swing.JComboBox<String> jComboBoxPersonalEsContacto;
    private javax.swing.JComboBox<String> jComboBoxSector;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTable jTableInciedencias;
    private javax.swing.JTextField jTextFieldApellidosProfe;
    private javax.swing.JTextField jTextFieldCIF;
    private javax.swing.JTextField jTextFieldIdPersonalID;
    private javax.swing.JTextField jTextFieldIdProfe;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldNombreProfe;
    private javax.swing.JTextField jTextFieldPersonalApellidos;
    private javax.swing.JTextField jTextFieldPersonalCargo;
    private javax.swing.JTextField jTextFieldPersonalIdEmpresa;
    private javax.swing.JTextField jTextFieldPersonalNombre;
    private javax.swing.JTextField jTextFieldPersonalTelefono;
    // End of variables declaration//GEN-END:variables
}
