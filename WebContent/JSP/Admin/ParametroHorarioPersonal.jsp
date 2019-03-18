<!--***********************************************-->
<!--******************* ESTILOS *******************-->
<!--***********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">
<link rel="stylesheet" href="css/cita_medica/paramHorarioPersonal.css">

<!--**************************************************-->
<!--******************* JAVASCRIPT *******************-->
<!--**************************************************--> 
<script src="js/administracion/paramHorarioPersonal.js"></script>

<script type="text/javascript">
	inicio();
	
</script>

<div class="container-fluid">
	<div class="row content" id="contenido">
		<div class="col-sm-12"><!-- Inicio datos especialista -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Datos del Especialista</h4>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="panel panel-default">
                      	<div class="panel-body">
                        <div class="col-sm-2">
	                        <div class="form-group">
	                    		<label for="cedula_especialista">C&eacute;dula</label>
	                    		<input type="text" id="cedula_especialista" data-toggle="tooltip" title="Puede presionar la tecla enter y se desplegará una ventana con los medicos" autofocus class="form-control" name="cedula_especialista">
                			</div>
                		</div>
                		<div id = "datosMedico">
		                 <!-- 
		                		RESULTADOS CON AJAX:
		                		
		                		NOMBRE Y APELLIDO DEL ESPECIALISTA ----- nb_especialista
		                		TIPO (DIMINUTIVO DE LA ESPECIALIDAD) ---- in_tipo_especial
		                		TIPO DE ESPECIALIDAD  ------ nb_tipo_especial
		                		TURNO ------ in_turno
		                 -->
		                 
                    	</div>
                    </div>
				</div>
			</div><!-- Fin datos especialista -->
		</div>
		<div class="col-sm-12" id="panelHorario">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Horario registrado</h4>
				</div>
			</div>
			<div class="col-sm-6">
	           		<table class="table table-bordered" id="tablaHorario">
					    <thead>
						    <tr>
							    <th>N° Citas</th>
							    <th>Intervalo</th>
							    <th>Hora Inicio</th>
						    </tr>
					    </thead>
					    <tbody id="respuestaHorario">
							<!-- RESULTADOS CON AJAX -->
					    </tbody>
					</table>
              	</div>
		</div>
		<div class="col-sm-12" id="panelHorarioNuevo"><!-- Inicio carga horario -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title" align="center">Carga de horario personalizado</h4>
				</div>
			</div>
			<div class="col-sm-12">
				<div class="panel panel-default">
                      	<div class="panel-body">
                        <div class="col-sm-4" >
							<div class="form-group">
			               		<label for="hora_inicio">Hora Inicio</label><br>
			               		<input type="text" name="hora_inicio" readonly class="btn btn-default" id="hora_inicio" >
			                </div>
						</div>
						<div class="col-sm-4">
							<div class="form-group">
			               		<label for="nu_citas">Cantidad de Citas</label><br>
			                    <input type="text" name="nu_citas" readonly  class="btn btn-default" id="nu_citas" >
			                </div>
						</div>
						<div class="col-sm-4">
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
						<div class="col-sm-11">
							<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
							<button type="button" id="generaHorario" class="btn btn-primary" onclick="actualizaParametros()">Generar Horario</button>
						</div>
                    </div>
                   </div>
			</div>
		</div><!-- Fin carga horario -->
	</div><!-- Fin row content -->
</div><!-- Fin container-fluid -->
 	
<div id="alerta"></div> 	
<div class="modal fade" id="medicos" role="dialog" data-keyboard="false" data-backdrop="static" style="width: auto;height: auto;">
    <div class="modal-dialog">
        <div class="modal-content">
    
            <!-- Encabezado del Modal -->
            <div class="modal-header">
                <h3 class="modal-title" style="color:#1B0082;text-align: center;font-size: 24px;">Listado de Medicos Disponibles</h3>
            </div>
    
            <!-- Cuerpo del Modal -->
            <div class="modal-body" id="cuerpoModal">
					<table class='table table-bordered table-striped'>
						<thead>
							<tr>
								<th>Cédula</th>
								<th>Nombre del Especialista</th>
								<th>Especialidad</th>
							</tr>
						</thead>
						<tbody id = "medicosModal">
						
							
						</tbody>
					</table><!-- Fin table -->
            </div>
            <!-- Pie del Modal -->
            <div class="modal-footer">
                <input type="button" class="btn btn-primary" value="Cancelar" id="aceptarFalse" style="width: 100px;margin-left:360px;" data-dismiss="modal">
            </div>
        </div>
    </div>
</div>

 	