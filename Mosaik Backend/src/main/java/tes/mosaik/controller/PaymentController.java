package tes.mosaik.controller;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tes.mosaik.modal.PlanType;
import tes.mosaik.modal.User;
import tes.mosaik.response.PaymentLinkResponse;
import tes.mosaik.service.UserService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Value("{razorpay.api.key}")
    private String apiKey;

    @Value("{razorpay.api.secret}")
    private String apiSecret;

    private final UserService userService;

    public PaymentController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
            @PathVariable PlanType planType,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        int amount = 799*100;
        if(planType.equals(PlanType.ANNUALLY)){
            amount = (int)(amount*12*0.7);
        }

        RazorpayClient razorpay = new RazorpayClient(apiKey, apiSecret);
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount", amount);
        paymentLinkRequest.put("currency", "INR");

        JSONObject customer = new JSONObject();
        customer.put("name", user.getFullName());
        customer.put("email", user.getEmail());
        paymentLinkRequest.put("customer", customer);

        JSONObject notify = new JSONObject();
        notify.put("email", true);
        paymentLinkRequest.put("notify",notify);

        paymentLinkRequest.put("callback_url",
                "https://localhost:5173/upgrade_plan/success?planType=" + planType);

        PaymentLink payment = razorpay.paymentLink.create(paymentLinkRequest);
        String paymentLinkID = payment.get("id");
        String paymentLinkURL = payment.get("short_url");

        PaymentLinkResponse response = new PaymentLinkResponse();
        response.setPaymentLinkID(paymentLinkID);
        response.setPaymentLinkURL(paymentLinkURL);

        return new ResponseEntity<>(response, HttpStatus.CREATED);


    }
}
