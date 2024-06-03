package CrearClases;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Principal.Central;
import modelo.Galeria;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PantallaPieza extends JFrame implements ActionListener { 
	private String[] optionsTipo = {"Escultura", "Pintura", "Fotografía"};
    private JComboBox<String> comboBoxTipo = new JComboBox<>(optionsTipo);
    private static final JButton btnContinuar = new JButton("Continuar");
    public static final String CONTINUAR = "CONTINUAR";
    
    Galeria galeria;
    Central central;
    String opcion;


	
	

	public PantallaPieza(Galeria galeria, Central central, String opcion) {
		 super();

		this.galeria = galeria;
		this.central = central;

        setTitle("Seleccionar Tipo de Pieza");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnContinuar.setActionCommand(CONTINUAR);
        btnContinuar.addActionListener(this);

        JPanel panelPrincipal = new JPanel(new FlowLayout());
        panelPrincipal.add(new JLabel("Seleccione el tipo de pieza:"));
        panelPrincipal.add(comboBoxTipo);
        panelPrincipal.add(btnContinuar);

        add(panelPrincipal, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (CONTINUAR.equals(command)) {
            String tipoSeleccionado = (String) comboBoxTipo.getSelectedItem();
            new CrearPieza(galeria, tipoSeleccionado, central, opcion); // Abre la ventana para ingresar los detalles
            dispose(); // Cierra la ventana actual
        }
    }


}
