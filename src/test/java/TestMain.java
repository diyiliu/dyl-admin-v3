import org.junit.Test;

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
}
