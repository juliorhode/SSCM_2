package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonObject;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Ausentismo;
import bean.Medico;
import modelo.DatosAusentismo;
import modelo.DatosMedico;

/**
 * Servlet implementation class ServletAusentismo
 */
@WebServlet({ "/ServletAusentismo", "/Ausentismo" })
public class ServletAusentismo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAusentismo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		String parametro = request.getParameter("parametro");
		
		HttpSession sesion = request.getSession(false);
		/*Voy a comentar esta parte como parte de pruebas a ver
		 * si se puede resolver el tema de la caida de conexion
		 * por aqui*/
		//conexion = (Connection) sesion.getAttribute("pool");	
		//if (conexion == null || conexion.isValid(5) ) {
		ServletConexion sc = new ServletConexion();
		conexion = sc.getConexion();
		sesion.setAttribute("pool", conexion);
			
		int cedula_especialista;
		switch (parametro) {
		case "buscaMedico":
			try {
				cedula_especialista = Integer.parseInt(request.getParameter("cedula_especialista"));
				buscaDatos(request,response,cedula_especialista);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
			}
			break;
		case "inserta":
			try {
				cedula_especialista = Integer.parseInt(request.getParameter("cedula_especialista"));
				insertaAusentismo(request,response,cedula_especialista);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
			}
			break;
		case "medicoModal":
			try {
				buscaMedicosModal(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				/*
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				*/
				e.printStackTrace();
			}
			break; 
		case "prueba":
			try {
				respuesta(request,response);
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
	}

	private void respuesta(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
		try (PrintWriter out = response.getWriter()){
			String valor = request.getParameter("json.nombre");
			out.print(valor);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void buscaMedicosModal(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		medico = new DatosMedico(conexion);
		List<Medico> datos = medico.getTodosMedicos();
		try (PrintWriter out = response.getWriter()){
				
			for(int i = 0; i < datos.size(); i++) {
				/* cargamos los datos del medico*/
				out.println("<tr>");                                                 
				out.println("<td><input name='optMedico' id='optMedico' type='radio' value='" + datos.get(i).getCedula() + "'>&nbsp;&nbsp;" + datos.get(i).getCedula() + "</td>");
				out.println("<td>"+ datos.get(i).getNb_medico() +"</td>");
				out.println("<td>"+ datos.get(i).getNb_especialidad() +"</td>");
				out.println("</tr>");
				/***********************************************************/
				/***El salto de color entre las filas es de blanco y azul***/
				/***********************************************************/
				out.println("<style>#medicosModal > tr:nth-child(odd) > td, th{background-color: #ffffff;} #medicosModal > tr:nth-child(even) > td, th {background-color: #e7f5fe;}</style>");
				out.println("<script>inputModal();</script>");
			}
		}
	}
	
	private void insertaAusentismo(HttpServletRequest request, HttpServletResponse response, int cedula_especialista) throws IOException {
		// TODO Auto-generated method stub
		
		fecha_ini = request.getParameter("fecha_ini");
    	hora_ini = request.getParameter("hora_ini");
 		fecha_fin = request.getParameter("fecha_fin");
 		hora_fin = request.getParameter("hora_fin");
 		//in_tipo_especial = request.getParameter("in_tipo_especial");
 		nb_tipo_especial = request.getParameter("nb_tipo_especial");
 		nb_especialista = request.getParameter("nb_especialista");
 		txt_motivo = request.getParameter("motivo");
		
			/*********************************************************/
			/********** Diferencia de Dias, Horas y minutos **********/
			/*********************************************************/
		try {
			SimpleDateFormat formato = new SimpleDateFormat("dd/mm/yyyy hh:mm");
		    Date fechaInicial = formato.parse(fecha_ini + " " + hora_ini);
		    Date fechaFinal   = formato.parse(fecha_fin + " " + hora_fin);
		    int diferencia = (int) ( ( fechaFinal.getTime() - fechaInicial.getTime() )/1000);
		    nu_dias    = 0;
		    nu_horas   = 0;
		    nu_minutos = 0;
		    if(diferencia > 86400) {
		    	nu_dias = (int)Math.floor(diferencia / 86400);
		        diferencia = diferencia - (nu_dias * 86400);
		    }
		    if(diferencia > 3600) {
		    	nu_horas = (int)Math.floor(diferencia / 3600);
		        diferencia = diferencia  -(nu_horas * 3600);
		    }
		    if(diferencia > 60) {
		    	nu_minutos = (int)Math.floor(diferencia / 60);
		        diferencia = diferencia - (nu_minutos * 60);
		    }
			ausentismo = new Ausentismo(fecha_ini, hora_ini, fecha_fin, hora_fin, nu_dias, 
										nu_horas, nu_minutos, in_tipo_especial, nb_tipo_especial, cedula_especialista, 
										nb_especialista,txt_motivo,nb_usuario);
			DatosAusentismo modeloAusentismo = new DatosAusentismo();
			boolean flag = modeloAusentismo.insertaAusentismo(ausentismo);
			if (flag) {
				try (PrintWriter out = response.getWriter()){
					out.println("<div class='col-md-12'>");
	            	out.println("<div class='alert alert-success alert-dismissable fade in' align='center'>");
		            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
		            out.println("<strong> Se ha almacenado con exito el ausentismo </strong>");
		            out.println("</div>");
		            out.println("</div>");
				}	
			}else {
				try (PrintWriter out = response.getWriter()){
					out.println("<div class='col-md-12'>");
	            	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
		            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
		            out.println("<strong> No se ha podido almacenado el ausentismo </strong>");
		            out.println("</div>");
		            out.println("</div>");
				}
			}
			
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
		}
	}
	
	private void buscaDatos(HttpServletRequest request, HttpServletResponse response, int cedula_especialista) throws Exception {
		
		medico = new DatosMedico(cedula_especialista,conexion);
		List<Medico> datos = medico.getDatosMedicos();
		
		/**********************************************************************************************
		 * DATOS DEL MEDICO (AusentismoMedico.jsp)
		 * --------------------------------------------------------------------------------------------
		 * EL PROCESO ES CUANDO EL USUARIO COLOCA LA CEDULA DEL MEDICIO,EN ESE MOMENTO POR MEDIO DE AJAX
		 * SE ENVIA ESE DATO A ESTE SERVLET Y SE COLOCA LA INFORMACION QUE SE GENERE AQUI
		 **********************************************************************************************/
		try (PrintWriter out = response.getWriter()){
			if(datos.isEmpty()) {
				out.println("<div class='col-md-12'>");
            	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
	            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
	            out.println("<strong> La c&eacute;dula del medico no existe</strong>");
	            out.println("</div>");
	            out.println("</div>");
			}else {
				for(int i = 0; i < datos.size(); i++) {
				
					/* cargamos los datos del medico*/
					out.println("<div class='col-sm-3'>");
					out.println("<div class='form-group'>");
					out.println("<label for='nb_especialista'>Nombre Especialista</label>");
					out.println("<input type='text' id='nb_especialista' class='form-control' disabled='disabled' name='nb_especialista' value='" + datos.get(i).getNb_medico() + "'>" );
					out.println("</div>");
					out.println("</div>");
					
					out.println("<div class='col-sm-2'>");
					out.println("<div class='form-group'>");
					out.println("<label for='in_especialidad' style='text-align: right;'>Especialidad</label>");
					out.println("<input type='text' id='in_especialidad' class='form-control' disabled='disabled' name='in_especialidad' value='" + datos.get(i).getIn_especialidad() + "'>");
					out.println("</div>");
					out.println("</div>");
					
					out.println("<div class='col-sm-2'>");
					out.println("<div class='form-group'>");
					out.println("<label for='in_turno' style='text-align: right;'>Turno</label>");
					out.println("<input type='text' id='in_turno' class='form-control' disabled='disabled' name='in_turno' value='" + datos.get(i).getIn_turno() + "'>");
					out.println("</div>");
					out.println("</div>");
					
					out.println("<div class='col-sm-3'>");
					out.println("<div class='form-group'>");
					out.println("<label for='nb_tipo_especial' style='text-align: right;'>Tipo Especialidad</label>");
					out.println("<input type='text' id='nb_tipo_especial' class='form-control' disabled='disabled' name='nb_tipo_especial' value='" + datos.get(i).getNb_especialidad()+ "'>");
					out.println("</div>");
					out.println("</div>");
				}
			}
		}
	}


	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/

	Ausentismo ausentismo;
	DatosMedico medico;
	private Connection conexion;
	private String fecha_ini;
	private String hora_ini;
	private String fecha_fin;
	private String hora_fin;
	private int nu_dias;
	private int nu_horas;
	private int nu_minutos;
	private String in_tipo_especial;
	private String nb_tipo_especial;
	private int cedula_especialista;
	private String nb_especialista;
	private String txt_motivo;
	private String nb_usuario;
	private String parametro;
	
}
