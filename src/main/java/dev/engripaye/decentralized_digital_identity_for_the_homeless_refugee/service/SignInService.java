package dev.engripaye.decentralized_digital_identity_for_the_homeless_refugee.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;

@Service
public class SignInService {

    private final JWSSigner signer;
    private final JWSHeader header;


    public SignInService() throws KeyLengthException{
        var secret = new byte[32];
        new SecureRandom().nextBytes(secret);

        this.signer = new MACSigner(secret);
        this.header = new JWSHeader(JWSAlgorithm.HS256);

    }

    public String signSimple(String sub, String type, String value){
        var payload = new JSONObject()
                .appendField("sub", sub)
                .appendField("type", sub)
                .appendField("value", sub)
                .appendField("iat", Instant.now().getEpochSecond());
        var jws = new JWSObject(header, new Payload(payload.toJSONString()));
        try {
            jws.sign(signer);
        } catch (JOSEException e){
            throw new RuntimeException(e);
        }
         return jws.serialize();
    }
}
