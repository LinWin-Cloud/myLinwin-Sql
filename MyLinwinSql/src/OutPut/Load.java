package OutPut;

import Control.Control;

import java.io.File;

public class Load {
    public void load()
    {
        try
        {
            System.out.println("[INFO] LOAD CONFIG FILE!");
            File file = new File("../../config/Service.json");
            String serverPort = Json.readJson(file.getAbsolutePath(),"ServerPort");
            assert serverPort != null;
            Control.ServerPort = Integer.parseInt(serverPort);
        }
        catch (Exception exception){
            System.out.println("[ERROR] CAN NOT FIND CONFIG FILE OR CONFIG C0NTENT IS ERROR.");
            System.exit(0);
        }
    }
}
