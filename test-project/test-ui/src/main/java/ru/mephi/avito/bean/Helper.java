package ru.mephi.avito.bean;

import ru.mephi.avito.models.Ad;
import ru.mephi.avito.models.Link;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Data
@AllArgsConstructor
public class Helper {

    private Ad ad;
    private Link general;
    private Link first;
    private Link second;

    public static List<Helper> create(List<Link> links, List<Ad> ads){
        List<Helper> helpers = new ArrayList<>(ads.size());
        Link general = null;
        Link first = null;
        Link second = null;
        for (Ad ad : ads){
            for (Link link : links){
                if (link.getAdId() == ad.getId()){
                    if (link.isGeneral()){
                        general = link;
                    } else {
                        if (first == null) {
                            first = link;
                        } else {
                            second = link;
                        }
                    }
                }
                if (general != null && second != null){
                    break;
                }
            }
            helpers.add(new Helper(ad, general, first, second));
            general = null;
            first = null;
            second = null;
        }
        return helpers;
    }
}
