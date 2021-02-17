package ru.mephi.avito.models;

import lombok.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**client-workspace.t_ads*/
@Data
@Builder
public class Ad implements Serializable {

    private int id;
    private String name;
    private double price;
    private String description;
    private Timestamp date;

}
