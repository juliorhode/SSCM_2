<!--***********************************************-->
<!--******************* ESTILOS *******************-->
<!--***********************************************-->
<link rel="stylesheet" href="css/cita_medica/general.css">
<link rel="stylesheet" href="css/cita_medica/cortesiaAdmin.css">

<!--**************************************************-->
<!--******************* JAVASCRIPT *******************-->
<!--**************************************************-->
<script src="js/administracion/cortesiaAdmin.js"></script>

<script type="text/javascript">
	inicial();
</script>

<div class="container-fluid">
    <div class="row content">
        <div class="col-md-12" id="panelCortesia">
                <!--************************************************************-->
                <!--******************* CONTENEDOR CORTESIA ********************-->
                <!--************************************************************ display: none;-->
                <div class="panel-group" id="panel-group-Cortesia" style=" height:auto;" float: left;>
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <h4 align="center" class="panel-title">Registro Cortesia</h4>
                        </div>
                        <div class="panel-body" id="panel-body-Cortesia">
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="cedula_Cortesia">C&eacute;dula</label>
                                    <input type="text" id="cedula_Cortesia" class="form-control" name="cedula_Cortesia">
                                </div>
                            </div>
                            <br><br><br><br>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="apellido1_Cortesia">Primer Apellido</label>
                                    <input type="text" id="apellido1_Cortesia" class="form-control" name="apellido1_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="apellido2_Cortesia">Segundo Apellido</label>
                                    <input type="text" id="apellido2_Cortesia" class="form-control" name="apellido2_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="nombre1_Cortesia">Primer Nombre</label>
                                    <input type="text" id="nombre1_Cortesia" class="form-control" name="nombre1_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <div class="form-group">
                                    <label for="nombre2_Cortesia">Segundo Nombre</label>
                                    <input type="text" id="nombre2_Cortesia" class="form-control" name="nombre2_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="empresa_Cortesia">Empresa</label>
                                    <input type="text" id="empresa_Cortesia" class="form-control" name="empresa_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-6">
                                <div class="form-group">
                                    <label for="telefono_Cortesia">Tel&eacute;fono</label>
                                    <input type="text" id="telefono_Cortesia" class="form-control" name="telefono_Cortesia">
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <label for="motivo_Cortesia">Motivo</label>
                                    <input type="text" id="motivo_Cortesia" class="form-control" name="motivo_Cortesia">
                                </div>
                            </div>
                        </div>
                        <!--Fin panel-body-->
                    </div>
                    <!--Fin panel panel-default-->
                </div>
            <!--Fin col-md-12-->
            <div class="col-md-12">
                <button class="btn btn-primary" type="button" style="margin-left: 45%;margin-top: 10px;">Procesar</button>
            </div>
        </div>
        <!--Fin col-md-9-->
    </div>
</div>



