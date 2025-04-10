/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

import com.controlgymfit.scgf.controller.beans.PlanForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.Select;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Plan;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.PlanService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.enums.TipoEntrenamiento;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;

@Controller
@RequestMapping("planes")
public class PlanesController {

	@Autowired
	MessageSource messageSource;
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
		
		System.out.println("Planes index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("planes/planes", map);
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
		map.put("plan", new Plan());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("planes/altaEditaPlan", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Plan plan = this.planService.findOne(id);
			
		if(plan==null){
			return new ModelAndView("Planes/");	
		}
						
		ModelMap map = new ModelMap();
		PlanForm forma = (new PlanForm()).fromOrmModel(plan, PlanForm.class);
		map.put("plan",forma);
		feedCatalogs(map);
				
		return new ModelAndView("planes/altaEditaPlan", map);		
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
			@Valid @ModelAttribute("plan") PlanForm plan,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {

		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("plan", plan);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("planes/altaEditaPlan", map);
		}

		// convierte la Forma al Modelo
		Plan modelo = plan.toOrmModel(Plan.class);

		// validaciones de negocio antes de persistir
		this.planService.validateBeforeCreate(modelo, result);
		
		if (result.hasErrors()) {
			map.put("plan", plan);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("planes/altaEditaPlan", map);
		}

		String[] msg = { "plan" };
		
		modelo.setUsuarioAlta(usuarioSesion);
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		// persiste el modelo
		if (modelo.getId() == null) {
			

			modelo.setEmpresa(user.getEmpresa());
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			this.planService.create(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			modelo.setEmpresa(user.getEmpresa());
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.planService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("planes/planes", map);
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
			this.planService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils.getDataIntegrityViolationMessage(e,this.messageSource)));

			return new ModelAndView("planes/planes", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("planes/planes", map);
	}
	
	/**
	 * Trae el detalle del plan.
	 * @return Vista de mensaje informativo 
	 */
	@RequestMapping(value = "/getPlanDetails", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Select> getPlanDetails(@RequestParam("idPlan") Integer idPlan) {
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
	 *            mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map) {

		map.put("tipoEntre", TipoEntrenamiento.getTipos());	
		
	}
	
	private void feedList(ModelMap map) {
		 map.put("allPlanes",planService.findAll());
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 map.put("planes",planService.getPlanesByEmp(user.getEmpresa().getId()));
		 map.put("planess",planService.findAll());

	}

	
}
