package com.toyota.tme.util;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;

/**
 * Created by SBO0662 on 06/10/2015.
 */
public class BrainTreeServices {


    public static final String MERCHANTID = "7yzyjwmwjzgnbpj5";
    public static final String PUBLICKEY = "ksf7pw7cp8dnn43n";
    public static final String PRIVATEKEY = "07f77eb2837771787f35462d9b56603f";

    public String merchantAccountConfiguration(){
        BraintreeGateway gateway = getBrainTreeGateway();
        return  gateway.clientToken().generate();
    }

    public BraintreeGateway getBrainTreeGateway(){
        BraintreeGateway gateway = new BraintreeGateway(
                Environment.SANDBOX,
                MERCHANTID,
                PUBLICKEY,
                PRIVATEKEY
        );
        return gateway;
    }
}
