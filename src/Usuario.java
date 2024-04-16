public class Usuario {

	
	String Nombre;
	String Contraseña; 
	String Permisos;
	
	
	//Constructor por defecto
	public Usuario (){
		
		Nombre = "admin";
		Contraseña = "1234";
		Permisos = "Admin";
		
	}
	
	//Contrusctores personalizados
		//Constructor en el que podríamos añadir nombres de usuario, sus contraseñas y permisos
	public Usuario (String U, String C,String P) {
		
		Nombre = U;
		Contraseña = C;
		Permisos = P;
		
	}
		//O
	public Usuario (String U, String C) {
		//Constructor de creación de usuarios desde el registro con permisos de usuario
		Nombre = U;
		Contraseña = C;
		Permisos = "Usuario";
		
		
	}

	
	//getters y setters de los parametros
	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public String getContraseña() {
		return Contraseña;
	}

	public void setContraseña(String contraseña) {
		Contraseña = contraseña;
	}

	public String getPermisos() {
		return Permisos;
	}

	public void setPermisos(String permisos) {
		Permisos = permisos;
	}
	
}
