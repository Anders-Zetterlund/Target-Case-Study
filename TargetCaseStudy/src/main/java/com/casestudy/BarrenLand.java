/*
 * BarrenLand.java
 *
 * Created on June 5, 2021
 */
package com.casestudy;

/**
 * Defines a rectangle of barren land within the farm
 *
 * @author Anders Zetterlund
 */
public class BarrenLand {
    private int xLowCorner;
    private int yLowCorner;
    private int xHighCorner;
    private int yHighCorner;
        
    public BarrenLand(int xLowCorner, int yLowCorner, int xHighCorner, int yHighCorner) {
        this.xLowCorner = xLowCorner;
        this.yLowCorner = yLowCorner;
        this.xHighCorner = xHighCorner;
        this.yHighCorner = yHighCorner;
    }

    public int getXLowCorner() {
        return xLowCorner;
    }

    public void setXLowCorner(int xLowCorner) {
        this.xLowCorner = xLowCorner;
    }
    
    public int getYLowCorner() {
        return yLowCorner;
    }

    public void setYLowCorner(int yLowCorner) {
        this.yLowCorner = yLowCorner;
    }

    public int getXHighCorner() {
        return xHighCorner;
    }

    public void setXHighCorner(int xHighCorner) {
        this.xHighCorner = xHighCorner;
    }

    public int getYHighCorner() {
        return yHighCorner;
    }

    public void setYHighCorner(int yHighCorner) {
        this.yHighCorner = yHighCorner;
    }
}
