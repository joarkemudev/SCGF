package com.controlgymfit.scgf.service.imp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.multipart.MultipartFile;

import com.controlgymfit.scgf.dao.ClienteDao;
import com.controlgymfit.scgf.dao.EmpresaDao;
import com.controlgymfit.scgf.dao.PlanDao;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.generic.IOperations;
import com.controlgymfit.scgf.service.ClienteService;
import com.controlgymfit.scgf.service.generic.AbstractService;
import com.controlgymfit.scgf.service.generic.DuplicateValidator;
import com.controlgymfit.scgf.controller.beans.Archivo;
import com.controlgymfit.scgf.controller.beans.Mensaje;

import com.controlgymfit.scgf.controller.beans.ClienteForm;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.util.enums.TipoIdentificacion;
import com.controlgymfit.scgf.util.enums.TipoGenero;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

@Service
public class ClienteServiceImp extends AbstractService<Cliente> implements ClienteService {

	@Autowired
	private ClienteDao dao;
	@Autowired
	private EmpresaDao empresaDao;
	private Hashtable<String, Empresa> empresas = new Hashtable<String, Empresa>();
	
	@Autowired
	private PlanDao planDao;
	private Hashtable<String, Plan> planes = new Hashtable<String, Plan>();

	public ClienteServiceImp() {
		super();
	}

	@Override
	protected IOperations<Cliente> getDao() {
		return dao;
	}

	@Override
	public void validateBeforeCreate(Cliente entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeUpdate(Cliente entity, BindingResult result) {
		validateDuplicates(entity, result);
	}

	@Override
	public void validateBeforeDelete(Cliente entity, BindingResult result) {
		validateDuplicates(entity, result);
	}
	
	/**
	 * Valida existencia de duplicados antes de guardar. Se basa en los campos: rol
	 * @param entity
	 * @param result
	 */
	private void validateDuplicates(Cliente entity, BindingResult result){
		ArrayList<String[]> props = new ArrayList<String[]>();
		props.add(new String[]{"numIdentificacion"});
		DuplicateValidator<Cliente> validator = new DuplicateValidator<Cliente>(Cliente.class, this, props);
		ValidationUtils.invokeValidator(validator, entity, result);		
	}

	@Override
	public Cliente getClientes(Integer idCliente) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Cliente> getClientesByEmp(Integer idEmpresa) {
		return dao.getClientesByEmp(idEmpresa);
	}

	@Override
	public Mensaje cargaMasivaClientes(Archivo cm) {
		Mensaje men = new Mensaje();
		men.setTipo(Mensaje.TIPO_SUCCESS);
		
		String nombreAcceso = ((UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal()).getUsername();

		System.out.println("Entra a carga masiva de los Socios.");

		if (cm.getArchivo() != null) {
			if (!cm.getArchivo().isEmpty()) {
				
				cargaEmpresas(); //Realiza la carga de empresas para hacer mas eficiente el acceso a la base de datos.
				cargaPlanes();
				
				MultipartFile multipartFile = cm.getArchivo();

				try {
					ArrayList<ClienteForm> array = new ArrayList<ClienteForm>();

					InputStream excel = multipartFile.getInputStream();
					HSSFWorkbook wb = new HSSFWorkbook(excel);

					HSSFSheet sheet = wb.getSheetAt(0);
					// Iterate through each rows one by one
					Iterator<Row> rowIterator = sheet.iterator();
					rowIterator.next(); //renglon 1
					int renglon = 2;
					cargaEmpresas();
					cargaPlanes();
					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();

						ClienteForm cli = new ClienteForm();

						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							System.out.println("SWITCH COLUMN: "+ cell.getColumnIndex());
							cell.setCellType(Cell.CELL_TYPE_STRING);

							try {
								switch (cell.getColumnIndex()) {
																
								case 0:
									Empresa empresa = empresas.get(cell.getStringCellValue());
									if (empresa == null){
										men.setTipo(Mensaje.TIPO_ERROR);
										men.getMensajeFijo().add("No existe la empresa. Renglon "+ renglon);
									}else{
										cli.setEmpresa(empresa);
									}									
									break;
								case 1:
									Plan plan = planes.get(cell.getStringCellValue());
									if (plan == null){
										men.setTipo(Mensaje.TIPO_ERROR);
										men.getMensajeFijo().add("No existe el plan. Renglon "+ renglon);
									}else{
										cli.setPlan(plan);
									}									
									break;									
								case 2:
									if(validaTam(men, 50, cell.getStringCellValue().toUpperCase(), renglon))
										cli.setNombre(cell.getStringCellValue().toUpperCase());
									break;
								case 3:
									if(validaTam(men, 50, cell.getStringCellValue().toUpperCase(), renglon))
										cli.setPrimerApellido(cell.getStringCellValue().toUpperCase());
									break;
								case 4:									
									if(validaTam(men, 50, cell.getStringCellValue().toUpperCase(), renglon))
										cli.setSegundoApellido(cell.getStringCellValue().toUpperCase());
									break;
								case 5:
								    String tipoString = cell.getStringCellValue().toUpperCase(); // Obtener y convertir a mayúsculas
								    try {
								        // Intentar convertir el String a TipoIdentificacion
								        TipoIdentificacion tipo = TipoIdentificacion.valueOf(tipoString);

								        // Asignar el tipo al cliente si es válido
								        cli.setTipoIdentificacion(tipo);
								    } catch (IllegalArgumentException e) {
								        // Manejar caso en que el tipo no sea válido
								        men.setTipo(Mensaje.TIPO_ERROR);
								        men.getMensajeFijo().add(String.format(
								            "Favor de especificar un Tipo de Identificación válido (CC, CE, TI, PP).", 
								            renglon
								        ));
								    }
								    break;
								case 6:									
									if(validaTam(men, 13, cell.getStringCellValue().toUpperCase(), renglon))
										if(!dao.existe(cell.getStringCellValue().toUpperCase())){
											cli.setNumIdentificacion(cell.getStringCellValue().toUpperCase());
										}else{												
											men.setTipo(Mensaje.TIPO_ERROR);
											men.getMensajeFijo().add("El Socio con Num de Identificación: " +cell.getStringCellValue().toUpperCase() 
													+" ya existe en la BD." + " Renglon "+ renglon);
										}
									break;
								case 7:
									if (validaTam(men, 12, cell.getStringCellValue().toUpperCase(), renglon)) {
									    String valorCelda = cell.getStringCellValue();
									    System.out.println(valorCelda);

									    if (valorCelda == null || valorCelda.isEmpty()) {
									        men.setTipo(Mensaje.TIPO_ERROR);
									        men.getMensajeFijo().add("El campo fecha de nacimiento está vacío. Renglón " + renglon);
									    } else {
									        try {
									            // Define el formato esperado de la fecha
									            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
									            LocalDate fechaNacimiento = LocalDate.parse(valorCelda, formatter);

									            // Validar si la fecha no está en el futuro
									            if (fechaNacimiento.isAfter(LocalDate.now())) {
									                men.setTipo(Mensaje.TIPO_ERROR);
									                men.getMensajeFijo().add("La fecha de nacimiento no puede estar en el futuro (valor: " 
									                                         + valorCelda + "). Renglón " + renglon);
									            }  
									            // Si pasa todas las validaciones
									            else {
									                // Convertir LocalDate a Date
									                Date fechaNacimientoDate = Date.from(fechaNacimiento.atStartOfDay(ZoneId.systemDefault()).toInstant());
									                cli.setFechaNacimiento(fechaNacimientoDate);
									            }
									        } catch (DateTimeParseException e) {
									            // Si el formato no es válido
									            men.setTipo(Mensaje.TIPO_ERROR);
									            men.getMensajeFijo().add("El campo fecha de nacimiento no tiene un formato válido (dd/MM/yyyy). "
									                                     + "Valor: " + valorCelda + ". Renglón " + renglon);
									        }
									    }
									}
									break;
								case 8:
									if (validaTam(men, 50, cell.getStringCellValue().toUpperCase(), renglon)) {
									    String valorCelda = cell.getStringCellValue();
									    System.out.println(valorCelda);

									    if (valorCelda == null || valorCelda.isEmpty()) {
									        men.setTipo(Mensaje.TIPO_ERROR);
									        men.getMensajeFijo().add("El campo correo electrónico está vacío. Renglón " + renglon);
									    } else {
									        // Expresión regular para validar correos electrónicos
									        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
									        if (!valorCelda.matches(emailRegex)) {
									            men.setTipo(Mensaje.TIPO_ERROR);
									            men.getMensajeFijo().add("El campo correo electrónico no tiene un formato válido. "
									                                     + "Valor: " + valorCelda + ". Renglón " + renglon);
									        } else {
									            // Si pasa la validación, asignar el correo
									            cli.setCorreoElectronico(valorCelda);
									        }
									    }
									}
									break;
								case 9:
									if(validaTam(men, 13, cell.getStringCellValue().toUpperCase(), renglon)){
										System.out.println(cell.getStringCellValue());
										if(!cell.getStringCellValue().isEmpty() && !cell.getStringCellValue().matches("[0-9]*")){
											men.setTipo(Mensaje.TIPO_ERROR);
											men.getMensajeFijo().add("El campo teléfono Movil debe ser numérico " 
													+ ". Renglon "+ renglon);
										}else{
											cli.setTelefonoMovil(cell.getStringCellValue());
										}
									}
									break;							
								case 10:
									String tipoGString = cell.getStringCellValue().toUpperCase(); // Obtener y convertir a mayúsculas
								    try {
								        // Intentar convertir el String a TipoIdentificacion
								        TipoGenero tipoG = TipoGenero.valueOf(tipoGString);

								        // Asignar el tipo al cliente si es válido
								        cli.setGenero(tipoG);
								    } catch (IllegalArgumentException e) {
								        // Manejar caso en que el tipo no sea válido
								        men.setTipo(Mensaje.TIPO_ERROR);
								        men.getMensajeFijo().add(String.format(
								            "Favor de especificar un Genero válido (MC, FM, OT).", 
								            renglon
								        ));
								    }
								    break;	
								}
								
							} catch (NumberFormatException nfe) {
								array.clear();
								break;
							}
						}
						
						
						validaVacio("ID de la Empresa", men, (cli.getEmpresa()==null?"":"Empresa"), renglon);
						validaVacio("ID del Plan", men, (cli.getPlan()==null?"":"Plan"), renglon);
						validaVacio("Num Identificación", men, cli.getNumIdentificacion(), renglon);
						//Agrega si no existen errores.
						if(men.getTipo() != Mensaje.TIPO_ERROR){						
							array.add(cli);
						}else{
							array.clear();
						}
						
						renglon++;
					}

					// Si no hubo problema en agregar Registro del archivo de
					// excel se procede a la alta.
					if (men.getTipo() == Mensaje.TIPO_SUCCESS) {
						for (ClienteForm cliForm : array) {
							
							Cliente modelo = cliForm.toOrmModel(Cliente.class);
							modelo.setFechaAlta(new Date());
							modelo.setUsuarioAlta(nombreAcceso);
							modelo.setEstado(EstadoCatalogo.ACTIVO);
							
							//System.out.println(provForm);
							dao.create(modelo);
						}
						men.setMensaje("Se cargaron " + array.size()
								+ " registros.");
					}
					excel.close();
					
				} catch (Exception e) {
					men.setTipo(Mensaje.TIPO_ERROR);
					men.setMensaje("Error al realizar la carga masiva.");
					e.printStackTrace();
				}

			} else {
				men.setTipo(Mensaje.TIPO_ERROR);
				men.setMensaje("El archivo no contiene información valida.");
			}
		} else {
			men.setTipo(Mensaje.TIPO_ERROR);
			men.setMensaje("El archivo no contiene información valida.");
		}
		empresas.clear();
		return men;
	}
	

	private boolean validaTam(Mensaje men, int limit, String texto, int renglon){
		boolean res = true;
		if(texto.length() > limit){
			men.setTipo(Mensaje.TIPO_ERROR);
			men.getMensajeFijo().add("El tamaño del campo debe ser menor a " + limit + ". Renglon "+ renglon);
			res = false;
		}
		return res;
	}
	
	private boolean validaTamExacto(String campo, Mensaje men, int tam, String texto, int renglon){
		boolean res = true;
		if(texto.length() != tam){
			men.setTipo(Mensaje.TIPO_ERROR);
			men.getMensajeFijo().add("La longitud del campo "+campo+" debe ser de " + tam + ". Renglon "+ renglon);
			res = false;
		}
		return res;
	}
	
	private boolean validaVacio(String campo, Mensaje men, String texto, int renglon){
		System.out.println("Valida:" + campo + " valor: " + texto);
		boolean res = true;
		if(texto == null || texto.trim().length() == 0){
			men.setTipo(Mensaje.TIPO_ERROR);
			men.getMensajeFijo().add("El campo " + campo + " es requerido" + ". Renglon "+ renglon);
			res = false;
		}
		return res;
	}
	
	/**
	 * Asigna oficina al usuario si es que le corresponden según el Negocio.
	 * @param nombreOficina
	 * Negocio al que aplica la carga masiva.
	 * @return True si la asignación de la empresa es correcta, false de lo contrario.
	 */
	private boolean cargaEmpresas() {
		List<Empresa> empresasList = empresaDao.findAll();
		for(Empresa em : empresasList){
			empresas.put(em.getId().toString(), em);
		}
		return true;
	}
	
	/**
	 * aplica la carga masiva.
	 * @return True si la asignación del plan es correcta, false de lo contrario.
	 */
	private boolean cargaPlanes() {
		List<Plan> planesList = planDao.findAll();
		for(Plan pl : planesList){
			planes.put(pl.getId().toString(), pl);
		}
		return true;
	}
	
   @Override
    public boolean existe(String numIdentificacion) {
        return dao.existe(numIdentificacion);
    }
   
   @Override
   public Cliente getIdEmpresaCliente(String correoElectronico) {
	   return dao.getIdEmpresaCliente(correoElectronico);
   }
}
