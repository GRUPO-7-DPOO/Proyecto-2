package modelo;


public class Compra {
    private Integer valorPagado;
    private TipoPago metodoPago;
    private Cliente cliente;
    private Pieza pieza;
    private String login;
    protected String fecha2;
    
    public Compra(Integer valorPagado, TipoPago metodoPago, Cliente cliente,String login, Pieza pieza, String fecha2) {
        this.valorPagado = valorPagado;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.login = cliente.getLogin();
        this.pieza = pieza;
        this.fecha2 = fecha2;;
    }
    
    
  
    public Integer getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Integer valorPagado) {
        this.valorPagado = valorPagado;
    }

    public TipoPago getMetodoPago() {
        return metodoPago;
    }
    
    public String getLogin() {
        return login;
    }

    public void setMetodoPago(TipoPago metodoPago) {
        this.metodoPago = metodoPago;
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
    
    
    public String getFecha2() {
        return fecha2;
    }

    public String toString() {
        return valorPagado + ";" + metodoPago + ";" + cliente.getLogin() + ";" + pieza.getId()+";"+fecha2;
    }
    
    public String toString2() {
        return "Valor pagado:" + valorPagado + "\nNuevo Propietario" + cliente.getLogin() + "\nFecha de compra"+fecha2;
    }
    
    public String toString3() {
        return "Nombre de la pieza: " + pieza.titulo + "\nFecha de compra: "+fecha2;
    }
    
    public String toString4() {
        return "Nombre de la pieza: " + pieza.titulo + "\nFecha de compra: "+fecha2 + "Valor pagado:" + valorPagado +"\n";
    }

}
