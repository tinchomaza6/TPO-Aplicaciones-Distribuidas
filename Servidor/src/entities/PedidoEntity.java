package entities;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

import negocio.ItemPedido;
import negocio.Pedido;

@Entity
@Table (name="Pedido")
public class PedidoEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4887603037351010209L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer nroPedido;
	private String estado;
	private Date fechaGeneracion;
	private Date fechaDespacho;
	private Date fechaEntregaEsperada;
	private Date fechaEntrega;
	private float precioTotalBruto;
	private float precioTotalFinal;
	private String formaDePago;
	private String calleDireccEnvio;
	private int nroDireccEnvio;
	private String localidadDireccEnvio;
	@Column (name="cpDireccEnvio")
	private int cpDirecEnvio;
	
	@Column (name="aclaracion")
	private String aclaracion;
	
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="idCliente")
	private ClienteEntity cliente;
	
	@OneToMany (cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "pedido")
	private List<ItemPedidoEntity> itemsPedido;
	
	public PedidoEntity() {}

	public PedidoEntity(Integer nroPedido, String estado, Date fechaGeneracion, 
			Date fechaDespacho,	Date fechaEntregaEsperada, Date fechaEntrega, 
			float precioTotalBruto, float precioTotalFinal, String formaDePago, 
			String calleDireccEnvio, int nroDireccEnvio, 
			String localidadDireccEnvio, int cpDirecEnvio, String aclaracion, ClienteEntity cliente) {
		super();
		this.nroPedido = nroPedido;
		this.estado = estado;
		this.fechaGeneracion = fechaGeneracion;
		this.fechaDespacho = fechaDespacho;
		this.fechaEntregaEsperada = fechaEntregaEsperada;
		this.fechaEntrega = fechaEntrega;
		this.precioTotalBruto = precioTotalBruto;
		this.precioTotalFinal = precioTotalFinal;
		this.formaDePago = formaDePago;
		this.calleDireccEnvio = calleDireccEnvio;
		this.nroDireccEnvio = nroDireccEnvio;
		this.localidadDireccEnvio = localidadDireccEnvio;
		this.cpDirecEnvio = cpDirecEnvio;
		this.aclaracion = aclaracion;
		this.cliente = cliente;
		this.itemsPedido = new ArrayList<ItemPedidoEntity>();
	}
	
	
	public PedidoEntity(Integer nroPedido, String estado, Date fechaGeneracion, 
			Date fechaDespacho,	Date fechaEntregaEsperada, Date fechaEntrega, 
			float precioTotalBruto, float precioTotalFinal, String formaDePago, 
			String calleDireccEnvio, int nroDireccEnvio, 
			String localidadDireccEnvio, int cpDirecEnvio, String aclaracion, ClienteEntity cliente, List<ItemPedidoEntity> items) {
		super();
		this.nroPedido = nroPedido;
		this.estado = estado;
		this.fechaGeneracion = fechaGeneracion;
		this.fechaDespacho = fechaDespacho;
		this.fechaEntregaEsperada = fechaEntregaEsperada;
		this.fechaEntrega = fechaEntrega;
		this.precioTotalBruto = precioTotalBruto;
		this.precioTotalFinal = precioTotalFinal;
		this.formaDePago = formaDePago;
		this.calleDireccEnvio = calleDireccEnvio;
		this.nroDireccEnvio = nroDireccEnvio;
		this.localidadDireccEnvio = localidadDireccEnvio;
		this.cpDirecEnvio = cpDirecEnvio;
		this.aclaracion = aclaracion;
		this.cliente = cliente;
		this.itemsPedido = items;
	}
	
	public PedidoEntity( String estado, Date fechaGeneracion, 
			Date fechaDespacho,	Date fechaEntregaEsperada, Date fechaEntrega, 
			float precioTotalBruto, float precioTotalFinal, String formaDePago, 
			String calleDireccEnvio, int nroDireccEnvio, 
			String localidadDireccEnvio, int cpDirecEnvio, String aclaracion, ClienteEntity cliente, List<ItemPedidoEntity> items) {
		super();
		this.estado = estado;
		this.fechaGeneracion = fechaGeneracion;
		this.fechaDespacho = fechaDespacho;
		this.fechaEntregaEsperada = fechaEntregaEsperada;
		this.fechaEntrega = fechaEntrega;
		this.precioTotalBruto = precioTotalBruto;
		this.precioTotalFinal = precioTotalFinal;
		this.formaDePago = formaDePago;
		this.calleDireccEnvio = calleDireccEnvio;
		this.nroDireccEnvio = nroDireccEnvio;
		this.localidadDireccEnvio = localidadDireccEnvio;
		this.cpDirecEnvio = cpDirecEnvio;
		this.aclaracion = aclaracion;
		this.cliente = cliente;
		this.itemsPedido = items;
	}
	
	public Pedido toNegocio() {
		Pedido p = new Pedido(this.nroPedido, this.estado, this.fechaGeneracion, this.fechaDespacho,
				this.fechaEntregaEsperada, this.fechaEntrega, this.precioTotalBruto, this.precioTotalFinal,
				this.formaDePago, this.calleDireccEnvio, this.nroDireccEnvio, this.localidadDireccEnvio,
				this.cpDirecEnvio, this.aclaracion, this.cliente.toNegocio());
		p.setItemsPedido(this.getItemsPedidoEntity(p));
		return p;
	
	}
	
	private List<ItemPedido> getItemsPedidoEntity(Pedido p) {
		List<ItemPedido> aux = new ArrayList<ItemPedido>();
		for (ItemPedidoEntity item : itemsPedido) {
			ItemPedido it = new ItemPedido();
			it.setPedido(p);
			it.setArticulo(item.getArticulo().toNegocio());
			it.setCant(item.getCantidad());
			it.setIdItemPedido(item.getIdItemPedido());;
			aux.add(it);
		}
		return aux;
	}

	//GETTERS Y SETTERS
	
	public Integer getNroPedido() {
		return nroPedido;
	}

	public void setNroPedido(Integer nroPedido) {
		this.nroPedido = nroPedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}

	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}

	public Date getFechaDespacho() {
		return fechaDespacho;
	}

	public void setFechaDespacho(Date fechaDespacho) {
		this.fechaDespacho = fechaDespacho;
	}

	public Date getFechaEntregaEsperada() {
		return fechaEntregaEsperada;
	}

	public void setFechaEntregaEsperada(Date fechaEntregaEsperada) {
		this.fechaEntregaEsperada = fechaEntregaEsperada;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public float getPrecioTotalBruto() {
		return precioTotalBruto;
	}

	public void setPrecioTotalBruto(float precioTotalBruto) {
		this.precioTotalBruto = precioTotalBruto;
	}

	public float getPrecioTotalFinal() {
		return precioTotalFinal;
	}

	public void setPrecioTotalFinal(float precioTotalFinal) {
		this.precioTotalFinal = precioTotalFinal;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public String getCalleDireccEnvio() {
		return calleDireccEnvio;
	}

	public void setCalleDireccEnvio(String calleDireccEnvio) {
		this.calleDireccEnvio = calleDireccEnvio;
	}

	public int getNroDireccEnvio() {
		return nroDireccEnvio;
	}

	public void setNroDireccEnvio(int nroDireccEnvio) {
		this.nroDireccEnvio = nroDireccEnvio;
	}

	public String getLocalidadDireccEnvio() {
		return localidadDireccEnvio;
	}

	public void setLocalidadDireccEnvio(String localidadDireccEnvio) {
		this.localidadDireccEnvio = localidadDireccEnvio;
	}

	public int getCpDirecEnvio() {
		return cpDirecEnvio;
	}

	public void setCpDirecEnvio(int cpDirecEnvio) {
		this.cpDirecEnvio = cpDirecEnvio;
	}

	public String getAclaracion() {
		return aclaracion;
	}

	public void setAclaracion(String aclaracion) {
		this.aclaracion = aclaracion;
	}
	
	public ClienteEntity getCliente() {
		return cliente;
	}

	public void setCliente(ClienteEntity cliente) {
		this.cliente = cliente;
	}

	public List<ItemPedidoEntity> getItemsPedido() {
		return itemsPedido;
	}

	public void setItemsPedido(List<ItemPedidoEntity> itemsPedido) {
		this.itemsPedido = itemsPedido;
	}
	
}