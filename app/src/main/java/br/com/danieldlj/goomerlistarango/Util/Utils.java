package br.com.danieldlj.goomerlistarango.Util;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.danieldlj.goomerlistarango.Model.HourModel;


public class Utils {

    @SuppressLint("SimpleDateFormat")
    public static boolean verifyHours(HourModel dias){

        String Tag = "Utils Verify Hours";

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int NowWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for (Integer weekSale: dias.getDays()) {
            if (NowWeek == weekSale) {
                try {
                    SimpleDateFormat simpleDateformat = new SimpleDateFormat("HH:mm");

                    Date time1 = simpleDateformat.parse(dias.getFrom());
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.setTime(time1);
                    calendar1.add(Calendar.WEEK_OF_MONTH, weekSale);

                    Date time2 = simpleDateformat.parse(dias.getTo());
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(time2);

                    Date d = simpleDateformat.parse(simpleDateformat.format(now));
                    Calendar calendar3 = Calendar.getInstance();
                    calendar3.setTime(d);
                    calendar3.add(Calendar.WEEK_OF_MONTH, NowWeek);


                    if (time1.after(calendar2.getTime())) {
                        Log.d(Tag, "Tem horario depois");
                        if (weekSale == 7) {
                            calendar2.add(Calendar.WEEK_OF_MONTH, 1);
                        } else {
                            calendar2.add(Calendar.WEEK_OF_MONTH, weekSale + 1);
                        }
                    } else {
                        Log.d(Tag, "Nao tem horario depois");
                        calendar2.add(Calendar.WEEK_OF_MONTH, weekSale);
                    }

                    Date x = calendar3.getTime();
                    if ((x.after(calendar1.getTime()) && x.before(calendar2.getTime())) || x.equals(calendar2.getTime()) || x.equals(calendar1.getTime())) {
                        Log.d(Tag,"Now: "+calendar3.getTime());
                        Log.d(Tag,"calendar1: "+calendar1.getTime());
                        Log.d(Tag,"calendar2: "+calendar2.getTime());
                        return true;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }


    @SuppressLint("SimpleDateFormat")
    public static long getMiliSegundos() {

        String Tag = "Utils get Mili Segundos";
        long milissegundos  = 0;
        try {
            SimpleDateFormat simpleDateformat = new SimpleDateFormat("00:mm");

            Date now = new Date();

            Date time = simpleDateformat.parse( "00:00");
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time);

            time = simpleDateformat.parse("00:15");
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time);

            time = simpleDateformat.parse("00:30");
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time);

            time = simpleDateformat.parse("00:45");
            Calendar calendar4 = Calendar.getInstance();
            calendar4.setTime(time);


            time = simpleDateformat.parse(simpleDateformat.format(now));
            Calendar calendarX = Calendar.getInstance();
            calendarX.setTime(time);


            Date x = calendarX.getTime();

            System.out.println(calendarX.getTime() );
            if((x.after(calendar1.getTime()) && x.before(calendar2.getTime())) || x.equals(calendar1.getTime()) ){
                Log.d(Tag,calendarX.get(Calendar.MINUTE) + "  Esta entre 00 e 15");
                milissegundos = (15*60000  - calendarX.get(Calendar.MINUTE) * 60000);
            }
            if((x.after(calendar2.getTime()) && x.before(calendar3.getTime())) || x.equals(calendar2.getTime()) ){
                Log.d(Tag,calendarX.get(Calendar.MINUTE) + "  Esta entre 15 e 30");
                milissegundos = (30*60000  - calendarX.get(Calendar.MINUTE) * 60000);

            }
            if((x.after(calendar3.getTime()) && x.before(calendar4.getTime())) || x.equals(calendar3.getTime()) ){
                Log.d(Tag,calendarX.get(Calendar.MINUTE) + "  Esta entre 30 e 45");
                milissegundos = (45*60000  - calendarX.get(Calendar.MINUTE) * 60000);
            }

            if(x.after(calendar4.getTime()) || x.equals(calendar4.getTime()) ){
                Log.d(Tag,calendarX.get(Calendar.MINUTE) + "  Esta entre 45 e 00");
                milissegundos = (60*60000  - calendarX.get(Calendar.MINUTE) * 60000);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milissegundos;
    }

}
