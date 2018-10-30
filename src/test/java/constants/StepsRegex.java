package constants;

import java.util.Arrays;
import java.util.List;

/**
 * StepsRegex contains constants for chunks of step definitions to be used in feature files
 *
 * @author gd <g.dimartino@reply.it>
 */
public class StepsRegex {
    public static final String AD_CHANNELS = "(ADAirport|ADCustomerService)";
    public static final String DIGITAL_CHANNELS = "(Digital|PublicApiMobile)";
    public static final String NON_AD_CHANNELS = "(Digital|PublicApiMobile|PublicApiB2B)";
    public static final String NON_PUBLIC_API_B2B_CHANNELS = "(ADAirport|ADCustomerService|Digital|PublicApiMobile|VT100)";
    public static final String CHANNELS = "(ADAirport|ADCustomerService|Digital|PublicApiMobile|PublicApiB2B|VT100|EI)";
    public static final String CHANNEL_LIST = "((?:ADAirport(?:,\\s)?)?(?:ADCustomerService(?:,\\s)?)?(?:Digital(?:,\\s)?)?(?:PublicApiMobile(?:,\\s)?)?(?:VT100(?:,\\s)?)?(?:PublicApiB2B)?)";

    public static final List<String> MEGASWITCH_GROUPS = Arrays.asList("b2bmegaswitchagent");
    public static final String FARE_TYPES = "(Standard|Flexi|Staff|Standby|Inclusive)";
    public static final String FARE_TYPES_AS_LIST = "(?: for ((?:Standard|Flexi|Staff|Standby|Inclusive)(?:, (?:Standard|Flexi|Staff|Standby|Inclusive))*) fares?)?";
    public static final String PASSENGER_TYPES = "(adult|child|infant|infantOnLap|infantOnSeat)";
    public static final String INFANT_TYPES = "(infantOnLap|infantOnSeat)";
    public static final String DEAL = "(?: using (.+) application id and (.+) office id(?: and (.+) corporate id)? deal information)?";
    public static final String RETURN = "( with return)?";
    public static final String SECTOR = "(?:(?:(?: (?:from|with|for) (\\w{3}|same|different|apis|non-apis|DCS|non-DCS|INF-OL-BP|non-INF-OL-BP)))?(?: (?:to )?(\\w{3}|sector))?)?";
    public static final String FARE_TYPE = "(?: (?:with|as) (Standard|Flexi|Staff|Standby|Inclusive|Group|StaffStandard) (?:fare|booking))?";
    public static final String FARE_TYPE_LIST = "(?: with ((?:Standard(?:,\\s)?)?(?:Flexi(?:,\\s)?)?(?:Staff(?:,\\s)?)?(?:Standby(?:,\\s)?)?(?:Inclusive(?:,\\s)?)?(?:Group)?) fares?)?";
    public static final String PASSENGER_MIX = "(?: for (\\d{1,3}(?:,\\d{1,3})?\\s+\\w+(?:\\s*;\\s*\\d{1,3}(?:,\\d{1,3})?\\s+\\w+)*))?";
    public static final String PASSENGER_MIX_APIS = "(?: for (\\d{1,3}(?:,\\d{1,3})?\\s+\\w+(?:\\s*;\\s*\\d{1,3}(?:,\\d{1,3})?\\s+\\w+)*)( without apis data)?)?";
    public static final String SAME_PASSENGER_NOT_LINKING = "( with same passenger not linking)?";
    public static final String OUTBOUND_DATE = "(?: departing (today|in \\d+ days?))?";
    public static final String INBOUND_DATE = "(?: returning after (\\d+) days?)?";
    public static final String DATES = "(?:" + OUTBOUND_DATE + "(?: and)?" + INBOUND_DATE + ")?";
    public static final String FLEXIBLE_DAYS = "(?: with (\\d+) flexible days)?";
    public static final String CURRENCY = "(?: with ([a-zA-Z]{3}) currency)?";
    public static final String ROUTEPAIR = "(?: with (O|R|no|invalid) routepair ?)?";
    public static final String JOURNEY = "(?: as an? (outbound|inbound|single|outbound/inbound) journey)?";
    public static final String HOLD_ITEM = "(?: an?| with)?(?: (\\d+|percentage promotion|basket promotion))? hold bags?(?: with (\\d+) (.+)? excess weight)?(?: for (\\d?|each) passenger)?";
    public static final String SPORTS_ITEM = "(?: and)?(?: a| with)?(?: (\\d+|percentage promotion|buy x get y promotion))? sport items?(?: for (\\d?|each) passenger)?";
    public static final String HOLD_ITEM_TYPE = "((?:(?: an| with)?(?: (15kgbag|23kgbag))? hold bag?)?(?: (?:a|with)? (\\d+) (.+)? excess weight)?(?: for (\\d?|each) passenger)?)?";
    public static final String EXCESS_WEIGHT = "( with excess weight)? ";
    public static final String HOLD_ITEMS = "((?: an| with)?(?: (\\d+|percentage promotion|basket promotion|15kgbag|23kgbag))? hold bags?(?: with (\\d+) (.+)? excess weight)?(?: for (\\d?|each) passenger)?)?";
    public static final String GUEST_CUSTOMER="(?: for guest customer (with|without)? email)?";
    public static final String SPORTS_ITEMS = "((?: and)?(?: a| with)?(?: (\\d+|percentage promotion|buy x get y promotion))? sport items?(?: for (\\d?|each) passenger)?)?";
    public static final String SEATS = "((?: and)?(?: a| with)?(?: (Extra Legroom|Up Front|Standard|Allocated))? purchased seat)?";
    public static final String CHECK_IN = "( for checked-in passengers)?";
    public static final String PAYMENT_METHOD_LIST = " with ((?:card|debit card|credit card|elv|cash|voucher|credit file)(?:, (?:card|debit card|credit card|elv|cash|voucher|credit file))*)";
    public static final String CURRENCY_LIST = " with (\\w{3}(?:, \\w{3})*) currencies";
    public static final String FIRST_NAME = "(?: with (.*) firstName)?";
    public static final String LAST_NAME = "(?: with (.*) lastName)?";
    public static final String EMAIL = "(?: with (.*) email)?";
    public static final String VOUCHER_CODE = "(?: with (.*) voucher code)?";
    public static final String BOOKING_ID = "(?: with (.*) booking ref)?";
    public static final String PAYMENT_METHOD = "(?: with ((?:card|debit card|credit card|elv|cash|voucher|credit file)(?:, (?:card|debit card|credit card|elv|cash|voucher|credit file))*)?(\\s?saved)? payment method)?";
    public static final String CAR = "(?: with (car product))?";
    public static final String INSURANCE = "(?: with (insurance))?";
    public static final String HOTEL = "(?: with (hotel))?";
    public static final String N_PASSENGER = "(?: for (one|all) passenger)?";
    public static final String N_FLIGHT = "(?: (one|all))?";
    public static final String PRODUCT = "(?: for (holdbag|seat))?";
    public static final String AGENTOVERRIDE = "(?: with agentoveride set to (true|false))?";
    public static final String SSR = "(?: with (an|VIP) ssr)?";
    public static final String BOOKING_TYPES = "(?: for (Business) booking types?)?";
    private static final String AGENT_PRINCIPAL_GROUPS = "callcentre|headoffice|airport";
    private static final String AGENT_SUB_GROUPS = "airportcc|airportsp|airportgroundcrew|airport_gv|csadmin|csmgt|cctier1|cctier2|cctier3|b2bmanager|fa|security|finance|b2bmegaswitchagent|finance-x|revenueprotection|hmga|inet|itops|itteam|ittesting|mgmt|pa|pwda|revman|rgm|rpl|sd|su|treasury|treasury_a|usermgt|dutytravel|crisismgt|alo|publicapimobileagent|ccgroupseriesb2b";
    public static final String AGENT_GROUPS = "(?:(" + AGENT_PRINCIPAL_GROUPS + "|" + AGENT_SUB_GROUPS + ") )?";
    public static final String EXCEEDING_LIMIT = "(?: for (adultWithChildren|childOnly) exceeding limit)?";
    public static final String INDIRECT = "(?: with (indirect) flights)?";
    public static final String FOR_MISSED_FLIGHT_FLAG = "(?: (with|without) forMissedFlight flag)?";

    private static final String AGENT_PERMISSIONS = "(?:OverrideCheckInWindowClosure|EarlierFlight|MissedFlight)";
    public static final String AGENT_PERMISSIONS_LIST = "(" + AGENT_PERMISSIONS + "(?:, " + AGENT_PERMISSIONS + ")*)";
}