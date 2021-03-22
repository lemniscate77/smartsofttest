package com.example.SmartSoftTestv01;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class Application extends JFrame{
	/*public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}*/

	public static void main(String[] args) {

		//SpringApplication.run(Application.class, args);
		SpringApplication.run(Application.class, args);

		openLogin();  // Открываем окно логина

	}


	public static void openLogin(){ // Открытие окна
		Login login = new Login();
		login.setVisible(true);
		login.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void openConvertor() throws ParserConfigurationException, SAXException, ParseException, IOException {
		Convertor convertor = new Convertor(); // Открытие основного окна
		convertor.setVisible(true);
		convertor.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void openHistory(ConvertToJavaObj a){ // Открытие истории
		History history = new History(a);
		history.setVisible(true);
		history.setDefaultCloseOperation(HIDE_ON_CLOSE);
	}

}
