<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="agregaFacturaModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-pq mx-5">

        <!-- Modal content -->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Alta de Factura del Socio</h4>
            </div>

            <form:form id="frmFactura" name="frmFactura" action="saveFactura" method="POST" 
                modelAttribute="factura" enctype="multipart/form-data">
                <div class="modal-body">
                    <small><fmt:message key="labels.forms.hint.requiredfields" /></small>
                    <div class="form-body">
                        <!-- Mensaje de error, validación backend -->
                        <spring:hasBindErrors name="factura">
                            <div class="alert alert-danger">
                                <button class="close" data-close="alert"></button>
                                <fmt:message key="error.form.globalmessage" />
                            </div>
                        </spring:hasBindErrors>
                        
                        <div class="row">
                            <!-- ID -->
                            <spring:bind path="factura.id">
                                <div class="col-md-12">
                                    <div class="form-group label-floating ${status.error ? 'has-error' : ''}">
                                        <label class="control-label">ID de Factura *</label>
                                        <form:input path="id" maxlength="80" required="true" type="text" 
                                            class="form-control input upper" />
                                        <form:errors path="id" class="help-block"></form:errors>
                                    </div>
                                </div>
                            </spring:bind>
                            <spring:bind path="factura.subTotal">
								<div class="col-md-3">
									<div
										class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label"><fmt:message
												key="partidas.subTotal" />*</label>
										<form:input id="imp" path="subTotal" required="true"
											type="number" class="form-control input upper"
											readonly="true" disabled="" />
										<form:errors path="subTotal" class="help-block"></form:errors>
										<div id="mensajeI" style="color: red;"></div>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="factura.iva">
								<div class="col-md-3">
									<div
										class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label"> *</label>
										<form:select path="iva" required="true"
											multiple="false"
											class="select2-container form-control select2me">
											<form:option value=""></form:option>
											<form:option value="16">16%</form:option>
											<form:option value="0">0%</form:option>
											<form:option value="0">EXENTO</form:option>

										</form:select>
										<form:errors path="iva" class="help-block"></form:errors>
										<div id="mensajeIva" style="color: gray;"></div>
									</div>
								</div>
							</spring:bind>
                            
                            <!-- Fecha de Emisión -->
                            <spring:bind path="factura.fechaEmision"> 
                                <div class="col-md-12">
                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                        <label class="control-label">Fecha de Emisión *</label>
                                        <form:input path="fechaEmision" type="date" 
                                            class="form-control" />
                                        <form:errors path="fechaEmision" class="help-block"></form:errors>
                                    </div>
                                </div>
                            </spring:bind>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Guardar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </form:form>
        </div>
    </div>
</div>