package com.mvii3iv.sat.crawler.components.captcha;

import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.mvii3iv.sat.crawler.components.anticaptcha.AntiCaptchaService;
import org.apache.commons.io.FileUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class CaptchaService {

    private AntiCaptchaService antiCaptchaService;
    private final String IMG_PATH = "/tmp/img/";//System.getProperty("inbox.dir") + "\\src\\main\\resources\\static\\img\\";
    private final String CAPTCHA_EXTENTION = ".PNG";

    @Autowired
    public CaptchaService(AntiCaptchaService antiCaptchaService) {
        this.antiCaptchaService = antiCaptchaService;
        System.out.println(IMG_PATH);
        deleteAllCaptchasFromServer();
    }


    /**
     * Saves the captcha from the SAT page in to the local server
     *
     * @param image
     * @param sessionId
     * @return true is the captcha has been saved
     */
    public boolean saveCaptcha(HtmlImage image, String sessionId) {

        File imagePath = new File( IMG_PATH + sessionId + CAPTCHA_EXTENTION);

        try {

            if (image == null || (sessionId.isEmpty() || sessionId.equals("")))
                return false;


            if (!imagePath.getParentFile().exists()) {
                System.out.println("Directory img didn't exist, has been created");
                imagePath.getParentFile().mkdir();
            }


            if (image == null || sessionId.length() == 0) {
                return false;
            }

            image.saveAs(imagePath);

            if (!imagePath.exists())
                throw new IOException();

            System.out.println("Captcha saved: " + imagePath.getName());
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Captcha couldn't be saved: " + imagePath.getName());
        return false;
    }


    /**
     * Inserts the downloaded captcha in to the login template (not used anymore, leave the code as future reference)
     *
     * @param template
     * @return the same template but with the captcha inserted
     * @deprecated
     */
    public String insertCaptcha(String template) {
        try {
            Path path = Paths.get(System.getProperty("inbox.dir") + "\\src\\main\\resources\\static\\img\\captcha.jpg");
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);
            String encodedImage = Base64.encode(bytes);
            return template.replace("$captcha", encodedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * decodes the captcha delegating data to the AntiCaptchaService.decode
     *
     * @param sessionId
     * @return an string with the captcha decoded
     */
    public String decodeCaptcha(String sessionId) {
        try {
            System.out.println("Decoding captcha id: " + sessionId);

            Path path = Paths.get(IMG_PATH + sessionId + CAPTCHA_EXTENTION);
            byte[] bytes = new byte[0];
            bytes = Files.readAllBytes(path);
            String decodedCaptcha = antiCaptchaService.decode(bytes);
            System.out.println("Captcha decoded: " + decodedCaptcha);
            deleteCaptchaFromServer(path.toAbsolutePath().toString());
            return decodedCaptcha;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * delete a captcha after has been decoded due it is not usable anymore
     *
     * @param path
     * @return true if the operation was successfully
     */
    private boolean deleteCaptchaFromServer(String path) {
        try {

            File file = new File(path);

            if (file.delete()) {
                System.out.println(file.getName() + " is deleted!");
            } else {
                System.out.println("Delete operation is failed.");
                return false;
            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }
    }


    /**
     * Removes all the files stored in img folder
     * @return true if files have been removed
     */
    private Boolean deleteAllCaptchasFromServer(){
        try {
            Path path = Paths.get(IMG_PATH);
            //if directory exists?
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    //fail to create directory
                    e.printStackTrace();
                }
            }
            FileUtils.cleanDirectory(new File(IMG_PATH));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
