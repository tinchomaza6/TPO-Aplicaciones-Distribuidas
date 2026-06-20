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
	<title>DistribuidasFinal</title>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
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
	<h2 class="text-center">Pedidos</h2>
	<div class="div-pedidosRealizados">
		<div class="table-responsive">
			<fieldset>
				<table class="table">
					<thead>
						<tr>
							<th class="danger">NroPedido</th>
							<th class="success">Cliente Dni</th>
							<th class="success">Cliente Nombre</th>
							<th class="success">Fecha Generacion</th>
							<th class="warning">Limite Credito</th>
							<th class="warning">Total Pedido</th>
							<th class="danger">Situacion</th>
							<th class="info">Aprobar/Rechazar</th>
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
							<td><%=pedido.getNroPedido()%></td>
							<td id="clienteDni"><%=pedido.getClienteDTO().getDni()%></td>
							<td id="clienteNombre"><%=pedido.getClienteDTO().getNombre()%></td>
							<td id="fechaGeneracion"><%=pedido.getFechaGeneracion()%></td>
							<td id="limiteCredito"><%=pedido.getClienteDTO().getLimiteCredito()%></td>
							<td id="totalPedido"><%=pedido.getPrecioTotalFinal()%></td>
							<td id="situacion" style="font-weight: bold;">
							<%
								if((pedido.getCliente().getLimiteCredito() + pedido.getCliente().getCuentaCorriente().getSaldo()) < pedido.getPrecioTotalFinal())
									out.println("SALDO INSUFICIENTE");
								else out.println("SALDO OK");
							%>
							</td>
							<td class="active">
								<button class="btn btn-warning" type="button" onclick="adminPedido('Aprobar', '<%=pedido.getNroPedido()%>')">Aprobar</button>
								<button class="btn btn-success" type="button" onclick="adminPedido('Rechazar', '<%=pedido.getNroPedido()%>')">Rechazar</button>
							</td>							
						</tr>
						<%
							}
							}
						%>
					</tbody>
				</table>
			</fieldset>
		</div>
	</div>
	<form method="post" action="Inicio?action=pedidoAdmin">	
		 <div role="dialog" tabindex="-1" class="modal fade" id="mymodal">
		    <div class="modal-dialog" role="document">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
		                <h4 class="modal-title">Aclaracion Aprobado/Rechazado</h4></div>
		            <div class="modal-body">		               
		               	<textarea cols="65" rows="10" class="form-control" name="aclaracion"></textarea>
		               	<input type="hidden" name="tipo" id="tipo_admin">
		               	<input type="hidden" name="idPedido" id="nro_pedido">		               
		            </div>
		            <div class="modal-footer">
		                <div id="modal-footer-adminCliente">
		                    <button class="btn btn-default" type="button" data-dismiss="modal">Close</button>
		                    <input type="submit" class="btn btn-primary" value="Guardar">
		                </div>
		            </div>
		        </div>
		    </div>
		</div>	
	</form>
	<div id="atras">
   		<button class="btn btn-info" type="button" onclick="location.href='Inicio?action=menuAdminClientes'">Atras </button>
	</div>	
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script type="text/javascript">
		function adminPedido(tipo, idPedido){
			document.getElementById("tipo_admin").value = tipo;
			document.getElementById("nro_pedido").value = idPedido;
			$("#mymodal").modal("show");		
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