package com.example.foodplaner.models;

import java.util.List;

public class CountryParser {
    private static final List<String> COUNTRY_NAMES=List.of("American","British","Canadian","Chinese","Croatian","Dutch","Egyptian","Filipino","French","Greek","Indian","Irish","Italian","Jamaican","Japanese","Kenyan","Malaysian","Mexican","Moroccan","Polish","Portuguese","Russian","Spanish","Thai","Tunisian","Turkish","Ukrainian","Uruguayan","Vietnamese");
    private static final List<String> COUNTRY_CODES=List.of("US", "GB", "CA", "CN", "HR", "NL", "EG", "PH", "FR", "GR", "IN", "IE", "IT", "JM", "JP", "KE", "MY", "MX", "MA", "PL", "PT", "RU", "ES", "TH", "TN", "TR", "UA", "UY", "VN");
    public static String getCode(String countryName){
       int index= COUNTRY_NAMES.indexOf(countryName);
        if (index!=-1){
            return COUNTRY_CODES.get(index);
        }
        return null;
    }
}
