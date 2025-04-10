<div class="modal fade right" id="configuracionModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-side modal-top-right">
        <div class="modal-content">
            <div class="modal-header bg-primary text-white">
                <h5 class="modal-title">Configuración</h5>
                <button type="button" class="close text-white" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                
                <!-- Tema -->
                <div class="config-section mb-4">
                    <h6 class="section-title mb-3">Tema</h6>
                    <div class="btn-group w-100" role="group">
                        <button type="button" 
                                class="btn btn-outline-secondary theme-btn active"
                                data-theme="claro">
                            <i class="material-icons">wb_sunny</i> Claro
                        </button>
                        <button type="button" 
                                class="btn btn-outline-secondary theme-btn"
                                data-theme="oscuro">
                            <i class="material-icons">nights_stay</i> Oscuro
                        </button>
                    </div>
                </div>

                <!-- Tamaño de Fuente -->
                <div class="config-section mb-4">
                    <h6 class="section-title mb-3">Tamaño de texto</h6>
                    <div class="btn-group w-100" role="group">
                        <button type="button" 
                                class="btn btn-outline-secondary"
                                onclick="adjustFontSize('increase')">
                            <i class="material-icons">zoom_in</i> Aumentar
                        </button>
                   
                        <button type="button" 
                                class="btn btn-outline-secondary"
                                onclick="adjustFontSize('decrease')">
                            <i class="material-icons">zoom_out</i> Disminuir
                        </button>
                    </div>
                </div>
				
				<!-- Imagen del Menú -->
                <div class="config-section">
                    <h6 class="section-title mb-3">Imagen del Menú</h6>
                    <div class="custom-file">
                        <div class="btn-group">
                        <button class="btn">Imagen 1</button>
                        <button class="btn">Imagen 2</button>
                    </div>
                    </div>
                    <div id="imagePreview" class="mt-2 text-center"></div>
                </div>
                <!-- Idioma -->
                <div class="config-section mb-4">
                    <h6 class="section-subtitle mb-3">Idioma</h6>
                    <div class="btn-group w-100" role="group">
                        <button type="button" 
                                class="btn btn-outline-secondary language-btn active"
                                data-lang="es">
                            <span class="flag-icon flag-icon-es"></span> ES
                        </button>
                        <button type="button" 
                                class="btn btn-outline-secondary language-btn"
                                data-lang="en">
                            <span class="flag-icon flag-icon-us"></span> EN
                        </button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>



