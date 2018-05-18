package filess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileServer extends Thread {

    private FileFrame ff = null;
    private ServerSocket server = null;

    public FileServer(FileFrame ff) throws Exception {
        this.ff = ff;

        // 随机安排端口号
        ff.port = (5000 + ((int) (Math.random() * 1000))) + "";
//         ff.port = (5678)+ "";
        server = new ServerSocket(Integer.parseInt(ff.port));

    }

    public void run() {
        while (true) {

            try {
                Socket socket = server.accept();// 等待客户端给你发送文件
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                byte[] b = new byte[1024];
                int len = in.read(b);
                String clientMsg = new String(b, 0, len);// 字节转换成文字
                String fileName = clientMsg.split("@")[0];
                long fileSize = Long.parseLong(clientMsg.split("@")[1]);
                // 提醒客户端有数据发送过来

                JOptionPane.showConfirmDialog(ff,
                        socket.getInetAddress().getHostAddress() + " TO YOU" + fileName + " SIZE:" + fileSize + " ARE YOU REVCEI？","SURE",JOptionPane.YES_NO_OPTION);

                out.write(1);
                out.flush();
                // 询问
                JFileChooser fileSave = new JFileChooser();
                fileSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileSave.setSelectedFile(new File(fileName));
                fileSave.showSaveDialog(ff);
                File saveFilePath = fileSave.getSelectedFile();

                ff.progressBar.setMaximum((int) (fileSize / 10000));

                long size = 0;
                // 把文件输出到本地
                FileOutputStream fileOut = new FileOutputStream(saveFilePath);
                while ((len = in.read(b)) != -1) {
                    fileOut.write(b, 0, len);
                    size += len;
                    ff.progressBar.setValue((int) (size / 10000));
                }
                socket.close();
                fileOut.close();
                javax.swing.JOptionPane.showMessageDialog(ff, "SEND DONE!");
            } catch (Exception e) {
            }
        }

    }

}
