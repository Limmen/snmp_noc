/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kth.se.exjobb.manager;

/**
 *
 * @author kim
 */
public class Startup2 {
    
    public static void main(String[] args){
        SNMPManager manager = new SNMPManager();
        new Thread(manager).start();
    }
}
