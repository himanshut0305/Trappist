package in.co.qedtech.trappist.payload;

public class SaveBadgeRequest {
    private String name;
    private int from;
    private int to;
    private String description;
    private String kpi;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKpi() {
        return kpi;
    }

    public void setKpi(String kpi) {
        this.kpi = kpi;
    }

    @Override
    public String toString() {
        return "SaveBadgeRequest{" +
                "name='" + name + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", description='" + description + '\'' +
                ", kpi='" + kpi + '\'' +
                '}';
    }
}
