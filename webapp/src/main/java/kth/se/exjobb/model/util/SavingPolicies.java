/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.model.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Singleton Read-Only class that represents the ordering of different severities.
 *
 * @author Kim Hammar
 */
public class SavingPolicies {
    private ArrayList<String> savingPolicies;
    private SavingPolicies(){
        savingPolicies = new ArrayList();
        savingPolicies.add("Dont save");
        savingPolicies.add("1 day");
        savingPolicies.add("3 days");
        savingPolicies.add("1 week");
        savingPolicies.add("2 weeks");
        savingPolicies.add("1 month");
        savingPolicies.add("6 months");
        savingPolicies.add("Forever");
    }

    private static class instanceHolder {
        private static final SavingPolicies instance = new SavingPolicies();
    }
   
    public static SavingPolicies getInstance(){
        return instanceHolder.instance;
    }
    
    public Date getDate(String policy){
        if(policy.equals("Forever")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, -100);
            return cal.getTime();   
        }            
        if(policy.equals("6 months")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -6);
            return cal.getTime();
        }
        if(policy.equals("1 month")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            return cal.getTime();
        }
        if(policy.equals("2 weeks")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, -2);
            return cal.getTime();
        }
        if(policy.equals("1 week")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.WEEK_OF_YEAR, -1);
            return cal.getTime();
        }
        if(policy.equals("3 days")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -3);
            return cal.getTime();
        }
        if(policy.equals("1 day")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -1);
            return cal.getTime();
        }
        if(policy.equals("Dont save")){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, 100);
            return cal.getTime();  
        }
        return null;
    }
    
    public ArrayList<String> getSavingPolicies(){
        return savingPolicies;
    }
}