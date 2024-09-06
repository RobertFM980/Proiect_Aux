package ro.siit.entity;

import java.util.UUID;

public class Bank {
    private UUID id;
    private String name;

    public Bank(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Bank(String name) {
        this(UUID.randomUUID(), name);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
