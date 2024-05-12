package modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Escultura extends Pieza {
	
	
	private String dimensiones;
    private Integer peso;
    private boolean electricidad;
    private String detalles;
    private List<String> materiales;
    
    
    

    
    
    public Escultura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidad, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza, String dimensiones,
			Integer peso, boolean electricidad, String detalles, List<String> materiales) {
		super(id, titulo, anioCreacion, lugarCreacion, disponible, valor, valorMinimo, bloqueado, ingreso,
				tiempoAcordado, modalidad, fechaVentaEntrega, autores, tipoPieza);
		this.dimensiones = dimensiones;
		this.peso = peso;
		this.electricidad = electricidad;
		this.detalles = detalles;
		this.materiales = new ArrayList<>();
	}
    
    

	public String getDimensiones() {
		return dimensiones;
	}



	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}



	public Integer getPeso() {
		return peso;
	}



	public void setPeso(Integer peso) {
		this.peso = peso;
	}



	public boolean isElectricidad() {
		return electricidad;
	}



	public void setElectricidad(boolean electricidad) {
		this.electricidad = electricidad;
	}



	public String getDetalles() {
		return detalles;
	}



	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}



	public List<String> getMateriales() {
		return materiales;
	}



	public void setMateriales(List<String> materiales) {
		this.materiales = materiales;
	}



	@Override
    public String toString() {
        return super.toString() + ";" + this.dimensiones + ";" + this.getMaterialesString() + ";" + this.peso + ";" + this.electricidad + ";" + this.detalles;
    }

    private String getMaterialesString() {
        String string = "";
        for (String material : this.materiales) {
            string += material + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }

    
}


