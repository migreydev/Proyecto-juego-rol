package logicaJuego;

import static logicaJuego.JuegoUtils.crearSeparador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elementos.*;



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
		for(int i=0;i<Constantes.NUM_JUGADORES;i++) {
			cargarJugadores(tipos[i]);
		}
		crearTablero();
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
				
				Jugador enemigo=(Jugador)elemento;
				int resultadoCombate=jugador.lucha(enemigo);
				switch(resultadoCombate) {
				case Constantes.EMPATE:
					resultado="El resultado es empate";
					break;
				case Constantes.GANA_USA_POCIMA:
					resultado="El jugador " + jugador.getNombre() + " gana y le roba una pocima al rival";
					break;
				case Constantes.GANA_DINERO:
					resultado="El jugador " + jugador.getNombre() + " gana y le roba al rival el dinero";
					break;
				case Constantes.GANA_MUERE:
					resultado="El jugador " + jugador.getNombre() + " gana.";
					this.eliminarJugador(coordDestino);
					break;
				case Constantes.PIERDE_USA_POCIMA:
					resultado="El jugador " + enemigo.getNombre() + " gana y le roba una pocima al jugador";
					break;
				case Constantes.PIERDE_DINERO:
					resultado="El jugador " + enemigo.getNombre() + " gana y le roba al jugador el dinero";
					break;
				case Constantes.PIERDE_MUERE:
					resultado="El jugador " + enemigo.getNombre() + " gana.";
					this.eliminarJugador(this.coordenadaJugadores.get(jugadorJuega));
					dado=0;
					break;
				}
				
			} else if (elemento.getType() == ElementType.ROCA) {
				int resultadoRoca = jugador.encuentraRoca();
				switch(resultadoRoca) {
				case Constantes.ROMPE_ROCA_CON_GEMA:
					resultado="El jugador " + jugador.getNombre() + " rompe la roca con una gema";
					this.cambiaJugadorAPosicion(coordDestino);
					break;
				case Constantes.GANA_A_LA_ROCA:
					resultado="El jugador " + jugador.getNombre() + " gana a la roca";
					this.cambiaJugadorAPosicion(coordDestino);
					break;
				case Constantes.PIERDE_A_LA_ROCA:
					resultado="El jugador " + jugador.getNombre() + " pierde y no se puede mover";
					this.cambiaJugadorAPosicion(coordDestino);
					break;
				}
				
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

	private String luchar(String resultado, Jugador jugador, Coordenada coordDestino, Jugador elemento) {
		Jugador enemigo = elemento;
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

	
	private Coordenada getNextPosition(char direction) throws JuegoException{
		Coordenada cor=this.coordenadaJugadores.get(jugadorJuega).clone();
		if(direction=='N') {
			cor.goUp();
		}else if(direction=='S') {
			cor.goDown();
		}else if(direction=='E') {
			cor.goRight();
		}else if(direction=='O') {
			cor.goLeft();
		}else {
			throw new JuegoException("La coordenada introducida no existe");
		}
		return null;
	}

	
	private void cambiaJugadorAPosicion(Coordenada coordDestino) {
		
		Coordenada cor=this.coordenadaJugadores.get(jugadorJuega);
		Element player=(Element)this.tablero.get(cor);
		tablero.remove(cor);
		tablero.put(cor, player);
		this.coordenadaJugadores.remove(jugadorJuega);
		this.coordenadaJugadores.add(jugadorJuega,cor);
		
	}

	
	private void eliminarJugador(Coordenada coordDestino) {
		this.tablero.remove(coordDestino);
		this.coordenadaJugadores.remove(coordDestino);
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
		StringBuilder sb=new StringBuilder("");
		int cont=1;
		for (Element e : this.tablero.values()) {
			if(e instanceof Jugador jugador) {
				sb.append("El jugador " + cont + " es un " + jugador.getNombre() + "\n");
				cont++;
			}
		}
		return sb.toString();
	}

	//TODO
	public String imprimeNombreJugadores() {
		StringBuilder sb=new StringBuilder("");
		int cont=1;
		for (Element e : this.tablero.values()) {
			if(e instanceof Jugador jug) {
				sb.append("El jugador " + cont + " es un " + jug.getNombre()+ "\n");
				cont++;
			}
		}
		return sb.toString();
	}

	//TODO
	public boolean isTerminado() {
		boolean terminado=false;
		if(coordenadaJugadores.size()==1) {
			terminado=true;
		}
		for (Element elemento : this.tablero.values()) {
			if(elemento instanceof Jugador player && player.getDinero()==Constantes.DINERO) {
				terminado=true;
			}
		}
		return terminado;
	}

	
	public int getValorDado() {
		return dado;
	}

	//TODO
	public void setDado() {
		dado;
	}

	//TODO
	public String getNombreJuegadorQueJuega() {
		Jugador jug= (Jugador)tablero.get(coordenadaJugadores.get(jugadorJuega));
		return jug.getNombre();
	}

	//TODO
	public void proximoJugador() {
		if(this.jugadorJuega==this.coordenadaJugadores.size()-1) {
			this.jugadorJuega=0;
		}else {
			this.jugadorJuega+=1;
		}
	}

	//TODO
	public String getGanador() {
		String winner="Aún no tenemos al ganador";
		if(this.coordenadaJugadores.size()==1) {
			Jugador jug=(Jugador)tablero.get(coordenadaJugadores.get(jugadorJuega));
			winner=jug.toString();
		}
		
		for (Element e : tablero.values()) {
			if(e instanceof Jugador jug && jug.getDinero()==Constantes.DINERO) {
				winner=jug.toString();
			}
		}
		return winner;
	}

	//TODO
	public Element obtenerElementoTablero(Coordenada coordenada) {
		return this.tablero.get(coordenada);
	}

	//TODO
	public Coordenada obtenerCoordenadaJugadorJuega() {
		return this.coordenadaJugadores.get(jugadorJuega);
	}

	//TODO
	public void decrementaDado() {
		dado-=1;
	}
	
	public int getMovimientoJugador() {
		Jugador j = (Jugador)tablero.get(coordenadaJugadores.get(jugadorJuega));
		return j.getVelocidadParaLuchar();
	}
	
	private void crearGemas() {
		int cont=0;
		while(cont<Constantes.NUM_GEMAS) {
			Coordenada coord=new Coordenada();
			Element e=new Element(ElementType.GEMA);
			
			if(!tablero.containsKey(coord)) {
				this.tablero.put(coord, e);
				cont+=1;
			}
		}
	}
	
	private void crearRocas() {
		int cont=0;
		while(cont<Constantes.NUM_ROCAS) {
			Coordenada coord=new Coordenada();
			Element e=new Element(ElementType.ROCA);
			
			if(!tablero.containsKey(coord)) {
				this.tablero.put(coord, e);
				cont+=1;
			}
		}
	}
	
	private void crearDinero() {
		int cont=0;
		while(cont<Constantes.NUM_DINERO) {			
			Coordenada coord=new Coordenada();
			Element e=new Element(ElementType.DINERO);
			
			if(!tablero.containsKey(coord)) {
				this.tablero.put(coord, e);
				cont+=1;
			}
		}
	}
	
	private void crearPocimas() {
		int cont=0;
		while(cont<Constantes.NUM_POCIONES) {
			Coordenada coord=new Coordenada();
			Element e=new Element(ElementType.POCION);
			
			if(!tablero.containsKey(coord)) {
				this.tablero.put(coord, e);
				cont+=1;
			}
		}
	}
	
	public boolean cargarJugadores(PlayerType tipo) {
		boolean crear=false;
		Jugador jugador=new Jugador(tipo);
		Coordenada coord=new Coordenada();
		while(coordenadaJugadores.contains(coord)) {
			coord=new Coordenada();
			crear=true;
		}
		coordenadaJugadores.add(coord);
		tablero.put(coord, jugador);
		return crear;
	}
	
	private void crearTablero() {
		crearDinero();
		crearGemas();
		crearPocimas();
		crearRocas();
	}
}

