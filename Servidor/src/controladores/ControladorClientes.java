package controladores;
import negocio.Pedido;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

import dao.ArticuloDao;
import dao.ClienteDao;
import dao.CuentaCorrienteDao;
import dao.PedidoDao;
import dto.ClienteDTO;
import dto.FacturaDTO;
import dto.ItemPedidoDTO;
import dto.PedidoDTO;
import excepciones.ArticuloException;
import excepciones.ClienteException;
import excepciones.CuentaCorrienteException;
import excepciones.FacturaException;
import excepciones.OrdenDeCompraException;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import excepciones.ProveedorException;
import excepciones.RemitoException;
import negocio.Articulo;
import negocio.Cliente;
import negocio.CuentaCorriente;
import negocio.Factura;

public class ControladorClientes {

	private static ControladorClientes instancia;

	public static ControladorClientes getInstancia() {
		if(instancia==null) {
			instancia = new ControladorClientes();
		}
		return instancia;
	}


	public void agregarCliente(int dni, String nombre, String rznSoc, int cuit, float limCredito, String condEspePago, String notaAdv, String calleDom, int nroDom, String localidadDom, int cpDom) throws ClienteException, SQLException{

			Cliente c = this.buscarClienteByDni(dni);
			if (c == null) {
				Cliente cl = new Cliente(dni, nombre, rznSoc, cuit, limCredito, condEspePago, notaAdv, calleDom, nroDom, localidadDom, cpDom);
				CuentaCorriente cc = new CuentaCorriente("no tiene especie", 0);
				int id = cc.save();
				cc.setidCuentaCorriente(id);
				cl.setCuentaCorriente(cc);
				cl.save();
			}
			else {
				throw new ClienteException("El cliente ya existe.");
			}

	}

	public void borrarCliente(int dni) throws ClienteException{
		Cliente c = this.buscarClienteByDni(dni);
		c.delete();
	}
	
	public void modificarCliente(int dni, String nombre, String razonSocial, float limiteCredito, String condiciones, String notasAdv, String calle, int nroDirecc, String localidad, int cp) throws ClienteException {
		Cliente c = this.buscarClienteByDni(dni);
		c.setNombre(nombre);
		c.setRazonSocial(razonSocial);
		c.setLimiteCredito(limiteCredito);
		c.setCondEspPago(condiciones);
		c.setNotasAdv(notasAdv);
		c.setCalleDom(calle);
		c.setNroDom(nroDirecc);
		c.setLocalidadDom(localidad);
		c.setCpDom(cp);
		c.update();
	}

	public Cliente buscarClienteByDni(int dni) {
		return ClienteDao.getInstancia().buscarClienteByDni(dni);
		
	}

	public List<PedidoDTO> buscarPedidosByEstado(String estado) throws PedidoException{
		List<PedidoDTO> devolver = new ArrayList<PedidoDTO>();
		List<Pedido> pedidos = PedidoDao.getInstancia().buscarPedidosByEstado(estado);
		for(Pedido pedido : pedidos){
			devolver.add(pedido.toDTO());
		}
		return devolver;
	}
	
	public Pedido buscarPedidoById(int nroPedido) throws PedidoException {
		return PedidoDao.getInstancia().buscarPedidoById(nroPedido);
	}
	
	public PedidoDTO buscarPedidoDTOById(int nroPedido) throws PedidoException {
		Pedido p = PedidoDao.getInstancia().buscarPedidoById(nroPedido);
		return p.toDTO();
	}

	public List<PedidoDTO> buscarPedidosByCliente(String dni) throws NumberFormatException, PedidoException {
		List<PedidoDTO> pDTO = new ArrayList<PedidoDTO>();
		for (Pedido p : PedidoDao.getInstancia().buscarPedidosByCliente(Integer.parseInt(dni))) {
			pDTO.add(p.toDTO());
		}
		return pDTO;
	}
	

	public void altaPedido(List<ItemPedidoDTO> itemsPedido, String estado, ClienteDTO clienteDTO, String formaDePago, String calleDireccEnvio, int nroDireccEnvio, String localidadDireccEnvio, int cpDirecEnvio, String aclaracion) throws ClienteException, ArticuloException, PedidoException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, FacturaException, RemitoException, CuentaCorrienteException{
		if (itemsPedido.size()<=0)
			throw new PedidoException("El pedido no tiene items asociados");
		int id;
		Date fechaGeneracion = Calendar.getInstance().getTime();
		Date fechaDespacho = null;
		Date fechaEntregaEsperada = null;
		Date fechaEntrega = null;
		float precioTotalBruto = 0;
		float precioTotalFinal = 0;
		Cliente cliente = null;
		cliente = buscarClienteByDni(clienteDTO.getDni());
		Pedido pedido = new Pedido(estado ,cliente, fechaGeneracion, fechaDespacho,fechaEntregaEsperada, fechaEntrega, precioTotalBruto, precioTotalFinal, formaDePago, calleDireccEnvio, nroDireccEnvio, localidadDireccEnvio, cpDirecEnvio, aclaracion);
		id = pedido.save();
		pedido.setNroPedido(id);
		for(ItemPedidoDTO item : itemsPedido){
			if (item.getCant()<=0) {
				throw new ArticuloException("La cantidad de " + item.getArticulo().getNombre() + " es 0");
			}
			Articulo articulo = null;
			articulo = ArticuloDao.getInstancia().buscarArticuloById(item.getArticulo().getIdArticulo()); 
			if (articulo != null){
				pedido.nuevoItemPedido(item.getCant(),articulo);		
			}
		}
		pedido.setPrecioTotalBruto(pedido.getPrecioTotalBruto());
		pedido.setPrecioTotalFinal(pedido.getPrecioTotalFinal());
		pedido.update();
	}

	public void autorizarPedido(boolean autorizado, String idPedido, String aclaracion) throws NumberFormatException, PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException {
			Pedido p = PedidoDao.getInstancia().buscarPedidoById(Integer.parseInt(idPedido));
			if (autorizado == true)
			{
				p.setEstado("APROBADO");
				p.setAclaracion(aclaracion);
				p.update();
				ControladorDeposito.getInstancia().verificarExistenciaStock(p);
			}
			else
			{
				p.setEstado("RECHAZADO");
				p.setAclaracion(aclaracion);
				p.update();
			}
		}
	
	
	public void pagarFactura(FacturaDTO facturaDTO) throws FacturaException, ClienteException, CuentaCorrienteException {
		Factura f = ControladorFacturacion.getInstancia().buscarFacturaById(facturaDTO.getNroFactura());
		Cliente c = this.buscarClienteByDni(facturaDTO.getCliente().getDni());
		c.pagarFactura(f);
	}
	
	public List<String> pagoDeFacturas(ClienteDTO c) throws ClienteException, FacturaException, CuentaCorrienteException {
		Cliente cli = this.buscarClienteByDni(c.getDni());
		return cli.pagoDeFacturas();
	}

	public void agregarSaldo(ClienteDTO c, float monto) throws CuentaCorrienteException, ClienteException {
		Cliente cliente = this.buscarClienteByDni(c.getDni());
		Date fecha = Calendar.getInstance().getTime();
		if (monto <= 0) {
			throw new CuentaCorrienteException("Saldo cero o negativo.");
		}
		cliente.getCuentaCorriente().nuevoMovimientoCarga(fecha, monto, "Carga de saldo");
	}
}