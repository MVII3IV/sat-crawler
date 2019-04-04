package com.mvii3iv.sat.crawler.components.anticaptcha;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mvii3iv.sat.crawler.components.anticaptcha.models.*;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

@Service
public class AntiCaptchaService {

    /**
     * Global and constant variables
     */
    private final String CLIENT_kEY = "d2e3cdd426d740b83ee2bd655067412e";
    private final String TASK_RESULT_URL = "https://api.anti-captcha.com/getTaskResult";
    private final String CREATE_TASK_URL = "https://api.anti-captcha.com/createTask";

    @Autowired
    private Environment env;


    /**
     * Calls all the methods in order to decode the captcha, this method is called to decode captchas
     * @param captcha
     * @return resolvedCaptcha
     */
    public String decode(byte[] captcha) {

        AntiCaptchaCreatedTaskResponse taskResponse = createTask(captcha);
        /*try {
            Thread.sleep(35000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        AntiCaptchaTaskResult result = getTaskResult(taskResponse.getTaskId());
        String resolvedCaptcha = result.getSolution().getText();

        return resolvedCaptcha;
    }


    /**
     * Creates the task in the Anti-Captcha Server, it returns an errorId and taskId
     * @param captcha
     * @return AntiCaptchaCreatedTaskResponse
     */
    private AntiCaptchaCreatedTaskResponse createTask(byte[] captcha) {

        try {
            Task task2 = new Task(
                    "ImageToTextTask",
                    Base64.encode(captcha),
                    false,
                    true,
                    false,
                    0,
                    0,
                    0
            );

            return postDataToCreateTask(CREATE_TASK_URL, new ObjectMapper().writeValueAsString( new AntiCaptchaCreateTask(CLIENT_kEY, task2) ).replace("caseSensitive", "case"));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Gets the retrieved captcha when is available
     * @param taskId
     * @return AntiCaptchaTaskResult
     */
    private AntiCaptchaTaskResult getTaskResult(double taskId) {

        try {
            AntiCaptchaConsultTaskStatus checkStatus = new AntiCaptchaConsultTaskStatus(CLIENT_kEY, taskId);
            return postDataToGetTaskResult(TASK_RESULT_URL, new ObjectMapper().writeValueAsString(checkStatus));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Do a post in order to create the Captcha Task
     * @param u
     * @param jsonData
     * @return AntiCaptchaCreatedTaskResponse
     */
    private AntiCaptchaCreatedTaskResponse postDataToCreateTask(String u, String jsonData) {
        try {
            HttpURLConnection conn = null;
            URL url = new URL(u);

            if (Boolean.valueOf(env.getProperty("PROXY_ENABLED"))) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP,
                        new InetSocketAddress("proxy.autozone.com", 8080));
                conn = (HttpURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes());
            os.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = "";
            System.out.println("Captcha server is processing data \n");

            while ((output = br.readLine()) != null) {
                AntiCaptchaCreatedTaskResponse response = new ObjectMapper().readValue(output, AntiCaptchaCreatedTaskResponse.class);

                if (response.getErrorId() == 0)
                    return response;
                else
                    System.out.println("anti-captcha returned error > 0");
            }
            return null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Do a post and consults the result of the task, it retrieves the captcha resolved
     * @param u
     * @param jsonData
     * @return AntiCaptchaTaskResult
     */
    private AntiCaptchaTaskResult postDataToGetTaskResult(String u, String jsonData) {
        boolean isResolved = false;
        try {
            do {
                HttpURLConnection conn = null;
                URL url = new URL(u);

                if (Boolean.valueOf(env.getProperty("PROXY_ENABLED"))) {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP,
                            new InetSocketAddress("proxy.autozone.com", 8080));
                    conn = (HttpURLConnection) url.openConnection(proxy);
                } else {
                    conn = (HttpURLConnection) url.openConnection();
                }

                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                OutputStream os = conn.getOutputStream();
                os.write(jsonData.getBytes());
                os.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

                String output = br.readLine();
                AntiCaptchaTaskResult taskResult = new ObjectMapper().readValue(output, AntiCaptchaTaskResult.class);

                if (taskResult.getErrorId() == 0) {

                    if (taskResult.getStatus().equals("ready")) {
                        System.out.println(output);
                        return taskResult;
                    } else {
                        conn.disconnect();
                        os.close();
                        br.close();
                        //tries counter
                    }

                } else {
                    System.out.println("anti-captcha returned error > 0");
                }


            } while (!isResolved);

            return null;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}