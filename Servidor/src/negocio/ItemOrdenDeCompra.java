package negocio;

import dao.ItemOrdenDeCompraDao;
import dto.ItemOrdenDeCompraDTO;
import dto.OrdenDeCompraDTO;
import entities.ItemOrdenDeCompraEntity;
import excepciones.OrdenDeCompraException;

public class ItemOrdenDeCompra {

	private OrdenDeCompra Oc;
	private int idItemOC;
	private Articulo articulo;
	private int cantidad;
	private float precio;

	public ItemOrdenDeCompra(OrdenDeCompra oc, Articulo articulo, int cantidad, float precio) {
		super();
		this.Oc = oc;
		this.articulo = articulo;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public ItemOrdenDeCompra() {
		super();
	}

	public float subtotal() {
		return cantidad * precio;
	}

	public ItemOrdenDeCompraEntity toEntitySave() {
		ItemOrdenDeCompraEntity aux = new ItemOrdenDeCompraEntity();
		aux.setArticulo(this.getArticulo().toEntityUpdate());
		aux.setCantidad(this.getCantidad());
		aux.setOc(this.getOc().toEntityUpdate());
		aux.setPrecio(this.getPrecio());
		return aux;
	}
	
	public ItemOrdenDeCompraEntity toEntityUpdate() {
		ItemOrdenDeCompraEntity aux = new ItemOrdenDeCompraEntity();
		aux.setItemOC(this.getIdItemOC());
		aux.setArticulo(this.getArticulo().toEntityUpdate());
		aux.setCantidad(this.getCantidad());
		aux.setOc(this.getOc().toEntityUpdate());
		aux.setPrecio(this.getPrecio());
		return aux;
	}

	public ItemOrdenDeCompraDTO toDTO(OrdenDeCompraDTO oc) {
		ItemOrdenDeCompraDTO aux = new ItemOrdenDeCompraDTO();
		aux.setArticulo(this.getArticulo().toDTO());
		aux.setCantidad(this.getCantidad());
		aux.setIdItemOC(this.getIdItemOC());
		aux.setOc(oc);
		aux.setPrecio(this.getPrecio());
		return aux;
	}

	//Getters y Setters

	public void setIdItemOC(int idItemOC) {
		this.idItemOC = idItemOC;
	}


	public int getIdItemOC() {
		return idItemOC;
	}

	public OrdenDeCompra getOc() {
		return Oc;
	}

	public void setOc(OrdenDeCompra oc) {
		Oc = oc;
	}


	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int save() throws OrdenDeCompraException {
		return ItemOrdenDeCompraDao.getInstancia().save(this);
		
	}
}