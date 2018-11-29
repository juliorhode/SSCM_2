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
	
	$("#nu_citas").click(function(){
		var a = document.getElementById("hora_inicio");
		if(parseInt(a.value) < 12){
			console.log("Es en la mañana");
			$("input[value='AM']:radio").prop('checked', true);
			$("input[value='AM']:radio").removeAttr("disabled");
			$("input[value='PM']:radio").prop('checked', false);
			$("input[value='PM']:radio").attr("disabled","disabled");
			
		}else{
			console.log("Es en la tarde");
			$("input[value='AM']:radio").prop('checked', false);
			$("input[value='AM']:radio").attr("disabled","disabled");
			$("input[value='PM']:radio").prop('checked', true);
			$("input[value='PM']:radio").removeAttr("disabled");
		}
	});
	
	
</script>

<div class="container-fluid" >
	<div class="row content" id="contenido" >
		<div class="col-sm-10" id="panelParam">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Carga de parametros para horario general</h4>
				</div>
			</div>
		</div>
		<div class="col-sm-10" id="datosHorario">
			<div class="col-sm-3" style="margin-left: 134px;">
				<div class="form-group">
               		<label for="hora_inicio">Hora Inicio</label><br>
               		<input type="text" name="hora_inicio" class="btn btn-default" id="hora_inicio">
                </div>
			</div>
			<div class="col-sm-3">
				<div class="form-group">
               		<label for="nu_citas">Cantidad de Citas</label><br>
                    <input type="text" name="nu_citas" readonly  class="btn btn-default" id="nu_citas" size="30">
                </div>
			</div>
			
			<div class="col-sm-3">
				<div class="form-group">
		        	<label for="nu_intervalo">Intervalo de Citas</label>
		      		<select class="form-control" id="nu_intervalo">
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
		<div class="col-sm-12" id="panelesEspecialistas-Turno">
			<div class="col-sm-7" id="panelEspecialidades">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title" align="center" >Especialidades</h4>
					</div>
					
					<div align="center" class="panel-body" id="panel-body-Tipo-Cita">  
						<div class="radio">
							<label><input name="in_especialidad" id="in_especialidad" type="radio" value="M">M&eacute;dicos</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input name="in_especialidad" id="in_especialidad" type="radio" value="O">Odont&oacute;logos</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input name="in_especialidad" id="in_especialidad" type="radio" value="N">Nutricionistas</label>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input name="in_especialidad" id="in_especialidad" type="radio" value="F">Fisioterapeutas</label>
						</div>
					</div><!--Fin panel-body-->
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
		</div>
		<div class="col-sm-12">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
				<button  type="button" id="generaHorario" onclick="horario()" class="btn btn-primary">Generar Horario</button>
			</div>
		<div class="col-sm-12 ">
			<div id="horarioGenerado">
				<!-- 
					AQUI VA EL HORARIO GENERADO DESDE AJAX								
				 -->
			</div>
		</div>
	</div><!-- Fin row content -->
</div><!-- Fin container-fluid -->
