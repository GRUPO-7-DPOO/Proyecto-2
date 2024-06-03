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
import modelo.Usuario;

public class VerCompras extends JFrame implements ActionListener{

	Central central;
	
	private static final Font font = new Font("titulos", Font.BOLD, 12);
	
	private static final JTextField valorPagado = new JTextField("VALOR PAGADO");
	private static final JTextField metodoDePago = new JTextField("METODO DE PAGO");
	private static final JTextField cliente = new JTextField("CLIENTE");
	private static final JTextField pieza = new JTextField("PIEZA");
	private static final JTextField fecha = new JTextField("FECHA");

	private static final JButton volver = new JButton("Volver");
	private static final String VOLVER = "VOLVER";
	
	
	public VerCompras(Collection<Compra> listaCompras) {
		super();
				
		volver.setActionCommand(VOLVER);
		volver.addActionListener(this);
		
		valorPagado.setEditable(false);
		metodoDePago.setEditable(false);
		cliente.setEditable(false);
		pieza.setEditable(false);
		fecha.setEditable(false);
		
		valorPagado.setFont(font);
		metodoDePago.setFont(font);
		cliente.setFont(font);
		pieza.setFont(font);
		fecha.setFont(font);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		JPanel norte = new JPanel();
		JPanel centro = new JPanel();
		JPanel sur = new JPanel();
		
		norte.setLayout(new GridLayout(1,5));
		norte.add(valorPagado);
		norte.add(metodoDePago);
		norte.add(cliente);
		norte.add(pieza);
		norte.add(fecha);
		
		centro.setLayout(new GridLayout(listaCompras.size(),5));
		
		for(Compra compra: listaCompras) {
			JTextField txtvalorPagado = new JTextField(compra.getValorPagado());
			JTextField txmetodoDePago = new JTextField(compra.getMetodoPago().toString());
			JTextField txtcliente = new JTextField(compra.getCliente().getNombre());
			JTextField txtpieza = new JTextField(compra.getPieza().getTitulo());
			JTextField txtfecha = new JTextField(compra.getFecha().toString());
			
			txtvalorPagado.setEditable(false);
			txmetodoDePago.setEditable(false);
			txtcliente.setEditable(false);
			txtpieza.setEditable(false);
			txtfecha.setEditable(false);
			
			centro.add(txtvalorPagado);
			centro.add(txmetodoDePago);
			centro.add(txtcliente);
			centro.add(txtpieza);
			centro.add(txtfecha);
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
