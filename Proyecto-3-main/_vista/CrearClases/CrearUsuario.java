package CrearClases;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Principal.Central;
import excepciones.OperacionIlegal;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Usuario;

public class CrearUsuario extends JFrame implements ActionListener{

	private static final JLabel nombre = new JLabel("Nombre");
	private static final JLabel user = new JLabel("User");
	private static final JLabel password = new JLabel("Password");
	private static final JLabel telefono = new JLabel("Telefono");
	private static final JLabel correo = new JLabel("Correo");
	
	private JLabel msjError = new JLabel(" ");
	
	private JTextField txtNombre = new JTextField();;
	private JTextField txtUser = new JTextField();
	private JTextField txtPassword = new JTextField();
	private JTextField txtTelefono = new JTextField();
	private JTextField txtCorreo = new JTextField();
	
	private static final JButton crearCliente = new JButton("Crear cliente");
	public static final String CLIENTE = "CLIENTE";
	
	private static final JButton crearEmpleado = new JButton("Crear empleado");
	public static final String EMPLEADO = "EMPLEADO";
	
	private static final JButton volver = new JButton("Volver");
	public static final String VOLVER = "VOLVER";
	
	private static final JButton crearClienteAutor = new JButton("Crear autor");
	public static final String AUTOR = "AUTOR";
	
	private static final JButton crearClienteComprador = new JButton("Crear comprador");
	public static final String COMPRADOR = "COMPRADOR";
	
	private static final JButton crearClientePropietario = new JButton("Crear propietario");
	public static final String PROPIETARIO = "PROPIETARIO";
	
	private static final JButton crearEmpleadoAdministrador = new JButton("Crear administrador");
	public static final String ADMIN = "ADMIN";
	
	private static final JButton crearEmpleadoOperario = new JButton("Crear operario");
	public static final String OPERARIO = "OPERARIO";
	
	private static final JButton crearEmpleadoCajero = new JButton("Crear cajero");
	public static final String CAJERO = "CAJERO";
	
	JPanel norte = new JPanel();
	JPanel centro = new JPanel();
	JPanel sur = new JPanel();
	
	Central central;
	Usuario nuevoUsuario;
	
	public CrearUsuario(Central central) {
		super();
		
		crearCliente.setActionCommand(CLIENTE);
		crearCliente.addActionListener(this);
		
		crearEmpleado.setActionCommand(EMPLEADO);
		crearEmpleado.addActionListener(this);
		
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		crearClienteAutor.setActionCommand(AUTOR);
		crearClienteAutor.addActionListener(this);
		
		crearClienteComprador.setActionCommand(COMPRADOR);
		crearClienteComprador.addActionListener(this);
		
		crearClientePropietario.setActionCommand(PROPIETARIO);
		crearClientePropietario.addActionListener(this);
		
		crearEmpleadoAdministrador.setActionCommand(ADMIN);
		crearEmpleadoAdministrador.addActionListener(this);
		
		crearEmpleadoOperario.setActionCommand(OPERARIO);
		crearEmpleadoOperario.addActionListener(this);
		
		crearEmpleadoCajero.setActionCommand(CAJERO);
		crearEmpleadoCajero.addActionListener(this);
		
		this.central = central;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		
		JPanel surNorte = new JPanel();
		JPanel surCentro = new JPanel();
		JPanel surSur = new JPanel();
		
		sur.setLayout(new BorderLayout());
		
		norte.setLayout(new GridLayout(5,2));
		norte.add(nombre);
		norte.add(txtNombre);
		norte.add(user);
		norte.add(txtUser);
		norte.add(password);
		norte.add(txtPassword);
		norte.add(telefono);
		norte.add(txtTelefono);
		norte.add(correo);
		norte.add(txtCorreo);
		
		surNorte.add(msjError);
		
		surCentro.setLayout(new GridLayout(1,5));
		surCentro.add(new JLabel(""));
		surCentro.add(crearCliente);
		surCentro.add(new JLabel(""));
		surCentro.add(crearEmpleado);
		surCentro.add(new JLabel(""));
		
		surSur.add(volver);
		
		sur.add(surNorte, BorderLayout.NORTH);
		sur.add(surCentro, BorderLayout.CENTER);
		sur.add(surSur, BorderLayout.SOUTH);
		
		add(norte, BorderLayout.NORTH);
		add(centro, BorderLayout.CENTER);
		add(sur, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	public String confirma() {
		if(!verifica(txtNombre)) {
			return "nombre";
		}
		if(!verifica(txtUser)) {
			return "user";
		}
		if(!verifica(txtPassword)) {
			return "password";
		}
		if(!verifica(txtTelefono) || !txtTelefono.getText().matches("-?\\d+(\\.\\d+)?")) {
			return "telefono";
		}
		if(!verifica(txtCorreo)) {
			return "correo";
		}
		return null;
	}
	
	public boolean verifica(JTextField campo) {
	    if (campo.getText().trim().isEmpty()) {
	        return false;
	    } else {
	        return true;
	    }
	}
	
	public void msjError(String tipo) {
		msjError.setForeground(Color.RED);
		msjError.setText("Error en el apartado: " + tipo);
	}
	
	public void cambioCliente() {
		msjError.setText(" ");
		
		JPanel sur0 = new JPanel();
		sur0.setLayout(new BorderLayout());
		
		JPanel surNorte = new JPanel();
		JPanel surCentro = new JPanel();
		JPanel surSur = new JPanel();
		
		surNorte.add(msjError);
	
		surCentro.add(new JLabel(""));
		surCentro.add(crearClienteAutor);
		surCentro.add(crearClienteComprador);
		surCentro.add(crearClientePropietario);
		surCentro.add(new JLabel(""));
		
		surSur.add(volver);
		
		sur0.add(surNorte, BorderLayout.NORTH);
		sur0.add(surCentro, BorderLayout.CENTER);
		sur0.add(surSur, BorderLayout.SOUTH);
		
		add(sur0, BorderLayout.SOUTH);
		remove(sur);
	}
	
	public void cambioEmpleado() {
		msjError.setText(" ");
		
		JPanel sur0 = new JPanel();
		sur0.setLayout(new BorderLayout());
		
		JPanel surNorte = new JPanel();
		JPanel surCentro = new JPanel();
		JPanel surSur = new JPanel();
		
		surNorte.add(msjError);
	
		surCentro.add(new JLabel(""));
		surCentro.add(crearEmpleadoAdministrador);
		surCentro.add(crearEmpleadoOperario);
		surCentro.add(crearEmpleadoCajero);
		surCentro.add(new JLabel(""));
		
		surSur.add(volver);
		
		sur0.add(surNorte, BorderLayout.NORTH);
		sur0.add(surCentro, BorderLayout.CENTER);
		sur0.add(surSur, BorderLayout.SOUTH);
		
		add(sur0, BorderLayout.SOUTH);
		remove(sur);
	}
	
	private void crearCliente(String tipo) {
		try {
			nuevoUsuario = new Cliente(txtNombre.getText(),txtUser.getText(),txtPassword.getText(),txtTelefono.getText(),txtCorreo.getText(),0,central.getGaleria(), tipo);
			central.get__empleado().getEmpleado().agregarUsuario(nuevoUsuario);
			msjError.setForeground(Color.GREEN);
			msjError.setText("Usuario creado con exito");
			
		} catch (OperacionIlegal e) {
			// TODO Auto-generated catch block
			msjError.setForeground(Color.RED);
			msjError.setText("El usuario ya exise : OperacionIlegal");
		}
	}
	
	private void crearEmpleado(String tipo) {
		try {
			nuevoUsuario = new Empleado(txtNombre.getText(),txtUser.getText(),txtPassword.getText(),txtTelefono.getText(),txtCorreo.getText(),central.getGaleria(), tipo);
			central.get__empleado().getEmpleado().agregarUsuario(nuevoUsuario);
			msjError.setForeground(Color.GREEN);
			msjError.setText("Usuario creado con exito");
			
		} catch (OperacionIlegal e) {
			// TODO Auto-generated catch block
			msjError.setForeground(Color.RED);
			msjError.setText("El usuario ya exise : OperacionIlegal");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String grito = e.getActionCommand();
		if(grito.equals(CLIENTE)) {
			if(confirma() == null) {
				cambioCliente();
			}else {
				msjError(confirma());
			}
		}
		if(grito.equals(EMPLEADO)) {
			if(confirma() == null) {
				cambioEmpleado();
			}else {
				msjError(confirma());
			}
		}
		if(grito.equals(AUTOR)) {
			crearCliente("Autor");
		}
		
		if(grito.equals(COMPRADOR)) {
			crearCliente("Comprador");
		}
		
		if(grito.equals(PROPIETARIO)) {
			crearCliente("Propietario");
		}
		
		if(grito.equals(ADMIN)) {
			crearEmpleado("Administrador");
		}
		
		if(grito.equals(OPERARIO)) {
			crearEmpleado("Operador");
		}
		
		if(grito.equals(CAJERO)) {
			crearEmpleado("Cajero");
		}
		
		if(grito.equals(VOLVER)) {
			dispose();
		}
		
	}
}


