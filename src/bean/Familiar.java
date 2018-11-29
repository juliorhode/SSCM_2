package bean;

public class Familiar {
	
	/************************************************/
	/***********Metodos Getters y Stters*************/
	/************************************************/
	
	public int getCedula() {
		return cedula;
	}
	public void setCedula(int cedula) {
		this.cedula = cedula;
	}
	public int getCedula_familiar() {
		return cedula_familiar;
	}
	public void setCedula_familiar(int cedula_familiar) {
		this.cedula_familiar = cedula_familiar;
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
	public String getNb_familiar() {
		return nb_familiar;
	}
	public void setNb_familiar(String nb_familiar) {
		this.nb_familiar = nb_familiar;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getParentesco() {
		return parentesco;
	}
	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
	public String getNb_parentesco() {
		return nb_parentesco;
	}
	public void setNb_parentesco(String nb_parentesco) {
		this.nb_parentesco = nb_parentesco;
	}

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private int cedula;
	private int cedula_familiar;
	private String nombre1;
	private String nombre2;
	private String apellido1;
	private String apellido2;
	private String nb_familiar;
	private String sexo;
	private String parentesco;
	private String nb_parentesco;
	
}
