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




import modelo.Usuario;
import modelo.Video;

public class mainAdmin {
    private Galeria galeria;
    private Usuario usuario;

    public static void main(String[] args) {
        mainAdmin app = new mainAdmin();
        app.iniciarAplicacion();
    }

    public void iniciarAplicacion() {
    	try {
            this.galeria = new Galeria();
            this.usuario = logIn();
            galeria.guardarDatos();
  
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

                if (usuario.getClave().equals(password) && usuario.getTipo().equals("Administrador")) {
                    System.out.println("Inicio de sesion exitoso");
                    iniciarAdmin();
                } else {
                    System.out.println("La contraseña o el tipo de usuario no coinciden");
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

    private void iniciarAdmin() throws Exception {
        Admin admin = (Admin) this.usuario;
        int opcion;
        do {
            System.out.println("\n\n" + getMenuAdmin(null));
            opcion = obtenerOpcion();
            if (opcion == 1) {
                administrarPiezas(admin);
            } else if (opcion == 2) {
                administrarSubastas(admin);
            } else if (opcion == 3) {
                verificarOfertasPendientes(admin);
            }  else if (opcion == 4) {
                administrarUsuarios(admin);
            } else if (opcion == 5) {
                System.out.println(getComprasHechas(admin));
            } else if (opcion == 6) {
                System.out.println(getPerfil(admin));
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
	            return "0. Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Administrar usuarios\n5. Compras hechas\n6. Perfil\n";
	        } else if (opcion == 1) {
	            return "0. Volver\n1. Agregar pieza a exhibicion\n2. Agregar pieza a bodega\n3. Cambiar ubicacion de pieza\n4. Ver piezas de inventario\n5. Devoluciones pendientes\n6. Ver piezas vendidas\n7. Ver piezas devueltas\n8. Modificar pieza\n9.Ver historial de una pieza";
	        } else if (opcion == 2) {
	            return "0. Volver\n1. Ver detalles de la subasta\n2. Crear subasta\n3. Modificar subasta\n4. Cerrar subasta\n5. Agregar pieza a subasta\n6. Remover pieza de subasta\n7. Verificar clientes.\n";
	        } else if (opcion == 5) {
	            return "0. Volver\n1. Crear usuario\n2. Modificar usuario\n3. Ver usuarios\n4.Ver historial de un comprador\n5.Ver historial de un autor";
	        }
	        return "0.Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Administrar usuarios\n5. Compras hechas\n6. Perfil\n";
	    }
	    
    private void administrarPiezas(Admin Admin) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(1));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1 || opcion == 2) {
	                Pieza pieza = formularioPieza(Admin);
	                Admin.agregarPieza(pieza, opcion);
	                List <Cliente> listaAt = pieza.getAutores();
	                Admin.agregarPiezaAt(pieza, listaAt);
	            } else if (opcion == 3) {
	                cambiarUbicacionPieza(Admin);
	            } else if (opcion == 4) {
	                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
	            } else if (opcion == 5) {
	                devolucionesPendientes(Admin);
	            } else if (opcion == 6) {
	                System.out.println(getPiezasVendidas(Admin));
	            } else if (opcion == 7) {
	                System.out.println(getPiezasRegresadas(Admin));
	            } else if (opcion == 8) {
	                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
	                modificarPieza(Admin);
	            } else if (opcion == 9) {
	            	String nombre = input("Seleccione la pieza de la que desea ver el historial: ").toLowerCase();
	            	historialPieza(Admin, nombre);
	            }
	            
	            
	        } while (opcion != 0);
	    }
	

    private void administrarSubastas(Admin admin) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(2));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1) {
	                List<Subasta> subastas = admin.getGaleria().getSubastas();
	                System.out.println(getSubastas(subastas));
	                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
	                Subasta subasta = subastas.get(eleccion-1);
	                System.out.println(getDetallesSubasta(subasta));
	            } else if (opcion == 2) {
	                crearSubasta(admin);
	            } else if (opcion == 3) {
	                modificarSubasta(admin);
	            } else if (opcion == 4) {
	                cerrarSubasta(admin);
	            } else if (opcion == 5) {
	                agregarPiezaSubasta(admin);
	            } else if (opcion == 6) {
	                removerPiezaSubasta(admin);
	            } else if (opcion == 7) {
	                verificarClientes(admin);
	            }
	        } while (opcion != 0);
	    }
	

    private void administrarUsuarios(Admin admin) throws Exception {
	        int opcion;
	        do {
	            System.out.println("\n\n" + getMenuAdmin(5));
	            opcion = Integer.parseInt(input("Opcion: "));
	            if (opcion == 1) {
	                crearUsuario(admin);
	            } else if (opcion == 2) {
	                modificarUsuario(admin);
	            } else if (opcion == 3) {
	                System.out.println(getUsuarios(admin));
	            } else if (opcion ==4) {
	            	String login = input("Ingrese el login del comprador: ");
	            	historialComprador(admin, login);
	            } else if (opcion ==5) {
	            	String login = input("Ingrese el login del autor: ");
	            	historialAutor(admin, login);
	            }
	            
	        } while (opcion != 0);
	    }
	
    private Pieza formularioPieza(Admin admin) throws Error {
        String titulo = input("Ingrese el título de la pieza: ");
        Integer anioCreacion = Integer.parseInt(input("Ingrese el anio de creacion de la pieza: "));
        String lugarCreacion = input("Ingrese el lugar de creacion de la pieza: ");
        String tipoPieza = "";
        Integer opcion = Integer.parseInt(input("Tipo pieza:\n1. Escultura\n2. Fotografia\n3. Impresion\n4. Pintura\n5. Video"));
        if (opcion == 1) {
            tipoPieza = "Escultura";
        } else if (opcion == 2) {
            tipoPieza = "Fotografia";
        } else if (opcion == 3) {
            tipoPieza = "Impresiones";
        } else if (opcion == 4) {
            tipoPieza = "Pintura";
        } else if (opcion == 5) {
            tipoPieza = "Video";
        }
        
        opcion = Integer.parseInt(input("Pieza disponible?\n1. Si\n2. No"));
        boolean disponible = opcion == 1 ? true : false;
        Integer valor = Integer.parseInt(input("Ingrese el valor de la pieza: "));
        Integer tiempoAcordado = Integer.parseInt(input("Tiempo acordado en dias: "));
        String modalidad = "Consignacion";
        Pieza pieza = null;
        
        if (tipoPieza.equals("Escultura")) {
        	
        	List<String> materiales1 =  new ArrayList<>();
            System.out.println("Materiales de construccion:");
            String mat = input("\nIngrese los materiales de la escultura separados por comas: ");
                
            String[] array1 = mat.split(",");
                
            for (String material : array1) {
                materiales1.add(material);
            }
            
            String dimensiones = input("Ingrese las dimensiones de la pieza: ");
            Integer peso = Integer.parseInt(input("Ingrese el peso de la pieza: "));
            opcion = Integer.parseInt(input("Requiere de electricidad?\n1. Si\n2. No"));
            boolean electricidad = opcion == 1 ? true : false;
            String detalles = input("Ingrese detalles a tener en cuenta: ");
            
            Escultura escultura  = new Escultura(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipoPieza,  disponible, valor, tiempoAcordado, modalidad, dimensiones,materiales1, peso, electricidad, detalles);

            
            pieza = escultura;
            
        } else if (tipoPieza.equals("Video")){
            Integer duracion = Integer.parseInt(input("Ingrese la duracion del video: "));
            String resolucion = input("Ingrese la resolucion del video");
            Float peso = Float.parseFloat(input("Infrese el peso del video"));
            String categoria = input("Ingrese la categoria del video");
            new Video(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor, tiempoAcordado, modalidad, duracion, resolucion, peso, categoria);
       
        }  else if (tipoPieza.equals("Pintura")) {
            String dimensiones = input("Ingrese las dimensiones de la pintura");
            
            List<String> materiales1 =  new ArrayList<>();
            System.out.println("Materiales de construccion:");
            String mat = input("\nIngrese los materiales de la pintura separados por comas: ");
                
            String[] array1 = mat.split(",");
                
            for (String material : array1) {
                materiales1.add(material);
            }
                        
            String cuidados = input("Ingrese los cuidados que se deben tener con la pintura: ");
            String tecnica = input("Ingrese la tecnica usada en la pintura");
            
            pieza = new Pintura(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor, tiempoAcordado, modalidad, dimensiones, materiales1, cuidados, tecnica);
            
        } else if (tipoPieza.equals("Impresiones")) {
            String dimensiones = input("Ingrese las dimensiones de la impresion");
            String formato = input("Ingrese el formato de la impresion");
            String material = input("Ingrese el material de la impresion");
            String resolucion = input("Ingrese la resolucion de la impresion");
            String acabado = input("Ingrese el acabado de la impresion");
            
            pieza = new Impresiones(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor, tiempoAcordado, modalidad, dimensiones, formato, material, resolucion, acabado);
        } else {
            String formato = input("Ingrese el formato de la fotografia: ");
            String resolucion = input("Ingrese la resolucion de la fotografia");
            String dimensiones = input("Ingrese las dimensiones de la fotografia");
            String categoria = input("Ingrese la categoria de la fotografia");
            pieza = new Fotografia(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor, tiempoAcordado, modalidad, formato,  resolucion, dimensiones, categoria);
        }
      
        boolean trabajando;
        do {
            String login = input("Ingrese el login del autor: ");
            Cliente cliente;
            if (!galeria.getUsuarios().containsKey(login)) {
                System.out.println("El usuario no existe\nCreando autor...");
                cliente = (Cliente) crearUsuario(admin);
            } else {
                cliente = (Cliente) galeria.getUsuarios().get(login);
            }
            pieza.agregarAutor(cliente);
            cliente.getAutoriaVendidasCompradas().add(pieza);
            trabajando = Integer.parseInt(input("Desea agregar otro autor?\n1. Si\n2. No")) == 1 ? true : false;
        } while (trabajando);
        String login = input("Ingrese el login del propietario: ");
        Cliente cliente;
        if (!galeria.getUsuarios().containsKey(login)) {
            System.out.println("El usuario no existe\nCreando propietario...");
            cliente = (Cliente) crearUsuario(admin);
        } else {
            cliente = (Cliente) galeria.getUsuarios().get(login);
        }
        cliente.getPrestadasEnEspera().add(pieza);
        return pieza;
    }
    

   
	    

    private void modificarPieza(Admin admin) throws Exception {
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
	        	admin.modificarPieza(nuevaPieza);
	        }else if (pieza.getTipoPieza().equals("Video")) {
	        	Pieza nuevaPieza = new Video(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
	        	admin.modificarPieza(nuevaPieza);
	        }else if (pieza.getTipoPieza().equals("Pintura")) {
	        	Pieza nuevaPieza = new Pintura(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
	        	admin.modificarPieza(nuevaPieza);
	        } else if (pieza.getTipoPieza().equals("Fotografia")) {
	        	Pieza nuevaPieza = new Fotografia(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null);
	        	admin.modificarPieza(nuevaPieza);
	        } else {
	        	Pieza nuevaPieza = new Impresiones(id, titulo, anioCreacion,lugarCreacion, null, disponible, valor, tiempoAcordado, null, null, null, null, null, null);
	        	admin.modificarPieza(nuevaPieza);
	        
	        }
	        	
	    }
	

    private void cambiarUbicacionPieza(Admin admin) {
	    	
	    	String nombre = input("Ingrese el nombre de la pieza: ");
	        
	        
	        admin.cambiarUbicacionPieza(nombre);
	        	
	
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
	

    private void verificarOfertasPendientes(Admin admin) throws Error {
	        List<Oferta> ofertasPendientes = admin.getGaleria().getOfertasPendientes();
	        System.out.println(getOfertasPendientes(ofertasPendientes));
	        Integer opcion = Integer.parseInt(input("Seleccione la oferta a verificar: "));
	        Oferta oferta = ofertasPendientes.get(opcion-1);
	        System.out.println("La pieza a comprar es: " + oferta.getPieza().getTitulo() + "\nValor: " + oferta.getValorOferta() + "\nLos datos del comprador son: " + oferta.getCliente().getNombre() + "\nBalance: " + oferta.getCliente().getBalance());
	        opcion = Integer.parseInt(input("Desea modificar el balance actual?\n1. Si\n2. No"));
	        Integer balance = opcion == 1 ? Integer.parseInt(input("Nuevo balance: ")) : oferta.getCliente().getBalance();
	        boolean aceptada = balance < oferta.getValorOferta() ? false : Integer.parseInt(input("Acepta la oferta?\n1. Si\n2. No")) == 1 ? true : false;
	        admin.verificarOfertaDeCompra(oferta, balance, aceptada);
	        System.out.println("Operacion exitosa");
	    }
	

    private String getOfertasPendientes(List<Oferta> ofertasPendientes) {
	        String string = "Ofertas pendientes:\n";
	        for (int i = 1; i <= ofertasPendientes.size(); i++) {
	            Oferta oferta = ofertasPendientes.get(i-1);
	            string += i + "- Pieza: " + oferta.getPieza().getTitulo() + " Comprador: " + oferta.getCliente().getNombre() + " Precio: " + oferta.getValorOferta() + "\n";
	        }
	        return string;
	    }
	

	

    private String getComprasHechas(Admin admin) {
	        String string = "";
	        for(int i = 1; i <= admin.getGaleria().getCompras().size(); i++) {
	            Compra compra = admin.getGaleria().getCompras().get(i-1);
	            string += i + "- Pieza: " + compra.getPieza().getTitulo() + " Comprador: " + compra.getCliente().getNombre() + " Valor: " + compra.getValorPagado() + "\n";
	        }
	        return string;
	    }
	

    private void devolucionesPendientes(Admin admin) throws Error {
	        System.out.println("0. Volver\n"); 
	        List<Pieza> piezasVencidas = admin.getPiezasVencidasLimiteTiempo();
	        System.out.println(getPiezasVencidas(piezasVencidas));
	        Integer opcion = Integer.parseInt(input("Seleccione la pieza a devolver:"));
	        if (opcion > 0) {
	        	admin.confirmarDevolucion(piezasVencidas, opcion-1);
	            System.out.println("Operacion exitosa");
	        }
	    }
	

    private String getPiezasVencidas(List<Pieza> piezasVencidas) {
	        String string = "Piezas vencidas:\n";
	        for (int i = 1; i <= piezasVencidas.size(); i++) {
	            string += Integer.toString(i) + "- " + piezasVencidas.get(i-1).getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private String getPiezasVendidas(Admin admin) {
	        String string = "Piezas vendidas:\n";
	        for (int i = 1; i <= admin.getGaleria().getVendidas().size(); i++) {
	            string += Integer.toString(i) + "- " + admin.getGaleria().getVendidas().get(i-1).getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private String getPiezasRegresadas(Admin admin) {
	        String string = "Piezas regresadas:\n";
	        for (int i = 1; i <= admin.getGaleria().getRegresadas().size(); i++) {
	            string += Integer.toString(i) + "- " + admin.getGaleria().getRegresadas().get(i-1).getTitulo() + "\n";
	        }
	        return string;
	    }
	

    private Usuario crearUsuario(Admin admin) throws Error {
	    	String tipo = input("Ingrese el tipo de usuario: ");
	    	String nombre = input("Ingrese el nombre del usuario: ");
	        String login = input("Ingrese el login del usuario: ");
	        String password = input("Ingrese el password del usuario: ");
	        String telefono = input("Ingrese el telefono del usuario: ");
	        String correo = input("Ingrese el correo del usuario: ");
	        Usuario usuario = null;
	        
	        if (tipo.equals("Autor") || tipo.equals("Cliente"))  {
	            usuario = new Cliente(tipo,nombre, login, password, telefono, correo, 0, galeria);
	       
	            
	            
	        } else {   
	        	usuario = new Empleado(tipo,nombre, login, password, telefono, correo, galeria);
	        }
	        if (usuario != null) {
	        	admin.agregarUsuario(usuario);
	        }
	        return usuario;
	    }

    

	

    private void modificarUsuario(Admin admin) throws Error {
	        String login = input("Ingrese el login del usuario: ");
	        Usuario usuario = admin.getUsuario(login);
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
	        Usuario nuevoUsuario = new Admin(nombre, login, password, telefono, correo, null, galeria);
	        admin.modificarInfoUsuario(nuevoUsuario);
	    }
	

    private String getUsuarios(Admin admin) {
	        String string = "Usuarios:\n";
	        for (Usuario usuario : admin.getGaleria().getUsuarios().values()) {
	            string += "- " + usuario.getNombre() + " " + usuario.getLogin() + " " + usuario.getTelefono() + "\n";
	        }
	        return string;
	    }
	    

    private String getPerfil(Usuario usuario) {
	
	        return "Tipo Usuario: " + usuario.getTipo() + "\nNombre: " + usuario.getNombre() + "\nLogin: " + usuario.getLogin() + "\nTelefono: " + usuario.getTelefono() + "\nCorreo: " + usuario.getCorreoElectronico();
	   
	    }
	    

    private void modificarSubasta(Admin admin) {
	        List<Subasta> subastas = admin.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta a modificar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        String nombre = subasta.getNombre();
	        nombre = Integer.parseInt(input("El nombre actual es: " + nombre + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? input("Nuevo nombre: ") : nombre;
	        boolean activa = subasta.isActiva();
	        activa = Integer.parseInt(input("El estado actual es: " + activa + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? !activa : activa;
	        Subasta nuevaSubasta = new Subasta(nombre, activa);
	        admin.modificarSubasta(subasta, nuevaSubasta);
	        System.out.println("Operacion exitosa");
	    }
	

    private void cerrarSubasta(Admin admin) throws Exception {
	        List<Subasta> subastas = admin.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta a cerrar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        admin.cerrarSubasta(subasta);
	        System.out.println("Operacion exitosa");
	    }
	

    private void agregarPiezaSubasta(Admin admin) throws Exception {
	        List<Subasta> subastas = admin.getGaleria().getSubastas();
	        
	        List<Pieza> enExhibicionL = new ArrayList<>();
	        List<Pieza> enBodegaL = new ArrayList<>();
	        
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta a la que agregara la pieza: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta) + "\n\nInventario de piezas:\n");
	        Map<String, Pieza> enExhibicion = admin.getGaleria().getEnExhibicion();
	        Map<String, Pieza> enBodega = admin.getGaleria().getEnBodega();
	        
	        for (Pieza pieza: enExhibicion.values()){
	        	enExhibicionL.add(pieza);	
	        }
	        
	        for (Pieza pieza: enBodega.values()){
	        	enBodegaL.add(pieza);
	        }
	        
	        System.out.println("Piezas en exhibicion:\n" + getListaPiezas(enExhibicionL));
	        System.out.println("\nPiezas en bodega:\n" + getListaPiezas(enBodegaL));
	        opcion = Integer.parseInt(input("Seleccione la ubicacion de la pieza:\n1. En exhibicion\n2. En bodega"));
	        Pieza pieza;
	        if (opcion == 1) {
	            pieza = enExhibicionL.get(Integer.parseInt(input("Seleccione la pieza a agregar: "))-1);
	        
	        
	        } else {
	            pieza = enBodegaL.get(Integer.parseInt(input("Seleccione la pieza a agregar: "))-1);
	        }
	        Integer valorMinimo = Integer.parseInt(input("Ingrese el valor minimo de la pieza: "));
	        String tipo = pieza.getTipoPieza();
	        
	        if (tipo.equals("Escultura")){
	        	
	        	Pieza piezaModificada = new Escultura(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null, null, null, null, null, null,(Boolean) null, null);
	        	admin.agregarPiezaSubasta(subasta, piezaModificada);
	        	
	        } else if (tipo.equals("Fotografia")){
	        	
	        	Pieza piezaModificada = new Fotografia(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null,null,null, null, null, null, null);
	        	admin.agregarPiezaSubasta(subasta, piezaModificada);
	        	
	        } else if (tipo.equals("Pintura")){
	        	
	        	Pieza piezaModificada = new Pintura(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null, null, null, null, null, null, null);
	        	admin.agregarPiezaSubasta(subasta, piezaModificada);
	        	
	        } if (tipo.equals("Impresiones")){
	        	
	        	Pieza piezaModificada = new Impresiones(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null, null, null, null, null, null, null, null);
	        	admin.agregarPiezaSubasta(subasta, piezaModificada);
	        }
	        
	        else {
	        	
	        	Pieza piezaModificada = new Video(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null,null,null, null, null, null, null);
	        	admin.agregarPiezaSubasta(subasta, piezaModificada);
	        }
	        
	
	        System.out.println("Operacion exitosa");
	    }
	

    private void removerPiezaSubasta(Admin admin) throws Exception {
	        List<Subasta> subastas = admin.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta de la que removera la pieza: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        opcion = Integer.parseInt(input("Seleccione la pieza que va a remover: "));
	        Pieza pieza = subasta.getPiezas().get(opcion-1);
	        admin.removerPiezaSubasta(subasta, pieza);
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
	    

    private void crearSubasta(Admin admin) {
	        String nombre = input("Ingrese el nombre de la subasta: ");
	        boolean activa = Integer.parseInt(input("Esta activa?\n1. Si\n2. No")) == 1 ? true : false;
	        admin.crearSubasta(new Subasta(nombre, activa));
	        System.out.println("Operacion exitosa");
	    }
	

    private void verificarClientes(Admin admin) throws Exception {
	        List<Subasta> subastas = admin.getGaleria().getSubastas();
	        System.out.println(getSubastas(subastas));
	        Integer opcion = Integer.parseInt(input("Seleccione la subasta en la que va a verificar: "));
	        Subasta subasta = subastas.get(opcion-1);
	        System.out.println(getDetallesSubasta(subasta));
	        opcion = Integer.parseInt(input("Seleccione el usuario que quiere verificar: "));
	        Cliente cliente = subasta.getEnEspera().get(opcion-1);
	        admin.verificarUsuarioDeSubasta(subasta, cliente);
	        System.out.println("Operacion exitosa");
	    }
	    


	    



    private String getListaPiezas(List<Pieza> listaPiezas) {
	        String string = "";
	        for (int i = 1; i <= listaPiezas.size(); i++) {
	            Pieza pieza = listaPiezas.get(i-1);
	            string += i + "- Titulo: " + pieza.getTitulo() + " Precio: " + pieza.getValor() + "\n";
	        }
	        return string;
	    }
	    
	    

    private void historialComprador(Admin admin, String login) throws Error {
	    	 String string = null;
	    	 Integer precio = 0;
	    	 
	    	if (admin.getGaleria().getUsuarios().containsKey(login)) {
	    		usuario = galeria.getUsuarios().get(login);
	    		Cliente cliente = (Cliente) usuario;
	    		List<Pieza> Piezas = cliente.getAutoriaVendidasCompradas();
	    		
	    		String respuesta = cliente.toStringPieza(Piezas);
	    		
	    		System.out.println("Piezas de la propiedad del comprador: \n");
	    		System.out.println(respuesta);
	    		
		       for (Compra compras : admin.getGaleria().getCompras()) {
		            	if (compras.getLogin()== login) {
		            		string = compras.toString3();
		            		
		            	for (int i = 0; i < Piezas.size(); i++) {
		            		if (Piezas.get(i) == compras.getPieza()) {
		            			precio += compras.getValorPagado();
		            		}
		            	}   
		            	   
		            }
		            
		        }          
		       
		       System.out.println("Piezas que fueron propiedad del comprador: \n");
		       System.out.println(string);
				
		    	
		       System.out.println("Valor de la coleccion del comprador es:" + Integer.toString(precio) + " pesos");
	    	
	    	} else {
	    		System.out.println("El comprador solicitado no se ha encontrado. Intenta de nuevo");
	    	}
	    	
	    	
	
	    }
	    
	    

    private void historialPieza(Admin admin, String nombre) throws Exception {
	        String detalles = null;
	        List<String> compraPieza = new ArrayList<>();
	        
	        System.out.println("Historial de la pieza\n");
	        
	        
	        if (admin.getGaleria().getEnExhibicion().containsKey(nombre) || admin.getGaleria().getEnBodega().containsKey(nombre)) {
	        
		        for (Pieza Piezas : admin.getGaleria().getEnExhibicion().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : admin.getGaleria().getCompras()) {
		                    if (compras.getPieza().getTitulo().equals(nombre)) {
		                        compraPieza.add(compras.toString2());
		                    }
		                }
		                
		                System.out.println(detalles);
		                
		            }
		        }
		        
		        for (Pieza Piezas : admin.getGaleria().getEnBodega().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : admin.getGaleria().getCompras()) {
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
		        	System.out.println("La pieza no ha sifo comprada\n");
		        }
	        } else {
	        	System.out.println("La pieza no se ha encontrado");
	        }
	        
	    }
	
	    

    private void historialAutor(Admin admin, String login) throws Error {
	   	 
	    	List<String> verificacionCompra = new ArrayList<>();
	    	
	   	if (admin.getGaleria().getUsuarios().containsKey(login)) {
	   		usuario = galeria.getUsuarios().get(login);
	   		Cliente cliente = (Cliente) usuario;
	   		List<Pieza> Piezas = cliente.getAutoriaVendidasCompradas();
	   		
	   		
	   		
	   		String respuesta = cliente.toStringPieza2(Piezas);
	   		
	   		
	
			System.out.println("Piezas del autor: \n");
			System.out.println(respuesta);
			
			
			for (Compra compra : admin.getGaleria().getCompras()) {
			    Pieza piezaCompra = compra.getPieza();
			    if (Piezas.contains(piezaCompra)) {
			        verificacionCompra.add(compra.toString4());
			    }
			}

	   		
	   		if (verificacionCompra.size() != 0) {
	   			System.out.println("Historial de compra de sus piezas: \n");
	   			System.out.println(verificacionCompra);
	   		} else {
	   			System.out.println("Las piezas del autor no han sido compradas.");	
	   			}
	   			
	   			
	   			
	   		
	
	   } else {
			System.out.println("El autor solicitado no se ha encontrado. Intenta de nuevo");	
	   }
	    
	     
	}
	}
