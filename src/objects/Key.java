/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *Kľúč sa nedá otvoriť, dá sa obsadiť a dá sa vziať.
 * @author P.Kadlec, T.Hudziec
 */
public class Key extends MatrixObject{

    /**
     * 
     * @return Vždy vracia false.
     */
    @Override
    public boolean open(){
        return false;
    }
    
    /**
     * 
     * @return Vždy vracia true.
     */
    @Override
    public boolean canSeize(){
        return true;
    }
    
    /**
     * 
     * @return Vždy vracia false.
     */
    @Override
    public boolean canBeOpen(){
        return false;
    }
    
    /**
     * 
     * @return Vždy vracia true.
     */
    @Override
    public boolean canTake(){
        return true;
    }
    
    /**
     * 
     * @return Vždy vracia "K".
     */
    @Override
    public String getType(){
        return "K";
    }
}
