<%@page import="delegado.BusinessDelegate"%>
<%@ page import="dto.ArticuloDTO"%>
<%@ page import="dto.ArticuloDepositoDTO"%>
<%@ page import="dto.ItemPedidoDTO"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
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
<style>
	#myTable{
		padding-right:0px !important;
	}
</style>
</head>
<body>
 	<input id="alertName" type="hidden" name ="alertName" value="<%=request.getAttribute("pedidoCompleto")%>"/>
 	<input id="Exception" type="hidden" name ="Exception" value="<%=request.getAttribute("excepcion")%>"/>
	<nav class="navbar navbar-default">
	<div style="margin-right: 0;position: absolute;right: 5%;top: 10%;">
   			<button class="btn btn-info" type="button" onclick="location.href='Inicio?action='">Log out</button>
		</div>
    <div class="container-fluid">
        <div class="navbar-header"><a href="#" class="navbar-brand navbar-link"><strong>Das Verrückte Lagerhaus</strong></a>
            <button data-toggle="collapse" data-target="#navcol-1" class="navbar-toggle collapsed"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
        </div>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="nav navbar-nav navbar-right"></ul>
        </div>
    </div>
	</nav>
	<h2 class="text-center">Nuevo Pedido</h2>
			<form class="bootstrap-form-with-validation" method="post" action="Inicio?action=guardarPedido&dni=<%=request.getParameter("dni") %>">
			<div id="nuevoPedido">
	 
	    	<span class="label label-warning" id="labelNuevoPedido">Calle </span>
	        <div class="form-group">
	            <input type="text" name="calle" placeholder="Ej: Irigoyen" class="form-control" id="text-input" />
	        </div>
	        <span class="label label-warning" id="labelNuevoPedido">Numero </span>
	        <div class="form-group">
	            <input type="number" name="numero" placeholder="Ej: 123" class="form-control" />
	        </div>
	        <span class="label label-warning" id="labelNuevoPedido">Localidad </span>
	        <div class="form-group">
	            <input type="text" name="localidad" placeholder="Ej: Palermo, Capital Federal" class="form-control" id="text-input" />
	        </div>
	        <span class="label label-warning" id="labelNuevoPedido">Codigo Postal</span>
	        <div class="form-group">
	            <input type="number" name="cp" placeholder="Ej: 123" class="form-control" />
	        </div>
	        <span class="label label-warning" id="labelNuevoPedido">Medio de Pago</span>
	        <div class="form-group">
	            <select class="form-control" name="pago" id="exampleSelect1">
	                <option>EFECTIVO</option>
	                <option>CREDITO</option>
	                <option>DEBITO</option>
	                <option>TRANSFERENCIA</option>
	            </select>
	        </div>

	</div>	
	
	<div class="div-pedidosRealizados">
		<fieldset>
			<%
				List<ArticuloDTO> articulos = BusinessDelegate.getInstancia().cargarArticulos();
				ArticuloDTO art;
			%>
			<div class="row">
				<div class="col-md-12">
					<div class="pull-right">
						<button class="btn btn-default" id=btn1 type="button" onclick="agregarFila()">Agregar Item</button>
						<button class="btn btn-default" id=btn2 type="button" onclick="eliminarFila()">Eliminar Ultimo Item</button>
					</div>
				</div>
			</div>

				<input type="hidden" name="cliente" value="<%=request.getParameter("dni")%>">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-hover" id="myTable">
							<tr>
								<th class="danger">Articulo</th>
								<th class="success">Cantidad</th>
							</tr>
							<tr>
								<td>
								<select class="form-control" name="articulos">
									<%
										for (Iterator<ArticuloDTO> i = articulos.iterator(); i.hasNext();) {
											art = i.next();
											int valor = art.getIdArticulo();
									%>
									<option value=<%=valor%>>
										<%
											out.print(art.getNombre() + " $" + art.getPrecioVentaUnitario());
										%>
									</option>
									<%
										}
									%>
								</select>
								</td>
								<td><input class="form-control" type="number" name="cantidades" placeholder="0"></td>
							</tr>
						</table>
					</div>			
				</div>	
				<div class="row">
					<div class="col-md-12 text-center">
						<input type="submit" id="btn-alta-pedido" class="btn btn-default" value="Alta Pedido">
					</div>
				</div>					
			</form>
		</fieldset>
	</div>
	<div id="atras">
   		<button class="btn btn-info" type="button" onclick="location.href='Inicio?action=pantallaCliente&&usuario=<%=request.getParameter("dni") %>'">Atras </button>
	</div>			
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script>
		$(document).ready(function() {
	        var alertName = $("#alertName").val();
	        var Exception = $("#Exception").val();
	        if (alertName==1){
	        	 alert("Pedido cargado en espera de confirmacion.");
		        }
	        if (Exception!=""){
	        	 alert(Exception);
		        }
	    });
	    
		function agregarFila() {			
			document.getElementById("myTable").insertRow(-1).innerHTML = '<td><select class="form-control" name="articulos"><%for (Iterator<ArticuloDTO> i = articulos.iterator(); i.hasNext();) {art = i.next(); int valor = art.getIdArticulo();%> <option value=<%=valor%>> <% out.print(art.getNombre() + " $" + art.getPrecioVentaUnitario()); %> </option> <% } %> </select></td> <td><input type="number" name="cantidades" class="form-control" placeholder="0"></td>';
		}

		function eliminarFila(){
				var table = document.getElementById("myTable");
				if (table.rows.length > 2) {
					table.deleteRow(table.rows.length -1);
				}
			}
		</script>	
</body>
</html>