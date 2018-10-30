package com.hybris.easyjet.database.hybris.dao;


import com.hybris.easyjet.TenantBeanFactoryPostProcessor;
import com.hybris.easyjet.config.SerenityFacade;
import com.jolbox.bonecp.BoneCPDataSource;
import javafx.util.Pair;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractDao {

    private static final Logger LOG = LogManager.getLogger(AbstractDao.class);

    /**
     * This class is responsible of parsing simple query based on sql, in order to run it with both sqlserver and mysql driver
     * We assume the only scope of the driver for mysql for local environment
     * We assume that the environment local should be mysql as database
     */
    private static final String LOCAL = "local";
    private static final String ENVIRONMENT = System.getProperty("environment");
    private static final Map<SQLFunction, Pair> functionSQLServer = new EnumMap<>(SQLFunction.class);
    private static final Map<SQLFunction, Pair> functionMySQL = new EnumMap<>(SQLFunction.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    protected SerenityFacade testData;

    protected String query;
    protected SqlParameterSource params;

    @SuppressWarnings("unchecked")
    protected AbstractDao() {
        BoneCPDataSource dataSource = (BoneCPDataSource) TenantBeanFactoryPostProcessor.getFactory().getBean("hybrisDataSource");
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        functionSQLServer.put(SQLFunction.CURRENT_TIME, new Pair<>(" GETDATE() ", "(?i)getdate\\(\\s*\\)"));
        functionSQLServer.put(SQLFunction.LIMIT_RESULT, new Pair<>(" TOP ", "(?i)top\\s*\\(?(\\s*[0-9]+\\s*)\\)?"));
        functionSQLServer.put(SQLFunction.CONVERT_DATE, new Pair<>(" CONVERT(VARCHAR(10), CAST(* AS DATE), 105)  ", "(?i)CONVERT\\s*\\(\\s*VARCHAR\\s*\\(\\s*\\d+\\s*\\)\\s*,\\s*CAST\\s*\\((\\s*\\w+\\s*)\\s+AS\\s+\\w+\\s*\\)\\s*,\\s*\\d+\\s*\\)\\s*"));
        functionSQLServer.put(SQLFunction.INTERVAL, new Pair<>(" BETWEEN DATEADD(day, 1, GETDATE()) AND DATEADD(month, 10, GETDATE()) ", "BETWEEN DATEADD\\(day, 1, GETDATE\\(\\)\\) AND DATEADD\\(month, 10, GETDATE\\(\\)\\)"));

        functionMySQL.put(SQLFunction.CURRENT_TIME, new Pair<>(" NOW() ", "(?i)now\\(\\s*\\)"));
        functionMySQL.put(SQLFunction.LIMIT_RESULT, new Pair<>(" LIMIT ", "(?i)limit\\s+(\\d+)\\s*;?"));
        functionMySQL.put(SQLFunction.CONVERT_DATE, new Pair<>(" DATE_FORMAT(CAST(* AS DATE), '%d-%m-%Y') ", "(?i)DATE_FORMAT\\s*\\(\\s*CAST\\s*\\((\\s*\\w+\\s*)\\s+AS\\s+\\w+\\s*\\)\\s*,\\s*\\'%d-%m-%Y'\\s*\\)\\s*"));
        functionMySQL.put(SQLFunction.INTERVAL, new Pair<>(" BETWEEN DATE_ADD(NOW(), INTERVAL 1 DAY) AND DATE_ADD(NOW(), INTERVAL 10 MONTH) ", "BETWEEN DATE_ADD\\(NOW\\(\\), INTERVAL 1 DAY\\) AND DATE_ADD\\(NOW\\(\\), INTERVAL 10 MONTH\\)"));
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFromSpring(Class<T> className) {
        return TenantBeanFactoryPostProcessor.getFactory().getBean(className);
    }

    /**
     * Parse the local query related to the environment [local, stable, cyhourly, cynightly]
     *
     * @param aLocalQuery query to parse before calling jdbcTemplate.query[..]
     * @return return converte query
     */
    private String parse(String aLocalQuery) {
        String myQuery;
        try {
            myQuery = aLocalQuery;
            //## remove square bracket (only for local use)
            myQuery = removeSquareBracket(myQuery);
            //## remove dbo syntax (only for local use)
            myQuery = removeDBOSynatax(myQuery);
            //## convert sql function; at the moment the support is limited just to sqlserver and mysql
            myQuery = convertFunctionForSQLGrammar(myQuery);

            return myQuery;
        } catch (Exception e) {
            LOG.error("Error while parsing local query! Return same value");
            return aLocalQuery;
        }
    }

    /**
     * Match a regex in a string source
     *
     * @param regex  to match
     * @param source string
     * @return result of campairison
     */
    private boolean matchResult(String regex, String source) {
        Matcher m = Pattern.compile(regex).matcher(source);
        return m.find();
    }

    private int getGroupMatch(String regex, String source) {
        Matcher m = Pattern.compile(regex).matcher(source);
        int count = 0;
        while (m.find())
            count++;

        return count;
    }

    /**
     * Return as value a number inside a string.
     * At the moment the full match is garanted just for the first occurance of a value
     *
     * @param regex  to match
     * @param source string
     * @return the value
     */
    private int getValue(String regex, String source) {
        Matcher m = Pattern.compile(regex).matcher(source);
        return m.find() ? Integer.parseInt(m.group(1)) : -1;
    }

    /**
     * Return a particular word inside a regex.
     * At the moment it require the word you want to match and build the regex to recognize the word as a particular group
     *
     * @param regex  to match
     * @param source string
     * @return a string matcheing the regex
     */
    private String getValueAsString(String regex, String source) {
        Matcher m = Pattern.compile(regex).matcher(source);
        return m.find() ? m.group(1) : "";
    }

    /**
     * Replace a word inside a regex
     *
     * @param regex       to match
     * @param source      string
     * @param replacement word to raplace inside regex
     * @return update the given string
     */
    private String replace(String regex, String source, String replacement) {
        return source.replaceAll(regex, replacement);
    }

    /**
     * Replace the first occurrence of a word inside a regex
     *
     * @param regex       to match
     * @param source      string
     * @param replacement word to raplace inside regex
     * @return update the given string
     */
    private String replaceFirst(String regex, String source, String replacement) {
        return source.replaceFirst(regex, replacement);
    }

    /**
     * Remove square bracket to run under mysql (local env)
     *
     * @return query without square bracket
     */
    private String removeSquareBracket(String aQuery) {
        return ENVIRONMENT.equals(LOCAL) ? aQuery.replace("[", "").replace("]", "") : aQuery;
    }

    /**
     * Remove 'dbo.' syntax to run under mysql (local env)
     *
     * @return query without 'dbo.' syntax
     */
    private String removeDBOSynatax(String aQuery) {
        return ENVIRONMENT.equals(LOCAL) ? aQuery.replace("dbo.", "") : aQuery;
    }

    /**
     * Convert the grammar of the query based on sql running (mysql or sqlserver)
     *
     * @return query parsed for different environemnt
     */
    private String convertFunctionForSQLGrammar(String aQuery) {

        Map<SQLFunction, Pair> functionAnalizer;
        Map<SQLFunction, Pair> oppositeFuncition;
        Pair item;
        String convertedQuery = aQuery;

        if (ENVIRONMENT.equals(LOCAL)) {
            functionAnalizer = functionSQLServer;
            oppositeFuncition = functionMySQL;
        } else {
            functionAnalizer = functionMySQL;
            oppositeFuncition = functionSQLServer;
        }

        for (SQLFunction key : functionAnalizer.keySet()) {
            item = functionAnalizer.get(key);

            if (matchResult((String) item.getValue(), convertedQuery)) {
                switch (key) {
                    case LIMIT_RESULT: //## replace the function LIMIT, TOP
                        int getValue = getValue((String) item.getValue(), convertedQuery);
                        if (ENVIRONMENT.equals(LOCAL)) {
                            String replacement = (String) oppositeFuncition.get(key).getKey() + getValue + ";";
                            convertedQuery = replace((String) item.getValue(), convertedQuery, " ");
                            convertedQuery = convertedQuery.contains(";") ? replace(";", convertedQuery, replacement) : (convertedQuery + " " + replacement);
                        } else {
                            String regexString = "(?i)select\\s*";
                            String replacement = "SELECT " + oppositeFuncition.get(key).getKey() + "(" + getValue + ") ";
                            convertedQuery = replace(regexString, convertedQuery, replacement);
                            convertedQuery = replace((String) item.getValue(), convertedQuery, ";");
                        }
                        break;
                    case CURRENT_TIME: //## replace the function NOW(), GETDATE()
                        convertedQuery = replace((String) item.getValue(), convertedQuery, (String) oppositeFuncition.get(key).getKey());
                        break;
                    case CONVERT_DATE: //## replace the function CONVERT, DATEFORMAT
                        int groupMatch = getGroupMatch((String) item.getValue(), convertedQuery);
                        for (int i = 1; i <= groupMatch; i++) {
                            String columnValue = getValueAsString((String) item.getValue(), convertedQuery);
                            String replacement = ((String) oppositeFuncition.get(key).getKey()).replace("*", columnValue) + " ";
                            convertedQuery = replaceFirst((String) item.getValue(), convertedQuery, replacement);
                        }
                        break;
                    case INTERVAL: //## replace the function BETWEEN date AND date
                        convertedQuery = replace((String) item.getValue(), convertedQuery, (String) oppositeFuncition.get(key).getKey());
                        break;
                }
            }
        }

        return convertedQuery;
    }

    protected <T> T getRandomItem(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(new Random().nextInt(list.size()));
    }

    protected void updateTable() {
        this.jdbcTemplate.update(parse(query), params);
    }

    protected SqlRowSet getRowSet() {
        return this.jdbcTemplate.queryForRowSet(parse(query), params);
    }

    protected <T> List<T> getResultsList(RowMapper<T> rowMapper) {
        return jdbcTemplate.query(parse(query), params, rowMapper);
    }

    /**
     * @deprecated should avoid to assume the first result is the right one
     */
    @Deprecated
    protected <T> T getFirstResult(Class<T> tClass) {
        List<T> list = jdbcTemplate.queryForList(parse(query), params, tClass);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @deprecated should avoid to assume the first result is the right one
     */
    @Deprecated
    protected <T> T getFirstResult(RowMapper<T> rowMapper) {
        List<T> list = jdbcTemplate.query(parse(query), params, rowMapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    protected <T> T getRandomResult(RowMapper<T> rowMapper) {
        List<T> list = jdbcTemplate.query(parse(query), params, rowMapper);
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return getRandomItem(list);
    }

    protected List<Map<String, Object>> getMapList() {
        try {
            return jdbcTemplate.queryForList(parse(query), params);
        } catch (DataAccessException e) {
            return null;
        }
    }

    protected <T> T getResult(Class<T> tClass) {
        try {
            return jdbcTemplate.queryForObject(parse(query), params, tClass);
        } catch (DataAccessException e) {
            return null;
        }
    }

    protected <T> T getResult(RowMapper<T> rowMapper) {
        try {
            return jdbcTemplate.queryForObject(parse(query), params, rowMapper);
        } catch (DataAccessException e) {
            return null;
        }
    }

    /**
     * Enum class to store the key related to the function of sql server grammar
     * For each key can be associated one or more function with related regex identification
     */
    enum SQLFunction {
        INTERVAL,
        CURRENT_TIME,
        LIMIT_RESULT,
        CONVERT_DATE
    }

}