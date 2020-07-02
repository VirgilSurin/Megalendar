package be.surin.engine;

public class HourMin implements Comparable<HourMin> {

    private int hour;
    private int min;

    public HourMin(int hour, int min) {
        if ( hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hours must be between [0,23], not " + hour);
        }
        if ( min < 0 || min > 59) {
            throw new IllegalArgumentException("Hours must be between [0,59], not " + min);
        }
        this.hour = hour;
        this.min = min;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return hour + "." + min;
    }


    @Override
    public int compareTo(HourMin o) {
        if (hour < o.getHour()) {
            return -1;
        } else if (hour > o.getHour()) {
            return 1;
        } else {
            if (min > o.getMin()) {
                return 1;
            } else if (min < o.getMin()) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
