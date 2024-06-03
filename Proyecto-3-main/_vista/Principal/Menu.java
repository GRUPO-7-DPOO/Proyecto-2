package Principal;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;


import Req.__Autor;
import Req.__Comprador;
import Req.__Empleado;
import Req.__Propietario;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Galeria;
import modelo.Usuario;

public class Menu extends JFrame implements ActionListener{


	
	private ArrayList<String> listaOpciones;
	
	public static final ArrayList<String> opcionesEmpleado_null = new ArrayList<>(Arrays.asList("Salir/Guardar","Administrar piezas","Administrar subastas","Ofertas pendientes", "Compras pendientes", "Administrar usuarios", "Compras hechas", "Perfil", "Historial de un comprador","Historial de un artista","Historial de una pieza"));
	public static final ArrayList<String> opcionesEmpleado_1 = new ArrayList<>(Arrays.asList("Volver","Agregar pieza a exhibicion", "Agregar pieza a bodega", "Cambiar ubicacion de pieza", "Ver piezas de inventario", "Devoluciones pendientes", "Ver piezas vendidas", "Ver piezas devueltas", "Modificar pieza"));
	public static final ArrayList<String> opcionesEmpleado_2 = new ArrayList<>(Arrays.asList("Volver","Ver detalles de la subasta","Crear subasta","Modificar subasta","Cerrar subasta","Agregar pieza a subasta","Remover pieza de subasta","Verificar clientes","Verificar ofertas"));
	public static final ArrayList<String> opcionesEmpleado_5 = new ArrayList<>(Arrays.asList("Volver","Crear usuario","Modificar usuario","Ver usuarios"));
	
	private static final ArrayList<String> opcionesAutor_ = new ArrayList<>(Arrays.asList("Salir/Guardar","Ver mis piezas (Autor)","Perfil"));
	
	private static final ArrayList<String> opcionesComprador_null = new ArrayList<>(Arrays.asList("Salir/Guardar","Ver piezas en exhibicion","Ver piezas en bodega","Ver subastas actuales","Ver mis compras","Ver compras pendientes","Perfil"));
	private static final ArrayList<String> opcionesComprador_1 = new ArrayList<>(Arrays.asList("Volver(Pendiente)"));
	
	private static final ArrayList<String> opcionesPropietario_ = new ArrayList<>(Arrays.asList("Salir","Ver piezas prestadas","Ver piezas vendidas","Perfil"));
		
	public static final String EMPLEADO = "EMPLEADO";
	public static final String AUTOR = "AUTOR";
	public static final String PROPIETARIO = "PROPIETARIO";
	public static final String COMPRADOR = "COMPRADOR";
	
	private Central central;
	private String tipo;
	private Integer opcion;
	private Integer seleccion;
	
	public Menu(String tipo, Integer opcion, Central central) {
		super();
		
		setTitle("Menu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		this.tipo = tipo;
		this.opcion = opcion;
		this.central = central;

		if(tipo == EMPLEADO) {
			if (opcion == null) {
				setListaOpciones(opcionesEmpleado_null);
	        } else if (opcion == 1) {
	        	setListaOpciones(opcionesEmpleado_1);
	        } else if (opcion == 2) {
	        	setListaOpciones(opcionesEmpleado_2);
	        } else if (opcion == 5) {
	        	setListaOpciones(opcionesEmpleado_5);
	        }else {
	        	
	        }
		}else if(tipo == AUTOR) {
			setListaOpciones(opcionesAutor_);
		}else if(tipo == COMPRADOR){
			if (opcion == null) {
				setListaOpciones(opcionesComprador_null);
	        } else if (opcion == 1) {
	        	setListaOpciones(opcionesComprador_1);
	        } else {
	        	
	        }
		}else if(tipo == PROPIETARIO) {
			setListaOpciones(opcionesPropietario_);
		}else {
			System.out.println("3312 repito un 3312 !!!");
		}
		
		setLayout(new GridLayout(listaOpciones.size(),1));
		
		for(int i=0; i<listaOpciones.size() ; i++) {
			JButton ff = new JButton(listaOpciones.get(i));
			ff.setActionCommand(listaOpciones.get(i));
			ff.addActionListener(this);
			add(ff);
		}
		
		pack();
		setVisible(true);
	}
	
	
	
	public ArrayList<String> getListaOpciones() {
		return listaOpciones;
	}



	public void setListaOpciones(ArrayList<String> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}



	public Central getCentral() {
		return central;
	}



	public void setCentral(Central padre) {
		this.central = padre;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public Integer getOpcion() {
		return opcion;
	}



	public void setOpcion(Integer opcion) {
		this.opcion = opcion;
	}



	public Integer getSeleccion() {
		return seleccion;
	}



	public void setSeleccion(Integer seleccion) {
		this.seleccion = seleccion;
	}

	public void encontrar() {
		
		Integer op = getSeleccion();
		
		if(tipo == Menu.EMPLEADO) {
			__Empleado __empleado = central.get__empleado();
			__empleado.setCentral(central);
			if (opcion == null) {
				if(op == 0) {
					salirGuardar();
				} else if (op == 1) {
					__empleado.administrarPiezas();
	            } else if (op == 2) {
	            	__empleado.administrarSubastas();
	            } else if (op == 3) {
	            	__empleado.verificarOfertasPendientes();
	            } else if (op == 4) {
	            	__empleado.verificarComprasPendientes();
	            } else if (op == 5) {
	            	__empleado.administrarUsuarios();
	            } else if (op == 6) {
	            	__empleado.getComprasHechas();
	            } else if (op == 7) {
	            	__empleado.getPerfil();
	            } else if(op == 8) {
	            	__empleado.historiaComprador();
	            } else if(op == 9) {
	            	__empleado.historiaArtista();
	            } else if(op == 10) {
	            	__empleado.historiaPieza();
	            }
				
				
	        } else if (opcion == 1) {
	        	if (op == 0) {
	        		volver(this);
	        	}else if (op == 1 || op == 2) {
	        		__empleado.agregarPieza(op);
	            } else if (op == 3) {
	            	__empleado.cambiarUbicacionPieza();
	            } else if (op == 4) {
	            	__empleado.getPiezasEnExhibicionYEnBodega();
	            } else if (op == 5) {
	            	__empleado.devolucionesPendientes();
	            } else if (op == 6) {
	            	__empleado.getPiezasVendidas();
	            } else if (op == 7) {
	            	__empleado.getPiezasRegresadas();
	            } else if (op == 8) {
	            	__empleado.modificarPieza();
	            }
	        } else if (opcion == 2) {
	        	if(op == 0) {
	        		volver(this);
				} else if (op == 1) {
					__empleado.getDetallesSubasta();
	            } else if (op == 2) {
	            	__empleado.crearSubasta();
	            } else if (op == 3) {
	            	__empleado.modificarSubasta();
	            } else if (op == 4) {
	            	__empleado.cerrarSubasta();
	            } else if (op == 5) {
	            	__empleado.agregarPiezaSubasta();
	            } else if (op == 6) {
	            	__empleado.removerPiezaSubasta();
	            } else if (op == 7) {
	            	__empleado.verificarClientes();
	            } else if (op == 8) {
	            	__empleado.verificarOfertaSubasta();
	            }
	        } else if (opcion == 5) {
	        	if(op == 0) {
	        		volver(this);
				} else if (op == 1) {
					__empleado.crearUsuario();
	            } else if (op == 2) {
	            	__empleado.modificarUsuario();
	            } else if (op == 3) {
	            	__empleado.getUsuarios();
	            }
	        }
		
		}else if(tipo == Menu.AUTOR) {
			__Autor __autor = central.get__autor();
			__autor.setCentral(central);
			if(op == 0) {
				salirGuardar();
			} else if (op == 1) {
               __autor.getPiezasAutorVendidas();
            } else if (op == 2) {
            	__autor.getPerfil();
            } 
		}else if(tipo == Menu.COMPRADOR){
			__Comprador __comprador = central.get__comprador();
			__comprador.setCentral(central);
			if (opcion == null) {
				if(op == 0) {
					salirGuardar();
				} else if (op == 1) {
					__comprador.iniciarCompra(1);
	            } else if (op == 2) {
	            	__comprador.iniciarCompra(2);
	            } else if (op == 3) {
	            	__comprador.verSubastasActuales();
	            } else if (op == 4) {
	                __comprador.getListaPiezas(1);
	            } else if (op == 5) {
	                __comprador.getListaPiezas(2);
	            } else if (op == 6) {
	                __comprador.getPerfil();
	            } 
	        } else if (opcion == 1) {
	        	if(op == 0) {
//	        		volverPendiente();
	        	}
	        } 
		}else if(tipo == Menu.PROPIETARIO) {
			__Propietario __propietario = central.get__propietario();
			__propietario.setCentral(central);
			if(op == 0) {
				salirGuardar();
			} else if (op == 1) {
                __propietario.getPiezasPrestadas();
            } else if (op == 2) {
            	__propietario.getPiezasVendidas();
            } else if (op == 3) {
            	__propietario.getPerfil();
            } 
		}else {
			System.out.println("");
		}
	}
	
public void salirGuardar() {
		
		try {
			Galeria galeria = central.getGaleria()
;        	System.out.println("");
			galeria.guardarDatos();
			central.setGaleria(galeria);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.exit(0);
	}
	
	private void volver(Menu menu) {
		menu.dispose();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String rt = e.getActionCommand();
		for(int i=0; i<listaOpciones.size() ; i++) {
			if(rt.equals(listaOpciones.get(i))) {
				setSeleccion(i);
				encontrar();
				}
			}
		}
}
