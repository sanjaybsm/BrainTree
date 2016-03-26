package com.toyota.tme.util;

/**
 * Created by SBO0662 on 25/09/2015.
 */

import java.io.IOException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
import com.toyota.tme.model.ClientToken;
import com.toyota.tme.model.PaymentMethodNounce;
import com.toyota.tme.wsutils.json.Json;
import com.toyota.tme.wsutils.json.JsonException;
import org.apache.cxf.rs.security.cors.CrossOriginResourceSharing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Infosys
 */
/*

@CrossOriginResourceSharing(
        allowAllOrigins = true,
        allowCredentials = true,
        maxAge = 1,
        allowHeaders = {
                "X-custom-1", "X-custom-2"
        },
        exposeHeaders = {
                "X-custom-3", "X-custom-4"
        }
)
*/


//@Path("ping.html")
public class Ping {

    private Logger log = LoggerFactory.getLogger(Ping.class);


    public Ping(){

    }
    /*
     * This is for Ping service
     */
    @GET
    @Path("/ping")
    public Response getPing() {
        System.out.println("Inside GET Request");
        //  Response.status(Status.OK).entity(pingResponse).build();
        return Response.ok().entity("Test Response check for GET CALL").type(MediaType.APPLICATION_JSON).build();
        //return Response.status(Status.OK).build();
    }

    @POST
  /*  @CrossOriginResourceSharing(
            allowAllOrigins = true,
            allowCredentials = true,
            exposeHeaders = { "X-custom-3", "X-custom-4" }
    )*/
    @Path("/pingpost")
    public Response getPingPost() {
        System.out.println("Inside POST Request");

        //  Response.status(Status.OK).entity(pingResponse).build();
        return Response.ok().entity("Test Response check for POST CALL").type(MediaType.APPLICATION_JSON).build();
        //return Response.status(Status.OK).build();
    }

    @GET
    @Path("/clienttoken")
    public Response getClientToken() {
        System.out.println("Inside getClientToken Request");
        //  Response.status(Status.OK).entity(pingResponse).build();
        BrainTreeServices services = new BrainTreeServices();
        String token = services.merchantAccountConfiguration();
        System.out.println("Braintree token : "+token);
        ClientToken clientToken = new ClientToken();
        clientToken.setClienttoken(token);
        return Response.ok().entity(clientToken).type(MediaType.APPLICATION_JSON).build();
        //return Response.status(Status.OK).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/checkout")
    public Response getCheckoutDetails(PaymentMethodNounce paymentMethodNounce) {

        System.out.println("Inside Method Checkout detail "+paymentMethodNounce.getPayment_method_nonce());
        String nonceFromTheClient = paymentMethodNounce.getPayment_method_nonce();
        createTransaction(nonceFromTheClient);
        String myHtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>My <span style=\"color:red\">Important</span> Heading</h1>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return Response.ok().entity(myHtml).type(MediaType.APPLICATION_JSON).build();
        //return Response.status(Status.OK).build();
    }

    private void createTransaction(String nonceFromTheClient) {

        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("10.00"))
                .paymentMethodNonce(nonceFromTheClient)
                .merchantAccountId("toyotaTest-GBP");
        //Change the following braintree services to Gateway
        BrainTreeServices services = new BrainTreeServices();
        BraintreeGateway gateway = services.getBrainTreeGateway();
        Transaction transaction;
        Result<Transaction> result = gateway.transaction().sale(request);
        try {
            System.out.println("Sanjay : " + Json.toJson(result));
        } catch (JsonException e) {
            e.printStackTrace();
        }
    }

    @POST
    @Path("/checkouttemp")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/html")
    public Response getCheckoutDetailsTemp(@FormParam("payment_method_nonce") String  paymentNounce) {
        System.out.println("Inside temp method");
        System.out.println("Request parameters "+paymentNounce);
        createTransaction(paymentNounce);
        String myHtml = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>My <span style=\"color:green\">Your Payment Successful!</span></h1>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        return Response.ok().entity(myHtml).type(MediaType.TEXT_HTML_TYPE).build();
        //return Response.status(Status.OK).build();
    }

}
