/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *Stnena sa nedá otvoriť, nedá sa obsadiť a nedá sa vziať.
 * @author P.Kadlec, T.Hudziec
 */
public class Wall extends MatrixObject {
    
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
     * @return Vždy vracia false.
     */
    @Override
   public boolean canSeize(){
       return false;
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
     * @return Vždy vracia "W".
     */
    @Override
    public String getType(){
        return "w";
    }
}
