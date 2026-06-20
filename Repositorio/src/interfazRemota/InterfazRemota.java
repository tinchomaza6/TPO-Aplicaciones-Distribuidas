package interfazRemota;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
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

public interface InterfazRemota extends Remote {
	
	/*************************************************** PEDIDOS 
	 * @throws ArticuloException 
	 * @throws ClienteException 
	 * @throws OrdenDePedidoException 
	 * @throws OrdenDeCompraException 
	 * @throws ProveedorException 
	 * @throws RemitoException 
	 * @throws FacturaException 
	 * @throws CuentaCorrienteException ************************************************************************************/
	
	public abstract void altaPedido(List<ItemPedidoDTO> items, String estado, ClienteDTO cliente, String formaDePago, String calleDireccEnvio, int nroDireccEnvio, String localidadDireccEnvio, int cpDirecEnvio, String aclaracion) throws RemoteException, PedidoException, ClienteException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, FacturaException, RemitoException, CuentaCorrienteException;
	
	public abstract List<PedidoDTO> buscarPedidosByEstado(String estado) throws RemoteException, PedidoException;
	
	public abstract void despacharPedido(PedidoDTO p) throws RemoteException, UbicacionException, PedidoException, ArticuloException, ClienteException, FacturaException, RemitoException, MovimientoException;
	
	public abstract OrdenDePedidoDTO buscarOPById(int idOP) throws RemoteException, PedidoException;
	
	/*************************************************** ARTICULOS ************************************************************************************/

	public abstract ArticuloDTO buscarArticuloById(int idArticulo) throws RemoteException, ArticuloException;

	public abstract void cargarTodasUbicacionesYArticulos() throws RemoteException;

	public abstract List<UbicacionDTO> getUbicaciones() throws RemoteException;
	
	public abstract List<ArticuloDTO> cargarArticulos() throws RemoteException;
	
	/***********************************************COMPRAS
	 * @throws UbicacionException 
	 * @throws OrdenDeCompraException 
	 * @throws LoteException 
	 * @throws ArticuloException 
	 * @throws SQLException 
	 * @throws PedidoException 
	 * @throws OrdenDePedidoException 
	 * @throws MovimientoException ***********************************************************************************************/
	
	public abstract void procesarOC(int idOC) throws RemoteException, PedidoException, SQLException, ArticuloException, LoteException, OrdenDeCompraException, UbicacionException, OrdenDePedidoException, MovimientoException;

	public abstract ClienteDTO buscarClienteByDni(int dni) throws RemoteException, ClienteException;

	public abstract void agregarCliente(int dni, String nombre, String rznSoc, int cuit, float limCredito,
			String condEspePago, String notaAdv, String calleDom, int nroDom, String localidadDom, int cpDom) throws ClienteException, RemoteException, SQLException;

	public abstract void autorizarPedido(boolean a, String idPedido, String aclaracion) throws PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException, ClienteException, FacturaException, RemitoException, CuentaCorrienteException, RemoteException;

	public abstract List<OrdenDeCompraDTO> buscarOCS() throws RemoteException;
	
	/***********CLIENTE 
	 * @throws PedidoException 
	 * @throws NumberFormatException *********************/
	
	public abstract List<PedidoDTO> buscarPedidosByCliente(String usuario) throws NumberFormatException, PedidoException, RemoteException;

	public abstract PedidoDTO buscarPedidoById(int nroPedido) throws RemoteException, PedidoException;

	public abstract List<FacturaDTO> buscarFacturasByCliente(int dni) throws RemoteException, FacturaException;

	public abstract void pagarFactura(FacturaDTO f) throws RemoteException, FacturaException, ClienteException, CuentaCorrienteException;

	public abstract FacturaDTO buscarFacturasById(int parseInt) throws RemoteException, FacturaException;

	public abstract List<ProveedorDTO> buscarProveedores(ArticuloDTO articulo) throws RemoteException;
	
	public abstract void agregarSaldo(ClienteDTO c, float monto) throws RemoteException, CuentaCorrienteException, ClienteException;

	public abstract List<OrdenDePedidoDTO> buscarOPS() throws RemoteException;

	public abstract void emitirOC(int idOP, List<String> artID, String s) throws RemoteException, ArticuloException, OrdenDeCompraException, ProveedorException, PedidoException, NumberFormatException, OrdenDePedidoException;

	public abstract void registrarFalla(int idArticulo, String encargado, String autorizante, String destino, String descripcion) throws RemoteException, ArticuloException, MovimientoException, UbicacionException;

	public abstract void registrarInconsistencia(int idArticulo, String encargado, String descripcion,  int idLote, String idUbicacion) throws RemoteException, ArticuloException, MovimientoException, LoteException, UbicacionException;

	public abstract void verificarVencimientos() throws RemoteException, MovimientoException, ArticuloException, UbicacionException;

	public abstract void enviarPedido(int idPedido) throws RemoteException, PedidoException;

	public abstract void bajaCliente(int dni) throws RemoteException, ClienteException;

	public abstract void modificarCliente(int dni, String nombre, String razonSocial, float limiteCredito, String condiciones, String notasAdv, String calle, int nroDirecc, String localidad, int cp) throws RemoteException, ClienteException;

	public abstract List<String> pagoDeFacturas(ClienteDTO c) throws RemoteException, ClienteException, FacturaException, CuentaCorrienteException;


	public abstract List<MovimientoAjusteDTO> buscarMovimientosAjuste() throws RemoteException; 
	
	public abstract List<MovimientoSimpleDTO> buscarMovimientosSimple() throws RemoteException ;
	
	public abstract List<MovimientoDañoDTO> buscarMovimientosDaño() throws RemoteException ;

}