package modelo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bean.Familiar;

public class DatosFamiliar {
	
	/***********************************/
	/***********Constructor*************/
	/***********************************/

	public DatosFamiliar(int cedulaEmpleado, int cedulaFamiliar, Connection conexion) {
		super();
		this.cedulaEmpleado = cedulaEmpleado;
		this.cedulaFamiliar = cedulaFamiliar;
		this.con = conexion;
	}
	
	public DatosFamiliar(int cedulaEmpleado, Connection conexion) {
		super();
		this.cedulaEmpleado = cedulaEmpleado;
		this.con = conexion;
	}

	/*****************************************************/
	/***********Array Familiares del Empleado*************/
	/*****************************************************/
	public List<Familiar> getDatoFamiliar() throws Exception{
		// Establecemos una coneccion
		//con = getConexion();
		
		// Creamos un Array con la informacion del empleado
		List<Familiar> listadoFamiliar = new ArrayList<>();
		
		// Creamos la consulta para la busqueda del empleado
		
		
		
		String consultaFam = "select "
								+"cedula, " 
								+"cedula_familiar, " 
								+"initcap(nombre1) as nombre1, " 
								+"initcap(nombre2) as nombre2, "
								+"initcap(apellido1) as apellido1, " 
								+"initcap(apellido2) as apellido2, "
								+"initcap( "
								+"case "
									+"when upper(nombre2) is null then upper(apellido1)||' '||upper(apellido2)||', '||upper(nombre1) " 
									+"when upper(apellido2) is null then upper(apellido1)|| ', '||upper(nombre1)||' ' || upper(nombre2) " 
									+"else upper(apellido1)|| ' ' ||upper(apellido2)|| ', '||upper(nombre1)||' '||upper(nombre2) "
								+"end) nb_familiar, "
								+"sexo, "
								+"parentesco, " 
								+"initcap(decode(parentesco,'C', 'CONYUGUE', " 
											  			  +"'D', decode (sexo, 'F', 'HIJA', 'M', 'HIJO'), " 
											  			  +"'A', decode (sexo, 'F', 'MADRE', 'M', 'PADRE'), " 
											  			  +"'H', decode (sexo, 'F', 'HERMANA','M','HERMANO'))) nb_parentesco " 
							+"from todos_familiares "
							+"where cedula_familiar = ?";
		
		// Pasamos el sql a la consulta con parametros
		buscaFamiliar = con.prepareStatement(consultaFam);
		
		// Pasamos los parametros que vamos a necesitar en la consulta
		buscaFamiliar.setInt(1, cedulaFamiliar);
		
		// Ejecutamos la consulta
		cursor = buscaFamiliar.executeQuery();
		try {
			// Recorremos la tabla para obtener los datos del empleado
			while(cursor.next()) {
				
				// Enviamos los datos al bean Familiar por medio de sus metodos setters
				Familiar fam = new Familiar();
				fam.setCedula			(cursor.getInt	 ("cedula"));
				fam.setCedula_familiar	(cursor.getInt	 ("cedula_familiar"));
				fam.setNombre1			(cursor.getString("nombre1"));
				fam.setNombre2			(cursor.getString("nombre2"));
				fam.setApellido1		(cursor.getString("apellido1"));
				fam.setApellido2		(cursor.getString("apellido2"));
				fam.setNb_familiar		(cursor.getString("nb_familiar"));
				fam.setSexo				(cursor.getString("sexo"));
				fam.setParentesco		(cursor.getString("parentesco"));
				fam.setNb_parentesco	(cursor.getString("nb_parentesco"));
				
				// Almacenamos todo el objeto familiar en el Array
				listadoFamiliar.add(fam);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();				// Cerramos el cursor
			buscaFamiliar.close();		// Cerramos la consulta
			//con.close();				// Cerramos la conexion
		}
		
		return listadoFamiliar; 	// Retornamos el array
	}
	
	public List<Familiar> getFamiliar() throws Exception{
		// Establecemos una coneccion
		//con = getConexion();
		
		// Creamos un Array con la informacion del empleado
		List<Familiar> listadoFamiliar = new ArrayList<>();
		
		// Creamos la consulta para la busqueda del empleado
		
		
		
		String consultaFam = "select "
				+ "cedula, "
				+ "cedula_familiar, "
				+ "initcap(nombre1) as nombre1, "
				+ "initcap(nombre2) as nombre2, "
				+ "initcap(apellido1) as apellido1, "
				+ "initcap(apellido2) as apellido2, "
				+ "initcap( "
				+ "case "
			    	+ "when upper(nombre2) is null then upper(apellido1)||' '||upper(apellido2)||', '||upper(nombre1) "
			    	+ "when upper(apellido2) is null then upper(apellido1)|| ', '||upper(nombre1)||' ' || upper(nombre2) "
			    	+ "else upper(apellido1)|| ' ' ||upper(apellido2)|| ', '||upper(nombre1)||' '||upper(nombre2) "
			    + "end) nb_familiar, "
			    + "sexo, "
			    + "parentesco, "
			    + "initcap(decode(parentesco,'C', 'CONYUGUE', "
			                  			   + "'D', decode (sexo, 'F', 'HIJA', 'M', 'HIJO'), "
			                  			   + "'A', decode (sexo, 'F', 'MADRE', 'M', 'PADRE'), "
			                  			   + "'H', decode (sexo, 'F', 'HERMANA','M','HERMANO'))) nb_parentesco "
			+ "from personal.todos_familiares where cedula = ? ";
		
		// Pasamos el sql a la consulta con parametros
		buscaFamiliar = con.prepareStatement(consultaFam);
		
		// Pasamos los parametros que vamos a necesitar en la consulta
		buscaFamiliar.setInt(1, cedulaEmpleado);
		
		// Ejecutamos la consulta
		cursor = buscaFamiliar.executeQuery();
		try {
			// Recorremos la tabla para obtener los datos del empleado
			while(cursor.next()) {
				// Guardamos los datos
				int cedula 			 = cursor.getInt("cedula");
				int cedula_familiar  = cursor.getInt("cedula_familiar");
				String nombre1 		 = cursor.getString("nombre1");
				String nombre2 		 = cursor.getString("nombre2");
				String apellido1 	 = cursor.getString("apellido1");
				String apellido2 	 = cursor.getString("apellido2");
				String nb_familiar	 = cursor.getString("nb_familiar");
				String sexo 		 = cursor.getString("sexo");
				String parentesco 	 = cursor.getString("parentesco");
				String nb_parentesco = cursor.getString("nb_parentesco");
				
				// Enviamos los datos al bean Familiar por medio de sus metodos setters
				Familiar fam = new Familiar();
				fam.setCedula(cedula);
				fam.setCedula_familiar(cedula_familiar);
				fam.setNombre1(nombre1);
				fam.setNombre2(nombre2);
				fam.setApellido1(apellido1);
				fam.setApellido2(apellido2);
				fam.setNb_familiar(nb_familiar);
				fam.setSexo(sexo);
				fam.setParentesco(parentesco);
				fam.setNb_parentesco(nb_parentesco);
				
				// Almacenamos todo el objeto familiar en el Array
				listadoFamiliar.add(fam);
			}
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
		}finally {
			cursor.close();				// Cerramos el cursor
			buscaFamiliar.close();		// Cerramos la consulta
			//con.close();				// Cerramos la conexion
		}
		
		return listadoFamiliar; 	// Retornamos el array
	}

	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private static Logger log = Logger.getLogger(DatosFamiliar.class);
	
	private int cedulaEmpleado;
	private int cedulaFamiliar;
	
	private String usuario 					= null;
	private Connection con   				= null;
	private PreparedStatement buscaFamiliar = null;
	private ResultSet  cursor    			= null;
}
