package com.payconiq.api.support.utils;

import org.apache.commons.lang.time.StopWatch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CommonUtils {

    public static String randomWordGenerator(int length){
        {
            Random random = new Random();
            char[] word = new char[length];
            for(int j = 0; j < length; j++)
            {
                word[j] = (char)(random.nextInt(26));
            }
            return word.toString();
        }
    }

}
