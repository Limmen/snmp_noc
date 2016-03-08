/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.model.snmp.SNMPMessage;
import kth.se.exjobb.model.snmp.SNMPVariableBinding;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author kim
 */
@Named(value = "statisticsBean")
@ViewScoped
public class StatisticsBean implements Serializable {
    
    @EJB
    Controller contr;
    private List<SNMPMessage> alarms;
    private ArrayList<String> dates = new ArrayList();
    private BarChartModel barModel = new BarChartModel();
    private LineChartModel lineModel = new LineChartModel();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    @PostConstruct
    public void init() {
        updateModels();
    }
    public void updateModels(){
        updateAlarms();
        updateDates();
        updateBarModel();
        updateLineModel();
    }
    private void updateDates(){
        dates = new ArrayList();
        for(int i = 6; i >= 0; i--){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            dates.add(dateFormat.format(cal.getTime()));
        }
    }
    private void updateAlarms(){
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
    }
    public void updateBarModel() {
        barModel = initBarModel();
        
        barModel.setTitle("Distribution of alarms");
        barModel.setLegendPosition("ne");
        
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("Day");
        
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number of alarms");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }
    
    private BarChartModel initBarModel() {
        ArrayList<String> names = new ArrayList();
        for(SNMPMessage message : alarms){
            for(SNMPVariableBinding binding : message.getVariableBindings()){
                if(binding.getOid().equals("sysName.0")){
                    if(!names.contains(binding.getValue()))
                        names.add(binding.getValue());
                }
            }
        }
        BarChartModel model = new BarChartModel();
        for(String name : names){
            ChartSeries serie = new ChartSeries();
            serie.setLabel(name);
            for(String date : dates){
                int count = 0;
                for(SNMPMessage message : alarms){
                    boolean match = false;
                    for(SNMPVariableBinding binding : message.getVariableBindings()){
                        if(binding.getOid().equals("sysName.0")){
                            match = binding.getValue().equals(name);
                        }
                    }
                    if(dateFormat.format(message.getDate()).equals(date) && match)
                        count++;
                }
                serie.set(date, count);
            }
            model.addSeries(serie);
        }
        return model;
    }
    private void updateLineModel() {
        lineModel = initCategoryModel();
        lineModel.setTitle("Alarms per day");
        lineModel.setLegendPosition("e");
        lineModel.setShowPointLabels(true);
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Day"));
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Alarms");
        yAxis.setMin(0);
        yAxis.setMax(80);             
    }
    
    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
 
        ChartSeries alarmSeries = new ChartSeries();
        alarmSeries.setLabel("Alarms per day");
        for(String date : dates){
            int count = 0;
            for(SNMPMessage message : alarms){
                if(dateFormat.format(message.getDate()).equals(date))
                    count++;
            }
            alarmSeries.set(date, count);
        }
        model.addSeries(alarmSeries);         
        return model;
    }
    public BarChartModel getBarModel() {
        return barModel;
    }
    public LineChartModel getLineModel() {
        return lineModel;
    }
    public int getNoAlarms(){
        return alarms.size();
    }
}
