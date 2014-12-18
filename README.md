Overview
========

Date4j came from Hirondelle Systems, "http://www.date4j.net/":http://www.date4j.net/ and his under License BSD (http://www.date4j.net/LICENSE_BSD.txt)

DATE4J is a simple alternative to the standard JDK date classes. It was created by "Hirondelle Systems":http://www.date4j.net/
(John O'Hanley). (42Ko ...)

It's an alternative to Date, Calendar, and related Java classes. The JDK's treatment of dates is likely the single most suctorial aspect of the Java core libraries. It needs improvement.

The main goals of date4j are :
* easy manipulation of dates/times in the Gregorian calendar (the civil calendar used in almost all countries).
* easy storage and retrieval of such dates/times from a relational database.
* a simplified model of civil timekeeping, similar to the model used by many databases.


Java's Date Classes Must Die.
=============================

The date4j tool chooses to focus on how databases can store dates and times in a simple style (without time zone/offset), and not on modeling the arcane details of civil timekeeping.

In summary :
* its public API consists of a single public class called DateTime. That class is immutable.
* it doesn't store any time zone information. Most date-times are stored in columns whose type does not include time zone information (see note above).
* it ignores all non-linearities: summer-hours, leap seconds, and the cutover from Julian to Gregorian calendars.
* its precision matches the highest precision used by databases (nanosecond).
* it uses only the proleptic Gregorian Calendar, over the years 1..9999.
* it has (very basic) support for wonky dates, such as the magic value 0000-00-00 used by MySQL.
* it lets you choose among 4 policies for 'day overflow' conditions during calculations.


Recommendations for using date4j :
* in your code, use date4j's DateTime to model date-time information.
* in your database, use columns having data types which do not attempt to manage time zones for you.
* if implicit time zones are sufficient for your users, consider not using your database at all for any time zone storage, or related calculations.
* if implicit time zones are not sufficient for your users, then roll your own solution, and store them in a column of their own, separate from the date-time. (To be normalized, such a solution would usually require construction of a simple time zone table, to store some or all of the time zone identifiers known to Java - 'America/Montreal', 'Asia/Jakarta', and so on.)

Examples
========

You could find more example in source "Examples.java":date4j-import/blob/master/src/main/java/hirondelle/date4j/Examples.java

Simple examples of using date4j's DateTime class
------------------------------------------------


``` java
DateTime dateAndTime = new DateTime("2010-01-19 23:59:59");
DateTime dateAndTime = new DateTime("2010-01-19T23:59:59.123456789");
DateTime dateOnly = new DateTime("2010-01-19");
DateTime timeOnly = new DateTime("23:59:59");
DateTime dateOnly = DateTime.forDateOnly(2010,01,19);
DateTime timeOnly = DateTime.forTimeOnly(23,59,59,0);

DateTime dt = new DateTime("2010-01-15 13:59:15");
boolean leap = dt.isLeapYear(); //false
dt.getNumDaysInMonth(); //31
dt.getStartOfMonth(); //2010-01-01, 00:00:00.000000000
dt.getEndOfDay(); //2010-01-15, 23:59:59.999999999
dt.format("YYYY-MM-DD"); //formats as '2010-01-15'
dt.plusDays(30); //30 days after Jan 15
dt.numDaysFrom(someDate); //returns an int
dueDate.lt(someDate); //less-than
dueDate.lteq(someDate); //less-than-or-equal-to
```

Although DateTime carries no TimeZone information internally, there are methods that take a TimeZone as a parameter
-------------------------------------------------------------------------------------------------------------------

``` java
DateTime now = DateTime.now(someTimeZone);
DateTime today = DateTime.today(someTimeZone);
DateTime fromMilliseconds = DateTime.forInstant(31313121L, someTimeZone);
birthday.isInFuture(someTimeZone);
dt.changeTimeZone(fromOneTimeZone, toAnotherTimeZone);
```


Maven Integration
======================

Add a new dependency on your project

``` xml
<dependency>
  <groupId>org.intelligents-ia.hirondelle</groupId>
  <artifactId>date4j</artifactId>
  <version>1.2.2</version>
</dependency>
```



Change Log
==========


1.2.2
-----

* publish on sonatype
* change package name

1.2.1
-----

* maven integration
* refactor test unit
