package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@WebServlet({ "/ServletConexion", "/Conn" })
public class ServletConexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletConexion() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			sesion = request.getSession();
			if(sesion.getAttribute("usuario")!=null) {
				sesion.invalidate();
				
				System.out.println("1) No existe conexion");
				System.out.println("2) Obtenemos el JDNI");
				
				/*Servidor Apache*/
				/*
				initialcontext = new InitialContext();
				DataSource datasource = (DataSource) initialcontext.lookup("java:/comp/env/jdbc/pool");
				conexion = datasource.getConnection();
				*/
				
				/*Servidor Weblogic*/
				initialcontext = new InitialContext();
				context = new InitialContext();
				datasource = (DataSource)context.lookup("jdbc/pool");
				conexion = datasource.getConnection();
				
				
				System.out.println("3) Asignamos la conexion de la sesion");
				sesion = request.getSession();
				sesion.setAttribute("pool", conexion);
				System.out.println("4) Redireccionamos a la pagina principal");
				request.getRequestDispatcher("JSP/Usuario/index.jsp").forward(request, response);
			}else {
					/*Servidor Apache*/
					/*
					initialcontext = new InitialContext();
					DataSource datasource = (DataSource) initialcontext.lookup("java:/comp/env/jdbc/pool");
					conexion = datasource.getConnection();
					*/
					
					/*Servidor Weblogic*/
					System.out.println("1) No existe conexion");
					System.out.println("2) Obtenemos el JDNI");
					initialcontext = new InitialContext();
					context = new InitialContext();
					datasource = (DataSource)context.lookup("jdbc/pool");
					conexion = datasource.getConnection();
					
					System.out.println("3) Asignamos la conexion de la sesion");
					sesion = request.getSession();
					sesion.setAttribute("pool", conexion);
					System.out.println("4) Redireccionamos a la pagina principal");
					request.getRequestDispatcher("JSP/Usuario/index.jsp").forward(request, response);
				//}
			}
		} catch (Exception e) {
			//response.sendRedirect("error.jsp");
			e.printStackTrace();
		}
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private HttpSession sesion;
	private Connection conexion;
	private DataSource datasource;
	private Statement  st;
	private ResultSet  rs;
	private InitialContext initialcontext;
	private Context context;
}
