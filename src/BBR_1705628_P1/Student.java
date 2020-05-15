/*
 * name: raghad zohair - ID: 1705628 - CPCS204 - secion: BBR
 * email: ryahya0010@stu.kau.edu.sa
 * Program 1: KAU Grade Book with File I/O
 * Due: Thursday, September 20th, 2018 by 11:59 PM
 */
package BBR_1705628_P1;


public class Student {

    private String fName;
    private String lName;
    private int ID;
    private int []examGrades =new int[3] ;
    private double finalGrade;
    private char letterGrade;
    private static int numStudent = 0;

    // constrcter
    public Student() {
    }


    public String getfName() {
        return fName;
    }

    public String getlName() {
        return lName;
    }

    public int getID() {
        return ID;
    }

    public double getFinalGrade() {
        return finalGrade;
    }

    public int getExamGrades(int index) {
        return examGrades[index];
    }   
    
    public char getLetterGrade() {
        return letterGrade;        
    }
    
    public static int getNumStudent() {
        return numStudent;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public void setFinalGrade(double finalGrade) {       
        this.finalGrade = finalGrade;
    }

    public void setExamGrades(int examGradess, int index) {
        examGrades[index]= examGradess;       
    }
    
    public void setLetterGrade(char letterGrade) {
        this.letterGrade = letterGrade;
    }  
    
    public static void increaseStudents(){
        Student.numStudent++;
    }
}
