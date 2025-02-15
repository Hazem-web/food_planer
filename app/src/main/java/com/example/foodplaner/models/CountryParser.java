package com.example.foodplaner.models;

public class CountryParser {
    public static final String[] COUNTRY_NAMES={"American","British","Canadian","Chinese","Croatian","Dutch","Egyptian","Filipino","French","Greek","Indian","Irish","Italian","Jamaican","Japanese","Kenyan","Malaysian","Mexican","Moroccan","Polish","Portuguese","Russian","Spanish","Thai","Tunisian","Turkish","Ukrainian","Uruguayan","Vietnamese"};
    public static final String[] COUNTRY_CODES={"US", "GB", "CA", "CN", "HR", "NL", "EG", "PH", "FR", "GR", "IN", "IE", "IT", "JM", "JP", "KE", "MY", "MX", "MA", "PL", "PT", "RU", "ES", "TH", "TN", "TR", "UA", "UY", "VN"};
    public static String getCode(String countryName){
        for (int i = 0; i < COUNTRY_NAMES.length; i++) {
            if(COUNTRY_NAMES[i].equals(countryName))
                return COUNTRY_CODES[i];
        }
        return "";
    }
}
