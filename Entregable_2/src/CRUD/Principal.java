package CRUD;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

public class Principal extends JFrame implements ActionListener {

    private JDesktopPane dsk_principal;
    private JMenuBar mb_principal;
    private JMenu mn_clientes, mn_productos, mn_pedido, mn_lugares, mn_reportes, mn_opciones;
    private JMenuItem mi_clie,mi_prod, mi_marc, mi_cat, mi_dep, mi_dist, mi_prov,mi_pedi,mi_report, mi_info, mi_salir;
    private JSeparator sep_opc;

    private Principal() {
        super();
        IniciarFormulario();
        IniciarControles();
    }

    public void IniciarFormulario() {
        this.setTitle("Aplicación Principal");
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void IniciarControles() {
        dsk_principal = new JDesktopPane();

        mb_principal = new JMenuBar();

        mn_clientes = new JMenu("Clientes");
        mi_clie = new JMenuItem("Clientes");
        mi_clie.addActionListener(this);

        mn_productos = new JMenu("Productos");
        mi_prod = new JMenuItem("Productos");
        mi_prod.addActionListener(this);
        mi_marc = new JMenuItem("Marca");
        mi_marc.addActionListener(this);
        mi_cat = new JMenuItem("Categoría");
        mi_cat.addActionListener(this);

        mn_lugares = new JMenu("Lugares");
        mi_dep = new JMenuItem("Departamentos");
        mi_dep.addActionListener(this);
        mi_prov = new JMenuItem("Provincia");
        mi_prov.addActionListener(this);
        mi_dist = new JMenuItem("Distritos");
        mi_dist.addActionListener(this);

        mn_pedido = new JMenu("Pedido");
        mi_pedi = new JMenuItem("Pedido");
        mi_pedi.addActionListener(this);
       

        mn_reportes = new JMenu("Reportes");
        mi_report = new JMenuItem("Reportes");
        mi_report.addActionListener(this);
        

        mn_opciones = new JMenu("Opciones");

        mi_info = new JMenuItem("Información");
        mi_info.addActionListener(this);

        mi_salir = new JMenuItem("Salir");
        mi_salir.addActionListener(this);

        sep_opc = new JSeparator();

        mn_clientes.add(mi_clie);
        
        mn_productos.add(mi_prod);
        mn_productos.add(mi_marc);
        mn_productos.add(mi_cat);
        
        mn_lugares.add(mi_dep);
        mn_lugares.add(mi_prov);
        mn_lugares.add(mi_dist);
        
        mn_pedido.add(mi_pedi);
        
        mn_reportes.add(mi_report);
        
        mn_opciones.add(mi_info);
        mn_opciones.add(sep_opc);
        mn_opciones.add(mi_salir);

        mb_principal.add(mn_clientes);
        mb_principal.add(mn_productos);
        mb_principal.add(mn_lugares);
        mb_principal.add(mn_pedido);
        mb_principal.add(mn_reportes);
        mb_principal.add(mn_opciones);

        this.setJMenuBar(mb_principal);

        this.add(dsk_principal);
    }

    public void AbrirFormulario(JInternalFrame frm) {

        JInternalFrame[] arr_iframe = dsk_principal.getAllFrames();
        boolean abierto = false;

        for (JInternalFrame iframe : arr_iframe) {
            if (frm.getClass().isInstance(iframe)) {
                JOptionPane.showMessageDialog(null, "Formulario ya abierto");
                abierto = true;
                break;
            }
        }
        if (!abierto) {
            dsk_principal.add(frm);
            frm.setLocation((dsk_principal.getWidth() - frm.getWidth()) / 2, (dsk_principal.getHeight() - frm.getHeight()) / 2);
            frm.show();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mi_clie) {
            cliente cliente = new cliente();
            AbrirFormulario(cliente);
        } else if (e.getSource() == mi_prod) {
            Producto producto = new Producto();
            AbrirFormulario(producto);
        } else if (e.getSource() == mi_cat) {
            CategoriaCRUD categoria = new CategoriaCRUD();
            AbrirFormulario(categoria);
        } else if (e.getSource() == mi_marc) {
            Marca marca = new Marca();
            AbrirFormulario(marca);
        }  else if (e.getSource() == mi_dep) {
            DepartamentoCRUD departamento = new DepartamentoCRUD();
            AbrirFormulario(departamento);
        } else if (e.getSource() == mi_prov) {
            ProvinciaCRUD provincia = new ProvinciaCRUD();
            AbrirFormulario(provincia);
        } else if (e.getSource() == mi_dist) {
            DistritoCRUD distrito = new DistritoCRUD();
            AbrirFormulario(distrito);
        }else if (e.getSource() == mi_pedi) {
            Pedido pedido = new Pedido();
            AbrirFormulario(pedido);
        }else if (e.getSource() == mi_report) {
            Reporte reporte = new Reporte();
            AbrirFormulario(reporte);
        }else if (e.getSource() == mi_info) {
            Informacion informacion = new Informacion();
            AbrirFormulario(informacion);
        } else if (e.getSource() == mi_salir) {
            System.exit(0);
        }
     


    }

    public static void main(String[] args) {
        Principal principal = new Principal();
        principal.setVisible(true);
    }

}
