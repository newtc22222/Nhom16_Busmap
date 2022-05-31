package hcmute.nhom16.busmap.model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private String phone;
    private boolean gender;
    private Date dob;
    private String email;
    private String password;
    private byte[] image;

    public User(int id, String name, String phone, boolean gender, Date dob, String email, String password, byte[] image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

}
