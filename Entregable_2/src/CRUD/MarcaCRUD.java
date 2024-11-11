package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Marca;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MarcaCRUD extends JFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_1, lbl_2;
    private JTextField txt_id_marca, txt_marca;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_marca;
    private JScrollPane scr_marca;

    private final conexionMYSQL cn = new conexionMYSQL();

    public MarcaCRUD() {
        super("CRUD Marca");
        IniciarFormulario();
        IniciarControles();
        LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setSize(430, 430); 
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel("Marcas");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel("ID Marca");
        lbl_1.setBounds(20, 50, 120, 25);

        txt_id_marca = new JTextField();
        txt_id_marca.setBounds(150, 50, 80, 25);

        lbl_2 = new JLabel("Marca");
        lbl_2.setBounds(20, 80, 110, 25);

        txt_marca = new JTextField();
        txt_marca.setBounds(150, 80, 220, 25);

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

        tb_marca = new JTable();
        tb_marca.setRowHeight(20);
        tb_marca.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Evento para seleccionar fila y cargar datos en los campos
        tb_marca.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tb_marca.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txt_id_marca.setText(tb_marca.getValueAt(filaSeleccionada, 0).toString());
                    txt_marca.setText(tb_marca.getValueAt(filaSeleccionada, 1).toString());
                    txt_id_marca.setEditable(false); // Bloquea ID en edición
                }
            }
        });

        scr_marca = new JScrollPane(tb_marca);
        scr_marca.setBounds(10, 180, 390, 100);

        this.add(lbl_titulo);
        this.add(lbl_1);
        this.add(txt_id_marca);
        this.add(lbl_2);
        this.add(txt_marca);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_marca);
    }

    private void LimpiarDatos() {
        txt_id_marca.setText("");
        txt_marca.setText("");
        txt_id_marca.setEditable(true);
        txt_id_marca.requestFocus();
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[] {"ID Marca", "Marca"});
        modelo.setRowCount(0);

        try (Connection cnx = cn.Conectar(); 
             CallableStatement cstmt = cnx.prepareCall("{CALL sp_obtener_marcas()}")) {
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[] { rs.getString("id_marca"), rs.getString("marca") });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }

        tb_marca.setModel(modelo);
    }

    private boolean validarCampos() {
        if (txt_id_marca.getText().isEmpty() || txt_marca.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
            dispose();
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (!validarCampos()) return;

            Marca marca = new Marca();
            marca.setIdMarca(txt_id_marca.getText());
            marca.setMarca(txt_marca.getText());

            try (Connection cnx = cn.Conectar()) {
                String cad_sql;
                CallableStatement cstmt;

                if (e.getSource() == btn_agregar) {
                    cad_sql = "{CALL sp_agregar_marca(?, ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, marca.getIdMarca());
                    cstmt.setString(2, marca.getMarca());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Marca Registrada");

                } else if (e.getSource() == btn_editar) {
                    cad_sql = "{CALL sp_actualizar_marca(?, ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, marca.getIdMarca());
                    cstmt.setString(2, marca.getMarca());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Marca Actualizada");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        cad_sql = "{CALL sp_eliminar_marca(?)}";
                        cstmt = cnx.prepareCall(cad_sql);
                        cstmt.setString(1, marca.getIdMarca());
                        cstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Marca Eliminada");
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
        MarcaCRUD frm = new MarcaCRUD();
        frm.setVisible(true);
    }
}
