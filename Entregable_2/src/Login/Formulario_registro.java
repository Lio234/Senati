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
        JPanel panel = new JPanel();
        panel.setLayout(null); // Layout nulo para posicionar los componentes manualmente

        // Configurar y posicionar campos y etiquetas
        JLabel lblUsername = new JLabel("Nombre de Usuario:");
        lblUsername.setBounds(50, 30, 120, 25);
        panel.add(lblUsername);
        
        usernameField = new JTextField();
        usernameField.setBounds(180, 30, 150, 25);
        panel.add(usernameField);

        JLabel lblEmail = new JLabel("Correo Electrónico:");
        lblEmail.setBounds(50, 70, 120, 25);
        panel.add(lblEmail);

        emailField = new JTextField();
        emailField.setBounds(180, 70, 150, 25);
        panel.add(emailField);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 110, 120, 25);
        panel.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 110, 150, 25);
        panel.add(passwordField);

        // Configurar botón de registro
        btn_registrar = new JButton("Registrar");
        btn_registrar.setBounds(70, 160, 100, 30);
        btn_registrar.addActionListener(new RegisterActionListener());
        panel.add(btn_registrar);

        // Configurar botón para ir al login
        btn_iniciar_secion = new JButton("Login");
        btn_iniciar_secion.setBounds(220, 160, 100, 30);
        btn_iniciar_secion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirLogin(); // Abrir la ventana de login al presionar el botón
            }
        });
        panel.add(btn_iniciar_secion);

        // Configurar etiqueta de estado
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setBounds(50, 210, 300, 25);
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
