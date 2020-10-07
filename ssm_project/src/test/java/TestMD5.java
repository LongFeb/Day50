import cn.nyse.utils.EncryptUtils;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TestMD5 {
    @Test
    public void test() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        md5.update("123456".getBytes());
        byte[] digest = md5.digest();
        System.out.println(Arrays.toString(digest));

        System.out.println(EncryptUtils.toHexString(digest));
    }
}
