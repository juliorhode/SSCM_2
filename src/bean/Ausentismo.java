package bean;

public class Ausentismo {

	public Ausentismo(String fe_inicio, String hh_inicio, String fe_fin, String hh_fin, int nu_dias, int nu_horas,
			int nu_minutos, String in_tipo_especial, String nb_tipo_especial, int ci_especialista,
			String nb_especialista, String tx_motivo, String nb_usuario) {
		super();
		this.fe_inicio = fe_inicio;
		this.hh_inicio = hh_inicio;
		this.fe_fin = fe_fin;
		this.hh_fin = hh_fin;
		this.nu_dias = nu_dias;
		this.nu_horas = nu_horas;
		this.nu_minutos = nu_minutos;
		this.in_tipo_especial = in_tipo_especial;
		this.nb_tipo_especial = nb_tipo_especial;
		this.ci_especialista = ci_especialista;
		this.nb_especialista = nb_especialista;
		this.tx_motivo = tx_motivo;
		this.nb_usuario = nb_usuario;
	}
	
	public String getFe_inicio() {
		return fe_inicio;
	}
	public void setFe_inicio(String fe_inicio) {
		this.fe_inicio = fe_inicio;
	}
	public String getHh_inicio() {
		return hh_inicio;
	}
	public void setHh_inicio(String hh_inicio) {
		this.hh_inicio = hh_inicio;
	}
	public String getFe_fin() {
		return fe_fin;
	}
	public void setFe_fin(String fe_fin) {
		this.fe_fin = fe_fin;
	}
	public String getHh_fin() {
		return hh_fin;
	}
	public void setHh_fin(String hh_fin) {
		this.hh_fin = hh_fin;
	}
	public int getNu_dias() {
		return nu_dias;
	}
	public void setNu_dias(int nu_dias) {
		this.nu_dias = nu_dias;
	}
	public int getNu_horas() {
		return nu_horas;
	}
	public void setNu_horas(int nu_horas) {
		this.nu_horas = nu_horas;
	}
	public int getNu_minutos() {
		return nu_minutos;
	}
	public void setNu_minutos(int nu_minutos) {
		this.nu_minutos = nu_minutos;
	}
	public String getIn_tipo_especial() {
		return in_tipo_especial;
	}
	public void setIn_tipo_especial(String in_tipo_especial) {
		this.in_tipo_especial = in_tipo_especial;
	}
	public String getNb_tipo_especial() {
		return nb_tipo_especial;
	}
	public void setNb_tipo_especial(String nb_tipo_especial) {
		this.nb_tipo_especial = nb_tipo_especial;
	}
	public int getCi_especialista() {
		return ci_especialista;
	}
	public void setCi_especialista(int ci_especialista) {
		this.ci_especialista = ci_especialista;
	}
	public String getNb_especialista() {
		return nb_especialista;
	}
	public void setNb_especialista(String nb_especialista) {
		this.nb_especialista = nb_especialista;
	}
	public String getTx_motivo() {
		return tx_motivo;
	}
	public void setTx_motivo(String tx_motivo) {
		this.tx_motivo = tx_motivo;
	}
	public String getNb_usuario() {
		return nb_usuario;
	}
	public void setNb_usuario(String nb_usuario) {
		this.nb_usuario = nb_usuario;
	}

	/******************************************/
	/***********Variables de clase*************/
	/******************************************/	
	
	private String 	fe_inicio;
	private String 	hh_inicio;
	private String 	fe_fin;
	private String 	hh_fin;
	private int 	nu_dias;
	private int 	nu_horas;
	private int 	nu_minutos;
	private String 	in_tipo_especial;
	private String 	nb_tipo_especial;
	private int 	ci_especialista;
	private String 	nb_especialista;
	private String 	tx_motivo;
	private String 	nb_usuario;

}
