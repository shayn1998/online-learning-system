//client.js script will be used to process the request from the customer to the server and response from the server to the browser.
// When the user goes to the page, a payment intent is created, the payment intent calls stripe, informs the customer wants to pay,
// and returns a secret key.

//Stripe has a javascript library called the javascript elements, and when you call elements.create() on a specific div, then stripe
// will insert the component into the div.



var stripe = Stripe('pk_test_51NisWJSATxUDQMU3UURflRdkkeu7Tq2Ju7foywl8ASet3KaqC1fE1cKzayM4JXUHtEqMMVBLoq3fGMfQVIoTw7Qj00F8TqRFzq');

var purchase = {

	"email": email,

	"amount": amount,

	"featureRequest": featureRequest

};

console.log(purchase)
console.log(purchase.amount);

console.log(JSON.stringify(purchase))

document.querySelector("button").disabled = true;

fetch("/create-payment-intent/", {

	method: "POST",

	headers: {

			"Content-Type" : "application/json", charset: "utf-8"
	},

	body : JSON.stringify(purchase)

}).then(function(result) {
	return result.json();
}).then(function(data) {

	console.log(data);

	var elements = stripe.elements();

	var style = {

		base: {

			color: "#32325d",
			fontFamily: 'Arial, sans-serif',
			fontSmoothing: "antialiased",
			fontSize: "16px",
			"::placeholder": {
				color: "#32325d"
			}


		},

		invalid: {

			fontFamily: 'Arial, sans-serif',

			color: "#fa755a",

			iconColor: "#fa755a"

		}

	};

	var card = elements.create("card", { style: style });

	card.mount("#card-element");

	card.on("change", function(event) {

		document.querySelector("button").disabled = event.empty;

		document.querySelector("#card-error").textContent = event.error ? event.error.message : "";

	})

	var form = document.getElementById("payment-form");
	form.addEventListener("submit", function(event) {
		event.preventDefault();
		// Complete payment when the submit button is clicked
		console.log(data.clientSecret);
		payWithCard(stripe, card, data.featureRequest);
	});

})

var payWithCard = function(stripe, card, clientSecret) {

	loading(true);

	stripe.confirmCardPayment(clientSecret, {

		receipt_email: email,

		payment_method: {
			card: card,
			billing_details: {
				email: email
			}
		}
	}).then(function(result) {
		if (result.error) {
			// Show error to your customer
			showError(result.error.message);
		} else {
			// The payment succeeded!
			orderComplete(result.paymentIntent.id);
		}
	});

}

var orderComplete = function(paymentIntentId) {
	loading(false);
	document
		.querySelector(".result-message a")
		.setAttribute(
			"href",
			"https://dashboard.stripe.com/test/payments/" + paymentIntentId
		);
	document.querySelector(".result-message").classList.remove("hidden");
	document.querySelector("button").disabled = true;
	
	$.ajax({
        type: "GET",
        url: "/payment-confirmed",
        success: function (result) {
            // do something.
        },
        error: function (result) {
            // do something.
        }
    });
};

// Show the customer the error from Stripe if their card fails to charge
var showError = function(errorMsgText) {
	loading(false);
	var errorMsg = document.querySelector("#card-error");
	errorMsg.textContent = errorMsgText;
	setTimeout(function() {
		errorMsg.textContent = "";
	}, 4000);
};

// Show a spinner on payment submission
var loading = function(isLoading) {
	if (isLoading) {
		// Disable the button and show a spinner
		document.querySelector("button").disabled = true;
		document.querySelector("#spinner").classList.remove("hidden");
		document.querySelector("#button-text").classList.add("hidden");
	} else {
		document.querySelector("button").disabled = false;
		document.querySelector("#spinner").classList.add("hidden");
		document.querySelector("#button-text").classList.remove("hidden");
	}
};