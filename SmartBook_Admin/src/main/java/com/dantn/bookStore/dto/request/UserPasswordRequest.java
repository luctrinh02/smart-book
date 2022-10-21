package com.dantn.bookStore.dto.request;

public class UserPasswordRequest {
    private String oldPass;
    private String newPass;
    private String confirm;
    public String getOldPass() {
        return oldPass;
    }
    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
    public String getNewPass() {
        return newPass;
    }
    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    public String getConfirm() {
        return confirm;
    }
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
}
