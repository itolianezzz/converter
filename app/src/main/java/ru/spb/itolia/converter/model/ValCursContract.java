package ru.spb.itolia.converter.model;

import android.provider.BaseColumns;

/**
 * Created by itolianezzz on 06.06.2017.
 */

public class ValCursContract {
        public ValCursContract() {}

        public static abstract class ValuteEntry implements BaseColumns {
            public static final String TABLE_NAME = "ValCurs";
            public static final String COLUMN_NAME_ENTRY_ID = "entryid";
            public static final String COLUMN_NAME_NUMCODE = "NumCode";
            public static final String COLUMN_NAME_CHARCODE = "CharCode";
            public static final String COLUMN_NAME_NOMINAL = "Nominal";
            public static final String COLUMN_NAME_NAME = "Name";
            public static final String COLUMN_NAME_VALUE = "Value";

        }

}
