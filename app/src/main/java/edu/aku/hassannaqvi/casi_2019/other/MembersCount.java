package edu.aku.hassannaqvi.casi_2019.other;

import java.util.Map;

/**
 * Created by ali.azaz on 02/12/18.
 */

public class MembersCount {

    int mwra, wra;
    Map<Integer, Map<Integer, Integer>> members;

    public int getMwra() {
        return mwra;
    }

    public void setMwra(int mwra) {
        this.mwra = mwra;
    }

    public int getWra() {
        return wra;
    }

    public void setWra(int wra) {
        this.wra = wra;
    }

    public Map<Integer, Map<Integer, Integer>> getMembers() {
        return members;
    }

    public void setMembers(Map<Integer, Map<Integer, Integer>> members) {
        this.members = members;
    }

    public void setMembers(int type, Map<Integer, Integer> members) {
        this.members.put(type, members);
    }

    public int getCount() {
        return members.get(1).get(1) + members.get(1).get(2);
    }

    public int getEligibleCount() {
        return getMembers().get(2).get(1) + getMembers().get(2).get(2)
                + getMembers().get(3).get(1) + getMembers().get(3).get(2)
                + getMwra() + getWra();
    }
}
