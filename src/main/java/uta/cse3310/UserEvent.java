package uta.cse3310;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserEvent {

    public enum UserEventType {
        NAME, STAND, HIT, BET, FOLD, DRAW, COIN, PASSWORD ,EXIT;

        private UserEventType() {
        }
    };

    UserEventType event;
    int playerID;
    String name;
    int[] discard;
    int coin;

    public UserEvent() {
    }

}
