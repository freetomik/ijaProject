package game;

import matrix.*;

import java.util.Scanner;
import java.io.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Třída Game obsahuje metody readCommand a loadMap, viz jejich konkrétní popis.
 * Třída obsahuje atributy pro uchování informace jaká mapa je otevřena a kteří
 * hráči momentálně hrají a další pomocné informace.
 * @author P.Kadlec, T.Hudziec
 */
public class Game {
  
  protected Matrix map = null;
  protected Player[] players;
  protected Zombie[] zombies;
  protected String mapName = "";
  protected String fileContent = "";
  protected int gameID;
  protected int width = 0, height = 0;
  protected int playersCount;
  protected int[] clientIDs;
  protected int[] spawnPoints;
  protected float delay;    //delay mezi kroky v prikazu go
  protected long gameStartTime;
  protected LinkedBlockingQueue messagesOUT;

  public void Game(String mapNameDelay, int gameID, LinkedBlockingQueue messagesOUT) {
    String MNDParse[];
    MNDParse = mapNameDelay.split(":");
    this.mapName = MNDParse[0];
    this.delay = Float.parseFloat(MNDParse[1]);
    this.gameID = gameID;
    this.messagesOUT = messagesOUT;
    this.playersCount = 0;
  }

  /**
   * Pokud hra neni plne obsazena(4 hraci), vytvori noveho hrace na prvnim volnem spawn pointu v poradi.
   * Hrace svaze s klientem pres pole clientsID, na indexu ID hrace prida ID klienta.
   * @param clientID
   * @return vraci zda se podarilo pridat hrace
   */
  public boolean addPlayer(int clientID) {
    Player tmpPlayer = null;
    if(this.playersCount < 4) {
      this.clientIDs[this.playersCount++] = clientID;
      for(int i = 0; i < this.spawnPoints.length; i+=2) {
        tmpPlayer = this.map.createPlayer(this.playersCount, this.spawnPoints[i], this.spawnPoints[i+1]);
        if(tmpPlayer != null) {
          this.players[this.playersCount] = tmpPlayer;
          return true;
        }
      }
    }
    return false;
  }

  public boolean executeCommand(int clientID, String command) throws IOException {
      
    String fileName;

//    switch(command) {
//      case "step":
//        if(this.player.step()) System.out.println("stepped forward");
//        else System.out.println("could not step forward");
//        if(this.player.finished()) {
//          System.out.println("player " + 1 + " won !!!");
//          System.exit(0);
//        } 
//        break;
//      case "right":
//        this.player.turnRight();
//        break;
//      case "left":
//        this.player.turnLeft();
//        break;
//      case "take":
//        if(this.player.take()) System.out.println("key was taken");
//        else System.out.println("could not take the key");
//        break;
//      case "open":
//        if(this.player.open()) System.out.println("gate was open");
//        else System.out.println("could not open the gate");
//        break;
//      case "keys":
//        System.out.println("num of keys: " + this.player.getKeys());
//        break;
//      case "show":
//        this.map.showMap();
//        break;
//      case "close":
//        System.out.println("Closing...");
//        System.exit(0);
//        break;
//      case "go":
//        while(this.player.step()) {
//
//        }
//      default:
//        System.out.println("wrong command");
//        break;
//    }//switch
//
//    System.out.println("End.");

    return true;

  }
  /**
  * Metoda loadMap otevře soubor a jeho obsah reprezentující mapu bludiště
  * zapíše do řetězce. Ignorují se přitom mezery a konce řádků.
  * Při neexistenci vstupního souboru se zachytí výjimka FileNotFoundException.
  * @param fileName vstupního souboru
  * @return řetězec s obsahem vstupního souboru
  */
  public String loadMap(String fileName) {
    int sp = 0, len;     //sp - index do pole spawn pointu, len - delka radku
    char c;
    String fileLine;
    Scanner fileInput;
    this.spawnPoints = new int[7];
    try {
      fileInput = new Scanner(new FileReader(fileName));
      while(fileInput.hasNextLine()) {
        fileLine = fileInput.nextLine();  //cteni souboru po radcich
        len = fileLine.length();
        this.width = 0;
        for(int i = 0; i < len; i++) {    //prochazeni radku po znacich
          c = fileLine.charAt(i);
          if(c != ' ') {      //ignorace mezer
            if(c == 'p' && sp < 7) {    //nacitani spawn pointu, max sedm
              this.spawnPoints[sp++] = this.width;
              this.spawnPoints[sp++] = this.height;
            }
            this.fileContent += c;  //pridani znaku do vystupniho retezce
            this.width++;
          }
        }
        //System.out.println(fileLine);
        this.height++;
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
