package Login;

import Conexion.conexionMYSQL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Formulario_registro extends JFrame {
    // Atributos de los componentes
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private JButton btn_registrar, btn_iniciar_secion;
    private conexionMYSQL conexion;

    public Formulario_registro() {
        setTitle("Registro de Usuario");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        conexion = new conexionMYSQL();
        
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        // Configurar campos
        panel.add(new JLabel("Nombre de Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Correo Electrónico:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        // Configurar botón de registro
        btn_registrar = new JButton("Registrar");
        btn_registrar.addActionListener(new RegisterActionListener());
        panel.add(btn_registrar);
        
        // Configurar botón para ir al login
        btn_iniciar_secion = new JButton("Login");
        btn_iniciar_secion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirLogin(); // Abrir la ventana de login al presionar el botón
            }
        });
        panel.add(btn_iniciar_secion);

        // Configurar etiqueta de estado
        statusLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(statusLabel);

        this.add(panel);
    }

    // Clase interna para manejar la acción de registro
    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            registerUser(username, email, password);
        }
    }

    private void registerUser(String username, String email, String password) {
        try (Connection connection = conexion.Conectar()) {
            // Llamada al procedimiento almacenado
            String query = "{ CALL register_user(?, ?, ?) }";
            CallableStatement stmt = connection.prepareCall(query);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password);

            stmt.executeUpdate();
            statusLabel.setText("Registro exitoso");

        } catch (SQLException ex) {
            if (ex.getSQLState().equals("45000")) {
                statusLabel.setText("Error: " + ex.getMessage());
            } else {
                statusLabel.setText("Error en la conexión: " + ex.getMessage());
            }
            ex.printStackTrace();
        }
    }

    // Método para abrir la ventana de login
    private void abrirLogin() {
        this.setVisible(false); // Oculta la ventana actual
        Login loginForm = new Login(); // Crea una instancia de la ventana de Login
        loginForm.setVisible(true); // Muestra la ventana de Login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Formulario_registro registerForm = new Formulario_registro();
            registerForm.setVisible(true);
        });
    }
}
