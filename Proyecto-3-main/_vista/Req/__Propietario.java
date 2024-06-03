package Req;

import java.util.ArrayList;
import java.util.List;

import Principal.Central;
import VerClases.VerPiezas;
import VerClases.VerUsuarios;
import modelo.Cliente;
import modelo.Pieza;
import modelo.Usuario;

public class __Propietario {

	Central central;
	Cliente cliente;
	
	public __Propietario(Central central) {
		super();
		this.central = central;
		cliente = (Cliente) central.getUsuario();
	}

	public void getPiezasPrestadas() {
		List<Pieza> listaPiezas = cliente.getPrestadasEnEspera();
		
		new VerPiezas(listaPiezas);
	}

	public void getPiezasVendidas() {
		List<Pieza> listaPiezas = cliente.getAutoriaVendidasCompradas();
		
		new VerPiezas(listaPiezas);
		
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
