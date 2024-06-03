package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import excepciones.OperacionIlegal;

public abstract class Pieza {
    protected Integer id;
    protected String titulo;
    protected Integer anioCreacion;
    protected String lugarCreacion;

    protected String tipo;
    protected boolean disponible;
    protected Integer valor;
    protected Integer valorMinimo;
    protected boolean bloqueado;
    protected Date ingreso;
    protected Integer tiempoAcordado;
    protected String modalidad;
    protected Date fechaVentaEntrega;
    protected List<Cliente> autores;

    public Pieza(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer tiempoAcordado, String modalidad) {
        this.id = id;
        this.titulo = titulo;
        this.anioCreacion = anioCreacion;
        this.lugarCreacion = lugarCreacion;
        this.tipo = tipo;
        this.disponible = disponible;
        this.valor = valor;
        this.ingreso = new Date();
        this.tiempoAcordado = tiempoAcordado;
        this.modalidad = modalidad;
        this.autores = new ArrayList<>();
    }

    public Pieza(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado, String modalidad, Date fechaVentaEntrega) {
        this.id = id;
        this.titulo = titulo;
        this.anioCreacion = anioCreacion;
        this.lugarCreacion = lugarCreacion;
        this.tipo = tipo;
        this.disponible = disponible;
        this.valor = valor;
        this.valorMinimo = valorMinimo;
        this.bloqueado = bloqueado;
        this.ingreso = ingreso;
        this.tiempoAcordado = tiempoAcordado;
        this.modalidad = modalidad;
        this.fechaVentaEntrega = fechaVentaEntrega;
        this.autores = new ArrayList<>();
    }

    public Cliente agregarAutor(Cliente autor) throws OperacionIlegal{
        if (!autor.getTipo().equals("Autor")) {
            throw new OperacionIlegal("El autor que quiere agregar no tiene el rol necesario para realizar esta operacion.");
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

    

    public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
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
        return id + ";" + titulo + ";" + anioCreacion + ";" + lugarCreacion + ";" + tipo+ ";" + disponible + ";" + valor + ";" + valorMinimo + ";" + bloqueado + ";" + ingreso.getTime() + ";" + tiempoAcordado + ";" + modalidad + ";" +  fechaVentaEntrega;
    }
}
