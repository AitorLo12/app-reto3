public class Persona {

	//Definimos las variables necesarias para la clase
	
	private String nombre;
	private String apellido;
	private String imagen;
	
	
	//Constructor por defecto
	
	public Persona () {
		
		nombre = null;
		apellido = null;
		imagen = null;
		
	}
	
	//Constructores personalizados
		//Jugadores
	public Persona (String N,String A,String I,String P) {
		
		nombre = N;
		apellido = A;
		imagen = I;
		
	}
		//Entrenadores
	public Persona (String N,String A,String I) {
		
		nombre = N;
		apellido = A;
		imagen = I;
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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	
}
