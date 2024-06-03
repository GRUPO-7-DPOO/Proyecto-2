package Req;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import CrearClases.CrearPieza;
import CrearClases.CrearUsuario;
import CrearClases.PantallaCambio;
import CrearClases.PantallaPieza;
import Principal.Central;
import Principal.Menu;
import VerClases.VerCompras;
import VerClases.VerPiezas;
import VerClases.VerUsuarios;
import modelo.Compra;
import modelo.Empleado;
import modelo.Galeria;
import modelo.Pieza;
import modelo.Usuario;

public class __Empleado {

	Central central;
	Empleado empleado;

	public __Empleado(Central central) {
		super();
		this.central = central;
		empleado = (Empleado) central.getUsuario();
	}

	public Central getCentral() {
		return central;
	}

	public void setCentral(Central central) {
		this.central = central;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public void administrarPiezas() {
		// TODO Auto-generated method stub
		new Menu(Menu.EMPLEADO, 1, central);
	}

	public void administrarSubastas() {
		new Menu(Menu.EMPLEADO, 2, central);
	}

	public void verificarOfertasPendientes() {
		// TODO Auto-generated method stub
		
	}

	public void verificarComprasPendientes() {
		// TODO Auto-generated method stub
		
	}

	public void administrarUsuarios() {
		new Menu(Menu.EMPLEADO, 5, central);
	}

	public void getComprasHechas() {
		List<Compra> compras = central.getGaleria().getCompras();
		
		new VerCompras(compras);
	}

	public void getPerfil() {
		ArrayList<Usuario> perf = new ArrayList<>();
		perf.add(empleado);
		new VerUsuarios(perf);
	}

	public void historiaComprador() {
		// TODO Auto-generated method stub
		
	}

	public void historiaArtista() {
		// TODO Auto-generated method stub
		
	}

	public void historiaPieza() {
		// TODO Auto-generated method stub
		
	}

	public void agregarPieza(Integer opcion) {
		// TODO Auto-generated method stub
		if (opcion == 1) {
			Galeria galeria = central.getGaleria();
			new PantallaPieza(galeria, central, "exhibicion");
			
        } else if (opcion == 2) {
        	Galeria galeria = central.getGaleria();
			new PantallaPieza(galeria, central, "bodega");
        }
	}

	public void cambiarUbicacionPieza() {
		Galeria galeria = central.getGaleria();
		
		new PantallaCambio(galeria, central);
		
	}

	public void getPiezasEnExhibicionYEnBodega() {
		List<Pieza> piezasE = central.getGaleria().getEnExhibicion();
		List<Pieza> piezasB = central.getGaleria().getEnBodega();
		piezasE.addAll(piezasB);
				
		new VerPiezas(piezasE);
	}

	public void devolucionesPendientes() {
		
		Empleado admin = (Empleado) central.getUsuario();
		 List<Pieza> piezasVencidas = admin.getPiezasVencidas();
		 new VerPiezas(piezasVencidas);
		
	}

	public void getPiezasVendidas() {
		List<Pieza> piezas = central.getGaleria().getVendidas();
		
		new VerPiezas(piezas);
	}

	public void getPiezasRegresadas() {
		List<Pieza> piezas = central.getGaleria().getRegresadas();
		
		new VerPiezas(piezas);
	}

	public void modificarPieza() {
		
		// TODO Auto-generated method stub
		
	}

	public void getDetallesSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void crearSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void modificarSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void cerrarSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void agregarPiezaSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void removerPiezaSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void verificarClientes() {
		// TODO Auto-generated method stub
		
	}

	public void verificarOfertaSubasta() {
		// TODO Auto-generated method stub
		
	}

	public void crearUsuario() {
		new CrearUsuario(central);
		
	}
	

	public void modificarUsuario() {
		// TODO Auto-generated method stub
		
	}

	public void getUsuarios() {
		// TODO Auto-generated method stub
		Galeria galeria = central.getGaleria();
		Collection<Usuario> usuarios = galeria.getUsuarios().values();
		new VerUsuarios(usuarios);
	}
	
	
}
