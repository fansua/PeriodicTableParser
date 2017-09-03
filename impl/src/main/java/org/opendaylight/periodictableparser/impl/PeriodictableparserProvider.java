/*
 * Copyright © 2016 Reneal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.periodictableparser.impl;

import org.opendaylight.periodictableparser.impl.PeriodicElement; 
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileReader; 
import java.io.File; 
import java.io.IOException; 
import java.io.FileOutputStream; 
import java.io.FileNotFoundException;
import java.util.List;  
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonEncoding;
import com.opencsv.bean.CsvToBeanBuilder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.commons.io.FilenameUtils; 





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

        String workingDirectory = System.getProperty("user.home");
        String jsonLocation = "/periodictableparser/impl/src/main/resources/Periodic_Table_JSON.json";
        String xmlLocation = "/periodictableparser/impl/src/main/resources/Periodic_Table_XML.xml";
        String csvLocation = "/periodictableparser/impl/src/main/resources/Periodic_Table_of_Elements.csv" ;
        String jsonFilePath = workingDirectory + jsonLocation;
        String xmlFilePath = workingDirectory + xmlLocation;

        LOG.info("PeriodictableparserProvider: Session Initiated");

    
        List<PeriodicElement> periodicElementList = parseCSV(workingDirectory + csvLocation);
        try{
            createJSONFile(jsonFilePath, periodicElementList);
        }catch(IOException e){
            e.printStackTrace();
        }
        try{
            createXMLFile(xmlFilePath, periodicElementList);
        }catch(IOException e){
         e.printStackTrace();
        } 


    }

    /**
    * This method is used to parse the csv file into java objects 
    * @pararm filePath This is a string reprsentatio of the absolute path of the file to be parsed
    * @return  List<PeriodicElement> This returns a list of the parsed data.
    */
     public static List<PeriodicElement> parseCSV(String filePath)
    {
         List<PeriodicElement> elementList = null;
         String fileExtension= "";
         File fileCheck = new File(filePath);
         if(fileCheck.exists() && !fileCheck.isDirectory() && fileCheck.canRead())
         {
            fileExtension = FilenameUtils.getExtension(filePath);
        
            if(fileExtension.equals("csv")){
                try{
                        elementList = new CsvToBeanBuilder(new FileReader(filePath)).withType(PeriodicElement.class).build().parse();
                        LOG.info("PeriodictableparserProvider: Created Java Objects");
                    }
                    catch(FileNotFoundException e){
                         e.printStackTrace();
                    }
            }
        }

        return elementList; 
    }



      /**
    * This method is used to export the java objects as an XML file
    * @pararm writeFile This is a string reprsentation of the absolute path of the file to write 
    *          XML data to
    * @param elementList This is a list of the parsed data.
    * @return void This method does not return anything. 
    */
    public static void createXMLFile(String writeFile, List<PeriodicElement> elementList) throws IOException
     { 
        if(!elementList.isEmpty()){

            Document doc = new Document(); 
            doc.setRootElement(new Element("PeriodicTable"));
            for(PeriodicElement element : elementList){

                Element ele = new Element("Element");

                ele.setAttribute("Name",""+ element.getElement());

                ele.addContent(new Element("AtomicNumber").setText(""+element.getAtomicNum()));

                ele.addContent(new Element("Symbol").setText(element.getElementSymbol()));

                ele.addContent(new Element("AtomicWeight").setText(""+element.getAtomicWeight()));

                ele.addContent(new Element("Period").setText(""+element.getPeriod()));

                ele.addContent(new Element("Group").setText(""+element.getGroup()));

                ele.addContent(new Element("Phase").setText(element.getPhase()));

                ele.addContent(new Element("MostStableCrystal").setText(element.getMstStableCrystal()));

                ele.addContent(new Element("Type").setText(element.getTypeElement()));

                ele.addContent(new Element("IonicRadius").setText(""+element.getIonicRadius()));

                ele.addContent(new Element("AtomicRadius").setText(""+element.getAtomicRadius()));

                ele.addContent(new Element("Electronegativity").setText(""+element.getElectronegativity()));

                ele.addContent(new Element("FirstIonizationPotential").setText(""+element.getFirstIonizedPotential()));

                ele.addContent(new Element("Density").setText(""+element.getDensity()));

                ele.addContent(new Element("MeltingPoint").setText(""+element.getMeltingPt()));

                ele.addContent(new Element("BoilingPoint").setText(""+element.getBoilingPt()));

                ele.addContent(new Element("Isotopes").setText(""+element.getIsotopes()));

                ele.addContent(new Element("Discoverer").setText(element.getDiscoverer()));

                ele.addContent(new Element("YearOfDiscovery").setText(""+element.getYrOfDiscovery()));

                ele.addContent(new Element("SpecificHeatCapacity").setText(""+element.getSpecifcHeatCapacity()));

                ele.addContent(new Element("ElectronConfiguration").setText(element.getElectronConfig()));

                ele.addContent(new Element("DisplayRow").setText(""+element.getDisplayRow()));

                ele.addContent(new Element("DisplayColumn").setText(""+element.getDisplayCol()));

                doc.getRootElement().addContent(ele);

            }
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(doc, new FileOutputStream(writeFile));
            
        }
    }

     /**
    * This method is used to export the java objects as an JSON file
    * @pararm writeFile This is a string reprsentation of the absolute path of the file to write 
    *          XML data to
    * @param elementList This is a list of the parsed data.
    * @return void This method does not return anything. 
    */
    public static void createJSONFile(String writeFile, List<PeriodicElement> elementList) throws IOException
    {
        if(!elementList.isEmpty()){
            
            JsonFactory jsonData = new JsonFactory(); 
            JsonGenerator writer = jsonData.createGenerator(new File(writeFile),JsonEncoding.UTF8);
            writer.writeStartArray();
            for(PeriodicElement element : elementList)
            {

                writer.writeStartObject();

                writer.writeNumberField("Atomic Number",element.getAtomicNum());

                writer.writeStringField("Element", element.getElement());

                writer.writeStringField("Symbol", element.getElementSymbol());

                writer.writeNumberField("Atomic Weight", element.getAtomicWeight());

                writer.writeNumberField("Period", element.getPeriod());

                writer.writeNumberField("Group", element.getGroup());

                writer.writeStringField("Phase", element.getPhase());

                writer.writeStringField("Most Stable Crystal", element.getMstStableCrystal());
    
                writer.writeStringField("Type", element.getTypeElement());

                writer.writeNumberField("Ionic Radius", element.getIonicRadius());

                writer.writeNumberField("Atomic Radius", element.getAtomicRadius());

                writer.writeNumberField("Electronegativity", element.getElectronegativity());

                writer.writeNumberField("First Ionization Potential", element.getFirstIonizedPotential());

                writer.writeNumberField("Density", element.getDensity());

                writer.writeNumberField("Melting Point", element.getMeltingPt());

                writer.writeNumberField("Boiling Point", element.getBoilingPt());

                writer.writeNumberField("Isotopes", element.getIsotopes());

                writer.writeStringField("Discoverer",element.getDiscoverer());

                writer.writeNumberField("Year of Discovery", element.getYrOfDiscovery());

                writer.writeNumberField("Specific Heat Capacity", element.getSpecifcHeatCapacity());

                writer.writeStringField("Electron Configuration", element.getElectronConfig());

                writer.writeNumberField("Display Row", element.getDisplayRow());

                writer.writeNumberField("Display Column", element.getDisplayCol());

                writer.writeEndObject();
            }
            writer.writeEndArray();
           
            writer.close();
            
        }

    }

    
    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("PeriodictableparserProvider Closed");
    }
}