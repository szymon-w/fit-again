package MAS.fitAgain.gui.controllers;

import MAS.fitAgain.gui.views.BookingView;
import MAS.fitAgain.model.*;
import MAS.fitAgain.repo.*;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.HighlightInformation;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class BookingViewController {

    private BookingView view;
    @Autowired
    private MasseurRepo masseurRepo;
    @Autowired
    private DutyRepo dutyRepo;
    @Autowired
    private SlotRepo slotRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private AppointmentRepo appointmentRepo;
    MainViewController mainViewController;
    private Masseur currentSelectedMasseur;
    private LocalDate currentSelectedDate;
    private Slot currentSelectedSlot;


    public BookingViewController() {
        this.view = new BookingView();
        initModels();
        initListeners();
        view.getBookingButton().setEnabled(false);
    }

    private void initListeners() {
        view.getChooseMasseurCB().addItemListener(e -> {
            SwingUtilities.invokeLater(() -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    clearViewAfterMasseurSelection();
                    Masseur selectedMasseur = (Masseur) view.getChooseMasseurCB().getSelectedItem();
                    currentSelectedMasseur = selectedMasseur;
                    updateViewAfterMasseurSelection(currentSelectedMasseur);
                    Masseur masseurWithDuties = masseurRepo.findById(currentSelectedMasseur.getPersonId()).get();
                    updateCalendar(masseurWithDuties.getAvailableDates());
                    updateSlotLabel(masseurWithDuties.getAvailableDates());
                }
            });
        });
        view.getCalendar().addCalendarListener(
                new CalendarListener() {
                    @Override
                    public void selectedDateChanged(CalendarSelectionEvent calendarSelectionEvent) {
                        currentSelectedSlot = null;
                        LocalDate selectedDate = view.getCalendar().getSelectedDate();
                        if (selectedDate != null) {
                            currentSelectedDate = selectedDate;
                        } else {
                            return;
                        }
                        Masseur masseurWithDutiesAndSlots = masseurRepo.findByIdAndDutyDate((currentSelectedMasseur).getPersonId(), currentSelectedDate).get();
                        updateSlotlist(masseurWithDutiesAndSlots.getFreeSlots(currentSelectedDate));
                        view.getBookingButton().setEnabled(false);
                    }

                    @Override
                    public void yearMonthChanged(YearMonthChangeEvent yearMonthChangeEvent) {
                        return;
                    }
                }
        );
        view.getSlotsList().addListSelectionListener(e -> {
            SwingUtilities.invokeLater(() ->
            {
                if (!e.getValueIsAdjusting()) {
                    Slot selectedSlot = (Slot) view.getSlotsList().getSelectedValue();
                    if (selectedSlot != null) {
                        currentSelectedSlot = selectedSlot;
                    } else {
                        return;
                    }
                    view.getBookingButton().setEnabled(true);
                }
            });
        });
        view.getBookingButton().addActionListener(e -> {
            SwingUtilities.invokeLater(() ->
            {
                if (currentSelectedDate != null && currentSelectedDate != null && currentSelectedMasseur != null) {
                    bookingDialog();
                }
            });
        });
    }

    private void bookingDialog() {
        int result = popupConfirm();
        if (result == 1) {
            //pobranie przykładowego klienta - wybór klienta nie wchodzi w skład use-case
            Customer customer = customerRepo.findByEmailAdress("kojot@gmail.com").get();
            currentSelectedSlot.setOccupied();
            Appointment appointment = Appointment.makeAppointment(customer, currentSelectedSlot);
            dutyRepo.save(currentSelectedSlot.getDuty());
            appointmentRepo.save(appointment);
            popupOk();
            SwingUtilities.invokeLater(() -> {
                this.showGui(mainViewController);
            });
        }
    }

    private int popupConfirm() {
        String[] options = {"Anuluj", "Potwierdź"};
        int result = JOptionPane.showOptionDialog(view.getMainPanel(),
                "Wybrana wizyta:\n\n" +
                        "Masażysta: " + currentSelectedMasseur + "\n" +
                        "Data: " + currentSelectedDate.toString() + "\n" +
                        "Termin: " + currentSelectedSlot.getStartTime().toString(),
                "Potrwierdź wizytę",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[1]);
        return result;
    }

    private void popupOk() {
        JOptionPane.showMessageDialog(view.getMainPanel(),
                "Wizyta została zarejestrowana",
                "Potrwierdzenie rezerwacji", JOptionPane.INFORMATION_MESSAGE);
    }


    private void clearViewAfterMasseurSelection() {
        currentSelectedDate = null;
        currentSelectedSlot = null;
        clearSlotlist();
        view.getBookingButton().setEnabled(false);
        view.getCalendar().setSelectedDate(null);
        view.getSlotsList().setVisible(false);
    }

    private void updateCalendar(Set<LocalDate> availableDates) {
        DatePickerSettings dpSettings = new DatePickerSettings();
        dpSettings.setHighlightPolicy(e -> {
            if (availableDates.contains(e)) {
                return new HighlightInformation(Color.green, null, "Dostępne terminy");
            }
            return null;
        });
        view.getCalendar().setSettings(dpSettings);
        dpSettings.setVetoPolicy(e -> {
            if (availableDates.contains(e)) {
                return true;
            }
            return false;
        });
        dpSettings.setVisibleClearButton(false);
    }

    private void updateSlotLabel(Set<LocalDate> availableDates) {
        if (availableDates.isEmpty()) {
            view.getChooseSlotLabel().setText("Brak wolnych terminów!");
            view.getSlotsList().setVisible(false);
        } else {
            view.getChooseSlotLabel().setText("Wybierz godzinę");
            view.getSlotsList().setVisible(true);
        }
    }


    private void clearSlotlist() {
        DefaultListModel<Slot> slotModel = (DefaultListModel<Slot>) view.getSlotsList().getModel();
        slotModel.removeAllElements();
    }

    private void updateSlotlist(List<Slot> freeSlots) {
        DefaultListModel<Slot> slotModel = (DefaultListModel<Slot>) view.getSlotsList().getModel();
        slotModel.removeAllElements();
        freeSlots.sort((s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()));
        for (Slot slot : freeSlots) {
            slotModel.addElement(slot);
        }
    }

    private void updateViewAfterMasseurSelection(Masseur masseur) {
        view.getMasseurLabel().setText(masseur.toString());
        view.getMasseurBusinessInfoTP().setText(masseur.getBusinessInfo());
        Set<String> courses = masseur.getFinishedCourses();
        StringBuilder sb = new StringBuilder();
        for (String s : courses) {
            sb.append("- ").append(s).append("\n");
        }
        view.getMasseurCoursesTP().setText(sb.toString());
    }

    private void initModels() {
        view.getChooseMasseurCB().setModel(new DefaultComboBoxModel<Masseur>());
        view.getSlotsList().setModel(new DefaultListModel<Slot>());
    }


    private void updateMasseurCBFromDB() {
        List<Masseur> masseurs = masseurRepo.findAll();
        DefaultComboBoxModel<Masseur> cbMasseurModel = (DefaultComboBoxModel<Masseur>) view.getChooseMasseurCB().getModel();
        cbMasseurModel.removeAllElements();
        for (Masseur m : masseurs) {
            cbMasseurModel.addElement(m);
        }

    }

    public void showGui(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
        updateMasseurCBFromDB();
        mainViewController.showView(view.getMainPanel());
    }


}
