package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import excepciones.Error;
import modelo.Cliente;
import modelo.Compra;
import modelo.Galeria;

import modelo.Oferta;

import modelo.Pieza;
import modelo.Subasta;



import modelo.Usuario;


public class mainCliente {
    private Galeria galeria;
    private Usuario usuario;

    public static void main(String[] args) {
        mainCliente app = new mainCliente();
        app.iniciarAplicacion();
    }

    private void iniciarAplicacion() {
        try {
            this.galeria = new Galeria();
            galeria.guardarDatos();
            this.usuario = logIn();
            galeria.guardarDatos();

            // Debugging: imprimir usuarios cargados
            System.out.println("Usuarios cargados:");
            for (Map.Entry<String, Usuario> entry : galeria.getUsuarios().entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
                System.out.println(entry.getValue().getLogin() + ":" + entry.getValue().getClave());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
}
    

    private Usuario logIn() throws Exception {
        System.out.println("Inicio de sesion");
        do {
            String login = input("Usuario: ");
            String password = input("Contrasenia: ").trim();

            if (galeria.getUsuarios().containsKey(login.toLowerCase())) {
                usuario = galeria.getUsuarios().get(login.toLowerCase());


                if (usuario.getClave().equals(password)){
                	if (usuario.getTipo().equals("Cliente")||usuario.getTipo().equals("Autor")) {
                    System.out.println("Inicio de sesion exitoso");
                    iniciarCliente();
                	} else {
                		System.out.println("el tipo de usuario no coinciden");
                	}
                } else {
                    System.out.println("La contraseña no coincide");
                }
            } else {
                System.out.println("El usuario no existe");
            }
        } while (this.usuario == null);
        return null;
    
    }

    public String input(String mensaje) {
        try {
            System.out.println(mensaje);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input = reader.readLine();
            while (input.isEmpty()) {
                System.out.println("Por favor, ingrese un valor.");
                input = reader.readLine();
            }
            return input;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void iniciarCliente() throws Exception {
    	Cliente cliente = (Cliente) this.usuario;
    	int opcion;
        do {
            System.out.println("\n\n" + getMenuComprador(null));
            opcion = Integer.parseInt(input("Opcion: "));
            if (opcion == 1) {
                iniciarCompra(cliente, 1);
            } else if (opcion == 2) {
                iniciarCompra(cliente, 2);
            } else if (opcion == 3) {
                List<Subasta> subastas = galeria.obtenerSubastasActivas();
                System.out.println(getSubastas(subastas));
                Integer eleccion = Integer.parseInt(input("Seleccione la subasta: "));
                Subasta subasta = subastas.get(eleccion-1);
                System.out.println(getDetallesSubasta(subasta));
                boolean verificado = cliente.verificarAccesoASubasta(subasta);
                if (verificado) {
                    eleccion = Integer.parseInt(input("Seleccione la pieza que quiere comprar: "));
                    Pieza pieza = subasta.getPiezas().get(eleccion-1);
                    Oferta mayorOferta = subasta.getMayorOferta(pieza);
                    String mayorOfertaStr = mayorOferta == null ? "0" : Integer.toString(mayorOferta.getValorOferta());
                    Integer valorOferta = Integer.parseInt(input("El valor inicial de la pieza es: " + pieza.getValor() + "\nLa mayor oferta actual es: " + mayorOfertaStr + "\nIngrese su oferta: "));
                    cliente.ofertaSubasta(subasta, pieza, valorOferta);
                } else {
                    System.out.println("No tiene acceso a esta subasta\nSolicitando acceso...");
                }
                System.out.println("Operacion exitosa");
            } else if (opcion == 4) {
                System.out.println("Piezas compradas:\n" + getListaPiezas(cliente.getAutoriaVendidasCompradas()));
            } else if (opcion == 5) {
                System.out.println("Compras pendientes\n" + getListaPiezas(cliente.getPrestadasEnEspera()));
            } else if (opcion == 6) {
            	String nombre = input("Seleccione la pieza de la que desea ver el historial: ").toLowerCase();
                historialPieza(cliente, nombre);
            } else if (opcion == 7) {
            	String login = input("Ingrese el login del autor: ");
            	historialAutor(cliente, login);
            }
            
            
            else if (opcion == 7) {
                System.out.println(getPerfil(cliente));
            }
        } while (opcion != 0);
    }

    private String getMenuComprador(Integer opcion) {
        if (opcion == null) {
            return "0. Salir/Guardar\n1. Ver piezas en exhibicion\n2. Ver piezas en bodega\n3. Ver subastas actuales\n4. Ver mis compras\n5. Ver compras pendientes\n6. Ver historial de una pieza\n7.Perfil\n";
        } else if (opcion == 1) {
            return "0. Volver\n(Pendiente)";
        }
        return "0. Salir/Guardar\n1. Ver piezas en exhibicion\n2. Ver piezas en bodega\n3. Ver subastas actuales\n4. Ver mis compras\n5. Ver compras pendientes\n6. Perfil\n";
    }
    



    private void iniciarCompra(Cliente cliente, int opcion) throws Error {
        List<Pieza> listaPiezas = null;
        if (opcion == 1) {
            listaPiezas = galeria.getListaEnExhibicion();
            System.out.println("Piezas en exhibicion:\n" + getListaPiezas(listaPiezas));
        } else if (opcion == 2) {
            listaPiezas = galeria.getListaEnBodega();
            System.out.println("Piezas en bodega:\n" + getListaPiezas(listaPiezas));
        }
        Integer eleccion = Integer.parseInt(input("Seleccione la pieza a comprar: "));
        Pieza pieza = listaPiezas.get(eleccion-1);
        cliente.ofertaDeCompra(pieza);
        System.out.println("Oferta generada con exito");
    }
	    
    private String getListaPiezas(List<Pieza> listaPiezas) {
        String string = "";
        for (int i = 1; i <= listaPiezas.size(); i++) {
            Pieza pieza = listaPiezas.get(i-1);
            string += i + "- Titulo: " + pieza.getTitulo() + " Precio: " + pieza.getValor() + "\n";
        }
		return string;
    }
        

	

    



	


	

    

	    

    private String getPerfil(Usuario usuario) {
	
	        return "Tipo Usuario: " + usuario.getTipo() + "\nNombre: " + usuario.getNombre() + "\nLogin: " + usuario.getLogin() + "\nTelefono: " + usuario.getTelefono() + "\nCorreo: " + usuario.getCorreoElectronico();
	   
	    }
	    


	    

    private String getSubastas(List<Subasta> subastas) {
	        String string = "Subastas:\n";
	        for (int i = 1; i <= subastas.size(); i++) {
	            Subasta subasta = subastas.get(i-1);
	            string += i + "- Nombre: " + subasta.getNombre() + " Activo: " + subasta.isActiva() + "\n";
	        }
	        return string;
	    }
	    

    private String getDetallesSubasta(Subasta subasta) {
	        String string = "Detalles de subasta:\n";
	        string += "Nombre: " + subasta.getNombre();
	        string += "\nActiva: " + subasta.isActiva();
	        string += "\nPiezas:\n";
	        List<Pieza> piezas = subasta.getPiezas();
	        for (int i = 1; i <= piezas.size(); i++) {
	            Pieza pieza = piezas.get(i-1);
	            Oferta mayorOferta = subasta.getMayorOferta(pieza);
	            String mayorOfertaStr = mayorOferta == null ? "ninguna" : Integer.toString(mayorOferta.getValorOferta());
	            string += i + "- Titulo: " + pieza.getTitulo() + " Anio creacion: " + pieza.getAnioCreacion() + " Valor: " + pieza.getValor() + " Mayor oferta: " + mayorOfertaStr + "\n"; 
	        }
	        string += "\nClientes verificados:\n";
	        List<Cliente> clientesVerificados = subasta.getVerificados();
	        for (int i = 1; i <= clientesVerificados.size(); i++) {
	            Cliente cliente = clientesVerificados.get(i-1);
	            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreoElectronico() + "\n"; 
	        }
	        string += "\nClientes en espera:\n";
	        List<Cliente> clientesEnEspera = subasta.getEnEspera();
	        for (int i = 1; i <= clientesEnEspera.size(); i++) {
	            Cliente cliente = clientesEnEspera.get(i-1);
	            string += i + "- Nombre: " + cliente.getNombre() + " Login: " + cliente.getLogin() + " Correo: " + cliente.getCorreoElectronico() + "\n"; 
	        }
	        return string;
	    }
	    

    

	    


	    
	   
    private void historialPieza(Cliente cliente, String nombre) throws Exception {
	        String detalles = null;
	        List<String> compraPieza = new ArrayList<>();
	        
	        System.out.println("Historial de la pieza\n");
	        
	        
	        if (cliente.getGaleria().getEnExhibicion().containsKey(nombre) || cliente.getGaleria().getEnBodega().containsKey(nombre)) {
	        
		        for (Pieza Piezas : cliente.getGaleria().getEnExhibicion().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : cliente.getGaleria().getCompras()) {
		                    if (compras.getPieza().getTitulo().equals(nombre)) {
		                        compraPieza.add(compras.toString2());
		                    }
		                }
		                
		                System.out.println(detalles);
		                
		            }
		        }
		        
		        for (Pieza Piezas : cliente.getGaleria().getEnBodega().values()) {
		            if (Piezas.getTitulo().equals(nombre)) {
		
		                detalles = Piezas.toString2();
		                for (Compra compras : cliente.getGaleria().getCompras()) {
		                    if (compras.getPieza().getTitulo().equals(nombre)) {
		                        compraPieza.add(compras.toString2());
		                    }
		                }
		                
		                System.out.println(detalles);
		                
		            }
		        }
		        
		        
		        
		        if (compraPieza.size()!= 0) {
			        System.out.println("Historial de compra de la pieza\n");
			        
			        for (String elemento : compraPieza) {
			            System.out.println(elemento);
			        }
		        } else {
		        	System.out.println("Historial de compra de la pieza\n");
		        }
	        } else {
	        	System.out.println("La pieza no se ha encontrado");
	        }
	        
	    }
	
	    

    private void historialAutor(Cliente cliente, String login) throws Error {
	   	 
	    	List<String> verificacionCompra = new ArrayList<>();
	    	
	   	if (cliente.getGaleria().getUsuarios().containsKey(login)) {
	   		usuario = galeria.getUsuarios().get(login);
	   		Cliente cliente1 = (Cliente) usuario;
	   		List<Pieza> Piezas = cliente1.getAutoriaVendidasCompradas();
	   		String respuesta = cliente1.toStringPieza2(Piezas);
	   		
	
			System.out.println("Piezas del autor: \n");
			System.out.println(respuesta);
			
	   		for (Compra compras : cliente.getGaleria().getCompras()) {
	   			for (int i = 0; i < Piezas.size(); i++) {
	   				if (compras.getPieza()== Piezas) {
	   					verificacionCompra.add(compras.toString4());
	   				}	
	   			
	   			}
	   			
	   		}
	   		
	   		if (verificacionCompra.size() != 0) {
	   			System.out.println("Historial de compra de sus piezas: \n");
	   			System.out.println(verificacionCompra);
	   		} else {
	   			System.out.println("Las piezas del autor no han sido compradas.");	
	   			}
	   			
	   			
	   			
	   		
	
	   } else {
			System.out.println("El autor solicitado no se ha encontrado. Intenta de nuevo");	
	   }
	    
	     
	}
	}
