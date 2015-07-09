
package forest.rice.field.k.gracenote;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class RESPONSE {

    @Expose
    private String STATUS;
    @Expose
    private List<forest.rice.field.k.gracenote.RADIO> RADIO = new ArrayList<forest.rice.field.k.gracenote.RADIO>();
    @Expose
    private List<forest.rice.field.k.gracenote.ALBUM> ALBUM = new ArrayList<forest.rice.field.k.gracenote.ALBUM>();

    /**
     * 
     * @return
     *     The STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * 
     * @param STATUS
     *     The STATUS
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    /**
     * 
     * @return
     *     The RADIO
     */
    public List<forest.rice.field.k.gracenote.RADIO> getRADIO() {
        return RADIO;
    }

    /**
     * 
     * @param RADIO
     *     The RADIO
     */
    public void setRADIO(List<forest.rice.field.k.gracenote.RADIO> RADIO) {
        this.RADIO = RADIO;
    }

    /**
     * 
     * @return
     *     The ALBUM
     */
    public List<forest.rice.field.k.gracenote.ALBUM> getALBUM() {
        return ALBUM;
    }

    /**
     * 
     * @param ALBUM
     *     The ALBUM
     */
    public void setALBUM(List<forest.rice.field.k.gracenote.ALBUM> ALBUM) {
        this.ALBUM = ALBUM;
    }

}
