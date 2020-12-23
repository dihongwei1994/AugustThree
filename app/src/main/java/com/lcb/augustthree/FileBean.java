package com.lcb.augustthree;

import java.io.File;

/**
 * created by: Eroch
 * time: 2020/8/25
 * introduce:
 */
class FileBean {
    private String title;
    private File file;

    public FileBean(String title, File file) {
        this.title = title;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
