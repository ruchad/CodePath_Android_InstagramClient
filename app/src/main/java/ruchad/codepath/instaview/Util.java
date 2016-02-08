package ruchad.codepath.instaview;

public class Util {

    private static long oneWeekInSeconds = 604800;
    private static long oneDayInSeconds = 86400;
    private static long oneHourInSeconds = 3600;
    private static long oneMinuteInSeconds = 60;

    static String calcRelativeTime(long timestamp){
        long relativeTime = (System.currentTimeMillis()/1000) - timestamp;
        if(relativeTime>=oneWeekInSeconds)
            return String.valueOf(relativeTime/oneWeekInSeconds + "w");
        else if (relativeTime>=oneDayInSeconds)
            return String.valueOf(relativeTime/oneDayInSeconds + "d");
        else if (relativeTime>=oneHourInSeconds)
            return String.valueOf(relativeTime/oneHourInSeconds + "h");
        else
            return String.valueOf(relativeTime/oneMinuteInSeconds + "m");

    }
}
