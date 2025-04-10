/**
 * 
 * CONTROLGYM
 * @author Joarkemu
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import com.controlgymfit.scgf.controller.beans.ClaseForm;
import com.controlgymfit.scgf.controller.beans.InscritosClaseForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.modelo.entidad.Clase;
import com.controlgymfit.scgf.modelo.entidad.InscritosClase;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.ClaseService;
import com.controlgymfit.scgf.service.InscritosClaseService;
import com.controlgymfit.scgf.service.ClienteService;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.EstadoFactura;
import com.controlgymfit.scgf.util.enums.MetodoPago;

@Controller
@RequestMapping("clases")
public class ClasesController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	MessageSource messageSource;
	@Autowired
	private ClaseService claseService;
	@Autowired
	private InscritosClaseService inscritosClaseService;
	@Autowired
	private UsuarioService usuarioService;
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
		
		System.out.println("Clases index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("clases/clases", map);
	}
	
	/**
	 * Agregar un elemento al catálogo
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("clase", new Clase());
		feedCatalogs(map, null);

		System.out.println("Entra a add");
		return new ModelAndView("clases/altaEditaClase", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Clase clase = this.claseService.findOne(id);
			
		if(clase==null){
			return new ModelAndView("clases/");	
		}
						
		ModelMap map = new ModelMap();
		ClaseForm forma = (new ClaseForm()).fromOrmModel(clase, ClaseForm.class);
		map.put("clase",forma);
		feedCatalogs(map, clase.getId());
				
		return new ModelAndView("clases/altaEditaClase", map);		
	}	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(
			@Valid @ModelAttribute("clase") ClaseForm clase,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {
	
		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();
		
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		Empresa emp =  user.getEmpresa();  
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("clase", clase);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("clases/altaEditaClase", map);
		}

		// convierte la Forma al Modelo
		Clase modelo = clase.toOrmModel(Clase.class);

		// validaciones de negocio antes de persistir
		this.claseService.validateBeforeCreate(modelo, result);	
		if (result.hasErrors()) {
			map.put("clase", clase);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("clases/altaEditaClase", map);
		}
        
		String[] msg = { "clase" };
		
		// persiste el modelo
		if (modelo.getId() == null) {
			
			// Obtén el valor de la fecha seleccionada
			String FechaHoraInicio = request.getParameter("fechaHoraInicio");
			if (FechaHoraInicio == ""){
				FechaHoraInicio = sdf.format(new Date());
			}
			String FechaHoraFin = request.getParameter("fechaHoraFin");
			if (FechaHoraFin == ""){
				FechaHoraFin = sdf.format(new Date());
			}
			modelo.setFechaHoraInicio(sdf.parse(FechaHoraInicio));
			modelo.setFechaHoraFin(sdf.parse(FechaHoraFin));
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			
			// Verificar solapamiento
	        System.out.println("Id EMpresa "+ emp.getId() +"clase fehca Inicio "+ modelo.getFechaHoraInicio() + "clase fehca Fin " +modelo.getFechaHoraFin()+"[DEBUG] Verificando solapamiento...");
	        if (claseService.existeSolapamientoHorario(emp.getId(), modelo.getFechaHoraInicio(), modelo.getFechaHoraFin())) {
	            System.out.println("clase fehca Inicio "+ clase.getFechaHoraInicio()+ "clase fehca Fin " +clase.getFechaHoraFin()+"[DEBUG] Solapamiento detectado!");
	            ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, 
	                "Ya tiene una clase programada en ese horario"));
	            return new ModelAndView("redirect:/clases/");
	        } else {
	            System.out.println("[DEBUG] No hay solapamiento");
	        }
	        
			this.claseService.create(modelo);
	        
	        //System.out.println("Envio Correo Destinatarios: " + modelo.getCliente());

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			// Obtén el valor de la fecha seleccionada
			// Obtén el valor de la fecha seleccionada
			String FechaHoraInicio = request.getParameter("fechaHoraInicio");
			if (FechaHoraInicio == ""){
				FechaHoraInicio = sdf.format(new Date());
			}
			String FechaHoraFin = request.getParameter("fechaHoraFin");
			if (FechaHoraFin == ""){
				FechaHoraFin = sdf.format(new Date());
			}
			modelo.setFechaHoraInicio(sdf.parse(FechaHoraInicio));
			modelo.setFechaHoraFin(sdf.parse(FechaHoraFin));
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());
			
			// Verificar solapamiento
	        System.out.println("Id EMpresa "+ emp.getId() +"clase fehca Inicio "+ modelo.getFechaHoraInicio() + "clase fehca Fin " +modelo.getFechaHoraFin()+"[DEBUG] Verificando solapamiento...");
	        if (claseService.existeSolapamientoHorario(emp.getId(), modelo.getFechaHoraInicio(), modelo.getFechaHoraFin())) {
	            System.out.println("clase fehca Inicio "+ clase.getFechaHoraInicio()+ "clase fehca Fin " +clase.getFechaHoraFin()+"[DEBUG] Solapamiento detectado!");
	            ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, 
	                "Ya tiene una clase programada en ese horario"));
	            return new ModelAndView("redirect:/clases/");
	        } else {
	            System.out.println("[DEBUG] No hay solapamiento");
	        }
	        
			this.claseService.update(modelo);
	        //System.out.println("Envio Correo Destinatarios: " + modelo.getCliente());

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("clases/clases", map);
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
			this.claseService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils
							.getDataIntegrityViolationMessage(e,
									this.messageSource)));

			return new ModelAndView("clases/clases", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("clases/clases", map);
	}
	
	/**
	 * Mostrar clases disponibles 
	 * @param authentication
	 * @param id
	 * @return
	 */
    @RequestMapping("/disponibles")
    public ModelAndView mostrarClasesDisponibles(HttpSession session) {
        //System.out.println("Mostrando clases disponibles 1");
        ModelMap map = new ModelMap();
        
        // Obtener empresa del usuario logueado
        String usuarioSesion = SecurityUtils.getCurrentUser();
        
        //System.out.println("Mostrando clases disponibles 2: "+ usuarioSesion);
        Cliente user = clienteService.getIdEmpresaCliente(usuarioSesion);
        
        if (user == null || user.getEmpresa() == null) {
            return new ModelAndView("redirect:/login");
        }
        
        //System.out.println("Mostrando clases disponibles 3: "+ user.getEmpresa().getId());
        // Obtener clases disponibles
        List<Clase> clases = claseService.getClasesDisponiblesByEmp(user.getEmpresa().getId());
        map.put("clases", clases);
        
        // Mantener tu método feedList si es necesario
        //feedList(map); 
        
        return new ModelAndView("clases/clasesDisponibles", map);
    }
    
	/**
	 * Incribir un socio a la clase
	 * @param authentication
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/inscribirSocio", method = RequestMethod.POST)
	public ModelAndView inscribirSocio(
			@Valid @ModelAttribute("inscritosClase") InscritosClaseForm inscritosClase,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra)  {
		
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("inscritosClase", inscritosClase);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			LOG.debug("Errores: {}", result.getAllErrors());
			return new ModelAndView("clases/"+ (inscritosClase.getId() == null ? "add" : "edit"), map);
		}

		// convierte la Forma al Modelo
		InscritosClase modelo = inscritosClase.toOrmModel(InscritosClase.class);
		
		// validaciones de negocio antes de persistir
		this.inscritosClaseService.validateBeforeCreate(modelo, result);
		if (result.hasErrors()) {
			map.put("inscritosClase", inscritosClase);
			feedCatalogs(map, null);
			System.out.println("Existen errores: " + result.getAllErrors());
			LOG.debug("Errores Validaci�n: {}", result.getAllErrors());
			return new ModelAndView("clases/"+ (inscritosClase.getId() == null ? "add" : "edit"), map);
		}
		
		if (modelo.getId() == null) {
			// Obtener empresa del usuario logueado
	        String usuarioSesion = SecurityUtils.getCurrentUser();
	        Cliente cliente = clienteService.getIdEmpresaCliente(usuarioSesion);
			Clase clase = claseService.findOne(inscritosClase.getClase().getId());
			
			// Verificar si ya está inscrito
		    if (inscritosClaseService.existeInscripcion(clase.getId(), cliente.getId())) {
		        ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, 
		            "Ya está inscrito en esta clase"));
		        return new ModelAndView("redirect:/clases/disponibles");
		    }
		    
		    // Verificar solapamiento
	        System.out.println("clase fehca Inicio "+ clase.getFechaHoraInicio()+ "clase fehca Fin " +clase.getFechaHoraFin()+"[DEBUG] Verificando solapamiento...");
	        if (inscritosClaseService.existeSolapamientoHorarioSocio(cliente.getId(), clase.getFechaHoraInicio(), clase.getFechaHoraFin())) {
	            System.out.println("clase fehca Inicio "+ clase.getFechaHoraInicio()+ "clase fehca Fin " +clase.getFechaHoraFin()+"[DEBUG] Solapamiento detectado!");
	            ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, 
	                "Ya tiene una clase programada en ese horario"));
	            return new ModelAndView("redirect:/clases/disponibles");
	        } else {
	            System.out.println("[DEBUG] No hay solapamiento");
	        }

			if (clase.getCapacidadMaxima() > 0) {
			    //Restar capacidad
			    clase.setCapacidadMaxima(clase.getCapacidadMaxima() - 1);
			    
			    //Actualizar clase en BD
			    claseService.update(clase);
			    
			    //Crear la inscripción
				modelo.setCliente(cliente);
				modelo.setFechaInscripcion(new Date());
				modelo.setAsistencia(true);
				
				this.inscritosClaseService.create(modelo);
				
			} else {
			    // Manejar caso cuando no hay cupo
			    ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, "No hay cupos disponibles"));
			}

			ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
					"entity.create.success", null, null)));
		}
		return new ModelAndView("redirect:disponibles");

	}
	
	/**
	 * Eliminar Inscritos Clase
	 * @param authentication
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteInscritosClase", method = RequestMethod.GET)
	public ModelAndView deleteInscritosClase(Authentication authentication, RedirectAttributes ra,
			@RequestParam(required = true) Integer id,
			@RequestParam(required = true) Integer idClase) {
		try {
			this.inscritosClaseService.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			ra.addFlashAttribute("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, "No es posible eliminar el registro."));

			return new ModelAndView("redirect:edit?id="+idClase);
		}
		ra.addFlashAttribute("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage("entity.delete.success", null, null)));
		return new ModelAndView("redirect:edit?id="+idClase);
	}
	
	/**
	 * Realiza la conversión de objetos de los formularios.
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
	private void feedCatalogs(ModelMap map, Integer idClase) {
		String usuarioSesion = SecurityUtils.getCurrentUser();
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		map.put("planes", planService.getPlanesByEmp(user.getEmpresa().getId()));
		map.put("usuarios", usuarioService.getUsuariosActivosByEmp(user.getEmpresa().getId()));
		Empresa emp =  user.getEmpresa();  
		map.put("emp", emp); 
		
		if (idClase != null) {
			map.put("inscritosClase", this.inscritosClaseService.getInscritosDeClase(idClase));
		}
	}
	
	private void feedList(ModelMap map) {
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 map.put("clases", claseService.getClaseByEmp(user.getEmpresa().getId()));
	}

}
