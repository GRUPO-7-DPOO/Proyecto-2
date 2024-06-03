package Principal;

import Req.__Autor;
import Req.__Comprador;
import Req.__Empleado;
import Req.__Propietario;
import modelo.Galeria;
import modelo.Usuario;

public class Central{

	private Galeria galeria;
    private Usuario usuario;
    private __Empleado __empleado;
    private __Autor __autor;
    private __Comprador __comprador;
    private __Propietario __propietario;
    
	public Central() {
		super();
		try {
			setGaleria(new Galeria());
			new LogIn(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Galeria getGaleria() {
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public __Empleado get__empleado() {
		return __empleado;
	}

	public void set__empleado(__Empleado __empleado) {
		this.__empleado = __empleado;
	}

	public __Autor get__autor() {
		return __autor;
	}

	public void set__autor(__Autor __autor) {
		this.__autor = __autor;
	}

	public __Comprador get__comprador() {
		return __comprador;
	}

	public void set__comprador(__Comprador __comprador) {
		this.__comprador = __comprador;
	}

	public __Propietario get__propietario() {
		return __propietario;
	}

	public void set__propietario(__Propietario __propietario) {
		this.__propietario = __propietario;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	 new Central();
	}


}
