package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Producto;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
// import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class ProductoCRUD extends JInternalFrame implements ActionListener {

    private JLabel lbl_titulo, lbl_id_producto, lbl_producto, lbl_costo, lbl_ganancia, lbl_id_marca, lbl_id_categoria;
    private JTextField txt_id_producto, txt_producto, txt_costo, txt_ganancia;
    private JComboBox cbo_id_marca, cbo_id_categoria;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_producto;
    private JScrollPane scr_producto;

    private final conexionMYSQL cn = new conexionMYSQL();

    private ArrayList<String[]> arr_marca;
    private ArrayList<String[]> arr_categoria;

    public ProductoCRUD() {
        super("CRUD Producto");
        IniciarFormulario();
        IniciarControles();
        MostrarDatos();
    }

    public void IniciarFormulario() {
        this.setTitle("Producto");
        this.setSize(430, 540);

        // this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void IniciarControles() {
        lbl_titulo = new JLabel("Producto");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_id_producto = new JLabel("ID Producto");
        lbl_id_producto.setBounds(20, 50, 120, 25);
        txt_id_producto = new JTextField();
        txt_id_producto.setBounds(150, 50, 80, 25);

        lbl_producto = new JLabel("Producto");
        lbl_producto.setBounds(20, 80, 110, 25);
        txt_producto = new JTextField();
        txt_producto.setBounds(20, 110, 110, 25);

        lbl_costo = new JLabel("Costo");
        lbl_costo.setBounds(150, 80, 110, 25);
        txt_costo = new JTextField();
        txt_costo.setBounds(150, 110, 110, 25);

        lbl_ganancia = new JLabel("Ganancia");
        lbl_ganancia.setBounds(280, 80, 110, 25);
        txt_ganancia = new JTextField();
        txt_ganancia.setBounds(280, 110, 110, 25);

        lbl_id_categoria = new JLabel("Categoria");
        lbl_id_categoria.setBounds(20, 140, 110, 25);
        cbo_id_categoria = new JComboBox();
        cbo_id_categoria.setBounds(20, 170, 110, 25);

        lbl_id_marca = new JLabel("Marca");
        lbl_id_marca.setBounds(150, 140, 110, 25);
        cbo_id_marca = new JComboBox();
        cbo_id_marca.setBounds(150, 170, 110, 25);

        arr_categoria = this.ObtenerCategoria();
        arr_marca = this.ObtenerMarca();

        for (String[] categoria : arr_categoria) {
            cbo_id_categoria.addItem(categoria[1]);
        }
        for (String[] marca : arr_marca) {
            cbo_id_marca.addItem(marca[1]);
        }

        btn_nuevo = new JButton();
        btn_nuevo.setText("NUEVO");
        btn_nuevo.setBounds(10, 250, 90, 25);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton();
        btn_agregar.setText("AGREGAR");
        btn_agregar.setBounds(110, 250, 90, 25);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton();
        btn_editar.setText("EDITAR");
        btn_editar.setBounds(210, 250, 90, 25);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton();
        btn_borrar.setText("BORRAR");
        btn_borrar.setBounds(310, 250, 90, 25);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(110, 450, 180, 30);
        btn_cerrar.addActionListener(this);

        tb_producto = new JTable();

        tb_producto.setRowHeight(20);
        tb_producto.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scr_producto = new JScrollPane(tb_producto);
        scr_producto.setBounds(10, 280, 390, 150);

        this.add(lbl_titulo);
        this.add(lbl_id_producto);
        this.add(txt_id_producto);
        this.add(lbl_producto);
        this.add(txt_producto);
        this.add(lbl_costo);
        this.add(txt_costo);
        this.add(lbl_ganancia);
        this.add(txt_ganancia);
        this.add(cbo_id_categoria);
        this.add(lbl_id_categoria);
        this.add(cbo_id_marca);
        this.add(lbl_id_marca);
        this.add(btn_agregar);
        this.add(btn_borrar);
        this.add(btn_editar);
        this.add(btn_nuevo);
        this.add(scr_producto);
        this.add(scr_producto);
        this.add(btn_cerrar);

        ControladorTxt ctxt = new ControladorTxt();
        txt_id_producto.addKeyListener(ctxt);
        txt_producto.addKeyListener(ctxt);
        txt_costo.addKeyListener(ctxt);
        txt_ganancia.addKeyListener(ctxt);

        ControladorClick click = new ControladorClick();

        tb_producto.addMouseListener(click);
    }

    private void LimpiarDatos() {
        txt_id_producto.setEditable(true);

        tb_producto.clearSelection();

        txt_id_producto.setText("");
        txt_producto.setText("");
        txt_costo.setText("");
        txt_ganancia.setText("");
        cbo_id_categoria.setSelectedIndex(0);
        cbo_id_marca.setSelectedIndex(0);
        txt_id_producto.requestFocus();
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };

        modelo.setRowCount(0);

        Connection cnx = null;
        Statement stm = null;
        ResultSet rs = null;

        try {
            cnx = cn.Conectar();
            stm = cnx.createStatement();

            rs = stm.executeQuery("call sp_obtener_productos();");

            int nc = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= nc; i++) {
                modelo.addColumn(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                Object[] arr_filas = new Object[nc];

                for (int i = 0; i < nc; i++) {
                    arr_filas[i] = rs.getObject(i + 1);
                }

                modelo.addRow(arr_filas);
            }

        } catch (SQLException e1) {

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stm != null) {
                    stm.close();
                }
                if (cnx != null) {
                    cnx.close();
                }
            } catch (SQLException e2) {

            }
        }

        tb_producto.setModel(modelo);
        tb_producto.setRowHeight(22);

        DefaultTableCellRenderer alinearCentro = new DefaultTableCellRenderer();

        alinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel arr_col = tb_producto.getColumnModel();

        arr_col.getColumn(0).setPreferredWidth(50);
        arr_col.getColumn(0).setCellRenderer(alinearCentro);

        arr_col.getColumn(1).setPreferredWidth(120);
    }

    private ArrayList<String[]> ObtenerCategoria() {
        ArrayList<String[]> arr_lista_cat = new ArrayList<>();

        String cad_sql = "call sp_obtener_categorias;";

        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista_cat.add(new String[]{"", "Seleccione la Categoria"});

                while (rs.next()) {
                    String id = rs.getString("id_categoria");
                    String distrito = rs.getString("categoria");

                    arr_lista_cat.add(new String[]{id, distrito});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }

        return arr_lista_cat;
    }

    private ArrayList<String[]> ObtenerMarca() {
        ArrayList<String[]> arr_lista_mar = new ArrayList<>();

        String cad_sql = "call sp_obtener_marcas;";

        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista_mar.add(new String[]{"", "Seleccione la Marca"});

                while (rs.next()) {
                    String id = rs.getString("id_marca");
                    String distrito = rs.getString("marca");

                    arr_lista_mar.add(new String[]{id, distrito});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }

        return arr_lista_mar;
    }

    private class ControladorTxt implements KeyListener {

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getSource() == txt_id_producto && txt_id_producto.getText().length() == 5) {
                e.consume();
            } else if (e.getSource() == txt_producto && txt_producto.getText().length() == 40) {
                e.consume();
            } else if (e.getSource() == txt_costo && txt_costo.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_ganancia && txt_ganancia.getText().length() == 5) {
                e.consume();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }
    }

    private class ControladorClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int registro = tb_producto.getSelectedRow();

            txt_id_producto.setEditable(false);

            String id_producto = (String) tb_producto.getValueAt(registro, 0);

            String cad_sql = "CALL sp_obtener_producto(?)";  // Llamada al procedimiento almacenado

            Connection cnx;

            try {
                cnx = cn.Conectar();  // Estableces la conexión con la base de datos

                java.sql.CallableStatement cstm;
                cstm = cnx.prepareCall(cad_sql);
                cstm.setString(1, id_producto);

                ResultSet rs = cstm.executeQuery();  // Ejecutamos el procedimiento y obtenemos los resultados

                if (rs.next()) {
                    // Recuperamos los datos del ResultSet
                    String producto = rs.getString("producto");
                    float costo = rs.getFloat("costo");
                    float ganancia = rs.getFloat("ganancia");
                    String id_categoria = rs.getString("id_categoria");
                    String id_marca = rs.getString("id_marca");

                    // Actualizamos los campos de texto con los datos obtenidos
                    txt_id_producto.setText(id_producto);
                    txt_producto.setText(producto);
                    txt_costo.setText(String.valueOf(costo));
                    txt_ganancia.setText(String.valueOf(ganancia));

                    // Actualizamos el JComboBox para el cargo
                    for (int i = 0; i < cbo_id_categoria.getItemCount(); i++) {
                        if (arr_categoria.get(i)[0].equals(id_categoria)) {
                            cbo_id_categoria.setSelectedIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < cbo_id_marca.getItemCount(); i++) {
                        if (arr_marca.get(i)[0].equals(id_marca)) {
                            cbo_id_marca.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean validarCampos() {
        if (txt_id_producto.getText().isEmpty() || txt_producto.getText().isEmpty() || txt_costo.getText().isEmpty() || txt_ganancia.getText().isEmpty() || cbo_id_categoria.getSelectedIndex() == 0 || cbo_id_marca.getSelectedIndex() == 0) {
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
                        "Producto",
                        JOptionPane.YES_NO_OPTION);

                if (op == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else {
            if (!validarCampos()) {
                return;
            }

            Producto producto = new Producto();
            producto.setId_producto(txt_id_producto.getText());
            producto.setProducto(txt_producto.getText());
            producto.setCosto(Float.parseFloat(txt_costo.getText()));
            producto.setGanancia(Float.parseFloat(txt_ganancia.getText()));

            String cc = "", nc = cbo_id_categoria.getSelectedItem().toString();
            for (String[] categoria : arr_categoria) {
                if (nc.equals(categoria[1])) {
                    cc = categoria[0];
                    break;
                }
            }
            producto.setId_categoria(cc);

            String cc1 = "", nc1 = cbo_id_marca.getSelectedItem().toString();
            for (String[] marca : arr_marca) {
                if (nc1.equals(marca[1])) {
                    cc1 = marca[0];
                    break;
                }
            }
            producto.setId_marca(cc1);

            try (Connection cnx = cn.Conectar()) {
                String cad_sql;
                CallableStatement cstmt;

                if (e.getSource() == btn_agregar) {
                    cad_sql = "{CALL sp_agregar_producto( ?, ? , ? , ? , ? , ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, producto.getId_producto());
                    cstmt.setString(2, producto.getProducto());
                    cstmt.setFloat(3, producto.getCosto());
                    cstmt.setFloat(4, producto.getGanancia());
                    cstmt.setString(5, producto.getId_marca());
                    cstmt.setString(6, producto.getId_categoria());

                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Producto Registrado");

                } else if (e.getSource() == btn_editar) {
                    cad_sql = "{CALL sp_actualizar_producto(?, ? , ? , ? , ? , ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, producto.getId_producto());
                    cstmt.setString(2, producto.getProducto());
                    cstmt.setFloat(3, producto.getCosto());
                    cstmt.setFloat(4, producto.getGanancia());
                    cstmt.setString(5, producto.getId_marca());
                    cstmt.setString(6, producto.getId_categoria());

                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Producto Actualizado");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        cad_sql = "{CALL sp_eliminar_producto(?)}";
                        cstmt = cnx.prepareCall(cad_sql);
                        cstmt.setString(1, producto.getId_producto());
                        cstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Producto Eliminado");
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
        ProductoCRUD informacion = new ProductoCRUD();

        informacion.setVisible(true);
    }

}
