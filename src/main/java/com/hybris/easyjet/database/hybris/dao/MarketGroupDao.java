package com.hybris.easyjet.database.hybris.dao;

import com.hybris.easyjet.database.hybris.models.ExpectedLocalizedDescription;
import com.hybris.easyjet.database.hybris.models.ExpectedLocalizedName;
import com.hybris.easyjet.database.hybris.models.ExpectedMarketGroup;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarketGroupDao extends AbstractDao {

    public List<ExpectedMarketGroup> getListOfAternateAirportsForAMarketGroup() {

        params = new MapSqlParameterSource();

        query = "SELECT l.p_code as code,at.code as type\n"+
                "FROM transportfacility AS tf\n"+
                "JOIN location AS l ON tf.p_location = l.pk\n"+
                "JOIN enumerationvalues AS at\n"+
                "on l.p_locationtype = at.pk\n"+
                "where l.p_active = 1 and tf.p_location = l.pk\n";

        return getResultsList((rs, rowNum) -> new ExpectedMarketGroup(
                rs.getString("code"),
                rs.getString("type")
        ));

    }

    public List<String> getExpectedMarketGroupData(){

        params = new MapSqlParameterSource();

        query = "SELECT *\n"+
                "FROM location AS l\n"+
                "JOIN enumerationvalues AS at\n"+
                "on l.p_locationtype = at.pk\n"+
                "where l.p_active = 1\n";

        return getResultsList((rs, numRow) -> rs.getString("code"));
    }

    public List<ExpectedLocalizedDescription> getMarketGroupLocalizedDescription(String locale){

        params = new MapSqlParameterSource("loc", locale);

        query = "SELECT lp.p_description as description, lan.p_isocode as locale\n"+
                "FROM location AS l\n"+
                "JOIN enumerationvalues AS at\n"+
                "on l.p_locationtype = at.pk\n"+
                "JOIN locationlp as lp\n"+
                "on l.pk = lp.itempk\n"+
                "join languages lan\n"+
                "on lp.langpk = lan.pk\n"+
                "where l.p_active = 1\n"+
                "and lan.p_isocode = :loc;\n";

        return getResultsList((rs,numRow) -> new ExpectedLocalizedDescription(
                rs.getString("description"),
                rs.getString("locale")
        ));
    }

    public List<ExpectedLocalizedName> getMarketGroupLocalizedName(String locale){

        params = new MapSqlParameterSource("loc",locale);

        query = "SELECT lp.p_name as name, lan.p_isocode as locale\n"+
                "FROM location AS l\n"+
                "JOIN enumerationvalues AS at\n"+
                "on l.p_locationtype = at.pk\n"+
                "JOIN locationlp as lp\n"+
                "on l.pk = lp.itempk\n"+
                "join languages lan\n"+
                "on lp.langpk = lan.pk\n"+
                "where l.p_active = 1\n"+
                "and lan.p_isocode = :loc;\n";

        return getResultsList((rs,numRow) -> new ExpectedLocalizedName(
                rs.getString("name"),
                rs.getString("locale")
        ));
    }
}
