package modelo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Galeria {
    private Map<String, Pieza> enExhibicion;
    private Map<String, Pieza>  enBodega;
    private List<Pieza> enBodegaLista;
    private List<Pieza> enExhibicionLista;
    private List<Pieza> vendidas;
    private List<Pieza> regresadas;
    private List<Compra> compras;
    private List<Oferta> ofertas;
    private List<Subasta> subastas;
    private Map<String, Usuario> usuarios;
    Integer secuenciaPiezas;
    private Integer secuenciaOfertas;
    private File archivoDeSecuencias = new File("data/secuencias.txt");
    private File archivoDePiezas = new File("data/piezas.txt");
    private File archivoDeUsuarios = new File("data/usuarios.txt");
    private File archivoDeOfertas = new File("data/ofertas.txt");
    private File archivoDeCompras = new File("data/compras.txt");
    private File archivoDeSubastas = new File("data/subastas.txt");

    public Galeria() throws Exception {
        this.enExhibicion = new HashMap<>();
        this.enBodega = new HashMap<>();
        this.vendidas = new ArrayList<>();
        this.regresadas = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.subastas = new ArrayList<>();
        this.usuarios = new HashMap<>();
        this.enBodegaLista = new ArrayList<>();
        this.enExhibicionLista = new ArrayList<>();
        cargarDatos();
    }

    public Pieza agregerAExhibicion(Pieza pieza) {
        this.enExhibicion.put(pieza.getTitulo(), pieza);
        return pieza;
    }
    
    
    public List<Pieza > getListaEnExhibicion () {

        for (Pieza pieza: this.enBodega.values()) {
        	enExhibicionLista.add(pieza);
        	
        }
        return enBodegaLista;
    }
    
    public List<Pieza > getListaEnBodega () {

        for (Pieza pieza: this.enBodega.values()) {
        	enBodegaLista.add(pieza);
        	
        }
        return enBodegaLista;
    }

    public Pieza removerDeExhibicion(Pieza pieza) {
        this.enExhibicion.remove(pieza.getTitulo());
        return pieza;
    }

    public Pieza agregerABodega(Pieza pieza) {
        this.enBodega.put(pieza.getTitulo(), pieza);
        return pieza;
    }

    public Pieza removerDeBodega(Pieza pieza) {
        this.enBodega.remove(pieza.getTitulo());
        return pieza;
    }

    public Pieza agregerAVendidas(Pieza pieza) {
        this.vendidas.add(pieza);
        return pieza;
    }

    public Pieza agregerARegresadas(Pieza pieza) {
        this.regresadas.add(pieza);
        return pieza;
    }

    public Compra agregerACompras(Compra compra) {
    	this.compras.add(compra);
        return compra;
    }

    public Oferta agregerAOfertas(Oferta oferta) {
        this.ofertas.add(oferta);
        return oferta;
    }

    public Subasta agregerASubastas(Subasta subasta) {
        this.subastas.add(subasta);
        return subasta;
    }

    public List<Subasta> obtenerSubastasActivas() {
        List<Subasta> newList = new ArrayList<>();
        for (Subasta subasta : this.subastas) {
            if (subasta.isActiva()) {
                newList.add(subasta);
            }
        }
        return newList;
    }

    private void cargarDatos() throws Exception {
        cargarSecuencia();
        cargarSubastas();
        
        cargarPiezas();
        
        cargarUsuarios();
        
        cargarCompras();
        cargarOfertas();
        
    }

    private void cargarSecuencia() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoDeSecuencias));
        String[] informacion = br.readLine().split(";");
        secuenciaPiezas = Integer.parseInt(informacion[0]);
        secuenciaOfertas = Integer.parseInt(informacion[1]);
        br.close();
    }
    
    private void cargarSubastas() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoDeSubastas));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            String nombre = informacion[0];
            boolean activa = Boolean.parseBoolean(informacion[1]);
            String[] aceptadasStr;
            if (informacion[2].isEmpty()) {
                aceptadasStr = null;
            } else {
                aceptadasStr = informacion[2].split(",");
            }

            String[] pendientesStr;
            if (informacion[3].isEmpty()) {
                pendientesStr = null;
            } else {
                pendientesStr = informacion[3].split(",");
            }

            String[] verificadosStr;
            if (informacion[4].isEmpty()) {
                verificadosStr = null;
            } else {
                verificadosStr = informacion[4].split(",");
            }

            String[] enEsperaStr;
            if (informacion[5].isEmpty()) {
                enEsperaStr = null;
            } else {
                enEsperaStr = informacion[5].split(",");
            }

            String[] piezasStr;
            if (informacion[6].isEmpty()) {
                piezasStr = null;
            } else {
                piezasStr = informacion[6].split(",");
            }

            Subasta subasta = new Subasta(nombre, activa);
            if (aceptadasStr != null) {
                for (String aceptada : aceptadasStr) {
                    subasta.getAceptadas().add(getOfertaPorId(Integer.parseInt(aceptada)));
                }
            }
            if (pendientesStr != null) {
                for (String pendiente : pendientesStr) {
                    subasta.getPendientes().add(getOfertaPorId(Integer.parseInt(pendiente)));
                }
            }
            if (verificadosStr != null) {
                for (String verificado : verificadosStr) {
                    subasta.getVerificados().add((Cliente) usuarios.get(verificado));
                }
            }
            if (enEsperaStr != null) {
                for (String enEspera : enEsperaStr) {
                    subasta.getEnEspera().add((Cliente) usuarios.get(enEspera));
                }
            }
            if (piezasStr != null) {
                for (String pieza : piezasStr) {
                    subasta.getPiezas().add(getPiezaPorId(Integer.parseInt(pieza)));
                }
            }
            subastas.add(subasta);
            linea = br.readLine();
        }
        br.close();
    }
    
    
    private void cargarUsuarios() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoDeUsuarios));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            String tipo = informacion[0];
            String nombre = informacion[1];
            String login = informacion[2];
            String password = informacion[3];
            String telefono;
            if (informacion[4].isEmpty()) {
                telefono = null;
            } else {
                telefono = informacion[4];
            }
            String correo;
            if (informacion[5].isEmpty()) {
                correo = null;
            } else {
                correo = informacion[5];
            }

            Usuario usuario;
            if (tipo.equals("Comprador") || tipo.equals("Autor")) {
                Integer balance = Integer.parseInt(informacion[6]);
                String[] prestadasEnEsperaStr;
                if (informacion[7].isEmpty()) {
                    prestadasEnEsperaStr = null;
                } else {
                    prestadasEnEsperaStr = informacion[7].split(",");
                }

                String[] autoriaVendidasCompradasStr;
                if (informacion[8].isEmpty()) {
                    autoriaVendidasCompradasStr = null;
                } else {
                    autoriaVendidasCompradasStr = informacion[8].split(",");
                }

                Cliente cliente = new Cliente(tipo, nombre, login, password, telefono, correo, balance, this);
                if (prestadasEnEsperaStr != null){
                    for (String enEspera : prestadasEnEsperaStr) {
                        Integer idPieza = Integer.parseInt(enEspera);
                        Pieza pieza = getPiezaPorId(idPieza);
                        cliente.getPrestadasEnEspera().add(pieza);
                    }
                }
                if (autoriaVendidasCompradasStr != null){
                    for (String autoriaVendidaComprada : autoriaVendidasCompradasStr) {
                        Integer idPieza = Integer.parseInt(autoriaVendidaComprada);
                        Pieza pieza = getPiezaPorId(idPieza);
                        cliente.getAutoriaVendidasCompradas().add(pieza);
                        
                        if (tipo.equals("Autor")) {
                            pieza.getAutores().add(cliente);
                        }

                    }
                }
                usuario = cliente;
            } else if (tipo.equals("Administrador")) { 
            	usuario = new Admin(tipo, nombre, login, password, telefono, correo, this);
        	}else {
                
                
                usuario = new Empleado(tipo, nombre, login, password, telefono, correo, this);
            }
            usuarios.put(login, usuario);
            linea = br.readLine();
        }
        br.close();
    }

    private void cargarPiezas() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoDePiezas));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
         
            
            
            System.out.println("Información de la línea:");
            for (int i = 0; i < informacion.length; i++) {
                System.out.println("Elemento " + i + ": " + informacion[i]);
            }
            
            Integer id = Integer.parseInt(informacion[0]);
            System.out.println("ID de la pieza: " + id);
            
            String titulo = informacion[1];
            Integer anioCreacion = Integer.parseInt(informacion[2]);
            String lugarCreacion = informacion[3];
            String tipoPieza = informacion[4];
            boolean disponible = informacion[5].equals("true");

            Integer valor = Integer.parseInt(informacion[6]);
            Integer valorMinimo;
            if (informacion[7].isEmpty()) {
                valorMinimo = null;
            } else {
                valorMinimo = Integer.parseInt(informacion[7]);
            }
            boolean bloqueado = Boolean.parseBoolean(informacion[8]);
            Date ingreso = new Date(Long.parseLong(informacion[9]));
            Integer tiempoAcordado = Integer.parseInt(informacion[10]);
            String modalidadPrestamo = informacion[11];
            
            Date fechaVentaEntrega = informacion[12].isEmpty() ? null : new Date(Long.parseLong(informacion[12]));;
            
            Integer ubicacion = null;
            Pieza pieza;
            if (tipoPieza.equals("Escultura")) {
                String dimensiones = informacion[13];
                
                String[] materialesStr = informacion[14].split(",");
                
                List<String> materialesLista = new ArrayList<>();
                for (String material : materialesStr) {
                    materialesLista.add(new String(material.trim()));
                }
                
                Integer peso = Integer.parseInt(informacion[15]);
                boolean electricidad = Boolean.parseBoolean(informacion[16]);
                String detalles = informacion[17];
                ubicacion = Integer.parseInt(informacion[18]);
                
                pieza = new Escultura(id, titulo, anioCreacion, lugarCreacion, tipoPieza,  disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidadPrestamo, fechaVentaEntrega, dimensiones,materialesLista, peso, electricidad, detalles);
            } else if (tipoPieza.equals("Pintura")) {
            	
                String dimensiones = informacion[13];
                
                String[] materialesStr = informacion[14].split(",");
                
                List<String> materialesLista = new ArrayList<>();
                for (String material : materialesStr) {
                    materialesLista.add(new String(material.trim()));
                }
                
                String cuidados = informacion[15];
                
                String tecnica = informacion[16];
                
                ubicacion = Integer.parseInt(informacion[17]);
                
                pieza = new Pintura(id,titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor,valorMinimo,bloqueado,ingreso, tiempoAcordado, modalidadPrestamo,fechaVentaEntrega, dimensiones, materialesLista, cuidados, tecnica);
                
            } else if (tipoPieza.equals("Video")) {
            	
                Integer duracion = Integer.parseInt(informacion[13]);
                String resolucion = informacion[14];
                Float peso = Float.parseFloat(informacion[15]);          
                String categoria = informacion[16];
                ubicacion = Integer.parseInt(informacion[17]);
                
                pieza = new Video(id, titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidadPrestamo,fechaVentaEntrega, duracion, resolucion, peso, categoria);
                
            } else if (tipoPieza.equals("Impresiones")) {
            	
                String dimensiones = informacion[13];
                String formato = informacion[14];
                String material = informacion[15];          
                String resolucion = informacion[16];
                String acabado = informacion[17];
                ubicacion = Integer.parseInt(informacion[18]);
                
                pieza = new Impresiones(id, titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor,valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidadPrestamo,fechaVentaEntrega, dimensiones, formato, material, resolucion, acabado);
                
            } else  {
            	
              
                String formato = informacion[14];
                String resolucion = informacion[15];          
                String dimensiones = informacion[16];
                String categoria = informacion[17];
                ubicacion = Integer.parseInt(informacion[18]);
                 
                pieza = new Fotografia(id, titulo, anioCreacion, lugarCreacion, tipoPieza, disponible, valor,valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidadPrestamo, fechaVentaEntrega, formato,  resolucion, dimensiones, categoria);
                
            	
            } 

            
            if (ubicacion == 1) {
                this.enExhibicion.put(pieza.getTitulo(), pieza);
            } else if (ubicacion == 2) {
                this.enBodega.put(pieza.getTitulo(), pieza);
            } else if (ubicacion == 3) {
                this.vendidas.add(pieza);
            } else if (ubicacion == 4) {
                this.regresadas.add(pieza);
            }
            linea = br.readLine();
        }
        br.close();
    }



    private void cargarCompras() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoDeCompras));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            Integer valorPagado = Integer.parseInt(informacion[0]);
            TipoPago metodoPago = TipoPago.valueOf(informacion[1]);
            Cliente cliente = (Cliente) usuarios.get(informacion[2]);
            Pieza pieza = getPiezaPorId(Integer.parseInt(informacion[3]));
            String login = informacion[4];
            String fecha = informacion[5];
            Compra compra = new Compra(valorPagado, metodoPago, cliente, login, pieza, fecha);
            compras.add(compra);
            linea = br.readLine();
        }
        br.close();
    }
    
    private void cargarOfertas() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(archivoDeOfertas));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            Integer id = Integer.parseInt(informacion[0]);
            Integer valorOferta = Integer.parseInt(informacion[1]);
            boolean revisada = Boolean.parseBoolean(informacion[2]);
            Cliente cliente = (Cliente) usuarios.get(informacion[3]);
            Pieza pieza = getPiezaPorId(Integer.parseInt(informacion[4]));
            boolean aceptada = Boolean.parseBoolean(informacion[5]);
            boolean pagado = Boolean.parseBoolean(informacion[6]);
            Oferta oferta = new Oferta(id, valorOferta, revisada, cliente, pieza, aceptada, pagado);
            ofertas.add(oferta);
            linea = br.readLine();
        }
        br.close();
    }
    
    

    public void guardarDatos() throws Exception {
        guardarSecuencia();
        guardarSubastas();
        guardarPiezas();
        guardarUsuarios();
        
        guardarCompras();
        guardarOfertas();
        
    }

    public void guardarSecuencia() throws Exception {
        String string = Integer.toString(secuenciaPiezas) + ";" + Integer.toString(secuenciaOfertas);
        guardarArchivo(string, archivoDeSecuencias);
    }

    private void guardarSubastas() throws Exception {
        String informacion = "";
        for (Subasta subasta : subastas) {
            informacion += subasta.toString() + "\n";
        }
        guardarArchivo(informacion, archivoDeSubastas);
    }
    
    private void guardarUsuarios() throws Exception {
        String info = "";
        for (Usuario usuario : usuarios.values()) {
            String ubicacion = usuario instanceof Cliente ? "1" : "2";
            info += usuario.toString() + ";" + ubicacion + "\n";
        }
        guardarArchivo(info, archivoDeUsuarios);
    }
    
    
  
    
    private void guardarPiezas() throws Exception {
        String info = "";
        for (Pieza pieza : this.enExhibicion.values()) {
        	if (pieza.getTipoPieza().equals("Pintura")) {
        		Pintura pieza1 = (Pintura) pieza;
        		info += pieza1.toString2() + ";1\n";
        	} else if (pieza.getTipoPieza().equals("Escultura")) {
        		Escultura pieza1 = (Escultura) pieza;
        		info += pieza1.toString() + ";1\n";
        	} else if (pieza.getTipoPieza().equals("Fotografia")) {
        		Fotografia pieza1 = (Fotografia) pieza;
        		info += pieza1.toString() + ";1\n";
        	} else if (pieza.getTipoPieza().equals("Impresiones")) {
        		Impresiones pieza1 = (Impresiones) pieza;
        		info += pieza1.toString() + ";1\n";
        	} else {
        		Video pieza1 = (Video) pieza;
        		info += pieza1.toString() + ";1\n";
        		
        	}
        	
  
        }
        for (Pieza pieza : this.enBodega.values()) {
        	if (pieza.getTipoPieza().equals("Pintura")) {
        		Pintura pieza1 = (Pintura) pieza;
        		info += pieza1.toString2() + ";2\n";
        	} else if (pieza.getTipoPieza().equals("Escultura")) {
        		Escultura pieza1 = (Escultura) pieza;
        		info += pieza1.toString() + ";2\n";
        	} else if (pieza.getTipoPieza().equals("Fotografia")) {
        		Fotografia pieza1 = (Fotografia) pieza;
        		info += pieza1.toString() + ";2\n";
        	} else if (pieza.getTipoPieza().equals("Impresiones")) {
        		Impresiones pieza1 = (Impresiones) pieza;
        		info += pieza1.toString() + ";2\n";
        	} else {
        		Video pieza1 = (Video) pieza;
        		info += pieza1.toString() + ";2\n";
        	
        }
        }
        for (Pieza pieza : this.vendidas) {
        	if (pieza.getTipoPieza().equals("Pintura")) {
        		Pintura pieza1 = (Pintura) pieza;
        		info += pieza1.toString2() + ";3\n";
        	} else if (pieza.getTipoPieza().equals("Escultura")) {
        		Escultura pieza1 = (Escultura) pieza;
        		info += pieza1.toString() + ";3\n";
        	} else if (pieza.getTipoPieza().equals("Fotografia")) {
        		Fotografia pieza1 = (Fotografia) pieza;
        		info += pieza1.toString() + ";3\n";
        	} else if (pieza.getTipoPieza().equals("Impresiones")) {
        		Impresiones pieza1 = (Impresiones) pieza;
        		info += pieza1.toString() + ";3\n";
        	} else {
        		Video pieza1 = (Video) pieza;
        		info += pieza1.toString() + ";3\n";
        	}
        }
        for (Pieza pieza : this.regresadas) {
        	if (pieza.getTipoPieza().equals("Pintura")) {
        		Pintura pieza1 = (Pintura) pieza;
        		info += pieza1.toString2() + ";4\n";
        	} else if (pieza.getTipoPieza().equals("Escultura")) {
        		Escultura pieza1 = (Escultura) pieza;
        		info += pieza1.toString() + ";4\n";
        	} else if (pieza.getTipoPieza().equals("Fotografia")) {
        		Fotografia pieza1 = (Fotografia) pieza;
        		info += pieza1.toString() + ";4\n";
        	} else if (pieza.getTipoPieza().equals("Impresiones")) {
        		Impresiones pieza1 = (Impresiones) pieza;
        		info += pieza1.toString() + ";4\n";
        	} else {
        		Video pieza1 = (Video) pieza;
        		info += pieza1.toString() + ";4\n";
        	}
        }
        guardarArchivo(info, archivoDePiezas);
    }


    private void guardarCompras() throws Exception {
        String info = "";
        for (Compra compra : compras) {
            info += compra.toString() + "\n";
        }
        guardarArchivo(info, archivoDeCompras);
    }
    
    private void guardarOfertas() throws Exception {
        String info = "";
        for (Oferta oferta : ofertas) {
            info += oferta.toString() + "\n";
        }
        guardarArchivo(info , archivoDeOfertas);
    }

    

    private void guardarArchivo(String informacion, File archivo) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(informacion);
        bw.close();
    }


    public Integer getSecuenciaOfertas() {
        Integer actual = this.secuenciaOfertas;
        this.secuenciaOfertas ++;
        return actual;
    }

    public Pieza getPiezaPorId(Integer id1) {
    	
        for (Pieza pieza: enExhibicion.values()) {
            if (pieza.id == id1) {
                return pieza;
            }
        }
        for (Pieza pieza: enBodega.values()) {
            if (pieza.id == id1) {
                return pieza;
            }
        }
        for (int i = 0; i < vendidas.size(); i++) {
            Pieza pieza = vendidas.get(i);
            if (pieza.id == id1) {
                return pieza;
            }
        }
        for (int i = 0; i < regresadas.size(); i++) {
            Pieza pieza = regresadas.get(i);
            if (pieza.id == id1) {
                return pieza;
            }
        }
        return null;
    } 

    public Integer getSecuenciaPiezas() {
        Integer actual = this.secuenciaPiezas;
        this.secuenciaPiezas ++;
        return actual;
    }
    
    public Usuario getPropietario(Pieza pieza) {
        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                if (cliente.getPrestadasEnEspera().contains(pieza)) {
                    return cliente;
                }
            }
        }
        return null;
    }

    public List<Oferta> getOfertasPendientes() {
        List<Oferta> ofertasPendientes = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            if (!oferta.isRevisada() && !oferta.isPagado()) {
                ofertasPendientes.add(oferta);
            }
        }
        return ofertasPendientes;
    }

  
    public Oferta getOfertaPorId(Integer id) {
        for (Oferta oferta : ofertas) {
            if (oferta.getId() == id) {
                return oferta;
            }
        }
        return null;
    }
 
    public Map<String, Pieza> getEnExhibicion() {
        return enExhibicion;
    }

    public List<Oferta> getComprasPendientes() {
        List<Oferta> comprasPendientes = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            if (oferta.isRevisada() && oferta.isAceptada() && !oferta.isPagado()) {
                comprasPendientes.add(oferta);
            } 
        }
        return comprasPendientes;
    }

    

    public Map<String, Pieza> getEnBodega() {
        return enBodega;
    }

    public void setEnBodega(Map<String, Pieza> enBodega) {
        this.enBodega = enBodega;
    }

    public List<Pieza> getVendidas() {
        return vendidas;
    }

    public void setVendidas(List<Pieza> vendidas) {
        this.vendidas = vendidas;
    }

    public void setEnExhibicion(Map<String, Pieza> enExhibicion) {
        this.enExhibicion = enExhibicion;
    }
    
    public List<Pieza> getRegresadas() {
        return regresadas;
    }

    

    public List<Compra> getCompras() {
        return compras;
    }
    
   

    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }

    public List<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(List<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public void setRegresadas(List<Pieza> regresadas) {
        this.regresadas = regresadas;
    }
    
    public List<Subasta> getSubastas() {
        return subastas;
    }

   

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }
    
    public void setSubastas(List<Subasta> subastas) {
        this.subastas = subastas;
    }
    

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

