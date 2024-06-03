package VerClases;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Principal.Central;
import modelo.Usuario;

public class VerUsuarios extends JFrame implements ActionListener{

	Central central;
	
	private static final Font font = new Font("titulos", Font.BOLD, 12);
	
	private static final JTextField nombre = new JTextField("NOMBRE");
	private static final JTextField user = new JTextField("USER");
	private static final JTextField password = new JTextField("PASSWORD");
	private static final JTextField telefono = new JTextField("TELEFONO");
	private static final JTextField correo = new JTextField("CORREO");
	
	private static final JButton volver = new JButton("Volver");
	private static final String VOLVER = "VOLVER";
	
	
	public VerUsuarios(Collection<Usuario> listaUsuarios) {
		super();
		
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		nombre.setEditable(false);
		user.setEditable(false);
		password.setEditable(false);
		telefono.setEditable(false);
		correo.setEditable(false);
		
		nombre.setFont(font);
		user.setFont(font);
		password.setFont(font);
		telefono.setFont(font);
		correo.setFont(font);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel norte = new JPanel();
		JPanel centro = new JPanel();
		JPanel sur = new JPanel();
		
		norte.setLayout(new GridLayout(1,5));
		norte.add(nombre);
		norte.add(user);
		norte.add(password);
		norte.add(telefono);
		norte.add(correo);
		
		centro.setLayout(new GridLayout(listaUsuarios.size(),5));
		
		for(Usuario usuario: listaUsuarios) {
			JTextField txtnombre = new JTextField(usuario.getNombre());
			JTextField txtuser = new JTextField(usuario.getLogin());
			JTextField txtpassword = new JTextField(usuario.getPassword());
			JTextField txttelefono = new JTextField(usuario.getTelefono());
			JTextField txtcorreo = new JTextField(usuario.getCorreo());
			
			txtnombre.setEditable(false);
			txtuser.setEditable(false);
			txtpassword.setEditable(false);
			txttelefono.setEditable(false);
			txtcorreo.setEditable(false);
			
			centro.add(txtnombre);
			centro.add(txtuser);
			centro.add(txtpassword);
			centro.add(txttelefono);
			centro.add(txtcorreo);
		}
		
		sur.add(volver);

		add(norte, BorderLayout.NORTH);
		add(centro, BorderLayout.CENTER);
		add(sur, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String rt = e.getActionCommand();
		if(rt.equals(VOLVER)) {
			dispose();
		}
	}

}
