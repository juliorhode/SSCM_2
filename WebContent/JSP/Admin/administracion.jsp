<%@ page import = "javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>Admin</title>
  	<meta name="viewport" content="width=device-width, initial-scale=1">
  	
  	<!--***********************************************-->
  	<!--******************* ESTILOS *******************-->
  	<!--***********************************************-->
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/jquery-timepicker.css">
	<link rel="stylesheet" href="css/jquery-ui.css">
	<link rel="stylesheet" href="css/Principal/plantilla.css">
	<link rel="stylesheet" href="css/jquery-confirm.min.css">
	
	<!--**************************************************-->
	<!--******************* JAVASCRIPT *******************-->
	<!--**************************************************--> 
  	<script src="js/jQuery_v3.3.1.js"></script>
  	<script src="js/Bootstrap_v3.3.7.js"></script>
  	<script src="js/jquery-timepicker.js"></script>
  	<script src="js/jquery-ui.js"></script>
  	<script src="js/administracion/administracion.js"></script>
  	<script src="js/jquery-confirm.min.js"></script>
  	
  	<script type="text/javascript">

		$(document).ready(function(){
			
			inicio();
		});
	</script>
	
	<style>
		#usuario_activo{
			color:white;
		   	width:auto;
		   	font-weight: bold;
		}
		.well p>a{color: #335f7d;}
		
		@media screen and (max-width: 767px) {
			#usuario_activo{
			   	margin-top:10px; 
			   	margin-left:10px;
			   	font-weight: bold;
			}
		}
	</style>

</head>

<body>
<% HttpSession sesion = request.getSession();%>


	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
	    	<div class="navbar-header">
	      		<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
	        		<span class="icon-bar"></span>
	        		<span class="icon-bar"></span>
	        		<span class="icon-bar"></span>                        
	      		</button>
	      		<img src="imagenes/logo.png" style="width: 150px;">
	    	</div>
	    	<div class="collapse navbar-collapse" id="myNavbar">
				<div class="flex-container">
	 	 			<div><h2 ><span class="label ">Gerencia de Seguridad y Salud en el Trabajo</span></h2></div>
	 	 			
	 	 			<ul class="nav navbar-nav navbar-right">
	        			<li><h5 id="usuario_activo"> Usuario: <%=sesion.getAttribute("usuario") %></h5></li>
	      			</ul>
	      			<input type="hidden" id="usuario" value="<%=sesion.getAttribute("usuario") %>">
	      			
	  				<div><h3 ><span class="label ">Sistema de Citas Médicas en Linea</span></h3></div>
				</div>
	      		<ul class="nav navbar-nav navbar-right">
	        		<li><a href="ServletConexion" style="color:white" style="width:auto;"><span style="color:white" class="glyphicon glyphicon-log-in"></span> Empleado</a></li>
	      		</ul>
	    	</div>
	  	</div>
	</nav>
	<div class="container-fluid text-center">    
	  	<div class="row content" id="laterales">
	    	<div class="col-sm-2 sidenav">
	      		<div class="well">
		      		<style>
		      			p:hover {
							cursor:pointer;
						}
					</style>
		      		<p data-toggle="collapse" href="#registro">Registro de Citas</p>
					<div id="registro" class="panel-collapse collapse">
						<ul class="list-group">
							<!-- <li class="list-group-item" ><a href="#;" id="programadas" >Programadas</a></li>  -->
							<!-- <li class="list-group-item" ><a href="#;" id="cortesias" >Cortesias</a></li>  -->
							<li class="list-group-item" ><a href="#;" id="emergencias" >Emergencias</a></li>
						</ul>
					</div>
		      	</div>
		      	
		      	<div class="well">
		      		<p data-toggle="collapse" href="#param">Registro de Horario</p>
					<div id="param" class="panel-collapse collapse">
						<ul class="list-group">
							<li class="list-group-item" ><a href="#;" id="cargaUnica" >Carga Unica</a></li>
							<!-- <li class="list-group-item" ><a href="#;" id="cargaIndividual" >Carga Individual</a></li> -->
							<!-- <li class="list-group-item" ><a href="#;" id="eliminaHorarios" >Horarios</a></li> -->
						</ul>
					</div>
		      	</div>
		      	
		      	<!-- <div class="well">
		      		 <p><a href="#;" id="ausentismo" >Registro de Ausentismo</a></p> 
		      	</div> -->
		      	
		      	<div class="well">
		      		<p data-toggle="collapse" href="#agenda">Agenda Médica</p>
					<div id="agenda" class="panel-collapse collapse">
						<ul class="list-group">
							<li class="list-group-item" ><a href="#;" id="listadoProgramadas" >Listado</a></li>
							<div class="well">
		      					<p data-toggle="collapse" href="#reporte">Reportes</p>
								<div id="reporte" class="panel-collapse collapse">
									<ul class="list-group">
										<li class="list-group-item" ><a href="#;" id="imprimeReporte" >Impresión</a></li>
									</ul>
								</div>
		      				</div>
							<!-- <li class="list-group-item" ><a href="#;" id="cargaIndividual" >Carga Individual</a></li> -->
							<!-- <li class="list-group-item" ><a href="#;" id="eliminaHorarios" >Horarios</a></li> -->
						</ul>
					</div>
		      	</div>
	    	</div>
	    	<!-- Se carga con respecto a la seleccion del menú izquierdo -->
	    	<div class="col-sm-10 text-left" id="cuerpo"></div>
	    	
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
</body>
</html>
