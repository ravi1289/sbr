package com.flipkart.studio34;

/**
 * Created by ravi.krishnan on 25/02/16.
 */
public class CurrentSongStateEvent {
    private int songstate;

    public int getSongstate() {
        return songstate;
    }

    public CurrentSongStateEvent(int songstate) {

        this.songstate = songstate;
    }
}
