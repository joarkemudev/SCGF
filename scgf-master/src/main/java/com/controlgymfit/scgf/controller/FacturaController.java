/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.text.DecimalFormat;
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

import com.controlgymfit.scgf.controller.beans.FacturaForm;
import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.controller.beans.Select;
import com.controlgymfit.scgf.controller.beans.generic.FechaEditor;
import com.controlgymfit.scgf.modelo.entidad.Factura;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.FacturaService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.util.ExceptionUtils;
import com.controlgymfit.scgf.util.SecurityUtils;

@Controller
@RequestMapping("facturas")
public class FacturaController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private FacturaService facturaService;
	@Autowired
	private UsuarioService usuarioService;
	
	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/")
	public ModelAndView index() {
		
		System.out.println("Facturas index");
		ModelMap map = new ModelMap();
		feedList(map);
		return new ModelAndView("facturas/facturas", map);
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
		map.put("factura", new Factura());
		feedCatalogs(map);

		System.out.println("Entra a add");
		return new ModelAndView("facturas/altaEditaFactura", map);
	
	}
	
	/**
	 * Editar un elemento del catálogo
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required=true) Integer id)
	{		
		Factura factura = this.facturaService.findOne(id);
			
		if(factura==null){
			return new ModelAndView("Facturas/");	
		}
						
		ModelMap map = new ModelMap();
		FacturaForm forma = (new FacturaForm()).fromOrmModel(factura, FacturaForm.class);
		map.put("factura",forma);
		feedCatalogs(map);
				
		return new ModelAndView("facturas/altaEditaFactura", map);		
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
			@Valid @ModelAttribute("factura") FacturaForm factura,
			BindingResult result, HttpServletRequest request,
			RedirectAttributes ra) throws ParseException {

		String usuarioSesion = SecurityUtils.getCurrentUser();
		ModelMap map = new ModelMap();

		// revisa validaciones simples
		if (result.hasErrors()) {
			map.put("Factura", factura);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("facturas/altaEditaFactura", map);
		}

		// convierte la Forma al Modelo
		Factura modelo = factura.toOrmModel(Factura.class);

		// validaciones de negocio antes de persistir
		this.facturaService.validateBeforeCreate(modelo, result);
		
		if (result.hasErrors()) {
			map.put("factura", factura);
			feedCatalogs(map);
			System.out.println("Existen errores: " + result.getAllErrors());
			return new ModelAndView("facturas/altaEditaFactura", map);
		}

		String[] msg = { "factura" };
		
		modelo.setUsuarioAlta(usuarioSesion);
		Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		// persiste el modelo
		if (modelo.getId() == null) {
			

			modelo.setIdEmpresa(user.getEmpresa().getId());
			modelo.setFechaAlta(new Date());
			System.out.println(modelo.toString());
			this.facturaService.create(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.create.success", msg, null)));
		} else {
			modelo.setIdEmpresa(user.getEmpresa().getId());
			modelo.setUsuarioModifica(usuarioSesion);
			modelo.setFechaModifica(new Date());

			System.out.println(modelo.toString());
			this.facturaService.update(modelo);

			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
							"entity.update.success", msg, null)));
		}

		// return new ModelAndView("redirect:list");
		feedList(map);
		return new ModelAndView("facturas/facturas", map);
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
			this.facturaService.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			ModelMap map = new ModelMap();
			feedList(map);
			map.put("mensaje",
					new Mensaje(Mensaje.TIPO_ERROR, ExceptionUtils.getDataIntegrityViolationMessage(e,this.messageSource)));

			return new ModelAndView("facturas/facturas", map);
		}

		ModelMap map = new ModelMap();
		feedList(map);
		map.put("mensaje",
				new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage(
						"entity.delete.success", null, null)));

		return new ModelAndView("facturas/facturas", map);
	}
	
	/**
	 * Trae el detalle del plan.
	 * @return Vista de mensaje informativo 
	 */
	@RequestMapping(value = "/getFactDetails", method = RequestMethod.GET)
	public @ResponseBody ArrayList<Select> getFactDetails(@RequestParam("id") Integer idCliente) {
	    ArrayList<Select> array = new ArrayList<>();
	    
		//Obtiene solo las cuentas activas.
		for(Factura fact : facturaService.getFacturas(idCliente)) {
			Select sel = new Select();
			sel.setValue(fact.getId().toString());
			sel.setLabel(fact.getDetalle().toString());
			DecimalFormat formatoMoneda = new DecimalFormat("#,##0");
			String montoFormateado = formatoMoneda.format(fact.getTotal()-fact.getMontoPagado());
			sel.setValue1(montoFormateado.toString());
			String totalFormateado = formatoMoneda.format(fact.getTotal());
			sel.setValue2(totalFormateado.toString());

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
	 * mapa de atributos del formulario
	 */
	private void feedCatalogs(ModelMap map) {
		//map.put("tipoIdenti", TipoIdentificacion.getTipos());		
	}
	
	private void feedList(ModelMap map) {
		 map.put("allFacturas",facturaService.findAll());
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
		 map.put("facturas",facturaService.getFacturasByEmp(user.getEmpresa().getId()));
		 map.put("facturass",facturaService.findAll());

	}

	
}
