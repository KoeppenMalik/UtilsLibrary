package de.malik.utilslib.managers.sorting;

public enum SortValue {

    /**
     * makes the sort manager to sort by ID ascending
     */
    ID_ASC,

    /**
     * makes the sort manager to sort by ID descending
     */
    ID_DESC,

    /**
     * makes the sort manager to sort from a to z
     */
    STRING_A_TO_Z,

    /**
     * makes the sort manager to sort from z to a
     */
    STRING_Z_TO_A,

    /**
     * makes the sort manager to sort by date from the oldest to the newest (for example 2022->2021->2020...)
     */
    DATE_OLD_TO_NEW,

    /**
     * makes the sort manager to sort by date from the newest to the oldest (for example 2020->2021->2022...)
     */
    DATE_NEW_TO_OLD
}
