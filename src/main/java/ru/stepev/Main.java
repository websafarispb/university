package ru.stepev;

import java.time.LocalDate;
import java.util.*;

import ru.stepev.model.*;
import ru.stepev.uinterface.UserInterface;
import ru.stepev.utils.DataHelper;

public class Main {

	public static void main(String[] args) {
		
		UserInterface userInterface = new UserInterface();		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println(userInterface.getMenu());
			System.out.println("Make your choice");
			String item = scanner.nextLine();
			if(item.toLowerCase().equals("exit"))
				break;
			System.out.println(userInterface.choseMenuItem(item));
			
		}
		scanner.close();
		
	}
}
