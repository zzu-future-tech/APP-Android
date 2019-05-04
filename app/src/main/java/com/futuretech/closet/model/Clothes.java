package com.futuretech.closet.model;

public class Clothes {
    private int dressid;
    private String style;
    private String color;
    private String thickness;
    private String attribute;

    public Clothes(int dressid, String style, String color, String thickness, String attribute) {
        this.dressid = dressid;
        this.style = style;
        this.color = color;
        this.thickness = thickness;
        this.attribute = attribute;
    }

    public int getDressid() {
        return dressid;
    }

    public void setDressid(int dressid) {
        this.dressid = dressid;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
