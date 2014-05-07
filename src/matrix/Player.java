/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *Trieda player reprezentuje hráča, jeho polohu a obsahuje metódy pre jeho riadenie.
 * @author P.Kadlec, T.Hudziec
 */
public class Player {
    protected int id;
    protected MatrixField field;
    protected int direction;
    protected int keys;
    protected Matrix map;
    
    /**
     * 
     * @param id identifikačné číslo hráča
     * @param field políčko ktoré je obsadené týmto hráčom
     */
    public Player(int id, MatrixField field, Matrix map){
        this.id = id;
        this.field = field;
        this.direction = 0;
        this.keys = 0;
        this.map = map;
    }
    
    /**
     * Zmení smer natočenia hráča o 90° v smere hodinových ručičiek.
     */
    public void turnRight(){
        if(direction == 0) direction = 3;
        else --direction;
        map.updateMap();
    }
    
    /**
     * Zmení smer natočenia hráča o 90° v protismere hodinových ručičiek.
     */
    public void turnLeft(){
        if(direction == 3) direction = 0;
        else ++direction;
        map.updateMap();
    }
    
    /**
     * Ak sa dá tak hráč sa presunie na políčko pred ním.
     * @return Vracia true, ak sa krok vpred podaril, inak false.
     */
    public boolean step(){
        MatrixField startF = this.field;
        MatrixField targetF  = this.field.fieldAhead();
        
        if(targetF != null){
            if(targetF.canSeize()){
                this.field.leave();
                targetF.seize(this);
                this.field = targetF;
                map.updateMap();
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * @return Vracia políčko na ktorom sa hráč aktuálne nachádza.
     */
    public MatrixField seizedField(){
        return this.field;
    }
    
    /**
     * 
     * @return Vracia smer aktuálneho natočenia hráča.
     */
    public int getDirection(){
        return this.direction;
    }
    
    /**
     * Metóda odstraňuje kľúč nachádzajúci sa pred hráčom a zvyšuje počet
     * kľúčov, ktoré hráč vlastní.
     * @return Ak sa podarí vziať kľúč vracia sa true. Ak sa pred hráčom kľúč
     * nenachádza, vracia sa false.
     */
    public boolean take(){        
        MatrixField startF = this.field;
        MatrixField targetF  = this.field.fieldAhead();
        
        if(targetF != null){
            if(targetF.canTake()){
                targetF.removeObj();
                this.keys++;
                map.updateMap();
                return true;
            }
        }
        return false;
    }
    
    /**
     * Metóda otvára bránu ktorá sa nachádza pred hráčom ak tento hráč má
     * aspoň jeden kľúč.
     * @return Ak sa podarí otvoriť bránu, vracia sa true. Ak sa nepodarí,
     * alebo pred hráčom sa nenachádza brána, vracia sa false.
     */
    public boolean open(){
        MatrixField startF = this.field;
        MatrixField targetF  = this.field.fieldAhead();
        
        if(targetF != null){
            if(this.keys != 0){
                if(targetF.canBeOpen()){
                    this.keys--;
                    targetF.open();
                    map.updateMap();
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * @return Vracia počet kľúčov, ktoré tento hráč vlastní.
     */
    public int getKeys(){
        return this.keys;
    }
    
    /**
     * 
     * @return Ak sa hráč nachádza na cieľovom políčku, vracia sa true, inak false.
     */
    public boolean finished(){
        if(this.field.getType().equals("F")) return true;
        else return false;
    }
    
    public int getID(){
        return id;
    }
}
