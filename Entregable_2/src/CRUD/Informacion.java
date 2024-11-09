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

public class Informacion extends JInternalFrame implements ActionListener {

    private JLabel lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    private JButton btn_cerrar;

    public Informacion() {
        super();
        IniciarFormulario();
        IniciarControles();
    }

    public void IniciarFormulario() {
        this.setTitle("Información - G3");
        this.setSize(370, 280);

        // this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setResizable(false);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void IniciarControles() {
        lbl1 = new JLabel();
        lbl1.setText("Integrantes del Grupo 3:");
        lbl1.setBounds(110, 15, 200, 25); 

        lbl2 = new JLabel();
        lbl2.setText("- Rojas Leonardo");
        lbl2.setBounds(120, 50, 200, 25);

        lbl3 = new JLabel();
        lbl3.setText("- Jarama Leonardo");
        lbl3.setBounds(120, 80, 200, 25);

        lbl4 = new JLabel();
        lbl4.setText("- Canchari Victor");
        lbl4.setBounds(120, 110, 200, 25);

        lbl5 = new JLabel();
        lbl5.setText("- Roca Mauricio");
        lbl5.setBounds(120, 140, 200, 25);

        lbl6 = new JLabel();
        lbl6.setText("- Chuquipiondo Elvis");
        lbl6.setBounds(120, 170, 200, 25);

        btn_cerrar = new JButton();
        btn_cerrar.setText("CERRAR");
        btn_cerrar.setBounds(120, 210, 120, 30);
        btn_cerrar.addActionListener(this);

        this.add(lbl1);
        this.add(lbl2);
        this.add(lbl3);
        this.add(lbl4);
        this.add(lbl5);
        this.add(lbl6);
        this.add(btn_cerrar);
    }

    public static void main(String[] args) {
        Informacion informacion = new Informacion();

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
