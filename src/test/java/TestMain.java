import com.diyiliu.support.util.JacksonUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: TestMain
 * Author: DIYILIU
 * Update: 2018-05-14 15:06
 */
public class TestMain {


    @Test
    public void test(){
        String str = "0/5/6";

        System.out.println(str.split("/")[1]);
    }


    @Test
    public void test1(){
        String str = "guide/image/unknown.png";

        System.out.println(str.substring(str.lastIndexOf("/")));
    }

    @Test
    public void test2(){
        String str = "guide.1";

        String[] arr = str.split("\\.");

        System.out.println(arr.length);
    }

    @Test
    public void test3() throws IOException {
        String str = "[{\"id\":29},{\"id\":28,\"children\":[{\"id\":28},{\"id\":28}]},{\"id\":30,\"children\":[{\"id\":30}]},{\"id\":4,\"children\":[{\"id\":4}]}]";

        List list = JacksonUtil.toList(str, Map.class);

        System.out.println(list.size());
    }
}
