package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import bean.Cita;
import bean.Medico;
import bean.ParametroCita;

public class DatosMedico {
	
	public DatosMedico(Connection conexion) {
		super();
		this.conexion = conexion;
	}

	public DatosMedico(String in_especialidad, String in_turno, Connection conexion) {
		super();
		this.in_especialidad = in_especialidad;
		this.in_turno = in_turno;
		this.conexion = conexion;
	}
	
	public DatosMedico(String fechaCita, int cedulaMedico, Connection conexion) {
		super();
		this.fechaCita = fechaCita;
		this.cedulaMedico = cedulaMedico;
		this.conexion = conexion;
	} 

	public DatosMedico(int cedula, Connection conexion) {
		super();
		this.cedula = cedula;
		this.conexion = conexion;
	}

	public ArrayList<String> getHorario() throws SQLException{
		
		//conexion = getConexion();
		buscaMedico = conexion.createStatement();
		//Los datos de busqueda son la cedula del medico y la fecha de la cita a solicitar
		//Aqui va la sentencia para buscar la funcion del horario que me va a dar joel
		
		//Vamos a simular que recibimos los datos para esa fecha y con ese medico
		//listaHorario = "07:30 a.m.|09:30 a.m.|10:00 a.m.|10:30 a.m.|11:00 a.m.";
		//Vamos a separar todo lo que este antes de cada |
		//String[] horario = listaHorario.split("\\|");//se debe colocar \\ para luego indicar el separador
		
		
		ArrayList<String> listado = new ArrayList<String>();
		int ci_especialista = 0;
		int nu_citas = 0;
		int nu_intervalo = 0;
		int hh_inicio = 0;
		
		String parametros = "select "
				+ "ci_especialista, "
				+ "nu_citas, "
				+ "nu_intervalo, "
				+ "hh_inicio "
		  + "from salud.cm_param_horario "
		  + "where ci_especialista = " + cedulaMedico; 
		buscaMedico = conexion.createStatement();
		cursor = buscaMedico.executeQuery(parametros);

		while ( cursor.next()) {
			nu_citas = cursor.getInt("nu_citas");
			nu_intervalo = cursor.getInt("nu_intervalo");
			hh_inicio = cursor.getInt("hh_inicio");
		}

		for (int i = 0; i < nu_citas; i ++) {
			for (int intervalo = 0; intervalo < 60; intervalo += nu_intervalo){
				if(hh_inicio < 10) {
					if(intervalo < 9) {
						listado.add(i , "0" + hh_inicio + ":" + "0" +intervalo +  " a.m.");	
					}else listado.add(i , "0" + hh_inicio + ":" + intervalo + " a.m." );
				}
				if(hh_inicio > 9 && hh_inicio <12) {
					if(intervalo < 9) {
						listado.add(i , hh_inicio + ":" + "0" + intervalo + " a.m.");	
					}else listado.add(i , hh_inicio + ":" + intervalo + " a.m." );
				}
				if(hh_inicio == 12) {
					if(intervalo < 9) {
						listado.add(i , hh_inicio  + ":" + "0" + intervalo + " m.");	
					}else listado.add(i , hh_inicio  + ":" + intervalo + " p.m." );
				}
				if(hh_inicio > 12) {
					if(intervalo < 9) {
						//listado.add(i , "0" + (hh_inicio - 12) + ":" + "0" + intervalo + " p.m.");	
						listado.add(i , hh_inicio + ":" + "0" + intervalo + " p.m.");
					}else listado.add(i , hh_inicio + ":" + intervalo + " p.m." );
					//listado.add(i , "0" + (hh_inicio - 12) + ":" + intervalo + " p.m." );
				}
				i ++;
			}//Fin for interno	
			i = i - 1;
			hh_inicio ++;
		}//Fin for externo
		
		
		List<String> citasSolicitadas = new ArrayList<String>();
		//conexion = getConexion();
		buscaMedico = conexion.createStatement();
		
		String MedSql = "select "
				  + "hh_cita "
			  + "from salud.cm_cita_medica "
			  + "where ci_especialista = " + cedulaMedico + " and TO_CHAR(TRUNC(fe_cita),'DD/MM/YYYY') = '" + fechaCita + "' and st_cita = 'S' order by hh_cita asc";
		cursor = buscaMedico.executeQuery(MedSql);

		while(cursor.next()) {
			String hh_cita 	= cursor.getString("hh_cita");
			citasSolicitadas.add(hh_cita);
		}
		
		/************************************************/
		/*********** Inicio del Listado Final ***********/
		/************************************************/
		for (int i = 0; i < listado.size(); i ++) {
			//System.out.println(listado.get(m));
			for (int j = 0; j < citasSolicitadas.size(); j ++) {
			    //System.out.println( "hora: " + citasSolicitadas.get(m)  );
				if(citasSolicitadas.get(j).equals(listado.get(i))) {
					listado.remove(i);
				}
			}
		}
		
		cursor.close();
		buscaMedico.close();
		//conexion.close();
		return listado;
	}
	public String getNombreCompleto(int ci_especialidad) throws SQLException {
		//conexion   = null;
		buscaMedico = null;
		cursor 	  = null;
		//conexion = getConexion();
		buscaMedico = conexion.createStatement();
		
		MedSql = "select "  
	    		+ "initcap("
	    		+ "case "
	      			+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
	      			+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
	      			+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
	      		+ "end) nb_medico "
	      + "from personal.medicos med "
	      + "join personal.todos_empleados emp on med.cedula = emp.cedula "
	      + "where med.cedula = '" + ci_especialidad + "' and med.situacion!='E' and med.in_especialidad is not null and emp.codigo_cia = '01'";

		cursor = buscaMedico.executeQuery(MedSql);
		if(cursor.next()) {
			nb_especialidad = cursor.getString(1);
		}
		
		cursor.close();
		buscaMedico.close();
		//conexion.close();
		
		return nb_especialidad;
		
	}
	public List<String> getNombreCompleto() throws SQLException {
		List<String> nombre = new ArrayList();
		//conexion   = null;
		buscaMedico = null;
		cursor 	  = null;
		//conexion = getConexion();
		buscaMedico = conexion.createStatement();
		
		MedSql = "select "  
	    		+ "initcap("
	    		+ "case "
	      			+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
	      			+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
	      			+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
	      		+ "end) nb_medico "
	      + "from personal.medicos med "
	      + "join personal.todos_empleados emp on med.cedula = emp.cedula where med.situacion!='E' and med.in_especialidad is not null and emp.codigo_cia = '01' order by nb_medico asc";

		cursor = buscaMedico.executeQuery(MedSql);
		while(cursor.next()) {
			nombre.add(cursor.getString(1));
		}

		cursor.close();
		buscaMedico.close();
		//conexion.close();
		
		return nombre;
		
	}
	public List<Medico> getTodosMedicos() throws Exception{
		//Instanciamos para crea una lista de medicos
		listaMedico = new ArrayList();
		
		//conexion   = null;
		buscaMedico = null;
		cursor 	  = null;
		//conexion = getConexion();
		buscaMedico = conexion.createStatement();
		
		
		//Crear la sentencia SQL
		MedSql =	"select "
						+ "med.cedula, " 
						+ "initcap( "
						+ "case "
							+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) " 
							+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) " 
							+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
						+ "end) nb_medico, "
						+ "in_clase_medico, "
						+ "med.in_especialidad, " 
						+ "initcap(decode(med.in_especialidad,'M','MEDICA','O','ODONTOLOGIA','N','NUTRICION','F','FISIOTERAPIA')) as nb_especialidad "  
				   + "from personal.medicos med "
				   + "join personal.todos_empleados emp on med.cedula = emp.cedula where med.situacion!='E' and med.in_especialidad is not null and emp.codigo_cia = '01' order by nb_especialidad asc";
		
		cursor = buscaMedico.executeQuery(MedSql);
		while(cursor.next()) {
			cedula 				= 	cursor.getInt("cedula");
			nb_medico 			= 	cursor.getString("nb_medico");
			nb_especialidad 	= 	cursor.getString("nb_especialidad");
			
			tempMed = new Medico (); 
			
			tempMed.setCedula			(cedula);
			tempMed.setNb_medico		(nb_medico);
			tempMed.setNb_especialidad	(nb_especialidad);
			
			listaMedico.add(tempMed);
		}
		
		cursor.close();
		buscaMedico.close();
		//conexion.close();
		
		return listaMedico;		// Retornamos el array
	}
	
	public List<Medico> getDatosMedicos() throws Exception{
		//Instanciamos para crea una lista de medicos
		listaMedico = new ArrayList();
		
		//Establecer la conexion
		//conexion = getConexion();
		
		//Crear la sentencia SQL
		MedSql =	"select "
						+ "med.cedula, " 
						+ "initcap( "
						+ "case "
							+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) " 
							+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) " 
							+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
						+ "end) nb_medico, "
						+ "in_clase_medico, "
						+ "med.in_especialidad, " 
						+ "initcap(decode(med.in_especialidad,'M','MEDICA','O','ODONTOLOGIA','N','NUTRICION','F','FISIOTERAPIA')) as nb_especialidad "  
				   + "from personal.medicos med "
				   + "join personal.todos_empleados emp on med.cedula = emp.cedula " 
				   + "where med.cedula  = ? and med.situacion!='E' and med.in_especialidad is not null and emp.codigo_cia = '01' ";
		
		//Crear Statement
		buscaMed = conexion.prepareStatement(MedSql);	
		buscaMed.setInt(1, cedula);
		cursor = buscaMed.executeQuery();
		
		//Asignamos los valores obtenidos por el cursor
		while(cursor.next()) {
			cedula 				= 	cursor.getInt("cedula");
			nb_medico 			= 	cursor.getString("nb_medico");
			in_clase_medico 	= 	cursor.getString("in_clase_medico");
			in_especialidad 	= 	cursor.getString("in_especialidad");
			nb_especialidad 	= 	cursor.getString("nb_especialidad");
			
			tempMed = new Medico (); 
			
			tempMed.setCedula			(cedula);
			tempMed.setNb_medico		(nb_medico);
			tempMed.setIn_clase_medico	(in_clase_medico);
			tempMed.setIn_especialidad	(in_especialidad);
			tempMed.setNb_especialidad	(nb_especialidad);
			
			listaMedico.add(tempMed);
		}
		
		cursor.close();
		buscaMed.close();
		//conexion.close();
		
		return listaMedico;		// Retornamos el array
	}
	
	public List<Medico> getEspecialista() throws Exception{
		//Instanciamos para crea una lista de medicos
		listaMedico = new ArrayList<>();
		
		//Establecer la conexion
		//conexion = getConexion();
		
		
		//Crear Statement
		buscaMedico = conexion.createStatement();
		
		//	Crear la sentencia SQL
		
			MedSql = "select "  
		    		+ "med.cedula, "
		    		+ "med.situacion, "
		    		+ "initcap(emp.nombre1) as nombre1, "
		    		+ "initcap(emp.nombre2) as nombre2, " 
		    		+ "initcap(emp.apellido1) as apellido1, "
		    		+ "initcap(emp.apellido2) as apellido2, "
		    		+ "initcap("
		    		+ "case "
		      			+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
		      			+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
		      			+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
		      		+ "end) nb_medico, "
		      		+ "med.in_clase_medico, " 
		      		+ "initcap(decode(med.in_clase_medico,'M','MEDICA','O','ODONTOLOGIA')) as nb_clase_medico, "
		      		+ "med.in_turno, "
		      		+ "initcap(decode(med.in_turno,'M','MAÑANA','T','TARDE')) as nb_turno, "
		      		+ "med.in_especialidad, "
		      		+ "initcap(decode(med.in_especialidad,'M','MEDICA','O','ODONTOLOGIA','N','NUTRICION','F','FISIOTERAPIA')) as nb_especialidad " 
		      + "from personal.medicos med "
		      + "join personal.todos_empleados emp on med.cedula = emp.cedula "
		      + "where med.in_especialidad = '" + in_especialidad + "' and in_turno = '"+ in_turno +"' "
		      		 + "and med.situacion!='E' and med.in_especialidad is not null and emp.codigo_cia = '01' order by nb_medico asc";
	
		cursor = buscaMedico.executeQuery(MedSql);
		
		//Asignamos los valores obtenidos por el cursor
		while(cursor.next()) {
			
			tempMed = new Medico (); 
			
			tempMed.setSituacion		(cursor.getString("situacion"));
			tempMed.setCedula			(cursor.getInt	 ("cedula"));
			tempMed.setNombre1			(cursor.getString("nombre1"));
			tempMed.setNombre2			(cursor.getString("nombre2"));
			tempMed.setApellido1		(cursor.getString("apellido1"));
			tempMed.setApellido2		(cursor.getString("apellido2"));
			tempMed.setNb_medico		(cursor.getString("nb_medico"));
			tempMed.setIn_clase_medico	(cursor.getString("in_clase_medico"));
			tempMed.setNb_clase_medico	(cursor.getString("nb_clase_medico"));
			tempMed.setIn_turno			(cursor.getString("in_turno"));
			tempMed.setNb_turno			(cursor.getString("nb_turno"));
			tempMed.setIn_especialidad	(cursor.getString("in_especialidad"));
			tempMed.setNb_especialidad	(cursor.getString("nb_especialidad"));
			
			listaMedico.add(tempMed);
		}
		
		cursor.close();
		buscaMedico.close();
		//conexion.close();
		
		return listaMedico;		// Retornamos el array
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(DatosMedico.class);
	
	private DataSource pool;
	private Medico tempMed;
	private List<Medico> listaMedico;
	private String listaHorario;
	
	private int 	cedula;
	private String 	situacion;
	private String 	nombre1;
	private String 	nombre2;
	private String 	apellido1;
	private String 	apellido2;
	private String 	nb_medico;
	private String 	in_clase_medico;
	private String 	nb_clase_medico;
	private String 	in_turno;
	private String 	nb_turno;
	private String 	in_especialidad;
	private String 	nb_especialidad;
	private String 	fechaCita;
	private int 	cedulaMedico;
	
	private String MedSql;
	private Connection conexion   = null;
	private Statement buscaMedico = null;
	private PreparedStatement buscaMed     = null;
	private ResultSet cursor 	  = null;
	
}
