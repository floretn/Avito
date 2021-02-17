package ru.mephi.avito.mappers;

import org.apache.ibatis.annotations.*;
import ru.mephi.avito.models.Ad;

import java.util.List;

public interface AdMapper {

    @Select("select ad_pk, ad_name, ad_price, ad_dsc, ad_date from client_workspace.t_ads where ad_pk = #{id}")
    Ad selectAd(int id);

    @Select("select ad_pk, ad_name, ad_price, ad_dsc, ad_date from client_workspace.t_ads")
    List<Ad> selectAllAd();

    @Insert("insert into client_workspace.t_ads (ad_name, ad_price, ad_dsc, ad_date)\n" +
            "values (#{ad.name}, #{ad.price}, #{ad.description}, #{ad.date});\n" +
            "commit;")
    @SelectKey(statement = "SELECT LASTVAL()", keyProperty = "ad.id", before = false, resultType = Integer.class)
    void insertAd(@Param("ad") Ad ad);
}
