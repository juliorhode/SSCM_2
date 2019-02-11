package controlador;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import bean.Cita;
import modelo.DatosCitaMedica;
import modelo.DatosMedico;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class ServletReporte
 */
@WebServlet("/ServletReporte")
public class ServletReporte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletReporte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		try (PrintWriter out = response.getWriter()){
			initialcontext = new InitialContext();
			context = new InitialContext();
			datasource = (DataSource)context.lookup("jdbc/pool");
			conexion = datasource.getConnection();
			
			tipoCita="";
			medico=request.getParameter("optMedico");
			fecha= request.getParameter("fecha");
			DatosCitaMedica agendaTemp = new DatosCitaMedica(conexion);
			cita = agendaTemp.getListado(tipoCita, fecha, medico);
			
			String fecha = request.getParameter("FechaSolicitud");
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel='stylesheet' href='css/bootstrap.css'>");
			out.println("<link rel='stylesheet' href='css/w3.css'>");
			out.println("<script src='js/jQuery_v3.3.1.js'></script>");
			out.println("<script src='js/jquery.PrintArea.js'></script>");
			out.println("</head>");
			out.println("<body>");
			//out.println("<img id='logo-header' src='imagenes/logo2.png' style='width: 10%; float:left; margin-left: 10px; margin-top: 10px;'>");
			out.println("<div class='w3-container' id='contenido'>");
			out.println("<h3 style='text-align: center;'>Gerencia de Seguridad y Salud en el Trabajo</h3>");
			out.println("<h3 style='text-align: center;'>Agenda Médica</h3>");
			
			out.println("<table class='w3-table-all w3-centered'>");
			out.println("<thead>");
			out.println("<tr style='background-color:#335f7d; font-size: 14px;color: white;font-weight: bold;'>");
			out.println("<th>CI. Emp.</th>");
			out.println("<th>Nombre Empleado</th>");
			out.println("<th>CI. Fam.</th>");
			out.println("<th>Nombre Familiar</th>");
			out.println("<th>N° Contacto</th>");
			out.println("<th>Fecha Cita</th>");
			out.println("<th>Hora Cita</th>");
			out.println("<th>Medico</th>");
			out.println("<th>Motivo</th>");
			out.println("</tr>");
			out.println("</thead>");//<!-- Fin thead -->
			out.println("<tbody>");
			for(int i = 0; i < cita.size(); i++) {
				out.println("<tr style='font-size: 12px;font-weight: bold;'>");
					out.println("<td>" + cita.get(i).getCi_empleado()   + "</td>");
					out.println("<td>" + cita.get(i).getNb_empleado()   + "</td>");
					out.println("<td>" + cita.get(i).getCi_familiar()   + "</td>");
					out.println("<td>" + cita.get(i).getNb_familiar()   + "</td>");
					if(cita.get(i).getExtension() == null) {
						out.println("<td>" + cita.get(i).getCelular()   + "</td>");
					}else {
						out.println("<td>" + cita.get(i).getExtension() +" / " + cita.get(i).getCelular()   + "</td>");	
					}
					
					out.println("<td>" + cita.get(i).getFe_cita()       + "</td>");
					if(cita.get(i).getHh_cita() == null){
						out.println("<td>0</td>");
					} else {
						out.println("<td>" + cita.get(i).getHh_cita() + "</td>");
					}
					DatosMedico especialistas = new DatosMedico(conexion);
					String nombreMedico = especialistas.getNombreCompleto(cita.get(i).getCi_especialista());
					out.println("<td>" + nombreMedico 					+ "</td>");
					out.println("<td>" + cita.get(i).getNb_ti_solicitud() + "</td>");
				out.println("</tr>");
			}
			out.println("</tbody>");//<!-- Fin tbody -->
			out.println("</table>");//<!-- Fin table -->
			out.println("</div>");
			
			out.println("<div id ='prueba' style='margin-top: 2%; margin-left: 46%;'>");
			out.println("<input type='button' class ='btn btn-primary' value='Imprimir' id='print'>");
			out.println("</div>");
			out.println("<script>$('#print').click(function (){$('#contenido').printArea();})</script>");
			out.println("</body>");
			out.println("</html>");
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*
		try {
			initialcontext = new InitialContext();
			context = new InitialContext();
			datasource = (DataSource)context.lookup("jdbc/pool");
			conexion = datasource.getConnection();
			
				File reporteFile = new File(System.getenv("DOMAIN_HOME") + File.separator + "resources" + File.separator + "Reporte_Agenda.jasper");
		        Map<String, Object> parametros = new HashMap<String,Object>();
		    	//parametros.put("fecha","05/12/2018");
		    	parametros.put("fecha",request.getParameter("FechaSolicitud"));
		    	
	    		response.setContentType("application/pdf");
	    		
	    		
	    		JasperReport report = (JasperReport)JRLoader.loadObject(reporteFile);
	    		JasperPrint print = JasperFillManager.fillReport(report, parametros,conexion);
	    		
	    		byte[] bytes = JasperExportManager.exportReportToPdf(print);
	    		
	    		response.setContentLength(bytes.length);
	    		
	    		ServletOutputStream salida = response.getOutputStream();
	    		
	    		salida.write(bytes);
	    		salida.flush();
	    		salida.close();
		}
		catch (NullPointerException e) {
			request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
		}
		catch(JRException e) {
			e.getMessage();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		*/
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/	
	private static Logger log = Logger.getLogger(ServletPersona.class);
	private Connection conexion;
	private DataSource datasource;
	private InitialContext initialcontext;
	private Context context;
	
	private String tipoCita ="";
	private String medico ="";
	private String fecha;
	private List<Cita> cita;
}
