package Login;

import CRUD.prueba;
import Conexion.conexionMYSQL;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame {
    private JTextField txt_Email;  // Cambiamos el nombre para mayor claridad
    private JPasswordField txt_Password;
    private JButton btn_Login, btn_Registrar;
    
    public Login() {
        super();
        configurarVentana();
        iniciarComponentes();
    }
    
    private void configurarVentana() {
        this.setTitle("Autenticación de Usuario");
        this.setSize(400, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void iniciarComponentes() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblEmail = new JLabel("Correo:");
        lblEmail.setBounds(50, 30, 80, 25);
        panel.add(lblEmail);

        txt_Email = new JTextField();
        txt_Email.setBounds(150, 30, 150, 25);
        panel.add(txt_Email);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 70, 80, 25);
        panel.add(lblPassword);

        txt_Password = new JPasswordField();
        txt_Password.setBounds(150, 70, 150, 25);
        panel.add(txt_Password);

        btn_Login = new JButton("Iniciar Sesión");
        btn_Login.setBounds(150, 110, 100, 20);
        panel.add(btn_Login);
        
        btn_Registrar = new JButton("Registrar usuario");
        btn_Registrar.setBounds(270, 110, 100, 20);
        panel.add(btn_Registrar);

        // Listener para el botón de iniciar sesión
        btn_Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        // Listener para el botón de registrar usuario
        btn_Registrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirFormularioRegistro();
            }
        });

        this.add(panel);
    }

    private void autenticarUsuario() {
        String email = txt_Email.getText();
        String password = new String(txt_Password.getPassword());
        conexionMYSQL cn = new conexionMYSQL();

        Connection cnx = null;
        CallableStatement stmt = null;
        try {
            cnx = cn.Conectar();
            stmt = cnx.prepareCall("{call sp_verificar_usuario(?, ?, ?)}");
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.registerOutParameter(3, java.sql.Types.INTEGER);

            stmt.execute();

            int resultado = stmt.getInt(3);
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                abrirDashboard(); // Llamar al método para abrir la ventana de Dashboard
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (cnx != null) cnx.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirDashboard() {
        this.setVisible(false);
        prueba pr = new prueba ();
        pr.setVisible(true);
        JOptionPane.showMessageDialog(this, "Bienvenido al Dashboard");
    }
    
    private void abrirFormularioRegistro() {
        this.setVisible(false);
        Formulario_registro formularioRegistro = new Formulario_registro();
        formularioRegistro.setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }
}
