package com.example.attendancesystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.attendancesystem.models.Attendance;
import com.example.attendancesystem.models.Courses;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends android.database.sqlite.SQLiteOpenHelper {

    Context context;
    private static final String DATABASE_NAME = "AttendanceDatabase.sqlite";
    private static final int VERSION = 1;

    public DatabaseHelper(Context c) {
        super(c, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create Table IF NOT EXISTS STAFF_LOGIN(ID INTEGER PRIMARY KEY,STAFF_ID VARCHAR UNIQUE, PASSWORD VARCHAR);");
        db.execSQL("create Table IF NOT EXISTS STUDENT_LOGIN(ID INTEGER PRIMARY KEY,STUDENT_ID VARCHAR UNIQUE, PASSWORD VARCHAR);");
        db.execSQL("create Table IF NOT EXISTS STUDENT(ID INTEGER PRIMARY KEY,STUDENT_ID VARCHAR UNIQUE, NAME VARCHAR,SURNAME VARCHAR,EMAIL VARCHAR,GRADE INTEGER,GENDER VARCHAR);");
        db.execSQL("create Table IF NOT EXISTS STAFF(ID INTEGER PRIMARY KEY,STAFF_ID VARCHAR UNIQUE, NAME VARCHAR,SURNAME VARCHAR,EMAIL VARCHAR,GENDER VARCHAR,DEPARTMENT VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS COURSES(ID INTEGER PRIMARY KEY, COURSE_CODE VARCHAR UNIQUE, COURSE_NAME VARCHAR, GRADE VARCHAR, SEMESTER VARCHAR, CLASSROOM VARCHAR, TIME VARCHAR, TOTAL_TIME VARCHAR, STAFF_ID VARCHAR, FOREIGN KEY (STAFF_ID) REFERENCES STAFF(STAFF_ID));");
        db.execSQL("create Table IF NOT EXISTS QR(ID INTEGER PRIMARY KEY,QR_DATA TEXT UNIQUE, SESSION_ID VARCHAR,COURSE_ID VARCHAR,STUDENT_ID VARCHAR);");
        db.execSQL("create Table IF NOT EXISTS REPORTS(ID INTEGER PRIMARY KEY,REPORT BLOB,REPORT_NAME VARCHAR,CREATION_TIME VARCHAR ,CREATION_DATE VARCHAR,STAFF_ID VARCHAR);");
        db.execSQL("create Table IF NOT EXISTS NOTES(ID INTEGER PRIMARY KEY,NOTES VARCHAR, TITLE VARCHAR,CREATION_DATE VARCHAR,USER_ID VARCHAR);");
        db.execSQL("CREATE TABLE IF NOT EXISTS ATTENDANCE(ID INTEGER PRIMARY KEY AUTOINCREMENT, ATTENDANCE_ID VARCHAR UNIQUE, SESSION_ID VARCHAR, STATUS VARCHAR(1) CHECK (STATUS IN ('P', 'A')), STUDENT_ID VARCHAR, COURSES_ID VARCHAR, FOREIGN KEY (STUDENT_ID) REFERENCES STUDENT(STUDENT_ID), FOREIGN KEY (COURSES_ID) REFERENCES COURSES(COURSE_CODE));");
        db.execSQL("CREATE TABLE IF NOT EXISTS SESSIONS(ID INTEGER PRIMARY KEY AUTOINCREMENT, SESSION_ID VARCHAR UNIQUE, CREATION_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP, STAFF_ID VARCHAR, COURSE_ID VARCHAR, FOREIGN KEY (STAFF_ID) REFERENCES STAFF(STAFF_ID), FOREIGN KEY (COURSE_ID) REFERENCES COURSES(COURSE_CODE));");
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT_COURSES ( ID INTEGER PRIMARY KEY AUTOINCREMENT,STUDENT_ID VARCHAR(50) NOT NULL,COURSE_CODE VARCHAR(10) NOT NULL,COURSE_NAME VARCHAR(100),GRADE VARCHAR(2), ATTENDANCE INT,SEMESTER VARCHAR(10),CLASSROOM VARCHAR(50),TOTAL_TIME INT,STAFF_ID VARCHAR(50),FOREIGN KEY (STUDENT_ID) REFERENCES Students(ID),FOREIGN KEY (COURSE_CODE) REFERENCES Courses(CODE), FOREIGN KEY (STAFF_ID) REFERENCES Staff(ID))");
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists STAFF_LOGIN");
        db.execSQL("drop Table if exists STUDENT_LOGIN");
        db.execSQL("drop Table if exists STUDENT");
        db.execSQL("drop Table if exists STAFF");
        db.execSQL("drop Table if exists COURSES");
        db.execSQL("drop Table if exists QR");
        db.execSQL("drop Table if exists REPORTS");
        db.execSQL("drop Table if exists NOTES");
        db.execSQL("drop Table if exists ATTENDANCE");
        db.execSQL("drop Table if exists SESSIONS");
        db.execSQL("drop Table if exists STUDENT_COURSES");

    }

    public boolean insertAttendance(Attendance attendance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSION_ID", attendance.getSession_id());
        contentValues.put("ATTENDANCE_ID", attendance.getAttendance_id());
        contentValues.put("STATUS", attendance.getStatus());
        contentValues.put("STUDENT_ID", attendance.getStudent_id());
        contentValues.put("COURSES_ID", attendance.getCourse_id());
        contentValues.put("ATTENDANCE_DATE", attendance.getAttendance_date());
        long result = db.insert("ATTENDANCE", null, contentValues);
        return result != -1;
    }

    public ArrayList<String> getStudentsNotScanned(String sessionId) {
        ArrayList<String> studentIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT STUDENT_ID FROM STUDENTS WHERE STUDENT_ID NOT IN (SELECT STUDENT_ID FROM ATTENDANCE WHERE SESSION_ID = ?)", new String[]{sessionId});
        if (cursor.moveToFirst()) {
            do {
                studentIds.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return studentIds;
    }

    public int countAbsences(String studentId, String courseId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM ATTENDANCE a WHERE a.STUDENT_ID = ? AND a.COURSES_ID = ? AND a.STATUS = 'A'", new String[]{studentId, courseId});
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
    public Boolean checkusernamepassword(String staff_id, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM STAFF_LOGIN WHERE STAFF_ID = ? AND PASSWORD = ?", new String[] {staff_id,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword2(String student_id, String password){
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM STUDENT_LOGIN WHERE STUDENT_ID = ? AND PASSWORD = ?", new String[] {student_id,password});

        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean deleteNote(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete("NOTES", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return deletedRows > 0;
    }

    public boolean insertNote(String title, String content, String creationDate, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", title);
        contentValues.put("NOTES", content);
        contentValues.put("CREATION_DATE", creationDate);
        contentValues.put("USER_ID", userId);

        long result = db.insert("NOTES", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean updateNote(int id, String title, String content, String creationDate, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TITLE", title);
        contentValues.put("NOTES", content);
        contentValues.put("CREATION_DATE", creationDate);
        contentValues.put("USER_ID", userId);

        int rowsAffected = db.update("NOTES", contentValues, "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return rowsAffected > 0;
    }

    public byte[] getPdfFile(String reportName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT REPORT FROM reports WHERE REPORT_NAME = ?";
        Cursor cursor = db.rawQuery(query, new String[]{reportName});

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("REPORT");
            if (columnIndex == -1) {
                // Column not found
                Log.e("DatabaseHelper", "Failed to find 'REPORT' column");
                cursor.close();
                db.close();
                return null;
            }
            byte[] pdfBytes = cursor.getBlob(columnIndex);
            cursor.close();
            db.close();
            return pdfBytes;
        } else {
            cursor.close();
            db.close();
            return null;
        }
    }

    public boolean createSession(String session_id, String staff_id, String creationDate, String course_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SESSION_ID", session_id);
        contentValues.put("CREATION_TIME", creationDate);
        contentValues.put("COURSE_ID", course_id);
        contentValues.put("STAFF_ID", staff_id);

        long result = db.insert("SESSIONS", null, contentValues);
        db.close();
        return result != -1;
    }

    public Boolean isExistSession(String session_code){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT SESSION_ID FROM SESSIONS WHERE SESSION_ID = ? ", new String[] {session_code});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor getAllAttandance(String session_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT s.STUDENT_ID, " +
                "CASE WHEN a.STATUS = 'A' THEN 'Absence' WHEN a.STATUS = 'P' THEN 'Present' ELSE 'Unknown' END AS ATTENDANCE_STATUS " +
                "FROM student s INNER JOIN ATTENDANCE a ON s.STUDENT_ID = a.STUDENT_ID WHERE a.SESSION_ID = ? ", new String[] {session_id});
        return cursor;
    }

    public  ArrayList<String> getAllAbsenceStd(String session_id) {
        ArrayList<String> presentStudentIds = new ArrayList<>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT s.STUDENT_ID FROM STUDENT s INNER JOIN ATTENDANCE a ON s.STUDENT_ID = a.STUDENT_ID WHERE a.STATUS ='A' AND a.SESSION_ID = ? ", new String[] {session_id});

        if (cursor.moveToFirst()) {
            do {
                String studentId = cursor.getString(0);
                presentStudentIds.add(studentId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return presentStudentIds;
    }

    public ArrayList<String> getAllPresentStd(String session_id) {
        ArrayList<String> presentStudentIds = new ArrayList<>();
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT s.STUDENT_ID FROM STUDENT s INNER JOIN ATTENDANCE a ON s.STUDENT_ID = a.STUDENT_ID WHERE a.STATUS ='P' AND a.SESSION_ID = ? ", new String[] {session_id});


        if (cursor.moveToFirst()) {
            do {
                String studentId = cursor.getString(0);
                presentStudentIds.add(studentId);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return presentStudentIds;
    }

    public Double  percentageParticipation(String session_id) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        double attendancePercentage = 0.0;
        Cursor cursor = MyDB.rawQuery("SELECT (SUM(CASE WHEN a.STATUS = 'P' THEN 1 ELSE 0 END) * 100.0 / COUNT(s.STUDENT_ID)) AS attendance_percentage FROM "
                + "student s LEFT JOIN  ATTENDANCE a ON s.STUDENT_ID = a.STUDENT_ID WHERE a.SESSION_ID = ? ", new String[] {session_id});

        if (cursor.moveToFirst()) {
            int attendancePercentageIndex = cursor.getColumnIndex("attendance_percentage");
            if (attendancePercentageIndex != -1) {
                attendancePercentage = cursor.getDouble(attendancePercentageIndex);
            } else {
                return null;
            }
        }

        cursor.close();
        return attendancePercentage;
    }

    public boolean insertReport(String reportName, byte[] report, String creationTime, String creationDate, String staffId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("REPORT_NAME", reportName);
        contentValues.put("REPORT", report);
        contentValues.put("CREATION_TIME", creationTime);
        contentValues.put("CREATION_DATE", creationDate);
        contentValues.put("STAFF_ID", staffId);

        long result = db.insert("REPORTS", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean insertData(String qrData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("COLUMN_NAME", qrData);
        long result = db.insert("TABLE_NAME", null, contentValues);
        return result != -1;
    }
}
