package ru.mephi.avito.models;

import lombok.*;
import java.io.Serializable;

/**client-workspace.t_links*/
@Data
@Builder
public class Link implements Serializable {

    private int id;
    private int adId;
    private String value;
    private boolean general;

}
