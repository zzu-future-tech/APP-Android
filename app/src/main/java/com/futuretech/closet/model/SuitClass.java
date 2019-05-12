package com.futuretech.closet.model;

public class SuitClass {
    private int id;
    private int topId;
    private int bottomId;

    public SuitClass(int topId, int bottomId) {
        this.topId = topId;
        this.bottomId = bottomId;
    }
    public SuitClass(int id, int topId, int bottomId) {
        this.id = id;
        this.topId = topId;
        this.bottomId = bottomId;
    }

    public int getTopId() {
        return topId;
    }

    public void setTopId(int topId) {
        this.topId = topId;
    }

    public int getBottomId() {
        return bottomId;
    }

    public void setBottomId(int bottomId) {
        this.bottomId = bottomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
