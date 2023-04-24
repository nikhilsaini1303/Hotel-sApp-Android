package com.example.assignment_1_android;

import java.util.ArrayList;

public class HotelData {

    String q;
    String rid;
    ArrayList<sr> sr;

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public ArrayList<HotelData.sr> getSr() {
        return sr;
    }

    public void setSr(ArrayList<HotelData.sr> sr) {
        this.sr = sr;
    }

    public class sr {
        regionNames regionNames;

        hierarchyInfo hierarchyInfo;

        public HotelData.hierarchyInfo getHierarchyInfo() {
            return hierarchyInfo;
        }

        public void setHierarchyInfo(HotelData.hierarchyInfo hierarchyInfo) {
            this.hierarchyInfo = hierarchyInfo;
        }

        public HotelData.regionNames getRegionNames() {
            return regionNames;
        }

        public void setRegionNames(HotelData.regionNames regionNames) {
            this.regionNames = regionNames;
        }
    }

    public class regionNames {
        String fullName;
        String shortName;
        String displayName;
        String primaryDisplayName;
        String secondaryDisplayName;
        String lastSearchName;

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getPrimaryDisplayName() {
            return primaryDisplayName;
        }

        public void setPrimaryDisplayName(String primaryDisplayName) {
            this.primaryDisplayName = primaryDisplayName;
        }

        public String getSecondaryDisplayName() {
            return secondaryDisplayName;
        }

        public void setSecondaryDisplayName(String secondaryDisplayName) {
            this.secondaryDisplayName = secondaryDisplayName;
        }

        public String getLastSearchName() {
            return lastSearchName;
        }

        public void setLastSearchName(String lastSearchName) {
            this.lastSearchName = lastSearchName;
        }
    }

    public class hierarchyInfo {
        country country;

        public HotelData.country getCountry() {
            return country;
        }

        public void setCountry(HotelData.country country) {
            this.country = country;
        }
    }

    public class country {
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}









