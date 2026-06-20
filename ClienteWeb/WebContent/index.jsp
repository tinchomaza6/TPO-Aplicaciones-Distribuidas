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
        <div class="container-fluid">
            <div class="navbar-header"><a class="navbar-brand navbar-link" href="#"><strong>Das Verrückte Lagerhaus</strong></a>
                <button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
            </div>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav navbar-right"></ul>
            </div>
        </div>
    </nav>
    <h1 class="text-center index-head">Login </h1>
    <div id="login">
        <form method="post" action="Inicio?action=login"><span class="label label-default">Usuario </span>
            <input class="form-control" type="text" name="usuario">
            <hr><span class="label label-default">Contraseña </span>
            <div class="index-btnAceptar">
                <input class="form-control" type="password" name = "password">
                <button class="btn btn-default index-btn" type="submit">Aceptar </button>
            </div>
        </form>
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