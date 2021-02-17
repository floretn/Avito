package ru.mephi.avito.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import ru.mephi.avito.models.Link;
import java.util.List;

public interface LinkMapper {

    @Select("select lnk_pk, lnk_ad_fk, lnk_value, lnk_gen from client_workspace.t_links where lnk_pk = #{id}")
    Link selectLink(int id);

    @Select("select lnk_pk, lnk_ad_fk, lnk_value, lnk_gen from client_workspace.t_links")
    List<Link> selectAllLink();

    @Insert("insert into client_workspace.t_links (lnk_ad_fk, lnk_value, lnk_gen)\n" +
            "values (#{link.adId}, #{link.value}, #{link.general});\n" +
            "commit;")
    @SelectKey(statement = "SELECT LASTVAL()", keyProperty = "link.id", before = false, resultType = Integer.class)
    void insertLink(@Param("link") Link link);
}
