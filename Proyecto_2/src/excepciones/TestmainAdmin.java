package excepciones;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import modelo.Admin;
import modelo.Cliente;
import modelo.Galeria;
import modelo.Pieza;
import modelo.Pintura;
import modelo.Subasta;

public class TestmainAdmin {

    private Pieza pieza;
    private Cliente cliente;
    private Galeria galeria;
    private Admin admin;

    @BeforeEach
    public void setUp() throws Exception {
        galeria = new Galeria();
        admin = new Admin("Administrador", "Jess", "js.garay", "1234", "301355666", "@@@@", this.galeria);
        cliente = new Cliente("Cliente", "Juan Pérez", "jperez","123456789", "23456", "juanperez@example.com", null, galeria);

        Calendar calendario = Calendar.getInstance();
        calendario.set(2022, Calendar.JANUARY, 1);
        Date ingreso = Date.valueOf("2022-01-01");

        List<String> miLista = new ArrayList<>();
        miLista.add("pintura");
        miLista.add("pigmentos");

        pieza = new Pintura(1, "Título de la pieza", 2020, "Ciudad de creación", "Pintura", true, 1000, 500, false, ingreso, 30, "Consignacion", null, "45x30", miLista, "sol", "oleo");
    }

    @Test
    public void testAgregarPieza() throws Error {
            galeria.agregerAExhibicion(pieza);
            assertEquals(pieza, admin.agregarPieza(pieza, 1));
            
            Error e = assertThrows(Error.class, () -> admin.agregarPieza(pieza, 4));
            assertEquals("Seleccionaste una opción incorrecta, intente nuevamente", e.getMessage());
            
            
            galeria.agregerABodega(pieza);
            assertEquals(pieza, admin.agregarPieza(pieza, 2));
            
            
            assertEquals(null, admin.agregarPieza(pieza, 0));
            
            Admin ad = new Admin("xd", "Jess", "js.garay", "1234", "301355666", "@@@@", this.galeria);
            
            
            Error e1 = assertThrows(Error.class, () -> ad.agregarPieza(pieza, 1));
            assertEquals("Acción limitada, únicamente el administrador puede registrar piezas." , e1.getMessage());
}
    @Test   
    public void testCambiarUbicacionPieza() {
        galeria.agregerAExhibicion(pieza);
        
        // Llama al método para cambiar la ubicación de la pieza
        admin.cambiarUbicacionPieza("Título de la pieza");
        
        // Verifica si la pieza está ahora en la lista de bodega
        assertTrue(galeria.getListaEnBodega().contains(pieza));
           
        
    }
    
    
    @Test   
    public void testCambiarUbicacionPieza2() {
        galeria.agregerABodega(pieza);
        
        // Llama al método para cambiar la ubicación de la pieza
        admin.cambiarUbicacionPieza("Título de la pieza");
        
        // Verifica si la pieza está ahora en la lista de bodega
        assertFalse(galeria.getListaEnBodega().contains(pieza));
           
        
    }
    
    
    @Test   
    public void testagregarUsuario() throws Error {
       
    	galeria.getUsuarios().put(cliente.getLogin(), cliente);
        
    	
    	Error e1 = assertThrows(Error.class, () -> admin.agregarUsuario(cliente));
        assertEquals("Ya existe un usuario con ese login" , e1.getMessage());
        
        
        galeria.getUsuarios().remove(cliente.getLogin());
        admin.agregarUsuario(cliente);
       
    
    }
    
    
    @Test   
    public void testcambioInfo() throws Error {
    	admin.agregarUsuario(cliente);
    	admin.modificarInfoUsuario(cliente);
    	assertEquals(cliente , admin.modificarInfoUsuario(cliente));
       
    }
    
    @Test   
    public void testcambioPieza() throws Error {
    	
    	galeria.agregerAVendidas(pieza);
    	admin.modificarPieza(pieza);
       
    }
    
    @Test   
    public void testcrearSubasta() throws Error {
    	Subasta subasta = new Subasta("Subasta", false);
    	
    	assertEquals(subasta , admin.crearSubasta(subasta));
       
    }
    
    @Test
    public void testPiezasSubasta() throws Error {
    	Subasta subasta = new Subasta("Subasta", false);
    	
    	galeria.agregerAExhibicion(pieza);
    	
    	
    	admin.agregarPiezaSubasta(subasta, pieza);

    	
       
    }
    
    @Test
    public void testPiezasSubasta2() throws Error {
    	Subasta subasta = new Subasta("Subasta", false);
    	
    	
    	Calendar calendario = Calendar.getInstance();
        calendario.set(2022, Calendar.JANUARY, 1);
        Date ingreso = Date.valueOf("2022-01-01");

        List<String> miLista = new ArrayList<>();
        miLista.add("pintura");
        miLista.add("pigmentos");

        Pieza pieza1 = new Pintura(1, "Título de la pieza", 2020, "Ciudad de creación", "Pintura", true, 1000, 500, false, ingreso, 30, "Consignacion", null, "45x30", miLista, "sol", "oleo");
        
    	galeria.agregerARegresadas(pieza1);
    	
    	
    	admin.agregarPiezaSubasta(subasta, pieza1);

    	
       
    }
       
    
        
        
        
    }
    
    
    

