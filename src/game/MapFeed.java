/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

/**
 *
 * @author skyscope
 */
public class MapFeed {
    
    protected String map;
    protected boolean available = false;
    protected int x;
    protected int y;
    
    public MapFeed(int x, int y){
        this.x = x;
        this.y = y;
    }

    public synchronized String getMap() {
        while (available == false) {
            try {
                wait();
            } catch (InterruptedException e) {}

        }
        available = false;
        notifyAll();
        return map;
    }

    public synchronized void putMap(String newMap) {
        while (available == true) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        available = true;
        map = newMap; 
        notifyAll();
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
}
