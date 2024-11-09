package CRUD;

import java.awt.event.ActionListener;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
// import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class cliente extends JInternalFrame implements ActionListener {

    private JLabel lbl1;
    private JButton btn_cerrar;

    public cliente() {
        super();
        IniciarFormulario();
        IniciarControles();
    }

    public void IniciarFormulario() {
        this.setTitle("Cliente");
        this.setSize(370, 280);

        // this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void IniciarControles() {
        lbl1 = new JLabel();
        lbl1.setText("Cliente");
        lbl1.setBounds(110, 15, 200, 25);


        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setBounds(120, 210, 120, 30);
        btn_cerrar.addActionListener(this);

        this.add(lbl1);
        
        this.add(btn_cerrar);
    }

    public static void main(String[] args) {
        cliente informacion = new cliente();

        informacion.setVisible(true);
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

}
