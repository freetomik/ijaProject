/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;


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
    protected Player player;
    
    /**
     * Konštruktor vytvorý mapu podla zadanej velkosti a naplní ju objektami.
     * @param fileContent zvolená mapa vo formáte stringu
     * @param x je počet stĺpcov mapy
     * @param y je počet riadkov mapy
     */
    public Matrix(String fileContent, int x, int y){
        this.map = new MatrixField[x][y];
        
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
    
    /**
     * Vytvorí nového hráča, priradí mu identifikačné číslo a pokúsi
     * sa ním obsadiť políčko na zadaných súradniciach.
     * @param id identifikačné číslo hráča
     * @param x súradnica stĺpca 
     * @param y súradnica riadku
     * @return Vracia hráča v prípade úspešného obsadenia políčka, inak null.
     */
    public Player createPlayer(int id, int x, int y){
        if(this.map[x][y].canSeize() == false) return null;
        this.map[x][y].seize(player);
        return new Player(id,map[x][y]);
    }
}
