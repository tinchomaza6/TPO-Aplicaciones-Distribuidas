package dto;

import java.io.Serializable;
import java.util.*;

public class PedidoDTO implements Serializable{
	private static final long serialVersionUID = -7014215366309776467L;
	
	private int nroPedido;
	private String estado;
	private ClienteDTO cliente;
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
	private int cpDirecEnvio;
	private String aclaracion;
	private List<ItemPedidoDTO> itemsPedido;
	
	


	public PedidoDTO (int nroPedido, String estado, ClienteDTO cliente, Date fechaGeneracion, 
			Date fechaDespacho, Date fechaEntregaEsperada, Date fechaEntrega, float precioTotalBruto,
			float precioTotalFinal, String formaDePago, String calleDireccEnvio, int nroDireccEnvio,
			String localidadDireccEnvio, int cpDirecEnvio, String aclaracion, List<ItemPedidoDTO> itemsPedido){
		super();
		this.nroPedido = nroPedido;
		this.estado = estado;
		this.cliente = cliente;
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
		this.itemsPedido = itemsPedido;
	}

	public PedidoDTO(Integer nroPedido, String estado, ClienteDTO cliente, Date fechaGeneracion, Date fechaDespacho,
			Date fechaEntregaEsperada, Date fechaEntrega, float precioTotalBruto, float precioTotalFinal,
			String formaDePago, String calleDireccEnvio, int nroDireccEnvio, String localidadDireccEnvio,
			int cpDirecEnvio, String aclaracion) {
		super();
		this.nroPedido = nroPedido; 
		this.estado = estado;
		this.cliente = cliente;
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
		this.itemsPedido = new ArrayList<ItemPedidoDTO>();
	}
	
	
	//Getters y Setters


	public int getNroPedido() {
		return nroPedido;
	}

	public void setNroPedido(int nroPedido) {
		this.nroPedido = nroPedido;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public ClienteDTO getClienteDTO() {
		return cliente;
	}

	public void setClienteDTO(ClienteDTO cliente) {
		this.cliente = cliente;
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

	public List<ItemPedidoDTO> getItemsPedidoDTO() {
		return itemsPedido;
	}

	public void setItemsPedidoDTO(List<ItemPedidoDTO> itemsPedido) {
		this.itemsPedido = itemsPedido;
	}

	public ClienteDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteDTO cliente) {
		this.cliente = cliente;
	}

	public String getAclaracion() {
		return aclaracion;
	}

	public void setAclaracion(String aclaracion) {
		this.aclaracion = aclaracion;
	}

	public List<ItemPedidoDTO> getItemsPedido() {
		return itemsPedido;
	}

	public void setItemsPedido(List<ItemPedidoDTO> itemsPedido) {
		this.itemsPedido = itemsPedido;
	}
}
