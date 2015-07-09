
package forest.rice.field.k.gracenote;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TRACK {

    @SerializedName("TRACK_NUM")
    @Expose
    private String TRACKNUM;
    @SerializedName("GN_ID")
    @Expose
    private String GNID;
    @Expose
    private List<TITLE_> TITLE = new ArrayList<TITLE_>();

    /**
     * 
     * @return
     *     The TRACKNUM
     */
    public String getTRACKNUM() {
        return TRACKNUM;
    }

    /**
     * 
     * @param TRACKNUM
     *     The TRACK_NUM
     */
    public void setTRACKNUM(String TRACKNUM) {
        this.TRACKNUM = TRACKNUM;
    }

    /**
     * 
     * @return
     *     The GNID
     */
    public String getGNID() {
        return GNID;
    }

    /**
     * 
     * @param GNID
     *     The GN_ID
     */
    public void setGNID(String GNID) {
        this.GNID = GNID;
    }

    /**
     * 
     * @return
     *     The TITLE
     */
    public List<TITLE_> getTITLE() {
        return TITLE;
    }

    /**
     * 
     * @param TITLE
     *     The TITLE
     */
    public void setTITLE(List<TITLE_> TITLE) {
        this.TITLE = TITLE;
    }

}
