/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package objects;

/**
 *Brána sa nedá vziať, dá otvoriť a dá sa aj obsadiť, ak je otvorená.
 * @author P.Kadlec, T.Hudziec
 */
public class Gate extends MatrixObject{
    
    /**
     * Ak je zavretá otvorí sa.
     * @return Ak je zavretá vracia true, inak false.
     */
    @Override
    public boolean open(){
        if(!opened){
            opened = true;
            return true;
        }
        else return false;
    }
    
    /**
     * 
     * @return Ak je otvorená vracia true, inak false.
     */
    @Override
    public boolean canSeize(){
        return opened ? true:false;
    }
    
    /**
     * 
     * @return Ak je zatvorená vracia true, inak false.
     */
    @Override
    public boolean canBeOpen(){
        if(!opened) return true;
        else return false;
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
     * @return Ak je zatvorená vracia "G",inak "O".
     */
    @Override
    public String getType(){
        if(opened) return ".";
        return "g";
    }
}
