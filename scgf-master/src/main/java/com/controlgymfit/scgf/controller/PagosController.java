/**
 * 
 * CONTROLGYM
 * @author Joarkemu
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.controlgymfit.scgf.controller.beans.PagoForm;
import com.controlgymfit.scgf.controller.beans.BusquedaConsultaPagoForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Pago;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.service.PagoService;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.ClienteService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.MetodoPago;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.util.enums.TipoDocumento;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;
import com.controlgymfit.scgf.controller.beans.BusquedaPagoForm;

@Controller
@RequestMapping("pagos")
public class PagosController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private PagoService pagoService;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private PlanService planService;
	@Autowired
	private ClienteService clienteService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {
	    System.out.println("Pagos index");
	    ModelMap map = new ModelMap();
	    feedCatalogs(map);
	    
	    map.put("busquedaPago", new BusquedaPagoForm());
	    map.put("bp", new BusquedaConsultaPagoForm());

	    feedList(map);
	    return new ModelAndView("pagos/pagos", map);
	}

	
	/**
	 * Agregar un elemento al catálogo
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("pago", new Pago());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("pagos/altaEditaPago", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Pago pago = this.pagoService.findOne(id);
			
		if(pago==null){
			return new ModelAndView("pagos/");	
		}
						
		ModelMap map = new ModelMap();
		PagoForm forma = (new PagoForm()).fromOrmModel(pago, PagoForm.class);
		map.put("pago",forma);
		feedCatalogs(map);
				
		return new ModelAndView("pagos/altaEditaPago", map);		
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
			@Valid @ModelAttribute("pago") PagoForm pago,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {
	
		//String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("pago", pago);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("pagos/altaEditaPago", map);
		}

		// convierte la Forma al Modelo
		Pago modelo = pago.toOrmModel(Pago.class);

		// validaciones de negocio antes de persistir
		this.pagoService.validateBeforeCreate(modelo, result);
		// Crea un objeto SimpleDateFormat para formatear la fecha
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		if (result.hasErrors()) {
			map.put("pago", pago);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("pagos/altaEditaPago", map);
		}

		String[] msg = { "pago" };
		// persiste el modelo
		if (modelo.getId() == null) {
			//modelo.setUsuarioAlta(usuarioSesion);
			//modelo.setFecha(new Date());
			System.out.println(modelo.toString());
			this.pagoService.create(modelo);

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
			modelo.setFechaModifica(sdf.parse(FechaInicio));
			
			//Usar Calendar para calcular la fecha fin 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			
			if (plan.getDuracion() == TipoEntrenamiento.ENTRE_MENSUAL) {
				// Sumar la cantidad de meses
			    int cantidadMeses = plan.getDuracionCantidad();
			    calendar.add(Calendar.MONTH, cantidadMeses);
			} else if (plan.getDuracion() == TipoEntrenamiento.ENTRE_SEMANAL) {
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
			//modelo.setFechaFin(calendar.getTime());
			//modelo.setEstado(EstadoCatalogo.ACTIVO);
			this.pagoService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("pagos/pagos", map);
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
			this.pagoService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils
							.getDataIntegrityViolationMessage(e,
									this.messageSource)));

			return new ModelAndView("pagos/pagos", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("pagos/pagos", map);
	}
	
	
	/**
	 * Método que se encarga realizar la búsqueda de los pagos
	 * @author JQ.
	 * @param request
	 * @param authentication
	 * @param response
	 * @param tipo
	 * 
	 * @return
	 */
	@RequestMapping(value = "/buscarP", method = RequestMethod.POST)
	public ModelAndView buscarP(@Valid @ModelAttribute("bp") BusquedaConsultaPagoForm bp, BindingResult result,
			HttpServletRequest request, RedirectAttributes ra) {
		ModelMap map = new ModelMap();
		feedCatalogs(map);
		
		String usuarioSesion = SecurityUtils.getCurrentUser();
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);

		if (bp.getTipoDocumento() == TipoDocumento.PAGOS) {
			List<Pago> pagos = pagoService.getPagosConsulta(user.getEmpresa(), bp);
			map.put("pagos", pagos);	
		System.out.println("Consulta de documentos "+bp);
		}
		else if (bp.getTipoDocumento() == TipoDocumento.FACTURAS) {
			List<Factura> facturas = facturaService.getFacturasConsulta(user.getEmpresa().getId(), bp);
			map.put("facturas", facturas);	
		System.out.println("Consulta de documentos "+bp);
		}
//		else if (bp.getMetodoPago() == MetodoPago.EFECTIVO) {
//			List<Pago> pagoE = pagoService.getPagosConsulta(user.getEmpresa(), bp);
//			map.put("pagoE", pagoE);
//		}
//		else if (bp.getMetodoPago() == MetodoPago.TARJETA) {
//			List<Pago> pagoT = pagoService.getPagosConsulta(user.getEmpresa(), bp);
//			map.put("pagoT", pagoT);
//		}else if (bp.getMetodoPago() == MetodoPago.TRANSFERENCIA) {
//			List<Pago> pagoTf = pagoService.getPagosConsulta(user.getEmpresa(), bp);
//			map.put("pagoTf", pagoTf);
//		}
		return new ModelAndView("pagos/pagos", map);
	}
	
	/**
	 * Realiza la conversión de objetos de los formularios.
	 * 
	 * @param binder
	 */
	@InitBinder
	void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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

		map.put("metodos", MetodoPago.getMetodos());
		map.put("tipos", TipoDocumento.getTipos());
		map.put("socios", clienteService.getClientesByEmp(user.getEmpresa().getId()));
		map.put("planes", planService.getPlanesByEmp(user.getEmpresa().getId()));
		Integer empId =  user.getEmpresa().getId();  
		map.put("empId", empId);
	}
	
	private void feedList(ModelMap map) {
		 //String usuarioSesion = SecurityUtils.getCurrentUser();
		 //Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 //map.put("pagos", pagoService.getPagoByEmp(user.getEmpresa().getId()));
	}

}
