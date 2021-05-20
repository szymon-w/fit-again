package MAS.fitAgain.gui.views;

import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainView extends JFrame {

    private JMenuItem menuItemBooking;

    public MainView(){
        setTitle("FitAgain");
        setSize(700,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar bar = new JMenuBar();
        JMenu menuBooking = new JMenu("Rezerwacje");
        bar.add(menuBooking);
        menuItemBooking = new JMenuItem("Rezerwacja wizyty u masażysty");
        menuBooking.add(menuItemBooking);
        this.setJMenuBar(bar);
    }

    public JMenuItem getMenuItemBooking() {
        return menuItemBooking;
    }
}
