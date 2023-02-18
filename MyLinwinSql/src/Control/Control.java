package Control;

import USERS.SqlUser;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Control {
    public static int ServerPort = 0;
    public static ExecutorService executorService = Executors.newFixedThreadPool(10000);
    public static HashMap<String, SqlUser> SqlUser = new HashMap<>();
}
