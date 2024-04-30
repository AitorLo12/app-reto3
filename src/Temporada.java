import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;


@Entity
public class Temporada {

	private String fecha;
	private String estado;
	private List<Equipo> listaEquiposT;
	private List<Jornada> listaJornadas;
	
	public Temporada () {
		
		fecha = "2023";
		estado = "En progreso";
		listaEquiposT = new ArrayList<Equipo>();
		listaJornadas = new ArrayList<Jornada>();
		
	}
	
	
	public Temporada (String fe) {
		
		fecha = fe;
		estado = "En progreso";
		listaEquiposT = new ArrayList<Equipo>();
		listaJornadas = new ArrayList<Jornada>();
		
	}
	
	public Temporada (String fe, String E) {
		
		fecha = fe;
		estado = E;
		listaEquiposT = new ArrayList<Equipo>();
		listaJornadas = new ArrayList <Jornada>();
		
	}
	
	public Temporada (String fe,Equipo A,Equipo B, Equipo C,Equipo D,Equipo E, Equipo F){
		fecha = fe;
		estado = "En progreso";
		listaEquiposT = new ArrayList<Equipo>();
		listaEquiposT.add(A);
		listaEquiposT.add(B);
		listaEquiposT.add(C);
		listaEquiposT.add(D);
		listaEquiposT.add(E);
		listaEquiposT.add(F);	
		listaJornadas = new ArrayList<Jornada>();
		
	}
	
	public Temporada (String fe,String es, Equipo A,Equipo B, Equipo C,Equipo D,Equipo E, Equipo F){
		fecha = fe;
		estado = es;
		listaEquiposT = new ArrayList<Equipo>();
		listaEquiposT.add(A);
		listaEquiposT.add(B);
		listaEquiposT.add(C);
		listaEquiposT.add(D);
		listaEquiposT.add(E);
		listaEquiposT.add(F);	
		listaJornadas = new ArrayList<Jornada>();
		
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Equipo> getListaEquipos() {
		return listaEquiposT;
	}

	public void setListaEquipos(List<Equipo> listaEquipos) {
		this.listaEquiposT = listaEquipos;
	}


	public List<Equipo> getListaEquiposT() {
		return listaEquiposT;
	}


	public void setListaEquiposT(List<Equipo> listaEquiposT) {
		this.listaEquiposT = listaEquiposT;
	}


	public List<Jornada> getListaJornadas() {
		return listaJornadas;
	}


	public void setListaJornadas(List<Jornada> listaJornadas) {
		this.listaJornadas = listaJornadas;
	}
	
	
	
}
