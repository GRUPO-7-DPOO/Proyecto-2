package modelo;

import java.util.ArrayList;
import java.util.List;

import excepciones.Error;

public class Cliente extends Usuario {
    private Integer balance;
    private List<Pieza> prestadasEnEspera;
    private List<Pieza> autoriaVendidasCompradas;

    public Cliente(String tipo, String nombre, String login, String password, String telefono, String correo,  Integer balance, Galeria galeria) {
        super(tipo, nombre, login, password, telefono, correo, galeria);
        this.balance = balance;
        this.prestadasEnEspera = new ArrayList<>();
        this.autoriaVendidasCompradas = new ArrayList<>();
    }

    public Pieza ofertaDeCompra(Pieza pieza) throws Error {
    	if (!this.tipo.equals("Comprador")) {
            throw new Error("Esta operacion solo la puede realizar un comprador");
        }
        if (!pieza.isDisponible() || pieza.isBloqueado() || pieza.valorMinimo != null) {
            throw new Error("Esta pieza no puede ser comprada. Intente de nuevo");
        }
        pieza.setBloqueado(true);
        Oferta oferta = new Oferta(galeria.getSecuenciaOfertas(), pieza.getValor(), false, this, pieza, false, false);
        galeria.agregerAOfertas(oferta);
        this.prestadasEnEspera.add(pieza);
        return pieza;
    }

    public boolean verificarAccesoASubasta(Subasta subasta) {
        if (!subasta.getVerificados().contains(this)) {
            subasta.getEnEspera().add(this);
        }
        return subasta.getVerificados().contains(this);
    }

    public Pieza ofertaSubasta(Subasta subasta, Pieza pieza, Integer valorOferta) throws Error {
        if (!this.tipo.equals("Comprador")) {
            throw new Error("Esta operacion solo la puede realizar un comprador");
        }
        if (!pieza.isDisponible() || pieza.getValorMinimo() == null || pieza.isBloqueado() || pieza.getFechaVentaEntrega() != null) {
            throw new Error("Esta pieza no está en la subasta");
        }
        if (valorOferta < pieza.getValor()) {
            throw new Error("Ningun comprador puede ofrecer menos del valor inicial");
        }
        if (!subasta.getVerificados().contains(this)) {
            throw new Error("El usuario no tiene acceso a la subasta");
        }
        Oferta oferta = new Oferta(galeria.getSecuenciaOfertas(), valorOferta, true, this, pieza, false, false);
        subasta.getPendientes().add(oferta);
        galeria.agregerAOfertas(oferta);
        return pieza;
    }


    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<Pieza> getPrestadasEnEspera() {
        return prestadasEnEspera;
    }

    public void setPrestadasEnEspera(List<Pieza> prestadasEnEspera) {
        this.prestadasEnEspera = prestadasEnEspera;
    }

    public List<Pieza> getAutoriaVendidasCompradas() {
        return autoriaVendidasCompradas;
    }
    
    
    public String toStringPieza(List<Pieza> lista) {
    	String string = "";
    	for (Pieza pieza : lista) {
            string += "Id de la pieza: " + Integer.toString(pieza.id) +  "Nombre de la pieza: " + pieza.titulo + "\n";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    
    
    public String toStringPieza2(List<Pieza> lista) {
    	String string = "";
    	for (Pieza pieza : lista) {
            string += "Id de la pieza: " + Integer.toString(pieza.id) +  "\nNombre de la pieza: " + pieza.titulo + "\nFecha de creacion: " + pieza.anioCreacion +"\n";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    
    

    public void setAutoriaVendidasCompradas(List<Pieza> autoriaVendidasCompradas) {
        this.autoriaVendidasCompradas = autoriaVendidasCompradas;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + balance + ";" + getIdsLista(prestadasEnEspera) + ";" + getIdsLista(autoriaVendidasCompradas);
    }

    private String getIdsLista(List<Pieza> lista) {
        String string = "";
        for (Pieza pieza : lista) {
            string += Integer.toString(pieza.id) + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }

}
