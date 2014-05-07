/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import net.Server;
import serialMessage.*;

/**
 * Je trieda ktorá uchováva informácie o mape a hráčoch.
 * @author P.Kadlec, T.Hudziec
 * 
 */
public class Matrix {
    /** 
     * Je dvojrozmerené pole reprezentujúce hraciu plochu. 
     */
    protected MatrixField[][] map;
    private Server server;
    private int gameID;
    
    /**
     * Konštruktor vytvorý mapu podla zadanej velkosti a naplní ju objektami.
     * @param fileContent zvolená mapa vo formáte stringu
     * @param x je počet stĺpcov mapy
     * @param y je počet riadkov mapy
     */
    public Matrix(String fileContent, int x, int y, Server server, int id){
        this.map = new MatrixField[x][y];
        this.server = server;
        this.gameID = id;
        
        for(int i=0; i<y ; i++){
            for(int j=0; j<x; j++){
                String type = fileContent.substring(i*x+j,i*x+j+1);
                this.map[j][i] = new MatrixField(this, j, i, type);
            }
        }
    }
    
    /**
     * 
     * @param x súranica stĺpca
     * @param y súradnica riadku
     * @return Políčko na zadaných súradniciach. V prípade neplatných suradníc null.
     */
    public MatrixField fieldAt(int x, int y){
        if(x<map.length && y<map[0].length && x>=0 && y>=0) return map[x][y];
        else return null;
    }
    
    /**
     * Metóda zobrazí aktuálny stav mapy na stdout.
     */
    public void showMap(){
    
        for(int i=0; i<map[0].length ; i++){
            for(int j=0; j<map.length; j++){
                System.out.print(this.map[j][i].getType());
            }
            System.out.println();
        }
    }
    
    public String getMapString(){
        String mapString = "";
        for(int i=0; i<map[0].length ; i++){
            for(int j=0; j<map.length; j++){
                mapString = mapString.concat(this.map[j][i].getType());
            }
        }
        return mapString;
    }
    
    public void updateMap(){
        Message msg = new Message();
        msg.setCode(1);
        msg.setGameID(this.gameID);
        msg.setContent(getMapString());
        server.addMessageOUT(msg);
    }
    
    /**
     * Vytvorí nového hráča, priradí mu identifikačné číslo a pokúsi
     * sa ním obsadiť políčko na zadaných súradniciach.
     * @param id identifikačné číslo hráča
     * @param x súradnica stĺpca 
     * @param y súradnica riadku
     * @return Vracia hráča v prípade úspešného obsadenia políčka, inak null.
     */
    public Player createPlayer(int id, int x, int y){
        Player tmpplayer = new Player(id,map[x][y], this);
        if(this.map[x][y].canSeize() == false) return null;
        this.map[x][y].seize(tmpplayer);
        if(tmpplayer != null)updateMap();
        return tmpplayer;
    }
    
    public Zombie createZombie(int id, int x, int y){
        Zombie tmpzombie = new Zombie(id,map[x][y],this);
        if(this.map[x][y].canSeize() == false) return null;
        this.map[x][y].seize(tmpzombie);
        return tmpzombie;
    }
}
