package CRUD;

import Conexion.conexionMYSQL;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;

import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

public class Pedido extends JInternalFrame implements ActionListener {

    private JLabel lblIdPedido, lblFecha, lblTotal, lblClientes;
    private JTextField txtIdPedido, txtTotal;
    private JDateChooser dateChooser;
    private JComboBox<String> cmbClientes;
    private JButton btnGuardar, btnCerrar, btnNuevo, btnEditar, btnEliminar, btnFiltrar;
    private JTable tblPedidos;
    private DefaultTableModel modeloPedidos;

    public Pedido() {
        super();
        IniciarFormulario();
        IniciarControles();
        cargarClientes();
        cargarPedidos();
        agregarEventoEliminarFila();
        agregarEventoFilaSeleccionada();
    }

private void agregarEventoFilaSeleccionada() {
    tblPedidos.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Verificar que la selección sea válida (es decir, que se haya hecho clic en una fila)
            int filaSeleccionada = tblPedidos.getSelectedRow();
            if (filaSeleccionada != -1) {
                // Obtener los valores de las celdas seleccionadas
                String idPedido = modeloPedidos.getValueAt(filaSeleccionada, 0).toString();
                String idCliente = modeloPedidos.getValueAt(filaSeleccionada, 1).toString();  // Obtener el id_cliente de la fila
                Date fecha = (Date) modeloPedidos.getValueAt(filaSeleccionada, 2);
                Float total = (Float) modeloPedidos.getValueAt(filaSeleccionada, 3);

                // Rellenar los campos con los valores del pedido
                txtIdPedido.setText(idPedido);
                dateChooser.setDate(fecha);
                txtTotal.setText(total.toString());

                // Consulta para obtener solo el nombre del cliente
                String sql = "SELECT nombre FROM tb_cliente WHERE id_cliente = ?";
                try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {

                    pstm.setString(1, idCliente);  // Establecer el id_cliente en la consulta

                    ResultSet rs = pstm.executeQuery();  // Ejecutar la consulta y obtener el resultado

                    if (rs.next()) {
                        // Obtener solo el nombre del cliente
                        String nombreCliente = rs.getString("nombre");

                        // Actualizar el JComboBox con solo el nombre del cliente
                        cmbClientes.setSelectedItem(nombreCliente);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();  // Manejo de excepciones
                }
            }
        }
    });
}

    private void agregarEventoEliminarFila() {
        tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Doble clic para eliminar
                    int filaSeleccionada = tblPedidos.getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String idPedido = modeloPedidos.getValueAt(filaSeleccionada, 0).toString();
                        int confirmacion = JOptionPane.showConfirmDialog(
                                null,
                                "¿Seguro que desea eliminar el pedido con ID: " + idPedido + "?",
                                "Confirmación de eliminación",
                                JOptionPane.YES_NO_OPTION
                        );
                        if (confirmacion == JOptionPane.YES_OPTION) {
                            eliminarPedido(idPedido);
                        }
                    }
                }
            }
        });
    }

    public void IniciarFormulario() {
        this.setTitle("Pedido");
        this.setSize(500, 500);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void IniciarControles() {
        // Crear el formato de fecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");

        lblIdPedido = new JLabel("ID Pedido:");
        lblIdPedido.setBounds(20, 30, 80, 25);

        txtIdPedido = new JTextField();
        txtIdPedido.setBounds(100, 30, 200, 25);

        lblFecha = new JLabel("Fecha:");
        lblFecha.setBounds(20, 70, 80, 25);

        lblTotal = new JLabel("Total:");
        lblTotal.setBounds(20, 110, 80, 25);

        lblClientes = new JLabel("Clientes:");
        lblClientes.setBounds(20, 150, 80, 25);

        txtTotal = new JTextField();
        txtTotal.setBounds(100, 110, 200, 25);

        // Configurar el JDateChooser
        dateChooser = new JDateChooser();
        dateChooser.setBounds(100, 70, 200, 25);
        dateChooser.setDateFormatString("yyyy/MM/dd"); // Formato de fecha que permite la entrada manual
        dateChooser.setPreferredSize(new java.awt.Dimension(200, 25));  // Ajustar el tamaño

        // Para permitir la edición manual, no deshabilites el campo de texto en JDateChooser
        dateChooser.setEnabled(true); // Asegura que el campo sea editable manualmente

        cmbClientes = new JComboBox<>();
        cmbClientes.setBounds(100, 150, 200, 25);

        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(50, 200, 100, 30);
        btnGuardar.addActionListener(this);

        btnCerrar = new JButton("Cerrar");
        btnCerrar.setBounds(170, 200, 100, 30);
        btnCerrar.addActionListener(this);

        btnNuevo = new JButton("Nuevo");
        btnNuevo.setBounds(20, 250, 100, 30);
        btnNuevo.addActionListener(this);

        btnEditar = new JButton("Editar");
        btnEditar.setBounds(130, 250, 100, 30);
        btnEditar.addActionListener(this);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(240, 250, 100, 30);
        btnEliminar.addActionListener(this);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.setBounds(350, 250, 100, 30);
        btnFiltrar.addActionListener(this);

        modeloPedidos = new DefaultTableModel();
        tblPedidos = new JTable(modeloPedidos);
        modeloPedidos.addColumn("ID Pedido");
        modeloPedidos.addColumn("ID Cliente");
        modeloPedidos.addColumn("Fecha");
        modeloPedidos.addColumn("Total");

        JScrollPane scrollPane = new JScrollPane(tblPedidos);
        scrollPane.setBounds(20, 300, 450, 150);

        this.add(lblIdPedido);
        this.add(txtIdPedido);
        this.add(lblFecha);
        this.add(lblTotal);
        this.add(lblClientes);
        this.add(txtTotal);
        this.add(dateChooser);
        this.add(cmbClientes);
        this.add(btnGuardar);
        this.add(btnCerrar);
        this.add(btnNuevo);
        this.add(btnEditar);
        this.add(btnEliminar);
        this.add(btnFiltrar);
        this.add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnCerrar) {
            dispose();
            return;
        }

        if (e.getSource() == btnNuevo) {
            LimpiarDatos();
        } else if (e.getSource() == btnFiltrar) {
            filtrarPedidosPorCliente();
        } else if (e.getSource() == btnGuardar) {
            // Obtener los valores de los campos para guardar
            String idPedido = txtIdPedido.getText();
            String total = txtTotal.getText();
            Date fecha = dateChooser.getDate();
            String idCliente = (String) cmbClientes.getSelectedItem();

            // Verificación de campos para la acción de guardar
            if (idPedido.isEmpty() || total.isEmpty() || fecha == null || idCliente == null) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos.");
                return;
            }
            guardarPedido(idPedido, fecha, Float.parseFloat(total), idCliente);

        } else if (e.getSource() == btnEditar) {
            // No se requiere validación completa para editar
            String idPedido = txtIdPedido.getText();
            Date fecha = dateChooser.getDate();
            String totalText = txtTotal.getText();
            String idCliente = (String) cmbClientes.getSelectedItem();

            float total = totalText.isEmpty() ? 0 : Float.parseFloat(totalText);
            editarPedido(idPedido, fecha, total, idCliente);

        } else if (e.getSource() == btnEliminar) {
            // Validación: si el campo de idPedido está vacío, mostrar un mensaje
            String idPedido = txtIdPedido.getText();

            if (idPedido.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un ID de pedido para eliminar.");
                return;  // No continuar si el ID está vacío
            }

            eliminarPedido(idPedido);
        }
    }

    private void cargarClientes() {
        String sql = "SELECT id_cliente, nombre FROM tb_cliente";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            // Limpiar los elementos existentes en el JComboBox
            cmbClientes.removeAllItems();

            // Agregar un ítem por defecto (vacío o "Seleccione un cliente")
            cmbClientes.addItem("Seleccione un cliente");

            // Cargar los clientes de la base de datos
            while (rs.next()) {
                // Obtener el nombre del cliente
                String nombreCliente = rs.getString("nombre");

                // Agregar solo el nombre del cliente al JComboBox
                cmbClientes.addItem(nombreCliente);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
        }
    }

    private void guardarPedido(String idPedido, Date fecha, float total, String clienteSeleccionado) {
        // Extraer el id_cliente basándonos en el nombre seleccionado
        String idCliente = obtenerIdClientePorNombre(clienteSeleccionado);

        String sql = "{ CALL sp_guardarPedido(?, ?, ?, ?) }";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {
            cstm.setString(1, idPedido);
            cstm.setDate(2, new java.sql.Date(fecha.getTime()));
            cstm.setFloat(3, total);
            cstm.setString(4, idCliente);  // Pasamos solo el id_cliente
            cstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido Guardado");
            cargarPedidos();
            LimpiarDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido: " + e.getMessage());
        }
    }

    private String obtenerIdClientePorNombre(String nombreCliente) {
        // Realizar una consulta para obtener el id_cliente basado en el nombre
        String sql = "SELECT id_cliente FROM tb_cliente WHERE nombre = ?";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {
            pstm.setString(1, nombreCliente);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString("id_cliente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del cliente: " + e.getMessage());
        }
        return null;  // Retornar null si no se encuentra el cliente
    }

    private void editarPedido(String idPedido, Date fecha, float total, String nombreCliente) {
        String idCliente = obtenerIdClientePorNombre(nombreCliente);  // Obtener id_cliente desde el nombre

        // Llamada al procedimiento almacenado
        String sql = "{ CALL sp_actualizar_pedido(?, ?, ?, ?) }";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {
            cstm.setString(1, idPedido);
            cstm.setDate(2, new java.sql.Date(fecha.getTime()));
            cstm.setFloat(3, total);
            cstm.setString(4, idCliente);
            cstm.execute();
            JOptionPane.showMessageDialog(null, "Pedido Actualizado");
            cargarPedidos();
            LimpiarDatos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el pedido: " + e.getMessage());
        }
    }

    private void eliminarPedido(String idPedido) {
        int confirmacion = JOptionPane.showConfirmDialog(
                null,
                "¿Seguro de borrar el pedido?",
                "Confirmación de eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            String sql = "{ CALL sp_eliminar_pedido(?) }";
            try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {

                cstm.setString(1, idPedido);
                cstm.executeUpdate();

                JOptionPane.showMessageDialog(null, "Pedido Eliminado");
                cargarPedidos();  // Refresca la tabla de pedidos
                LimpiarDatos();   // Limpia los campos del formulario
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar el pedido: " + e.getMessage() + " (SQLState: " + e.getSQLState() + ")");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            }
        }
    }

    private void LimpiarDatos() {
        // Limpiar los campos de entrada
        txtIdPedido.setText("");
        txtTotal.setText("");
        dateChooser.setDate(null);
        cmbClientes.setSelectedIndex(-1);

        // Volver a cargar la tabla con todos los pedidos
        cargarPedidos();
    }

    private void cargarPedidos() {
        String sql = "{ CALL sp_listar_pedidos() }";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql); ResultSet rs = cstm.executeQuery()) {

            modeloPedidos.setRowCount(0);
            while (rs.next()) {
                modeloPedidos.addRow(new Object[]{
                    rs.getString("id_pedido"),
                    rs.getString("cliente"),
                    rs.getDate("fecha"),
                    rs.getFloat("total")
                });
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        }
    }

private void filtrarPedidosPorCliente() {
    // Obtener el nombre del cliente a partir de la selección en el JComboBox
    String nombreCliente = (String) cmbClientes.getSelectedItem();
    
    // Verificar si se seleccionó un cliente válido
    if (nombreCliente == null || nombreCliente.equals("Seleccione un cliente")) {
        JOptionPane.showMessageDialog(null, "Seleccione un cliente válido.");
        return;
    }
    
    // Obtener el id_cliente correspondiente al nombre del cliente
    String idCliente = obtenerIdClientePorNombre(nombreCliente);

    // Llamada al procedimiento almacenado que filtra por id_cliente
    String sql = "{ CALL sp_filtrar_pedidos_por_cliente(?) }";
    try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {
        // Establece el idCliente en el parámetro del procedimiento almacenado
        cstm.setString(1, idCliente);

        try (ResultSet rs = cstm.executeQuery()) {
            modeloPedidos.setRowCount(0); // Limpiar la tabla antes de agregar los datos

            // Llenar la tabla con los datos obtenidos del ResultSet
            while (rs.next()) {
                modeloPedidos.addRow(new Object[]{
                    rs.getString("id_pedido"),
                    nombreCliente, // Mostrar el nombre del cliente
                    rs.getDate("fecha"),
                    rs.getFloat("total")
                });
            }
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al filtrar pedidos: " + e.getMessage());
    }
}}
