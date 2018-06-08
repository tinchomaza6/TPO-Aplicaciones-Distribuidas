package controladores;

import java.sql.SQLException;
import java.util.*;

import javax.swing.JOptionPane;

import dao.ArticuloDao;
import dao.ArticuloDepositoDao;
import dao.OrdenDePedidoDao;
import dao.PedidoDao;
import dao.ProveedoresDao;
import dao.UbicacionDao;
import dto.ArticuloDTO;
import dto.PedidoDTO;
import dto.UbicacionDTO;
import excepciones.ArticuloException;
import excepciones.OrdenDeCompraException;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import excepciones.ProveedorException;
import excepciones.UbicacionException;
import negocio.Ubicacion;
import negocio.Articulo;
import negocio.ArticuloDeposito;
import negocio.ItemOrdenDePedido;
import negocio.ItemPedido;
import negocio.OrdenDePedido;
import negocio.Pedido;
import negocio.Proveedor;

public class ControladorDeposito {


	private static ControladorDeposito instancia;

	public static ControladorDeposito getInstancia() {
		if(instancia==null) {
			instancia = new ControladorDeposito();
		}
		return instancia;
	}

	private List<Ubicacion> ubicaciones = new ArrayList<Ubicacion>();
	private List<OrdenDePedido> orDePedidos = new ArrayList<OrdenDePedido>();
	
	public void completarPedidosRestantes(int nroPedido) throws PedidoException, ArticuloException {
		//este se llama para buscar todos los pedidos que no generaron una orden de pedido porque ya existia una para determinado articulo
		List<Pedido> pedidosRestantes = PedidoDao.getInstancia().buscarPedidosByEstadoPorOrden("APROBADO_EN_ESPERA_STOCK", nroPedido);
		for(Pedido p : pedidosRestantes)
			this.completarPedido(p);
	}

	public void completarPedido(Pedido pedido) throws PedidoException, ArticuloException {
		JOptionPane.showMessageDialog(null, "entro a completar pedido" + pedido.getNroPedido() + " - " + pedido.getItemsPedido().get(0).getIdItemPedido());
		//Este se llama cuando se completa una orden de compra
		int stockTotal=0;
		boolean pedidoCompleto=true;
		for (ItemPedido it: pedido.getItemsPedido()){
			stockTotal = getCantidadStockTotal(it.getArticulo());
			int cantTemp = it.getCant();
			if (stockTotal >= cantTemp){
				for (Ubicacion u: ubicaciones){
					for (ArticuloDeposito a : u.getArticulos())
						if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && cantTemp > 0 && a.getEstado().equals("DISPONIBLE")){
							a.reservarStock(pedido.getNroPedido());
							a.update();
							cantTemp--;
						}
				}
			}	
			else
			{
				pedidoCompleto=false; //falta otra orden de compra para poder completar el pedido
				if (stockTotal>0){
					for (Ubicacion u: ubicaciones){
						for (ArticuloDeposito a : u.getArticulos())
							if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && it.getCant()>0 && a.getEstado().equals("DISPONIBLE")){
								{
									a.reservarStock(pedido.getNroPedido());
									a.update();
									it.setCant(it.getCant()-1);
								}
							}
					}
					it.update();
				}
			}

		}
		if(pedidoCompleto==true) {
			pedido.setEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
			pedido.update();
			//modificar el array pedidosRealizados en ControladorClientes
		}
	}

	public void verificarExistenciaStock(Pedido pedido) throws PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException {
		int stockTotal=0;
		boolean pedidoCompleto=true;
		List<Articulo> pedirArticulos = new ArrayList<Articulo>();
		for (ItemPedido it: pedido.getItemsPedido()){
			stockTotal = getCantidadStockTotal(it.getArticulo());
			int cantTemp = it.getCant();
			if (stockTotal >= cantTemp){
				for (Ubicacion u: ubicaciones){
					for (ArticuloDeposito a : u.getArticulos()){
						if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && cantTemp > 0 && a.getEstado().equals("DISPONIBLE")){
							a.reservarStock(pedido.getNroPedido());
							a.update();
							cantTemp--;
						}
					}
				}

			}	
			else
			{
				//comprobacion de que no exista otra Orden de pedido para el articulo
				pedidoCompleto=false;
				if(!existeOrdenPedido(it.getArticulo(), cantTemp - stockTotal))
					pedirArticulos.add(it.getArticulo());
				//reservo lo que haya del articulo
				if (stockTotal>0){
					for (Ubicacion u: ubicaciones){
						for (ArticuloDeposito a : u.getArticulos())
							if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && a.getEstado().equals("DISPONIBLE") && it.getCant()>0){
								a.reservarStock(pedido.getNroPedido());
								a.update();
								it.setCant(it.getCant()-1);

							}
					}
					it.update();
				}
			}
		}
		if (pedidoCompleto==false){
			if(!pedirArticulos.isEmpty()) {
				OrdenDePedido op = new OrdenDePedido(pedido, "PENDIENTE DE PROVEEDOR");
				int id = op.save();
				op.setIdOp(id);
				for (Articulo a : pedirArticulos){
					int cantidadAComprar = a.getCantCompraFija();
					for (ItemPedido aux : pedido.getItemsPedido()) {
						if(aux.getArticulo().getIdArticulo() == a.getIdArticulo()) {
							while(aux.getCant() > cantidadAComprar) {
								cantidadAComprar += a.getCantCompraFija();
							}
						}
					}
					op.nuevoItemOP(a,cantidadAComprar);
				}
				//modificar el array pedidosRealizados en ControladorClientes

				orDePedidos.add(op);
				Proveedor prov = ProveedoresDao.getInstancia().buscarProveedorByCuit(2456932);
				ControladorCompras.getInstancia().emitirOC(op, prov);	
			}
			pedido.setEstado("APROBADO_EN_ESPERA_STOCK");
			pedido.update();
	
		}
		else
		{
			pedido.setEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
			pedido.update();
			//modificar el array pedidosRealizados en ControladorClientes
		}
	}


	private boolean existeOrdenPedido(Articulo articulo, int cantidad) throws OrdenDePedidoException {
		/*boolean b = false;
		for (OrdenDePedido op : this.orDePedidos)
			for (ItemOrdenDePedido item : op.getArticulos())
				if(item.getArticulo().getIdArticulo() == articulo.getIdArticulo())
					b = true;
		if(b)
			return b;
		else*/
			return OrdenDePedidoDao.getInstancia().existeOrdenPedido(articulo, cantidad); //Si hay que buscar en la base y agregarlo a memoria, retornar un objeto OP y agregarlo a la cache.
	}

	public int getCantidadStockTotal(Articulo articulo){
		int stockTotal=0;
		for (Ubicacion u: ubicaciones){
			for (ArticuloDeposito a : u.getArticulos())
				if (a.getArticulo().getIdArticulo() == articulo.getIdArticulo() && a.getEstado().equals("DISPONIBLE")){
					stockTotal++;
				}
		}
		return stockTotal;
	}

	public void ubicar(List<ArticuloDeposito> articulosRecibidos) throws ArticuloException, UbicacionException{
		for (ArticuloDeposito a : articulosRecibidos) {
			for (Ubicacion u : ubicaciones) {
				if(u.estaLibre() ||  (u.getLote() == a.getLote().getIdLote() && u.getCapacidadDisponible() >= a.getArticulo().getCapacidadArticulo())) {
					u.getArticulos().add(a);
					a.setUbicacion(u);
					a.update();
					if(u.getEstado().equals("DISPONIBLE")) {
						u.setEstado("OCUPADA");
						u.update();
					}
					articulosRecibidos.remove(a);
				}
			}
		}
		if(!articulosRecibidos.isEmpty())
			throw new UbicacionException("Algun articulo no pudo ser ubicado - Revisar");
	}

	public void cargarTodasUbicacionesYArticulos() {
		List<Ubicacion> ubs = UbicacionDao.getInstancia().cargarUbicaciones();
		List<ArticuloDeposito> arts = ArticuloDepositoDao.getInstancia().cargarArticulosDeposito();
		for (Ubicacion u : ubs) {
			for (ArticuloDeposito a : arts)
				if (a.getUbicacion().getIdUbicacion().equals(u.getIdUbicacion()))
					u.getArticulos().add(a);
			this.ubicaciones.add(u);
		}
	}


	public void retirar(PedidoDTO pedido) throws UbicacionException{ 
		for (Ubicacion u : ubicaciones){
			for (ArticuloDeposito art : u.getArticulos()){
				if (art.getReservaIdPedido() == pedido.getNroPedido()){
					u.getArticulos().remove(art);
					if(u.getArticulos().isEmpty())
						u.setEstado("DISPONIBLE");
				}
			}
			u.save();
		}
	}


	public Ubicacion buscarUbicacionById(String idUbicacion){
		return null;
	}

	public ArticuloDTO buscarArticuloById(int idArt) throws ArticuloException{
		return ArticuloDao.getInstancia().buscarArticuloById(idArt).toDTO();
	}
	public void movimientoDeStock(int cant, String tipoMovSimple){

	}
	public void movimientoDeStock(Articulo articulo, int cant, String tipoMovXDa�os, String destino, String encargado, String autorizante, String descripcion){

	}
	public void movimientoDeStock(Articulo articulo, int cant, String tipoMovAjuste, String encargado, String observacion){

	}
	public void actualizarStockPorDa�o(String destino, String tipo, String encargado, String autorizante, String descripcion, int cant, Articulo articulo){

	}
	public void actualizarStockPorIncosistencia(String tipo, String encargado, String descripcion, int cant, Articulo articulo){

	}
	public void actualizarStockPorVencimiento(String tipo, int cant, Articulo articulo){

	}

	public List<UbicacionDTO> getUbicaciones() {
		List<UbicacionDTO> u = new ArrayList<UbicacionDTO>();
		for (Ubicacion ub: ubicaciones){
			u.add(ub.toDTO());			
		}
		return u;
	}
}