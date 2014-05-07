/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

import objects.MatrixObject;

/**
 * Trieda má na starosti uchovávanie informácii o políčku a operácie nad políčkom. 
 * @author P.Kadlec, T.Hudziec
 */
public class MatrixField {
    protected int x;
    protected int y;
    protected Matrix map;
    protected MatrixObject object;
    protected Player player;
    
    /**
     * Uloží odkaz na mapu a pošle požiadavok na vytvorenie objektu daného typu
     * MatrixObject-u
     * @param map odkaz na mapu
     * @param x stĺpcová súradnica pozície v mape 
     * @param y riadová súradnica pozície v mape
     * @param type Písmeno reprezentujúce typ objektu, ktorý má byť umiestnený
     * na danom políčku.
     */
    public MatrixField(Matrix map, int x, int y, String type){
        this.x = x;
        this.y = y;
        this.map = map;
        this.object = MatrixObject.create(type);  
        this.player = null;
    }
    
    /**
     * Obsadí toto políčko hráčom.
     * @param player obsadzujúci hráč
     * @return Ak sa políčko dá obsadiť vracia true ak nie tak false.
     */
   public boolean seize(Player player){
        if(this.canSeize()){
            this.player = player;
            return true;
        } else return false;
    }
   
   /**
    * Uvolní políčko.
    * @return Vracia hráča ktorý opúšťa políčko.
    */
   public Player leave(){
        Player pom = player;
        this.player = null;
        return pom;
    }
   
   /**
    * Otázka či sa dá dané políčko obsadiť.
    * @return Ak sa políčko dá obsadiť vracia true, inak false.
    */
   public boolean canSeize(){
        if(this.player == null) {
            if(this.object == null) return true;
            else if(this.object.canSeize())return true;
        }
        return false;
    }
   
   /**
    * Otvára objekt na tomto políčku.
    * @return Ak sa na tomto políčku nenachádza nijaký objekt vracia false,
    * inak vracia výsledok otvorenia tohoto objektu.
    */
   public boolean open(){
        if(this.object == null) return false;
        return this.object.open();
   }
   
   /**
    * Otázka na objekt na tomto políčku, či sa dá otvoriť.
    * @return Ak sa dá otvoriť vracia true, inak false.
    */
   public boolean canBeOpen(){
       if(this.object == null) return false;
       else return this.object.canBeOpen();
   }
   
   /**
    * Získa políčko, ktoré sa nachádza pred hráčom.
    * @return Vracia políčko pred hráčom. Ak sa políčko pred hráčom nachádza
    * mimo mapu, tak vracia null.
    */
   public MatrixField fieldAhead(){
       switch(this.player.getDirection()){
           case 0: return map.fieldAt(x+1,y);
           case 1: return map.fieldAt(x,y-1);
           case 2: return map.fieldAt(x-1,y);
           case 3: return map.fieldAt(x,y+1);
           default: return null;
       }
   }
   
   /**
    * Otázka na objekt na tomto políčku, či sa dá zobrať.
    * @return Ak sa objekt dá zobrať, tak vracia true, inak false.
    */
   public boolean canTake(){
       if(this.object == null) return false;
       else return this.object.canTake();
   }
   
   /**
    * Zistí typ objektu na tomto políčku. 
    * @return Vracia znak, ktorý reprezentuje objekt na tomto políčku.
    * Ak je toto políčko obsadené hráčom vracia symbol reprezentujúci smer,
    * do ktorého je hráč natčený.
    */
   public String getType(){
      if(this.object != null && this.object.getType().equals("F"))return this.object.getType();
      if(this.player != null){
            switch(player.getID()){
                case 0:
                {switch (player.getDirection()){
                      case 0: return ">";
                      case 1: return "^";
                      case 2: return "<";
                      case 3: return "v";
                      default: return ".";
                  }}
                case 1:
                {switch (player.getDirection()){
                      case 0: return "a";
                      case 1: return "b";
                      case 2: return "c";
                      case 3: return "d";
                      default: return ".";
                  }}
                case 2:
                {switch (player.getDirection()){
                      case 0: return "0";
                      case 1: return "1";
                      case 2: return "2";
                      case 3: return "3";
                      default: return ".";
                  }}
                case 3:
                {switch (player.getDirection()){
                      case 0: return "4";
                      case 1: return "5";
                      case 2: return "6";
                      case 3: return "7";
                      default: return ".";
                  }}
                case 4:
                {switch (player.getDirection()){
                      case 0: return "q";
                      case 1: return "r";
                      case 2: return "s";
                      case 3: return "t";
                      default: return ".";
                  }}
                default: return ".";
            }
      }
      if(this.object != null)return this.object.getType();
      else return ".";
   }
   
   /**
    * Odstráni objekt z tohoto políčka.
    */
   public void removeObj(){
       this.object = null;
   }
   
}
