package proves;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "usuaris", schema = "public")
public class Usuari implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuari")
	private Integer id;

	@Column(name = "nom_usuari", length = 32, nullable = false)
	private String nombre;

	@Column(name = "cognoms_usuari", length = 64, nullable = false)
	private String apellidos;

	@Column(name = "email_usuari", length = 255, nullable = false)
	private String email;

	@Column(name = "contransenya_usuari", length = 255, nullable = false)
	private String contrasenia;

	@Column(name = "rol_usuari", length = 255, nullable = false)
	private String rol;

	// Constructor por defecto (necesario para JPA)
	public Usuari() {
		this.id = 111111;
	}

	public Usuari(int i, String string, String string2, String string3, String string4, String string5) {
		this.id=i;
		this.nombre=string;
		this.apellidos=string2;
		this.contrasenia=string3;
		this.email=string4;
		this.rol=string5;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getFullName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getEmail() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public String toString() {
		return "Usuari [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", email=" + email
				+ ", contrasenia=" + "***" + ", rol=" + rol + "]";
	}

}
