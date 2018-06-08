package controladores;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dao.ArticuloDao;
import dao.OrdenDeCompraDao;
import dto.ItemPedidoDTO;
import excepciones.ArticuloException;
import excepciones.LoteException;
import excepciones.OrdenDeCompraException;
import excepciones.PedidoException;
import excepciones.UbicacionException;
import negocio.Articulo;
import negocio.ArticuloDeposito;
import negocio.ItemOrdenDeCompra;
import negocio.ItemOrdenDePedido;
import negocio.Lote;
import negocio.OrdenDeCompra;
import negocio.OrdenDePedido;
import negocio.Proveedor;

public class ControladorCompras {

	private List<OrdenDeCompra> misOC = new ArrayList<OrdenDeCompra>();
	private static ControladorCompras instancia;
	
	public static ControladorCompras getInstancia() {
		if(instancia == null)
			return new ControladorCompras();
		return instancia;
	}

	public OrdenDeCompra buscarOrdenDeCompraById(int idOC){
		return null;
	}
	public void emitirOC(OrdenDePedido op, Proveedor prov) throws ArticuloException, OrdenDeCompraException{
		OrdenDeCompra oc = new OrdenDeCompra();
		oc.setEstado("PENDIENTE");
		oc.setOrdenPedido(op);
		oc.setProv(prov);
		oc.setFecha(Calendar.getInstance().getTime());
		int id = oc.save();
		oc.setIdOc(id);		
		//this.misOC.add(oc);
		for (ItemOrdenDePedido item : op.getArticulos()) {
			oc.nuevoItemOC(item.getArticulo(), item.getCant(), item.getArticulo().getPrecioVentaUnitario());
		}
	}
	public List<OrdenDeCompra> buscarByEstado(String estado){
		return misOC;
	}
	public void actualizarEstado(OrdenDeCompra oc, String estado){

	}
	public List<Proveedor> busarUltimosTres(OrdenDeCompra oc){
		return null;
	}

	public void procesarOC(int idOC) throws PedidoException, SQLException, ArticuloException, LoteException, OrdenDeCompraException, UbicacionException {
		OrdenDeCompra OC = OrdenDeCompraDao.getInstancia().buscarOPById(idOC);
		Lote loteRandom=null;
		List<ArticuloDeposito> articulosRecibidos = new ArrayList<ArticuloDeposito>();
		//seteo por defecto que el lote vence en 180 dias
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 180);
		Date fechaLote = c.getTime();
		for (ItemOrdenDeCompra item : OC.getItems()) {
			loteRandom= new Lote(fechaLote);
			//Crear funcion que genere un lote random
			for (int cant = item.getCantidad() ; cant > 0 ; cant--)
				articulosRecibidos.add(new ArticuloDeposito(item.getArticulo(), loteRandom));
		}		
		ControladorDeposito.getInstancia().ubicar(articulosRecibidos);
		OC.setEstado("COMPLETADA");
		OC.update();
		ControladorDeposito.getInstancia().completarPedido(OC.getOrdenPedido().getPedido());
		ControladorDeposito.getInstancia().completarPedidosRestantes(OC.getOrdenPedido().getPedido().getNroPedido());
	}
}