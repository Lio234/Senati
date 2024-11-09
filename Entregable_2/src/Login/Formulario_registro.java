package Login;

import Conexion.conexionMYSQL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Formulario_registro extends JFrame {
    private JTextField usernameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private conexionMYSQL conexion;

    public Formulario_registro() {
        setTitle("Registro de Usuario");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        conexion = new conexionMYSQL();

        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        
        
        panel.add(new JLabel("Nombre de Usuario:"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Correo Electrónico:"));
        emailField = new JTextField();
        panel.add(emailField);
        
        panel.add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        
        JButton registerButton = new JButton("Registrar");
        registerButton.addActionListener(new RegisterActionListener());
        panel.add(registerButton);

        
        statusLabel = new JLabel("", SwingConstants.CENTER);
        panel.add(statusLabel);

        add(panel);
    }

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Formulario_registro registerForm = new Formulario_registro();
            registerForm.setVisible(true);
        });
    }
}
