<%@page import="dto.ClienteDTO"%>
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
    <nav class="navbar navbar-default">
    	<div style="margin-right: 0;position: absolute;right: 5%;top: 10%;">
   			<button class="btn btn-info" type="button" onclick="location.href='Inicio?action='">Log out</button>
		</div>
        <div class="container-fluid">
            <div class="navbar-header"><a class="navbar-brand navbar-link" href="#"><strong>Das Verrückte Lagerhaus</strong></a>
                <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
            </div>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav navbar-right"></ul>
            </div>
        </div>
    </nav>
    <%
   		ClienteDTO c = (ClienteDTO) request.getAttribute("cliente");
    %>
    <h2 class="text-center">Modificar Cliente</h2>
    <h6 class="text-center text-muted">Modifique los datos necesarios</h6>
    <form method="post" action="Inicio?action=modificacionCliente">
        <div>
            <label>DNI </label>
            <input class="form-control" type="number" name="dni" value=<%= c.getDni() %> readonly>
        </div>
        <div>
            <label>Nombre </label>
            <input class="form-control" type="text" name="nombre" placeholder="Nombre Apellido" value=<%= c.getNombre() %>>
        </div>
        <div>
            <label>Razón social </label>
            <input class="form-control" type="text" name="razonSocial" placeholder="Razon social" value=<%= c.getRazonSocial() %>>
        </div>
        <div>
            <label>CUIT </label>
            <input class="form-control" type="number" name="cuit" placeholder="cuit" value=<%= c.getCuit() %> readonly>
        </div>
        <div>
            <label>Límite de Crédito</label>
            <input class="form-control" type="number" name="limite" placeholder="Limite de Credito" value=<%= c.getLimiteCredito() %>>
        </div>
        <div>
            <label>Condiciones especiales de Pago </label>
            <input class="form-control" type="text" name="condicion" placeholder="Condicion especial de pago" value=<%= c.getCondEspPago() %>>
        </div>
        <div>
            <label>Notas de advertencia </label>
            <input class="form-control" type="text" name="notas" placeholder="Notas" value=<%= c.getNotasAdv() %>>
        </div>
        <div>
            <label>Calle Direccion </label>
            <input class="form-control" type="text" name="calle" placeholder="Calle" value= <%= c.getCalleDom() %>>
        </div>
        <div>
            <label>Numero Direccion </label>
            <input class="form-control" type="number" name="numero" placeholder="Numero" value=<%= c.getNroDom() %>>
        </div>
        <div>
            <label>Localidad Direccion </label>
            <input class="form-control" type="text" name="localidad" placeholder="Localidad" value=<%= c.getLocalidadDom() %>>
        </div>
        <div>
            <label>Codigo Postal direccion </label>
            <input class="form-control" type="number" name="cp" placeholder="Codigo postal" value=<%= c.getCpDom() %>>
        </div>
	    <div>
	        <button class="btn btn-warning" type="submit">Aceptar</button>
	    </div>
    </form>
    <div id="atras">
   		<button class="btn btn-info" type="button" onclick="location.href='Inicio?action=modificarCliente'">Atras </button>
	</div>	
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>