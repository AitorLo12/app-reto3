public class Jugador extends Persona {

	private String posicion;
	private String localidad;
	private int año;
	private int idequipo;
	private String capitan;
	
	public Jugador () {
		
		super ();
		posicion = null;
		localidad = null;
		año = 0;
		idequipo = 0;
		capitan = "";
		
	}

	//constructor personalizado
	
	public Jugador (int id, String n, String img, String p, String l, int a, int ide, String c) {
		
		super(id,n,img);
		posicion = p;
		localidad = l;
		año = a;
		idequipo = ide;
		capitan = c;
		
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public int getAño() {
		return año;
	}

	public void setAño(int año) {
		this.año = año;
	}

	public int getIdequipo() {
		return idequipo;
	}

	public void setIdequipo(int idequipo) {
		this.idequipo = idequipo;
	}

	public String getCapitan() {
		return capitan;
	}

	public void setCapitan(String capitan) {
		this.capitan = capitan;
	}
	
}
