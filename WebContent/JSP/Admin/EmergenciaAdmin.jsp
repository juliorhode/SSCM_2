<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!--**********************************************-->
<!--******************* ESTILO *******************-->
<!--**********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">

<!--**************************************************-->
<!--******************* JAVASCRIPT *******************-->
<!--**************************************************-->
<script src="js/administracion/emergenciaAdmin.js"></script>



<script type="text/javascript">
	$(document).ready(function(){
		inicio();
	});
</script>

<div class="container-fluid">
    <div class="row content">
        <div class="col-md-12" id="panelCitas">
        	<!--*************************************************************-->
			<!--******************* CONTENEDOR TIPO CITA ********************-->
			<!--*************************************************************-->
				<div class="col-sm-12">
					<div class="panel-group" id="panel-group-Tipo-Cita" >
						<div class="panel panel-default">
							<div class="panel-heading">
								<h4 align="center" class="panel-title">Tipo Cita</h4>
							</div>
							<div align="center" class="panel-body" id="panel-body-Tipo-Cita">  
								<div class="radio" id="panel-body-Tipo-Cita-radios">
									<label><input name="otpTipoCita" id="optMedico" type="radio" value="M">Médica</label>
									<label><input name="otpTipoCita" id="optOdonto" type="radio" value="O">Odontológica</label>
								</div>
							</div><!--Fin panel-body-->
						</div><!--Fin panel panel-default-->
					</div><!--Fin panel-group-->
				</div>
			<!--***************************************************************-->
			<!--******************* CONTENEDOR SOLICITANTE ********************-->
			<!--**************************************************************-->					
			<div class="col-sm-12" > 
				<div class="panel-group" id="panel-group-Tipo-Solicitante" >
					<div class="panel panel-default" id="solicita">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Tipo Solicitante</h4>
						</div>
						<div class="panel-body" id="panel-body-Tipo-Solicitante">  
							<div class="col-sm-2">
								<div class='form-group'>
									<label for='cedula' style='text-align: right;'>C&eacute;dula Empleado</label>
									<input type="text" id="cedula" class="form-control" name="cedula">
								</div>
							</div>
							<div id="resultado">
								<div class='col-sm-6'>
									<div class='form-group'>
										<label for='apellidoNombre' style='text-align: right;'>Apellidos y Nombres</label>
										<input type='text' id='apellidoNombre' class='form-control' disabled='disabled' name='apellidoNombre' >
									</div>
								</div>
								<div class='col-sm-3'>
									<div class='form-group'>
										<label for='contacto' style='text-align: right;'>Numero de Contacto</label>
										<input type='text' id='contacto' class='form-control' disabled='disabled' name='contacto' >
									</div>
								</div>
							</div>
							
							<input type="hidden" name="Solicitante" id="Solicitante">
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
			
			<div class="col-sm-6">
				<div class="panel-group" id="panel-group-Especialista" >
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Especialista</h4>
						</div>
						<div align="center" class="panel-body" id="panel-body-Especialista">  
							<div class="form-group" id="form-group-Especialistas">
							<!-- Resultados por medio de AJAX -->
							
							</div>
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div>
			
			<div class="col-sm-6"> 
				<div class="panel-group" id="panel-group-Motivo">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 align="center" class="panel-title">Motivo</h4>
						</div>
						<div align="center" class="panel-body" id="panel-body-Motivo"> 
							<div class='form-group'>
								<input type='text' id='txtMotivo' style="text-transform: capitalize;"  class='form-control' name='txtMotivo'">
							</div>			
						</div><!--Fin panel-body-->
					</div><!--Fin panel panel-default-->
				</div><!--Fin panel-group-->
			</div><!--Fin col-sm-12-->
			
			
			
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
			
			
			<!--*********************************************************************-->
			<!--*********************************************************************-->
			<!--******************* CONTENEDOR VENTANA EMERGENTE ********************-->
			<!--*********************************************************************-->
			<!-- Ventana Modal -->
			<div id="Modal"></div>
			<div class="modal fade" id="mensajeCita" data-keyboard="false" data-backdrop="static">
			    <div class="modal-dialog">
					<div class="modal-content">
						<!-- Encabezado del Modal -->
						<div class="modal-header">
							<div class="row" align="center">
				  				<div class="col-xs-6 col-md-12">
									<h3 class="modal-title" style="color:#1B0082;text-align: center;">Solicitud de Emergencia Médica</h3>
								</div>
							</div>
							
						</div>
						<!-- Cuerpo del Modal -->
						<div class="modal-body" id="cuerpoModal">
							<div class="row" align="center">
				  				<div class="col-xs-12 col-md-12">
									<h4 style="color:#ff0000;text-align: center;">¿Desea solicitar la Emergencia para el familiar?</h4>
								</div>
							</div>
						</div>
						<!-- Pie del Modal -->
						<div class="modal-footer">
							<div class="row" align="center">
				  				<div class="col-xs-6 col-md-6">
									<input type="button" class="btn btn-primary" value="Si" id="si">
								</div>
				  				<div class="col-xs-6 col-md-6">
									<input type="button" class="btn btn-primary" value="No" id="no" data-dismiss="modal">
								</div>
							</div>
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

#btnProcesar{
	margin-top: 5%;
}

</style>