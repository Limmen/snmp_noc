/*
* Royal Institute of Technology
* 2016 (c) Kim Hammar Marcus Blom
*/
package kth.se.exjobb.view.view;

import kth.se.exjobb.controller.Controller;
import kth.se.exjobb.integration.DAO.DataAccessObject;
import kth.se.exjobb.integration.entities.SNMPMessage;
import kth.se.exjobb.model.snmp.SNMPVariableBinding;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Managed bean representing the interface between the statistics page and the server.
 * ViewScope means that the bean will be active as long as the user is interacting with the same
 * JSF view.
 *
 * @author Kim Hammar
 */
@Named(value = "statisticsBean")
@ViewScoped
public class StatisticsBean implements Serializable {

    @EJB
    Controller contr;
    @EJB
    DataAccessObject dao;
    private List<SNMPMessage> alarms;
    private ArrayList<String> dates = new ArrayList();
    private BarChartModel alarmsByLocationModel = new BarChartModel();
    private BarChartModel alarmsBySeverityModel = new BarChartModel();
    private LineChartModel alarmsByDay = new LineChartModel();
    private PieChartModel alarmsByLocationPieModel;
    private MeterGaugeChartModel alarmsFrequencyModel;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

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
    public void updateModels() {
        updateAlarms();
        updateDates();
        updateAlarmsByLocationModel();
        updateAlarmsBySeverityModel();
        updateAlarmsByLocationPieModel();
        updateAlarmsFrequencyModel();
        alarmsByDayModel();
    }

    private void updateDates() {
        dates = new ArrayList();
        for (int i = 6; i >= 0; i--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            dates.add(dateFormat.format(cal.getTime()));
        }
    }

    private void updateAlarms() {
        alarms = (List) dao.getAllMessages();
        if (alarms == null)
            alarms = new ArrayList();
    }

    private void updateAlarmsByLocationModel() {
        alarmsByLocationModel = initAlarmsByLocationModel();
        alarmsByLocationModel.setTitle("Distribution of alarms");
        alarmsByLocationModel.setLegendPosition("ne");

        Axis xAxis = alarmsByLocationModel.getAxis(AxisType.X);
        xAxis.setLabel("Day");
        Axis yAxis = alarmsByLocationModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number of alarms");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }

    private BarChartModel initAlarmsByLocationModel() {
        ArrayList<String> names = new ArrayList();
        for (SNMPMessage message : alarms) {
            for (SNMPVariableBinding binding : message.getVariableBindings()) {
                if (binding.getOid().equals("sysName.0")) {
                    if (!names.contains(binding.getValue()))
                        names.add(binding.getValue());
                }
            }
        }
        BarChartModel model = new BarChartModel();
        for (String name : names) {
            ChartSeries serie = new ChartSeries();
            serie.setLabel(name);
            for (String date : dates) {
                int count = 0;
                for (SNMPMessage message : alarms) {
                    boolean match = false;
                    for (SNMPVariableBinding binding : message.getVariableBindings()) {
                        if (binding.getOid().equals("sysName.0")) {
                            match = binding.getValue().equals(name);
                        }
                    }
                    if (dateFormat.format(message.getRawDate()).equals(date) && match)
                        count++;
                }
                serie.set(date, count);
            }
            model.addSeries(serie);
        }
        return model;
    }

    private void updateAlarmsBySeverityModel() {
        alarmsBySeverityModel = initAlarmsBySeverityModel();

        alarmsBySeverityModel.setTitle("Severity of alarms");
        alarmsBySeverityModel.setLegendPosition("ne");

        Axis xAxis = alarmsBySeverityModel.getAxis(AxisType.X);
        xAxis.setLabel("Day");

        Axis yAxis = alarmsBySeverityModel.getAxis(AxisType.Y);
        yAxis.setLabel("Number of alarms");
        yAxis.setMin(0);
        yAxis.setMax(20);
    }

    private BarChartModel initAlarmsBySeverityModel() {
        ArrayList<String> names = new ArrayList();
        for (SNMPMessage message : alarms) {
            for (SNMPVariableBinding binding : message.getVariableBindings()) {
                if (binding.getOid().equals("calSeverity")) {
                    if (!names.contains(binding.getValue()))
                        names.add(binding.getValue());
                }
            }
        }
        BarChartModel model = new BarChartModel();
        for (String name : names) {
            ChartSeries serie = new ChartSeries();
            serie.setLabel(name);
            for (String date : dates) {
                int count = 0;
                for (SNMPMessage message : alarms) {
                    boolean match = false;
                    for (SNMPVariableBinding binding : message.getVariableBindings()) {
                        if (binding.getOid().equals("calSeverity")) {
                            match = binding.getValue().equals(name);
                        }
                    }
                    if (dateFormat.format(message.getRawDate()).equals(date) && match)
                        count++;
                }
                serie.set(date, count);
            }
            model.addSeries(serie);
        }
        return model;
    }

    private void alarmsByDayModel() {
        alarmsByDay = initAlarmsByDayModel();
        alarmsByDay.setTitle("Alarms per day");
        alarmsByDay.setLegendPosition("e");
        alarmsByDay.setShowPointLabels(true);
        alarmsByDay.getAxes().put(AxisType.X, new CategoryAxis("Day"));
        Axis yAxis = alarmsByDay.getAxis(AxisType.Y);
        yAxis.setLabel("Alarms");
        yAxis.setMin(0);
        yAxis.setMax(80);
    }

    private LineChartModel initAlarmsByDayModel() {
        LineChartModel model = new LineChartModel();

        ChartSeries alarmSeries = new ChartSeries();
        alarmSeries.setLabel("Alarms per day");
        for (String date : dates) {
            int count = 0;
            for (SNMPMessage message : alarms) {
                if (dateFormat.format(message.getRawDate()).equals(date))
                    count++;
            }
            alarmSeries.set(date, count);
        }
        model.addSeries(alarmSeries);
        return model;
    }

    private void updateAlarmsByLocationPieModel() {
        alarmsByLocationPieModel = initAlarmsByLocationPieModel();
    }

    private PieChartModel initAlarmsByLocationPieModel() {
        alarmsByLocationPieModel = new PieChartModel();
        HashMap<String, Integer> systems = new HashMap();
        for (SNMPMessage message : alarms) {
            if (systems.containsKey(message.getSysName()))
                systems.put(message.getSysName(), systems.get(message.getSysName()) + 1);
            else
                systems.put(message.getSysName(), 1);
        }
        Iterator iterator = systems.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry keyValue = (Map.Entry) iterator.next();
            alarmsByLocationPieModel.set((String) keyValue.getKey(), (Integer) keyValue.getValue());
        }

        alarmsByLocationPieModel.setTitle("Distribution of alarms per system");
        alarmsByLocationPieModel.setLegendPosition("w");

        return alarmsByLocationPieModel;
    }

    private void updateAlarmsFrequencyModel() {
        alarmsFrequencyModel = initAlarmsFrequencyModel();
    }

    private MeterGaugeChartModel initAlarmsFrequencyModel() {
        List<Number> intervals = new ArrayList<Number>() {{
            add(2);
            add(5);
            add(10);
            add(20);
            add(50);
            add(100);
        }};
        alarmsFrequencyModel = new MeterGaugeChartModel(getFrequencyHour(), intervals);
        alarmsFrequencyModel.setTitle("Frequency of alarms in the latest hour");
        alarmsFrequencyModel.setGaugeLabel("Alarms/h");
        return alarmsFrequencyModel;
    }

    private int getFrequencyHour() {
        Date now = new Date();
        int count = 0;
        for (SNMPMessage message : alarms) {
            if(hoursDifference(now, message.getRawDate()) <= 1)
                count++;
        }
        return count;
    }
    private int hoursDifference(Date date1, Date date2) {

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }
    /**
     * getAlarmsByLocationModel
     *
     * @return model that visualize alarms by location
     */
    public BarChartModel getAlarmsByLocationModel() {
        return alarmsByLocationModel;
    }

    /**
     * getAlarmsBySeverityModel
     *
     * @return model that visualize alarms by severity
     */
    public BarChartModel getAlarmsBySeverityModel() {
        return alarmsBySeverityModel;
    }

    /**
     * getAlarmsByDayModel
     *
     * @return model for the line chart
     */
    public LineChartModel getAlarmsByDayModel() {
        return alarmsByDay;
    }

    /**
     * getAlarmsByLocationPieModel
     *
     * @return model for the pie chart of source for the alarms.
     */
    public PieChartModel getAlarmsByLocationPieModel() {
        return alarmsByLocationPieModel;
    }

    /**
     * getAlarmsFrequencyModel
     *
     * @return model for the frequency gauge-meter
     */
    public MeterGaugeChartModel getAlarmsFrequencyModel() {
        return alarmsFrequencyModel;
    }

    /**
     * getNoAlarms
     *
     * @return number of alarms
     */
    public int getNoAlarms() {
        return alarms.size();
    }
}
