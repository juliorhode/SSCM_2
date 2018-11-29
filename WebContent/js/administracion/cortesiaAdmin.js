function inicial() {
	 /*SOLO SE ACEPTAN NUMEROS*/
    $("#cedula_Cortesia").focus();
    $("#cedula_Cortesia").keyup(function() {
        this.value = (this.value + '').replace(/[^0-9]/, '');
    });
    /*SOLO SE ACEPTAN LETRAS*/
    $("#apellido1_Cortesia, #apellido2_Cortesia, #nombre1_Cortesia, #nombre2_Cortesia").keyup(function() {
        this.value = (this.value + '').replace(/[^a-z]/, '');
    });
    /*SOLO SE ACEPTAN LETRAS*/
    $("#empresa_Cortesia").keyup(function() {
        this.value = (this.value + '').replace(/[^a-z ]/, '');
    });
    $("#telefono_Cortesia").keyup(function() {
        this.value = (this.value + '').replace(/[^0-9-/]/, '');
    });
    $("#apellido1_Cortesia, " +
    		"#apellido2_Cortesia, " +
    		"#nombre1_Cortesia, " +
    		"#nombre2_Cortesia, " +
    		"#empresa_Cortesia, " +
    		"#telefono_Cortesia").blur(function() {
    			
        $("#apellido1_Cortesia, " +
        		"#apellido2_Cortesia, " +
        		"#nombre1_Cortesia, " +
        		"#nombre2_Cortesia, " +
        		"#empresa_Cortesia, " +
        		"#telefono_Cortesia," +
        		"#motivo_Cortesia").addClass("trans");
    });
}
       
