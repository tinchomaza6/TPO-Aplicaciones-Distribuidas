<%@ page import="dto.MovimientoSimpleDTO"%>
<%@ page import="dto.MovimientoAjusteDTO"%>
<%@ page import="dto.MovimientoDañoDTO"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>VerMovimientos</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="assets/css/styles.css">
</head>
<body>
	<nav class="navbar navbar-default">
		<div style="margin-right: 0; position: absolute; right: 5%; top: 10%;">
			<button class="btn btn-info" type="button"
				onclick="location.href='Inicio?action='">Log out</button>
		</div>
		<div class="container-fluid">
			<div class="navbar-header">
				<a href="#" class="navbar-brand navbar-link"><strong>Das
						Verrückte Lagerhaus</strong></a>
				<button data-toggle="collapse" data-target="#navcol-1"
					class="navbar-toggle collapsed">
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
	<h2 class="text-center">Movimientos de Stock</h2>
	<h6 class="text-center text-muted">Detalles de Movimientos</h6>
	<div id="tablaVerMovimientos" style="width: 80%; margin: auto;">
		<div class="table-responsive">
			<table class="table">
				<thead>
					<tr>
						<th class="info" style="width: 140px">ID Movimiento</th>
						<th class="info">Fecha</th>
						<th class="info">Tipo</th>
						<th class="info" style="width: 130px">ID Articulo</th>
						<th class="info">Destino</th>
						<th class="info">Encargado</th>
						<th class="info">Autorizante</th>
						<th class="info">Descripcion</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<MovimientoSimpleDTO> movSimple = (List<MovimientoSimpleDTO>) request.getAttribute("movSimple");
						MovimientoSimpleDTO mov;
						if (movSimple != null) {
							for (Iterator<MovimientoSimpleDTO> i = movSimple.iterator(); i.hasNext();) {
								mov = i.next();
					%>
					<tr>
						<td class="active"><%=mov.getIdMov()%></td>
						<td class="active"><%=mov.getFecha()%></td>
						<td class="active"><%=mov.getTipoMovimiento().toUpperCase()%></td>
						<td class="active"><%=mov.getArticuloDTO().getIdArticulo()%></td>
						<td class="active">-</td>
						<td class="active">-</td>
						<td class="active">-</td>
						<td class="active">
							<button class="btn btn-default" type="button" data-toggle="modal"
								onclick="abrirPopUp('<%=mov.getDescripcion()%>')">Ver</button>
						</td>
					</tr>
					<%
						}
						}
						List<MovimientoAjusteDTO> movAjuste = (List<MovimientoAjusteDTO>) request.getAttribute("movAjuste");
						MovimientoAjusteDTO movA;
						if (movAjuste != null) {
							for (Iterator<MovimientoAjusteDTO> i = movAjuste.iterator(); i.hasNext();) {
								movA = i.next();
					%>
					<tr>
						<td class="active"><%=movA.getIdMov()%></td>
						<td class="active"><%=movA.getFecha()%></td>
						<td class="active"><%=movA.getTipoMovimiento().toUpperCase()%></td>
						<td class="active"><%=movA.getArticuloDTO().getIdArticulo()%></td>
						<td class="active">-</td>
						<td class="active"><%=movA.getEncargado()%></td>
						<td class="active">-</td>
						<td class="active">
							<button class="btn btn-default" type="button" data-toggle="modal"
								onclick="abrirPopUp('<%=movA.getDescripcion()%>')">Ver</button>
						</td>
					</tr>
					<%
						}
						}
						List<MovimientoDañoDTO> movDaño = (List<MovimientoDañoDTO>) request.getAttribute("movDaño");
						MovimientoDañoDTO movd;
						if (movDaño != null) {
							for (Iterator<MovimientoDañoDTO> i = movDaño.iterator(); i.hasNext();) {
								movd = i.next();
					%>
					<tr>
						<td class="active"><%=movd.getIdMov()%></td>
						<td class="active"><%=movd.getFecha()%></td>
						<td class="active"><%=movd.getTipoMovimiento().toUpperCase()%></td>
						<td class="active"><%=movd.getArticuloDTO().getIdArticulo()%></td>
						<td class="active"><%=movd.getDestino()%></td>
						<td class="active"><%=movd.getEncargado()%></td>
						<td class="active"><%=movd.getAutorizante()%></td>
						<td class="active">
							<button class="btn btn-default" type="button" data-toggle="modal" onclick="abrirPopUp('<%=movd.getDescripcion()%>')">Ver</button>
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
					<h4 class="modal-title">Descripcion</h4>
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
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script>
	function abrirPopUp(descripcion)
	{		
		$("#message").html(descripcion);
		$("#modalDescripcion").modal("show");		

	}
	</script>
</body>
</html>