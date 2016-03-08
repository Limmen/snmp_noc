/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package kth.se.exjobb.view.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private BarChartModel barModel;
    private LineChartModel lineModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    @PostConstruct
    public void init() {
        updateModels();
    }
    public void updateModels(){
        updateBarModel();
        updateLineModel();
    }
    public void updateBarModel() {
        alarms = (List) contr.getAllAlarms();
        if(alarms == null)
            alarms = new ArrayList();
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
        ArrayList<String> dateList = new ArrayList();
        dateList.add("06/03/2016");
        dateList.add("07/03/2016");
        dateList.add("08/03/2016");
        dateList.add("09/03/2016");
        dateList.add("10/03/2016");
        for(SNMPMessage message : alarms){
            for(SNMPVariableBinding binding : message.getVariableBindings()){
                if(binding.getOid().equals("sysName.0")){
                    if(!names.contains(binding.getValue()))
                        names.add(binding.getValue());
                }
            }
            if(!dateList.contains(dateFormat.format(message.getDate())))
                dateList.add(dateFormat.format(message.getDate()));
        }
        System.out.println("Names size: " + names.size());
        System.out.println("Dates size: " + dateList.size());
        BarChartModel model = new BarChartModel();
        mockData(model);
        for(String name : names){
            ChartSeries serie = new ChartSeries();
            serie.setLabel(name);
            for(String date : dateList){
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
        dates = dateList;
        return model;
    }
    private void mockData(BarChartModel model){
        ChartSeries mock1 = new ChartSeries();
        mock1.setLabel("MockData1");
        mock1.set("08/03/2016", 9);
        mock1.set("09/03/2016", 2);
        mock1.set("10/03/2016", 2);
        mock1.set("07/03/2016", 5);
        mock1.set("06/03/2016", 0);
        
        ChartSeries mock2 = new ChartSeries();
        mock2.setLabel("MockData2");
        mock2.set("08/03/2016", 1);
        mock2.set("09/03/2016", 7);
        mock2.set("10/03/2016", 0);
        mock2.set("07/03/2016", 2);
        mock2.set("06/03/2016", 4);
        
        model.addSeries(mock1);
        model.addSeries(mock2);
        
    }
    private void updateLineModel() {
        lineModel = initLinearModel();
        lineModel.setTitle("Alarms per day");
        lineModel.setLegendPosition("e");
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
        yAxis.setMax(50);               
    }
    
    private LineChartModel initLinearModel() {
        LineChartModel model = new LineChartModel();
        
        ChartSeries series = new ChartSeries();
        series.setLabel("Alarms per day");
        System.out.println("Dates  size: " + dates.size());
        for(String date : dates){
            int count = 0;
            for(SNMPMessage message : alarms){
                if(dateFormat.format(message.getDate()).equals(date))
                    count++;
            }
            if(date.equals("08/03/2016")){
                count = count + 10;
            }
            if(date.equals("09/03/2016")){
                count = count + 9;
            }
            if(date.equals("10/03/2016")){
                count = count + 2;
            }
            if(date.equals("07/03/2016")){
                count = count + 7;
            }
            if(date.equals("06/03/2016")){
                count = count + 4;
            }
            series.set(date, count);
            System.out.println("Date: " + date + " Count: " + count);
        }
        model.addSeries(series);        
        return model;
    }
    public BarChartModel getBarModel() {
        return barModel;
    }
    public LineChartModel getLineModel() {
        return lineModel;
    }
    
}
