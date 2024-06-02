package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

public class Staff implements Parcelable {

    private String STAFF_ID , NAME, SURNAME, EMAIL, GENDER,DEPARTMENT;
    private Integer ID;

    public Staff() {

    }

    public Staff(String STAFF_ID, String NAME, String SURNAME, String EMAIL, String GENDER, String DEPARTMENT, Integer ID) {
        this.STAFF_ID = STAFF_ID;
        this.NAME = NAME;
        this.SURNAME = SURNAME;
        this.EMAIL = EMAIL;
        this.GENDER = GENDER;
        this.DEPARTMENT = DEPARTMENT;
        this.ID = ID;
    }

    public String getSTAFF_ID() {
        return STAFF_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public String getSURNAME() {
        return SURNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getGENDER() {
        return GENDER;
    }

    public String getDEPARTMENT() {
        return DEPARTMENT;
    }

    public Integer getID() {
        return ID;
    }

    public void setSTAFF_ID(String STAFF_ID) {
        this.STAFF_ID = STAFF_ID;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public void setSURNAME(String SURNAME) {
        this.SURNAME = SURNAME;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public void setDEPARTMENT(String DEPARTMENT) {
        this.DEPARTMENT = DEPARTMENT;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    static public ArrayList<Staff> getData(Context context,String staff_id){

        ArrayList <Staff> list = new ArrayList<Staff>();

        ArrayList<Integer> IdList = new ArrayList<>();
        ArrayList<String> staff_idList = new ArrayList<>();
        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> surnameList = new ArrayList<>();
        ArrayList<String> emailList = new ArrayList<>();
        ArrayList<String> genderList = new ArrayList<>();
        ArrayList<String> departmentList = new ArrayList<>();


        try{

            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM STAFF WHERE STAFF_ID= ?  ", new String[]{staff_id});
            int idIndex=cursor.getColumnIndex("ID");
            int nameIndex=cursor.getColumnIndex("NAME");
            int staff_idIndex=cursor.getColumnIndex("STAFF_ID");
            int surnameIndex = cursor.getColumnIndex("SURNAME");
            int emailIndex = cursor.getColumnIndex("EMAIL");
            int genderIndex = cursor.getColumnIndex("GENDER");
           int departmentIndex = cursor.getColumnIndex("DEPARTMENT");


            while (cursor.moveToNext()) {
                IdList.add(cursor.getInt(idIndex));
                nameList.add(cursor.getString(nameIndex));
                staff_idList.add(cursor.getString(staff_idIndex));
                surnameList.add(cursor.getString(surnameIndex));
                emailList.add(cursor.getString(emailIndex));
                genderList.add(cursor.getString(genderIndex));
                departmentList.add(cursor.getString(departmentIndex));

            }
            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Staff h = new Staff();
                h.setID(IdList.get(i));
                h.setDEPARTMENT(departmentList.get(i));
                h.setEMAIL(emailList.get(i));
                h.setGENDER(genderList.get(i));
                h.setSURNAME(surnameList.get(i));
                h.setNAME(nameList.get(i));
                h.setSTAFF_ID(staff_idList.get(i));

                list.add(h);
            }


        } catch (Exception e) {
        }
        return list;
    }

    protected Staff(Parcel in) {
        STAFF_ID = in.readString();
        NAME = in.readString();
        SURNAME = in.readString();
        EMAIL = in.readString();
        GENDER = in.readString();
        DEPARTMENT = in.readString();
        if (in.readByte() == 0) {
            ID = null;
        } else {
            ID = in.readInt();
        }
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel in) {
            return new Staff(in);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(STAFF_ID);
        dest.writeString(NAME);
        dest.writeString(SURNAME);
        dest.writeString(EMAIL);
        dest.writeString(GENDER);
        dest.writeString(DEPARTMENT);
        if (ID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ID);
        }
    }
}
