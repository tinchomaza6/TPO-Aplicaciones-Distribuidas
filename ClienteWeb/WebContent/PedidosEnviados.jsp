<%@ page import="dto.PedidoDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Distribuidas-</title>
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
				<a class="navbar-brand navbar-link" href="#"><strong>Das
						Verrückte Lagerhaus</strong></a>
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
	<h2 class="text-center">Pedidos aprobados en espera de envío</h2>
	<div class="div-pedidosRealizados">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="danger">Nro Pedido</th>
						<th class="active">Cliente</th>
						<th class="active">Destino</th>
						<th class="active">Accion</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<PedidoDTO> pedidos = (List<PedidoDTO>) request.getAttribute("pedidos");
						PedidoDTO pedido = null;

						if (pedidos != null) {
							for (Iterator<PedidoDTO> i = pedidos.iterator(); i.hasNext();) {
								pedido = i.next();
					%>
					<tr>
						<td class="active"><%= pedido.getNroPedido() %></td>
						<td class="active"><%= pedido.getCliente().getDni() %></td>
						<% 
							String direccion = "Calle: " + pedido.getCalleDireccEnvio() + " - Numero: " + pedido.getNroDireccEnvio() + " - Localidad: " + pedido.getLocalidadDireccEnvio() + " - Cod. Postal: " + pedido.getCpDirecEnvio();
						%>
						<td class="active"><%= direccion %></td>
						<td class="active"><button class="btn btn-success btn-xs" type="button" onclick="location.href='Inicio?action=enviarPedido&idPedido=<%= pedido.getNroPedido() %>'">Enviar</button></td>
					</tr>
					<%
						}
						}
					%>
				</tbody>
			</table>
		</div>
	</div>
	<div id="atras">
   		<button class="btn btn-info" type="button" onclick="location.href='Inicio?action=menuAdminDespacho'">Atras </button>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
		<script>

	$(document).ready(function() {
        var Exception = $("#Exception").val();
        if (Exception!=""){
        	 alert(Exception);
	        }
    });
	</script>
</body>

</html>