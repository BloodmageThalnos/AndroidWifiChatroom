package sse.bupt.androidwifichatroom;

import java.util.Random;

/**
 * Created by Administrator on 7/11/2019.
 */

public class MyNameService {
    static public MyNameService myNameService;

    public MyNameService(){
        Random random = new Random(System.currentTimeMillis());
        String[] names = {"Ana", "Bob", "Kai", "Alice", "Dva", "Jason", "Messi", "Lucio", "Jennie", "Dembele", "Doomfist", "Solder76", "Solder77"};
        MyInfo.setMyName(names[(((int)System.currentTimeMillis() % names.length)+names.length)%names.length]);
    }

}
