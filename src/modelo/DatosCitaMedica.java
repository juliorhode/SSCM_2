package modelo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bean.Cita;

public class DatosCitaMedica{
	
	public DatosCitaMedica(Connection conexion) {
		super();
		this.con = conexion;
	}
	public DatosCitaMedica(String tipoCita, String turno, String medico, String fe_cita, Connection conexion) {
		super();
		this.tipoCita = tipoCita;
		this.turno = turno;
		this.medico = medico;
		this.fe_cita = fe_cita;
		this.con = conexion;
	}

	public DatosCitaMedica(int ci_empleado, Connection conexion) {
		super();
		this.ci_empleado = ci_empleado;
		this.con = conexion;
	}
	public DatosCitaMedica(int ci_empleado, String in_ti_cita, String fe_cita, Connection conexion) {
		super();
		this.ci_empleado = ci_empleado;
		this.in_ti_cita = in_ti_cita;
		this.fe_cita = fe_cita;
		this.con = conexion;
		//this.cedulaFam = 0;
	}
	public DatosCitaMedica(int ci_empleado, int ci_familiar, String in_ti_cita, String fe_cita, Connection conexion) {
		super();
		this.ci_empleado = ci_empleado;
		this.ci_familiar = ci_familiar;
		this.in_ti_cita = in_ti_cita;
		this.fe_cita = fe_cita;
		this.con = conexion;
	}

	/**
	 * Aqui verificamos si el empleado posee ya una cita programada.
	 * Una persona no puede tener un mismo tipo de cita (odontologica) 
	 * el mismo dia que se encuentre en status solicitada. No importa si 
	 * son medicos diferentes. 
	 * */
	public int getCantidadCitasEmp() throws Exception {
		// Establecemos una coneccion
		//con = getConexion();
		
		// Creamos la consulta para la busqueda de citas
		String sqlCantidad = "SELECT COUNT(*) AS CANT FROM SALUD.CM_CITA_MEDICA "
				+ "WHERE CI_EMPLEADO = ? and "
				+ "TO_CHAR(FE_CITA,'DD/MM/YYYY') = ? and IN_TI_CITA = ? AND ST_CITA ='S'";

		// Pasamos el sql a la consulta con parametros
		pst_buscaCita = con.prepareStatement(sqlCantidad);
		
		// Pasamos los parametros que vamos a necesitar en la consulta
		pst_buscaCita.setInt(1, ci_empleado);
		pst_buscaCita.setString(2, fe_cita);
		pst_buscaCita.setString(3, in_ti_cita);
		
		// Ejecutamos la consulta
		cursor = pst_buscaCita.executeQuery();
		
		// Recorremos los datos que se encuentran en el cursor
		try {
			while(cursor.next()) {
				cantidad = cursor.getInt(1);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			
			cursor.close();
			pst_buscaCita.close();
			//con.close();
			
		}
		return cantidad;
	}
	
	/**
	 * Aqui verificamos si el familiar posee ya una cita programada.
	 * Una persona no puede tener un mismo tipo de cita (odontologica) 
	 * el mismo dia que se encuentre en status solicitada. No importa si 
	 * son medicos diferentes. 
	 * */
	public int getCantidadCitasFam() throws Exception {
		// Establecemos una coneccion
		//con = getConexion();
		
		// Creamos la consulta para la busqueda de citas
		String sqlCantidad = "SELECT COUNT(*) AS CANT FROM SALUD.CM_CITA_MEDICA "
				+ "WHERE CI_FAMILIAR =? and "
				+ "TO_CHAR(FE_CITA,'DD/MM/YYYY')=? and IN_TI_CITA=? AND ST_CITA ='S'";
		
		// Pasamos el sql a la consulta con parametros
		pst_buscaCita = con.prepareStatement(sqlCantidad);
		
		// Pasamos los parametros que vamos a necesitar en la consulta
		pst_buscaCita.setInt(1, ci_familiar);
		pst_buscaCita.setString(2, fe_cita);
		pst_buscaCita.setString(3, in_ti_cita);
		
		// Ejecutamos la consulta
		cursor = pst_buscaCita.executeQuery();
		
		// Recorremos los datos que se encuentran en el cursor
		try {
			while(cursor.next()) {
				cantidad = cursor.getInt(1);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();
			pst_buscaCita.close();
			//con.close();
		}
		return cantidad;
	}
	
	public boolean buscaRegistro(Cita nuevacita) throws SQLException {
		boolean flag = false;
		String sql = "select * "
				   + "from salud.cm_cita_medica "
				   + "where  fe_cita = to_date(?,'DD/MM/YYYY') and "
				   + "hh_cita = ? and "
				   + "ci_especialista = ? and "
				   + "st_cita = 'S'";
		try {
			pst_buscaCita = con.prepareStatement(sql);
			pst_buscaCita.setString(1, nuevacita.getFe_cita());
			pst_buscaCita.setString(2, nuevacita.getHh_cita());
			pst_buscaCita.setInt(3, nuevacita.getCi_especialista());
			cursor = pst_buscaCita.executeQuery();	
			if (cursor.next()) {
				flag = true;
			}else {
				flag = false;
			}
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();
			pst_buscaCita.close();
			//con.close();
		}
		
		return flag;
	}
	
	public boolean agregarCita(Cita nuevacita, String especialidadMedico) throws Exception{
		boolean flag = false;
		//obtener la conexion con la BBDD
		//con 	= null;
		pst_buscaCita = null;

		try {
			//Creamos la conexion
			//con = getConexion();
			
			//Creamos la instruccion sql para insertar (consulta preparada)
			String citaSQL = "insert into salud.cm_cita_medica "
								+ "(nu_solicitud, "
								+ "st_cita, "
								+ "ci_empleado, "
								+ "nb_empleado, "
								+ "ci_familiar, "
								+ "nb_familiar, "
								+ "in_ti_cita, "
								+ "in_especialidad,"
								+ "fe_cita, "
								+ "hh_cita, "
								+ "ci_especialista, "
								+ "nb_ti_solicitud,"
								+ "fe_atencion,"
								+ "hh_atencion,"
								+ "nb_usuario_modific, "
								+ "tx_dato_modificado,"
								+ "fe_solicitud, "
								+ "hh_solicitud) "
							+ "values(salud.seq_nu_solicitud.nextval, ?, ?, ?, ?, ?, ?, ?, to_date(?,'DD/MM/YYYY'), ?, ?, ?, to_date(?,'DD/MM/YYYY'), ?, ?, ?, to_date(?,'DD/MM/YYYY'), ?)";
			
			
			pst_buscaCita = con.prepareStatement(citaSQL);
						
			//Establecemos los parametros para la cita
			pst_buscaCita.setString	(1, nuevacita.getSt_cita());
			pst_buscaCita.setInt	(2, nuevacita.getCi_empleado());
			pst_buscaCita.setString	(3, nuevacita.getNb_empleado());
			pst_buscaCita.setInt	(4, nuevacita.getCi_familiar());
			pst_buscaCita.setString	(5, nuevacita.getNb_familiar());
			pst_buscaCita.setString	(6, especialidadMedico);
			pst_buscaCita.setString	(7, nuevacita.getIn_ti_cita());
			pst_buscaCita.setString	(8, nuevacita.getFe_cita());
			pst_buscaCita.setString	(9, nuevacita.getHh_cita());
			pst_buscaCita.setInt	(10, nuevacita.getCi_especialista());
			pst_buscaCita.setString	(11, nuevacita.getNb_ti_solicitud());
			pst_buscaCita.setString	(12, nuevacita.getFe_atencion());
			pst_buscaCita.setString	(13, nuevacita.getHh_atencion());
			pst_buscaCita.setString	(14, nuevacita.getNb_usuario_modific());
			pst_buscaCita.setString	(15, nuevacita.getTx_dato_modificado());
			pst_buscaCita.setString	(16, nuevacita.getFe_solicitud());
			pst_buscaCita.setString	(17, nuevacita.getHh_solicitud());
			
			//Ejecutamos la instruccion sql
			pst_buscaCita.execute();
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			flag = false;
			return flag;
		}finally {
			pst_buscaCita.close();
			//con.close();
		}
		flag = true;
		return flag;
	}
	
	public boolean agregarCita(Cita nuevacita) throws Exception{
		boolean flag = false;
		//obtener la conexion con la BBDD
		//con 	= null;
		pst_buscaCita = null;

		try {
			//Creamos la conexion
			//con = getConexion();
			
			//Creamos la instruccion sql para insertar (consulta preparada)
			String citaSQL = "insert into salud.cm_cita_medica "
								+ "(nu_solicitud, "
								+ "st_cita, "
								+ "ci_empleado, "
								+ "nb_empleado, "
								+ "ci_familiar, "
								+ "nb_familiar, "
								+ "in_ti_cita, "
								+ "in_especialidad, "
								+ "fe_cita, "
								+ "hh_cita, "
								+ "ci_especialista, "
								+ "nb_ti_solicitud,"
								+ "fe_atencion,"
								+ "hh_atencion,"
								+ "nb_usuario_modific, "
								+ "fe_solicitud, "
								+ "hh_solicitud,"
								+ "tx_dato_modificado) "
							+ "values(salud.seq_nu_solicitud.nextval, ?, ?, ?, ?, ?, ?, ?, to_date(?,'DD/MM/YYYY'), ?, ?, ?, to_date(?,'DD/MM/YYYY'), ?, ?, to_date(?,'DD/MM/YYYY'), ?,?)";
			
			pst_buscaCita = con.prepareStatement(citaSQL);
						
			//Establecemos los parametros para la cita
			pst_buscaCita.setString	(1, nuevacita.getSt_cita());
			pst_buscaCita.setInt	(2, nuevacita.getCi_empleado());
			pst_buscaCita.setString	(3, nuevacita.getNb_empleado());
			pst_buscaCita.setInt	(4, nuevacita.getCi_familiar());
			pst_buscaCita.setString	(5, nuevacita.getNb_familiar());
			pst_buscaCita.setString	(6, nuevacita.getIn_ti_cita());
			pst_buscaCita.setString	(7, nuevacita.getIn_especialidad());
			pst_buscaCita.setString	(8, nuevacita.getFe_cita());
			pst_buscaCita.setString	(9, nuevacita.getHh_cita());
			pst_buscaCita.setInt	(10, nuevacita.getCi_especialista());
			pst_buscaCita.setString	(11, nuevacita.getNb_ti_solicitud());
			pst_buscaCita.setString	(12, nuevacita.getFe_atencion());
			pst_buscaCita.setString	(13, nuevacita.getHh_atencion());
			pst_buscaCita.setString	(14, nuevacita.getNb_usuario_modific());
			pst_buscaCita.setString	(15, nuevacita.getFe_solicitud());
			pst_buscaCita.setString	(16, nuevacita.getHh_solicitud());
			pst_buscaCita.setString	(17, nuevacita.getTx_dato_modificado());
			//Ejecutamos la instruccion sql
			pst_buscaCita.execute();
			
			
			/* hay que arreglar eso... creando un medico de mentira para emergencia
			 * ORA-02291: restricción de integridad (SALUD.FK_CM_CITA_MEDICA_PARAM_HORAR) violada - clave principal no encontrada
			 * */
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			flag = false;
			return flag;
		}finally {
			pst_buscaCita.close();
			//con.close();
		}
		flag = true;
		return flag;
	}

	public void anulaCita(int nu_solicitud) throws Exception{
		//obtener la conexion con la BBDD
		//con 	= null;
		pst_buscaCita = null;
		
		try {
			// Creamos la consulta para la busqueda de la cita
			String citaSql="UPDATE SALUD.CM_CITA_MEDICA SET ST_CITA = 'A' WHERE NU_SOLICITUD = ?";

			//Establecer la conexion
			//con = getConexion();
			
			//Crear consulta preparada
			pst_buscaCita = con.prepareStatement(citaSql);
			pst_buscaCita.setInt(1, nu_solicitud);
			
			// Ejecutamos la consulta
			pst_buscaCita.execute();
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			pst_buscaCita.close();
			//con.close();
		}
	}
		
	public List<Cita> getListado(String tipo_cita, String fecha_cita, String medico ) throws Exception{
		String citaSQL="";
		//Instanciamos para crea una lista de empleados
		List<Cita> Citas 	= new ArrayList<>();
		//con   			= null;
		pst_buscaCita 		= null;
		cursor				= null;
		
		try {
			//Establecer la conexion
			//con = getConexion();
			
			String sql ="select "+
						  "cm.nu_solicitud,"+ 
						  "decode(cm.st_cita,'S','Solicitada','A','Anulada','P', 'Procesada')st_cita, "+
						  "cm.ci_empleado,"+ 
						  "cm.nb_empleado, "+
						  "case "+
					        "when emp.nu_celular_1 is null and emp.nu_celular_2 is null and emp.nu_celular_3 is null then 'n/r' "+ 
					        "when emp.nu_celular_1 is null and emp.nu_celular_2 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_3,'-',''),' ','')) "+ 
					        "when emp.nu_celular_1 is null and emp.nu_celular_3 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_2,'-',''),' ','')) "+
					        "when emp.nu_celular_2 is null and emp.nu_celular_3 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_1,'-',''),' ','')) "+
					        "when emp.nu_celular_1 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_2,'-',''),' ',''))||' / '||TRIM(REPLACE(REPLACE(emp.nu_celular_3,'-',''),' ','')) "+
					        "when emp.nu_celular_2 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_1,'-',''),' ',''))||' / '||TRIM(REPLACE(REPLACE(emp.nu_celular_3,'-',''),' ','')) "+
					        "when emp.nu_celular_3 is null then TRIM(REPLACE(REPLACE(emp.nu_celular_1,'-',''),' ',''))||' / '||TRIM(REPLACE(REPLACE(emp.nu_celular_2,'-',''),' ','')) "+
					        "else TRIM(REPLACE(REPLACE(emp.nu_celular_1,'-',''),' ',''))|| ' / ' ||TRIM(REPLACE(REPLACE(emp.nu_celular_2,'-',''),' ',''))|| ' / '||TRIM(REPLACE(REPLACE(emp.nu_celular_3,'-',''),' ','')) "+
					      "end celular, "+
						  "emp.nu_extension_1 extension, "+
						  "cm.ci_familiar, "+
						  "cm.nb_familiar, "+
						  "cm.in_ti_cita, "+
						  "cm.in_especialidad, "+ 
						  "to_char(cm.fe_cita,'dd/mm/yyyy') fe_cita, "+ 
						  "cm.hh_cita, "+
						  "cm.ci_especialista, "+ 
						  "cm.nb_ti_solicitud, "+
						  "to_char(cm.fe_atencion,'dd/mm/yyyy') fe_atencion, "+ 
						  "cm.hh_atencion, "+
						  "cm.nb_usuario_modific, "+ 
						  "cm.tx_dato_modificado, "+
						  "to_char(cm.fe_solicitud,'DD/MM/YYYY') fe_solicitud, "+ 
						  "cm.hh_solicitud "+
						"from salud.cm_cita_medica cm "+
						"join personal.empleado_direccion emp on cm.ci_empleado = emp.nu_cedula ";
						
			
			//Creamos la instruccion sql para consulta
			
			if (tipo_cita != "") {
				switch (tipo_cita) {
				case "E":
					tipo_cita = "Emergencia";
					if(fecha_cita != null) {
						citaSQL = sql + "where nb_ti_solicitud = ? and fe_cita = to_date(?,'DD/MM/YYYY') order by nu_solicitud asc";
						pst_buscaCita = con.prepareStatement(citaSQL);
						pst_buscaCita.setString(1, tipo_cita);
						pst_buscaCita.setString(2, fecha_cita);
					}else {
						citaSQL = sql + "where nb_ti_solicitud = ? and rownum <= 4000 order by fe_cita asc, nu_solicitud asc";
						pst_buscaCita = con.prepareStatement(citaSQL);
						pst_buscaCita.setString(1, tipo_cita);
					}
					break;
				case "T":
					if(fecha_cita != null) {
						citaSQL = sql + "where fe_cita = to_date(?,'DD/MM/YYYY') order by hh_cita asc";
						pst_buscaCita = con.prepareStatement(citaSQL);
						pst_buscaCita.setString(1, fecha_cita);
					}else {
						citaSQL = sql + "where rownum <= 4000 order by fe_cita asc, hh_cita asc";
						pst_buscaCita = con.prepareStatement(citaSQL);	
					}
					break;
				case "M": 
				case "O":
					if(fecha_cita != null) {
						citaSQL = sql + "where in_especialidad = ? and nb_ti_solicitud!='Emergencia' and fe_cita = to_date(?,'DD/MM/YYYY') order by hh_cita asc";
						pst_buscaCita = con.prepareStatement(citaSQL);
						pst_buscaCita.setString(1, tipo_cita);
						pst_buscaCita.setString(2, fecha_cita);
					}else {
						citaSQL = sql + "where in_especialidad = ? and nb_ti_solicitud!='Emergencia' and rownum <= 4000 order by fe_cita asc, hh_cita asc";
						pst_buscaCita = con.prepareStatement(citaSQL);
						pst_buscaCita.setString(1, tipo_cita);
					}
					break;
				}
			}else {
				if(fecha_cita != null && medico != null) {
					citaSQL = sql + "where fe_cita = to_date(?,'DD/MM/YYYY') and ci_especialista = ? order by hh_cita asc";
					pst_buscaCita = con.prepareStatement(citaSQL);
					pst_buscaCita.setString(1, fecha_cita);
					pst_buscaCita.setString(2, medico);
				}else if(fecha_cita != null) {
					citaSQL = sql + "where fe_cita = to_date(?,'DD/MM/YYYY') order by hh_cita asc";
					pst_buscaCita = con.prepareStatement(citaSQL);
					pst_buscaCita.setString(1, fecha_cita);
				}
			}
			
			cursor = pst_buscaCita.executeQuery();
	
			while(cursor.next()) {
				int nu_solicitud 			= cursor.getInt("nu_solicitud");
				String st_cita 				= cursor.getString("st_cita");
				int ci_empleado 			= cursor.getInt("ci_empleado");
				String nb_empleado 			= cursor.getString("nb_empleado");
				int ci_familiar 			= cursor.getInt("ci_familiar");
				String nb_familiar 			= cursor.getString("nb_familiar");
				
				String celular				= cursor.getString("celular");
				String extension			= cursor.getString("extension");
				
				String in_ti_cita 			= cursor.getString("in_ti_cita");
				String in_especialidad 		= cursor.getString("in_especialidad");
				String fe_cita 				= cursor.getString("fe_cita");
				String hh_cita 				= cursor.getString("hh_cita");
				int ci_especialista 		= cursor.getInt("ci_especialista");
				String nb_ti_solicitud 		= cursor.getString("nb_ti_solicitud");
				String fe_atencion			= cursor.getString("fe_atencion");
				String hh_atencion			= cursor.getString("hh_atencion");
				String nb_usuario_modific	= cursor.getString("nb_usuario_modific");
				String tx_dato_modificado	= cursor.getString("tx_dato_modificado");
				String fe_solicitud			= cursor.getString("fe_solicitud");
				String hh_solicitud			= cursor.getString("hh_solicitud");
				
				Cita tempCita = new Cita();
				
				tempCita.setNu_solicitud(nu_solicitud);
				tempCita.setSt_cita(st_cita);
				tempCita.setCi_empleado(ci_empleado);
				tempCita.setNb_empleado(nb_empleado);
				tempCita.setCi_familiar(ci_familiar);
				tempCita.setNb_familiar(nb_familiar);
				
				tempCita.setCelular(celular);
				tempCita.setExtension(extension);
				
				tempCita.setIn_ti_cita(in_ti_cita);
				tempCita.setIn_especialidad(in_especialidad);
				tempCita.setFe_cita(fe_cita);
				tempCita.setHh_cita(hh_cita);
				tempCita.setCi_especialista(ci_especialista);
				tempCita.setNb_ti_solicitud(nb_ti_solicitud);
				tempCita.setFe_atencion(fe_atencion);
				tempCita.setNb_usuario_modific(nb_usuario_modific);
				tempCita.setNb_ti_solicitud(nb_ti_solicitud);
				tempCita.setTx_dato_modificado(tx_dato_modificado);
				tempCita.setFe_solicitud(fe_solicitud);
				tempCita.setHh_solicitud(hh_solicitud);
				
				Citas.add(tempCita);
			}
		}catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();
			pst_buscaCita.close();
			//con.close();
		}
		return Citas;
	}
	
	public List<Cita> getHistoricoCitas() throws Exception{
		//Establecer la conexion
		//con = getConexion();
		pst_buscaCita 	= null;
		cursor 			= null;
		//Creamos un Array con la informacion de la cita
		List<Cita> historiaCitas = new ArrayList<>();
		
		// Creamos la consulta para la busqueda de la cita
		
		String citaSql=	"select "
							+ "cita.nu_solicitud, "
							+ "decode(cita.st_cita,'S','Solicitada','A','Anulada','P', 'Procesada')st_cita, "
							+ "cita.ci_empleado, "
							+ "cita.nb_empleado, "
							+ "cita.ci_familiar, "
							+ "cita.nb_familiar, "
							+ "cita.in_ti_cita, "
							+ "cita.in_especialidad, "
							+ "to_char(cita.fe_cita,'dd/mm/yyyy') fe_cita, "
							+ "cita.hh_cita, "
							+ "cita.ci_especialista, "
							+ "initcap( case "
											+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
											+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
											+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) " 
									  + "end) nb_medico, "
							+ "cita.nb_ti_solicitud, "
							+ "to_char(cita.fe_atencion,'dd/mm/yyyy') fe_atencion, "
							+ "cita.hh_atencion, "
							+ "cita.nb_usuario_modific, "
							+ "cita.tx_dato_modificado, "
							+ "to_char(cita.fe_solicitud,'dd/mm/yyyy') fe_solicitud , "
							+ "cita.hh_solicitud "
					+ "from salud.cm_cita_medica cita "
					+ "join PERSONAL.TODOS_EMPLEADOS emp on cita.ci_especialista = emp.cedula "
					+ "where ci_empleado = ? "
					+ "and st_cita = 'S' and nb_ti_solicitud != 'Emergencia'"
					+ "order by nu_solicitud desc";

		
		//Crear consulta preparada
		pst_buscaCita = con.prepareStatement(citaSql);
		pst_buscaCita.setInt(1, ci_empleado);
		
		// Ejecutamos la consulta
		cursor = pst_buscaCita.executeQuery();
		
		try {
			//Recorremos la tabla para obtener los datos de la cita
			while(cursor.next()) {
				// Guardamos los datos
				Cita tempCita = new Cita();
				
				tempCita.setNu_solicitud		(cursor.getInt("nu_solicitud"));
				tempCita.setSt_cita				(cursor.getString("st_cita"));
				tempCita.setCi_empleado			(cursor.getInt("ci_empleado"));
				tempCita.setNb_empleado			(cursor.getString("nb_empleado"));
				tempCita.setCi_familiar			(cursor.getInt("ci_familiar"));
				tempCita.setNb_familiar			(cursor.getString("nb_familiar"));
				tempCita.setIn_ti_cita			(cursor.getString("in_ti_cita"));
				tempCita.setIn_especialidad		(cursor.getString("in_especialidad"));
				tempCita.setFe_cita				(cursor.getString("fe_cita"));
				tempCita.setHh_cita				(cursor.getString("hh_cita"));
				tempCita.setCi_especialista		(cursor.getInt("ci_especialista"));
				tempCita.setNb_especialista		(cursor.getString("nb_medico")); 
				tempCita.setNb_ti_solicitud		(cursor.getString("nb_ti_solicitud"));
				tempCita.setFe_atencion			(cursor.getString("fe_atencion"));
				tempCita.setHh_atencion			(cursor.getString("hh_atencion"));
				tempCita.setNb_usuario_modific	(cursor.getString("nb_usuario_modific"));
				tempCita.setTx_dato_modificado	(cursor.getString("tx_dato_modificado"));
				tempCita.setFe_solicitud		(cursor.getString("fe_solicitud"));
				tempCita.setHh_solicitud		(cursor.getString("hh_solicitud"));
				
				historiaCitas.add(tempCita);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();
			pst_buscaCita.close();
			//con.close();
		}
		return historiaCitas;
	}

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(DatosCitaMedica.class);
	
	private int cantidad 					= 0   ;
	private int ci_empleado					= 0   ;
	private int ci_familiar					= 0   ;
	private String in_ti_cita 				= null;
	private String fe_cita 					= null;
	
	private String tipoCita 				= null;
	private String turno 					= null;
	private String medico 					= null;
	
	private Connection con;
	private Statement  st_buscaCita 		= null;
	private PreparedStatement pst_buscaCita = null;
	private ResultSet  cursor    			= null;
}



