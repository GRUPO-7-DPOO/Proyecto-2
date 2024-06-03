package modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Escultura extends Pieza {
	private String dimensiones;
	
 
    private Integer peso;
    private boolean electricidad;
    private String detalles;
    private List<MaterialConstruccion> materiales;

    public Escultura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer tiempoAcordado, String modalidad, String dimensiones, Integer peso, boolean electricidad, String detalles) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad);
        this.dimensiones = dimensiones;
        this.peso = peso;
        this.electricidad = electricidad;
        this.detalles = detalles;
        this.materiales = new ArrayList<>();
    }

    public Escultura(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado, String modalidad, Date fechaVentaEntrega, String dimensiones, Integer peso, boolean electricidad, String detalles, List<MaterialConstruccion> materiales) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega);
        this.dimensiones = dimensiones;
        this.peso = peso;
        this.electricidad = electricidad;
        this.detalles = detalles;
        this.materiales = materiales;
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

    public List<MaterialConstruccion> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<MaterialConstruccion> materiales) {
        this.materiales = materiales;
    }
    
    @Override
    public String toString() {
        return super.toString() + ";" + this.dimensiones + ";" +  this.peso + ";" + this.electricidad + ";" + this.detalles + ";" + getMaterialesString();
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
