package MAS.fitAgain;

import MAS.fitAgain.model.*;
import MAS.fitAgain.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
public class DataInitializer {

    @Autowired
    AppointmentRepo appointmentRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CustomerTrainingRepo customerTrainingRepo;
    @Autowired
    DutyRepo dutyRepo;
    @Autowired
    MasseurRepo masseurRepo;
    @Autowired
    RoomRepo roomRepo;
    @Autowired
    SlotRepo slotRepo;
    @Autowired
    TrainerRepo trainerRepo;
    @Autowired
    TrainingEquipmentRepo trainingEquipmentRepo;
    @Autowired
    TrainingRepo trainingRepo;
    @Autowired
    TrainingTypeRepo trainingTypeRepo;

    public void setData(){
        final DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate dt = LocalDate.parse("2020-06-28", dtfDate);
        LocalDate dt2 = LocalDate.parse("2020-06-29", dtfDate);
        LocalDate dt3 = LocalDate.parse("2020-06-30", dtfDate);
        LocalDate dt_bd = LocalDate.parse("1980-01-01", dtfDate);
        LocalTime start = LocalTime.parse("10:00", dtfTime);
        LocalTime mid = LocalTime.parse("11:00", dtfTime);
        LocalTime end = LocalTime.parse("12:00", dtfTime);
        BigDecimal bd = new BigDecimal(0);
        String masseur1Info = "Masuje od ponad dwudziestu lat.\nSpecjalizuję się w masażu brzucha oraz rehabilitacji i fizjoterapii opartej o rozbudowę mięśni core.\nPrzywatnie fan żużla i baseballu.";
        String masseur2Info = "W mojej pracy najważniejsze jest osobiste podejście do klienta.\nMasuje od ponad dziesięciu lat i nieustannie czerpie z tego przyjemność - jest to moja pasja.\nW wolnych chwilach gram w golfa i prowadzę kółko brydżowe.";
        String masseur3Info = "Profesjonalista w każdym calu.\nZapraszam zwłaszcza osoby starsze.\nUlżę w bólu i przywrócę młodzieńczą sprawność.";


        Appointment appointment1 = new Appointment(dt, start, Appointment.AppointmentStatus.booked);
        Customer customer1 = new Customer("Adam", "Kojot","81692929393", "kojot@gmail.com");
        Masseur masseur1 = new Masseur(111, "Jan", "Dobry", dt_bd, true, bd, bd, masseur1Info, "Masaż punktowy");
        Masseur masseur2 = new Masseur(222, "Marian", "Lepszy", dt_bd, false, bd, bd, masseur2Info, "Masaż sportowy");
        Masseur masseur3 = new Masseur(333, "Andrzej", "Ciekawy", dt_bd, false, bd, bd, masseur3Info, "Masaż sportowy");
        Room room1 = new Room(108,3,2,true);
        Trainer trainer1 = new Trainer(3, "Jan", "Rakon", dt, true, bd, bd);
        Training training1 = new Training(dt, start, end);
        TrainingEquipment trainingEquipment1 = new TrainingEquipment("roller");
        TrainingType trainingType1 = new TrainingType("fatBurning");
        CustomerTraining customerTraining1 = new CustomerTraining(training1, customer1, 5);

        masseur1.addFinishedCourse("Masaż brzucha");
        masseur1.addFinishedCourse("Fizjoterapia kończyn dolnych");
        masseur1.addFinishedCourse("Fizjoterapia mięśni głębokich");
        masseur1.addFinishedCourse("Mięśnie core i ich struktura");
        masseur1.addFinishedCourse("Mięśnie core - opieka nad profesjonalistami");

        masseur2.addFinishedCourse("Masaż brzucha");
        masseur2.addFinishedCourse("Fizjoterapia kończyn dolnych");
        masseur2.addFinishedCourse("Fizjoterapia ogólna");
        masseur2.addFinishedCourse("Fizjoterapia biegaczy");

        masseur3.addFinishedCourse("Fizjoterapia biegaczy");
        masseur3.addFinishedCourse("Fizjoterapia seniorów");

        Duty duty1m1 = new Duty(dt, start, end, true );
        Duty duty2m1 = new Duty(dt2, start, end, true );
        Duty duty3m1 = new Duty(dt3, start, end, true );
        Duty duty1m2 = new Duty(dt, start, end, true );
        Duty duty2m2 = new Duty(dt2, start, end, true );
        Duty duty3m2 = new Duty(dt3, start, end, true );
        Duty duty1m3 = new Duty(dt3, mid, end, false );

        Slot slot1d1m1 = new Slot(start,true);
        Slot slot2d1m1 = new Slot(mid,true);
        Slot slot1d2m1 = new Slot(start,false);
        Slot slot2d2m1 = new Slot(mid,true);
        Slot slot1d3m1 = new Slot(start,true);
        Slot slot2d3m1 = new Slot(mid,true);
        Slot slot1d1m2 = new Slot(start,false);
        Slot slot2d1m2 = new Slot(mid,true);
        Slot slot1d2m2 = new Slot(start,true);
        Slot slot2d2m2 = new Slot(mid,false);
        Slot slot1d3m2 = new Slot(start,false);
        Slot slot2d3m2 = new Slot(mid,true);
        Slot slot1d1m3 = new Slot(mid,false);

        trainer1.addTrainingType(trainingType1);
        training1.addTrainingType(trainingType1);
        training1.addTrainingEquipment(trainingEquipment1);
        training1.setRoom(room1);
        training1.setTrainer(trainer1);

        masseur1.addDuty(duty1m1);
        masseur1.addDuty(duty2m1);
        masseur1.addDuty(duty3m1);
        masseur2.addDuty(duty1m2);
        masseur2.addDuty(duty2m2);
        masseur2.addDuty(duty3m2);
        masseur3.addDuty(duty1m3);

        duty1m1.setRoom(room1);
        duty2m1.setRoom(room1);
        duty3m1.setRoom(room1);
        duty1m2.setRoom(room1);
        duty2m2.setRoom(room1);
        duty3m2.setRoom(room1);
        duty1m3.setRoom(room1);

        duty1m1.addSlot(slot1d1m1);
        duty1m1.addSlot(slot2d1m1);
        duty2m1.addSlot(slot1d2m1);
        duty2m1.addSlot(slot2d2m1);
        duty3m1.addSlot(slot1d3m1);
        duty3m1.addSlot(slot2d3m1);
        duty1m2.addSlot(slot1d1m2);
        duty1m2.addSlot(slot2d1m2);
        duty2m2.addSlot(slot1d2m2);
        duty2m2.addSlot(slot2d2m2);
        duty3m2.addSlot(slot1d3m2);
        duty3m2.addSlot(slot2d3m2);
        duty1m3.addSlot(slot1d1m3);

        appointment1.setSlot(slot1d1m1);
        customer1.addAppointment(appointment1);


        trainingTypeRepo.save(trainingType1);
        trainerRepo.save(trainer1);
        roomRepo.save(room1);
        trainingEquipmentRepo.save(trainingEquipment1);
        trainingRepo.save(training1);
        masseurRepo.save(masseur1);
        masseurRepo.save(masseur2);
        masseurRepo.save(masseur3);
        customerRepo.save(customer1);
        customerTrainingRepo.save(customerTraining1);

    }

}
