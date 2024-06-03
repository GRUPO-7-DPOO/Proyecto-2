package modelo;

import java.util.Date;

public class Compra {
    private Integer valorPagado;
    private TipoPago metodoPago;
    private Cliente cliente;
    private Pieza pieza;
    private Date fecha;
    
    public Compra(Integer valorPagado, TipoPago metodoPago, Cliente cliente, Pieza pieza, Date fecha) {
        this.valorPagado = valorPagado;
        this.metodoPago = metodoPago;
        this.cliente = cliente;
        this.pieza = pieza;
        this.fecha = fecha;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public String toString() {
        String fecha = this.fecha == null ? "" : Long.toString(this.fecha.getTime());
        return valorPagado + ";" + metodoPago + ";" + cliente.getLogin() + ";" + pieza.getId() + ";" + fecha;
    }
}
