package com.max.app;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public final class VaultClientMain {

    private static final Logger LOG = LoggerFactory.getLogger(VaultClientMain.class);

    public static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");

    public static void main(String[] args) throws Exception {

        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(5, TimeUnit.SECONDS).
                readTimeout(5, TimeUnit.SECONDS).
                build();

        String host = "https://ss.cne-security-ss.oraclecne:30080";
        String trustDomain = "ugbu.ugbu-opower-pgw";
        String serviceAccessToken = "s.K3sv5IfngThB4vVvExIu2N0B";

        String url = String.format("%s/store/v4/secret/%s/stage/nes/connection-details", host, trustDomain);

        String jsonData = "{'url': 'http://some-fancy-url'}";

        putData(client, url, jsonData, serviceAccessToken);

        LOG.info("VaultClientMain done. java version {}", System.getProperty("java.version"));
    }

    // curl -4 --insecure -X "PUT"
    // -H "Authorization:Bearer ${COOLAPP_SAT}"
    // -H "Content-Type:application/json"
    // -d '{"username":"user","password":"123abc"}'
    // https://ss.cne-security-ss.oraclecne:30080/store/v4/secret/cgbu.coolapp/clients/credentials/acme
    private static String putData(OkHttpClient client, String url, String jsonData, String token) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + token)
                .put(RequestBody.create(jsonData, JSON_MEDIA_TYPE))
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    /*
    the secure store does not sit on a public load balancer.
    to query the secure store you will either need to route using a proxy through the bastion host on that
    stack or from the bastion host itself

    for example.  I am connected to Oracle VPN on a VM (Ubuntu in my case).
    I run an SSH proxy on Port 5555:

    ssh -i bh_key -D 5555 -f -N pfudge@129.146.151.164

    where <bh_key> is my private key associated with my account on the Bastion Host (129.146.151.164) in the PHX Dev v3 MSP
    I can now curl to the secure store using a SOCKS proxy:
    curl -k -x socks5h://127.0.0.1:5555 https://ss.cne-security-ss.oraclecne:30080/store/v4/health
    {"msg": "Secure Store is healthy."}
    I use socks5h as the protocol for the proxy because I need the proxy to do domain name resolution on the
    secure store URL, otherwise my local VM will not know how to resolve that address.
     */
}
