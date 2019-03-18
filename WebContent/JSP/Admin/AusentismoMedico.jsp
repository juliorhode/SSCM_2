<!--***********************************************-->
<!--******************* ESTILOS *******************-->
<!--***********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">


<script src="js/administracion/Ausentismo.js"></script>
<style>
	#cargaIndividual{
		margin-top: 20px;
		margin-left: 5px;
		height: 650px;
	}
</style>
<!-- 1) Funcionalidad a los datapicker -->
<!-- 2) Campo cedula, colocar validacion en linea solo numneros -->
<!-- 3) Armar Array de la tabla calendario para enviar por ajax -->
<!-- 4) en el motivo, colocar capitalize -->
<!-- 5) Enviar el objeto por ajax al servlet -->
<script>
        $(document).ready(function(){
        	
            $("#panelFecha").hide();
            $("#panelCalendario").hide();
            var txtSemana = new Array();
            $("#panel-tablaDias").hide();

            $("input:radio[name=otpTipoAusentismo]").click(function(){
                var tipoAusentismo = $('input:radio[name=otpTipoAusentismo]:checked').val();
                if (tipoAusentismo=="conti") {
                    $("#panelFecha").show();
                    $("#panelCalendario").hide();
                    $("#laterales").height(688);
                }else{
                    $("#panelFecha").hide();
                    $("#panelCalendario").show();
                    $("#laterales").height(1070);
                }
            });

            $("#tipoMes").change(function(){
                //Obtenemos el valor del mes seleccionado
                var seleccion = $("#tipoMes").val();
                if (seleccion == 0) {
                    $("#tablaDias").empty();
                    $("#panel-tablaDias").hide();
                }else{
                    $("#panel-tablaDias").show();
                    //Obtenemos la cantidad de dias del mes, invocando la funcion mes()
                    var diasMes = mes(seleccion);
                }
                txtSemana = diaSemana(diasMes,seleccion);
                //console.log("Este es el array: " +txtSemana[24]);
                //Con el valor obtenido de los dias, generamos la tabla
                generaTabla(diasMes,txtSemana);
            });
            
            $("#cedula_especialista").keypress(function(evento){
            	if(evento.which == 13){
            		listadoMedicosModal();
            	}
            });
            
            $("#agregaAusentismo").click(function(){
            	var json = {nombre : "Julio", apellido : "Ferrer"};
            	$.ajax({
                    type: "POST",
                    url: "ServletAusentismo",
                    data: {
                    	parametro 	: "prueba",
                    	json		: json
                    },
                    success: function( result ) {
                        alert(result);
                    }
                });
            
            
            
            
        	});
            
            
        });
        
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
        
        function diaSemana(dia,mes){
            
            var ano = new Date().getFullYear();
            // var fecha = new Date(ano,mes-1,1);
            // var diaSemana = ["Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"];
            // var txtSemana = diaSemana[fecha.getDay()];
            var diaSemana = ["Domingo","Lunes","Martes","Miercoles","Jueves","Viernes","Sabado"];
            var fecha;
            var txtSemana = new Array();
            for(var i=1; i < dia + 1;i++){
                fecha = new Date(ano,mes-1,i);
                fechaCorta = i+"/"+mes+"/"+ano;
                //console.log(fechaCorta + "-->" + diaSemana[fecha.getDay()]);
                txtSemana.push( diaSemana[fecha.getDay()] );
            }
            //console.log(txtSemana);
            return txtSemana;
        }
        /******************************************************************************************
        * Esta funcion se encarga de determinar la cantidad de dias que tiene el mes seleccionado *
        *******************************************************************************************/
        function mes (numeroMes){
            /*Obtenemos el año actual*/
            var ano = new Date().getFullYear();
            /*Recibimos la cantidad de dias del mes*/
            var numeros = new Date( ano,numeroMes,0).getDate();
            /*Retornamos la cantidad de dias*/
            return numeros;
        }
             
            
        /**********************************************************************************************
        * Esta funcion se encarga de construir dinamicamente, la tabla calendario del mes seleccionado *
        **********************************************************************************************/
        function generaTabla(diasMes,diaSemana){
            /*Vacioamos el contenido de la tabla*/
            $("#tablaDias").empty();
            /*Iniciamos la apertura de la tabla*/
            $("#tablaDias").append("<table><tr>");
            for (var i=1; i  < diasMes + 1; i++){
                if (i < 10) {
                    $("#tablaDias").append("<td><td style='padding: 10px;text-align: center;color:#335f7d '><input type='checkbox' name = 'chkDiaMes' value='0" + i + "'><br><label>"+ diaSemana[i-1] +" 0"+i+"</label></td>");
                }else{
                    $("#tablaDias").append("<td><td style='padding: 10px;text-align: center;color:#335f7d '><input type='checkbox' name = 'chkDiaMes' value='" + i + "'><br><label>"+ diaSemana[i-1] +" "+i+"</label></td>");
                }
                /*esto se realiza para cerrar la fila cada 7 dias y aperturar una nueva Fila*/
                
                switch (i) {
                    case 7:
                        $("#tablaDias").append('</tr><tr>');
                    break;
                    case 14:
                        $("#tablaDias").append('</tr><tr>');
                    break;
                    case 21:
                        $("#tablaDias").append('</tr><tr>');
                    break;
                    case 28:
                        $("#tablaDias").append('</tr><tr>');
                    break;
                    default:
                        break;
                }
            }
            /*Cerramos la tabla*/
            $("#tablaDias").append("</table>");
        }
    </script>

<div id="cargaIndividual">
    <div class="container-fluid">
        <div class="row content">
            <div class="col-sm-12">
	            <div class="panel panel-default">
		          	<div class="panel-heading">
		           		<h4 class="panel-title" align="center" style="font-weight: bold;">Datos del Especialista</h4>
		            </div>
	            </div>
            </div>
            <div class="col-sm-12">
                <div class="col-sm-2">
	                <div class="form-group">
	                    <label for="cedula_especialista">C&eacute;dula</label>
	                    <input type="text" id="cedula_especialista" data-toggle="tooltip" title="Puede presionar la tecla enter y se desplegará una ventana con los medicos" autofocus class="form-control" name="cedula_especialista">
	                </div>
	            </div>
                <div id = "datosMedico">
	                 <!-- 
	                		RESULTADOS CON AJAX:
	                		
	                		NOMBRE Y APELLIDO DEL ESPECIALISTA ----- nb_especialista
	                		TIPO (DIMINUTIVO DE LA ESPECIALIDAD) ---- in_tipo_especial
	                		TIPO DE ESPECIALIDAD  ------ nb_tipo_especial
	                 -->
                </div>
            </div>
            <div class="col-sm-12">
            	<br><br>
	            <div class="panel panel-default">
		          	<div class="panel-heading">
		           		<h4 class="panel-title" align="center" style="font-weight: bold;">Datos del Ausentismo</h4>
		            </div>
                    <div class="panel-body">
                        <div class="col-md-2">
                        	<div class="panel panel-default">
	                        	<div class="panel-body">
			                        <div class="radio-inline">
		                                <label><input name="otpTipoAusentismo" id="otpContinuo" type="radio" value="conti">Continuo</label>
			                            <br><br>
		                                <label><input name="otpTipoAusentismo" id="otpIntercalado" type="radio" value="inter">Intercalado</label>
			                        </div>
			                    </div>
		                    </div>
                        </div>
                        <div class="col-md-6" id="panelFecha">
			                <div class="panel panel-default">
			                    <div class="panel-body">
			                        <div class="col-md-6">
			                            <label for="txtFechaInicio">Fecha Inicio</label>
			                            <input type="button" class="form-control" id="txtFechaInicio">
			                        </div>
			                        <div class="col-md-6">
			                                <label for="txtFechaFin">Fecha Fin</label>
			                                <input type="button" class="form-control" id="txtFechaFin">
			                        </div>
			                    </div>
			                </div>
			            </div>
			            <div class="col-md-10" id="panelCalendario">
			                <div class="panel panel-default">
			                    <div class="panel-body">
			                        <div class="col-md-2">
			                            <label for="tipoMes">Tipo de Solicitud:</label><br>
			                            <select name='tipoMes' class='form-control' id='tipoMes'>
			                                <!-- <option value=""></option> -->
			                                <option value="0"></option>
			                                <option value="1">Enero</option>
			                                <option value="2">Febrero</option>
			                                <option value="3">Marzo</option>
			                                <option value="4">Abril</option>
			                                <option value="5">Mayo</option>
			                                <option value="6">Junio</option>
			                                <option value="7">Julio</option>
			                                <option value="8">Agosto</option>
			                                <option value="9">Septiembre</option>
			                                <option value="10">Octubre</option>
			                                <option value="11">Noviembre</option>
			                                <option value="12">Diciembre</option>
			                            </select>
			                        </div>
			                        <div class="col-md-8">
			                            <div class="panel panel-default" id="panel-tablaDias">
			                                <div class="panel-body" id="tablaDias">
			                                    
			                                </div>
			                            </div>
			                        </div>
			                    </div>
			                </div>
            			</div>
                    </div>
	            </div>
            </div>
            <div class="col-sm-12">
               	<div class="form-group">
                    <label for="txt_motivo" style="text-align: right;">Motivo</label>
                    <textarea class="form-control" id="txt_motivo" data-toggle="tooltip" title="Puede colocar la descripcion por el cual el especialista va a estar ausente" rows="5" name="txt_motivo"></textarea>
                </div>
            </div>
 			<div class="col-sm-6">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
				<button  type="button"  style=" margin-left:100%; margin-top: 3%;" id="agregaAusentismo" name="agregaAusentismo" class="btn btn-success">Guardar</button>
			</div>
			<div class="col-sm-12" id="respuesta">
				<!--style="margin-left: 35%;" MANEJARLO CON @MEDIA SCREEN-->
			</div>
		</div>
    </div>
</div>

<!--*********************************************************************-->
<!--*********************************************************************-->
<!--******************* CONTENEDOR VENTANA EMERGENTE ********************-->
<!--*********************************************************************  -->

<div class="modal fade" id="medicos" role="dialog" data-keyboard="false" data-backdrop="static" style="width: auto;height: auto;">
    <div class="modal-dialog">
        <div class="modal-content">
    
            <!-- Encabezado del Modal -->
            <div class="modal-header">
                <h3 class="modal-title" style="color:#1B0082;text-align: center;font-size: 24px;">Listado de Medicos Disponibles</h3>
            </div>
    
            <!-- Cuerpo del Modal -->
            <div class="modal-body" id="cuerpoModal">
					<table class='table table-bordered table-striped'>
						<thead>
							<tr>
								<th>Cédula</th>
								<th>Nombre del Especialista</th>
								<th>Especialidad</th>
							</tr>
						</thead>
						<tbody id = "medicosModal">
						
							
						</tbody>
					</table><!-- Fin table -->
            </div>
            <!-- Pie del Modal -->
            <div class="modal-footer">
                <input type="button" class="btn btn-primary" value="Cancelar" id="aceptarFalse" style="width: 100px;margin-left:360px;" data-dismiss="modal">
            </div>
        </div>
    </div>
</div>



<!-- <script type="text/javascript"> -->
<!-- 	cedulaIni(); -->
<!--  	window.onkeyup = compruebaTecla; -->
<!-- </script> -->
