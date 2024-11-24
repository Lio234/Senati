package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Cliente;
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

public class ClienteCRUD extends JInternalFrame implements ActionListener {

    private JLabel lbl_titulo, lbl_id_cliente, lbl_nombre, lbl_ap_paterno, lbl_ap_materno, lbl_direccion, lbl_correo, lbl_telefono, lbl_distrito;
    private JTextField txt_id_cliente, txt_nombre, txt_ap_paterno, txt_ap_materno, txt_direccion, txt_correo, txt_telefono;
    private JComboBox cbo_id_distrito;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_cliente;
    private JScrollPane scr_cliente;

    private final conexionMYSQL cn = new conexionMYSQL();

    private ArrayList<String[]> arr_distrito;

    public ClienteCRUD() {
        super("CRUD Cliente");
        IniciarFormulario();
        IniciarControles();
        MostrarDatos();
    }

    public void IniciarFormulario() {
        this.setTitle("Cliente");
        this.setSize(430, 540);

        // this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void IniciarControles() {
        lbl_titulo = new JLabel("Cliente");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(10, 15, 300, 25);

        lbl_id_cliente = new JLabel("ID Cliente");
        lbl_id_cliente.setBounds(20, 50, 120, 25);
        txt_id_cliente = new JTextField();
        txt_id_cliente.setBounds(150, 50, 80, 25);

        lbl_nombre = new JLabel("Nombres");
        lbl_nombre.setBounds(20, 80, 110, 25);
        txt_nombre = new JTextField();
        txt_nombre.setBounds(20, 110, 110, 25);

        lbl_ap_paterno = new JLabel("Apellido Paterno");
        lbl_ap_paterno.setBounds(150, 80, 110, 25);
        txt_ap_paterno = new JTextField();
        txt_ap_paterno.setBounds(150, 110, 110, 25);

        lbl_ap_materno = new JLabel("Apellido Materno");
        lbl_ap_materno.setBounds(280, 80, 110, 25);
        txt_ap_materno = new JTextField();
        txt_ap_materno.setBounds(280, 110, 110, 25);

        lbl_direccion = new JLabel("Direccion");
        lbl_direccion.setBounds(20, 140, 110, 25);
        txt_direccion = new JTextField();
        txt_direccion.setBounds(20, 170, 110, 25);

        lbl_correo = new JLabel("Correo Electronico");
        lbl_correo.setBounds(150, 140, 110, 25);
        txt_correo = new JTextField();
        txt_correo.setBounds(150, 170, 110, 25);

        lbl_telefono = new JLabel("Telefono");
        lbl_telefono.setBounds(280, 140, 110, 25);
        txt_telefono = new JTextField();
        txt_telefono.setBounds(280, 170, 110, 25);

        lbl_distrito = new JLabel("Distrito");
        lbl_distrito.setBounds(100, 210, 110, 25);
        cbo_id_distrito = new JComboBox();
        cbo_id_distrito.setBounds(200, 210, 110, 25);

        arr_distrito = this.ObtenerDistrito();

        for (String[] distrito : arr_distrito) {
            cbo_id_distrito.addItem(distrito[1]);
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

        tb_cliente = new JTable();

        tb_cliente.setRowHeight(20);
        tb_cliente.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        scr_cliente = new JScrollPane(tb_cliente);
        scr_cliente.setBounds(10, 280, 390, 150);

        this.add(lbl_titulo);
        this.add(lbl_id_cliente);
        this.add(txt_id_cliente);
        this.add(lbl_nombre);
        this.add(txt_nombre);
        this.add(lbl_ap_paterno);
        this.add(txt_ap_paterno);
        this.add(lbl_ap_materno);
        this.add(txt_ap_materno);
        this.add(lbl_direccion);
        this.add(txt_direccion);
        this.add(lbl_correo);
        this.add(txt_correo);
        this.add(lbl_telefono);
        this.add(txt_telefono);
        this.add(cbo_id_distrito);
        this.add(lbl_distrito);
        this.add(btn_agregar);
        this.add(btn_borrar);
        this.add(btn_editar);
        this.add(btn_nuevo);
        this.add(scr_cliente);
        this.add(scr_cliente);
        this.add(btn_cerrar);

        ControladorTxt ctxt = new ControladorTxt();
        txt_id_cliente.addKeyListener(ctxt);
        txt_nombre.addKeyListener(ctxt);
        txt_ap_materno.addKeyListener(ctxt);
        txt_ap_paterno.addKeyListener(ctxt);
        txt_direccion.addKeyListener(ctxt);
        txt_correo.addKeyListener(ctxt);
        txt_telefono.addKeyListener(ctxt);

        ControladorClick click = new ControladorClick();

        tb_cliente.addMouseListener(click);
    }

    private void LimpiarDatos() {
        txt_id_cliente.setEditable(true);

        tb_cliente.clearSelection();

        txt_id_cliente.setText("");
        txt_nombre.setText("");
        txt_ap_paterno.setText("");
        txt_ap_materno.setText("");
        txt_direccion.setText("");
        txt_correo.setText("");
        txt_telefono.setText("");
        cbo_id_distrito.setSelectedIndex(0);
        txt_id_cliente.requestFocus();
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

            rs = stm.executeQuery("call sp_obtener_clientes();");

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

        tb_cliente.setModel(modelo);
        tb_cliente.setRowHeight(22);

        DefaultTableCellRenderer alinearCentro = new DefaultTableCellRenderer();

        alinearCentro.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel arr_col = tb_cliente.getColumnModel();

        arr_col.getColumn(0).setPreferredWidth(50);
        arr_col.getColumn(0).setCellRenderer(alinearCentro);

        arr_col.getColumn(1).setPreferredWidth(120);
    }

    private ArrayList<String[]> ObtenerDistrito() {
        ArrayList<String[]> arr_lista = new ArrayList<>();

        String cad_sql = "call sp_obtener_distritos;";

        Connection cnx;

        try {
            cnx = cn.Conectar();

            java.sql.PreparedStatement pstm;
            pstm = cnx.prepareStatement(cad_sql);

            try (ResultSet rs = pstm.executeQuery()) {

                arr_lista.add(new String[]{"", "Seleccione el Distrito"});

                while (rs.next()) {
                    String id = rs.getString("id_distrito");
                    String distrito = rs.getString("distrito");

                    arr_lista.add(new String[]{id, distrito});
                }
            }

            pstm.close();
            cnx.close();
        } catch (SQLException ex) {
        }

        return arr_lista;
    }

    private class ControladorTxt implements KeyListener {

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getSource() == txt_telefono && txt_telefono.getText().length() == 12) {
                e.consume();
            } else if (e.getSource() == txt_ap_materno && txt_ap_materno.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_ap_paterno && txt_ap_paterno.getText().length() == 50) {
                e.consume();
            } else if (e.getSource() == txt_direccion && txt_direccion.getText().length() == 50) {
                e.consume();
            } else if (e.getSource() == txt_correo && txt_correo.getText().length() == 50) {
                e.consume();
            } else if (e.getSource() == txt_nombre && txt_nombre.getText().length() == 20) {
                e.consume();
            } else if (e.getSource() == txt_id_cliente && txt_id_cliente.getText().length() == 5) {
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
            int registro = tb_cliente.getSelectedRow();

            txt_id_cliente.setEditable(false);

            String id = (String) tb_cliente.getValueAt(registro, 0);

            String cad_sql = "CALL sp_obtener_cliente(?)";  // Llamada al procedimiento almacenado

            Connection cnx;

            try {
                cnx = cn.Conectar();  // Estableces la conexión con la base de datos

                java.sql.CallableStatement cstm;
                cstm = cnx.prepareCall(cad_sql);
                cstm.setString(1, id);

                ResultSet rs = cstm.executeQuery();  // Ejecutamos el procedimiento y obtenemos los resultados

                if (rs.next()) {
                    // Recuperamos los datos del ResultSet
                    String nombre = rs.getString("nombre");
                    String ap_paterno = rs.getString("ap_paterno");
                    String ap_materno = rs.getString("ap_materno");
                    String direccion = rs.getString("direccion");
                    String correo = rs.getString("correo");
                    String telefono = rs.getString("telefono");
                    String id_distrito = rs.getString("id_distrito");

                    // Actualizamos los campos de texto con los datos obtenidos
                    txt_id_cliente.setText(id);
                    txt_nombre.setText(nombre);
                    txt_ap_paterno.setText(ap_paterno);
                    txt_ap_materno.setText(ap_materno);
                    txt_direccion.setText(direccion);
                    txt_correo.setText(correo);
                    txt_telefono.setText(telefono);

                    // Actualizamos el JComboBox para el cargo
                    for (int i = 0; i < cbo_id_distrito.getItemCount(); i++) {
                        if (arr_distrito.get(i)[0].equals(id_distrito)) {
                            cbo_id_distrito.setSelectedIndex(i);
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
        if (txt_id_cliente.getText().isEmpty() || txt_nombre.getText().isEmpty() || txt_ap_paterno.getText().isEmpty() || txt_ap_materno.getText().isEmpty() || txt_direccion.getText().isEmpty() || txt_correo.getText().isEmpty() || txt_telefono.getText().isEmpty()|| cbo_id_distrito.getSelectedIndex()==0) {
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
                        "Marca",
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

            Cliente cliente = new Cliente();
            cliente.setId_cliente(txt_id_cliente.getText());
            cliente.setNombre(txt_nombre.getText());
            cliente.setApPaterno(txt_ap_paterno.getText());
            cliente.setApMaterno(txt_ap_materno.getText());
            cliente.setDireccion(txt_direccion.getText());
            cliente.setCorreo(txt_correo.getText());
            cliente.setTelefono(txt_telefono.getText());

            String cc = "", nc = cbo_id_distrito.getSelectedItem().toString();

            for (String[] distrito : arr_distrito) {
                if (nc.equals(distrito[1])) {
                    cc = distrito[0];
                    break;
                }
            }

            cliente.setIdDistrito(cc);

            try (Connection cnx = cn.Conectar()) {
                String cad_sql;
                CallableStatement cstmt;

                if (e.getSource() == btn_agregar) {
                    cad_sql = "{CALL sp_agregar_cliente( ?, ? , ? , ? , ? , ?, ? , ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, cliente.getId_cliente());
                    cstmt.setString(2, cliente.getNombre());
                    cstmt.setString(3, cliente.getApPaterno());
                    cstmt.setString(4, cliente.getApMaterno());
                    cstmt.setString(5, cliente.getDireccion());
                    cstmt.setString(6, cliente.getCorreo());
                    cstmt.setString(7, cliente.getTelefono());
                    cstmt.setString(8, cliente.getIdDistrito());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Cliente Registrada");

                } else if (e.getSource() == btn_editar) {
                    cad_sql = "{CALL sp_actualizar_cliente(?, ? , ? , ? , ? , ?, ? , ?)}";
                    cstmt = cnx.prepareCall(cad_sql);
                    cstmt.setString(1, cliente.getId_cliente());
                    cstmt.setString(2, cliente.getNombre());
                    cstmt.setString(3, cliente.getApPaterno());
                    cstmt.setString(4, cliente.getApMaterno());
                    cstmt.setString(5, cliente.getDireccion());
                    cstmt.setString(6, cliente.getCorreo());
                    cstmt.setString(7, cliente.getTelefono());
                    cstmt.setString(8, cliente.getIdDistrito());
                    cstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Cliente Actualizado");

                } else if (e.getSource() == btn_borrar) {
                    int opc = JOptionPane.showConfirmDialog(this, "¿Seguro de borrar el registro?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (opc == JOptionPane.YES_OPTION) {
                        cad_sql = "{CALL sp_eliminar_cliente(?)}";
                        cstmt = cnx.prepareCall(cad_sql);
                        cstmt.setString(1, cliente.getId_cliente());
                        cstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Cliente Eliminada");
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
        ClienteCRUD informacion = new ClienteCRUD();

        informacion.setVisible(true);
    }

}
