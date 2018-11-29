
function inicial(){
	$("#FechaSolicitud").datepicker({
		dateFormat: "dd/mm/yy",
		//minDate: new Date(),
		showAnim: "fadeIn",
		beforeShowDay: $.datepicker.noWeekends,
		//beforeShowDay: noExcursion,
		maxDate: "+2m"
	});
	
	$("#panelParametrosEspecialista-Fecha").hide();
	$("#panelParametros-busqueda").hide();
	
}
$("#FechaSolicitud").change(function(){
	if($("#FechaSolicitud").val() !=""){
		filtroFecha();
	}
});

function filtroFecha(){
	var fecha = $("#FechaSolicitud").val();
	var tipoCita = $('input:radio[name=otpTipoCita]:checked').val();
	$.ajax({
        type: "POST",
        url: "Especialista",
        data: {
        	tipoCita : tipoCita,
        	fecha    : fecha,
        	parametro : "agendaCitas",
        },
        success: function( result ) {
        	$("#listadoCitas").html( result );
        }
    });
}
$('input[name=otpTipoCita]').click(function(){
	if($("input[name='otpTipoCita']:radio").is(':checked')){
		$("#panelParametrosEspecialista-Fecha").show();
		$("#panelParametros-busqueda").show();
	}
	var tipoCita = $('input:radio[name=otpTipoCita]:checked').val();
	$("#FechaSolicitud").val("");
	$.ajax({
        type: "POST",
        url: "Especialista",
        data: {
        	tipoCita : tipoCita,
        	//fecha    : fecha,
        	parametro : "agendaCitas",
        },
        success: function( result ) {
        	$("#listadoCitas").html( result );
        }
    });
});

/**************************************************************************
*  Esto es para busqueda progresiva en la tabla (HistorialCitasAdmin.jsp) 
***************************************************************************/
$("#txtbusqueda").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#listadoCitas tr").filter(function() {
      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
});
