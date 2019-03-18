function inicio(){
	$("#panelHorarioNuevo").hide();
	$("#panelHorario").hide();
	$("#generaHorario").hide();
	
	//$("#hora_inicio").attr("disabled","disabled");
	$("#nu_citas").attr("disabled","disabled");
	$("#nu_intervalo").attr("disabled","disabled");
	
	$("#cedula_especialista").keyup(function() {
        this.value = (this.value + '').replace(/[^0-9]/, '');
    });
	
	$("#cedula_especialista").keypress(function(evento){
    	if(evento.which == 13){
    		listadoMedicosModal();
    	}
    });
	
	timePicker();
}

function habilitaNuCitas(){
	alert("mes estas jodiendo");
}
function listadoMedicosModal(){
	$.ajax({
        type: "POST",
        url: "ServletAusentismo",
        data: {
        	parametro : "medicoModal",
        },
        success: function( result ) {
            $( "#medicosModal" ).html( result );
            $('#medicos').modal('show');
        }
    });
}

function inputModal(){
	$("#cuerpoModal input").click(function(){
		var medico = $('input:radio[name=optMedico]:checked').val();
		$("#cedula_especialista").val(medico);
		$("#medicos").modal("hide");
		$("#cedula_especialista").focus();
		buscar();
	});
}

function buscar(){
	var cedulaMedico = $("#cedula_especialista").val();
	$.ajax({
        type: "POST",
        url: "ServletAusentismo",
        data: {
        	cedula_especialista : cedulaMedico,
        	parametro : "buscaMedico",
        },
        success: function( result ) {
            $( "#datosMedico" ).html( result );
            $("#cedula_especialista").blur();
        	//$("#in_especialidad").hide();
        	
        }
    });
	listaHorario();
}

function listaHorario(){
	var cedulaMedico = $("#cedula_especialista").val();
	$.ajax({
        type: "POST",
        url: "ServletEspecialista",
        data: {
        	cedula_especialista : cedulaMedico,
        	parametro : "listaHorario",
        },
        success: function( result ) {
            $("#respuestaHorario").html(result);
            $("#panelHorarioNuevo").show();
        	$("#panelHorario").show();
        }
    });
	
}

function timePicker(){
	$("#hora_inicio").timepicker({
	    timeFormat: 'HH',
	    interval: 1,
	    minTime: '07',
	    maxTime: '18',
	    startTime: '07',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true,
	    change: function() {
	    	$("#nu_citas").removeAttr("disabled");
        }
	});
	$("#nu_citas").timepicker({
	    timeFormat: 'hh',
	    interval: 1,
	    minTime: '1',
	    maxTime: '10',
	    startTime: '1',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true,
	    change: function() {
	    	$("#nu_intervalo").removeAttr("disabled");
        }
	});
}

$("#nu_intervalo").change(function(){
	if( $("#nu_intervalo").val() != "" ){
		$("#generaHorario").show();
	}else{
		$("#generaHorario").hide();
	}
});

function actualizaParametros(){
	var ci_especialista 	= $("#cedula_especialista").val();
	var nb_especialista 	= $("#nb_especialista").val();
	var nu_citas 			= $("#nu_citas").val();
	var nu_intervalo 		= $("#nu_intervalo").val();
	var in_turno 			= $("#in_turno").val();
	var in_especialidad 	= $("#in_especialidad").val();
	var hh_inicio 			= $("#hora_inicio").val();
	var parametro 			= "individual";
	
	$.ajax({
		  type: "POST",
		  url: "ServletParametrosHorario",
		  data: {
			  	ci_especialista : ci_especialista,
				nb_especialista : nb_especialista,
				nu_citas 		: nu_citas,
				nu_intervalo 	: nu_intervalo,
				in_turno 		: in_turno,
				in_especialidad : in_especialidad,
				hh_inicio 		: hh_inicio,
				parametro 		: parametro
		  },
		  success: function( result ) {
		    $( "#alerta" ).html( result );
		  }
		});
	/*
	alert(
	 	"cedula " + ci_especialista +
	 	"\n nombre " + nb_especialista + 	
	 	"\n especialidad " + in_especialidad + 	
	 	"\n turno " + in_turno + 			
	 	"\n inicio " + hh_inicio +
	 	"\n citas " + nu_citas + 	
	 	"\n intervalo " + nu_intervalo  		
	);
	*/
	
}
