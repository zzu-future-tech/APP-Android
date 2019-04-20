package com.futuretech.closet.model;

public class ClothesOne {
    private String name;
    private int imageSrc;

    public String getName() {
        return name;
    }

    public int getImageSrc() {
        return imageSrc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageSrc(int imageSrc){this.imageSrc=imageSrc;}

    public ClothesOne(String name,int imageSrc) {
        this.name = name;
        this.imageSrc=imageSrc;
    }
}
