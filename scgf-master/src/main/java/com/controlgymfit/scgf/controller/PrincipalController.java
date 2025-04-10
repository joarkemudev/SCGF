/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.controlgymfit.scgf.modelo.entidad.Parametros;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.service.ParametrosService;
import com.controlgymfit.scgf.util.SecurityUtils;
import com.controlgymfit.scgf.util.SessionListener;

@Controller
public class PrincipalController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	private ParametrosService parametrosService; 
	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private ServletContext context;

	/**
	 * Metodo para inicializar el proceso de recuperación de contraseña.
	 */
	@ResponseBody
	@RequestMapping("/principal")
	public ModelAndView principal() {

		ModelMap map = new ModelMap();

		feedList(map);
		return new ModelAndView("inicio", map);
	}
	
	/**
	 * Metodo para mostrar vista de cancelaciones:
	 */
	@ResponseBody
	@RequestMapping("/cancelaciones")
	public ModelAndView cancelaciones() {

		return new ModelAndView("cancelaciones");
	}
	
	/**
	 * Realiza la conversión de objetos de los formularios.
	 * 
	 * @param binder
	 */
	@InitBinder
	void initBinder(WebDataBinder binder) {
		//binder.registerCustomEditor(Rol.class, new GenericModelEditor<Rol>(this.rolService));
	}

	private void feedList(ModelMap map) {
		 String usuarioSesion = SecurityUtils.getCurrentUser();
		 System.out.println("principal 1  "+ usuarioSesion);
		 //Usuario user = this.usuarioService.getIdEmpesaUsuario(usuarioSesion);
	}

	@ResponseBody
	@RequestMapping("/saveFile")
	public ModelAndView saveFile(){
			File file = new File("/mnt/disks/data/archivo.txt");
			String fileData = "Hola mundo, este es mi contenido.";
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(file);
				fos.write(fileData.getBytes());
				fos.flush();
				fos.close();
			} catch (IOException e) {				
				e.printStackTrace();
			}
			
	      ModelMap map = new ModelMap();
	      feedList(map);
	      return new ModelAndView("inicio", map);
	}

	
	/**
	 * Obtiene el estado del sistema
	 */
	@RequestMapping(value = "/getEstadoSistema", method = RequestMethod.GET)
	public  @ResponseBody Boolean getEstadoSistema() {
		
		Parametros param = parametrosService.findAll().get(0);		
		return param.getBloqueo();
		//return "\""+param.getBloqueo()+"\"";
	}
	
	/**
	 * Cambia el estado del sistema
	 */
	@RequestMapping(value = "/cambiaEstadoSistema", method = RequestMethod.GET)
	public  @ResponseBody Boolean cambiaEstadoSistema(@RequestParam String estado) {
		
		
		Parametros param = parametrosService.findAll().get(0);
		if(estado.equals("INACTIVO")) {
			param.setBloqueo(true);
		}else {
			param.setBloqueo(false);
		}
		parametrosService.update(param);
		SessionListener.getInstance().invalidaSessiones();
		return true;
	}
	
}
