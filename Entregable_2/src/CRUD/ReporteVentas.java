package CRUD;

import Conexion.conexionMYSQL;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
// import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class ReporteVentas extends JInternalFrame implements ActionListener {

    private JLabel lbl_titulo, lbl_cliente_mayor_compra;
    private JButton btn_cerrar;
    private JTable tb_reporte;
    private JScrollPane scr_reporte;

    private final conexionMYSQL cn = new conexionMYSQL();

    public ReporteVentas() {
        super();
        IniciarFormulario();
        IniciarControles(); // Asegúrate de inicializar los controles primero
        MostrarDatos();      // Ahora se pueden cargar los datos después de inicializar los controles
        MostrarClienteMayorCompra(); // Llamada para mostrar el cliente que más compró
    }

    public void IniciarFormulario() {
        this.setTitle("Reporte de Ventas");
        this.setSize(600, 430);
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void IniciarControles() {
        lbl_titulo = new JLabel("Reporte de Ventas");
        lbl_titulo.setFont(new Font("Calibri", Font.BOLD, 24));
        lbl_titulo.setForeground(Color.BLUE);
        lbl_titulo.setBounds(230, 15, 300, 25);

        // Inicializa la tabla antes de agregarla al JScrollPane
        tb_reporte = new JTable();
        tb_reporte.setRowHeight(20);
        tb_reporte.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Configura el JScrollPane para la tabla
        scr_reporte = new JScrollPane(tb_reporte);
        scr_reporte.setBounds(10, 80, 570, 150);

        // JLabel para mostrar el cliente con mayor compra
        lbl_cliente_mayor_compra = new JLabel("Cliente que más compró: ");
        lbl_cliente_mayor_compra.setFont(new Font("Calibri", Font.PLAIN, 16));
        lbl_cliente_mayor_compra.setBounds(10, 250, 500, 25);

        // Botón cerrar
        btn_cerrar = new JButton("CERRAR");
        btn_cerrar.setBounds(230, 340, 120, 30);
        btn_cerrar.addActionListener(this);

        this.add(lbl_titulo);
        this.add(scr_reporte);
        this.add(lbl_cliente_mayor_compra);
        this.add(btn_cerrar);
    }

    private void MostrarDatos() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"ID Cliente", "Cliente", "N* Compras", "S/. Compras", "Distrito", "Provincia", "Departamento"});
        modelo.setRowCount(0); // Limpiar la tabla antes de llenarla

        try (Connection cnx = cn.Conectar(); CallableStatement cstmt = cnx.prepareCall("{CALL obtener_resumen_compras()}")) {

            ResultSet rs = cstmt.executeQuery();
            while (rs.next()) {
                modelo.addRow(new Object[] {
                    rs.getString("id_cliente"),
                    rs.getString("cliente"),
                    rs.getString("numero_compras"),
                    rs.getString("total_compras"),
                    rs.getString("distrito"),
                    rs.getString("provincia"),
                    rs.getString("departamento")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + e.getMessage());
        }

        tb_reporte.setModel(modelo);  // Ahora podemos asignar el modelo a la tabla
    }

    private void MostrarClienteMayorCompra() {
        String clienteMayorCompra = "";
        double totalCompraMaxima = 0;

        // Realizamos una consulta para obtener el cliente con mayor cantidad de compras
        String query = "call obtener_cliente_mayor_compra()"; // Tomamos solo el primer cliente con mayor compra

        try (Connection cnx = cn.Conectar(); Statement stmt = cnx.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                clienteMayorCompra = rs.getString("cliente");
                totalCompraMaxima = rs.getDouble("total_compras");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener el cliente con más compras: " + e.getMessage());
        }

        // Actualizamos el JLabel para mostrar al cliente que más compró
        lbl_cliente_mayor_compra.setText("Cliente que más compró: " + clienteMayorCompra + " con un total de S/. " + totalCompraMaxima);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_cerrar) {
            int op = JOptionPane.showConfirmDialog(null,
                    "¿Seguro de cerrar?",
                    "Información",
                    JOptionPane.YES_NO_OPTION);

            if (op == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        ReporteVentas informacion = new ReporteVentas();
        informacion.setVisible(true);
    }
}
