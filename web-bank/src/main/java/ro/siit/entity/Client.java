package ro.siit.entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Client {
    private UUID id;
    private String name;
    private String city;
    private Date birthDate;

    public Client(UUID id, String name, String city, Date birthDate) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.birthDate = birthDate;
    }

    public Client(String name, String city, Date birthDate) {
        this(UUID.randomUUID(), name, city, birthDate);
    }

    @Override
    public String toString() {
        return this.name + " from " + this.city+", ID: "+this.id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public UUID getId() {
        return id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getFormattedBirthDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMMM yyyy");
        return formatter.format(birthDate);
    }
}
