package negocio;

import dao.ArticuloDepositoDao;
import dto.ArticuloDepositoDTO;
import entities.ArticuloDepositoEntity;
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
		this.reservaIdPedido=null;
	}
	


	public ArticuloDeposito() {
		super();
	}

	public ArticuloDepositoEntity toEntitySave(){
		ArticuloDepositoEntity aux = new ArticuloDepositoEntity();
		aux.setArticulo(this.articulo.toEntityUpdate());
		aux.setEstado(this.estado);
		aux.setLote(this.lote.toEntityUpdate());
		aux.setUbicacion(this.ubicacion.toEntity());
		aux.setReservaIdPedido(this.reservaIdPedido);
		return aux;
	}

	public ArticuloDepositoEntity toEntityUpdate(){
		ArticuloDepositoEntity aux = new ArticuloDepositoEntity();
		aux.setArticulo(this.articulo.toEntityUpdate());
		aux.setEstado(this.estado);
		aux.setLote(this.lote.toEntityUpdate());
		aux.setUbicacion(this.ubicacion.toEntity());
		aux.setReservaIdPedido(this.reservaIdPedido);
		aux.setIdArticuloDeposito(this.idArticuloDeposito);
		return aux;
	}
	
	

	public ArticuloDepositoEntity toEntityDelete() { 
		ArticuloDepositoEntity aux = new ArticuloDepositoEntity();
		//aux.setArticulo(this.articulo.toEntityUpdate());
		//aux.setEstado(this.estado);
		//aux.setLote(this.lote.toEntityUpdate());
		//aux.setUbicacion(this.ubicacion.toEntityUpdate());
		//aux.setReservaIdPedido(this.reservaIdPedido);
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

	public int save() throws ArticuloException {
		return ArticuloDepositoDao.getInstancia().save(this);	
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

	public void delete() throws ArticuloException {
		ArticuloDepositoDao.getInstancia().delete(this);
	}

}