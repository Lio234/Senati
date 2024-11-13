package CRUD;

import Conexion.conexionMYSQL;
import Modelos.MPedido;
import Modelos.Detalle_Pedido;
import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.PreparedStatement;
import Modelos.Detalle_Pedido;
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

    private JLabel lblIdPedido, lblFecha, lblCantidad, lblClientes, lblProducto, lblcostouni, lblsubtotal, lbltotal;
    private JTextField txtIdPedido, txtcantidad, txtcostouni, txtsubtotal, txttotal;
    private JDateChooser dateChooser;
    private JComboBox<String> cmbClientes, cmbProducto;
    private JButton btnGuardar, btnCerrar, btnNuevo, btnEditar, btnEliminar, btnFiltrar, btnCambiarVista, btnAgregarProducto;
    private JTable tblPedidos;
    private DefaultTableModel modeloPedidos;
    private boolean vistaDetalle = false;

    public Pedido() {
        super();
        iniciarFormulario();
        iniciarControles();
        cargarClientes();
        cargarPedidos();
        agregarEventoEliminarFila();
        agregarEventoFilaSeleccionada();
        cargarProductos();
    }

    private void iniciarFormulario() {
        setTitle("Pedido");
        setSize(500, 600);
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void iniciarControles() {
        lblIdPedido = new JLabel("ID Pedido:");
        lblFecha = new JLabel("Fecha:");
        lblClientes = new JLabel("Clientes:");
        lblProducto = new JLabel("Producto:");
        lblCantidad = new JLabel("Cantidad:");
        lblcostouni = new JLabel("Costo Uni:");
        lblsubtotal = new JLabel("Subtotal:");
        lbltotal = new JLabel("Total:");

        lblIdPedido.setBounds(20, 30, 80, 25);
        lblFecha.setBounds(20, 70, 80, 25);
        lblClientes.setBounds(20, 110, 80, 25);
        lblProducto.setBounds(20, 150, 80, 25);
        lblCantidad.setBounds(20, 190, 80, 25);
        lblcostouni.setBounds(20, 230, 80, 25);
        lblsubtotal.setBounds(20, 490, 80, 25);
        lbltotal.setBounds(200, 490, 80, 25);

        txtIdPedido = new JTextField();
        txtcantidad = new JTextField();
        txtcostouni = new JTextField();
        txtsubtotal = new JTextField();
        txttotal = new JTextField();

        txtIdPedido.setBounds(100, 30, 200, 25);
        txtcantidad.setBounds(100, 190, 200, 25);
        txtcostouni.setBounds(100, 230, 200, 25);
        txtsubtotal.setBounds(100, 490, 70, 25);
        txttotal.setBounds(250, 490, 50, 25);

        txtcostouni.setEditable(false);
        txtsubtotal.setEditable(false);
        txttotal.setEditable(false);

        dateChooser = new JDateChooser();
        dateChooser.setBounds(100, 70, 200, 25);
        dateChooser.setDateFormatString("yyyy/MM/dd");

        cmbClientes = new JComboBox<>();
        cmbProducto = new JComboBox<>();

        cmbClientes.setBounds(100, 110, 200, 25);
        cmbProducto.setBounds(100, 150, 200, 25);

        modeloPedidos = new DefaultTableModel();
        tblPedidos = new JTable(modeloPedidos);
        modeloPedidos.addColumn("ID Pedido");
        modeloPedidos.addColumn("ID Cliente");
        modeloPedidos.addColumn("Fecha");
        modeloPedidos.addColumn("Total");

        JScrollPane scrollPane = new JScrollPane(tblPedidos);
        scrollPane.setBounds(20, 320, 450, 150);

        btnGuardar = new JButton("Guardar");
        btnCerrar = new JButton("Cerrar");
        btnNuevo = new JButton("Nuevo");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        btnFiltrar = new JButton("Filtrar");
        btnCambiarVista = new JButton("Ver Detalle");
        btnAgregarProducto = new JButton("Agregar Producto");

        btnGuardar.setBounds(320, 30, 100, 30);
        btnCerrar.setBounds(320, 70, 100, 30);
        btnNuevo.setBounds(320, 110, 100, 30);
        btnEditar.setBounds(320, 150, 100, 30);
        btnEliminar.setBounds(320, 190, 100, 30);
        btnFiltrar.setBounds(320, 230, 100, 30);
        btnCambiarVista.setBounds(320, 270, 100, 30);
        btnAgregarProducto.setBounds(200, 270, 100, 30);

        btnGuardar.addActionListener(this);
        btnCerrar.addActionListener(this);
        btnNuevo.addActionListener(this);
        btnEditar.addActionListener(this);
        btnEliminar.addActionListener(this);
        btnFiltrar.addActionListener(this);
        btnCambiarVista.addActionListener(this);
        btnAgregarProducto.addActionListener(this);

        txtcantidad.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                actualizarSubtotal();
            }
        });

        txtIdPedido.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    buscarTotalPorIdPedido();
                }
            }
        });

        add(lblIdPedido); add(txtIdPedido);        
        add(lblFecha);add(dateChooser);        
        add(lblCantidad); add(txtcantidad);        
        add(lblClientes);  add(cmbClientes);      
        add(lblProducto);add(cmbProducto);     
        add(lblcostouni);add(txtcostouni);       
        add(lblsubtotal);add(txtsubtotal);
        add(lbltotal); add(txttotal);
        
        //BOTONES
        add(btnGuardar); add(btnCerrar);  add(btnNuevo);add(btnEditar); add(btnEliminar);add(btnFiltrar); add(btnCambiarVista); add(btnAgregarProducto); add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnCerrar) {
            dispose();
        } else if (e.getSource() == btnNuevo) {
            limpiarDatos();
        } else if (e.getSource() == btnFiltrar) {
            filtrarPedidosPorCliente();
        } else if (e.getSource() == btnGuardar) {
            guardarPedido();
        } else if (e.getSource() == btnEditar) {
            editarPedido();
        } else if (e.getSource() == btnEliminar) {
            eliminarPedido();
        } else if (e.getSource() == btnCambiarVista) {
            cambiarVista();
        }else if (e.getSource() == btnAgregarProducto){
            agregarProductoAlDetallePedido();
        }
    }

    private class ControladorClickPedido extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            int registro = tblPedidos.getSelectedRow();  // Selecciona la fila de la tabla

            // Deshabilitar el campo ID del pedido ya que es solo de lectura
            txtIdPedido.setEditable(false);

            // Recuperamos el ID del pedido seleccionado
            String idPedido = (String) tblPedidos.getValueAt(registro, 0);

            // Consulta SQL para obtener el pedido y sus detalles
            String cadSql = "CALL sp_obtener_pedido(?)";  // Llamada al procedimiento almacenado para obtener detalles del pedido

            try (Connection cnx = new conexionMYSQL().Conectar()) {

                java.sql.CallableStatement cstm = cnx.prepareCall(cadSql);
                cstm.setString(1, idPedido);

                ResultSet rs = cstm.executeQuery();  // Ejecutamos la consulta y obtenemos los resultados

                if (rs.next()) {
                    // Recuperamos los datos del pedido desde el ResultSet
                    String clienteId = rs.getString("id_cliente");
                    Date fecha = rs.getDate("fecha");
                    double costoUnitario = rs.getDouble("costo_unitario");
                    int cantidad = rs.getInt("cantidad");
                    double subtotal = rs.getDouble("subtotal");
                    double total = rs.getDouble("total");

                    // Rellenamos los campos con los datos obtenidos
                    txtIdPedido.setText(idPedido);
                    cmbClientes.setSelectedItem(clienteId);  // Asumiendo que el JComboBox contiene el ID del cliente
                    dateChooser.setDate(fecha);
                    txtcantidad.setText(String.valueOf(cantidad));
                    txtcostouni.setText(String.valueOf(costoUnitario));
                    txtsubtotal.setText(String.valueOf(subtotal));
                    txttotal.setText(String.valueOf(total));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();  // Manejo de excepciones
            }
        }
    }

    private void buscarTotalPorIdPedido() {
        String idPedido = txtIdPedido.getText();

        if (idPedido.isEmpty()) {
            txttotal.setText("");
            return;
        }

        String sql = "SELECT total FROM tb_pedido WHERE id_pedido = ?";

        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {

            pstm.setString(1, idPedido);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                float total = rs.getFloat("total");
                txttotal.setText(String.valueOf(total));
            } else {
                JOptionPane.showMessageDialog(null, "Pedido no encontrado.");
                txttotal.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar el total del pedido: " + e.getMessage());
        }
    }

    private void actualizarSubtotal() {
        try {
            float precioUnitario = Float.parseFloat(txtcostouni.getText());
            int cantidad = Integer.parseInt(txtcantidad.getText());
            float subtotal = precioUnitario * cantidad;
            txtsubtotal.setText(String.valueOf(subtotal));
        } catch (NumberFormatException e) {
            txtsubtotal.setText("");  // Limpia el subtotal si el campo de cantidad o precio unitario no es válido
        }
    }

    private void cambiarVista() {
        modeloPedidos.setRowCount(0); // Limpiar la tabla
        if (vistaDetalle) {
            modeloPedidos.setColumnCount(0);
            modeloPedidos.addColumn("ID Pedido");
            modeloPedidos.addColumn("ID Cliente");
            modeloPedidos.addColumn("Fecha");
            modeloPedidos.addColumn("Total");
            cargarPedidos();
            btnCambiarVista.setText("Ver Detalle");
        } else {
            cargarVistaDetalle();
            btnCambiarVista.setText("Ver Pedidos");
        }
        vistaDetalle = !vistaDetalle;
    }

    private void cargarVistaDetalle() {
        modeloPedidos.setRowCount(0);  // Limpiar la tabla
        modeloPedidos.setColumnCount(0);  // Limpiar las columnas

        modeloPedidos.addColumn("Codigo Pedido");
        modeloPedidos.addColumn("Nombre del Cliente");
        modeloPedidos.addColumn("Producto");
        modeloPedidos.addColumn("Precio Unitario");
        modeloPedidos.addColumn("Cantidad");
        modeloPedidos.addColumn("Precio Total");

        String sql = "CALL sp_listar_detalle();";  // Llamada al procedimiento almacenado

        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql); ResultSet rs = cstm.executeQuery()) {

            while (rs.next()) {
                modeloPedidos.addRow(new Object[]{
                    rs.getString("id_pedido"),
                    rs.getString("cliente"), // Aquí se muestra el nombre del cliente
                    rs.getString("producto"), // Aquí se muestra el nombre del producto
                    rs.getFloat("precio_unitario"),
                    rs.getInt("cantidad"),
                    rs.getFloat("precio_total") // Calculado como precio_unitario * cantidad
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar detalles de pedidos: " + e.getMessage());
        }
    }

    private void cargarClientes() {
        String sql = "SELECT id_cliente, nombre FROM tb_cliente";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {
            cmbClientes.removeAllItems();
            cmbClientes.addItem("Seleccione un cliente");

            while (rs.next()) {
                String cliente = rs.getString("id_cliente") + " - " + rs.getString("nombre");  // Formato "Código - Nombre"
                cmbClientes.addItem(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar clientes: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        String sql = "SELECT id_producto, producto FROM tb_producto";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            cmbProducto.removeAllItems();
            cmbProducto.addItem("Seleccione un producto");

            // Llenar el combo con productos
            while (rs.next()) {
                String producto = rs.getString("id_producto") + " - " + rs.getString("producto");
                cmbProducto.addItem(producto);
            }

            // Agregar el ActionListener al combo para actualizar el precio unitario
            cmbProducto.addActionListener(e -> {
                if (cmbProducto.getSelectedIndex() > 0) {  // Verifica que no sea la opción "Seleccione un producto"
                    String seleccionado = cmbProducto.getSelectedItem().toString();
                    String idProducto = seleccionado.split(" - ")[0];  // Obtener solo el ID del producto

                    // Consultar el precio unitario del producto seleccionado
                    actualizarPrecioUnitario(idProducto);
                } else {
                    txtcostouni.setText("");  // Limpia el campo si no hay un producto seleccionado
                }
            });

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos: " + e.getMessage());
        }
    }

// Método para consultar y actualizar el precio unitario
    private void actualizarPrecioUnitario(String idProducto) {
        String sql = "SELECT costo FROM tb_producto WHERE id_producto = ?";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {

            pstm.setString(1, idProducto);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                float precioUnitario = rs.getFloat("costo");
                txtcostouni.setText(String.valueOf(precioUnitario));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener precio unitario: " + e.getMessage());
        }
    }

    private void cargarPedidos() {
        String sql = "SELECT p.id_pedido, c.nombre AS cliente, p.fecha "
                + "FROM tb_pedido p "
                + "JOIN tb_cliente c ON p.id_cliente = c.id_cliente";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql); ResultSet rs = pstm.executeQuery()) {

            modeloPedidos.setRowCount(0); // Limpiar la tabla antes de cargar los datos

            while (rs.next()) {
                String idPedido = rs.getString("id_pedido");

                // Calcular el total sumando los detalles del pedido
                float total = calcularTotalPedido(idPedido);

                modeloPedidos.addRow(new Object[]{
                    idPedido,
                    rs.getString("cliente"),
                    rs.getDate("fecha"),
                    total // Total calculado de los detalles
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar pedidos: " + e.getMessage());
        }
    }

    private float calcularTotalPedido(String idPedido) {
        String sql = "SELECT SUM(dp.precio_unitario * dp.cantidad) AS total "
                + "FROM tb_detalle_pedido dp "
                + "WHERE dp.id_pedido = ?";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {
            pstm.setString(1, idPedido);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                return rs.getFloat("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al calcular el total del pedido: " + e.getMessage());
        }
        return 0;
    }

    private void guardarPedido() {
        String clienteSeleccionado = (String) cmbClientes.getSelectedItem();
        String[] clientePartes = clienteSeleccionado.split(" - ");
        String idCliente = clientePartes[0];  // Obtener solo el ID del cliente

        String productoSeleccionado = (String) cmbProducto.getSelectedItem();
        String[] productoPartes = productoSeleccionado.split(" - ");
        String idProducto = productoPartes[0];  // Obtener solo el ID del producto

        Date fecha = dateChooser.getDate();
        String cantidadStr = txtcantidad.getText();
        String precioUnitarioStr = txtcostouni.getText();  // Suponiendo que tienes un campo para el precio unitario

        int cantidad = Integer.parseInt(cantidadStr);
        float precioUnitario = Float.parseFloat(precioUnitarioStr);
        float total = cantidad * precioUnitario;

        // Llamar al procedimiento almacenado para guardar el pedido y su detalle
        String sql = "{CALL sp_guardar_pedido(?, ?, ?, ?, ?, ?)}";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {

            cstm.setString(1, idCliente);
            cstm.setDate(2, new java.sql.Date(fecha.getTime()));
            cstm.setFloat(3, total);
            cstm.setString(4, idProducto);
            cstm.setInt(5, cantidad);
            cstm.setFloat(6, precioUnitario);

            cstm.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido guardado con éxito");
            limpiarDatos();
            cargarPedidos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido: " + e.getMessage());
        }
    }
    
    private void agregarProductoAlDetallePedido() {
        // Obtener el ID del pedido y del producto desde los campos de la interfaz de usuario
        String idPedido = txtIdPedido.getText();
        String productoSeleccionado = (String) cmbProducto.getSelectedItem();
        String[] productoPartes = productoSeleccionado.split(" - ");
        String idProducto = productoPartes[0];  // Obtener solo el ID del producto

        // Obtener la cantidad y precio unitario
        String cantidadStr = txtcantidad.getText();
        String precioUnitarioStr = txtcostouni.getText();  // Suponiendo que tienes un campo para el precio unitario

        int cantidad = Integer.parseInt(cantidadStr);
        float precioUnitario = Float.parseFloat(precioUnitarioStr);

        // Llamar al procedimiento almacenado para agregar el producto al detalle del pedido
        String sql = "{CALL AgregarProductoDetallePedido(?, ?, ?, ?)}";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {

            cstm.setString(1, idPedido);  // ID del pedido
            cstm.setString(2, idProducto);  // ID del producto
            cstm.setInt(3, cantidad);  // Cantidad
            cstm.setFloat(4, precioUnitario);  // Precio unitario

            cstm.executeUpdate();  // Ejecutar la llamada al procedimiento almacenado

            JOptionPane.showMessageDialog(null, "Producto agregado al detalle del pedido con éxito");
            actualizarTotalPedido(idPedido);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al agregar el producto al detalle del pedido: " + e.getMessage());
        }
    }
    
    private void actualizarTotalPedido(String idPedido) {
        String sql = "SELECT SUM(dp.precio_unitario * dp.cantidad) AS total "
                + "FROM tb_detalle_pedido dp "
                + "WHERE dp.id_pedido = ?";
        try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {
            pstm.setString(1, idPedido);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                float total = rs.getFloat("total");

                // Actualizar el total en la base de datos
                String updateSql = "UPDATE tb_pedido SET total = ? WHERE id_pedido = ?";
                try (PreparedStatement updatePstm = (PreparedStatement) cnx.prepareStatement(updateSql)) {
                    updatePstm.setFloat(1, total);
                    updatePstm.setString(2, idPedido);
                    updatePstm.executeUpdate();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el total del pedido: " + e.getMessage());
        }
    }
    
    

    private void editarPedido() {
        // Obtener los datos del pedido principal
        String idPedido = txtIdPedido.getText();  // Obtener el id_pedido de la interfaz
        String clienteSeleccionado = (String) cmbClientes.getSelectedItem();
        String[] clientePartes = clienteSeleccionado.split(" - ");
        String idCliente = clientePartes[0];  // Obtener solo el ID del cliente

        Date fecha = dateChooser.getDate();  // Obtener la fecha seleccionada
        String total = txttotal.getText();  // Obtener el total actualizado

        // Verificar si hay un producto seleccionado en los detalles
        int filaDetalle = tblPedidos.getSelectedRow();
        if (filaDetalle == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto en el detalle del pedido para editar.");
            return;
        }

        // Obtener los datos del producto seleccionado en el detalle
        String productoSeleccionado = (String) cmbProducto.getSelectedItem();
        String[] productoPartes = productoSeleccionado.split(" - ");
        String idProducto = productoPartes[0];  // Obtener solo el ID del producto
        String cantidad = txtcantidad.getText();  // Cantidad modificada
        String costoUnitario = txtcostouni.getText();  // Costo unitario modificado

        // Verificar que la cantidad y el costo unitario son números válidos
        try {
            Integer.parseInt(cantidad);  // Validar que la cantidad es un número entero
            Float.parseFloat(costoUnitario);  // Validar que el costo unitario es un número flotante
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad o Costo Unitario no son válidos.");
            return;
        }

        // Llamar al procedimiento almacenado para actualizar el pedido y su detalle
        String sql = "{CALL sp_editar_pedido(?, ?, ?, ?, ?, ?, ?)}";
        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {
            cstm.setString(1, idPedido);  // ID del pedido
            cstm.setString(2, idCliente);  // ID del cliente
            cstm.setDate(3, new java.sql.Date(fecha.getTime()));  // Fecha del pedido
            cstm.setFloat(4, Float.parseFloat(total));  // Total del pedido
            cstm.setString(5, idProducto);  // ID del producto
            cstm.setInt(6, Integer.parseInt(cantidad));  // Cantidad del producto
            cstm.setFloat(7, Float.parseFloat(costoUnitario));  // Costo unitario del producto

            cstm.executeUpdate();  // Ejecutar el procedimiento almacenado

            JOptionPane.showMessageDialog(null, "Pedido y detalle actualizado con éxito");

            // Recargar los datos para reflejar los cambios
            limpiarDatos();
            cargarPedidos();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar el pedido: " + e.getMessage());
        }
    }

    private void eliminarPedido() {
        int fila = tblPedidos.getSelectedRow(); // Obtener la fila seleccionada
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.");
            return;
        }

        String idPedido = (String) modeloPedidos.getValueAt(fila, 0); // Obtener el id_pedido de la fila seleccionada

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este pedido y sus detalles?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "{CALL sp_eliminar_pedido_y_detalles(?)}"; // Llamada al procedimiento almacenado

        try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {
            cstm.setString(1, idPedido); // Establecer el parámetro id_pedido en el procedimiento
            cstm.executeUpdate(); // Ejecutar el procedimiento

            JOptionPane.showMessageDialog(null, "Pedido y sus detalles eliminados con éxito");

            limpiarDatos();
            cargarPedidos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el pedido y sus detalles: " + e.getMessage());
        }
    }

    private void limpiarDatos() {
        txtIdPedido.setText("");
        txtcantidad.setText("");
        txtcostouni.setText("");
        txtsubtotal.setText("");
        txttotal.setText("");
        dateChooser.setDate(null);
        cmbClientes.setSelectedIndex(0);
        cmbProducto.setSelectedIndex(0);
    }

    private void filtrarPedidosPorCliente() {
        String cliente = (String) cmbClientes.getSelectedItem();
        if (cliente != null && !cliente.equals("Seleccione un cliente")) {
            String sql = "SELECT * FROM tb_pedido WHERE id_cliente = (SELECT id_cliente FROM tb_cliente WHERE nombre = ?)";
            try (Connection cnx = new conexionMYSQL().Conectar(); PreparedStatement pstm = (PreparedStatement) cnx.prepareStatement(sql)) {
                pstm.setString(1, cliente);
                ResultSet rs = pstm.executeQuery();
                modeloPedidos.setRowCount(0);

                while (rs.next()) {
                    modeloPedidos.addRow(new Object[]{
                        rs.getString("id_pedido"),
                        rs.getString("id_cliente"),
                        rs.getDate("fecha"),
                        rs.getFloat("total")
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al filtrar pedidos: " + e.getMessage());
            }
        } else {
            cargarPedidos();
        }
    }

    private void agregarEventoFilaSeleccionada() {
        tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (tblPedidos.getSelectedRow() != -1) {
                    cargarDatosSeleccionados();
                }
            }
        });
    }

    private void cargarDatosSeleccionados() {
        int fila = tblPedidos.getSelectedRow();
        if (fila != -1) {
            // Obtener el ID del pedido desde la tabla
            String idPedido = (String) modeloPedidos.getValueAt(fila, 0);

            // Llamar al procedimiento almacenado para obtener los detalles del pedido
            String sql = "{CALL sp_obtener_pedido(?)}"; // Llamada al procedimiento almacenado
            try (Connection cnx = new conexionMYSQL().Conectar(); CallableStatement cstm = (CallableStatement) cnx.prepareCall(sql)) {

                // Establecer el parámetro para el procedimiento almacenado
                cstm.setString(1, idPedido);

                // Ejecutar la consulta y obtener el resultado
                ResultSet rs = cstm.executeQuery();

                float total = 0; // Inicializar el total

                while (rs.next()) {
                    // Cargar el ID de Pedido
                    txtIdPedido.setText(rs.getString("id_pedido"));

                    // Cargar el Cliente
                    String cliente = rs.getString("cliente");
                    cmbClientes.setSelectedItem(cliente);

                    // Cargar la Fecha
                    Date fecha = rs.getDate("fecha");
                    dateChooser.setDate(fecha);

                    // Cargar la Cantidad
                    int cantidad = rs.getInt("cantidad");
                    txtcantidad.setText(String.valueOf(cantidad));

                    // Cargar el Producto
                    String producto = rs.getString("producto");
                    cmbProducto.setSelectedItem(producto);

                    // Cargar el Precio Unitario
                    float precioUnitario = rs.getFloat("precio_unitario");
                    txtcostouni.setText(String.valueOf(precioUnitario));

                    // Calcular y cargar el subtotal
                    float subtotal = precioUnitario * cantidad;
                    txtsubtotal.setText(String.valueOf(subtotal));

                    // Sumar al total
                    total += subtotal;
                }

                // Cargar el Total
                txttotal.setText(String.valueOf(total));

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar los datos del pedido: " + e.getMessage());
            }
        }
    }

    private void agregarEventoEliminarFila() {
        tblPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tblPedidos.getSelectedRow() != -1) {
                    confirmarEliminar();
                }
            }
        });
    }

    private void confirmarEliminar() {
        int fila = tblPedidos.getSelectedRow();
        if (fila >= 0) {
            int confirmacion = JOptionPane.showConfirmDialog(this, "¿Está seguro de eliminar este pedido?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                eliminarPedido();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un pedido para eliminar.");
        }
    }
}
