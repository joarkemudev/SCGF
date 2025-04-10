<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div id="agregaPagoModal" class="modal fade" role="dialog">
    <div class="modal-dialog modal-pq mx-5">

        <!-- Modal content -->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Alta de Pago del Socio</h4>
            </div>

            <form:form id="frmPago" name="frmPago" action="savePago" method="POST" 
                modelAttribute="pago" enctype="multipart/form-data">
                <div class="modal-body">
	                <div class="row">
	                	<div  style="text-align: center;">
	                	<div class="col-md-6">
						    <label>Detalle Factura:</label>
						    <span id="modalFactNombre" style="font-size: 28px; color: red; display: block; text-align: center; margin: 0 auto;"></span>
						</div>
						<div class="col-md-6">
						    <label>Valor por pagar:</label>
						    <span id="modalValorFact" style="font-size: 28px; color: red; display: block; text-align: center; margin: 0 auto;">
							    <fmt:formatNumber value="" type="currency" />
							</span>
						</div>
						</div>
					</div>
                	<hr>
                    <small><fmt:message key="labels.forms.hint.requiredfields" /></small>
                    <div class="form-body">
                        <!-- Mensaje de error, validación backend -->
                        <spring:hasBindErrors name="pago">
                            <div class="alert alert-danger">
                                <button class="close" data-close="alert"></button>
                                <fmt:message key="error.form.globalmessage" />
                            </div>
                        </spring:hasBindErrors>
                        
                        <div class="row">
          					<div class="col-md-4">
								<div class="form-group label-floating">
								<label class="control-label">Factura: *</label>
									<form:select id="idFactura" path="factura.id" class="select2-container form-control select2me">
									<form:options itemValue="id" itemLabel="uuid" items="${facturas}"/>
									</form:select>
								</div>
							</div>
                            <spring:bind path="pago.montoPagado">
								<div class="col-md-4">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label">Monto Pagado: *</label>
										<form:input path="montoPagado" required="true" type="currency"
											number="true" class="form-control input upper" />
										<form:errors path="montoPagado" class="help-block"></form:errors>
									</div>
								</div>
							</spring:bind>

                            <spring:bind path="pago.metodoPago">
								<div class="col-md-4">
									<div class="form-group label-floating ${status.error ? 'has-error' : ''}">
										<label class="control-label">Método de Pago :*</label>
										<form:select path="metodoPago" required="true" multiple="false"
											class="select2-container form-control select2me">
											<form:options items="${metodosPago}"/>
										</form:select>
										<form:errors path="metodoPago" class="help-block"></form:errors>
									</div>
								</div>
							</spring:bind>
                        </div>
                    </div>
                </div>
                <input type="hidden" id="cliente.id" name="cliente.id" value="${cliente.id}">
                
                <div class="modal-footer">
                    <button type="submit" class="btn btn-success">Guardar</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                </div>
            </form:form>
        </div>
    </div>
</div>