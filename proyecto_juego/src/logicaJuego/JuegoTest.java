package logicaJuego;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import elementos.Coordenada;
import elementos.Jugador;
import elementos.JugadorException;
import elementos.PlayerType;

class JuegoTest {

	@Test
	void testGetNextPosition() throws JuegoException {
	    Jugador jugador = new Jugador(PlayerType.GUERRERO);
	    Coordenada coordenadaInicial = new Coordenada(0, 0);
	    Coordenada siguientePosicionNorte = jugador.getNextPosition('N');
	    assertEquals(new Coordenada(0, 1), siguientePosicionNorte);
	}
	
	void testEncuentraRoca() {
	    Jugador jugador = new Jugador(PlayerType.GUERRERO);
	    
	    jugador.encuentraGema();
	    int resultado = jugador.encuentraRoca();
	    assertEquals(Constantes.ROMPE_ROCA_CON_GEMA, resultado);
	    assertEquals(0, jugador.encuentraRoca());
	}
	
	@Test
	void testLucha() throws JugadorException {
	    Jugador jugador1 = new Jugador(PlayerType.GUERRERO);
	    Jugador jugador2 = new Jugador(PlayerType.MAGO);

	    int fuerzaJugador1 = jugador1.getFuerzaParaLuchar();
	    int fuerzaJugador2 = jugador2.getFuerzaParaLuchar();

	    int resultado = jugador2.lucha(jugador1);

	    if (fuerzaJugador1 > fuerzaJugador2) {
	        assertTrue(resultado == Constantes.GANA_MUERE);
	    } else if (fuerzaJugador2 > fuerzaJugador1) {
	    	assertTrue(resultado == Constantes.PIERDE_MUERE);
	    } else {
	        assertEquals(resultado, Constantes.EMPATE);
	    }
	}
	
	@Test
	public void cargarJugadoresTest() {
	    PlayerType[] tipos = {PlayerType.GUERRERO}; // Array de tipos de jugador
	    Juego juego = new Juego(tipos); // Crear instancia de la clase Juego y pasar los tipos de jugador

	    Jugador jugador = juego.getTablero().get(juego.getCoordenadaJugadores().get(0));
	    assertNotNull(jugador);
	    assertTrue(juego.getTablero().containsValue(jugador));
	    assertTrue(juego.getCoordenadaJugadores().contains(juego.getCoordenadaJugadores().get(0)));
	}

}
