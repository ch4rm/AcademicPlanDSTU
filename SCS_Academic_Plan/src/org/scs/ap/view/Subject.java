package org.scs.ap.view;

import java.util.ArrayList;

/**
 * Created by Shishko.Arthur on 10.02.2018.
 */
public class Subject {
    private int key_subject_fk;
    private ArrayList<Integer> course_num_sa = new ArrayList<>();
    private ArrayList<Integer> semester_num_sa = new ArrayList<>();
    private ArrayList<Integer> hour_lec_sa = new ArrayList<>();
    private ArrayList<Integer> hour_lab_sa = new ArrayList<>();
    private ArrayList<Integer> hour_prac_sa = new ArrayList<>();
    private ArrayList<Integer> hour_self_sa = new ArrayList<>();

    public Subject(int key_subject_fk){
        this.key_subject_fk=key_subject_fk;
    }
    /** add */
    public void addCourse(int i){course_num_sa.add(i);}
    public void addSemester(int i){semester_num_sa.add(i);}
    public void addLec(int i){hour_lec_sa.add(i);}
    public void addLab(int i){hour_lab_sa.add(i);}
    public void addPrac(int i){hour_prac_sa.add(i);}
    public void addSelf(int i){hour_self_sa.add(i);}
    /** get */
    public int getCourse(int i){return course_num_sa.get(i);}
    public int getSemester(int i){return semester_num_sa.get(i);}
    public int getLec(int i){return hour_lec_sa.get(i);}
    public int getLab(int i){return hour_lab_sa.get(i);}
    public int getPrac(int i){return hour_prac_sa.get(i);}
    public int getSelf(int i){return hour_self_sa.get(i);}

    public int getSizeSemester(){return semester_num_sa.size();}
}
