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
import modelo.Pieza;
import modelo.Usuario;

public class CrearUsuario2 extends JFrame implements ActionListener{

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
	
	
	
	private static final JButton volver = new JButton("Volver");
	public static final String VOLVER = "VOLVER";
	
	private static final JButton crearClienteAutor = new JButton("Crear autor");
	public static final String AUTOR = "AUTOR";
	
	
	
	JPanel norte = new JPanel();
	JPanel centro = new JPanel();
	JPanel sur = new JPanel();
	
	Central central;
	Usuario nuevoUsuario;
	Pieza pieza;
	
	public CrearUsuario2(Central central, Pieza pieza) {
		super();
		this.central = central;
		this.pieza = pieza;
		
		
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		crearClienteAutor.setActionCommand(AUTOR);
		crearClienteAutor.addActionListener(this);

		
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
		surCentro.add(crearClienteAutor);
		
		
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
	


	Cliente crearCliente(String tipo) {
		try {
			nuevoUsuario = new Cliente(txtNombre.getText(),txtUser.getText(),txtPassword.getText(),txtTelefono.getText(),txtCorreo.getText(),0,central.getGaleria(), tipo);
			central.get__empleado().getEmpleado().agregarUsuario(nuevoUsuario);
			
			
		} catch (OperacionIlegal e) {
			// TODO Auto-generated catch block
			msjError.setForeground(Color.RED);
			msjError.setText("El usuario ya exise : OperacionIlegal");
		}
		return (Cliente) nuevoUsuario;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	    String grito = e.getActionCommand();
	    
	    if (grito.equals(AUTOR)) {
	        try {
	        	Cliente cliente2 = crearCliente("Autor");
	        	cliente2.getPrestadasEnEspera().add(pieza);
	        	
	        	new SuccessScreen(central);
	            dispose();
	        } catch (Exception ex) {
	           
	            msjError.setForeground(Color.RED);
	            msjError.setText("Error al crear el autor: " + ex.getMessage());
	        }
	    } else {
	        
	        msjError(confirma());
	    }
	}



	private void msjError(Object confirma) {
		// TODO Auto-generated method stub
		
	}



	private Object confirma() {
		// TODO Auto-generated method stub
		return null;
	}
}



