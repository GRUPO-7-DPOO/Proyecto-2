package modelo;

public class Oferta {
    private Integer id;
    private Integer precioDeOferta;
    private boolean revisionOferta;
    private Cliente cliente;
    private Pieza pieza;
    private boolean ofertaAceptada;
    private boolean pagado;
    
    public Oferta(Integer id, Integer valorOferta, boolean revisionOferta, Cliente cliente, Pieza pieza, boolean ofertaAceptada, boolean pagado) {
        this.id = id;
        this.precioDeOferta = valorOferta;
        this.revisionOferta = revisionOferta;
        this.cliente = cliente;
        this.pieza = pieza;
        this.ofertaAceptada = ofertaAceptada;
        this.pagado = pagado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValorOferta() {
        return precioDeOferta;
    }

    public void setValorOferta(Integer valorOferta) {
        this.precioDeOferta = valorOferta;
    }

    public boolean isRevisada() {
        return revisionOferta;
    }

    public void setRevisada(boolean revisionOferta) {
        this.revisionOferta = revisionOferta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public boolean isAceptada() {
        return ofertaAceptada;
    }

    public void setAceptada(boolean ofertaAceptada) {
        this.ofertaAceptada = ofertaAceptada;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    
    public String toString() {
        return id + ";" + precioDeOferta + ";" + revisionOferta + ";" + cliente.getLogin() + ";" + pieza.getId() + ";" + ofertaAceptada + ";" + pagado;
    }

}
