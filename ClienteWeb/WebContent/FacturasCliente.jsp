<%@page import="delegado.BusinessDelegate"%>
<%@ page import="dto.ClienteDTO"%>
<%@ page import="dto.FacturaDTO"%>
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
	<h2 class="text-center">Facturas pendientes de pago</h2>
	<form class="form-btn-nuevoPedido" method="post">
		<%
			ClienteDTO c = BusinessDelegate.getInstancia().buscarClienteByDni(Integer.parseInt(request.getParameter("dni")));
		%>
		<div id="cobranza">
			<h4>
				<span class="label label-default">Dni: <%=c.getDni()%></span>
			</h4>
		</div>
	</form>
	<form class="form-btn-nuevoPedido" method="post">
		<div id="cobranza">
			<h4>
				<span class="label label-default">Saldo actual: <%=c.getCuentaCorriente().getSaldo()%></span>
			</h4>
		</div>
		<div id="cobranza">
			<span class="label label-default"><font size="3">Agregar Saldo</font></span> 
			<input type="number" name="saldo" id="saldo" placeholder="0"/>
			<button class="btn btn-warning" type="button" onclick="agregarSaldo()">Agregar</button>
		</div>
	</form>
	<div class="div-pedidosRealizados">
	<div class="row">
		<div class="col-md-12">
			<div class="pull-right">
				<button class="btn btn-success" id=btn1 type="button" onclick="location.href='Inicio?action=pagarTodasFacturas&dni=<%=c.getDni()%>'">Pagar todas</button>
			</div>
		</div>
	</div>
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="danger">NroFactura</th>
						<th class="active">Fecha Factura</th>
						<th class="active">Valor Total a Pagar</th>
						<th class="active">Accion</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<FacturaDTO> facturas = (List<FacturaDTO>) request.getAttribute("facturas");
						FacturaDTO fac;
						if (facturas != null) {
							for (Iterator<FacturaDTO> i = facturas.iterator(); i.hasNext();) {
								fac = i.next();
					%>
					<tr>
						<td class="active"><%=fac.getNroFactura()%></td>
						<td class="active"><%=fac.getFecha()%></td>
						<td class="active"><%=fac.getTotalFact()%></td>
						<td class="active">
							<button class="btn btn-success btn-xs" type="button" onclick="location.href='Inicio?action=pagarFactura&idFactura=<%=fac.getNroFactura()%>&dni=<%=c.getDni()%>'">Pagar</button>
						</td>
					</tr>
					<%
						}
						}
					%>
				</tbody>
			</table>
		</div>
	</div>s
	<div id="atras">
   		<center><button class="btn btn-info" type="button" onclick="location.href='Inicio?action=pantallaCliente&usuario=<%=c.getDni()%>'">Atras </button></center>
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
    
	
		function agregarSaldo() {
			var s = $("#saldo").val();
			location.href='Inicio?action=agregarSaldo&dni=<%=c.getDni()%>&saldo=' + s;
	
		}
	</script>
</body>
</html>