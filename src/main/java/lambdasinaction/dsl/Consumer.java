package lambdasinaction.dsl;

import java.util.Arrays;

/**
 * @author chenhaipeng
 * @version 1.0
 * @date 2018/08/02 下午5:55
 */
public class Consumer {
    public static void main(String[] args) {
        Arrays.asList("Justin", "Monica", "Irene").forEach(System.out::println);

    }


}

class User{
    String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
