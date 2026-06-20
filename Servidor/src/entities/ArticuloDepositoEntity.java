package entities;

import java.io.Serializable;
import javax.persistence.*;

import negocio.ArticuloDeposito;

@Entity
@Table(name="ArticulosDeposito")
public class ArticuloDepositoEntity implements Serializable{


	private static final long serialVersionUID = 3150698741905256870L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer idArticuloDeposito;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn (name="idArticulo")
	private ArticuloEntity articulo;
	
	private String estado;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER, optional = false)
	@JoinColumn (name="idLote")
	private LoteEntity lote;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn (name="idUbicacion")
	private UbicacionEntity ubicacion;
	
	@Column (name="idPedido")
	private Integer reservaIdPedido;

	public ArticuloDepositoEntity(Integer idArticuloDeposito, ArticuloEntity articulo, String estado,
			LoteEntity lote, UbicacionEntity ubicacion, int reservaIdPedido) {
		super();
		this.idArticuloDeposito = idArticuloDeposito;
		this.articulo = articulo;
		this.estado = estado;
		this.lote = lote;
		this.ubicacion = ubicacion;
		this.reservaIdPedido=reservaIdPedido;
	}


	public ArticuloDeposito toNegocio() {
		ArticuloDeposito aux = new ArticuloDeposito();
		aux.setEstado(this.estado);
		aux.setIdArticuloDeposito(this.idArticuloDeposito);
		aux.setLote(this.lote.toNegocio());
		aux.setUbicacion(this.ubicacion.toNegocio());
		aux.setReservaIdPedido(this.reservaIdPedido);
		aux.setArticulo(this.articulo.toNegocio());
		return aux;
	}


	public ArticuloDepositoEntity(){}


	//GETTERS Y SETTERS

	public Integer getIdArticuloDeposito() {
		return idArticuloDeposito;
	}

	public ArticuloEntity getArticulo() {
		return articulo;
	}


	public void setArticulo(ArticuloEntity articulo) {
		this.articulo = articulo;
	}


	public LoteEntity getLote() {
		return lote;
	}


	public void setLote(LoteEntity lote) {
		this.lote = lote;
	}


	public UbicacionEntity getUbicacion() {
		return ubicacion;
	}


	public void setUbicacion(UbicacionEntity ubicacion) {
		this.ubicacion = ubicacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Integer getReservaIdPedido() {
		return reservaIdPedido;
	}

	public void setReservaIdPedido(Integer reservaIdPedido) {
		this.reservaIdPedido = reservaIdPedido;
	}

	public void setIdArticuloDeposito(Integer idArticuloDeposito) {
		this.idArticuloDeposito = idArticuloDeposito;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}