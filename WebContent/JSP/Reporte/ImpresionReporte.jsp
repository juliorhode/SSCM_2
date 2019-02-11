<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!--**********************************************-->
<!--******************* ESTILO *******************-->
<!--**********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">
<script src="js/jquery.PrintArea.js"></script>

<style>
	#panel{margin-top: 20px;}
	#btnPDF{display: none;}
</style>

<div class="col-sm-12" id="panel">
	<div class="panel-group">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 align="center" class="panel-title">Impresi�n de Reporte</h4>
			</div>
			<div class="panel-body">
				<form action="ServletReporte" method="post" target="_blank">
					
						<label for="fecha">Reporte Agenda por Fecha</label><br>				
						<input type="text" name="fecha" readonly="readonly" class="btn btn-default" id="fecha" placeholder="Fecha" style="width: 150px; ">
						<input type="submit" id="btnPDF" class="btn btn-primary" value="Solicitar">
					
					<div id="selectlistaMedicos" class="col-sm-12">
						<!-- respuesta viene desde AJAX 
						Se necesitan los medicos para unirlos con la fecha seleccionada
						y asi emitir reporte especifico por medico en esa fecha
						-->
					</div>
								
				</form>
			</div><!--Fin panel-body-->
		</div><!--Fin panel panel-default-->
	</div><!--Fin panel-group-->
</div>
			




<script>
$("#fecha").datepicker({
	dateFormat: "dd/mm/yy",
	//minDate: new Date(),
	showAnim: "fadeIn",
	beforeShowDay: $.datepicker.noWeekends,
	//beforeShowDay: noExcursion,
	maxDate: "+2m"
});

$("#fecha").change(function(){
	if($("#fecha").val() !=""){
		$("#btnPDF").show();
		$.ajax({
			type: "POST",
			url: "Especialista",
			data: {
			 parametro : "medicoReporte"
			},
			success: function( result ) {
			  $( "#selectlistaMedicos" ).html( result );
			}
		});
		
	}
});



</script>