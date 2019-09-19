package Day6restapi_GSON_Serialization;

import java.util.List;

public class AllSpartans {

    List<Spartan> spartanList;

    public AllSpartans(){

    }

    public AllSpartans(List<Spartan> spartanList) {
        this.spartanList = spartanList;
    }



    public List<Spartan> getSpartanList() {
        return spartanList;
    }

    public void setSpartanList(List<Spartan> spartanList) {
        this.spartanList = spartanList;
    }

    @Override
    public String toString() {
        return "AllSpartans{" +
                "spartanList=" + spartanList +
                '}';
    }
}
