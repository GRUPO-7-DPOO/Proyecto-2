
	package CrearClases;

	import java.awt.BorderLayout;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JPanel;

import Principal.Central;
import Req.__Empleado;

	public class SuccessScreen extends JFrame implements ActionListener {

	    private JButton volverButton = new JButton("Volver");
		private static final String VOLVER = "VOLVER";
	    private Central central;

	    public SuccessScreen(Central central) {
	        super("Éxito");

	        this.central = central;

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLayout(new BorderLayout());

	        JPanel panel = new JPanel();
	        panel.add(new JLabel("El cambio fue exitoso"));
	        panel.add(volverButton);
	        
	        volverButton.setActionCommand(VOLVER);

	       

	        add(panel, BorderLayout.CENTER);

	        pack();
	        setVisible(true);
	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	String rt = e.getActionCommand();
			if(rt.equals(VOLVER)) {
	        	__Empleado empleadoInstance = central.get__empleado();
	            empleadoInstance.administrarPiezas();
	            dispose();
	        }
	    }
	}


