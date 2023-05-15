package proves;

import javax.persistence.*;

@Entity
@Table(name = "llibres", schema = "public")
public class Llibre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_llibre")
	private Integer id;

	@Column(name = "titol_llibre", nullable = false)
	private String titol;

	@Column(name = "id_autor_llibre", nullable = false)
	private Integer idAutor;

	@Column(name = "id_editorial_llibre", nullable = false)
	private Integer idEditorial;

	@Column(name = "any_publicacio_llibre", nullable = false)
	private Integer anyPublicacio;

	@Column(name = "ISBN_llibre", nullable = false)
	private String ISBN;

	@Column(name = "id_categoria_llibre", nullable = false)
	private Integer idCategoria;

	@Column(name = "descripcio_llibre")
	private String descripcio;

	// Otros atributos del libro (autor, editorial, etc.)

	// Constructor por defecto
	public Llibre() {
	}

	// Constructor con parámetros
	public Llibre(String titol) {
		this.titol = titol;
	}

	public Llibre(int id2, String titol2, int autor, int anyPublicacio) {
		this.id = id2;
		this.titol = titol2;
		this.idAutor = autor;
		this.anyPublicacio = anyPublicacio;
	}

	// Getters y Setters
	public Integer getId() {
		return id;
	}

	public String getTitol() {
		return titol;
	}

	public void setTitol(String titol) {
		this.titol = titol;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}

	public Integer getIdEditorial() {
		return idEditorial;
	}

	public void setIdEditorial(Integer idEditorial) {
		this.idEditorial = idEditorial;
	}

	public Integer getAnyPublicacio() {
		return anyPublicacio;
	}

	public void setAnyPublicacio(Integer anyPublicacio) {
		this.anyPublicacio = anyPublicacio;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// Otros métodos de la clase Llibre
}
