/*
 * Copyright © 2016 Reneal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodictableparser.impl;

import java.io.FileReader;
import java.io.File;  
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.util.List;  

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonEncoding;

import org.opendaylight.periodictableparser.impl.PeriodicElement; 
import com.opencsv.CSVReader; 
import com.opencsv.bean.ColumnPositionMappingStrategy; 
import com.opencsv.bean.HeaderColumnNameMappingStrategy; 
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder; 
//import com.google.gson.stream.JsonWriter; 

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;



public class PeriodictableparserProvider {

    private static final Logger LOG = LoggerFactory.getLogger(PeriodictableparserProvider.class);

    private final DataBroker dataBroker;

    public PeriodictableparserProvider(final DataBroker dataBroker) {
        this.dataBroker = dataBroker;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {

        LOG.info("PeriodictableparserProvider Session Initiated");
        String jsonFilePath ="src/main/resources/Periodic_Table_JSON.json";
        String xmlFilePath ="src/main/resources/Periodic_Table_XML.xml";

        // Call the CSV parser function and return  list of the Java objects 
        List<PeriodicElement> periodicElements = parseCSV("periodictableparserPeriodic_Table_of_Elements.csv");
        try{
            writeJSONFile(jsonFilePath, periodicElements);
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            writeXMLFile(xmlFilePath, periodicElements);
        }catch(IOException e){
         e.printStackTrace();
        } 


    }

    private List<PeriodicElement> parseCSV(String filename)
    {
        Reader reader = null; 
        List<PeriodicElement> elements= null; 
        try{
              
                reader = new BufferedReader(new FileReader(filename));
                ColumnPositionMappingStrategy<PeriodicElement> colStrategy = new ColumnPositionMappingStrategy<PeriodicElement>();
                elements = new CsvToBeanBuilder(reader).withType(PeriodicElement.class).build().parse();
                 LOG.info("Java Objects have been created");
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally{
                if(reader != null){
                    try{
                        reader.close();

                    }
                    catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            
        return elements; 
    }

     
    private void writeXMLFile(String writeFile, List<PeriodicElement> elements) throws IOException
     { 
        Document doc = new Document(); 
        doc.setRootElement(new Element("PeriodicTable"));
        for(PeriodicElement element : elements){
                Element ele = new Element("Element");
                ele.setAttribute("Name",""+element.getAtomicNum());
                ele.addContent(new Element("AtomicNumber").setText(element.getAtomicNum()));
                ele.addContent(new Element("Element").setText(element.getElement()));
                ele.addContent(new Element("Symbol").setText(element.getElementSymbol()));
                ele.addContent(new Element("AtomicWeight").setText(element.getAtomicWeight()));
                ele.addContent(new Element("Period").setText(element.getPeriod()));
                ele.addContent(new Element("Group").setText(element.getGroup()));
                ele.addContent(new Element("Phase").setText(element.getPhase()));
                ele.addContent(new Element("MostStableCrystal").setText(element.getMstStableCrystal()));
                ele.addContent(new Element("Type").setText(element.getTypeElement()));
                ele.addContent(new Element("IonicRadius").setText(element.getIonicRadius()));
                ele.addContent(new Element("AtomicRadius").setText(element.getAtomicRadius()));
                ele.addContent(new Element("Electronegativity").setText(element.getElectronegativity()));
                ele.addContent(new Element("FirstIonizationPotential").setText(element.getFirstIonizedPotential()));
                ele.addContent(new Element("Density").setText(element.getDensity()));
                ele.addContent(new Element("MeltingPoint").setText(element.getMeltingPt()));
                ele.addContent(new Element("BoilingPoint").setText(element.getBoilingPt()));
                ele.addContent(new Element("Isotopes").setText(element.getIsotopes()));
                ele.addContent(new Element("Discoverer").setText(element.getDiscoverer()));
                ele.addContent(new Element("YearOfDiscovery").setText(element.getYrOfDiscovery()));
                ele.addContent(new Element("SpecificHeatCapacity").setText(element.getSpecifcHeatCapacity()));
                ele.addContent(new Element("ElectronConfiguration").setText(element.getElectronConfig()));
                ele.addContent(new Element("DisplayRow").setText(element.getDisplayRow()));
                ele.addContent(new Element("DisplayColumn").setText(element.getDisplayCol()));
                doc.getRootElement().addContent(ele);

        }
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(doc, new FileOutputStream(writeFile));
    }

    private void writeJSONFile(String writeFile, List<PeriodicElement> elements) throws IOException
    {
             JsonFactory jsonData = new JsonFactory(); 
             JsonGenerator writer = jsonData.createGenerator(new File(writeFile),JsonEncoding.UTF8);
            for(PeriodicElement element : elements){
                writer.writeStartObject();
                writer.writeStringField("AtomicNumber",element.getAtomicNum());
                writer.writeStringField("Element", element.getElement());
                writer.writeStringField("Symbol", element.getElementSymbol());
                writer.writeStringField("AtomicWeight", element.getAtomicWeight());
                writer.writeStringField("Period", element.getPeriod());
                writer.writeStringField("Group", element.getGroup());
                writer.writeStringField("Phase", element.getPhase());
                writer.writeStringField("MostStableCrystal", element.getMstStableCrystal());
                writer.writeStringField("Type", element.getTypeElement());
                writer.writeStringField("IonicRadius", element.getIonicRadius());
                writer.writeStringField("AtomicRadius", element.getAtomicRadius());
                writer.writeStringField("Electronegativity", element.getElectronegativity());
                writer.writeStringField("FirstIonizationPotential", element.getFirstIonizedPotential());
                writer.writeStringField("Density", element.getDensity());
                writer.writeStringField("MeltingPoint", element.getMeltingPt());
                writer.writeStringField("BoilingPoint", element.getBoilingPt());
                writer.writeStringField("Isotopes", element.getIsotopes());
                writer.writeStringField("Discoverer",element.getDiscoverer());
                writer.writeStringField("YearOfDiscovery", element.getYrOfDiscovery());
                writer.writeStringField("SpecificHeatCapacity", element.getSpecifcHeatCapacity());
                writer.writeStringField("ElectronConfiguration", element.getElectronConfig());
                writer.writeStringField("DisplayRow", element.getDisplayRow());
                writer.writeStringField("DisplayColumn", element.getDisplayCol());
                writer.writeEndObject();
            }
            
        writer.close();

    }

    
    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("PeriodictableparserProvider Closed");
    }
}