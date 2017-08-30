/*
 * Copyright © 2016 Reneal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.periodictableparser.impl;


import com.opencsv.bean.CsvBindByName;

public class PeriodicElement 
{
    @CsvBindByName(column = "Atomic Number")
    private int atomicNum;

   @CsvBindByName(column = "Period")
    private int period;

    @CsvBindByName(column = "Group")
    private int group;

    @CsvBindByName(column = "Isotopes")
    private int isotopes;

    @CsvBindByName(column = "Year of Discovery")
    private int yrOfDiscovery;

    @CsvBindByName(column = "Display Row")
    private int displayRow;

    @CsvBindByName(column = "Display Column")
    private int displayCol;

    @CsvBindByName(column = "Element")
    private String element;

    @CsvBindByName(column = "Phase")
    private String phase;

    @CsvBindByName(column = "Most Stable Crystal")
    private String mstStableCrystal;

    @CsvBindByName(column = "Type")
    private String typeElement;

    @CsvBindByName(column = "Ionic Radius")
    private float ionicRadius;

    @CsvBindByName(column = "Discoverer")
    private String discoverer;

    @CsvBindByName(column = "Electron Configuration")
    private String electronConfig;

    @CsvBindByName(column = "Atomic Weight")
    private float atomicWeight;

    @CsvBindByName(column = "Atomic Radius")
    private float atomicRadius;

    @CsvBindByName(column = "Electronegativity")
    private float electronegativity;

    @CsvBindByName(column = "First Ionization Potential")
    private float firstIonizedPotential;

    @CsvBindByName(column = "Density")
    private float density;

    @CsvBindByName(column = "Melting Point (K)")
    private float meltingPt;

    @CsvBindByName(column = "Boiling Point (K)")
    private float boilingPt;

    @CsvBindByName(column = "Specific Heat Capacity")
    private float specifcHeatCapacity; 

    @CsvBindByName(column = "Symbol")
    private String elementSymbol; 


    public int getAtomicNum(){
        return atomicNum; 
    }
    public void setAtomicNum(int atomicNum){
        this.atomicNum = atomicNum; 
    }
    public int getPeriod(){
        return period;
    }
    public void setPeriod(int period){
        this.period = period; 
    }
    public int getGroup(){
        return group;
    }
    public void setGroup(int group){
        this.group = group; 
    }
    public int getIsotopes(){
        return isotopes;  
    }
    public void setIsotopes(int isotopes){
        this.isotopes = isotopes;
    }
    public int getYrOfDiscovery(){
        return yrOfDiscovery;
    }
    public void setYrOfDiscovery(int yrOfDiscovery){
        this.yrOfDiscovery = yrOfDiscovery; 
    }
    public int getDisplayRow(){
        return displayRow; 
    } 
    public void setDisplayRow(int displayRow){
        this.displayRow = displayRow; 
    }
    public int getDisplayCol(){
        return displayCol;
    }
    public void setDisplayCol(int displayCol){
        this.displayCol = displayCol; 
    }
    
    public float getAtomicWeight(){
        return atomicWeight; 
    }
    public void setAtomicWeight(float atomicWeight){
        this.atomicWeight = atomicWeight; 
    }
    public float getAtomicRadius(){
        return atomicRadius;
    }
    public void setAtomicRadius(float atomicRadius){
        this.atomicRadius = atomicRadius; 
    }
    public float getElectronegativity(){
        return electronegativity;
    }
    public void setElectronegativity(float electronegativity){
        this.electronegativity = electronegativity; 
    }
    public float getFirstIonizedPotential(){
        return firstIonizedPotential;  
    }
    public void setFirstIonizedPotential(float firstIonizedPotential){
        this.firstIonizedPotential = firstIonizedPotential;
    }
    public float getDensity(){
        return density;
    }
    public void setDensity(float density){
        this.density = density; 
    }
    public float getMeltingPt(){
        return meltingPt; 
    } 
    public void setMeltingPt(float meltingPt){
        this.meltingPt = meltingPt; 
    }
    public float getBoilingPt(){
        return boilingPt;
    }
    public void setBoilingPt(float boilingPt){
        this.boilingPt = boilingPt; 
    }
     public float getSpecifcHeatCapacity(){
        return specifcHeatCapacity;
    }
    public void setSpecifcHeatCapacity(float specifcHeatCapacity){
        this.specifcHeatCapacity = specifcHeatCapacity; 
    }
     public String getElementSymbol(){
        return elementSymbol;
    }
    public void setElementSymbol(String elementSymbol){
        this.elementSymbol = elementSymbol; 
    }
    
    public String getElement(){
        return element; 
    }
    public void setElement(String element){
        this.element = element; 
    }
    public String getPhase(){
        return phase;
    }
    public void setPhase(String phase){
        this.phase = phase; 
    }
    public String getMstStableCrystal(){
        return mstStableCrystal;
    }
    public void setMstStableCrystal(String mstStableCrystal){
        this.mstStableCrystal = mstStableCrystal; 
    }
    public String getTypeElement(){
        return typeElement;  
    }
    public void setTypeElement(String typeElement){
        this.typeElement = typeElement;
    }
    public float getIonicRadius(){
        return ionicRadius;
    }
    public void setIonicRadius(float ionicRadius){
        this.ionicRadius = ionicRadius; 
    }
    public String getDiscoverer(){
        return discoverer; 
    } 
    public void setDiscoverer(String discoverer){
        this.discoverer = discoverer; 
    }
    public String getElectronConfig(){
        return electronConfig;
    }
    public void setElectronConfig(String electronConfig){
        this.electronConfig = electronConfig; 
    }

}