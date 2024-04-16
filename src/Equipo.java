import java.util.ArrayList;
import java.util.List;

public class Equipo {
	
	//Definimos las variables necesarias para la clase
	
	String nombre;
	String iniciales;
	int puntos;
	int pjugados;
	int	pganados;
	int	pperdidos;
	int	ptsfavor;
	int	ptscontra;
	String imagenEscudo;
	String imagenEstadio;
	String descripcion;
	List<Persona> listaJugadores;
	
	
	//Constructor por defecto (placeholder)
	
	public Equipo () {
		
		this.nombre = null;
		this.iniciales = null;
		this.puntos = 0;
		this.pjugados = 0;
		this.pganados = 0;
		this.pperdidos = 0;
		this.ptsfavor = 0;
		this.ptscontra = 0;
		this.imagenEscudo = null;
		this.imagenEstadio = null;
		this.descripcion = null;
		this.listaJugadores = new ArrayList<>();
	
	}
	
	//Contructores personalizados
		//Constructor para guardar la información de cada equipo que creemos
	public Equipo (String N,String I,String icn,String E,String D,List<Persona> J) {
		
		this.nombre = N;
		this.iniciales = I;
		this.imagenEscudo = icn;
		this.imagenEstadio = E;
		this.descripcion = D;
		this.listaJugadores = J;
		this.puntos = 0;
		this.pjugados = 0;
		this.pganados = 0;
		this.pperdidos = 0;
		this.ptsfavor = 0;
		this.ptscontra = 0;
		
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
