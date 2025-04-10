<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<tiles:insertDefinition name="defaultTemplate">
<fmt:message key="catalogos.empresas" var="entity"/> 

	<tiles:putAttribute name="title">
	<fmt:message key="catalogos.empresas" />
	</tiles:putAttribute>

	<tiles:putAttribute name="body">		
		<div class="row">	
		
		<div class="col-md-12">
				<div class="card">
					<div class="card-header card-header-icon"
						data-background-color="blue">
						<i class="material-icons">domain_add</i>
					</div>
					<div class="card-content">
						<h4 class="card-title">
						<c:if test="${empty empresa.id}"><fmt:message key="labels.forms.newform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						<c:if test="${not empty empresa.id}"><fmt:message key="labels.forms.editform.title"><fmt:param value="${entity}"/></fmt:message></c:if>
						</h4>
						<small><fmt:message key="labels.forms.hint.requiredfields"/></small>
						
						
						<form:form id="add" action="save" method="POST" modelAttribute="empresa" empresa="form" enctype="multipart/form-data">
						
						<div class="form-body">
							<!-- Mensaje de error, validación backend -->
							<spring:hasBindErrors name="empresa">							
							<div class="alert alert-danger" style="display: block;"><button class="close" data-close="alert"></button><fmt:message key="error.form.globalmessage"/></div>
							</spring:hasBindErrors>
							
							<div class="row">
							
								<spring:bind path="empresa.nombre">
									<div class="col-md-4">
										<div class="form-group label-floating ${status.error ? 'has-error' : ''}">						
											<label class="control-label"><fmt:message key="catalogos.empresas.nombre"/> *</label>									
											<form:input path="nombre" maxlength="50" required="true" type="text" class="form-control input upper"/>					
											<form:errors path="nombre" class="help-block"></form:errors>		
										</div>
									</div>
								</spring:bind>
								<spring:bind path="empresa.responsable">
									<div class="col-md-4">
										<div class="form-group label-floating ${status.error ? 'has-error' : ''}">						
											<label class="control-label">Representante Legal *</label>									
											<form:input path="responsable" maxlength="50" required="true" type="text" class="form-control input upper"/>					
											<form:errors path="responsable" class="help-block"></form:errors>		
										</div>
									</div>
								</spring:bind>
							
								<spring:bind path="empresa.nit">
									<div class="col-md-4">
										<div class="form-group label-floating ${status.error ? 'has-error' : ''}">						
											<label class="control-label"><fmt:message key="catalogos.empresas.nit"/> *</label>									
											<form:input path="nit" maxlength="20" required="true" type="text" class="form-control input" />					
											<form:errors path="nit" class="help-block"></form:errors>		
										</div>
									</div>
								</spring:bind>
																
							</div>
							<div class="row">
								<spring:bind path="empresa.correoElectronico">
									<div class="col-md-3">
										<div class="form-group ${status.error ? 'has-error' : ''}">						
											<label class="control-label"><fmt:message key="catalogos.usuarios.correoElectronico"/> *</label>									
											<form:input path="correoElectronico" maxlength="80" required="true" type="email" class="form-control input upper" />					
											<form:errors path="correoElectronico" class="help-block"></form:errors>		
										</div>
									</div>
								</spring:bind>
			
								<spring:bind path="empresa.telefono">
									<div class="col-md-3">
										<div class="form-group ${status.error ? 'has-error' : ''}">
											<label class="control-label">Telefono *</label>											
											<form:input path="telefono" maxlength="13" minlength="10" required="true" type="currency" class="form-control input upper"/>					
											<form:errors path="telefono" class="help-block"></form:errors>											
										</div>
									</div>
								</spring:bind>
								
								<spring:bind path="empresa.rutaImagen">
									<div class="col-md-6" align="center">
										<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
											<label class="control-label">PDF *</label>
											<div class="fileinput fileinput-new text-center" data-provides="fileinput">
												<div class="fileinput-preview fileinput-exists thumbnail"></div>
												<div>
													<span class="btn btn-danger btn-round btn-file"> <span
														class="fileinput-new">Selecciona Imagen</span> <span
														class="fileinput-exists">Cambiar</span> 
														<input type="file" name="imagen" accept="image/*" required />
													</span> 
													<a href="#pablo" class="btn btn-danger btn-round fileinput-exists"
														data-dismiss="fileinput"><i class="fa fa-times"></i>Remover
													</a>
												</div>
											</div>
											<form:errors path="rutaImagen" class="help-block"></form:errors>
										</div>
									</div>
								</spring:bind>
							</div>	

							<h4 class="form-section">Datos Informativos </h4>
							<div class="row">
								
								<div class="col-md-3">
									<div class="form-group">
										<label ><fmt:message key="catalogos.general.fechaAlta"/>: </label>									
										<div ><fmt:formatDate value="${empresa.fechaAlta}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
										<label><fmt:message key="catalogos.general.usuarioAlta"/>: </label>									
										<div><c:out value="${empresa.usuarioAlta}"/></div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
										<label><fmt:message key="catalogos.general.fechaModifica"/>: </label>									
										<div ><fmt:formatDate value="${empresa.fechaModifica}" pattern="yyyy/MM/dd HH:mm:ss"/> </div>
									</div>
								</div>
								
								<div class="col-md-3">
									<div class="form-group">
										<label><fmt:message key="catalogos.general.usuarioModifica"/>: </label>									
										<div><c:out value="${empresa.usuarioModifica}"/></div>
									</div>
								</div>				
							</div>
											
						</div>
						<div class="form-actions">
							<c:if test="${empty empresa.id}">
								<sec:authorize access="hasAnyRole('ALTA_EMPRESAS')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save"/></button>									
								</sec:authorize>
							</c:if>
							<c:if test="${not empty empresa.id}">
								<sec:authorize access="hasAnyRole('EDITA_EMPRESAS')">
									<button type="submit" class="btn btn-success"><fmt:message key="labels.buttons.save.changes"/></button>									
								</sec:authorize>
							</c:if>
							
							<a id="cancelar" href="<c:url value="/empresas/"/>" type="button" class="btn btn-default"><fmt:message key="labels.buttons.cancel"/></a>
						</div>
						
						<form:hidden path="id"/>
					</form:form>

            	</div>
        	</div>
        </div>

	</div>
	
	</tiles:putAttribute>

	<tiles:putAttribute name="ready"> 
	
	$('#empresas').addClass("collapse in");
	$('#empresas_op').addClass("active");
	
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
