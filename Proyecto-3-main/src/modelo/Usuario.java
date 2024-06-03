package modelo;

public abstract class Usuario {
    protected String nombre;
    protected String login;
    protected String password;
    protected String telefono;
    protected String correo;
    protected String tipo;
    
    protected Galeria galeria;

    public Usuario(String nombre, String login, String password, String telefono, String correo, Galeria galeria, String tipo) {
        this.nombre = nombre;
        this.login = login;
        this.password = password;
        this.telefono = telefono;
        this.correo = correo;
        this.tipo = tipo;
        this.galeria = galeria;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setGaleria(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return nombre + ";" + login + ";" + password + ";" + telefono + ";" + correo + ";" + tipo;
    }
}
