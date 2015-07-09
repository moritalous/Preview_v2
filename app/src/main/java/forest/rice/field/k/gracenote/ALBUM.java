
package forest.rice.field.k.gracenote;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ALBUM {

    @Expose
    private String ORD;
    @SerializedName("GN_ID")
    @Expose
    private String GNID;
    @SerializedName("TRACK_COUNT")
    @Expose
    private String TRACKCOUNT;
    @Expose
    private List<forest.rice.field.k.gracenote.ARTIST> ARTIST = new ArrayList<forest.rice.field.k.gracenote.ARTIST>();
    @Expose
    private List<forest.rice.field.k.gracenote.TITLE> TITLE = new ArrayList<forest.rice.field.k.gracenote.TITLE>();
    @Expose
    private List<forest.rice.field.k.gracenote.GENRE> GENRE = new ArrayList<forest.rice.field.k.gracenote.GENRE>();
    @Expose
    private List<forest.rice.field.k.gracenote.TRACK> TRACK = new ArrayList<forest.rice.field.k.gracenote.TRACK>();

    /**
     * 
     * @return
     *     The ORD
     */
    public String getORD() {
        return ORD;
    }

    /**
     * 
     * @param ORD
     *     The ORD
     */
    public void setORD(String ORD) {
        this.ORD = ORD;
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
     *     The TRACKCOUNT
     */
    public String getTRACKCOUNT() {
        return TRACKCOUNT;
    }

    /**
     * 
     * @param TRACKCOUNT
     *     The TRACK_COUNT
     */
    public void setTRACKCOUNT(String TRACKCOUNT) {
        this.TRACKCOUNT = TRACKCOUNT;
    }

    /**
     * 
     * @return
     *     The ARTIST
     */
    public List<forest.rice.field.k.gracenote.ARTIST> getARTIST() {
        return ARTIST;
    }

    /**
     * 
     * @param ARTIST
     *     The ARTIST
     */
    public void setARTIST(List<forest.rice.field.k.gracenote.ARTIST> ARTIST) {
        this.ARTIST = ARTIST;
    }

    /**
     * 
     * @return
     *     The TITLE
     */
    public List<forest.rice.field.k.gracenote.TITLE> getTITLE() {
        return TITLE;
    }

    /**
     * 
     * @param TITLE
     *     The TITLE
     */
    public void setTITLE(List<forest.rice.field.k.gracenote.TITLE> TITLE) {
        this.TITLE = TITLE;
    }

    /**
     * 
     * @return
     *     The GENRE
     */
    public List<forest.rice.field.k.gracenote.GENRE> getGENRE() {
        return GENRE;
    }

    /**
     * 
     * @param GENRE
     *     The GENRE
     */
    public void setGENRE(List<forest.rice.field.k.gracenote.GENRE> GENRE) {
        this.GENRE = GENRE;
    }

    /**
     * 
     * @return
     *     The TRACK
     */
    public List<forest.rice.field.k.gracenote.TRACK> getTRACK() {
        return TRACK;
    }

    /**
     * 
     * @param TRACK
     *     The TRACK
     */
    public void setTRACK(List<forest.rice.field.k.gracenote.TRACK> TRACK) {
        this.TRACK = TRACK;
    }

}
