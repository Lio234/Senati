package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Categoria;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class CategoriaCRUD extends JInternalFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_1, lbl_2;
    private JTextField txt_id_categoria, txt_categoria;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_categoria;
    private JScrollPane scr_categoria;

    private final conexionMYSQL cn = new conexionMYSQL();

    public CategoriaCRUD() {
        super("CRUD Categoría");
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
        lbl_titulo = new JLabel("Categorías");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_1 = new JLabel("ID Categoría");
        lbl_1.setBounds(20, 50, 120, 25);

        txt_id_categoria = new JTextField();
        txt_id_categoria.setBounds(150, 50, 80, 25);

        lbl_2 = new JLabel("Categoría");
        lbl_2.setBounds(20, 80, 110, 25);

        txt_categoria = new JTextField();
        txt_categoria.setBounds(150, 80, 220, 25);

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

        tb_categoria = new JTable();
        tb_categoria.setRowHeight(20);
        tb_categoria.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Evento para seleccionar fila y cargar datos en los campos
        tb_categoria.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tb_categoria.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    txt_id_categoria.setText(tb_categoria.getValueAt(filaSeleccionada, 0).toString());
                    txt_categoria.setText(tb_categoria.getValueAt(filaSeleccionada, 1).toString());
                    txt_id_categoria.setEditable(false); // Bloquea ID en edición
                }
            }
        });

        scr_categoria = new JScrollPane(tb_categoria);
        scr_categoria.setBounds(10, 180, 390, 100);
        

        this.add(lbl_titulo);
        this.add(lbl_1);
        this.add(txt_id_categoria);
        this.add(lbl_2);
        this.add(txt_categoria);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_categoria);
    }

    private void LimpiarDatos() {
        txt_id_categoria.setText("");
        txt_categoria.setText("");
        txt_id_categoria.setEditable(true);
        txt_id_categoria.requestFocus();
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[] {"ID Categoría", "Categoría"});
        modelo.setRowCount(0);

        try (Connection cnx = cn.Conectar(); 
             CallableStatement cstmt = cnx.prepareCall("{CALL sp_obtener_categorias()}")) {
            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[] { rs.getString("id_categoria"), rs.getString("categoria") });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }

        tb_categoria.setModel(modelo);
    }

    private boolean validarCampos() {
        if (txt_id_categoria.getText().isEmpty() || txt_categoria.getText().isEmpty()) {
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
                    "Categoria",
                    JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (!validarCampos()) return;

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(txt_id_categoria.getText());
            categoria.setCategoria(txt_categoria.getText());

            try (Connection cnx = cn.Conectar()) {
                String cad_sql;
                CallableStatement cstmt;

                if (e.getSource() == btn_agregar) {
                    cad_sql = "{CALL sp_agregar_categoria(?, ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, categoria.getIdCategoria());
                    cstmt.setString(2, categoria.getCategoria());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Categoría Registrada");

                } else if (e.getSource() == btn_editar) {
                    cad_sql = "{CALL sp_actualizar_categoria(?, ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, categoria.getIdCategoria());
                    cstmt.setString(2, categoria.getCategoria());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Categoría Actualizada");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        cad_sql = "{CALL sp_eliminar_categoria(?)}";
                        cstmt = cnx.prepareCall(cad_sql);
                        cstmt.setString(1, categoria.getIdCategoria());
                        cstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Categoría Eliminada");
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
        CategoriaCRUD frm = new CategoriaCRUD();
        frm.setVisible(true);
    }
}
