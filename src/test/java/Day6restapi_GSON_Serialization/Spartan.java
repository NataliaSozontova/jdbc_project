package Day6restapi_GSON_Serialization;

/*
{
    "id": 15,
    "name": "Meta",
    "gender": "Female",
    "phone": 1938695106
}
 */
public class Spartan {
    private Integer id;
    private String name;
    private String  gender;
    private Long phone;

    public Spartan(){

    }
    public Spartan(Integer id, String name, String gender, Long phone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
