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
				<label for="FechaSolicitud">Reporte Agenda por Fecha</label><br>				
				<input type="text" name="FechaSolicitud" class="btn btn-default" id="FechaSolicitud" placeholder="Fecha" style="width: 150px; ">
				<a href="#" id="imprimeFecha" target="_blank">
					<button id="btnPDF" class="btn btn-default">
						<span class="glyphicon glyphicon-print" style="width: 20px;height: 10px;"></span>
					</button>
				</a>
			</div><!--Fin panel-body-->
		</div><!--Fin panel panel-default-->
	</div><!--Fin panel-group-->
</div>
				



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

$("#imprimeFecha").click(function(){
	var fecha = $("#FechaSolicitud").val();
	window.open('JSP/Reporte/ReporteFecha.jsp?fecha=' + fecha, 'Reporte Agenda','width=600,height=600,top=100,left=300');
});

</script>