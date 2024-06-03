package CrearClases;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import Principal.Central;
import Req.__Empleado;
import excepciones.OperacionIlegal;
import modelo.Cliente;
import modelo.Empleado;
import modelo.Escultura;
import modelo.Fotografia;
import modelo.Galeria;
import modelo.MaterialConstruccion;
import modelo.Pieza;
import modelo.Pintura;
import modelo.Usuario;

public class CrearPieza extends JFrame implements ActionListener {

    private static final JLabel titulo = new JLabel("Título de la Obra");
    private static final JLabel anio = new JLabel("Año de Creación");
    private static final JLabel lugar = new JLabel("Lugar de Creación");
    private static final JLabel disponible = new JLabel("Disponible?");
    private static final JLabel valor = new JLabel("Valor");
    private static final JLabel tiempo = new JLabel("Tiempo Acordado (Días)");
    private static final JLabel modalidad = new JLabel("Modalidad");
    private static final JLabel detalles = new JLabel("Detalles");
    private static final JLabel logIn = new JLabel("LogIn del autor");

    private JTextField txtTitulo = new JTextField(20);
    private JTextField txtAnio = new JTextField(20);
    private JTextField txtLugar = new JTextField(20);
    private JTextField txtValor = new JTextField(20);
    private JTextField txtTiempo = new JTextField(20);
    private JTextField txtLogIn = new JTextField(20);
    private JTextField txtTipo = new JTextField(20);
    
    private JTextArea txtDetalles = new JTextArea(5, 20);
    private JScrollPane scrollPane = new JScrollPane(txtDetalles);

    private JToggleButton toggleDisponible = new JToggleButton("NO");

    private String[] optionsModalidad = {"Consignación"};
    private JComboBox<String> comboBoxModalidad = new JComboBox<>(optionsModalidad);

    private JTextField txtDimensiones = new JTextField(20);
    private JTextField txtMateriales = new JTextField(20);
    private JTextField txtPeso = new JTextField(20);
    private JTextField txtCuidados = new JTextField(20);
    private JTextField txtTecnica= new JTextField(20);
    private JTextField txtResolucion = new JTextField(20);
    private JTextField txtFormato = new JTextField(20);
    private JTextField txtCategoria= new JTextField(20);
    
  
    
    private JCheckBox checkElectricidad = new JCheckBox("Requiere Electricidad");

    private static final JButton btnContinuar = new JButton("Confirmar y Continuar");
    public static final String CONTINUAR = "CONTINUAR";

    private static final JButton btnVolver = new JButton("Volver");
    public static final String VOLVER = "VOLVER";

    private JLabel lblError = new JLabel(" ");

    JPanel panelPrincipal = new JPanel();
    JPanel panelBotones = new JPanel();

    Galeria galeria;
    
    Central central;
    
    String tipoSeleccionado;
    
    String opcion;

    public CrearPieza(Galeria galeria, String tipoSeleccionado, Central central, String opcion) {
        super();

        this.galeria = galeria;
        this.tipoSeleccionado = tipoSeleccionado;
        this.central = central;
        this.opcion = opcion;

        setTitle("Ingresar Detalles de la Pieza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        txtTipo.setText(tipoSeleccionado);
        txtTipo.setEditable(false); 

        toggleDisponible.addItemListener(e -> {
            if (toggleDisponible.isSelected()) {
                toggleDisponible.setText("SÍ");
            } else {
                toggleDisponible.setText("NO");
            }
        });

        comboBoxModalidad.addActionListener(e -> {
            String selectedOption = (String) comboBoxModalidad.getSelectedItem();
        });

        btnContinuar.setActionCommand(CONTINUAR);
        btnContinuar.addActionListener(this);

        btnVolver.setActionCommand(VOLVER);
        btnVolver.addActionListener(this);

        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        addComponent(panelPrincipal, titulo, txtTitulo);
        addComponent(panelPrincipal, anio, txtAnio);
        addComponent(panelPrincipal, lugar, txtLugar);
        addComponent(panelPrincipal, disponible, toggleDisponible);
        addComponent(panelPrincipal, valor, txtValor);
        addComponent(panelPrincipal, tiempo, txtTiempo);
        addComponent(panelPrincipal, modalidad, comboBoxModalidad);

        if (tipoSeleccionado.equals("Escultura")) {
            addComponent(panelPrincipal, new JLabel("Dimensiones"), txtDimensiones);
            addComponent(panelPrincipal, new JLabel("Peso"), txtPeso);
            addComponent(panelPrincipal, new JLabel("Electricidad"), checkElectricidad);
            addComponent(panelPrincipal, new JLabel("Materiales"), txtMateriales);
        } else if (tipoSeleccionado.equals("Pintura")) {
        	 addComponent(panelPrincipal, new JLabel("Dimensiones"), txtDimensiones);
             addComponent(panelPrincipal, new JLabel("Cuidados"), txtCuidados);
             addComponent(panelPrincipal, new JLabel("Tecnica"), txtTecnica);
             addComponent(panelPrincipal, new JLabel("Materiales"), txtMateriales);
        	
  
        } else {
            addComponent(panelPrincipal, detalles, scrollPane);
        }

        addComponent(panelPrincipal, logIn, txtLogIn);

        panelBotones.setLayout(new FlowLayout());
        panelBotones.add(btnVolver);
        panelBotones.add(btnContinuar);

        add(panelPrincipal, BorderLayout.CENTER);
        add(lblError, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void addComponent(JPanel panel, JComponent label, JComponent field) {
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        subPanel.add(label);
        subPanel.add(field);
        panel.add(subPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    	Pieza pieza;
        String command = e.getActionCommand();
        if (CONTINUAR.equals(command)) {
            String titulo = txtTitulo.getText();
            Integer anio = Integer.parseInt(txtAnio.getText());
            String lugar = txtLugar.getText();
            Integer valor = Integer.parseInt(txtValor.getText());
            Integer tiempo = Integer.parseInt(txtTiempo.getText());
            String logIn = txtLogIn.getText();
            boolean disponible = toggleDisponible.isSelected();
            String modalidad = (String) comboBoxModalidad.getSelectedItem();

            if (tipoSeleccionado.equals("Escultura")) {
                String dimensiones = txtDimensiones.getText();
                Integer peso = Integer.parseInt(txtPeso.getText());

                boolean electricidad = checkElectricidad.isSelected();
                String detalles = txtDetalles.getText();
                String materiales = txtMateriales.getText();
                
                String[] materialesArray = materiales.split(",");
                
                Escultura escultura = new Escultura(galeria.getSecuenciaPiezas(), titulo, anio, lugar, "Escultura", disponible, valor, tiempo, modalidad, dimensiones, peso, electricidad, detalles);
                for (String material : materialesArray) {
                	escultura.getMateriales().add(new MaterialConstruccion(material));
                }
                
                pieza = escultura;
                
            }  if (tipoSeleccionado.equals("Pintura")) {
                String dimensiones = txtDimensiones.getText();
                String cuidados = txtCuidados.getText();
                String tecnica = txtTecnica.getText();
                
                
                String materiales = txtMateriales.getText();
                
                String[] materialesArray = materiales.split(",");
                Pintura pintura = new Pintura(galeria.getSecuenciaPiezas(), titulo, anio, lugar, "Pintura", disponible, valor, tiempo, modalidad, dimensiones, cuidados, tecnica);

                for (String material : materialesArray) {
                	pintura.getMateriales().add(new MaterialConstruccion(material));
                }
                
                pieza = pintura;
            } else {
            	String dimensiones = txtDimensiones.getText();
            	String resolucion = txtResolucion.getText();
            	String formato = txtFormato.getText();
            	String categoria = txtCategoria.getText();
            	pieza = new Fotografia(galeria.getSecuenciaPiezas(), titulo, anio, lugar, "Fotografia", disponible, valor, tiempo, modalidad, dimensiones,resolucion, formato, categoria);
            }
            
  
            	String login = txtLogIn.getText();
                Cliente cliente = null;

                if (!central.getGaleria().getUsuarios().containsKey(login)) {
                    new CrearUsuario2(central, pieza);
                     
                    
                } else {
                	cliente = (Cliente) central.getGaleria().getUsuarios().get(login);
                	cliente.getPrestadasEnEspera().add(pieza);
                }
                
                if (opcion.equals("Exhibicion")) {
                	this.galeria.agregerAExhibicion(pieza);
                } else {
                	this.galeria.agregerABodega(pieza);
                }

                
            
            new SuccessScreen(central);
            dispose();
            
          
            
        }
            

        else if (VOLVER.equals(command)) {
            __Empleado empleadoInstance = central.get__empleado();
            empleadoInstance.administrarPiezas();
            dispose();
        }
    }
        
        
 
    
    public String input(String mensaje) {
        try {
            System.out.println(mensaje);
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
