package controladores;

import java.util.*;

import javax.swing.JOptionPane;

import dao.PedidoDao;
import dto.PedidoDTO;
import excepciones.ArticuloException;
import excepciones.ClienteException;
import excepciones.FacturaException;
import excepciones.MovimientoException;
import excepciones.PedidoException;
import excepciones.RemitoException;
import excepciones.UbicacionException;
import negocio.ItemPedido;
import negocio.Pedido;

public class ControladorDespacho {

	private static ControladorDespacho instancia;

	public static ControladorDespacho getInstancia() {
		if(instancia==null) {
			instancia = new ControladorDespacho();
		}
		return instancia;
	}

	public void despachar(PedidoDTO pedidoDTO) throws UbicacionException, PedidoException, ArticuloException, ClienteException, FacturaException, RemitoException, MovimientoException {
		Pedido pedido = ControladorClientes.getInstancia().buscarPedidoById(pedidoDTO.getNroPedido());
		for(ItemPedido item : pedido.getItemsPedido()) {
			ControladorDeposito.getInstancia().movimientoDeStock(item);
		}
		ControladorDeposito.getInstancia().retirar(pedido);
		ControladorFacturacion.getInstancia().facturarPedido(ControladorClientes.getInstancia().buscarPedidoById(pedidoDTO.getNroPedido()));
		ControladorFacturacion.getInstancia().generarRemito(pedido);
		Date fechaDespacho = Calendar.getInstance().getTime();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 20);
		//asumimos que la entrega se realiza en 20 dias
		Date fechaEntregaEsperada = c.getTime();
		pedido.setFechaEntregaEsperada(fechaEntregaEsperada);
		pedido.setFechaDespacho(fechaDespacho);
		pedido.setEstado("APROBADO_EN_ESPERA_ENVIO");
		pedido.update();
	}

	public List<PedidoDTO> listarPedidosDespacho() throws PedidoException{
		List<PedidoDTO> devolver = new ArrayList<PedidoDTO>();
		List<Pedido> pedidos = PedidoDao.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_DESPACHO");
		for(Pedido pedido : pedidos){
			devolver.add(pedido.toDTO());
		}
		return devolver;
	}

	public void enviarPedido(int idPedido) throws PedidoException {
		//enviar lo tomamos como finalizado y entregado al cliente
		Pedido pedido = ControladorClientes.getInstancia().buscarPedidoById(idPedido);
		pedido.setEstado("FINALIZADO");
		Date fechaHoy = Calendar.getInstance().getTime();
		pedido.setFechaEntrega(fechaHoy);
		pedido.update();
	}
}