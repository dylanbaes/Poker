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
    int turn = 0; // player ID that has the current turn
    int round_num = 1;
    int winner_id = -1;
    ArrayList<Integer> whoDrew = new ArrayList<>();
    int[] money = {100,100,100,100,100};
    int pot = 0;
    int losers = 0;

    public Game() {
        System.out.println("creating a Game Object");
        Player.readyUp = 0;
        start = 0;
        newgame = 0;
        turn = 0;
        round_num = 1;
        winner_id = -1;
        whoDrew = new ArrayList<>();
        pot = 0;
        losers = 0;
        Player.pile = new HashMap<>();
    }

    public void deal() {
        int[] discards = {1,2,3,4,5};
        for (int i = 0; i < players.size(); i++) {
            players.get(i).Cards = players.get(i).draw(players.get(i).Cards, discards);
        }
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
        players.get(playerid).lose = true;
        players.remove(playerid);
    }

    public void processMessage(String msg) {

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        // take the string we just received, and turn it into a user event
        UserEvent event = gson.fromJson(msg, UserEvent.class);
        //int count_losses = 0;
        if(event.event == UserEventType.END) {
            round_num = 5;
        }
        if (event.event == UserEventType.NAME) {
            players.get(event.playerID).SetName(event.name);
        }
        else if (event.event == UserEventType.FOLD) {
            players.get(event.playerID).lose();
            System.out.println(event.playerID + " folded");
            losers++;
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
            players.get(event.playerID).Cards = players.get(event.playerID).draw(players.get(event.playerID).Cards, event.discard);
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
            turn++;
            
        }
        /*
        if (players.get(turn).lose) {
            turn++;
        }
        */
        if (turn > players.size() - 1) {
            turn = 0;
            round_num += 1;
        }
        if (players.get(turn).lose) {
            turn++;
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
            }
            if (hands[0].is_better_than(hands[1])) {
                players.get(1).lose();
                winner_id = 0;
            } 
            else if (hands[1].is_better_than(hands[0])) {
                players.get(0).lose();
                winner_id = 1;
            } else {
                winner_id = 500; // A winner id of 500 means that there has been an error
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
        /*
        for(i=0;i<players.size();i++) // Count number of players who've lost
        {
            if(players.get(i).lose)
            {
                count_losses++;
            }
        }
        if(count_losses == players.size() - 1) // If every player but one lost, set winner_id
        {
            for(i=0;i<players.size();i++)
            {
                if(!players.get(i).lose)
                {
                    players.get(i).win = true;
                    winner_id = i;
                }
            }
        }
        */

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
