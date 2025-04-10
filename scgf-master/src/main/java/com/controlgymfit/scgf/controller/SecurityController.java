/**
 * 
 * CONTROLGYM
 * @author Sistemas
 *
 */
package com.controlgymfit.scgf.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.controlgymfit.scgf.controller.beans.Mensaje;
import com.controlgymfit.scgf.modelo.entidad.Parametros;
import com.controlgymfit.scgf.modelo.entidad.Usuario;
import com.controlgymfit.scgf.modelo.entidad.Cliente;
import com.controlgymfit.scgf.modelo.entidad.Empresa;
import com.controlgymfit.scgf.service.ParametrosService;
import com.controlgymfit.scgf.service.UsuarioService;
import com.controlgymfit.scgf.service.ClienteService;

/**
 * Controller encargado del login.
 */
@Controller
public class SecurityController {

    @Autowired
    private ParametrosService parametrosService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model, Principal principal) {
        return "login";
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public ModelAndView init(HttpSession session, Principal principal, Authentication authentication) {
    	System.out.println("Método welcome ejecutado2");
    	
    	String userName = principal.getName();
        
		boolean isSocio = authentication.getAuthorities().stream()
				.anyMatch(grantedAuth -> grantedAuth.getAuthority().equals("ROLE_SOCIO"));
		
		if (isSocio) {
			Cliente socio = clienteService.getIdEmpresaCliente(userName);
			Empresa empresa = socio.getEmpresa();
			
			if (empresa != null) {
		        String logoPath = "/assets/img/" + empresa.getRutaImagen(); // imageBasePath debería inyectarse
		        session.setAttribute("empresaLogo", logoPath);
		        session.setAttribute("idUsuario", socio.getId());
		        session.setAttribute("empresaNombre", empresa.getNombre());
		        session.setAttribute("tipoUsuario", "socio");
		  
		        //logger.info("Usuario administrativo ingresando: {}, Empresa: {}", 
		        //   userName, empresa.getNombre());
			}
		      
		} else {
		    // Manejo de administradores
			Usuario user = usuarioService.getUsuarioPorNombreAcceso(userName);
			Empresa empresa = user.getEmpresa();
		  
			if (empresa != null) {
		        String logoPath = "/assets/img/" + empresa.getRutaImagen(); // imageBasePath debería inyectarse
		        session.setAttribute("empresaLogo", logoPath);
		        session.setAttribute("idUsuario", user.getId());
		        session.setAttribute("empresaNombre", empresa.getNombre());
		        session.setAttribute("tipoUsuario", "admin");
		  
		        //logger.info("Usuario administrativo ingresando: {}, Empresa: {}", 
		        //   userName, empresa.getNombre());
			}
		}
   
        Parametros param = parametrosService.findAll().get(0);
        if (param.getBloqueo() != null && param.getBloqueo()) {
            // Implementar lógica si es necesario   
        }
        return new ModelAndView("redirect:principal");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView welcome(HttpSession session, Principal principal, Authentication authentication) {
        System.out.println("Método welcome ejecutado");

    	String userName = principal.getName();
        
		boolean isSocio = authentication.getAuthorities().stream()
				.anyMatch(grantedAuth -> grantedAuth.getAuthority().equals("ROLE_SOCIO"));

		if (isSocio) {
			Cliente socio = clienteService.getIdEmpresaCliente(userName);
			Empresa empresa = socio.getEmpresa();
			
			if (empresa != null) {
		        String logoPath = "/assets/img/" + empresa.getRutaImagen(); // imageBasePath debería inyectarse
		        session.setAttribute("empresaLogo", logoPath);
		        session.setAttribute("idUsuario", socio.getId());
		        session.setAttribute("empresaNombre", empresa.getNombre());
		        session.setAttribute("tipoUsuario", "socio");
		  
		        //logger.info("Usuario administrativo ingresando: {}, Empresa: {}", 
		        //   userName, empresa.getNombre());
			}
		      
		} else {
		    // Manejo de administradores
			Usuario user = usuarioService.getUsuarioPorNombreAcceso(userName);
			Empresa empresa = user.getEmpresa();
		  
			if (empresa != null) {
		        String logoPath = "/assets/img/" + empresa.getRutaImagen(); // imageBasePath debería inyectarse
		        session.setAttribute("empresaLogo", logoPath);
		        session.setAttribute("idUsuario", user.getId());
		        session.setAttribute("empresaNombre", empresa.getNombre());
		        session.setAttribute("tipoUsuario", "admin");
		  
		        //logger.info("Usuario administrativo ingresando: {}, Empresa: {}", 
		        //   userName, empresa.getNombre());
			}
		}

        return new ModelAndView("redirect:init");
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(RedirectAttributes ra) {
        System.out.println("Cierre de sesión...");
        ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_SUCCESS, messageSource.getMessage("login.cierreSesion", null, null)));
        return "redirect:init";
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public String loginFailed(RedirectAttributes ra) {
        System.out.println("Login Failed...");
        ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, messageSource.getMessage("login.accesoDenegado", null, null)));
        return "redirect:/login";
    }

    @RequestMapping(value = "/denegado", method = RequestMethod.GET)
    public String accessDenied(ModelMap map, RedirectAttributes ra) {
        System.out.println("Acceso denegado...");
        ra.addFlashAttribute("mensaje", new Mensaje(Mensaje.TIPO_ERROR, messageSource.getMessage("login.accesoDenegado", null, null)));
        return "redirect:/login";
    }

    @RequestMapping(value = "/lock", method = RequestMethod.GET)
    public String lock() {
        return "lock";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound() {
        return "errors/404";
    }
}


//    /**
//     * Método de entrada al sistema para usuarios y socios
//     */
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ModelAndView welcome(HttpSession session, Principal principal, Authentication authentication) {
//        if (principal == null) {
//            return new ModelAndView("redirect:/login");
//        }
//
//        String userName = principal.getName();
//        //String userId = principal.getClass();
//        
//        boolean isSocio = authentication.getAuthorities().stream()
//            .anyMatch(grantedAuth -> grantedAuth.getAuthority().equals("ROLE_SOCIO"));
//        
//        if (isSocio) {
//            // Manejo de socios
//            try {
//                Integer idCl = Integer.valueOf(userName);
//                Cliente socio = clienteService.getClientes(idCl);
//                System.out.println("principal 0  "+ idCl);
//                //logger.info("Socio ingresando: {}", socio);
//                
//                // Establecer atributos de sesión si son necesarios
//                // session.setAttribute(...);
//                
//            } catch (NumberFormatException e) {
//                //logger.error("Formato de ID de socio inválido: {}", userName);
//                return new ModelAndView("redirect:/login?error=invalid_id");
//            }
//        } else {
//            // Manejo de administradores
//            Usuario user = usuarioService.getUsuarioPorNombreAcceso(userName);
//            Empresa empresa = user.getEmpresa();
//            
//            if (empresa != null) {
//                String logoPath = "/assets/img/" + empresa.getRutaImagen(); // imageBasePath debería inyectarse
//                session.setAttribute("empresaLogo", logoPath);
//                session.setAttribute("idUsuario", user.getId());
//                session.setAttribute("empresaNombre", empresa.getNombre());
//                session.setAttribute("tipoUsuario", "admin");
//                
//                //logger.info("Usuario administrativo ingresando: {}, Empresa: {}", 
//                //   userName, empresa.getNombre());
//            }
//        }
//        
//        return new ModelAndView("redirect:/init");
//    }
