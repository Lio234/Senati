package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Provincia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class ProvinciaCRUD extends JInternalFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_id_provincia, lbl_provincia, lbl_departamento;
    private JTextField txt_id_provincia, txt_provincia;
    private JComboBox<String> cb_departamento;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_provincia;
    private JScrollPane scr_provincia;

    private final conexionMYSQL cn = new conexionMYSQL();
    private final HashMap<String, String> departamentoMap = new HashMap<>(); // Almacenar id y nombre del departamento

    public ProvinciaCRUD() {
        super("CRUD Provincia");
        IniciarFormulario();
        IniciarControles();
        LlenarComboDepartamentos();
        LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setSize(500, 450);
        //this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel("Provincias");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 280, 25);

        lbl_id_provincia = new JLabel("ID Provincia");
        lbl_id_provincia.setBounds(20, 50, 120, 25);
        txt_id_provincia = new JTextField();
        txt_id_provincia.setBounds(150, 50, 80, 25);

        lbl_provincia = new JLabel("Provincia");
        lbl_provincia.setBounds(20, 80, 120, 25);
        txt_provincia = new JTextField();
        txt_provincia.setBounds(150, 80, 220, 25);

        lbl_departamento = new JLabel("Departamento");
        lbl_departamento.setBounds(20, 110, 120, 25);
        cb_departamento = new JComboBox<>();
        cb_departamento.setBounds(150, 110, 220, 25);

        btn_nuevo = new JButton("NUEVO");
        btn_nuevo.setBounds(10, 150, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton("AGREGAR");
        btn_agregar.setBounds(110, 150, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton("EDITAR");
        btn_editar.setBounds(210, 150, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton("BORRAR");
        btn_borrar.setBounds(310, 150, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 350, 180, 30);
        btn_cerrar.addActionListener(this);

        tb_provincia = new JTable();
        scr_provincia = new JScrollPane(tb_provincia);
        scr_provincia.setBounds(10, 200, 450, 110);
        

        this.add(lbl_titulo);
        this.add(lbl_id_provincia);
        this.add(txt_id_provincia);
        this.add(lbl_provincia);
        this.add(txt_provincia);
        this.add(lbl_departamento);
        this.add(cb_departamento);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_provincia);
    }

    private void LlenarComboDepartamentos() {
        try (Connection cnx = cn.Conectar(); Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery("SELECT id_departamento, departamento FROM tb_departamento")) {
            while (rs.next()) {
                String id = rs.getString("id_departamento");
                String nombre = rs.getString("departamento");
                departamentoMap.put(nombre, id);
                cb_departamento.addItem(nombre);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los departamentos: " + e.getMessage());
        }
    }

    private void LimpiarDatos() {
        txt_id_provincia.setText("");
        txt_provincia.setText("");
        cb_departamento.setSelectedIndex(-1);
        txt_id_provincia.requestFocus();
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID Provincia", "Provincia", "Departamento"});
        modelo.setRowCount(0);

        try (Connection cnx = cn.Conectar(); Statement stm = cnx.createStatement(); ResultSet rs = stm.executeQuery("CALL sp_obtener_provincias()")) {
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString("id_provincia"), rs.getString("provincia"), rs.getString("id_departamento")});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }

        tb_provincia.setModel(modelo);
    }

    private boolean validarCampos() {
        if (txt_id_provincia.getText().isEmpty() || txt_provincia.getText().isEmpty() || cb_departamento.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
            if (e.getSource() == btn_cerrar) {
            int op = JOptionPane.showConfirmDialog(null,
                    "¿Seguro de cerrar?",
                    "Provincia",
                    JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (!validarCampos()) return;

            Provincia provincia = new Provincia();
            provincia.setIdProvincia(txt_id_provincia.getText());
            provincia.setProvincia(txt_provincia.getText());
            provincia.setIdDepartamento(departamentoMap.get((String) cb_departamento.getSelectedItem()));

            try (Connection cnx = cn.Conectar()) {
                String sql;
                CallableStatement cs;

                if (e.getSource() == btn_agregar) {
                    sql = "{CALL sp_agregar_provincia(?, ?, ?)}";
                    cs = cnx.prepareCall(sql);
                    cs.setString(1, provincia.getIdProvincia());
                    cs.setString(2, provincia.getProvincia());
                    cs.setString(3, provincia.getIdDepartamento());
                    cs.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Provincia Registrada");

                } else if (e.getSource() == btn_editar) {
                    sql = "{CALL sp_actualizar_provincia(?, ?, ?)}";
                    cs = cnx.prepareCall(sql);
                    cs.setString(1, provincia.getIdProvincia());
                    cs.setString(2, provincia.getProvincia());
                    cs.setString(3, provincia.getIdDepartamento());
                    cs.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Provincia Actualizada");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        sql = "{CALL sp_eliminar_provincia(?)}";
                        cs = cnx.prepareCall(sql);
                        cs.setString(1, provincia.getIdProvincia());
                        cs.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Provincia Eliminada");
                    }
                }

                MostrarDatos();
                LimpiarDatos();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al realizar la operación: " + ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        ProvinciaCRUD frm = new ProvinciaCRUD();
        frm.setVisible(true);
    }
}
