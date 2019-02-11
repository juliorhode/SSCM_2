package modelo;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import bean.Ausentismo;

public class DatosAusentismo {
	
	public DatosAusentismo() {
		super();
		
	}
	
	public DatosAusentismo(Connection conexion) {
		this.conexion = conexion;
	}

	public List<bean.Ausentismo> getAusentismo(){
		
		
		
		return null;
		
	}
	
	public boolean insertaAusentismo(Ausentismo ausentismo) throws SQLException {
		try {
			
			//Creamos la instruccion sql para insertar (consulta preparada)
			
			String parametrosSQL = "INSERT INTO SALUD.CM_AUSENTISMO_ESPEC "
									 + "(NU_REGISTRO, "
									 + "FE_INICIO, "
									 + "HH_INICIO, "
									 + "FE_FIN, "
									 + "HH_FIN, "
									 + "NU_DIAS, "
									 + "NU_HORAS, "
									 + "NU_MINUTOS, "
									 + "IN_TIPO_ESPECIAL, "
									 + "NB_TIPO_ESPECIAL, "
									 + "CI_ESPECIALISTA, "
									 + "NB_ESPECIALISTA, "
									 + "TX_MOTIVO, "
									 + "NB_USUARIO) "
								 + "VALUES (SALUD.SEQ_NU_REGISTRO.NEXTVAL, to_date(?,'DD/MM/YYYY'), ?, to_date(?,'DD/MM/YYYY'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			paramAusentismo = conexion.prepareStatement(parametrosSQL);
			
			//Establecemos los parametros
			paramAusentismo.setString(1, ausentismo.getFe_inicio());
			paramAusentismo.setString(2, ausentismo.getHh_inicio());
			paramAusentismo.setString(3, ausentismo.getFe_fin());
			paramAusentismo.setString(4, ausentismo.getHh_fin());
			paramAusentismo.setInt(5, ausentismo.getNu_dias());
			paramAusentismo.setInt(6, ausentismo.getNu_horas());
			paramAusentismo.setInt(7, ausentismo.getNu_minutos());
			paramAusentismo.setString(8, ausentismo.getIn_tipo_especial());
			paramAusentismo.setString(9, ausentismo.getNb_tipo_especial());
			paramAusentismo.setInt(10, ausentismo.getCi_especialista());
			paramAusentismo.setString(11, ausentismo.getNb_especialista());
			paramAusentismo.setString(12, ausentismo.getTx_motivo());
			paramAusentismo.setString(13, ausentismo.getNb_usuario());
			
			//Ejecutamos la instruccion sql
			paramAusentismo.execute();
		}catch(Exception e) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			log.info(errors.toString());
			return false;
			
		}finally {
			paramAusentismo.close();
			conexion.close();
		}
		
		return true;
		
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/	
	private static Logger log = Logger.getLogger(DatosAusentismo.class);
	
	private PreparedStatement paramAusentismo 	= null;
	private Connection conexion		       		= null;
	private Statement  buscaParametros 	   		= null;
	private ResultSet  cursor    	       		= null;
	
}
