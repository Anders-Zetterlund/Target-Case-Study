/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casestudy;

import java.io.ByteArrayInputStream;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Anders Zetterlund
 */
public class BarrenLandAnalysisTest {
    
    public BarrenLandAnalysisTest() {
    }

    /**
     * Test of parseBarrenLands method, of class BarrenLandAnalysis.
     */
    @Test
    public void testParseBarrenLands() {        
        List<BarrenLand> expected = new ArrayList();
        expected.add(new BarrenLand(48, 192, 351, 207));
        expected.add(new BarrenLand(48, 392, 351, 407));
        expected.add(new BarrenLand(120, 52, 135, 547));
        expected.add(new BarrenLand(260, 52, 275, 547));
        
        BarrenLandAnalysis bla = new BarrenLandAnalysis();
        bla.parseBarrenLands("{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}");
        
        for (int i = 0; i < bla.getBarrenLands().size(); i++) {
            assertEquals(expected.get(i).getXLowCorner(), bla.getBarrenLands().get(i).getXLowCorner());
            assertEquals(expected.get(i).getYLowCorner(), bla.getBarrenLands().get(i).getYLowCorner());
            assertEquals(expected.get(i).getXHighCorner(), bla.getBarrenLands().get(i).getXHighCorner());
            assertEquals(expected.get(i).getYHighCorner(), bla.getBarrenLands().get(i).getYHighCorner());
        }
    }

    /**
     * Test of calculateFertileAreas method, of class BarrenLandAnalysis.
     */
    @Test
    public void testCalculateFertileAreas() {
        String expected = "[22816, 192608]";
        
        BarrenLandAnalysis bla = new BarrenLandAnalysis();
        bla.parseBarrenLands("{\"48 192 351 207\", \"48 392 351 407\", \"120 52 135 547\", \"260 52 275 547\"}");
        bla.insertBarrenLands();
        bla.calculateFertileAreas();
        
        assertEquals(expected, bla.getFertileAreas().toString());
    }
}
