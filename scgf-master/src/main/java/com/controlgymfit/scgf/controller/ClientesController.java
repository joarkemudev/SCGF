/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.NumberFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.controlgymfit.scgf.controller.beans.Archivo;
import com.controlgymfit.scgf.controller.beans.ClienteForm;
import com.controlgymfit.scgf.controller.beans.FacturaForm;
import com.controlgymfit.scgf.controller.beans.PagoForm;
import com.controlgymfit.scgf.controller.beans.Select;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Membresia;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.service.MembresiaService;
import com.controlgymfit.scgf.service.ClienteService;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.service.PagoService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.EmailService;
import com.controlgymfit.scgf.util.Encripta;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;
import com.controlgymfit.scgf.util.enums.TipoIdentificacion;

import com.controlgymfit.scgf.util.enums.EstadoFactura;
import com.controlgymfit.scgf.util.enums.TipoActividad;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;
import com.controlgymfit.scgf.util.enums.TipoGenero;
import com.controlgymfit.scgf.util.enums.MetodoPago;

@Controller
@RequestMapping("clientes")
public class ClientesController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private MembresiaService membresiaService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private PagoService pagoService;
	@Autowired
	private CorreoService correoService; 
	@Autowired
	private EmailService emailService;
	@Autowired
	private PlanService planService;
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {
		
		System.out.println("clientes index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("clientes/clientes", map);
	}
	
	/**
	 * Agregar un elemento al catálogo
	 * 
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("cliente", new ClienteForm(EstadoCatalogo.ACTIVO));
		feedCatalogs(map, null);

		System.out.println("Entra a add");
		return new ModelAndView("clientes/altaEditaCliente", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Cliente cliente = this.clienteService.findOne(id);
			
		if(cliente==null){
			return new ModelAndView("Clientes/");	
		}
						
		ModelMap map = new ModelMap();
		ClienteForm forma = (new ClienteForm()).fromOrmModel(cliente, ClienteForm.class);
		map.put("cliente",forma);
		feedCatalogs(map, cliente.getId());
				
		return new ModelAndView("clientes/altaEditaCliente", map);		
	}
	
	/**
	 * Salvar/Actualizar un elemento del catálogo
	 * 
	 * @param usuario
	 * @param result
	 * @param request
	 * @param ra
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(
			@Valid @ModelAttribute("cliente") ClienteForm cliente,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {

		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("cliente", cliente);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("clientes/altaEditaCliente", map);
		}

		// convierte la Forma al Modelo
		Cliente modelo = cliente.toOrmModel(Cliente.class);

		// validaciones de negocio antes de persistir
		this.clienteService.validateBeforeCreate(modelo, result);
		
		if (result.hasErrors()) {
			map.put("cliente", cliente);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("clientes/altaEditaCliente", map);
		}

		String[] msg = { "cliente" };
		//se tare el plan requerido 
		Plan plan = this.planService.getPlanByIdCliente(modelo.getPlan().getId());
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		modelo.setEmpresa(user.getEmpresa());
		// Crea un objeto SimpleDateFormat para formatear la fecha
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Formato de moneda
        NumberFormat formatoMoneda = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));

        
		// persiste el modelo
		if (modelo.getId() == null) {
			
			// Obtén el valor de la fecha seleccionada
			String FechaNacimiento = request.getParameter("fechaNacimiento");
			if (FechaNacimiento == ""){
				FechaNacimiento = sdf.format(new Date());
			}
			modelo.setFechaNacimiento(sdf.parse(FechaNacimiento));
			modelo.setNumIdentificacion(Encripta.encripta(modelo.getNumIdentificacion()));
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			this.clienteService.create(modelo);	
			
			//Crear y configurar la Membresia en los clientes 
			Membresia membresia = new Membresia();
			membresia.setEmpresa(modelo.getEmpresa());
			membresia.setCliente(modelo);
			membresia.setPlan(plan);
			membresia.setFechaInicio(new Date());
			
			// Usar Calendar para calcular la fecha de fin
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			if (plan.getDuracion() == TipoEntrenamiento.ENTRE_MENSUAL) {
				// Sumar la cantidad de meses
			    int cantidadMeses = plan.getDuracionCantidad();
			    calendar.add(Calendar.MONTH, cantidadMeses);
			}else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_SEMANAL) {
			    // Sumar la cantidad de semanas
			    int cantidadSemanas = plan.getDuracionCantidad();
			    calendar.add(Calendar.WEEK_OF_YEAR, cantidadSemanas);
			} else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_DIARIO) {
			    // Sumar la cantidad de días
			    int cantidadDias = plan.getDuracionCantidad();
			    calendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
			} else {
			    // Manejar otros casos de duración o lanzar una excepción si es necesario
			    throw new IllegalArgumentException("Duración no soportada: " + plan.getDuracion());
			}
			// Establecer la fecha de fin en la membresía
			membresia.setFechaFin(calendar.getTime());
			membresia.setEstado(EstadoCatalogo.ACTIVO);
			membresia.setUsuarioAlta(usuarioSesion);
			membresia.setFechaAlta(new Date());
			membresiaService.create(membresia);
			
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
			factura.setCliente(modelo);
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
	        
			//String fechaFinFormateada = sdf.format(membresia.getFechaFin());
			//Envío de correo de notificación al Socio afiliado.
			Correo correo = correoService.getCorreoPorActividad(TipoActividad.BIENVENIDA_SOCIO);
			if(correo != null){
				//sol = solicitudGastosService.findOne(datos.getIdSolicitud());
				ArrayList<String> destinatarios = new ArrayList<String>();
				destinatarios.add(modelo.getCorreoElectronico());
				correo.setTo(destinatarios);
				HashMap<String, Object> mapp = new HashMap<String, Object>();
				mapp.put("{nombreGym}", user.getEmpresa().getNombre());
				mapp.put("{socio}", modelo.getNombre());
				mapp.put("{nombreSocio}", modelo.getNombre()+" " + modelo.getPrimerApellido());
				mapp.put("{valorPlan}", formatoMoneda.format(plan.getPrecio()));
				mapp.put("{fechaInicio}", sdf.format(membresia.getFechaInicio()));
				mapp.put("{fechaFin}", sdf.format(membresia.getFechaFin()));
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
					
			//Actualizar y configurar la Membresia en los clientes 
			Membresia membresia = this.membresiaService.getMembresiaByIdCliente(modelo.getId());
			System.out.println("Id CLiente encontrado."+modelo.getId());
			System.out.println("Membresía encontrada."+membresia.getId());
			System.out.println("Plan encontrado."+plan.getDuracion());
			membresia.setFechaInicio(new Date());
			
			// Usar Calendar para calcular la fecha de fin
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(membresia.getFechaInicio());
			
			if (plan.getDuracion() == TipoEntrenamiento.ENTRE_MENSUAL) {
				// Sumar la cantidad de meses
			    int cantidadMeses = plan.getDuracionCantidad();
			    calendar.add(Calendar.MONTH, cantidadMeses);
			}else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_SEMANAL) {
			    // Sumar la cantidad de semanas
			    int cantidadSemanas = plan.getDuracionCantidad();
			    calendar.add(Calendar.WEEK_OF_YEAR, cantidadSemanas);
			} else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_DIARIO) {
			    // Sumar la cantidad de días
			    int cantidadDias = plan.getDuracionCantidad();
			    calendar.add(Calendar.DAY_OF_YEAR, cantidadDias);
			} else {
			    // Manejar otros casos de duración o lanzar una excepción si es necesario
			    throw new IllegalArgumentException("Duración no soportada: " + plan.getDuracion());
			}
			// Establecer la fecha de fin en la membresía
			membresia.setFechaFin(calendar.getTime());
			membresia.setEstado(EstadoCatalogo.ACTIVO);
			membresia.setUsuarioModifica(usuarioSesion);
			membresia.setFechaModifica(new Date());
			membresiaService.update(membresia);
			
			// Obtén el valor de la fecha seleccionada
			String FechaNacimiento = request.getParameter("fechaNacimiento");
			if (FechaNacimiento == ""){
				FechaNacimiento = sdf.format(new Date());
			}
			
			modelo.setNumIdentificacion(Encripta.encripta(modelo.getNumIdentificacion())); // Actualiza clave.
			
			modelo.setFechaNacimiento(sdf.parse(FechaNacimiento));
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.clienteService.update(modelo);
			
			//Envío de correo de notificación al Socio afiliado.
			Correo correo = correoService.getCorreoPorActividad(TipoActividad.ACT_FECHA_MEMB);
			if(correo != null){
				//sol = solicitudGastosService.findOne(datos.getIdSolicitud());
				ArrayList<String> destinatarios = new ArrayList<String>();
				destinatarios.add(modelo.getCorreoElectronico());
				correo.setTo(destinatarios);
				HashMap<String, Object> mapp = new HashMap<String, Object>();
				mapp.put("{nombreGym}", user.getEmpresa().getNombre());
				mapp.put("{socio}", modelo.getNombre());
				mapp.put("{nombreSocio}", modelo.getNombre()+" " + modelo.getPrimerApellido());
				mapp.put("{valorPlan}", formatoMoneda.format(plan.getPrecio()));
				mapp.put("{fechaInicio}", sdf.format(membresia.getFechaInicio()));
				mapp.put("{fechaFin}", sdf.format(membresia.getFechaFin()));
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
		return new ModelAndView("clientes/clientes", map);
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
	        // Primero eliminar la membresía asociada
	        Membresia mem = this.membresiaService.getMembresiaByIdCliente(id);
	        if (mem != null) {
	            this.membresiaService.deleteById(mem.getId());
	        }

	        // Luego eliminar el cliente
	        this.clienteService.deleteById(id);

	    } catch (DataIntegrityViolationException e) {
	        ModelMap map = new ModelMap();
	        feedList(map);
	        map.put("mensaje",
	                new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils.getDataIntegrityViolationMessage(e,this.messageSource)));

	        return new ModelAndView("clientes/clientes", map);
	    }

	    ModelMap map = new ModelMap();
	    feedList(map);
	    map.put("mensaje",
	            new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
	                    "entity.delete.success", null, null)));

	    return new ModelAndView("clientes/clientes", map);
	}
	
	/**
	 * Obtenemos la bitácora
	 * @return 
	 * @return
	 */
	@RequestMapping(value = "/consultaBitacora", method = RequestMethod.GET)
	public @ResponseBody void consultaBitacora(HttpServletResponse resp,
			@RequestParam(required = true) Integer idCliente) {
		
		//SimpleDateFormat sdfF = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		System.out.println("Bitacoras size: "+ idCliente);
		
		return;

	}
	
	/**
	 * Muestra pantalla de Carga masiva.
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/cargaMasiva", method = RequestMethod.GET)
	public ModelAndView cargaMasiva(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("archivo", new Archivo());

		return new ModelAndView("clientes/cargaMasiva", map);
	}
	
	/**
	 * Ejecuta la carga masiva de los socios.
	 * @param authentication
	 * @return Vista de mensaje de Error o Vista de Busqueda de Partidas
	 */
	@RequestMapping(value = "/ejecutaCargaMasiva", method = RequestMethod.POST)
	public ModelAndView ejecutaCargaMasiva(Authentication authentication, RedirectAttributes ra,
			@ModelAttribute("archivo") Archivo archivo) {

		System.out.println("Entra a ejecutaCargaMasiva");
		Mensaje men = clienteService.cargaMasivaClientes(archivo);
		
		if (men.getTipo() == Mensaje.TIPO_ERROR) {
			ra.addFlashAttribute("mensaje", men);
			return new ModelAndView("redirect:cargaMasiva");
		} else {
			ra.addFlashAttribute("mensaje", men);
			return new ModelAndView("redirect:/clientes/");
		}
	}
	
	/**
	 * Trae el detalle de la factura.
	 * @return Vista de mensaje informativo 
	 */
	@RequestMapping(value = "/getFactDetails", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Select> getFactsDetails(@RequestParam("idPlan") Integer idPlan) {
	    ArrayList<Select> array = new ArrayList<>();
	    
		//Obtiene solo las cuentas activas.
		for(Plan pl : planService.getPlanes(idPlan)) {
			Select sel = new Select();
			sel.setValue(pl.getId().toString());
			sel.setValue(pl.getPrecio().toString());
			sel.setLabel(pl.getNombre());
			array.add(sel);
		}

		
		return array;	
	}
	
	/**
	 * M�todo que sube y guarda pago correspondiente al Cliente
	 * @author JQ
	 * @version 1.0
	 * @param factura objeto correspondiente al formulario.
	 * @param result
	 * @param request
	 * @param ra
	 * @throws ParseException
	 */
	@RequestMapping(value = "/savePago", method = RequestMethod.POST)
	public ModelAndView savePago(@Valid @ModelAttribute("pago") PagoForm pago,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {
		
		String usuarioSesion = SecurityUtils.getCurrentUser();	   
		
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("pago", pago);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("clientes/"+ (pago.getId() == null ? "add" : "edit"),map);
		}

		// convierte la Forma al Modelo
		Pago modelo = pago.toOrmModel(Pago.class);
 

		// validaciones de negocio antes de persistir
		this.pagoService.validateBeforeCreate(modelo, result);
		if (result.hasErrors()) {
			map.put("pago", pago);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("clientes/"+ (pago.getId() == null ? "add" : "edit"), map);
		}

		if (modelo.getId() == null) {
			Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
			modelo.setIdEmpresa(user.getEmpresa().getId());
			modelo.setFechaPago(new Date());
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			
			// Se actualiza el importe de la Factura.
			Factura factura = this.facturaService.findOne(pago.getFactura().getId());
			// Valida el estado actual de la factura antes de actualizar.
			if (factura.getEstado() == EstadoFactura.PENDIENTE) {
			    // Actualiza el monto pagado acumulado.
			    Double montoPagado = factura.getMontoPagado() + modelo.getMontoPagado();
			    factura.setMontoPagado(montoPagado);

			    // Actualiza el estado de la factura basado en el monto pagado.
			    if (montoPagado >= factura.getTotal()) {
			        factura.setEstado(EstadoFactura.PAGADA);
			    } else {
			        factura.setEstado(EstadoFactura.PENDIENTE);
			    }
			}

			// Actualiza la factura en la base de datos.
			this.facturaService.update(factura);
			// Guarda el pago correspondiente a la factura
			this.pagoService.create(modelo);
			
			ra.addFlashAttribute("mensaje",
						new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
								"entity.create.success", null, null)));
			
		}
		return new ModelAndView("redirect:/clientes/edit?id=" + modelo.getCliente().getId());


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
	 *            mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map, Integer idCliente) {
		String usuarioSesion = SecurityUtils.getCurrentUser();
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);

		map.put("pago", new PagoForm());
		map.put("factura", new FacturaForm());
		map.put("planes", planService.getPlanesByEmp(user.getEmpresa().getId()));
		map.put("tipoIdenti", TipoIdentificacion.getTipos());	
		map.put("tipoGen", TipoGenero.getTipos());
		
		
		if (idCliente != null) {
			map.put("metodosPago", MetodoPago.getMetodos());
			map.put("facturas", this.facturaService.getFacturasDeCliente(EstadoFactura.PENDIENTE, idCliente));
		}
	}
	
	private void feedList(ModelMap map) {
		 map.put("allClientes",clienteService.findAll());
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 map.put("clientes",clienteService.getClientesByEmp(user.getEmpresa().getId()));
	}

}
