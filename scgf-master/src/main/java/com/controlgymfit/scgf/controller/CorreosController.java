/**
 *
 * @author Joarkemu
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.SimpleDateFormat;
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

import com.controlgymfit.scgf.controller.beans.CorreoForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.controller.beans.generic.GenericModelEditor;
import com.controlgymfit.scgf.modelo.entidad.Correo;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.service.CorreoService;
import com.controlgymfit.scgf.service.EmpresaService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.TipoActividad;

@Controller
@RequestMapping("correos")
public class CorreosController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private CorreoService correoService;
	@Autowired
	private EmpresaService empresaService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {

		System.out.println("correo index");
		System.out.println("Entra a controller");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("correos/correos", map);
	}
	
	/**
	 * Agregar un elemento al catálogo
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(Authentication authentication) {

		ModelMap map = new ModelMap();
		map.put("correo", new Correo());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("correos/altaEditaCorreo", map);
	
	}

	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Correo correo = this.correoService.findOne(id);
			
		if(correo==null){
			return new ModelAndView("correos/");	
		}
						
		ModelMap map = new ModelMap();
		CorreoForm forma = (new CorreoForm()).fromOrmModel(correo, CorreoForm.class);
		map.put("correo",forma);
		feedCatalogs(map);
				
		return new ModelAndView("correos/altaEditaCorreo", map);		
	}	
	
	
	/**
	 * Salvar/Actualizar un elemento del catálogo
	 * @param usuario
	 * @param result
	 * @param request
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(
			@Valid @ModelAttribute("correo") CorreoForm correo,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) {
	
		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("correo", correo);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("correos/altaEditaCorreo", map);
		}

		// convierte la Forma al Modelo
		Correo modelo = correo.toOrmModel(Correo.class);

		// validaciones de negocio antes de persistir
		this.correoService.validateBeforeCreate(modelo, result);
		
		if (result.hasErrors()) {
			map.put("correo", correo);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			
			return new ModelAndView("correos/altaEditaCorreo", map);
		}

		String[] msg = { "Correo" };
		// persiste el modelo
		if (modelo.getId() == null) {
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			this.correoService.create(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.correoService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("correos/correos", map);
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
			this.correoService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils
							.getDataIntegrityViolationMessage(e,
									this.messageSource)));

			return new ModelAndView("correos/correos", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("configuracion/correos/correos", map);
	}

	/**
	 * Realiza la conversión de objetos de los formularios.
	 * 
	 * @param binder
	 */
	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Empresa.class, new GenericModelEditor<Empresa>(
				this.empresaService));
		SimpleDateFormat sdf = new SimpleDateFormat();
		binder.registerCustomEditor(Date.class, new FechaEditor(sdf));
	}

	private void feedList(ModelMap map) {

	 map.put("correos",correoService.findAll());
	}

	/**
	 * Provee catálogos requeridos en el formulario
	 * @param map mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map) {

		map.put("tipoActividades", TipoActividad.getTipos());
	}

}
