package bean;

public class Empleado {
	
	/************************************************/
	/***********Metodos Getters y Stters*************/
	/************************************************/
	
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getNb_empleado() {
		return nb_empleado;
	}
	public void setNb_empleado(String nb_empleado) {
		this.nb_empleado = nb_empleado;
	}
	public String getNu_celular() {
		return nu_celular;
	}
	public void setNu_celular(String nu_celular) {
		this.nu_celular = nu_celular;
	}
	public String getTx_email_propio() {
		return tx_email_propio;
	}
	public void setTx_email_propio(String tx_email_propio) {
		this.tx_email_propio = tx_email_propio;
	}
	public String getTx_email_bcv() {
		return tx_email_bcv;
	}
	public void setTx_email_bcv(String tx_email_bcv) {
		this.tx_email_bcv = tx_email_bcv;
	}
	public String getCo_user_id() {
		return co_user_id;
	}
	public void setCo_user_id(String co_user_id) {
		this.co_user_id = co_user_id;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getNb_sexo() {
		return nb_sexo;
	}
	public void setNb_sexo(String nb_sexo) {
		this.nb_sexo = nb_sexo;
	}
	public String getNb_identificacion() {
		return nb_identificacion;
	}
	public void setNb_identificacion(String nb_identificacion) {
		this.nb_identificacion = nb_identificacion;
	}

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private int cedula;
	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private String nb_empleado;
	private String nu_celular;
	private String tx_email_propio;
	private String tx_email_bcv;
	private String co_user_id;
	private String sexo;
	private String nb_sexo;
	private String nb_identificacion;
	
}
