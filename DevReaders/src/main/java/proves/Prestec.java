package proves;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "prestems", schema = "public")
public class Prestec implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestem")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuari_prestem")
    private Usuari usuari;

    @ManyToOne
    @JoinColumn(name = "id_llibre_prestem")
    private Llibre llibre;

    @Column(name = "data_prestem", nullable = false)
    private Date dataPrestem;

    @Column(name = "data_devolucio_prestem")
    private Date dataDevolucio;

    // Constructor por defecto
    public Prestec() {
    }

    // Constructor con parámetros
    public Prestec(Usuari usuari, Llibre llibre, Date dataPrestem) {
        this.usuari = usuari;
        this.llibre = llibre;
        this.dataPrestem = dataPrestem;
    }

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Llibre getLlibre() {
        return llibre;
    }

    public void setLlibre(Llibre llibre) {
        this.llibre = llibre;
    }

    public Date getDataPrestem() {
        return dataPrestem;
    }

    public void setDataPrestem(Date dataPrestem) {
        this.dataPrestem = dataPrestem;
    }

    public Date getDataDevolucio() {
        return dataDevolucio;
    }

    public void setDataDevolucio(Date dataDevolucio) {
        this.dataDevolucio = dataDevolucio;
    }

	public void setId(Integer id) {
		this.id = id;
	}
}
