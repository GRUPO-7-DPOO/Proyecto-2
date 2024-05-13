package modelo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import excepciones.Error;

public class Empleado extends Usuario {

	public Empleado(String tipo, String nombre, String login, String clave, String telefono, String correoElectronico,
			Galeria galeria) {
		super(tipo, nombre, login, clave, telefono, correoElectronico, galeria);
		// TODO Auto-generated constructor stub
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

    

    public Compra verificarCompra(Oferta oferta, TipoPago tipoPago) throws Error {
    	if (!this.tipo.equals("Cajero")) {
            throw new Error("Esta accion solo puede ser realizada por un usuario con el rol de cajero");
        }
        Cliente cliente = oferta.getCliente();
        Pieza pieza = oferta.getPieza();
        cliente.setBalance(cliente.getBalance() - pieza.getValor());
        galeria.getEnExhibicion().remove(pieza.getTitulo());
        galeria.getEnBodega().remove(pieza.getTitulo());
        cliente.getAutoriaVendidasCompradas().add(pieza);
        galeria.getVendidas().add(pieza);
        Cliente propietario = (Cliente) galeria.getPropietario(pieza);
        propietario.getPrestadasEnEspera().remove(pieza);
        propietario.getAutoriaVendidasCompradas().add(pieza);
        pieza.setFechaVentaEntrega(new Date());
        oferta.setPagado(true);
        Date fecha1 = new Date();
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaString = formatoFecha.format(fecha1);
        Compra compra = new Compra(oferta.getValorOferta(), tipoPago, cliente, cliente.getLogin(), pieza, fechaString);
        galeria.getCompras().add(compra);
        return compra;
    }

    public Subasta crearSubasta(Subasta subasta) {
        this.getGaleria().getSubastas().add(subasta);
        return subasta;
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

    public Oferta aceptarOferta(Subasta subasta, Oferta oferta) throws Error{
    	if (!this.tipo.equals("Operador")) {
            throw new Error("Esta accion solo puede ser realizada por un usuario con el rol de operador");
        }
        subasta.getPendientes().remove(oferta);
        subasta.getAceptadas().add(oferta);
        return oferta;
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



