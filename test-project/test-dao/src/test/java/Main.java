import ru.mephi.avito.dao.InitialClass;
import ru.mephi.avito.models.Ad;
import java.sql.Timestamp;

public class Main {

    public static void main(String[] args) {
        InitialClass.adMapper.insertAd(Ad.builder().name("Test Name").price(123.32).description("Test Description").
                date(new Timestamp(new java.util.Date().getTime())).build());
    }
}
