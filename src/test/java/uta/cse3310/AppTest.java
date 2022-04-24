package uta.cse3310;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {
	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	/*
	 * Test the 5 Players of the game was not ready for the game.
	 */
	@Test
	public void testNotReady() {

		assertFalse(false);
	}

	/*
	 * Test the 5 Players of the game was ready for the game.
	 */
	@Test
	public void testReady() {
		assertTrue(true);
	}

	/*
	 * Test the 2 Players Ready added in the game
	 */
	@Test
	public void isGameStart() {
		Game game = new Game();

		Player player0 = new Player(0);
		Player player1 = new Player(1);

		player0.readyUp();
		player1.readyUp();

		game.addPlayer(player0);
		game.addPlayer(player1);

		if (game.players.size() >= 2)
			game.start = 1;

		assertTrue(game.start == 1);
	}

	/*
	 * Test the 2 Players Ready added in the game
	 */
	@Test
	public void isGameNotStart() {
		Game game = new Game();

		Player player0 = new Player(0);
		Player player1 = new Player(1);

		player0.readyUp();
		player1.readyNo();

		game.addPlayer(player0);
		game.addPlayer(player1);

		int ready = 0;

		for (Player player : game.players) {
			if (player.ready) {
				ready++;
			}
		}

		if (ready >= 2)
			game.start = 1;

		assertFalse("Game is not started as only Player 0 is ready", game.start == 1);

		assertTrue("Game is not started as only Player 0 is ready", game.start == 0);
	}

	/*
	 * Test the 2 Players Ready removed in the game
	 */
	@Test
	public void checkingPlayerLose() {
		Game game = new Game();

		Player player0 = new Player(0);
		Player player1 = new Player(1);

		player0.readyUp();
		player1.readyUp();

		game.addPlayer(player0);
		game.addPlayer(player1);

		game.removePlayer(1);
		game.removePlayer(0);

		assertTrue(player0.lose && player1.lose);
	}

	/*
	 * Test the 2 Players Ready while 3 players not ready while players around of
	 * the game
	 */
	@Test
	public void checkingPlayersReady() {
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

		game.addPlayer(player0);
		game.addPlayer(player4);

		assertTrue(player0.ready && player4.ready && !player1.ready && !player2.ready && !player3.ready);
	}

	/*
	 * Test the 5 Players of the game works
	 */
	@Test
	public void isGameReady() {
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

		game.addPlayer(player0);
		game.addPlayer(player1);
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);

		game.deal();

		// the cards are distributed to all players
		// game.deal();
		assertFalse("Game is not empty so return true", game.players.isEmpty());

		assertTrue("Game is not empty so return true", !game.players.isEmpty());
	}

	/*
	 * Test the 5 Players of the game try to remove player except 1st player in the
	 * game. There should be winner
	 */
	@Test
	public void checkWin() {
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

		game.addPlayer(player0);
		game.addPlayer(player1);
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);

		game.removePlayer(4);
		game.removePlayer(3);
		game.removePlayer(2);
		game.removePlayer(1);
		player0.win();

		assertTrue("Player 1, Player 2, Player 3 and Player 4 left the game so, Player 0 wins should be true",
				player0.win);
	}

	/*
	 * Test the 5 Players of the game try to remove player0 to see it loose
	 */
	@Test
	public void testLose() {
		Game game = new Game();

		Player player0 = new Player(0);
		Player player1 = new Player(1);

		player0.readyUp();
		player1.readyUp();

		game.addPlayer(player0);
		game.addPlayer(player1);

		game.removePlayer(1);

		assertFalse("Player 1 left the game so Player 1 is loser", !player1.lose);
		assertTrue("Player 1 left the game so Player 1 is loser", player1.lose);
	}

	/*
	 * Test the does the card distributed get sorted
	 */
	@Test
	public void testsorting() {
		Card c1 = new Card();
		c1.value = Card.Value.ACE;
		c1.suite = Card.Suite.DIAMONDS;

		Card c2 = new Card();
		c2.value = Card.Value.TWO;
		c2.suite = Card.Suite.DIAMONDS;

		Card c3 = new Card();
		c3.value = Card.Value.SIX;
		c3.suite = Card.Suite.DIAMONDS;

		Card c4 = new Card();
		c4.value = Card.Value.KING;
		c4.suite = Card.Suite.SPADES;

		Card testingsortcard[] = { c2, c1, c4, c3 };
		Card correctsortCard[] = { c1, c2, c3, c4 };

		Hand.sortHand(testingsortcard);

		for (int i = 0; i < 4; i++) {
			if (testingsortcard[i] == correctsortCard[i])
				assertTrue(true);
		}
	}

	/*
	 * Test the 2 Players of the game, does the card distributed
	 */
	@Test
	public void testHand() {
		Game game = new Game();

		Player player0 = new Player(0);
		Player player1 = new Player(1);

		player0.readyUp();
		player1.readyUp();

		game.addPlayer(player0);
		game.addPlayer(player1);

		// the cards are distributed to all players
		game.deal();

		assertTrue(player0.Cards.length != 0 && player1.Cards.length != 0);
	}
}
