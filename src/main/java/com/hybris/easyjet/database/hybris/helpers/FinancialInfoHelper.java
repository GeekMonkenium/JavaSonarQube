package com.hybris.easyjet.database.hybris.helpers;

import com.hybris.easyjet.database.hybris.dao.AbstractDao;
import com.hybris.easyjet.database.hybris.models.FinancialInfoModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Repository
public class FinancialInfoHelper extends AbstractDao {

    private SqlRowSet results;

    private FinancialInfoModel.FinancialInfoEntryModel getFinancialInfoEntry(String item) {
        params = new MapSqlParameterSource("item", item);
        query =
                "SELECT p_code\n" +
                        "	, p_amount\n" +
                        "	, p_isocode\n" +
                        "	, p_spotrate\n" +
                        "	, p_margin\n" +
                        "FROM financialinfoentry AS fie\n" +
                        "	INNER JOIN (\n" +
                        "		SELECT pk AS cPk\n" +
                        "			, p_isocode\n" +
                        "		FROM currencies\n" +
                        "	) AS c ON c.cPk = fie.p_currency\n" +
                        "WHERE pk = :item;";

        return getResult((rs, rowNum) -> new FinancialInfoModel.FinancialInfoEntryModel(
                rs.getString("p_code"),
                rs.getDouble("p_amount"),
                rs.getString("p_isocode"),
                rs.getDouble("p_spotrate"),
                rs.getDouble("p_margin")
        ));
    }

    private List<FinancialInfoModel.FinancialInfoEntryModel> getFinancialInfo(String financialInfo) {
        List<FinancialInfoModel.FinancialInfoEntryModel> financialItems = new ArrayList<>();
        List<String> items = new ArrayList<>();
        String itemsList = results.getString(financialInfo);
        if (StringUtils.isNotBlank(itemsList)) {
            items.addAll(Arrays.asList(itemsList.substring(4).split(",")));
            items.removeIf(String::isEmpty);
            for (String item : items) {
                financialItems.add(getFinancialInfoEntry(item));
            }
        }

        return financialItems;
    }

    private FinancialInfoModel.FinancialItemModel getItemFinancialInfo() {
        String itemCode = results.getString("productId");

        FinancialInfoModel.FinancialInfoEntryModel itemBasePrice = getFinancialInfoEntry(results.getString("itemBasePrice"));
        assertThat(itemBasePrice)
                .withFailMessage(itemCode + " price is not stored")
                .isNotNull();

        itemBasePrice.setCode(itemCode);

        List<FinancialInfoModel.FinancialInfoEntryModel> itemTaxes = getFinancialInfo("itemTaxes");

        List<FinancialInfoModel.FinancialInfoEntryModel> itemFees = getFinancialInfo("itemFees");

        List<FinancialInfoModel.FinancialInfoEntryModel> itemDiscounts = getFinancialInfo("itemDiscounts");

        List<FinancialInfoModel.FinancialInfoEntryModel> itemPromotions = getFinancialInfo("itemPromotions");

        return new FinancialInfoModel.FinancialItemModel(
                itemCode
                , itemBasePrice
                , itemTaxes
                , itemFees
                , itemDiscounts
                , itemPromotions
        );
    }

    private List<FinancialInfoModel.FinancialItemModel> getPassengerItems() {
        List<FinancialInfoModel.FinancialItemModel> items = new ArrayList<>();
        String passenger = results.getString("passengerCode");
        do {
            items.add(getItemFinancialInfo());
        } while (results.next() && results.getString("passengerCode").equals(passenger));

        return items;
    }

    private FinancialInfoModel.PassengerFinancialModel getPassengerFinancialInfo() {
        return new FinancialInfoModel.PassengerFinancialModel(
                results.getString("passengerCode")
                , results.getString("passengerType")
                , results.getString("sector")
                , getPassengerItems()
        );
    }

    private List<FinancialInfoModel.PassengerFinancialModel> getPassengersFinancialInfo() {
        List<FinancialInfoModel.PassengerFinancialModel> passengerList = new ArrayList<>();

        do {
            passengerList.add(getPassengerFinancialInfo());
        } while (!results.isAfterLast());

        return passengerList;
    }

    public FinancialInfoModel getFinancialInfoModel(SqlRowSet results) {
        this.results = results;
        if (results.next()) {
            return new FinancialInfoModel(
                    getFinancialInfo("orderTaxes")
                    , getFinancialInfo("orderFees")
                    , getFinancialInfo("orderPromotions")
                    , getPassengersFinancialInfo()
            );
        } else {
            return null;
        }
    }

    public List<FinancialInfoModel.FinancialInfoEntryModel> getTransactionFinancialInfo(SqlRowSet results) {
        List<FinancialInfoModel.FinancialInfoEntryModel> financialItems = new ArrayList<>();
        if (results.next()) {
            do {
                FinancialInfoModel.FinancialInfoEntryModel item = getFinancialInfoEntry(results.getString("firstDepartureEquivalent"));
                item.setCode(results.getString("transactionId"));
                financialItems.add(item);
            } while (results.next());
            return financialItems;
        } else {
            return null;
        }
    }

}