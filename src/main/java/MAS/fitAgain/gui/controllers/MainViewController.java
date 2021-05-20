package MAS.fitAgain.gui.controllers;

import MAS.fitAgain.gui.views.MainView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class MainViewController {
    private MainView view;
    @Autowired
    private BookingViewController bookingViewController;

     public MainViewController(){
        view = new MainView();
        initMenuListeners();
     }

    private void initMenuListeners() {
         view.getMenuItemBooking().addActionListener(e->{
             SwingUtilities.invokeLater(()-> {
                 bookingViewController.showGui(this);
             });
         });
    }

    public void showGUI(){
         view.setVisible(true);
     }

     public void showView(JPanel newView){
         view.getContentPane().removeAll();
         view.getContentPane().add(newView);
         view.revalidate();
     }

}

