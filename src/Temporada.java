import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;


@Entity
public class Temporada {

	private String fecha;
	private String estado;
	private List<Equipo> listaEquipos;
	
	public Temporada () {
		
		fecha = "2023-2024";
		estado = "En progreso";
		listaEquipos = new ArrayList<Equipo>();
		
		
	}
	
	
	public Temporada (String fe) {
		
		fecha = fe;
		estado = "En progreso";
		listaEquipos = new ArrayList<Equipo>();
		
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
		return listaEquipos;
	}

	public void setListaEquipos(List<Equipo> listaEquipos) {
		this.listaEquipos = listaEquipos;
	}
	
	
	
}
