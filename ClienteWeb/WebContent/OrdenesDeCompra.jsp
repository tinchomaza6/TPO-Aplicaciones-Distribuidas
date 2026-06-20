<%@page import="dto.OrdenDeCompraDTO"%>
<%@ page import="dto.OrdenDePedidoDTO"%>
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
				<a class="navbar-brand navbar-link" href="#"><strong>DasVerrückte Lagerhaus</strong></a>
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
	<h2 class="text-center">Ordenes de Compra</h2>
	<div class="div-pedidosRealizados">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="danger">Nro Orden de Compra</th>
						<th class="danger">Nro Orden de pedido</th>
						<th class="success">Accion</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<OrdenDeCompraDTO> ordenes = (List<OrdenDeCompraDTO>) request.getAttribute("pedidos");
						OrdenDeCompraDTO oc = null;
						if (ordenes != null) {
							for (Iterator<OrdenDeCompraDTO> i = ordenes.iterator(); i.hasNext();) {
								oc = i.next();
					%>
					<tr>
						<td class="active"><%=oc.getIdOc()%></td>
						<td class="active"><%=oc.getOrdenPedido().getIdOp()%></td>
						<td class="active">
							<button class="btn btn-warning submit-button" type="button" onclick="location.href='Inicio?action=procesarOC&orden=<%=oc.getIdOc()%>'">Procesar Orden de compra</button>
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
	<form method="post" action="Inicio?action=menuCompras">
		<center><button class="btn btn-warning submit-button" type="submit">Volver</button></center>
	</form>
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