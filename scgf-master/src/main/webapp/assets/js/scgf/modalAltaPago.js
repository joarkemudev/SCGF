//Acciones del modal de Alta de Factura.

$(function(){
	$(document).ready(function() {	
	// Obtén el valor seleccionado en el campo #plan
   		const facturaId = $('#idFactura').val();
   		console.log("este es el id de la factura"+ facturaId);
   		$('#modalFactNombre').text(''); 
    	$('#modalValorFact').text('');     
	    if (facturaId === "") {
	        // Si no se seleccionó ningún plan, limpia los campos del modal
	        $('#modalFactNombre').text(''); 
	        $('#modalValorFact').text('');
	    } else {
	        // Realiza una llamada AJAX para obtener los datos del plan
	        $.getJSON("../facturas/getFactDetails", {id: facturaId}, function(factura) {
	       		console.log("tamaño de la factura "+ factura.length);
				if (factura.length > 0) {
				    for (var i = 0; i < factura.length; i++) {
				        // Aquí seleccionamos dónde poner cada `label` y `value`
				        $("#modalFactNombre").append($('<span></span>').text(factura[i].label)); // Agrega el nombre (label)
				        var valorMostrar = (factura[i].value1 == "$0") ? factura[i].value2 : factura[i].value1;
				        console.log("este es el valorMostrar"+ valorMostrar+factura[i].value2);
				        $("#modalValorFact").append($('<span></span>').text(valorMostrar));
				    }
				} else {
				    // Si no hay planes, limpia los elementos
				    $("#modalValorPlan").text("No hay facturas disponibles");
				}
	            
	        }).fail(function() {
	            // Manejo de error si la solicitud falla
	            swal("Hubo un error al obtener los datos de la factura. Intenta nuevamente. 1");
	        });
	    }
    });
	$('#idFactura').change(function() { 
	    const facturaId = $(this).val(); // Obtén el ID del plan seleccionado
	    console.log("cambio el id de la factura"+ facturaId);	
	    $('#modalFactNombre').text(''); 
	    $('#modalValorFact').text('');    
	    if (facturaId === "") {
	        // Si no se seleccionó ningúna factura, limpia los campos del modal
	        $('#modalValorFact').text('');
	        $('#modalFactNombre').text(''); 
	    } else {
	        // Realiza una llamada AJAX para obtener los datos de la factura
	        $.getJSON("../facturas/getFactDetails", {id: facturaId}, function(factura) {
	       		
				if (factura.length > 0) {
				    for (var i = 0; i < factura.length; i++) {
				        // Aquí seleccionamos dónde poner cada `label` y `value`
				        $("#modalFactNombre").append($('<span></span>').text(factura[i].label)); // Agrega el nombre (label)
				      	var valorMostrar = (factura[i].value1 == "0") ? factura[i].value2 : factura[i].value1;
				      	console.log("este es el valorMostrar"+ valorMostrar+ factura[i].value2);
				        $("#modalValorFact").append($('<span></span>').text(valorMostrar));
				    }
				} else {
				    // Si no hay planes, limpia los elementos
				    $("#modalValorPlan").text("No hay facturas disponibles");
				}
	            
	        }).fail(function() {
	            // Manejo de error si la solicitud falla
	            swal("Hubo un error al obtener los datos de la factura. Intenta nuevamente. 2");
	        });
	    }
	});
	
	$('#frmPago').validate({
		 lang: 'es',
      submitHandler: function(form) {
		  var valorPlanTexto = $('#modalValorPlan').text();
		  var valorPlan = parseFloat(valorPlanTexto.replace(/\./g, '').replace(',', '.'))
	      var montoPagado=parseFloat($('#montoPagado').val());

	      if(montoPagado == 0 || montoPagado > valorPlan){
	      	swal({
				title : 'Error en monto pagado',
				text : 'El monto pagado deber ser mayor que 0 y no puede ser mayor al valor por pagar.',
				type : 'error',
				confirmButtonClass : "btn btn-info",
				buttonsStyling : false
			});
		      
	      }else{
	    	  guardar(form);
	      }
	     
       }
	});

});	


function currencyFormat(number){
	return "$ " + (number).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}
