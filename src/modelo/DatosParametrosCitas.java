package modelo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bean.Medico;
import bean.ParametroCita;

public class DatosParametrosCitas{

	public DatosParametrosCitas(Connection conexion) {
		super();
		this.conexion = conexion;
	}
	
	public DatosParametrosCitas(ParametroCita param, Connection conexion) {
		this.conexion = conexion;
		this.param = param;
	}

	public ArrayList<String> getHorario(ParametroCita paramCita) {
		int hora_ini = paramCita.getHh_inicio();
		int cantidadCita = paramCita.getNu_citas();
		int tiempo_cita  = paramCita.getNu_intervalo();
		
		ArrayList<String> listado = new ArrayList<String>();
		
		for (int i = 0; i < cantidadCita; i ++) {
			for (int intervalo = 0; intervalo < 60; intervalo += tiempo_cita){
				if(hora_ini < 10) {
					if(intervalo < 9) {
						listado.add(i , "0" + hora_ini + ":" + "0" +intervalo +  " a.m.");	
					}else listado.add(i , "0" + hora_ini + ":" + intervalo + " a.m." );
				}
				if(hora_ini > 9 && hora_ini <12) {
					if(intervalo < 9) {
						listado.add(i , hora_ini + ":" + "0" + intervalo + " a.m.");	
					}else listado.add(i , hora_ini + ":" + intervalo + " a.m." );
				}
				if(hora_ini == 12) {
					if(intervalo < 9) {
						listado.add(i , hora_ini  + ":" + "0" + intervalo + " m.");	
					}else listado.add(i , hora_ini  + ":" + intervalo + " p.m." );
				}
				if(hora_ini > 12) {
					if(intervalo < 9) {
						listado.add(i , "0" + (hora_ini - 12) + ":" + "0" + intervalo + " p.m.");	
					}else listado.add(i , "0" + (hora_ini - 12) + ":" + intervalo + " p.m." );
				}
				i ++;
			}//Fin for interno	
			i = i - 1;
			hora_ini ++;
        }//Fin for externo
		
		
		return listado;
	}
	public boolean actualizaParametros(Connection conexion) throws SQLException {
		try {
			String sql = "update salud.cm_param_horario "
						+ "set nu_citas = ?, "
						+ "nu_intervalo = ?, "
						+ "hh_inicio = ? "
						+ "where ci_especialista = ?";
			paramHorario = conexion.prepareStatement(sql);
			
			paramHorario.setInt(1, param.getNu_citas());
			paramHorario.setInt(2, param.getNu_intervalo());
			paramHorario.setInt(3, param.getHh_inicio());
			paramHorario.setInt(4, param.getCi_especialista());
			
			paramHorario.executeUpdate();
			
		} catch (Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			return false;
		} finally {
			paramHorario.close();
		}
		return true;
		
	}
	public boolean insertaParametros(ParametroCita paramCita, List<Medico> cedulaMedicos) throws SQLException {
		try {
			//Creamos la conexion
			//conexion = getConexion();
			
			//Creamos la instruccion sql para insertar (consulta preparada)
			
			String parametrosSQL = "insert into salud.cm_param_horario"
					+ "(ci_especialista,"
					+ "nb_especialista,"
					+ "nu_citas,"
					+ "nu_intervalo,"
					+ "in_turno,"
					+ "in_especialidad,"
					+ "hh_inicio)"
					+ "values(?,?,?,?,?,?,?)";			

			paramHorario = conexion.prepareStatement(parametrosSQL);
					
			for (int i = 0; i < cedulaMedicos.size(); i++) {
				
				//Establecemos los parametros
				paramHorario.setInt		(1,cedulaMedicos.get(i).getCedula());
				paramHorario.setString	(2,cedulaMedicos.get(i).getNb_medico());
				paramHorario.setInt		(3,paramCita.getNu_citas());
				paramHorario.setInt		(4,paramCita.getNu_intervalo());
				paramHorario.setString	(5,cedulaMedicos.get(i).getIn_turno());
				paramHorario.setString	(6,cedulaMedicos.get(i).getIn_especialidad());
				paramHorario.setInt		(7,paramCita.getHh_inicio());
				
				/*
				paramHorario.setInt(2, paramCita.getNu_citas());
				paramHorario.setInt(3, paramCita.getNu_intervalo());
				paramHorario.setString(4, paramCita.getIn_turno());
				paramHorario.setString(5, paramCita.getIn_especialidad());
				paramHorario.setInt(6, paramCita.getHh_inicio());
				*/
				//Ejecutamos la instruccion sql
				paramHorario.execute();
			}
			
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			return false;
		}finally {
			paramHorario.close();
			//conexion.close();
		}
		return true;
		
	}
	
	public int horarioMedico (int cedulaMedico, String fechaCita, int cedulaEmpleado) throws SQLException {
		//obtener parametrohorario de BD
		int flag = 0 ;
		try {
			java.sql.Date fecha = null;
			
				//String fecha_Cita = fechaCita;
				
				//Creamos el formato que se le va a dar
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				//Creamos una variable nueva pero con el formato ya hecho
				java.util.Date strFecha = formatter.parse(fechaCita);
				//pasamos el valor final a nuestra variable de tipo fecha para base de datos
				fecha = new java.sql.Date(strFecha.getTime());
			
			
			//conexion = getConexion();
			//String function = "{?=call SALUD.PKG_SSPS_GENERAL.CM_ESTA_AUSENTE(?,?)}";
			plsql = conexion.prepareCall("{?=call SALUD.PKG_SSPS_GENERAL.CM_ESTA_AUSENTE(?,?)}");
			plsql.registerOutParameter(1, Types.NUMERIC);
			plsql.setInt(2, cedulaMedico);
			plsql.setDate(3,fecha);
			
			plsql.execute();
			
			flag = plsql.getInt(1);
			
				if(flag == 1) {
					log.info("Verdadero");
				}else {
					log.info("Falso");
				}
			
		} catch (Exception e) {
				// TODO: handle exception
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			flag = 0;
			
		}finally {
			//getConexion().close();
			plsql.close();
			//conexion.close();
		}
		//verificar si posee un ausentismo y eliminar esa hora del array
		return flag;
		
		
				
	}
	



	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(DatosParametrosCitas.class);
	
	private PreparedStatement paramHorario = null;
	private Connection conexion		       = null;
	private Statement  buscaParametros 	   = null;
	private ResultSet  cursor    	       = null;
	
	private ParametroCita param ;
	private ArrayList<String> horaMedico;
	
	private DatosParametrosCitas datosParamCita;
	
	private CallableStatement plsql 	= null;

}
