package com.mvii3iv.sat.crawler.components.browser;

import com.mvii3iv.sat.crawler.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.crawler.components.assets.HostValidator;
import com.mvii3iv.sat.crawler.components.captcha.*;
import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;
import com.mvii3iv.sat.crawler.components.bills.Bills;
import com.mvii3iv.sat.crawler.components.bills.BillsRepository;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
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
    private AntiCaptchaService antiCaptchaService;
    private CaptchaService captchaService;
    private BillsRepository billsRepository;
    private HostValidator hostValidator;

    private static final String LOGIN_URL = "https://portalcfdi.facturaelectronica.sat.gob.mx/";

    /**
     * Just the class constructor
     * @param antiCaptchaService
     * @param captchaService
     * @param env
     */
    public Browser(AntiCaptchaService antiCaptchaService, CaptchaService captchaService, Environment env, BillsRepository billsRepository, HostValidator hostValidator) {
        this.antiCaptchaService = antiCaptchaService;
        this.captchaService = captchaService;
        this.env = env;
        this.billsRepository = billsRepository;
        this.hostValidator = hostValidator;
    }


    /**
     * This methods orders how the data is extracted from SAT by calling different methods
     * at the end, it returns a List of Bills
     * @param webClient
     * @param rfc
     * @return
     */
    public List<Bills> getUserData(WebClient webClient, String rfc) {
        getReceivedBills(webClient, rfc);
        getEmittedBills(webClient, rfc);
        return billsRepository.findByUserId(rfc);
    }


    /**
     * Extracts received bills from SAT
     * @param webClient
     * @param rfc
     * @return
     */
    public WebClient getReceivedBills(WebClient webClient, String rfc) {
        HtmlTable table = null;

        String transformedDate = "Fecha de Emisión";
        String month = "01";
        String day = "01";
        List incomes = new ArrayList<Bills>();

        try {
            HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaReceptor.aspx");
            browser.getHtmlElementById("ctl00_MainContent_RdoFechas").click();


            for (int i = 1; i < (Calendar.getInstance().get(Calendar.MONTH) + 2) + 1; i++) {

                if (i < 10)
                    month = "0" + i;
                    int count = 0;

                    HtmlSelect monthSelect = (HtmlSelect) browser.getElementById("ctl00_MainContent_CldFecha_DdlMes");
                    HtmlOption option = monthSelect.getOptionByText(month);
                    monthSelect.setSelectedAttribute(option, true);
                    browser = ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_BtnBusqueda")).click();

                    do {
                        System.out.println("try number: " + count++);
                        webClient.waitForBackgroundJavaScript(1000 * count);
                        table = browser.getHtmlElementById("ctl00_MainContent_tblResult");


                        if (browser.getHtmlElementById("ctl00_MainContent_PnlNoResultados").getAttribute("style").contains("display:inline")) {
                            System.out.println("no info available for that user");
                            break;
                        }

                        if (count > 6)
                            break;
                    } while (table.getRows().size() <= 1);

                    if (table.getRows().size() <= 1)
                        continue;

                    billsRepository.save(getBillsFromTable(table, rfc, false));
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return webClient;
    }


    /**
     * Extracts Emitted Bills from SAT
     * @param webClient
     * @param rfc
     * @return a webclient
     */
    public WebClient getEmittedBills(WebClient webClient, String rfc) {

        System.out.println("Extracting Emmitted bills..");
        HtmlTable table = null;
        List incomes = new ArrayList<Bills>();

        try {
            System.out.print("Loading sat emitted bill page:");
            HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
            browser = webClient.getPage("https://portalcfdi.facturaelectronica.sat.gob.mx/ConsultaEmisor.aspx");
            System.out.println(" OK!");


            System.out.print("Selecting Date Range ");
            browser.getHtmlElementById("ctl00_MainContent_RdoFechas").click();
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaInicial2_Calendario_text")).setValueAttribute("01/01/" + Year.now().getValue());
            ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_CldFechaFinal2_Calendario_text")).setValueAttribute(new SimpleDateFormat("dd/MM/yyy").format(new Date()));
            System.out.print("01/01/" + Year.now().getValue() + " to " + new SimpleDateFormat("dd/MM/yyy").format(new Date()));
            browser = ((HtmlInput) browser.getHtmlElementById("ctl00_MainContent_BtnBusqueda")).click();
            System.out.println("Date selected: OK!");

            int counter = 0;
            do {
                webClient.waitForBackgroundJavaScript(1000 * counter++);
                table = browser.getHtmlElementById("ctl00_MainContent_tblResult");

                if(counter > 6){
                    System.out.println("Javascript background time over exceeded");
                    return null;
                }

                if (browser.getHtmlElementById("ctl00_MainContent_PnlNoResultados").getAttribute("style").contains("display:inline")) {
                    System.out.println("no info available for that user");
                    return null;
                }
            } while (table.getRows().size() <= 1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        billsRepository.save(getBillsFromTable(table, rfc, true));

        return webClient;
    }


    /**
     * Receives a table and decompiles to extract its data and form a Bill register
     * @param table
     * @param rfc
     * @param isEmmited
     * @return List of Bills
     */
    private List<Bills> getBillsFromTable(HtmlTable table, String rfc, boolean isEmmited){

        List incomes = new ArrayList<Bills>();
        boolean firstTimeFlag = true;
        String transformedDate = "Fecha de Emisión";


        System.out.println("extracting data from the table...");
        for (final HtmlTableRow row : table.getRows()) {

            if (!firstTimeFlag) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                Date date = null;
                try {
                    date = simpleDateFormat.parse(row.getCells().get(6).asText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat simpleDateFormatAux = new SimpleDateFormat("dd/MM/yyyy");
                transformedDate = simpleDateFormatAux.format(date);
            }
            firstTimeFlag = false;

            if( row.getCells().get(12).asText().toLowerCase().equals("cancelado") ){
                continue;
            }

            if(isEmmited == false && row.getCells().get(10).asText().toLowerCase().equals("nomina")){
                continue;
            }

            boolean isEdited = false;

            if(isEmmited == true && ( row.getCells().get(10).asText().toLowerCase().equals("nomina") || ( row.getCells().get(10).asText().toLowerCase().equals("ingreso")))    ){
                isEmmited = false;
                isEdited = true;
            }



            incomes.add(
                    new Bills(
                            row.getCells().get(1).asText(),  //fiscalId
                            rfc,
                            row.getCells().get(2).asText(),  //emisorRFC
                            row.getCells().get(3).asText(),  //emisorName
                            row.getCells().get(4).asText(),  //receiverRFC
                            row.getCells().get(5).asText(),  //receiverName
                            transformedDate,                 //emitedDate
                            row.getCells().get(7).asText(),  //certificationDate
                            row.getCells().get(8).asText(),  //certifiedPAC
                            row.getCells().get(9).asText(),  //total
                            row.getCells().get(10).asText(), //Efecto del Comprobante //voucher effect
                            row.getCells().get(11).asText(), //Estatus de cancelación
                            row.getCells().get(12).asText(), //Estado del Comprobante //voucher status
                            row.getCells().get(13).asText(), //Estatus de Proceso de Cancelación
                            row.getCells().get(14).asText(), //Fecha de Proceso de Cancelación
                            isEmmited,
                            isEdited

                    )
            );
        }
        System.out.println("Information extracted successfully");
        return incomes;
    }

    /**
     * This method do login in SAT
     * @param rfc
     * @param pass
     * @return webclient with browser information
     */
    public WebClient login(String rfc, String pass) {

        WebClient webClient = init();
        boolean hasError = false;
        int count = 0;

        do {
            try {
                webClient = openLoginURL(webClient, rfc, pass);
                webClient = getMainPage(webClient);
            } catch (Exception e) {
                hasError = true;
                System.out.println("Error while login trying again, try number: " + count++);
                if(count > 5)
                    break;
            }
        }while(hasError);




        return webClient;
    }


    /**
     * @param webClient
     * @return
     */
    private WebClient openLoginURL(WebClient webClient, String RFC, String PASS) {

        int timeMultiplier = 1;
        HtmlPage browser = null;
        HtmlImage image = null;

        do {
            if (timeMultiplier != 1)
                System.out.println("--->login form could be found, try:" + timeMultiplier);

            try {
                browser = webClient.getPage(LOGIN_URL);

                image = browser.<HtmlImage>getFirstByXPath("//*[@id='IDPLogin']/div[3]/label/img");
                webClient.waitForBackgroundJavaScript(1000 * ++timeMultiplier);
                captchaService.saveCaptcha(image, RFC);
                browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();

                HtmlForm loginForm = browser.getFormByName("IDPLogin");
                HtmlInput rfc = loginForm.getInputByName("Ecom_User_ID");
                HtmlPasswordInput pass = loginForm.getInputByName("Ecom_Password");
                HtmlInput captcha = loginForm.getInputByName("jcaptcha");
                HtmlInput sendButton = loginForm.getInputByName("submit");
                rfc.setValueAttribute(RFC);
                pass.setValueAttribute(PASS);
                captcha.setValueAttribute(captchaService.decodeCaptcha(RFC));

                try {
                    browser = sendButton.click();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            if (timeMultiplier > 5) {
                System.out.println("--->login form could be found after " + timeMultiplier + " tries");
                return null;
            }
        } while (!browser.getTitleText().toLowerCase().equals("portal contribuyentes cfdi | buscar cfdi"));

        return webClient;
    }


    /**
     * @param webClient
     * @param RFC
     * @param PASS
     * @return
     */
    private WebClient sendLoginForm(WebClient webClient, String RFC, String PASS) {
        int timeMultiplier = 1;
        HtmlPage browser = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
        //--->begin login form
        do {
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
            if (timeMultiplier > 5) {
                System.out.println("--->login form couldn't be sent after " + timeMultiplier + " tries");
                return null;
            }
        } while (!browser.getTitleText().toLowerCase().equals("portal contribuyentes cfdi | buscar cfdi"));
        //--->end login form
        return webClient;
    }


    /**
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


    /**
     * Initializes the WebClient
     * @return
     */
    private WebClient init() {

        WebClient webClient = new WebClient();

        if(hostValidator.isProxyRequired()){
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER, "proxy.autozone.com", 8080);
            DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials("edomingu", "asdEWQ123!");
        } else {
            webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER);
        }

        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);



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
