package modelo;


import java.util.Date;


public class Fotografia extends Pieza {
	private String dimensiones;
	private String resolucion;
	private String formato;
    private String categoria;
 

    public Fotografia(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer tiempoAcordado, String modalidad, String dimensiones, String resolucion, String formato, String categoria) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad);
        this.dimensiones = dimensiones;
        this.resolucion = resolucion;
        this.formato = formato;
        this.categoria = categoria;
       
    }

    public Fotografia(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado, String modalidad, Date fechaVentaEntrega, String dimensiones, String resolucion, String formato, String categoria) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega);
        this.dimensiones = dimensiones;
        this.resolucion = resolucion;
        this.formato = formato;
        this.categoria = categoria;

        
    }
    
    public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public String getResolucion() {
		return resolucion;
	}

	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Override
    public String toString() {
        return super.toString() + ";" + this.dimensiones + ";" + this.resolucion + ";" + this.formato+ ";" + this.categoria;
    }

    
}
