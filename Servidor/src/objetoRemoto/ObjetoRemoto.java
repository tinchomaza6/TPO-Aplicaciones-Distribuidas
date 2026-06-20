package objetoRemoto;

import interfazRemota.InterfazRemota;
import negocio.Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import dto.ArticuloDTO;
import dto.ArticuloDepositoDTO;
import dto.ClienteDTO;
import dto.FacturaDTO;
import dto.ItemPedidoDTO;
import dto.MovimientoAjusteDTO;
import dto.MovimientoDañoDTO;
import dto.MovimientoSimpleDTO;
import dto.MovimientoStockDTO;
import dto.OrdenDeCompraDTO;
import dto.OrdenDePedidoDTO;
import dto.PedidoDTO;
import dto.ProveedorDTO;
import dto.UbicacionDTO;
import excepciones.ArticuloException;
import excepciones.ClienteException;
import excepciones.CuentaCorrienteException;
import excepciones.FacturaException;
import excepciones.LoteException;
import excepciones.MovimientoException;
import excepciones.OrdenDeCompraException;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import excepciones.ProveedorException;
import excepciones.RemitoException;
import excepciones.UbicacionException;
import controladores.ControladorClientes;
import controladores.ControladorCompras;
import controladores.ControladorDeposito;
import controladores.ControladorDespacho;
import controladores.ControladorFacturacion;

public class ObjetoRemoto extends UnicastRemoteObject implements InterfazRemota  {

	private static final long serialVersionUID = 1L;

	public ObjetoRemoto() throws RemoteException {
		super();
	}
	
	
	/********************************************* CLIENTES 
	 * @throws ClienteException *********************************************/
	



	public ClienteDTO buscarClienteByDni(int dni) throws ClienteException {
		Cliente c = ControladorClientes.getInstancia().buscarClienteByDni(dni);
		if (c == null) {
			throw new ClienteException("El cliente no existe.");
		}
		else {
			return c.toDTO();
		}
		
	}

	public List<PedidoDTO> buscarPedidosByCliente(String dni) throws NumberFormatException, PedidoException {
		return ControladorClientes.getInstancia().buscarPedidosByCliente(dni);
	}
	
	public List<FacturaDTO> buscarFacturasByCliente(int dni) throws RemoteException, FacturaException {
		return ControladorFacturacion.getInstancia().buscarFacturasDTOByCliente(dni);
	}
	
	public void agregarSaldo(ClienteDTO c, float monto) throws RemoteException, CuentaCorrienteException, ClienteException {
		ControladorClientes.getInstancia().agregarSaldo(c, monto);
	}
	
	public void bajaCliente(int dni) throws RemoteException, ClienteException {
		ControladorClientes.getInstancia().borrarCliente(dni);
	}
	
	public void modificarCliente(int dni, String nombre, String razonSocial, float limiteCredito, String condiciones, String notasAdv, String calle, int nroDirecc, String localidad, int cp) throws RemoteException, ClienteException {
		ControladorClientes.getInstancia().modificarCliente(dni,nombre,razonSocial,limiteCredito,condiciones,notasAdv,calle,nroDirecc,localidad,cp);
	}
	
	/********************************************* PEDIDOS 
	 * @throws ArticuloException 
	 * @throws ClienteException 
	 * @throws OrdenDePedidoException 
	 * @throws OrdenDeCompraException 
	 * @throws ProveedorException 
	 * @throws RemitoException 
	 * @throws FacturaException 
	 * @throws CuentaCorrienteException *********************************************/
	
	
	
	public void altaPedido(List<ItemPedidoDTO> items, String estado, ClienteDTO cliente, String formaDePago, String calleDireccEnvio, int nroDireccEnvio, String localidadDireccEnvio, int cpDirecEnvio, String aclaracion)
			throws RemoteException, PedidoException, ClienteException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, FacturaException, RemitoException, CuentaCorrienteException {
				ControladorClientes.getInstancia().altaPedido(items, estado, cliente, formaDePago, calleDireccEnvio, nroDireccEnvio, localidadDireccEnvio, cpDirecEnvio, aclaracion);
			}
	
	public List<PedidoDTO> buscarPedidosByEstado(String estado) throws RemoteException, PedidoException{
		return ControladorClientes.getInstancia().buscarPedidosByEstado(estado);
	}
	
	public void despacharPedido(PedidoDTO p) throws UbicacionException, PedidoException, ArticuloException, ClienteException, FacturaException, RemitoException, MovimientoException {
		ControladorDespacho.getInstancia().despachar(p);
	}
	
	public PedidoDTO buscarPedidoById(int nroPedido) throws RemoteException, PedidoException {
		return ControladorClientes.getInstancia().buscarPedidoDTOById(nroPedido);
	}
	
	public OrdenDePedidoDTO buscarOPById(int idOP) throws RemoteException, PedidoException {
		return ControladorCompras.getInstancia().buscarOPById(idOP);
	}
	
	public void enviarPedido(int idPedido) throws RemoteException, PedidoException {
		ControladorDespacho.getInstancia().enviarPedido(idPedido);
	}
	
	
	/********************************************* ARTICULOS *********************************************/

	public ArticuloDTO buscarArticuloById(int idArticulo) throws RemoteException, ArticuloException {
		return ControladorDeposito.getInstancia().buscarArticuloById(idArticulo);
	}


	public void cargarTodasUbicacionesYArticulos() throws RemoteException{
		ControladorDeposito.getInstancia().cargarTodasUbicacionesYArticulos();
	}

	public List<UbicacionDTO> getUbicaciones() {
		return ControladorDeposito.getInstancia().getUbicaciones();
	}

	public List<ArticuloDTO> cargarArticulos() {
		return ControladorDeposito.getInstancia().cargarArticulos();
	}


	/**************************************************************************COMPRAS
	 * @throws UbicacionException 
	 * @throws OrdenDeCompraException 
	 * @throws LoteException 
	 * @throws ArticuloException 
	 * @throws SQLException 
	 * @throws PedidoException 
	 * @throws OrdenDePedidoException 
	 * @throws MovimientoException ***********************************************************************************************************************************************************************************************************************************************************************************************/

	public void procesarOC(int idOC) throws RemoteException, PedidoException, SQLException, ArticuloException, LoteException, OrdenDeCompraException, UbicacionException, OrdenDePedidoException, MovimientoException {
		ControladorCompras.getInstancia().procesarOC(idOC);
		
	}

	public void agregarCliente(int dni, String nombre, String rznSoc, int cuit, float limCredito, String condEspePago,
			String notaAdv, String calleDom, int nroDom, String localidadDom, int cpDom) throws ClienteException, SQLException {
		ControladorClientes.getInstancia().agregarCliente(dni, nombre, rznSoc, cuit, limCredito, condEspePago, notaAdv, calleDom, nroDom, localidadDom, cpDom);
	}

	public void autorizarPedido(boolean a, String idPedido, String aclaracion) throws PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, ClienteException, FacturaException, RemitoException, CuentaCorrienteException, RemoteException {
		ControladorClientes.getInstancia().autorizarPedido(a,idPedido, aclaracion);
		
	}

	public void pagarFactura(FacturaDTO f) throws RemoteException, FacturaException, ClienteException, CuentaCorrienteException {
		ControladorClientes.getInstancia().pagarFactura(f);
	}

	public FacturaDTO buscarFacturasById(int fac) throws RemoteException, FacturaException {
		return ControladorFacturacion.getInstancia().buscarFacturaById(fac).toDTO();
	}

	public List<ProveedorDTO> buscarProveedores(ArticuloDTO articulo) throws RemoteException {
		return ControladorCompras.getInstancia().buscarProveedores(articulo);
	}
	
	public List<OrdenDeCompraDTO> buscarOCS() throws RemoteException {
		return ControladorCompras.getInstancia().buscarOCS();
	}

	public List<OrdenDePedidoDTO> buscarOPS() throws RemoteException {
		return ControladorCompras.getInstancia().buscarOPS();
	}

	public void emitirOC(int idOP, List<String> artID, String s) throws RemoteException, ArticuloException, OrdenDeCompraException, ProveedorException, PedidoException, NumberFormatException, OrdenDePedidoException {
		ControladorCompras.getInstancia().emitirOC(idOP, artID, s);
	}


	/************************ DEPOSITO 
	 * @throws ArticuloException 
	 * @throws MovimientoException 
	 * @throws UbicacionException *************************/
	public void registrarFalla(int idArticulo, String encargado, String autorizante, String destino, String descripcion) throws RemoteException, ArticuloException, MovimientoException, UbicacionException {
		ControladorDeposito.getInstancia().registrarFalla(idArticulo, encargado, autorizante, destino, descripcion);
		
	}

	public void registrarInconsistencia(int idArticulo, String encargado, String descripcion,  int idLote, String idUbicacion) throws RemoteException, ArticuloException, MovimientoException, LoteException, UbicacionException {
		ControladorDeposito.getInstancia().registrarInconsistencia(idArticulo, encargado, descripcion, idLote, idUbicacion);
		
	}

	public void verificarVencimientos() throws RemoteException, MovimientoException, ArticuloException, UbicacionException {
		ControladorDeposito.getInstancia().verificarVencimientos();
	}
	
	public List<String> pagoDeFacturas(ClienteDTO c) throws RemoteException, ClienteException, FacturaException, CuentaCorrienteException {
		return ControladorClientes.getInstancia().pagoDeFacturas(c);
	}

	public List<MovimientoAjusteDTO> buscarMovimientosAjuste() throws RemoteException {
		return ControladorDeposito.getInstancia().buscarMovimientosAjuste();
	}
	public List<MovimientoSimpleDTO> buscarMovimientosSimple() throws RemoteException {
		return ControladorDeposito.getInstancia().buscarMovimientosSimple();
	}
	public List<MovimientoDañoDTO> buscarMovimientosDaño() throws RemoteException {
		return ControladorDeposito.getInstancia().buscarMovimientosDaño();
	}



}