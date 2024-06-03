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
    private List<Pieza> enExhibicion;
    private List<Pieza> enBodega;
    private List<Pieza> vendidas;
    private List<Pieza> regresadas;
    private List<Compra> compras;
    private List<Oferta> ofertas;
    private List<Subasta> subastas;
    private Map<String, Usuario> usuarios;
    private Integer secuenciaPiezas;
    private Integer secuenciaOfertas;
    private File archivoSecuencias = new File("data/secuencias.txt");
    private File archivoPiezas = new File("data/piezas.txt");
    private File archivoUsuarios = new File("data/usuarios.txt");
    private File archivoOfertas = new File("data/ofertas.txt");
    private File archivoCompras = new File("data/compras.txt");
    private File archivoSubastas = new File("data/subastas.txt");

    public Galeria() throws Exception {
        this.enExhibicion = new ArrayList<>();
        this.enBodega = new ArrayList<>();
        this.vendidas = new ArrayList<>();
        this.regresadas = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.subastas = new ArrayList<>();
        this.usuarios = new HashMap<>();
        cargarDatos();
    }

    public Pieza agregerAExhibicion(Pieza pieza) {
        this.enExhibicion.add(pieza);
        return pieza;
    }

    public Pieza removerDeExhibicion(Pieza pieza) {
        this.enExhibicion.remove(pieza);
        return pieza;
    }

    public Pieza agregerABodega(Pieza pieza) {
        this.enBodega.add(pieza);
        return pieza;
    }

    public Pieza removerDeBodega(Pieza pieza) {
        this.enBodega.remove(pieza);
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
        cargarPiezas();
        cargarUsuarios();
        cargarOfertas();
        cargarCompras();
        cargarSubastas();
    }

    private void cargarSecuencia() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoSecuencias));
        String[] informacion = br.readLine().split(";");
        secuenciaPiezas = Integer.parseInt(informacion[0]);
        secuenciaOfertas = Integer.parseInt(informacion[1]);
        br.close();
    }

    private void cargarPiezas() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoPiezas));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            Integer id = Integer.parseInt(informacion[0]);
            String titulo = informacion[1];
            Integer anioCreacion = Integer.parseInt(informacion[2]);
            String lugarCreacion = informacion[3];
            String tipo = informacion[4];
            boolean disponible = Boolean.parseBoolean(informacion[5]);
            Integer valor = Integer.parseInt(informacion[6]);
            Integer valorMinimo = informacion[7].isEmpty() ? null : Integer.parseInt(informacion[6]);
            boolean bloqueado = Boolean.parseBoolean(informacion[8]);
            Date ingreso = new Date(Long.parseLong(informacion[9]));
            Integer tiempoAcordado = Integer.parseInt(informacion[10]);
            String modalidad = informacion[11];
            Date fechaVentaEntrega = informacion[12].isEmpty() ? null : new Date(Long.parseLong(informacion[12]));
            Integer ubicacion;
            Pieza pieza;
            if (tipo.equals("Escultura")) {
                
                String dimensiones = informacion[13];
                Integer peso = Integer.parseInt(informacion[14]);
                boolean electricidad = Boolean.parseBoolean(informacion[15]);
                String detalles = informacion[16];
                String[] materialesStr = informacion[17].split(",");
                ubicacion = Integer.parseInt(informacion[18]);
                List<MaterialConstruccion> materiales = new ArrayList<>();
                for (String material : materialesStr) {
                    materiales.add(new MaterialConstruccion(material.trim()));
                }
                pieza = new Escultura(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega, dimensiones, peso, electricidad, detalles, materiales);
            } else if (tipo.equals("Pintura")) {
                
                String dimensiones = informacion[13];
                String cuidados = informacion[14];
                String tecnica = informacion[15];
                String[] materialesStr = informacion[16].split(",");
                ubicacion = Integer.parseInt(informacion[17]);
                List<MaterialConstruccion> materiales = new ArrayList<>();
                for (String material : materialesStr) {
                    materiales.add(new MaterialConstruccion(material.trim()));
                }
                pieza = new Pintura(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega, dimensiones, cuidados, tecnica, materiales);
                
            }
            
            else {
                String dimensiones = informacion[13];
                String resolucion = informacion[14];
                String formato = informacion[15];
                String categoria= informacion[16];
                ubicacion = Integer.parseInt(informacion[17]);
                pieza = new Fotografia(id, titulo, anioCreacion, lugarCreacion, tipo, disponible, valor, valorMinimo, bloqueado, ingreso, tiempoAcordado, modalidad, fechaVentaEntrega, dimensiones, resolucion, formato, categoria);
            }
            if (ubicacion == 1) {
                this.enExhibicion.add(pieza);
            } else if (ubicacion == 2) {
                this.enBodega.add(pieza);
            } else if (ubicacion == 3) {
                this.vendidas.add(pieza);
            } else if (ubicacion == 4) {
                this.regresadas.add(pieza);
            }
            linea = br.readLine();
        }
        br.close();
    }

    private void cargarUsuarios() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoUsuarios));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            String nombre = informacion[0];
            String login = informacion[1];
            String password = informacion[2];
            String telefono = informacion[3].isEmpty() ? null: informacion[3];
            String correo = informacion[4].isEmpty() ? null: informacion[4];
            Integer ubicacion = Integer.parseInt(informacion[informacion.length - 1]);
            Usuario usuario;
            if (ubicacion == 1) {
                
                String tipo = informacion[5];
                Integer balance = Integer.parseInt(informacion[6]);
                String[] prestadasEnEsperaStr = informacion[7].isEmpty() ? null : informacion[7].split(",");
                String[] autoriaVendidasCompradasStr = informacion[8].isEmpty() ? null : informacion[8].split(",");
                Cliente cliente = new Cliente(nombre, login, password, telefono, correo, balance, this, tipo);
                if (prestadasEnEsperaStr != null){
                    for (String prestadaEnEspera : prestadasEnEsperaStr) {
                        Integer idPieza = Integer.parseInt(prestadaEnEspera);
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
            } else {
            	String tipo = informacion[5];
                usuario = new Empleado(nombre, login, password, telefono, correo, this, tipo);
            }
            usuarios.put(login, usuario);
            linea = br.readLine();
        }
        br.close();
    }

    private void cargarOfertas() throws Exception{
        BufferedReader br = new BufferedReader(new FileReader(archivoOfertas));
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

    private void cargarCompras() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoCompras));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            Integer valorPagado = Integer.parseInt(informacion[0]);
            TipoPago metodoPago = TipoPago.valueOf(informacion[1]);
            Cliente cliente = (Cliente) usuarios.get(informacion[2]);
            Pieza pieza = getPiezaPorId(Integer.parseInt(informacion[3]));
            Date fecha = new Date(Long.parseLong(informacion[4]));
            Compra compra = new Compra(valorPagado, metodoPago, cliente, pieza, fecha);
            compras.add(compra);
            linea = br.readLine();
        }
        br.close();
    }

    private void cargarSubastas() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivoSubastas));
        String linea = br.readLine();
        while (linea != null) {
            String[] informacion = linea.split(";");
            String nombre = informacion[0];
            boolean activa = Boolean.parseBoolean(informacion[1]);
            String[] aceptadasStr = informacion[2].isEmpty() ? null : informacion[2].split(",");
            String[] pendientesStr = informacion[3].isEmpty() ? null : informacion[3].split(",");
            String[] verificadosStr = informacion[4].isEmpty() ? null : informacion[4].split(",");
            String[] enEsperaStr = informacion[5].isEmpty() ? null : informacion[5].split(",");
            String[] piezasStr = informacion[6].isEmpty() ? null : informacion[6].split(",");
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

    public void guardarDatos() throws Exception {
        guardarSecuencia();
        guardarPiezas();
        guardarUsuarios();
        guardarOfertas();
        guardarCompras();
        guardarSubastas();
    }

    public void guardarSecuencia() throws Exception {
        String string = Integer.toString(secuenciaPiezas) + ";" + Integer.toString(secuenciaOfertas);
        guardarArchivo(string, archivoSecuencias);
    }

    private void guardarPiezas() throws Exception {
        String informacion = "";
        for (Pieza pieza : this.enExhibicion) {
            informacion += pieza.toString() + ";1\n";
        }
        for (Pieza pieza : this.enBodega) {
            informacion += pieza.toString() + ";2\n";
        }
        for (Pieza pieza : this.vendidas) {
            informacion += pieza.toString() + ";3\n";
        }
        for (Pieza pieza : this.regresadas) {
            informacion += pieza.toString() + ";4\n";
        }
        guardarArchivo(informacion, archivoPiezas);
    }

    private void guardarUsuarios() throws Exception {
        String informacion = "";
        for (Usuario usuario : usuarios.values()) {
            String ubicacion = usuario instanceof Cliente ? "1" : "2";
            informacion += usuario.toString() + ";" + ubicacion + "\n";
        }
        guardarArchivo(informacion, archivoUsuarios);
    }

    private void guardarOfertas() throws Exception {
        String informacion = "";
        for (Oferta oferta : ofertas) {
            informacion += oferta.toString() + "\n";
        }
        guardarArchivo(informacion, archivoOfertas);
    }

    private void guardarCompras() throws Exception {
        String informacion = "";
        for (Compra compra : compras) {
            informacion += compra.toString() + "\n";
        }
        guardarArchivo(informacion, archivoCompras);
    }

    private void guardarSubastas() throws Exception {
        String informacion = "";
        for (Subasta subasta : subastas) {
            informacion += subasta.toString() + "\n";
        }
        guardarArchivo(informacion, archivoSubastas);
    }

    private void guardarArchivo(String informacion, File archivo) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(informacion);
        bw.close();
    }

    public Integer getSecuenciaPiezas() {
        Integer actual = this.secuenciaPiezas;
        this.secuenciaPiezas ++;
        return actual;
    }

    public Integer getSecuenciaOfertas() {
        Integer actual = this.secuenciaOfertas;
        this.secuenciaOfertas ++;
        return actual;
    }

    public Pieza getPiezaPorId(Integer id) {
        for (int i = 0; i < enExhibicion.size(); i++) {
            Pieza pieza = enExhibicion.get(i);
            if (pieza.id == id) {
                return pieza;
            }
        }
        for (int i = 0; i < enBodega.size(); i++) {
            Pieza pieza = enBodega.get(i);
            if (pieza.id == id) {
                return pieza;
            }
        }
        for (int i = 0; i < vendidas.size(); i++) {
            Pieza pieza = vendidas.get(i);
            if (pieza.id == id) {
                return pieza;
            }
        }
        for (int i = 0; i < regresadas.size(); i++) {
            Pieza pieza = regresadas.get(i);
            if (pieza.id == id) {
                return pieza;
            }
        }
        return null;
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

    public List<Oferta> getComprasPendientes() {
        List<Oferta> comprasPendientes = new ArrayList<>();
        for (Oferta oferta : ofertas) {
            if (oferta.isRevisada() && oferta.isAceptada() && !oferta.isPagado()) {
                comprasPendientes.add(oferta);
            } 
        }
        return comprasPendientes;
    }

    public Oferta getOfertaPorId(Integer id) {
        for (Oferta oferta : ofertas) {
            if (oferta.getId() == id) {
                return oferta;
            }
        }
        return null;
    }

    public List<Cliente> getDueniosPieza(Pieza pieza) {
        List<Cliente> duenios = new ArrayList<> ();
        for (Usuario usuario : usuarios.values()) {
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                if (cliente.getTipo().equals("Propietario")) {
                    for (Pieza piezaVendida : cliente.getAutoriaVendidasCompradas()) {
                        if (piezaVendida.getId() == pieza.getId()) {
                            duenios.add(cliente);
                        }
                    }
                    for (Pieza piezaPrestada: cliente.getPrestadasEnEspera()) {
                        if (piezaPrestada.getId() == pieza.getId()) {
                            duenios.add(cliente);
                        }
                    }
                }
            }
        }
        return duenios;
    }

    public List<Compra> getComprasPieza(Pieza pieza) {
        List<Compra> listaCompras = new ArrayList<>();
        for (Compra compra: compras) {
            if (compra.getPieza().getId() == pieza.getId()) {
                listaCompras.add(compra);
            }
        }
        return listaCompras;
    }

    public List<Compra> getComprasComprador(Cliente comprador) {
        List<Compra> listaCompras = new ArrayList<>();
        for (Compra compra: compras) {
            if (compra.getCliente().equals(comprador)) {
                listaCompras.add(compra);
            }
        }
        return listaCompras;
    }
 
    public List<Pieza> getEnExhibicion() {
        return enExhibicion;
    }

    public void setEnExhibicion(List<Pieza> enExhibicion) {
        this.enExhibicion = enExhibicion;
    }

    public List<Pieza> getEnBodega() {
        return enBodega;
    }

    public void setEnBodega(List<Pieza> enBodega) {
        this.enBodega = enBodega;
    }

    public List<Pieza> getVendidas() {
        return vendidas;
    }

    public void setVendidas(List<Pieza> vendidas) {
        this.vendidas = vendidas;
    }

    public List<Pieza> getRegresadas() {
        return regresadas;
    }

    public void setRegresadas(List<Pieza> regresadas) {
        this.regresadas = regresadas;
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

    public List<Subasta> getSubastas() {
        return subastas;
    }

    public void setSubastas(List<Subasta> subastas) {
        this.subastas = subastas;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

}
