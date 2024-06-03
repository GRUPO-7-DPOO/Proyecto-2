package modelo;

import java.util.Date;

public class Otro extends Pieza {
    private String detalles;

    public Otro(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer tiempoAcordado, String modalidad, String detalles) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, tiempoAcordado, modalidad);
        this.detalles = detalles;
    }

    public Otro(Integer id, String titulo, Integer anioCreacion, String lugarCreacion, String tipo, boolean disponible, Integer valor, Integer valorMinimo, boolean bloqueado, Date ingreso, Integer tiempoAcordado, String modalidad, Date fechaVentaEntrega, String detalles) {
        super(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega);
        this.detalles = detalles;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    @Override
    public String toString() {
        return super.toString() + ";" + detalles;
    }

}
