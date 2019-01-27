package org.sadtech.vkbot.core.sender;

public class MailSend {

    private String message;
    private String keyboard;
    private Integer idRecipient;
    private Float lat;
    private Float aLong;

    public MailSend() {

    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(Integer idRecipient) {
        this.idRecipient = idRecipient;
    }

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getaLong() {
        return aLong;
    }

    public void setaLong(Float aLong) {
        this.aLong = aLong;
    }
}
