function inicio(){
	$("#panel-body-Familiares input").attr("disabled","disabled");
	$("#panel-body-Tipo-Cita input").attr("disabled","disabled");
	$("#panel-body-Especialista-Fecha input").attr("disabled","disabled");
	$("#panel-body-horario input").attr("disabled","disabled");
	$("#tipoSolicitud").attr("disabled","disabled");
	$("#FechaSolicitud").attr("disabled","disabled");
	$("#panel-body-Tipo-Turno input").attr("disabled","disabled");
	$("#form-group-Especialistas").hide();
	
	$("#FechaSolicitud").datepicker({
		dateFormat: "dd/mm/yy",
		minDate: new Date(),
		showAnim: "fadeIn",
		beforeShowDay: $.datepicker.noWeekends,
		//beforeShowDay: noExcursion,
		maxDate: "+2m"
	});
	
	$("#cedula").keyup(function() {
        this.value = (this.value + '').replace(/[^0-9]/, '');
	});
}

$("#cedula").keypress(function(evento){
 	if(evento.which == 13){
		if( $("#cedula").val() == "" ){
			alert("No puede dejar el campo cedula en blanco");
		}else{
			buscaPersona();
		}
	}	 
});

function buscaPersona(){
	var cedulaEmp = $("#cedula").val();
	$.ajax({
		type:"POST",
		url:"Persona",
		data:{
			cedula:cedulaEmp,
			parametro:"SolicitanteAdmin",
		},
		success: function(result){
			$("#resultado").html(result);
			$("#cedula").blur();
			$("#panel-group-Familiares").hide();
			$("#panel-body-Familiares input").attr("disabled","disabled");
			$("#panel-body-Tipo-Cita input").removeAttr("disabled");
			$("#optMedico").removeAttr("disabled");
			$("#optOdonto").removeAttr("disabled");
			$("#optNutri").removeAttr("disabled");
			$("#optFisio").removeAttr("disabled");
			$('input:radio[name=otpTipoTurno]').prop('checked', false);
			$('input:radio[name=otpTipoCita]').prop('checked', false);
			$('input:radio[name=optFamiliar]').prop('checked', false);
			$("#mensajeCita").modal("show");
		}
		
	});
	
}//Fin function buscaPersona()

$("#si").click(function(){
	buscaFamilia();
	$("#mensajeCita").modal("hide");
	/*************************************
			REAJUSTE CSS DEL LATERAL
	 **************************************/
	$("#laterales").height(962);
	$("#Solicitante").val("FAM");
});

function buscaFamilia(){
	var cedulaEmp = $("#cedula").val();
	$.ajax({
	    type: "POST",
	    url: "Persona",
	    data: {
	    	cedula : cedulaEmp,
	    	parametro : "Fam",
	    },
	    success: function( result ) {
			
	    	$("#panel-body-Familiares").html( result );
	    	$("#cedula").blur();
	    	$("#panel-group-Familiares").show();
	    	$("#panel-body-Familiares input").removeAttr("disabled");
	    	$("#optMedico").attr("disabled","disabled");
			$("#optOdonto").removeAttr("disabled");
			$("#optNutri").attr("disabled","disabled");
			$("#optFisio").attr("disabled","disabled");
			$("#panel-body-horario input").attr("disabled","disabled");
			$('input:radio[name=otpTipoTurno]').prop('checked', false);
			$('input:radio[name=otpTipoCita]').prop('checked', false);
			$('input:radio[name=optFamiliar]').prop('checked', false);
			$("#form-group-Especialistas").hide();
	    }
	});
}//Fin function buscaFamilia()

$("#no").click(function(){
	alert("La cita será programada para el empleado.");
	$("#Solicitante").val("EMP");
	/*************************************
			REAJUSTE CSS DEL LATERAL
	 **************************************/
	$("#laterales").height(750);
});	

/**********************************
		PANEL TIPO CITA
**********************************/
$("#panel-body-Tipo-Cita input").click(function(){
	$("#tipoSolicitud").empty();
	$("#tipoSolicitud").attr("disabled","disabled");
	$("#panel-body-Tipo-Turno input").removeAttr("disabled");
	if($("input[name='otpTipoTurno']:radio").is(':checked')){
	//desactivamos la opcion marcada en el turno
	$('input:radio[name=otpTipoTurno]').prop('checked', false);
	$("#listadoMedico").select().val("");
	$("#FechaSolicitud").val("");
	$("#form-group-Especialistas").hide();
	$("#panel-group-horario").hide();
	$("#panel-group-Botones").hide();
	//document.getElementById("FechaSolicitud").disabled=false;
	$("#FechaSolicitud").attr("disabled","disabled");
	}
});

/**********************************
		PANEL TIPO TURNO
**********************************/
$("#panel-body-Tipo-Turno input").click(function(){
	$("#tipoSolicitud").empty();
	$("#tipoSolicitud").attr("disabled","disabled");
	$("#panel-group-horario").hide();
	$("#panel-group-Botones").hide();
	$("#FechaSolicitud").attr("disabled","disabled");
	$("#FechaSolicitud").val("");
	var tipoCita = $('input:radio[name=otpTipoCita]:checked').val();
	var tipoTurno = $('input:radio[name=otpTipoTurno]:checked').val();
	var co_cia_fisica = $("#co_cia_fisica").val();
	$.ajax({
		type: "POST",
		url: "Especialista",
		data: {
			tipo 			: tipoCita,
			turno 			: tipoTurno,
			co_cia_fisica 	: co_cia_fisica,
			parametro 		: "listadoE",
		},
		success: function( result ) {
			$("#form-group-Especialistas" ).html( result );
		  $("#form-group-Especialistas").show();
			$("#panel-group-Especialista-Fecha").show();
		}
	});
});

$("#listadoMedico").change(function(){
	listado();
});

function listado(){
	$("#tipoSolicitud").empty();
	$("#tipoSolicitud").attr("disabled","disabled");
	$("#listadoMedico").change(function(){
		if( $("#listadoMedico").select().val() !="" ){
			solicitud();
		}else{
			$("#tipoSolicitud").select().val("");
			$("#tipoSolicitud").attr("disabled","disabled");
		}
	});
}

function solicitud(){
	/*COLCOAR VALIDACION SI ES UN FAMILIAR*/
	$("#tipoSolicitud").empty();
	$("#tipoSolicitud").removeAttr("disabled");
	var tipoSolicitante = $('input:radio[name=otpTipoSolicitante]:checked').val();
	var tipoCita = $('input:radio[name=otpTipoCita]:checked').val();
	switch (tipoCita) { 
	case 'M': 
		$("#tipoSolicitud").append('<option></option>');
		$("#tipoSolicitud").append('<option>Consulta M&eacute;dica</option>');
		$("#tipoSolicitud").append('<option>Conformaci&oacute;n de Reposo</option>');
		break;
	case 'O': 
		if(tipoSolicitante == "EMP"){
			$("#tipoSolicitud").append('<option></option>');
			$("#tipoSolicitud").append('<option>Consulta Odontol&oacute;gica</option>');
			$("#tipoSolicitud").append('<option>Conformaci&oacute;n de Reposo</option>');
			$("#tipoSolicitud").append('<option>Evaluaci&oacute;n Odontol&oacute;gica</option>');
			$("#tipoSolicitud").append('<option>Asesor&iacute;a Odontol&oacute;gica</option>');	
		}else{
			$("#tipoSolicitud").append('<option></option>');
			$("#tipoSolicitud").append('<option>Evaluaci&oacute;n Odontol&oacute;gica</option>');
		}
		break;
	case 'N':
		$("#tipoSolicitud").append('<option></option>');
		$("#tipoSolicitud").append('<option>Consulta Nutricionista</option>');
		break;		
	case 'F': 
		$("#tipoSolicitud").append('<option></option>');
		$("#tipoSolicitud").append('<option>Consulta Fisioterapia</option>');
		break;
	}
}

$("#tipoSolicitud").change(function(){
	if( $("#tipoSolicitud").select().val() !="" ){
		//habilitamos la fecha
		$("#FechaSolicitud").removeAttr("disabled");
	}else{
		//deshabilitamos la fecha
		$("#FechaSolicitud").attr("disabled","disabled");
		$("#FechaSolicitud").val("");
		$("#panel-group-horario").hide();
		$("#panel-group-Botones").hide();
		/*
		if($("input[name='otpTipoCita']:radio").is(':checked') && $("input[name='otpTipoTurno']:radio").is(':checked') && $("#listadoMedico").select().val() !="" ){
			fecha();
		}
		*/
	}
});

$("#FechaSolicitud").change(function(){
	if($("#FechaSolicitud").val() !=""){
		fecha();
	}
	
});
function fecha(){
	var  fecha;
	var cedulaEmp = $("#cedula").val();
	var cedulaFam = $('input:radio[name=optFamiliar]:checked').val();
	var Solicitante = $("#Solicitante").val();
	var fechaCita = $("#FechaSolicitud").val();
	var cedulaMed = $("#listadoMedico").select().val();
	var tipoCita  = $('input:radio[name=otpTipoCita]:checked').val();
	var tipoTurno = $('input:radio[name=otpTipoTurno]:checked').val();
	var cedEmpleado = $("#cedula").val();
		
	$.ajax({
        type: "POST",
        url: "Especialista",
        data: {
        	parametro : "horario",
        	Solicitante : Solicitante,
        	tipo : tipoCita,
        	turno : tipoTurno,
        	fecha : fechaCita,
        	especialista : cedulaMed,
        	cedulaEmp : cedulaEmp,
        	cedulaFam :	cedulaFam,
        },
        success: function( result ) {
        	$("#panel-group-horario").show();
            $( "#form-group-horario" ).html( result );
            
            $('input:radio[name=optHora]').prop('checked', false);
            $("#panel-group-Botones").hide();
            
            $("#form-group-horario input").click(function(){
           	 	if ( $('input:radio[name=optHora]:checked').val() ){
           	 		$("#panel-group-Botones").show();
           	 	}
           });
        }
    });
}

/*************************************
PROCESAR CITA
**************************************/
$("#btnProcesar").click(function(){

	var fechaActual, fechaSolicitud;
	fechaActual = new Date();
	var ano = fechaActual.getFullYear();
	var mes = (fechaActual.getMonth()+1);
	var dia = fechaActual.getDate();
	fechaSolicitud = dia + "/" + mes + "/" +  ano;
	var tipoSolicitante = $("#Solicitante").val();
	if(tipoSolicitante == "EMP"){
		var cedulaEmp = $("#cedula").val();
		var cedulaFam = 0;
	}else{
		var cedulaEmp = $("#cedula").val();
		var cedulaFam = $('input:radio[name=optFamiliar]:checked').val();
	}
	var tipoCita 		= $('input:radio[name=otpTipoCita]:checked').val();	
	var cedulaMed 		= $("select[name='listadoMedico']").val();
	var nombreMed		= $("#listadoMedico option:selected").text();
	var tipoSolicitud 	= $("#tipoSolicitud option:selected").text();
	var fechaCita 		= $("input[name='FechaSolicitud']").val();
	var horaCita 		= $('input:radio[name=optHora]:checked').val();
	var usuario 		= $("#usuario").val();
	$.ajax({
		type: "POST",
		url: "Citas",
		data: {
		status          :   "S",
		solicitante 	: 	tipoSolicitante,
		cedulaEmp 		: 	cedulaEmp,
		cedulaFam 		: 	cedulaFam,
		tipoCita 		: 	tipoCita,
		cedulaMed 	    : 	cedulaMed,
		nombreMed       :   nombreMed,
		fechaCita 		: 	fechaCita,
		fechaSolicitud  :   fechaSolicitud,
		horaCita		:	horaCita,
		tipoSolicitud   :   tipoSolicitud,
		usuario         :   usuario,
		},
		success: function( result ) {
		$("#Modal").html( result );
		}
	});
	$('#cuerpo').load('JSP/Admin/CitaMedicaAdmin.jsp');

});