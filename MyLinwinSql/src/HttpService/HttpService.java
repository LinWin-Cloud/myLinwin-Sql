package HttpService;

import Control.Control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.Callable;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpService {
    private static int boot = 0;
    public static ServerSocket getServerSocket(int port)
    {
        try
        {
            System.out.println("Try to start Http Port on Host: "+port);
            boot = boot + 1;
            return new ServerSocket(port);
        }catch (Exception exception) {
            if (boot >= 10)
            {
                System.exit(0);
            }
            System.out.println("[ERR] Bind: "+port+" |"+boot+"|");
            return HttpService.getServerSocket(port);
        }
    }
    public void start(int port) {
        Thread httpService_Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    ServerSocket serverSocket = new ServerSocket(Control.ServerPort);

                    while (true)
                    {
                        Socket socket = serverSocket.accept();
                        Control.executorService.submit(new Callable<Integer>(){
                            @Override
                            public Integer call() throws Exception {
                                HttpSocket(socket);
                                return 0;
                            }
                        });
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                }
            }
        });
        httpService_Thread.start();
    }
    private void HttpSocket(Socket socket)
    {
        try
        {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            PrintWriter printWriter = new PrintWriter(outputStream);
            String SEND_MESSAGE = new BufferedReader(new InputStreamReader(inputStream)).readLine();

            if (SEND_MESSAGE == null) {
                socket.close();
            }
            SEND_MESSAGE = SEND_MESSAGE.substring(SEND_MESSAGE.indexOf(" ")+1,SEND_MESSAGE.lastIndexOf("HTTP/")-1);
            SEND_MESSAGE = URLDecoder.decode(SEND_MESSAGE, "UTF-8");

            String user = this.getParamByUrl(SEND_MESSAGE,"Login");
            String passwd = this.getParamByUrl(SEND_MESSAGE,"Passwd");
            String command = this.getParamByUrl(SEND_MESSAGE,"Command");

            if (user == null || passwd == null || command == null)
            {
                this.setTitle(printWriter,400);
                printWriter.println("Send Message Error");
                printWriter.flush();
                socket.close();
            }
            this.setTitle(printWriter,200);
            printWriter.println(user+" "+passwd+" "+command);
            printWriter.flush();
            socket.close();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public String getParamByUrl(String url, String name) {
        url += "&";
        String pattern = "(\\?|&){1}#{0,1}" + name + "=[a-zA-Z0-9]*(&{1})";
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(url);
        if (matcher.find()) {
            return matcher.group(0).split("=")[1].replace("&", "");
        } else {
            return null;
        }
    }
    private void setTitle(PrintWriter printWriter,int code) {
        printWriter.println("HTTP/1.1 "+code+" OK");
        printWriter.println("Content-Type: application/json");
        printWriter.println("Server: MyLinwin Sql");
        printWriter.println();
        printWriter.flush();
    }
}
