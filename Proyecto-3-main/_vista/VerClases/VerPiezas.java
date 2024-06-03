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
import modelo.Pieza;
import modelo.Usuario;

public class VerPiezas extends JFrame implements ActionListener{

	Central central;
	
	private static final Font font = new Font("titulos", Font.BOLD, 12);
	
	private static final JTextField id = new JTextField("ID");
	private static final JTextField titulo = new JTextField("TITULO");
	private static final JTextField anioCreacion = new JTextField("ANIO CREACION");
	private static final JTextField lugarCreacion = new JTextField("LUGAR CREACION");
	private static final JTextField tipoPieza = new JTextField("TIPO");
	private static final JTextField autores = new JTextField("AUTORES");

	private static final JButton volver = new JButton("Volver");
	private static final String VOLVER = "VOLVER";
	
	
	public VerPiezas(Collection<Pieza> listaPiezas) {
		super();
				
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		id.setEditable(false);
		titulo.setEditable(false);
		anioCreacion.setEditable(false);
		lugarCreacion.setEditable(false);
		tipoPieza.setEditable(false);
		autores.setEditable(false);
		
		id.setFont(font);
		titulo.setFont(font);
		anioCreacion.setFont(font);
		lugarCreacion.setFont(font);
		tipoPieza.setFont(font);
		autores.setFont(font);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel norte = new JPanel();
		JPanel centro = new JPanel();
		JPanel sur = new JPanel();
		
		norte.setLayout(new GridLayout(1,5));
		norte.add(id);
		norte.add(titulo);
		norte.add(anioCreacion);
		norte.add(lugarCreacion);
		norte.add(tipoPieza);
		norte.add(autores);
		
		centro.setLayout(new GridLayout(listaPiezas.size(),6));
		
		for(Pieza pieza: listaPiezas) {
			JTextField txtid = new JTextField(pieza.getId());
			JTextField txtitulo = new JTextField(pieza.getTitulo());
			JTextField txtanioCreacion = new JTextField(pieza.getAnioCreacion());
			JTextField txtlugarCreacion = new JTextField(pieza.getLugarCreacion());
			JTextField txttipoPieza = new JTextField(pieza.getTipo().toString());
			
			String autores = "";
			for(Cliente autor: pieza.getAutores()) {
				autores += " | " + autor.getNombre() + " | ";
			}
			
			JTextField txtautores = new JTextField(autores);
			
			txtid.setEditable(false);
			txtitulo.setEditable(false);
			txtanioCreacion.setEditable(false);
			txtlugarCreacion.setEditable(false);
			txttipoPieza.setEditable(false);
			txtautores.setEditable(false);
			
			centro.add(txtid);
			centro.add(txtitulo);
			centro.add(txtanioCreacion);
			centro.add(txtlugarCreacion);
			centro.add(txttipoPieza);
			centro.add(txtautores);
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
