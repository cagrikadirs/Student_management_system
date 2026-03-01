public class Main{
    public static void main(String[] args){
        StudentManagementSystem.reader(args[0]);
        StudentManagementSystem.reader(args[1]);
        StudentManagementSystem.reader(args[2]);
        StudentManagementSystem.reader(args[3]);
        StudentManagementSystem.reader(args[4]);
        StudentManagementSystem.reader(args[5]);
        StudentManagementSystem.personInformation();
        StudentManagementSystem.departmentInfo();
        StudentManagementSystem.programInfo();
        StudentManagementSystem.courseInfo();
        StudentManagementSystem.courseReports();
        StudentManagementSystem.studentReports();
        Output.write(args[6]);
    }
}