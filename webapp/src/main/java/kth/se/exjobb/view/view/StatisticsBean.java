/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
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
 * Managed bean representing the interface between the statistics page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
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
    private BarChartModel barModel2 = new BarChartModel();
    private LineChartModel lineModel = new LineChartModel();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    /**
     * This method is called after all dependency injections and initialization are done
     * but before the class is put to service.
     *
     * Updates the models that the graphs represent.
     */
    @PostConstruct
    public void init() {
        updateModels();
    }
    
    /**
     * Method called on time-intervals to update the graph-models.
     */
    public void updateModels(){
        updateAlarms();
        updateDates();
        updateBarModel();
        updateBarModel2();
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
    
    private void updateBarModel() {
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
    private void updateBarModel2() {
        barModel2 = initBarModel2();
        
        barModel2.setTitle("Severity of alarms");
        barModel2.setLegendPosition("ne");
        
        Axis xAxis = barModel2.getAxis(AxisType.X);
        xAxis.setLabel("Day");
        
        Axis yAxis = barModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Number of alarms");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }
    
    private BarChartModel initBarModel2() {
        ArrayList<String> names = new ArrayList();
        for(SNMPMessage message : alarms){
            for(SNMPVariableBinding binding : message.getVariableBindings()){
                if(binding.getOid().equals("calSeverity")){
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
                        if(binding.getOid().equals("calSeverity")){
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
    
    /**
     * getBarModel
     * @return model for the bar chart
     */
    public BarChartModel getBarModel() {
        return barModel;
    }
    
    /**
     * getBarModel
     * @return model for the second bar chart
     */
    public BarChartModel getBarModel2() {
        return barModel2;
    }
    
    /**
     * getLineModel
     * @return model for the line chart
     */
    public LineChartModel getLineModel() {
        return lineModel;
    }
    
    /**
     * getNoAlarms
     * @return number of alarms
     */
    public int getNoAlarms(){
        return alarms.size();
    }
}
