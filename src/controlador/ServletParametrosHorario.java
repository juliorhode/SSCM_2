package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bean.Medico;
import bean.ParametroCita;
import modelo.DatosMedico;
import modelo.DatosParametrosCitas;

/**
 * Servlet implementation class ServletParametrosHorario
 */
@WebServlet({ "/ServletParametrosHorario", "/Param" })
public class ServletParametrosHorario extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletParametrosHorario() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		doGet(request, response);
		response.setContentType("text/html;charset=UTF-8");
		
		/**********************************************************************
		 * ESTOS DATOS VIENEN DE ParametroHorarioUnica.jsp --> paramHorario.js
         *********************************************************************/
		//Inicializamos las variables de clase con los valores de la pagina
		parametro = request.getParameter("parametro");
		nu_citas 			= Integer.parseInt(request.getParameter("nu_citas"));
		nu_intervalo 		= Integer.parseInt(request.getParameter("nu_intervalo"));
		in_especialidad 	= request.getParameter("in_especialidad");
		hora_inicio 		= Integer.parseInt(request.getParameter("hora_inicio"));
		in_turno 			= request.getParameter("in_turno");
		
		try {
			HttpSession sesion = request.getSession(false);
			conexion = (Connection) sesion.getAttribute("pool");	
			if (conexion.isClosed()) {
				request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
			}else {
				//Enviamos los datos al Bean ParametroCita
				param = new ParametroCita();
				param.setNu_citas(nu_citas);
				param.setNu_intervalo(nu_intervalo);
				param.setIn_especialidad(in_especialidad);
				param.setHh_inicio(hora_inicio);
				
				//Enviamos al modelo DatosParametrosCitas el objeto del bean ParametroCita
				try {
					switch(parametro) {
						case "unica":
							obetenerHorarioGenerado(request,response,in_especialidad,in_turno);
							break;
						case "individual":
							break;
						case "verifica":
							//verificarCita(request, response, cedula_empleado, fecha_cita, tipo_cita);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					log.info(errors.toString());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
		}
		
		
	}
	
	private void obetenerHorarioGenerado(HttpServletRequest request, HttpServletResponse response, String in_especialidad, String in_turno) throws SQLException, IOException {
		// TODO Auto-generated method stub
		try (PrintWriter out = response.getWriter()) {
			try {
				modelMedicos = new DatosMedico(in_especialidad,in_turno, conexion);
				cedulaMedicos = modelMedicos.getEspecialista();
				modeloParametros = new DatosParametrosCitas(conexion);
				boolean flag = modeloParametros.insertaParametros(param,cedulaMedicos);
				/**********************************************************************************************
				 * CREACION DEL HORARIO PARA EL ESPECIALISTA (lateral_usuario_der.html)
				 * --------------------------------------------------------------------------------------------
				 * EL PROCESO OCURRE AL INSERTAR LOS DATOS QUE SE SOLICITAN Y POR MEDIO DE AJAX
				 * SE ENVIA ESE DATO A ESTE SERVLET, SE INSERTA LA INFORMACIÓN Y SE COLOCA LA INFORMACION 
				 * QUE SE GENERE AQUI.
				 * 
				 * NOTA IMPORTANTE: 
				 * SI EL ESPECIALISTA NO TIENE NINGUN HORARIO GENERADO, PUES NO SE VA A PERMITIR EL REGISTRO 
				 * DE ALGUNA CITA CON ESE ESPECIALISTA EN PARTICULAR, YA QUE EXISTE UNA RELACION PADRE-HIJO
				 * CON LAS TABLAS (salud.cm_param_horario Y salud.cm_cita_medica)
				 **********************************************************************************************/
				if (flag == true) {
					horario = modeloParametros.getHorario(param);
					out.println("<div class='col-sm-12' style='margin-top: -50%;margin-left: 35%; position: absolute;'>");
            		out.println("<div class='alert alert-success alert-dismissable fade in' align='center'>");
            		out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
            		out.println("<strong> Se ha almacenado con exito el nuevo horario!!! </strong>");
            		out.println("<hr>");
            		out.println("<div class='panel panel-default'>");
            		out.println("<div class='panel-heading'>");
            		out.println("<h4 class='panel-title' align='center' style='font-weight: bold;'>Horario Generado</h4>");
            		out.println("</div>");
            		out.println("<div class='panel-body' align='center'>");
	            		for(int i = 0; i < nu_citas; i++) {
	            		out.println("<label>" + horario.get(i) + "</label>");
	            		out.println("<br>");
	            		}
            		out.println("</div>");
            		out.println("</div>");
            		out.println("</div>");
            		out.println("</div>");

            		out.println("<script> $('#hora_inicio').val('');"
            		+ "$('#nu_citas').val('');"
            		+ "$('#nu_intervalo').val('');"
            		+ "$('input:checkbox').prop('checked', false);"
            		+ "$('input:radio').prop('checked', false);"
            		+ "</script>");
	            }else {
	            	out.println("<br>");
	            	out.println("<div class='col-sm-11'>");
	            	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
		            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
		            out.println("<strong> No se pudo almacenadar el nuevo horario!!! </strong>");
		            out.println("</div>");
		            out.println("</div>");
	            }
			}catch(Exception e) {
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				log.info(errors.toString());
			}
		}
	}

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(ServletParametrosHorario.class);

	private ParametroCita param;
	private DatosParametrosCitas modeloParametros;
	private DatosMedico modelMedicos;
	private String parametro;
	private int cedula_empleado;
	private int cedula_especialista;
	private int nu_citas;
	private int nu_intervalo;
	private String in_turno;
	private String in_especialidad;
	private int hora_inicio;
	private String fecha_cita;
	private String tipo_cita;
	ArrayList<String> horario;
	List<Medico> cedulaMedicos;
	private Connection conexion;
}
