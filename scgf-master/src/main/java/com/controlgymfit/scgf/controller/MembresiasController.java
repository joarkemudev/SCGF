/**
 * 
 * CONTROLGYM
 * @author Joarkemu
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.controlgymfit.scgf.controller.beans.MembresiaForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.service.MembresiaService;
import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.EmailService;
import com.controlgymfit.scgf.service.ClienteService;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.util.enums.EstadoFactura;
import com.controlgymfit.scgf.util.enums.TipoActividad;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;

@Controller
@RequestMapping("membresias")
public class MembresiasController {
	
	//private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	MessageSource messageSource;
	@Autowired
	private MembresiaService membresiaService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private CorreoService correoService;
	@Autowired
	private EmailService emailService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private PlanService planService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {
		
		System.out.println("Membresias index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("membresias/membresias", map);
	}
	
	/**
	 * Agregar un elemento al catálogo
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("membresia", new Membresia());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("membresias/altaEditaMembresia", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Membresia membresia = this.membresiaService.findOne(id);
			
		if(membresia==null){
			return new ModelAndView("membresias/");	
		}
						
		ModelMap map = new ModelMap();
		MembresiaForm forma = (new MembresiaForm()).fromOrmModel(membresia, MembresiaForm.class);
		map.put("membresia",forma);
		feedCatalogs(map);
				
		return new ModelAndView("membresias/altaEditaMembresia", map);		
	}	
	
	/**
	 * Salvar/Actualizar un elemento del catálogo
	 * @param usuario
	 * @param result
	 * @param request
	 * @param ra
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(
			@Valid @ModelAttribute("membresia") MembresiaForm membresia,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {
	
		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("membresia", membresia);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("membresias/altaEditaMembresia", map);
		}

		// convierte la Forma al Modelo
		Membresia modelo = membresia.toOrmModel(Membresia.class);

		// validaciones de negocio antes de persistir
		this.membresiaService.validateBeforeCreate(modelo, result);
		// Crea un objeto SimpleDateFormat para formatear la fecha
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (result.hasErrors()) {
			map.put("membresia", membresia);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("membresias/altaEditaMembresia", map);
		}

		// Validar si el cliente ya tiene una membresía activa
		if (membresiaService.tieneMembresiaActiva(modelo.getCliente().getId(), EstadoCatalogo.ACTIVO)) {
		    map.put("mensaje", new Mensaje(Mensaje.TIPO_ERROR, "El socio ya tiene una membresía activa."));
		    return new ModelAndView("membresias/altaEditaMembresia", map);
		}

		// Si no tiene membresía activa, continuar con la lógica de creación
		// Formato de moneda
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
        
		String[] msg = { "membresia" };
		// persiste el modelo
		if (modelo.getId() == null) {
			// Obtén el id del plan seleccionado
			String PlanSeleccionado = request.getParameter("plan.id");
			// Convertir a Integer
			int planId = Integer.parseInt(PlanSeleccionado);
			//se tare el plan requerido 
			Plan plan = this.planService.getPlanByIdCliente(planId);
			// Obtén el valor de la fecha seleccionada
			String FechaInicio = request.getParameter("fechaInicio");
			if (FechaInicio == ""){
				FechaInicio = sdf.format(new Date());
			}
			//Usar Calendar para calcular la fecha fin 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			if (plan.getDuracion()== TipoEntrenamiento.ENTRE_MENSUAL) {
				// Sumar la cantidad de meses
			    int cantidadMeses = plan.getDuracionCantidad();
			    calendar.add(Calendar.MONTH, cantidadMeses);
			} else if (plan.getDuracion()== TipoEntrenamiento.ENTRE_SEMANAL) {
				// Sumar la cantidad de semanas
			    int cantidadSemanas = plan.getDuracionCantidad();
			    calendar.add(Calendar.WEEK_OF_YEAR, cantidadSemanas);
			}else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_DIARIO) {
			    // Sumar la cantidad de días
			    int cantidadDias = plan.getDuracionCantidad();
			    calendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
			}
			else {
				// Manejar otros casos de duración o lanzar una excepción si es necesario
			    throw new IllegalArgumentException("Duración no soportada: " + plan.getDuracion());
			}
			
			modelo.setFechaInicio(sdf.parse(FechaInicio));
			// Establecer la fecha de fin en la membresía
			modelo.setFechaFin(calendar.getTime());
			modelo.setEstado(EstadoCatalogo.ACTIVO);
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			this.membresiaService.create(modelo);
			
			//Crear y configurar la Factura en los clientes 
			String STIVAP = "19";
			double IVAP = Double.parseDouble(STIVAP);
			double IVA = (IVAP/100);
			double PR = plan.getPrecio();
			double VAIVA = (PR*IVA);
			double ST = (PR-VAIVA);
			double MP = 0;
			Factura factura = new Factura();
			factura.setIdEmpresa(modelo.getEmpresa().getId());
			factura.setCliente(modelo.getCliente());
			factura.setEstado(EstadoFactura.PENDIENTE);
	        //Generar automáticamente el número de factura
	        String nuevoNumFactura = generarNumFactura();
	        factura.setUuid(nuevoNumFactura);
	        factura.setDetalle(plan.getNombre());
	        factura.setFechaEmision(new Date());
	        factura.setSubTotal(ST);
	        factura.setIvaAplicable(STIVAP);
	        factura.setValorIva(VAIVA);
	        factura.setMontoPagado(MP);
	        factura.setTotal(ST+VAIVA);
	        factura.setUsuarioAlta(usuarioSesion);
	        factura.setFechaAlta(new Date());
	        facturaService.create(factura);
	        
	        System.out.println("Envio Correo Destinatarios: " + modelo.getCliente());
			
			//Envío de correo de notificación al Socio afiliado.
			Correo correo = correoService.getCorreoPorActividad(TipoActividad.BIENVENIDA_SOCIO);
			if(correo != null){
				//sol = solicitudGastosService.findOne(datos.getIdSolicitud());
				ArrayList<String> destinatarios = new ArrayList<String>();
				destinatarios.add(modelo.getCliente().getCorreoElectronico());
				correo.setTo(destinatarios);
				HashMap<String, Object> mapp = new HashMap<String, Object>();
				mapp.put("{nombreGym}", user.getEmpresa().getNombre());
				mapp.put("{socio}", modelo.getCliente().getNombre());
				mapp.put("{nombreSocio}", modelo.getCliente().getNombre()+" " + modelo.getCliente().getPrimerApellido());
				mapp.put("{valorPlan}", formatoMoneda.format(plan.getPrecio()));
				mapp.put("{fechaInicio}", sdf.format(modelo.getFechaInicio()));
				mapp.put("{fechaFin}", sdf.format(modelo.getFechaFin()));
				mapp.put("{tipoPlan}",plan.getNombre());
				mapp.put("{responsableGym}", user.getEmpresa().getResponsable());
				mapp.put("{correoGym}", user.getEmpresa().getCorreoElectronico());
				mapp.put("{telefonoGym}", user.getEmpresa().getTelefono());
				emailService.envioCorreo(correo, mapp);
				System.out.println("Envio Correo Destinatarios: " + destinatarios );
			}

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			// Obtén el id del plan seleccionado
			String PlanSeleccionado = request.getParameter("plan.id");
			// Convertir a Integer
			int planId = Integer.parseInt(PlanSeleccionado);
			//se tare el plan requerido 
			Plan plan = this.planService.getPlanByIdCliente(planId);
			// Obtén el valor de la fecha seleccionada
			String FechaInicio = request.getParameter("fechaInicio");
			
			if (FechaInicio == ""){
				FechaInicio = sdf.format(new Date());
			}
			modelo.setFechaInicio(sdf.parse(FechaInicio));
			
			//Usar Calendar para calcular la fecha fin 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			if (plan.getDuracion()== TipoEntrenamiento.ENTRE_MENSUAL) {
				// Sumar la cantidad de meses
			    int cantidadMeses = plan.getDuracionCantidad();
			    calendar.add(Calendar.MONTH, cantidadMeses);
			} else if (plan.getDuracion()== TipoEntrenamiento.ENTRE_SEMANAL) {
				// Sumar la cantidad de semanas
			    int cantidadSemanas = plan.getDuracionCantidad();
			    calendar.add(Calendar.WEEK_OF_YEAR, cantidadSemanas);
			}else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_DIARIO) {
			    // Sumar la cantidad de días
			    int cantidadDias = plan.getDuracionCantidad();
			    calendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
			}
			else {
				// Manejar otros casos de duración o lanzar una excepción si es necesario
			    throw new IllegalArgumentException("Duración no soportada: " + plan.getDuracion());
			}
			
			// Establecer la fecha de fin en la membresía
			modelo.setFechaFin(calendar.getTime());
			modelo.setEstado(EstadoCatalogo.ACTIVO);
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());
			this.membresiaService.update(modelo);
			
			//Crear y configurar la Factura en los clientes 
			String STIVAP = "19";
			double IVAP = Double.parseDouble(STIVAP);
			double IVA = (IVAP/100);
			double PR = plan.getPrecio();
			double VAIVA = (PR*IVA);
			double ST = (PR-VAIVA);
			double MP = 0;
			Factura factura = new Factura();
			factura.setIdEmpresa(modelo.getEmpresa().getId());
			factura.setCliente(modelo.getCliente());
			factura.setEstado(EstadoFactura.PENDIENTE);
	        //Generar automáticamente el número de factura
	        String nuevoNumFactura = generarNumFactura();
	        factura.setUuid(nuevoNumFactura);
	        factura.setDetalle(plan.getNombre());
	        factura.setFechaEmision(new Date());
	        factura.setSubTotal(ST);
	        factura.setIvaAplicable(STIVAP);
	        factura.setValorIva(VAIVA);
	        factura.setMontoPagado(MP);
	        factura.setTotal(ST+VAIVA);
	        factura.setUsuarioAlta(usuarioSesion);
	        factura.setFechaAlta(new Date());
	        facturaService.create(factura);
	        
	        System.out.println("Envio Correo Destinatarios: " + modelo.getCliente());
			
			//Envío de correo de notificación al Socio afiliado.
			Correo correo = correoService.getCorreoPorActividad(TipoActividad.ACT_FECHA_MEMB);
			if(correo != null){
				//sol = solicitudGastosService.findOne(datos.getIdSolicitud());
				ArrayList<String> destinatarios = new ArrayList<String>();
				destinatarios.add(modelo.getCliente().getCorreoElectronico());
				correo.setTo(destinatarios);
				HashMap<String, Object> mapp = new HashMap<String, Object>();
				mapp.put("{nombreGym}", user.getEmpresa().getNombre());
				mapp.put("{socio}", modelo.getCliente().getNombre());
				mapp.put("{nombreSocio}", modelo.getCliente().getNombre()+" " + modelo.getCliente().getPrimerApellido());
				mapp.put("{valorPlan}", formatoMoneda.format(plan.getPrecio()));
				mapp.put("{fechaInicio}", sdf.format(modelo.getFechaInicio()));
				mapp.put("{fechaFin}", sdf.format(modelo.getFechaFin()));
				mapp.put("{tipoPlan}",plan.getNombre());
				mapp.put("{responsableGym}", user.getEmpresa().getResponsable());
				mapp.put("{correoGym}", user.getEmpresa().getCorreoElectronico());
				mapp.put("{telefonoGym}", user.getEmpresa().getTelefono());
				emailService.envioCorreo(correo, mapp);
				System.out.println("Envio Correo Destinatarios: " + destinatarios );
			}

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("membresias/membresias", map);
	}
	
	/**
	 * Eliminar un elemento del catálogo
	 * @param authentication
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(Authentication authentication,
			@RequestParam(required = true) Integer id) {
		try {
			this.membresiaService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils
							.getDataIntegrityViolationMessage(e,
									this.messageSource)));

			return new ModelAndView("membresias/membresias", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("membresias/membresias", map);
	}
	
	// Método para generar el número de factura
	private String generarNumFactura() {
	    Factura ultimaFactura = facturaService.existeUuid();
	    String ultimoNumero = (ultimaFactura != null && ultimaFactura.getUuid() != null) 
	            ? ultimaFactura.getUuid() 
	            : "FAC000";

	    // Incrementar el número
	    int numero = Integer.parseInt(ultimoNumero.substring(3)) + 1;

	    // Formatear el número con ceros a la izquierda
	    return String.format("FAC%03d", numero);
	}
	
	/**
	 * Realiza la conversión de objetos de los formularios.
	 * 
	 * @param binder
	 */
	@InitBinder
	void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		binder.registerCustomEditor(Date.class, new FechaEditor(sdf));
	}
	
	/**
	 * Provee catálogos requeridos en el formulario
	 * 
	 * @param map
	 * mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map) {
		String usuarioSesion = SecurityUtils.getCurrentUser();
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		map.put("planes", planService.getPlanesByEmp(user.getEmpresa().getId()));
		map.put("clientes", clienteService.getClientesByEmp(user.getEmpresa().getId()));
		map.put("socios", membresiaService.tieneMembresiaInactiva(user.getEmpresa().getId(), EstadoCatalogo.INACTIVO));
		Empresa emp =  user.getEmpresa();  
		map.put("emp", emp); 
		

	}
	
	private void feedList(ModelMap map) {
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 map.put("membresias", membresiaService.getMembByEmp(user.getEmpresa().getId()));
	}

}
