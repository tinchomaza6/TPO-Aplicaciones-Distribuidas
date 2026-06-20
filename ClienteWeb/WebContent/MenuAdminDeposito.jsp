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
            <div class="navbar-header"><a class="navbar-brand navbar-link" href="#"><strong>Das Verrückte Lagerhaus</strong></a>
                <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
            </div>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav navbar-right"></ul>
            </div>
        </div>
    </nav>
    <h2 class="text-center">Manejo de Stock</h2>
    <div id="menuesLindos" style="width: 310px; margin: 75px auto;">
        <button class="btn btn-primary btn-lg btn-block" type="button" onclick="location.href='Inicio?action=reportarFalla'">Reportar por Falla</button>
        <button class="btn btn-primary btn-lg btn-block" type="button" onclick="location.href='Inicio?action=reportarInconsistencia'">Reportar Inconsistencia</button>
        <button class="btn btn-primary btn-lg btn-block" type="button" onclick="location.href='Inicio?action=verificarVencimientos'">Verificar Vencimientos</button>
        <button class="btn btn-primary btn-lg btn-block" type="button" onclick="location.href='Inicio?action=verMovimientos'">Ver Movimientos Stock</button>
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