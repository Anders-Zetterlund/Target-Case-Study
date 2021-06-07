/*
 * BarrenLandAnalysis.java
 * 
 * Created on June 5, 2021
 */
package com.casestudy;

import java.util.*;

/**
 * Calculates size of each area of fertile land in a farm given an input of
 * rectangles of barren land.
 *
 * @author Anders Zetterlund
 */
public class BarrenLandAnalysis {
    // Constants
    private static int FARM_X = 400;
    private static int FARM_Y = 600;
    
    // Class variables
    private Field[][] farm;
    private List<BarrenLand> barrenLands;
    private List<Integer> fertileAreas;
    private int nextFieldNumber;

    public BarrenLandAnalysis() {
        farm = new Field[FARM_X][FARM_Y];
        barrenLands = new ArrayList();
        fertileAreas = new ArrayList();
        nextFieldNumber = 0;
        
        // Instantiate farm
        for (int x = 0; x < FARM_X; x++) {
            for (int y = 0; y < FARM_Y; y++) {
                farm[x][y] = new Field();
            }
        }
    }
    
    /**
     * Converts argument containing all barren land coordinates to
     * a list of each rectangle of barren land.
     *
     * @param barrenCoordinates the sets of coordinates for each rectangle of barren land
     */
    protected void parseBarrenLands(String barrenCoordinates) throws ArrayIndexOutOfBoundsException {
        String coordinatesString = barrenCoordinates.replace("{", "").replace("}", "");
        String[] coordinateParts = coordinatesString.split(",");

        List<String> barrenLandStrings = Arrays.asList(coordinateParts);
        for (String barrenLandString : barrenLandStrings) {
            String[] barrenLandParts = barrenLandString.trim().replace("\"", "").split(" ");
            barrenLands.add(new BarrenLand(
                    Integer.parseInt(barrenLandParts[0]), 
                    Integer.parseInt(barrenLandParts[1]), 
                    Integer.parseInt(barrenLandParts[2]), 
                    Integer.parseInt(barrenLandParts[3])));
        }
    }
    
    /**
     * Sets each field in the farm that is within the barrenLands list to not fertile.
     */
    protected void insertBarrenLands() {
        for (BarrenLand bl : barrenLands) {
            for (int x = bl.getXLowCorner(); x <= bl.getXHighCorner(); x++) {
                for (int y = bl.getYLowCorner(); y <= bl.getYHighCorner(); y++) {
                    farm[x][y].setFertile(false);
                }
            }
        }
    }
    
    /**
     * Updates a field with the fieldNumber of an adjacent field and updates 
     * the fertileArea of the fieldNumber
     *
     * @param curX X coordinate for current field
     * @param curY Y coordinate for current field
     * @param adjX X coordinate for adjacent field
     * @param adjY Y coordinate for adjacent field
     */
    protected void updateField(int curX, int curY, int adjX, int adjY) {
        int fieldNumber = farm[adjX][adjY].getFieldNumber();
        farm[curX][curY].setFieldNumber(fieldNumber);
        fertileAreas.set(fieldNumber, fertileAreas.get(fieldNumber) + 1);
    }
    
    /**
     * Updates fertileAreas with the area of each continuous area of fertile land, 
     * sorted from smallest to largest.
     */
    protected void calculateFertileAreas() {
        fertileAreas.clear();
        
        for (int x = 0; x < FARM_X; x++) {
            for (int y = 0; y < FARM_Y; y++) {
                if (farm[x][y].isFertile()) {
                    if ((x == 0 || !farm[x-1][y].isFertile()) && (y == 0 || !farm[x][y-1].isFertile())) {
                        // Create new field if both the left and lower fields are barren or the farm edge
                        farm[x][y].setFieldNumber(nextFieldNumber++);
                        fertileAreas.add(1);
                    } else if (x == 0 || !farm[x-1][y].isFertile()) {
                        // Set field number to the lower field if the left field is barren or the farm edge
                        updateField(x, y, x, y-1);
                    } else if (y == 0 || !farm[x][y-1].isFertile()) {
                        // Set field number to the left field if the lower field is barren or the farm edge
                        updateField(x, y, x-1, y);
                    } else if (farm[x-1][y].isFertile() && farm[x][y-1].isFertile()) {
                        // When both adjacent fields are fertile, check if they are part of the same field already
                        if (farm[x-1][y].getFieldNumber() == farm[x][y-1].getFieldNumber()) {
                            // When both adjacent fields are already part of the same area, make current field part of that area
                            updateField(x, y, x-1, y);
                        } else {
                            // If not, update all fields of the higher field number to the lower number, combine areas
                            int lowField = farm[x-1][y].getFieldNumber() < farm[x][y-1].getFieldNumber() ? farm[x-1][y].getFieldNumber() : farm[x][y-1].getFieldNumber();
                            int highField = farm[x-1][y].getFieldNumber() > farm[x][y-1].getFieldNumber() ? farm[x-1][y].getFieldNumber() : farm[x][y-1].getFieldNumber();

                            fertileAreas.set(lowField, fertileAreas.get(lowField) + fertileAreas.get(highField) + 1);
                            fertileAreas.set(highField, 0);

                            for (int x2 = 0; x2 < FARM_X; x2++) {
                                for (int y2 = 0; y2 < FARM_Y; y2++) {
                                    if (farm[x2][y2].getFieldNumber() == highField) {
                                        farm[x2][y2].setFieldNumber(lowField);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        fertileAreas.removeIf(n -> (n == 0));
        Collections.sort(fertileAreas);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        BarrenLandAnalysis bla = new BarrenLandAnalysis();
        
        // Request input of barren land coordinates and parse them
        String barrenCoordinates = System.console().readLine("Input barren land coordinates: ");
        try {
            bla.parseBarrenLands(barrenCoordinates);
        
            // Add barren land and calculate the fertile areas
            bla.insertBarrenLands();
            bla.calculateFertileAreas();

            // Output fertile areas
            for (Integer area : bla.fertileAreas) {
                System.out.print(area + " ");
            }
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error in input values. Please input in the format {\"XLow YLow XHigh YHigh\", \"XLow2 YLow2 XHigh2 YHigh2\", ...}");
        }
    }

    public Field[][] getFarm() {
        return farm;
    }

    public void setFarm(Field[][] farm) {
        this.farm = farm;
    }

    public List<BarrenLand> getBarrenLands() {
        return barrenLands;
    }

    public void setBarrenLands(List<BarrenLand> barrenLands) {
        this.barrenLands = barrenLands;
    }

    public List<Integer> getFertileAreas() {
        return fertileAreas;
    }

    public void setFertileAreas(List<Integer> fertileAreas) {
        this.fertileAreas = fertileAreas;
    }

    public int getNextFieldNumber() {
        return nextFieldNumber;
    }

    public void setNextFieldNumber(int nextFieldNumber) {
        this.nextFieldNumber = nextFieldNumber;
    }
}
