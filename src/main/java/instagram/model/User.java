package instagram.model;

import javax.persistence.*;


@Entity
@Table(name = "users")
public class User {

    @Column
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Integer id;
    @Column
    private  String name;
    @Column
    private  String surname;
    @Column
    private  String username;
    @Column
    private  String mail;
    @Column
    private  String password;
    @Column
    private  String gender;
    @Column
    private  String birthdate;
    @Column
    private  String phone_number;
    @Column
    private  String description;
    @Column
    private  String profile_photo;
    @Column
    private  int number_followers;
    @Column
    private  int number_follow;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public int getNumber_followers() {
        return number_followers;
    }

    public void setNumber_followers(int number_followers) {
        this.number_followers = number_followers;
    }

    public int getNumber_follow() {
        return number_follow;
    }

    public void setNumber_follow(int number_follow) {
        this.number_follow = number_follow;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", description='" + description + '\'' +
                ", profile_photo='" + profile_photo + '\'' +
                ", number_followers=" + number_followers +
                ", number_follow=" + number_follow +
                '}';
    }
}
