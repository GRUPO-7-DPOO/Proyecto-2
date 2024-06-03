package modelo;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import excepciones.OperacionIlegal;

public class Empleado extends Usuario {


    public Empleado(String nombre, String login, String password, String telefono, String correo, Galeria galeria, String tipo) {
        super(nombre, login, password, telefono, correo, galeria, tipo);
        
    }

    public Pieza agregarPieza(Pieza pieza, Integer opcion) throws OperacionIlegal {
        if (!this.tipo.equals("Administrador")) {
            throw new OperacionIlegal("Solo los administradores pueden agregar piezas.");
        }
        if (opcion == 0) {
            return null;
        }
        if (opcion == 1) {
            this.galeria.agregerAExhibicion(pieza);
        } else if (opcion == 2) {
            this.galeria.agregerABodega(pieza);
        } else {
            throw new OperacionIlegal("Opcion incorrecta");
        }
        return pieza;
    }

    public Pieza cambiarUbicacionPieza(Integer ubicacion, Integer posicion) {
        Pieza pieza;
        if (ubicacion == 0) {
            pieza = galeria.getEnExhibicion().get(posicion);
            galeria.getEnExhibicion().remove(pieza);
            galeria.getEnBodega().add(pieza);
        } else {
            pieza = galeria.getEnBodega().get(posicion);
            galeria.getEnBodega().remove(pieza);
            galeria.getEnExhibicion().add(pieza); 
        }
        return pieza;
    }

    public List<Pieza> getPiezasVencidas() {
        List<Pieza> piezasVencidas = new ArrayList<>();
        for (Pieza pieza : galeria.getEnExhibicion()) {
            if (pieza.getFechaSalida().before(new Date())) {
                piezasVencidas.add(pieza);
            }
        }
        for (Pieza pieza : galeria.getEnBodega()) {
            if (pieza.getFechaSalida().before(new Date())) {
                piezasVencidas.add(pieza);
            }
        }
        return piezasVencidas;
    }

    public Pieza confirmarDevolucionPieza(List<Pieza> piezasVencidas, Integer posicion) throws OperacionIlegal {
    	if (!this.tipo.equals("Administrador")) {
            throw new OperacionIlegal("Esta accion solo puede realizarla un administrador");
        }
        Pieza pieza = piezasVencidas.get(posicion);
        galeria.getEnExhibicion().remove(pieza);
        galeria.getEnBodega().remove(pieza);
        galeria.getRegresadas().add(pieza);
        pieza.setFechaVentaEntrega(new Date());
        return pieza;
    }

    public Usuario agregarUsuario(Usuario usuario) throws OperacionIlegal {
        if (galeria.getUsuarios().containsKey(usuario.getLogin())) {
            throw new OperacionIlegal("Ya existe un usuario con ese login");
        }
        galeria.getUsuarios().put(usuario.getLogin(), usuario);
        return usuario;
    }

    public Usuario modificarUsuario(Usuario nuevoUsuario) throws OperacionIlegal {
        Usuario usuario = getUsuario(nuevoUsuario.getLogin());
        usuario.setNombre(nuevoUsuario.getNombre());
        usuario.setPassword(nuevoUsuario.getPassword());
        usuario.setTelefono(nuevoUsuario.getTelefono());
        usuario.setCorreo(nuevoUsuario.getCorreo());
        return usuario;
    }

    public Pieza modificarPieza(Pieza nuevaPieza) throws OperacionIlegal {
        Pieza pieza = galeria.getPiezaPorId(nuevaPieza.id);
        if (galeria.getVendidas().contains(pieza) || galeria.getRegresadas().contains(pieza)) {
            throw new OperacionIlegal("Esta pieza no puede ser modificada.");
        }
        pieza.setTitulo(nuevaPieza.getTitulo());
        pieza.setAnioCreacion(nuevaPieza.getAnioCreacion());
        pieza.setLugarCreacion(nuevaPieza.getLugarCreacion());
        pieza.setDisponible(nuevaPieza.isDisponible());
        pieza.setValor(nuevaPieza.getValor());
        pieza.setTiempoAcordado(nuevaPieza.getTiempoAcordado());
        return pieza;
    }

    public Oferta verificarOferta(Oferta oferta, Integer balance, boolean aceptada) throws OperacionIlegal {
    	if (!this.tipo.equals("Administrador")) {
            throw new OperacionIlegal("Esta accion solo la puede realizar un administrador");
        }
        Cliente cliente = oferta.getCliente();
        Pieza pieza = oferta.getPieza();
        cliente.setBalance(balance);
        pieza.setBloqueado(false);
        cliente.getPrestadasEnEspera().remove(pieza);
        boolean esAceptada = aceptada && balance >= pieza.getValor();
        oferta.setAceptada(esAceptada);
        oferta.setRevisada(true);
        return oferta;
    }

    public Compra verificarCompra(Oferta oferta, TipoPago tipoPago) throws OperacionIlegal {
        if (!this.tipo.equals("Cajero")) {
            throw new OperacionIlegal("Esta accion solo puede ser realizada por un usuario con el rol de cajero");
        }
        Cliente cliente = oferta.getCliente();
        Pieza pieza = oferta.getPieza();
        cliente.setBalance(cliente.getBalance() - pieza.getValor());
        galeria.getEnExhibicion().remove(pieza);
        galeria.getEnBodega().remove(pieza);
        cliente.getAutoriaVendidasCompradas().add(pieza);
        Cliente nuevoPropietario = (Cliente) galeria.getUsuarios().get(cliente.getLogin().split("-")[0] + "-propietario");
        nuevoPropietario.getPrestadasEnEspera().add(pieza);
        for (Pieza piezaa : nuevoPropietario.getPrestadasEnEspera()) {
            System.out.println(piezaa.getTitulo());
        }
        System.out.println("Sali del for");
        galeria.getVendidas().add(pieza);
        Cliente propietario = (Cliente) galeria.getPropietario(pieza);
        propietario.getPrestadasEnEspera().remove(pieza);
        propietario.getAutoriaVendidasCompradas().add(pieza);
        pieza.setFechaVentaEntrega(new Date());
        oferta.setPagado(true);
        Compra compra = new Compra(oferta.getValorOferta(), tipoPago, cliente, pieza, new Date());
        galeria.getCompras().add(compra);
        return compra;
    }

    public Subasta crearSubasta(Subasta subasta) {
        this.getGaleria().getSubastas().add(subasta);
        return subasta;
    }

    public Cliente verificarUsuarioSubasta(Subasta subasta, Cliente cliente) throws OperacionIlegal {
      
        if (!this.tipo.equals("Administrador")) {
            throw new OperacionIlegal("Esta accion solo puede ser realizada por un usuario con el rol de administrador");
        }
        subasta.getEnEspera().remove(cliente);
        subasta.getVerificados().add(cliente);
        return cliente;
    }

    public Pieza agregarPiezaSubasta(Subasta subasta, Pieza piezaModificada) throws OperacionIlegal {
        Pieza pieza = this.getGaleria().getPiezaPorId(piezaModificada.id);
        if (subasta.getPiezas().contains(pieza)) {
            throw new OperacionIlegal("Esta pieza ya fue agregada");
        }
        if (galeria.getVendidas().contains(pieza) || galeria.getRegresadas().contains(pieza) || !pieza.isDisponible()) {
            throw new OperacionIlegal("Esta pieza no se puede subastar");
        }
        pieza.setValorMinimo(piezaModificada.getValorMinimo());
        subasta.getPiezas().add(pieza);
        return pieza;
    }

    public Pieza removerPiezaSubasta(Subasta subasta, Pieza pieza) throws OperacionIlegal {
        if (!subasta.getPiezas().contains(pieza)) {
            throw new OperacionIlegal("Esta pieza ya fue removida");
        }
        if (pieza.getFechaVentaEntrega() == null) {
            throw new OperacionIlegal("Esta pieza ya fue vendida");
        }
        subasta.getPiezas().remove(subasta.getPiezas().indexOf(pieza));
        pieza.setValorMinimo(null);
        return pieza;
    }

    public Subasta modificarSubasta(Subasta subasta, Subasta nuevaSubasta) {
        subasta.setNombre(nuevaSubasta.getNombre());
        subasta.setActiva(nuevaSubasta.isActiva());
        return subasta;
    }

    public Oferta aceptarOferta(Subasta subasta, Oferta oferta) throws OperacionIlegal{
        if (!this.tipo.equals("Operador")) {
            throw new OperacionIlegal("Esta accion solo puede ser realizada por un usuario con el rol de operador");
        }
        subasta.getPendientes().remove(oferta);
        subasta.getAceptadas().add(oferta);
        return oferta;
    }

    public Subasta cerrarSubasta(Subasta subasta) throws OperacionIlegal {
        subasta.setActiva(false);
        Iterator<Pieza> piezaIterator = subasta.getPiezas().iterator();
        while (piezaIterator.hasNext()) {
            Pieza pieza = piezaIterator.next();
            Oferta mayorOferta = subasta.getMayorOferta(pieza);
            if (mayorOferta != null) {
                pieza.setBloqueado(true);
                galeria.getOfertaPorId(mayorOferta.getId()).setRevisada(false);
                mayorOferta.getCliente().getPrestadasEnEspera().add(mayorOferta.getPieza());
            } else {
                pieza.setValorMinimo(null);
                piezaIterator.remove();
            }
        }
        return subasta;
    }    

    public Usuario getUsuario(String login) throws OperacionIlegal {
        if (!galeria.getUsuarios().containsKey(login)) {
            throw new OperacionIlegal("El usuario no existe");
        }
        Usuario usuario = galeria.getUsuarios().get(login);
        return usuario;
    }

    @Override
    public String toString() {
        return super.toString() ;
    }
    
}
