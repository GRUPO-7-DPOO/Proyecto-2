package modelo;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pintura extends Pieza {
	
	

    private String dimensiones;
    private List<String> materiales;
    private String cuidados;
    private String tecnica;
    




	public Pintura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, boolean disponible,
			Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado,
			String modalidadPrestamo, Date fechaVentaEntrega, List<Cliente> autores, String tipoPieza,
			String dimensiones, List<String> materiales, String cuidados, String tecnica) {
		super(id, titulo, anioCreacion, lugarCreacion, disponible, valor, valorMinimo, bloqueado, ingreso,
				tiempoAcordado, modalidadPrestamo, fechaVentaEntrega, autores, tipoPieza);
		this.dimensiones = dimensiones;
		this.materiales = new ArrayList<>();
		this.cuidados = cuidados;
		this.tecnica = tecnica;
	}





	public String getDimensiones() {
		return dimensiones;
	}





	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}





	public List<String> getMateriales() {
		return materiales;
	}





	public void setMateriales(List<String> materiales) {
		this.materiales = materiales;
	}





	public String getCuidados() {
		return cuidados;
	}





	public void setCuidados(String cuidados) {
		this.cuidados = cuidados;
	}





	public String getTecnica() {
		return tecnica;
	}





	public void setTecnica(String tecnica) {
		this.tecnica = tecnica;
	}





	@Override
    public String toString2() {
        return super.toString() + ";" + this.dimensiones + ";" + this.getMaterialesString() + ";" + this.cuidados + ";" + this.tecnica ;
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

