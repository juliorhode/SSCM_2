package modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import bean.Cita;
import bean.Correo;

public class DatosCorreo {
	
	public DatosCorreo(Cita nuevaCita,Connection conexion) {
		super();
		this.beanCita = nuevaCita;
		this.conexion = conexion;
	}
	
	public void enviaCorreoEmp() throws SQLException, MessagingException, FileNotFoundException, IOException {
		beanCorreo = new Correo ();
		
		/*Servidor Weblogic*/
		String archivo = System.getenv("DOMAIN_HOME") + File.separator + "resources" + File.separator + "correo.properties";
		if(System.getenv("DOMAIN_HOME") == null) {
			/*Servidor Apache*/
			archivo = System.getProperty("catalina.base") + File.separator + "webapps"+ File.separator + "correo.properties";
		}
		/*
		 * RECORDAR A ANGEL CREAR EL DOMAIN_HOME EN EL ARCHIVO DE PROPIEDADES
		 * */
		properties = new Properties();
		
		properties.load(new FileReader(archivo));
		
		beanCorreo.setAuth(properties.getProperty("auth"));
    	beanCorreo.setHost(properties.getProperty("host"));
    	beanCorreo.setPort(properties.getProperty("port"));
    	beanCorreo.setSender(properties.getProperty("sender"));
    	beanCorreo.setStarttls(properties.getProperty("starttls"));
    	beanCorreo.setUser(properties.getProperty("user"));
    	beanCorreo.setPass(properties.getProperty("pass"));
    	
		properties.put("mail.smtp.host"				, beanCorreo.getHost());
		properties.put("mail.smtp.starttls.enable"	, beanCorreo.getStarttls());
		properties.put("mail.smtp.port"				, beanCorreo.getPort());
		properties.put("mail.smtp.mail.sender"		, beanCorreo.getSender());
		properties.put("mail.smtp.user"				, beanCorreo.getUser());
		properties.put("mail.smtp.auth"				, beanCorreo.getAuth());	
    	
		session = Session.getDefaultInstance(properties);
		Message msg = new MimeMessage(session);
		
		//conexion = getConexion();
		buscaDatos = conexion.createStatement();
		String sqlSolicitud = "select max(nu_solicitud) from SALUD.CM_CITA_MEDICA where ci_empleado=" + beanCita.getCi_empleado();
		cursor = buscaDatos.executeQuery(sqlSolicitud);
		if(cursor.next()){
			nu_solicitud = cursor.getInt(1);
		}
		
		cursor.close();
		buscaDatos.close();
		
		buscaDatos = null;
		cursor     = null;
		
		String nombreSolicitante = beanCita.getNb_empleado(); 
		String nb_ti_cita 		 = beanCita.getNb_ti_solicitud();
		String fe_cita 			 = beanCita.getFe_cita();
		String hh_cita 			 = beanCita.getHh_cita();
		
		int ci_especialista 		 = beanCita.getCi_especialista();
		
		//conexion = getConexion();
		buscaDatos = conexion.createStatement();
		String sqlMedico = "select "  
				    			+ "initcap("
				    			+ "case "
				    			+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
				    			+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
				    			+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
				    			+ "end) nb_medico "
			    			+ "from personal.medicos med "
			    			+ "join personal.todos_empleados emp on med.cedula = emp.cedula "
			    			+ "where med.cedula =" + ci_especialista + " and med.situacion!='E' and emp.codigo_cia = '01' order by nb_medico asc";
		cursor = buscaDatos.executeQuery(sqlMedico);
		if(cursor.next()){
			nb_especialista = cursor.getString(1);
		}
		
		cursor.close();
		buscaDatos.close();
		
		buscaDatos = null;
		cursor     = null;
		
		correos = new ArrayList<String>();
		
		buscaDatos = conexion.createStatement();
		//String sqlCorreo = "SELECT tx_email_propio , tx_email_bcv FROM PERSONAL.EMPLEADO_DIRECCION where nu_cedula =" + beanCita.getCi_empleado();
		String sqlCorreo = "SELECT "
								+ "lower(tx_email_propio) as tx_email_propio, "
								+ "lower(tx_email_bcv) as tx_email_bcv "
						 + "FROM personal.todos_empleados "
						 + "where cedula =" + beanCita.getCi_empleado();
		cursor = buscaDatos.executeQuery(sqlCorreo);
		while(cursor.next()) {
			if(cursor.getString(2) == null) {
				correos.add(cursor.getString(1));
			}else if (cursor.getString(1) == null && cursor.getString(2) == null) {
				break;
			}else {
				correos.add(cursor.getString(1));
				correos.add(cursor.getString(2));
			}
			
		}
		
		if(beanCita.getCi_familiar()== 0) {
			String mensajeEmp =   "<p>Estimado " + nombreSolicitante + "</p><br><p>Por medio de la presente, me dirijo a usted con la finalidad de informarle que posee una "
					+ "<strong>" + nb_ti_cita + "</strong> para el día <strong>" + fe_cita +"</strong> a las <strong>" + hh_cita +"</strong> "
					+ "con el especialista <strong>" + nb_especialista + "</strong>." + "<p style='color:red; font-weight: bold;font-size: 16px;'>Le agradecemos asistir  5 minutos antes de su cita."
					+ "</p>De realizar alguna modificación, se agradece notificarla con 24 horas de anticipación a los teléfonos (0212) 801-1116, "
					+ "(0212) 801-5414, o puede realizarla de manera presencial en el área de servicio médico, en el horario comprendido entre las 7:00 am. a 7:00 pm. "
					+ "que gustosamente lo atenderemos. <br><br><br><p style='color:red; font-weight: bold;font-size: 16px;'>Esta es una cuenta de correo electrónico no monitoreada, no responda o reenvie mensajes a esta cuenta.<br><br><br></p>"
					+ "<p style='font-weight: bold;'>Atentamente <br><br><br><br>Dpto.  Asistencia Médica y Emergencias<br>Gerencia de Seguridad y Salud en el Trabajo</p>";
			msg.setContent(mensajeEmp,"text/html");
			
		}else {
			String mensajeFam =   "<p>Estimado " + nombreSolicitante + "</p>" + "<br><p>Por medio de la presente, me dirijo a usted con la finalidad de informarle que <strong>" + beanCita.getNb_familiar() + "</strong> posee una "
					+ "<strong>" + nb_ti_cita + "</strong> para el día <strong>" + fe_cita +"</strong> a las <strong>" + hh_cita +"</strong> "
					+ "con el especialista <strong>" + nb_especialista + "</strong>." + "<p style='color:red; font-weight: bold;font-size: 16px;'>Le agradecemos asistir  5 minutos antes de su cita."
					+ "</p>De realizar alguna modificación, se agradece notificarla con 24 horas de anticipación a los teléfonos (0212) 801-1116, "
					+ "(0212) 801-5414, o puede realizarla de manera presencial en el área de servicio médico, en el horario comprendido entre las 7:00 am. a 7:00 pm. "
					+ "que gustosamente lo atenderemos. <br><br><br><p style='color:red; font-weight: bold;font-size: 16px;'>Esta es una cuenta de correo electrónico no monitoreada, no responda o reenvie mensajes a esta cuenta.<br><br><br></p>"
					+ "<p style='font-weight: bold;'>Atentamente <br><br><br><br>Dpto.  Asistencia Médica y Emergencias<br>Gerencia de Seguridad y Salud en el Trabajo</p>";
			msg.setContent(mensajeFam,"text/html");
		}
		
		//Definimos el tamaño del array con la cantidad que esta en el array correos
		InternetAddress[] address = new InternetAddress[correos.size()];
		//Llenamos el objeto InternetAddress
		for(int i = 0; i < correos.size(); i++) {
			address[i] = new InternetAddress(correos.get(i));
			//System.out.println("La pisicion " + i + " para el correo " + correos.get(i).toString());
		}
		msg.setFrom(new InternetAddress((String)properties.get("mail.smtp.mail.sender")));
		msg.setRecipients(Message.RecipientType.TO, address);
		msg.setSubject("Solicitud de Cita Programada N°: " + nu_solicitud +".");
		
		
		
		Transport t = session.getTransport("smtp");
		t.connect((String)properties.get("mail.smtp.user"), beanCorreo.getPass());
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
		/*
		cursor.close();	
		buscaDatos.close();
		conexion.close();
		*/
	}
	
	
	private Properties properties;
	private Session session;
	private Message msg;
	
	ArrayList<String> correos;
	private InternetAddress[] address;
	private Transport transp;
	private Correo 	beanCorreo;
	private Cita beanCita;
	
	private int nu_solicitud = 0;
	private String nb_especialista;
	
	private Connection conexion   = null;
	private Statement buscaDatos = null;
	private ResultSet cursor 	  = null;
	
}
