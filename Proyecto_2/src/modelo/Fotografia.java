package modelo;
import java.util.Date;
import java.util.List;

public class Fotografia extends Pieza {
	
	

    private String formato;
    private  String resolucion;
    private String dimensiones;
    private String categoria;
    
    
    
    


    
    




	public Fotografia(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidad, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza, String formato,
			String resolucion, String dimensiones, String categoria) {
		super(id, titulo, anioCreacion, lugarCreacion, disponible, valor, valorMinimo, bloqueado, ingreso,
				tiempoAcordado, modalidad, fechaVentaEntrega, autores, tipoPieza);
		this.formato = formato;
		this.resolucion = resolucion;
		this.dimensiones = dimensiones;
		this.categoria = categoria;
	}
	


	public String getFormato() {
		return formato;
	}



	public void setFormato(String formato) {
		this.formato = formato;
	}



	public String getResolucion() {
		return resolucion;
	}



	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}


	public String getDimensiones() {
		return dimensiones;
	}



	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}



	public String getCategoria() {
		return categoria;
	}




	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}





	@Override
    public String toString() {
        return super.toString() + ";" + this.formato + ";" + this.resolucion + ";" + this.dimensiones + ";" + this.categoria  ;
    }
	
	

    
}

