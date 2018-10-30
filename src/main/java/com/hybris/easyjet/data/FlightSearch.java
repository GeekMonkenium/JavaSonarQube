package com.hybris.easyjet.data;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tejaldudhale on 01/08/2017.
 */
public class FlightSearch {

    private static final Random rand = new Random(System.currentTimeMillis());
    private static List<String> availableSectors;
    private static List<String> defaultSectors;

    static {
        availableSectors = new ArrayList<>();
        defaultSectors = Arrays.asList("LTNBCN",
                "LTNBCN",
                "LTNBCN",
                "LTNBFS",
                "LTNBFS",
                "LTNBFS",
                "LTNEDI",
                "LTNEDI",
                "LTNEDI",
                "LTNGLA",
                "LTNGLA",
                "LTNGVA",
                "LTNINV",
                "LTNMAD",
                "LTNPMI",
                "LTNZRH",
                "LTNCDG",
                "LTNCDG",
                "LTNTFS",
//                "LTNVLC", fail on http://34.250.231.216:8080/job/CI-Sprint36-TeamC/29/HTML_Report/
                "EDILGW",
                "EDILGW",
                "EDILGW",
                "EDILGW",
                "LGWABZ",
                "LGWABZ",
                "LGWAGP",
                "LGWAGP",
                "LGWAGP",
                "LGWBCN",
                "LGWBCN",
                "LGWBCN",
                "LGWBCN",
                "LGWBCN",
                "LGWBCN",
                "LGWBFS",
                "LGWBFS",
                "LGWBFS",
                "LGWBFS",
                "LGWBFS",
                "LGWBFS",
                "LGWBFS",
                "LGWEDI",
                "LGWEDI",
                "LGWEDI",
                "LGWEDI",
                "LGWEDI",
                "LGWGLA",
                "LGWGLA",
                "LGWGLA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWGVA",
                "LGWINV",
                "LGWINV",
                "LGWINV",
                "LGWMAD",
                "LGWMAD",
                "LGWMAD",
                "LGWMAD",
                "LGWNCE",
                "LGWNCE",
                "LGWNCE",
                "LGWPMI",
                "LGWPMI",
                "LGWZRH",
                "LGWZRH",
                "LGWACE",
                "LGWALC",
                "LGWALC",
                "LGWBOD",
                "LGWBSL",
                "LGWBSL",
                "LGWBSL",
                "LGWCDG",
                "LGWCDG",
                "LGWCDG",
                "LGWFUE",
                "LGWIOM",
                "LGWIOM",
                "LGWJER",
                "LGWJER",
                "LGWJER",
                "LGWJER",
                "LGWLPA",
                "LGWLYS",
                "LGWLYS",
                "LGWLYS",
                "LGWMJV",
                "LGWMPL",
                "LGWMRS",
                "LGWNTE",
                "LGWNTE",
                "LGWTFS",
                "LGWTFS",
                "LGWTLS",
                "LGWTLS",
                "LGWTLS",
                "LGWVLC",
                "LGWVLC",
                "LGWSVQ");
        loadSectors();
    }

    public static List<String> getAvailableSectors() {
        if (CollectionUtils.isEmpty(availableSectors)) {
            loadSectors();
        }
        return availableSectors;
    }

    public static String getRandomSector() {
        return getAvailableSectors().get(rand.nextInt(getAvailableSectors().size()));
    }

    private static void loadSectors() {
        System.out.println("Loading Sectors...");
        //TODO commented out as we are not using the csv at the moment
//        FileReader csvFileReader = null;
//        CSVParser csvFileParser = null;
//        try {
//            URL csvResource = ClassLoader.getSystemResource("testdata/" + getFileName());
//            if (csvResource != null) {
//                File csvFile = new File(URLDecoder.decode(csvResource.getFile(), "UTF-8"));
//                CSVFormat csvFileFormat = CSVFormat.DEFAULT.withIgnoreEmptyLines();
//                csvFileReader = new FileReader(csvFile);
//                csvFileParser = new CSVParser(csvFileReader, csvFileFormat);
//                List<CSVRecord> csvRecords = csvFileParser.getRecords();
//                int sectorIndex = getSectorColumnIndex();
//                csvRecords.forEach(csvRecord -> availableSectors.add(csvRecord.get(sectorIndex)));
//            }
//        } catch (Exception e) {
//            System.out.println("Error loading available sectors, falling to default sectors");
//            e.printStackTrace();
//            loadDefaultSectors();
//        } finally {
//            try {
//                if (csvFileReader != null) {
//                    csvFileReader.close();
//                }
//                if (csvFileParser != null) {
//                    csvFileParser.close();
//                }
//            } catch (Exception e) {
//                System.out.println("Error while closing csvFileReader/csvFileParser !!!");
//                e.printStackTrace();
//            }
//        }
        if (CollectionUtils.isEmpty(availableSectors)) {
            System.out.println("No data loaded, failing to default sectors");
            loadDefaultSectors();
        }
        availableSectors = availableSectors.stream().distinct().collect(Collectors.toList());
    }

    private static void loadDefaultSectors() {
        availableSectors.addAll(defaultSectors);
    }

    private static String getFileName() {
        return Optional.ofNullable(System.getProperty("transportOfferingFileName")).orElse("transportoffering_15k_129.csv");
    }

    private static int getSectorColumnIndex() {
        String transportOfferingSectorColumnIndex = Optional.ofNullable(System.getProperty("transportOfferingSectorColumnIndex")).orElse("5");
        return Integer.parseInt(transportOfferingSectorColumnIndex);
    }
}

