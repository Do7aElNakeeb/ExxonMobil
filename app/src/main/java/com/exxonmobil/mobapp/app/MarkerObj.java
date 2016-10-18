package com.exxonmobil.mobapp.app;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Nakeeb PC on 8/2/2016.
 */
public class MarkerObj implements ClusterItem{

    private long id;
    private String name;
    private String address;
    private String region;
    private String city;
    private String latitude;
    private String longitude;
    private String MOG80;
    private String MOG92;
    private String MOG95;
    private String diesel;
    private String MobilMart;
    private String OnTheRun;
    private String TheWayToGo;
    private String CarWash;
    private String lubricants;
    private String phone;


    public MarkerObj() {
    }

    public MarkerObj(long id, String name, String address, String region, String city, String latitude,
                     String longitude, String MOG80, String MOG92, String MOG95, String diesel,
                     String MobilMart, String OnTheRun, String TheWayToGo, String CarWash, String lubricants, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.MOG80 = MOG80;
        this.MOG92 = MOG92;
        this.MOG95 = MOG95;
        this.diesel = diesel;
        this.MobilMart = MobilMart;
        this.OnTheRun = OnTheRun;
        this.TheWayToGo = TheWayToGo;
        this.CarWash = CarWash;
        this.lubricants = lubricants;
        this.phone = phone;
    }
    public MarkerObj(String name, String address, String region, String city, String latitude,
                     String longitude, String MOG80, String MOG92, String MOG95, String diesel,
                     String MobilMart, String OnTheRun, String TheWayToGo, String CarWash, String lubricants, String phone) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.MOG80 = MOG80;
        this.MOG92 = MOG92;
        this.MOG95 = MOG95;
        this.diesel = diesel;
        this.MobilMart = MobilMart;
        this.OnTheRun = OnTheRun;
        this.TheWayToGo = TheWayToGo;
        this.CarWash = CarWash;
        this.lubricants = lubricants;
        this.phone = phone;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the title
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the title to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the snippet
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the snippet to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the position
     */
    public String getRegion() {
        return region;
    }

    /**
     * @param region the position to set
     */
    public void setRegion(String region) {
        this.region = region;
    }

    /**
     * @return the position
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the position to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the position
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the position to set
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the position
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the position to set
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the position
     */
    public String getMOG80() {
        return MOG80;
    }

    /**
     * @param MOG80 the position to set
     */
    public void setMOG80(String MOG80) {
        this.MOG80 = MOG80;
    }/**
     * @return the position
     */
    public String getMOG92() {
        return MOG92;
    }

    /**
     * @param MOG92 the position to set
     */
    public void setMOG92(String MOG92) {
        this.MOG92 = MOG92;
    }

    /**
     * @return the position
     */
    public String getMOG95() {
        return MOG95;
    }

    /**
     * @param MOG95 the position to set
     */
    public void setMOG95(String MOG95) {
        this.MOG95 = MOG95;
    }/**
     * @return the position
     */
    public String getDiesel() {
        return diesel;
    }

    /**
     * @param diesel the position to set
     */
    public void setDiesel(String diesel) {
        this.diesel = diesel;
    }

    /**
     * @return the position
     */
    public String getMobilMart() {
        return MobilMart;
    }

    /**
     * @param MobilMart the position to set
     */
    public void setMobilMart(String MobilMart) {
        this.MobilMart = MobilMart;
    }

    /**
     * @return the position
     */
    public String getOnTheRun() {
        return OnTheRun;
    }

    /**
     * @param OnTheRun the position to set
     */
    public void setOnTheRun(String OnTheRun) {
        this.OnTheRun = OnTheRun;
    }

    /**
     * @return the position
     */
    public String getTheWayToGo() {
        return TheWayToGo;
    }

    /**
     * @param TheWayToGo the position to set
     */
    public void setTheWayToGo(String TheWayToGo) {
        this.TheWayToGo = TheWayToGo;
    }

    /**
     * @return the position
     */
    public String getCarWash() {
        return CarWash;
    }

    /**
     * @param CarWash the position to set
     */
    public void setCarWash(String CarWash) {
        this.CarWash = CarWash;
    }

    /**
     * @return the position
     */
    public String getLubricants() {
        return lubricants;
    }

    /**
     * @param lubricants the position to set
     */
    public void setLubricants(String lubricants) {
        this.lubricants = lubricants;
    }

    /**
     * @return the position
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the position to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public LatLng getPosition() {
        return new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
    }
}