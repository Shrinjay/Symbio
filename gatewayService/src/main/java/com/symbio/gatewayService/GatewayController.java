package com.symbio.gatewayService;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.SocketTimeoutException;

@CrossOrigin
@RestController
@SpringBootApplication
public class GatewayController {

    @Value("${intercept.Urls}")
    String joinedCheckUrls;

    @Value("${intercept.wakeTimeout}")
    int timeout;

    @CrossOrigin
    @GetMapping("/wake/")
    public boolean wakeAll() throws IOException, InterruptedException {
        String[] checkUrls = joinedCheckUrls.split(",", -1);

        for (String checkUrl: checkUrls) {
            try {
                RequestConfig timeoutConfig = RequestConfig.custom().setSocketTimeout(timeout).build();
                CloseableHttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(timeoutConfig).build();
                HttpGet wakeRequest = new HttpGet(checkUrl);
                client.execute(wakeRequest);
            }
            catch(SocketTimeoutException e) {
                return false;
            }
        }
        return true;

    }

}
