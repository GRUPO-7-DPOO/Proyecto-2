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
import modelo.Pieza;
import modelo.Subasta;
import modelo.Usuario;

public class AplicacionCliente {
    private Galeria galeria;
    private Cliente cliente;

    public void iniciarAplicacion() {
        try {
            this.galeria = new Galeria();
            this.cliente = logIn();
            if (cliente.getTipo().equals("Comprador")) {
            	iniciarAplicacionComprador(cliente);
        	} else if (cliente.getTipo().equals("Propietario")) {
        		iniciarAplicacionPropietario(cliente);
    		}
            galeria.guardarDatos();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Cliente logIn() {
        Usuario usuario = null;
        int opcion;
        System.out.println("Inicio de sesion");
        do {
            String login = input("Usuario (Cliente): ").toLowerCase();
            String password = input("Constrasenia: ");
            if (galeria.getUsuarios().containsKey(login + "-propietario")) {
                do {
                    System.out.println("\n\nEliga el menu que quiera usar:\n1. Menu de comprador\n2. Menu de propietario\n");
                    opcion = Integer.parseInt(input("Opcion: "));
                    if (opcion == 1) {
                        login += "-comprador";
                    } else if (opcion == 2) {
                        login += "-propietario";
                    } else {
                        System.out.println("Opcion incorrecta.\nIntente nuevamente.");
                    }
                } while (opcion != 1 && opcion != 2);
                usuario = galeria.getUsuarios().get(login);
                if (usuario instanceof Cliente) {
                    Cliente cliente = (Cliente) usuario;
                    if (cliente.getPassword().equals(password)) {
                        System.out.println("Inicio de sesion exitoso.");
                        return cliente;
                    }
                    System.out.println("Constrasenia incorrecta.");
                } else {
                    System.out.println("El usuario no se ha encontrado. Intente nuevamente.");
                }
            } else {
                System.out.println("El usuario no existe.");
            }
        } while (this.cliente == null);
        return null;
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
            } else if (opcion == 7) {
                System.out.println(historiaArtista());
            } else if (opcion == 8) {
                System.out.println(historiaPieza());
            }
        } while (opcion != 0);
    }

    private String getMenuComprador(Integer opcion) {
        if (opcion == null) {
            return "0. Salir/Guardar\n1. Ver piezas en exhibicion\n2. Ver piezas en bodega\n3. Ver subastas actuales\n4. Ver mis compras\n5. Ver compras pendientes\n6. Perfil\n7. Historial de un artista\n8. Historial de una pieza";
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
            } else if (opcion == 4) {
                System.out.println(historiaArtista());
            } else if (opcion == 5) {
                System.out.println(historiaPieza());
            }
        } while (opcion != 0);
    }

    private String getMenuPropietario() {
        return "0. Salir\n1. Ver piezas prestadas\n2. Ver piezas vendidas\n3. Perfil\n4. Historial de un artista\n5. Historial de una pieza";
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
        AplicacionCliente aplicacionCliente = new AplicacionCliente();
        aplicacionCliente.iniciarAplicacion();
    }
}
