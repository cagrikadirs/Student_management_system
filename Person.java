import java.util.*;

public abstract class Person{
    private String ID;
    private String name;
    private String email;
    private String department;
    private static LinkedHashMap<String,Student> studentIDList= new LinkedHashMap<>();
    private static LinkedHashMap<String,AcademicMember> academicMemberIDList = new LinkedHashMap<>();

    public Person(String ID,String name,String email,String department){
        this.ID=ID;
        this.name=name;
        this.email=email;
        this.department=department;
    }

    //each type has its own enroll method
    public abstract void enroll(Course course);

    public static Person findByID(String type,String ID){
        if(type.equals("S")){
            return studentIDList.get(ID);
        }else if(type.equals("F")){
            return academicMemberIDList.get(ID);
        }else{return null;}
    }

    public static void newStudent(String ID,Student student){studentIDList.put(ID,student);}

    public static void newAcademicMember(String ID,AcademicMember person){academicMemberIDList.put(ID,person);}

    public static HashMap<String,AcademicMember> getAcademicMembers(){return academicMemberIDList;}

    public static HashMap<String,Student> getStudents(){return studentIDList;}

    public abstract void showProps();

    public String getName(){return name;}

    public String getID(){return ID;}

    public String getEmail(){return email;}

    public String getDepartment(){return department;}
}

class Student extends Person{
    //class and grade list
    private List<Course> enrolledClasses=new ArrayList<>();
    private HashMap<Course,String> grades = new HashMap<Course,String>();
    private HashMap<Course,Double> gradesDouble = new HashMap<Course,Double>();

    public Student(String ID,String name,String email,String department){
        super(ID,name,email,department);
        Person.newStudent(ID,this);
    }

    public List<Course> getClasses(){
        List<Course> rList =new ArrayList<>();
        for(Course course:enrolledClasses){
            if(grades.keySet().contains(course)){
                rList.add(course);
            }
        }
        return rList;
    }

    @Override
    public void enroll(Course course){
        course.newEnroll(this);
        enrolledClasses.add(course);
    }

    public void setGrade(String grade,Course course){
        boolean x=false;
        switch(grade){
            case "A1":
                gradesDouble.put(course,4.00);
                break;
            case "A2":
                gradesDouble.put(course,3.50);
                break;
            case "B1":
                gradesDouble.put(course,3.00);
                break;
            case "B2":
                gradesDouble.put(course,2.50);
                break;
            case "C1":
                gradesDouble.put(course,2.00);
                break;
            case "C2":
                gradesDouble.put(course,1.50);
                break;
            case "D1":
                gradesDouble.put(course,1.00);
                break;
            case "D2":
                gradesDouble.put(course,0.50);
                break;
            case "F3":
                gradesDouble.put(course,0.00);
                break;
            default:
                Output.outputAdd("Invalid grade");
                x=true;
                break;
            }
        if(x){return;}
        grades.put(course,grade);
        course.addGrade(grade);
    }

    public String getGrade(Course course){return grades.get(course);}

    public HashMap<Course,String> completedCourses(){return grades;}

    public double calcGPA(){
        double sum=0;
        int counter=0;
        for(double grade:gradesDouble.values()){
            sum+=grade;
            counter++;
        }
        if(sum==0){
            return 0.00;
        }
        return sum/counter;
    }

    @Override
    public void showProps(){
        Output.outputAdd("Student ID: "+getID());
        Output.outputAdd("Name: "+getName());
        Output.outputAdd("Email: "+getEmail());
        Output.outputAdd("Major: "+getDepartment());
        Output.outputAdd("Status: Active");
    }
}

class AcademicMember extends Person{
    private List<Course> teachingClasses=new ArrayList<>();

    public AcademicMember(String ID,String name,String email,String department){
        super(ID,name,email,department);
        newAcademicMember(ID,this);
    }

    public List<Course> getClasses(){return teachingClasses;}

    @Override
    public void enroll(Course course){
        teachingClasses.add(course);
        course.assignTeacher(this);
    }

    @Override
    public void showProps(){
        Output.outputAdd("Faculty ID: "+this.getID());
        Output.outputAdd("Name: "+this.getName());
        Output.outputAdd("Email: "+getEmail());
        Output.outputAdd("Department: "+getDepartment());
    }
}