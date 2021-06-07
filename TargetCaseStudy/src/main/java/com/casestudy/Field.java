/*
 * Field.java
 *
 * Created on June 5, 2021
 */
package com.casestudy;

/**
 * Defines each section of the farm
 *
 * @author Anders Zetterlund
 */
public class Field {
    private boolean fertile;
    private int fieldNumber;
    
    public Field() {
        fertile = true;
    }

    public boolean isFertile() {
        return fertile;
    }

    public void setFertile(boolean fertile) {
        this.fertile = fertile;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }
    
    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }
}
