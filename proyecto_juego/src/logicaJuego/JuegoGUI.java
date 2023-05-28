package logicaJuego;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import elementos.Coordenada;
import elementos.Element;
import elementos.ElementType;
import elementos.JugadorException;
import elementos.PlayerType;
import static logicaJuego.Constantes.IMAGE_ICON_DIRECTORY;
import static logicaJuego.Constantes.TAMANNO;



public class JuegoGUI extends Juego implements ActionListener {

	private JButton[][] botones; 	// Matriz de botones
	private JFrame ventana; 		// Ventana que se utilizará para el gráfico. Esta ventana
									// se divide en el panel superio y el panel del juego
	private JPanel panelSuperior; 	// Panel superior para mostrar la información
	private JLabel informacion; 	// Etiqueta que se muestra en el panel superior para mostrar la información
	private JPanel panelJuego; 		// Panel para mostrar los botones

	public JuegoGUI(PlayerType[] jugadores) throws JuegoException {
		super(jugadores);

		// Crea el JFrame que es la ventana. Le pongo el nombre y el botón de cerrar
		ventana = new JFrame("Juego Jacarandá 2023");
		ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Crea el panel superior donde pondremos información del juego
		panelSuperior = new JPanel();
		panelSuperior.setBackground(Color.BLUE);
		panelSuperior.setPreferredSize(new Dimension(200, 350));
		informacion = new JLabel("");
		informacion.setForeground(Color.WHITE);
		panelSuperior.add(informacion);

		ventana.add(panelSuperior, BorderLayout.NORTH);

		// Crea el panel del juego
		panelJuego = new JPanel();
		Dimension d = new Dimension(50, 50);

		// Crea una matriz para guardar los botones que se añaden
		botones = new JButton[TAMANNO][TAMANNO];
		// Crea un GridLayout para organizar los botones en forma de matriz
		panelJuego.setLayout(new GridLayout(TAMANNO, TAMANNO));
		// Crea los botones y los añade al panelJuego
		for (int j = 0; j < TAMANNO; j++) {
			for (int i = 0; i < TAMANNO; i++) {
				JButton button1 = new JButton();
				button1.setPreferredSize(d);
				// Le pone el icono correspondiente al botón
				this.asignarIcono(button1, i, j);
				// Le asigna el tamaño
				button1.setMaximumSize(new Dimension(TAMANNO, TAMANNO));
				// Le añade un Listener para que cuando se haga click lance el listener
				button1.addActionListener((ActionListener) this);
				// Lo añade al panelJuego
				panelJuego.add(button1);
				// Lo guarda en la matriz de botones para luego buscarlo cuando
				// el listener nos avise que se ha hecho clic sobre un botón.
				botones[i][j] = button1;
			}
		}

		ventana.add(panelJuego, BorderLayout.SOUTH);

		// Lanza el dado del jugador que le toca jugar
		super.setDado(getValorDado());;
		// Muestra la información
		setInformacion();

		ventana.pack();
		ventana.setVisible(true);

		// Muestra ventana con el jugador que le toca jugar y el número que le ha salido
		// en el dado.

		JOptionPane.showMessageDialog(ventana,
				"Le toca al jugador " + super.getNombreJuegadorQueJuega() + ". El dado saca " + super.getValorDado() + " movimientos");
	}


	// Devuelve el icono correspondiente según el elemento
	private void asignarIcono(JButton button, int x, int y) {
	    ImageIcon imageIcon;

	    // Obtener el elemento en la coordenada
	    Element elemento = super.obtenerElementoTablero(new Coordenada(x, y));

	    // Si el elemento es nulo, asignar un icono predeterminado
	    if (elemento == null) {
	        imageIcon = getDefaultImageIcon();
	    } else {
	        // Obtener el tipo del elemento y asignar el icono correspondiente
	        ElementType tipo = elemento.getType();
	        if (tipo != null) {
	            switch (tipo.getSymbol()) {
	                // Asignar iconos según el tipo de elemento
	                case 'D':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/dinero.png");
	                    break;
	                case 'P':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/pocion.png");
	                    break;
	                case 'R':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/roca.png");
	                    break;
	                case 'Y':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/gema.png");
	                    break;
	                case 'E':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/elfo.png");
	                    break;
	                case 'G':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/guerrero.png");
	                    break;
	                case 'M':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/mago.png");
	                    break;
	                case 'O':
	                    imageIcon = new ImageIcon(System.getProperty("user.dir") + "/img/ogro.png");
	                    break;
	                default:
	                    imageIcon = getDefaultImageIcon();
	                    break;
	            }
	        } else {
	            imageIcon = getDefaultImageIcon();
	        }
	    }

	    // Asignar el icono al botón
	    button.setIcon(imageIcon);
	}


	
	protected ImageIcon getImageIcon(String imagen) {
		return new ImageIcon( Constantes.CURRENT_USER_DIRECTORY+IMAGE_ICON_DIRECTORY+imagen);
	}
	
	protected ImageIcon getDefaultImageIcon() {
		return new ImageIcon( Constantes.CURRENT_USER_DIRECTORY+IMAGE_ICON_DIRECTORY+"nada.png");
	}

	/** Muestra información
	 * 
	 */
	public void setInformacion() {

		this.informacion.setText("<html>" + super.imprimeValoresJugadores().replaceAll("\n", "<br>") + "<br>"
				+ super.imprimeNombreJugadores().replaceAll("\n", "<br><br>") + "</html>");
	}

	


	/**
	 * Actualizar iconos, necesario cuando se cambia de casillas algún jugador.
	 */
	private void actualizarIconos() {

		for (int i = 0; i < Constantes.TAMANNO; i++)
			for (int j = 0; j < Constantes.TAMANNO; j++) {
				asignarIcono(botones[i][j], i, j);
			}
	}
	
	public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                crearInterfaz();
            }
        });
    }
	
	 private static void crearInterfaz() {
	        try {
	            PlayerType[] jugadores = {PlayerType.GUERRERO, PlayerType.ELFO};
	            JuegoGUI juego = new JuegoGUI(jugadores);
	        } catch (JuegoException e) {
	            e.printStackTrace();
	        }
	    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

