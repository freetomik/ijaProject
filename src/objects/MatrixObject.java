/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *Abstraktná trieda, ktorá vytvára objekty konkrétnych typov a deklaruje metódy
 * nad nimi.
 * @author P.Kadlec, T.Hudziec
 */
public abstract class MatrixObject{
    protected boolean opened;
    
    
    public MatrixObject(){
        this.opened = false;
    }
    
    /**
     * Otázka pre objekt, či sa dá obsadiť.
     * @return Návratová hodnota záleží na implementujúcej triede.
     */
   public abstract boolean canSeize();
   
   /**
    * Pokúsi sa otvoriť objekt.
    * @return Návratová hodnota záleží na implementujúcej triede.
    */
   public abstract boolean open();
   
   /**
    * Otázka pre objekt, či sa dá otvoriť.
    * @return Návratová hodnota záleží na implementujúcej triede.
    */
   public abstract boolean canBeOpen();
 
   /**
    * Otázka pre objekt, či sa dá vziať.
    * @return Návratová hodnota záleží na implementujúcej triede.
    */
   public abstract boolean canTake();
   
   /**
    * Otázka pre objekt akého je typu.
    * @return Návratová hodnota záleží na implementujúcej triede.
    */
   public abstract String getType();
   
   /**
    * Vztvára konkrétnu inšnciu objektu.
    * @param format symbol reprezentujuci objekt ktorý má byť vytvorený.
    * @return Vracia vytvorený objekt, ak je formát zadaný pre niektorý objekt
    * z množiny {stena, brána, kľúč, cieľ}, inak vracia null.
    */
   public static MatrixObject create(String format){
       if(format.equals("w")){
           return new Wall();
       }else if(format.equals("g")){
           return new Gate();
       }else if(format.equals("f")){
           return new Finish();
       }else if(format.equals("k")){
           return new Key();  
       }else return null;
   }
}
