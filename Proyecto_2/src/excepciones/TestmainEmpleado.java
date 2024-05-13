package excepciones;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import modelo.Cliente;
import modelo.Compra;
import modelo.Empleado;
import modelo.Galeria;
import modelo.Oferta;
import modelo.Pieza;
import modelo.Subasta;

public class TestmainEmpleado {

    private TestmainEmpleado mainEmpleado;
    private InputStream inputStream;
    private Galeria galeria;
    private Empleado empleado;

    @Before
    public void setUp() {
        mainEmpleado = new TestmainEmpleado();
        galeria = new Galeria();
        empleado = new Empleado("Empleado", "Juan Pérez", "juanperez", "password", "123456789", "juanperez@example.com", galeria);
        mainEmpleado.galeria = galeria;
        mainEmpleado.empleado = empleado;
    }

    @Test
    public void testInput() {
        String input = "Hola mundo";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        String result = mainEmpleado.input("Ingrese un valor:");
        assertEquals(input, result);
    }

    private String input(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Test
    public int testObtenerOpcion() {
        String input = "3";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        int result = mainEmpleado.testObtenerOpcion();
        assertEquals(3, result);
		return result;
    }

    @Test
    public void testGetMenuAdmin() {
        String expected = "0. Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Compras pendientes\n5. Administrar usuarios\n6. Compras hechas\n7. Perfil\n";
        String result = mainEmpleado.getMenuAdmin(null);
        assertEquals(expected, result);
    }

    private String getMenuAdmin(Object object) {
		// TODO Auto-generated method stub
		return null;
	
  
    }

    @Test
    public String testGetPiezasEnExhibicion() {
        Pieza pieza1 = new Pieza(1, "Pieza 1", 2020, "Ciudad 1", true, 100, 80, false, null, 30, "Préstamo", null, null, "Pintura") {
           
        };
        Pieza pieza2 = new Pieza(2, "Pieza 2", 2021, "Ciudad 2", false, 200, 150, true, null, 60, "Venta", null, null, "Escultura") {
           
        };
        galeria.getEnExhibicion().put("pieza 1", pieza1);
        galeria.getEnExhibicion().put("pieza 2", pieza2);

        String expected = "Piezas en exhibicion:\n1- Pieza 1\n2- Pieza 2\n";
        String result = mainEmpleado.testGetPiezasEnExhibicion();
        assertEquals(expected, result);
		return result;
    }

}