package filess;



import java.awt.*;

import javax.swing.*;

public class DefaultComponentFactory implements ComponentFactory {

    private static final DefaultComponentFactory INSTANCE = new DefaultComponentFactory();

    private static final char MNEMONIC_MARKER = '&';

    public static DefaultComponentFactory getInstance() {
        return INSTANCE;
    }

    public JLabel createLabel(String textWithMnemonic) {
        JLabel label = new JLabel();
        setTextAndMnemonic(label, textWithMnemonic);
        return label;
    }

    public JLabel createTitle(String textWithMnemonic) {
        JLabel label = new TitleLabel();
        setTextAndMnemonic(label, textWithMnemonic);
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }

    public JComponent createSeparator(String textWithMnemonic) {
        return createSeparator(textWithMnemonic, SwingConstants.LEFT);
    }

    public JComponent createSeparator(String textWithMnemonic, int alignment) {
        if (textWithMnemonic == null || textWithMnemonic.length() == 0) {
            return new JSeparator();
        }
        JLabel title = createTitle(textWithMnemonic);
        title.setHorizontalAlignment(alignment);
        return createSeparator(title);
    }

    public JComponent createSeparator(JLabel label) {
        if (label == null)
            throw new NullPointerException("The label must not be null.");
        int horizontalAlignment = label.getHorizontalAlignment();
        if ((horizontalAlignment != SwingConstants.LEFT) && (horizontalAlignment != SwingConstants.CENTER)
                && (horizontalAlignment != SwingConstants.RIGHT))
            throw new IllegalArgumentException(
                    "The label's horizontal alignment" + " must be one of: LEFT, CENTER, RIGHT.");

        JPanel panel = new JPanel();// (new TitledSeparatorLayout(!FormUtils.isLafAqua()));
        panel.setOpaque(false);
        panel.add(label);
        panel.add(new JSeparator());
        if (horizontalAlignment == SwingConstants.CENTER) {
            panel.add(new JSeparator());
        }
        return panel;
    }

    public static void setTextAndMnemonic(JLabel label, String textWithMnemonic) {
        int markerIndex = textWithMnemonic.indexOf(MNEMONIC_MARKER);
        // No marker at all
        if (markerIndex == -1) {
            label.setText(textWithMnemonic);
            return;
        }
        int mnemonicIndex = -1;
        int begin = 0;
        int end;
        int length = textWithMnemonic.length();
        int quotedMarkers = 0;
        StringBuffer buffer = new StringBuffer();
        do {
            if ((markerIndex + 1 < length) && (textWithMnemonic.charAt(markerIndex + 1) == MNEMONIC_MARKER)) {
                end = markerIndex + 1;
                quotedMarkers++;
            } else {
                end = markerIndex;
                if (mnemonicIndex == -1) {
                    mnemonicIndex = markerIndex - quotedMarkers;
                }
            }
            buffer.append(textWithMnemonic.substring(begin, end));
            begin = end + 1;
            markerIndex = begin < length ? textWithMnemonic.indexOf(MNEMONIC_MARKER, begin) : -1;
        } while (markerIndex != -1);
        buffer.append(textWithMnemonic.substring(begin));

        String text = buffer.toString();
        label.setText(text);
        if ((mnemonicIndex != -1) && (mnemonicIndex < text.length())) {
            label.setDisplayedMnemonic(text.charAt(mnemonicIndex));
            label.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    private static final class TitleLabel extends JLabel {
        private TitleLabel() {
        }

        public void updateUI() {
            super.updateUI();
            Color foreground = getTitleColor();
            if (foreground != null) {
                setForeground(foreground);
            }
            setFont(getTitleFont());
        }

        private Color getTitleColor() {
            return UIManager.getColor("TitledBorder.titleColor");
        }

        private Font getTitleFont() {
            return
                    // FormUtils.isLafAqua()
                    // ? UIManager.getFont("Label.font").deriveFont(Font.BOLD)
                    // :
                    UIManager.getFont("TitledBorder.font");
        }

    }

    private static final class TitledSeparatorLayout implements LayoutManager {

        private final boolean centerSeparators;

        private TitledSeparatorLayout(boolean centerSeparators) {
            this.centerSeparators = centerSeparators;
        }

        public void addLayoutComponent(String name, Component comp) {
        }

        public void removeLayoutComponent(Component comp) {
        }

        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        public Dimension preferredLayoutSize(Container parent) {
            Component label = getLabel(parent);
            Dimension labelSize = label.getPreferredSize();
            Insets insets = parent.getInsets();
            int width = labelSize.width + insets.left + insets.right;
            int height = labelSize.height + insets.top + insets.bottom;
            return new Dimension(width, height);
        }

        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {

                Dimension size = parent.getSize();
                Insets insets = parent.getInsets();
                int width = size.width - insets.left - insets.right;

                JLabel label = getLabel(parent);
                Dimension labelSize = label.getPreferredSize();
                int labelWidth = labelSize.width;
                int labelHeight = labelSize.height;
                Component separator1 = parent.getComponent(1);
                int separatorHeight = separator1.getPreferredSize().height;

                FontMetrics metrics = label.getFontMetrics(label.getFont());
                int ascent = metrics.getMaxAscent();
                int hGapDlu = centerSeparators ? 3 : 1;
                int hGap = 3;// Sizes.dialogUnitXAsPixel(hGapDlu, label);
                int vOffset = centerSeparators ? 1 + (labelHeight - separatorHeight) / 2 : ascent - separatorHeight / 2;

                int alignment = label.getHorizontalAlignment();
                int y = insets.top;
                if (alignment == JLabel.LEFT) {
                    int x = insets.left;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x += labelWidth;
                    x += hGap;
                    int separatorWidth = size.width - insets.right - x;
                    separator1.setBounds(x, y + vOffset, separatorWidth, separatorHeight);
                } else if (alignment == JLabel.RIGHT) {
                    int x = insets.left + width - labelWidth;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x -= hGap;
                    x--;
                    int separatorWidth = x - insets.left;
                    separator1.setBounds(insets.left, y + vOffset, separatorWidth, separatorHeight);
                } else {
                    int xOffset = (width - labelWidth - 2 * hGap) / 2;
                    int x = insets.left;
                    separator1.setBounds(x, y + vOffset, xOffset - 1, separatorHeight);
                    x += xOffset;
                    x += hGap;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x += labelWidth;
                    x += hGap;
                    Component separator2 = parent.getComponent(2);
                    int separatorWidth = size.width - insets.right - x;
                    separator2.setBounds(x, y + vOffset, separatorWidth, separatorHeight);
                }
            }
        }

        private JLabel getLabel(Container parent) {
            return (JLabel) parent.getComponent(0);
        }

    }

}

