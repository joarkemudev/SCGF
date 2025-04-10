<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.usuarios" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="cambio" />
	</tiles:putAttribute>

	
<tiles:putAttribute name="body">
	<%@include file="../secciones/messages.jsp" %>
		<div class="row">
			<div class="col-md-10 col-md-offset-1">

				<div class="portlet box green-haze">
					<div class="portlet-title">
						<div class="caption">
							<i class="fa fa-edit"></i>
							Cambio de contraseña.
							<small><fmt:message key="labels.forms.hint.requiredfields" /></small>
						</div>
						<div class="tools"><a href="" class="collapse"></a>
						
							<div class="portlet-body form">
								<form:form id="add" action="save" method="POST" modelAttribute="usuario" usuario="form" >
									<div class="form-body">
										<spring:hasBindErrors name="usuario">
											<div class="alert alert-danger" style="display: block;">
											<button class="close" data-close="alert"></button>
											<fmt:message key="error.form.globalmessage" />
											</div>
										</spring:hasBindErrors>
										
										<spring:bind path="usuario.nombreAcceso">
											<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
												<label class="control-label"><fmt:message key="catalogos.usuarios.nombreAcceso" /> *</label>
												<form:input path="nombreAcceso" maxlength="20" required="true" type="text" class="form-control input"   readonly="${empty usuario.id?'false':'true'}"/>					
												<form:errors path="nombreAcceso" class="help-block"></form:errors>
											</div>
										</spring:bind>
										
										<spring:bind path="usuario.estado">
											<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
												<label class="control-label"><fmt:message key="catalogos.usuarios.nombreAcceso" /> *</label>
												<form:select path="estado" required="true" multiple="false" class="select2-container form-control select2me">
													<form:options items="${estados}"/>
												</form:select>
												<form:errors path="estado" class="help-block"></form:errors>
											</div>
										</spring:bind>
										
										<spring:bind path="usuario.claveAcceso">
											<div class="form-group ${status.error ? 'has-error' : ''}">
												<label><fmt:message key="cambioClave.claveAccesoActual" /></label>
												<form:input path="claveAcceso" maxlength="50" required="true"
												type="password" class="form-control input upper" placeholder="actual" />
												<form:errors path="claveAcceso" class="help-block"></form:errors>
											</div>
										</spring:bind>
										
										<spring:bind path="usuario.claveAcceso2">
											<div class="form-group ${status.error ? 'has-error' : ''}">
												<label><fmt:message key="cambioClave.claveAccesoNueva2" /></label>
												<form:input path="claveAcceso2" maxlength="50" required="true" equalTo="#claveAcceso"
												type="password" class="form-control input upper" placeholder="confirmación" />
												<form:errors path="claveAcceso2" class="help-block"></form:errors>
											</div>
										</spring:bind>
										
										<h4 class="form-section">Datos Informativos </h4>
										<div class="row">
											
											<div class="col-md-3">
												<div class="form-group">
												<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
												<div ><fmt:formatDate value="${usuario.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
												</div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
												<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
												<div><c:out value="${usuario.usuarioAlta}"/></div>
												</div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
												<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
												<div ><fmt:formatDate value="${usuario.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
												</div>
											</div>
											
											<div class="col-md-3">
												<div class="form-group">
												<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
												<div><c:out value="${usuario.usuarioModifica}"/></div>
												</div>
											</div>				
										</div>
										
										<form:hidden path="id" />
										<form:hidden path="nombre" />
										<form:hidden path="rol" />
										<form:hidden path="correoElectronico" />
										<div class="form-actions">
										<button type="submit" class="btn green"><fmt:message key="labels.buttons.save"/></button>									
										<a href="<c:url value="/principal"/>" type="button" class="btn default"><fmt:message key="labels.buttons.cancel"/></a>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
	</tiles:putAttribute>

		
	<tiles:putAttribute name="ready"> 
	
	$('#usuarios').addClass("collapse in");
	$('#usuarios_op').addClass("active");
	
	
	$('#add').validate({
		 lang: 'es',
       submitHandler: function(form) {
          guardar(form);
        }
	});
	
	$('#cancelar').click(function(e) {
      e.preventDefault(); // Prevent the href from redirecting directly
      var linkURL = $(this).attr("href");
      cancelar(linkURL);
    });
	
	</tiles:putAttribute>
	
</tiles:insertDefinition>