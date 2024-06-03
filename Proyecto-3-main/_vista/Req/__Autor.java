package Req;

import java.util.ArrayList;
import java.util.List;

import Principal.Central;
import VerClases.VerPiezas;
import VerClases.VerUsuarios;
import modelo.Cliente;
import modelo.Pieza;
import modelo.Usuario;

public class __Autor {

	Central central;
	Cliente cliente;

	public __Autor(Central central) {
		super();
		this.central = central;
		cliente = (Cliente) central.getUsuario();
	}

	public void getPiezasAutorVendidas() {
		List<Pieza> piezas = cliente.getAutoriaVendidasCompradas();
		new VerPiezas(piezas);
	}

	public void getPerfil() {
		ArrayList<Usuario> perf = new ArrayList<>();
		perf.add(cliente);
		new VerUsuarios(perf);
	}

	public Central getCentral() {
		return central;
	}

	public void setCentral(Central central) {
		this.central = central;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
}
