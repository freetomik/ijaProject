package game;

import matrix.*;
import net.*;
import serialMessage.*;

import java.util.Scanner;
import java.io.*;

/**
 * Třída Game obsahuje metody readCommand a loadMap, viz jejich konkrétní popis.
 * Třída obsahuje atributy pro uchování informace jaká mapa je otevřena a kteří
 * hráči momentálně hrají a další pomocné informace.
 * @author P.Kadlec, T.Hudziec
 */
public class Game {
  
  protected Server server;
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

  public Game(String mapNameDelay, int gameID, Server server) {
    String MNDParse[];
    MNDParse = mapNameDelay.split(":");
    this.mapName = MNDParse[0]+MNDParse[1];
    this.delay = Float.parseFloat(MNDParse[2]);
    loadMap(this.mapName);  //ulozim si obsah souboru do fileContent
    this.server = server;
    this.map = new Matrix(this.fileContent, this.width, this.height);
    this.gameID = gameID;
    this.players = new Player[4];
    this.zombies = new Zombie[2];
    this.spawnPoints = new int[14];
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
          this.playersCount++;
          return true;
        }
      }
    }
    return false;
  }

  public boolean executeCommand(int clientID, String command) throws IOException {
      
    Player selectedPlayer = null;
    Message returnMessage = new Message();
    int playerIndex = 0;
    boolean returnCode;

    returnMessage.setCode(2);

    for(int i : clientIDs)
      if(clientID == clientIDs[i]) {
        selectedPlayer = players[i];
        playerIndex = i;
        break;
      }
    
    if(selectedPlayer == null) return false;
    
    switch(command) {
      case "step":
        if((returnCode = selectedPlayer.step()) == true)
          returnMessage.setContent("stepped forward");
        else
          returnMessage.setContent("could not step forward");
        if(selectedPlayer.finished()) {
          returnMessage.setContent("player " + playerIndex + " won !!!");
        } 
        break;
      case "right":
        selectedPlayer.turnRight();
        returnCode = true;
        break;
      case "left":
        selectedPlayer.turnLeft();
        returnCode = true;
        break;
      case "take":
        if((returnCode = selectedPlayer.take()) == true) returnMessage.setContent("key was taken");
        else returnMessage.setContent("could not take the key");
        break;
      case "open":
        if((returnCode = selectedPlayer.open()) == true) returnMessage.setContent("gate was open");
        else returnMessage.setContent("could not open the gate");
        break;
      case "keys":
        returnMessage.setContent("num of keys: " + selectedPlayer.getKeys());
        returnCode = true;
        break;
      case "show":
        this.map.showMap();
        returnCode = true;
        break;
//      case "go":
//        while((returnCode = selectedPlayer.step()) == true) {
//
//        }
      default:
        returnMessage.setContent("wrong command");
        returnCode = false;
        break;
    }//switch

    this.server.addMessageOUT(returnMessage); 
    return returnCode;

  }
  /**
  * Metoda loadMap otevře soubor a jeho obsah reprezentující mapu bludiště
  * zapíše do řetězce. Ignorují se přitom mezery a konce řádků.
  * Při neexistenci vstupního souboru se zachytí výjimka FileNotFoundException.
  * @param fileName vstupního souboru
  */
  public void loadMap(String fileName) {
    int sp = 0, len;     //sp - index do pole spawn pointu, len - delka radku
    char c;
    String fileLine;
    Scanner fileInput;
    try {
      fileInput = new Scanner(new FileReader("examples/"+fileName));
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
}

public String getMapName() {
  return this.mapName;
}
  
}
