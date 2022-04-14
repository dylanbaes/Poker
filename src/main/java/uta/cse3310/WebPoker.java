package uta.cse3310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import org.java_websocket.WebSocket;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple WebSocketServer implementation. Keeps track of a "chatroom".
 */
public class WebPoker extends WebSocketServer {

  private int numPlayers;
  private Game game;
  // to protect the game object from concurrent access
  private Object mutex = new Object();

  private void setNumPlayers(int N) {
    numPlayers = N;
  }

  public WebPoker(int port) throws UnknownHostException {
    super(new InetSocketAddress(port));
  }

  public WebPoker(InetSocketAddress address) {
    super(address);
  }

  public WebPoker(int port, Draft_6455 draft) {
    super(new InetSocketAddress(port), Collections.<Draft>singletonList(draft));
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {

    System.out.println(
        conn.getRemoteSocketAddress().getAddress().getHostAddress() + " connected");

    // Since this is a new connection, it is also a new player
    numPlayers = numPlayers + 1; // player id's start at 0
    Player player = new Player(numPlayers);
    /*
      TODO: 
        How I am trying to implement the new game functionality is there is a waiting and ready room, which is already implemented
        Once there is more than one player in a lobby, the game switches from the waiting room to the ready room where each player can declare if they are ready
        This then enters a while loop that processes the number of players that are ready which is determine by player.readyUp
        If player.readyUp == this.numPlayers, then create a new Game etc etc
        
        The transition from waiting room to ready room is determined by whether there is more than one player in the lobby, which should be communicated from the server to the client only when it happens

      ALTERNATIVE:
        There is one initial game that is created, which is already written in this code.. but can a game object, instantiate itself when we are trying to create a new game.
    */
    if (numPlayers == 0) {
      System.out.println("starting a new game");
      game = new Game();
    }
    // This puts the player number into the conn data structure so
    // it is available later on
    conn.setAttachment(numPlayers);

    // this is the only time we send info to a single client.
    // it needs to know it's player ID.
    conn.send(player.asJSONString());
    synchronized (mutex) {
      game.addPlayer(player);
    }

    // and as always, we send the game state to everyone
    synchronized (mutex) {
      broadcast(game.exportStateAsJSON());
      System.out.println("the game state" + game.exportStateAsJSON());
    }
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println(conn + " has closed");

    // the player number of this connection was saved earlier when the
    // websocket connection was opened.
    int idx = conn.getAttachment();

    
    synchronized (mutex) {
      game.removePlayer(idx);

      System.out.println("removed player index " + idx);

      // The state is now changed, so every client needs to be informed
      broadcast(game.exportStateAsJSON());
      System.out.println("the game state" + game.exportStateAsJSON());
    }
  }

  @Override
  public void onMessage(WebSocket conn, String message) {

    synchronized (mutex) {
      // all incoming messages are processed by the game
      if (game.start == 0 && game.newgame == 0) {
        Player.processReady(message);
        if(Player.readyUp == game.players.size()) {
          game.start = 1;
          System.out.println("Game has started");
          broadcast(game.exportStateAsJSON());
        }
        //System.out.println(Player.readyUp);
      } /*else if (game.start == 1 && game.newgame == 1) { // If the game has started and finished and a new game is being requested
        ArrayList<Player> oldplayers = game.players;
        game = new Game();
        game.players = oldplayers;
        System.out.println("A new game has been started");
        broadcast(game.exportStateAsJSON());
      } 
      */
      else {
        game.processMessage(message);
        // and the results of that message are sent to everyone
        // as the "state of the game"

        broadcast(game.exportStateAsJSON());
      }
      
    }
    System.out.println(conn + ": " + message);
  }

  @Override
  public void onMessage(WebSocket conn, ByteBuffer message) {
    synchronized (mutex) {
      broadcast(message.array());
    }
    System.out.println(conn + ": " + message);
  }

  public class upDate extends TimerTask {

    @Override
    public void run() {
      if (game != null) {
        synchronized (mutex) {
        }
        if (game.update()) {
          ArrayList<Player> oldplayers = game.players;
          game = new Game();
          game.players = oldplayers;
          game.deal();
          System.out.println("A new game has been started");
          System.out.println(game.start);
          broadcast(game.exportStateAsJSON());
        }
      }
    }
  }

  public static void main(String[] args) throws InterruptedException, IOException {

    // Create and start the http server

    HttpServer H = new HttpServer(8087, "./html");
    H.start();

    // create and start the websocket server

    int port = 8887;

    WebPoker s = new WebPoker(port);
    s.start();
    System.out.println("WebPokerServer started on port: " + s.getPort());

    // Below code reads from stdin, making for a pleasant way to exit
    BufferedReader sysin = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String in = sysin.readLine();
      s.broadcast(in);
      if (in.equals("exit")) {
        s.stop(1000);
        break;
      }
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    ex.printStackTrace();
    if (conn != null) {
      // some errors like port binding failed may not be assignable to a specific
      // websocket
    }
  }

  @Override
  public void onStart() {
    System.out.println("Server started!");
    setConnectionLostTimeout(0);
    setConnectionLostTimeout(100);
    setNumPlayers(-1);
    // once a second call update
    // may want to start this in the main() function??
    new java.util.Timer().scheduleAtFixedRate(new upDate(), 0, 1000);
  }

}