package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import excepciones.OperacionIlegal;
import modelo.Cliente;
import modelo.Compra;
import modelo.Empleado;
import modelo.Escultura;
import modelo.Fotografia;
import modelo.Galeria;
import modelo.MaterialConstruccion;
import modelo.Oferta;
import modelo.Otro;
import modelo.Pieza;
import modelo.Pintura;
import modelo.Subasta;
import modelo.TipoPago;
import modelo.Usuario;

public class AplicacionConsola {
    private Galeria galeria;
    private Usuario usuario;

    public void iniciarAplicacion() {
        try {
            this.galeria = new Galeria();
            this.usuario = logIn();
            if (usuario instanceof Empleado) {
                iniciarAplicacionEmpleado();
            } else {
                Cliente cliente = (Cliente) usuario;
                if (cliente.getTipo().equals("Autor")) {
                    iniciarAplicacionAutor(cliente);
                } else if (cliente.getTipo().equals("Comprador")) {
                    iniciarAplicacionComprador(cliente);
                } else {
                    iniciarAplicacionPropietario(cliente);
                }
            }
            galeria.guardarDatos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Usuario logIn() {
        Usuario usuario = null;
        System.out.println("Inicio de sesion");
        do {
            String login = input("Usuario: ").toLowerCase();
            String password = input("Constrasenia: ");
            if (galeria.getUsuarios().containsKey(login)) {
                usuario = galeria.getUsuarios().get(login);
                if (usuario.getPassword().equals(password)) {
                    System.out.println("Inicio de sesion exitoso");
                    return usuario;
                }
                System.out.println("Contrasenia incorrecta");
            } else {
                System.out.println("El usuario no existe");
            }
        } while (this.usuario == null);
        return null;
    }
    
    private void iniciarAplicacionEmpleado() throws Exception {
        Empleado empleado = (Empleado) this.usuario;
        int opcion;
        do {
            System.out.println("\n\n" + getMenuEmpleado(null));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                administrarPiezas(empleado);
            } else if (opcion == 2) {
                administrarSubastas(empleado);
            } else if (opcion == 3) {
                verificarOfertasPendientes(empleado);
            } else if (opcion == 4) {
                verificarComprasPendientes(empleado);
            } else if (opcion == 5) {
                administrarUsuarios(empleado);
            } else if (opcion == 6) {
                System.out.println(getComprasHechas(empleado));
            } else if (opcion == 7) {
                System.out.println(getPerfil(empleado));
            }
        } while (opcion != 0);
    }

    private String getMenuEmpleado(Integer opcion) {
        if (opcion == null) {
            return "0. Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Compras pendientes\n5. Administrar usuarios\n6. Compras hechas\n7. Perfil\n";
        } else if (opcion == 1) {
            return "0. Volver\n1. Agregar pieza a exhibicion\n2. Agregar pieza a bodega\n3. Cambiar ubicacion de pieza\n4. Ver piezas de inventario\n5. Devoluciones pendientes\n6. Ver piezas vendidas\n7. Ver piezas devueltas\n8. Modificar pieza\n";
        } else if (opcion == 2) {
            return "0. Volver\n1. Ver detalles de la subasta\n2. Crear subasta\n3. Modificar subasta\n4. Cerrar subasta\n5. Agregar pieza a subasta\n6. Remover pieza de subasta\n7. Verificar clientes\n8. Verificar ofertas\n";
        } else if (opcion == 5) {
            return "0. Volver\n1. Crear usuario\n2. Modificar usuario\n3. Ver usuarios";
        }
        return "0.Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Ofertas pendientes\n4. Compras pendientes\n5. Administrar usuarios\n6. Compras hechas\n7. Perfil\n";
    }
    
    private void administrarPiezas(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuEmpleado(1));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1 || opcion == 2) {
                Pieza pieza = formularioPieza(empleado);
                empleado.agregarPieza(pieza, opcion);
            } else if (opcion == 3) {
                cambiarUbicacionPieza(empleado);
            } else if (opcion == 4) {
                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
            } else if (opcion == 5) {
                devolucionesPendientes(empleado);
            } else if (opcion == 6) {
                System.out.println(getPiezasVendidas(empleado));
            } else if (opcion == 7) {
                System.out.println(getPiezasRegresadas(empleado));
            } else if (opcion == 8) {
                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
                modificarPieza(empleado);
            }
        } while (opcion != 0);
    }

    private void administrarSubastas(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuEmpleado(2));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                List<Subasta> subastas = empleado.getGaleria().getSubastas();
                System.out.println(getSubastas(subastas));
                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
                Subasta subasta = subastas.get(eleccion-1);
                System.out.println(getDetallesSubasta(subasta));
            } else if (opcion == 2) {
                crearSubasta(empleado);
            } else if (opcion == 3) {
                modificarSubasta(empleado);
            } else if (opcion == 4) {
                cerrarSubasta(empleado);
            } else if (opcion == 5) {
                agregarPiezaSubasta(empleado);
            } else if (opcion == 6) {
                removerPiezaSubasta(empleado);
            } else if (opcion == 7) {
                verificarClientes(empleado);
            } else if (opcion == 8) {
                verificarOfertaSubasta(empleado);
            }
        } while (opcion != 0);
    }

    private void administrarUsuarios(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuEmpleado(5));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                crearUsuario(empleado);
            } else if (opcion == 2) {
                modificarUsuario(empleado);
            } else if (opcion == 3) {
                System.out.println(getUsuarios(empleado));
            } 
        } while (opcion != 0);
    }

    private Pieza formularioPieza(Empleado administrador) throws OperacionIlegal {
        String titulo = input("Ingrese el título de la pieza: ");
        Integer anioCreacion = Integer.parseInt(input("Ingrese el anio de creacion de la pieza: "));
        String lugarCreacion = input("Ingrese el lugar de creacion de la pieza: ");
        Integer opcion = Integer.parseInt(input("Tipo pieza:\n1. Escultura\n2. Pintura\n3. Fotografia"));
        String tipo = "";
        if (opcion==1) {
        	tipo = "Escultura";
        } else if (opcion == 2) {
        	tipo = "Pintura";
        } else {
        	tipo = "Fotografia";
        }
        opcion = Integer.parseInt(input("Pieza disponible?\n1. Si\n2. No"));
        boolean disponible = opcion == 1 ? true : false;
        Integer valor = Integer.parseInt(input("Ingrese el valor de la pieza: "));
        Integer tiempoAcordado = Integer.parseInt(input("Tiempo acordado en dias: "));
        String modalidad = "Consignacion";
        Pieza pieza;
        if (tipo.equals("Escultura")) {
            String dimensiones = input("Ingresa las dimensiones");
            Integer peso = Integer.parseInt(input("Ingrese el peso de la pieza: "));
            opcion = Integer.parseInt(input("Requiere de electricidad?\n1. Si\n2. No"));
            boolean electricidad = opcion == 1 ? true : false;
            String detalles = input("Ingrese detalles a tener en cuenta: ");
            Escultura escultura = new Escultura(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad, dimensiones, peso, electricidad, detalles);
            System.out.println("Materiales de construccion:");
            boolean trabajando;
            do {
                escultura.getMateriales().add(new MaterialConstruccion(input("Ingrese el material: ")));
                trabajando = Integer.parseInt(input("Agregar otro material?\n1. Si\n2. No")) == 1 ? true : false;
            } while (trabajando); 
            pieza = escultura;
            
        	}else if (tipo.equals("Pintura")) {
                String dimensiones = input("Ingresa las dimensiones: ");
            	String cuidados = input("Ingresa los cuidados: ");
            	String tecnica = input("Ingresa la tecnica utilizada: ");
                Pintura pintura = new Pintura(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad, dimensiones, cuidados, tecnica);
                System.out.println("Materiales de construccion:");
                boolean trabajando;
                do {
                    pintura.getMateriales().add(new MaterialConstruccion(input("Ingrese el material: ")));
                    trabajando = Integer.parseInt(input("Agregar otro material?\n1. Si\n2. No")) == 1 ? true : false;
                } while (trabajando); 
                pieza = pintura;
            
            
        } else {
        	
            String dimensiones = input("Ingrese las dimensiones: ");
        	String resolucion = input("Ingrese la resolucion: ");
        	String formato = input("Ingrese el formato: ");
        	String categoria = input("Ingrese la categoria: ");
            pieza = new Fotografia(galeria.getSecuenciaPiezas(), titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad, dimensiones,resolucion, formato, categoria);
        }
        boolean trabajando;
        do {
            String login = input("Ingrese el login del autor: ");
            Cliente cliente;
            if (!galeria.getUsuarios().containsKey(login)) {
                System.out.println("El usuario no existe\nCreando autor...");
                cliente = (Cliente) crearUsuario(administrador);
            } else {
                cliente = (Cliente) galeria.getUsuarios().get(login);
            }
            pieza.agregarAutor(cliente);
            cliente.getAutoriaVendidasCompradas().add(pieza);
            trabajando = Integer.parseInt(input("Desea agregar otro autor?\n1. Si\n2. No")) == 1 ? true : false;
        } while (trabajando);
        String login = input("Ingrese el login del propietario: ").toLowerCase() + "-propietario";
        Cliente cliente;
        if (!galeria.getUsuarios().containsKey(login)) {
            System.out.println("El usuario no existe\nCreando propietario...");
            cliente = (Cliente) crearUsuario(administrador);
        } else {
            cliente = (Cliente) galeria.getUsuarios().get(login);
        }
        cliente.getPrestadasEnEspera().add(pieza);
        return pieza;
    }

    private void modificarPieza(Empleado empleado) throws Exception {
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
        Pieza nuevaPieza = new Otro(id, titulo, anioCreacion, lugarCreacion, null, disponible, valor, tiempoAcordado, null, null);
        empleado.modificarPieza(nuevaPieza);
    }

    private void cambiarUbicacionPieza(Empleado empleado) {
        Integer ubicacion = Integer.parseInt(input("Seleccione el cambio a realizar:\n1. En exhibicion -> En bodega\n2. En bodega -> En exhibicion")) - 1;
        String inventarioUbicacion = ubicacion == 1 ? getPiezasEnExhibicion() : getPiezasEnBodega();
        System.out.println(inventarioUbicacion);
        Integer posicion = Integer.parseInt(input("Ingrese la posicion de la pieza a cambiar: ")) - 1;
        empleado.cambiarUbicacionPieza(ubicacion, posicion);
        System.out.println("Operacion exitosa\n");
    }

    private String getPiezasEnExhibicion() {
        String string = "Piezas en exhibicion:\n";
        for (int i = 1; i <= galeria.getEnExhibicion().size(); i++) {
            string += Integer.toString(i) + "- " + galeria.getEnExhibicion().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private String getPiezasEnBodega() {
        String string = "Piezas en bodega:\n";
        for (int i = 1; i <= galeria.getEnBodega().size(); i++) {
            string += Integer.toString(i) + "- " + galeria.getEnBodega().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private void verificarOfertasPendientes(Empleado empleado) throws OperacionIlegal {
        List<Oferta> ofertasPendientes = empleado.getGaleria().getOfertasPendientes();
        System.out.println(getOfertasPendientes(ofertasPendientes));
        Integer opcion = Integer.parseInt(input("Seleccione la oferta a verificar: "));
        Oferta oferta = ofertasPendientes.get(opcion-1);
        System.out.println("La pieza a comprar es: " + oferta.getPieza().getTitulo() + "\nValor: " + oferta.getValorOferta() + "\nLos datos del comprador son: " + oferta.getCliente().getNombre() + "\nBalance: " + oferta.getCliente().getBalance());
        opcion = Integer.parseInt(input("Desea modificar el balance actual?\n1. Si\n2. No"));
        Integer balance = opcion == 1 ? Integer.parseInt(input("Nuevo balance: ")) : oferta.getCliente().getBalance();
        boolean aceptada = balance < oferta.getValorOferta() ? false : Integer.parseInt(input("Acepta la oferta?\n1. Si\n2. No")) == 1 ? true : false;
        empleado.verificarOferta(oferta, balance, aceptada);
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

    private void verificarComprasPendientes(Empleado empleado) throws OperacionIlegal {
        List<Oferta> comprasPendientes = empleado.getGaleria().getComprasPendientes();
        System.out.println(getComprasPendientes(comprasPendientes));
        Integer opcion = Integer.parseInt(input("Seleccione la compra a verificar: "));
        Oferta oferta = comprasPendientes.get(opcion-1);
        System.out.println("La pieza a comprar es: " + oferta.getPieza().getTitulo() + "\nValor: " + oferta.getValorOferta() + "\nLos datos del comprador son: " + oferta.getCliente().getNombre() + "\nBalance: " + oferta.getCliente().getBalance());
        opcion = Integer.parseInt(input("Seleccione un tipo de pago:\n1. Efectivo\n2. Transferencia\n3. Tarjeta"));
        TipoPago tipoPago = opcion == 1 ? TipoPago.EFECTIVO : opcion == 2 ? TipoPago.TRANSFERENCIA : TipoPago.TARJETA;
        empleado.verificarCompra(oferta, tipoPago);
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

    private String getComprasHechas(Empleado empleado) {
        String string = "";
        for(int i = 1; i <= empleado.getGaleria().getCompras().size(); i++) {
            Compra compra = empleado.getGaleria().getCompras().get(i-1);
            string += i + "- Pieza: " + compra.getPieza().getTitulo() + " Comprador: " + compra.getCliente().getNombre() + " Valor: " + compra.getValorPagado() + "\n";
        }
        return string;
    }

    private void devolucionesPendientes(Empleado empleado) throws OperacionIlegal {
        System.out.println("0. Volver\n"); 
        List<Pieza> piezasVencidas = empleado.getPiezasVencidas();
        System.out.println(getPiezasVencidas(piezasVencidas));
        Integer opcion = Integer.parseInt(input("Seleccione la pieza a devolver:"));
        if (opcion > 0) {
            empleado.confirmarDevolucionPieza(piezasVencidas, opcion-1);
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

    private String getPiezasVendidas(Empleado empleado) {
        String string = "Piezas vendidas:\n";
        for (int i = 1; i <= empleado.getGaleria().getVendidas().size(); i++) {
            string += Integer.toString(i) + "- " + empleado.getGaleria().getVendidas().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private String getPiezasRegresadas(Empleado empleado) {
        String string = "Piezas regresadas:\n";
        for (int i = 1; i <= empleado.getGaleria().getRegresadas().size(); i++) {
            string += Integer.toString(i) + "- " + empleado.getGaleria().getRegresadas().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private Usuario crearUsuario(Empleado empleado) throws OperacionIlegal {
        String nombre = input("Ingrese el nombre del usuario: ");
        String login = input("Ingrese el login del usuario: ");
        String password = input("Ingrese el password del usuario: ");
        String telefono = input("Ingrese el telefono del usuario: ");
        String correo = input("Ingrese el correo del usuario: ");
        Usuario usuario = null;
        Integer ubicacion = Integer.parseInt(input("Tipo de usuario:\n1. Cliente\n2. Empleado"));
        if (ubicacion == 1) {
            Integer opcion = Integer.parseInt(input("Tipo cliente:\n1. Autor\n2. Propietario\n3. Comprador"));
            String tipo = opcion == 1 ? "Autor" : opcion == 2 ? "Propietario" : "Comprador";
            usuario = new Cliente(nombre, login, password, telefono, correo, 0, galeria, tipo);
        } else if (ubicacion == 2) {
            Integer opcion = Integer.parseInt(input("Tipo empleado:\n1. Administrador\n2. Operador\n3. Cajero\n4. Otro"));
            String tipo = opcion == 1 ? "Administrador": opcion == 2 ?  "Operador": opcion == 3 ? "Cajero" : "Empleado";
            usuario = new Empleado(nombre, login, password, telefono, correo, galeria, tipo);
        }
        if (usuario != null) {
            empleado.agregarUsuario(usuario);
        }
        return usuario;
    }

    private void modificarUsuario(Empleado empleado) throws OperacionIlegal {
        String login = input("Ingrese el login del usuario: ");
        Usuario usuario = empleado.getUsuario(login);
        String nombre = usuario.getNombre();
        Integer opcion = Integer.parseInt(input("El nombre actual del usuario es: " + nombre + "\nDesea cambiarlo?\n1. Si\n2. No"));
        nombre = opcion == 1 ? input("Nuevo nombre: ") : nombre;
        String password = usuario.getPassword();
        opcion = Integer.parseInt(input("La constrasenia actual del usuario es: " + password + "\nDesea cambiarla?\n1. Si\n2. No"));
        password = opcion == 1 ? input("Nueva contrasenia: ") : password;
        String telefono = usuario.getTelefono();
        opcion = Integer.parseInt(input("El telefono actual del usuario es: " + telefono + "\nDesea cambiarlo?\n1. Si\n2. No"));
        telefono = opcion == 1 ? input("Nuevo telefono: ") : telefono;
        String correo = usuario.getCorreo();
        opcion = Integer.parseInt(input("El correo actual del usuario es: " + correo + "\nDesea cambiarlo?\n1. Si\n2. No"));
        correo = opcion == 1 ? input("Nuevo correo: ") : correo;
        Usuario nuevoUsuario = new Empleado(nombre, login, password, telefono, correo, galeria, null);
        empleado.modificarUsuario(nuevoUsuario);
    }

    private String getUsuarios(Empleado empleado) {
        String string = "Usuarios:\n";
        for (Usuario usuario : empleado.getGaleria().getUsuarios().values()) {
            string += "- " + usuario.getNombre() + " " + usuario.getLogin() + " " + usuario.getTelefono() + "\n";
        }
        return string;
    }

    private void iniciarAplicacionAutor(Cliente cliente) {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuAutor());
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                System.out.println("Piezas de mi Autoria:\n" + getPiezasAutorVendidas(cliente));
            } else if (opcion == 2) {
                System.out.println(getPerfil(cliente));
            }
        } while (opcion != 0);
    }

    private String getMenuAutor() {
        return "0.Salir/Guardar\n1. Ver mis piezas (Autor)\n2. Perfil\n";
    }

    private String getPiezasAutorVendidas(Cliente cliente) {
        String string = "";
        for (int i = 1; i <= cliente.getAutoriaVendidasCompradas().size(); i++) {
            string += Integer.toString(i) + "- " + cliente.getAutoriaVendidasCompradas().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private void iniciarAplicacionComprador(Cliente cliente) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuComprador(null));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                iniciarCompra(cliente, 1);
            } else if (opcion == 2) {
                iniciarCompra(cliente, 2);
            } else if (opcion == 3) {
                List<Subasta> subastas = galeria.obtenerSubastasActivas();
                System.out.println(getSubastas(subastas));
                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
                Subasta subasta = subastas.get(eleccion-1);
                System.out.println(getDetallesSubasta(subasta));
                boolean verificado = cliente.verificarAccesoSubasta(subasta);
                if (verificado) {
                    eleccion = Integer.parseInt(input("Seleccione la pieza que quiere comprar: "));
                    Pieza pieza = subasta.getPiezas().get(eleccion-1);
                    Oferta mayorOferta = subasta.getMayorOferta(pieza);
                    String mayorOfertaStr = mayorOferta == null ? "0" : Integer.toString(mayorOferta.getValorOferta());
                    Integer valorOferta = Integer.parseInt(input("El valor inicial de la pieza es: " + pieza.getValor() + "\nLa mayor oferta actual es: " + mayorOfertaStr + "\nIngrese su oferta: "));
                    cliente.ofertaSubasta(subasta, pieza, valorOferta);
                } else {
                    System.out.println("No tiene acceso a esta subasta\nSolicitando acceso...");
                }
                System.out.println("Operacion exitosa");
            } else if (opcion == 4) {
                System.out.println("Piezas compradas:\n" + getListaPiezas(cliente.getAutoriaVendidasCompradas()));
            } else if (opcion == 5) {
                System.out.println("Compras pendientes\n" + getListaPiezas(cliente.getPrestadasEnEspera()));
            } else if (opcion == 6) {
                System.out.println(getPerfil(cliente));
            }
        } while (opcion != 0);
    }

    private String getMenuComprador(Integer opcion) {
        if (opcion == null) {
            return "0. Salir/Guardar\n1. Ver piezas en exhibicion\n2. Ver piezas en bodega\n3. Ver subastas actuales\n4. Ver mis compras\n5. Ver compras pendientes\n6. Perfil\n";
        } else if (opcion == 1) {
            return "0. Volver\n(Pendiente)";
        }
        return "0. Salir/Guardar\n1. Ver piezas en exhibicion\n2. Ver piezas en bodega\n3. Ver subastas actuales\n4. Ver mis compras\n5. Ver compras pendientes\n6. Perfil\n";
    }

    private void iniciarCompra(Cliente cliente, int opcion) throws OperacionIlegal {
        List<Pieza> listaPiezas = null;
        if (opcion == 1) {
            listaPiezas = galeria.getEnExhibicion();
            System.out.println("Piezas en exhibicion:\n" + getListaPiezas(listaPiezas));
        } else if (opcion == 2) {
            listaPiezas = galeria.getEnBodega();
            System.out.println("Piezas en bodega:\n" + getListaPiezas(listaPiezas));
        }
        Integer eleccion = Integer.parseInt(input("Seleccione la pieza a comprar: "));
        Pieza pieza = listaPiezas.get(eleccion-1);
        cliente.ofertaCompra(pieza);
        System.out.println("Oferta generada con exito");
    }

    private String getListaPiezas(List<Pieza> listaPiezas) {
        String string = "";
        for (int i = 1; i <= listaPiezas.size(); i++) {
            Pieza pieza = listaPiezas.get(i-1);
            string += i + "- Titulo: " + pieza.getTitulo() + " Precio: " + pieza.getValor() + "\n";
        }
        return string;
    }

    private void iniciarAplicacionPropietario(Cliente cliente) {
        int opcion;
        do {
            System.out.println("\n\n" + getMenuPropietario());
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                System.out.println(getPiezasPrestadas(cliente));
            } else if (opcion == 2) {
                System.out.println(getPiezasVendidas(cliente));
            } else if (opcion == 3) {
                System.out.println(getPerfil(cliente));
            }
        } while (opcion != 0);
    }

    private String getMenuPropietario() {
        return "0. Salir\n1. Ver piezas prestadas\n2. Ver piezas vendidas\n3. Perfil\n";
    }

    private String getPiezasPrestadas(Cliente cliente) {
        String string = "Piezas prestadas:\n";
        for (int i = 1; i <= cliente.getPrestadasEnEspera().size(); i++) {
            string += Integer.toString(i) + "- " + cliente.getPrestadasEnEspera().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private String getPiezasVendidas(Cliente cliente) {
        String string = "Piezas vendidas:\n";
        for (int i = 1; i <= cliente.getAutoriaVendidasCompradas().size(); i++) {
            string += Integer.toString(i) + "- " + cliente.getAutoriaVendidasCompradas().get(i-1).getTitulo() + "\n";
        }
        return string;
    }

    private String getPerfil(Usuario usuario) {
        String tipoUsuario = usuario instanceof Empleado ? "Empleado" : "Cliente";
        return "Tipo Usuario: " + tipoUsuario + "\nNombre: " + usuario.getNombre() + "\nLogin: " + usuario.getLogin() + "\nTelefono: " + usuario.getTelefono() + "\nCorreo: " + usuario.getCorreo() + "\nRol: " + usuario.getTipo();
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
            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreo() + "\n"; 
        }
        string += "\nClientes en espera:\n";
        List<Cliente> clientesEnEspera = subasta.getEnEspera();
        for (int i = 1; i <= clientesEnEspera.size(); i++) {
            Cliente cliente = clientesEnEspera.get(i-1);
            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreo() + "\n"; 
        }
        return string;
    }

    private void crearSubasta(Empleado empleado) {
        String nombre = input("Ingrese el nombre de la subasta: ");
        boolean activa = Integer.parseInt(input("Esta activa?\n1. Si\n2. No")) == 1 ? true : false;
        empleado.crearSubasta(new Subasta(nombre, activa));
        System.out.println("Operacion exitosa");
    }

    private void modificarSubasta(Empleado empleado) {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta a modificar: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta));
        String nombre = subasta.getNombre();
        nombre = Integer.parseInt(input("El nombre actual es: " + nombre + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? input("Nuevo nombre: ") : nombre;
        boolean activa = subasta.isActiva();
        activa = Integer.parseInt(input("El estado actual es: " + activa + "\nDesea cambiarlo?\n1. Si\n2. No")) == 1 ? !activa : activa;
        Subasta nuevaSubasta = new Subasta(nombre, activa);
        empleado.modificarSubasta(subasta, nuevaSubasta);
        System.out.println("Operacion exitosa");
    }

    private void cerrarSubasta(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta a cerrar: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta));
        empleado.cerrarSubasta(subasta);
        System.out.println("Operacion exitosa");
    }

    private void agregarPiezaSubasta(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta a la que agregara la pieza: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta) + "\n\nInventario de piezas:\n");
        List<Pieza> enExhibicion = empleado.getGaleria().getEnExhibicion();
        List<Pieza> enBodega = empleado.getGaleria().getEnBodega();
        System.out.println("Piezas en exhibicion:\n" + getListaPiezas(enExhibicion));
        System.out.println("\nPiezas en bodega:\n" + getListaPiezas(enBodega));
        opcion = Integer.parseInt(input("Seleccione la ubicacion de la pieza:\n1. En exhibicion\n2. En bodega"));
        Pieza pieza;
        if (opcion == 1) {
            pieza = enExhibicion.get(Integer.parseInt(input("Seleccione la pieza a agregar: "))-1);
        } else {
            pieza = enBodega.get(Integer.parseInt(input("Seleccione la pieza a agregar: "))-1);
        }
        Integer valorMinimo = Integer.parseInt(input("Ingrese el valor minimo de la pieza: "));
        Pieza piezaModificada = new Otro(pieza.getId(), null, null, null, null, false, null, valorMinimo, false, null, null, null, null, null);
        empleado.agregarPiezaSubasta(subasta, piezaModificada);
        System.out.println("Operacion exitosa");
    }

    private void removerPiezaSubasta(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta de la que removera la pieza: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta));
        opcion = Integer.parseInt(input("Seleccione la pieza que va a remover: "));
        Pieza pieza = subasta.getPiezas().get(opcion-1);
        empleado.removerPiezaSubasta(subasta, pieza);
        System.out.println("Operacion exitosa");
    }

    private void verificarClientes(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta en la que va a verificar: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta));
        opcion = Integer.parseInt(input("Seleccione el usuario que quiere verificar: "));
        Cliente cliente = subasta.getEnEspera().get(opcion-1);
        empleado.verificarUsuarioSubasta(subasta, cliente);
        System.out.println("Operacion exitosa");
    }

    private void verificarOfertaSubasta(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta en la que va a verificar: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta) + "\n" + getOfertas(subasta));
        opcion = Integer.parseInt(input("Seleccione la oferta que quiere verificar: "));
        Oferta oferta = subasta.getPendientes().get(opcion-1);
        empleado.aceptarOferta(subasta, oferta);
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

    public String input(String mensaje) {
        try {
            System.out.println(mensaje);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        AplicacionConsola aplicacionConsola = new AplicacionConsola();
        aplicacionConsola.iniciarAplicacion();
    }
}
