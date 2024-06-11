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
public class ConfigDialogFCT extends javax.swing.JDialog {
    
    /**
     * Creates new form ConfigDialogStart
     */
    public ConfigDialogFCT(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        
        // Agregar evento al botón
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pack();
        
        poblarComboBoxCiclo();
        poblarComboBoxCurso();
        poblarComboBoxGrupo();  
        poblarComboBoxEmpresa();
        poblarComboBoxCursoEscolarC2C3C4();
        poblarComboBoxCursoEscolarC6();
        
    }
   
  /**
   * FUNCION C2 ----------------------------
   */
    private List<String> llamarFuncionC2(String ciclo, String curso, String cursoEscolar) {
        String query = "SELECT * FROM c2(?, ?, ?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, ciclo);
            stmt.setString(2, curso);
            stmt.setString(3, cursoEscolar);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String empresaNombre = rs.getString("empresa_nombre");
                int numPracticas = rs.getInt("num_practicas");
                resultados.add("Empresa: " + empresaNombre + ", Número de prácticas: " + numPracticas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }


    private void actualizarTextPaneC2(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPane1.setText(sb.toString());
    }
   

    private void poblarComboBoxCiclo() {
        String query = "SELECT DISTINCT ciclo FROM CICLO";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxCiclo.addItem(rs.getString("ciclo"));
                jComboBoxCicloC6.addItem(rs.getString("ciclo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void poblarComboBoxCurso() {
        String query = "SELECT DISTINCT curso FROM CICLO";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxCurso.addItem(rs.getString("curso"));
                jComboBoxCursoC6.addItem(rs.getString("curso"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
   /**
   * FUNCION C3 ----------------------------
   **/
    private List<String> llamarFuncionC3(String grupo, String cursoEscolar) {
        String query = "SELECT * FROM c3(?, ?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, grupo);
            stmt.setString(2, cursoEscolar);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int profesorId = rs.getInt("profesor_id");
                String profesorNombre = rs.getString("profesor_nombre");
                String profesorApellidos = rs.getString("profesor_apellidos");
                String empresaNombre = rs.getString("empresa_nombre");
                int numPracticas = rs.getInt("num_practicas");

                resultados.add("Profesor ID: " + profesorId 
                             + ", Nombre: " + profesorNombre 
                             + " " + profesorApellidos 
                             + ", Empresa: " + empresaNombre 
                             + ", Número de prácticas: " + numPracticas);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private void actualizarTextPaneC3(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC3.setText(sb.toString());
    }
    
    private void poblarComboBoxGrupo() {
        String query = "SELECT DISTINCT nombreGrupo FROM GRUPO";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxGrupoC3.addItem(rs.getString("nombreGrupo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void poblarComboBoxCursoEscolarC2C3C4() {
        String query = "SELECT DISTINCT cursoescolar FROM realizan_fct";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxCursoEscolarC3.addItem(rs.getString("cursoescolar"));
                jComboBoxCursoEscolarC2.addItem(rs.getString("cursoescolar"));
                jComboBoxCursoEscolarC4.addItem(rs.getString("cursoescolar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void poblarComboBoxEmpresa() {
        String query = "SELECT DISTINCT nombre FROM EMPRESA";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxEmpresaC4.addItem(rs.getString("nombre"));
                jComboBoxEmpresaC6.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
     
   /**
   * FUNCION C4 ----------------------------
   **/
     
    private List<String> llamarFuncionC4(String empresaNombre, String cursoEscolar) {
        String query = "SELECT * FROM c4(?, ?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, empresaNombre);
            stmt.setString(2, cursoEscolar);

             ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String grupoNombre = rs.getString("grupo_nombre");
                int numAlumnos = rs.getInt("num_alumnos");
                resultados.add("Grupo: " + grupoNombre + ", Número de alumnos: " + numAlumnos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private void actualizarTextPaneC4(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC4.setText(sb.toString());
    }

    
   /**
   * FUNCION C6 ----------------------------
   **/
    
    private List<String> llamarFuncionC6(String empresaNombre, String cicloNombre, String cicloCurso, String cursoEscolar) {
        String query = "SELECT * FROM c6(?, ?, ?, ?)";
        List<String> resultados = new ArrayList<>();

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, empresaNombre);
            stmt.setString(2, cicloNombre);
            stmt.setString(3, cicloCurso);
            stmt.setString(4, cursoEscolar);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String mensaje = rs.getString("mensaje");
                resultados.add("Mensaje: " + mensaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }

    private void actualizarTextPaneC6(List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        for (String resultado : resultados) {
            sb.append(resultado).append("\n");
        }
        jTextPaneC6.setText(sb.toString());
    }
    
    private void poblarComboBoxCursoEscolarC6() {
        String query = "SELECT DISTINCT cursoescolar FROM prevision_fct";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                jComboBoxCursoEscolarC6.addItem(rs.getString("cursoescolar"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxCiclo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxCurso = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxCursoEscolarC2 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jComboBoxCursoEscolarC3 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxGrupoC3 = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPaneC3 = new javax.swing.JTextPane();
        jButtonC3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxCursoEscolarC4 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxEmpresaC4 = new javax.swing.JComboBox<>();
        jButtonC4 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPaneC4 = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxEmpresaC6 = new javax.swing.JComboBox<>();
        jButtonC6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPaneC6 = new javax.swing.JTextPane();
        jComboBoxCursoEscolarC6 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxCicloC6 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jComboBoxCursoC6 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(190, 228, 255));

        jTabbedPane1.setBackground(new java.awt.Color(190, 228, 255));

        jPanel1.setBackground(new java.awt.Color(217, 239, 255));

        jLabel1.setText("CICLO: ");

        jComboBoxCiclo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCicloActionPerformed(evt);
            }
        });

        jLabel2.setText("CURSO:");

        jComboBoxCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoActionPerformed(evt);
            }
        });

        jLabel3.setText("CURSO ESCOLAR:");

        jComboBoxCursoEscolarC2.setSelectedItem(2);
        jComboBoxCursoEscolarC2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoEscolarC2ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(239, 248, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setText("Consult");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBoxCiclo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 149, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCursoEscolarC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxCursoEscolarC2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBoxCiclo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxCurso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jButton1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Practicas", jPanel1);

        jPanel2.setBackground(new java.awt.Color(217, 239, 255));

        jComboBoxCursoEscolarC3.setSelectedItem(2);
        jComboBoxCursoEscolarC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoEscolarC3ActionPerformed(evt);
            }
        });

        jLabel4.setText("CURSO ESCOLAR:");

        jLabel5.setText("GRUPO:");

        jComboBoxGrupoC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxGrupoC3ActionPerformed(evt);
            }
        });

        jTextPaneC3.setEditable(false);
        jScrollPane2.setViewportView(jTextPaneC3);

        jButtonC3.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC3.setText("Consult");
        jButtonC3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxGrupoC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCursoEscolarC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(199, 199, 199)
                        .addComponent(jButtonC3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxCursoEscolarC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxGrupoC3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(jButtonC3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        jTabbedPane1.addTab("Grupos", jPanel2);

        jPanel3.setBackground(new java.awt.Color(217, 239, 255));

        jComboBoxCursoEscolarC4.setSelectedItem(2);
        jComboBoxCursoEscolarC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoEscolarC4ActionPerformed(evt);
            }
        });

        jLabel7.setText("CURSO ESCOLAR:");

        jLabel6.setText("EMPRESA:");

        jButtonC4.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC4.setText("Consult");
        jButtonC4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC4ActionPerformed(evt);
            }
        });

        jTextPaneC4.setEditable(false);
        jScrollPane3.setViewportView(jTextPaneC4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxCursoEscolarC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(jButtonC4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxEmpresaC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboBoxCursoEscolarC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jComboBoxEmpresaC4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jButtonC4)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Empresas", jPanel3);

        jPanel4.setBackground(new java.awt.Color(217, 239, 255));

        jLabel8.setText("EMPRESA:");

        jComboBoxEmpresaC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEmpresaC6ActionPerformed(evt);
            }
        });

        jButtonC6.setBackground(new java.awt.Color(239, 248, 255));
        jButtonC6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButtonC6.setText("Consult");
        jButtonC6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(168, 184, 217), new java.awt.Color(168, 184, 217), java.awt.Color.gray, java.awt.Color.gray));
        jButtonC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonC6ActionPerformed(evt);
            }
        });

        jTextPaneC6.setEditable(false);
        jScrollPane4.setViewportView(jTextPaneC6);

        jComboBoxCursoEscolarC6.setSelectedItem(2);
        jComboBoxCursoEscolarC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCursoEscolarC6ActionPerformed(evt);
            }
        });

        jLabel9.setText("CURSO ESCOLAR:");

        jLabel10.setText("CICLO:");

        jComboBoxCicloC6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCicloC6ActionPerformed(evt);
            }
        });

        jLabel11.setText("CURSO:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel9))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxCursoC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBoxCicloC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBoxEmpresaC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jButtonC6, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCursoEscolarC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxCursoEscolarC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBoxEmpresaC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBoxCicloC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jComboBoxCursoC6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonC6))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 17, Short.MAX_VALUE)))
                .addGap(31, 31, 31))
        );

        jTabbedPane1.addTab("Solicitudes", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxCicloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCicloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCicloActionPerformed

    private void jComboBoxCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCursoActionPerformed

    private void jComboBoxCursoEscolarC2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoEscolarC2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCursoEscolarC2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String ciclo = jComboBoxCiclo.getSelectedItem().toString();
        String curso = jComboBoxCurso.getSelectedItem().toString();
        String cursoEscolar = jComboBoxCursoEscolarC2.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC2(ciclo, curso, cursoEscolar);
        actualizarTextPaneC2(resultados);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBoxCursoEscolarC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoEscolarC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCursoEscolarC3ActionPerformed

    private void jComboBoxGrupoC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxGrupoC3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxGrupoC3ActionPerformed

    private void jButtonC3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC3ActionPerformed
        // TODO add your handling code here:
        String grupo = jComboBoxGrupoC3.getSelectedItem().toString();
        String cursoEscolar = jComboBoxCursoEscolarC3.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC3(grupo, cursoEscolar);
        actualizarTextPaneC3(resultados);
    }//GEN-LAST:event_jButtonC3ActionPerformed

    private void jComboBoxCursoEscolarC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoEscolarC4ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jComboBoxCursoEscolarC4ActionPerformed

    private void jButtonC4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC4ActionPerformed
        // TODO add your handling code here:
        String empresa = jComboBoxEmpresaC4.getSelectedItem().toString();
        String cursoEscolar = jComboBoxCursoEscolarC4.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC4(empresa, cursoEscolar);
        actualizarTextPaneC4(resultados);
    }//GEN-LAST:event_jButtonC4ActionPerformed

    private void jButtonC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonC6ActionPerformed
        // TODO add your handling code here:
        String empresa = jComboBoxEmpresaC6.getSelectedItem().toString();
        String ciclo = jComboBoxCicloC6.getSelectedItem().toString();
        String curso = jComboBoxCursoC6.getSelectedItem().toString();
        String cursoEscolar = jComboBoxCursoEscolarC6.getSelectedItem().toString();
        
        List<String> resultados = llamarFuncionC6(empresa, ciclo, curso, cursoEscolar);
        actualizarTextPaneC6(resultados);
    }//GEN-LAST:event_jButtonC6ActionPerformed

    private void jComboBoxCursoEscolarC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCursoEscolarC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCursoEscolarC6ActionPerformed

    private void jComboBoxEmpresaC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEmpresaC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxEmpresaC6ActionPerformed

    private void jComboBoxCicloC6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCicloC6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCicloC6ActionPerformed
    

    
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
            java.util.logging.Logger.getLogger(ConfigDialogFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigDialogFCT.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                ConfigDialogFCT dialog = new ConfigDialogFCT(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonC3;
    private javax.swing.JButton jButtonC4;
    private javax.swing.JButton jButtonC6;
    private javax.swing.JComboBox<String> jComboBoxCiclo;
    private javax.swing.JComboBox<String> jComboBoxCicloC6;
    private javax.swing.JComboBox<String> jComboBoxCurso;
    private javax.swing.JComboBox<String> jComboBoxCursoC6;
    private javax.swing.JComboBox<String> jComboBoxCursoEscolarC2;
    private javax.swing.JComboBox<String> jComboBoxCursoEscolarC3;
    private javax.swing.JComboBox<String> jComboBoxCursoEscolarC4;
    private javax.swing.JComboBox<String> jComboBoxCursoEscolarC6;
    private javax.swing.JComboBox<String> jComboBoxEmpresaC4;
    private javax.swing.JComboBox<String> jComboBoxEmpresaC6;
    private javax.swing.JComboBox<String> jComboBoxGrupoC3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPaneC3;
    private javax.swing.JTextPane jTextPaneC4;
    private javax.swing.JTextPane jTextPaneC6;
    // End of variables declaration//GEN-END:variables
}


