package modelo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import bean.Empleado;

public class DatosEmpleado{
	
	/*********************************************/
	/***********Constructor de prueba*************/
	/**
	 * @param conexion *******************************************/
	
	public DatosEmpleado(int cedula, Connection conexion) throws SQLException {
		super();
		this.cedula = cedula;
		this.con = conexion;
	}
	
	
	/***********************************/
	/***********Constructor*************/
	/***********************************/
	/*
	public DatosEmpleado(DataSource pool, int cedula) throws SQLException {
		super();
		this.pool = pool;
		this.cedula = cedula;
	}
		*/
	/*****************************************/
	/***********Array de Empleado*************/
	/*****************************************/
	
	public List<Empleado> getEmpleado() throws Exception{
		
		// Establecemos una coneccion
		//con = pool.getConnection();
		
		// Creamos un Array con la informacion del empleado
		List<Empleado> listadoEmpleado = new ArrayList<>();
		
		// Creamos la consulta para la busqueda del empleado
		
		/*String consultaEmp =  "select "
								+ "emp.cedula, "
								+ "initcap(emp.nombre1) as nombre1, "
								+ "initcap(emp.nombre2) as nombre2, "
								+ "initcap(emp.apellido1) as apellido1, "
								+ "initcap(emp.apellido2) as apellido2, "
								+ "initcap( "
								+ "case "
									+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
									+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
									+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
								+ "end) as nb_empleado, "
								+ "trim(replace(replace(dir.nu_celular_1,'-',''),' ',''))nu_celular, "
								+ "emp.tx_email_propio, "
								+ "emp.tx_email_bcv, "
								+ "dir.co_user_id, "
								+ "emp.sexo, "
								+ "decode(emp.sexo, 'M', 'Masculino', 'F', 'Femenino')as nb_sexo, "
								+ "decode(emp.sexo, 'M', 'Sr.', 'F', 'Sra.')as nb_identificacion "
							+ "from personal.todos_empleados emp join personal.empleado_direccion dir on dir.nu_cedula = emp.cedula "
							+ "where dir.nu_cedula = ? "; // and tipo_emp = 'OBR' --> esto es para operativo de tallas //esta condicion hay que eliminarla luego del operativo encuesta
		 */
		String consultaEmp = "select * from( "
								+ "select "
								    + "emp.cedula, "
								    + "initcap(emp.nombre1) as nombre1, "
								    + "initcap(emp.nombre2) as nombre2, "
								    + "initcap(emp.apellido1) as apellido1, "
								    + "initcap(emp.apellido2) as apellido2, "
								    + "initcap( "
								    + "case "
								        + "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
								        + "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
								        + "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
								    + "end) as nb_empleado, "
								    + "trim(replace(replace(dir.nu_celular_1,'-',''),' ',''))nu_celular, "
								    + "emp.tx_email_propio, "
								    + "emp.tx_email_bcv, "
								    + "dir.co_user_id, "
								    + "emp.sexo, "
								    + "decode(emp.sexo, 'M', 'Masculino', 'F', 'Femenino')as nb_sexo, "
								    + "decode(emp.sexo, 'M', 'Sr.', 'F', 'Sra.')as nb_identificacion "
								+ "from personal.todos_empleados emp left join personal.empleado_direccion dir on dir.nu_cedula = emp.cedula "
								+ "union "
								+ "select  "
								    + "ci_sobreviviente as cedula, "
								    + "initcap(nb1_sobreviviente) as nombre1, "
								    + "initcap(nb2_sobreviviente) as nombre2, "
								    + "initcap(tx_apellido1_sv) as apellido1, "
								    + "initcap(tx_apellido2_sv) as apellido2, "
								    + "initcap( "
								    + "case "
								        + "when upper(nb2_sobreviviente) is null then upper(tx_apellido1_sv)||' '||upper(tx_apellido2_sv)||', '||upper(nb1_sobreviviente) "
								        + "when upper(tx_apellido2_sv) is null then upper(tx_apellido1_sv)|| ', '||upper(nb1_sobreviviente)||' ' || upper(nb2_sobreviviente) "
								        + "else upper(tx_apellido1_sv)|| ' ' ||upper(tx_apellido2_sv)|| ', '||upper(nb1_sobreviviente)||' '||upper(nb2_sobreviviente) "
								    + "end) as nb_empleado, "
								    + "trim(replace(replace(nu_telefono,'-',''),' ',''))nu_celular, "
								    + "'' tx_email_propio, "
								    + "'' tx_email_bcv, "
								    + "'' co_user_id, "
								    + "in_sexo, "
								    + "decode(in_sexo, 'M', 'Masculino', 'F', 'Femenino')as nb_sexo, "
								    + "decode(in_sexo, 'M', 'Sr.', 'F', 'Sra.')as nb_identificacion "
								+ "from rhsv.sobreviviente "
								+ "where ci_sobreviviente not in (select cedula from todos_empleados)) "
								+ "where cedula = ? ";
		// Pasamos el sql a la consulta con parametros
		buscaEmpleado = con.prepareStatement(consultaEmp);
		
		// Pasamos los parametros que vamos a necesitar en la consulta
		//buscaEmpleado.setString(1, usuario);
		buscaEmpleado.setInt(1, cedula);
		
		// Ejecutamos la consulta
		cursor = buscaEmpleado.executeQuery();
		try {

			// Recorremos la tabla para obtener los datos del empleado
			while(cursor.next()) {
				// Guardamos los datos
				int cedula 					= cursor.getInt   ("cedula");
				String nombre1 				= cursor.getString("nombre1");
				String nombre2 				= cursor.getString("nombre2");
				String apellido1 			= cursor.getString("apellido1");
				String apellido2 			= cursor.getString("apellido2");
				String nb_empleado 			= cursor.getString("nb_empleado");
				String nu_celular 			= cursor.getString("nu_celular");
				String tx_email_propio 		= cursor.getString("tx_email_propio");
				String tx_email_bcv 		= cursor.getString("tx_email_bcv");
				String co_user_id 			= cursor.getString("co_user_id");
				String sexo 				= cursor.getString("sexo");
				String nb_sexo 				= cursor.getString("nb_sexo");
				String nb_identificacion 	= cursor.getString("nb_identificacion");
				
				// Enviamos los datos al bean Empleado por medio de sus metodos setters
				Empleado emp = new Empleado();
				emp.setCedula(cedula);
				emp.setNombre1(nombre1);
				emp.setNombre2(nombre2);
				emp.setApellido1(apellido1);
				emp.setApellido2(apellido2);
				emp.setNb_empleado(nb_empleado);
				emp.setNu_celular(nu_celular);
				emp.setTx_email_propio(tx_email_propio);
				emp.setTx_email_bcv(tx_email_bcv);
				emp.setCo_user_id(co_user_id);
				emp.setSexo(sexo);
				emp.setNb_sexo(nb_sexo);
				emp.setNb_identificacion(nb_identificacion);
				
				
				// Almacenamos todo el objeto empleado en el Array
				listadoEmpleado.add(emp);
			}
		}catch (Exception e){
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();
			buscaEmpleado.close();
		}
		return listadoEmpleado;     // Retornamos el array
	}
	
	public Empleado getDatoEmpleado() throws SQLException {
		// Creamos la consulta para la busqueda del empleado
		String consultaEmp = "select "
								+ "emp.cedula, "
								+ "initcap(emp.nombre1) as nombre1, "
								+ "initcap(emp.nombre2) as nombre2, "
								+ "initcap(emp.apellido1) as apellido1, "
								+ "initcap(emp.apellido2) as apellido2, "
								+ "initcap( "
								+ "case "
									+ "when upper(emp.nombre2) is null then upper(emp.apellido1)||' '||upper(emp.apellido2)||', '||upper(emp.nombre1) "
									+ "when upper(emp.apellido2) is null then upper(emp.apellido1)|| ', '||upper(emp.nombre1)||' ' || upper(emp.nombre2) "
									+ "else upper(emp.apellido1)|| ' ' ||upper(emp.apellido2)|| ', '||upper(emp.nombre1)||' '||upper(emp.nombre2) "
								+ "end) as nb_empleado, "
								+ "trim(replace(replace(dir.nu_celular_1,'-',''),' ',''))nu_celular, "
								+ "emp.tx_email_propio, "
								+ "emp.tx_email_bcv, "
								+ "dir.co_user_id, "
								+ "emp.sexo, "
								+ "decode(emp.sexo, 'M', 'Masculino', 'F', 'Femenino')as nb_sexo, "
								+ "decode(emp.sexo, 'M', 'Sr.', 'F', 'Sra.')as nb_identificacion "
							+ "from personal.todos_empleados emp join personal.empleado_direccion dir on dir.nu_cedula = emp.cedula "
							+ "where dir.nu_cedula = ? ";
				
				// Pasamos el sql a la consulta con parametros
				buscaEmpleado = con.prepareStatement(consultaEmp);
				
				// Pasamos los parametros que vamos a necesitar en la consulta
				//buscaEmpleado.setString(1, usuario);
				buscaEmpleado.setInt(1, cedula);
				
				// Ejecutamos la consulta
				cursor = buscaEmpleado.executeQuery();
				
				Empleado emp = new Empleado();
				try {

					// Recorremos la tabla para obtener los datos del empleado
					while(cursor.next()) {
						// Guardamos los datos
						int cedula 					= cursor.getInt("cedula");
						String nombre1 				= cursor.getString("nombre1");
						String nombre2 				= cursor.getString("nombre2");
						String apellido1 			= cursor.getString("apellido1");
						String apellido2 			= cursor.getString("apellido2");
						String nb_empleado 			= cursor.getString("nb_empleado");
						String nu_celular 			= cursor.getString("nu_celular");
						String tx_email_propio 		= cursor.getString("tx_email_propio");
						String tx_email_bcv 		= cursor.getString("tx_email_bcv");
						String co_user_id 			= cursor.getString("co_user_id");
						String sexo 				= cursor.getString("sexo");
						String nb_sexo 				= cursor.getString("nb_sexo");
						String nb_identificacion 	= cursor.getString("nb_identificacion");
						
						emp.setCedula(cedula);
						emp.setNombre1(nombre1);
						emp.setNombre2(nombre2);
						emp.setApellido1(apellido1);
						emp.setApellido2(apellido2);
						emp.setNb_empleado(nb_empleado);
						emp.setNu_celular(nu_celular);
						emp.setTx_email_propio(tx_email_propio);
						emp.setTx_email_bcv(tx_email_bcv);
						emp.setCo_user_id(co_user_id);
						emp.setSexo(sexo);
						emp.setNb_sexo(nb_sexo);
						emp.setNb_identificacion(nb_identificacion);
	
					}
				}catch (Exception e){
					StringWriter errors = new StringWriter();
					e.printStackTrace(new PrintWriter(errors));
					log.info(errors.toString());
				}finally {
					cursor.close();			// Cerramos el cursor
					buscaEmpleado.close();      // Cerramos la consulta	
				}
				
				return emp;
				
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(DatosEmpleado.class);
	
	private int cedula;
	private Connection con   					= null;
	private PreparedStatement buscaEmpleado     = null;
	private ResultSet  cursor                   = null;
	private HttpServletRequest request;
}
