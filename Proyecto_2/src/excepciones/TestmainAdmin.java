package excepciones;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.Cliente;
import modelo.Pieza;

public class TestmainAdmin {

    private Pieza pieza;
    private Cliente autor;

    @Before
    public void setUp() {
        List<Cliente> autores = new ArrayList<>();
        autor = new Cliente("Autor", "Juan Pérez", "123456789", "juanperez@example.com", null, null, null, null);
        autores.add(autor);
        Calendar calendario = Calendar.getInstance();
        calendario.set(2022, Calendar.JANUARY, 1);
        Date ingreso = calendario.getTime();
        pieza = new Pieza(1, "Título de la pieza", 2020, "Ciudad de creación", true, 1000, 500, false, ingreso, 30, "Préstamo", null, autores, "Tipo de pieza") {
            // Implementación de métodos abstractos
        };
    }

    @Test
    public void testAgregarAutor() {
        try {
            Cliente nuevoAutor = new Cliente("Autor", "María Gómez", "987654321", "mariagomez@example.com", null, null, null, null);
            Cliente autorAgregado = pieza.agregarAutor(nuevoAutor);
            assertEquals(nuevoAutor, autorAgregado);
            assertTrue(pieza.getAutores().contains(nuevoAutor));
        } catch (Error e) {
            fail("No debería lanzar una excepción");
        }
    }

    @Test
    public void testAgregarAutorConRolIncorrecto() {
        try {
            Cliente clienteNoAutor = new Cliente("Cliente", "Pedro Ramírez", "456789012", "pedroramirez@example.com", null, null, null, null);
            pieza.agregarAutor(clienteNoAutor);
            fail("Debería lanzar una excepción");
        } catch (Error e) {
            assertEquals("El autor que quiere agregar no tiene el rol necesario para realizar esta operacion.", e.getMessage());
        }
    }

    @Test
    public void testGetFechaSalida() {
        Calendar calendario = Calendar.getInstance();
        calendario.set(2022, Calendar.JANUARY, 31);
        Date fechaEsperada = calendario.getTime();
        assertEquals(fechaEsperada, pieza.getFechaSalida());
    }

    // Agrega más pruebas para otros métodos según tus necesidades
}