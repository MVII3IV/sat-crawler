package com.mvii3iv.sat.crawler.components.assets;

import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class HostValidator {

    private String HOST_NAME;
    private String MAGIC_WORD = "dz";

    public HostValidator(){

        try {
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public boolean isProxyRequired(){
        if(HOST_NAME.toLowerCase().contains(MAGIC_WORD))
            return true;
        else
            return false;
    }

}
