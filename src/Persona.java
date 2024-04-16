public class Persona {

	//Definimos las variables necesarias para la clase
	
	private String nombre;
	private String apellido;
	private String imagen;
	private String posicion;
	
	
	//Constructor por defecto
	
	public Persona () {
		
		nombre = null;
		apellido = null;
		imagen = null;
		posicion = null;
		
	}
	
	//Constructores personalizados
		//Jugadores
	public Persona (String N,String A,String I,String P) {
		
		nombre = N;
		apellido = A;
		imagen = I;
		posicion = P;
		
	}
		//Entrenadores
	public Persona (String N,String A,String I) {
		
		nombre = N;
		apellido = A;
		imagen = I;
		posicion = "Entrenador";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getPosicion() {
		return posicion;
	}

	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
