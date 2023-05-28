package main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Swing {

	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            JFrame ventana = new JFrame("Mi Juego");
	            // Configuración de la ventana y creación de componentes
	            // ...
	            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            ventana.pack();
	            ventana.setVisible(true);
	        });
	    }

}
