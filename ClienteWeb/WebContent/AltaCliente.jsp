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
		<div style="margin-right: 0; position: absolute; right: 5%; top: 10%;">
			<button class="btn btn-info" type="button"
				onclick="location.href='Inicio?action='">Log out</button>
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
	<h2 class="text-center">Alta Cliente</h2>
	<h6 class="text-center text-muted">Ingrese datos del Cliente</h6>
	<form class="form-horizontal custom-form" method="post" action="Inicio?action=altaCliente">
		<div class="form-group">
			<div class="col-md-12 col-sm-2">
				<label class="control-label" for="name-input-field">DNI:</label> <input
					class="form-control" type="number" name="dni" placeholder="DNI">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12">
				<label class="control-label" for="email-input-field">Nombre:
				</label> <input class="form-control" type="text" name="nombre"
					placeholder="Nombre">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12">
				<label class="control-label" for="pawssword-input-field">Razón
					Social</label> <input class="form-control" type="text" name="Razon Social"
					placeholder="Razon Social">
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-0 col-sm-12 label-column">
				<label class="control-label" for="name-input-field">CUIT: </label> <input
					class="form-control" type="number" name="CUIT" placeholder="CUIT">
			</div>
		</div>
		<div class="form-group">
			<div class="col-md-offset-0 col-sm-12 label-column">
				<label class="control-label">Límite de Crédito: </label> <input
					class="form-control" type="number" name="Limite"
					placeholder="Limite">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="name-input-field">Condiciones
					especiales de pago:</label> <input class="form-control" type="text"
					name="condesp" placeholder="Condiciones especiales de pago">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="email-input-field">Notas
					de advertencias: </label> <input class="form-control" type="text"
					name="notas" placeholder="Notas de Advertencias">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="email-input-field">Calle
					Domicilio: </label> <input class="form-control" type="text" name="calle"
					placeholder="Calle">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="email-input-field">Numero
					Domicilio: </label> <input class="form-control" type="number" name="numero"
					placeholder="Numero">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="email-input-field">Localidad
					Domicilio: </label> <input class="form-control" type="text"
					name="localidad" placeholder="Localidad">
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12 label-column">
				<label class="control-label" for="email-input-field">Cod.
					Postal: </label> <input class="form-control" type="number" name="cp"
					placeholder="Codigo Postal">
			</div>
		</div>
		<button class="btn btn-warning submit-button" type="submit">Crear Cliente</button>
	</form>
	<div id="atras">
		<button class="btn btn-info" type="button"
			onclick="location.href='Inicio?action=menuAdminClientes'">Atras
		</button>
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