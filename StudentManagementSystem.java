import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class AcademicEntity{
    private String code;
    private String name;
    private String description;

    public AcademicEntity(String code,String name,String description){
        this.code=code;
        this.name=name;
        this.description=description;
    }

    public String getName(){return name;}

    public String getCode(){return code;}
}

class Course extends AcademicEntity{
    private int credit;
    private String department;
    private String semester;
    private List<String> gradeList = new ArrayList<>();
    private String program;
    private static HashMap<String,Course> codeMap=new HashMap<>();
    private List<Student> enrolled=new ArrayList<>();
    private AcademicMember teacher;
    private static List<Course> courseList =new ArrayList<>();

    public String getDepartment(){return department;}

    public Course(String code,String name,String department ,String credit,String semester,String program){
        super(code,name,"");
        this.department=department;
        this.credit=Integer.parseInt(credit);
        this.semester=semester;
        this.program=program;
        codeMap.put(code,this);
        courseList.add(this);
    }

    public void addGrade(String grade){gradeList.add(grade);}

    public static Course findByCode(String code){return codeMap.get(code);}

    public void newEnroll(Student student){enrolled.add(student);}
    
    public List<Student> getStudents(){return enrolled;}

    public void assignTeacher(AcademicMember teacher){this.teacher=teacher;}

    public AcademicMember getTeacher(){return teacher;}

    public void getCourseInfo (){
        Output.outputAdd("Course Code: "+this.getCode());
        Output.outputAdd("Name: "+this.getName());
        Output.outputAdd("Department: "+this.getDepartment());
        Output.outputAdd("Credits: "+credit);
        Output.outputAdd("Semester: "+semester);
    }

    public static List<Course> getCourses(){
        courseList.sort(Comparator.comparing(Course::getName));
        return courseList;
    }

    public double avgGrade(){
        double sum=0;
        for(String grade:gradeList){
            switch(grade){
                case "A1":
                    sum+=4.00;
                    break;
                case "A2":
                    sum+=3.50;
                    break;
                case "B1":
                    sum+=3.00;
                    break;
                case "B2":
                    sum+=2.50;
                    break;
                case "C1":
                    sum+=2.00;
                    break;
                case "C2":
                    sum+=1.50;
                    break;
                case "D1":
                    sum+=1.00;
                    break;
                case "D2":
                    sum+=0.50;
                    break;
                case "F3":
                    sum+=0;
                    break;
            }
        }
        if (sum==0){return 0.00;}
        return sum/(gradeList.size());
    }

    public void showGrades(){
        int a1=0;
        int a2=0;
        int b1=0;
        int b2=0;
        int c1=0;
        int c2=0;
        int d1=0;
        int d2=0;
        int f3=0;
        for(String grade:gradeList){
            switch(grade){
                case "A1":
                    a1++;
                    break;
                case "A2":
                    a2++;
                    break;
                case "B1":
                    b1++;
                    break;
                case "B2":
                    b2++;
                    break;
                case "C1":
                    c1++;
                    break;
                case "C2":
                    c2++;
                    break;
                case "D1":
                    d1++;
                    break;
                case "D2":
                    d2++;
                    break;
                case "F3":
                    f3++;
                    break;
            }
        }
        if(a1!=0){
            Output.outputAdd("A1: "+a1);
        }
        if(a2!=0){
            Output.outputAdd("A2: "+a2);
        }
        if(b1!=0){
            Output.outputAdd("B1: "+b1);
        }
        if(b2!=0){
            Output.outputAdd("B2: "+b2);
        }
        if(c1!=0){
            Output.outputAdd("C1: "+c1);
        }
        if(c2!=0){
            Output.outputAdd("C2: "+c2);
        }
        if(d1!=0){
            Output.outputAdd("D1: "+d1);
        }
        if(d2!=0){
            Output.outputAdd("D2: "+d2);
        }
        if(f3!=0){
            Output.outputAdd("F3: "+f3);
        }
        Output.outputAdd("");
    }
}

class Program extends AcademicEntity{
    private String department;
    private String degree;
    private int totalCredits;
    private static List<Program> progList=new ArrayList<>();
    private static HashMap<String,Program> programCodes = new HashMap<>();

    public String getDepartment(){return department;}

    public Program(String code ,String name,String description, String department,String degree,int totalCredits){
        super(code,name,description);
        this.department=department;
        this.degree=degree;
        this.totalCredits=totalCredits;
        progList.add(this);
        programCodes.put(code,this);
        //found by codes form courses
    }

    public static List<Program> getProgList(){return progList;}

    public void getProgInfo(){
        Output.outputAdd("Program Code: "+getCode());
        Output.outputAdd("Name: "+getName());
        Output.outputAdd("Department: "+getDepartment());
        Output.outputAdd("Degree Level: "+degree);
        Output.outputAdd("Required Credits: "+totalCredits);
        if(this.getProgCourses().size()!=0){
            Output.outputAdd(String.format("Courses: {%s}\n",String.join(",",this.getProgCourses())));
        }else{
            Output.outputAdd("Courses: - ");
        }
    }

    public static Program getByCode(String code){
        return programCodes.get(code);
    }

    public List<String> getProgCourses(){
        List<String> courseCodes = new ArrayList<>();
        List<String> finalList= new ArrayList<>();
        for(Course course:Course.getCourses()){
            courseCodes.add(course.getCode());
        }
        for(String code:courseCodes){
            if(code.contains(getCode())){
                finalList.add(code);
            }
        }
        return finalList;
    }

    public static void sortList(){
        progList.sort(Comparator.comparing(Program::getCode));
    }
}

class Department extends AcademicEntity{
    private AcademicMember head;
    private static List<Department> departmentList= new ArrayList<>();

    public Department(String code,String name ,String description,String head){
        super(code,name,description);
        if(head!="null"){
            AcademicMember headPerson=(AcademicMember)Person.findByID("F",head);
            this.setHead(headPerson);
        }
        departmentList.add(this);
    }

    public void setHead(AcademicMember person){head=person;}

    public void getDepartmentInfo(){
        Output.outputAdd("Department Code: "+this.getCode());
        Output.outputAdd("Name: "+this.getName());
        if(head==null){
            Output.outputAdd("Head: Not assigned");
        }else{
            Output.outputAdd("Head: "+head.getName());
        }
    }

    public static List<Department> getDepartmentList(){return departmentList;}
}

public class StudentManagementSystem{

    public static void reader(String file){
        //reads the input line by line
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            int lineIndex=0;
            while ((line = br.readLine()) != null){
                lineIndex++;
                //splits each line by comma
                String[] parts = line.split(",");
                //finding the type of input
                if(parts.length==6){
                    /*if this try block does not give exception that means
                    its a programs.txt if does give an exception its courses
                    because there is not an integer at parts[5] of courses */
                    try{
                        int credit = Integer.parseInt(parts[5]);
                        if(lineIndex==1){
                            Output.outputAdd("Reading Programs ");
                        }
                        new Program(parts[0],parts[1],parts[2],parts[3],parts[4],credit);
                    }catch(NumberFormatException e){
                        if(lineIndex==1){
                            Output.outputAdd("Reading Courses ");
                        }
                        if(Program.getProgList().contains(Program.getByCode(parts[5]))){
                            new Course(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]);
                        }else{
                            Output.outputAdd("Program "+parts[5]+" Not Found");
                        }
                    }
                }
                //persons
                if(parts.length==5){
                    if(lineIndex==1){
                        Output.outputAdd("Reading Person Information ");
                    }
                    if(parts[0].equals("S")){
                        new Student(parts[1],parts[2],parts[3],parts[4]);
                    }else if(parts[0].equals("F")){
                        new AcademicMember(parts[1],parts[2],parts[3],parts[4]);
                    }else{
                        Output.outputAdd("Invalid Person Type");
                    }
                }
                //department
                if(parts.length==4){
                    if(lineIndex==1){
                        Output.outputAdd("Reading Departments ");
                    }
                    if(Person.findByID("F",parts[3])==null){
                        Output.outputAdd("Academic Member Not Found with ID "+parts[3]);
                        new Department(parts[0],parts[1],parts[2],"null");
                    }else{
                        new Department(parts[0],parts[1],parts[2],parts[3]);
                    }
                }
                if(parts.length==3){
                    Course course= Course.findByCode(parts[2]);
                    if(parts[0].equals("S") || parts[0].equals("F")){
                        //assignment
                        if(lineIndex==1){
                            Output.outputAdd("Reading Course Assignments ");
                        }
                        //no course
                        if(parts[0].equals("S")){
                            Student s=(Student) Person.findByID("S",parts[1]);  
                            if(s==null){
                                Output.outputAdd("Student Not Found with ID "+parts[1]);
                            }else{
                                if(course==null){
                                    Output.outputAdd("Course "+parts[2]+" Not Found");
                                }else{
                                    s.enroll(course);
                                }
                            }
                        }else if(parts[0].equals("F")){
                            AcademicMember a=(AcademicMember) Person.findByID("F",parts[1]);
                            if(a==null){
                                Output.outputAdd("Academic Member Not Found with ID "+parts[1]);
                            }else{
                                if(course==null){
                                    Output.outputAdd("Course "+parts[2]+" Not Found");
                                }else{   
                                    a.enroll(course);
                                }
                            }
                        }else{
                            Output.outputAdd("Invalid Person Type");
                        }
                    }else{
                        //grading
                        Student s = (Student) Person.findByID("S",parts[1]);
                        if(lineIndex==1){
                            Output.outputAdd("Reading Grades ");
                        }
                        if(s==null){
                            Output.outputAdd("Student Not Found with ID "+parts[1]);
                        }else if(course==null){ 
                            Output.outputAdd("Course "+parts[2]+" Not Found");
                        }else{
                            s.setGrade(parts[0],course);
                        }
                    }
                }
            }
        }catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public static void personInformation(){
        Output.outputAdd("----------------------------------------");
        Output.outputAdd("            Academic Members");
        Output.outputAdd("----------------------------------------");
        for(AcademicMember person:Person.getAcademicMembers().values()){
            person.showProps();
            Output.outputAdd("");
        }
        Output.outputAdd("----------------------------------------\n");
        Output.outputAdd("----------------------------------------");
        Output.outputAdd("                STUDENTS");
        Output.outputAdd("----------------------------------------");
        for(Student person:Person.getStudents().values()){
            person.showProps();
            Output.outputAdd("");
        }
        Output.outputAdd("----------------------------------------\n");
    }

    public static void departmentInfo(){
        Output.outputAdd("---------------------------------------");
        Output.outputAdd("              DEPARTMENTS");
        Output.outputAdd("---------------------------------------");
        for(Department department:Department.getDepartmentList()){
            department.getDepartmentInfo();
            Output.outputAdd("");
        }
        Output.outputAdd("----------------------------------------\n");
    }

    public static void programInfo(){
        Output.outputAdd("--------------------------------------");
        Output.outputAdd("                PROGRAMS");
        Output.outputAdd("---------------------------------------");
        Program.sortList();
        for(Program program:Program.getProgList()){
            program.getProgInfo();
        }
        Output.outputAdd("----------------------------------------\n");
    }

    public static void courseInfo(){
        Output.outputAdd("---------------------------------------");
        Output.outputAdd("                COURSES");
        Output.outputAdd("---------------------------------------");
        for(Course course:Course.getCourses()){
            course.getCourseInfo();
            Output.outputAdd("");
        }
        Output.outputAdd("----------------------------------------\n");
    }

    public static void courseReports(){
        Output.outputAdd("----------------------------------------");
        Output.outputAdd("             COURSE REPORTS");
        Output.outputAdd("----------------------------------------");
        for(Course course:Course.getCourses()){
            course.getCourseInfo();
            Output.outputAdd("");
            try{
                Output.outputAdd("Instructor: "+course.getTeacher().getName()+"\n");
            }catch(NullPointerException e){
                Output.outputAdd("Instructor: Not assigned\n");
            }
            Output.outputAdd("Enrolled Students:");
            for(Student student:course.getStudents()){
                Output.outputAdd(String.format("- %s (ID: %s)",student.getName(),student.getID()));
            }
            Output.outputAdd("\nGrade Distribution:");
            course.showGrades();
            Output.outputAdd(String.format("Average Grade: %.2f\n",course.avgGrade()));
            Output.outputAdd("----------------------------------------\n");
        }
    }

    public static void studentReports(){
        Output.outputAdd("----------------------------------------");
        Output.outputAdd("            STUDENT REPORTS");
        Output.outputAdd("----------------------------------------");
        for(Student student:Person.getStudents().values()){
            student.showProps();
            Output.outputAdd("\n");
            Output.outputAdd("Enrolled Courses:");
            for(Course course:student.getClasses()){
                Output.outputAdd(String.format("- %s (%s)",course.getName(),course.getCode()));
            }
            Output.outputAdd("");
            Output.outputAdd("Completed Courses:");
            for(Course course:student.completedCourses().keySet()){
                Output.outputAdd(String.format("- %s (%s): %s",course.getName(),course.getCode(),student.getGrade(course)));
            }
            Output.outputAdd("");
            Output.outputAdd(String.format("GPA: %.2f",student.calcGPA()));
            Output.outputAdd("----------------------------------------\n");
        }
    }
}