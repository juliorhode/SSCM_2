package controlador;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bean.Empleado;
import bean.Familiar;
import modelo.DatosEmpleado;
import modelo.DatosFamiliar;


@WebServlet({ "/DatosPersona", "/Persona" })
public class ServletPersona extends HttpServlet {

	public ServletPersona() {
        super();
    } 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		response.setContentType("text/html;charset=UTF-8");
		parametro = request.getParameter("parametro");
		cedula = Integer.parseInt(request.getParameter("cedula"));
		try {
			HttpSession sesion = request.getSession(false);
			conexion = (Connection) sesion.getAttribute("pool");	
			if (conexion == null) {
				request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response); 
			}else {
				switch (parametro) {
				case "Emp":
					try {
						obtenerEmpleado(request,response,cedula,conexion);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					} 
					break;
				case "Fam":
					try {
						obtenerFamiliar(request, response, cedula,conexion);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
					break;
				/**********************************************************************************************
				 * ESTO ES LO QUE SE CARGA EN EL LATERAL DERECHO DEL USUARIO (lateral_usuario_der.html)
				 * --------------------------------------------------------------------------------------------
				 * EL PROCESO ES CUANDO EL USUARIO LE DA A LA OPCION SOLICITUD DE CITA DEL PANEL IZQUIERDO,
				 * LE APARECE UNA VENTANA MODAL, SOLICITANDO LA CEDULA. EN ESE MOMENTO POR MEDIO DE AJAX
				 * SE ENVIA ESE DATO A ESTE SERVLET Y COLOCA LA INFORMACION QUE SE GENERE AQUI
				 **********************************************************************************************/
				case "Solicitante":
					try (PrintWriter out = response.getWriter()){
						modeloEmpleado = new DatosEmpleado(cedula, conexion);
						emp = modeloEmpleado.getEmpleado();
						if(emp.isEmpty()) {
							out.println("<script>$.alert({title: 'Busqueda de Empleado',content: 'La c&eacute;dula del empleado no existe',type: 'red',theme: 'bootstrap',});</script>");
							out.println("<label>Sin Registros</label>");
							/* 
							out.println("<div class='col-md-12'>");
			            	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
				            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
				            out.println("<strong> La c&eacute;dula del empleado no existe</strong>");
				            out.println("</div>");
				            out.println("</div>");
				            */
							
				            out.println("<script> $('#cuerpo').empty();$('#lateral_der').hide();$('#listado_cita').hide();$('#listadoCita').hide();</script>");
						}else {
							for(int i = 0; i < emp.size(); i++) {
								out.println("<style> #lateral_der > input[type=text]{font-size: 12px;text-align: center;font-weight: bold;}</style>");
								out.println("<label>C&eacute;dula:</label><br>");
								out.println("<input type='text' class='form-control' disabled='disabled' name='cedula' id='cedula' value='"+ emp.get(i).getCedula() + "'>");
								out.println("<br>");
								out.println("<label>Apellido y Nombre:</label><br>");
								out.println("<input type='text' class='form-control' disabled='disabled' name='nombreSolicitante' value='"+ emp.get(i).getNb_empleado() + "'>");
								out.println("<br>");
								out.println("<label>N&uacute;mero Contacto:</label><br>");
								out.println("<input type='text' class='form-control' disabled='disabled' name='numeroSolicitante' value='"+ emp.get(i).getNu_celular() + "'>");
								out.println("<br>");
								out.println("<label>Usuario:</label><br>");
								out.println("<input type='text' class='form-control' disabled='disabled' name='usuario' id='usuario' value='"+ emp.get(i).getCo_user_id() + "'>");
								out.println("<br>");
								out.println("<br>");
								out.println("<input type='button' class='btn btn-primary' value='Cambio Usuario' id='cambioUsuario' style='margin-left:5%;'>");
								out.println("<script>$('#cuerpo').load('JSP/Usuario/CitasMedicas.jsp');$('#lateral_der').show();$('#listado_cita').show();$('#listadoCita').show();</script>");
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
				break;
				case "SolicitanteAdmin":
					try (PrintWriter out = response.getWriter()){
						modeloEmpleado = new DatosEmpleado(cedula, conexion);
						emp = modeloEmpleado.getEmpleado();
						if(emp.isEmpty()) {
								out.println("<div class='col-md-10'>");
				            	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
					            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
					            out.println("<strong> La c&eacute;dula que busca no existe</strong>");
					            out.println("</div>");
					            out.println("</div>");
					            out.println("</div>");
					            out.println("<input type='hidden' name='flag' id='flag' value='0'>");
						}else {
							for(int i = 0; i < emp.size(); i++) {
								out.println("<div class='col-sm-6'>");
								out.println("<div class='form-group'>");
								out.println("<label for='apellidoNombre' style='text-align: right;'>Apellidos y Nombres</label>");
								out.println("<input type='text' id='apellidoNombre' class='form-control' disabled='disabled' name='apellidoNombre' value='" + emp.get(i).getNb_empleado() + "'>");
								out.println("</div>");
								out.println("</div>");
								out.println("<div class='col-sm-3'>");
								out.println("<div class='form-group'>");
								out.println("<label for='contacto' style='text-align: right;'>Numero de Contacto</label>");
								out.println("<input type='text' id='contacto' class='form-control' disabled='disabled' name='contacto' value='" + emp.get(i).getNu_celular() + "'>");
								out.println("</div>");
								out.println("</div>");
								out.println("<input type='hidden' name='flag' id='flag' value='1'>");
								
							}
						}
					}catch (Exception e) {
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
	
	private void obtenerEmpleado(HttpServletRequest request, HttpServletResponse response, int cedula, Connection conexion) throws Exception{
		// TODO Auto-generated method stub
		try {
			modeloEmpleado = new DatosEmpleado(cedula,conexion);
			emp = modeloEmpleado.getEmpleado();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}
	}
	
	private void obtenerFamiliar(HttpServletRequest request, HttpServletResponse response, int cedula, Connection conexion) throws Exception {
		modeloFamiliar = new DatosFamiliar(cedula,conexion);
		fam = modeloFamiliar.getFamiliar();
		try (PrintWriter out = response.getWriter()){
			//out.println("<div class='col-sm-12'>");
			//out.println("<ul class='list-group'>");
			//out.println("<li class='list-group-item'>");
			out.println("<div class='radio' style='text-align:left; margin-left:5%;'>");
				for(int i =0; i < fam.size(); i++) {
					out.println("<input name='optFamiliar' id='optFamiliar' type='radio' value='"+ fam.get(i).getCedula_familiar() +"'>" + fam.get(i).getNb_familiar());
					out.println("<br>");
				}
			out.println("</div>");
		}
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(ServletPersona.class);
	private Connection conexion;
	private int cedula;
	private DatosEmpleado modeloEmpleado;
	private DatosFamiliar modeloFamiliar;
	private String parametro;
	List<Empleado> emp;
	List<Familiar> fam;
}
