package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Student implements Parcelable {

    private String STUDENT_ID , NAME, SURNAME, EMAIL,GRADE, GENDER;
    private Integer ID;

    public Student() {

    }

    public Student(String STUDENT_ID, String NAME, String SURNAME, String EMAIL, String GRADE, String GENDER, Integer ID) {
        this.STUDENT_ID = STUDENT_ID;
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.EMAIL = EMAIL;
        this.GRADE = GRADE;
        this.GENDER = GENDER;
        this.ID = ID;
    }

    protected Student(Parcel in) {
        STUDENT_ID = in.readString();
        NAME = in.readString();
        SURNAME = in.readString();
        EMAIL = in.readString();
        GRADE = in.readString();
        GENDER = in.readString();
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readInt();
        }
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public void setSURNAME(String SURNAME) {
        this.SURNAME = SURNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    static public ArrayList<Student> getData(Context context, String student_id){

        ArrayList <Student> list = new ArrayList<Student>();

        ArrayList<Integer> IdList = new ArrayList<>();
        ArrayList<String> student_idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> surnameList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        ArrayList<String> genderList = new ArrayList<>();
        ArrayList<String> gradeList = new ArrayList<>();


        try{

            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM STUDENT WHERE STUDENT_ID= ?  ", new String[]{student_id});
            int idIndex=cursor.getColumnIndex("ID");
            int nameIndex=cursor.getColumnIndex("NAME");
            int student_idIndex=cursor.getColumnIndex("STUDENT_ID");
            int surnameIndex = cursor.getColumnIndex("SURNAME");
            int emailIndex = cursor.getColumnIndex("EMAIL");
            int genderIndex = cursor.getColumnIndex("GRADE");
            int gradeIndex = cursor.getColumnIndex("GENDER");


            while (cursor.moveToNext()) {
                IdList.add(cursor.getInt(idIndex));
                nameList.add(cursor.getString(nameIndex));
                student_idList.add(cursor.getString(student_idIndex));
                surnameList.add(cursor.getString(surnameIndex));
                emailList.add(cursor.getString(emailIndex));
                genderList.add(cursor.getString(genderIndex));
                gradeList.add(cursor.getString(gradeIndex));

            }
            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Student h = new Student();
                h.setID(IdList.get(i));
                h.setGRADE(gradeList.get(i));
                h.setEMAIL(emailList.get(i));
                h.setGENDER(genderList.get(i));
                h.setSURNAME(surnameList.get(i));
                h.setNAME(nameList.get(i));
                h.setSTUDENT_ID(student_idList.get(i));

                list.add(h);
            }


        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

        dest.writeString(STUDENT_ID);
        dest.writeString(NAME);
        dest.writeString(SURNAME);
        dest.writeString(EMAIL);
        dest.writeString(GENDER);
        dest.writeString(GRADE);

        if (ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ID);
        }
    }
}
