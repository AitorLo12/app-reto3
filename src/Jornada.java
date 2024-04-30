import java.util.ArrayList;
import java.util.List;

public class Jornada {

	private int ID;
	private int Numero;
	private List<Partido> listaPartidos;

	
	
	
	public Jornada () {
		
		ID = 0;
		Numero = 0;
		listaPartidos = new ArrayList<Partido>();
		
}
	
	public Jornada (int id, int N, List <Partido> L) {
		
		ID = id;
		Numero = N;
		listaPartidos = L;
		
		
	}




	public int getNumero() {
		return Numero;
	}




	public void setNumero(int numero) {
		Numero = numero;
	}




	public List<Partido> getListaPartidos() {
		return listaPartidos;
	}




	public void setListaPartidos(List<Partido> listaPartidos) {
		this.listaPartidos = listaPartidos;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	
	
}
