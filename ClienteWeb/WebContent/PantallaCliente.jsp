<%@page import="delegado.BusinessDelegate"%>
<%@ page import="dto.PedidoDTO"%>
<%@ page import="dto.ClienteDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>DistribuidasFinal</title>
	<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" href="assets/css/styles.css">
	<link rel="stylesheet" href="assets/css/untitled.css">
	<link rel="stylesheet" href="assets/css/untitled-1.css">
	<link rel="stylesheet" href="assets/css/buscar.css">
	<link rel="stylesheet" href="assets/css/Pretty-Registration-Form.css">
</head>
<body>
	<input id="Exception" type="hidden" name ="Exception" value="<%=request.getAttribute("excepcion")%>"/>
	<nav class="navbar navbar-default">
		<div style="margin-right: 0;position: absolute;right: 5%;top: 10%;">
   			<button class="btn btn-info" type="button" onclick="location.href='Inicio?action='">Log out</button>
		</div>
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand navbar-link" href="#"><strong>Das Verrückte Lagerhaus</strong></a>
				<button class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#navcol-1">
					<span class="sr-only">Toggle navigation</span><span
						class="icon-bar"></span><span class="icon-bar"></span><span
						class="icon-bar"></span>
				</button>
			</div>
			<div class="collapse navbar-collapse" id="navcol-1">
				<ul class="nav navbar-nav navbar-right"></ul>
			</div>
		</div>
	</nav>
	<h2 class="text-center">Datos Cliente</h2>
	<div class="div-datosCliente">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="info">Datos</th>
						<th class="success">Info</th>
					</tr>
				</thead>
				<tbody>
					<%
						{
							ClienteDTO c = (ClienteDTO) request.getAttribute("cliente");
					%>
					<tr>
						<td class="active">DNI</td>
						<td class="active"><%= c.getDni() %></td>
					</tr>
					<tr>
						<td class="active">Nombre</td>
						<td class="active"><%= c.getNombre() %></td>
					</tr>
					<tr>
						<td class="active">Razon social</td>
						<td class="active"><%= c.getRazonSocial() %></td>
					</tr>
					<tr>
						<td class="active">CUIT</td>
						<td class="active"><%= c.getCuit() %></td>
					</tr>
					<tr>
						<td class="active">Limite de Credito</td>
						<td class="active"><%= c.getLimiteCredito() %></td>
					</tr>
					<tr>
						<td class="active">Condiciones especiales de Pago</td>
						<td class="active"><%= c.getCondEspPago() %></td>
					</tr>
					<tr>
						<td class="active">Notas de advertencia</td>
						<td class="active"><%= c.getNotasAdv() %></td>
					</tr>
					<tr>
						<td class="active">Calle</td>
						<td class="active"><%= c.getCalleDom() %></td>
					</tr>
					<tr>
						<td class="active">Numero</td>
						<td class="active"><%= c.getNroDom() %></td>
					</tr>
					<tr>
						<td class="active">Localidad</td>
						<td class="active"><%= c.getLocalidadDom() %></td>
					</tr>
					<tr>
						<td class="active">Codigo Postal</td>
						<td class="active"><%= c.getCpDom()%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
	<h2 class="text-center">Pedidos Realizados</h2>
	<div class="div-pedidosRealizados">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="danger">NroPedido</th>
						<th class="success">Precio Total Bruto</th>
						<th class="success">Fecha Generacion</th>
						<th class="success">Fecha Entrega Esperada</th>
						<th class="warning">Estado</th>
						<th class="danger">Aclaracion</th>
						<th class="success">Detalle</th>

					</tr>
				</thead>
				<tbody>
					<tr>
						<%
							List<PedidoDTO> pedidos = (List<PedidoDTO>) request.getAttribute("pedidos");
							PedidoDTO pedido = null;

							if (pedidos != null) {
								for (Iterator<PedidoDTO> i = pedidos.iterator(); i.hasNext();) {
									pedido = i.next();
						%>
					<tr>
						<td id="idPedido"><%=pedido.getNroPedido()%></td>
						<td id="precioTotalBruto"><%=pedido.getPrecioTotalBruto()%></td>
						<td id="fechaGeneracion"><%=pedido.getFechaGeneracion()%></td>
						<td id="fechaEntregaEsperada"><%
							if(pedido.getFechaEntregaEsperada() == null)
								out.println("-");
							else out.println(pedido.getFechaEntregaEsperada());
						%></td>
						<td id="estado"><%=pedido.getEstado()%></td>
						<td id="aclaracion"><button class="btn btn-warning submit-button" type="button" onclick="abrirPopUp('<%=pedido.getAclaracion()%>')">Ver Aclaracion</button></td>
						<td id="verPedido">
							<button class="btn btn-warning submit-button" type="button" onclick="location.href='Inicio?action=showDetallePedido&pedido=<%=pedido.getNroPedido()%>&dni=<%=pedido.getCliente().getDni()%>'">Ver Pedido</button>
						</td>
					</tr>
					<%
						}
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
	<div role="dialog" tabindex="-1" class="modal fade"
		id="modalDescripcion">
		<div class="modal-dialog" role="document" style="margin-top: 200px">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">Aclaración</h4>
				</div>
				<div class="modal-body" style="height: 100px">
					<textarea id="message" style="width: 100%;height: 100%;" readonly></textarea>
				</div>
				<div class="modal-footer">
					<button class="btn btn-default" type="button" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	<form method="post" class="form-btn-nuevoPedido" action="Inicio?action=nuevoPedido&dni=<%=request.getParameter("usuario")%>">
		<button class="btn btn-warning" type="submit">Nuevo Pedido</button>
	</form>
	<form method="post" class="form-btn-nuevoPedido" action="Inicio?action=facturasCliente&dni=<%=request.getParameter("usuario")%>">
		<button class="btn btn-warning" type="submit">Pagar Facturas</button>
	</form>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		<script>
		function abrirPopUp(descripcion)
		{		
			$("#message").html(descripcion);
			$("#modalDescripcion").modal("show");		

		}
		
		$(document).ready(function() {
	        var Exception = $("#Exception").val();
	        if (Exception!=""){
	        	 alert(Exception);
		        }
	    });
	</script>
</body>

</html>