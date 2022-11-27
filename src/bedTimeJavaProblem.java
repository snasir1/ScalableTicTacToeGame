import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.*;

import java.time.LocalDateTime;

import java.time.Duration;
import java.time.Instant;


public class bedTimeJavaProblem {

    public static void main(String[] args) throws ParseException {


        String string = "Momin";
        String string2 = "every end is a new beginning";

        System.out.println(string.toLowerCase().startsWith("nom".toLowerCase()));       
        System.out.println(string.toLowerCase().endsWith("mom".toLowerCase()));    

        String currentTimeButBest = new SimpleDateFormat("HH:mm").format(new Date());
        //String currentTimeButBest = "15:15";
        //String getAmorPm = new SimpleDateFormat("a").format(new Date());
        //System.out.println("currentTimeButBest " + currentTimeButBest);
        //System.out.println("getAmorPm " + getAmorPm);
        String[] checkFromNinePmToTwoAm = {"21:", "22:", "23:", "00:", "01:", "02:00"};
        /*String test = "21:00";
        boolean contains = Arrays.stream(checkFromNinePmToTwoAm).anyMatch(test::contains);*/
        /*if (contains)
            System.out.println("SAAD BHAIJAN IS RIGHT");*/

        if (currentTimeButBest.contains("20:")) //8PM -- 8:59pm say this.
            System.out.println("It’s almost your bedtime. Wind up!");
        else if (currentTimeButBest.contains("21:00")) //if 9pm say this.
            System.out.println("It's your bedtime!");
        //Anything after up till 2 AM, and he should be informed that it is xx:xx amount of time over his bedtime    
        else if (stringContainsItemFromList(currentTimeButBest, checkFromNinePmToTwoAm))
        {
            
            /*String time1 = "21:00";
            String time2 = currentTimeButBest;
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();*/
            long difference = timeDifference("21:00", currentTimeButBest);
            

            if (difference < 0)
            {
                Calendar cal = Calendar.getInstance();                
               
                //now we want to manually set the hours and minutes and just set second and milisecond to 0.
                cal.set(Calendar.HOUR_OF_DAY,21);
                cal.set(Calendar.MINUTE,0);
                cal.set(Calendar.SECOND,0);
                cal.set(Calendar.MILLISECOND,0);
                cal.add(Calendar.DATE, -1);
                Date d1 = cal.getTime(); //first date we want to set to 9 pm.

                Date d2 = Calendar.getInstance().getTime(); //gets current date and time
                //formatDate = new SimpleDateFormat("MM/dd/yyyy");
                //String test1 = formatDate.format(d1);
                //String test2 = formatDate.format(d2);
                //System.out.println("test1" + test1 +  "- test2: " + test2);
                difference = d2.getTime() - d1.getTime();
            }


            long differenceMinutes = difference / (60 * 1000) % 60;
            long differenceHours = difference / (60 * 60 * 1000) % 24;
            //System.out.println("It's xx:xx past your bedtime!");
            System.out.printf("It's %d:%d past your bedtime!", differenceHours, differenceMinutes);
        }
        else
        {
            //Must inform user not his bed time, but will be in xx:xx.
            /*String time1 = currentTimeButBest;
            String time2 = "21:00";
            
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date date1 = format.parse(time1);
            Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();*/
            long difference = timeDifference(currentTimeButBest, "21:00");

            long differenceMinutes = difference / (60 * 1000) % 60;
            long differenceHours = difference / (60 * 60 * 1000) % 24;
            //System.out.println("It's xx:xx past your bedtime!");
            System.out.printf("It's %d:%d till your bedtime!", differenceHours, differenceMinutes);
        }
    }

    private static long timeDifference(String t1, String t2) throws ParseException
    {
        //Must inform user not his bed time, but will be in xx:xx.
        String time1 = t1;
        String time2 = t2;
        
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long difference = date2.getTime() - date1.getTime();
        return difference;
    }


    private static long timeDifference(String t1, String t2, String t3) throws ParseException
    {
        //Must inform user not his bed time, but will be in xx:xx.
        String time1 = t1;
        String time2 = t2;
        
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = format.parse(time1);
        Date date2 = format.parse(time2);
        long difference = date2.getTime() - date1.getTime();
        return difference;
    }


    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).anyMatch(inputStr::contains);
    }
}



