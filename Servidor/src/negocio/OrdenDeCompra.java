package negocio;

import java.util.*;

import javax.swing.JOptionPane;

import dao.OrdenDeCompraDao;
import dto.ItemOrdenDeCompraDTO;
import dto.OrdenDeCompraDTO;
import entities.ItemOrdenDeCompraEntity;
import entities.OrdenDeCompraEntity;
import excepciones.ArticuloException;
import excepciones.OrdenDeCompraException;

public class OrdenDeCompra {

	private int idOc;
	private Date fecha;
	private Proveedor prov;
	private OrdenDePedido ordenPedido;
	private String estado;
	private List<ItemOrdenDeCompra> items;
	

	public OrdenDeCompra(int idOc, Date fecha, Proveedor prov, OrdenDePedido ordenPedido, String estado,
			List<ItemOrdenDeCompra> items) {
		super();
		this.idOc = idOc;
		this.fecha = fecha;
		this.prov = prov;
		this.ordenPedido = ordenPedido;
		this.estado = estado;
		this.items = items;
	}
	
	public OrdenDeCompra(int idOc, Date fecha, Proveedor prov, OrdenDePedido ordenPedido, String estado) {
		super();
		this.idOc = idOc;
		this.fecha = fecha;
		this.prov = prov;
		this.ordenPedido = ordenPedido;
		this.estado = estado;
		this.items= new ArrayList<ItemOrdenDeCompra>();
	}
	

	public OrdenDeCompra(Date fecha, Proveedor prov, OrdenDePedido ordenPedido, String estado) {
		super();
		this.fecha = fecha;
		this.prov = prov;
		this.ordenPedido = ordenPedido;
		this.estado = estado;
		this.items= new ArrayList<ItemOrdenDeCompra>();
	}


	public OrdenDeCompra() {
		super();
		this.items = new ArrayList <ItemOrdenDeCompra>();
	}


	public float total(){
		float total = 0;
		for (ItemOrdenDeCompra item : items){
			total += item.subtotal();
		}
		return total;
	}

	public void actualizarEstado(String estado){
		this.estado=estado;
	}

	public void nuevoItemOC(Articulo articulo, int cant, float precio) throws OrdenDeCompraException{
		ItemOrdenDeCompra itemOC = new ItemOrdenDeCompra(this, articulo, cant, precio);
		int id = itemOC.save();
		itemOC.setIdItemOC(id);
		this.items.add(itemOC);
	}
	
	public int save() throws ArticuloException, OrdenDeCompraException {
		return OrdenDeCompraDao.getInstancia().save(this);	
	}

	public void update() throws ArticuloException, OrdenDeCompraException{
		OrdenDeCompraDao.getInstancia().update(this);
	}

	public OrdenDeCompraEntity toEntitySave(){
		OrdenDeCompraEntity oc = new OrdenDeCompraEntity();
		oc.setEstado(this.getEstado());
		oc.setFecha(this.getFecha());
		oc.setItems(this.getItemsEntity(oc));
		oc.setOP(this.getOrdenPedido().toEntityUpdate());
		oc.setProveedor(this.getProv().toEntity());
		return oc;
	}

	
	public OrdenDeCompraEntity toEntityUpdate(){
		OrdenDeCompraEntity oc = new OrdenDeCompraEntity();
		oc.setEstado(this.getEstado());
		oc.setFecha(this.getFecha());
		oc.setItems(this.getItemsEntity(oc));
		oc.setOP(this.getOrdenPedido().toEntityUpdate());
		oc.setProveedor(this.getProv().toEntity());
		oc.setIdOC(this.getIdOc());
		return oc;
	}

	
	public OrdenDeCompraDTO toDTO(){
		OrdenDeCompraDTO oc = new OrdenDeCompraDTO();
		oc.setEstado(this.getEstado());
		oc.setFecha(this.getFecha());
		oc.setIdOc(this.getIdOc());
		oc.setProv(this.getProv().toDTO());
		oc.setOrdenPedidoDTO(this.getOrdenPedido().toDTO());
		oc.setItems(this.getItemsDTO(oc));
		return oc;
	}

	//Getters y Setters

	public int getIdOc() {
		return idOc;
	}

	public void setIdOc(int idOc) {
		this.idOc = idOc;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Proveedor getProv() {
		return prov;
	}

	public void setProv(Proveedor prov) {
		this.prov = prov;
	}

	public OrdenDePedido getOrdenPedido() {
		return ordenPedido;
	}

	public void setOrdenPedido(OrdenDePedido ordenPedido) {
		this.ordenPedido = ordenPedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<ItemOrdenDeCompra> getItems() {
		return items;
	}

	public List<ItemOrdenDeCompraEntity> getItemsEntity(OrdenDeCompraEntity oc) {
		List<ItemOrdenDeCompraEntity> list = new ArrayList<ItemOrdenDeCompraEntity>();
		if (this.getItems().isEmpty() || this.getItems() == null) {
			return list;
		}			
		for (ItemOrdenDeCompra o: this.getItems()){
			ItemOrdenDeCompraEntity aux = new ItemOrdenDeCompraEntity();
			aux.setArticulo(o.getArticulo().toEntityUpdate());
			aux.setCantidad(o.getCantidad());
			aux.setOc(oc);
			aux.setItemOC(o.getIdItemOC());
			aux.setPrecio(o.getPrecio());			
			list.add(aux);
		}
		return list;
	}

	public List<ItemOrdenDeCompraDTO> getItemsDTO(OrdenDeCompraDTO oc) {
		List<ItemOrdenDeCompraDTO> list = new ArrayList<ItemOrdenDeCompraDTO>();
		for (ItemOrdenDeCompra o: this.getItems()){
			list.add(o.toDTO(oc));
		}
		return list;
	}

	public void setItems(List<ItemOrdenDeCompra> items) {
		this.items = items;
	}

}
