package negocio;


import java.util.*;

import javax.swing.JOptionPane;

import dao.FacturaDao;
import dto.FacturaDTO;
import dto.ItemFacturaDTO;
import entities.FacturaEntity;
import entities.ItemFacturaEntity;
import entities.ItemPedidoEntity;
import entities.PedidoEntity;
import excepciones.FacturaException;

public class Factura {

	private int nroFactura;
	private Date fecha;
	private Pedido pedido;
	private Cliente cliente;
	private float totalFact;
	private String estado;
	private List<ItemFactura> itemsFact;

	public Factura(Date fecha, Pedido pedido, Cliente cliente, float totalFact) {
		super();
		this.fecha = fecha;
		this.pedido = pedido;
		this.cliente = cliente;
		this.totalFact = totalFact;
		this.estado = "IMPAGA";
		this.itemsFact = new ArrayList<ItemFactura>();
	}

	public Factura(int nroFactura, Date fecha, Pedido pedido, Cliente cliente, float totalFact, String estado,
			List<ItemFactura> itemsFact) {
		super();
		this.nroFactura = nroFactura;
		this.fecha = fecha;
		this.pedido = pedido;
		this.cliente = cliente;
		this.totalFact = totalFact;
		this.estado = estado;
		this.itemsFact = itemsFact;
	}

	public Factura() {
		super();
	}

	public void aplicarDescuento(float descuento){
		//el parametro descuento es entre 1 y 100
		float desc = 1 - (descuento/100);
		this.totalFact = (this.pedido.getPrecioTotalFinal())*(desc);
	}
	
	public void nuevoItemFact(Articulo articulo, int cant, float precio) throws FacturaException{	
		ItemFactura item = new ItemFactura(this,cant, articulo ,precio);
		int id = item.save();
		item.setIdItemFact(id);
		itemsFact.add(item);
	}

	public int save() throws FacturaException {
		return FacturaDao.getInstancia().save(this);
	}

	public void update() throws FacturaException{
		FacturaDao.getInstancia().update(this);
	}


	public FacturaEntity toEntityUpdate(){
		FacturaEntity aux = new FacturaEntity();
		aux.setCliente(this.cliente.toEntity());
		aux.setEstado(this.estado);
		aux.setFecha(this.fecha);
		aux.setItems(this.getItemsFactEntity());
		aux.setNroFactura(this.nroFactura);
		aux.setPedido(this.pedido.toEntityUpdate());
		aux.setTotalFact(this.totalFact);
		return aux;
	}
	
	
	public FacturaEntity toEntitySave(){
		FacturaEntity aux = new FacturaEntity();
		aux.setCliente(this.cliente.toEntity());
		aux.setEstado(this.estado);
		aux.setFecha(this.fecha);
		aux.setItems(this.getItemsFactEntity());
		aux.setPedido(this.pedido.toEntityUpdate());
		aux.setTotalFact(this.totalFact);
		return aux;
	}
	


	public FacturaDTO toDTO (){
		FacturaDTO aux = new FacturaDTO();
		aux.setCliente(this.cliente.toDTO());
		aux.setEstado(this.estado);
		aux.setFecha(this.fecha);
		aux.setNroFactura(this.nroFactura);
		aux.setPedido(this.pedido.toDTO());
		aux.setTotalFact(this.totalFact);
		
		aux.setItemsFact(this.getItemsFactDTO(aux));
		return aux;
	}



	//Getters y Setters

	public int getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(int nroFactura) {
		this.nroFactura = nroFactura;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Pedido getPedido() {
		return pedido;
	}
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public float getTotalFact() {
		return totalFact;
	}
	public void setTotalFact(float totalFact) {
		this.totalFact = totalFact;
	}
	public List<ItemFactura> getItemsFact() {
		return itemsFact;
	}

	public List<ItemFacturaEntity> getItemsFactEntity(){
		List<ItemFacturaEntity> items = new ArrayList<ItemFacturaEntity>();
		ItemFacturaEntity aux = new ItemFacturaEntity();
		for(ItemFactura item: itemsFact){
			aux.setArticulo(item.getArticulo().toEntityUpdate());
			aux.setCantidad(item.getCantidad());
			aux.setPrecio(item.getPrecioSubTotal());
			aux.setIdItemFactura(item.getIdItemFact());
			FacturaEntity factEntity = new FacturaEntity(this.nroFactura, this.fecha, this.totalFact, this.cliente.toEntity(), this.pedido.toEntityUpdate());
			aux.setFactura(factEntity);
			items.add(aux);
		}
		return items;
	}

	public List<ItemFacturaDTO> getItemsFactDTO(FacturaDTO f){
		//HAY  UN LOOP INFINITO ACA, VOY  A COMER. FIJATE SI LO PODES ARREGLAR
		List<ItemFacturaDTO> aux = new ArrayList<ItemFacturaDTO>();
		for(ItemFactura item: itemsFact) {
			aux.add(item.toDTO(f));
		}
		return aux;
	}


	public void setItemsFact(List<ItemFactura> itemsFact) {
		this.itemsFact = itemsFact;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
}