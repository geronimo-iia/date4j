/**
 *        Licensed to the Apache Software Foundation (ASF) under one
 *        or more contributor license agreements.  See the NOTICE file
 *        distributed with this work for additional information
 *        regarding copyright ownership.  The ASF licenses this file
 *        to you under the Apache License, Version 2.0 (the
 *        "License"); you may not use this file except in compliance
 *        with the License.  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing,
 *        software distributed under the License is distributed on an
 *        "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *        KIND, either express or implied.  See the License for the
 *        specific language governing permissions and limitations
 *        under the License.
 *
 */
package hirondelle.date4j;

import java.util.Locale;
import java.util.TimeZone;

/** Examples of how to use date4j. */
public final class Examples {

  /** 
   Run the examples, and output to the console. 
   
  <P>Example output when you run this class: 
  <PRE>
Current date-time in default time zone : 2011-06-03 19:50:09
Current date-time in Cairo : 2011-06-04 01:50:10 (Saturday)
Age of someone born May 16, 1995 is : 16
The 3rd Friday of this month is : 2011-06-17
Number of days till Christmas : 205
90 days from today is : 2011-08-02
3 months and 5 days from today is : 2011-09-08
Numbers of hours difference between Paris and Perth : 6
The number of weeks since Sep 6, 2010 : 38
This many seconds till midnight : 14990
Output using the 'T' found in ISO formats: 2011-06-03T19:50:10
The number of years the JDK date-time API has been suctorial : 15
  </PRE>  
  */
  public static void main(String... aArgs){
    Examples examples = new Examples();
    examples.currentDateTime();
    examples.currentDateTimeInCairo();
    examples.ageIfBornOnCertainDate();
    examples.optionsExpiry();
    examples.daysTillChristmas();
    examples.whenIs90DaysFromToday();
    examples.whenIs3Months5DaysFromToday();
    examples.hoursDifferenceBetweenParisAndPerth();
    examples.weeksSinceStart();
    examples.timeTillMidnight();
    examples.imitateISOFormat();
    examples.jdkDatesSuctorial();
  }
  
  // PRIVATE
  
  private static void log(Object aMsg){
    System.out.println(String.valueOf(aMsg));
  }
  
  /** What is the current date-time in the JRE's default time zone? */
  private void currentDateTime(){
    DateTime now = DateTime.now(TimeZone.getDefault());
    String result = now.format("YYYY-MM-DD hh:mm:ss");
    log("Current date-time in default time zone : " + result);
  }
  
  /** What is the current date-time in Cairo (include weekday)? */
  private void currentDateTimeInCairo(){
    DateTime now = DateTime.now(TimeZone.getTimeZone("Africa/Cairo"));
    String result = now.format("YYYY-MM-DD hh:mm:ss (WWWW)", Locale.getDefault());
    log("Current date-time in Cairo : " + result);
  }
  
  /** What's the age of someone born May 16, 1995? */
  private void ageIfBornOnCertainDate(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime birthdate = DateTime.forDateOnly(1995, 5, 16);
    int age = today.getYear() - birthdate.getYear();
    if(today.getDayOfYear() < birthdate.getDayOfYear()){
      age = age - 1; 
    }
    log("Age of someone born May 16, 1995 is : " + age);
  }
  
  /** Stock options expire on the 3rd Friday of this month. What day of the month is that? */
  private void optionsExpiry(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime firstOfMonth = today.getStartOfMonth();
    int result = 0;
    if (firstOfMonth.getWeekDay() == 7){
      result = 21;
    }
    else {
       result = 21 - firstOfMonth.getWeekDay();
    }
    DateTime thirdFriday = DateTime.forDateOnly(firstOfMonth.getYear(), firstOfMonth.getMonth(), result);
    log("The 3rd Friday of this month is : " + thirdFriday.format("YYYY-MM-DD"));
  }
  
  /** How many days till the next December 25? */
  private void daysTillChristmas(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime christmas = DateTime.forDateOnly(today.getYear(), 12, 25);
    int result = 0;
    if(today.isSameDayAs(christmas)){
      // do nothing
    }
    else if (today.lt(christmas)){
      result = today.numDaysFrom(christmas);
    }
    else if (today.gt(christmas)){
      DateTime christmasNextYear = DateTime.forDateOnly(today.getYear() + 1, 12, 25);
      result = today.numDaysFrom(christmasNextYear);
    }
    log("Number of days till Christmas : " + result);
  }
  
  /** What day is 90 days from today? */
  private void whenIs90DaysFromToday(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    log("90 days from today is : " + today.plusDays(60).format("YYYY-MM-DD"));
  }

  /** What day is 3 months and 5 days from today? */
  private void whenIs3Months5DaysFromToday(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime result = today.plus(0,3,5,0,0,0,DateTime.DayOverflow.FirstDay);
    log("3 months and 5 days from today is : " + result.format("YYYY-MM-DD"));
  }
  
  /** Current number of hours difference between Paris, France and Perth, Australia. */
  private void hoursDifferenceBetweenParisAndPerth(){
    //this assumes the time diff is a whole number of hours; other styles are possible
    DateTime paris = DateTime.now(TimeZone.getTimeZone("Europe/Paris"));
    DateTime perth = DateTime.now(TimeZone.getTimeZone("Australia/Perth"));
    int result = perth.getHour() - paris.getHour();
    if( result < 0 ) {
      result = result + 24;
    }
    log("Numbers of hours difference between Paris and Perth : " + result);
  }
  
  /** How many weeks is it since Sep 6, 2010? */
  private void weeksSinceStart(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime startOfProject = DateTime.forDateOnly(2010, 9, 6);
    int result = today.getWeekIndex() - startOfProject.getWeekIndex();
    log("The number of weeks since Sep 6, 2010 : " + result);
  }
  
  /** How much time till midnight? */
  private void timeTillMidnight(){
    DateTime now = DateTime.now(TimeZone.getDefault());
    DateTime midnight = now.plusDays(1).getStartOfDay();
    long result = now.numSecondsFrom(midnight);
    log("This many seconds till midnight : " + result);
  }
  
  /** Use the escape character '|'. */
  private void imitateISOFormat(){
    DateTime now = DateTime.now(TimeZone.getDefault());
    log("Output using the 'T' found in ISO formats: " + now.format("YYYY-MM-DDThh:mm:ss"));
  }

  /** For how many years has the JDK date-time API been suctorial? */
  private void jdkDatesSuctorial(){
    DateTime today = DateTime.today(TimeZone.getDefault());
    DateTime jdkFirstPublished = DateTime.forDateOnly(1996, 1, 23);
    int result = today.getYear() - jdkFirstPublished.getYear();
    log("The number of years the JDK date-time API has been suctorial : " + result);
  }
  
}