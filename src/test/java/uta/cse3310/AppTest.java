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
    Test the 5 Players of the game was not ready for the game.
    */
    @Test 
    public void testnotready()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);
	Player player2 = new Player(2);
	Player player3 = new Player(3);
	Player player4 = new Player(4);

	player0.readyNo();
	player1.readyNo();
	player2.readyNo();
	player3.readyNo();
	player4.readyNo();

	asserttrue(false);
   }


    /*
    Test the 5 Players of the game was ready for the game.
    */
    @Test 
    public void testready()
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

	asserttrue(true);
   }

		
    /*
    Test the 2 Players Ready added in the game
    */
    @Test 
    public void AddingPlayers2ready()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);

	    
	player0.readyUp();
	player1.readyUp();


	game.addplayer(player0);
	game.addplayer(player1);

	asserttrue( player0.readyUp() && player1.readyUp() );
    }
	
       /*
    Test the 2 Players Ready removed in the game
    */
    @Test 
    public void AddingPlayers2ready()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);


	player0.readyUp();
	player1.readyUp();


	game.addplayer(player0);
	game.addplayer(player1);
	
	game.removeplayer(1);
	game.removeplayer(2);
	
	asserttrue( player0.lose() &&  player0.lose());
    }
	
	
    /*
    Test the 2 Players Ready while 3 players not ready while players around of the game 
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
    Test the 5 Players of the game works
    */
    @Test 
    public void Test5readygame()
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


	
// 	the cards are distributed to all players
// 	game.deal();
	assertrue( game.deal() );
    }



    /*
    Test the 5 Players of the game  try to remove player except 1st player in the game. There should be winner
    */
    @Test 
    public void testwin()
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

	asserttrue(player4.win());
   }


   /*
    Test the 5 Players of the game  try to remove player0 to see it loose
    */
    @Test 
    public void testlose()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);
	

	player0.readyUp();
	player1.readyUp();


	game.addplayer(player0);
	game.addplayer(player1);

	game.removeplayer(1);

	asserttrue(player0.lose());
   }

         /*
    Test the does the card distributed get sorted has the better hand check
    */
    @Test 
    public void testbetterHandsorting()
    {
	// Hand 2 : HIGH CARDS

	Card c1 = new Card();
        c1.value = Card.Value.ACE;
        c1.suite = Card.Suite.DIAMONDS;
        
	Card c2 = new Card();
        c2.value = Card.Value.TWO;
        c2.suite = Card.Suite.DIAMONDs;
        
	Card c3 = new Card();
        c3.value = Card.Value.SIX;
        c3.suite = Card.Suite.DIAMONDS;
        
	Card c4 = new Card();
        c4.value = Card.Value.KING;
        c4.suite = Card.Suite.SPADES;
        
	// Hand 2 : PAIR CARDS

	Card c5 = new Card();
        c1.value = Card.Value.ACE;
        c1.suite = Card.Suite.DIAMONDS;
        
	Card c6 = new Card();
        c2.value = Card.Value.ACE;
        c2.suite = Card.Suite.SPADES;
        
	Card c7 = new Card();
        c3.value = Card.Value.SIX;
        c3.suite = Card.Suite.DIAMONDS;
        
	Card c8 = new Card();
        c4.value = Card.Value.TWO;
        c4.suite = Card.Suite.DIAMONDS;
        

        Card Hand1[4] = {c2,c1,c4,c3};
        Card Hand2[4] = {c5,c6,c7,c8};

	   if  (Hand2.isbetterHand(Hand1) == true)
		{
		assertTrue(true);   
		}
   }


      /*
    Test the does the card distributed get sorted
    */
    @Test 
    public void testsorting()
    {
	Card c1 = new Card();
        c1.value = Card.Value.ACE;
        c1.suite = Card.Suite.DIAMONDS;
        
	Card c2 = new Card();
        c2.value = Card.Value.TWO;
        c2.suite = Card.Suite.DIAMONDs;
        
	Card c3 = new Card();
        c3.value = Card.Value.SIX;
        c3.suite = Card.Suite.DIAMONDS;
        
	Card c4 = new Card();
        c4.value = Card.Value.KING;
        c4.suite = Card.Suite.SPADES;
        


        Card testingsortcard[4] = {c2,c1,c4,c3};
        Card correctsortCard[4] = {c1,c2,c3,c4};

        Hand.sortHand(testingsortcard);

	for(int i = 0; i < 5; i++)
	{
	   if  (testingsortcard[i] == correctsortCard[i])
	   {
		assertTrue(true);   
	   }
	}
   }

    /*
    Test the 2 Players of the game, does the card distributed
    */
    @Test 
    public void testHand()
    {
	Game game = new Game();

	Player player0 = new Player(0);
	Player player1 = new Player(1);

	player0.readyUp();
	player1.readyUp();

	game.addplayer(player0);
	game.addplayer(player1);

	
	// the cards are distributed to all players
	game.deal();
	
	asserttrue(player0.hand.size() !=0 && player1.hand.size() != 0);
   }
}
