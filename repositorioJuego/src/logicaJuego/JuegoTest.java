package logicaJuego;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import elementos.Coordenada;
import elementos.Element;
import elementos.Jugador;
import elementos.PlayerType;

public class JuegoTest {

	@Test
	public void testLuchar() {
	    Jugador jugador1 = new Jugador(PlayerType.GUERRERO);
	    Jugador jugador2 = new Jugador(PlayerType.MAGO);

	    int GANA_JUGADOR1 = Constantes.GANA_MUERE;

	    PlayerType[] tipos = {PlayerType.GUERRERO, PlayerType.MAGO};
	    Juego juego = new Juego(tipos);
	    int resultado = jugador1.lucha(jugador2);

	    assertNotEquals(GANA_JUGADOR1, resultado);
	}
	
	@Test
	public void testEliminarJugador() {
	    PlayerType[] tipos = {PlayerType.GUERRERO};
	    Juego juego = new Juego(tipos);
	    Coordenada coordenadaJugador = new Coordenada(2, 3);
	    
	    //esta incompleto 
	}



	

	
}




