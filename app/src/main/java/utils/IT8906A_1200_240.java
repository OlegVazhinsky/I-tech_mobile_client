package utils;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;

public class IT8906A_1200_240 {

    private TCPconnection connection;

    public IT8906A_1200_240() {

    }

    public void setConnection(TCPconnection connection) {
        this.connection = connection;
    }

    public void setFunctionCC() {
        connection.writeToSocket("FUNC CURR");
    }

    public void setCurrent(String current) {
        setFunctionCC();
        connection.writeToSocket("CURR " + current);
    }

    public String getCurrent() {
        connection.writeToSocket("MEAS:CURR?");
        return connection.readFromSocket();
    }

    public String getSetupCurrent() {
        connection.writeToSocket("SOUR:CURR?");
        return connection.readFromSocket();
    }

    public String getProtectionCurrent() {
        connection.writeToSocket("CURR:PROT?");
        return connection.readFromSocket();
    }

    public void setProtectionCurrent(String current) {
        connection.writeToSocket("CURR:PROT:STAT 1");
        connection.writeToSocket("CURR:PROT " + current);
    }

    public void setFunctionCV() {
        connection.writeToSocket("FUNC VOLT");
    }

    public void setVoltage(String voltage) {
        setFunctionCV();
        connection.writeToSocket("VOLT " + voltage);
    }

    public String getVoltage() {
        connection.writeToSocket("MEAS:VOLT?");
        return connection.readFromSocket();
    }

    public String getSetupVoltage() {
        connection.writeToSocket("SOUR:VOLT?");
        return connection.readFromSocket();
    }

    public void setResistance(String resistance) {
        setFunctionCR();
        connection.writeToSocket("RES " + resistance);
    }

    public String getSetupResistance() {
        connection.writeToSocket("SOUR:RES?");
        return connection.readFromSocket();
    }

    public String getPower() {
        connection.writeToSocket("MEAS:POW?");
        return connection.readFromSocket();
    }

    public String getProtectionPower() {
        connection.writeToSocket("POW:PROT?");
        return connection.readFromSocket();
    }

    public void setProtectionPower(String power) {
        connection.writeToSocket("POW:PROT " + power);
    }

    public void setDeviceOn() {
        connection.writeToSocket("INP 1");
    }

    public void setDeviceOff() {
        connection.writeToSocket("INP 0");
    }

    public boolean isDeviceOn() {
        connection.writeToSocket("INP?");
        return connection.readFromSocket().equals("1");
    }

    public String getProtectionError () {
        int answer;
        connection.writeToSocket("STAT:QUES:COND?");
        try {
            answer = Integer.parseInt(connection.readFromSocket());
            if (answer == 8194) {
                return "Error 8194 - Overcurrent protection";
            } else if (answer == 8200) {
                return "Error 8200 - Overpower protection";
            } else if (answer == 8208) {
                return "Error 8208 - Overtemperature protection";
            } else return "0";
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public void resetProtection() {
        connection.writeToSocket("PROT:CLE");
    }

    public boolean isDeviceConnected() {
        return connection.isConnected();
    }

    public void connect() throws IOException {
        connection.connect();
        connection.writeToSocket("SYST:RWL");
    }

    public void disconnect() throws IOException {
        connection.disconnect();
    }

    public String getPC_IP() throws UnknownHostException {
        return Inet4Address.getLocalHost().getHostAddress();
    }

    public void setFunctionCR() {
        connection.writeToSocket("FUNC RES");
    }

}