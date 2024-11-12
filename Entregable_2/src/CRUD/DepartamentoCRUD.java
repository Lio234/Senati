package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Departamento;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class DepartamentoCRUD extends JInternalFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_1, lbl_2;
    private JTextField txt_id_departamento, txt_departamento;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_departamento;
    private JScrollPane scr_departamento;

    private final conexionMYSQL cn = new conexionMYSQL();

    public DepartamentoCRUD() {
        super("CRUD Departamento");
        IniciarFormulario();
        IniciarControles();
        LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setSize(430, 430); 
        //this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel("Departamentos");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel("ID Departamento");
        lbl_1.setBounds(20, 50, 120, 25);

        txt_id_departamento = new JTextField();
        txt_id_departamento.setBounds(150, 50, 80, 25);

        lbl_2 = new JLabel("Departamento");
        lbl_2.setBounds(20, 80, 110, 25);

        txt_departamento = new JTextField();
        txt_departamento.setBounds(150, 80, 220, 25);

        btn_nuevo = new JButton("NUEVO");
        btn_nuevo.setBounds(10, 120, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton("AGREGAR");
        btn_agregar.setBounds(110, 120, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton("EDITAR");
        btn_editar.setBounds(210, 120, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton("BORRAR");
        btn_borrar.setBounds(310, 120, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 300, 180, 30);
        btn_cerrar.addActionListener(this);

        tb_departamento = new JTable();
        tb_departamento.setRowHeight(20);
        tb_departamento.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tb_departamento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tb_departamento.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txt_id_departamento.setText(tb_departamento.getValueAt(filaSeleccionada, 0).toString());
                    txt_departamento.setText(tb_departamento.getValueAt(filaSeleccionada, 1).toString());
                    txt_id_departamento.setEditable(false);
                }
            }
        });

        scr_departamento = new JScrollPane(tb_departamento);
        scr_departamento.setBounds(10, 180, 390, 100);
        
        this.add(lbl_titulo);
        this.add(lbl_1);
        this.add(txt_id_departamento);
        this.add(lbl_2);
        this.add(txt_departamento);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_departamento);
    }

    private void LimpiarDatos() {
        txt_id_departamento.setText("");
        txt_departamento.setText("");
        txt_id_departamento.setEditable(true);
        txt_id_departamento.requestFocus();
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[] {"ID Departamento", "Departamento"});
        modelo.setRowCount(0);

        try (Connection cnx = cn.Conectar();
             CallableStatement cstm = cnx.prepareCall("{CALL sp_obtener_departamentos()}");
             ResultSet rs = cstm.executeQuery()) {

            while (rs.next()) {
                modelo.addRow(new Object[] { rs.getString("id_departamento"), rs.getString("departamento") });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }

        tb_departamento.setModel(modelo);
    }

    private boolean validarCampos() {
        if (txt_id_departamento.getText().isEmpty() || txt_departamento.getText().isEmpty()) {
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
                    "Departamento",
                    JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (!validarCampos()) return;

            Departamento departamento = new Departamento();
            departamento.setIdDepartamento(txt_id_departamento.getText());
            departamento.setDepartamento(txt_departamento.getText());

            try (Connection cnx = cn.Conectar()) {
                CallableStatement cstm;

                if (e.getSource() == btn_agregar) {
                    cstm = cnx.prepareCall("{CALL sp_agregar_departamento(?, ?)}");
                    cstm.setString(1, departamento.getIdDepartamento());
                    cstm.setString(2, departamento.getDepartamento());
                    cstm.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Departamento Registrado");

                } else if (e.getSource() == btn_editar) {
                    cstm = cnx.prepareCall("{CALL sp_actualizar_departamento(?, ?)}");
                    cstm.setString(1, departamento.getIdDepartamento());
                    cstm.setString(2, departamento.getDepartamento());
                    cstm.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Departamento Actualizado");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        cstm = cnx.prepareCall("{CALL sp_eliminar_departamento(?)}");
                        cstm.setString(1, departamento.getIdDepartamento());
                        cstm.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Departamento Eliminado");
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
        DepartamentoCRUD frm = new DepartamentoCRUD();
        frm.setVisible(true);
    }
}
