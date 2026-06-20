<%@page import="dto.ItemOrdenDePedidoDTO"%>
<%@page import="dto.OrdenDePedidoDTO"%>
<%@page import="delegado.BusinessDelegate"%>
<%@page import="dto.ItemPedidoDTO"%>
<%@page import="dto.ProveedorDTO"%>
<%@ page import="dto.PedidoDTO"%>
<%@ page import="java.util.*"%>
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
	<h2 class="text-center">Detalle Orden de Pedido</h2>
	<h6 class="text-center text-muted">Datos de la orden de pedido</h6>
	<div class="div-pedidosRealizados">
		<div class="table-responsive">
		<form method="post" action="Inicio?action=crearOrdenesDeCompra&idOP=<%=Integer.parseInt(request.getParameter("orden"))%>">
			<table class="table">
				<thead>
					<tr>
						<th class="success">Articulo ID</th>
						<th class="success">Articulo Nombre</th>
						<th class="success">Cantidad</th>
						<th class="success">Proveedores</th>
					</tr>
				</thead>
				<tbody>
					<%
					OrdenDePedidoDTO op = BusinessDelegate.getInstancia().buscarOPById(Integer.parseInt(request.getParameter("orden")));
						ItemOrdenDePedidoDTO its;
						if (op != null && op.getArticulos() != null) {
							for (Iterator<ItemOrdenDePedidoDTO> it = op.getArticulos().iterator(); it.hasNext();) {
								its = it.next();
								List<ProveedorDTO> provs = BusinessDelegate.getInstancia().buscarProveedores(its.getArticulo());
					%>
					<tr>
						<td class="active"><%=its.getArticulo().getIdArticulo()%><input type="hidden" name="articulos" value="<%=its.getArticulo().getIdArticulo()%>"></td>
						<td class="active"><%=its.getArticulo().getNombre()%></td>
						<td class="active"><%=its.getCant()%></td>
						<td class="active">
							<select class="form-control" name="proveedores">
							<%
								for (ProveedorDTO p : provs){
									int id = p.getCuit();
							%>
							<option value=<%=id%>>
							<%
								out.print(p.getNombre());
							%>
								</option>
							<%
								}
							%>
							</select>
						</td>
					</tr>
				</tbody>
				<%
							}
					}
				%>
			</table>
			<center><button class="btn btn-warning submit-button" type="submit">Crear Ordenes de Compra</button></center>
		</form>
			<center style="margin: 20px"><button class="btn btn-warning submit-button" type="button" onclick="location.href='Inicio?action=showOP'">Atras</button></center>
		</div>
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