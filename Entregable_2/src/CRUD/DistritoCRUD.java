package CRUD;

import Conexion.conexionMYSQL;
import Modelos.Distrito;
import Modelos.Provincia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;

public class DistritoCRUD extends JFrame implements ActionListener {
    private JLabel lbl_titulo, lbl_id_distrito, lbl_distrito, lbl_departamento, lbl_provincia;
    private JTextField txt_id_distrito, txt_distrito;
    private JComboBox<String> cb_departamento, cb_provincia;
    private JButton btn_nuevo, btn_agregar, btn_editar, btn_borrar, btn_cerrar;
    private JTable tb_distrito;
    private JScrollPane scr_distrito;

    private final conexionMYSQL cn = new conexionMYSQL();
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
        this.setLocationRelativeTo(null);
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
                txt_id_distrito.setText(tb_distrito.getValueAt(row, 0).toString());
                txt_distrito.setText(tb_distrito.getValueAt(row, 1).toString());

                // Obtener el nombre de la provincia y departamento desde la tabla
                String provinciaNombre = tb_distrito.getValueAt(row, 2).toString();
                String departamentoNombre = tb_distrito.getValueAt(row, 3).toString();

                // Seleccionar la provincia en el JComboBox de provincias
                cb_provincia.setSelectedItem(provinciaNombre);

                // Seleccionar el departamento en el JComboBox de departamentos
                cb_departamento.setSelectedItem(departamentoNombre);

                // Obtener el id_departamento desde el departamento seleccionado
                String idDepartamento = departamentoMap.get(departamentoNombre);

                // Cargar provincias basadas en el id_departamento seleccionado
                if (idDepartamento != null) {
                    LlenarComboProvincias(idDepartamento); // Llenar el combo de provincias de acuerdo al departamento
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

    // Llenar combo de Departamentos
    private void LlenarComboDepartamentos() {
        try (Connection cnx = cn.Conectar()) {
            Statement stmt = cnx.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id_departamento, departamento FROM tb_departamento");

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

    // Llenar combo de Provincias según el Departamento seleccionado
    private void LlenarComboProvincias(String id_departamento) {
        cb_provincia.removeAllItems(); // Limpiar el JComboBox antes de llenarlo

        try (Connection cnx = cn.Conectar()) {
            PreparedStatement stmt = cnx.prepareStatement("SELECT id_provincia, provincia FROM tb_provincia WHERE id_departamento = ?");
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
            Statement stmt = cnx.createStatement();
            // Usamos JOIN para combinar las tablas y obtener los nombres de provincia y departamento
            ResultSet rs = stmt.executeQuery(
                "SELECT d.id_distrito, d.distrito, p.provincia, dep.departamento " +
                "FROM tb_distrito d " +
                "JOIN tb_provincia p ON d.id_provincia = p.id_provincia " +
                "JOIN tb_departamento dep ON p.id_departamento = dep.id_departamento"
            );

            while (rs.next()) {
                String idDistrito = rs.getString("id_distrito");
                String distrito = rs.getString("distrito");
                String provincia = rs.getString("provincia");
                String departamento = rs.getString("departamento");

                modelo.addRow(new Object[]{idDistrito, distrito, provincia, departamento});
            }
            tb_distrito.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los distritos: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
            dispose();
        } else if (e.getSource() == btn_nuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btn_agregar) {
            if (validarCampos()) {
                String id_Provincia = provinciaMap.get(cb_provincia.getSelectedItem());
                String id_Departamento = departamentoMap.get(cb_departamento.getSelectedItem());

                Distrito distrito = new Distrito();
                distrito.setIdDistrito(txt_id_distrito.getText());
                distrito.setDistrito(txt_distrito.getText());
                distrito.setIdProvincia(id_Provincia);

                try (Connection cnx = cn.Conectar()) {
                    PreparedStatement ps = cnx.prepareStatement(
                            "INSERT INTO tb_distrito (id_distrito, distrito, id_provincia) VALUES (?, ?, ?)");
                    ps.setString(1, distrito.getIdDistrito());
                    ps.setString(2, distrito.getDistrito());
                    ps.setString(3, distrito.getIdProvincia());
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Distrito Registrado");
                    MostrarDatos();
                    LimpiarDatos();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al realizar la operación: " + ex.getMessage());
                }
            }
        }
    }

    // Validar campos
    private boolean validarCampos() {
        if (txt_id_distrito.getText().isEmpty() || txt_distrito.getText().isEmpty() ||
            cb_provincia.getSelectedIndex() == -1 || cb_departamento.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Todos los campos deben estar completos.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        DistritoCRUD frm = new DistritoCRUD();
        frm.setVisible(true);
    }
}
