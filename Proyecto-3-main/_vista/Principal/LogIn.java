package Principal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Req.__Autor;
import Req.__Comprador;
import Req.__Empleado;
import Req.__Propietario;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Galeria;
import modelo.Usuario;

public class LogIn extends JFrame implements ActionListener {

    private String user;
    private String password;

    private static final JLabel usuario = new JLabel("User: ");
    private static final JLabel contrasenia = new JLabel("Password: ");
    private JLabel msjError = new JLabel(" ");
    private static final JTextField txtUsuario = new JTextField("admin", 20);
    private static final JPasswordField txtContrasenia = new JPasswordField("admin", 20);
    private static final JButton ingresar = new JButton("Ingresar");
    private static final JButton salir = new JButton("Salir");

    private static final String INGRESAR = "INGRESAR";
    private static final String SALIR = "SALIR";

    private Central central;

    public LogIn(Central central) {
        super();

        this.central = central;

        setTitle("Log In");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(500, 400); 

        msjError.setForeground(Color.RED);

        ingresar.setActionCommand(INGRESAR);
        ingresar.addActionListener(this);

        salir.setActionCommand(SALIR);
        salir.addActionListener(this);

        
        Font serifFont = new Font("Serif", Font.BOLD, 16);
        Font sansSerifFont = new Font("SansSerif", Font.BOLD, 16);
        Font monospacedFont = new Font("Monospaced", Font.BOLD, 16);
        Font dialogFont = new Font("Dialog", Font.BOLD, 16);
        Font dialogInputFont = new Font("DialogInput", Font.BOLD, 16);

        // Aplicar diferentes fuentes a los componentes
        usuario.setFont(monospacedFont);
        contrasenia.setFont(monospacedFont);
        txtUsuario.setFont(monospacedFont);
        txtContrasenia.setFont(monospacedFont);
        msjError.setFont(monospacedFont);

        JPanel norte = new JPanel(new GridBagLayout());
        JPanel centro = new JPanel(new GridBagLayout());
        JPanel sur = new JPanel(new GridBagLayout());

        norte.setBorder(new EmptyBorder(20, 20, 20, 20));
        centro.setBorder(new EmptyBorder(20, 20, 20, 20));
        sur.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        norte.add(usuario, gbc);

        gbc.gridx = 1;
        norte.add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        norte.add(contrasenia, gbc);

        gbc.gridx = 1;
        norte.add(txtContrasenia, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centro.add(msjError, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        sur.add(ingresar, gbc);

        gbc.gridx = 2;
        sur.add(salir, gbc);

        add(norte, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        setVisible(true);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    private void verificarLogIn(String user, String password) {
        Galeria galeria = central.getGaleria();
        Usuario usuario;
        if (galeria.getUsuarios().containsKey(user)) {
            usuario = galeria.getUsuarios().get(user);
            if (usuario.getPassword().equals(password)) {
                central.setUsuario(usuario);
                obtenerMenu();
                dispose();

            } else {
                msjError.setText("Contrasenia incorrecta");
            }
        } else {
            msjError.setText("El usuario no existe");
        }
//      return null;
    }

    private void obtenerMenu() {
        Usuario usuario = central.getUsuario();
        if (usuario instanceof Empleado) {
            central.set__empleado(new __Empleado(central));
            new Menu(Menu.EMPLEADO, null, central);
        } else {
            Cliente cliente = (Cliente) usuario;
            if (cliente.getTipo().equals("Autor")) {
                central.set__autor(new __Autor(central));
                new Menu(Menu.AUTOR, null, central);
            } else if (cliente.getTipo().equals("Comprador")) {
                central.set__comprador(new __Comprador(central));
                new Menu(Menu.COMPRADOR, null, central);
            } else {
                central.set__propietario(new __Propietario(central));
                new Menu(Menu.PROPIETARIO, null, central);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String rt = e.getActionCommand();
        if (rt.equals(INGRESAR)) {
            String user = txtUsuario.getText();
            String password = new String(txtContrasenia.getPassword());
            verificarLogIn(user, password);

        }
        if (rt.equals(SALIR)) {
            System.exit(0);
        }
    }
}


