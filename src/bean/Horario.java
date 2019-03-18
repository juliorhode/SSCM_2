package bean;

public class Horario {
	
	/************************************************/
	/***********Metodos Getters y Stters*************/
	/************************************************/
	public int getNu_citas() {
		return nu_citas;
	}
	public void setNu_citas(int nu_citas) {
		this.nu_citas = nu_citas;
	}
	public int getNu_intervalo() {
		return nu_intervalo;
	}
	public void setNu_intervalo(int nu_intervalo) {
		this.nu_intervalo = nu_intervalo;
	}
	public int getHh_inicio() {
		return hh_inicio;
	}
	public void setHh_inicio(int hh_inicio) {
		this.hh_inicio = hh_inicio;
	}
	public String getIn_turno() {
		return in_turno;
	}
	public void setIn_turno(String in_turno) {
		this.in_turno = in_turno;
	}
	public String getIn_especialidad() {
		return in_especialidad;
	}
	public void setIn_especialidad(String in_especialidad) {
		this.in_especialidad = in_especialidad;
	}
	
	/******************************************/
	/***********Variables de clase*************/
	/******************************************/
	private int 		nu_citas;
	private int 		nu_intervalo;
	private int 		hh_inicio;
	private String 		in_turno;
	private String 		in_especialidad;

}
