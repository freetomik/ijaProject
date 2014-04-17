package game;

import matrix.*;

import java.util.Scanner;
import java.io.*;

/**
 * Třída Game obsahuje metody readCommand a loadMap, viz jejich konkrétní popis.
 * Třída obsahuje atributy pro uchování informace jaká mapa je otevřena a kteří
 * hráči momentálně hrají a další pomocné informace.
 * @author P.Kadlec, T.Hudziec
 */
public class Game {
  
  protected Matrix map = null;
  protected Player player = null;
  protected boolean inGame = false;
  protected int x = 0, y = 0, xp = 0, yp = 0;
  protected String fileContent = "";

  public void Game() {}

  /**
   *  Metoda readCommand čeká na uživatelovy příkazy a reaguje na ně.
   *  Rozlišují se přitom dva módy, před a po otevření hry.
   *  Před otevřením hry jsou možné dva příkazy, otevření hry a ukončení programu.
   *  Ve hře jsou k dispozici příkazy pro ovládání hry a taky příkaz k ukončení.
   *  Příkazy pro ovládání hráče mu zasílají zprávy, jakou operaci má provést.
   *  Příkaz show invokuje metodu objektu hrací mapy, slouží k jejímu výpisu.
   *  Hra je ukončena zadáním příkazu close nebo když se postavička hráče dostane
   *  na cílové políčko.
  */
  public void readCommand() throws IOException {

    String command, words[], fileName;
    Scanner CLInput = new Scanner(System.in);

    while(true) {
      command = CLInput.nextLine();
      
      if(!this.inGame) {
        if(command.equals("close")) {
          System.out.println("Closing...");
          break;
        }
        words = command.split(" ");   //rozdelim vstup na slova po mezerach
        if(words[0].equals("game")) {
          if(words.length > 1) {
            fileName = words[1];
            this.fileContent = loadMap(fileName);
            this.inGame = true;
            this.map = new Matrix(this.fileContent, this.x, this.y);
            this.player = this.map.createPlayer(1, this.xp, this.yp);

            System.out.println("game started with map from file " + fileName);
          }
        }
        else {
          System.out.println("wrong command");
        }
      }

      else if(this.inGame) {
        switch(command) {
          case "step":
            if(this.player.step()) System.out.println("stepped forward");
            else System.out.println("could not step forward");
            if(this.player.finished()) {
              System.out.println("player " + 1 + " won !!!");
              System.exit(0);
            } 
            break;
          case "right":
            this.player.turnRight();
            break;
          case "left":
            this.player.turnLeft();
            break;
          case "take":
            if(this.player.take()) System.out.println("key was taken");
            else System.out.println("could not take the key");
            break;
          case "open":
            if(this.player.open()) System.out.println("gate was open");
            else System.out.println("could not open the gate");
            break;
          case "keys":
            System.out.println("num of keys: " + this.player.getKeys());
            break;
          case "show":
            this.map.showMap();
            break;
          case "close":
            System.out.println("Closing...");
            System.exit(0);
            break;
          default:
            System.out.println("wrong command");
            break;
        }//switch
      }//game mode

      System.out.print(((this.inGame) ? "[game]" : "[menu]") + "next command: ");

    }//cyklus nacitani prikazu

    System.out.println("End.");

  }
  /**
  * Metoda loadMap otevře soubor a jeho obsah reprezentující mapu bludiště
  * zapíše do řetězce. Ignorují se přitom mezery a konce řádků.
  * Při neexistenci vstupního souboru se zachytí výjimka FileNotFoundException.
  * @param fileName vstupního souboru
  * @return řetězec s obsahem vstupního souboru
  */
  public String loadMap(String fileName) {
    int len;     //all - vsechny nebile znaky v souboru, len - delka radku
    char c;
    String fileLine;
    Scanner fileInput;
    try {
      fileInput = new Scanner(new FileReader(fileName));
      while(fileInput.hasNextLine()) {
        fileLine = fileInput.nextLine();  //cteni souboru po radcich
        len = fileLine.length();
        this.x = 0;
        for(int i = 0; i < len; i++) {    //prochazeni radku po znacich
          c = fileLine.charAt(i);
          if(c != ' ') {      //ignorace mezer
            if(c == 'p') {    //nalezeni hrace a ulozeni jeho pocatecni pozice
              this.yp = this.y;
              this.xp = this.x;
            }
            this.fileContent += c;  //pridani znaku do vystupniho retezce
            this.x++;
          }
        }
        //System.out.println(fileLine);
        this.y++;
      }
      fileInput.close();
      //System.out.println("rows: " + this.y);
      //System.out.println("columns: " + this.x);
}
    catch(FileNotFoundException e) {
//      e.printStackTrace();
      System.out.println("File " + fileName + " does not exist.");
    } 
    return this.fileContent;
  }

}
