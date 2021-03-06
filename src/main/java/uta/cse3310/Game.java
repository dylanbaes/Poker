package uta.cse3310;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import uta.cse3310.UserEvent.UserEventType;
import uta.cse3310.*;

// mvn exec:java -Dexec.mainClass=uta.cse3310.WebPoker


/*
For players that fold, they cannot make any more turns.
For players that leave, default them to a fold.
*/
public class Game {

    ArrayList<Player> players = new ArrayList<>();
    public int start = 0;
    public int newgame = 0;
    int listturn = 0; // index turn processor for player arraylist
    int turn = 0; // player ID that has the current turn
    int round_num = 1;
    int winner_id = -1;
    ArrayList<Integer> whoDrew = new ArrayList<>();
    int[] money = {100,100,100,100,100};
    int pot = 0;
    int losers = 0;
    public static HashMap<Integer, Integer> pile = new HashMap<>();

    public Game() {
        System.out.println("creating a Game Object");
        Player.readyUp = 0;
        start = 0;
        listturn = 0;
        newgame = 0;
        turn = 0;
        round_num = 1;
        winner_id = -1;
        whoDrew = new ArrayList<>();
        pot = 0;
        losers = 0;
        pile = new HashMap<>();
    }

    public void deal() {
        int set = 0;
        for (int p = 0; p < players.size(); p++) {
            for (int i = 0; i < 5; i++) {
                set = 0;
                while (set == 0) {
                    players.get(p).Cards[i] = new Card();
                    players.get(p).Cards[i].suite = Card.Suite.randomSuite();
                    players.get(p).Cards[i].value = Card.Value.randomValue();
                    if (!pile.containsKey(players.get(p).Cards[i].suite.val+players.get(p).Cards[i].value.val)) {
                        pile.put(players.get(p).Cards[i].suite.val+players.get(p).Cards[i].value.val, 1);
                        players.get(p).CardId[i] = players.get(p).Cards[i].suite.val+players.get(p).Cards[i].value.val;
                        set++;
                    } else {
                        continue;
                    }
                }
            }
        }
        turn = players.get(listturn).Id;
        losers = 0;
    }

    public Card[] draw(int playerID, Card[] cards, int[] discard) {
        if (discard.length == 0) {
            return cards;
        }
        System.out.println("length = " +discard.length);
        int set = 0;
        for (int i = 0; i < discard.length; i++) {
            set = 0;
            while (set == 0) {
                cards[discard[i]-1].suite = Card.Suite.randomSuite();
                cards[discard[i]-1].value = Card.Value.randomValue();
                if (!pile.containsKey(cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val)) {
                    pile.put(cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val, 1);
                    players.get(playerID).CardId[discard[i]-1] = cards[discard[i]-1].suite.val+cards[discard[i]-1].value.val;
                    set++;
                } else {
                    continue;
                }
            }
        }
        
        return cards;
    }

    public String exportStateAsJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(int playerid) {
        // given it's player number, this method
        // deletes the player from the players array
        // and does whatever else is needed to remove
        // the player from the game.
        System.out.println(playerid +" has been removed");
        int index = 0;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).Id == playerid) {
                index = i;
            }
        }
        players.get(index).lose = true;
        players.get(index).active = false;
    }

    public void processMessage(String msg) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // take the string we just received, and turn it into a user event
        UserEvent event = gson.fromJson(msg, UserEvent.class);
        //int count_losses = 0;
        turn = players.get(listturn).Id;
        if(event.event == UserEventType.END) {
            round_num = 5;
        }
        if (event.event == UserEventType.NAME) {
            players.get(event.playerID).SetName(event.name);
        }
        else if (event.event == UserEventType.FOLD) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).Id == event.playerID) {
                    players.get(i).lose();
                }
            }
            System.out.println(event.playerID + " folded"); 
            losers++;
            System.out.println("There are " + losers + " out of " + players.size() + " lost");
            if (losers == players.size()-1) {
                
                for (int i = 0; i < players.size(); i++) {
                    if (!players.get(i).lose) {
                        players.get(i).win=true;
                        winner_id=i;
                        System.out.println((winner_id+1) + " Won");
                    }
            }
            
            }
            // if the player folds, take them out of the game and should not be able to make moves anymore
        } 
        else if (event.event == UserEventType.DRAW) {
            int index = 0;
            for (int i = 0 ; i < players.size(); i++) {
                if (players.get(i).Id == event.playerID) {
                    index = i;
                }
            }
            players.get(index).Cards = draw(index, players.get(index).Cards, event.discard);
            if(event.discard.length!=0)
            {
                whoDrew.add(event.playerID+1);
            }
        }
        else if (event.event == UserEventType.BET) {
            pot = pot + event.amount;
            money[event.playerID] = money[event.playerID] - event.amount;
        }
        if(event.event == UserEventType.WIN)
        {
            money[event.playerID] = money[event.playerID] + pot;
            round_num = 5;
            return;
        }
        else
        {
            listturn++;
            if (listturn > players.size() - 1) {
                listturn = 0;
                turn = players.get(listturn).Id;
                round_num += 1;
            } else {
                turn = players.get(listturn).Id;
            }
            
        }
        
        if (players.get(listturn).lose) {
            listturn++;
            if (listturn > players.size() - 1) {
                listturn = 0;
                turn = players.get(listturn).Id;
                round_num += 1;
            } else {
                turn = players.get(listturn).Id;
            }
        }
        if (round_num==2) {
            //It is draw round and we need to discard and draw new cards for each user if they choose to

        }
        if (round_num==4) {
            Hand[] hands = new Hand[5];
            for (int j = 0; j < players.size(); j++) {
                players.get(j).Cards = Hand.sortHand(players.get(j).Cards);
                hands[j] = new Hand();
                hands[j].cards = players.get(j).Cards;
                hands[j].rank = hands[j].ranking(hands[j]);
            }
            winner_id = 0;
            //System.out.println("Player 1 has " + hands[0].rank.rank);
            for (int i = 1; i < players.size(); i++) {
                System.out.println("Player " + (i+1) + " has " + hands[i].rank.rank);
                if (hands[i].rank.rank > hands[winner_id].rank.rank) {
                    players.get(winner_id).lose();
                    winner_id = i;
                }
            }
            //It is the showdown round and put the hands of both players through the is_better_than() in Hand.java
        }
        else if (round_num > 4) {
            if(event.event == UserEventType.END) {
                round_num = 5;
            }
            else if (event.event == UserEventType.NEW) {
                newgame = 1;
            }
        }

    }

    

    public boolean update() {

        // this function is called on a periodic basis (once a second) by a timer
        // it is to allow time based situations to be handled in the game
        // if the game state is changed, it returns a true.
        /*
        seconds = seconds + 1;
        if ((seconds % 10) == 0) {
            turn = turn + 1;
            if (turn == 5) {
                turn = 0;
            }
            return true;
        }
        return false;

        */
        if (this.newgame == 1) {
            return true;
        }

        return false;
        // expecting that returning a true will trigger a send of the game
        // state to everyone


    }

    
    

}
/*

    TODO Allow for more than 2 players to play
    TODO Only create Game object when all players ready up
    TODO Destroy Game object once the game has finished
    TODO There is a bug of when a player folds, it still goes through all 4 rounds of just alerting who won

*/
