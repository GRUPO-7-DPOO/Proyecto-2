package CrearClases;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import Principal.Central;
import modelo.Galeria;
import modelo.Pieza;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PantallaCambio extends JFrame implements ActionListener { 
	private String[] optionsTipo = {"En exhibicion a Bodega", "De Bodega a en Exhibicion"};
    private JComboBox<String> comboBoxTipo = new JComboBox<>(optionsTipo);
    private static final JButton btnContinuar = new JButton("Continuar");
    public static final String CONTINUAR = "CONTINUAR";
    
    Galeria galeria;
    Central central;
    String opcion;


	
	

	public PantallaCambio(Galeria galeria, Central central) {
		 super();

		this.galeria = galeria;
		this.central = central;

        setTitle("Seleccionar el cambio que desea realizar: ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        btnContinuar.setActionCommand(CONTINUAR);
        btnContinuar.addActionListener(this);

        JPanel panelPrincipal = new JPanel(new FlowLayout());
        panelPrincipal.add(new JLabel("Seleccionar el cambio que desea realizar: "));
        panelPrincipal.add(comboBoxTipo);
        panelPrincipal.add(btnContinuar);

        add(panelPrincipal, BorderLayout.CENTER);

        pack();
        setVisible(true);
        
        
    }
	
	
	private String getPiezasEnExhibicion() {
        String string = "Piezas en exhibicion:\n";
        for (int i = 1; i <= galeria.getEnExhibicion().size(); i++) {
            string += Integer.toString(i) + "- " + galeria.getEnExhibicion().get(i-1).getTitulo() + "\n";
        }
        return string;
    }
	
	private String getPiezasEnBodega() {
        String string = "Piezas en bodega:\n";
        for (int i = 1; i <= galeria.getEnBodega().size(); i++) {
            string += Integer.toString(i) + "- " + galeria.getEnBodega().get(i-1).getTitulo() + "\n";
        }
        return string;
    }
	

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        if (CONTINUAR.equals(command)) {
            String ubicacion = (String) comboBoxTipo.getSelectedItem();
            Integer ubi;
			if (ubicacion.equals("En exhibicion a Bodega")) {
            	ubi = 1;
            	Collection<Pieza> lista = galeria.getEnExhibicion();
            	new piezas(lista, central, ubi);
            } else {
            	ubi = 2;
            	Collection<Pieza> lista = galeria.getEnBodega();
            	new piezas(lista, central, ubi);
;            }
            
            dispose(); // Cierra la ventana actual
        }
    }


}
