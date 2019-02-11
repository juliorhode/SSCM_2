package pruebas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import bean.Cita;
import bean.Medico;


public class pruebaHorario {
	
	//Esto se declara en cada clase por motivo de que cada una tiene una salida en particular
	private final static Logger log = Logger.getLogger(pruebaHorario.class);
	
	
	
public static  void main(String[] args) throws Exception {
	Connection conexion;
	Statement  st_buscaCita 		= null;
	PreparedStatement pst_buscaCita = null;
	ResultSet  cursor    			= null;
	String username = "admssps";
	String password = "admssps";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.OracleDriver";
	String url = jdbc + hostname + port +  database;
	Class.forName(classname);
	conexion = DriverManager.getConnection(url,username,password);

	
	/*Se obtienen los datos de la cita a programar*/
	Cita nuevacita = new Cita();
	int nu_solicitud = 5001;
	String st_cita = "S";
	int ci_empleado = 13802982;
	String nb_empleado = "Ferrer Julio";
	int ci_familiar = 0;
	String nb_familiar = "No aplica";
	String in_ti_cita = "M";
	String in_especialidad = "M";
	String fe_cita = "20/12/2019";
	String hh_cita = "08:00 a.m.";
	int ci_especialista = 6518383;
	String nb_ti_solicitud = "Consulta Médica";
	String fe_atencion = null;
	String hh_atencion = null;
	String nb_usuario_modific = "juferrer";
	String tx_dato_modificado =  null;
	String fe_solicitud = "11/02/2019";
	String hh_solicitud = "08:00 a.m.";
	/*Se construye el objeto Cita*/
	nuevacita.setNu_solicitud(nu_solicitud);
	nuevacita.setSt_cita(st_cita);
	nuevacita.setCi_empleado(ci_empleado);
	nuevacita.setNb_empleado(nb_empleado);
	nuevacita.setCi_familiar(ci_familiar);
	nuevacita.setNb_familiar(nb_familiar);
	nuevacita.setIn_ti_cita(in_ti_cita);
	nuevacita.setIn_especialidad(in_especialidad);
	nuevacita.setFe_cita(fe_cita);
	nuevacita.setHh_cita(hh_cita);
	nuevacita.setCi_especialista(ci_especialista);
	nuevacita.setNb_ti_solicitud(nb_ti_solicitud);
	nuevacita.setFe_atencion(fe_atencion);
	nuevacita.setHh_atencion(hh_atencion);
	nuevacita.setNb_usuario_modific(nb_usuario_modific);
	nuevacita.setTx_dato_modificado(tx_dato_modificado);
	nuevacita.setFe_solicitud(fe_solicitud);
	nuevacita.setHh_solicitud(hh_solicitud);
	/*se verifica si existe una cita con esas caracteristicas*/
	String sql = "select * from salud.cm_cita_medica where fe_cita = ? and hh_cita = ? and ci_especialista = ? and st_cita = 'S'";
	pst_buscaCita = conexion.prepareStatement(sql);
	pst_buscaCita.setString(1, fe_cita);
	pst_buscaCita.setString(2, hh_cita);
	pst_buscaCita.setInt(3, ci_especialista);
	cursor = pst_buscaCita.executeQuery();
	
	if (cursor.next()) {
		/*No se permite el registro porque se encontro uno previo*/
		System.out.println("No se puede registrar la cita");
	}else {
		/*Se procede a registrar la cita*/
		String citaSQL = "insert into salud.cm_cita_medica "
				+ "(nu_solicitud, "
				+ "st_cita, "
				+ "ci_empleado, "
				+ "nb_empleado, "
				+ "ci_familiar, "
				+ "nb_familiar, "
				+ "fe_cita, "
				+ "hh_cita, "
				+ "ci_especialista) "
			+ "values(5000, 'S', 13802982, 'Ferrer Julio', 0, 'No aplica',to_date('20/12/2019','DD/MM/YYYY'), '08:00 a.m.', 6518383)";
			pst_buscaCita.execute();
	}
	
	
       
       
	/*
	Calendar calendario = new GregorianCalendar();
	String dia = Integer.toString(calendario.get(Calendar.DATE));
	String mes = Integer.toString(calendario.get(Calendar.MONTH));
	String ano = Integer.toString(calendario.get(Calendar.YEAR));
	if(calendario.get(Calendar.DATE) < 10) {
		dia = "0" + Integer.toString(calendario.get(Calendar.DATE));
	}else {
		dia = Integer.toString(calendario.get(Calendar.DATE));
	}
	if(calendario.get(Calendar.MONTH) + 1 < 10) {
		mes = "0" + Integer.toString(calendario.get(Calendar.MONTH)+1);
	}else {
		mes = Integer.toString(calendario.get(Calendar.MONTH)+1);
	}

	String fechaActual = dia + "/" + mes + "/" + ano;
	System.out.println(fechaActual);
	*/
	/************************/
	/** JDNI para WebLogic **/
	/************************/ 
	/*
	HttpSession sesion;
	Connection conexion;
	DataSource datasource;
	InitialContext initialcontext;
	Context context;
	
	initialcontext = new InitialContext();
	context = new InitialContext();
	datasource = (DataSource)context.lookup("jdbc/desa");
	conexion = datasource.getConnection();
	*/
	/**********************/
	/** JDNI para Apache **/
	/**********************/
	/*
	initContext = new InitialContext();
	DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/prod");
	cn = ds.getConnection();
	*/
	
	/************************************************/
	/********** Prueba reprogramacion cita **********/
	/************************************************/
	/*
	Connection conexion   = null;
	Statement busca       = null;
	ResultSet cursor 	  = null;
	String username = "admssps";
	String password = "admssps";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.OracleDriver";
	String url = jdbc + hostname + port +  database;
	Class.forName(classname);
	conexion = DriverManager.getConnection(url,username,password);
	
	// Verificamos que parametros tiene el medico seteados en cantidad de citas
	int cedula = 11983569;
	int cantidadCitas = 0;
	String sqlParametro =  "select ci_especialista, nu_citas from SALUD.CM_PARAM_HORARIO where ci_especialista =" + cedula;
	busca = conexion.createStatement();
	cursor = busca.executeQuery(sqlParametro);
	if(cursor.next()) {
		cantidadCitas = cursor.getInt("nu_citas");
	}
	
	System.out.println("N° programado de citas: " + cantidadCitas);
	
	busca       = null;
	cursor 	  = null;
	
	// Traemmos las citas que hay para el momento del ausentismo
	String fechaAusentismo = "09/07/2018";
	int CI_EMPLEADO = 0;
	String NB_EMPLEADO = null;
	String FE_CITA = null;
	
	List<Cita> citasPendiente = new ArrayList<>();
	//conexion = getConexion();
	busca = conexion.createStatement();
	
	String sqlCitas =  "select CI_EMPLEADO,NB_EMPLEADO, to_char(FE_CITA,'dd/mm/yyyy') FE_CITA from SALUD.CM_CITA_MEDICA where st_cita = 'S' and fe_cita = '" + fechaAusentismo + "' and ci_especialista =11983569";
	cursor = busca.executeQuery(sqlCitas);
	Cita tempCita = new Cita();
	while(cursor.next()) {
		tempCita.setCi_empleado(cursor.getInt("CI_EMPLEADO"));
		tempCita.setNb_empleado(cursor.getString("NB_EMPLEADO"));
		tempCita.setFe_cita(cursor.getString("FE_CITA"));
		citasPendiente.add(tempCita);
	}

	for (int m = 0; m < citasPendiente.size(); m ++) {
	    System.out.println( citasPendiente.get(m).getCi_empleado() + " | " + citasPendiente.get(m).getNb_empleado() + " | " + citasPendiente.get(m).getFe_cita());
	}
	
	
	// Crear una cita
	
	// Crear un ausentismo
	
	// Enviar mensaje que existe una cita
	
	// Mostrar fecha mas proxima para reprogramacion
	
	// Actualizar los datos
	
	// Enviar correo de notificacion
	
	
	/*******************************************************/
	/********** Prueba Lectura Archivo Properties **********/
	/*******************************************************/
        	/*
        	Properties p = new Properties();
        	p.load(new FileReader("src/correo.properties"));
        	
        	Correo beanCorreo = new Correo();
        	
        	beanCorreo.setAuth(p.getProperty("auth"));
        	beanCorreo.setHost(p.getProperty("host"));
        	beanCorreo.setPort(p.getProperty("port"));
        	beanCorreo.setSender(p.getProperty("sender"));
        	beanCorreo.setStarttls(p.getProperty("starttls"));
        	beanCorreo.setUser(p.getProperty("user"));
        	beanCorreo.setPass(p.getProperty("pass"));


        	if (!p.isEmpty()) {
            	System.out.println(beanCorreo.getAuth());
            	System.out.println(beanCorreo.getHost());
            	System.out.println(beanCorreo.getPass());
            	System.out.println(beanCorreo.getPort());
            	System.out.println(beanCorreo.getSender());
            	System.out.println(beanCorreo.getStarttls());
            	System.out.println(beanCorreo.getUser());

        	}else {
        		System.out.println("No hay contenido");
        	}
        		
     
	
	/*
	Context initContext;
	Connection cn = null;
	Statement st = null;
	ResultSet rs = null;

	try {
		initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/prod");
		cn = ds.getConnection();
		
		String sql = "select * from todos_empleados where cedula=13802982";
		st = cn.createStatement();
		rs = st.executeQuery(sql);
		
		while(rs.next()) {
			System.out.println("Nombre: " + rs.getString("nombre1"));
			System.out.println("Apellido: " + rs.getString("apellido1"));
		}
	} catch (SQLException ex) {
		log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
	} catch (NamingException ex) {
		log.error("Error al intentar obtener el DataSource: " + ex.getMessage());
	}finally {
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException ex) {
	            log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
	        }
	    }

	    if (st != null) {
	        try {
	            st.close();
	        } catch (SQLException ex) {
	            log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
	        }
	    }

	    if (cn != null) {
	        try {
	            cn.close();
	        } catch (SQLException ex) {
	            log.error("Error (" + ex.getErrorCode() + "): " + ex.getMessage());
	        }
	    }
	}
	    
	*/
	/******************************************/
	/********** Prueba Registro Foto **********/
	/******************************************/	
	/*
	String sql="insert into personal.imagen_empleado values (123456,?)";
	PreparedStatement ps = conexion.prepareStatement(sql);
	File archivo = new File("C:/Users/juferrer/Desktop/Vade.jpg");
	input = new FileInputStream(archivo);
	ps.setBinaryStream(1, input);
	ps.executeQuery();
	/*
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
					  + "where ci_especialista=5963508"; 
	busca1 = conexion.createStatement();
	cursor = busca1.executeQuery(parametros);
	
	ParametroCita param = new ParametroCita();
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
					listado.add(i , "0" + (hh_inicio - 12) + ":" + "0" + intervalo + " p.m.");	
				}else listado.add(i , "0" + (hh_inicio - 12) + ":" + intervalo + " p.m." );
			}
			i ++;
		}//Fin for interno	
		i = i - 1;
		hh_inicio ++;
    }//Fin for externo
	
	
	System.out.println("-------- Inicio Horario -------- ");
	for (int m = 0; m < listado.size(); m ++) {
		
		System.out.println(listado.get(m));
	}
	System.out.println("-------- Fin Horario -------- ");
	List<String> citasSolicitadas = new ArrayList<String>();
	//conexion = getConexion();
	busca1 = conexion.createStatement();
	
	String MedSql = "select "
					  + "hh_cita "
				  + "from salud.cm_cita_medica "
				  + "where ci_especialista = 11983569 and fe_cita ='9/4/2018' and st_cita = 'S' order by fe_cita";
	cursor = busca1.executeQuery(MedSql);
	Cita tempCita = new Cita();
	while(cursor.next()) {
		String hh_cita 				= cursor.getString("hh_cita");
		
		citasSolicitadas.add(hh_cita);
	}

	for (int m = 0; m < citasSolicitadas.size(); m ++) {
	    System.out.println( "hora: " + citasSolicitadas.get(m)  );
	}
	
	System.out.println("-------- Inicio Listado Final -------- ");
	
		for (int i = 0; i < listado.size(); i ++) {
			//System.out.println(listado.get(m));
			for (int j = 0; j < citasSolicitadas.size(); j ++) {
			    //System.out.println( "hora: " + citasSolicitadas.get(m)  );
				if(citasSolicitadas.get(j).equals(listado.get(i))) {
					listado.remove(i);
				}
			}
		}
		
		Iterator<String> listaFin = listado.iterator();
		while(listaFin.hasNext()) {
			System.out.println(listaFin.next());
		}
		
	System.out.println("-------- Fin Listado Final -------- ");
	
	/*******************************************/
	/********** Prueba Registro Talla **********/
	/*******************************************/
	/*
	DatosTallas talla = new DatosTallas();
	int respuesta = talla.getCantidadRegistro(13405818);
	if(respuesta > 0) {
		System.out.println("Ya se ha registrado las tallas para este empleado");
	}else {
		System.out.println("No se ha registrado las tallas para este empleado");
	}
	*/
	
	/******************************************/
	/********** Prueba Carga Medicos **********/
	/******************************************/
	/*
	Connection conexion   = null;
	Statement busca       = null;
	ResultSet cursor 	  = null;
	String username = "admssps";
	String password = "admssps";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.OracleDriver";
	String url = jdbc + hostname + port +  database;
	 
	Class.forName(classname);
	conexion = DriverManager.getConnection(url,username,password);
	
		List<String> nombre = new ArrayList<String>();
		//conexion = getConexion();
		busca = conexion.createStatement();
		
		String MedSql = "select "  
	    		+ "initcap("
	    		+ "case "
	      			+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
	      			+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
	      			+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
	      		+ "end) nb_medico "
	      + "from personal.medicos med "
	      + "join personal.todos_empleados emp on med.cedula = emp.cedula";

		cursor = busca.executeQuery(MedSql);
		while(cursor.next()) {
			nombre.add(cursor.getString(1));
		}*/
	/*
	for (int m = 0; m < nombre.size(); m ++) {
	    System.out.println(nombre.get(m).toString());
	}
	
	
	
	/***********************************************/
	/********** Prueba Parametros Horario **********/
	/***********************************************/
	/*
	Connection conexion   = null;
	Statement busca       = null;
	ResultSet cursor 	  = null;
	String username = "admssps";
	String password = "admssps";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.OracleDriver";
	String url = jdbc + hostname + port +  database;
	 
	Class.forName(classname);
	conexion = DriverManager.getConnection(url,username,password);
	/*
	List<Medico> listaMedico = new ArrayList<>();
	
	busca = conexion.createStatement();
	String MedSql = "select "  
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
      + "where (med.in_especialidad = 'N' and in_turno in('M','T') ) "
      		 + "and med.situacion!='E' and emp.codigo_cia = '01' order by nb_medico asc";
	
	cursor = busca.executeQuery(MedSql);
	
	while(cursor.next()) {
		Medico tempMed = new Medico();
		tempMed.setCedula(cursor.getInt("cedula"));
		tempMed.setNb_medico(cursor.getString("nb_medico"));
		tempMed.setIn_turno(cursor.getString("in_turno"));
		tempMed.setNb_turno(cursor.getString("nb_turno"));
		tempMed.setIn_especialidad(cursor.getString("in_especialidad"));
		tempMed.setNb_especialidad(cursor.getString("nb_especialidad"));
		
		listaMedico.add(tempMed);
	}

	busca.close();	// Cerramos la consulta

	String parametrosSQL = "insert into salud.cm_param_horario"
			+ "(ci_especialista,"
			+ "nb_especialista,"
			+ "nu_citas,"
			+ "nu_intervalo,"
			+ "in_turno,"
			+ "in_especialidad,"
			+ "hh_inicio)"
			+ "values(?,?,?,?,?,?,?)";	
	PreparedStatement paramHorario = conexion.prepareStatement(parametrosSQL);
	for(int i = 0; i<listaMedico.size(); i++) {
		paramHorario.setInt(1, listaMedico.get(i).getCedula());
		paramHorario.setString(2, listaMedico.get(i).getNb_medico());
		paramHorario.setInt(3, 10);
		paramHorario.setInt(4, 30);
		paramHorario.setString(5, listaMedico.get(i).getIn_turno());
		paramHorario.setString(6, listaMedico.get(i).getIn_especialidad());
		paramHorario.setInt(7, 8);
		paramHorario.execute();
	}
	
	conexion.close();		// Cerramos la conexion
	cursor.close();			// Cerramos el cursor
	
	*/
	/**********************************************/
	/********** Prueba de String a Array **********/
	/**********************************************/	
	
	/*
	String cadena = "08:00 a.m.|09:30 a.m.|11:30 a.m.";
	
	String[] partes = cadena.split("\\|");
	//String separador = java.util.regex.Pattern.quote("|");
	//String[] parts = cadena.split(separador);
	for(int i = 0; i<partes.length; i++) {
		System.out.println(partes[i]);	
	}
	*/
	
	/**************************************/
	/********** Prueba de Correo **********/
	/**************************************/
	/*
	ArrayList<String> correos = new ArrayList<String>();
	Connection conexion   = null;
	Statement buscaCorreo = null;
	ResultSet cursor 	  = null;
	String username = "admssps";
	String password = "admssps";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.OracleDriver";
	String url = jdbc + hostname + port +  database;
	 
	Class.forName(classname);
	conexion = DriverManager.getConnection(url,username,password);
	
	
	buscaCorreo = conexion.createStatement();

	String sql = "SELECT tx_email_propio , tx_email_bcv FROM PERSONAL.EMPLEADO_DIRECCION where nu_cedula = 13802982";
	cursor = buscaCorreo.executeQuery(sql);
	
	while(cursor.next()) {
		correos.add(cursor.getString(1));
		correos.add(cursor.getString(2));
	}
	
	Properties properties = new Properties();
	Session session;
	properties.put("mail.smtp.host", "mail.intra.bcv");
	properties.put("mail.smtp.starttls.enable", "true");
	properties.put("mail.smtp.port",25);
	properties.put("mail.smtp.mail.sender","admsspv@bcv.org.ve");
	properties.put("mail.smtp.user", "admsspv");
	properties.put("mail.smtp.auth", "true");

	session = Session.getDefaultInstance(properties);
	Message msg = new MimeMessage(session);

	String nb_medico = "Dr. Ferrer Julio";
	String fe_cita = "15/02/2018";
	String hh_cita = "08:00 a.m.";
	String nb_ti_cita = "Consulta Médica";
	String mensaje =   "<p>Por medio de la presente, me dirijo a usted con la finalidad de informarle que posee una "
			+ "<strong>" + nb_ti_cita + "</strong> para el día <strong>" + fe_cita +"</strong> a las <strong>" + hh_cita +".</strong> "
			+ "con el especialista <strong>" + nb_medico + ".</strong> <p style='color:red; font-weight: bold;'>Le agradecemos asistir  5 minutos antes de su cita."
			+ "</p>De realizar alguna modificación, se agradece notificarla con 24 horas de anticipación a los teléfonos (0212) 801-1116, "
			+ "(0212) 801-5414, o puede realizarla de manera presencial en el área de servicio médico, en el horario comprendido entre las 7:00 am. a 7:00 pm. "
			+ "que gustosamente lo atenderemos. Por favor, no responder a este correo. No se realizan solicitudes por este medio.<br><br><br></p>"
			+ "<p style='font-weight: bold;'>Atentamente <br><br><br><br>Dpto.  Asistencia Médica y Emergencias<br>Gerencia de Seguridad y Salud en el Trabajo</p>";
	
	//Definimos el tamaño del array con la cantidad que esta en el array correos
			InternetAddress[] address = new InternetAddress[correos.size()];
	//Llenamos el objeto InternetAddress
	for(int i = 0; i < correos.size(); i++) {
		address[i] = new InternetAddress(correos.get(i));
		System.out.println("La pisicion " + i + " para el correo " + correos.get(i).toString());
	}

	msg.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
	//msg.setSentDate(new Date());
	msg.setRecipients(Message.RecipientType.TO, address);
	msg.setSubject("Prueba desde JAVA");
	//msg.setText("Esta parte del body, es una nueva prueba");
	
	msg.setContent(mensaje,"text/html");
	
	Transport t = session.getTransport("smtp");
	t.connect((String)properties.get("mail.smtp.user"), "mznxbcv");
	t.sendMessage(msg, msg.getAllRecipients());
	t.close();
*/	
	/**************************************/
	/********** Prueba de Correo **********/
	/**************************************/

	
	/*
	try {
		//mail.intra.bcv -- puerto 25
		Properties properties = new Properties();
		Session session;
		properties.put("mail.smtp.host", "mail.intra.bcv");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port",25);
		properties.put("mail.smtp.mail.sender","admsspv@bcv.org.ve");
		properties.put("mail.smtp.user", "admsspv");
		properties.put("mail.smtp.auth", "true");
 
		session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
	
		//Array con los correos de destino
		//String[] correos = {"juferrer@bcv.org.ve","obecerra@bcv.org.ve"};
		String[] correos = {"juferrer@bcv.org.ve","oovalles@bcv.org.ve"};
		
		String nb_medico = "Dr. Ferrer Julio";
		String fe_cita = "15/02/2018";
		String hh_cita = "08:00 a.m.";
		String nb_ti_cita = "Consulta Médica";
		String mensaje =   "<p>Por medio de la presente, me dirijo a usted con la finalidad de informarle que posee una "
				+ "<strong>" + nb_ti_cita + "</strong> para el día <strong>" + fe_cita +"</strong> a las <strong>" + hh_cita +".</strong> "
				+ "con el especialista <strong>" + nb_medico + ".</strong> <p style='color:red; font-weight: bold;'>Le agradecemos asistir  5 minutos antes de su cita."
				+ "</p>De realizar alguna modificación, se agradece notificarla con 24 horas de anticipación a los teléfonos (0212) 801-1116, "
				+ "(0212) 801-5414, o puede realizarla de manera presencial en el área de servicio médico, en el horario comprendido entre las 7:00 am. a 7:00 pm. "
				+ "que gustosamente lo atenderemos. Por favor, no responder a este correo. No se realizan solicitudes por este medio.<br><br><br></p>"
				+ "<p style='font-weight: bold;'>Atentamente <br><br><br><br>Dpto.  Asistencia Médica y Emergencias<br>Gerencia de Seguridad y Salud en el Trabajo</p>";
				
		//Definimos el tamaño del array con la cantidad que esta en el array correos
		InternetAddress[] address = new InternetAddress[correos.length];
	
		//Llenamos el objeto InternetAddress
		for(int i = 0; i < correos.length; i++) {
			address[i] = new InternetAddress(correos[i]);
			System.out.println("La pisicion " + i + " para el correo " + correos[i].toString());
		}
			msg.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("Prueba desde JAVA");
			//msg.setText("Esta parte del body, es una nueva prueba");
			
			msg.setContent(mensaje,"text/html");
			
			Transport t = session.getTransport("smtp");
			t.connect((String)properties.get("mail.smtp.user"), "mznxbcv");
			t.sendMessage(msg, msg.getAllRecipients());
			t.close();
		
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
		e.getCause();
		e.getMessage();
	}
	*/
	/***************************************/
	/********** Prueba de persona **********/
	/***************************************/
	/*
	cedula = 13802982;
	modeloFamiliar = new DatosFamiliar(cedula);
	fam = modeloFamiliar.getFamiliar();
	for(int i = 0; i< fam.size(); i++) {
		System.out.println(fam.get(i).getNombreCompleto());
	}
	*/
	
	/*****************************************************/
	/********** Prueba para diferencia de horas **********/
	/*****************************************************/
	/*
	String fechaini = "2018-01-20";
	String fechafin = "2018-01-21";
	String horaIni = "08:30";
	String horaFin = "09:40";
	SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd hh:mm");
	SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm");
	 
    Date diaini=formatoFecha.parse(fechaini + " " + horaIni);
    Date diafin=formatoFecha.parse(fechafin + " " + horaFin);
    
    Date horaini=formatoHora.parse(horaIni);
    Date horafin=formatoHora.parse(horaFin);

    int diferencia=(int) ((diafin.getTime()-diaini.getTime())/1000);
    
    //int diferencia=(int) ((horafin.getTime()-horaini.getTime())/1000);

    int dias=0;
    int horas=0;
    int minutos=0;
    if(diferencia>86400) {
        dias=(int)Math.floor(diferencia/86400);
        diferencia=diferencia-(dias*86400);
    }
    if(diferencia>3600) {
        horas=(int)Math.floor(diferencia/3600);
        diferencia=diferencia-(horas*3600);
    }
    if(diferencia>60) {
        minutos=(int)Math.floor(diferencia/60);
        diferencia=diferencia-(minutos*60);
    }
    System.out.println(horas + " horas, "  +minutos + " minutos");
    System.out.println("Hay " + dias + " dias, " + horas + " horas, " + minutos + " minutos");
	*/
	
	/*
	try  {
        
        LocalTime ingreso = LocalTime.parse("08:30");
        
        LocalTime salida  = LocalTime.parse("08:40");

        int minutes = (int) ChronoUnit.MINUTES.between(ingreso, salida);
        
        System.out.println(minutes);
        
    } catch(DateTimeParseException e) {
        System.out.println("Fecha de ingreso o salida inválida");
    }
	/*
	/******************************************************/
	/********** Prueba para diferencia de fechas **********/
	/******************************************************/
	/*
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	 
	Date fechaInicial=dateFormat.parse("2018-01-20");
	Date fechaFinal=dateFormat.parse("2018-01-20");

	int dias=(int) ((fechaFinal.getTime()-fechaInicial.getTime())/86400000);

	System.out.println("Hay "+dias+" dias de diferencia");
	*/
	
	
	/*************************************************/
	/********** Prueba para generar horario **********/
	/*************************************************/
	/*
	int hora_ini = 7;
	int cantidadCita = 22;
	int tiempo_cita  = 30;
	

	
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
		for (int m = 0; m < listado.size(); m ++) {
			System.out.println( listado.get(m) );
		}
	*/
	}

	private static int cedula;
	
}
