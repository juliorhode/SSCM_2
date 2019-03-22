package controlador;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import sun.print.PrinterJobWrapper;

/**
 * Servlet implementation class ServletConexionAdmin
 */
public class ServletConexionAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConexionAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }
//admin/administracion.jsp
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		//String archivo = System.getProperty("catalina.base") + "/webapps/"+ File.separator + "JDBC_Admin.properties";
		
		/*Servidor Weblogic*/
		String archivo = System.getenv("DOMAIN_HOME") + File.separator + "resources" + File.separator + "JDBC_Admin.properties";
		if (System.getenv("DOMAIN_HOME") == null) {
			/*Servidor Apache*/
			archivo = System.getProperty("catalina.base") + File.separator + "webapps"+ File.separator + "JDBC_Admin.properties";
		}
		
		Properties p = new Properties();
		p.load(new FileReader(archivo));
		hostname = p.getProperty("hostname");
		database = p.getProperty("database");
		classname = p.getProperty("classname");
		jdbc = p.getProperty("jdbc");
		port = p.getProperty("port");
		url = jdbc+":" + "@"+ hostname + ":"+port + "/"+ database;
		try {
			Class.forName(classname);
			sesion = request.getSession();
			System.out.println(sesion.getAttribute("usuario"));
			if(sesion.getAttribute("usuario") == null) {
				sesion.invalidate();
				if(conexion!= null) {
					conexion.close();
					System.out.println("Se cerró la conexion");
				}
				try {
					System.out.println("1) No existe conexion");
					System.out.println("2) Obtenemos el JDNI");
					username = request.getParameter("usuario");
					password = request.getParameter("contraseña");
					conexion = DriverManager.getConnection(url,username,password);
					
					String sqlBusqueda = "SELECT * FROM SALUD.CM_PARAM_HORARIO";
					st = conexion.createStatement();
					rs = st.executeQuery(sqlBusqueda);
					
					System.out.println("3) Asignamos la conexion de la sesion");
					co_cia_fisica = getCodigoFisico(username,conexion);
					sesion = request.getSession();
					sesion.setAttribute("usuario", username);
					sesion.setAttribute("co_cia_fisica", co_cia_fisica);
					sesion.setAttribute("pool", conexion);
					//if(rs.next() || rs==null) {
						System.out.println("4) Redireccionamos a la pagina principal");
						request.getRequestDispatcher("JSP/Admin/administracion.jsp").forward(request, response);
					//}else {
						request.getRequestDispatcher("JSP/Error/UserAutorizacion.jsp").forward(request, response);
					//}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage().toString()); 
					request.getRequestDispatcher("JSP/Error/UserInvalido.jsp").forward(request, response);
				}
			}else {
				if(conexion!=null) {
					if(conexion!= null) {
						conexion.close();
						System.out.println("Se cerró la conexion");
					}
					sesion = request.getSession();
					System.out.println("1) Existe conexion");	
					sesion.setAttribute("pool", conexion);
					System.out.println("2) Existe sesion");
					sesion.setAttribute("usuario", username);
					System.out.println("3) Redireccionamos a la pagina principal");
					request.getRequestDispatcher("JSP/Admin/administracion.jsp").forward(request, response);
					
				}else {
					System.out.println("1) No existe conexion");
					System.out.println("2) Obtenemos el JDNI");
					username = request.getParameter("usuario");
					password = request.getParameter("contraseña");
					conexion = DriverManager.getConnection(url,username,password);
					
					String sqlBusqueda = "SELECT * FROM SALUD.CM_PARAM_HORARIO";
					st = conexion.createStatement();
					rs = st.executeQuery(sqlBusqueda);
					
					System.out.println("3) Asignamos la conexion de la sesion");
					co_cia_fisica = getCodigoFisico(username,conexion);
					sesion = request.getSession();
					sesion.setAttribute("usuario", username);
					sesion.setAttribute("co_cia_fisica", co_cia_fisica);
					sesion.setAttribute("pool", conexion);
					//if(rs.next() || rs==null) {
						System.out.println("4) Redireccionamos a la pagina principal");
						request.getRequestDispatcher("JSP/Admin/administracion.jsp").forward(request, response);
					//}else {
						request.getRequestDispatcher("JSP/Error/UserAutorizacion.jsp").forward(request, response);
					//}
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			request.getRequestDispatcher("JSP/Error/UserInvalido.jsp").forward(request, response);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private String getCodigoFisico(String username, Connection conexion) {
		try {
			st = conexion.createStatement();
			
			String sql = "select emp.co_cia_fisica, ed.co_user_id "
					   + "from todos_empleados emp "
					   + "join empleado_direccion ed on ed.nu_cedula = emp.cedula "
					   + "where ed.co_user_id ='" + username + "'";
					
			rs = st.executeQuery(sql);
			if(rs.next()) {
				co_cia_fisica = rs.getString("co_cia_fisica");	
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return co_cia_fisica;
		
	}
			
			
	private String username;
	private String password;
	private String hostname;
	private String database;
	private String port;
	private String jdbc;
	private String classname;
	private String url;
	private Connection conexion;
	private String co_cia_fisica;
	
	private HttpSession sesion;
	private Statement  st;
	private ResultSet  rs;
}
