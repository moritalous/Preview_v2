package forest.rice.field.k.base;

import android.view.Menu;

import forest.rice.field.k.preview.entity.Tracks;

public interface NavigationDrawerBaseInterface {

    public void setChecked(int id);

    public Menu getNavigationMenu();

    public void setTitle(CharSequence title);

    public void setTracks(Tracks tracks);

    public void clearTracks();

    public Tracks getTracks();

}
