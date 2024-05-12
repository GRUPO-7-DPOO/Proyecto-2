package modelo;
import java.util.Date;
import java.util.List;

public class Impresiones extends Pieza {
	
	
	private String dimensiones;
    private String formato;
    private String material;
    private  String resolucion;
    private String acabado;
    
    
    
    


    
    
    
    public Impresiones(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidad, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza,
			String dimensiones, String formato, String material, String resolucion, String acabado) {
		super(id, titulo, anioCreacion, lugarCreacion, disponible, valor, valorMinimo, bloqueado, ingreso,
				tiempoAcordado, modalidad, fechaVentaEntrega, autores, tipoPieza);
		this.dimensiones = dimensiones;
		this.formato = formato;
		this.material = material;
		this.resolucion = resolucion;
		this.acabado = acabado;
	}

	
    
    public String getDimensiones() {
		return dimensiones;
	}



	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}



	public String getFormato() {
		return formato;
	}



	public void setFormato(String formato) {
		this.formato = formato;
	}



	public String getMaterial() {
		return material;
	}



	public void setMaterial(String material) {
		this.material = material;
	}



	public String getResolucion() {
		return resolucion;
	}



	public void setResolucion(String resolucion) {
		this.resolucion = resolucion;
	}



	public String getAcabado() {
		return acabado;
	}



	public void setAcabado(String acabado) {
		this.acabado = acabado;
	}



	@Override
    public String toString() {
        return super.toString() + ";" + this.dimensiones + ";" + this.formato + ";" + this.material + ";" + this.resolucion + ";" + this.acabado ;
    }


    
}

