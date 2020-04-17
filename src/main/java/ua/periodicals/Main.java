package ua.periodicals;

import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        String password = "hello world";
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashed);
    }
}
