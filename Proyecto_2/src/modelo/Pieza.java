package modelo;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import excepciones.Error;

public abstract class Pieza {
    protected Integer id;
    protected String titulo;
    protected Integer anioCreacion;
    protected String lugarCreacion;
    protected boolean disponible;
    protected Integer valor;
    protected Integer valorMinimo;
    protected boolean bloqueado;
    protected Date ingreso;
    protected Integer tiempoAcordado;
    protected String modalidadPrestamo;
    protected Date fechaVentaEntrega;
    protected List<Cliente> autores;
    protected String tipoPieza;
    
    
    

    public Pieza(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidadPrestamo, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.anioCreacion = anioCreacion;
		this.lugarCreacion = lugarCreacion;
		this.disponible = disponible;
		this.valor = valor;
		this.valorMinimo = valorMinimo;
		this.bloqueado = bloqueado;
		this.ingreso = ingreso != null ? ingreso : new Date();
		this.tiempoAcordado = tiempoAcordado;
		this.modalidadPrestamo = modalidadPrestamo;
		this.fechaVentaEntrega = fechaVentaEntrega;
		this.autores = new ArrayList<>();
		this.tipoPieza = tipoPieza;
	}

	

    public Cliente agregarAutor(Cliente autor) throws Error{
        if (!(autor.getTipo() == "Autor")) {
            throw new Error("El autor que quiere agregar no tiene el rol necesario para realizar esta operacion.");
        }
        autores.add(autor);
        return autor;
    }

    public Date getFechaSalida() {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(ingreso);
        calendario.add(Calendar.DAY_OF_MONTH, tiempoAcordado);
        return calendario.getTime();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getAnioCreacion() {
        return anioCreacion;
    }

    public void setAnioCreacion(Integer anioCreacion) {
        this.anioCreacion = anioCreacion;
    }

    public String getLugarCreacion() {
        return lugarCreacion;
    }

    public void setLugarCreacion(String lugarCreacion) {
        this.lugarCreacion = lugarCreacion;
    }

    public String getTipoPieza() {
        return tipoPieza;
    }

    public void setTipoPieza(String tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Integer getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Integer valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Date getIngreso() {
        return ingreso;
    }

    public void setIngreso(Date ingreso) {
        this.ingreso = ingreso;
    }

    public Integer getTiempoAcordado() {
        return tiempoAcordado;
    }

    public void setTiempoAcordado(Integer tiempoAcordado) {
        this.tiempoAcordado = tiempoAcordado;
    }

    public String getModalidad() {
        return modalidadPrestamo;
    }

    public void setModalidad(String modalidadPrestamo) {
        this.modalidadPrestamo = modalidadPrestamo;
    }

    public Date getFechaVentaEntrega() {
        return fechaVentaEntrega;
    }

    public void setFechaVentaEntrega(Date fechaVentaEntrega) {
        this.fechaVentaEntrega = fechaVentaEntrega;
    }

    public List<Cliente> getAutores() {
        return this.autores;
    }


    public String toString() {
        String valorMinimo = this.valorMinimo == null ? "" : Integer.toString(this.valorMinimo);
        String fechaVentaEntrega = this.fechaVentaEntrega == null ? "" : Long.toString(this.fechaVentaEntrega.getTime());
        String ingreso = this.ingreso == null ? "" : Long.toString(this.ingreso.getTime());
        return Integer.toString(id) + ";" + titulo + ";" + Integer.toString(anioCreacion) + ";" + lugarCreacion + ";" + String.valueOf(disponible) + ";" + Integer.toString(valor) + ";" + valorMinimo  + ";" + String.valueOf(bloqueado) + ";" + ingreso + ";"  + Integer.toString(tiempoAcordado) + ";" + modalidadPrestamo + ";" + fechaVentaEntrega + ";" + getAutoresToString() + ";" + tipoPieza;
    }


    private String getAutoresToString() {
        String string = "";
        for (Cliente autor : this.autores) {
            string += autor.getNombre() + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    
    public String toString2() {
    	String fechaVentaEntrega = this.fechaVentaEntrega == null ? "" : Long.toString(this.fechaVentaEntrega.getTime());
        return "Id:" + Integer.toString(id) + "\nTitulo:" + titulo + "\nAutor(es):" + getAutoresToString() + "\nAnio de creacion" + Integer.toString(anioCreacion) + "\nLugar de creacion" + lugarCreacion + "\ntipo de pieza" + tipoPieza + "\nDisponibilidad" + String.valueOf(disponible) + "\nValor" + Integer.toString(valor) + "\nNosequeesperobueno"+fechaVentaEntrega;
    }
}