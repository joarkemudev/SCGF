//Acciones del modal de Alta de Factura.

$(function(){

	$('#frmFactura').validate({
	
		lang: 'es', submitHandler: function(form) {

       	}
	});

});	


function currencyFormat(number){
	return "$ " + (number).toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
}
