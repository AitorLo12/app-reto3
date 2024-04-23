import javax.persistence.Entity;

@Entity
public class Usuario {

	
	private String Nombre;
	private String Contraseña; 
	private String Permisos;
	private String Correo;
	
	
	//Constructor por defecto
	public Usuario (){
		
		Nombre = "admin";
		Contraseña = "1234";
		Permisos = "Admin";
		Correo = "admin@gmail.com";
		
	}
	
	//Contrusctores personalizados
		//Constructor en el que podríamos añadir nombres de usuario, sus contraseñas y permisos
	public Usuario (String U, String C,String P) {
		
		Nombre = U;
		Contraseña = C;
		Permisos = P;
		Correo = U+"@gmail.com";
		
	}
	
	public Usuario (String U, String C) {
		//Constructor de creación de usuarios desde el registro con permisos de usuario
		Nombre = U;
		Contraseña = C;
		Permisos = "Usuario";
		Correo = U+"gmail.com";
		
		
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
	public String getCorreo() {
		return Correo;
	}

	public void setCorreo(String correo) {
		Correo = correo;
	}
	
}
