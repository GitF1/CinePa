/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.owner.dashboard;

import java.util.List;
import util.ChartUtil;

/**
 *
 * @author PC
 */
public class ChartModel {

    private int size;
    private List<String> lables;
    private List<Double> data;
    private Double totalData;
    private Double maxData;

    private String lable;
    private String type;

    public ChartModel() {
        this.type = ChartUtil.TYPE_LINE;
    }

    public ChartModel(int size, List<String> lables, List<Double> data) {
        this.size = size;
        this.lables = lables;
        this.data = data;
        this.type = ChartUtil.TYPE_LINE;
    }

    public ChartModel(int size, List<String> lables, List<Double> data, String lable, String type) {
        this.size = size;
        this.lables = lables;
        this.data = data;
        this.lable = lable;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }

    public double getTotalData() {
        return totalData;
    }

    public void setTotalData(double totalData) {
        this.totalData = totalData;
    }

    public double getMaxData() {
        return maxData;
    }

    public void setMaxData(double maxData) {
        this.maxData = maxData;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ChartModel{" + "size=" + size + ", lables=" + lables + ", data=" + data + ", totalData=" + totalData + ", maxData=" + maxData + ", lable=" + lable + ", type=" + type + '}';
    }

}
