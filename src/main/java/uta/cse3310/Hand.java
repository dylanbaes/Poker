package uta.cse3310;

import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Hand extends Card {
    public enum Rank
    {
        ROYAL(9),
        STRAIGHT_FLUSH(10),
        FOUR(7),
        FULL(6),
        FLUSH(5),
        STRAIGHT(4),
        THREE(3),
        TWO(2),
        PAIR(1),
        HIGH(0);

        public final int rank;
        private Rank(int rank)
        {
            this.rank = rank;
        }
    }
    public Card[] cards;
    public Rank rank;
    public Hand()
    {
    }

    public static Card[] sortHand(Card[] hand) //translate array into map then sort then translate the map back into the array
    {
        ArrayList<Card> sortedHand = new ArrayList<Card>();
        for (int i = 0; i < hand.length; i++)
        {
            sortedHand.add(hand[i]);
        }
        Collections.sort(sortedHand);
        Card[] newHand = sortedHand.toArray(new Card[0]);
        return newHand;
    }
    
    public boolean is_better_than(Hand H)
    {
        this.rank = findRank(this.cards);
        H.rank = findRank(H.cards);
        System.out.println("The rank of the first deck is " + this.rank + " and the rank of the second deck is " + H.rank + " ");
        System.out.println("Comparing " + this.rank.rank + " against " + H.rank.rank);
        if (this.rank.rank > H.rank.rank)
        {
            return true;
        }
        if (this.rank.rank == H.rank.rank)
        {
            return this.is_equal(H);
        }
        return false;
    }

    public Rank findRank(Card[] cards)
    {
        if (royalFlush(cards))
        {
            return Rank.ROYAL;
        }
        if (straightFlush(cards))
        {
            return Rank.STRAIGHT_FLUSH;
        }
        if (four_of_a_kind(cards))
        {
            return Rank.FOUR;
        }
        if (full_house(cards))
        {
            return Rank.FULL;
        }
        if (Flush(cards))
        {
            return Rank.FLUSH;
        }
        if (straight(cards))
        {
            return Rank.STRAIGHT;
        }
        if (three_of_a_kind(cards))
        {
            return Rank.THREE;
        }
        if (two_pair(cards))
        {
            return Rank.TWO;
        }
        if (pair(cards))
        {
            return Rank.PAIR;
        }
        return Rank.HIGH;
    }
   
   public boolean is_equal(Hand H)
   {
       Rank cases;
       if (this.rank == H.rank)
       {
            cases = rank;
       }
       else
       {
           return false;
       }
       switch (cases)
       {
            case ROYAL:
                return false;
            case STRAIGHT_FLUSH:
                int hand1 = 0;
                int hand2 = 0;
                for (int i = 0; i < H.cards.length; i++)
                {
                    hand1 = this.cards[i].value.val;
                    hand2 = H.cards[i].value.val;
                }
                if (hand1 > hand2)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            case FOUR:
                //Two hashmaps, compare the keys with the values of one
                HashMap<Card.Value, Integer> map1 = new HashMap<>();
                HashMap<Card.Value, Integer> map2 = new HashMap<>();
                int counter = 0;
                int num1 = 0, num2 = 0;
                
                for (int i = 0; i < cards.length; i++)
                {
                if (!map1.containsKey(this.cards[i].value))
                    {
                        counter = 1;
                        map1.put(this.cards[i].value, counter);                
                    }
                else
                    {
                        counter = map1.get(this.cards[i].value);
                        counter++;
                        map1.put(this.cards[i].value, counter);
                    }
                if (!map2.containsKey(H.cards[i].value))
                    {
                        counter = 1;
                        map2.put(H.cards[i].value, counter);                
                    }
                else
                    {
                        counter = map2.get(H.cards[i].value);
                        counter++;
                        map2.put(H.cards[i].value, counter);
                    }
                }
                for (int i = 0; i < cards.length; i++)
                {
                    if (map1.get(this.cards[i].value) == 4)
                    {
                        num1 = this.cards[i].value.val;
                    }
                    if (map2.get(H.cards[i].value) == 4)
                    {
                        num2 = H.cards[i].value.val;
                    }
                }
                if (num1 > num2)
                {
                    return true;
                }
                if (num1 < num2)
                {
                    return false;
                }
                for(int i = 0; i < cards.length; i++)
                {
                    if(map1.get(this.cards[i].value) == 1)
                    {
                        //num1 = map1.get(this.cards[i].value);
                        num1 = this.cards[i].value.val;
                    }
                    if(map2.get(H.cards[i].value) == 1)
                    {
                        //num2 = map2.get(H.cards[i].value);
                        num2 = H.cards[i].value.val;
                    }
                }
                if (num1 > num2)
                {
                    return true;
                }
                return false;
            case FULL:
                HashMap<Card.Value, Integer> m1 = new HashMap<>();
                HashMap<Card.Value, Integer> m2 = new HashMap<>();
                int count = 0;
                int n1 = 0, n2 = 0;
                for (int i = 0; i < cards.length; i++)
                {
                if (!m1.containsKey(this.cards[i].value))
                    {
                        count = 1;
                        m1.put(this.cards[i].value, count);                
                    }
                else
                    {
                        count = m1.get(this.cards[i].value);
                        count++;
                        m1.put(this.cards[i].value, count);
                    }
                if (!m2.containsKey(H.cards[i].value))
                    {
                        count = 1;
                        m2.put(H.cards[i].value, count);                
                    }
                else
                    {
                        count = m2.get(H.cards[i].value);
                        count++;
                        m2.put(H.cards[i].value, count);
                    }
                }
                for (int i = 0; i < cards.length; i++)
                {
                    if (m1.get(this.cards[i].value) == 3)
                    {
                        n1 = this.cards[i].value.val;
                    }
                    if (m2.get(H.cards[i].value) == 3)
                    {
                        n2 = H.cards[i].value.val;
                    }
                }
                if (n1 > n2)
                {
                    return true;
                }
                if (n2 < n1)
                {
                    return false;
                }
                else
                {
                    n1 = 0;
                    n2 = 0;
                    for (int i = 0; i < cards.length; i++)
                    {
                        n1 += this.cards[i].value.val;
                        n2 += H.cards[i].value.val;
                    }
                    if (n1 > n2)
                    {
                        return true;
                    }
                    return false;
                }
            case FLUSH:
                num1 = 0;
                num2 = 0;
                for (int i = 0; i < cards.length; i++)
                {
                    num1 += this.cards[i].value.val;
                    num2 += H.cards[i].value.val;
                }
                if (num1 > num2)
                {
                    return true;
                }
                return false;
            case STRAIGHT:
                num1 = 0;
                num2 = 0;
                for (int i = 0; i < cards.length; i++)
                {
                    num1 += this.cards[i].value.val;
                    num2 += H.cards[i].value.val;
                }
                if (num1 > num2)
                {
                    return true;
                }
            return false;
            case THREE:
                map1 = new HashMap<>();
                map2 = new HashMap<>();
                counter = 0;
                num1 = 0;
                num2 = 0;
                for (int i = 0; i < cards.length; i++)
                {
                    if (!map1.containsKey(this.cards[i].value))
                    {
                        counter = 1;
                        map1.put(this.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map1.get(this.cards[i].value);
                        counter++;
                        map1.put(this.cards[i].value, counter);
                    }
                    if (!map2.containsKey(H.cards[i].value))
                    {
                        counter = 1;
                        map2.put(H.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map2.get(H.cards[i].value);
                        counter++;
                        map2.put(H.cards[i].value, counter);
                    }
                }
                for (int i = 0; i < cards.length; i++)
                {
                    if (map1.get(this.cards[i].value) == 3)
                    {
                        num1 = this.cards[i].value.val;
                    }
                    if (map2.get(H.cards[i].value) == 3)
                    {
                        num2 = H.cards[i].value.val;
                    }
                }
                if (num1 > num2)
                {
                    return true;
                }
                if (num1 < num2)
                {
                    return false;
                }
                num1 = 0;
                num2 = 0;
                for(int i = 0; i < cards.length; i++)
                {
                    num1 += this.cards[i].value.val;
                    num2 += H.cards[i].value.val;
                }
                if (num1 > num2)
                {
                    return true;
                }
                return false;
            case TWO:
                int deck1 = 0;
                int deck2 = 0;
                map1 = new HashMap<>();
                map2 = new HashMap<>();
                for (int i = 0; i < cards.length; i++)
                {
                    if (!map1.containsKey(this.cards[i].value))
                    {
                        counter = 1;
                        map1.put(this.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map1.get(this.cards[i].value);
                        counter++;
                        map1.put(this.cards[i].value, counter);
                    }
                    if (!map2.containsKey(H.cards[i].value))
                    {
                        counter = 1;
                        map2.put(H.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map2.get(H.cards[i].value);
                        counter++;
                        map2.put(H.cards[i].value, counter);
                    }
                }

                for (int i = 0; i < cards.length; i++)
                {
                    if (map1.get(this.cards[i].value) == 2)
                    {
                        if (this.cards[i].value.val > deck1)
                        {
                            deck1 = cards[i].value.val;
                        }
                    }
                    if (map2.get(H.cards[i].value) == 2)
                    {
                        if (H.cards[i].value.val > deck2)
                        {
                            deck2 = cards[i].value.val;
                        }
                    }
                }
                if (deck1 > deck2)
                {
                    return true;
                }
                if (deck1 < deck2)
                {
                    return false;
                }
                else
                {
                    deck1 = 0;
                    deck2 = 0;
                    for (int i = 0; i < cards.length; i++)
                    {
                        deck1 += this.cards[i].value.val;
                        deck2 += H.cards[i].value.val;
                    }
                    return deck1 > deck2;
                }
            case PAIR:
                deck1 = 0;
                deck2 = 0;
                map1 = new HashMap<>();
                map2 = new HashMap<>();
                counter = 0;
                for (int i = 0; i < cards.length; i++)
                {
                    if (!map1.containsKey(this.cards[i].value))
                    {
                        counter = 1;
                        map1.put(this.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map1.get(this.cards[i].value);
                        counter++;
                        map1.put(this.cards[i].value, counter);
                    }
                    if (!map2.containsKey(H.cards[i].value))
                    {
                        counter = 1;
                        map2.put(H.cards[i].value, counter);                
                    }
                    else
                    {
                        counter = map2.get(H.cards[i].value);
                        counter++;
                        map2.put(H.cards[i].value, counter);
                    }
                }
                for (int i = 0; i < cards.length; i++)
                {
                    if (map1.get(this.cards[i].value) == 2)
                    {
                        deck1 = this.cards[i].value.val;
                    }
                    if (map2.get(H.cards[i].value) == 2)
                    {
                        deck2 = H.cards[i].value.val;
                    }
                    if (deck1 != deck2)
                    {
                        return deck1 > deck2;
                    }
                    else
                    {
                        deck1 = 0;
                        deck2 = 0;
                        for (i = 0; i < cards.length; i++)
                        {
                            deck1 += this.cards[i].value.val;
                            deck2 += H.cards[i].value.val;
                        }
                        return deck1 > deck2;
                    }
                }
            case HIGH:
                deck1 = 0;
                deck2 = 0;
                for (int i = 0; i < cards.length; i++)
                {
                    deck1 += this.cards[i].value.val;
                    deck2 += H.cards[i].value.val;
                }
                return deck1 > deck2;
       }
    return false;
   }

   private boolean straightFlush(Card[] cards)  // see if the deck is the same suite, then sort the deck, see if deck is consecutive. if all are satisfied, return true
   {
        for (int i = 1; i < cards.length; i++)
        {
            if (cards[i-1].suite != cards[i].suite)
            {
                return false;
            }
        }
        cards = sortHand(cards);
        int con = cards[(cards.length)-1].value.val - cards[0].value.val + 1;
        if (con == cards.length)
        {
            //System.out.println("This deck is a straight flush");
            return true;
        }
        return false;
   }

   private boolean four_of_a_kind(Card[] cards) // translating hand into hashmap to count the amount of each suites there are in the hand, if there are 4 of one suite, then return true, else false.
   {
       HashMap<Card.Value, Integer> hand = new HashMap<>();
       int counter = 0;
       for (int i = 0; i < cards.length; i++)
       {
            if (!hand.containsKey(cards[i].value))
            {
                counter = 1;
                hand.put(cards[i].value, counter);                
            }
            else
            {
                counter = hand.get(cards[i].value);
                counter++;
                hand.put(cards[i].value, counter);
            }
            if (hand.get(cards[i].value) == 4)
            {
                return true;
            }
       }
       return false;
   }

   private boolean three_of_a_kind(Card[] cards)
   {
        HashMap<Card.Value, Integer> hand = new HashMap<>();
        int counter = 0;
        for (int i = 0; i < cards.length; i++)
        {
            if (!hand.containsKey(cards[i].value))
            {
                counter = 1;
                hand.put(cards[i].value, counter);                
            }
            else
            {
                counter = hand.get(cards[i].value);
                counter++;
                hand.put(cards[i].value, counter);
            }
        }
        int t = 0;
        for (int i = 0; i < cards.length; i++)
        {
            if (hand.get(cards[i].value) == 3)
            {
                t = 1;
            }
        }
        return t==1;
   }

   private boolean two_pair (Card[] cards)
   {
        HashMap<Card.Value, Integer> hand = new HashMap<>();
        int counter = 0;
        for (int i = 0; i < cards.length; i++)
        {
            if (!hand.containsKey(cards[i].value))
            {
                counter = 1;
                hand.put(cards[i].value, counter);                
            }
            else
            {
                counter = hand.get(cards[i].value);
                counter++;
                hand.put(cards[i].value, counter);
            }
        }
        int w = 0;
        for (int i = 0; i < cards.length; i++)
        {
            if (hand.get(cards[i].value) == 2)
            {
                w++;
            }
        }
        return w==4;
   }

   private boolean pair (Card[] cards)
   {
    HashMap<Card.Value, Integer> hand = new HashMap<>();
    int counter = 0;
    for (int i = 0; i < cards.length; i++)
    {
        if (!hand.containsKey(cards[i].value))
        {
            counter = 1;
            hand.put(cards[i].value, counter);                
        }
        else
        {
            counter = hand.get(cards[i].value);
            counter++;
            hand.put(cards[i].value, counter);
        }
    }
    int w = 0;
    for (int i = 0; i < cards.length; i++)
    {
        if (hand.get(cards[i].value) == 2)
        {
            w++;
        }
    }
    return w==2;
   }

   private boolean full_house (Card[] cards)
   {
       HashMap<Card.Value, Integer> hand = new HashMap<>();
       int counter = 0;
       for (int i = 0; i < cards.length; i++)
       {
            if (!hand.containsKey(cards[i].value))
            {
                counter = 1;
                hand.put(cards[i].value, counter);                
            }
            else
            {
                counter = hand.get(cards[i].value);
                counter++;
                hand.put(cards[i].value, counter);
            }
       }
       int t = 0;
       int w = 0;
       for (int i = 0; i < cards.length; i++)
       {
           if (hand.get(cards[i].value) == 3)
           {
                t = 1;
           }
           if (hand.get(cards[i].value) == 2)
           {
               w = 1;
           }
       }
       if (t == 1 && w == 1)
       {
           return true;
       }
       return false;
   }

   private boolean Flush(Card[] cards)
   {
    for (int i = 1; i < cards.length; i++)
    {
        if (cards[i-1].suite != cards[i].suite) // See if all cards within the array are of the same suite, if not then return false, else return true
        {
           return false;
        }
    }
     return true;
   }

   //sees if deck is the same suite, then sees if all royal cards apply, if all are satisfied, return true
   private boolean royalFlush(Card[] cards)
   {
       int a = 0, k = 0, q = 0, j = 0, t = 0;
       for (int i = 1; i < cards.length; i++)
       {
            if (cards[i-1].suite != cards[i].suite)
            {
                return false;
            }
        }
        for (int i = 0; i < cards.length; i++)
        {
            switch (cards[i].value)
            {
                case ACE:
                
                    a++;
                    break;
                
                case KING:
                    k++;
                    break;
                
                case QUEEN:
                    q++;
                    break;
                
                case JACK:
                    j++;
                    break;

                case TEN:
                    t++;
                    break;
                
                default:
                    break;
            }
        }
        if (a == k && k == q && q == j && j == t && a == 1)
        {
            System.out.println("This deck is a royal flush.");
            return true;
        }   
        return false;
   }

   private boolean straight (Card[] cards)
   {
       int min = cards[0].value.val;
       int max = cards[0].value.val;
       for (int i = 0; i < cards.length; i++)
       {
           if (cards[i].value.val < min)
           {
               min = cards[i].value.val;
           }
           if (cards[i].value.val > max)
           {
               max = cards[i].value.val;
           }
       }
       int n = max-min+1;
       for (int i = 1; i < cards.length; i++)
       {
           if (cards[i-1].value.val==cards[i].value.val)
           {
               return false;
           }
       }
       if (n == cards.length)
       {
           return true;
       }
       return false;
   }
}
