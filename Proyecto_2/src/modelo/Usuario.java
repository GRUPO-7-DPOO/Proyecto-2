package modelo;

public abstract class Usuario {
	protected String tipo;
    protected String nombre;
    protected String login;
    protected String clave;
    protected String telefono;
    protected String correoElectronico;
    protected Galeria galeria;

    public Usuario(String tipo, String nombre, String login, String clave, String telefono, String correoElectronico, Galeria galeria) {
        this.tipo = tipo;
    	this.nombre = nombre;
        this.login = login;
        this.clave = clave;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.galeria = galeria;
    }
    
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getClave() {
        return clave;
    }

    public void setPassword(String clave) {
        this.clave = clave;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }
    
    public void setCorreo(String correo) {
        this.correoElectronico = correo;
    }

    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }

    public String toString() {
        return tipo + ";" + nombre + ";" + login + ";" + clave + ";" + telefono + ";" + correoElectronico;
    }
}
