import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author qibao
 * @since 2020-04-06
 */
public class ExmaTest {


    public static void main(String[] args) throws Exception{

//        System.out.println(search("dbbbaaa"));
//        System.out.println(search("aabbgccc"));
//        System.out.println(search("aaaaaac"));
//        System.out.println(search("a"));
//        System.out.println(search("aaabb"));
//        System.out.println(search("fffaaacccfffiue"));
//        System.out.println(search("this is test"));


        Thread t1 = new Thread(() -> {
            while (true) {

            }
        });

        t1.start();

        TimeUnit.SECONDS.sleep(1);




    }

    public static Character search(String s) {
        IntStream chars = s.chars();
        Map<Integer, Integer> map = new LinkedHashMap<>();
        chars.forEach((value) -> map.merge(value, 1, (o, n) -> o + n));
        Optional<Map.Entry<Integer, Integer>> r = map.entrySet().
                stream()
                .filter((entry) -> entry.getValue().compareTo(1) == 0)
                .findFirst();
        if (r.isPresent()) {
            return (char) r.get().getKey().intValue();
        }
        return null;
    }
}

