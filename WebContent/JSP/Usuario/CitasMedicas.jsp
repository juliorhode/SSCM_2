<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!--**********************************************-->
<!--******************* ESTILO *******************-->
<!--**********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">

<!--**************************************************-->
<!--******************* JAVASCRIPT *******************-->
<!--**************************************************-->
<script src="js/cita_medica/cita.js"></script>

<script type="text/javascript">
	$(document).ready(function(){
		//alert("LATERAL IZQUIERDA: " + $("#lateral_izq").outerHeight() + "\nLATERAL DERECHA: " + $("#lateral_der").outerHeight());
		iniciaCita();
	});
</script>

<div class="container-fluid">
    <div class="row content">
        <div class="col-md-12" id="panelCitas">
			<!--***************************************************************-->
			<!--******************* CONTENEDOR SOLICITANTE ********************-->
			<!--**************************************************************-->					
			<div class="col-sm-12" > 
				<div class="panel-group" id="panel-group-Tipo-Solicitante" >
					<div class="panel panel-default" id="solicita">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Tipo Solicitante</h4>
						</div>
						<div align="center" class="panel-body" id="panel-body-Tipo-Solicitante">  
							<div class="radio">
								<label><input name="otpTipoSolicitante" id="optTipoEmpleado" type="radio" value="EMP">Empleado</label>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<label><input name="otpTipoSolicitante" id="optTipoFamiliar" type="radio" value="FAM">Familiar</label>
							</div>
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-12-->
			<!--************************************************************-->
			<!--******************* CONTENEDOR FAMILIAR ********************-->
			<!--************************************************************-->
			<div class="col-sm-12"> 
				<div class="panel-group" id="panel-group-Familiares">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Familiares</h4>
						</div>
						<div align="center" class="panel-body" id="panel-body-Familiares"> 
									<!-- Resultados por medio de AJAX -->
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-6-->
			<!--*************************************************************-->
			<!--******************* CONTENEDOR TIPO CITA ********************-->
			<!--*************************************************************-->
			
				<div class="col-sm-8 col-sm-12">
				<div class="panel-group" id="panel-group-Tipo-Cita" >
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Tipo Cita</h4>
						</div>
						<div align="center" class="panel-body" id="panel-body-Tipo-Cita">  
							<div class="radio" id="panel-body-Tipo-Cita-radios">
								<label><input name="otpTipoCita" id="optMedico" type="radio" value="M">Médica</label>
								<label><input name="otpTipoCita" id="optOdonto" type="radio" value="O">Odontológica</label>
								<label><input name="otpTipoCita" id="optNutri" type="radio" value="N">Nutrición</label>
								<label><input name="otpTipoCita" id="optFisio" type="radio" value="F">Fisioterapia</label>
							</div>
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
				</div>
			<!--**************************************************************-->
			<!--******************* CONTENEDOR TIPO TURNO ********************-->
			<!--**************************************************************-->
			 <div class="col-sm-4 col-sm-12">
					<div class="panel-group" id="panel-group-Tipo-Turno" >
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 align="center" class="panel-title">Horario</h4>
							</div>
							<div align="center" class="panel-body" id="panel-body-Tipo-Turno">  
								<div class="radio" id="panel-body-Tipo-Turno-radios">
									<label><input name="otpTipoTurno" id="otpTipoTurno" type="radio" value="M">Mañana</label>
									<label><input name="otpTipoTurno" id="otpTipoTurno" type="radio" value="T">Tarde</label>
								</div>
							</div><!--Fin panel-body-->
						</div><!--Fin panel panel-default-->
					</div><!--Fin panel-group-->
			</div>
			<!--****************************************************************************-->
			<!--******************* CONTENEDOR ESPECIALISTA Y FECHA CITA********************-->
			<!--****************************************************************************-->	
			<div class="col-sm-12">
				<div class="panel-group" id="panel-group-Especialista-Fecha">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Detalles Cita</h4>
						</div>
						<div class="panel-body" id="panel-body-Especialista-Fecha">  
							<div class="col-sm-12">
								<div class="form-group" id="form-group-Especialistas">
									<!-- Resultados por medio de AJAX -->
								</div>
							</div>
							<div class="col-sm-6 col-sm-12">
								<div class="form-group">
									<label for="tipoSolicitud">Tipo de Solicitud:</label><br>
									<select name='tipoSolicitud' class='form-control' id='tipoSolicitud'>
										<option></option>
									</select>
								</div>
							</div>
							<div class="col-sm-6 col-sm-12">
								<div class="form-group">
									<label for="FechaSolicitud">Fecha:</label><br>
									<!--  <input name="FechaSolicitud" disabled="disabled" class="btn btn-default" id="FechaSolicitud" type="date" data-format="dd/MM/yyyy">-->
									<input type="text" name="FechaSolicitud" disabled="disabled" class="btn btn-default" id="FechaSolicitud" onchange="fecha()">
								</div>
							</div>	
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-7-->
			<!--*****************************************************************************-->
			<!--******************* INFORMACION HORARIO Y DISPONIBILIDAD ********************-->
			<!--*****************************************************************************-->	
			<div class="col-sm-12" >
				<div class="panel-group" id="panel-group-horario">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Información</h4>
						</div>
						<div class="panel-body" id="panel-body-horario">  
							<div class="col-sm-12">
								<div class="form-group" id="form-group-horario">
									<!-- Resultados por medio de AJAX -->
								</div>
							</div>
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-4-->
			<!--*********************************************************-->
			<!--******************* CONTENEDOR BOTON ********************-->
			<!--*********************************************************-->
			<div class="col-sm-12" >
				<div class="panel-group" id="panel-group-Botones">
					<div align="center" class="col-sm-12">
						<button name="btnProcesar" class="btn btn-success" id="btnProcesar" type="button" value="procesar">Procesar</button>
					</div>
					<!--
					<div align="center" class="col-sm-6">
						<button class="btn btn-danger" type="reset">Cancelar</button>
					</div>-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-7-->
			
			
			<div id="Modal">
			<!-- Resultados por medio de AJAX -->
			</div>
			
			<!--*********************************************************************-->
			<!--******************* CONTENEDOR VENTANA EMERGENTE ********************-->
			<!--*********************************************************************-->
			<!-- Ventana Modal -->
			<div class="modal fade" role="dialog" id="mensajeModalTrue" data-keyboard="false" data-backdrop="static"><!-- data-keyboard="false" data-backdrop="static" -->
			    <div class="modal-dialog">
			        <div class="modal-content">
			    
			            <!-- Encabezado del Modal -->
			            <div class="modal-header">
			                <h3 class="modal-title" style="color:#1B0082;text-align: center;font-size: 24px;">Solicitud de Cita Médica</h3>
			            </div>
			    
			            <!-- Cuerpo del Modal -->
			            <div class="modal-body" id="cuerpoModal">
			                <h4 style="color:#1B0082;text-align: center;font-size: 22px;">Su Cita ha sido Programada con éxito</h4>
			            </div>
			            <!-- Pie del Modal -->
			            <div class="modal-footer">
			                <input type="button" class="btn btn-primary" data-dismiss="modal" value="Aceptar" id="aceptarTrue" style="width: 100px;margin-left:360px;" >
			            </div>
			        </div>
			    </div>
			</div>
			<!--*********************************************************************-->
			<!--*********************************************************************-->
			<!--******************* CONTENEDOR VENTANA EMERGENTE ********************-->
			<!--*********************************************************************-->
			<!-- Ventana Modal -->
			<div id="Modal"></div>
			<div class="modal fade" id="mensajeModalFalse" data-keyboard="false" data-backdrop="static">
			    <div class="modal-dialog">
			        <div class="modal-content">
			    
			            <!-- Encabezado del Modal -->
			            <div class="modal-header">
			                <h3 class="modal-title" style="color:#1B0082;text-align: center;font-size: 24px;">Solicitud de Cita Médica</h3>
			            </div>
			    
			            <!-- Cuerpo del Modal -->
			            <div class="modal-body" id="cuerpoModal">
			                <h4 style="color:#ff0000;text-align: center;font-size: 22px;">Su Cita no pudo ser procesada</h4>
			            </div>
			            <!-- Pie del Modal -->
			            <div class="modal-footer">
			                <input type="button" class="btn btn-primary" value="Aceptar" id="aceptarFalse" style="width: 100px;margin-left:360px;" data-dismiss="modal">
			            </div>
			        </div>
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
.ui-datepicker{background: #335f7d;}

/*La cabecera*/
.ui-datepicker .ui-datepicker-header {font-size: 12px;background: #FFFFFF;font-weight: bold;}

/*Para los días de la semana: Sa Mo ... */
.ui-datepicker th{color: #FFFFFF;font-weight: bold;width: auto;}

/*Para el item del día del mes seleccionado */
/*.ui-datepicker .ui-state-active{background: orange;color: #FFFFFF;}*/


</style>