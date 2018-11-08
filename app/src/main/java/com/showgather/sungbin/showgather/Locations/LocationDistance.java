package com.showgather.sungbin.showgather.Locations;

public class LocationDistance {
    public double distance(double lat1,double lon1,double lat2,double lon2,String unit){
        double theta = lon1-lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist*60*1.1515;

        if(unit=="kilometer"){
            dist=dist*1.609344;
        }else if(unit=="meter"){
            dist = dist*1609.344;
        }
        return (dist);
    }

    public double deg2rad(double deg){return (deg*Math.PI/180.0);}
    public double rad2deg(double rad){return (rad*180/Math.PI);}
}

