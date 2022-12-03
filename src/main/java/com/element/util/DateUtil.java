/*
 * @(#)DateUtils.java 8/22/2008
 *
 * Copyright 2002 - 2008 JIDE Software Inc. All rights reserved.
 */
package com.element.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.*;

/**
 * 日期时间工具类
 *
 * <ul>
 *     <li>日期格式化</li>
 *     <li>日期判断、比较</li>
 * </ul>
 */
public class DateUtil {
	/** 一天的毫秒数 */
	private static final long DAY_IN_MS = 24 * 60 * 60 * 1000;

	/**
	 * 获取当前时间的字符串形式
	 *
	 * @return 当前时间的字符串形式
	 */
	public static String getDateTimeNowStr() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	public static String getDateNowStr() {
		return new SimpleDateFormat("MM月dd日").format(new Date());
	}

	/**
	 * 以yyyy-MM-dd HH:mm:ss格式将字符串转化为Date对象
	 *
	 * @param datetime 符合格式的字符串
	 * @return 转化后的date对象
	 */
	public static Date dateOf(String datetime) throws ParseException {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
	}

	/**
	 * 创建指定日期时间的Date对象
	 *
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @param time  HH:mm:ss格式的时间字符串
	 * @return Date
	 */
	public static Date dateOf(int year, int month, int day, String time) throws ParseException {
		String datetime = String.format("%4d-%02d-%02d %s", year, month, day, time);
		return dateOf(datetime);
	}

	/**
	 * 获取指定日期的最后一分钟所在时间，
	 * 比如2021-12-12 23:59:59
	 *
	 * @param year  年
	 * @param month 月
	 * @param day   日
	 * @return 格式化字符串
	 */
	public static String stringOf(int year, int month, int day) {
		return String.format("%4d-%02d-%02d 23:59:59", year, month, day);
	}

	/**
	 * 返回格式化date对象后的字符串
	 *
	 * @param date 待格式化的date对象
	 * @return 格式化后的时间
	 */
	public static String date2String(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	/**
	 * 检查时间字符串是否符合HH:mm:ss格式，符合就返回转换后的date对象
	 *
	 * @param time 待检测的字符串
	 * @return 转换的date对象或null(不符合)
	 */
	public static Date checkTimeStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		try {
			return sdf.parse(time);// 将时间字符串转为Date对象
		} catch (ParseException e) {
			return null;// 发生异常则表示字符串格式错误
		}
	}

	/**
	 * 获取指定月份的总天数
	 *
	 * @param year  年
	 * @param month 月
	 * @return 指定年和月的最大天数(最后一天)
	 */
	public static int getLastDay(int year, int month) {
		Calendar c = Calendar.getInstance();                // 日历对象
		c.set(Calendar.YEAR, year);                         // 指定年
		c.set(Calendar.MONTH, month - 1);                   // 指定月
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);   // 返回这月的最后一天
	}

	/**
	 * 获取由当前年、月、日、时、分、秒数字所组成的数组
	 *
	 * @return Integer[] 各个时间字段组成的长度为6的数组
	 */
	public static Integer[] now() {
		// 保存年、月、日、时、分、秒的数组
		Integer[] now = new Integer[6];
		Calendar c = Calendar.getInstance();// 日历对象
		now[0] = c.get(Calendar.YEAR);// 年
		now[1] = c.get(Calendar.MONTH) + 1;// 月
		now[2] = c.get(Calendar.DAY_OF_MONTH);// 日
		now[3] = c.get(Calendar.HOUR_OF_DAY);// 时
		now[4] = c.get(Calendar.MINUTE);// 分
		now[5] = c.get(Calendar.SECOND);// 秒
		return now;
	}

	////        -----------日期的判断-----------         ////

	/**
	 * 检查日历对象的日期是否与今天相同。
	 *
	 * @param cal 日历对象
	 * @return 如果日历对象与今天的日期相同，则为真。
	 */
	public static boolean isToday(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && today.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is same week as today.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same week as today.
	 */
	public static boolean isThisWeek(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && today.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is same month as today.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same month as today.
	 */
	public static boolean isThisMonth(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && today.get(Calendar.MONTH) == cal.get(Calendar.MONTH);
	}

	/**
	 * Checks if the calendar object is same quarter as today.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same quarter as today.
	 */
	public static boolean isThisQuarter(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && today.get(Calendar.MONTH) / 3 == cal.get(Calendar.MONTH) / 3;
	}

	/**
	 * Checks if the calendar object is same year as today.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same year as today.
	 */
	public static boolean isThisYear(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR);

	}

	/**
	 * Checks if the calendar object is same date as yesterday.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same date as yesterday.
	 */
	public static boolean isYesterday(Calendar cal) {
		Calendar yesterday = adjustDate(Calendar.getInstance(), -1);
		return yesterday.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && yesterday.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is last week.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is last week.
	 */
	public static boolean isLastWeek(Calendar cal) {
		Calendar lastWeek = adjustDate(Calendar.getInstance(), -7);
		return lastWeek.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && lastWeek.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is last month.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is last month.
	 */
	public static boolean isLastMonth(Calendar cal) {
		Calendar today = Calendar.getInstance();
		int thisMonth = today.get(Calendar.MONTH);
		if (thisMonth >= 1) {
			return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && thisMonth - 1 == cal.get(Calendar.MONTH);
		} else {
			return today.get(Calendar.YEAR) - 1 == cal.get(Calendar.YEAR) && today.getActualMaximum(Calendar.MONTH) == cal.get(Calendar.MONTH);
		}
	}

	/**
	 * Checks if the calendar object is last quarter.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is last quarter.
	 */
	public static boolean isLastQuarter(Calendar cal) {
		Calendar today = Calendar.getInstance();
		int thisQuarter = today.get(Calendar.MONTH) / 3;
		if (thisQuarter >= 1) {
			return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && thisQuarter - 1 == cal.get(Calendar.MONTH) / 3;
		} else {
			return today.get(Calendar.YEAR) - 1 == cal.get(Calendar.YEAR) && today.getActualMaximum(Calendar.MONTH) / 3 == cal.get(Calendar.MONTH) / 3;
		}
	}

	/**
	 * Checks if the calendar object is last year.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is last year.
	 */
	public static boolean isLastYear(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) - 1 == cal.get(Calendar.YEAR);

	}

	/**
	 * Checks if the calendar object is same date as tomorrow.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is the same date as tomorrow.
	 */
	public static boolean isTomorrow(Calendar cal) {
		Calendar tomorrow = adjustDate(Calendar.getInstance(), 1);
		return tomorrow.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && tomorrow.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is next week.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is next week.
	 */
	public static boolean isNextWeek(Calendar cal) {
		Calendar nextWeek = adjustDate(Calendar.getInstance(), 7);
		return nextWeek.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && nextWeek.get(Calendar.WEEK_OF_YEAR) == cal.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * Checks if the calendar object is next month.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is next month.
	 */
	public static boolean isNextMonth(Calendar cal) {
		Calendar today = Calendar.getInstance();
		int thisMonth = today.get(Calendar.MONTH);
		if (thisMonth < today.getActualMaximum(Calendar.MONTH)) {
			return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && thisMonth + 1 == cal.get(Calendar.MONTH);
		} else {
			return today.get(Calendar.YEAR) + 1 == cal.get(Calendar.YEAR) && today.getMinimum(Calendar.MONTH) == cal.get(Calendar.MONTH);
		}
	}

	/**
	 * Checks if the calendar object is next quarter.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is next quarter.
	 */
	public static boolean isNextQuarter(Calendar cal) {
		Calendar today = Calendar.getInstance();
		int thisQuarter = today.get(Calendar.MONTH) / 3;
		if (thisQuarter < today.getActualMaximum(Calendar.MONTH) / 3) {
			return today.get(Calendar.YEAR) == cal.get(Calendar.YEAR) && thisQuarter + 1 == cal.get(Calendar.MONTH) / 3;
		} else {
			return today.get(Calendar.YEAR) + 1 == cal.get(Calendar.YEAR) && today.getActualMinimum(Calendar.MONTH) / 3 == cal.get(Calendar.MONTH) / 3;
		}
	}

	/**
	 * Checks if the calendar object is next year.
	 *
	 * @param cal the calendar object
	 * @return true if the calendar object is next year.
	 */
	public static boolean isNextYear(Calendar cal) {
		Calendar today = Calendar.getInstance();
		return today.get(Calendar.YEAR) + 1 == cal.get(Calendar.YEAR);

	}

	/**
	 * Checks if the calendar object is in the specified month, regardless of the year.
	 *
	 * @param cal   the calendar object.
	 * @param month the month, starting from 0 for January. You can use the value defined in Calendar such as
	 *              Calendar.JANUARY, Calendar.FEBRUARY, etc.
	 * @return true if the calendar object is the specified month.
	 */
	public static boolean isAtMonth(Calendar cal, int month) {
		return cal.get(Calendar.MONTH) == month;
	}

	/**
	 * Checks if the calendar object is in the specified quarter, regardless of the year.
	 *
	 * @param cal     the calendar object.
	 * @param quarter the quarter, starting from 1 for the first quarter (including January, February, and March).
	 * @return true if the calendar object is the specified quarter.
	 */
	public static boolean isAtQuarter(Calendar cal, int quarter) {
		return cal.get(Calendar.MONTH) / 3 + 1 == quarter;
	}

	public static boolean isSameYear(Calendar c1, Calendar c2) {
		return isSameInField(YEAR, c1, c2);
	}

	public static boolean isSameMonth(Calendar c1, Calendar c2) {
		return isSameInField(MONTH, c1, c2);
	}

	public static boolean isSameWeek(Calendar c1, Calendar c2) {
		return isSameInField(WEEK_OF_YEAR, c1, c2);
	}

	public static boolean isSameDay(Calendar c1, Calendar c2) {
		return isSameInField(DAY_OF_MONTH, c1, c2);
	}

	public static boolean isSameHour(Calendar c1, Calendar c2) {
		return isSameInField(HOUR_OF_DAY, c1, c2);
	}

	public static boolean isSameMinute(Calendar c1, Calendar c2) {
		return isSameInField(MINUTE, c1, c2);
	}

	public static boolean isSameSecond(Calendar c1, Calendar c2) {
		return isSameInField(SECOND, c1, c2);
	}

	public static boolean isSameMillis(Calendar c1, Calendar c2) {
		return isSameInField(MILLISECOND, c1, c2);
	}

	public static boolean isSameInField(int field, Calendar c1, Calendar c2) {
		return c1.get(field) == c2.get(field);
	}

	/**
	 * @return the int field from the Calendar class at which the dates differ. This will be one of YEAR, MONTH,
	 * WEEK_OF_YEAR, DAY_OF_YEAR, HOUR_OF_DAY, MINUTE, SECOND, MILLISECOND, or null if there are no
	 * differences.
	 */
	public static Integer mostSignificantDifference(Calendar c1, Calendar c2) {
		if (!isSameYear(c1, c2)) {
			return YEAR;
		} else if (!isSameMonth(c1, c2)) {
			return MONTH;
		} else if (!isSameWeek(c1, c2)) {
			return WEEK_OF_YEAR;
		} else if (!isSameDay(c1, c2)) {
			return DAY_OF_MONTH;
		} else if (!isSameHour(c1, c2)) {
			return HOUR_OF_DAY;
		} else if (!isSameMinute(c1, c2)) {
			return MINUTE;
		} else if (!isSameSecond(c1, c2)) {
			return SECOND;
		} else if (!isSameMillis(c1, c2)) {
			return MILLISECOND;
		} else {
			return null;
		}
	}

	/**
	 * Adjusts the Calendar to several days before or after the current date.
	 *
	 * @param calendar        the Calendar object to be adjusted.
	 * @param differenceInDay the difference in days. It accepts both position and negative number.
	 * @return the calendar after the adjustment. It should always be the same instance as the calendar parameter.
	 */
	public static Calendar adjustDate(Calendar calendar, int differenceInDay) {
		// 这里可以使用add的，不知道哪个会更好
		calendar.setTimeInMillis(calendar.getTimeInMillis() + DAY_IN_MS * differenceInDay);
		return calendar;
	}

	/**
	 * Returns the minimum Date in the Date list.
	 *
	 * @param dates the list of Date to calculate the minimum.
	 * @return the minimum date in the Date list.
	 * @deprecated replaced by {@link #minDates(List)} to comply with the new compilation regulation. We
	 * have to delete the min(List<Calendar>) in releases earlier than 2.9.4 due to the same reason.
	 */
	@Deprecated
	public static Date min(List<Date> dates) {
		return minDates(dates);
	}

	/**
	 * Returns the maximum Date in the Date list.
	 *
	 * @param dates the list of Date to calculate the maximum.
	 * @return the maximum date in the Date list.
	 * @deprecated replaced by {@link #maxDates(List)} to comply with the new compilation regulation. We
	 * have to delete the max(List<Calendar>) in releases earlier than 2.9.4 due to the same reason.
	 */
	@Deprecated
	public static Date max(List<Date> dates) {
		return maxDates(dates);
	}

	/**
	 * Returns the minimum Date in the Date list.
	 *
	 * @param dates the list of Date to calculate the minimum.
	 * @return the minimum date in the Date list.
	 */
	public static Date minDates(List<Date> dates) {
		long min = Long.MAX_VALUE;
		Date minDate = null;
		for (Date value : dates) {
			long v = value.getTime();
			if (v < min) {
				min = v;
				minDate = value;
			}
		}
		return minDate;
	}

	/**
	 * Returns the maximum Date in the Date list.
	 *
	 * @param dates the list of Date to calculate the maximum.
	 * @return the maximum date in the Date list.
	 */
	public static Date maxDates(List<Date> dates) {
		long max = Long.MIN_VALUE;
		Date maxDate = null;
		for (Date value : dates) {
			long v = value.getTime();
			if (v > max) {
				max = v;
				maxDate = value;
			}
		}
		return maxDate;
	}


	/**
	 * Returns the minimum Calendar in the Calendar list.
	 *
	 * @param calendars the list of Calendar to calculate the minimum.
	 * @return the minimum calendar in the Calendar list.
	 */
	public static Calendar minCalendars(List<Calendar> calendars) {
		long min = Long.MAX_VALUE;
		Calendar minCalendar = null;
		for (Calendar value : calendars) {
			long v = value.getTimeInMillis();
			if (v < min) {
				min = v;
				minCalendar = value;
			}
		}
		return minCalendar;
	}

	/**
	 * Returns the maximum Calendar in the Calendar list.
	 *
	 * @param calendars the list of Calendar to calculate the maximum.
	 * @return the maximum calendar in the Calendar list.
	 */
	public static Calendar maxCalendars(List<Calendar> calendars) {
		long max = Long.MIN_VALUE;
		Calendar maxCalendar = null;
		for (Calendar value : calendars) {
			long v = value.getTimeInMillis();
			if (v > max) {
				max = v;
				maxCalendar = value;
			}
		}
		return maxCalendar;
	}
}
