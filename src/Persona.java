public class Persona {

	//Definimos las variables necesarias para la clase
	
	private int ID;
	private String nombre;
	private String imagen;
	
	
	//Constructor por defecto
	
	public Persona () {
		
		ID = 0;
		nombre = null;
		imagen = null;
		
	}
	
	//Constructores personalizados
	public Persona (int id,String N,String I) {
		
		ID = id;
		nombre = N;
		imagen = I;
		
	}
	
	//Constructor copia
	public Persona (Persona p) {
		
		nombre = p.nombre;
		imagen = p.imagen;
		
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
