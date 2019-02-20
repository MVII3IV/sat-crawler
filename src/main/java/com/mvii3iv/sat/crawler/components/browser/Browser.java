package com.mvii3iv.sat.crawler.components.browser;

import com.mvii3iv.sat.crawler.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.crawler.components.captcha.*;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

@Component
public class Browser {

    /*
        RFC LULR860821MTA
        PAS goluna21

        CASA8412202SA         17201720               Alberto
        PACY920531TS9         ab45ac56               Yara
        OIGO510728N20         ab45ac56               Olga
        GORA870926A8A         13081308               Alexandra
     */

    private static final String LOGIN_URL = "https://portalcfdi.facturaelectronica.sat.gob.mx/";
    private AntiCaptchaService antiCaptchaService;
    private CaptchaService captchaService;

    @Autowired
    private Environment env;

    public Browser(AntiCaptchaService antiCaptchaService, CaptchaService captchaService, Environment env) {
        this.antiCaptchaService = antiCaptchaService;
        this.captchaService = captchaService;
        this.env = env;
    }

    public void login() throws IOException {

            WebClient webClient = init();
            HtmlPage browser = webClient.getPage(LOGIN_URL);
            System.out.println();

            HtmlImage image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");

            captchaService.saveCaptcha(image, "LULR860821MTA");


            browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

            int timeMultiplier = 2;


            HtmlForm loginForm = browser.getFormByName("IDPLogin");
            HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
            HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
            HtmlInput captcha = loginForm.getInputByName("jcaptcha");
            HtmlInput sendButton = loginForm.getInputByName("submit");


            rfc.setValueAttribute("LULR860821MTA");
            pass.setValueAttribute("goluna21");
            captcha.setValueAttribute(captchaService.decodeCaptcha("LULR860821MTA"));
            browser = sendButton.click();


            do {
                browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/");
                webClient.waitForBackgroundJavaScript(1000 * timeMultiplier++);
            } while (browser.getPage().getTitleText().toLowerCase().equals("sat autenticación"));


    }

    private WebClient init() {

        WebClient webClient = new WebClient();

        if (Boolean.valueOf(env.getProperty("PROXY_ENABLED"))) {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, "proxy.autozone.com", 8080);
            DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials("edomingu", "ASDewq123!");
        } else {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        }

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {

            @Override
            public void timeoutError(HtmlPage arg0, long arg1, long arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void scriptException(HtmlPage arg0, ScriptException arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void malformedScriptURL(HtmlPage arg0, String arg1, MalformedURLException arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void loadScriptError(HtmlPage arg0, URL arg1, Exception arg2) {
                // TODO Auto-generated method stub

            }
        });
        webClient.setHTMLParserListener(new HTMLParserListener() {

            @Override
            public void error(String message, URL url, String html, int line, int column, String key) {

            }

            @Override
            public void warning(String message, URL url, String html, int line, int column, String key) {

            }
        });

        return webClient;
        //CookieManager cookieMan = new CookieManager();
        //cookieMan = browser.getCookieManager();
        //cookieMan.setCookiesEnabled(true);
    }
}
