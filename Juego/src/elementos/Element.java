package elementos;

import java.util.Objects;

public class Element {
	
	private ElementType tipo;
	
	public Element(ElementType tipo) {
		super();
		this.tipo=tipo;
	}

	public ElementType getType() {
		return tipo;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(tipo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;
		return tipo == other.tipo;
	}
	
	
	@Override
	public String toString() {
		return "Element [tipo=" + tipo + "]";
	}
	
	
}
