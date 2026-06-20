package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import delegado.BusinessDelegate;
import dto.ArticuloDTO;
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

/**
 * Servlet implementation class Inicio
 */
@WebServlet("/Inicio")
public class Inicio extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Inicio() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		//request.getRequestDispatcher("/index.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String jspPage = "/index.jsp";

		if ((action == null) || (action.length() < 1)) {
			action = "default";
			request.setAttribute("excepcion", "");
			dispatch(jspPage, request, response);
		}
		if ("default".equalsIgnoreCase(action)) {
			jspPage = "/index.jsp";
		} else if ("login".equalsIgnoreCase(action)) {

			String usuario = request.getParameter("usuario");
			String contrasena = request.getParameter("password");


			switch(checkUsuario(usuario,contrasena)) {
			case "ADMIN":
				request.setAttribute("excepcion", "");
				dispatch("MenuAdminClientes.jsp",request,response);
				break;
			case "USUARIO":
				List<PedidoDTO> pedidosDTO;
				try {
					ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("usuario")));			
					pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente(usuario);
					request.setAttribute("excepcion", "");
					request.setAttribute("cliente", c);
					request.setAttribute("pedidos", pedidosDTO);
					dispatch("PantallaCliente.jsp",request,response);
				} catch (PedidoException | NumberFormatException | ClienteException e) {
					request.setAttribute("excepcion", e.getMessage());
					dispatch("index.jsp", request,response);
				}
				break;
			case "DESPACHO":
				request.setAttribute("excepcion", "");
				dispatch("MenuAdminDespacho.jsp",request,response);
				break;
			case "COMPRAS":
				request.setAttribute("excepcion", "");
				dispatch("MenuAdminCompras.jsp",request,response);
				break;
			case "DEPOSITO":
				request.setAttribute("excepcion", "");
				dispatch("MenuAdminDeposito.jsp",request,response);
				break;
			default:
				request.setAttribute("excepcion", "Los datos no coinciden.");
				dispatch("index.jsp",request,response);
				break;
			}
		}
		else if ("menuAdminClientes".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("MenuAdminClientes.jsp", request, response);
		}
		else if ("menuAdminDespacho".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("MenuAdminDespacho.jsp", request, response);
		}
		else if ("menuAdminDeposito".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("MenuAdminDeposito.jsp", request, response);
		}
		else if ("altaCliente".equalsIgnoreCase(action)) {
			String dni = request.getParameter("dni");
			String nombre = request.getParameter("nombre");
			String razonSocial = request.getParameter("Razon Social");
			String cuit = request.getParameter("CUIT");
			String limiteCredito = request.getParameter("Limite");
			String condiciones = request.getParameter("condesp");
			String notas = request.getParameter("notas");
			String calle = request.getParameter("calle");
			String numero = request.getParameter("numero");
			String localidad = request.getParameter("localidad");
			String cp = request.getParameter("cp");
			try {
				BusinessDelegate.getInstancia().altaCliente(Integer.parseInt(dni), nombre, razonSocial,Integer.parseInt(cuit), Float.parseFloat(limiteCredito), condiciones, notas, calle, Integer.parseInt(numero), localidad, Integer.parseInt(cp));
				request.setAttribute("excepcion","Cliente cargado con éxito.");
				dispatch("AltaCliente.jsp", request, response);
			} catch (ClienteException | NumberFormatException | SQLException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("AltaCliente.jsp", request, response);
			}
		}
		else if ("bajaCliente".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("BajaCliente.jsp", request, response);
		}
		else if ("eliminarCliente".equalsIgnoreCase(action)) {
			try {
				int dni = Integer.parseInt(request.getParameter("dni"));
				BusinessDelegate.getInstancia().bajaCliente(dni);
				request.setAttribute("excepcion","Cliente eliminado con éxito.");
				dispatch("BajaCliente.jsp", request, response);
			} catch (ClienteException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("BajaCliente.jsp", request, response); 
			}
		}
		else if ("modificarCliente".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion","");
			dispatch("ModificarCliente.jsp", request, response);
		}
		else if ("editarCliente".equalsIgnoreCase(action)) {
			try {
				ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
				request.setAttribute("cliente", c);
				request.setAttribute("excepcion","");
				dispatch("EditarCliente.jsp", request, response);
			} catch (NumberFormatException | ClienteException e) {
				request.setAttribute("excepcion",e.getMessage());
				dispatch("ModificarCliente.jsp",request,response);
			}
		}
		else if ("modificacionCliente".equalsIgnoreCase(action)) {
			try {
				int dni = Integer.parseInt(request.getParameter("dni"));
				String nombre = request.getParameter("nombre");
				String razonSocial = request.getParameter("razonSocial");
				//cuit no es modificable entonces no lo recibo aca y lo paso para modificar. solo con el dni que es su id me alcanza
				float limiteCredito = Float.parseFloat(request.getParameter("limite"));
				String condiciones = request.getParameter("condicion");
				String notasAdv = request.getParameter("notas");
				String calle = request.getParameter("calle");
				int nroDirecc = Integer.parseInt(request.getParameter("numero"));
				String localidad = request.getParameter("localidad");
				int cp = Integer.parseInt(request.getParameter("cp"));
				BusinessDelegate.getInstancia().modificarCliente(dni,nombre,razonSocial,limiteCredito,condiciones,notasAdv,calle,nroDirecc,localidad,cp);
				request.setAttribute("excepcion","Cliente modificar con éxito");
				dispatch("ModificarCliente.jsp", request, response);
			} catch (ClienteException e) {
				request.setAttribute("excepcion",e.getMessage());
				dispatch("ModificarCliente.jsp", request, response); 
			}
		}
		else if ("showAC".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("AltaCliente.jsp", request, response);
		}
		else if ("showOP".equalsIgnoreCase(action)) {
			List<OrdenDePedidoDTO> ords = BusinessDelegate.getInstancia().buscarOPS();
			request.setAttribute("pedidos", ords);
			request.setAttribute("excepcion", "");
			dispatch("OrdenesDePedido.jsp", request, response);
		}
		else if ("detalleOP".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("DetalleOP.jsp", request, response);
		}
		else if ("procesarOC".equalsIgnoreCase(action)) {
			try {
				BusinessDelegate.getInstancia().procesarOC(Integer.parseInt(request.getParameter("orden")));
				List<OrdenDeCompraDTO> ords = BusinessDelegate.getInstancia().buscarOCS();
				request.setAttribute("excepcion", "Orden de compra procesada con éxito.");
				request.setAttribute("pedidos", ords);
				dispatch("OrdenesDeCompra.jsp", request, response);
			} catch (NumberFormatException | PedidoException | SQLException | ArticuloException | LoteException
					| OrdenDeCompraException | UbicacionException | OrdenDePedidoException | MovimientoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminCompras.jsp", request,response);
				
			}
		}
		else if ("crearOrdenesDeCompra".equalsIgnoreCase(action)) {
			String[] proveedores = request.getParameterValues("proveedores");
			String[] articulos = request.getParameterValues("articulos");
			int idOP = Integer.parseInt(request.getParameter("idOP"));
			List<String> auxP = new ArrayList<String>();
			if (proveedores != null)
				for(int i=0; i<proveedores.length; i++){
					if(!auxP.contains(proveedores[i])){
						auxP.add(proveedores[i]);
					}
				}
			for (String s: auxP) {
				List<String> artID = new ArrayList<String>();
				for(int i=0; i<proveedores.length; i++){
					if (proveedores[i].equals(s)) {
						artID.add(articulos[i]);
					}
				}
				try {
					BusinessDelegate.getInstancia().emitirOC(idOP, artID, s);
				} catch (ArticuloException | OrdenDeCompraException | ProveedorException | PedidoException | NumberFormatException | OrdenDePedidoException e) {
					request.setAttribute("excepcion", e.getMessage());
					List<OrdenDePedidoDTO> ords = BusinessDelegate.getInstancia().buscarOPS();
					request.setAttribute("pedidos", ords);
					dispatch("OrdenesDePedido.jsp", request, response);
				}
			}
			request.setAttribute("excepcion", "Orden de compra emitida con éxito.");
			List<OrdenDePedidoDTO> ords = BusinessDelegate.getInstancia().buscarOPS();
			request.setAttribute("pedidos", ords);
			dispatch("OrdenesDePedido.jsp", request, response);

		}
		else if ("showOC".equalsIgnoreCase(action)) {
			List<OrdenDeCompraDTO> ords = BusinessDelegate.getInstancia().buscarOCS();
			request.setAttribute("pedidos", ords);
			request.setAttribute("excepcion", "");
			dispatch("OrdenesDeCompra.jsp", request, response);
		}
		else if ("menuCompras".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("MenuAdminCompras.jsp", request, response);
		}
		else if ("showPedidos".equalsIgnoreCase(action)) {
			try {
				List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("PENDIENTE");
				request.setAttribute("excepcion", "");
				request.setAttribute("pedidos", pedidosDTO);
				dispatch("PedidosAdmin.jsp", request, response);
			} catch (PedidoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminClientes.jsp",request,response);
			}

		}
		else if ("pedidoAdmin".equalsIgnoreCase(action)) {
			String idPedido = request.getParameter("idPedido");
			String aclaracion = request.getParameter("aclaracion");
			String tipo = request.getParameter("tipo");
			String excepcion = "";
			boolean a = true;
			if(tipo.equalsIgnoreCase("Aprobar")) {
				a = true;
				excepcion="Pedido aprobado con éxito.";
			}else {
				a = false;
				excepcion="Pedido rechazado con éxito.";
			}
			try {	
				
				BusinessDelegate.getInstancia().autorizarPedido(a,idPedido,aclaracion);
				
			} catch (PedidoException | ArticuloException | OrdenDePedidoException | ProveedorException
					| OrdenDeCompraException | ClienteException | FacturaException | CuentaCorrienteException | RemitoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("PedidosAdmin.jsp", request,response);
			}
			
			try {
				List<PedidoDTO>pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("PENDIENTE");
				request.setAttribute("pedidos", pedidosDTO);
				request.setAttribute("excepcion", excepcion);
				dispatch("PedidosAdmin.jsp", request, response);
			} catch (PedidoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminClientes", request,response);
				
			}
		}
		else if ("showDetallePedido".equalsIgnoreCase(action)) {
			try {
				PedidoDTO p = BusinessDelegate.getInstancia().buscarPedidoById(Integer.parseInt(request.getParameter("pedido")));
				request.setAttribute("pedido", p);
				dispatch("DetallePedido.jsp", request, response);
			} catch (NumberFormatException | PedidoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("index.jsp",request,response);
			}
			
		}
		else if ("PantallaCliente".equalsIgnoreCase(action)) {
			List<PedidoDTO> pedidosDTO;
			try {
				ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("usuario")));
				pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente(request.getParameter("usuario"));
				request.setAttribute("pedidos", pedidosDTO);
				request.setAttribute("cliente", c);
				request.setAttribute("excepcion", "");
				dispatch("PantallaCliente.jsp", request, response);
			} catch (PedidoException | NumberFormatException | ClienteException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("index.jsp", request,response);
			}

		}
		else if ("guardarPedido".equalsIgnoreCase(action)) {
			int dni = Integer.parseInt((String) request.getParameter("dni"));
			String[] articulos = request.getParameterValues("articulos");
			String[] cantidades = request.getParameterValues("cantidades");

			String calle= request.getParameter("calle");
			int numero= Integer.parseInt((String) request.getParameter("numero"));
			String localidad= request.getParameter("localidad");
			int cp = Integer.parseInt((String)  request.getParameter("cp"));
			String pago= request.getParameter("pago");



			if(articulos==null) {
				request.setAttribute("pedidoCompleto", "0");
				request.setAttribute("excepcion", "Error en la seleccion de articulo o cantidad");
				dispatch("NuevoPedido.jsp", request, response);
			} else {
				List<ItemPedidoDTO> items = new ArrayList<ItemPedidoDTO>();
				ArticuloDTO art;
				ClienteDTO c;
				try {
					for(int i=0; i<articulos.length; i++){
						art = BusinessDelegate.getInstancia().buscarArticuloById(Integer.parseInt(articulos[i]));
						if (cantidades[i].isEmpty())
							throw new ArticuloException("Error en setear una cantidad");
						int cantidad = Integer.parseInt(cantidades[i]);
						ItemPedidoDTO it = new ItemPedidoDTO();
						it.setArticulo(art);
						it.setCant(cantidad);
						items.add(it);
					}
					c = BusinessDelegate.getInstancia().buscarClienteByDni(dni);
					BusinessDelegate.getInstancia().altaPedido(items, "PENDIENTE", c, pago, calle, numero, localidad, cp, "");
					request.setAttribute("excepcion", "");
					request.setAttribute("pedidoCompleto", "1");
					dispatch("NuevoPedido.jsp", request, response);
				} catch (NumberFormatException | ArticuloException | ClienteException | PedidoException | OrdenDePedidoException | ProveedorException | OrdenDeCompraException | FacturaException | RemitoException | CuentaCorrienteException e) {
					request.setAttribute("excepcion", e.getMessage());
					request.setAttribute("pedidoCompleto", "0");
					dispatch("NuevoPedido.jsp", request, response);
				}

			}	

		}
		else if ("nuevoPedido".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			request.setAttribute("pedidoCompleto", "0");
			dispatch("NuevoPedido.jsp", request, response);
		}
		else if ("facturasCliente".equalsIgnoreCase(action)) {
			List<FacturaDTO> facturasDTO;
			try {
				facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
				request.setAttribute("facturas", facturasDTO);
				request.setAttribute("excepcion", "");
				dispatch("FacturasCliente.jsp", request, response);
			} catch (NumberFormatException | FacturaException e) {
				ClienteDTO c;
				try {
					c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
					List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente("dni");
					request.setAttribute("cliente", c);
					request.setAttribute("pedidos", pedidosDTO);
					request.setAttribute("excepcion", e.getMessage());
					dispatch("PantallaCliente.jsp", request,response);
				} catch (NumberFormatException | ClienteException | PedidoException e1) {
					request.setAttribute("excepcion", e1.getMessage());
					dispatch("index.jsp", request,response);
					
				}

			}

		}
		else if ("pagarFactura".equalsIgnoreCase(action)) {
			List<FacturaDTO> facturasDTO;
			try {
				FacturaDTO f = BusinessDelegate.getInstancia().buscarFacturaById(request.getParameter("idFactura"));
				BusinessDelegate.getInstancia().pagarFactura(f);
				facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
				request.setAttribute("facturas", facturasDTO);
				request.setAttribute("excepcion", "");
				dispatch("FacturasCliente.jsp", request, response);
			} catch (NumberFormatException | FacturaException | ClienteException | CuentaCorrienteException e) {
				try {
					facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
					request.setAttribute("facturas", facturasDTO);
					request.setAttribute("excepcion", "");
					dispatch("FacturasCliente.jsp", request, response);
				} catch (NumberFormatException | FacturaException e1) {
					ClienteDTO c;
					try {
						c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
						List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente("dni");
						request.setAttribute("cliente", c);
						request.setAttribute("pedidos", pedidosDTO);
						request.setAttribute("excepcion", e1.getMessage());
						dispatch("PantallaCliente.jsp", request,response);
					} catch (NumberFormatException | ClienteException | PedidoException e2) {
						request.setAttribute("excepcion", e2.getMessage());
						dispatch("index.jsp", request,response);
						
					}
				}

			}
		}
		else if ("pagarTodasFacturas".equalsIgnoreCase(action)) {
			List<FacturaDTO> facturasDTO;
			String Excepcion = "";
			try {
				ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
				facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(c.getDni());
				if (facturasDTO.size()>0) {
					List<String> facturasPagas = BusinessDelegate.getInstancia().pagoDeFacturas(c);
					if (facturasPagas.size()>0) {
						Excepcion = "Se han pagado las facturas: ";
						for (String f: facturasPagas) {
							Excepcion = Excepcion + f + " , ";
						}
					}
					else {
						Excepcion = "No se pudo pagar ninguna factura. Ingrese saldo por favor.";
					}
				}
				else {
					Excepcion= "No existen facturas para pagar.";
				}
				facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(c.getDni());
				request.setAttribute("facturas", facturasDTO);
				request.setAttribute("excepcion", Excepcion);
				dispatch("FacturasCliente.jsp", request, response);

			} catch (NumberFormatException | FacturaException | ClienteException | CuentaCorrienteException e) {
				try {
					facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
					request.setAttribute("facturas", facturasDTO);
					request.setAttribute("excepcion", "");
					dispatch("FacturasCliente.jsp", request, response);
				} catch (NumberFormatException | FacturaException e1) {
					ClienteDTO c;
					try {
						c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
						List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente("dni");
						request.setAttribute("cliente", c);
						request.setAttribute("pedidos", pedidosDTO);
						request.setAttribute("excepcion", e1.getMessage());
						dispatch("PantallaCliente.jsp", request,response);
					} catch (NumberFormatException | ClienteException | PedidoException e2) {
						request.setAttribute("excepcion", e2.getMessage());
						dispatch("index.jsp", request,response);
						
					}
				}
			}
		}
		else if ("pedidosDespacho".equalsIgnoreCase(action)) {
			List<PedidoDTO> pedidosDTO;
			try {
				pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
				request.setAttribute("pedidos", pedidosDTO);
				request.setAttribute("excepcion", "");
				dispatch("PedidosDespacho.jsp",request,response);
			} catch (PedidoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminDespacho", request, response);
			}
		}
		else if ("despacharPedido".equalsIgnoreCase(action)) {
			List<PedidoDTO> pedidosDTO;
			try {
				PedidoDTO p = BusinessDelegate.getInstancia().buscarPedidoById(Integer.parseInt(request.getParameter("idPedido")));
				BusinessDelegate.getInstancia().despacharPedido(p);
				request.setAttribute("excepcion", "Pedido despachado con éxito");
				pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
				request.setAttribute("pedidos", pedidosDTO);
				dispatch("PedidosDespacho.jsp",request,response);
			} catch (PedidoException | UbicacionException | ArticuloException | ClienteException | FacturaException | RemitoException | MovimientoException e) {
				try {
					pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_DE_DESPACHO");
					request.setAttribute("excepcion", e.getMessage());
					request.setAttribute("pedidos", pedidosDTO);
					dispatch("PedidosDespacho.jsp",request,response);
				} catch (PedidoException e1) {
					request.setAttribute("excepcion", e1.getMessage());
					dispatch("MenuAdminDespacho.jsp",request,response);
				}

			}
		}
		else if ("agregarSaldo".equalsIgnoreCase(action)) {
			try {
				ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
				if(request.getParameter("saldo").isEmpty())
					throw new NumberFormatException("El saldo no puede estar vacio.");
				float monto = Float.parseFloat(request.getParameter("saldo"));
				BusinessDelegate.getInstancia().agregarSaldo(c, monto);
				List<FacturaDTO> facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
				request.setAttribute("facturas", facturasDTO);
				request.setAttribute("excepcion", "Saldo cargado.");
				dispatch("FacturasCliente.jsp", request, response);
			} catch (NumberFormatException | ClienteException | CuentaCorrienteException | FacturaException e) {
				List<FacturaDTO> facturasDTO;
				try {
					facturasDTO = BusinessDelegate.getInstancia().buscarFacturasByCliente(Integer.parseInt(request.getParameter("dni")));
					request.setAttribute("facturas", facturasDTO);
					request.setAttribute("excepcion", e.getMessage());
					dispatch("FacturasCliente.jsp", request, response);
				} catch (NumberFormatException | FacturaException e1) {
					ClienteDTO c;
					try {
						c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
						List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByCliente("dni");
						request.setAttribute("cliente", c);
						request.setAttribute("pedidos", pedidosDTO);
						request.setAttribute("excepcion", e1.getMessage());
						dispatch("PantallaCliente.jsp", request,response);
					} catch (NumberFormatException | ClienteException | PedidoException e2) {
						request.setAttribute("excepcion", e2.getMessage());
						dispatch("index.jsp", request,response);
						
					}
				}
			}
		}

		else if ("reportarFalla".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("ReportarFalla.jsp", request, response);
		}
		else if ("registrarFalla".equalsIgnoreCase(action)) {
			int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
			String encargado = request.getParameter("encargado");
			String autorizante = request.getParameter("autorizante");
			String destino = request.getParameter("destino");
			String descripcion = request.getParameter("descripcion");
			try {
				BusinessDelegate.getInstancia().registrarFalla(idArticulo, encargado, autorizante, destino, descripcion);
				request.setAttribute("excepcion","Falla reportada con éxito.");
				dispatch("ReportarFalla.jsp", request, response);
			} catch (ArticuloException | MovimientoException | UbicacionException e) {
				request.setAttribute("excepcion",e.getMessage());
				dispatch("ReportarFalla.jsp", request, response);
			}
		}
		else if ("reportarInconsistencia".equalsIgnoreCase(action)) {
			request.setAttribute("excepcion", "");
			dispatch("ReportarInconsistencia.jsp", request, response);
		}
		else if ("registrarInconsistencia".equalsIgnoreCase(action)) {
			int idArticulo = Integer.parseInt(request.getParameter("idArticulo"));
			String encargado = request.getParameter("encargado");
			String descripcion = request.getParameter("descripcion");
			int idLote = Integer.parseInt(request.getParameter("idLote"));
			String idUbicacion = request.getParameter("idUbicacion");
			try {
				BusinessDelegate.getInstancia().registrarInconsistencia(idArticulo, encargado, descripcion, idLote, idUbicacion);
				request.setAttribute("excepcion", "Inconsistencia reportada con éxito.");
				dispatch("ReportarInconsistencia.jsp", request, response);
			} catch (ArticuloException | MovimientoException | LoteException | UbicacionException e) {
				request.setAttribute("excepcion",e.getMessage());
				dispatch("ReportarInconsistencia.jsp", request, response);
			}
		}
		else if ("verificarVencimientos".equalsIgnoreCase(action)) {
			try {
				BusinessDelegate.getInstancia().verificarVencimientos();
				request.setAttribute("excepcion", "Vencimientos verificados con éxito.");
				dispatch("MenuAdminDeposito.jsp", request, response);
			} catch (MovimientoException | ArticuloException | UbicacionException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminDeposito.jsp", request, response);
			}
		}
		else if ("pedidosEnvio".equalsIgnoreCase(action)) {
			try {
				List<PedidoDTO> pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_ENVIO");
				request.setAttribute("pedidos", pedidosDTO);
				request.setAttribute("excepcion", "");
				dispatch("PedidosEnviados.jsp", request, response);
			} catch (PedidoException e) {
				request.setAttribute("excepcion", e.getMessage());
				dispatch("MenuAdminDespacho.jsp", request, response);
			}
		}
		else if ("enviarPedido".equalsIgnoreCase(action)) {
			try {
				List<PedidoDTO> pedidosDTO;
				int idPedido = Integer.parseInt(request.getParameter("idPedido"));
				BusinessDelegate.getInstancia().enviarPedido(idPedido);
				request.setAttribute("excepcion", "Pedido enviado con éxito.");
				pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_ENVIO");
				request.setAttribute("pedidos", pedidosDTO);
				dispatch("PedidosEnviados.jsp", request, response);
			} catch (PedidoException e) {
				List<PedidoDTO> pedidosDTO;
				try {
					pedidosDTO = BusinessDelegate.getInstancia().buscarPedidosByEstado("APROBADO_EN_ESPERA_ENVIO");
					request.setAttribute("pedidos", pedidosDTO);
					request.setAttribute("excepcion", e.getMessage());
					dispatch("PedidosEnviados.jsp", request, response);
				} catch (PedidoException e1) {
					request.setAttribute("excepcion", e1.getMessage());
					dispatch("MenuAdminDespacho.jsp", request, response);
				}
			}
		}
		else if ("verMovimientos".equalsIgnoreCase(action)) {
			List<MovimientoSimpleDTO> movSimple = BusinessDelegate.getInstancia().buscarMovimientosSimple();
			List<MovimientoAjusteDTO> movAjuste = BusinessDelegate.getInstancia().buscarMovimientosAjuste();
			List<MovimientoDañoDTO> movDaño = BusinessDelegate.getInstancia().buscarMovimientosDaño();
			request.setAttribute("movSimple", movSimple);
			request.setAttribute("movDaño", movDaño);
			request.setAttribute("movAjuste", movAjuste);
			dispatch("VerMovimientos.jsp", request, response);
		}
	}



	private String checkUsuario(String usuario, String contrasena) {

		switch (usuario.toLowerCase()) {
		case "1":
			if (contrasena.equalsIgnoreCase("1"))
				return "USUARIO";
			break;
		case "2":
			if (contrasena.equalsIgnoreCase("2"))
				return "USUARIO";
			break;
		case "3":
			if (contrasena.equalsIgnoreCase("3"))
				return "USUARIO";
			break;
		case "4":
			if (contrasena.equalsIgnoreCase("4"))
				return "USUARIO";
			break;
		case "5":
			if (contrasena.equalsIgnoreCase("5"))
				return "USUARIO";
			break;
		case "admin":
			if (contrasena.equalsIgnoreCase("admin"))
				return "ADMIN";
			break;
		case "deposito":
			if (contrasena.equalsIgnoreCase("deposito"))
				return "DEPOSITO";
			break;
		case "despacho":
			if (contrasena.equalsIgnoreCase("despacho"))
				return "DESPACHO";
			break;
		case "compras":
			if (contrasena.equalsIgnoreCase("compras"))
				return "COMPRAS";
			break;
		default:
			return "";
		}
		return "";
	}

	protected void dispatch(String jsp, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (jsp != null) {
			/*
			 * Envía el control al JSP que pasamos como parámetro, y con los
			 * request / response cargados con los parámetros
			 */
			RequestDispatcher rd = request.getRequestDispatcher(jsp);
			rd.forward(request, response);
		}
	}
}