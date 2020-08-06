package com.example.CubeX;

import java.io.Serializable;
import java.util.ArrayList;

public class Device implements Serializable {

    //Variables
    private String nickname;
    private String status;
    private int battery;
    private String fillStatus;
    private int deviceImage;
    private int batteryIcon;
    private int fillIcon;
    private String identifier;


    //Constructor
    public Device(String nickname, int deviceImage, int batteryIcon, int fillIcon, int battery,String fillStatus, String identifier)
    {
        this.nickname = nickname;
        this.deviceImage = deviceImage;
        this.batteryIcon = batteryIcon;
        this.fillIcon = fillIcon;
        this.battery = battery;
        this.fillStatus = fillStatus;
        this.identifier = identifier;
    }

    //Setters
    public void setNickname(String nickname){this.nickname = nickname;}
    public void setBattery(int battery){this.battery = battery;}
    public void setBatteryIcon(int batteryIcon){this.batteryIcon = batteryIcon;}
    public void setDeviceImage(int deviceImage){this.deviceImage = deviceImage;}
    public void setFillIcon(int fillIcon){this.fillIcon = fillIcon;}
    public void setFillStatus(String fillStatus){this.fillStatus = fillStatus;}
    public void setIdentifier(String identifier){this.identifier = identifier;}

    //Getters
    public String getNickname(){return nickname;}
    public String getIdentifier(){return identifier;}
    public String getFillStatus(){return fillStatus;}
    public int getDeviceImage(){return deviceImage;}
    public int getBatteryIcon(){return batteryIcon;}
    public int getFillIcon() {return fillIcon;}
    public int getBattery(){return battery;}

    /*
    private static int lastDeviceId = 0;


    public static ArrayList<Device> createDeviceList(int numDevices)
    {
        ArrayList<Device> devices = new ArrayList<Device>();

        for (int i = 1; i <= numDevices; i++)
        {
            devices.add(new Device("ABC's Bin", ,"ic_check_circle_24px","ic_warning_24px",
                    "Needs Attention",87,14,"ZW3hJH3hzd2ooF0wqM2o"));
        }

        return devices;
    }

     */

}
