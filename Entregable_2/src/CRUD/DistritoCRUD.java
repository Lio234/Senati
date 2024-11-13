package CRUD;

import Conexion.conexionMYSQL;
import Conexion.conexionSQL;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class DistritoCRUD extends JInternalFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_id_distrito, lbl_distrito, lbl_departamento, lbl_provincia;
    private JTextField txt_id_distrito, txt_distrito;
    private JComboBox<String> cb_departamento, cb_provincia;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_distrito;
    private JScrollPane scr_distrito;

    private final conexionMYSQL cn = new conexionMYSQL();
  //private final conexionSQL cn = new conexionMYSQL();
  
    private final HashMap<String, String> provinciaMap = new HashMap<>(); // Almacenar id y nombre de la provincia
    private final HashMap<String, String> departamentoMap = new HashMap<>(); // Almacenar id y nombre de departamento

    public DistritoCRUD() {
        super("CRUD Distrito");
        IniciarFormulario();
        IniciarControles();
        LlenarComboDepartamentos();
        LimpiarDatos();
        MostrarDatos();
    }

    private void IniciarFormulario() {
        this.setSize(600, 500);
        //this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); // Para evitar que el usuario cambie el tamaño de la ventana
    }

    private void IniciarControles() {
        lbl_titulo = new JLabel("CRUD Distritos");
        lbl_titulo.setFont(new Font("Arial", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(160, 20, 300, 30);

        lbl_id_distrito = new JLabel("ID Distrito");
        lbl_id_distrito.setBounds(30, 70, 120, 25);
        txt_id_distrito = new JTextField();
        txt_id_distrito.setBounds(160, 70, 120, 25);

        lbl_distrito = new JLabel("Distrito");
        lbl_distrito.setBounds(30, 110, 120, 25);
        txt_distrito = new JTextField();
        txt_distrito.setBounds(160, 110, 200, 25);

        lbl_departamento = new JLabel("Departamento");
        lbl_departamento.setBounds(30, 150, 120, 25);
        cb_departamento = new JComboBox<>();
        cb_departamento.setBounds(160, 150, 200, 25);
        cb_departamento.addActionListener(this);

        lbl_provincia = new JLabel("Provincia");
        lbl_provincia.setBounds(30, 190, 120, 25);
        cb_provincia = new JComboBox<>();
        cb_provincia.setBounds(160, 190, 200, 25);

        // Botones
        btn_nuevo = new JButton("NUEVO");
        btn_nuevo.setBounds(30, 230, 100, 30);
        btn_nuevo.addActionListener(this);

        btn_agregar = new JButton("AGREGAR");
        btn_agregar.setBounds(140, 230, 100, 30);
        btn_agregar.addActionListener(this);

        btn_editar = new JButton("EDITAR");
        btn_editar.setBounds(250, 230, 100, 30);
        btn_editar.addActionListener(this);

        btn_borrar = new JButton("BORRAR");
        btn_borrar.setBounds(360, 230, 100, 30);
        btn_borrar.addActionListener(this);

        btn_cerrar = new JButton("CERRAR");
        btn_cerrar.setFont(new Font("Consolas", Font.BOLD, 14));
        btn_cerrar.setBackground(Color.RED);
        btn_cerrar.setForeground(Color.WHITE);
        btn_cerrar.setBounds(160, 400, 200, 30);
        btn_cerrar.addActionListener(this);

        // Tabla
        tb_distrito = new JTable();
        tb_distrito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tb_distrito.getSelectedRow();
                if (row != -1) {
                    txt_id_distrito.setText(tb_distrito.getValueAt(row, 0).toString());
                    txt_distrito.setText(tb_distrito.getValueAt(row, 1).toString());

                    String provinciaNombre = tb_distrito.getValueAt(row, 2).toString();
                    String departamentoNombre = tb_distrito.getValueAt(row, 3).toString();
                    cb_provincia.setSelectedItem(provinciaNombre);
                    cb_departamento.setSelectedItem(departamentoNombre);

                    String idDepartamento = departamentoMap.get(departamentoNombre);
                    if (idDepartamento != null) {
                        LlenarComboProvincias(idDepartamento); // Llenar provincias
                    }
                }
            }
        });
        scr_distrito = new JScrollPane(tb_distrito);
        scr_distrito.setBounds(30, 280, 520, 90);

        // Agregar todo al formulario
        this.add(lbl_titulo);
        this.add(lbl_id_distrito);
        this.add(txt_id_distrito);
        this.add(lbl_distrito);
        this.add(txt_distrito);
        this.add(lbl_departamento);
        this.add(cb_departamento);
        this.add(lbl_provincia);
        this.add(cb_provincia);
        this.add(btn_nuevo);
        this.add(btn_agregar);
        this.add(btn_editar);
        this.add(btn_borrar);
        this.add(btn_cerrar);
        this.add(scr_distrito);
    }

    private void EliminarDistrito(String idDistrito) {
    try (Connection cnx = cn.Conectar()) {
        CallableStatement stmt = cnx.prepareCall("CALL sp_eliminar_distrito(?)");
      //CallableStatement stmt = cnx.prepareCall("{CALL sp_eliminar_distrito(?)}");
        stmt.setInt(1, Integer.parseInt(idDistrito));

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(this, "Distrito eliminado correctamente.");
            MostrarDatos();  // Actualizar la tabla
            LimpiarDatos();  // Limpiar los campos
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el distrito con el ID proporcionado.");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al eliminar el distrito: " + e.getMessage());
    }
}


private void LlenarComboDepartamentos() {
        try (Connection cnx = cn.Conectar()) {
            CallableStatement stmt = cnx.prepareCall("CALL sp_mostrar_departamentos()");
          //CallableStatement stmt = cnx.prepareCall("{CALL sp_mostrar_departamentos()}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_departamento");
                String nombre = rs.getString("departamento");
                cb_departamento.addItem(nombre);
                departamentoMap.put(nombre, id); // Guardamos el id en el map
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los departamentos: " + e.getMessage());
        }
    }

    // Llenar combo de Provincias según el Departamento seleccionado usando el procedimiento almacenado
    private void LlenarComboProvincias(String id_departamento) {
        cb_provincia.removeAllItems(); // Limpiar el JComboBox antes de llenarlo

        try (Connection cnx = cn.Conectar()) {
            CallableStatement stmt = cnx.prepareCall("CALL sp_mostrar_provincias_por_departamento(?)");
          //CallableStatement stmt = cnx.prepareCall("{CALL sp_mostrar_provincias_por_departamento(?)}");  
            stmt.setString(1, id_departamento);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id_provincia");
                String nombre = rs.getString("provincia");
                cb_provincia.addItem(nombre);
                provinciaMap.put(nombre, id); // Guardamos el id en el map
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las provincias: " + e.getMessage());
        }
    }

    // Limpiar campos
    private void LimpiarDatos() {
        txt_id_distrito.setText("");
        txt_distrito.setText("");
        cb_departamento.setSelectedIndex(-1);
        cb_provincia.setSelectedIndex(-1);
    }

    // Mostrar los distritos en la tabla usando JOIN
    private void MostrarDatos() {
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new Object[]{"ID Distrito", "Distrito", "Provincia", "Departamento"});

    try (Connection cnx = cn.Conectar()) {
        CallableStatement stmt = cnx.prepareCall("CALL sp_mostrar_distritos()");
      //CallableStatement stmt = cnx.prepareCall("{CALL sp_mostrar_distritos()}");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String idDistrito = rs.getString("id_distrito");
            String distrito = rs.getString("distrito");
            String provincia = rs.getString("provincia");
            String departamento = rs.getString("departamento");
            modelo.addRow(new Object[]{idDistrito, distrito, provincia, departamento});
        }

        tb_distrito.setModel(modelo);
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
    }
}

<<<<<<< HEAD
=======
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
            if (e.getSource() == btn_cerrar) {
            int op = JOptionPane.showConfirmDialog(null,
                    "¿Seguro de cerrar?",
                    "Distrito",
                    JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btn_agregar) {
            if (validarCampos()) {
                String id_Provincia = provinciaMap.get(cb_provincia.getSelectedItem());
                String id_Departamento = departamentoMap.get(cb_departamento.getSelectedItem());
>>>>>>> 224bb7453fe9abaec916c71e024d12dbd467f3fe

    // Validar campos antes de insertar
    private boolean validarCampos() {
        if (txt_id_distrito.getText().isEmpty() || txt_distrito.getText().isEmpty() ||
            cb_provincia.getSelectedIndex() == -1 || cb_departamento.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return false;
        }
        try {
            Integer.parseInt(txt_id_distrito.getText()); // Verificar si el ID es numérico
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.");
            return false;
        }
        return true;
    }

    // Agregar nuevo distrito
    private void AgregarDistrito() {
    if (validarCampos()) {
        String id_Provincia = provinciaMap.get(cb_provincia.getSelectedItem());
        String id_Departamento = departamentoMap.get(cb_departamento.getSelectedItem());

        try (Connection cnx = cn.Conectar()) {
            CallableStatement stmt = cnx.prepareCall("CALL sp_insertar_distrito(?, ?, ?)");
          //CallableStatement stmt = cnx.prepareCall("{CALL sp_insertar_distrito(?, ?, ?)}");
            stmt.setInt(1, Integer.parseInt(txt_id_distrito.getText()));
            stmt.setString(2, txt_distrito.getText());
            stmt.setInt(3, Integer.parseInt(id_Provincia));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Distrito agregado correctamente.");
            MostrarDatos();
            LimpiarDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar el distrito: " + e.getMessage());
        }
    }
}


    // Manejar eventos de botones
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btn_agregar) {
            AgregarDistrito();
        } else if (e.getSource() == btn_borrar) {
            String idDistrito = txt_id_distrito.getText();
            if (!idDistrito.isEmpty()) {
                EliminarDistrito(idDistrito);
            } else {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un distrito para eliminar.");
            }
        } else if (e.getSource() == btn_editar) {
            EditarDistrito();
        } else if (e.getSource() == btn_cerrar) {
            System.exit(0);
        }
    }

    // Editar un distrito seleccionado
    private void EditarDistrito() {
    if (validarCampos()) {
        String id_Provincia = provinciaMap.get(cb_provincia.getSelectedItem());
        String id_Departamento = departamentoMap.get(cb_departamento.getSelectedItem());

        try (Connection cnx = cn.Conectar()) {
            CallableStatement stmt = cnx.prepareCall("CALL sp_actualizar_distrito(?, ?, ?)");
          //CallableStatement stmt = cnx.prepareCall("{CALL sp_actualizar_distrito(?, ?, ?)}");
            stmt.setInt(1, Integer.parseInt(txt_id_distrito.getText()));
            stmt.setString(2, txt_distrito.getText());
            stmt.setInt(3, Integer.parseInt(id_Provincia));

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Distrito actualizado correctamente.");
            MostrarDatos();
            LimpiarDatos();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el distrito: " + ex.getMessage());
        }
    }
}


    public static void main(String[] args) {
        new DistritoCRUD().setVisible(true);
    }
}
