package com.example.push_app;

import android.util.Log;

import androidx.annotation.Nullable;

public class alert_getset {
    String msgTag, date, title, content, opend, mode, imgCheck, imgUrl, labelCode, link, customKeyCheck;

    public alert_getset(String msgTag, String date, String title, String content, String opend, String mode, String imgCheck, String imgUrl, String labelCode, String link, String customKeyCheck) {
        this.msgTag = msgTag;
        this.date = date;
        this.title = title;
        this.content = content;
        this.opend = opend;
        this.mode = mode;
        this.imgCheck = imgCheck;
        this.imgUrl = imgUrl;
        this.labelCode = labelCode;
        this.link = link;
        this.customKeyCheck = customKeyCheck;

    }

    public String getMsgTag() {
        return msgTag;
    }

    public void setMsgTag(String msgTag) {
        this.msgTag = msgTag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpend() {
        return opend;
    }

    public void setOpend(String opend) {
        this.opend = opend;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(String imgCheck) {
        this.imgCheck = imgCheck;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLabelCode() {
        return labelCode;
    }

    public void setLabelCode(String labelCode) {
        this.labelCode = labelCode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCustomKeyCheck() {
        return customKeyCheck;
    }

    public void setCustomKeyCheck(String customKeyCheck) {
        this.customKeyCheck = customKeyCheck;
    }
}
