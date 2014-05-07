/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net;
import serialMessage.Message;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import game.*;

/**
 *
 * @author skyscope
 */
public class Server {
    private ArrayList<ConnectionToClient> clientList;
    private ArrayList<Game> gameList; 
    private LinkedBlockingQueue<Message> messagesIN;
    private LinkedBlockingQueue<Message> messagesOUT;
    private ServerSocket serverSocket;
    private String maps;
    private Server server = this;

    public Server(int port, final String maps) {
        clientList = new ArrayList<ConnectionToClient>();
        gameList = new ArrayList<Game>();
        messagesIN = new LinkedBlockingQueue<Message>();
        messagesOUT = new LinkedBlockingQueue<Message>();
        this.maps = maps;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        Thread accept = new Thread() {
            public void run(){
                int clientCounter = 0;
                while(true){
                    try{
                        Socket s = serverSocket.accept();
                        System.out.println("novy klient id: "+clientCounter);
                        clientList.add(new ConnectionToClient(s));
                        //posleme klientovy jeho pridelene ID
                        Message msg = new Message();
                        msg.setCode(3);
                        msg.setClientID(clientCounter);
                        msg.setContent("si klient cislo "+clientCounter); // tento parameter je irelevantny
                        sendToOne(clientCounter,msg);
                        
                        msg = new Message();
                        //posleme klientovy dostupne mapy
                        msg.setCode(5);
                        msg.setContent(maps);
                        sendToOne(clientCounter,msg);
                        clientCounter++;
                    }
                    catch(IOException e){ e.printStackTrace(); }
                }
            }
        };

        accept.setDaemon(true);
        accept.start();

        //spracovanie sprav od klienta
        Thread messageHandling = new Thread() {
            public void run(){
                int gameCounter = 0;
                while(true){
                    try{
                        Message message = (Message)messagesIN.take();
                        //handling message...
                        System.out.println("client ID: "+message.getClienID());
                        System.out.println("game ID: "+message.getGameID());
                        System.out.println("message code: "+message.getCode());
                        System.out.println("content: "+message.getContent());
                        
                        
                        
                        if(message.getCode() == 1){
                            if(message.getGameID() != -1){
                                try {
                                    gameList.get(message.getGameID()).executeCommand(message.getClienID(),message.getContent());
                                } catch (IOException ex) {
                                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }
                        else if(message.getCode() == 2){
                            Message msg = new Message();
                            msg.setClientID(message.getClienID());
                            msg.setCode(6);
                            msg.setGameID(gameCounter);
                            messagesOUT.add(msg);
                            gameList.add(new Game(message.getContent(),gameCounter, server));
                            gameList.get(gameCounter).addPlayer(message.getClienID());
                            gameCounter++;
                        }
                        else if(message.getCode() == 3){
                            gameList.get(message.getGameID()).addPlayer(message.getClienID());
                        }
                        else if(message.getCode() == 4){
                            String runningGames = "";
                            for(int i = 0; i < gameList.size(); i++){
                                if(i != 0) runningGames = runningGames.concat("@");
                                runningGames = runningGames.concat(gameList.get(i).getMapName()+":"+i); 
                            }
                            Message msg = new Message();
                            msg.setClientID(message.getClienID());
                            msg.setCode(4);
                            msg.setContent(runningGames);
                            messagesOUT.add(msg);
                        }
                    }
                    catch(InterruptedException e){ }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();
        
        //odosielatel sprav
        Thread messageSender = new Thread() {
            public void run(){
                while(true){
                    try{
                        Message message = (Message)messagesOUT.take();
                        //handling message...
                        if(message.getCode() != 1){
                            sendToOne(message.getClienID(),message);
                        }
                        else{
                            int[] client = client = gameList.get(message.getGameID()).getClientIDs();
                            for(int i=0;i<4;i++){
                                if(client[i] != -1){
                                    System.out.println("+client ID: "+client[i]);
                                    System.out.println("+game ID: "+message.getGameID());
                                    System.out.println("+message code: "+message.getCode());
                                    System.out.println("+content: "+message.getContent());
                                    sendToOne(client[i],message);
                                }
                            }
                        }
                    }
                    catch(InterruptedException e){ }
                }
            }
        };

        messageSender.setDaemon(true);
        messageSender.start();
    }

    private class ConnectionToClient {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;

        ConnectionToClient(Socket socket) throws IOException {
            this.socket = socket;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread(){
                public void run(){
                        try{
                            Message message; 
                            while((message = (Message)in.readObject()) != null){
                            messagesIN.put(message);}
                        }
                        catch(Exception e){ e.printStackTrace(); }
                }
            };

            read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public void write(Message msg) {
            try{
                out.writeObject(msg);
            }
            catch(IOException e){ e.printStackTrace(); }
        }
    }

    public synchronized void sendToOne(int index, Message msg)throws IndexOutOfBoundsException {
        clientList.get(index).write(msg);
    }

    public synchronized void sendToAll(Message msg){
        for(ConnectionToClient client : clientList)
            client.write(msg);
    }
    
    public void addMessageOUT(Message message){
        messagesOUT.add(message);
    }
}