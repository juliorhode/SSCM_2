<script src="js/administracion/listadoCitas_adm.js"></script>
<link rel="stylesheet" href="css/cita_medica/general.css">
<link rel="stylesheet" href="css/cita_medica/historialCitasAdmin.css">

<script type="text/javascript">
$(document).ready(function(){
	inicial();
});
</script>


								
<div class="container">
  	
  <div class="col-sm-5" id="panelParametrosTipo">
	<div class="panel-group">
		<div class="panel panel-default">
		  <div class="panel-heading">
			<h4 class="panel-title">
			  <a>Tipo de Cita</a>
			</h4>
		  </div>
		  
			<div class="panel-body" >
				<div class="radio">
					<div class="col-sm-4">
						<label><input type="radio" name="otpTipoCita" value="M">Médicas</label>
						<label><input type="radio" name="otpTipoCita" value="O">Odontológicas</label>
					</div><!-- Fin  col-sm-6 -->
					<div class="col-sm-4">
						<label><input type="radio" name="otpTipoCita" value="N">Nutrición</label>
						<label><input type="radio" name="otpTipoCita" value="F">Fisioterapia</label>
					</div><!-- Fin  col-sm-6 -->
  					<div class="col-sm-4">
						<label><input type="radio" name="otpTipoCita" value="E">Emergencias</label>
						<label><input type="radio" name="otpTipoCita" value="T">Todas</label>
					</div><!-- Fin  col-sm-6 -->
					
					
				</div><!-- Fin  class="radio" -->
			</div><!-- Fin  panel-body -->
  
		</div><!-- Fin  panel-default -->
 	</div><!-- Fin  panel-group -->
  </div><!-- Fin  col-sm-4 -->

  <div class="col-sm-3" id="panelParametrosEspecialista-Fecha" >
	<div class="panel-group">
		<div class="panel panel-default">
		  <div class="panel-heading">
			<h4 class="panel-title">
			  <a>Fecha</a>
			</h4>
		  </div>
		  
		  <div class="panel-body"  style="height: 80px">
			<input type="text" name="FechaSolicitud" class="btn btn-default" id="FechaSolicitud" placeholder="Fecha" style="width: 150px;">
		  </div>
   			
		</div>
 	</div>
  </div>

  <div class="col-sm-3" id="panelParametros-busqueda" >
	<div class="panel-group">
		<div class="panel panel-default">
		  <div class="panel-heading">
			<h4 class="panel-title">
			  <a>Busqueda Progresiva</a>
			</h4>
		  </div>
		  
			<div class="panel-body"  style="height: 80px">
				<div class="col-sm-12">
					<input type="text" name="txtbusqueda"  class="form-control" id="txtbusqueda" placeholder="Introduzca valor a buscar">
				</div>
			</div>
  
		</div>
 	</div>
  </div>
  
 <div class="col-sm-12" id="Contenedor-Panel" >
		<div class="panel-group">
			<div class="panel-body" >
				<div class="col-sm-12" id="historialCitas">
					<!-- Los resultados vienen de Ajax -->
					<table class='table table-bordered table-striped' >
					<thead >
						<tr>
							<th>Solicitud</th>
							<th>Status</th>
							<th>CI. Emp.</th>
							<th>Nombre Empleado</th>
							<th>CI. Fam.</th>
							<th>Nombre Familiar</th>
							<th>Celular</th>
							<th>Ext.</th>
							<th>Fecha Cita</th>
							<th>Hora Cita</th>
							<th>Medico</th>
						</tr>
					</thead>
					<tbody id = "listadoCitas">
						
					</tbody>
				</table><!-- Fin table -->
				</div>
			</div>
 		</div>
  </div>
	
</div>
<style>

/*Iconos del datepicker*/
.ui-icon,.ui-widget-content .ui-icon {background-image: url("css/ui-icons_444444_256x240.png");}
.ui-widget-header .ui-icon {background-image: url("css/ui-icons_444444_256x240.png");}

/*div principal del datepicker*/
.ui-datepicker{background: #335f7d;height: 260px;}

/*La cabecera*/
.ui-datepicker .ui-datepicker-header {font-size: 12px;background: #FFFFFF;font-weight: bold;}

/*Para los días de la semana: Sa Mo ... */
.ui-datepicker th{color: #FFFFFF;font-weight: bold;}

/*Para el item del día del mes seleccionado */
/*.ui-datepicker .ui-state-active{background: orange;color: #FFFFFF;}*/

@media only screen and (max-width: 1367px) and (max-height: 769px) {
	/*div principal del datepicker*/
	.ui-datepicker{background: #335f7d;height: 210px;}
}

</style>