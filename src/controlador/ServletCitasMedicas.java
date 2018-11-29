package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bean.Cita;
import bean.Empleado;
import bean.Familiar;
import bean.Medico;
import modelo.DatosCitaMedica;
import modelo.DatosEmpleado;
import modelo.DatosFamiliar;
import modelo.DatosMedico;
import modelo.DatosCorreo;

/**
 * Servlet implementation class ServletCitasMedicas
 */
@WebServlet({ "/ServletCitasMedicas", "/Citas" })
public class ServletCitasMedicas extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCitasMedicas() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		solicitante = request.getParameter("solicitante");
		
		try {
			HttpSession sesion = request.getSession(false);
			conexion = (Connection) sesion.getAttribute("pool");	
			if (conexion.isClosed()) {
				request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
			}else {
				/*****************************************/
				/************** HORA ACTUAL **************/
				/*****************************************/
				int hora, minutos, segundos;
				Calendar calendario = new GregorianCalendar();
				hora 				= calendario.get(Calendar.HOUR_OF_DAY);
				minutos 			= calendario.get(Calendar.MINUTE);
				segundos 			= calendario.get(Calendar.SECOND);
				String h 			= Integer.toString(hora);
				String m 			= Integer.toString(minutos);
				String s 			= Integer.toString(segundos);
				String horaActual 	= h +":" + m + ":" + s;
				
				switch (solicitante) {
				case "EMP":
					st_cita 	= request.getParameter("status");
					ci_empleado = Integer.parseInt(request.getParameter("cedulaEmp"));
					try {
						modeloEmpleado 	= new DatosEmpleado(ci_empleado,conexion);
						beanEmpleado 	= modeloEmpleado.getDatoEmpleado();
						nb_empleado 	= beanEmpleado.getNb_empleado();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					ci_familiar 		= Integer.parseInt(request.getParameter("cedulaFam"));
					nb_familiar 		= " No Aplica ";
					in_ti_cita 			= request.getParameter("tipoCita");
					fe_cita 			= request.getParameter("fechaCita");
					hh_cita 			= request.getParameter("horaCita");
					ci_medico 			= Integer.parseInt(request.getParameter("cedulaMed"));
					nb_medico 			= request.getParameter("nombreMed");
					nb_ti_solicitud 	= request.getParameter("tipoSolicitud");
					nb_usuario_modific 	= request.getParameter("usuario");
					fe_solicitud 		= request.getParameter("fechaSolicitud");
					
					
					
					try {
						beanCita = new Cita();
						beanCita.setSt_cita(st_cita);
						beanCita.setCi_empleado(ci_empleado);
						beanCita.setNb_empleado(nb_empleado);
						beanCita.setCi_familiar(ci_familiar);
						beanCita.setNb_familiar(nb_familiar);
						beanCita.setIn_ti_cita(in_ti_cita);
						beanCita.setFe_cita(fe_cita);
						beanCita.setHh_cita(hh_cita);
						beanCita.setCi_especialista(ci_medico);
						
						
						DatosMedico med = new DatosMedico(ci_medico,conexion);
						List<Medico> listMedico = med.getDatosMedicos();
						String especialidadMedico ="";
						for(int i = 0; i < listMedico.size(); i++) {
							especialidadMedico = listMedico.get(i).getIn_clase_medico();
						}
						
						beanCita.setNb_especialista(nb_medico);
						beanCita.setNb_ti_solicitud(nb_ti_solicitud);
						beanCita.setNb_usuario_modific(nb_usuario_modific);
						beanCita.setFe_solicitud(fe_solicitud);
						beanCita.setHh_solicitud(horaActual);
						
						insertaCita(request,response,beanCita,especialidadMedico);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					break;
				case "FAM":
					st_cita 	= request.getParameter("status");
					ci_empleado = Integer.parseInt(request.getParameter("cedulaEmp"));
					ci_familiar = Integer.parseInt(request.getParameter("cedulaFam"));
					try {
						modeloEmpleado 	= new DatosEmpleado(ci_empleado,conexion);
						beanEmpleado 	= modeloEmpleado.getDatoEmpleado();
						nb_empleado 	= beanEmpleado.getNb_empleado();
						modeloFamiliar 	= new DatosFamiliar (ci_empleado,ci_familiar,conexion);
						
						//El nombre esta saliendo mal.... arreglar esto
						
						familiar 	= modeloFamiliar.getDatoFamiliar();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					in_ti_cita 			= request.getParameter("tipoCita");
					fe_cita 			= request.getParameter("fechaCita");
					hh_cita 			= request.getParameter("horaCita");
					ci_medico 			= Integer.parseInt(request.getParameter("cedulaMed"));
					nb_medico 			= request.getParameter("nombreMed");
					nb_ti_solicitud 	= request.getParameter("tipoSolicitud");
					nb_usuario_modific 	= request.getParameter("usuario");
					fe_solicitud 		= request.getParameter("fechaSolicitud");
					
					
					for(int i = 0; i<familiar.size(); i++) {
						nb_familiar = familiar.get(i).getNb_familiar();
					}
					try {
						beanCita = new Cita();
						
						beanCita.setSt_cita(st_cita);
						beanCita.setCi_empleado(ci_empleado);
						beanCita.setNb_empleado(nb_empleado);
						beanCita.setCi_familiar(ci_familiar);
						beanCita.setNb_familiar(nb_familiar);
						beanCita.setIn_ti_cita(in_ti_cita);
						beanCita.setFe_cita(fe_cita);
						beanCita.setHh_cita(hh_cita);
						beanCita.setCi_especialista(ci_medico);
						beanCita.setNb_especialista(nb_medico);
						beanCita.setNb_ti_solicitud(nb_ti_solicitud);
						beanCita.setNb_usuario_modific(nb_usuario_modific);
						beanCita.setFe_solicitud(fe_solicitud);
						beanCita.setHh_solicitud(horaActual);
						
						DatosMedico med = new DatosMedico(ci_medico,conexion);
						List<Medico> listMedico = med.getDatosMedicos();
						String especialidadMedico ="";
						for(int i = 0; i < listMedico.size(); i++) {
							especialidadMedico = listMedico.get(i).getIn_clase_medico();
						}
						
						insertaCita(request,response,beanCita, especialidadMedico);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					break;
					
				case "EMERG":
					st_cita 			= request.getParameter("status");
					ci_empleado 		= Integer.parseInt(request.getParameter("cedulaEmp"));
					ci_familiar 		= Integer.parseInt(request.getParameter("cedulaFam"));
					in_ti_cita 			= request.getParameter("tipoCita");
					ci_medico 			= Integer.parseInt(request.getParameter("cedulaMed"));
					nb_medico 			= request.getParameter("nombreMed");
					fe_cita 			= request.getParameter("fechaCita");
					fe_solicitud 		= fe_cita;
					hh_solicitud 		= horaActual;
					nb_ti_solicitud 	= request.getParameter("tipoSolicitd");
					tx_dato_modificado 	= request.getParameter("motivo");
					nb_usuario_modific 	= request.getParameter("usuario");
					
					try {
						if (ci_familiar!=0) {
							
							modeloEmpleado 	= new DatosEmpleado(ci_empleado,conexion);
						
							beanEmpleado 	= modeloEmpleado.getDatoEmpleado();
							nb_empleado 	= beanEmpleado.getNb_empleado();
							modeloFamiliar 	= new DatosFamiliar (ci_empleado,ci_familiar,conexion);
							
							//El nombre esta saliendo mal.... arreglar esto
							
							familiar 		= modeloFamiliar.getDatoFamiliar();
							
							for(int i = 0; i<familiar.size(); i++) {
									nb_familiar = familiar.get(i).getNb_familiar();
							}
							
						}else {
							modeloEmpleado 	= new DatosEmpleado(ci_empleado,conexion);
							beanEmpleado 	= modeloEmpleado.getDatoEmpleado();
							nb_empleado 	= beanEmpleado.getNb_empleado();
						}
						
						beanCita = new Cita();
						
						beanCita.setSt_cita(st_cita);
						beanCita.setCi_empleado(ci_empleado);
						beanCita.setNb_empleado(nb_empleado);
						beanCita.setCi_familiar(ci_familiar);
						if(nb_familiar!=null) {
							beanCita.setNb_familiar(nb_familiar);	
						}else {
							beanCita.setNb_familiar(" No Aplica ");
						}
						beanCita.setIn_ti_cita(in_ti_cita);
						beanCita.setIn_especialidad(in_ti_cita);
						beanCita.setFe_cita(fe_cita);
						beanCita.setCi_especialista(ci_medico);
						beanCita.setNb_ti_solicitud(nb_ti_solicitud);
						beanCita.setNb_usuario_modific(nb_usuario_modific);
						beanCita.setTx_dato_modificado(tx_dato_modificado);
						beanCita.setFe_solicitud(fe_solicitud);
						beanCita.setHh_solicitud(horaActual);
						
						modeloCita = new DatosCitaMedica(conexion);
						
						boolean flag = modeloCita.agregarCita(beanCita);
						try (PrintWriter out = response.getWriter()){
							if(flag==true) {
								out.println("<script>$.alert({title: 'Registro de Emergencia',content: 'La Emergencia fue registrada',type: 'blue',theme: 'bootstrap',});</script>");
							}else {
								out.println("<script>$.alert({title: 'Registro de Cita',content: 'La Emergencia no pudo ser registrada',type: 'red',theme: 'bootstrap',});</script>");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
		}
		
		
	}

	private void insertaCita(HttpServletRequest request, HttpServletResponse response, Cita nuevaCita, String especialidadMedico) throws Exception {
		modeloCita = new DatosCitaMedica(conexion);
		
		boolean flag = modeloCita.agregarCita(nuevaCita,especialidadMedico);
		try (PrintWriter out = response.getWriter()){
			if(flag==true) {
				out.println("<script>$.alert({title: 'Registro de Cita',content: 'La Cita Programada fue registrada',type: 'blue',theme: 'bootstrap',});</script>");
				modeloCorreo = new DatosCorreo(nuevaCita,conexion);
				modeloCorreo.enviaCorreoEmp();
			}else {
				out.println("<script>$.alert({title: 'Registro de Cita',content: 'La Cita Programada no pudo ser registrada',type: 'red',theme: 'bootstrap',});</script>");
			}
			
		}
		
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(ServletCitasMedicas.class);
	
	private String 	st_cita;
	private String 	parametro;
	private String 	solicitante;
	private int 	ci_medico; //cambiarlo luego de realizar los cambion en BBDD a int
	private String 	nb_medico;
	private String 	in_ti_cita;
	private String 	nb_ti_solicitud;
	private String 	fe_cita;
	private String 	hh_cita;
	private String 	fe_solicitud;
	private String 	hh_solicitud;
	private int 	ci_empleado;
	private String 	nb_empleado;
	private int 	ci_familiar;
	private String 	nb_familiar;
	private String 	tx_dato_modificado;
	private String 	nb_usuario_modific;
	
	private Connection conexion;
	private DatosEmpleado 	modeloEmpleado;
	private DatosFamiliar 	modeloFamiliar;
	private DatosCitaMedica modeloCita;
	private DatosCorreo     modeloCorreo;
	private List<Familiar> 	familiar;
	private Empleado 		beanEmpleado;
	private Cita 			beanCita;
	
	
}
