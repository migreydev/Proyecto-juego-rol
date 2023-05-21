package elementos;

import java.util.Objects;

public class Coordenada {

	private int x;
	private int y;
	
	public Coordenada() {
		
	}
	
	public  Coordenada(int x, int y) {
		super();
		this.x=x;
		this.y=y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		Coordenada other = (Coordenada) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "Coordenada [x=" + x + ", y=" + y + "]";
	}
	
	public boolean goRight() {
		return false;
	}
	
	public boolean goLeft() {
		return false;
	}
	
	public boolean goUp() {
		return false;
	}
	
	public boolean goDown() {
		return false;
	}
	
	public Coordenada clone() {
		return null;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	
	
	
	
	
	
}
