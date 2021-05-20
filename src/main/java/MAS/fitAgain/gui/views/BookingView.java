package MAS.fitAgain.gui.views;

import com.github.lgooddatepicker.components.CalendarPanel;
import org.springframework.stereotype.Component;
import sun.swing.plaf.synth.SynthFileChooserUIImpl;

import javax.swing.*;

@Component
public class BookingView {
    private JPanel MainPanel;
    private JPanel MasseurPanel;
    private JPanel SlotPanel;
    private JLabel MainLabel;
    private JComboBox ChooseMasseurCB;
    private JList DatesList;
    private JList SlotsList;
    private JButton BookingButton;
    private JTextArea MasseurInfoTA;
    private JTextArea MasseurCoursesTA;
    private JLabel ChooseMasseurLabel;
    private JLabel MasseurLabel;
    private JLabel MassuerCourses;
    private JLabel ChooseSlotLabel;
    private JTextPane MasseurBusinessInfoTP;
    private JTextPane MasseurCoursesTP;
    private CalendarPanel calendar;

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public JComboBox getChooseMasseurCB() {
        return ChooseMasseurCB;
    }

    public JList getDatesList() {
        return DatesList;
    }

    public JList getSlotsList() {
        return SlotsList;
    }

    public JLabel getMasseurLabel() {
        return MasseurLabel;
    }

    public JTextPane getMasseurBusinessInfoTP() {
        return MasseurBusinessInfoTP;
    }

    public JButton getBookingButton() {
        return BookingButton;
    }

    public JTextPane getMasseurCoursesTP() {
        return MasseurCoursesTP;
    }

    public CalendarPanel getCalendar() {
        return calendar;
    }

    public JLabel getChooseSlotLabel() {
        return ChooseSlotLabel;
    }
}
