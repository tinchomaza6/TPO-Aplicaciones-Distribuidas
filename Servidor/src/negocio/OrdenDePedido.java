package negocio;

import java.util.*;

import dao.OrdenDePedidoDao;
import dto.ItemOrdenDePedidoDTO;
import dto.OrdenDeCompraDTO;
import dto.OrdenDePedidoDTO;
import entities.ItemOrdenDePedidoEntity;
import entities.OrdenDeCompraEntity;
import entities.OrdenDePedidoEntity;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;

public class OrdenDePedido {

	private int idOp;
	private Pedido pedido;
	private String estado;
	private List<OrdenDeCompra> ordenesDeCompra;
	private List<ItemOrdenDePedido> articulos;


	public OrdenDePedido(int idOp, Pedido pedido, String estado, List<OrdenDeCompra> ordenesDeCompra,
			List<ItemOrdenDePedido> articulos) {
		super();
		this.idOp = idOp;
		this.pedido = pedido;
		this.estado = estado;
		this.ordenesDeCompra = ordenesDeCompra; 
		this.articulos = articulos;
	}

	public OrdenDePedido(Pedido pedido, String estado) {
		super();
		this.pedido = pedido;
		this.estado = estado;
		this.articulos = new ArrayList<ItemOrdenDePedido>();
		this.ordenesDeCompra = new ArrayList<OrdenDeCompra>();
	}

	public OrdenDePedido() {
		super();
		this.articulos = new ArrayList<ItemOrdenDePedido>();
		this.ordenesDeCompra = new ArrayList<OrdenDeCompra>();
	}

	public void actualizarEstado (String estado){
		this.estado = estado;
	}

	public OrdenDePedidoEntity toEntitySave(){
		OrdenDePedidoEntity aux = new OrdenDePedidoEntity(this.getPedido().toEntityUpdate(), this.getEstado());
		aux.setItems(this.getArticulosEntity(aux));
		aux.setOrdenesDeCompra(this.getOrdenesDeCompraEntity(aux));
		return aux;
	}
	
	public OrdenDePedidoEntity toEntityUpdate(){
		OrdenDePedidoEntity aux = new OrdenDePedidoEntity(this.getIdOp(), this.getPedido().toEntityUpdate(), this.getEstado());
		aux.setItems(this.getArticulosEntity(aux));
		aux.setOrdenesDeCompra(this.getOrdenesDeCompraEntity(aux));
		return aux;
	}

	public OrdenDePedidoDTO toDTO() {
		OrdenDePedidoDTO aux = new OrdenDePedidoDTO();
		aux.setEstado(this.getEstado());
		aux.setIdOp(this.getIdOp());
		aux.setOrdenesDeCompra(this.getOrdenesDeCompraDTO());
		aux.setPedido(this.getPedido().toDTO());
		aux.setArticulos(this.getArticulosDTO(aux));
		return aux;
	}


	public int save() throws OrdenDePedidoException {
		return OrdenDePedidoDao.getInstancia().save(this);
	}
	
	public void update() throws OrdenDePedidoException {
		OrdenDePedidoDao.getInstancia().update(this);
	}


	//Getters y Setters

	public int getIdOp() {
		return idOp;
	}

	public void setIdOp(int idOp) {
		this.idOp = idOp;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<OrdenDeCompra> getOrdenesDeCompra() {
		return ordenesDeCompra;
	}


	public List<OrdenDeCompraEntity> getOrdenesDeCompraEntity(OrdenDePedidoEntity aux) {
		List<OrdenDeCompraEntity>  oc = new ArrayList<OrdenDeCompraEntity>();
		if (this.ordenesDeCompra==null)
			return oc;
		for (OrdenDeCompra o : this.ordenesDeCompra){
				OrdenDeCompraEntity ope = new OrdenDeCompraEntity();
				ope.setEstado(o.getEstado());
				ope.setFecha(o.getFecha());
				ope.setIdOC(o.getIdOc());
				ope.setOP(aux);
				ope.setProveedor(o.getProv().toEntity());
				ope.setItems(o.getItemsEntity(ope));
				oc.add(ope);
			}
					
		return oc;
	}


	public List<OrdenDeCompraDTO> getOrdenesDeCompraDTO() {
		List<OrdenDeCompraDTO>  oc = new ArrayList<OrdenDeCompraDTO>();
		for (OrdenDeCompra o : this.getOrdenesDeCompra()){
			oc.add(o.toDTO());
		}
		return oc;
	}

	public void setOrdenesDeCompra(List<OrdenDeCompra> ordenesDeCompra) {
		this.ordenesDeCompra = ordenesDeCompra;
	}

	public List<ItemOrdenDePedido> getArticulos() {
		return articulos;
	}

	public List<ItemOrdenDePedidoEntity> getArticulosEntity(OrdenDePedidoEntity aux) {
		List<ItemOrdenDePedidoEntity> list = new ArrayList<ItemOrdenDePedidoEntity>();
		for (ItemOrdenDePedido p: this.getArticulos()){
				ItemOrdenDePedidoEntity itop = new ItemOrdenDePedidoEntity();
				itop.setArticulo(p.getArticulo().toEntityUpdate());
				itop.setCant(p.getCant());
				itop.setIdItemOp(p.getIdItemOp());
				itop.setOp(aux);
				list.add(itop);
			}
		return list;
	}


	public List<ItemOrdenDePedidoDTO> getArticulosDTO(OrdenDePedidoDTO op) {
		List<ItemOrdenDePedidoDTO> list = new ArrayList<ItemOrdenDePedidoDTO>();
		for (ItemOrdenDePedido p: this.getArticulos()){
			list.add(p.toDTO(op));
		}
		return list;
	}


	public void setArticulos(List<ItemOrdenDePedido> articulos) {
		this.articulos = articulos;
	}

	public void nuevoItemOP(Articulo a, int cantidadAComprar) throws PedidoException {
		ItemOrdenDePedido it = new ItemOrdenDePedido(this,a,cantidadAComprar);
		int id = it.save();
		it.setIdItemOp(id);
		this.articulos.add(it);
	}




}
