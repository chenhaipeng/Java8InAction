package lambdasinaction.chap10;

import org.junit.Test;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class OperationsWithOptional {

    public static void main(String... args) {
        System.out.println(max(of(3), of(5)));
        System.out.println(max(empty(), of(5)));

        Optional<Integer> opt1 = of(5);
        Optional<Integer> opt2 = opt1.or(() -> of(4));

        System.out.println(
        of(5).or(() -> of(4))
                          );
    }

    public static final Optional<Integer> max(Optional<Integer> i, Optional<Integer> j) {
         return i.flatMap(a -> j.map(b -> Math.max(a, b)));
    }

    /**
     * 这个类型的对象可能包含值，也可能为空。你可以使用同名方法创建一个空的 Optional。
     */
    @Test(expected = NoSuchElementException.class)
    public void whenCreateEmptyOptional_thenNull() {
        Optional<User> emptyOpt = Optional.empty();
        emptyOpt.get();
    }

    @Test(expected = NullPointerException.class)
    public void whenCreateOfEmptyOptional_thenNullPointerException() {
        User user = null;
        Optional<User> opt = Optional.of(user);
    }

    @Test
    public void whenCreateOfNullableOptional_thenOk() {
        String name = "John";
        Optional<String> opt = Optional.ofNullable(name);

        assertEquals("John", opt.get());
    }

    @Test
    public void whenCheckIfPresent_thenOk() {
        User user = new User("john@gmail.com", "1234");
        Optional<User> opt = Optional.ofNullable(user);
        assertTrue(opt.isPresent());

        opt.ifPresent( u -> assertEquals(user.getEmail(), u.getEmail()));

        assertEquals(user.getEmail(), opt.get().getEmail());
    }


    @Test
    public void whenEmptyValue_thenReturnDefault() {
        User user = null;
        User user2 = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);

        assertEquals(user2.getEmail(), result.getEmail());
    }

    @Test
    public void whenValueNotNull_thenIgnoreDefault() {
        User user = new User("john@gmail.com","1234");
        User user2 = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user).orElse(user2);

        User result2 = Optional.ofNullable(user).orElseGet( () -> user2);

        assertEquals("john@gmail.com", result.getEmail());
        assertEquals("john@gmail.com", result2.getEmail());
    }


    @Test
    public void givenEmptyValue_whenCompare_thenOk() {
        User user = null;
        System.out.println( "Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    private User createNewUser() {
        System.out.println("Creating New User");
        return new User("extra@gmail.com", "1234");
    }

    /**
     * 这个大坑啊，用orElse ，是必定执行后面orElse的表达式的,
     *
     * 建议用orElseGet 或者 orElse不用表达式
     *
     */
    @Test
    public void givenPresentValue_whenCompare_thenOk() {
        User user = new User("john@gmail.com", "1234");
        System.out.println("Using orElse");
        User result = Optional.ofNullable(user).orElse(createNewUser());
        System.out.println("Using orElseGet");
        User result2 = Optional.ofNullable(user).orElseGet(() -> createNewUser());
    }

    /**
     * 返回异常
     */
    @Test(expected = IllegalArgumentException.class)
    public void whenThrowException_thenOk() {
        User user = null;
        User result = Optional.ofNullable(user)
            .orElseThrow( () -> new IllegalArgumentException());
    }

    /**
     * 转换值
     */

    @Test
    public void whenMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        String email = Optional.ofNullable(user)
            //orElse 后面不要带表达式，无论null 或者非null 都会执行的
            .map(u -> u.getEmail()).orElse(print());

        assertEquals(email, user.getEmail());
    }

    private String print(){
        System.out.println("test");
        return "xxx";

    }

    @Test
    public void whenFlatMap_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        user.setPosition("Developer");
        String position = Optional.ofNullable(user)
            .map(u -> u.getPosition()).orElse("default");

        assertEquals(position, user.getPosition());
    }

    /**
     * 过滤值
     */
    @Test
    public void whenFilter_thenOk() {
        User user = new User("anna@gmail.com", "1234");
        Optional<User> result = Optional.ofNullable(user)
            .filter(u -> u.getEmail() != null && u.getEmail().contains("@"));

        assertTrue(result.isPresent());
    }

    /**
     * Java 9 增强,如果对象包含值，则 Lambda 表达式不会执行：
     */

    @Test
    public void whenEmptyOptional_thenGetValueFromOr() {
        User user = new User("anna@gmail.com", "1234");
        User result = Optional.ofNullable(user)
            .or( () -> Optional.of(new User("default","1234"))).get();

        assertEquals(result.getEmail(), "default");


        Optional.ofNullable(user).ifPresentOrElse( u -> System.out.println("User is:" + u.getEmail()),
            () -> System.out.println("User not found"));
    }

    @Test
    public void whenGetStream_thenOk() {
        User user = new User("john@gmail.com", "1234");
        List<String> emails = Optional.ofNullable(user)
            .stream()
            .filter(u -> u.getEmail() != null && u.getEmail().contains("@"))
            .map( u -> u.getEmail())
            .collect(Collectors.toList());

        assertTrue(emails.size() == 1);
        assertEquals(emails.get(0), user.getEmail());
    }



}
