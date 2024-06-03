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
import modelo.Cliente;
import modelo.Compra;
import modelo.Pieza;
import modelo.Subasta;
import modelo.Usuario;

public class verSubastas extends JFrame implements ActionListener{

	Central central;
	
	private static final Font font = new Font("titulos", Font.BOLD, 12);
	
	private static final JTextField nombre = new JTextField("NOMBRE");
	private static final JTextField activa = new JTextField("¿ACTIVA?");

	private static final JButton volver = new JButton("Volver");
	private static final String VOLVER = "VOLVER";
	
	
	public verSubastas(Collection<Subasta> listaSubastas) {
		super();
				
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		nombre.setEditable(false);
		activa.setEditable(false);

		
		nombre.setFont(font);
		activa.setFont(font);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel norte = new JPanel();
		JPanel centro = new JPanel();
		JPanel sur = new JPanel();
		
		norte.setLayout(new GridLayout(1,5));
		norte.add(nombre);
		norte.add(activa);
		
		centro.setLayout(new GridLayout(listaSubastas.size(),2));
		
		for(Subasta subasta: listaSubastas) {
			JTextField txtnombre = new JTextField(subasta.getNombre());
			String str;
			if(subasta.isActiva()) {
				str = "Si";
			}else {
				str = "No";
			}
			JTextField txtactiva = new JTextField(str);

			txtnombre.setEditable(false);
			txtactiva.setEditable(false);
			
			centro.add(txtnombre);
			centro.add(txtactiva);
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