package negocio;

import dao.ItemOrdenDePedidoDao;
import dto.ItemOrdenDePedidoDTO;
import dto.OrdenDePedidoDTO;
import entities.ItemOrdenDePedidoEntity;
import excepciones.PedidoException;

public class ItemOrdenDePedido {

	private Integer idItemOp; 
	private OrdenDePedido Op;
	private Articulo articulo;
	private int cant;

	public int save() throws PedidoException{
		return ItemOrdenDePedidoDao.getInstancia().save(this);
 
	}
	public void delete(){

	}
	public ItemOrdenDePedido(Integer idItemOp, OrdenDePedido Op, Articulo articulo, int cant) {
		super();
		this.idItemOp = idItemOp;
		this.Op = Op;
		this.articulo = articulo;
		this.cant = cant;
	}

	public ItemOrdenDePedido(OrdenDePedido Op, Articulo articulo, int cant) {
		super();
		this.Op = Op;
		this.articulo = articulo;
		this.cant = cant;
	}


	public ItemOrdenDePedidoEntity toEntitySave() {
		ItemOrdenDePedidoEntity aux = new ItemOrdenDePedidoEntity();
		aux.setArticulo(this.getArticulo().toEntityUpdate());
		aux.setCant(this.getCant());
		aux.setOp(this.getOp().toEntityUpdate());
		return aux;

	}
	
	public ItemOrdenDePedidoEntity toEntityUpdate() {
		ItemOrdenDePedidoEntity aux = new ItemOrdenDePedidoEntity();
		aux.setIdItemOp(this.getIdItemOp());
		aux.setArticulo(this.getArticulo().toEntityUpdate());
		aux.setCant(this.getCant());
		aux.setOp(this.getOp().toEntityUpdate());
		return aux;

	}

	public ItemOrdenDePedidoDTO toDTO(OrdenDePedidoDTO op) {
		ItemOrdenDePedidoDTO aux = new ItemOrdenDePedidoDTO();
		aux.setArticulo(this.getArticulo().toDTO());
		aux.setCant(this.getCant());
		aux.setIdItemOp(this.getIdItemOp());
		aux.setOp(op);
		return aux;
	}

	//GETTERS Y SETTERS

	public Integer getIdItemOp() {
		return idItemOp;
	}
	public void setIdItemOp(Integer idItemOp) {
		this.idItemOp = idItemOp;
	}
	public OrdenDePedido getOp() {
		return Op;
	}
	public void setOp(OrdenDePedido op) {
		Op = op;
	}
	public Articulo getArticulo() {
		return articulo;
	}
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}


}
