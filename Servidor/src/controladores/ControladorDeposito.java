package controladores;

import java.util.*;

import javax.swing.JOptionPane;

import dao.ArticuloDao;
import dao.ArticuloDepositoDao;
import dao.LoteDao;
import dao.MovimientoDao;
import dao.OrdenDePedidoDao;
import dao.PedidoDao;
import dao.ProveedoresDao;
import dao.UbicacionDao;
import dto.ArticuloDTO;
import dto.ArticuloDepositoDTO;
import dto.MovimientoAjusteDTO;
import dto.MovimientoDañoDTO;
import dto.MovimientoSimpleDTO;
import dto.MovimientoStockDTO;
import dto.UbicacionDTO;
import excepciones.ArticuloException;
import excepciones.LoteException;
import excepciones.MovimientoException;
import excepciones.OrdenDeCompraException;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import excepciones.ProveedorException;
import excepciones.UbicacionException;
import negocio.Ubicacion;
import negocio.Articulo;
import negocio.ArticuloDeposito;
import negocio.ItemPedido;
import negocio.Lote;
import negocio.MovimientoAjuste;
import negocio.MovimientoDaño;
import negocio.MovimientoSimple;
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


	public void completarPedidosRestantes(int nroPedido) throws PedidoException, ArticuloException, OrdenDePedidoException {
		//este se llama para buscar todos los pedidos que no generaron una orden de pedido porque ya existia una para determinado articulo
		List<Pedido> pedidosRestantes = PedidoDao.getInstancia().buscarPedidosByEstadoPorOrden("APROBADO_EN_ESPERA_STOCK", nroPedido);
		for(Pedido p : pedidosRestantes) {
			this.completarPedido(p);
		}
	}

	public void completarPedido(Pedido pedido) throws PedidoException, ArticuloException, OrdenDePedidoException {
		//Este se llama cuando se completa una orden de compra
		int stockTotal=0;
		boolean pedidoCompleto=true;
		for (ItemPedido it: pedido.getItemsPedido()){
			stockTotal = getCantidadStockTotal(it.getArticulo());
			int cantTemp = it.getCant() - it.getArticulo().cantidadReservada(pedido.getNroPedido());
			if (stockTotal >= cantTemp){
				for (Ubicacion u: ubicaciones){ // revisar cuando agregamos mas de un item a la orden de pedido a ver si esta ubicacion esta actualizada.
					for (ArticuloDeposito a : u.getArticulos())
						if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && cantTemp > 0 && a.getEstado().equals("DISPONIBLE")){
							a.reservarStock(pedido.getNroPedido());
							a.update();
							cantTemp--;
							//it.setCant(it.getCant()-1);
						}
				}
				it.update();
			}	
			else
			{
				pedidoCompleto=false; //falta otra orden de compra para poder completar el pedido
				if (stockTotal>0){
					for (Ubicacion u: ubicaciones){
						for (ArticuloDeposito a : u.getArticulos())
							if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && cantTemp>0 && a.getEstado().equals("DISPONIBLE")){
								{
									a.reservarStock(pedido.getNroPedido());
									a.update();
									cantTemp--;
									//it.setCant(it.getCant()-1);
								}
							}
					}
					it.update();
				}
			}

		}
		if(pedidoCompleto==true) {
			Pedido nuevoPedido = PedidoDao.getInstancia().buscarPedidoById(pedido.getNroPedido());
			nuevoPedido.setEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
			nuevoPedido.update();
			OrdenDePedido ordenPedido = OrdenDePedidoDao.getInstancia().buscarOPbyPedido(nuevoPedido.getNroPedido());
			if(ordenPedido != null) {
				ordenPedido.setEstado("COMPLETADA");
				ordenPedido.update();
			}
		}
	}

	public void verificarExistenciaStock(Pedido pedido) throws PedidoException, ArticuloException, OrdenDePedidoException, ProveedorException, OrdenDeCompraException {
		int stockTotal=0;
		boolean pedidoCompleto=true;
		List<Articulo> pedirArticulos = new ArrayList<Articulo>();
		for (ItemPedido it: pedido.getItemsPedido()){
			stockTotal = getCantidadStockTotal(it.getArticulo());
			int cantTemp = it.getCant() - it.getArticulo().cantidadReservada(pedido.getNroPedido());
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
				it.update();
			}	
			else
			{
				//comprobacion de que no exista otra Orden de pedido para el articulo
				pedidoCompleto=false;
				if(existeOrdenPedido(it.getArticulo(), cantTemp - stockTotal) ==  false)
					pedirArticulos.add(it.getArticulo());
				//reservo lo que haya del articulo
				if (stockTotal>0){
					for (Ubicacion u: ubicaciones){
						for (ArticuloDeposito a : u.getArticulos())
							if (a.getArticulo().getIdArticulo() == it.getArticulo().getIdArticulo() && a.getEstado().equals("DISPONIBLE") && cantTemp>0){
								a.reservarStock(pedido.getNroPedido());
								a.update();
								cantTemp--;
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
			}
			pedido.setEstado("APROBADO_EN_ESPERA_STOCK");
			pedido.update();
		}
		else{
			pedido.setEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
			pedido.update();
		}
	}


	private boolean existeOrdenPedido(Articulo articulo, int cantidad) throws OrdenDePedidoException {
		return OrdenDePedidoDao.getInstancia().existeOrdenPedido(articulo, cantidad); 
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

	public void ubicar(List<ArticuloDeposito> articulosRecibidos) throws ArticuloException, UbicacionException, MovimientoException{
		boolean fueInsertado;
		for (ArticuloDeposito a : articulosRecibidos) {
			fueInsertado = false;
			for (Ubicacion u : ubicaciones) {
				if(fueInsertado == false) {
					if(u.estaLibre() ||  (u.getLote() == a.getLote().getIdLote() && u.getCapacidadDisponible() >= a.getArticulo().getCapacidadArticulo())) {
						a.setUbicacion(u);
						u.getArticulos().add(a);
						int id = a.save();
						a.setIdArticuloDeposito(id);
						this.movimientoDeStockCarga(a,u);
						if(u.getEstado().equals("DISPONIBLE")) {
							u.setEstado("OCUPADA");
						}
						u.update();
						fueInsertado = true;
					}
				}
			}
		}
	}
	private void movimientoDeStockCarga(ArticuloDeposito a, Ubicacion u) throws MovimientoException {
		Date fecha = Calendar.getInstance().getTime();
		Articulo articulo = a.getArticulo();
		String ubicado = "Articulo: " + articulo.getNombre() + " ubicado en: " + u.getIdUbicacion() + " con Id Deposito: " + a.getIdArticuloDeposito();
		MovimientoSimple mov = new MovimientoSimple(fecha, articulo, ubicado);
		mov.save();
	}

	public void cargarTodasUbicacionesYArticulos() {
		List<Ubicacion> ubs = UbicacionDao.getInstancia().cargarUbicaciones();
		List<ArticuloDeposito> arts = ArticuloDepositoDao.getInstancia().cargarArticulosDeposito();
		for (Ubicacion u : ubs) {
			for (ArticuloDeposito a : arts)
				if (a.getUbicacion().getIdUbicacion().equals(u.getIdUbicacion())) {
					u.getArticulos().add(a);
				}
			this.ubicaciones.add(u);
		}
	}


	public void retirar(Pedido pedido) throws UbicacionException, ArticuloException{
		for (Ubicacion u : ubicaciones){
			List<ArticuloDeposito> remover = new ArrayList<ArticuloDeposito>();
			for (ArticuloDeposito art : u.getArticulos()){
				if (art.getReservaIdPedido() != null) {
					if (art.getReservaIdPedido().compareTo(pedido.getNroPedido())==0){
						remover.add(art);
						ArticuloDeposito aux = this.buscarArticuloDepositoById(art.getIdArticuloDeposito());
						aux.delete();
					}
				}
			}
			u.getArticulos().removeAll(remover);
			if(u.getArticulos().isEmpty()) {
				u.setEstado("DISPONIBLE");
			}
			u.update();
		}
	}

	public ArticuloDTO buscarArticuloById(int idArt) throws ArticuloException{
		return ArticuloDao.getInstancia().buscarArticuloById(idArt).toDTO();
	}

	public Articulo buscarArticuloNegocioById(int idArt) throws ArticuloException{
		return ArticuloDao.getInstancia().buscarArticuloById(idArt);
	}

	public ArticuloDeposito buscarArticuloDepositoById(int idArt) throws ArticuloException{
		return ArticuloDepositoDao.getInstancia().buscarArticuloById(idArt);
	}

	public void movimientoDeStock(ItemPedido item) throws MovimientoException{
		Date fecha = Calendar.getInstance().getTime();
		int cant = item.getCant();
		Articulo art = item.getArticulo();
		String ubicaciones = this.getUbicacionesRetiradas(item.getPedido(), art);
		String des = "Despachadas " + cant + " Unid. del articulo: " + art.getNombre() + ubicaciones;
		MovimientoSimple mov = new MovimientoSimple(fecha, art, des);
		mov.save();
	} 

	private String getUbicacionesRetiradas(Pedido pedido, Articulo a) {
		String ubicaciones = "";
		List<ArticuloDeposito> articulosDelPedido = ArticuloDepositoDao.getInstancia().buscarArticulosReservados(pedido, a);
		for (ArticuloDeposito art : articulosDelPedido) {
			ubicaciones += " / U: " + art.getUbicacion().getIdUbicacion() + " - Id: " + art.getIdArticuloDeposito();
		}
		return ubicaciones;
	}

	public List<UbicacionDTO> getUbicaciones() {
		List<UbicacionDTO> u = new ArrayList<UbicacionDTO>();
		for (Ubicacion ub: ubicaciones){
			u.add(ub.toDTO());			
		}
		return u;
	}

	public List<ArticuloDTO> cargarArticulos() {
		List<ArticuloDTO> arts = new ArrayList<ArticuloDTO>();
		for (Articulo a : ArticuloDao.getInstancia().cargarArticulos()) {
			arts.add(a.toDTO());
		}
		return arts;
	}

	public void registrarFalla(int idArticulo, String encargado, String autorizante, String destino, String descripcion) throws ArticuloException, MovimientoException, UbicacionException {
		ArticuloDeposito a = this.buscarArticuloDepositoById(idArticulo);
		Date fecha = Calendar.getInstance().getTime();
		MovimientoDaño m = new MovimientoDaño(fecha, a.getArticulo(), destino, encargado, descripcion, autorizante);
		m.save();
		a.delete();
		//borrar el articulo de la ubicacion
		Ubicacion u = UbicacionDao.getInstancia().buscarUbicacionById(a.getUbicacion().getIdUbicacion());
		u.getArticulos().remove(a);
		if(u.getArticulos().isEmpty())
			u.setEstado("DISPONIBLE");
		u.update();
	}

	public void registrarInconsistencia(int idArticulo, String encargado, String descripcion, int idLote, String idUbicacion) throws ArticuloException, MovimientoException, LoteException, UbicacionException {
		//EJEMPLO: hay 10 en el deposito pero 9 en el sistema, entonces se crea un articuloDeposito
		ArticuloDeposito a = new ArticuloDeposito();
		Articulo aux = this.buscarArticuloNegocioById(idArticulo);
		Lote lote = LoteDao.getInstancia().buscarLoteById(idLote);
		Ubicacion u = UbicacionDao.getInstancia().buscarUbicacionById(idUbicacion);
		a.setArticulo(aux);
		a.setEstado("DISPONIBLE");
		a.setLote(lote);
		a.setUbicacion(u);
		a.setReservaIdPedido(null);
		a.save();
		Date fecha = Calendar.getInstance().getTime();
		MovimientoAjuste m = new MovimientoAjuste(fecha, a.getArticulo(), encargado, descripcion);
		m.save();
	}

	public void verificarVencimientos() throws MovimientoException, ArticuloException, UbicacionException {
		Date hoy = Calendar.getInstance().getTime();
		for(Ubicacion u : ubicaciones) {
			List<ArticuloDeposito> vencidos = new ArrayList<ArticuloDeposito>();
			if(!u.getArticulos().isEmpty()) {
				if((u.getArticulos().get(0).getLote().getFechaVenc()).before(hoy)) {
					//articulo vencido
					//en una ubicacion hay articulos de un solo lote, es decir, se vencieron todos los de esa ubiacion
					for(ArticuloDeposito a : u.getArticulos()) {
						MovimientoAjuste m = new MovimientoAjuste(hoy, a.getArticulo(), "SISTEMA", "Articulo vencido. Fecha Vencimiento: " + a.getLote().getFechaVenc() + "  -- Fecha al momento de la verificacion: " + hoy);
						m.save();
						a.delete();
						vencidos.add(a);
					}
					u.setEstado("DISPONIBLE");
					u.getArticulos().removeAll(vencidos);
					u.update();
				}
			}
		}
	}

	public List<MovimientoAjusteDTO> buscarMovimientosAjuste() {
		return MovimientoDao.getInstancia().buscarMovimientosAjuste();
	}
	
	public List<MovimientoSimpleDTO> buscarMovimientosSimple() {
		return MovimientoDao.getInstancia().buscarMovimientosSimple();
	}
	public List<MovimientoDañoDTO> buscarMovimientosDaño() {
		return MovimientoDao.getInstancia().buscarMovimientosDaño();
	}
	
	
}