/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.UsuarioForm;
import com.controlgymfit.scgf.controller.beans.generic.GenericModelEditor;
import com.controlgymfit.scgf.modelo.entidad.Rol;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.RolService;
import com.controlgymfit.scgf.service.EmpresaService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.Encripta;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.enums.AccesoUsuario;
import com.controlgymfit.scgf.util.enums.EstadoCatalogo;

@Controller
@RequestMapping("usuarios")
public class UsuarioController {

	private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	MessageSource messageSource;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private RolService rolService;
	@Autowired
	private EmpresaService empresaService;

	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {

		System.out.println("usuarios index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("usuarios/usuarios", map);
	}

	/**
	 * Agregar un elemento al catálogo
	 * 
	 * @param authentication
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(RedirectAttributes ra) {
		
		if(!SecurityUtils.hasCurrentUserRole("ALTA_USUARIOS")){
			ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, messageSource.getMessage(
					"access.wrong", null, null)));
			return new ModelAndView("redirect:../principal");
		}
		
		ModelMap map = new ModelMap();
		map.put("usuario", new UsuarioForm(EstadoCatalogo.ACTIVO));
		feedCatalogs(map);

		return new ModelAndView("usuarios/altaEditaUsuario", map);
	}

	/**
	 * Editar un elemento del catálogo
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = true) Integer id) {
		Usuario usuario = this.usuarioService.findOne(id);
		if (usuario == null) {
			return new ModelAndView("usuarios/");
		}

		UsuarioForm forma = (new UsuarioForm()).fromOrmModel(usuario,
				UsuarioForm.class);
		forma.setClaveAcceso2(forma.getClaveAcceso());

		ModelMap map = new ModelMap();
		map.put("usuario", forma);
		feedCatalogs(map);

		return new ModelAndView("usuarios/altaEditaUsuario", map);
	}

	/**
	 * Salvar/Actualizar un elemento del catálogo
	 * 
	 * @param usuario
	 * @param result
	 * @param request
	 * @param ra
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(
			@Valid @ModelAttribute("usuario") UsuarioForm usuario,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) {
		
		
		System.out.println("Enable login: " + LOG.isErrorEnabled());
		String usuarioSesion = SecurityUtils.getCurrentUser();

		LOG.debug("Updating usuario {}.", usuario.getId());
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("usuario", usuario);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			LOG.debug("Errores: {}", result.getAllErrors());
			return new ModelAndView("usuarios/altaEditaUsuario", map);
		}

		// convierte la Forma al Modelo
		Usuario modelo = usuario.toOrmModel(Usuario.class);

		// validaciones de negocio antes de persistir
		this.usuarioService.validateBeforeCreate(modelo, result);
		if (result.hasErrors()) {
			map.put("usuario", usuario);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			LOG.debug("Errores Validación: {}", result.getAllErrors());
			return new ModelAndView("usuarios/altaEditaUsuario", map);
		}

		String[] msg = { "Usuario" };
		// persiste el modelo
		if (modelo.getId() == null) {
			modelo.setCambioClave(true);
			modelo.setClaveAcceso(Encripta.encripta(modelo.getClaveAcceso()));
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());

			this.usuarioService.create(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null) +" Favor de agregar Cotizaciones y contratos."));
		} else {
			Usuario usuarioActual = usuarioService.findOne(modelo.getId());

			if (!usuarioActual.getClaveAcceso().equals(modelo.getClaveAcceso()))
				modelo.setClaveAcceso(Encripta.encripta(modelo.getClaveAcceso())); // Actualiza clave.
			modelo.setCambioClave(true);
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());
			this.usuarioService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("usuarios/usuarios", map);
	}
	
	/**
	 * Cambiar la clave de un usuario
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/cambio", method = RequestMethod.GET)
	public ModelAndView cambio(@RequestParam(required = true) Integer id) {
		Usuario usuario = this.usuarioService.findOne(id);
		if (usuario == null) {
			return new ModelAndView("usuarios/");
		}

		UsuarioForm forma = (new UsuarioForm()).fromOrmModel(usuario,UsuarioForm.class);
		forma.setClaveAcceso2(forma.getClaveAcceso());

		ModelMap map = new ModelMap();
		map.put("usuario", forma);
		feedCatalogs(map);

		return new ModelAndView("usuarios/cambioUsuario", map);
	}
	/**
	 * Eliminar un elemento del catálogo
	 * 
	 * @param authentication
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(Authentication authentication,
			@RequestParam(required = true) Integer id) {
		try {
			this.usuarioService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, "No es posible eliminar al usuario por tener Solicitudes o Partidas Asociadas."));

			return new ModelAndView("usuarios/usuarios", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("usuarios/usuarios", map);
	}
	
	
	/**
	 * Realiza la conversión de objetos de los formularios.
	 * 
	 * @param binder
	 */
	@InitBinder
	void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Rol.class, new GenericModelEditor<Rol>(this.rolService));
	}

	private void feedList(ModelMap map) {
		map.put("usuarios", usuarioService.findAll());
	}

	/**
	 * Provee catálogos requeridos en el formulario
	 * 
	 * @param map mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map) {
		map.put("usuarios", usuarioService.getActivos());
		map.put("empresas", empresaService.getAllOrdenados());
		map.put("roles", rolService.getAllOrdenados());
		map.put("accesos", AccesoUsuario.getAccesos());
		
	}

}
