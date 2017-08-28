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
import com.google.gson.stream.JsonWriter; 

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.jdom2.Document;
//import org.jdom2.Element;
//import org.jdom2.output.Format;
//import org.jdom2.output.XMLOutputter;



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

        // Call the CSV parser function and return  list of the Java objects 
        List<PeriodicElement> periodicElements = parseCSV("src/main/resources/periodictableparserPeriodic_Table_of_Elements.csv");
        try{
            writeJSONFile("Periodic_Table_JSON.json", periodicElements);
        }catch(IOException e){
            e.printStackTrace();
        } 
        try{
            writeXMLFile("Periodic_Table_XML.xml", periodicElements);
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

     private void writeJSONFile(String writeFile, List<PeriodicElement> elements) throws IOException
     { 
        JsonWriter writer = new JsonWriter(new FileWriter(writeFile));
        writer.setIndent("  ");
        writeElementsArray(writer,elements);
        writer.close();

    }

    private void writeElementsArray(JsonWriter writer, List<PeriodicElement> elements) throws IOException {

        writer.beginArray(); 
        for(PeriodicElement element : elements)
            writeElement(writer, element);
        writer.endArray(); 
    }
    private void writeElement(JsonWriter writer, PeriodicElement element) throws IOException{
        writer.beginObject(); 
        writer.name("AtomicNumber").value(element.getAtomicNum());
        writer.name("Element").value(element.getElement());
        writer.name("Symbol").value(element.getElementSymbol());
        writer.name("AtomicWeight").value(element.getAtomicWeight());
        writer.name("Period").value(element.getPeriod());
        writer.name("Group").value(element.getGroup());
        writer.name("Phase").value(element.getPhase());
        writer.name("MostStableCrystal").value(element.getMstStableCrystal());
        writer.name("Type").value(element.getTypeElement());
        writer.name("IonicRadius").value(element.getIonicRadius());
        writer.name("AtomicRadius").value(element.getAtomicRadius());
        writer.name("Electronegativity").value(element.getElectronegativity());
        writer.name("FirstIonizationPotential").value(element.getFirstIonizedPotential());
        writer.name("Density").value(element.getDensity());
        writer.name("MeltingPoint").value(element.getMeltingPt());
        writer.name("BoilingPoint").value(element.getBoilingPt());
        writer.name("Isotopes").value(element.getIsotopes());
        writer.name("Discoverer").value(element.getDiscoverer());
        writer.name("YearOfDiscovery").value(element.getYrOfDiscovery());
        writer.name("SpecificHeatCapacity").value(element.getSpecifcHeatCapacity());
        writer.name("ElectronConfiguration").value(element.getElectronConfig());
        writer.name("DisplayRow").value(element.getDisplayRow());
        writer.name("DisplayColumn").value(element.getDisplayCol());
        writer.endObject(); 
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
    
    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("PeriodictableparserProvider Closed");
    }
}