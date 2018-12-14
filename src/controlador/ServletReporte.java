package controlador;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
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
		//doGet(request, response);
		
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
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/	
	private static Logger log = Logger.getLogger(ServletPersona.class);
	private Connection conexion;
	private DataSource datasource;
	private InitialContext initialcontext;
	private Context context;
}
