package jdbcTests;

public class Regions {
    private String regionName;
    private int regionID;

    public Regions(String regionName,int regionID) {
        this.regionName = regionName;
        this.regionID = regionID;
    }

    public Regions(){
        super();
    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }



    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Regions{" +
                "regionName='" + regionName + '\'' +
                ", regionID=" + regionID +
                '}';
    }
}
