package com.mvii3iv.sat.crawler.components.bills;


import org.springframework.data.annotation.Id;

public class Bills {

    @Id
    private String fiscalId;
    private String userId;
    private String emisorRFC;
    private String emisorName;
    private String receiverRFC;
    private String receiverName;
    private String emitedDate;
    private String certificationDate;
    private String certifiedPAC;
    private String total;
    private String voucherEffect;
    private String cancelationStatus;
    private String voucherStatus;
    private String cancelationProcessStatus;
    private String cancelationProcessDate;
    private boolean emited;
    private boolean edited;


    @Override
    public String toString(){
        return String.format("Bills[fiscalId=%s, userId=%s, emisorRFC='%s', emisorName='%s', receiverRFC='%s', emitedDate='%s', certificationDate='%s', certifiedPAC='%s', total='%s', voucherEffect='%s', cancelationStatus='%s', voucherStatus='%s', cancelationProcessStatus='%s', cancelationProcessDate='%s', emited='%b', edited='%b']",
                fiscalId, emisorRFC, emisorName, receiverRFC, receiverName, emitedDate, certificationDate, certifiedPAC, total, voucherEffect, cancelationStatus, voucherStatus, cancelationProcessStatus, cancelationProcessDate, emited, edited);
    }

    public Bills(){

    }

    public Bills(String fiscalId, String userId, String emisorRFC, String emisorName, String receiverRFC, String receiverName, String emitedDate, String certificationDate, String certifiedPAC, String total, String voucherEffect, String cancelationStatus, String voucherStatus, String cancelationProcessStatus, String cancelationProcessDate, boolean emited, boolean edited) {
        this.fiscalId = fiscalId;
        this.userId = userId;
        this.emisorRFC = emisorRFC;
        this.emisorName = emisorName;
        this.receiverRFC = receiverRFC;
        this.receiverName = receiverName;
        this.emitedDate = emitedDate;
        this.certificationDate = certificationDate;
        this.certifiedPAC = certifiedPAC;
        this.total = total;
        this.voucherEffect = voucherEffect;
        this.cancelationStatus = cancelationStatus;
        this.voucherStatus = voucherStatus;
        this.cancelationProcessStatus = cancelationProcessStatus;
        this.cancelationProcessDate = cancelationProcessDate;
        this.emited = emited;
        this.edited = edited;
    }

    public String getFiscalId() {
        return fiscalId;
    }

    public void setFiscalId(String fiscalId) {
        this.fiscalId = fiscalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmisorRFC() {
        return emisorRFC;
    }

    public void setEmisorRFC(String emisorRFC) {
        this.emisorRFC = emisorRFC;
    }

    public String getEmisorName() {
        return emisorName;
    }

    public void setEmisorName(String emisorName) {
        this.emisorName = emisorName;
    }

    public String getReceiverRFC() {
        return receiverRFC;
    }

    public void setReceiverRFC(String receiverRFC) {
        this.receiverRFC = receiverRFC;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getEmitedDate() {
        return emitedDate;
    }

    public void setEmitedDate(String emitedDate) {
        this.emitedDate = emitedDate;
    }

    public String getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(String certificationDate) {
        this.certificationDate = certificationDate;
    }

    public String getCertifiedPAC() {
        return certifiedPAC;
    }

    public void setCertifiedPAC(String certifiedPAC) {
        this.certifiedPAC = certifiedPAC;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getVoucherEffect() {
        return voucherEffect;
    }

    public void setVoucherEffect(String voucherEffect) {
        this.voucherEffect = voucherEffect;
    }

    public String getCancelationStatus() {
        return cancelationStatus;
    }

    public void setCancelationStatus(String cancelationStatus) {
        this.cancelationStatus = cancelationStatus;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public String getCancelationProcessStatus() {
        return cancelationProcessStatus;
    }

    public void setCancelationProcessStatus(String cancelationProcessStatus) {
        this.cancelationProcessStatus = cancelationProcessStatus;
    }

    public String getCancelationProcessDate() {
        return cancelationProcessDate;
    }

    public void setCancelationProcessDate(String cancelationProcessDate) {
        this.cancelationProcessDate = cancelationProcessDate;
    }

    public boolean isEmited() {
        return emited;
    }

    public void setEmited(boolean emited) {
        this.emited = emited;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }
}
