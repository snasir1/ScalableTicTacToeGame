//Very Simple Class to return a pair of values. Could have simply just returned as an Array as well, but decided to do it this way as it is cleaner.
public class Pair<T, U> {         
    public final T pair_value_one;
    public final U pair_value_two;

    public Pair(T pair_value_one, U pair_value_two) {         
        this.pair_value_one= pair_value_one;
        this.pair_value_two= pair_value_two;
     }
 }