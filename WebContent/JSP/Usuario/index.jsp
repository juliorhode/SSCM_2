<!DOCTYPE html>
<html lang="en">
<head>
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	
  	<!--  
  	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Last-Modified" content="0">
	<meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
	<meta http-equiv="Pragma" content="no-cache">
  	-->
  	<!--***********************************************-->
  	<!--******************* ESTILOS *******************-->
  	<!--***********************************************-->
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/jquery-timepicker.css">
	<link rel="stylesheet" href="css/jquery-ui.css">
	<link rel="stylesheet" href="css/Principal/plantilla.css">
	<link rel="stylesheet" href="css/login/login.css">
	<link rel="stylesheet" href="css/jquery-confirm.min.css">
	
	<!--**************************************************-->
	<!--******************* JAVASCRIPT *******************-->
	<!--**************************************************--> 
  	<script src="js/jQuery_v3.3.1.js"></script>
  	<script src="js/Bootstrap_v3.3.7.js"></script>
  	<script src="js/jquery-timepicker.js"></script>
  	<script src="js/jquery-ui.js"></script>
  	<script src="js/login/login.js"></script>
  	<script src="js/personal/principal.js"></script>
  	<script src="js/jquery-confirm.min.js"></script>
  	
<script type="text/javascript">

	$(document).ready(function(){
		
		inicial();
	});
</script>	
	
<title>Cita Medica</title>  	
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
	    	<div class="navbar-header">
	      		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	        		<span class="icon-bar"></span>
	        		<span class="icon-bar"></span>
	        		<span class="icon-bar"></span>                        
	      		</button>
	      		<img id="logo-header" src="imagenes/logo.png" style="width: 150px;">
	    	</div>
	    	<div class="collapse navbar-collapse" id="myNavbar">
				<div class="flex-container">
	 	 			<div><h2 ><span class="label ">Gerencia de Seguridad y Salud en el Trabajo</span></h2></div>
	  				<div><h3 ><span class="label ">Sistema de Citas Médicas en Linea</span></h3></div>
				</div>
	      		<ul class="nav navbar-nav navbar-right">
	      			<li><a href="#" id="admin" style="color:white" style="width:auto;"><span style="color:white" class="glyphicon glyphicon-log-in"></span> Administrador</a></li>
	      		</ul>
	    	</div>
	  	</div>
	</nav>
	<div class="container-fluid text-center">    
	  	<div class="row content" id="laterales">
	    	<div class="col-sm-2 sidenav">
	    		<div class="well">
		        	<p><a href="#" id="SolicitudCita" >Solicitud de Cita</a></p>
		      	</div>
	    		<div class="well" id="listado_cita">
		        	<p><a href="#" id="listadoCita">Agenda de Citas</a></p>
		      	</div>	      		
	      		
	    	</div>
	    	
	    	<div class="col-sm-8 text-left" id="cuerpo"></div>
	    	
	    	<div class="col-sm-2 sidenav" id="lateral_d">
		      	<div class="well" id= "lateral_der"></div>
	    	</div>
	    	
	  	</div>
	</div>
	<footer class="container-fluid text-center">
		<div class="col-sm-12" >
		  	<div class="col-sm-6" align="center">
				<p>Copyright 2018 - Todos Los Derechos Reservados</p>	
			</div>
			<div class="col-sm-6" align="center">
				<p>Banco Central de Venezuela</p>	
			</div>
		</div>
	</footer>	

	<!--****************************************************************-->
  	<!--******************* Ventana Modal para Login *******************-->
  	<!--****************************************************************-->
	<div class="modal fade" id="modalAdmin" data-keyboard="false" data-backdrop="static">
	    <div class="modal-dialog">
	    	
       		<form class="modal-contenido" action="ServletConexionAdmin" method="post">
       			<button type="button" class="close" data-dismiss="modal">&times;</button>
	            <div class="imagen" align="center">
	        		<img src="imagenes/logo.png" alt="logo" class="logo">
	        	</div>
	            <div class="contenedor" id="contenedor-text">
		      		<input type="text"  placeholder="Usuario" name="usuario" id="usuario" required>
		      		<input type="password" placeholder="Contraseña" name="contraseña" required>
		    	</div>
		    	<div class="contenedor-color" id="contenedor-btn">
		    		<button type="submit" class="aceptabtn">Ingresar</button>
		    	</div>
            </form>
	    </div>
	</div>	
	<!--*****************************************************************-->
  	<!--******************* Ventana Modal para cedula *******************-->
  	<!--*****************************************************************-->
	<div class="modal fade" id="modalUsuario" data-keyboard="false" data-backdrop="static" >
	    <div class="modal-dialog">
	        <div class="modal-content" >
	    
	            <!-- Encabezado del Modal -->
	            <div class="modal-header" align="center">
	                <h3 class="modal-title" style="color:#1B0082;">Usuario para la Solicitud</h3>
	            </div>
	    
	            <!-- Cuerpo del Modal -->
	            <div class="modal-body" id="cuerpoModal">
	                <div class="form-group">
		    			<label for="cedula_usuario" >Cédula del Empleado:</label>
		    			<input type="text" id="cedula_usuario" autofocus class="form-control" name="cedula_usuario" style="width: 300px;">
					</div>
	            </div>
	            <!-- Pie del Modal -->
	            <div class="modal-footer">
	                <input type="button" class="btn btn-primary" value="Aceptar" id="enviaCedula" style="width: 100px;margin-left:360px;" data-dismiss="modal">
	            </div>
	        </div>
	    </div>
	</div>
	<div class="modal fade" id="mensajeAnulacion" data-keyboard="false" data-backdrop="static">
	    <div class="modal-dialog">
	        <div class="modal-content">
	    
	            <!-- Encabezado del Modal -->
	            <div class="modal-header">
	                <h3 class="modal-title" style="color:#1B0082;text-align: center;font-size: 24px;">Anulación de Cita Médica</h3>
	            </div>
	    
	            <!-- Cuerpo del Modal -->
	            <div class="modal-body" id="cuerpoModal">
	                <h4 style="color:#ff0000;text-align: center;font-size: 22px;">Su Cita ha sido anulada con éxito</h4>
	            </div>
	            <!-- Pie del Modal -->
	            <div class="modal-footer">
	                <input type="button" class="btn btn-primary" value="Aceptar" id="aceptarFalse" style="width: 100px;margin-left:360px;" data-dismiss="modal">
	            </div>
	        </div>
	    </div>
	</div>

</body>
</html>
