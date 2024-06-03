package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pintura extends Pieza {
	private String dimensiones;
	private String cuidados;
	private String detalles;
    private String tecnica;
    private List<MaterialConstruccion> materiales;

    public Pintura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer tiempoAcordado, String modalidad, String dimensiones, String cuidados, String tecnica) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad);
        this.dimensiones = dimensiones;
        this.cuidados = cuidados;
        this.tecnica = tecnica;
        this.materiales = new ArrayList<>();
    }

    public Pintura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado, String modalidad, Date fechaVentaEntrega, String dimensiones, String cuidados, String tecnica,  List<MaterialConstruccion> materiales) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega);
        this.dimensiones = dimensiones;
        this.cuidados = cuidados;
        this.tecnica = tecnica;

        this.materiales = materiales;
    }

    
    public String getDimensiones() {
		return dimensiones;
	}

	public void setDimensiones(String dimensiones) {
		this.dimensiones = dimensiones;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	public String getTecnica() {
		return tecnica;
	}

	public void setTecnica(String tecnica) {
		this.tecnica = tecnica;
	}

	public List<MaterialConstruccion> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialConstruccion> materiales) {
        this.materiales = materiales;
    }
    
    @Override
    public String toString() {
        return super.toString() + ";" + this.dimensiones + ";" + this.cuidados + ";" + this.tecnica+ ";" + getMaterialesString();
    }

    private String getMaterialesString() {
        String string = "";
        for (MaterialConstruccion material : this.materiales) {
            string += material.getNombre() + ",";
        }
        string = string.length() > 0 ? string.substring(0, string.length()-1) : string;
        return string;
    }
    
}
