package logicaJuego;

import static logicaJuego.JuegoUtils.crearSeparador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elementos.Coordenada;
import elementos.Element;
import elementos.ElementType;
import elementos.Jugador;
import elementos.JugadorException;
import elementos.PlayerType;

public class Juego {

	private Map<Coordenada, Element> tablero;
	private List<Coordenada> coordenadaJugadores;
	private int jugadorJuega;
	private int dado; // Dado para ver los movimientos del jugador que juega



	public Juego(PlayerType[] tipos) {
		super();
		this.tablero = new HashMap<>();
		this.coordenadaJugadores = new ArrayList<>();
		this.jugadorJuega=0;
	}
	



	/**
	 * Mueve el jugador en el tablero
	 * 
	 * @param direccion
	 * @return resultado de la operación
	 * @throws JuegoException
	 * @throws JugadorException
	 */
	public String moverJugador(char direccion) throws JuegoException, JugadorException {

		String resultado = "";
		Jugador jugador = (Jugador) this.tablero.get(this.coordenadaJugadores.get(jugadorJuega));

		Coordenada coordDestino = getNextPosition(direccion);

		// Tengo que ver que hay en la nueva casilla
		Element elemento = this.tablero.get(coordDestino);

		if (elemento != null) { // Hay algo en la casilla
			if (elemento instanceof Jugador) {
				// Después de la lucha los jugadores no se mueven
				resultado = luchar(resultado, jugador, coordDestino, elemento);
				
			} else if (elemento.getType() == ElementType.ROCA) {
				resultado = encuentraRoca(resultado, jugador, coordDestino);
				
			} else if (elemento.getType() == ElementType.GEMA) {
				jugador.encuentraGema();
				this.cambiaJugadorAPosicion(coordDestino);

			} else if (elemento.getType() == ElementType.DINERO) {
				jugador.encuentraDinero();
				this.cambiaJugadorAPosicion(coordDestino);

			} else if (elemento.getType() == ElementType.POCION) {
				jugador.encuentraPocion();
				this.cambiaJugadorAPosicion(coordDestino);

			}

		} else {
			this.cambiaJugadorAPosicion(coordDestino);
		}

		return resultado;
	}

	private String encuentraRoca(String resultado, Jugador jugador, Coordenada coordDestino) {
		int resultadoRoca = jugador.encuentraRoca();
		switch (resultadoRoca) {
				case Constantes.ROMPE_ROCA_CON_GEMA:
					resultado = String.format("El jugador %s rompe la roca con una gema.", jugador.getNombre());
					this.cambiaJugadorAPosicion(coordDestino);
					break;
		
				case Constantes.GANA_A_LA_ROCA:
					resultado = String.format("El jugador %s gana a la roca.", jugador.getNombre());
					this.cambiaJugadorAPosicion(coordDestino);
					break;
		
				case Constantes.PIERDE_A_LA_ROCA:
					resultado = String.format("El jugador %s pierde. No se mueve.", jugador.getNombre());
					break;
		}
		return resultado;
	}

	private String luchar(String resultado, Jugador jugador, Coordenada coordDestino, Element elemento) {
		Jugador enemigo = (Jugador) elemento;
		int resultadoLucha = jugador.lucha(enemigo);
		switch (resultadoLucha) {
		case Constantes.EMPATE:
			resultado = "Empate entre los jugadores";
			break;
		case Constantes.GANA_USA_POCIMA:
			resultado = "El jugador " + jugador.getNombre() + " gana. Le quita una pócima al enemigo";
			break;
		case Constantes.GANA_DINERO:
			resultado = "El jugador " + jugador.getNombre() + " gana. Le quita el dinero al enemigo";
			break;
		case Constantes.GANA_MUERE:
			resultado = "El jugador " + jugador.getNombre() + " gana. El enemigo muere";
			this.eliminarJugador(coordDestino);
			// Si se elimina el jugador que juega el dado se pone a 0 para que no siga
			// tirando
			break;
		case Constantes.PIERDE_USA_POCIMA:
			resultado = "El enemigo " + enemigo.getNombre() + " gana. Le quita una pócima al jugador";
			break;
		case Constantes.PIERDE_DINERO:
			resultado = "El enemigo " + enemigo.getNombre() + " gana. Le quita el dinero al jugador";
			break;
		case Constantes.PIERDE_MUERE:
			resultado = "El enemigo " + enemigo.getNombre() + " gana. El jugador muere";
			this.eliminarJugador(this.coordenadaJugadores.get(jugadorJuega));
			dado = 0;
			// Decrementamos en uno el jugador, para que no se salte al siguiente
			// ya que al borrarlo el siguiente apunta al siguiente, y al incrementarlo
			// se le salta
			this.jugadorJuega--;
			break;
		}
		return resultado;
	}

	//TODO
	private Coordenada getNextPosition(char direction) {
		return null;
	}

	//TODO
	private void cambiaJugadorAPosicion(Coordenada coordDestino) {
	}

	//TODO
	private void eliminarJugador(Coordenada coordDestino) {
	}


	/**
	 * Escribe el tablero en formato no gráfico. Devuelve el string con la
	 * información
	 */
	@Override
	public String toString() {
		StringBuilder resul = new StringBuilder();
		resul.append(crearSeparador());
		resul.append("     |");

		for (int fila = 0; fila < Constantes.TAMANNO; fila++) {
			for (int columna = 0; columna < Constantes.TAMANNO; columna++) {
				Coordenada coor = new Coordenada(columna, fila);

				if (this.tablero.get(coor) != null) {
					resul.append(" " + this.tablero.get(coor).getType().getSymbol() + " ");
				} else {
					resul.append("   ");
				}

				resul.append("|");
			}
			resul.append(System.lineSeparator()).append(crearSeparador()).append("     |");
		}
		resul.delete(resul.length() - 5, resul.length());
		return resul.toString();
	}

	//TODO
	public String imprimeValoresJugadores() {
		return "";
	}

	//TODO
	public String imprimeNombreJugadores() {
		return "";
	}

	//TODO
	public boolean isTerminado() {
		return false;
	}

	//TODO
	public int getValorDado() {
		return 0;
	}

	//TODO
	public void setDado() {
	}

	//TODO
	public String getNombreJuegadorQueJuega() {
		return null;
	}

	//TODO
	public void proximoJugador() {
	}

	//TODO
	public String getGanador() {
		return null;
	}

	//TODO
	public Element obtenerElementoTablero(Coordenada coordenada) {
		return null;
	}

	//TODO
	public Coordenada obtenerCoordenadaJugadorJuega() {
		return null;
	}

	//TODO
	public void decrementaDado() {
		
	}
}

