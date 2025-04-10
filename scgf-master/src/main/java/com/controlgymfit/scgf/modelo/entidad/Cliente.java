/**
 * 
 */
package com.controlgymfit.scgf.modelo.entidad;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.controlgymfit.scgf.modelo.generic.GenericModel;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.util.enums.TipoGenero;
import com.controlgymfit.scgf.util.enums.TipoIdentificacion;

/**
  * Modelo del repositorio de Clientes
  * @author Sistemas
  * @version 1.0
 */
public class Cliente extends GenericModel<Cliente> implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	
	private Empresa empresa;
	@ManyToOne
	@JoinColumn(name = "idPlan", referencedColumnName = "id")
	private Plan plan;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	
	private TipoIdentificacion tipoIdentificacion;
	private String numIdentificacion;
	private String correoElectronico;
	private Date fechaNacimiento;
	private String telefonoMovil;
	private TipoGenero genero;
	private EstadoCatalogo estado;
	
	private String usuarioAlta;
	private Date fechaAlta;
	private String usuarioModifica;
	private Date fechaModifica;

	public Cliente() {
	}
	
    @OneToMany(mappedBy = "socio")
    private Set<InscritosClase> clasesInscritas = new HashSet<>();

	public Set<InscritosClase> getClasesInscritas() {
		return clasesInscritas;
	}

	public void setClasesInscritas(Set<InscritosClase> clasesInscritas) {
		this.clasesInscritas = clasesInscritas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public TipoIdentificacion getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(TipoIdentificacion tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public String getNumIdentificacion() {
		return numIdentificacion;
	}

	public void setNumIdentificacion(String numIdentificacion) {
		this.numIdentificacion = numIdentificacion;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	
	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public TipoGenero getGenero() {
		return genero;
	}

	public void setGenero(TipoGenero genero) {
		this.genero = genero;
	}

	public EstadoCatalogo getEstado() {
		return estado;
	}

	public void setEstado(EstadoCatalogo estado) {
		this.estado = estado;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}
	
    public String getNombreCompleto() {
        return nombre + " " + primerApellido;
    }

	@Override
	public String toString() {
		return "Cliente [id=" + id +", empresa=" + empresa +", nombre=" + nombre + ", primerApellido=" + primerApellido + ", segundoApellido=" + segundoApellido 
				+ ", numIdentificacion=" + numIdentificacion + ", correoElectronico=" + correoElectronico + ", fechaNacimiento=" + fechaNacimiento 
				+ ", telefonoMovil=" + telefonoMovil + ", usuarioAlta=" + usuarioAlta + ", fechaAlta=" + fechaAlta + ", usuarioModifica="
				+ usuarioModifica + ", fechaModifica=" + fechaModifica + ", plan=" + plan + "]";
	}
}
