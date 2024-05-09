public class Partido {

	private int ID;
	private int Numero;
	private Equipo EquipoLocal;
	private int PtsLocal;
	private Equipo EquipoVisit;
	private int PtsVisit;
	private String Estado;
	
	public Partido (int id,int N, Equipo EL, int PL, Equipo EV, int PV, String E) {
		
		ID = id;
		Numero = N;
		EquipoLocal = EL;
		PtsLocal = PL;
		EquipoVisit = EV;
		PtsVisit = PV;
		Estado = E;
		
	}
	
	public Partido (int id,Equipo EL, int PL, Equipo EV, int PV, String E) {
		
		ID = id;
		EquipoLocal = EL;
		PtsLocal = PL;
		EquipoVisit = EV;
		PtsVisit = PV;
		Estado = E;
		
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getNumero() {
		return Numero;
	}

	public void setNumero(int numero) {
		Numero = numero;
	}

	public Equipo getEquipoLocal() {
		return EquipoLocal;
	}

	public void setEquipoLocal(Equipo equipoLocal) {
		EquipoLocal = equipoLocal;
	}

	public int getPtsLocal() {
		return PtsLocal;
	}

	public void setPtsLocal(int ptsLocal) {
		PtsLocal = ptsLocal;
	}

	public Equipo getEquipoVisit() {
		return EquipoVisit;
	}

	public void setEquipoVisit(Equipo equipoVisit) {
		EquipoVisit = equipoVisit;
	}

	public int getPtsVisit() {
		return PtsVisit;
	}

	public void setPtsVisit(int ptsVisit) {
		PtsVisit = ptsVisit;
	}

	public String getEstado() {
		return Estado;
	}

	public void setEstado(String estado) {
		Estado = estado;
	}
	
}
