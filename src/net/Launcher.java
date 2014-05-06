/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import serialMessage.Message;

/**
 *
 * @author skyscope
 */
public class Launcher {
    public static void main(String[] args) {
        String maps = getMapList();
        System.out.println(maps);
        Server server = new Server(4444, maps);
        try {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

            String inputLine;
            while ((inputLine = stdIn.readLine()) != null) {
                if(inputLine.equals("close")) break;
                else{
                    Message msg = new Message();
                    msg.setContent(inputLine);
                    server.sendToAll(msg);
                }
            }
        } catch (IOException e) {}
    }
    
    private static String getMapList(){
        // Directory path here
        String path = "examples";

        String files;
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String mapList = "";
        for (int i = 0; i < listOfFiles.length; i++) {
            if(i != 0){ mapList = mapList.concat("@");}
            mapList = mapList.concat(listOfFiles[i].getName());

        }
        return mapList;
    }
    
}
