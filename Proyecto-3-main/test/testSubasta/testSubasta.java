package testSubasta;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import modelo.Cliente;
import modelo.Fotografia;
import modelo.Galeria;
import modelo.Oferta;
import modelo.Otro;
import modelo.Pieza;
import modelo.Subasta;


class testSubasta {
	
	Galeria galeria = null;
	
	Subasta subastaVacia = new Subasta("SubastaVacia", true);
	Subasta subastaNoVacia = new Subasta("SubastaNoVacia", true);
	
	Cliente cliente1 = new Cliente("Jhon1","login1", null, null, null,  10000000, galeria, "Comprador");
	Cliente cliente2 = new Cliente("Jhon2","login2", null, null, null, 10000000, galeria, "Comprador");
	Cliente cliente3 = new Cliente("Jhon3","login3", null, null, null, 10000000, galeria, "Comprador");
	
	String clientesId = "login1,login2,login3";
	
	Pieza pieza1 = new Fotografia(1, "5 noites com eduagdo 1", 2000, "China", "Fotografia", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null, null, null, null);
	Pieza pieza2 = new Fotografia(2, "5 noites com eduagdo 2", 2000, "China", "Fotografia", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null, null, null, null);
	Pieza pieza3 = new Fotografia(3, "5 noites com eduagdo 3", 2000, "China", "Fotografia", true, 100000, 3000, false, new Date(), 1000, "Consignacion", new Date(), null, null, null, null);
	
	String piezasId = "1,2,3";
	
	Oferta oferta1 = new Oferta(9999, 500000, true, cliente1, pieza1, true, true);
	Oferta oferta2 = new Oferta(9949, 100000, true, cliente2, pieza1, true, true);
	Oferta oferta3 = new Oferta(9959, 400000, true, cliente3, pieza1, true, true);
	
	String ofertasId = "9999,9949,9959";
	
	List<Oferta> ofertas = new ArrayList<Oferta>();
	List<Cliente> clientes = new ArrayList<Cliente>();
	List<Pieza> piezas = new ArrayList<Pieza>();
	 
	@Test
	void testGetMayorOferta() {
		assertEquals(subastaVacia.getMayorOferta(pieza1), null, "No retrna el valor correcto en el caso de subasta vacia");
		
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta3);
		
		subastaNoVacia.setAceptadas(ofertas);
		
		Oferta ofertaTest = subastaNoVacia.getMayorOferta(pieza1);
		
		assertEquals(ofertaTest, oferta1, "No coincide la mayor oferta");
	
	}
	
	@Test 
	void getOfertasId(){
		assertEquals(subastaVacia.getOfertasId(ofertas), "", "Retorno incorrecto caso subasta vacia");
		
		ofertas.add(oferta1);
		ofertas.add(oferta2);
		ofertas.add(oferta3);
		
		assertEquals(subastaNoVacia.getOfertasId(ofertas), ofertasId, "No coinciden los ids esperados");
	}
	
	@Test
	void testGetClienteLogin() {
		String out = subastaVacia.getClienteLogin(clientes);
		assertEquals(out, "", "Error con clientes vacio");
		
		clientes.add(cliente1);
		clientes.add(cliente2);
		clientes.add(cliente3);
		
		assertEquals(subastaVacia.getClienteLogin(clientes), clientesId, "No coinciden los ids esperados");
	}
	
	@Test
	void testGetPiezaId() {
		assertEquals("", subastaVacia.getPiezaId(piezas), "No coincide con lista vacia");
		
		piezas.add(pieza1);
		piezas.add(pieza2);
		piezas.add(pieza3);
		
		assertEquals(piezasId, subastaVacia.getPiezaId(piezas), "No coinciden los ids de las piezas");
	}
}
