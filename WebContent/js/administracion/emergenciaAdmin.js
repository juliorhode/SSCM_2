function inicio(){
	$("#panel-group-Especialista").hide();
	$("#panel-group-Motivo").hide();
	$("#panel-group-Especialista select").attr("disabled","disabled");
	$("#panel-group-Motivo input").attr("disabled","disabled");
	$("#panel-group-Motivo input").val("");
	$("#panel-group-Especialista select").val("");
	$("#panel-body-Tipo-Cita input").removeAttr("disabled");
	$("#cedula").attr('disabled','disabled');
	/**********************************
		PANEL TIPO CITA
	**********************************/
	$("#panel-body-Tipo-Cita input").click(function(){
			$("#cedula").removeAttr("disabled");
			$("#cedula").focus();
			$("#panel-group-Especialista select").val("");
			$("#panel-group-Motivo input").val("");
			$("#panel-group-Especialista select").attr("disabled","disabled");
			$("#panel-group-Motivo input").attr("disabled","disabled");
			$("#panel-group-Especialista").hide();
			$("#panel-group-Motivo").hide();
			$("#panel-group-Botones").hide();
			$('input:radio[name=optFamiliar]').prop('checked', false);
			$("#panel-group-Familiares").hide();
	});//Fin $("#panel-body-Tipo-Cita input").click(function()
	$("#cedula").keypress(function(evento){
	 	if(evento.which == 13){
			if( $("#cedula").val() == "" ){
				alert("No puede dejar el campo cedula en blanco");
			}else{
				buscaPersona();
			}//Fin if
		}//Fin if 
	});//Fin $("#cedula").keypress(function(evento)
}//Fin function inicio()
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
			$('input:radio[name=optFamiliar]').prop('checked', false);
			if( $("#flag").val() == "1" ){
				$("#mensajeCita").modal("show");
			}else{
				$("#mensajeCita").modal("hide");
				$("#panel-group-Motivo input").attr('disabled','disabled');
				$("#panel-group-Motivo").hide();
				$("#panel-group-Especialista").hide();
			}//Fin if
		}//Fin success: function(result)
	});//Fin $.ajax()
}//Fin function buscaPersona()
$("#si").click(function(){
	buscaFamilia();
	$("#mensajeCita").modal("hide");
	$("#Solicitante").val("FAM");
	/*************************************
			REAJUSTE CSS DEL LATERAL
	 **************************************/
	$("#laterales").height(762);//Le quite 200
});//Fin $("#si").click(function()
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
			$('input:radio[name=optFamiliar]').prop('checked', false);
			
			$("#panel-body-Familiares input").click(function(){
				var fam = $('input:radio[name=optFamiliar]:checked').val();
				buscaEspecialista();
				$("#panel-group-Especialista").show();
				$("#panel-group-Especialista select").removeAttr("disabled");
				$("#panel-group-Motivo input").attr("disabled","disabled");
				$("#panel-group-Motivo input").val("");
				$("#panel-group-Motivo").hide();
				/*
				$("#panel-group-Motivo").show();
				$("#panel-group-Motivo input").removeAttr("disabled");
				$("#panel-group-Motivo input").focus();
				*/
			});
	    }//Fin success: function( result )
	});//Fin $.ajax()
}//Fin function buscaFamilia()
$("#no").click(function(){
	buscaEspecialista();
	$("#mensajeCita").modal("hide");
	$("#Solicitante").val("EMP");
	alert("La cita será programada para el empleado.");
	/*************************************
			REAJUSTE CSS DEL LATERAL
	 **************************************/
	//$("#laterales").height(750);
});//Fin $("#no").click(function()
function buscaEspecialista() {
	var tipoCita 		= $('input:radio[name=otpTipoCita]:checked').val();	
	$.ajax({
        type: "POST",
        url: "Especialista",
        data: {
        	tipo : tipoCita,
        	parametro : "listadoEm",
        },
        success: function( result ) {
        	$("#form-group-Especialistas" ).html( result );
        	$("#panel-group-Especialista").show();
        }//Fin success: function( result )
    });//Fin $.ajax()
}//Fin function buscaEspecialista()
$("#form-group-Especialistas").change(function(){
	if( $("#form-group-Especialistas select").val()!=""){
		$("#panel-group-Motivo").show();
		$("#panel-group-Motivo input").removeAttr("disabled");
		$("#panel-group-Motivo input").focus();	
	}else{
		$("#panel-group-Motivo input").attr("disabled","disabled");
		$("#panel-group-Motivo input").val("");
		$("#panel-group-Motivo").hide();
		$("#panel-group-Botones").hide();
	}//Fin if
});//Fin $("#form-group-Especialistas").change(function()
$("#panel-group-Motivo input").on('keyup', function(){
    var value = $(this).val().length;
    if(value>0){
    	$("#panel-group-Botones").show();
    }else{
    	$("#panel-group-Botones").hide();
    }
}).keyup();//Fin $("#panel-group-Motivo input").on('keyup', function()

/*************************************
		PROCESAR EMERGENCIA
**************************************/
$("#btnProcesar").click(function(){
	var fechaActual, fechaSolicitud;
	fechaActual = new Date();
	var ano = fechaActual.getFullYear();
	var mes = (fechaActual.getMonth()+1);
	var dia = fechaActual.getDate();
	fechaSolicitud = dia + "/" + mes + "/" +  ano;
	/*ESTO ES PARA DETERMINAR QUIEN LA SOLICITA*/
	var tipoSolicitante 	= $("#Solicitante").val();
	if(tipoSolicitante == "EMP"){
		var cedulaEmp 		= $("#cedula").val();
		var cedulaFam 		= 0;
	}else{
		var cedulaEmp = $("#cedula").val();
		var cedulaFam = $('input:radio[name=optFamiliar]:checked').val();
	}//Fin if
	/*AHORA LO CAMBIAMOS PARA INDICARLE QUE ES UNA EMERGENCIA AL SERVLET*/
	tipoSolicitante = "EMERG";
	var tipoCita 		= $('input:radio[name=otpTipoCita]:checked').val();	
	var cedulaMed 		= $("select[name='listadoMedico']").val();
	var nombreMed		= $("#listadoMedico option:selected").text();
	var fechaCita 		= fechaSolicitud;
	var usuario 		= $("#usuario").val();
	var motivo			= $("#txtMotivo").val();
	$.ajax({
		type: "POST",
		url: "Citas",
		data: {
			/*Para identificar en el servlet que es una emergencia*/
			solicitante 	: 	tipoSolicitante,
			
			status          :   "S",
			cedulaEmp 		: 	cedulaEmp,
			cedulaFam 		: 	cedulaFam,
			/*Medica u Odontologica*/
			tipoCita 		: 	tipoCita,
			
			cedulaMed		:	cedulaMed,
			nombreMed       :   nombreMed,
			fechaCita 		: 	fechaSolicitud,
			/*Medica u Odontologica*/
			especialidad	:	tipoCita,
			
			tipoSolicitd	:	"Emergencia",
			motivo			:	motivo,
			usuario			:	usuario,
		},
		success: function( result ) {
			$("#Modal").html( result );
			//$("#Modal").html( result );
				//alert(result);
				if (result = 1){
					$("#Modal").html( result );
				}else{
					$("#Modal2").html( result );
				}
					
					//alert("Emergencia registrada");
					
				//}else{
					//alert("Emergencia no registrada");
					
					//$('#mensajeModalFalse').modal('show');
				//}
			
		}//Fin success: function( result )
	});//Fin $.ajax()

	$('#cuerpo').load('JSP/Admin/EmergenciaAdmin.jsp');
});//Fin $("#btnProcesar").click(function()