
package forest.rice.field.k.gracenote;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class GracenoteJson {

    @Expose
    private List<forest.rice.field.k.gracenote.RESPONSE> RESPONSE = new ArrayList<forest.rice.field.k.gracenote.RESPONSE>();

    /**
     * 
     * @return
     *     The RESPONSE
     */
    public List<forest.rice.field.k.gracenote.RESPONSE> getRESPONSE() {
        return RESPONSE;
    }

    /**
     * 
     * @param RESPONSE
     *     The RESPONSE
     */
    public void setRESPONSE(List<forest.rice.field.k.gracenote.RESPONSE> RESPONSE) {
        this.RESPONSE = RESPONSE;
    }

}
