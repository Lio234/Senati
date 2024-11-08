package CRUD;

import javax.swing.JFrame;

public class prueba extends JFrame {
    public prueba() {
        super("prueba");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        prueba marca = new prueba();
        marca.setVisible(true);
    }
}
