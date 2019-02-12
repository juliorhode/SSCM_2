<!--***********************************************-->
<!--******************* ESTILOS *******************-->
<!--***********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">
<!-- <link rel="stylesheet" href="css/ausentismo.css"> -->

<script src="js/administracion/ausentismo.js"></script>


<div id="cargaIndividual">
    <div class="container-fluid">
        <div class="row content">
<!--         	<div class="col-sm-12"> -->
<!-- 				<div class="panel panel-default"> -->
<!-- 					<div class="panel-heading"> -->
<!-- 						<h4 class="panel-title" align="center">Carga de Ausentismo Médico</h4> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
            <div class="col-sm-12">
	            <div class="panel panel-default">
		          	<div class="panel-heading">
		           		<h4 class="panel-title" align="center" style="font-weight: bold;">Datos del Especialista</h4>
		            </div>
	            </div>
            </div>
            
            <div class="col-sm-12">
                <div class="col-sm-2">
	                <div class="form-group">
	                    <label for="cedula_especialista">C&eacute;dula</label>
	                    <input type="text" id="cedula_especialista" data-toggle="tooltip" title="Puede presionar la tecla enter y se desplegará una ventana con los medicos" autofocus class="form-control" name="cedula_especialista">
	                </div>
	            </div>
                 <a href="Ausentismo">Servlet</a>
                 <div id = "datosMedico">
	                 <!-- 
	                		RESULTADOS CON AJAX:
	                		
	                		NOMBRE Y APELLIDO DEL ESPECIALISTA ----- nb_especialista
	                		TIPO (DIMINUTIVO DE LA ESPECIALIDAD) ---- in_tipo_especial
	                		TIPO DE ESPECIALIDAD  ------ nb_tipo_especial
	                 -->
                 </div>
            </div>
            
            <div class="col-sm-12">
            	<br><br>
	            <div class="panel panel-default">
		          	<div class="panel-heading">
		           		<h4 class="panel-title" align="center" style="font-weight: bold;">Datos del Ausentismo</h4>
		            </div>
	            </div>
	            <br><hr>
            </div>
            
            <div class="col-sm-12">
            	<div class="col-sm-3">
                	<div class="form-group">
                		<label for="fecha_ini">Fecha Inicio</label><br>
	                    <!--<input type="date" id="fecha_ini" class="form-control" name="fecha_ini">-->
	                    <input type="text" name="fecha_ini" readonly data-toggle="tooltip" title="Fecha de inicio que comienza el ausentismo" class="btn btn-default" id="fecha_ini" size="30">
	                </div>
	            </div>
	            <div class="col-sm-3">
                	<div class="form-group">
                		<label for="fecha_fin">Fecha Fin</label><br>
	                    <!-- <input type="date" id="fecha_fin" class="form-control" name="fecha_fin"> -->
	                    <input type="text" name="fecha_fin" readonly data-toggle="tooltip" title="Fecha que termina el ausentismo" class="btn btn-default" id="fecha_fin" size="30">
	                </div>
	            </div>
	            
	            <div class="col-sm-3">
                	<div class="form-group">
                		<label for="hora_ini">Hora Inicio</label><br>
	                    <input type="text" name="hora_ini" readonly  data-toggle="tooltip" title="Hora de inicio del ausentismo" class="btn btn-default" id="hora_ini" size="30">
	                </div>
	            </div>
	            <div class="col-sm-3">
                	<div class="form-group">
                		<label for="hora_fin">Hora Fin</label><br>
                		<input type="text" name="hora_fin" readonly  data-toggle="tooltip" title="Hora que termina el ausentismo" class="btn btn-default" id="hora_fin" size="30">
	                </div>
	            </div>
	            
            </div>
            
            <div class="col-sm-12">
               	<div class="form-group">
                    <label for="txt_motivo" style="text-align: right;">Motivo</label>
                    <textarea class="form-control" id="txt_motivo" data-toggle="tooltip" title="Puede colocar la descripcion por el cual el especialista va a estar ausente" rows="5" name="txt_motivo"></textarea>
                </div>
            </div>
 
 			<div class="col-sm-6">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
				<button  type="button"  style=" margin-left:100%; margin-top: 5%;" id="agregaAusentismo" name="agregaAusentismo" onclick="ausentismo()" class="btn btn-success">Guardar</button>
			</div>
			
			<div class="col-sm-12" id="respuesta">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
			</div>
			
		</div>
    </div>
</div>

<!--*********************************************************************-->
<!--*********************************************************************-->
<!--******************* CONTENEDOR VENTANA EMERGENTE ********************-->
<!--*********************************************************************  -->

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



<!-- <script type="text/javascript"> -->
<!-- 	cedulaIni(); -->
<!--  	window.onkeyup = compruebaTecla; -->
<!-- </script> -->
