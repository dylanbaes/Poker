package uta.cse3310;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
     /*
    Test the 5 Players of the game works
    */
    @Test 
    public void AddingPlayers5ready()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);
	Player player2 = new Player(2);
	Player player3 = new Player(3);
	Player player4 = new Player(4);

	player0.readyUp();
	player1.readyUp();
	player2.readyUp();
	player3.readyUp();
	player4.readyUp();


	game.addplayer(player0);
	game.addplayer(player1);
	game.addplayer(player2);	
	game.addplayer(player3);
	game.addplayer(player4);

	asserttrue( !player0.readyUp && !player1.readyUp && !player2.readyUp && !player3.readyUp && !player4.readyUp);
    }


    /*
    Test the 2 Players Ready while 3 players not ready of the game 
    */
    @Test 
    public void AddingPlayers2ready()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);
	Player player2 = new Player(2);
	Player player3 = new Player(3);
	Player player4 = new Player(4);

	player0.readyUp();
	player1.readyNo();
	player2.readyNo();
	player3.readyNo();
	player4.readyUp();

	game.addplayer(player0);
	game.addplayer(player4);

	asserttrue( !player0.readyUp && !player1.readyNo && !player2.readyNo && !player3.readyNo && !player4.readyUp);
    }


    /*
    Test the 5 Players of the game  try to remove player except 1st player
    */
    @Test 
    public void testremoveplayer()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);
	Player player2 = new Player(2);
	Player player3 = new Player(3);
	Player player4 = new Player(4);

	player0.readyUp();
	player1.readyUp();
	player2.readyUp();
	player3.readyUp();
	player4.readyUp();


	game.addplayer(player0);
	game.addplayer(player1);
	game.addplayer(player2);	
	game.addplayer(player3);
	game.addplayer(player4);

	game.removeplayer(1);
	game.removeplayer(2);	
	game.removeplayer(3);
	game.removeplayer(4);

	asserttrue(win());
   }
}
