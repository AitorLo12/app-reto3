import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;


@Entity
public class Temporada {

	private String fecha;
	private String estado;
	private List<Equipo> listaEquiposT;
	
	public Temporada () {
		
		fecha = "2023";
		estado = "En progreso";
		listaEquiposT = new ArrayList<Equipo>();
		
		
	}
	
	
	public Temporada (String fe) {
		
		fecha = fe;
		estado = "En progreso";
		listaEquiposT = new ArrayList<Equipo>();
		
	}
	
	public Temporada (String fe, String E) {
		
		fecha = fe;
		estado = E;
		listaEquiposT = new ArrayList<Equipo>();
		
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
	
	
	
}
