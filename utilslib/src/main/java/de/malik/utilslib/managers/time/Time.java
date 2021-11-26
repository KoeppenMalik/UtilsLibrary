package de.malik.utilslib.managers.time;

import androidx.annotation.NonNull;

public class Time {

    /**
     * the string by which the time values will be separated
     */
    public static final String TIME_SEPARATOR = ":";

    /**
     * decides if hours should be involved in the time string
     */
    public static final int INVOLVEMENT_HOURS = 0;

    /**
     * decides if minutes should be involved in the time string
     */
    public static final int INVOLVEMENT_MINUTES = 1;

    /**
     * decides if seconds should be involved in the time string
     */
    public static final int INVOLVEMENT_SECONDS = 2;

    /**
     * decides if milliseconds should be involved in the time string
     */
    public static final int INVOLVEMENT_MILLISECONDS = 3;

    /**
     * contains the value of the time
     */
    public int hours, minutes, seconds, millis;

    /**
     * creates a new Time with the given value
     * @param millis the time in milliseconds
     */
    public Time(long millis) {
        long[] conversion = millisToTimeValues(millis);
        hours = (int) conversion[0];
        minutes = (int) conversion[1];
        seconds = (int) conversion[2];
        this.millis = (int) conversion[3];
    }

    /**
     * creates a new Time with the given values
     * @param hours the hours of the time
     * @param minutes the minutes of the time
     * @param seconds the seconds of the time
     * @param millis the milliseconds of the time
     */
    public Time(int hours, int minutes, int seconds, int millis) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.millis = millis;
    }

    /**
     * converts the given millis into a long array containing the resulting hours, minutes, seconds and milliseconds
     * @param millis the milliseconds which will be converted
     * @return a long array containing the resulting hours, minutes, seconds, milliseconds
     */
    private static long[] millisToTimeValues(long millis) {
        if (millis < 0)
            throw new IllegalArgumentException("Millis can not be smaller than zero");
        long seconds = (int) (millis / 1000) % 60;
        long minutes = (int) (millis / (1000 * 60)) % 60;
        long hours = (int) (millis / (1000 * 60 * 60)) % 24;
        long millisLeft = millis - toMillis(new long[] {hours, minutes, seconds, 0});
        return new long[] {hours, minutes, seconds, millisLeft};
    }

    /**
     * converts the time values (given in the format hours, minutes, seconds, milliseconds) into milliseconds
     * @param timeValues the time values which will be converted
     * @return the time values int milliseconds
     * @throws IllegalArgumentException if the time values are not in the format hours, minutes, seconds, milliseconds
     */
    private static long toMillis(@NonNull long[] timeValues) {
        if (timeValues.length != 4)
            throw new IllegalArgumentException("Wrong array content; It has to contain hours, minutes, seconds, milliseconds");
        return (timeValues[0] * 60 * 60 * 1000) + (timeValues[1] * 60 * 1000) + (timeValues[2] * 1000) + timeValues[3];
    }

    /**
     * calculates the difference between this time and the given one
     * @param time the subtrahend time
     * @return the time difference between this time and the given time
     */
    public Time getDiffTo(@NonNull Time time) {
        return new Time(this.getTime() - time.getTime());
    }

    /**
     * calculates the difference between this time and the given one. Different to getDiffTo(), this
     * method switches at 24 hours so that there is no negative value and it returns always the time difference just
     * like on a clock
     * @param time the subtrahend time
     * @return the time difference between this time and the given one, just like on a clock - there is no negative value
     */
    public Time getClockDiffTo(@NonNull Time time) {
        long diff = this.getTime() - time.getTime();
        long result = 0;
        if (diff < 0) {
            result = 24 * 60 * 60 * 1000 + diff;
        }
        else result = diff;
        return new Time(result);
    }

    /**
     * converts this time into milliseconds
     * @return this time in milliseconds
     */
    public long getTime() {
        return toMillis(new long[] {hours, minutes, seconds, millis});
    }

    /**
     * converts this time into a time string
     * @param involvements all the time values which the string will contain
     * @return a time string containing the given values
     */
    public String toFormattedString(@NonNull int... involvements) {
        String timeString = "";
        String[] timeParts = toFormattedString().split(TIME_SEPARATOR);
        for (int i = 0; i < involvements.length; i++) {
            if (i != involvements.length -1)
                timeString += timeParts[involvements[i]] + TIME_SEPARATOR;
            else timeString += timeParts[involvements[i]];
        }
        return timeString;
    }

    /**
     * creates a time string containing all time values (hours, minutes, seconds, milliseconds)
     * @return a time string containing all time values (hours, minutes, seconds, milliseconds)
     */
    public String toFormattedString() {
        String formattedString = "";
        String[] formattedValues = new String[] {"0" + hours, "0" + minutes, "0" + seconds, "0" + millis};
        long[] timeValues = new long[] {hours, minutes, seconds, millis};
        for (int i = 0; i < timeValues.length; i++) {
            if (timeValues[i] > 9) {
                formattedValues[i] = formattedValues[i].replaceFirst("0", "");
            }
            if (i != formattedValues.length -1)
                formattedString += formattedValues[i] + TIME_SEPARATOR;
            else formattedString += formattedValues[i];
        }
        return formattedString;
    }
}
