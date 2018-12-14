<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<!--**********************************************-->
<!--******************* ESTILO *******************-->
<!--**********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">

<style>
	#panel{margin-top: 20px;}
</style>

<div class="col-sm-12" id="panel">
	<div class="panel-group">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h4 align="center" class="panel-title">Impresión de Reporte</h4>
			</div>
			<div class="panel-body">
			<form action="ServletReporte" method="post" target="_blank">
				<label for="FechaSolicitud">Reporte Agenda por Fecha</label><br>				
				<input type="text" name="FechaSolicitud" class="btn btn-default" id="FechaSolicitud" placeholder="Fecha" style="width: 150px; ">
				<input type="submit" id="btnPDF" class="btn btn-default">
			</form>
			</div><!--Fin panel-body-->
		</div><!--Fin panel panel-default-->
	</div><!--Fin panel-group-->
</div>
<div id ="prueba"></div>				



<script>
$("#imprimeFecha").hide();
$("#FechaSolicitud").datepicker({
	dateFormat: "dd/mm/yy",
	//minDate: new Date(),
	showAnim: "fadeIn",
	beforeShowDay: $.datepicker.noWeekends,
	//beforeShowDay: noExcursion,
	maxDate: "+2m"
});

$("#FechaSolicitud").change(function(){
	if($("#FechaSolicitud").val() !=""){
		$("#imprimeFecha").show();
	}
});


</script>