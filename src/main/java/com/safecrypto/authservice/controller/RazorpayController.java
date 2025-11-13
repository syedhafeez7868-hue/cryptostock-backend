package com.safecrypto.authservice.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.safecrypto.authservice.service.WalletService;

import java.util.Map;

@RestController
@RequestMapping("/api/razorpay")
@CrossOrigin(origins = "http://localhost:3000")
public class RazorpayController {

    @Value("${razorpay.key_id}")
    private String razorpayKeyId;

    @Value("${razorpay.key_secret}")
    private String razorpayKeySecret;

    @Autowired
    private WalletService walletService;

    @PostMapping("/create-order")
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> data) throws Exception {

        double amount = Double.parseDouble(data.get("amount").toString());
        int amountInPaise = (int) (amount * 100);

        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);

        return Map.of(
                "id", order.get("id"),
                "amount", order.get("amount"),
                "currency", order.get("currency"),
                "key", razorpayKeyId
        );
    }

    @PostMapping("/verify-payment")
    public String verifyPayment(@RequestBody Map<String, Object> data) {

        try {
            String email = data.get("email").toString();
            double amount = Double.parseDouble(data.get("amount").toString());

            walletService.addFunds(email, amount);

            return "Payment successful: ₹" + amount + " added to " + email;
        } catch (Exception e) {
            return "Payment verification failed: " + e.getMessage();
        }
    }
}
