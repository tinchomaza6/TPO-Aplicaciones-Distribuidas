package negocio;

import dao.ArticuloDepositoDao;
import dto.ArticuloDepositoDTO;
import entities.ArticuloDepositoEntity;
import entities.LoteEntity;
import excepciones.ArticuloException;

public class ArticuloDeposito {
	private int idArticuloDeposito;
	private Articulo articulo;
	private String estado;
	private Lote lote;
	private Ubicacion ubicacion;
	private Integer reservaIdPedido;

	public ArticuloDeposito(int idArticuloDeposito, Articulo articulo, String estado, Lote lote,
			Ubicacion ubicacion, Integer reservaIdPedido) {
		super();
		this.idArticuloDeposito = idArticuloDeposito;
		this.articulo = articulo;
		this.estado = estado;
		this.lote = lote;
		this.ubicacion = ubicacion;
		this.reservaIdPedido=reservaIdPedido;
	}

	public ArticuloDeposito(Articulo articulo, Lote lote) {
		super();
		this.articulo = articulo;
		this.estado = "DISPONIBLE";
		this.lote = lote;
		this.reservaIdPedido=0;
	}


	public ArticuloDeposito() {
		// TODO Auto-generated constructor stub
	}

	public ArticuloDepositoEntity toEntity(){
		ArticuloDepositoEntity aux = new ArticuloDepositoEntity();
		aux.setArticulo(this.getArticulo().toEntity());
		aux.setEstado(this.getEstado());
		aux.setLote(this.getLote().toEntity());
		aux.setUbicacion(this.getUbicacion().toEntity());
		aux.setReservaIdPedido(this.getReservaIdPedido());
		aux.setIdArticuloDeposito(this.idArticuloDeposito);
		return aux;
	}

	public ArticuloDepositoDTO toDTO() {
		ArticuloDepositoDTO aux = new ArticuloDepositoDTO();
		aux.setArticulo(this.getArticulo().toDTO());
		aux.setEstado(this.getEstado());
		aux.setLote(this.getLote().toDTO());
		aux.setUbicacion(this.getUbicacion().toDTO());
		aux.setReservaIdPedido(this.getReservaIdPedido());
		aux.setIdArticuloDeposito(this.idArticuloDeposito);
		return aux;
	}

	public void save() throws ArticuloException {
		ArticuloDepositoDao.getInstancia().save(this);	
	}

	public void update() throws ArticuloException{
		ArticuloDepositoDao.getInstancia().update(this);
	}

	

	public void reservarStock(int idPedido) {
		this.setEstado("RESERVADO");
		this.setReservaIdPedido(idPedido);
	}
	
	//	Getters y Setters
	
	
	
	
	public Integer getReservaIdPedido() {
		return reservaIdPedido;
	}

	public void setReservaIdPedido(Integer reservaIdPedido) {
		this.reservaIdPedido = reservaIdPedido;
	}

	public int getIdArticuloDeposito() {
		return idArticuloDeposito;
	}

	public void setIdArticuloDeposito(int idArticuloDeposito) {
		this.idArticuloDeposito = idArticuloDeposito;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}
}