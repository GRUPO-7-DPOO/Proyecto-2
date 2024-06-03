package testEmpleado;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import excepciones.OperacionIlegal;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Escultura;
import modelo.Galeria;
import modelo.MaterialConstruccion;
import modelo.Oferta;
import modelo.Otro;
import modelo.Pieza;
import modelo.Pintura;
import modelo.Subasta;
import modelo.Usuario;

class testEmpleado {

	@Test
	void testAgregarEnBodega() throws Exception {
		int opcion = 2;
		Empleado empleado;
		Galeria galeria;
		Pieza pieza;
		
		
		galeria = new Galeria();
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		pieza = new Escultura(32, "L", 2022, "Bogota", "Escultura", true, 20000, 30000, false, new Date(), 23, "Consignacion", new Date(), "2x3x4", 8, false, "No", new ArrayList<MaterialConstruccion>());
		Pieza retorno = empleado.agregarPieza(pieza, opcion);
		List<Pieza> lista = galeria.getEnBodega();
		assertTrue("El objeto no se añadió correctamente a la lista", lista.contains(pieza));
		//assertEquals(pieza, retorno);
		assertEquals(pieza, retorno);
	}
	
	@Test
	void testCambiarUbicacionPiezaBodegaAExhibicion() throws Exception {
		
		int pos = 0;
		int opcion1 = 1;
		int opcion2 = 2;
		Empleado empleado;
		Galeria galeria;
		Pieza pieza;
		
		galeria = new Galeria();
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		pieza = new Escultura(32, "L", 2022, "Bogota", "Escultura", true, 20000, 30000, false, new Date(), 23, "Consignacion", new Date(), "45x3", 8, false, "No", new ArrayList<MaterialConstruccion>());
		empleado.agregarPieza(pieza, opcion2);
		empleado.cambiarUbicacionPieza(opcion1, pos);
		List<Pieza> bodega = galeria.getEnBodega();
		List<Pieza> exhibicion = galeria.getEnExhibicion();
		if (exhibicion.contains(pieza) && bodega.isEmpty()) {
			assertEquals(1,1);
		}else {
			fail("El resultado no es el esperado");
		}
		
	}
	
	@Test
	void testAgregarUsuario() throws Exception {
		
		Galeria galeria;
		Map<String, Usuario> usuarios;
		Empleado empleadoInicial;
		Empleado empleadoAniadir;
		
		galeria = new Galeria();
		empleadoInicial = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		empleadoAniadir = new Empleado("Javier", "j.javier", "121", "249856", "javi@gmail", galeria, "Administrador");
		empleadoInicial.agregarUsuario(empleadoAniadir);
		usuarios = galeria.getUsuarios();
		assertTrue("El objeto no se añadió correctamente a la lista", usuarios.containsKey(empleadoAniadir.getLogin()));
		
	}
	
	@Test
	void testModificarPieza() throws Exception {
		
		Empleado empleado;
		Galeria galeria;
		int opcion1 = 1;
		Pieza piezaOriginal;
		Pieza piezaModificada;
		Pieza extraida;
		
		galeria = new Galeria();
		piezaOriginal = new Pintura(32, "L", 2022, "Bogota", "Pintura", true, 20000, 23, "Consignacion",null, null, null );
		piezaModificada = new Pintura(32, "ZA", 2022, "Bogota", "Pintura", true, 20000, 23, "Consignacion",null, null, null );
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		empleado.agregarPieza(piezaOriginal, opcion1);
		empleado.modificarPieza(piezaModificada);
		extraida = galeria.getPiezaPorId(piezaModificada.getId());
		assertTrue("El objeto no quedo modificado", (piezaModificada.getAnioCreacion() == extraida.getAnioCreacion() && piezaModificada.getLugarCreacion() == extraida.getLugarCreacion()));
	}
	
	@Test
	void testCrearSubasta() throws Exception {
		
		Galeria galeria;
		Subasta subasta;
		List<Subasta> subastas;
		Empleado empleado;
		
		galeria = new Galeria();
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		subasta = new Subasta("Prueba", true);
		empleado.crearSubasta(subasta);
		subastas = galeria.getSubastas();
		assertTrue("Subasta no creada", subastas.contains(subasta));
		
		
		
	}
	
	@Test
	void testAgregarPiezaSubasta() throws Exception {
		
		Galeria galeria;
		Empleado empleado;
		Subasta subasta;
		Pieza pieza;
		int opcion2 = 2;
		
		galeria = new Galeria();
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		pieza = new Escultura(32, "L", 2022, "Bogota", "Escultura", true, 20000, 30000, false, new Date(), 23, "Consignacion", new Date(), "46x78", 8, false, "No", new ArrayList<MaterialConstruccion>());
		empleado.agregarPieza(pieza, opcion2);
		subasta = new Subasta("Prueba", true);
		empleado.crearSubasta(subasta);
		empleado.agregarPiezaSubasta(subasta, pieza);
		assertTrue("No se añadio la pieza a la subasta", subasta.getPiezas().contains(pieza));
		
		
	}
	
	@Test
	void testAceptarOferta() throws Exception {
		
		Galeria galeria;
		Empleado empleado;
		Empleado empleadoA;
		Subasta subasta;
		Pieza pieza;
		Oferta oferta;
		Cliente cliente;
		Boolean enPendientes;
		Boolean enAceptadas;
		
		
		// Oferta: Integer id, Integer valorOferta, boolean revisada, Cliente cliente, Pieza pieza, boolean aceptada, boolean pagado
		// Cliente: String nombre, String login, String password, String telefono, String correo, TipoCliente rol, Integer balance, Galeria galeria
		
		galeria = new Galeria();
		pieza = new Escultura(32, "L", 2022, "Bogota", "Escultura", true, 20000, 30000, false, new Date(), 23, "Consignacion", new Date(), "46x90", 8, false, "No", new ArrayList<MaterialConstruccion>());
		empleadoA = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Administrador");
		empleadoA.agregarPieza(pieza, 2);
		empleado = new Empleado("John", "j.john", "111", "243556", "john@gmail", galeria, "Operador");
		subasta = new Subasta("Prueba", true);
		cliente = new Cliente("Roberto", "r.berto", "bert", "464574", "r.berto@gmail.com", 50000, galeria, "Propietario");
		oferta = new Oferta(132, 20000, false, cliente, pieza, true, true);
		empleadoA.agregarPiezaSubasta(subasta, pieza);
		subasta.getPendientes().add(oferta);
		empleado.aceptarOferta(subasta, oferta);
		enPendientes = subasta.getPendientes().contains(oferta);
		enAceptadas = subasta.getAceptadas().contains(oferta);
		
		assertTrue("No aceptada", !enPendientes && enAceptadas);
		
	}

}
