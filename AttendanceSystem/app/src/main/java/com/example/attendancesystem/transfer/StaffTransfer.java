package com.example.attendancesystem.transfer;

public class StaffTransfer {

    private Integer id;
    private String staff_id,staff_name,surname,staff_email,gender,department;

    public StaffTransfer(Integer id,String staff_id, String staff_name, String surname, String staff_email, String gender, String department) {
        this.id = id;
        this.staff_name = staff_name;
        this.surname = surname;
        this.staff_email = staff_email;
        this.gender = gender;
        this.department = department;
        this.staff_id=staff_id;
    }

    public StaffTransfer() {
    }

    public Integer getId() {
        return id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getSurname() {
        return surname;
    }

    public String getStaff_email() {
        return staff_email;
    }

    public String getGender() {
        return gender;
    }

    public String getDepartment() {
        return department;
    }
    public String getStaff_id() {
        return staff_id;
    }

}
