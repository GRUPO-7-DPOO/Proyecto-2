package modelo;

public class Oferta {
    private Integer id;
    private Integer valorOferta;
    private boolean revisada;
    private Cliente cliente;
    private Pieza pieza;
    private boolean aceptada;
    private boolean pagado;
    
    public Oferta(Integer id, Integer valorOferta, boolean revisada, Cliente cliente, Pieza pieza, boolean aceptada, boolean pagado) {
        this.id = id;
        this.valorOferta = valorOferta;
        this.revisada = revisada;
        this.cliente = cliente;
        this.pieza = pieza;
        this.aceptada = aceptada;
        this.pagado = pagado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getValorOferta() {
        return valorOferta;
    }

    public void setValorOferta(Integer valorOferta) {
        this.valorOferta = valorOferta;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
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
        return aceptada;
    }

    public void setAceptada(boolean aceptada) {
        this.aceptada = aceptada;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }
    
    public String toString() {
        return id + ";" + valorOferta + ";" + revisada + ";" + cliente.getLogin() + ";" + pieza.getId() + ";" + aceptada + ";" + pagado;
    }

}
