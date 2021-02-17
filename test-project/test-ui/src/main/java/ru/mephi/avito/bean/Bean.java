package ru.mephi.avito.bean;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import ru.mephi.avito.dao.InitialClass;
import ru.mephi.avito.mappers.AdMapper;
import ru.mephi.avito.mappers.LinkMapper;
import ru.mephi.avito.models.Ad;
import ru.mephi.avito.models.Link;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="bean")
@ViewScoped
public class Bean implements Serializable {

    private AdMapper adMapper;
    private LinkMapper linkMapper;
    private static volatile List<Helper> allHelpersInDB;
    private List<Helper> allHelpers;
    private List<Helper> helpers;
    private int numOfDecades;
    private int currentDecade;
    private String msg;

    private Ad newAd;
    private Helper helper;
    private Helper show;

    private int sort;

    public List<Helper> getHelpers() {
        return helpers;
    }

    public String getMsg() {
        return msg;
    }

    public Helper getHelper() {
        return helper;
    }

    public void setHelper(Helper helper) {
        this.helper = helper;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Helper getShow() {
        return show;
    }

    public void setShow(Helper show) {
        this.show = show;
    }

    @PostConstruct
    private void init() {
        try {
            sort = 1;
            adMapper = InitialClass.adMapper;
            linkMapper = InitialClass.linkMapper;
            allHelpersInDB = Helper.create(linkMapper.selectAllLink(), adMapper.selectAllAd());
            allHelpers = allHelpersInDB;
            if (allHelpers.size() > 10) {
                helpers = allHelpers.subList(0, 10);
                numOfDecades = (allHelpers.size() / 10) + 1;
            } else {
                helpers = allHelpers;
                numOfDecades = 1;
            }
            currentDecade = 1;
        } catch (Exception e) {
            msg = "Can't connect with Data Base:(";
            return;
        }
        msg = null;
    }

    public void nextHelpers(){
        if (numOfDecades != 1) {
            if (currentDecade == numOfDecades) {
                currentDecade = 0;
                nextHelpers();
                return;
            }
            if ((currentDecade + 1) != numOfDecades) {
                helpers = allHelpers.subList((++currentDecade - 1) * 10, currentDecade * 10);
            } else {
                helpers = allHelpers.subList((++currentDecade - 1) * 10, allHelpers.size());
            }
        }
    }

    public void update(){
        allHelpers = allHelpersInDB;
    }

    public void previewHelpers(){
        if (numOfDecades != 1) {
            if (currentDecade == 1) {
                currentDecade = numOfDecades + 1;
                previewHelpers();
                return;
            }
            if ((currentDecade - 1) != numOfDecades) {
                helpers = allHelpers.subList((--currentDecade - 1) * 10, currentDecade * 10);
            } else {
                helpers = allHelpers.subList((--currentDecade - 1) * 10, allHelpers.size());
            }
        }
    }

    public void sort() {
        if (sort == 1) {
            allHelpers.sort(Comparator.comparingDouble(o -> o.getAd().getPrice()));
        }
        if (sort == 2){
            Comparator<Helper> comparator = Comparator.comparingDouble(o -> o.getAd().getPrice());
            allHelpers.sort(comparator.reversed());
        }
        if (sort == 3){
            allHelpers.sort(Comparator.comparing(o -> o.getAd().getDate()));
        }
        if (sort == 4) {
            Comparator<Helper> comparator = Comparator.comparing(o -> o.getAd().getDate());
            allHelpers.sort(comparator.reversed());
        }
        currentDecade = 0;
        nextHelpers();
    }

    public void prepareForInsert(){
        helper = new Helper(Ad.builder().build(), Link.builder().build(), Link.builder().build(), Link.builder().build());
    }

    public void insertHelper(){
        helper.getAd().setDate(new java.sql.Timestamp((new Date()).getTime()));
        adMapper.insertAd(helper.getAd());

        helper.getGeneral().setAdId(helper.getAd().getId());
        helper.getGeneral().setGeneral(true);
        if (helper.getFirst() != null) {
            helper.getFirst().setAdId(helper.getAd().getId());
        }
        if (helper.getSecond() != null) {
            helper.getSecond().setAdId(helper.getAd().getId());
            if (helper.getFirst() == null) {
                helper.setFirst(helper.getSecond());
                helper.setSecond(null);
            }
        }

        linkMapper.insertLink(helper.getGeneral());
        if (helper.getFirst() != null) {
            linkMapper.insertLink(helper.getFirst());
        }
        if (helper.getSecond() != null) {
            linkMapper.insertLink(helper.getSecond());
        }
        allHelpersInDB.add(helper);
        update();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Изменения приняты! id вашего объявления " + helper.getAd().getId()));
        if (allHelpers.size() > 10) {
            numOfDecades = (allHelpers.size() / 10) + 1;
        }
        currentDecade = numOfDecades - 1;
        nextHelpers();
        helper = new Helper(Ad.builder().build(), Link.builder().build(), Link.builder().build(), Link.builder().build());
    }
}
