package iter;

import collection.MyCollection;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyIterator<String> iter = new MyCollection<String>(Arrays.asList("a", "bb", "ccc", "bbbb")).iterator();

        while (iter.hasNext()) {
            String s = iter.next();
            int len = s.length();
            if(len%2==0) continue;
            System.out.println(s);
        }
//        Iterator<String> iter = list.iterator();
//        while(iter.hasNext()){
//            System.out.println(iter.next());
//        }
    }
}
