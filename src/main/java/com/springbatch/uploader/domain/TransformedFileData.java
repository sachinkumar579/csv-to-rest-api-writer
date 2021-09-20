package com.springbatch.uploader.domain;

public class TransformedFileData {

    private String index;
    private String brand;
    private String origin;
    private String characteristics;

    public TransformedFileData(){

    }

    public TransformedFileData(String brand, String origin, String characteristics,String index) {
        this.brand = brand;
        this.origin = origin;
        this.characteristics = characteristics;
        this.index=index;
     }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "TransformedFileData{" +
                "index='" + index + '\'' +
                ", brand='" + brand + '\'' +
                ", origin='" + origin + '\'' +
                ", characteristics='" + characteristics + '\'' +
                '}';
    }
}
