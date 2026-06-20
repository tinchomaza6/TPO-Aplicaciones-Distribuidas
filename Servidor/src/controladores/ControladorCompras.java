package controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.OrdenDeCompraDao;
import dao.OrdenDePedidoDao;
import dao.ProveedoresDao;
import dto.ArticuloDTO;
import dto.OrdenDeCompraDTO;
import dto.OrdenDePedidoDTO;
import dto.ProveedorDTO;
import excepciones.ArticuloException;
import excepciones.LoteException;
import excepciones.MovimientoException;
import excepciones.OrdenDeCompraException;
import excepciones.OrdenDePedidoException;
import excepciones.PedidoException;
import excepciones.ProveedorException;
import excepciones.UbicacionException;
import negocio.ArticuloDeposito;
import negocio.ItemOrdenDeCompra;
import negocio.ItemOrdenDePedido;
import negocio.Lote;
import negocio.OrdenDeCompra;
import negocio.OrdenDePedido;
import negocio.Proveedor;

public class ControladorCompras {

	private static ControladorCompras instancia;

	public static ControladorCompras getInstancia() {
		if(instancia == null)
			return new ControladorCompras();
		return instancia;
	}

	public void emitirOC(int idOP, List<String> artID, String s) throws PedidoException, NumberFormatException, ProveedorException, OrdenDeCompraException, ArticuloException, OrdenDePedidoException {
		OrdenDePedido op = OrdenDePedidoDao.getInstancia().buscarOPByID(idOP);
		Proveedor prov = ProveedoresDao.getInstancia().buscarProveedorByCuit(Integer.parseInt(s));
		OrdenDeCompra oc = new OrdenDeCompra();
		oc.setEstado("PENDIENTE");
		oc.setOrdenPedido(op);
		oc.setProv(prov);
		oc.setFecha(Calendar.getInstance().getTime());
		int id = oc.save();
		oc.setIdOc(id);	
		for (String art: artID) {
			for (ItemOrdenDePedido item : op.getArticulos()) {
				if(item.getArticulo().getIdArticulo() == (Integer.parseInt(art)))
					oc.nuevoItemOC(item.getArticulo(), item.getCant(), item.getArticulo().getPrecioVentaUnitario());
			}
		}
		op.setEstado("PENDIENTE DE RECEPCION");
		op.update();
	}

	public void procesarOC(int idOC) throws PedidoException, SQLException, ArticuloException, LoteException, OrdenDeCompraException, UbicacionException, OrdenDePedidoException, MovimientoException {
		List<ArticuloDeposito> articulosRecibidos = new ArrayList<ArticuloDeposito>();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 180);
		Date fechaLote = c.getTime();
		Lote lote = new Lote(fechaLote);
		int id = lote.save();
		lote.setIdLote(id);
		OrdenDeCompra OC = OrdenDeCompraDao.getInstancia().buscarOCById(idOC);
		for (ItemOrdenDeCompra item : OC.getItems()) {
			for (int cant = item.getCantidad() ; cant > 0 ; cant--)
				articulosRecibidos.add(new ArticuloDeposito(item.getArticulo(), lote));
		}		
		ControladorDeposito.getInstancia().ubicar(articulosRecibidos);
		OC.setEstado("COMPLETADA");
		OC.update();
		ControladorDeposito.getInstancia().completarPedido(OC.getOrdenPedido().getPedido());
		ControladorDeposito.getInstancia().completarPedidosRestantes(OC.getOrdenPedido().getPedido().getNroPedido());

	}

	public List<ProveedorDTO> buscarProveedores (ArticuloDTO adto){
		List<ProveedorDTO> devolver = new ArrayList<ProveedorDTO>();
		List<OrdenDeCompra> ordenes = OrdenDeCompraDao.getInstancia().buscarOCCompletadas();
		List<Proveedor> proveedores = ProveedoresDao.getInstancia().buscarProveedores(adto.getIdArticulo());
		for (OrdenDeCompra oc : ordenes) {
			for (ItemOrdenDeCompra it : oc.getItems()) {
				for (Proveedor p : proveedores) {
					if (oc.getProv().getIdProv().compareTo(p.getIdProv()) == 0 && it.getArticulo().getIdArticulo() == adto.getIdArticulo()) {
						devolver.add(p.toDTO());
					}
				}
			}
		}
		//hasta se agregaron los que tenian al menos un pedido realizado y en orden por los mas recientes
		//ahora se agregan los que faltan asi aparecen todos los proveedores de ese articulo
		for(Proveedor p : proveedores) {
			if(!devolver.contains(p.toDTO())) {
				devolver.add(p.toDTO());
			}
		}
		return devolver;
	}

	public OrdenDePedidoDTO buscarOPById(int idOP) throws PedidoException {
		OrdenDePedido orden = OrdenDePedidoDao.getInstancia().buscarOPByID(idOP);
		return orden.toDTO();
	}

	public List<OrdenDeCompraDTO> buscarOCS() {
		List<OrdenDeCompraDTO> devolver = new ArrayList<OrdenDeCompraDTO>();
		List<OrdenDeCompra> ords = OrdenDeCompraDao.getInstancia().buscarOCS();
		for(OrdenDeCompra oc : ords) {
			devolver.add(oc.toDTO());
		}
		return devolver;
	}

	public List<OrdenDePedidoDTO> buscarOPS() {
		List<OrdenDePedidoDTO> devolver = new ArrayList<OrdenDePedidoDTO>();
		List<OrdenDePedido> ords = OrdenDePedidoDao.getInstancia().buscarOPS();
		for(OrdenDePedido op : ords) {
			devolver.add(op.toDTO());
		}
		return devolver;
	}
}