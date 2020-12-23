package com.lcb.augustthree;

/**
 * created by: Eroch
 * time: 2020/8/23
 * introduce:
 */
class SearchBean {

    private String title,content,time;
    private boolean isSelect;

    public SearchBean(String title, String content, String time) {
        this.title = title;
        this.content = content;
        this.time = time;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}


