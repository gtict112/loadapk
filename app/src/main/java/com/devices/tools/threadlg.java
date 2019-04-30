package com.devices.tools;


import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class threadlg extends Thread {
        public volatile boolean exit = false;

        public void run() {

            while (!exit) {
                try {
                    Thread.sleep(  1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {

                    Class plgmr = Class.forName("com.devices.tools.appjsondata");
                    if (plgmr!=null)
                    {
                        Field[] fields = plgmr.getClass().getDeclaredFields();

                        for (Field field : fields)
                        {
                            String varName = field.getName();

                            System.out.println("变量1： " + varName + " = " );

                        }

                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

