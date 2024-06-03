package testGaleria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Cliente;
import modelo.Compra;
import modelo.Empleado;
import modelo.Galeria;
import modelo.Oferta;
import modelo.Otro;
import modelo.Pieza;
import modelo.Subasta;
import modelo.TipoPago;
import modelo.Usuario;

class testGaleria {
	
	Galeria galeria;
	
	@BeforeEach
	void setUp() throws Exception {
		galeria = new Galeria();
	}
	
	
	
	Subasta subastaVacia = new Subasta("SubastaVacia", true);
	Subasta subastaNoVacia = new Subasta("SubastaNoVacia", true);
	
	Cliente cliente1 = new Cliente("Jhon1","login1", null, null, null, 10000000, galeria, "Comprador");
	Cliente cliente2 = new Cliente("Jhon2","login2", null, null, null,  10000000, galeria, "Comprador");
	Cliente cliente3 = new Cliente("Jhon3","login3", null, null, null,  10000000, galeria, "Comprador");
	
	Pieza pieza1 = new Otro(1, "5 noites com eduagdo 1", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza2 = new Otro(2, "5 noites com eduagdo 2", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza3 = new Otro(3, "5 noites com eduagdo 3", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza4 = new Otro(4, "5 noites com eduagdo 4", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza5 = new Otro(5, "5 noites com eduagdo 5", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza6 = new Otro(6, "5 noites com eduagdo 6", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza7 = new Otro(7, "5 noites com eduagdo 7", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza8 = new Otro(8, "5 noites com eduagdo 8", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza9 = new Otro(9, "5 noites com eduagdo 9", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);
	Pieza pieza10 = new Otro(10, "5 noites com eduagdo 10 ", 2000, "China", "Pintura", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null);

	List<Pieza> enBodega = new ArrayList<>();
	List<Pieza> enExhibicion = new ArrayList<>();
	List<Pieza> vendidas= new ArrayList<>();
	List<Pieza> regresadas = new ArrayList<>();
	
	String piezasId = "1,2,3";
	
	//                                   revisada       aceptada|pagada           
	Oferta oferta1 = new Oferta(1, null, false, null, null, true, false);
	Oferta oferta2 = new Oferta(2, null, false, null, null, true, false);
	Oferta oferta3 = new Oferta(3, null, true, null, null, true, false);
	Oferta oferta4 = new Oferta(4, null, false, null, null, true, true);
	Oferta oferta5 = new Oferta(5, null, false, null, null, false, false);
	
	@Test
	void testGetPiezaPorId() {
		
		galeria.setEnExhibicion(enExhibicion);
		galeria.setEnBodega(enBodega);
		galeria.setVendidas(vendidas);
		galeria.setRegresadas(regresadas);
		
		assertEquals(null, galeria.getPiezaPorId(null),"Encuentra una pieza inexistente");
		
		enBodega.add(pieza1);
		assertEquals(pieza1, galeria.getPiezaPorId(1), "No coinide la búsqueda en bodega");
		
		enExhibicion.add(pieza10);
		assertEquals(pieza10, galeria.getPiezaPorId(10), "No coincide la busqueda en exhibicion");
		
		vendidas.add(pieza4);
		assertEquals(pieza4, galeria.getPiezaPorId(4), "No coincide la busqueda en vendidas");
		
		regresadas.add(pieza8);
		assertEquals(pieza8, galeria.getPiezaPorId(8), "No coincide la busqueda en regresadas");
	}
	
	@Test
	void testGetPropietario() {
		cliente1.setPrestadasEnEspera(vendidas);
		
		assertEquals(null, galeria.getPropietario(pieza1), "No coinciden el propietario al no tener");
		
		vendidas.add(pieza1);
		vendidas.add(pieza2);
		
		cliente1.setPrestadasEnEspera(vendidas);
		
		Map<String, Usuario> usuarios0 = new HashMap<String, Usuario>();
		usuarios0.put(cliente1.getLogin(), cliente1);
		galeria.setUsuarios(usuarios0);
		
		assertEquals(cliente1, galeria.getPropietario(pieza1), "No coincide el propietario");
	}
	
	@Test
	void testGetOfertasPendientes() {
		
		ArrayList<Oferta> ofertasTest = new ArrayList<Oferta>();
		assertEquals(ofertasTest, galeria.getOfertasPendientes(), "Error al no tener ofertas pendientes");
		
		ofertasTest.add(oferta1);
		ofertasTest.add(oferta2);
		ofertasTest.add(oferta3);
		ofertasTest.add(oferta4);
		ofertasTest.add(oferta5);
		
		ArrayList<Oferta> ofertasOut = new ArrayList<Oferta>();
		ofertasOut.add(oferta1);
		ofertasOut.add(oferta2);
		ofertasOut.add(oferta5);
		//Ofertas out no incluye la oferta 3 y 4 pq estas no cumplen con los parametros
		
		galeria.setOfertas(ofertasTest);
		
		assertEquals(ofertasOut, galeria.getOfertasPendientes(), "No coinciden las ofertas pendientes");
	}
	
	@Test 
	void testGetComprasPendientes(){
		ArrayList<Oferta> comprasTest = new ArrayList<Oferta>();
		assertEquals(comprasTest, galeria.getComprasPendientes(),"Error caso listas vacias");
		
		comprasTest.add(oferta1);
		comprasTest.add(oferta2);
		comprasTest.add(oferta3);
		comprasTest.add(oferta4);
		comprasTest.add(oferta5);
		
		ArrayList<Oferta> comprasOut = new ArrayList<Oferta>();
		comprasOut.add(oferta3);
		galeria.setOfertas(comprasTest);

		assertEquals(comprasOut, galeria.getComprasPendientes(), "No coinciden las compras con los parametros");
	}
	
	@Test
	void testGetOfertaPorId() {
		Integer id = 9999;
		assertEquals(null, galeria.getOfertaPorId(id), "Error en listas vacias");
		
		Integer _id = 4;
		
		ArrayList<Oferta> _l = new ArrayList<Oferta>();
		
		_l.add(oferta1);
		_l.add(oferta2);
		_l.add(oferta3);
		_l.add(oferta4);
		_l.add(oferta5);
		galeria.setOfertas(_l);
		
		assertEquals(oferta4, galeria.getOfertaPorId(_id), "No coincide la oferta buscada");
	}

}
