function inicial(){
	/*Deshabilitamos la opcion de listado, hasta que no se cargue el empleado*/
	$("#listadoCita").attr("disabled","disabled");
	/*Invocamos la ventana modal*/
	$("#SolicitudCita").click(function(){
		$('#modalUsuario').modal('show');
		$('body').on('shown.bs.modal', '#modalUsuario', function () {
		/************************************************************
		 * Al cargar el modal, enviamos el foco al primer elemento 
		 * que se encuentra dentro, y si se selecciona todo si 
		 * contiene algo
		 ************************************************************/	
		    $('input:visible:enabled:first', this).focus();
		    $("#cedula_usuario").select();
		});
	});
	$("#admin").click(function(){
		$('#modalAdmin').modal('show');
		$('body').on('shown.bs.modal', '#modalUsuario', function () {
		/************************************************************
		 * Al cargar el modal, enviamos el foco al primer elemento 
		 * que se encuentra dentro, y si se selecciona todo si 
		 * contiene algo
		 ************************************************************/	
		    $('input:visible:enabled:first', this).focus();
		    $("#usuario").select();
		});
	});
	 $("#cedula_usuario").keyup(function() {
	        this.value = (this.value + '').replace(/[^0-9]/, '');
	 });
	 
	$("#listadoCita").click(function(){
		 historialCitas();
	});
	
	/*Evento con la tecla Enter*/
	$("#cedula_usuario").keypress(function(evento){
		
		 	if(evento.which == 13){
				if( $("#cedula_usuario").val() == "" ){
					alert("No puede dejar el campo cedula en blanco");
				}else{
					$("#modalUsuario").modal("hide");
					cedulaModal();
				}
			}	 
	});
		
	$("#enviaCedula").click(function(){
		if( $("#cedula_usuario").val() == "" ){
			alert("No puede dejar el campo cedula en blanco");
			$("#modalUsuario").modal("show");
		}else{
			$("#modalUsuario").modal("hide");
			cedulaModal();	
		}
		
	});
	 
	$("#salir").click(function(){
		window.close("http://172.24.37.243:8080/ve.org.citasmedicas/index.jsp");
	});
	
	$("#lateral").height(862).css({
    	background : "#335f7d",
    	width: "280px"
    });
	
}

function cedulaModal(){
	
	var cedula = $("#cedula_usuario").val();
	$.ajax({
	    type: "POST",
	    url: "Persona",
	    data: {
	    	cedula : cedula,
	    	parametro : "Solicitante",
	    },
	     
	    success: function( result ) {
			/**********************************
		      Info Lateral derecho
			 **********************************/
	    	$("#lateral_der").html( result );
	    	/*
	    	if($("#cambioUsuario").val() != ""){
	    		$("#lateral_der").show();
		    	$("#listado_cita").show();
		    	$("#listadoCita").show();
	    	}
	    	*/

	    	if ( screen.width > 800 && screen.width <= 1370){
    			var ancho = $( window ).width();
    			//Ajuste del contenido para telefono
    			if( ancho <= 761 ){
    				$("#cuerpo").height(true);
    		    //Ajuste del contenido para pantalla media
    			}else if( ancho >= 761 ){
    				$("#cuerpo").height(435);	
    			}
    			
    		}
	    	
	    	$("#cambioUsuario").click(function(){
	    		$("#cedula_usuario").val('');
	    		$('#modalUsuario').modal('show');
	    		
	    	});
	    },
	    /*
	    error: function(theRequest,textStatus, errorThrown){
	    	alert (theRequest.responseText); 
            alert(errorThrown);

	    }
	    */
	});
}

function historialCitas(){
	var cedula = $("#cedula_usuario").val();
	$.ajax({
	    type: "POST",
	    url: "Especialista",
	    data: {
	    	cedulaEmp : cedula,
	    	parametro : "historialCitas",
	    },
	    success: function( result ) {
	    	$("#cuerpo").html( result );
	    }
	});
}

function anula(){
	$('input[name=optsolicitud]').click(function(){
		var cedula = $("#cedula_usuario").val();
		var nuSolicitud = $('input:radio[name=optsolicitud]:checked').val();
		if($("input[name='optsolicitud']:radio").is(':checked')){
			$("#anular").show();
			$("#anular").click(function(){
				$.ajax({
				    type: "POST",
				    url: "Especialista",
				    data: {
				    	cedulaEmp : cedula,
				    	nuSolicitud : nuSolicitud,
				    	parametro : "anulacionCita",
				    },
				    success: function( result ) {
				    	$("#cuerpo").html( result );
				    	$('#mensajeAnulacion').modal('show');
				    }
				});
			});
		}else{
			$("#anular").hide();
		}
	});
}
