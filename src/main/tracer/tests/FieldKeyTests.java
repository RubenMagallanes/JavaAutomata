package main.tracer.tests;


import static org.junit.Assert.*;
import main.tracer.FieldKey;

import org.junit.Test;


/**
 * Created by Shane on 21/08/15.
 */
public class FieldKeyTests {

	private String className = "ClassName";
	private String fieldName = "FieldName";

    @Test
    public void constructionTestOne(){
        FieldKey testKey = makeFieldKey();
    }

    @Test
    public void equalsTestOne(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = makeFieldKey();
    	assertTrue(one.equals(two));
    }

    @Test
    public void equalsTestTwo(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = new FieldKey(className+"NOT", fieldName+"NOT");
    	assertFalse(one.equals(two));
    }

    /**
     * Comparing of the same
     */
    @Test
    public void compareToTestOne(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = makeFieldKey();

    	assertTrue(one.compareTo(two) == 0);
    }

    /**
     * Comparing different class same name
     */
    @Test
    public void compareToTestTwo(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = new FieldKey(className+"NOT", fieldName);
    	assertTrue(one.compareTo(two) > 0 || one.compareTo(two) < 0);
    }

    /**
     * Comparing different name same class name.
     */
    @Test
    public void compareToTestThree(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = new FieldKey(className, fieldName+"NOT");
    	assertTrue(one.compareTo(two) > 0 || one.compareTo(two) < 0);
    }

    /**
     * Comparing different name and class name.
     */
    @Test
    public void compareToTestFour(){
    	FieldKey one = makeFieldKey();
    	FieldKey two = new FieldKey(className+"NOT", fieldName+"NOT");
    	assertTrue(one.compareTo(two) > 0 || one.compareTo(two) < 0);
    }

    private FieldKey makeFieldKey(){
    	return new FieldKey(className, fieldName);
    }



}
