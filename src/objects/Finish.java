/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *Cieľ sa nedá otvoriť, nedá sa vziať ale dá sa obsadiť.
 * @author P.Kadlec, T.Hudziec
 */
public class Finish extends MatrixObject{
    
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
     * @return Vždy vracia false.
     */
    @Override
    public boolean canTake(){
        return false;
    }
    
    /**
     * 
     * @return Vždy vracia "F".
     */
    @Override
    public String getType(){
        return "f";
    }
}
