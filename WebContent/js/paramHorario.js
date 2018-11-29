function timePicker(){
	$("#hora_inicio").timepicker({
	    timeFormat: 'HH',
	    interval: 1,
	    minTime: '07',
	    maxTime: '18',
	    startTime: '07',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
	$("#nu_citas").timepicker({
	    timeFormat: 'hh',
	    interval: 1,
	    minTime: '1',
	    maxTime: '10',
	    startTime: '1',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	    
	});
}

function horario(){
	var hora_inicio = $("#hora_inicio").val();
	var nu_citas = $("#nu_citas").val();
	var nu_intervalo = $("#nu_intervalo").val();
	//Capturamos el valor tildado
    //var in_turno = $('input:radio[name=in_turno]:checked').val();
    var in_turno = $('input:radio[name=in_turno]:checked').val();
    //Se redefine a M = mañana y T = tarde, ya que en el JSP se colocó
    // AM y PM por conflicto con los values del especialista Medico = M
    if(in_turno == 'AM'){in_turno = 'M'}else{in_turno = 'T'}
	
    var in_especialidad = $('input:radio[name=in_especialidad]:checked').val();

	var parametro = "unica";
    $.ajax({
        type: "POST",
        url: "Param",
        data: {
        	hora_inicio 	: hora_inicio,
        	nu_citas 		: nu_citas,
        	nu_intervalo 	: nu_intervalo,
        	in_turno 		: in_turno,
        	in_especialidad : in_especialidad,
        	parametro 		: parametro,
        },
        success: function( result ) {
            $( "#horarioGenerado" ).html( result );
        }
    });
}