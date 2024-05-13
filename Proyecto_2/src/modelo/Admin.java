package modelo;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

import excepciones.Error;

public class Admin extends Usuario {
    

    public Admin(String tipo, String nombre, String login, String clave, String telefono, String correoElectronico, Galeria galeria) {
        super(tipo, nombre, login, clave, telefono, correoElectronico, galeria);
        
    }

    public Pieza agregarPieza(Pieza pieza, Integer opcionEscogida) throws Error {
        if (!this.tipo.equals("Administrador")) {
            throw new Error("Acción limitada, únicamente el administrador puede registrar piezas.");
        }
        if (opcionEscogida == 0) {
            return null;
        }
        if (opcionEscogida == 1) {
            this.galeria.agregerAExhibicion(pieza);
        } else if (opcionEscogida == 2) {
            this.galeria.agregerABodega(pieza);
        } else {
            throw new Error("Seleccionaste una opción incorrecta, intente nuevamente");
        }
        return pieza;
    }
    
    public void agregarPiezaAt(Pieza pieza, List<Cliente> lista) {
    	for (Cliente cliente: lista) {
    		cliente.getAutoriaVendidasCompradas().add(pieza);
    	}
	}

    public Pieza cambiarUbicacionPieza(String nombre) {
        Pieza pieza;
        
        
        if (galeria.getEnExhibicion().containsKey(nombre)) {
            pieza = galeria.getEnExhibicion().get(nombre);
            galeria.getEnExhibicion().remove(nombre);
            galeria.getEnBodega().put(pieza.getTitulo(), pieza);
        } else {
            pieza = galeria.getEnBodega().get(nombre);
            galeria.getEnBodega().remove(nombre);
            galeria.getEnExhibicion().put(pieza.getTitulo(), pieza);; 
        }
        return pieza;
    }

    public List<Pieza> getPiezasVencidasLimiteTiempo() {
        List<Pieza> piezasVencidas = new ArrayList<>();
        for (Pieza pieza : galeria.getEnExhibicion().values()) {
            if (pieza.getFechaSalida().before(new Date())) {
                piezasVencidas.add(pieza);
            }
        }
        for (Pieza pieza : galeria.getEnBodega().values()) {
            if (pieza.getFechaSalida().before(new Date())) {
                piezasVencidas.add(pieza);
            }
        }
        return piezasVencidas;
    }

    public Pieza confirmarDevolucion(List<Pieza> piezasVencidas, Integer posicion) throws Error {
    	if (!this.tipo.equals("Admin")) {
            throw new Error("El administrador es el único que puede realizar esta accion");
        }
        Pieza pieza = piezasVencidas.get(posicion);
        galeria.getEnExhibicion().remove(pieza.getTitulo());
        galeria.getEnBodega().remove(pieza.getTitulo());
        galeria.getRegresadas().add(pieza);
        pieza.setFechaVentaEntrega(new Date());
        return pieza;
    }

    public Usuario agregarUsuario(Usuario usuario) throws Error {
        if (galeria.getUsuarios().containsKey(usuario.getLogin())) {
            throw new Error("Ya existe un usuario con ese login");
        }
        galeria.getUsuarios().put(usuario.getLogin(), usuario);
        return usuario;
    }

    public Usuario modificarInfoUsuario(Usuario usuario) throws Error {
        Usuario usuario1 = getUsuario(usuario.getLogin());
        usuario1.setNombre(usuario1.getNombre());
        usuario1.setPassword(usuario1.getClave());
        usuario1.setTelefono(usuario1.getTelefono());
        usuario1.setCorreo(usuario1.getCorreoElectronico());
        return usuario1;
    }

    public Pieza modificarPieza(Pieza nuevaPieza) throws Error {
        Pieza pieza = galeria.getPiezaPorId(nuevaPieza.id);
        if (galeria.getVendidas().contains(pieza) || galeria.getRegresadas().contains(pieza)) {
            throw new Error("Esta pieza no puede ser modificada.");
        }
        pieza.setTitulo(nuevaPieza.getTitulo());
        pieza.setAnioCreacion(nuevaPieza.getAnioCreacion());
        pieza.setLugarCreacion(nuevaPieza.getLugarCreacion());
        pieza.setDisponible(nuevaPieza.isDisponible());
        pieza.setValor(nuevaPieza.getValor());
        pieza.setTiempoAcordado(nuevaPieza.getTiempoAcordado());
        return pieza;
    }

    public Oferta verificarOfertaDeCompra(Oferta oferta, Integer balance, boolean aceptada) throws Error {
    	if (!this.tipo.equals("Admin")) {
            throw new Error("Esta accion solo la puede realizar el administrador");
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

    

    public Subasta crearSubasta(Subasta subasta) {
        this.getGaleria().getSubastas().add(subasta);
        return subasta;
    }

    public Cliente verificarUsuarioDeSubasta(Subasta subasta, Cliente cliente) throws Error {
    	if (!this.tipo.equals("Admin")) {
            throw new Error("Esta accion solo puede ser realizada por un usuario con el rol de administrador");
        }
        subasta.getEnEspera().remove(cliente);
        subasta.getVerificados().add(cliente);
        return cliente;
    }

    public Pieza agregarPiezaSubasta(Subasta subasta, Pieza piezaModificada) throws Error {
        Pieza pieza = this.getGaleria().getPiezaPorId(piezaModificada.id);
        if (subasta.getPiezas().contains(pieza)) {
            throw new Error("Esta pieza ya fue agregada");
        }
        if (galeria.getVendidas().contains(pieza) || galeria.getRegresadas().contains(pieza) || !pieza.isDisponible()) {
            throw new Error("Esta pieza no se puede subastar");
        }
        pieza.setValorMinimo(piezaModificada.getValorMinimo());
        subasta.getPiezas().add(pieza);
        return pieza;
    }

    public Pieza removerPiezaSubasta(Subasta subasta, Pieza pieza) throws Error {
        if (!subasta.getPiezas().contains(pieza)) {
            throw new Error("Esta pieza ya fue removida");
        }
        if (pieza.getFechaVentaEntrega() == null) {
            throw new Error("Esta pieza ya fue vendida");
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

    

    public Subasta cerrarSubasta(Subasta subasta) throws Error {
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




    public Usuario getUsuario(String login) throws Error {
        if (!galeria.getUsuarios().containsKey(login)) {
            throw new Error("El usuario no existe");
        }
        Usuario usuario = galeria.getUsuarios().get(login);
        return usuario;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    
}

