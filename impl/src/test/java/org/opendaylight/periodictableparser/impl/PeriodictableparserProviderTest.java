/*
 * Copyright © 2016 Reneal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodictableparser.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.opendaylight.periodictableparser.impl.PeriodicElement; 
import org.opendaylight.periodictableparser.impl.PeriodictableparserProvider; 
import java.io.File; 

import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;  

public class PeriodictableparserProviderTest {

        List<PeriodicElement> periodicElementList;
        List<Element> lstG; 
        List<Element> lstE;

        private String workingDirectory;
        private String csvLocation;

        private String locationGJSON;
        private String locationEJSON;
        private String jsonGPath;
        private String jsonEPath;

         private String locationGXML;
        private String locationEXML;
        private String xmlGPath;
        private String xmlEPath;
        private String noFile; 
        private String invalidFile; 


    @Before
    public void setUp() throws Exception {

    	workingDirectory = System.getProperty("user.home");
    	csvLocation = "/periodictableparser/impl/src/main/resources/Periodic_Table_of_Elements.csv" ;

    	locationGJSON = "/periodictableparser/impl/src/main/resources/testData/generated_JSON.json";
    	locationEJSON = "/periodictableparser/impl/src/main/resources/testData/expected_json.json";
        jsonGPath = workingDirectory + locationGJSON;
        jsonEPath = workingDirectory + locationEJSON;

        locationGXML = "/periodictableparser/impl/src/main/resources/testData/generated_XML.xml";
    	locationEXML = "/periodictableparser/impl/src/main/resources/testData/expected_xml.xml";
        xmlGPath = workingDirectory + locationGXML;
        xmlEPath = workingDirectory + locationEXML;
        noFile = workingDirectory + "/periodictableparser/impl/src/main/resources/foo.csv";
        invalidFile = workingDirectory + locationEJSON; 
    	
    	List<PeriodicElement> periodicElementList = PeriodictableparserProvider.parseCSV(workingDirectory + csvLocation);
    	PeriodictableparserProvider.createJSONFile(jsonGPath, periodicElementList);
    	PeriodictableparserProvider.createXMLFile(xmlGPath, periodicElementList);

    }


    @Test (expected = NullPointerException.class)
    public void inValidFileType() throws Exception {

       Boolean emptyL = false; 
       List<PeriodicElement> emptyList = PeriodictableparserProvider.parseCSV(invalidFile);
       if(emptyList.isEmpty())
           emptyL = true; 

       assertTrue(emptyL);

   }

   @Test (expected = NullPointerException.class)
    public void nonExistFile() throws Exception {

       Boolean emptyL = false; 
       List<PeriodicElement> emptyList = PeriodictableparserProvider.parseCSV(noFile);
       if(emptyList.isEmpty())
           emptyL = true; 

       assertTrue(emptyL);

   }

    @Test
    public  void correctJSONCreated() throws Exception {

    	
      Boolean match = false; 
      int count = 0; 
      ObjectMapper periodicMapper = new ObjectMapper(); 
      List<PeriodicElement> jsonListG  = periodicMapper.readValue(new File(jsonGPath),new TypeReference<List<PeriodicElement>>(){});
      List<PeriodicElement> jsonListE  = periodicMapper.readValue(new File(jsonEPath),new TypeReference<List<PeriodicElement>>(){});

      	if(jsonListE.size() == jsonListG.size()){

      		for(int i =0; i< jsonListE.size(); i++)
      		{
      			if(jsonListG.get(i).getElement().equals(jsonListE.get(i).getElement()))
      				count++; 

      		}
      		if(count == jsonListE.size())
      			match = true; 
      	}

        assertTrue(match);
    }

    @Test
    public  void correctXMLCreated() throws Exception {
    	Boolean match = false; 
    	int count = 0; 

    	SAXBuilder xmlBuilder = new SAXBuilder(); 
    	File xmlGFile = new File(xmlGPath);
    	File xmlEFile = new File(xmlEPath); 

    	Document docG =  (Document) xmlBuilder.build(xmlGFile);
    	Document docE =  (Document) xmlBuilder.build(xmlEFile);
    	Element rootGNode = docG.getRootElement(); 
    	Element rootENode = docE.getRootElement(); 
    	lstG = rootGNode.getChildren("Element");
    	lstE = rootENode.getChildren("Element");

    	if(lstG.size() == lstE.size()){
    		for(int i =0; i< lstE.size(); i++)
    		{
    			Element nodeG = (Element) lstG.get(i); 
    			Element nodeE = (Element) lstE.get(i); 
    			if(nodeG.getChildText("Symbol").equals(nodeE.getChildText("Symbol")))
    				count++; 
    		}
    		if(count == lstG.size())
    			match = true;
    	}
    	assertTrue(match); 
    }
    

}