package com.berat.constant;

public class EndPoints {
    public static final String VERSION="api/v1";
    public static final String ELASTIC=VERSION+"/elastic";
    public static final String USER=ELASTIC+"/user";



    // Auth Controller
    public static final String CREATE="/create";
    public static final String UPDATE="/update";
    public static final String DELETEBYID="/deletebyid";
    public static final String FINDBYID="/findbyid";
    public static final String FINDBYROLE="/findbyrole";

    public static final String FINDBYUSERNAME="/findbyusername";
    public static final String FINDALL="/findall";
    public static final String ACTIVESTATUS="/activestatus";
}
