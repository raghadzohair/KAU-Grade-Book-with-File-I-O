/*
 * name: raghad zohair - ID: 1705628 - CPCS204 - secion: BBR
 * email: ryahya0010@stu.kau.edu.sa
 * Program 1: KAU Grade Book with File I/O
 * Due: Thursday, September 20th, 2018 by 11:59 PM
 */
package BBR_1705628_P1;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

public class KAUgradebookIO {

    
    public static void main(String[] args) throws Exception {
        // create input file
        File input = new File("input.txt");
        // check if filr is exists
        if(!input.exists()){
            System.out.println("The file input NOT exists!!!");
            System.exit(0);
        }
        // create output file and objects of Scanner,PrintWriter
        File output = new File("output.txt");
        Scanner read = new Scanner(input);
        PrintWriter write = new PrintWriter(output);
        write.println("Welcome to the KAU Grade Book.\r\n");
        //read 3 variabls 1)course number 2)instructor 3)max number of arrayStudent
        String courseName = read.next();
        String instructor = read.next()+" "+read.next();
        int maxNumStudents = read.nextInt();        
        //creat array from arrayStudent object 
        Student[] arrayStudent = new Student[maxNumStudents];       
        //declear String for read the command from input file
        String command;
        while (read.hasNext()) {
            command = read.next();
            if (command.equals("ADDRECORD")) {
                addRecord(read, arrayStudent, command,write);                
            }
            else if(command.equals("SEARCHBYID")){
                searchStudentsByID(read, command,  arrayStudent, write);
            }
            else if(command.equals("DISPLAYSTATS")){
                displayClassStatistics(arrayStudent, command, courseName, instructor, write);
            }

            else if(command.equals("DISPLAYSTUDENTS")){
                displayAllStudents (arrayStudent,command, write );
            }
            else if (command.equals("SEARCHBYNAME")){
                searchStudentsByName(read, arrayStudent, command, write);
            }
            
            else {
                write.println("Thank you for using the the KAU Grade Book.\r\nGood Bye");           
            }
        }
        //close the Scanner and printwriter objects 
        read.close();
        write.close();
    }
    ///////////////////////////////////////   
    //////////////////////////////////////
    public static void addRecord(Scanner read, Student arrayStudent[], String command, PrintWriter write){
        // create student object and save all info. in student 
        Student student= new Student();
        student.setID(read.nextInt());
        student.setfName(read.next());
        student.setlName(read.next());
        for (int i = 0; i < 3; i++) {           
            student.setExamGrades(read.nextInt(), i);    
        }
        student.setFinalGrade((student.getExamGrades(0)*0.3+student.getExamGrades(1)*0.3+student.getExamGrades(2)*0.4));        
        student.setLetterGrade(getLetterGrade(student));
        write.println("Command: "+command);
        write.printf(student.getfName()+" "+student.getlName()+                
            " (ID# "+student.getID()+") "
            + "has been added to the KAU Grade Book.\r\n"+"   His final grade is  %.2f (%c)\r\n\r\n",student.getFinalGrade(),student.getLetterGrade());
        //  sort array of student object
        if(arrayStudent[0] !=null){
            int index=0;
            for (int i = 0; i < Student.getNumStudent(); i++) {
                if(arrayStudent[i].getID()<student.getID()){
                    index++;
                }                                
            }
            // for loop to shift elements
            for (int i = Student.getNumStudent(); i > index; i--) {
                arrayStudent[i] = arrayStudent[i - 1];
            }
            arrayStudent[index] = student;
            Student.increaseStudents();
        } 
        else {
            arrayStudent[0]=student;
            Student.increaseStudents();
        }   
    }    
    //////////////////////////////////////// 
    ///////////////////////////////////////
    public static void searchStudentsByID(Scanner read, String command, Student[] arrayStudent, PrintWriter write){
        write.println("Command: "+command);
        int ID = read.nextInt();
        // use binary search
        int low = 0, high = Student.getNumStudent()-1, mid = 0;
        while (low <= high) {
            mid = (low + high) / 2;
            if (arrayStudent[mid] !=null && ID == arrayStudent[mid].getID()) {
                write.println("Student Record for " + arrayStudent[mid].getfName() + " " + arrayStudent[mid].getlName() + " (ID # " + arrayStudent[mid].getID() + "):");
                write.println("     Exam1:        " + arrayStudent[mid].getExamGrades(0));
                write.println("     Exam2:        " + arrayStudent[mid].getExamGrades(1));
                write.println("     Final Exam:   " + arrayStudent[mid].getExamGrades(2));
                write.printf("     Final Grade:  %.2f\r\n" ,arrayStudent[mid].getFinalGrade());
                write.println("     Letter Grade: " + arrayStudent[mid].getLetterGrade() + "\r\n");
                return;
            } 
            else if (arrayStudent[mid] !=null && ID < arrayStudent[mid].getID()) {               
                high = mid - 1;
            } 
            else {
                low = mid + 1;
            }
        }
        write.println("ERROR: there is no record for student ID # " + ID + ".\r\n");
    }
    /////////////////////////////////////// 
    //////////////////////////////////////
    public static void searchStudentsByName(Scanner read, Student[] arrayStudent, String command, PrintWriter write ){
        write.println("Command: "+command);
        String fName = read.next();
        String lName = read.next();
        boolean isFound = false;
        int j = 0;
        // check if array is null and use linear search to find name student
        if (arrayStudent != null) {
            for (int i = 0; i < arrayStudent.length; i++) {
                if (arrayStudent[i] != null && arrayStudent[i].getfName().equalsIgnoreCase(fName) && arrayStudent[i].getlName().equalsIgnoreCase(lName)) {
                    isFound = true;
                    j = i;
                }
            }
        }
        if (isFound == true) {
            write.println("Student Record for " + fName + " " + lName + " (ID # " + arrayStudent[j].getID() + "):");
            write.println("     Exam1:        "+arrayStudent[j].getExamGrades(0));
            write.println("     Exam2:        "+arrayStudent[j].getExamGrades(1));
            write.println("     Final Exam:   "+arrayStudent[j].getExamGrades(2));
            write.printf("     Final Grade:  %.2f\r\n",arrayStudent[j].getFinalGrade());
            write.println("     Letter Grade: "+arrayStudent[j].getLetterGrade()+"\r\n");
        } 
        else {
            write.println("ERROR: there is no record for student \"" + fName + " " + lName + "\".\r\n");
        }
    }
    /////////////////////////////////////// 
    //////////////////////////////////////
    public static void displayClassStatistics(Student[] arrayStudent, String command,String courseName,String instructor, PrintWriter write){
        write.println("Command: "+command);
        write.println("Statistical Results of "+courseName+" (Instructor: "+instructor+"):");
        write.println("     Total number of student records: "+Student.getNumStudent());
        // check if any student is recorded
        if(Student.getNumStudent()==0){
            write.println("     Average Score: 0.00");
            write.println("     Highest Score: 0.00");
            write.println("     Lowest Score:  0.00");
            write.println("     Total 'A' Grades: 0 (0.00% of class)");
            write.println("     Total 'B' Grades: 0 (0.00% of class)");
            write.println("     Total 'C' Grades: 0 (0.00% of class)");
            write.println("     Total 'D' Grades: 0 (0.00% of class)");
            write.println("     Total 'F' Grades: 0 (0.00% of class)\r\n");
        }
        else {
            double average = 0;
            double HigheScore = 0;
            double lowScore = 100; // the largest score is 100
            int A = 0, B = 0, C = 0, D = 0, F = 0;
            for (int i = 0; i < Student.getNumStudent(); i++) {

                average = average+arrayStudent[i].getFinalGrade();
                if (HigheScore < arrayStudent[i].getFinalGrade()) {
                    HigheScore = arrayStudent[i].getFinalGrade();
                }
                if (lowScore > arrayStudent[i].getFinalGrade()) {
                    lowScore = arrayStudent[i].getFinalGrade();
                }
                if (arrayStudent[i].getLetterGrade() == 'A') {
                    A++;
                } else if (arrayStudent[i].getLetterGrade() == 'B') {
                    B++;
                } else if (arrayStudent[i].getLetterGrade() == 'C') {
                    C++;
                } else if (arrayStudent[i].getLetterGrade() == 'D') {
                    D++;
                } else {
                    F++;
                }
            }
            write.printf("     Average Score: %.2f\r\n", Math.floor(average/Student.getNumStudent()));
            write.printf("     Highest Score: %.2f\r\n", HigheScore);
            write.printf("     Lowest Score:  %.2f\r\n", lowScore);
            double Ascore = ((100/ Student.getNumStudent())*A);
            double Bscore = ((100/ Student.getNumStudent())*B);
            double Cscore = ((100/ Student.getNumStudent())*C);
            double Dscore = ((100/ Student.getNumStudent())*D);
            double Fscore = ((100/ Student.getNumStudent())*F);
            write.printf("     Total 'A' Grades: %d (%.2f%% of class)\r\n", A, (Ascore));
            write.printf("     Total 'B' Grades: %d (%.2f%% of class)\r\n", B, (Bscore));
            write.printf("     Total 'C' Grades: %d (%.2f%% of class)\r\n", C, (Cscore));
            write.printf("     Total 'D' Grades: %d (%.2f%% of class)\r\n", D, (Dscore));
            write.printf("     Total 'F' Grades: %d (%.2f%% of class)\r\n\r\n", F, (Fscore));
        }   
    }   
    ///////////////////////////////////////  
    //////////////////////////////////////
    public static void displayAllStudents (Student [] arrayStudent,String command, PrintWriter write ){
        write.println("Command: "+command);
        // check if any student recorded
        if (Student.getNumStudent() !=0) {
            write.println("***Class Roster and Grade Sheet***\r\n");
            // print all student 
            for (int i = 0; i < Student.getNumStudent(); i++) {
                write.println(" - Student Record for " + arrayStudent[i].getfName() + " " + arrayStudent[i].getlName() + " (ID # " + arrayStudent[i].getID() + "):");
                write.println("     Exam1:        " + arrayStudent[i].getExamGrades(0));
                write.println("     Exam2:        " + arrayStudent[i].getExamGrades(1));
                write.println("     Final Exam:   " + arrayStudent[i].getExamGrades(2));
                write.printf("     Final Grade:  %.2f\r\n" , arrayStudent[i].getFinalGrade());
                write.println("     Letter Grade: " + arrayStudent[i].getLetterGrade());                   
            }
            write.println();
        }
        else {
            write.println("   ERROR: there are no students currently in the system.\r\n");
        }
    }    
    ////////////////////////////////////// 
    /////////////////////////////////////
    public static char getLetterGrade(Student student){
        if(student.getFinalGrade()<=100&&student.getFinalGrade()>=90){
            return 'A';
        }
        else if(student.getFinalGrade()<=89&&student.getFinalGrade()>=80){
            return 'B';
        }
        else if(student.getFinalGrade()<=79&&student.getFinalGrade()>=70){
            return 'C';
        }
        else if(student.getFinalGrade()<=69&&student.getFinalGrade()>=60){
            return 'D';
        }
        else
            return 'F';        
    }
}
