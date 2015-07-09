
package forest.rice.field.k.gracenote;

import com.google.gson.annotations.Expose;

public class GENRE {

    @Expose
    private String NUM;
    @Expose
    private String ID;
    @Expose
    private String VALUE;

    /**
     * 
     * @return
     *     The NUM
     */
    public String getNUM() {
        return NUM;
    }

    /**
     * 
     * @param NUM
     *     The NUM
     */
    public void setNUM(String NUM) {
        this.NUM = NUM;
    }

    /**
     * 
     * @return
     *     The ID
     */
    public String getID() {
        return ID;
    }

    /**
     * 
     * @param ID
     *     The ID
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * 
     * @return
     *     The VALUE
     */
    public String getVALUE() {
        return VALUE;
    }

    /**
     * 
     * @param VALUE
     *     The VALUE
     */
    public void setVALUE(String VALUE) {
        this.VALUE = VALUE;
    }

}
