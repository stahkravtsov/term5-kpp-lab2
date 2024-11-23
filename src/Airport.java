import java.time.ZoneId;

class Airport {
    private final String code;
    private final String name;
    private final ZoneId timezone;

    public Airport(String code, String name, String timezone) {
        this.code = code;
        this.name = name;
        this.timezone = ZoneId.of(timezone);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public ZoneId getTimezone() {
        return timezone;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", timezone='" + timezone + '\'' +
                '}';
    }
}