import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import com.google.gson.*;

public class BuildResume {
	private static Scanner input = new Scanner(System.in);
	private static String resumeString = "";
	public static void main(String[] args) {
		int choice = 0;
		while(choice != 4) {
			menu();
			choice = menuValid();
			input.nextLine();
			menuChoice(choice);
		}
	}

	public static void menu() {
		System.out.print("\tWelcome to Resume Builder!\n"
				+ "1) New Resume\n"
				+ "2) Load Resume\n"
				+ "3) Print Resume\n"
				+ "4) Quit\n"
				+ "Please choose an option: ");
	}

	public static int menuValid() {

		int choice = input.nextInt();
		while(choice < 1 || choice > 3) {
			System.out.print("Invalid choice please choose again: ");
			choice = input.nextInt();
		}
		return choice;
	}

	public static void menuChoice(int choice) {
		switch(choice) {
		case 1:
			buildResume();
			break;
		case 2: 
			try {
				loadResume();
			} catch (org.json.simple.parser.ParseException e) {
				e.printStackTrace();
			}
			break;
		case 3: 
			System.out.println(resumeString);
			break;
		case 4:
			System.exit(0);
			break;
		default:
			break;
		}
	}
	@SuppressWarnings("unchecked")
	public static void buildResume() {

		JSONObject resume = new JSONObject();

		System.out.print("Enter name: ");
		resume.put("name", input.nextLine());

		System.out.print("Enter birth date: ");
		resume.put("birthdate", input.nextLine());

		System.out.print("Enter your location: ");
		resume.put("location", input.nextLine());

		System.out.print("Enter phone number: ");
		resume.put("phone", input.nextLine());

		System.out.print("Enter email: ");
		resume.put("email", input.nextLine());

		System.out.print("Enter a brief description about yourself: ");
		resume.put("description", input.nextLine());

		System.out.print("How many work experiences are there?: ");
		int numExperiences = input.nextInt();
		input.nextLine();

		if(numExperiences != 0) {
			JSONArray expArr = new JSONArray();
			for(int i = 0; i < numExperiences; i++) {
				String exp = "Experience-" + (i+1);
				System.out.println(exp);
				JSONObject experience = new JSONObject();
				System.out.print("Enter experience name: ");
				experience.put("exp-name", input.nextLine());
				System.out.print("Enter experience description: ");
				experience.put("exp-description", input.nextLine());
				System.out.print("Enter date of experience (month/year): ");
				experience.put("exp-year", input.nextLine());
				expArr.add(experience);
			}
			resume.put("experience", expArr);
		}


		System.out.print("Enter your Education\n");

		JSONObject school = new JSONObject();
		System.out.print("Enter school name: ");
		school.put("school-name", input.nextLine());
		System.out.print("Enter dates attended (start year - end year): ");
		school.put("school-year", input.nextLine());
		System.out.print("Enter Degree Type: ");
		school.put("school-degree", input.nextLine());

		resume.put("education", school);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(resume.toString());
		String prettyJsonString = gson.toJson(je);
		resumeString = prettyJsonString;
		try (FileWriter file = new FileWriter("resume.json")) {

			file.write(prettyJsonString);
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Your resume has been created!");
	}

	public static void loadResume() throws org.json.simple.parser.ParseException {
		JSONParser jsonParser = new JSONParser();
		Object obj = null;
        try (FileReader reader = new FileReader("resume.json"))
        {
            //Read JSON file
        	obj = jsonParser.parse(reader);
        	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    		JsonParser jp = new JsonParser();
    		JsonElement je = jp.parse(obj.toString());
    		String prettyJsonString = gson.toJson(je);
            resumeString = prettyJsonString;
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Resume has loaded successfully!");

	}
}
