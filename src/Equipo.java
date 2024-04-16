import java.util.ArrayList;
import java.util.List;

public class Equipo {
	
	//Definimos las variables necesarias para la clase
	
	private String nombre;
	private String iniciales;
	private int temporada;
	private int puntos;
	private int pjugados;
	private int	pganados;
	private int	pperdidos;
	private int	ptsfavor;
	private int	ptscontra;
	private String imagenEscudo;
	private String imagenEstadio;
	private String descripcion;
	private List<Persona> listaJugadores;
	
	
	//Constructor por defecto (placeholder)
	
	public Equipo () {
		
		nombre = null;
		iniciales = null;
		temporada = 1;
		puntos = 0;
		pjugados = 0;
		pganados = 0;
		pperdidos = 0;
		ptsfavor = 0;
		ptscontra = 0;
		imagenEscudo = null;
		imagenEstadio = null;
		descripcion = null;
		listaJugadores = new ArrayList<>();
	
	}
	
	//Contructores personalizados
		//Constructor para guardar la información de cada equipo que creemos
	public Equipo (String N,String I, int T, String icn,String E,String D,List<Persona> J) {
		
		nombre = N;
		iniciales = I;
		temporada = T;
		imagenEscudo = icn;
		imagenEstadio = E;
		descripcion = D;
		listaJugadores = J;
		puntos = 0;
		pjugados = 0;
		pganados = 0;
		pperdidos = 0;
		ptsfavor = 0;
		ptscontra = 0;
		
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

	public int getPtsfavor() {
		return ptsfavor;
	}

	public void setPtsfavor(int ptsfavor) {
		this.ptsfavor = ptsfavor;
	}

	public int getPtscontra() {
		return ptscontra;
	}

	public void setPtscontra(int ptscontra) {
		this.ptscontra = ptscontra;
	}

	public String getImagenEscudo() {
		return imagenEscudo;
	}

	public void setImagenEscudo(String imagenEscudo) {
		this.imagenEscudo = imagenEscudo;
	}

	public String getImagenEstadio() {
		return imagenEstadio;
	}

	public void setImagenEstadio(String imagenEstadio) {
		this.imagenEstadio = imagenEstadio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Persona> getListaJugadores() {
		return listaJugadores;
	}

	public void setListaJugadores(List<Persona> listaJugadores) {
		this.listaJugadores = listaJugadores;
	}

}
