function inicio(){
	$("#programadas").click(function(){
		$("#cuerpo").load('JSP/Admin/CitaMedicaAdmin.jsp');
	});
			
	$("#cortesias").click(function(){
		$("#cuerpo").load('JSP/Admin/CortesiaAdmin.jsp');
	});
	
	$("#emergencias").click(function(){
		$("#cuerpo").load('JSP/Admin/EmergenciaAdmin.jsp');
	});
	
	$("#ausentismo").click(function(){
		$("#cuerpo").load('JSP/Admin/AusentismoMedico.jsp');
	});
	
	$("#cargaUnica").click(function(){
			$("#cuerpo").load('JSP/Admin/ParametroHorarioUnica.jsp');
	});
		
	$("#cargaIndividual").click(function(){
		$("#cuerpo").load('JSP/Admin/ParametroHorarioPersonal.jsp');
	});
	
	$("#eliminaHorarios").click(function(){
		
	});
	
	$("#listadoProgramadas").click(function(){
		$("#cuerpo").load('JSP/Admin/HistorialCitasAdmin.jsp');
	});
	
	$("#imprimeReporte").click(function(){
		$("#cuerpo").load('JSP/Reporte/ImpresionReporte.jsp');
	});
	
	
}