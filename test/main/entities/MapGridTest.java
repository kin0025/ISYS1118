package main.entities;
import static org.junit.Assert.*;
import main.entities.intersection.Intersection;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;


public class MapGridTest {
	
	@Before
    public void createMapGrid() throws Exception{
		        grid = new Intersection[3][3];
		        
 }

    @After
    public void closeMapGrid() {
    	grid = null;
    }
    
    @Test
    public void addIntersection(){
    }

    @Test
    public void getWidth() {
    }

    @Test
    public void getHeight() {
    }
    
    @Test
    public void fillRoads(){
    }