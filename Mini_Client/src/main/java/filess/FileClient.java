package filess;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class FileClient extends Thread {
    private FileFrame ff = null;

    private File file;
    private String ipAndPort;

    public FileClient(FileFrame ff, File file, String ipAndPort) throws Exception {
        this.ff = ff;
        this.file = file;
        this.ipAndPort = ipAndPort;
    }

    public void run() {

        String msg = file.getName() + "@" + file.length()+"@";

        String ip = ipAndPort.split(":")[0];
        String port = ipAndPort.split(":")[1];

        try {
            // 开始连接服务器
            Socket socket = new Socket(ip, Integer.parseInt(port));
            // 操作数据
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            out.write(msg.getBytes());
            out.flush();
            in.read();
            // 读本地文件
            FileInputStream fileIn = new FileInputStream(file);
            byte[] b = new byte[1024];

            ff.progressBar.setMaximum((int) (file.length() / 10000));
            long size = 0;
            // 开传递文件内容
            while (fileIn.available() != 0) {
                int len = fileIn.read(b);
                out.write(b, 0, len);
                out.flush();
                size += len;
                ff.progressBar.setValue((int) (size / 10000));
            }
            socket.close();
            fileIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
