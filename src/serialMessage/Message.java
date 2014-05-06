/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package serialMessage;

import java.io.Serializable;

/**
 *
 * @author skyscope
 */
public class Message implements Serializable {
    int code;
    int gameID;
    int clientID;
    String content;
    

    public void setCode(int code) { this.code = code; }
    public void setGameID(int id) {this.gameID = id;}
    public void setClientID(int id){this.clientID = id;}
    public void setContent(String content) { this.content = content; }
    
    public int getCode() { return this.code; }
    public int getGameID(){return this.gameID;}
    public int getClienID(){return this.clientID;}
    public String getContent() { return this.content; }
}
