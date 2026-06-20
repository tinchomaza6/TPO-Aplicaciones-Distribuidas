package delegado;

import java.rmi.Naming;
import java.rmi.RemoteException;
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
import interfazRemota.InterfazRemota;

public class BusinessDelegate {
	
	private static BusinessDelegate instancia;
	private InterfazRemota ir;
	
	public static BusinessDelegate getInstancia() {
		if (instancia == null)
			instancia = new BusinessDelegate();
		return instancia;
	}
	
	private BusinessDelegate() {
		try {
			ir = (InterfazRemota) Naming.lookup ("//localhost/circuitoPedidos");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/****************** CLIENTES 
	 * @throws ClienteException 
	 * @throws SQLException 
	 * @throws RemitoException ***********************************/
	
	public void altaCliente(int dni, String nombre, String rznSoc, int cuit, float limCredito,  String condEspePago, String notaAdv, String calleDom, int nroDom, String localidadDom, int cpDom) throws ClienteException, RemoteException, SQLException {
		ir.agregarCliente(dni, nombre, rznSoc, cuit, limCredito,  condEspePago, notaAdv, calleDom, nroDom, localidadDom, cpDom);
	}
	
	public void bajaCliente(int dni) throws RemoteException, ClienteException {
		ir.bajaCliente(dni);
	}
	
	public void modificarCliente(int dni, String nombre, String razonSocial, float limiteCredito, String condiciones, String notasAdv, String calle, int nroDirecc, String localidad, int cp) throws RemoteException, ClienteException {
		ir.modificarCliente(dni,nombre,razonSocial,limiteCredito,condiciones,notasAdv,calle,nroDirecc,localidad,cp);
	}
	
	/*************************************************** PEDIDOS 
	 * @throws ArticuloException 
	 * @throws ClienteException 
	 * @throws OrdenDePedidoException 
	 * @throws OrdenDeCompraException 
	 * @throws ProveedorException 
	 * @throws RemitoException 
	 * @throws FacturaException 
	 * @throws CuentaCorrienteException ************************************************************************************/
	
	public void altaPedido(List<ItemPedidoDTO> items, String estado, ClienteDTO cliente, String formaDePago, String calleDireccEnvio, int nroDireccEnvio, String localidadDireccEnvio, int cpDirecEnvio, String aclaracion) throws RemoteException, PedidoException, ClienteException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, FacturaException, RemitoException, CuentaCorrienteException {
		ir.altaPedido(items,  estado,  cliente,  formaDePago,  calleDireccEnvio,  nroDireccEnvio,  localidadDireccEnvio,  cpDirecEnvio, aclaracion);		
	}
	
	public List<PedidoDTO> buscarPedidosByEstado(String estado) throws RemoteException, PedidoException{
		return ir.buscarPedidosByEstado(estado);
	}

	
	public PedidoDTO buscarPedidoById(int nroPedido) throws RemoteException, PedidoException {
		return ir.buscarPedidoById(nroPedido);
	}
	
	public OrdenDePedidoDTO buscarOPById(int idOP) throws RemoteException, PedidoException {
		return ir.buscarOPById(idOP);
	}
	
	/*************************************************** ARTICULOS ************************************************************************************/

	
	public ArticuloDTO buscarArticuloById(int idArticulo) throws RemoteException, ArticuloException {
		return ir.buscarArticuloById(idArticulo);
	}

	public void cargarTodasUbicacionesYArticulos() throws RemoteException{
		ir.cargarTodasUbicacionesYArticulos();
		
	}

	public List<UbicacionDTO> getUbicaciones() throws RemoteException {
		return ir.getUbicaciones();
	}

	public void procesarOC(int idOC) throws RemoteException, PedidoException, SQLException, ArticuloException, LoteException, OrdenDeCompraException, UbicacionException, OrdenDePedidoException, MovimientoException {
		ir.procesarOC(idOC);
		
	}

	public ClienteDTO buscarClienteByDni(int dni) throws RemoteException, ClienteException {
		return ir.buscarClienteByDni(dni);
		
	}

	public void autorizarPedido(boolean a, String idPedido, String aclaracion) throws RemoteException, PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, ClienteException, FacturaException, RemitoException, CuentaCorrienteException {
		ir.autorizarPedido(a, idPedido, aclaracion);
		
	}

	public List<PedidoDTO> buscarPedidosByCliente(String usuario) throws NumberFormatException, PedidoException, RemoteException {
		return ir.buscarPedidosByCliente(usuario);
	}
	
	public List<ArticuloDTO> cargarArticulos() throws RemoteException{
		return ir.cargarArticulos();
	}

	public List<FacturaDTO> buscarFacturasByCliente(int dni) throws RemoteException, FacturaException {
		return ir.buscarFacturasByCliente(dni);
	}

	public FacturaDTO buscarFacturaById(String f) throws RemoteException, NumberFormatException, FacturaException {
		return ir.buscarFacturasById(Integer.parseInt(f));
	}

	public void pagarFactura(FacturaDTO f) throws RemoteException, FacturaException, ClienteException, CuentaCorrienteException {
		ir.pagarFactura(f);
	}

	public List<ProveedorDTO> buscarProveedores(ArticuloDTO articulo) throws RemoteException {
		return ir.buscarProveedores(articulo);
		
	}

	public void despacharPedido(PedidoDTO p) throws UbicacionException, PedidoException, ArticuloException, ClienteException, FacturaException, RemitoException, MovimientoException, RemoteException {
		ir.despacharPedido(p);
		
	}

	public void agregarSaldo(ClienteDTO c, float monto) throws RemoteException, CuentaCorrienteException, ClienteException {
		ir.agregarSaldo(c,monto);
	}

	public List<OrdenDeCompraDTO> buscarOCS() throws RemoteException {
		return ir.buscarOCS();
	}

	public List<OrdenDePedidoDTO> buscarOPS() throws RemoteException {
		return ir.buscarOPS();
	}

	public void emitirOC(int idOP, List<String> artID, String s) throws RemoteException, ArticuloException, OrdenDeCompraException, ProveedorException, PedidoException, NumberFormatException, OrdenDePedidoException{
		ir.emitirOC(idOP, artID, s);
	}

	public void registrarFalla(int idArticulo, String encargado, String autorizante, String destino, String descripcion) throws RemoteException, ArticuloException, MovimientoException, UbicacionException {
		ir.registrarFalla(idArticulo, encargado, autorizante, destino, descripcion);
	}

	public void registrarInconsistencia(int idArticulo, String encargado, String descripcion, int idLote, String idUbicacion) throws RemoteException, ArticuloException, MovimientoException, LoteException, UbicacionException {
		ir.registrarInconsistencia(idArticulo, encargado, descripcion, idLote, idUbicacion);
	}

	public void verificarVencimientos() throws RemoteException, MovimientoException, ArticuloException, UbicacionException {
		ir.verificarVencimientos();
	}

	public void enviarPedido(int idPedido) throws RemoteException, PedidoException {
		ir.enviarPedido(idPedido);
	}

	public List<String> pagoDeFacturas(ClienteDTO c) throws RemoteException, ClienteException, FacturaException, CuentaCorrienteException {
		return ir.pagoDeFacturas(c);
	}

	public List<MovimientoAjusteDTO> buscarMovimientosAjuste() throws RemoteException {
		return ir.buscarMovimientosAjuste();
	}
	public List<MovimientoSimpleDTO> buscarMovimientosSimple() throws RemoteException {
		return ir.buscarMovimientosSimple();
	}
	public List<MovimientoDañoDTO> buscarMovimientosDaño() throws RemoteException {
		return ir.buscarMovimientosDaño();
	}
}
