package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import excepciones.OperacionIlegal;
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

public class ApliacionEmpleado {
    Galeria galeria;
    Empleado empleado;

    public void iniciarAplicacion() {
        try {
            this.galeria = new Galeria();
            this.empleado = logIn();
            int opcion;
            do {
                System.out.println("\n\n" + getMenu(null));
                opcion = Integer.parseInt(input("Opcion: "));
                if (opcion == 1) {
                    administrarPiezas(empleado);
                } else if (opcion == 2) {
                    administrarSubastas(empleado);
                } else if (opcion == 3) {
                    verificarComprasPendientes(empleado);
                } else if (opcion == 4) {
                    administrarUsuarios(empleado);
                } else if (opcion == 5) {
                    System.out.println(getComprasHechas(empleado));
                } else if (opcion == 6) {
                    System.out.println(getPerfil(empleado));
                } else if (opcion == 7) {
                    System.out.println(historiaArtista());
                } else if (opcion == 8) {
                    System.out.println(historiaPieza());
                } else if (opcion == 0) {
                    galeria.guardarDatos();
                } else {
                    System.out.println("Opcion incorrecta.\nIntente nuevamente.");
                }
            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Empleado logIn() {
        Usuario usuario = null;
        System.out.println("Inicio de sesion");
        do {
            String login = input("Usuario del empleado: ").toLowerCase();
            String clave = input("Constrasenia: ");
            if (galeria.getUsuarios().containsKey(login)) {
                usuario = galeria.getUsuarios().get(login);
                if (usuario instanceof Empleado) {
                    Empleado empleado = (Empleado) usuario;
                    if (!empleado.getTipo().equals("Administrador")) {
                    	if (empleado.getPassword().equals(clave)) {
                    		System.out.println("Inicio de sesion exitoso.");
                            return empleado;
                    	}
                    }
                   
                } else {
                    System.out.println("El usuario no ha encontrado. Intente nuevamente.");
                }
            } else {
                System.out.println("El usuario no existe.");
            }
        } while (this.empleado == null);
        return null;
    }

    private String getMenu(Integer opcion) {
        if (opcion == null) {
            return "0. Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Compras pendientes\n4. Administrar usuarios\n5. Compras hechas\n6. Perfil\n7. Historial de un artista\n8. Historial de una pieza";
        } else if (opcion == 1) {
            return "0. Volver\n1. Cambiar ubicacion de pieza\n2. Ver piezas de inventario\n3. Ver piezas vendidas\n4. Ver piezas devueltas\n5. Modificar pieza\n";
        } else if (opcion == 2) {
            return "0. Volver\n1. Ver detalles de la subasta\n2. Cerrar subasta\n3. Verificar ofertas\n";
        } else if (opcion == 5) {
            return "0. Volver\n1. Modificar usuario\n2. Ver usuarios";
        }
        return "0.Salir/Guardar\n1. Administrar piezas\n2. Administrar subastas\n3. Compras pendientes\n4. Administrar usuarios\n5. Compras hechas\n6. Perfil\n7. Historial Artista\\n8. Historial Pieza";
    }

    private void administrarPiezas(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenu(1));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                cambiarUbicacionPieza(empleado);
            } else if (opcion == 2) {
                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
            } else if (opcion == 3) {
                System.out.println(getPiezasVendidas(empleado));
            } else if (opcion == 4) {
                System.out.println(getPiezasRegresadas(empleado));
            } else if (opcion == 5) {
                System.out.println(getPiezasEnExhibicion() + "\n" + getPiezasEnBodega());
                modificarPieza(empleado);
            }
        } while (opcion != 0);
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
        Integer ubicacion = Integer.parseInt(input("Seleccione el cambio que usted desea realizar:\n1. Cambiar de exhibicion a bodega\n2. Cambiar de bodega a exhibicion")) - 1;
        String inventarioUbicacion = ubicacion == 1 ? getPiezasEnExhibicion() : getPiezasEnBodega();
        System.out.println(inventarioUbicacion);
        Integer posicion = Integer.parseInt(input("Ingrese la posicion de la pieza a cambiar: ")) - 1;
        empleado.cambiarUbicacionPieza(ubicacion, posicion);
        System.out.println("La operacion ha sido exitosa\n");
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
        String tipo = usuario.getTipo();
        opcion = Integer.parseInt(input("El correo actual del usuario es: " + correo + "\nDesea cambiarlo?\n1. Si\n2. No"));
        correo = opcion == 1 ? input("Nuevo correo: ") : correo;
        Usuario nuevoUsuario = new Empleado(nombre, login, password, telefono, correo, galeria, tipo);
        empleado.modificarUsuario(nuevoUsuario);
    }

    private String getUsuarios(Empleado empleado) {
        String string = "Usuarios:\n";
        for (Usuario usuario : empleado.getGaleria().getUsuarios().values()) {
            string += "- " + usuario.getNombre() + " " + usuario.getLogin() + " " + usuario.getTelefono() + "\n";
        }
        return string;
    }

    private void administrarSubastas(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenu(2));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                List<Subasta> subastas = empleado.getGaleria().getSubastas();
                System.out.println(getSubastas(subastas));
                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
                Subasta subasta = subastas.get(eleccion-1);
                System.out.println(getDetallesSubasta(subasta));
            } else if (opcion == 2) {
                cerrarSubasta(empleado);
            } else if (opcion == 3) {
                verificarOfertaSubasta(empleado);
            }
        } while (opcion != 0);
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

    private void cerrarSubasta(Empleado empleado) throws Exception {
        List<Subasta> subastas = empleado.getGaleria().getSubastas();
        System.out.println(getSubastas(subastas));
        Integer opcion = Integer.parseInt(input("Seleccione la subasta a cerrar: "));
        Subasta subasta = subastas.get(opcion-1);
        System.out.println(getDetallesSubasta(subasta));
        empleado.cerrarSubasta(subasta);
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

    private void administrarUsuarios(Empleado empleado) throws Exception {
        int opcion;
        do {
            System.out.println("\n\n" + getMenu(5));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                modificarUsuario(empleado);
            } else if (opcion == 2) {
                System.out.println(getUsuarios(empleado));
            } 
        } while (opcion != 0);
    }

    private String getPerfil(Usuario usuario) {
        String tipoUsuario = usuario instanceof Empleado ? "Empleado" : "Cliente";
        return "Tipo Usuario: " + tipoUsuario + "\nNombre: " + usuario.getNombre() + "\nLogin: " + usuario.getLogin() + "\nTelefono: " + usuario.getTelefono() + "\nCorreo: " + usuario.getCorreo() + "\nRol: " + usuario.getTipo();
    }

    private String historiaArtista() {
        String string = "";
        String login = input("LogIn del artista:").toLowerCase();
        if (galeria.getUsuarios().containsKey(login)) {
            Usuario usuario = galeria.getUsuarios().get(login);
            if (usuario instanceof Cliente) {
                Cliente artista = (Cliente) usuario;
                string += "Piezas que ha hecho: ";
                for (Pieza pieza: artista.getAutoriaVendidasCompradas()) {
                    string += "\nPieza: " + pieza.getTitulo() + "\n" + "Fecha de creacion: " + pieza.getAnioCreacion() + "\n" + "Historial de compra: \n";
                    for (Compra compra: galeria.getComprasPieza(pieza)) {
                        string += "Fecha de venta: " + compra.getFecha() + "\nValor pagado: " + compra.getValorPagado() + "\n";
                    }
                }
            } else {
                string += "El usuario no es un artista.";
            }
        } else {
            string += "No se encontró el artista. Intente nuevamente";
        }
        return string;
    }

    private String historiaPieza() {
        String string = "";
        Integer id = Integer.parseInt(input("Ingrese el id de la pieza: "));
        Pieza pieza = galeria.getPiezaPorId(id);
        string += "Datos generales de la pieza:\n";
        string += "Titulo: " + pieza.getTitulo() + "\nAnio de creacion: " + pieza.getAnioCreacion() + "\nLugar de creacion: " + pieza.getLugarCreacion() + "\nTipo pieza: " + pieza.getTipo() + "\nDisponible: " + pieza.isDisponible() + "\nValor: " + pieza.getValor() + "\n\n";
        string += "Propietarios de la pieza:\n";
        for (Cliente propietario : galeria.getDueniosPieza(pieza)) {
            string += "Nombre: " + propietario.getNombre() + "\n";
        }
        string += "\nCompras de la pieza:\n";
        for (Compra compra : galeria.getComprasPieza(pieza)) {
            string += "Fecha de venta: " + compra.getFecha() + "\nValor pagado: " + compra.getValorPagado() + "\n";
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
        ApliacionEmpleado aplicacionEmpledado = new ApliacionEmpleado();
        aplicacionEmpledado.iniciarAplicacion();
    }
}
