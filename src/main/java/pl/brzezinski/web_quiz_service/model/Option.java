package pl.brzezinski.web_quiz_service.model;

import javax.persistence.*;
//
//@Entity
//@Table(name = "options")
public class Option {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;

    public Option() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
