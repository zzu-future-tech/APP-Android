package com.futuretech.closet.model;

import java.util.Date;

public class SuitClass {
    private int topId;
    private int bottomId;

    public SuitClass(int topId, int bottomId) {
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
}
