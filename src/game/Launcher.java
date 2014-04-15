package game;

import java.io.IOException;

/**
 * Třída launcher obsahuje metodu main a ta vytváří novou instanci třídy Game.
 * Invokací metody readCommand() se spustí hlavní načítací cyklus příkazů, který
 * reaguje na příkazy uživatele a volá metody dalších tříd, které příkazy provedou.
 * V rámci běhu metody readCommand může být vyvolána výjimka IOException, kdy
 * uživatel jako parametr příkazu game zadá název neexistujícího vstupního souboru.
 * @author P.Kadlec, T.Hudziec
 */
public class Launcher {
  public static void main(String[] argv) throws IOException {
    System.out.println("Game menu, enter command: ");
    Game newGame = new Game();
    try {
      newGame.readCommand();
    }
    catch(IOException e) {
      System.err.println("Vyhozena IO vyjimka.");
      System.exit(1);
    } 
  }
}
