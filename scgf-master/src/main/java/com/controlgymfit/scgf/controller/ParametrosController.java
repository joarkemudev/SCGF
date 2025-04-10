/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.ParametrosForm;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Parametros;
import com.controlgymfit.scgf.service.ParametrosService;
import com.controlgymfit.scgf.util.SecurityUtils;

@Controller
@RequestMapping("parametros")
public class ParametrosController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private ParametrosService parametrosService;

	/**
	 * Index.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {

		System.out.println("Entra a parametros.");
		ModelMap map = new ModelMap();
		feedCatalogs(map);
		return new ModelAndView("configuracion/parametrosSis/parametros", map);
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
	public ModelAndView save(@Valid @ModelAttribute("parametros") ParametrosForm parametros, BindingResult result,
			HttpServletRequest request, RedirectAttributes ra) {

		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("parametros", parametros);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());

			return new ModelAndView("configuracion/parametros/parametros", map);
		}

		// convierte la Forma al Modelo
		Parametros modelo = parametros.toOrmModel(Parametros.class);

		String[] msg = { "parametros" };
		// persiste el modelo
		if (modelo.getId() == null) {			
			System.out.println(modelo.toString());
			this.parametrosService.create(modelo);
			ra.addFlashAttribute("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage("entity.create.success", msg, null)));
		} else {

			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.parametrosService.update(modelo);

			ra.addFlashAttribute("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage("entity.update.success", msg, null)));
		}
		return new ModelAndView("redirect:/parametros/");
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
	private void feedCatalogs(ModelMap map) {
		//map.put("opcionesTipoAcceso", AccesoArchivos.getAccesos());
		map.put("parametros", parametrosService.findOne(1));
	}

}
