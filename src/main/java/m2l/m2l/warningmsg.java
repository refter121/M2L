package m2l.m2l;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class warningmsg {
    //attrib
    private String txt;
    private boolean value = false;

    public boolean isValue() {
        return value;
    }

    //builder
    public warningmsg(String cible) {
        txt = cible;
        value = false;
        ImageIcon icon = new ImageIcon("");
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(250, 100));
        panel.setLayout(null);
        JLabel label1 = new JLabel("Etes vous sur de vouloir suprimer");
        label1.setVerticalAlignment(SwingConstants.BOTTOM);
        label1.setBounds(20, 20, 200, 30);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label1);
        JLabel label2 = new JLabel("Vous etes sur le point de suprimer "+cible+" êtes vous sûr de vouloir continuer?");
        label2.setVerticalAlignment(SwingConstants.TOP);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        label2.setBounds(20, 80, 200, 20);
        panel.add(label2);
        UIManager.put("OptionPane.minimumSize", new Dimension(400, 200));
        int res = JOptionPane.showConfirmDialog(null, panel, "File",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE, icon);
        if(res == 0) {
            System.out.println("Pressed YES");
            value =true;
        } else if (res == 1) {
            System.out.println("Pressed NO");
            value =false;
        }
    }



    public static void main(String[] args) {
        warningmsg wm = new warningmsg("aaa");
        if(wm.isValue()) {
            System.out.println("wah");
        }
    }

}
