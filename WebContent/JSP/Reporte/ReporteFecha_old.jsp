<%@page contentType="application/pdf"%>
<%@page trimDirectiveWhitespaces="true"%>

<%@page import="java.sql.*" %>
<%@page import="java.io.*" %>
<%@page import="java.lang.*" %>
<%@page import="java.util.*" %>

<%@page import="net.sf.jasperreports.engine.*"%>
<%@page import="net.sf.jasperreports.view.JasperViewer"%>

<%@page import="javax.servlet.ServletResponse"%>


<%
	Connection con = null;
	String username = "juferrer";
	String password = "j13802982f";
	String hostname = "@cl-oradtbcv01:";
	String port = "1521";
	String database = "/orabdd02";
	String jdbc = "jdbc:oracle:thin:";
	String classname = "oracle.jdbc.driver.OracleDriver";
	String url = jdbc + hostname + port +  database;
	
	String a = System.getenv("DOMAIN_HOME") + File.separator + "resources" + File.separator + "JDBC_Admin.properties";
	/*
	try{
		
		Class.forName(classname);
		con = DriverManager.getConnection(url,username,password);
		String archivo = System.getenv("DOMAIN_HOME") + File.separator + "resources" + File.separator + "JDBC_Admin.properties";
		//String archivo = System.getenv("$DOMAIN_HOME") + File.separator + "resources" + File.separator + "Reporte_Agenda.jasper";
      	//Ruta del reporte
        File reporteFile = new File("/appldesa/SSCM/domains/SSCMdomain/resources/Reporte_Agenda.jasper");
    	//Crearemos un objeto HashMap
    	//Map<String, Object> parametros = new HashMap<String,Object>();
    	//parametros.put("fecha",request.getParameter("fecha"));
    	//parametros.put("fecha","05/12/2018");
    	//crearemos un arreglo byte
    	//byte[] bytes = JasperRunManager.runReportToPdf(reporteFile.getPath(), parametros, con);
    	//indicamos la salida que es en formato pdf
    	response.setContentType("application/pdf");
    	//creamos un objeto de salida
    	ServletOutputStream salida = response.getOutputStream();
    	//escribimos la salida
    	//salida.write(bytes);
    	//limpiamos el flujo de salida
    	salida.flush();
    	salida.close();
	}catch (Exception e){
		e.printStackTrace();
	}finally{
		if(con!=null){
			con.close();
		}
	}*/

%>

<html>
<body>
<h1><%=a %></h1>
</body>
</html>





try {
            String jrxmlFileName = "/formatosjasper/formatoreporte.jasper";
            File archivoReporte = new File(request.getRealPath(jrxmlFileName));
            HashMap hm = null;
            hm = new HashMap();
 
            ServletOutputStream servletOutputStream = response.getOutputStream();
 
            byte[] bytes = null;
 
            try {
                bytes = JasperRunManager.runReportToPdf(archivoReporte.getPath(), hm, new JREmptyDataSource());
 
                response.setContentType("application/pdf");
                response.setContentLength(bytes.length);
                servletOutputStream.write(bytes, 0, bytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (JRException e) {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                e.printStackTrace(printWriter);
                response.setContentType("text/plain");
                response.getOutputStream().print(stringWriter.toString());
            }
        } catch (Exception e) {
            salida = "Error generando Reporte Jasper, el error del Sistema es " + e;
            System.out.println(salida);
        }