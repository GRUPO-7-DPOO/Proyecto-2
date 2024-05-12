package modelo;

import java.util.ArrayList;
import java.util.List;

public class Subasta {
    private String nombre;
    private boolean activa;
    private List<Oferta> subastasAceptadas;
    private List<Oferta> subastasPendientes;
    private List<Cliente> clientesVerificados;
    private List<Cliente> clientesEnEspera;
    private List<Pieza> piezasEnSubasta;
    
    public Subasta(String nombre, boolean activa) {
        this.nombre = nombre;
        this.activa = activa;
        this.subastasAceptadas = new ArrayList<>();
        this.subastasPendientes = new ArrayList<>();
        this.clientesVerificados = new ArrayList<>();
        this.clientesEnEspera = new ArrayList<>();
        this.piezasEnSubasta = new ArrayList<>();
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
        return subastasAceptadas;
    }

    public void setAceptadas(List<Oferta> subastasAceptadas) {
        this.subastasAceptadas = subastasAceptadas;
    }

    public List<Oferta> getPendientes() {
        return subastasPendientes;
    }

    public void setPendientes(List<Oferta> subastasPendientes) {
        this.subastasPendientes = subastasPendientes;
    }

    public List<Cliente> getVerificados() {
        return clientesVerificados;
    }

    public void setVerificados(List<Cliente> clientesVerificados) {
        this.clientesVerificados = clientesVerificados;
    }

    public List<Cliente> getEnEspera() {
        return clientesEnEspera;
    }

    public void setEnEspera(List<Cliente> clientesEnEspera) {
        this.clientesEnEspera = clientesEnEspera;
    }

    public List<Pieza> getPiezas() {
        return piezasEnSubasta;
    }

    public void setPiezas(List<Pieza> piezasEnSubasta) {
        this.piezasEnSubasta = piezasEnSubasta;
    }

    public Oferta getMayorOferta(Pieza piezasEnSubasta) {
        Oferta mayorOferta = null;
        for (Oferta oferta : subastasAceptadas) {
            if (oferta.getPieza().equals(piezasEnSubasta) && oferta.getValorOferta() >= piezasEnSubasta.getValorMinimo()) {
                if (mayorOferta == null || oferta.getValorOferta() > mayorOferta.getValorOferta()) {
                    mayorOferta = oferta;
                } 
            }
        }
        return mayorOferta;
    }
    
    public String toString() {
        return nombre + ";" + activa + ";" + getOfertasId(subastasAceptadas) + ";" + getOfertasId(subastasAceptadas) + ";" + getClienteLogin(clientesVerificados) + ";" + getClienteLogin(clientesEnEspera) + ";" + getPiezaId(piezasEnSubasta);
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
