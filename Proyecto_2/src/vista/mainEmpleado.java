package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import excepciones.Error;
import modelo.Admin;
import modelo.Cliente;
import modelo.Compra;
import modelo.Empleado;
import modelo.Escultura;
import modelo.Fotografia;
import modelo.Galeria;
import modelo.Impresiones;

import modelo.Oferta;

import modelo.Pieza;
import modelo.Pintura;
import modelo.Subasta;


import modelo.TipoPago;

import modelo.Usuario;
import modelo.Video;

public class mainEmpleado {
    private Galeria galeria;
    private Usuario usuario;

    public static void main(String[] args) {
        mainEmpleado app = new mainEmpleado();
        app.iniciarAplicacion();
    }

    private void iniciarAplicacion() {
        try {
            this.galeria = new Galeria();
            galeria.guardarDatos();
            this.usuario = logIn();
            galeria.guardarDatos();

            // Debugging: imprimir usuarios cargados
            System.out.println("Usuarios cargados:");
            for (Map.Entry<String, Usuario> entry : galeria.getUsuarios().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                System.out.println(entry.getValue().getLogin() + ":" + entry.getValue().getClave());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
}
    

    private Usuario logIn() throws Exception {
        System.out.println("Inicio de sesion");
        do {
            String login = input("Usuario: ");
            String password = input("Contrasenia: ").trim();

            if (galeria.getUsuarios().containsKey(login.toLowerCase())) {
                usuario = galeria.getUsuarios().get(login.toLowerCase());


                if (usuario.getClave().equals(password)){
                	if (usuario.getTipo().equals("Cajero")||usuario.getTipo().equals("Operador") || usuario.getTipo().equals("Empleado")) {
                    System.out.println("Inicio de sesion exitoso");
                    iniciarEmpleado();
                	} else {
                		System.out.println("el tipo de usuario no coinciden");
                	}
                } else {
                    System.out.println("La contraseña no coincide");
                }
            } else {
                System.out.println("El usuario no existe");
            }
        } while (this.usuario == null);
        return null;
    
    }

    public String input(String mensaje) {
        try {
            System.out.println(mensaje);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            while (input.isEmpty()) {
                System.out.println("Por favor, ingrese un valor.");
                input = reader.readLine();
            }
            return input;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void iniciarEmpleado() throws Exception {
        Empleado emp = (Empleado) this.usuario;
        int opcion;
        do {
            System.out.println("\n\n" + getMenuAdmin(null));
            opcion = obtenerOpcion();
            if (opcion == 1) {
                administrarPiezas(emp);
            } else if (opcion == 2) {
                administrarSubastas(emp);
            }  else if (opcion == 3) {
                verificarComprasPendientes(emp);
            } else if (opcion == 5) {
                administrarUsuarios(emp);
            } else if (opcion == 6) {
                System.out.println(getComprasHechas(emp));
            } else if (opcion == 7) {
                System.out.println(getPerfil(emp));
            }
        } while (opcion != 0);
    }

    private int obtenerOpcion() {
        String input = input("Opcion: ");
        while (input.isEmpty()) {
            System.out.println("Por favor, ingrese una opción válida.");
            input = input("Opcion: ");
        }
        return Integer.parseInt(input);
    }



    private String getMenuAdmin(Integer opcion) {
	        if (opcion == null) {
	            return "0. Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Compras pendientes\n5. Administrar usuarios\n6. Compras hechas\n7. Perfil\n";
	        } else if (opcion == 1) {
	            return "0. Volver\n1. Cambiar ubicacion de pieza\n2. Ver piezas de inventario\n3. Ver piezas vendidas\n4. Ver piezas devueltas\n5. Modificar pieza\n6.Ver historial de una pieza";
	        } else if (opcion == 2) {
	            return "0. Volver\n1. Ver detalles de la subasta\n2. Crear subasta\n3. Modificar subasta\n4. Cerrar subasta\n5. Remover pieza de subasta\n6. Verificar ofertas\n";
	        } else if (opcion == 5) {
	            return "n1. Modificar su usuario\n2.Ver historial de un autor";
	        }
	        return "0.Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Compras pendientes\n5. Administrar usuarios\n6. Compras hechas\n7. Perfil\n";
	    }
	    
    private void administrarPiezas(Empleado emp) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(1));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1) {
	                cambiarUbicacionPieza(emp);
	            } else if (opcion == 2) {
	                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
	            } else if (opcion == 3) {
	                System.out.println(getPiezasVendidas(emp));
	            } else if (opcion == 4) {
	                System.out.println(getPiezasRegresadas(emp));
	            } else if (opcion == 5) {
	                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
	                modificarPieza(emp);
	            } else if (opcion == 6) {
	            	String nombre = input("Seleccione la pieza de la que desea ver el historial: ").toLowerCase();
	            	historialPieza(emp, nombre);
	                
	            }
	            
	            
	        } while (opcion != 0);
	    }
	

    private void administrarSubastas(Empleado emp) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(2));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1) {
	                List<Subasta> subastas = emp.getGaleria().getSubastas();
	                System.out.println(getSubastas(subastas));
	                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
	                Subasta subasta = subastas.get(eleccion-1);
	                System.out.println(getDetallesSubasta(subasta));
	            } else if (opcion == 2) {
	                crearSubasta(emp);
	            } else if (opcion == 3) {
	                modificarSubasta(emp);
	            } else if (opcion == 4) {
	                cerrarSubasta(emp);
	            
	            } else if (opcion == 5) {
	                removerPiezaSubasta(emp);
	            } else if (opcion == 6) {
	                verificarOfertaSubasta(emp);
	            }
	        } while (opcion != 0);
	    }
	

    private void administrarUsuarios(Empleado emp) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(5));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1) {
	                modificarUsuario(emp);
	            } else if (opcion ==2) {
	            	String login = input("Ingrese el login del autor: ");
	            	historialAutor(emp, login);
	            }
	            
	        } while (opcion != 0);
	    }

    
    private void modificarPieza(Empleado emp) throws Exception {
        Integer id = Integer.parseInt(input("Ingrese el id de la pieza a modificar: "));
        Pieza pieza = galeria.getPiezaPorId(id);
        String titulo = pieza.getTitulo();
        Integer opcion = Integer.parseInt(input("El titulo actual es: " + titulo + "\nDesea cambiarlo?\n1. Si\n2. No"));
        titulo = opcion == 1 ? input("Nuevo titulo: ") : titulo;
        Integer anioCreacion = pieza.getAnioCreacion();
        opcion = Integer.parseInt(input("El anio de creacion actual es: " + anioCreacion + "\nDesea cambiarlo?\n1. Si\n2. No"));
        anioCreacion = opcion == 1 ? Integer.parseInt(input("Nuevo anio de creacion: ")) : anioCreacion;
        String lugarCreacion = pieza.getLugarCreacion();
        opcion = Integer.parseInt(input("El lugar de creacion actual es: " + lugarCreacion + "\nDesea cambiarlo?\n1. Si\n2. No"));
        lugarCreacion = opcion == 1 ? input("Nuevo lugar de creacion: ") : lugarCreacion;
        boolean disponible = pieza.isDisponible();
        opcion = Integer.parseInt(input("La disponibilidad actual es: " + disponible + "\nDesea cambiarla?\n1. Si\n2. No"));
        disponible = opcion == 1 ? !disponible : disponible;
        Integer valor = pieza.getValor();
        opcion = Integer.parseInt(input("El valor actual es: " + valor + "\nDesea cambiarlo?\n1. Si\n2. No"));
        valor = opcion == 1 ? Integer.parseInt(input("Nuevo valor: ")) : valor;
        Integer tiempoAcordado = pieza.getTiempoAcordado();
        opcion = Integer.parseInt(input("El tiempo acordado actual es: " + tiempoAcordado + "\nDesea cambiarlo?\n1. Si\n2. No"));
        tiempoAcordado = opcion == 1 ? Integer.parseInt(input("Nuevo tiempo acordado: ")) : tiempoAcordado;
        if (pieza.getTipoPieza().equals("Escultura")) {
        	
			@SuppressWarnings("null")
			Pieza nuevaPieza = new Escultura(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, (Boolean) null, null);
        	emp.modificarPieza(nuevaPieza);
        }else if (pieza.getTipoPieza().equals("Video")) {
        	Pieza nuevaPieza = new Video(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
        	emp.modificarPieza(nuevaPieza);
        }else if (pieza.getTipoPieza().equals("Pintura")) {
        	Pieza nuevaPieza = new Pintura(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
        	emp.modificarPieza(nuevaPieza);
        } else if (pieza.getTipoPieza().equals("Fotografia")) {
        	Pieza nuevaPieza = new Fotografia(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
        	emp.modificarPieza(nuevaPieza);
        } else {
        	Pieza nuevaPieza = new Impresiones(id, titulo, anioCreacion,lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null, null);
        	emp.modificarPieza(nuevaPieza);
        
        }
        	
    }
	        	
	   
	

    private void cambiarUbicacionPieza(Empleado emp) {
	    	
	    	String nombre = input("Ingrese el nombre de la pieza: ");
	       
	        
	        emp.cambiarUbicacionPieza(nombre);
	        	
	
	    }
	

    private String getPiezasEnExhibicion() {
	        String string = "Piezas en exhibicion:\n";
	        for (Pieza pieza:  galeria.getEnExhibicion().values()) {
	            string += Integer.toString(pieza.getId()) + "- " + pieza.getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private String getPiezasEnBodega() {
	        String string = "Piezas en bodega:\n";
	        for (Pieza pieza:  galeria.getEnBodega().values()) {
	            string += Integer.toString(pieza.getId()) + "- " + pieza.getTitulo() + "\n";
	        }
	        return string;
	    }
	

	

    private void verificarComprasPendientes(Empleado emp) throws Error {
	        List<Oferta> comprasPendientes = emp.getGaleria().getComprasPendientes();
	        System.out.println(getComprasPendientes(comprasPendientes));
	        Integer opcion = Integer.parseInt(input("Seleccione la compra a verificar: "));
	        Oferta oferta = comprasPendientes.get(opcion-1);
	        System.out.println("La pieza a comprar es: " + oferta.getPieza().getTitulo() + "\nValor: " + oferta.getValorOferta() + "\nLos datos del comprador son: " + oferta.getCliente().getNombre() + "\nBalance: " + oferta.getCliente().getBalance());
	        opcion = Integer.parseInt(input("Seleccione un tipo de pago:\n1. Efectivo\n2. Transferencia\n3. Tarjeta"));
	        TipoPago tipoPago = opcion == 1 ? TipoPago.EFECTIVO : opcion == 2 ? TipoPago.TRANSFERENCIA : TipoPago.TARJETA;
	        emp.verificarCompra(oferta, tipoPago);
	        System.out.println("Operacion exitosa");
	    }
	

    private String getComprasPendientes(List<Oferta> comprasPendientes) {
	        String string = "Compras pendientes:\n";
	        for (int i = 1; i <= comprasPendientes.size(); i++) {
	            Oferta oferta = comprasPendientes.get(i-1);
	            string += i + "- Pieza: " + oferta.getPieza().getTitulo() + " Comprador: " + oferta.getCliente().getNombre() + " Precio: " + oferta.getValorOferta() + "\n";
	        }
	        return string;
	    }
	

    private String getComprasHechas(Empleado emp) {
	        String string = "";
	        for(int i = 1; i <= emp.getGaleria().getCompras().size(); i++) {
	            Compra compra = emp.getGaleria().getCompras().get(i-1);
	            string += i + "- Pieza: " + compra.getPieza().getTitulo() + " Comprador: " + compra.getCliente().getNombre() + " Valor: " + compra.getValorPagado() + "\n";
	        }

	        if (string.equals("")){
	        	string = "No se ham realizado compras";
	        } else {
	        	return string;
	    }
			return string;
	        
    }
	


	


	

    private String getPiezasVendidas(Empleado emp) {
	        String string = "Piezas vendidas:\n";
	        for (int i = 1; i <= emp.getGaleria().getVendidas().size(); i++) {
	            string += Integer.toString(i) + "- " + emp.getGaleria().getVendidas().get(i-1).getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private String getPiezasRegresadas(Empleado emp) {
	        String string = "Piezas regresadas:\n";
	        for (int i = 1; i <= emp.getGaleria().getRegresadas().size(); i++) {
	            string += Integer.toString(i) + "- " + emp.getGaleria().getRegresadas().get(i-1).getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private void modificarUsuario(Empleado emp) throws Error {
	        String login = input("Ingrese el login del usuario: ");
	        Usuario usuario = emp.getUsuario(login);
	        String nombre = usuario.getNombre();
	        Integer opcion = Integer.parseInt(input("El nombre actual del usuario es: " + nombre + "\nDesea cambiarlo?\n1. Si\n2. No"));
	        nombre = opcion == 1 ? input("Nuevo nombre: ") : nombre;
	        String password = usuario.getClave();
	        opcion = Integer.parseInt(input("La constrasenia actual del usuario es: " + password + "\nDesea cambiarla?\n1. Si\n2. No"));
	        password = opcion == 1 ? input("Nueva contrasenia: ") : password;
	        String telefono = usuario.getTelefono();
	        opcion = Integer.parseInt(input("El telefono actual del usuario es: " + telefono + "\nDesea cambiarlo?\n1. Si\n2. No"));
	        telefono = opcion == 1 ? input("Nuevo telefono: ") : telefono;
	        String correo = usuario.getCorreoElectronico();
	        opcion = Integer.parseInt(input("El correo actual del usuario es: " + correo + "\nDesea cambiarlo?\n1. Si\n2. No"));
	        correo = opcion == 1 ? input("Nuevo correo: ") : correo;
	        Usuario nuevoUsuario = new Empleado("Empleado",nombre, login, password, telefono, correo, galeria);
	        emp.modificarInfoUsuario(nuevoUsuario);
	    }
	

	    

    private String getPerfil(Usuario usuario) {
	
	        return "Tipo Usuario: " + usuario.getTipo() + "\nNombre: " + usuario.getNombre() + "\nLogin: " + usuario.getLogin() + "\nTelefono: " + usuario.getTelefono() + "\nCorreo: " + usuario.getCorreoElectronico();
	   
	    }
	    

    private void modificarSubasta(Empleado emp) {
	        List<Subasta> subastas = emp.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta a modificar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        String nombre = subasta.getNombre();
	        nombre = Integer.parseInt(input("El nombre actual es: " + nombre + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? input("Nuevo nombre: ") : nombre;
	        boolean activa = subasta.isActiva();
	        activa = Integer.parseInt(input("El estado actual es: " + activa + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? !activa : activa;
	        Subasta nuevaSubasta = new Subasta(nombre, activa);
	        emp.modificarSubasta(subasta, nuevaSubasta);
	        System.out.println("Operacion exitosa");
	    }
	

    private void cerrarSubasta(Empleado emp) throws Exception {
	        List<Subasta> subastas = emp.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta a cerrar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        emp.cerrarSubasta(subasta);
	        System.out.println("Operacion exitosa");
	    }
	


	

    private void removerPiezaSubasta(Empleado emp) throws Exception {
	        List<Subasta> subastas = emp.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta de la que removera la pieza: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        opcion = Integer.parseInt(input("Seleccione la pieza que va a remover: "));
	        Pieza pieza = subasta.getPiezas().get(opcion-1);
	        emp.removerPiezaSubasta(subasta, pieza);
	        System.out.println("Operacion exitosa");
	    }
	    

    private String getSubastas(List<Subasta> subastas) {
	        String string = "Subastas:\n";
	        for (int i = 1; i <= subastas.size(); i++) {
	            Subasta subasta = subastas.get(i-1);
	            string += i + "- Nombre: " + subasta.getNombre() + " Activo: " + subasta.isActiva() + "\n";
	        }
	        return string;
	    }
	    

    private String getDetallesSubasta(Subasta subasta) {
	        String string = "Detalles de subasta:\n";
	        string += "Nombre: " + subasta.getNombre();
	        string += "\nActiva: " + subasta.isActiva();
	        string += "\nPiezas:\n";
	        List<Pieza> piezas = subasta.getPiezas();
	        for (int i = 1; i <= piezas.size(); i++) {
	            Pieza pieza = piezas.get(i-1);
	            Oferta mayorOferta = subasta.getMayorOferta(pieza);
	            String mayorOfertaStr = mayorOferta == null ? "ninguna" : Integer.toString(mayorOferta.getValorOferta());
	            string += i + "- Titulo: " + pieza.getTitulo() + " Anio creacion: " + pieza.getAnioCreacion() + " Valor: " + pieza.getValor() + " Mayor oferta: " + mayorOfertaStr + "\n"; 
	        }
	        string += "\nClientes verificados:\n";
	        List<Cliente> clientesVerificados = subasta.getVerificados();
	        for (int i = 1; i <= clientesVerificados.size(); i++) {
	            Cliente cliente = clientesVerificados.get(i-1);
	            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreoElectronico() + "\n"; 
	        }
	        string += "\nClientes en espera:\n";
	        List<Cliente> clientesEnEspera = subasta.getEnEspera();
	        for (int i = 1; i <= clientesEnEspera.size(); i++) {
	            Cliente cliente = clientesEnEspera.get(i-1);
	            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreoElectronico() + "\n"; 
	        }
	        return string;
	    }
	    

    private void crearSubasta(Empleado emp) {
	        String nombre = input("Ingrese el nombre de la subasta: ");
	        boolean activa = Integer.parseInt(input("Esta activa?\n1. Si\n2. No")) == 1 ? true : false;
	        emp.crearSubasta(new Subasta(nombre, activa));
	        System.out.println("Operacion exitosa");
	    }
	


	    

    private void verificarOfertaSubasta(Empleado emp) throws Exception {
	        List<Subasta> subastas = emp.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta en la que va a verificar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta) + "\n" + getOfertas(subasta));
	        opcion = Integer.parseInt(input("Seleccione la oferta que quiere verificar: "));
	        Oferta oferta = subasta.getPendientes().get(opcion-1);
	        emp.aceptarOferta(subasta, oferta);
	        System.out.println("Operacion exitosa");
	    }
	    

    private String getOfertas(Subasta subasta) {
	        String string = "Ofertas:\n";
	        List<Oferta> pendientes = subasta.getPendientes();
	        for (int i = 1; i <= pendientes.size(); i++) {
	            Oferta oferta = pendientes.get(i-1);
	            string += i + "- Pieza: " + oferta.getPieza().getTitulo() + " Cliente: " + oferta.getCliente().getNombre() + " Valor: " + oferta.getValorOferta() + "\n";
	        }
	        return string;
	    }
	    


	    
	   
    private void historialPieza(Empleado emp, String nombre) throws Exception {
	        String detalles = null;
	        List<String> compraPieza = new ArrayList<>();
	        
	        System.out.println("Historial de la pieza\n");
	        
	        
	        if (emp.getGaleria().getEnExhibicion().containsKey(nombre) || emp.getGaleria().getEnBodega().containsKey(nombre)) {
	        
		        for (Pieza Piezas : emp.getGaleria().getEnExhibicion().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : emp.getGaleria().getCompras()) {
		                    if (compras.getPieza().getTitulo().equals(nombre)) {
		                        compraPieza.add(compras.toString2());
		                    }
		                }
		                
		                System.out.println(detalles);
		                
		            }
		        }
		        
		        for (Pieza Piezas : emp.getGaleria().getEnBodega().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : emp.getGaleria().getCompras()) {
		                    if (compras.getPieza().getTitulo().equals(nombre)) {
		                        compraPieza.add(compras.toString2());
		                    }
		                }
		                
		                System.out.println(detalles);
		                
		            }
		        }
		        
		        
		        
		        if (compraPieza.size()!= 0) {
			        System.out.println("Historial de compra de la pieza\n");
			        
			        for (String elemento : compraPieza) {
			            System.out.println(elemento);
			        }
		        } else {
		        	System.out.println("Historial de compra de la pieza\n");
		        }
	        } else {
	        	System.out.println("La pieza no se ha encontrado");
	        }
	        
	    }
	
	    

    private void historialAutor(Empleado emp, String login) throws Error {
	   	 
	    	List<String> verificacionCompra = new ArrayList<>();
	    	
	   	if (emp.getGaleria().getUsuarios().containsKey(login)) {
	   		usuario = galeria.getUsuarios().get(login);
	   		Cliente cliente = (Cliente) usuario;
	   		List<Pieza> Piezas = cliente.getAutoriaVendidasCompradas();
	   		String respuesta = cliente.toStringPieza2(Piezas);
	   		
	
			System.out.println("Piezas del autor: \n");
			System.out.println(respuesta);
			
	   		for (Compra compras : emp.getGaleria().getCompras()) {
	   			for (int i = 0; i < Piezas.size(); i++) {
	   				if (compras.getPieza()== Piezas) {
	   					verificacionCompra.add(compras.toString4());
	   				}	
	   			
	   			}
	   			
	   		}
	   		
	   		if (verificacionCompra.size() != 0) {
	   			System.out.println("Historial de compra de sus piezas: \n");
	   			System.out.println(verificacionCompra);
	   		} else {
	   			System.out.println("Las piezas del autor no han sio compradas.");	
	   			}
	   			
	   			
	   			
	   		
	
	   } else {
			System.out.println("El autor solicitado no se ha encontrado. Intenta de nuevo");	
	   }
	    
	     
	}
	}
