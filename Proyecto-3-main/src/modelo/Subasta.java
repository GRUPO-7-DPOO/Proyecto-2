package modelo;

import java.util.ArrayList;
import java.util.List;

public class Subasta {
    private String nombre;
    private boolean activa;
    private List<Oferta> aceptadas;
    private List<Oferta> pendientes;
    private List<Cliente> verificados;
    private List<Cliente> enEspera;
    private List<Pieza> piezas;
    
    public Subasta(String nombre, boolean activa) {
        this.nombre = nombre;
        this.activa = activa;
        this.aceptadas = new ArrayList<>();
        this.pendientes = new ArrayList<>();
        this.verificados = new ArrayList<>();
        this.enEspera = new ArrayList<>();
        this.piezas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public List<Oferta> getAceptadas() {
        return aceptadas;
    }

    public void setAceptadas(List<Oferta> aceptadas) {
        this.aceptadas = aceptadas;
    }

    public List<Oferta> getPendientes() {
        return pendientes;
    }

    public void setPendientes(List<Oferta> pendientes) {
        this.pendientes = pendientes;
    }

    public List<Cliente> getVerificados() {
        return verificados;
    }

    public void setVerificados(List<Cliente> verificados) {
        this.verificados = verificados;
    }

    public List<Cliente> getEnEspera() {
        return enEspera;
    }

    public void setEnEspera(List<Cliente> enEspera) {
        this.enEspera = enEspera;
    }

    public List<Pieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<Pieza> piezas) {
        this.piezas = piezas;
    }

    public Oferta getMayorOferta(Pieza pieza) {
        Oferta mayorOferta = null;
        for (Oferta oferta : aceptadas) {
            if (oferta.getPieza().equals(pieza) && oferta.getValorOferta() >= pieza.getValorMinimo()) {
                if (mayorOferta == null || oferta.getValorOferta() > mayorOferta.getValorOferta()) {
                    mayorOferta = oferta;
                } 
            }
        }
        return mayorOferta;
    }
    
    public String toString() {
        return nombre + ";" + activa + ";" + getOfertasId(aceptadas) + ";" + getOfertasId(pendientes) + ";" + getClienteLogin(verificados) + ";" + getClienteLogin(enEspera) + ";" + getPiezaId(piezas);
    }

    public String getOfertasId(List<Oferta> ofertas) {
        String string = "";
        for (Oferta oferta : ofertas) {
            string += oferta.getId() + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    public String getClienteLogin(List<Cliente> clientes) {
        String string = "";
        for (Cliente cliente : clientes) {
            string += cliente.getLogin() + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    public String getPiezaId(List<Pieza> piezas) {
        String string = "";
        for (Pieza pieza : piezas) {
            string += pieza.getId() + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
}
