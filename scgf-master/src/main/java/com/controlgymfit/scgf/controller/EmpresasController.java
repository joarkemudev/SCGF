/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.controlgymfit.scgf.controller.beans.EmpresaForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.service.EmpresaService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;

@Controller
@RequestMapping("empresas")
public class EmpresasController {
	
	private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

	@Autowired
	MessageSource messageSource;
	@Autowired
	private EmpresaService empresaService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {
		
		System.out.println("Empresas index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("empresas/empresas", map);
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
		map.put("empresa", new Empresa());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("empresas/altaEditaEmpresa", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Empresa empresa = this.empresaService.findOne(id);
			
		if(empresa==null){
			return new ModelAndView("empresas/");	
		}
						
		ModelMap map = new ModelMap();
		EmpresaForm forma = (new EmpresaForm()).fromOrmModel(empresa, EmpresaForm.class);
		map.put("empresa",forma);
		feedCatalogs(map);
				
		return new ModelAndView("empresas/altaEditaEmpresa", map);		
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
			@Valid @ModelAttribute("empresa") EmpresaForm empresa,
			BindingResult result, HttpServletRequest request,
			@RequestParam("imagen") MultipartFile imagen, //agregar parametro para el archivo
			RedirectAttributes ra) throws ParseException {

		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("empresa", empresa);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("empresas/altaEditaEmpresa", map);
		}

		
		// convierte la Forma al Modelo
		Empresa modelo = empresa.toOrmModel(Empresa.class);

		// validaciones de negocio antes de persistir
		this.empresaService.validateBeforeCreate(modelo, result);
		
		if (result.hasErrors()) {
			map.put("empresa", empresa);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			LOG.debug("Errores Validación: {}", result.getAllErrors());
			return new ModelAndView("empresas/altaEditaEmpresa", map);
		}
		
		if (!imagen.isEmpty()) {
			try {
				String rutaDirectorio = "C:/Users/SISTEMAS/Desktop/SCGFIT/build/scgf-master/src/main/webapp/assets/img";
				String nombreArchivo = imagen.getOriginalFilename();
				File archivoGuardado = new File(rutaDirectorio, nombreArchivo);
				
				imagen.transferTo(archivoGuardado);
				modelo.setRutaImagen(nombreArchivo);
			} catch (Exception e) {
				System.out.println("Error al guardar la imagen"+ e.getMessage());
				map.put("mensaje", new Mensaje(Mensaje.TIPO_ERROR, "Error al guardar imagen"));
				return new ModelAndView("empresas/altaEditaEmpresa", map);
			}
			
		}

		String[] msg = { "empresa" };
		// persiste el modelo
		if (modelo.getId() == null) {
			
			
			modelo.setUsuarioAlta(usuarioSesion);
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			this.empresaService.create(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.empresaService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		feedList(map);
		return new ModelAndView("empresas/empresas", map);
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
			this.empresaService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils.getDataIntegrityViolationMessage(e,this.messageSource)));

			return new ModelAndView("empresas/empresas", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("empresas/empresas", map);
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
	private void feedCatalogs(ModelMap map) {

		//map.put("tipoActividades", TipoActividad.getTipos());	
		
	}
	
	private void feedList(ModelMap map) {
		 map.put("empresas",empresaService.findAll());
	}

}
