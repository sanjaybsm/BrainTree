 function getClientToken() {
     		var that = this;
     		$.ajax({
             			url: "http://localhost:8082/services/clienttoken",
             		    type: "GET",
             		    contentType: "application/json",
             			success: function (dataWeGot) {
             		      var clientToken = dataWeGot.clienttoken;
                                   braintree.setup(clientToken, "dropin", {
                                   container: "payment-form"
                           });
             		    },
             			 error : function() {
             				  alert(1);
             			 }
             });
     	}


      function processPayment() {
     		var that = this;
     		alert(document.checkout.elements['payment_method_nonce'].value);
			var payment_method_nonce = document.checkout.elements['payment_method_nonce'].value;

     		$.ajax({
             			url: "http://localhost:8082/services/checkout",
             		    type: "POST",
             		    data:JSON.stringify({'payment_method_nonce':payment_method_nonce}),
             		    contentType: "application/json",
             			success: function (dataWeGot) {
             		      alert('Something happened good!');
             		    },
             			 error : function() {
             				 alert('Something happened bad!');
             			 }
             });
     	}
