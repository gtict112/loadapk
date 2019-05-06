package com.devices.tools;


import android.util.Log;

import org.json.JSONArray;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

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

                    // 打印输出方法的修饰域
                    Method method1 =   plgmr.getDeclaredMethod("getItemList");
                    method1.setAccessible(true);
                    Object result = method1.invoke(plgmr,null);
                    if (result instanceof List<?>)
                    {
                        exit =true;
                        System.out.println(result);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


            }
        }
    }

