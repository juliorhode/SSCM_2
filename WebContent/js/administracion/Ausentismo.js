function cedulaIni(){
	limpiar();
	listadoMedicosModal();
	
	/*
	$("#nb_especialista").val();
	$("#nb_tipo_especial").val();
	
	$("#fecha_ini").val();
	$("#fecha_fin").val();
	$("#hora_ini").val();
	$("#hora_fin").val();
	$("#txt_motivo").val();
	*/
	
	$("#fecha_ini, #fecha_fin").datepicker({
		dateFormat: "d/mm/yy",
		minDate: new Date(),
		showAnim: "fadeIn",
		beforeShowDay: $.datepicker.noWeekends,
		//beforeShowDay: noExcursion,
		maxDate: "+2m"
	});
	
	$("#hora_ini, #hora_fin").timepicker({
	    timeFormat: 'hh:mm p',
	    interval: 1,
	    minTime: '07',
	    maxTime: '07:00pm',
	    startTime: '07:00',
	    dynamic: false,
	    dropdown: true,
	    scrollbar: true
	});
	
	$("#cedula_especialista").keyup(function() {
        this.value = (this.value + '').replace(/[^0-9]/, '');
    });
	
	
	$("#cedula_especialista").keypress(function(evento){
		/*
		if(evento.which == 13){
			if( $("#cedula_especialista").val() == "" ){
				$('#medicos').modal('show');	
			}else{
				var cedulaMedico = $("#cedula_especialista").val();
				$.ajax({
		            type: "POST",
		            url: "ServletPrueba",
		            data: {
		            	cedula_especialista : cedulaMedico,
		            	parametro : "buscaMedico",
		            },
		            success: function( result ) {
		                $( "#datosMedico" ).html( result );
		                
		            }
		        });
			}
		}
		*/
		
		$.ajax({
			  type: "POST",
			  url: "/ServletAusentismo",
			  data: {
				  parametro : "medicoModal"
			  },
			  success: function( result ) {
				  $("#datosMedico").html( result );
			  }
			});
		
		
		
	});
	
	$("#cedula_especialista").blur(function() {
    			
        $("#txt_motivo").addClass("trans");
    });
	
	
	
}

function compruebaTecla(){
    var e = window.event;
    var tecla = (document.all) ? e.keyCode : e.which;
    if(tecla == 27){
    	limpiar();
    }
}

function listadoMedicosModal(){
	$.ajax({
        type: "POST",
        url: "ServletPrueba",
        data: {
        	parametro : "medicoModal",
        },
        success: function( result ) {
            $( "#medicosModal" ).html( result );
            $('#medicos').modal('show');
        }
    });
}

function limpiar(){
	$("#cedula_especialista").val("");
	
	$("#nb_especialista").val("");
	$("#in_tipo_especial").val("");
	$("#nb_tipo_especial").val("");
	
	$("#nb_especialista").remove();
	$("#in_tipo_especial").remove();
	$("#nb_tipo_especial").remove();
	$("#datosMedico label").remove();
	
	$("#fecha_ini").attr("disabled","disabled");
	$("#fecha_ini").val("");
	
	$("#fecha_fin").attr("disabled","disabled");
	$("#fecha_fin").val("");
	
	$("#hora_ini").attr("disabled","disabled");
	$("#hora_ini").val("");
	
	$("#hora_fin").attr("disabled","disabled");
	$("#hora_fin").val("");
	
	$("#txt_motivo").attr("disabled","disabled");
	$("#txt_motivo").val("");
	
	$("#agregaAusentismo").hide();
	
	 $("input:text:visible:first").focus();
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

$("input[name=cedula_especialista]").focus(function(){
	$("#fecha_ini").attr("disabled","disabled");
});
$("input[name=cedula_especialista]").blur(function(){
	if($("input[name=cedula_especialista]").val()==""){
		$("#fecha_ini").attr("disabled","disabled");	
	}else{
		$("#fecha_ini").removeAttr("disabled");	
	}
	
});

$("input[name=fecha_ini]").change(function(){
	$("#fecha_fin").removeAttr("disabled");
});
$("input[name=fecha_fin]").change(function(){
	$("#hora_ini").removeAttr("disabled");
});
$("input[name=hora_ini]").blur(function(){
	$("#hora_fin").removeAttr("disabled");
});

$("input[name=hora_fin]").blur(function(){
		$("#txt_motivo").removeAttr("disabled");
		$("#agregaAusentismo").show();	
		
});



function ausentismo(){

	var hora_ini = $("input[name=hora_ini]").val();
	var hora_fin = $("input[name=hora_fin]").val();

	if(hora_fin == hora_ini){
		alert("horas iguales");
		$("input[name=hora_fin]").val("");
		$("#agregaAusentismo").hide();
	}else{
		$("#agregaAusentismo").show();	
	
		var cedula_especialista = $("#cedula_especialista").val();
		var nb_especialista = $("#nb_especialista").val();
		var nb_tipo_especial = $("#nb_tipo_especial").val();
		
		var fecha_ini = $("#fecha_ini").val();
		var fecha_fin = $("#fecha_fin").val();
		var hora_ini = $("#hora_ini").val();
		var hora_fin = $("#hora_fin").val();
		var motivo = $("#txt_motivo").val();
		
		$.ajax({
	        type: "POST",
	        url: "ServletAusentismo",
	        data: {
	        	cedula_especialista : cedula_especialista,
	        	nb_especialista : nb_especialista,
	        	nb_tipo_especial : nb_tipo_especial,
	        	fecha_ini 		: fecha_ini,
	        	fecha_fin 		: fecha_fin,
	        	hora_ini 		: hora_ini,
	        	hora_fin 		: hora_fin,
	        	motivo 			: motivo,
	        	parametro 		: "inserta"
	        	
	        },
	        success: function( result ) {
	        	limpiar();
	            $( "#respuesta" ).html( result );
	        }
	   });
	}
}


