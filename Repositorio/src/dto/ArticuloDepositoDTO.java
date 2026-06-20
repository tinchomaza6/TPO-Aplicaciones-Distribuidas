package dto;

import java.io.Serializable;

public class ArticuloDepositoDTO implements Serializable{
	private static final long serialVersionUID = 2972755113985607401L;
	
	private int idArticuloDeposito;
	private ArticuloDTO articulo;
	private String estado;
	private LoteDTO lote;
	private Integer reservaIdPedido;
	private UbicacionDTO ubicacion;

	public ArticuloDepositoDTO(int idArticuloDeposito, ArticuloDTO articulo, int cantidad, String estado, LoteDTO lote,
			UbicacionDTO ubicacion, int reservaIdPedido) {
		super();
		this.idArticuloDeposito = idArticuloDeposito;
		this.articulo = articulo;
		this.estado = estado;
		this.lote = lote;
		this.ubicacion = ubicacion;
		this.reservaIdPedido=reservaIdPedido;
	}

	public ArticuloDepositoDTO(ArticuloDTO articulo, int cantidad, String estado, LoteDTO lote,
			UbicacionDTO ubicacion) {
		super();
		this.articulo = articulo;
		this.estado = estado;
		this.lote = lote;
		this.ubicacion = ubicacion;
		this.reservaIdPedido=0;
	}

	public ArticuloDepositoDTO(){
		
	}

	
	//	Getters y Setters
	
	
	
	public int getIdArticuloDeposito() {
		return idArticuloDeposito;
	}

	public int getReservaIdPedido() {
		return reservaIdPedido;
	}

	public void setReservaIdPedido(Integer reservaIdPedido) {
		this.reservaIdPedido = reservaIdPedido;
	}

	public void setIdArticuloDeposito(int idArticuloDeposito) {
		this.idArticuloDeposito = idArticuloDeposito;
	}


	public ArticuloDTO getArticulo() {
		return articulo;
	}

	public void setArticulo(ArticuloDTO articulo) {
		this.articulo = articulo;
	}

	public LoteDTO getLote() {
		return lote;
	}

	public void setLote(LoteDTO lote) {
		this.lote = lote;
	}

	public UbicacionDTO getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(UbicacionDTO ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}








}





