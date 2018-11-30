<!--***********************************************-->
<!--******************* ESTILOS *******************-->
<!--***********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">
<link rel="stylesheet" href="css/cita_medica/paramHorario.css">

<!--**************************************************-->
<!--******************* JAVASCRIPT *******************-->
<!--**************************************************-->
<script src="js/paramHorario.js"></script>

<script>
	$(document).ready(function(){
		
		timePicker();
		
	});
	
</script>

<div class="container-fluid" >
	<div class="row content" id="contenido" >
		<div class="col-sm-11" id="panelParam" style="margin-left: 1%;">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Carga de parametros para horario general</h4>
				</div>
			</div>
		</div>
		<div class="col-sm-12" id="datosHorario" style="margin-left: 10%;">
			<div class="col-sm-3" >
				<div class="form-group">
               		<label for="hora_inicio">Hora Inicio</label><br>
               		
               		<input type="text" name="hora_inicio" readonly class="btn btn-default" id="hora_inicio">
                </div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
               		<label for="nu_citas">Cantidad de Citas</label><br>
                    <input type="text" name="nu_citas" readonly  class="btn btn-default" id="nu_citas" >
                </div>
			</div>
			
			<div class="col-sm-3">
				<div class="form-group">
		        	<label for="nu_intervalo">Intervalo de Citas</label>
		      		<select class="form-control" readonly id="nu_intervalo" style="width: 180px;">
		      			<option></option>
				        <option>05</option>
				        <option>10</option>
				        <option>15</option>
				        <option>20</option>
				        <option>25</option>
				        <option>30</option>
				        <option>35</option>
				        <option>40</option>
				        <option>45</option>
				        <option>50</option>
				        <option>55</option>
				        <option>60</option>
		      		</select>
		    	</div>
			</div>
		</div>
		<div class="col-sm-11" id="panelesEspecialistas-Turno" style="margin-left: 1%;">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Especialidades</h4>
				</div>
			</div>
		</div>
		<div class="col-sm-12" id="panel-body-Tipo-Cita" style="margin-left: 1%;">
			<div align="center" class="radio" style="margin-left: 15%;">
				<div class="col-sm-2">
					<label><input name="in_especialidad" id="in_especialidad"  type="radio" value="M">M&eacute;dicos</label>
				</div>
				<div class="col-sm-2">
					<label><input name="in_especialidad" id="in_especialidad" type="radio" value="O">Odont&oacute;logos</label>
				</div>
				<div class="col-sm-2">
					<label><input name="in_especialidad" id="in_especialidad" type="radio" value="N">Nutricionistas</label>
				</div>				
				<div class="col-sm-2">
					<label><input name="in_especialidad" id="in_especialidad" type="radio" value="F">Fisioterapeutas</label>
				</div>				
			</div>
		</div>
		
		<div class="col-sm-3" id="panel-body-Turno" hidden="true">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center" >Turno</h4>
				</div>
				
				<div align="center" class="panel-body" >  
					<div class="radio">
						<label><input name="in_turno" id="in_turno" type="radio" value="AM">Mañana</label>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<label><input name="in_turno" id="in_turno" type="radio" value="PM">Tarde</label>
					</div>
				</div><!--Fin panel-body-->
			</div>
		</div>
		
		<div class="col-sm-11"">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
				<button style="margin-left: 45%;margin-top: 5%;" type="button" id="generaHorario" onclick="horario()" class="btn btn-primary">Generar Horario</button>
			</div>
		<div class="col-sm-6" style="">
			<div id="horarioGenerado">
				<!-- 
					AQUI VA EL HORARIO GENERADO DESDE AJAX								
				 -->
			</div>
		</div>
	</div><!-- Fin row content -->
</div><!-- Fin container-fluid -->
