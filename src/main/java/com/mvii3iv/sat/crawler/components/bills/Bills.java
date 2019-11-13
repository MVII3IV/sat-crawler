package com.mvii3iv.sat.crawler.components.bills;


import org.springframework.data.annotation.Id;

public class Bills {
    private String _id;
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
    public String toString() {
        return "Bills{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", emisorRFC='" + emisorRFC + '\'' +
                ", emisorName='" + emisorName + '\'' +
                ", receiverRFC='" + receiverRFC + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", emitedDate='" + emitedDate + '\'' +
                ", certificationDate='" + certificationDate + '\'' +
                ", certifiedPAC='" + certifiedPAC + '\'' +
                ", total='" + total + '\'' +
                ", voucherEffect='" + voucherEffect + '\'' +
                ", cancelationStatus='" + cancelationStatus + '\'' +
                ", voucherStatus='" + voucherStatus + '\'' +
                ", cancelationProcessStatus='" + cancelationProcessStatus + '\'' +
                ", cancelationProcessDate='" + cancelationProcessDate + '\'' +
                ", emited=" + emited +
                ", edited=" + edited +
                '}';
    }

    public Bills() {
    }

    public Bills(String userId, String emisorRFC, String emisorName, String receiverRFC, String receiverName, String emitedDate, String certificationDate, String certifiedPAC, String total, String voucherEffect, String cancelationStatus, String voucherStatus, String cancelationProcessStatus, String cancelationProcessDate, boolean emited, boolean edited) {
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
