package negocio;

import dao.ItemFacturaDao;
import dto.FacturaDTO;
import dto.ItemFacturaDTO;
import entities.ItemFacturaEntity;
import excepciones.FacturaException;

public class ItemFactura {

	private Factura factura;
	private int cantidad;
	private Articulo articulo;
	private float precioSubTotal;
	private int IdItemFact;


	public ItemFactura(Factura factura, int cantidad, Articulo articulo, float precioSubTotal, int idItemFact) {
		super();
		this.factura = factura;
		this.cantidad = cantidad;
		this.articulo = articulo;
		this.precioSubTotal = precioSubTotal;
		this.IdItemFact = idItemFact;
	}

	public ItemFactura(Factura factura, int cantidad, Articulo articulo, float precioSubTotal) {
		super();
		this.factura = factura;
		this.cantidad = cantidad;
		this.articulo = articulo;
		this.precioSubTotal = precioSubTotal;
	}


	public int save() throws FacturaException {
		return ItemFacturaDao.getInstancia().save(this);
	}

	public void update() throws FacturaException {
		ItemFacturaDao.getInstancia().update(this);
	}

	public ItemFacturaEntity toEntityUpdate(){
		ItemFacturaEntity aux = new ItemFacturaEntity();
		aux.setArticulo(this.articulo.toEntityUpdate());
		aux.setCantidad(this.cantidad);
		aux.setFactura(this.factura.toEntityUpdate());
		aux.setIdItemFactura(this.IdItemFact);
		aux.setPrecio(this.precioSubTotal);
		return aux;
	}

	public ItemFacturaEntity toEntitySave(){
		ItemFacturaEntity aux = new ItemFacturaEntity();
		aux.setArticulo(this.articulo.toEntityUpdate());
		aux.setCantidad(this.cantidad);
		aux.setFactura(this.factura.toEntityUpdate());
		aux.setPrecio(this.precioSubTotal);
		return aux;
	}
	
	public ItemFacturaDTO toDTO (FacturaDTO f){
		ItemFacturaDTO aux = new ItemFacturaDTO();
		aux.setArticulo(this.getArticulo().toDTO());
		aux.setCantidad(this.getCantidad());
		aux.setFactura(f);
		aux.setIdItemFact(this.getIdItemFact());
		aux.setPrecio(this.getPrecioSubTotal());
		return aux;
	}


	//Getters y Setters

	public float getPrecioSubTotal() {
		return precioSubTotal;
	}
	public void setPrecioSubTotal(float precioSubTotal) {
		this.precioSubTotal = precioSubTotal;
	}

	public Factura getFactura() {
		return factura;
	}
	
	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public int getIdItemFact() {
		return IdItemFact;
	}

	public void setIdItemFact(int idItemFact) {
		IdItemFact = idItemFact;
	}

}
