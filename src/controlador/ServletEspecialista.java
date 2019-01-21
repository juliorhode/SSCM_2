package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.ArrayList;
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
import bean.Familiar;
import bean.Medico;
import bean.ParametroCita;
import modelo.DatosCitaMedica;
import modelo.DatosMedico;
import modelo.DatosParametrosCitas;

/**
 * Servlet implementation class ServletEspecialista
 */
@WebServlet({ "/ServletEspecialista", "/Especialista" })
public class ServletEspecialista extends HttpServlet {
	
    public ServletEspecialista() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		param = request.getParameter("parametro");
		solicitante = request.getParameter("Solicitante");
		tipo = request.getParameter("tipo");
		turno = request.getParameter("turno");
		
		tipoCita = request.getParameter("tipoCita");
		medico   = request.getParameter("medico");
		fecha    = request.getParameter("fecha");
		
		int hora, minutos;
		Calendar calendario = new GregorianCalendar();
		hora 				= calendario.get(Calendar.HOUR_OF_DAY);
		minutos 			= calendario.get(Calendar.MINUTE);
		String h 			= Integer.toString(hora);
		String m 			= Integer.toString(minutos);
		
		try {
			HttpSession sesion = request.getSession(false);
			conexion = (Connection) sesion.getAttribute("pool");	
			if (conexion.isClosed()) {
				request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
			}else {
				switch (param) {
					case "agendaCitas"://La que le aparece al DAME
						
						DatosCitaMedica agenda = new DatosCitaMedica(conexion);
						try {
							cita = agenda.getListado(tipoCita, fecha, medico);
							agendaCitas(request,response,cita,conexion);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							StringWriter errors = new StringWriter();
							e.printStackTrace(new PrintWriter(errors));
							log.info(errors.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							StringWriter errors = new StringWriter();
							e.printStackTrace(new PrintWriter(errors));
							log.info(errors.toString());
						}
					break;
					case "reporteTemp":
						DatosCitaMedica agendaTemp = new DatosCitaMedica(conexion);
						cita = agendaTemp.getListado(tipoCita, fecha, medico);
						agendaCitasTemp(request,response,cita,conexion);
					break;	
					case "anulacionCita":
						cedulaEmpleado = Integer.parseInt(request.getParameter("cedulaEmp"));
						solicitud = request.getParameter("nuSolicitud");
						DatosCitaMedica anula = new DatosCitaMedica(conexion);
						try {
							anula.anulaCita(Integer.parseInt(solicitud));
							historialCitas(request,response,cedulaEmpleado,conexion);
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							StringWriter errors = new StringWriter();
							e.printStackTrace(new PrintWriter(errors));
							log.info(errors.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							StringWriter errors = new StringWriter();
							e.printStackTrace(new PrintWriter(errors));
							log.info(errors.toString());
						}
					break;
				case "historialCitas"://La que le aparece al empleado
					cedulaEmpleado = Integer.parseInt(request.getParameter("cedulaEmp"));
					historialCitas(request,response,cedulaEmpleado,conexion);	
				break;
				case "listadoE":
					obtenerEspecialistas(request,response,tipo, turno,conexion);	
				break;
				case "listadoEm":
					if (hora < 12) {
						turno = "M";
					}else {
						turno = "T";
					}
					obtenerEspecialistas(request,response,tipo, turno,conexion);
				break;
				case "medicoReporte":
					obtenerEspecialistas(request,response,conexion);	
				break;
				case "horario":
					fechaCita = request.getParameter("fecha");
					cedulaMedico = Integer.parseInt(request.getParameter("especialista"));
					cedulaEmpleado = Integer.parseInt(request.getParameter("cedulaEmp"));
					
					
					int horaCaptura = 0;
					/******************************************/
					/************** FECHA ACTUAL **************/
					/******************************************/
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
					
					/*******************************************************************/
					/************** VERIFICAMOS SI EXISTE UNA CITA PREVIA **************/
					/*******************************************************************/
					try {
						switch (solicitante) {
							case "EMP":
								DatosCitaMedica datosCitaEmp = new DatosCitaMedica(cedulaEmpleado,tipo,fechaCita,conexion);
								cantidadCitas = datosCitaEmp.getCantidadCitasEmp();
								try (PrintWriter out = response.getWriter()) {
									DatosParametrosCitas ausentismo = new DatosParametrosCitas(conexion);
									int flag = ausentismo.horarioMedico(cedulaMedico, fechaCita, cedulaEmpleado);
									if (flag ==1) {
												out.println("<div id='respuesta' align='center'>");
									            out.println("<h4 style='color: #f00; font-weight: bold;'>No se encuentra disponible el especialista</h4>");
									            out.println("</div>");
									            out.println("</div>");
									}else {
										if(cantidadCitas > 0) {
											out.println("<div id='respuesta' align='center'>");
											out.println("<h4 style='color: #f00; font-weight: bold;'>Estimado usuario, ya usted posee un tipo de cita programada para la misma fecha.</h4>");
											out.println("</div>");
										}else {
											DatosMedico horarioMedico = new DatosMedico(fechaCita,cedulaMedico,conexion);
											ArrayList<String> horario = horarioMedico.getHorario();
											out.println("<div id=respuesta class='radio' style= 'margin-left: 10px'>");
											if (horario == null) {
												out.println("<label>No hay Citas Disponibles</label>");
											}else {
												for(int i = 0; i < horario.size(); i++) {
													int horaLista = Integer.parseInt(horario.get(i).substring(0,2));
													horaCaptura = Integer.parseInt(h);
													if(fechaActual.equals(fechaCita)) {
														
														if(horaLista > horaCaptura   ) {
															out.println("<label><input type='radio' name='optHora' value ='" + horario.get(i) + "'>" + horario.get(i) + "</label>");
														}	
													}else {
														out.println("<label><input type='radio' name='optHora' value ='" + horario.get(i) + "'>" + horario.get(i) + "</label>");
													}
												}
											}
											out.println("</div>");
										}
									}
								}
							break;
							case "FAM":
								cedulaFamiliar = Integer.parseInt(request.getParameter("cedulaFam"));
								DatosCitaMedica datosCitaFam = new DatosCitaMedica(cedulaEmpleado,cedulaFamiliar,tipo,fechaCita,conexion);
								cantidadCitas = datosCitaFam.getCantidadCitasFam();
								try (PrintWriter out = response.getWriter()) {
									
									DatosParametrosCitas ausentismo = new DatosParametrosCitas(conexion);
									int flag = ausentismo.horarioMedico(cedulaMedico, fechaCita, cedulaEmpleado);
									if (flag ==1) {
												out.println("<div id='respuesta' align='center'>");
									            out.println("<h4 style='color: #f00; font-weight: bold;'>No se encuentra disponible el especialista</h4>");
									            out.println("</div>");
									            out.println("</div>");
									}else {
										if(cantidadCitas > 0) {
											out.println("<div id='respuesta' align='center'>");
											out.println("<h4 style='color: #f00; font-weight: bold;'>Estimado usuario, ya usted posee un tipo de cita programada para la misma fecha.</h4>");
											out.println("</div>");
										}else {
											DatosMedico horarioMedico = new DatosMedico(fechaCita,cedulaMedico,conexion);
											ArrayList<String> horario = horarioMedico.getHorario();
											out.println("<div id=respuesta class='radio' style= 'margin-left: 10px'>");
											if (horario == null) {
												out.println("<label>No hay Citas Disponibles</label>");
											}else {
												for(int i = 0; i < horario.size(); i++) {
													int horaLista = Integer.parseInt(horario.get(i).substring(0,2));
													horaCaptura = Integer.parseInt(h);
													if(fechaActual.equals(fechaCita)) {
														
														if(horaLista > horaCaptura   ) {
															out.println("<label><input type='radio' name='optHora' value ='" + horario.get(i) + "'>" + horario.get(i) + "</label>");
														}	
													}else {
														out.println("<label><input type='radio' name='optHora' value ='" + horario.get(i) + "'>" + horario.get(i) + "</label>");
													}
												}
											}
											out.println("</div>");
										}
									}
								}
							break;
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						StringWriter errors = new StringWriter();
						e.printStackTrace(new PrintWriter(errors));
						log.info(errors.toString());
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			request.getRequestDispatcher("JSP/Error/error.jsp").forward(request, response);
		}
			
	}
		
			
	

	private void agendaCitasTemp(HttpServletRequest request, HttpServletResponse response, List<Cita> cita, Connection conexion) {
			try (PrintWriter out = response.getWriter()){
				try {
					for(int i = 0; i < cita.size(); i++) {
						out.println("<tr>");
							out.println("<td>" + cita.get(i).getCi_empleado()   + "</td>");
							out.println("<td>" + cita.get(i).getNb_empleado()   + "</td>");
							out.println("<td>" + cita.get(i).getCi_familiar()   + "</td>");
							out.println("<td>" + cita.get(i).getNb_familiar()   + "</td>");
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					log.info(errors.toString());
				}
			} catch (IOException e1) {
				try {
					response.sendRedirect("Error/error.jsp");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}	
	}

			/*****************************************************************/
			/************** VERIFICAMOS SI EXISTE UN AUSENTISMO **************/
			/*****************************************************************/
			/*
			DatosParametrosCitas datosParamCita = new DatosParametrosCitas();
			try {
				int bandera = datosParamCita.horarioMedico(cedulaMedico, fechaCita, cedulaEmpleado);
				if (bandera ==1) {
					try (PrintWriter out = response.getWriter()){
							out.println("<div class='col-sm-12'>");
				        	out.println("<div class='alert alert-danger alert-dismissable fade in' align='center'>");
				            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
				            out.println("<strong> No se encuentra disponible el especialista </strong>");
				            out.println("</div>");
				            out.println("</div>");
					}
				}else {
					try (PrintWriter out = response.getWriter()){
						out.println("<div class='col-sm-12'>");
			        	out.println("<div class='alert alert-success alert-dismissable fade in' align='center'>");
			            out.println("<a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>");
			            out.println("<strong> Se encuentra disponible el especialista </strong>");
			            out.println("</div>");
			            out.println("</div>");
				}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//obtenerDispHorario(request,response,tipo,turno,fechaCita,cedulaMedico, cedulaEmpleado);
			break;
		default:
			break;
		}
		*/
	
	private void agendaCitas(HttpServletRequest request, HttpServletResponse response, List<Cita> cita, Connection conexion) throws IOException {
		if (conexion!=null) {
			try (PrintWriter out = response.getWriter()){
				try {
					for(int i = 0; i < cita.size(); i++) {
						out.println("<tr>");
							out.println("<td><input type='radio' name='optsolicitud' value='" + cita.get(i).getNu_solicitud() +"'>&nbsp;&nbsp;" + cita.get(i).getNu_solicitud() + "</td>");
							out.println("<td>" + cita.get(i).getSt_cita()     	+ "</td>");
							out.println("<td>" + cita.get(i).getCi_empleado()   + "</td>");
							out.println("<td>" + cita.get(i).getNb_empleado()   + "</td>");
							out.println("<td>" + cita.get(i).getCi_familiar()   + "</td>");
							out.println("<td>" + cita.get(i).getNb_familiar()   + "</td>");
							out.println("<td>" + cita.get(i).getCelular()     	+ "</td>");
							if(cita.get(i).getExtension() == null){
								out.println("<td>0</td>");
							}else {
								out.println("<td>" + cita.get(i).getExtension() + "</td>");
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
							
							//No va por solicitud del usuario
							//out.println("<td>" + cita.get(i).getFe_atencion() 		+ "</td>");
							//out.println("<td>" + cita.get(i).getHh_atencion() 		+ "</td>");
							//out.println("<td>" + cita.get(i).getFe_solicitud() 		+ "</td>");
							//out.println("<td>" + cita.get(i).getHh_solicitud() 		+ "</td>");
							//out.println("<td>" + cita.get(i).getNb_ti_solicitud() + "</td>");
						out.println("</tr>");
						out.println("<style>@media screen and (max-width: 767px){#historialCitas > td{font-size: 6px;}}</style>");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					log.info(errors.toString());
				}
			}	
		}else {
			response.sendRedirect("Error/error.jsp");
		}
		
	}
	
	private void historialCitas(HttpServletRequest request, HttpServletResponse response, int cedulaEmpleado, Connection conexion) throws IOException {
		DatosCitaMedica DatosCita = new DatosCitaMedica(cedulaEmpleado,conexion);
		if (conexion!=null) {
			try (PrintWriter out = response.getWriter()){
				try {
					//ACOMODAR DISEÑO DE LA TABLA Y ACTIVAR ANULAR CITA
					
					cita = DatosCita.getHistoricoCitas();
					out.println("<br>");
					out.println("<div align='center' class='col-md-10' id='historialCitas'");
					out.println("<br>");
					out.println("<table class='table table-bordered table-striped'>");
					out.println("<thead>");
					out.println("<tr>");
					out.println("<th>Solicitud</th>");
					out.println("<th>Nombre Empleado</th>");
					out.println("<th>Nombre Familiar</th>");
					out.println("<th>Fecha Cita</th>");
					out.println("<th>Hora Cita</th>");
					out.println("<th>Medico</th>");
					out.println("<th>Tipo de Solicitud</th>");
					out.println("</tr>");
					out.println("</thead>");
					out.println("<tbody>");
					for(int i = 0; i < cita.size(); i++) {
						out.println("<tr>");
						out.println("<td><input type='radio' name='optsolicitud' value='" + cita.get(i).getNu_solicitud() +"'>&nbsp;&nbsp;" + cita.get(i).getNu_solicitud() + "</td>");
						out.println("<td>" + cita.get(i).getNb_empleado()     + "</td>");
						out.println("<td>" + cita.get(i).getNb_familiar()     + "</td>");
						out.println("<td>" + cita.get(i).getFe_cita()         + "</td>");
						out.println("<td>" + cita.get(i).getHh_cita()         + "</td>");
						out.println("<td>" + cita.get(i).getNb_especialista() + "</td>");
						out.println("<td>" + cita.get(i).getNb_ti_solicitud() + "</td>");
						out.println("</tr>");
					}
					out.println("</tbody>");
					out.println("</table><!-- Fin table -->");
					out.println("<script>$('#anular').hide();</script>");
					out.println("</div><!-- Fin col-sm-12 -->");
					out.println("<input type='button' class='btn btn-primary' value='Anular' id='anular'>");
					out.println("<script>anula();</script>");
					out.println("<link rel='stylesheet' href='css/cita_medica/historial_citas.css'>");
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					log.info(errors.toString());
				}
			}
		}else {
			response.sendRedirect("Error/error.jsp");
		}
		
	}
	
	private void obtenerEspecialistas(HttpServletRequest request, HttpServletResponse response, Connection conexion) {
		/**
		 * Se creó para el reporte dado que necesito la cedula y el de abajo no lo 
		 * puedo modificar ya que va a afectar varias partes.
		 * */
		DatosMedico especialistas = new DatosMedico(conexion);
		try {
			List<Medico> med = especialistas.getTodosMedicos() ;
			try (PrintWriter out = response.getWriter()){
				//out.println("<div class='col-sm-12'>");
				//out.println("<ul class='list-group'>");
				//out.println("<li class='list-group-item'>");
				out.println("<br>");
				out.println("<div class='radio' style='text-align:left;'>");
					for(int i =0; i < med.size(); i++) {
						out.println("<input name='optMedico' id='optMedico' type='radio' value='"+ med.get(i).getCedula() +"'>" + med.get(i).getNb_medico() );
						out.println("<br>");
					}
				out.println("</div>");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void obtenerEspecialistas(HttpServletRequest request, HttpServletResponse response, String tipo, String turno, Connection conexion) throws IOException {
		try (PrintWriter out = response.getWriter()){
			try {
				if((tipo == "" && turno == "") || (tipo == null && turno == null)) {
					DatosMedico especialistas = new DatosMedico(conexion);
					List<String> esp = especialistas.getNombreCompleto();
					out.println("<select name='listadoMedico' class='form-control' id='listadoMedico'>");
					out.println("<option></option>");
					for(int i = 0; i < esp.size(); i++) {
						out.println("<option>"+ esp.get(i).toString() + "</option>");
					}
					out.println("</select>");
					/**************************************************************************
					*  Esto es para busqueda progresiva en la tabla (HistorialCitasAdmin.jsp) 
					***************************************************************************/
					out.println("<script>");
					out.println("$('#listadoMedico').change(function() {");
					out.println("var value = $(this).val().toLowerCase();");
					out.println("$('#listadoCitas tr').filter(function() {");
					out.println("$(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)");
					out.println("});");
					out.println("});");
					out.println("</script>");
				}else {
					DatosMedico especialistas = new DatosMedico(tipo, turno, conexion);
					esp = especialistas.getEspecialista();
					out.println("<select name='listadoMedico' class='form-control' id='listadoMedico'>");
					out.println("<option></option>");
					for(int i = 0; i < esp.size(); i++) {
						out.println("<option value='"+ esp.get(i).getCedula() +"'>"+ esp.get(i).getNb_medico() + "</option>");
					}
					out.println("</select>");
					out.print("<script>listado();</script>");	
				}
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));
				log.info(errors.toString());
			}
		}
	}
	
	

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(ServletEspecialista.class);
	
	private String param;
	private String tipo;
	private String solicitante;
	private String solicitud;
	private int cantidadCitas;
	private int cedulaEmpleado;
	private int cedulaFamiliar;
	private int cedulaMedico;
	private String fechaCita;
	private String turno;
	private String tipoCita;
	private String medico;
	private String fecha;
	
	private List<Medico> esp;
	private List<Cita> cita;
	private ArrayList<String> horaMedico;
	private ParametroCita paramCita;
	private Connection conexion;
	
}
