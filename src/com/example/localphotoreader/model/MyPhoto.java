package com.example.localphotoreader.model;

public class MyPhoto {
    private String path;
    private String displayName;
    private String  type;
    private String title;
    private int size;
    private int width;
    private int height;
    private String pathDir;
    
    public String getPathDir() {
        return pathDir;
    }

    public void setPathDir(String pathDir) {
        this.pathDir = pathDir;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "MyPhoto [path=" + path + ", displayName=" + displayName + ", type=" + type
                + ", title=" + title + ", size=" + size + ", width=" + width + ", height=" + height
                + "]";
    }
    
    
}
