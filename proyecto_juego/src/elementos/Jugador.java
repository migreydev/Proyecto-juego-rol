package elementos;

import java.util.Random;

import logicaJuego.Constantes;
import logicaJuego.JuegoException;

public class Jugador extends Element {

	protected int dinero;
	protected int pociones;
	protected int gemas;
	protected PlayerType jugador;
	static Random number=new Random();
	
	
	public Jugador(PlayerType jugador) {
		super();
		this.jugador=jugador;
		this.dinero=0;
		this.pociones=0;
		this.gemas=0;
	}
	
	public String getNombre() {
		return this.jugador.name();
	}
	
	public int getFuerzaParaLuchar() {
		return number.nextInt(getFuerza());
	}
	
	private int getFuerza() {
		return this.jugador.getFuerza();
	}
	
	private int getMagia() {
		return this.jugador.getMagia();
	}
	
	public int getMagiaParaLuchar() {
		return number.nextInt(this.getMagia());
	}
	
	private int getVelocidad() {
		return this.jugador.getVelocidad();
	}
	
	public int getVelocidadParaLuchar() {
		int velocidad=0;
		while(velocidad==0) {
			velocidad=number.nextInt(this.getVelocidad());
		}
		return velocidad;
	}
	
	public int getDinero() {
		return dinero;
	}
	
	public void setDinero(int dinero)throws JugadorException{
		this.dinero=dinero;
		if(dinero>Constantes.DINERO || dinero<0) {
			throw new JugadorException("El dinero no es válido");
		}
		this.dinero=dinero;
	}
	
	public int getPociones() {
		return pociones;
	}
	
	public void setPociones(int pociones) throws JugadorException{
		if(pociones>Constantes.NUM_POCIONES||pociones<0) {
			throw new JugadorException("Pociones no válidas");
		}
		this.pociones=pociones;
	}
	
	public int getGemas() {
		return gemas;
	}
	
	public void setGemas(int gemas) throws JugadorException{
		if(gemas>Constantes.NUM_GEMAS||gemas<0) {
			throw new JugadorException("Gemas no válidas");
		}
		this.gemas=gemas;
	}
	
	public String resumen() {
		return "Jugador: " + this.getNombre() + " Pociones: " + this.getPociones() + " Gemas: " + this.getGemas() +
				" Magia: " + this.getMagia() + " Fuerza: " + this.getFuerza() + " Dinero: " + this.getDinero();
	}
	
	public PlayerType getPlayer() {
		return jugador;
		
	}
	
	public int lucha(Jugador j1) throws JugadorException {
		int resultado=0;
		int fuerzaJugador=this.getFuerzaParaLuchar();
		int fuerzaRival=this.getFuerzaParaLuchar();
		
		if(fuerzaJugador>fuerzaRival) {
			if(j1.getPociones()>=1) {
				j1.pociones-=1;
				resultado=Constantes.GANA_USA_POCIMA;
			}else if(j1.getDinero()>=1) {
				this.setDinero(this.getDinero()+j1.getDinero());
				j1.setDinero(0);
				resultado=Constantes.GANA_DINERO;
			}else {
				resultado=Constantes.GANA_MUERE;
			}
		}else if(fuerzaRival>fuerzaJugador){
			if(j1.getPociones()>=1) {
				j1.pociones-=1;
				resultado=Constantes.PIERDE_USA_POCIMA;
			}else if(j1.getDinero()>=1) {
				this.setDinero(j1.getDinero()+this.getDinero());
				j1.setDinero(0);
				resultado=Constantes.PIERDE_DINERO;
			}else {
				resultado=Constantes.PIERDE_MUERE;
			}
		}else {
			resultado=Constantes.EMPATE;
		}
		return resultado;
	}
	
	public int encuentraRoca() {
		int resultado;
		int jugadorMagia=this.getMagiaParaLuchar();
		if(this.getGemas()>=1) {
			this.gemas-=1;
			resultado=Constantes.ROMPE_ROCA_CON_GEMA;
		}else {
			if(jugadorMagia>=4) {
				resultado=Constantes.GANA_A_LA_ROCA;
			}else {
				resultado=Constantes.PIERDE_A_LA_ROCA;
			}
		}
		return resultado;
	}
	
	public Coordenada getNextPosition(char direction) throws JuegoException {
        int deltaX = 0;
        int deltaY = 0;
        Coordenada coordenada = new Coordenada();
        switch (Character.toUpperCase(direction)) {
            case 'N':
                deltaY = 1;
                break;
            case 'S':
                deltaY = -1;
                break;
            case 'W':
                deltaX = -1;
                break;
            case 'E':
                deltaX = 1;
                break;
            default:
                throw new JuegoException("direccion no valida.");
        }

        int nextX = coordenada.getX() + deltaX;
        int nextY = coordenada.getY() + deltaY;

        return new Coordenada(nextX, nextY);
    }
	
	public void encuentraDinero() {
		this.dinero+=1;
	}
	
	public void encuentraPocion() {
		this.pociones+=1;
	}
	
	
	public void encuentraGema() {
		this.gemas+=1;
	}

	
	
}
