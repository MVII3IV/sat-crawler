package com.mvii3iv.sat.crawler.components.assets;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class HostValidator {

    private String HOST_NAME;
    private String MAGIC_WORD = "dz";

    /**
     * Acontructor which saves the hostname in to a variable HOST_NAME
     */
    public HostValidator(){
        try {
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * this method is in charge of validate if a proxy is needed
     * the methdo is called fron different parts in the code
     * @return
     */
    public boolean isProxyRequired(){
        if(HOST_NAME.toLowerCase().contains(MAGIC_WORD))
            return true;
        else
            return false;
    }

}
