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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

@Component
public class Browser {

     /*
        LULR860821MTA   goluna21
        CASA8412202SA   17201720    Alberto
        PACY920531TS9   ab45ac56    Yara
        OIGO510728N20   ab45ac56    Olga
        GORA870926A8A   13081308    Alexandra
     */

    @Autowired
    private Environment env;

    private static final String LOGIN_URL = "https://portalcfdi.facturaelectronica.sat.gob.mx/";
    private AntiCaptchaService antiCaptchaService;
    private CaptchaService captchaService;

    /**
     *
     * @param antiCaptchaService
     * @param captchaService
     * @param env
     */
    public Browser(AntiCaptchaService antiCaptchaService, CaptchaService captchaService, Environment env) {
        this.antiCaptchaService = antiCaptchaService;
        this.captchaService = captchaService;
        this.env = env;
    }



    public WebClient getUserData(WebClient webClient){
        getEmittedBills(webClient);
        return null;
    }


    public WebClient getEmittedBills(WebClient webClient){

        HtmlTable table = null;
        try {
            HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaEmisor.aspx");
            //browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaReceptor.aspx");

            //setup date range
            browser.getHtmlElementById("ctl00_MainContent_RdoFechas").click();
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaInicial2_Calendario_text")).setValueAttribute("01/01/" + Year.now().getValue());
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaFinal2_Calendario_text")).setValueAttribute(new SimpleDateFormat("dd/MM/yyy").format(new Date()));
            browser = ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_BtnBusqueda")).click();


            do {
                webClient.waitForBackgroundJavaScript(1000);
                table = browser.getHtmlElementById("ctl00_MainContent_tblResult");
            } while (table.getRows().size() <= 1);

            boolean firstTimeFlag = true;
            String transformedDate = "Fecha de Emisión";

            for (final HtmlTableRow row : table.getRows()) {

                if (!firstTimeFlag) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    Date date = null;
                    date = simpleDateFormat.parse(row.getCells().get(6).asText());
                    SimpleDateFormat simpleDateFormatAux = new SimpleDateFormat("dd/MM/yyyy");
                    transformedDate = simpleDateFormatAux.format(date);
                }
                firstTimeFlag = false;

                for(HtmlTableCell data : row.getCells()){
                    System.out.print(data.asText() + "\t" );
                }
                System.out.println();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        /*
        List incomes = new ArrayList<Incomes>();
        boolean firstTimeFlag = true;
        String transformedDate = "Fecha de Emisión";

        for (final HtmlTableRow row : table.getRows()) {

            if (!firstTimeFlag) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = simpleDateFormat.parse(row.getCells().get(6).asText());

                SimpleDateFormat simpleDateFormatAux = new SimpleDateFormat("dd/MM/yyyy");
                transformedDate = simpleDateFormatAux.format(date);
            }
            firstTimeFlag = false;


            incomes.add(
                    new Incomes(
                            row.getCells().get(1).asText(), //fiscalId
                            row.getCells().get(2).asText(), //emisorRFC
                            row.getCells().get(3).asText(), //emisorName
                            row.getCells().get(4).asText(), //receiverRFC
                            row.getCells().get(5).asText(), //receiverName
                            transformedDate,                //emitedDate
                            row.getCells().get(7).asText(), //certificationDate
                            row.getCells().get(8).asText(), //certifiedPAC
                            row.getCells().get(9).asText(), //total
                            row.getCells().get(10).asText(),//voucherEffect
                            row.getCells().get(11).asText() //voucherStatus
                    )
            );*/
        return webClient;
    }


    public WebClient getReceivedBills(WebClient webClient){
        return null;
    }



    public WebClient login(String rfc, String pass) {

        WebClient webClient = init();

        webClient = openLoginURL(webClient, rfc);
        webClient = sendLoginForm(webClient, rfc, pass);
        webClient = getMainPage(webClient);

        return webClient;
    }



    /**
     *
     * @param webClient
     * @return
     */
    private WebClient openLoginURL(WebClient webClient, String rfc){

        int timeMultiplier = 1;
        HtmlPage browser = null;
        HtmlImage image = null;

        do {
            if(timeMultiplier != 1)
                System.out.println("--->login form could be found, try:" + timeMultiplier);

            try {
                browser = webClient.getPage(LOGIN_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }

            image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
            webClient.waitForBackgroundJavaScript(1000 * ++timeMultiplier);
            captchaService.saveCaptcha(image, rfc);
            browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

            if(timeMultiplier > 5){
                System.out.println("--->login form could be found after " + timeMultiplier + " tries");
                return null;
            }
        } while (!browser.getTitleText().toLowerCase().equals("sat autenticación"));

        return webClient;
    }


    /**
     *
     * @param webClient
     * @param RFC
     * @param PASS
     * @return
     */
    private WebClient sendLoginForm(WebClient webClient, String RFC, String PASS) {
        int timeMultiplier = 1;
        HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
        //--->begin login form
        do{
            try {
                HtmlForm loginForm = browser.getFormByName("IDPLogin");
                HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
                HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
                HtmlInput captcha = loginForm.getInputByName("jcaptcha");
                HtmlInput sendButton = loginForm.getInputByName("submit");
                rfc.setValueAttribute(RFC);
                pass.setValueAttribute(PASS);
                captcha.setValueAttribute(captchaService.decodeCaptcha(RFC));
                browser = sendButton.click();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(timeMultiplier > 5){
                System.out.println("--->login form couldn't be sent after " + timeMultiplier + " tries");
                return null;
            }
        } while (!browser.getTitleText().toLowerCase().equals("portal contribuyentes cfdi | buscar cfdi"));
        //--->end login form
        return webClient;
    }



    /**
     *
     * @param webClient
     * @return
     */
    private WebClient getMainPage(WebClient webClient) {
        int timeMultiplier = 1;
        HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
        //--->login validator
        do {
            try {
                browser = webClient.getPage(LOGIN_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            webClient.waitForBackgroundJavaScript(1000 * timeMultiplier++);
        } while (browser.getPage().getTitleText().toLowerCase().equals("sat autenticación"));
        //--->end login validator
        return webClient;
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
