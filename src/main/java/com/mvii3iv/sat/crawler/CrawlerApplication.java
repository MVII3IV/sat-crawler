package com.mvii3iv.sat.crawler;

import com.mvii3iv.sat.crawler.components.anticaptcha.AntiCaptchaService;
import com.mvii3iv.sat.crawler.components.browser.Browser;
import com.mvii3iv.sat.crawler.components.captcha.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class CrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrawlerApplication.class, args);
	}

}
