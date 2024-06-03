package Req;

import java.util.ArrayList;
import java.util.List;

import Principal.Central;
import VerClases.VerPiezas;
import VerClases.VerUsuarios;
import VerClases.verSubastas;
import modelo.Cliente;
import modelo.Pieza;
import modelo.Subasta;
import modelo.Usuario;

public class __Comprador {

	Central central;
	Cliente cliente;
	
	public __Comprador(Central central) {
		super();
		this.central = central;
		cliente = (Cliente) central.getUsuario();
	}


	public void iniciarCompra(int i) {
		// TODO Auto-generated method stub
		
	}


	public void verSubastasActuales() {
		List<Subasta> listaSubastas = central.getGaleria().getSubastas();
		
		new verSubastas(listaSubastas);
	}


	public void getListaPiezas(Integer num) {
		List<Pieza> listaPiezas;
		if(num == 1) {
			listaPiezas = cliente.getAutoriaVendidasCompradas();
		}else {
			listaPiezas = cliente.getPrestadasEnEspera();
		}
		
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
