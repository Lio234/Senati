package Login;


import CRUD.prueba;
import Conexion.conexionMYSQL;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JTextField txt_Username;
    private JPasswordField txt_Password;
    private JButton btn_Login;
    
    
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

        JLabel lblUsername = new JLabel("Usuario:");
        lblUsername.setBounds(50, 30, 80, 25);
        panel.add(lblUsername);

        txt_Username = new JTextField();
        txt_Username.setBounds(150, 30, 150, 25);
        panel.add(txt_Username);

        JLabel lblPassword = new JLabel("Contraseña:");
        lblPassword.setBounds(50, 70, 80, 25);
        panel.add(lblPassword);

        txt_Password = new JPasswordField();
        txt_Password.setBounds(150, 70, 150, 25);
        panel.add(txt_Password);

        btn_Login = new JButton("Iniciar Sesión");
        btn_Login.setBounds(150, 110, 150, 25);
        panel.add(btn_Login);

        btn_Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        this.add(panel);
    }

    private void autenticarUsuario() {
        String username = txt_Username.getText();
        String password = new String(txt_Password.getPassword());
        conexionMYSQL cn = new conexionMYSQL();

        Connection cnx = null;
        CallableStatement stmt = null;
        try {
            cnx = cn.Conectar();
            stmt = cnx.prepareCall("{call sp_verificar_usuario(?, ?, ?)}");
            stmt.setString(1, username);
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
        prueba marca = new prueba();
        marca.setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login();
        login.setVisible(true);
    }
}
