package filess;



import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.InetAddress;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class FileFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField textField;

    public String port = "";



    public FileFrame() {
        super();

        try {
            new FileServer(this).start();
            // 获得本机IP地址
            label.setText("IP:" + InetAddress.getLocalHost().getHostAddress() + "    MyNumber: " +this.port);
        } catch (Exception e1) {
            javax.swing.JOptionPane.showMessageDialog(this, "SORRY！");
//            System.exit(0);
              setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }
        setTitle("FileTrans");
        setResizable(false);
        getContentPane().setLayout(null);
        setBounds(100, 100, 350, 260);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBorder(new TitledBorder(null, "FileTransport", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        panel.setBounds(10, 10, 310, 78);
        getContentPane().add(panel);

        final JLabel daaaaaaviLabel = new JLabel();
        daaaaaaviLabel.setText("SelectAddress");
        daaaaaaviLabel.setBounds(10, 22, 228, 18);
        panel.add(daaaaaaviLabel);

        final JButton button = new JButton();
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {

                // 文件选择窗口
                JFileChooser jf = new JFileChooser();
                jf.setFileSelectionMode(JFileChooser.FILES_ONLY);// 这个设置的是选择什么文件或文件夹

                jf.showOpenDialog(FileFrame.this);
                String selectFilePath = jf.getSelectedFile().getPath();// 把选择的文件路径得到

                daaaaaaviLabel.setText(selectFilePath);

            }
        });
        button.setText("...");
        button.setBounds(244, 17, 56, 28);
        panel.add(button);

        textField = new JTextField();
        textField.setText("169.254.173.18:");
        textField.setBounds(10, 50, 162, 22);
        panel.add(textField);

        final JButton button_1 = new JButton();
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                try {

                    new FileClient(FileFrame.this, new File(daaaaaaviLabel.getText()), textField.getText()).start();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            }
        });
        button_1.setText("SEND");
        button_1.setBounds(178, 49, 122, 25);
        panel.add(button_1);

        final JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Progress", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        panel_1.setLayout(null);
        panel_1.setBounds(10, 98, 310, 61);
        getContentPane().add(panel_1);

        progressBar.setBounds(10, 25, 290, 24);
        panel_1.add(progressBar);

        label.setBounds(0, 166, 332, 18);
        getContentPane().add(label);
    }

    public final JProgressBar progressBar = new JProgressBar();
    JLabel label = DefaultComponentFactory.getInstance().createLabel("");

}
