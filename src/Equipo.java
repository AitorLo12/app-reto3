import java.util.ArrayList;
import java.util.List;

public class Equipo {
	
	//Definimos las variables necesarias para la clase
	
	private String nombre;
	private String iniciales;
	private int ID;
	private int temporada;
	private int puntos;
	private int pjugados;
	private int	pganados;
	private int	pperdidos;
	private int	golesfavor;
	private int	golescontra;
	private String imagenEscudo;
	private String Estadio;
	private String equipacion;
	private List<Jugador> listaJugadores;
	
	
	//Constructor por defecto (placeholder)
	
	public Equipo () {
		
		nombre = null;
		iniciales = null;
		ID = 0;
		temporada = 0;
		puntos = 0;
		pjugados = 0;
		pganados = 0;
		pperdidos = 0;
		golesfavor = 0;
		golescontra = 0;
		imagenEscudo = null;
		Estadio = null;
		equipacion = null;
		listaJugadores = new ArrayList<>();
	
	}
	
	//Constructor copia
	public Equipo(Equipo c) {
		
		nombre = c.nombre;
		iniciales = c.iniciales;
		ID = c.ID;
		temporada = c.temporada;
		puntos = c.puntos;
		pjugados = c.pjugados;
		pganados = c.pganados;
		pperdidos = c.pperdidos;
		golesfavor = c.golesfavor;
		golescontra = c.golescontra;
		imagenEscudo = c.imagenEscudo;
		Estadio = c.Estadio;
		equipacion = c.equipacion;
		listaJugadores = c.listaJugadores;
		
	}
	
	//Contructores personalizados
	//Constructor para guardar la información de cada equipo que creemos cogiendo los datos desde la base de datos
	public Equipo (String N,String I,int id, int T, String icn,String E,String eq) {
		
		nombre = N;
		iniciales = I;
		ID = id;
		temporada = T;
		imagenEscudo = icn;
		Estadio = E;
		equipacion = eq;
		listaJugadores = new ArrayList<Jugador>();
		puntos = 0;
		pjugados = 0;
		pganados = 0;
		pperdidos = 0;
		golesfavor = 0;
		golescontra = 0;
		
	}
	//Constructor para guardar la información de cada equipo que creemos
	public Equipo (String N,String I,int id, int T, String icn,String E,String eq,List<Jugador> J) {
		
		nombre = N;
		iniciales = I;
		ID = id;
		temporada = T;
		imagenEscudo = icn;
		Estadio = E;
		equipacion = eq;
		listaJugadores = J;
		puntos = 0;
		pjugados = 0;
		pganados = 0;
		pperdidos = 0;
		golesfavor = 0;
		golescontra = 0;
		
	}
	
	//Constructor para la tabla de la clasificacion
	public Equipo(int id,String N,int T, int P, int PJ, int PG, int PP, int GF, int GC) {

		ID = id;
		nombre = N;
		temporada = T;
		puntos = P;
		pjugados = PJ;
		pganados = PG;
		pperdidos = PP;
		golesfavor = GF;
		golescontra = GC;
		iniciales = "";
		imagenEscudo = "";
		Estadio = "";
		
	
	
	}
		
		//Constructor para buscar solo el nombre del equipo
	public Equipo (String N) {
		
		nombre = N;
		
	}
		
	
	
		

	
	//getters y setters de todos los parametros
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}
	
	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada= temporada;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getPjugados() {
		return pjugados;
	}

	public void setPjugados(int pjugados) {
		this.pjugados = pjugados;
	}

	public int getPganados() {
		return pganados;
	}

	public void setPganados(int pganados) {
		this.pganados = pganados;
	}

	public int getPperdidos() {
		return pperdidos;
	}

	public void setPperdidos(int pperdidos) {
		this.pperdidos = pperdidos;
	}

	public int getGolesfavor() {
		return golesfavor;
	}

	public void setGolesfavor(int golesfavor) {
		this.golesfavor = golesfavor;
	}

	public int getGolescontra() {
		return golescontra;
	}

	public void setGolescontra(int golescontra) {
		this.golescontra = golescontra;
	}

	public String getImagenEscudo() {
		return imagenEscudo;
	}

	public void setImagenEscudo(String imagenEscudo) {
		this.imagenEscudo = imagenEscudo;
	}

	public String getEstadio() {
		return Estadio;
	}

	public void setEstadio(String estadio) {
		this.Estadio = estadio;
	}

	public List<Jugador> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(List<Jugador> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getEquipacion() {
		return equipacion;
	}

	public void setEquipacion(String equipacion) {
		this.equipacion = equipacion;
	}
	
}
