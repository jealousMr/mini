package filess;


import javax.swing.JComponent;
import javax.swing.JLabel;

public interface ComponentFactory {
    JLabel createLabel(String textWithMnemonic);

    JLabel createTitle(String textWithMnemonic);

    JComponent createSeparator(String textWithMnemonic, int alignment);

}
