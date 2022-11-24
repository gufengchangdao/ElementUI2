package com.element.grouper.date;

import com.element.converter.ConverterContext;
import com.element.converter.YearNameConverter;
import com.element.grouper.GroupResources;
import com.element.grouper.GrouperContext;

import java.util.Calendar;
import java.util.Locale;

/**
 *
 */
public class DateYearGrouper extends DateGrouper {
	public static GrouperContext CONTEXT = new GrouperContext("DateYear");

	public Object getValue(Object value) {
		return getCalendarField(value, Calendar.YEAR);
	}

	public String getName() {
		return GroupResources.getResourceBundle(Locale.getDefault()).getString("Date.year");
	}

//    public static void main(String[] args) {
//        ObjectGrouper grouper = new DateYearGrouper();
//        Calendar calendar = Calendar.getInstance();
//        for (int i = 0; i < 40; i++) {
//            System.out.println(grouper.getGroupValue(calendar));
//            calendar.roll(Calendar.YEAR, 1);
//        }
//    }


	@Override
	public ConverterContext getConverterContext() {
		return YearNameConverter.CONTEXT;
	}
}
