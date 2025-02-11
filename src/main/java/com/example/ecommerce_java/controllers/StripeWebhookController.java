package com.example.ecommerce_java.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import io.github.cdimascio.dotenv.Dotenv;

@RestController
@RequestMapping("/api/webhooks")
public class StripeWebhookController {
    private final String STRIPE_WEBHOOK_SECRET = getStripeSecret();

    @PostMapping
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            Event event = Webhook.constructEvent(payload, sigHeader, STRIPE_WEBHOOK_SECRET);

            if ("payment_intent.succeeded".equals(event.getType())) {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
                System.out.println("Payment received: " + paymentIntent.getId());

                //update order status in the database
            }

            return ResponseEntity.ok("Webhook received");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook error: " + e.getMessage());
        }
    }

    private String getStripeSecret() {
        Dotenv dotenv = Dotenv.load();
        return dotenv.get("STRIPE_WEBHOOK_SECRET");
    }
}
