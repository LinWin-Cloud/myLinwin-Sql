import Control.Control;
import HttpService.HttpService;
import OutPut.Load;

public class Main {
    public static void main(String[] args)
    {
        /**
         * Load all the config file.
         * If the config file is not exists , then will exit.
         */
        Load load = new Load();
        load.load();

        HttpService httpService = new HttpService();
        httpService.start(Control.ServerPort);
    }
}
