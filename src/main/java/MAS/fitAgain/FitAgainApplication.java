package MAS.fitAgain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import MAS.fitAgain.gui.controllers.MainViewController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import MAS.fitAgain.model.*;
import MAS.fitAgain.model.Appointment.AppointmentStatus;
import MAS.fitAgain.repo.*;

import javax.swing.*;

@SpringBootApplication
@EnableJpaRepositories("MAS.fitAgain.repo")
public class FitAgainApplication {
	 @Autowired
	 private DataInitializer dataInitializer;

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(FitAgainApplication.class)
				.headless(false).run(args);

		//context.getBean(DataInitializer.class).setData();
		SwingUtilities.invokeLater(()-> {
			context.getBean(MainViewController.class).showGUI();
		});
	}
}
