package modelo;
import java.util.Date;
import java.util.List;

public class Video extends Pieza {
	
	

    private Integer duracion;
    private  String resolucion;
    private Float peso;
    private String categoria;
    



	
	
	



	public Video(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidadPrestamo, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza,
			Integer duracion, String resolucion, Float peso, String categoria) {
		super(id, titulo, anioCreacion, lugarCreacion, disponible, valor, valorMinimo, bloqueado, ingreso,
				tiempoAcordado, modalidadPrestamo, fechaVentaEntrega, autores, tipoPieza);
		this.duracion = duracion;
		this.resolucion = resolucion;
		this.peso = peso;
		this.categoria = categoria;
	}






	public Integer getDuracion() {
		return duracion;
	}



	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}


	public String getResolucion() {
		return resolucion;
	}


	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}


	public Float getPeso() {
		return peso;
	}


	public void setPeso(Float peso) {
		this.peso = peso;
	}


	public String getCategoria() {
		return categoria;
	}



	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



















	@Override
    public String toString() {
        return super.toString() + ";" + this.duracion + ";" + this.resolucion + ";" + this.peso + ";" + this.categoria  ;
    }
	
	

    
}

